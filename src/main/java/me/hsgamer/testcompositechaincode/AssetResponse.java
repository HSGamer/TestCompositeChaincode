package me.hsgamer.testcompositechaincode;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@DataType
public class AssetResponse {
    @Property
    String key;
    @Property
    String value;
}
