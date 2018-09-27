package com.oceanprotocol.secretstore.helpers;

/**
 * Secret Store Helper functions
 */
public final class SecretStoreHelper {

    /**
     * Given an input string generates a Document Key ID (SHA256 Hex)
     * @param doc seed for generating the document key id
     * @return document key id
     */
    public static String generateDocumentKeyId(String doc)   {
        return EncryptionHelper.encryptSHA256(doc);
    }

    /**
     * Removes quotes of a String
     * @param input input string
     * @return string without quotes
     */
    public static String removeQuotes(String input) {
        return input.replace("\"", "");
    }
}
