package me.hsgamer.testcompositechaincode;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.execution.SerializerInterface;
import org.hyperledger.fabric.contract.metadata.TypeSchema;
import org.hyperledger.fabric.contract.routing.TypeRegistry;

public class GensonSerializer implements SerializerInterface {
    private static final Genson genson = new Genson();

    @Override
    public byte[] toBuffer(Object value, TypeSchema ts) {
        return genson.serialize(value).getBytes();
    }

    @Override
    public Object fromBuffer(byte[] buffer, TypeSchema ts) {
        return genson.deserialize(new String(buffer), ts.getTypeClass(TypeRegistry.getRegistry()));
    }
}
