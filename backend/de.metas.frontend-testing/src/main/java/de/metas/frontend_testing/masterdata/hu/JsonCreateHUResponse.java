package de.metas.frontend_testing.masterdata.hu;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonCreateHUResponse
{
	@NonNull String huId;
	@Nullable String qrCode;
	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@Nullable String externalBarcode;
}
