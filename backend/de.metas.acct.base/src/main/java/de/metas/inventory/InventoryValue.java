package de.metas.inventory;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class InventoryValue
{
	String combination;
	String description;
	String activityName;
	String warehouseName;
	String productValue;
	String productName;
	BigDecimal qty;
	String uomSymbol;
	BigDecimal costPrice;
	BigDecimal totalAmt;
}
