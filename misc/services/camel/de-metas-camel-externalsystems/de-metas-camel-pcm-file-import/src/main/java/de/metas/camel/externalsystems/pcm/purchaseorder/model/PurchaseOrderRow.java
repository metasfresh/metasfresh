package de.metas.camel.externalsystems.pcm.purchaseorder.model;

import de.metas.camel.externalsystems.common.CamelProcessorUtil;
import lombok.Getter;
import lombok.ToString;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;

@CsvRecord(separator = ";", skipField = true)
@Getter
@ToString
public class PurchaseOrderRow {
    
    @DataField(pos = 1)
    private String warehouseIdentifier;

    @DataField(pos = 2)
    private String externalHeaderId;

    @DataField(pos = 3)
    private String poReference;

    @DataField(pos = 4)
    private String dateOrdered;

    @DataField(pos = 5)
    private String datePromised;

    @DataField(pos = 6)
    private String bpartnerIdentifier;

    @DataField(pos = 7)
    private String externalLineId;

    @DataField(pos = 8)
    private String productIdentifier;

    @DataField(pos = 9)
    private String qty;

    @DataField(pos = 10)
    private String price;

    public BigDecimal getQty() 
    {
        return CamelProcessorUtil.parseGermanNumberString(qty);
    }

    public BigDecimal getPrice() 
    {
        return CamelProcessorUtil.parseGermanNumberString(price);
    }

    
}
