package de.metas.pos;

import com.google.common.collect.ImmutableSet;
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
import de.metas.workplace.WorkplaceId;
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
	@NonNull private final String name;

	@NonNull private final ImmutableSet<POSPaymentMethod> availablePaymentMethods;
	@NonNull private final BankAccountId cashbookId;
	@Nullable private final POSTerminalPaymentProcessorConfig paymentProcessorConfig;

	@NonNull private final PricingSystemAndListId pricingSystemAndListId;
	@NonNull private final CurrencyPrecision pricePrecision;
	private final boolean isTaxIncluded;

	@NonNull private final POSShipFrom shipFrom;
	@Nullable private final WorkplaceId workplaceId;

	@NonNull private final BPartnerLocationAndCaptureId walkInCustomerShipToLocationId;

	@NonNull private final DocTypeId salesOrderDocTypeId;

	@NonNull private final Currency currency;

	@Nullable private final POSCashJournalId cashJournalId;
	@NonNull private final Money cashLastBalance;

	@Builder(toBuilder = true)
	private POSTerminal(
			@NonNull final POSTerminalId id,
			@NonNull final String name,
			@NonNull final BankAccountId cashbookId,
			@Nullable final POSTerminalPaymentProcessorConfig paymentProcessorConfig,
			@NonNull final PricingSystemAndListId pricingSystemAndListId,
			@NonNull final CurrencyPrecision pricePrecision,
			final boolean isTaxIncluded,
			@NonNull final POSShipFrom shipFrom,
			@Nullable final WorkplaceId workplaceId,
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
		this.name = name;
		this.cashbookId = cashbookId;
		this.paymentProcessorConfig = paymentProcessorConfig;
		this.pricingSystemAndListId = pricingSystemAndListId;
		this.pricePrecision = pricePrecision;
		this.isTaxIncluded = isTaxIncluded;
		this.shipFrom = shipFrom;
		this.workplaceId = workplaceId;
		this.walkInCustomerShipToLocationId = walkInCustomerShipToLocationId;
		this.salesOrderDocTypeId = salesOrderDocTypeId;
		this.currency = currency;
		this.cashJournalId = cashJournalId;
		this.cashLastBalance = cashLastBalance != null ? cashLastBalance : Money.zero(currency.getId());

		final ImmutableSet.Builder<POSPaymentMethod> availablePaymentMethods = ImmutableSet.builder();
		availablePaymentMethods.add(POSPaymentMethod.CASH);
		if (paymentProcessorConfig != null)
		{
			availablePaymentMethods.add(POSPaymentMethod.CARD);
		}
		this.availablePaymentMethods = availablePaymentMethods.build();
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

	public POSTerminal openingCashJournal(@NonNull final POSCashJournalId cashJournalId)
	{
		if (this.cashJournalId != null)
		{
			throw new AdempiereException("Cash journal already open");
		}

		return toBuilder()
				.cashJournalId(cashJournalId)
				.build();
	}

	public POSTerminal closingCashJournal(@NonNull final Money cashEndingBalance)
	{
		cashEndingBalance.assertCurrencyId(currency.getId());

		return toBuilder()
				.cashJournalId(null)
				.cashLastBalance(cashEndingBalance)
				.build();
	}
}
