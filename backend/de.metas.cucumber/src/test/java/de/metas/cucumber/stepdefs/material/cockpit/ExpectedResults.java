package de.metas.cucumber.stepdefs.material.cockpit;

import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
class ExpectedResults
{
	@NonNull ProductId productId;
	@NonNull AttributesKey storageAttributesKey;
	@NonNull Instant dateGeneral;
	@Nullable BigDecimal qtyDemandSumAtDate;
	@Nullable BigDecimal qtyDemandSalesOrderAtDate;
	@Nullable BigDecimal qtyStockCurrentAtDate;
	@Nullable BigDecimal qtyExpectedSurplusAtDate;
	@Nullable BigDecimal qtyInventoryCountAtDate;
	@Nullable BigDecimal qtySupplyPurchaseOrderAtDate;
	@Nullable BigDecimal qtySupplySumAtDate;
	@Nullable BigDecimal qtySupplyRequiredAtDate;
	@Nullable BigDecimal qtySupplyToScheduleAtDate;
	@Nullable BigDecimal mdCandidateQtyStockAtDate;
	@Nullable BigDecimal qtyStockChange;
	@Nullable BigDecimal qtyDemandPPOrderAtDate;
	@Nullable BigDecimal qtySupplyPPOrderAtDate;
	@Nullable BigDecimal qtyDemandDDOrderAtDate;
	@Nullable BigDecimal qtySupplyDDOrderAtDate;
	@Nullable WarehouseId warehouseId;
	@Nullable BigDecimal qtyOrderedPurchaseOrderAtDate;
	@Nullable BigDecimal qtyOrderedSalesOrderAtDate;

	@Nullable StepDefDataIdentifier identifier;
}
