package com.oceanprotocol.secretstore.core;

import com.oceanprotocol.secretstore.helpers.EncodingHelper;
import com.oceanprotocol.secretstore.protocol.parity.secretstore.JsonRpcSecretStoreRpc;
import com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response.EncryptionKeysDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.List;

public class EvmDto {

    protected static final Logger log = LogManager.getLogger(EvmDto.class);

    private static EvmDto dto= null;
    private String evmUrl;
    private Web3j web3;
    private JsonRpcSecretStoreRpc ssRpc;
    private String address;
    private String password;

    public static EvmDto builder(String url, String address, String password) {
        if (dto == null)    {
            dto= new EvmDto(url, address, password);
        }
        return dto;
    }

    private EvmDto(String url, String address, String password)    {
        log.debug("Initializing Evm Dto: " + url);
        this.address= address;
        this.password= password;
        this.evmUrl= url;
        this.web3 = Web3j.build(new HttpService(this.evmUrl));
        this.ssRpc= new JsonRpcSecretStoreRpc(new HttpService(this.evmUrl));

    }


    public String signDocumentKeyId(String documentKeyId) throws IOException {
        if (!documentKeyId.startsWith("0x"))
            documentKeyId= "0x" + documentKeyId;
        log.debug("Signing DocumentKeyId: " + documentKeyId);
        return ssRpc.paritySecretStoreSignRawHash(address, password, documentKeyId)
                .send()
                .getResult();

    }

    public EncryptionKeysDocument generateDocumentKeyFromKey(String serverKey) throws IOException {
        log.debug("Generating DocumentKey for address: " + address);
        return ssRpc.paritySecretStoreGenerateDocumentKey(address, password, serverKey)
                .send()
                .getEncryptionKeysDocument();

    }

    public String documentEncryption(String encryptedKey, String url) throws IOException {
        log.debug("Encrypting document from address: " + address);
        return ssRpc.paritySecretStoreDocumentEncrypt(
                    address,
                    password,
                    encryptedKey,
                    "0x" + EncodingHelper.encodeToHex(url))
                .send()
                .getResult();
    }

    public String shadowDecrypt(String descryptedSecret, String commonPoint, List<String> descryptShadows, String encryptedDocument) throws IOException {
        log.debug("Decryption document requested from address: " + address);
        return EncodingHelper.decodeHex(
                ssRpc.paritySecretStoreDocumentDecrypt(
                    address,
                    password,
                    descryptedSecret,
                    commonPoint,
                    descryptShadows,
                    encryptedDocument)
                .send()
                .getDecryptedDocument()
        );
    }

}
