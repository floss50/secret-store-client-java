package com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response;

import org.web3j.protocol.core.Response;

public class ParitySecretStoreShadowDecrypt extends Response<String> {

    public String getDecryptedDocument() {
        return getResult();
    }
}
