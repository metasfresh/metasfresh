package de.metas.pos;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class POSConfig
{
	@NonNull BankAccountId cashbookId;
	
	@NonNull PricingSystemAndListId pricingSystemAndListId;
	@NonNull CurrencyPrecision pricePrecision;
	boolean isTaxIncluded;

	@NonNull POSShipFrom shipFrom;

	@NonNull BPartnerLocationAndCaptureId walkInCustomerShipToLocationId;

	@Nullable UserId salesRepId;
	@NonNull DocTypeId salesOrderDocTypeId;

	@NonNull Currency currency;

	public PriceListId getPriceListId() {return pricingSystemAndListId.getPriceListId();}

	public CurrencyId getCurrencyId() {return currency.getId();}

	public String getCurrencySymbol(final String adLanguage) {return currency.getSymbol().translate(adLanguage);}

	public CurrencyPrecision getCurrencyPrecision() {return currency.getPrecision();}

}
