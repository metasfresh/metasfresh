package de.metas.pos;

import de.metas.document.DocTypeId;
import de.metas.pricing.PriceListId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
public class POSConfig
{
	@NonNull PriceListId priceListId;
	@NonNull WarehouseId warehouseId;
	@Nullable UserId salesRepId;
	@NonNull DocTypeId salesOrderDocTypeId;
}
