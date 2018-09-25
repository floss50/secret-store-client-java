package com.oceanprotocol.secretstore.protocol.parity.secretstore;

import com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response.ParitySecretStoreGenerateDocumentEncrypted;
import com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response.ParitySecretStoreGenerateDocumentKey;
import com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response.ParitySecretStoreShadowDecrypt;
import com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response.ParitySecretStoreSignRawHash;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.core.Request;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * JSON-RPC 2.0 factory implementation for Parity.
 */


public class JsonRpcSecretStoreRpc extends JsonRpc2_0Admin {

    public JsonRpcSecretStoreRpc(Web3jService web3jService) {
        super(web3jService);
    }

    public JsonRpcSecretStoreRpc(Web3jService web3jService, long pollingInterval, ScheduledExecutorService scheduledExecutorService) {
        super(web3jService, pollingInterval, scheduledExecutorService);
    }

    public Request<?, ParitySecretStoreSignRawHash> paritySecretStoreSignRawHash(
            String accountId, String password, String docKeyId) {

        return new Request<>(
                "secretstore_signRawHash",
                Arrays.asList(accountId, password, docKeyId),
                web3jService,
                ParitySecretStoreSignRawHash.class);
    }

    public Request<?, ParitySecretStoreGenerateDocumentKey> paritySecretStoreGenerateDocumentKey(
            String accountId, String password, String docKeyId) {

        return new Request<>(
                "secretstore_generateDocumentKey",
                Arrays.asList(accountId, password, docKeyId),
                web3jService,
                ParitySecretStoreGenerateDocumentKey.class);
    }


    public Request<?, ParitySecretStoreGenerateDocumentEncrypted> paritySecretStoreDocumentEncrypt(
            String accountId, String password, String encryptedKey, String url) {

        return new Request<>(
                "secretstore_encrypt",
                Arrays.asList(accountId, password, encryptedKey, url),
                web3jService,
                ParitySecretStoreGenerateDocumentEncrypted.class);
    }

    public Request<?, ParitySecretStoreShadowDecrypt> paritySecretStoreDocumentDecrypt(
            String accountId, String password, String descryptedSecret, String commonPoint, List<String> descryptShadows, String encryptedDocument) {

        return new Request<>(
                "secretstore_shadowDecrypt",
                Arrays.asList(accountId, password, descryptedSecret, commonPoint, descryptShadows, encryptedDocument),
                web3jService,
                ParitySecretStoreShadowDecrypt.class);
    }

}

