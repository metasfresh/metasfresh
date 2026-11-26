package de.metas.frontend_testing.masterdata.hu;

import com.google.common.collect.ImmutableList;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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

	/**
	 * Included TUs' QR codes, populated additively when this HU was created as an LU with included
	 * TUs (i.e. the request's packingInstructions had an {@code luPIItem}). Empty otherwise.
	 */
	@NonNull @Singular("tu") ImmutableList<Tu> tus;

	@Value
	@Builder
	@Jacksonized
	public static class Tu
	{
		@NonNull String huId;
		@Nullable String qrCode;
	}
}
