package com.oceanprotocol.secretstore.helpers;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;

/**
 * Enconding Helper functions
 */
public abstract class EncodingHelper {

    /**
     * Encodes a String in Hex
     * @param input
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeToHex(String input) throws UnsupportedEncodingException {
        return DatatypeConverter.printHexBinary(input.getBytes("UTF-8"));
    }

    /**
     * Decodes a Hex String
     * @param input
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decodeHex(String input) throws UnsupportedEncodingException {
        return new String(
                DatatypeConverter.parseHexBinary(
                        removeEthereumAddressPrefix(input)), "UTF-8"
        );
    }

    /**
     * If exists, removes the "0x" prefix of a String
     * @param address
     * @return
     */
    public static String removeEthereumAddressPrefix(String address)    {
        if (address.startsWith("0x"))
            return address.replaceFirst("0x", "");
        return address;
    }
}
