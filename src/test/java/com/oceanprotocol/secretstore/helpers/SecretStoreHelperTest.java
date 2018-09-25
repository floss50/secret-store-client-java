package com.oceanprotocol.secretstore.helpers;

import org.junit.Test;

import static org.junit.Assert.*;

public class SecretStoreHelperTest {

    @Test
    public void generateDocumentKeyId() {
        String docId= SecretStoreHelper.generateDocumentKeyId("123");
        assertTrue(docId.length()>0);
    }

    @Test
    public void removeQuotes() {

        assertEquals("123", SecretStoreHelper.removeQuotes("\"123\""));

        assertEquals("123", SecretStoreHelper.removeQuotes("\"\"\"123\""));
    }
}