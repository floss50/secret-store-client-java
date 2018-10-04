package com.oceanprotocol.secretstore.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class AccessServiceAgreement extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506105c9806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680638130b289146100725780639d6a7348146100db578063b36a9a7c14610186578063c106cb41146101ef578063ea01e2cc14610258575b600080fd5b34801561007e57600080fd5b506100c1600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080356000191690602001909291905050506102c1565b604051808215151515815260200191505060405180910390f35b3480156100e757600080fd5b5061016c600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610380565b604051808215151515815260200191505060405180910390f35b34801561019257600080fd5b506101d5600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080356000191690602001909291905050506103c0565b604051808215151515815260200191505060405180910390f35b3480156101fb57600080fd5b5061023e600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803560001916906020019092919050505061049e565b604051808215151515815260200191505060405180910390f35b34801561026457600080fd5b506102a7600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803560001916906020019092919050505061055e565b604051808215151515815260200191505060405180910390f35b600080600080846000191660001916815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000018190555081600019168373ffffffffffffffffffffffffffffffffffffffff167f995f7a5bcd564cd795537645afdefebc7676312d99f138bd06be6366f485b8bc6000604051808215151515815260200191505060405180910390a392915050565b600060018214156103a45761039d8461039885610572565b61049e565b90506103b9565b6103b6846103b185610572565b6102c1565b90505b9392505050565b60006001600080846000191660001916815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154141515610494576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260188152602001807f5573657220776173206e6f742077686974656c6973746564000000000000000081525060200191505060405180910390fd5b6001905092915050565b60006001600080846000191660001916815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000018190555081600019168373ffffffffffffffffffffffffffffffffffffffff167f995f7a5bcd564cd795537645afdefebc7676312d99f138bd06be6366f485b8bc6001604051808215151515815260200191505060405180910390a392915050565b600061056a83836103c0565b905092915050565b6000606082905060008151141561058f5760006001029150610597565b602083015191505b509190505600a165627a7a72305820eacc15115ce650a29428a82da7eba86a812892ecd64ffeebdc081c52fd555c3c0029";

    public static final String FUNC_CHECKPERMISSIONS = "checkPermissions";

    public static final String FUNC_CHECK_PERMISSIONS = "check_permissions";

    public static final String FUNC_SETPERMISSIONS = "setPermissions";

    public static final String FUNC_GRANTPERMISSIONS = "grantPermissions";

    public static final String FUNC_REVOKEPERMISSIONS = "revokePermissions";

    public static final Event ACCESSPERMISSIONS_EVENT = new Event("AccessPermissions", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bool>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("17", "0x6d6a34f2be1e76902a2fde049f317610cdf453eb");
    }

    protected AccessServiceAgreement(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AccessServiceAgreement(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<AccessPermissionsEventResponse> getAccessPermissionsEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ACCESSPERMISSIONS_EVENT, transactionReceipt);
        ArrayList<AccessPermissionsEventResponse> responses = new ArrayList<AccessPermissionsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AccessPermissionsEventResponse typedResponse = new AccessPermissionsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.document = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.status = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AccessPermissionsEventResponse> accessPermissionsEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, AccessPermissionsEventResponse>() {
            @Override
            public AccessPermissionsEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ACCESSPERMISSIONS_EVENT, log);
                AccessPermissionsEventResponse typedResponse = new AccessPermissionsEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.document = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.status = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<AccessPermissionsEventResponse> accessPermissionsEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACCESSPERMISSIONS_EVENT));
        return accessPermissionsEventObservable(filter);
    }

    public RemoteCall<Boolean> checkPermissions(String user, byte[] document) {
        final Function function = new Function(FUNC_CHECKPERMISSIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.generated.Bytes32(document)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Boolean> check_permissions(String user, byte[] document) {
        final Function function = new Function(FUNC_CHECK_PERMISSIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.generated.Bytes32(document)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> setPermissions(String user, String document, BigInteger status) {
        final Function function = new Function(
                FUNC_SETPERMISSIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.Utf8String(document), 
                new org.web3j.abi.datatypes.generated.Uint256(status)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> grantPermissions(String user, byte[] document) {
        final Function function = new Function(
                FUNC_GRANTPERMISSIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.generated.Bytes32(document)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> revokePermissions(String user, byte[] document) {
        final Function function = new Function(
                FUNC_REVOKEPERMISSIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.generated.Bytes32(document)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<AccessServiceAgreement> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AccessServiceAgreement.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AccessServiceAgreement> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AccessServiceAgreement.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static AccessServiceAgreement load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AccessServiceAgreement(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static AccessServiceAgreement load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AccessServiceAgreement(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class AccessPermissionsEventResponse {
        public Log log;

        public String user;

        public byte[] document;

        public Boolean status;
    }
}
