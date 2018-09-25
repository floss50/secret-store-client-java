package com.oceanprotocol.secretstore.auth;

import com.oceanprotocol.secretstore.core.EvmDto;
import com.oceanprotocol.secretstore.core.SecretStoreDto;
import com.oceanprotocol.secretstore.helpers.SecretStoreHelper;
import com.oceanprotocol.secretstore.models.HttpResponse;
import com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response.EncryptionKeysDocument;
import org.apache.commons.httpclient.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;

/**
 * Class useful to abstract all the EVM & Secret Store requests allowing to a user to encrypt a document
 */
public class PublisherWorker {

    /**
     * Logger
     */
    protected static final Logger log = LogManager.getLogger(PublisherWorker.class);

    /**
     * Secret Store interface instance
     */
    private SecretStoreDto ssDto;

    /**
     * Parity EVM interface instance
     */
    private EvmDto evmDto;

    /**
     * Publisher constructor. Initialize the Secret Store & Parity EVM connections using the urls and connection parameters
     * @param ssUrl Secret Store URL (i.e: http://localhost:8010/)
     * @param evmUrl Parity EVM URL (i.e: http://localhost:8545/)
     * @param address Publisher Ethereum address (i.e: 0xb3e6499f2b07817ee8e35c8e63cb200df2055d91)
     * @param password Publisher Ethereum account password
     */
    public PublisherWorker(String ssUrl, String evmUrl, String address, String password)  {
        this(
                SecretStoreDto.builder(ssUrl),
                EvmDto.builder(evmUrl, address, password)
        );
    }

    /**
     * Publisher constructor. It assigns the Secret Store and Parity EVM DTO's
     * @param ssDto Secret Store DTO object (SecretStoreDto.class)
     * @param evmDto Parity EVM DTO object (EvmDto.class)
     */
    public PublisherWorker(SecretStoreDto ssDto, EvmDto evmDto) {
        this.ssDto= ssDto;
        this.evmDto= evmDto;
    }

    /**
     * Given a documentId and a document, the method negotiate with the Parity EVM & Secret Store to
     * encrypt the document and store the decryption keys in the Secret Store.
     * If in the Secret Store the acl_contract attribute is specified, an on-chain access control validation
     * will be performed in the consumption.
     * @param documentId Identifier of the document
     * @param document Document content
     * @return Document content encrypted
     * @throws IOException
     */
    public String publishDocument(String documentId, String document) throws IOException {

        String signedDocKey;
        String docEncrypted;

        try {

            log.debug("Generating Document Key: " + documentId);
            String documentKeyId= SecretStoreHelper.generateDocumentKeyId(documentId);
            log.debug("Encrypted Document Key: " + documentKeyId);

            log.debug("EVM: Signing Document Key");
            signedDocKey = evmDto.signDocumentKeyId(documentKeyId);

            log.debug("SecretStore: Generating Secret Store Server key");
            String ssServerKey= SecretStoreHelper.removeQuotes(
                    ssDto.generateServerKey(documentKeyId, signedDocKey));

            log.debug("EVM: Generate the Document key from the Secret Store key");
            EncryptionKeysDocument docKeys = evmDto.generateDocumentKeyFromKey(ssServerKey);

            log.debug("EVM: Document encryption");
            docEncrypted= evmDto.documentEncryption(docKeys.getEncryptedKey(), document);

            log.debug("SecretStore: Store the Document key");
            HttpResponse result= ssDto.storeDocumentKey(documentKeyId, signedDocKey, docKeys.getCommonPoint(), docKeys.getEncryptedPoint());

            log.debug("Document Stored " + result.getBody());

        } catch (HttpException e) {
            log.error("HttpException: Unable to register the document: " + e.getMessage());
            throw new IOException("Unable to encrypt document");
        } catch (IOException e) {
            log.error("IOException: Unable to register the document: " + e.getMessage());
            throw new IOException("Unable to encrypt document");
        } catch (Exception e) {
            log.error("Exception: Unable to register the document: " + e.getMessage());
            throw new IOException("Unable to encrypt document");
        }

        return docEncrypted;
    }


}
