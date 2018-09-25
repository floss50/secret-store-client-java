package com.oceanprotocol.secretstore.auth;

import org.junit.*;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;

public class PublisherWorkerIT {

    private static final String DOCUMENT_ID = UUID.randomUUID().toString();
    private static final String SS_URL = "http://localhost:8010";
    private static final String EVM_URL = "http://localhost:8545";

    private static final String ALICE_ADDRESS= "0xb3e6499f2b07817ee8e35c8e63cb200df2055d91";
    private static final String ALICE_PASSWORD= "1234";
    private static final String BOB_ADDRESS= "0x9629f11b7a43f44892d9722b4bfc0675c7cc4bf9";
    private static final String BOB_PASSWORD= "1234";

    private static final String CONTENT_URL = "http://catplanet.org/wp-content/uploads/2014/02/I-can-xplain.jpg";
    private static PublisherWorker publisher;
    private static ConsumerWorker consumer;

    @BeforeClass
    public static void setUp() {
        // Initializing the Publisher using Alice address
        publisher= new PublisherWorker(SS_URL, EVM_URL, ALICE_ADDRESS, ALICE_PASSWORD);

        // Initializing the Consumer using Bob address
        consumer= new ConsumerWorker(SS_URL, EVM_URL, BOB_ADDRESS, BOB_PASSWORD);
    }

    @AfterClass
    public static void tearDown() {
    }

    @Test
    public void publishConsumeDocument() throws IOException {

        String docEncrypted= publisher.publishDocument(DOCUMENT_ID, CONTENT_URL);

        assertTrue(docEncrypted.length()>2);

        String document= consumer.consumeDocument(DOCUMENT_ID, docEncrypted);

        assertEquals(CONTENT_URL, document);
    }
}