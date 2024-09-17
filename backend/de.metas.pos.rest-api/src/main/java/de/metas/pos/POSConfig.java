package de.metas.pos;

import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyId;
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

	@NonNull Currency currency;
	@NonNull CurrencyPrecision pricePrecision;

	public static POSConfigBuilder builderFrom(final POSConfigRaw rawConfig)
	{
		return builder()
				.priceListId(rawConfig.getPriceListId())
				.warehouseId(rawConfig.getWarehouseId())
				.salesRepId(rawConfig.getSalesRepId())
				.salesOrderDocTypeId(rawConfig.getSalesOrderDocTypeId());
	}

	public CurrencyId getCurrencyId() {return currency.getId();}

	public String getCurrencySymbol(final String adLanguage) {return currency.getSymbol().translate(adLanguage);}

	public CurrencyPrecision getCurrencyPrecision() {return currency.getPrecision();}

}
