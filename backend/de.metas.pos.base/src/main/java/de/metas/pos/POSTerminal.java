package de.metas.pos;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemAndListId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Getter
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
public class POSTerminal
{
	@NonNull private final POSTerminalId id;

	@NonNull private final BankAccountId cashbookId;
	@Nullable private final POSTerminalPaymentProcessorConfig paymentProcessorConfig;

	@NonNull private final PricingSystemAndListId pricingSystemAndListId;
	@NonNull private final CurrencyPrecision pricePrecision;
	private final boolean isTaxIncluded;

	@NonNull private final POSShipFrom shipFrom;

	@NonNull private final BPartnerLocationAndCaptureId walkInCustomerShipToLocationId;

	@NonNull private final DocTypeId salesOrderDocTypeId;

	@NonNull private final Currency currency;

	@Nullable POSCashJournalId cashJournalId;
	@NonNull Money cashLastBalance;

	@Builder
	private POSTerminal(
			@NonNull final POSTerminalId id,
			@NonNull final BankAccountId cashbookId,
			@Nullable final POSTerminalPaymentProcessorConfig paymentProcessorConfig,
			@NonNull final PricingSystemAndListId pricingSystemAndListId,
			@NonNull final CurrencyPrecision pricePrecision,
			final boolean isTaxIncluded,
			@NonNull final POSShipFrom shipFrom,
			@NonNull final BPartnerLocationAndCaptureId walkInCustomerShipToLocationId,
			@NonNull final DocTypeId salesOrderDocTypeId,
			@NonNull final Currency currency,
			@Nullable final POSCashJournalId cashJournalId,
			@Nullable final Money cashLastBalance)
	{
		if (cashLastBalance != null)
		{
			cashLastBalance.assertCurrencyId(currency.getId());
		}

		this.id = id;
		this.cashbookId = cashbookId;
		this.paymentProcessorConfig = paymentProcessorConfig;
		this.pricingSystemAndListId = pricingSystemAndListId;
		this.pricePrecision = pricePrecision;
		this.isTaxIncluded = isTaxIncluded;
		this.shipFrom = shipFrom;
		this.walkInCustomerShipToLocationId = walkInCustomerShipToLocationId;
		this.salesOrderDocTypeId = salesOrderDocTypeId;
		this.currency = currency;
		this.cashJournalId = cashJournalId;
		this.cashLastBalance = cashLastBalance != null ? cashLastBalance : Money.zero(currency.getId());
	}

	public POSTerminalPaymentProcessorConfig getPaymentProcessorConfigNotNull()
	{
		if (paymentProcessorConfig == null)
		{
			throw new AdempiereException("No payment processor configured");
		}
		return paymentProcessorConfig;
	}

	public OrgId getOrgId() {return getShipFrom().getOrgId();}

	public PriceListId getPriceListId() {return pricingSystemAndListId.getPriceListId();}

	public CurrencyId getCurrencyId() {return currency.getId();}

	public String getCurrencySymbol(final String adLanguage) {return currency.getSymbol().translate(adLanguage);}

	public CurrencyPrecision getCurrencyPrecision() {return currency.getPrecision();}

	public boolean isCashJournalOpen() {return cashJournalId != null;}

	@NonNull
	public POSCashJournalId getCashJournalIdNotNull()
	{
		if (cashJournalId == null)
		{
			throw new AdempiereException("No open journals found");
		}
		return cashJournalId;
	}

	public void openCashJournal(@NonNull final POSCashJournalId cashJournalId)
	{
		if (this.cashJournalId != null)
		{
			throw new AdempiereException("Cash journal already open");
		}

		this.cashJournalId = cashJournalId;
	}

	public void closeCashJournal(@NonNull final Money cashEndingBalance)
	{
		cashEndingBalance.assertCurrencyId(currency.getId());

		this.cashJournalId = null;
		this.cashLastBalance = cashEndingBalance;
	}
}
