package com.oceanprotocol.secretstore.auth;

import com.oceanprotocol.secretstore.contracts.AccessServiceAgreement;
import com.oceanprotocol.secretstore.helpers.EncodingHelper;
import com.oceanprotocol.secretstore.helpers.SecretStoreHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class PublishConsumeIT {

    protected static final Logger log = LogManager.getLogger(PublishConsumeIT.class);

    private static final String SS_URL = "http://localhost:8010";
    private static final String EVM_URL = "http://localhost:8545";

    private static AccessServiceAgreement serviceAgreement;
    private static Web3j web3= null;
    private static TransactionManager txManager;
    private static Credentials credentials;

    private static final String TEST_PASSWORD= "";
    private static final String TEST_FILE= "src/test/resources/8fff55a4-dbac-b059-6b8b-bfc7394ed98a.json.testaccount";

    private static final String ACL_CONTRACT_ADDRESS= "0x6d6a34f2be1e76902a2fde049f317610cdf453eb";


    private static final String ALICE_ADDRESS= "0x00a329c0648769A73afAc7F9381E08FB43dBEA72";
    private static final String ALICE_PASSWORD= "";
    private static final String BOB_ADDRESS= "0x9629f11b7a43f44892d9722b4bfc0675c7cc4bf9";
    private static final String BOB_PASSWORD= "1234";

    private static final String CHARLIE_ADDRESS= "0x0d5a52051f75c8e106585541fe2ce05413bd5e18";
    private static final String CHARLIE_PASSWORD= "";

    private static final String CONTENT_URL = "http://catplanet.org/wp-content/uploads/2014/02/I-can-xplain.jpg";
    private static PublisherWorker alicePublisher;
    private static ConsumerWorker bobConsumer;
    private static ConsumerWorker charlieConsumer;

    private static final BigInteger GAS_PRICE= BigInteger.valueOf(1500);
    private static final BigInteger GAS_LIMIT= BigInteger.valueOf(250000);


    @BeforeClass
    public static void setUp() throws IOException, CipherException {
        log.debug("Using ACL Contract: " + ACL_CONTRACT_ADDRESS);

        // Initializing the Publisher using Alice address
        alicePublisher= new PublisherWorker(SS_URL, EVM_URL, ALICE_ADDRESS, ALICE_PASSWORD);

        // Initializing the Consumer using Bob address
        bobConsumer= new ConsumerWorker(SS_URL, EVM_URL, BOB_ADDRESS, BOB_PASSWORD);

        // Initializing the Consumer using Charlie address
        charlieConsumer= new ConsumerWorker(SS_URL, EVM_URL, CHARLIE_ADDRESS, CHARLIE_PASSWORD);

        credentials = WalletUtils.loadCredentials("", TEST_FILE);

        web3 = Web3j.build(new HttpService(EVM_URL));
        txManager= new RawTransactionManager(web3, credentials);
        serviceAgreement= AccessServiceAgreement.load(ACL_CONTRACT_ADDRESS, web3, txManager ,GAS_PRICE, GAS_LIMIT);

    }

    @AfterClass
    public static void tearDown() {
    }

    private static String generateRandomDocumentId()    {
        return UUID.randomUUID().toString();
    }

    @Test
    public void publishConsumeDocument() throws Exception {
        String docId= generateRandomDocumentId();
        String docKeyId= SecretStoreHelper.generateDocumentKeyId(docId);
        byte[] docKeyIdBytes= EncodingHelper.stringToByteArray(docKeyId);

        log.debug("\tdocId: " + docId);
        log.debug("\tdocKeyId: " + docKeyId);

        log.debug("GRANT PERMISSIONS TO BOB -------------");

        TransactionReceipt grantReceipt= serviceAgreement.grantPermissions(BOB_ADDRESS, docKeyIdBytes).send();

        assertTrue(grantReceipt.getBlockNumber().intValue() > 0);

        TimeUnit.SECONDS.sleep(1);

        Boolean isBobGranted= serviceAgreement.checkPermissions(BOB_ADDRESS, docKeyIdBytes).send();
        log.debug("Is Bob Granted? " + isBobGranted);
        assertTrue(isBobGranted);


        log.debug("ALICE PUBLISHING -------------");

        String docEncrypted= alicePublisher.publishDocument(docId, CONTENT_URL);
        assertTrue(docEncrypted.length()>2);


        log.debug("BOB CONSUMING -------------");

        String document= bobConsumer.decryptDocument(docId, docEncrypted);

        log.debug("Document decrypted: " + document);

        assertEquals(CONTENT_URL, document);
    }

    @Test(expected = IOException.class)
    public void publishConsumeHighThreshold() throws IOException {

        String docId= generateRandomDocumentId();
        String docKeyId= SecretStoreHelper.generateDocumentKeyId(docId);
        byte[] docKeyIdBytes= EncodingHelper.stringToByteArray(docKeyId);

        log.debug("\tdocId: " + docId);
        log.debug("\tdocKeyId: " + docKeyId);


        String docEncrypted= alicePublisher.publishDocument(docId, CONTENT_URL, 100);

        assertTrue(docEncrypted.length()>2);

        String document= bobConsumer.decryptDocument(docId, docEncrypted);

        log.debug("Document decrypted: " + document);

        assertEquals(CONTENT_URL, document);
    }

    @Test(expected = IOException.class)
    public void charlieIsTryingToAccess() throws IOException {

        String docId= generateRandomDocumentId();
        String docKeyId= SecretStoreHelper.generateDocumentKeyId(docId);
        byte[] docKeyIdBytes= EncodingHelper.stringToByteArray(docKeyId);

        log.debug("\tdocId: " + docId);
        log.debug("\tdocKeyId: " + docKeyId);


        String docEncrypted= alicePublisher.publishDocument(docId, CONTENT_URL);

        assertTrue(docEncrypted.length()>2);

        String document= charlieConsumer.decryptDocument(docId, docEncrypted);

        log.debug("Document decrypted: " + document);

        assertNotEquals(CONTENT_URL, document);
    }

}