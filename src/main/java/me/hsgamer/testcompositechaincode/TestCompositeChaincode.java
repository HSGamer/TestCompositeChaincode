package me.hsgamer.testcompositechaincode;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.ArrayList;
import java.util.List;

@Contract(name = "TestCompositeChaincode",
        info = @Info(title = "Test composite contract",
                description = "A contract to test the composite key function",
                version = "0.0.1"
        ))
@Default
public class TestCompositeChaincode implements ContractInterface {
        @Transaction(intent = Transaction.TYPE.SUBMIT)
        public void init(Context ctx) {
                String key = ctx.getStub().createCompositeKey("a", "key1").toString();
                ctx.getStub().putState(key, "value1".getBytes());
                key = ctx.getStub().createCompositeKey("a", "key2").toString();
                ctx.getStub().putState(key, "value2".getBytes());

                key = ctx.getStub().createCompositeKey("b", "key1").toString();
                ctx.getStub().putState(key, "value1".getBytes());
                key = ctx.getStub().createCompositeKey("b", "key2").toString();
                ctx.getStub().putState(key, "value2".getBytes());
        }

        @Transaction(intent = Transaction.TYPE.EVALUATE)
        public List<AssetResponse> getObject(Context ctx, String object) {
                CompositeKey key = ctx.getStub().createCompositeKey(object);
                QueryResultsIterator<KeyValue> results = ctx.getStub().getStateByPartialCompositeKey(key);
                List<AssetResponse> list = new ArrayList<>();
                for (KeyValue result : results) {
                        list.add(new AssetResponse(result.getKey(), result.getStringValue()));
                }
                return list;
        }

        @Transaction(intent = Transaction.TYPE.EVALUATE)
        public List<AssetResponse> getObjectAndKey(Context ctx, String object, String key) {
                CompositeKey compositeKey = ctx.getStub().createCompositeKey(object, key);
                QueryResultsIterator<KeyValue> results = ctx.getStub().getStateByPartialCompositeKey(compositeKey);
                List<AssetResponse> list = new ArrayList<>();
                for (KeyValue result : results) {
                        list.add(new AssetResponse(result.getKey(), result.getStringValue()));
                }
                return list;
        }

        @Transaction(intent = Transaction.TYPE.SUBMIT)
        public void putObjectAndKey(Context ctx, String object, String key, String value) {
                CompositeKey compositeKey = ctx.getStub().createCompositeKey(object, key);
                ctx.getStub().putState(compositeKey.toString(), value.getBytes());
        }
}
