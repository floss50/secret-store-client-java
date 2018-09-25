package com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response;

import org.web3j.protocol.core.Response;

public class ParitySecretStoreGenerateDocumentKey extends Response<EncryptionKeysDocument> {

    public EncryptionKeysDocument getEncryptionKeysDocument() {
        return getResult();
    }

    public String getStringResult() {
        return getResult().toString();
    }


}
