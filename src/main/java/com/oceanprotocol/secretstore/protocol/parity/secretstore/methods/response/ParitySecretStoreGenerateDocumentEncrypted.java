package com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response;

import org.web3j.protocol.core.Response;

public class ParitySecretStoreGenerateDocumentEncrypted  extends Response<String> {
    public String getEncryptedDocument() {
        return getResult();
    }
}
