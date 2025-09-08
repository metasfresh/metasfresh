package de.metas.inventory;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class InventoryValue
{
	@NonNull String combination;
	@Nullable String description;
	@Nullable String activityName;
	@Nullable String warehouseName;
	@Nullable String productValue;
	@Nullable String productName;
	@Nullable BigDecimal qty;
	@Nullable String uomSymbol;

	@NonNull Amounts accounted;
	@NonNull Amounts costing;
	@NonNull BigDecimal inventoryValueAcctAmt;

	@Value
	@Builder
	public static class Amounts
	{
		@NonNull BigDecimal costPrice;
		@NonNull BigDecimal expectedAmt;
		/**
		 * errorAmt = inventoryValueAcctAmt - expectedAmt
		 */
		@NonNull BigDecimal errorAmt;
	}
}
