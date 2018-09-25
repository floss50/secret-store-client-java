package com.oceanprotocol.secretstore.protocol.parity.secretstore.methods.response;

import org.json.JSONObject;

public class EncryptionKeysDocument {

    public String common_point;
    public String encrypted_key;
    public String encrypted_point;

    public EncryptionKeysDocument() {}

    public EncryptionKeysDocument(String doc)   {
        new EncryptionKeysDocument(new JSONObject(doc));
    }

    public EncryptionKeysDocument(JSONObject doc)   {
        setCommonPoint(doc.getString("common_point"));
        setEncryptedKey(doc.getString("encrypted_key"));
        setEncryptedPoint(doc.getString("encrypted_point"));
    }

    public String getCommonPoint() {
        return common_point;
    }

    public void setCommonPoint(String commonPoint) {
        this.common_point = commonPoint;
    }

    public String getEncryptedKey() {
        return encrypted_key;
    }

    public void setEncryptedKey(String encryptedKey) {
        this.encrypted_key = encryptedKey;
    }

    public String getEncryptedPoint() {
        return encrypted_point;
    }

    public void setEncryptedPoint(String encryptedPoint) {
        this.encrypted_point = encryptedPoint;
    }

    @Override
    public String toString() {
        return "{" +
                "'commonPoint': '" + common_point + '\'' +
                ",'encryptedKey': '" + encrypted_key + '\'' +
                ",'encryptedPoint': '" + encrypted_point + '\'' +
                '}';
    }
}
