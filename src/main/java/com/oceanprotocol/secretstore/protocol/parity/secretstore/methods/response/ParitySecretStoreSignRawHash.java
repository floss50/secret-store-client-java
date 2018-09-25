package com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response;

import org.web3j.protocol.core.Response;

public class ParitySecretStoreSignRawHash extends Response<String> {
    public String getSignedDocumentKeyId() {
        return getResult();
    }
}
