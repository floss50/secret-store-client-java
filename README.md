[![banner](https://raw.githubusercontent.com/oceanprotocol/art/master/github/repo-banner%402x.png)](https://oceanprotocol.com)

# secret-store-client-java
Parity Secret Store Java Client

> 🐳 Secret Store client Library (Java)
> [oceanprotocol.com](https://oceanprotocol.com)

[![Travis (.com)](https://img.shields.io/travis/com/oceanprotocol/secret-store-client-java.svg)](https://travis-ci.com/oceanprotocol/secret-store-client-java)


---

## Table of Contents


   * [secret-store-client-java](#secret-store-client-java)
      * [Table of Contents](#table-of-contents)
      * [Features](#features)
      * [Technical Details](#technical-details)
      * [Pre-requisites](#pre-requisites)
      * [API](#api)
         * [Installing the library](#installing-the-library)
         * [Publishing Documents](#publishing-documents)
         * [Decrypting Documents](#decrypting-documents)
      * [On-chain Permissions](#on-chain-permissions)
      * [Links](#links)
      * [License](#license)



---

## Features

This library allows to encrypt & decrypt secrets using the Parity EVM and Secret Store components.
The library exposes 2 main objects to do that (PublisherWorker & ConsumerWorker).

The **PublisherWorker** class, given a document id and the content of a document, encrypts the document,
store the decryption keys in the distributed vault (Secret Store) and return the encrypted document.

The **ConsumerWorker** class, given a document id and the encrypted document, decrypt the document using
the keys stored in the secret store.


## Technical Details

From the [Parity Secret Store documentation](https://wiki.parity.io/Secret-Store) page:

The Parity Secret Store is core technology that enables:

* distributed elliptic curve (EC) key pair generation - key is generated by several parties using special cryptographic protocol, so that:
  - private key portion remains unknown to every single party;
  - public key portion could be computed on every party and could be safely exposed to external entities;
  - every party hold the ‘share’ of the private key;
  - any subset of t+1 parties could unite to restore the private portion of the key;
  - any subset of less than t+1 parties could not restore the private portion of the key;
* distributed key storage - private key shares are stored separately by every party and are never exposed neither to another parties, nor to external entities;
* threshold retrieval according to blockchain permissions - all operations that are requiring private key, require at least t+1 parties to agree on ‘Permissioning contract’ state.

## Pre-requisites

If you want to run this locally you need the following:

* A URL to a Secret Store node (you can run it locally too)
* A URL to an instance of the Parity EVM client (you should run it locally)
* Consumer and Publisher ethereum accounts
* JVM >= 8

## API

### Installing the library

Typically in Maven you could add the dependency:

```xml
<dependency>
  <groupId>com.oceanprotocol</groupId>
  <artifactId>secretstore-client</artifactId>
  <version>0.0.13</version>
</dependency>
```

You can find more information about how to download/integrate this library in the [Secret Store Client Packagecloud page](https://packagecloud.io/oceanprotocol/secret-store-client)


### Encrypting Documents

Encrypt or decrypt documents require interaction with the Parity blockchain client (for security reasons it's better to have this running locally) and one of the nodes of the Secret Store cluster.
You can initialize the PublisherWorker object passing the URL's to both components, the ethereum address of the user encrypting documents and the password of that ethereum account.

```java
// Initializing the Publisher
publisher= new PublisherWorker(
    "http://localhost:8010",
    "http://localhost:8545",
    "0x123..",
    "password"
);
```

Publishing a document only require an API call:

```java
String docEncrypted= publisher.encryptDocument("my-document-id", contentOfTheDocument);
```


### Decrypting Documents

You can initialize the ConsumerWorker object passing the URL's of the Secret Store and Parity EVM client,
the ethereum address of the user consuming documents and the password of that ethereum account.


```java
// Initializing the Consumer
consumer= new ConsumerWorker(
    "http://localhost:8010",
    "http://localhost:8545",
    "0xabc..",
    "password"
);
```

It's possible to decrypt a document using the decrypt method:

```java
String document= consumer.decryptDocument("my-document-id", docEncrypted);
```


You can find a complete integration test in the PublishConsumeIT file.


## On-chain Permissions

Secret Store incorporate the mechanisms to query a Smart Contract to authorize a Consumer to decrypt a secret.
This library was tested in a Secret Store setup using this feature.
You can find more details in the [PublishConsumeIT.java](https://github.com/oceanprotocol/secret-store-client-java/blob/develop/src/test/java/com/oceanprotocol/secretstore/auth/PublishConsumeIT.java) integration test or in the [Proof of Concept about the Secret Store](https://github.com/oceanprotocol/poc-secret-store).

In order to test the integration with last version of Keeper Service Agreements, you can integrate the SLA [Smart Contracts of the keeper](https://github.com/oceanprotocol/keeper-contracts).

For testing purposes, this library includes the web3j bindings of a testing Smart Contract implementing the authorization phase integrated with the Secret Store.
This Smart Contract ([AccessServiceAgreement.sol](https://github.com/oceanprotocol/poc-secret-store/blob/master/contracts/AccessServiceAgreement.sol)) provided as reference is **TOTALLY INSECURE!**.
It doesn't include the validations to ensure that only the publisher of an asset is allowed to give or revoke grants.

To build the java bindings we use the following command:

```bash
$ web3j truffle generate --javaTypes ../poc-secret-store/build/contracts/AccessServiceAgreement.json -o src/main/java -p com.oceanprotocol.secretstore.contracts
```



## Links

You can find further information about the Secret Store in the following links:

* [Parity Secret Store](https://wiki.parity.io/Secret-Store)
* [Parity Secret Store Tutorial](https://wiki.parity.io/Secret-Store-Tutorial-overview.html)
* [Proof of Concept about the Secret Store](https://github.com/oceanprotocol/poc-secret-store)



## License

```
Copyright 2018 Ocean Protocol Foundation Ltd.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


