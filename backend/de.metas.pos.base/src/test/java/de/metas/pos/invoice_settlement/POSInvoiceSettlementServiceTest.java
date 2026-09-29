package de.metas.pos.invoice_settlement;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pos.POSCashJournalRepository;
import de.metas.pos.POSCashJournalService;
import de.metas.pos.POSShipFrom;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalRepository;
import de.metas.pos.POSTerminalService;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.ThrowableAssert;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Guards of {@link POSInvoiceSettlementService} that reject a settle request before any payment is created and that
 * the cucumber/Playwright end-to-end flow cannot faithfully reach: an invoice from a foreign org (a client-supplied
 * invoiceId the till's org-scoped search would never return) and an invoice in a foreign currency (the till's own
 * masterdata is single-currency). Exercised directly on the extracted guard methods rather than through
 * {@code settleInCash}, which needs the DB-backed invoice/allocation lookups these guards run before. The
 * {@code NoLongerOpen} guard and the happy path are covered end-to-end by the {@code posInvoiceSettlement} cucumber
 * feature.
 */
class POSInvoiceSettlementServiceTest
{
	private static final AdMessageKey MSG_CurrencyMismatch = AdMessageKey.of("de.metas.pos.InvoiceSettlement.CurrencyMismatch");
	private static final AdMessageKey MSG_WrongOrg = AdMessageKey.of("de.metas.pos.InvoiceSettlement.WrongOrg");

	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(102);
	private static final CurrencyId OTHER_CURRENCY_ID = CurrencyId.ofRepoId(103);
	private static final OrgId TERMINAL_ORG_ID = OrgId.ofRepoId(1);
	private static final OrgId OTHER_ORG_ID = OrgId.ofRepoId(2);
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(1);

	private POSInvoiceSettlementService service;
	private POSTerminal terminal;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		terminal = newTerminal();
		final FixedPOSTerminalService posTerminalService = new FixedPOSTerminalService();
		posTerminalService.terminal = terminal;

		service = new POSInvoiceSettlementService(
				posTerminalService,
				new POSCashJournalService(new POSCashJournalRepository()));
	}

	@Test
	void invoiceFromForeignOrg_isRejected()
	{
		final I_C_Invoice invoice = newInvoice(OTHER_ORG_ID);
		assertThrowsWithKey(() -> service.assertInvoiceBelongsToTerminalOrg(invoice, terminal), MSG_WrongOrg);
	}

	@Test
	void invoiceFromTerminalOrg_isAccepted()
	{
		final I_C_Invoice invoice = newInvoice(TERMINAL_ORG_ID);
		assertThatCode(() -> service.assertInvoiceBelongsToTerminalOrg(invoice, terminal)).doesNotThrowAnyException();
	}

	@Test
	void invoiceInForeignCurrency_isRejected()
	{
		final I_C_Invoice invoice = newInvoice(TERMINAL_ORG_ID);
		assertThrowsWithKey(
				() -> service.assertInvoiceCurrencyMatchesTerminal(invoice, OTHER_CURRENCY_ID, terminal),
				MSG_CurrencyMismatch);
	}

	@Test
	void invoiceInTerminalCurrency_isAccepted()
	{
		final I_C_Invoice invoice = newInvoice(TERMINAL_ORG_ID);
		assertThatCode(() -> service.assertInvoiceCurrencyMatchesTerminal(invoice, CURRENCY_ID, terminal)).doesNotThrowAnyException();
	}

	private static I_C_Invoice newInvoice(@NonNull final OrgId orgId)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(orgId.getRepoId());
		return invoice;
	}

	private static POSTerminal newTerminal()
	{
		return POSTerminal.builder()
				.id(TERMINAL_ID)
				.name("till")
				.cashbookId(BankAccountId.ofRepoId(1))
				.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(1, 1))
				.pricePrecision(CurrencyPrecision.TWO)
				.isTaxIncluded(true)
				.shipFrom(POSShipFrom.builder()
						.warehouseId(WarehouseId.ofRepoId(1))
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(Env.getClientId(), TERMINAL_ORG_ID))
						.countryId(CountryId.ofRepoId(101))
						.build())
				.walkInCustomerShipToLocationId(BPartnerLocationAndCaptureId.ofRepoId(1, 1))
				.salesOrderDocTypeId(DocTypeId.ofRepoId(1))
				.currency(Currency.builder()
						.id(CURRENCY_ID)
						.currencyCode(CurrencyCode.EUR)
						.symbol(TranslatableStrings.anyLanguage("€"))
						.description("Euro")
						.precision(CurrencyPrecision.TWO)
						.costingPrecision(CurrencyPrecision.TWO)
						.build())
				.cashJournalId(null)
				.build();
	}

	/**
	 * {@link AdempiereException#getErrorCode()} resolves to the {@code AD_Message.ErrorCode} column when the message
	 * has one, falling back to the {@link AdMessageKey} itself otherwise (mirrors the exact resolution
	 * {@code AdempiereException}'s own constructor does) — so the expected value here is resolved the same way rather
	 * than assumed to always be the key, which would only hold before the AD_Message row exists.
	 */
	private static void assertThrowsWithKey(@NonNull final ThrowableAssert.ThrowingCallable code, @NonNull final AdMessageKey expectedKey)
	{
		final String expectedErrorCode = Optional.ofNullable(Services.get(IMsgBL.class).getErrorCode(expectedKey))
				.orElseGet(expectedKey::toAD_Message);

		assertThatThrownBy(code)
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.getErrorCode())
						.as("AD_Message of the exception")
						.isEqualTo(expectedErrorCode));
	}

	/**
	 * Serves one in-memory terminal, so the guards can be exercised without the terminal's full master data.
	 */
	private static class FixedPOSTerminalService extends POSTerminalService
	{
		private POSTerminal terminal;

		FixedPOSTerminalService()
		{
			super(new CurrencyRepository(), new POSTerminalRepository());
		}

		@Override
		@NonNull
		public POSTerminal getPOSTerminalById(final POSTerminalId posTerminalId)
		{
			assertThat(terminal).as("terminal set up by the test").isNotNull();
			return terminal;
		}
	}
}
