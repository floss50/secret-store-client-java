package com.oceanprotocol.secretstore.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oceanprotocol.secretstore.helpers.EncodingHelper;
import com.oceanprotocol.secretstore.helpers.HttpHelper;
import com.oceanprotocol.secretstore.models.HttpResponse;
import com.oceanprotocol.secretstore.models.secretstore.DecriptionKeys;
import com.oceanprotocol.secretstore.protocol.parity.secretstore.JsonRpcSecretStoreRpc;
import org.apache.commons.httpclient.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class SecretStoreDto {

    protected static final Logger log = LogManager.getLogger(SecretStoreDto.class);

    private static SecretStoreDto dto= null;
    private JsonRpcSecretStoreRpc ssRpc= null;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String ssUrl;
    private final String THRESHOLD = "1";

    public static SecretStoreDto builder(String url) {
        if (dto == null)    {
            dto= new SecretStoreDto(url);
        }
        return dto;
    }

    private SecretStoreDto(String url)    {
        log.debug("Initializing Secret Store Dto: " + url);
        this.ssUrl= url;
    }

    /**
     * PUBLISHING
     */

    public String generateServerKey(String documentKey, String signedDocKey) throws HttpException, IOException {
        return generateServerKey(documentKey, signedDocKey, THRESHOLD);
    }

    public String generateServerKey(String documentKey, String signedDocKey, String threshold) throws HttpException, IOException {
        String url= ssUrl + "/shadow/" +
                documentKey + "/" +
                EncodingHelper.removeEthereumAddressPrefix(signedDocKey) + "/" +
                threshold;

        String result= HttpHelper.httpClientPostBody(url);
        if (null == result || result.isEmpty())
            throw new IOException("Unable to generate Server Key");
        return result;
    }

    public HttpResponse storeDocumentKey(String documentKey, String signedDocKey, String commonPoint, String encryptedPoint) throws HttpException, IOException {
        String url= ssUrl + "/shadow/" +
                documentKey + "/" +
                EncodingHelper.removeEthereumAddressPrefix(signedDocKey) + "/" +
                EncodingHelper.removeEthereumAddressPrefix(commonPoint) + "/" +
                EncodingHelper.removeEthereumAddressPrefix(encryptedPoint);

        log.debug("Storing Document key: " + url);

        HttpResponse result= HttpHelper.httpClientPost(url);
        if (null == result || result.getStatusCode() != 200)
            throw new IOException("Unable to store Document query");
        return result;
    }

    /**
     * CONSUMING
     */

    public DecriptionKeys retrieveDocumentKeys(String documentKey, String signedDocKey) throws IOException {
        String url= ssUrl + "/shadow/" +
                documentKey + "/" +
                EncodingHelper.removeEthereumAddressPrefix(signedDocKey);

        log.debug("EVM: retrieveDocumentKeys(url): " + url);
        HttpResponse response= HttpHelper.httpClientGet(url);
        if (null == response || response.getStatusCode() != 200)
            throw new IOException("Unable to retrieve document keys");
        return DecriptionKeys.builder(objectMapper, response.getBody());
    }



}
