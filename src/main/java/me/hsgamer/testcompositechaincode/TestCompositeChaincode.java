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
                ctx.getStub().putState("a_1", "value1".getBytes());
                ctx.getStub().putState("a_2", "value2".getBytes());
                ctx.getStub().putState("b_1", "value3".getBytes());
                ctx.getStub().putState("b_2", "value4".getBytes());
        }

        @Transaction(intent = Transaction.TYPE.EVALUATE)
        public List<AssetResponse> getRange(Context ctx, String start, String end) {
                QueryResultsIterator<KeyValue> results = ctx.getStub().getStateByRange(start, end);
                List<AssetResponse> list = new ArrayList<>();
                for (KeyValue result : results) {
                        list.add(new AssetResponse(result.getKey(), result.getStringValue()));
                }
                return list;
        }

        @Transaction(intent = Transaction.TYPE.EVALUATE)
        public AssetResponse get(Context ctx, String key) {
                byte[] value = ctx.getStub().getState(key);
                return new AssetResponse(key, new String(value));
        }

        @Transaction(intent = Transaction.TYPE.SUBMIT)
        public void put(Context ctx, String key, String value) {
                ctx.getStub().putState(key, value.getBytes());
        }
}
