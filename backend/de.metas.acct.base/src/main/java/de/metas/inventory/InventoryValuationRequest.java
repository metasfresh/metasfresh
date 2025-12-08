package de.metas.inventory;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
public class InventoryValuationRequest
{
	@NonNull LocalDate dateAcct;
	@Nullable ProductId productId;
	@Nullable WarehouseId warehouseId;
	@Nullable String adLanguage;
}
