package de.metas.pos.rest_api.json;

import de.metas.document.DocTypeId;
import de.metas.pos.POSConfig;
import de.metas.pricing.PriceListId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonPOSConfig
{
	@NonNull PriceListId priceListId;
	@NonNull WarehouseId warehouseId;
	@Nullable UserId salesRepId;
	@NonNull DocTypeId salesOrderDocTypeId;
	@NonNull String currencySymbol;
	int pricePrecision;
	int currencyPrecision;

	@NonNull
	public static JsonPOSConfig from(@NonNull final POSConfig config, @NonNull final String adLanguage)
	{
		return JsonPOSConfig.builder()
				.priceListId(config.getPriceListId())
				.warehouseId(config.getWarehouseId())
				.salesRepId(config.getSalesRepId())
				.salesOrderDocTypeId(config.getSalesOrderDocTypeId())
				.currencySymbol(config.getCurrencySymbol(adLanguage))
				.pricePrecision(config.getPricePrecision().toInt())
				.currencyPrecision(config.getCurrencyPrecision().toInt())
				.build();
	}
}
