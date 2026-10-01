package de.metas.pos.withdrawal;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.costing.ChargeId;
import de.metas.costing.impl.ChargeRepository;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pos.POSCashJournalId;
import de.metas.pos.POSCashJournalRepository;
import de.metas.pos.POSCashJournalService;
import de.metas.pos.POSShipFrom;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalRepository;
import de.metas.pos.POSTerminalService;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.ThrowableAssert;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_ChargeType;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Guards of {@link POSCashWithdrawalService#withdraw}: each rejects the withdrawal with its AD_Message before any
 * payment or journal line is created.
 */
class POSCashWithdrawalServiceTest
{
	private static final AdMessageKey MSG_CashJournalNotOpen = AdMessageKey.of("de.metas.pos.CashJournalNotOpen");
	private static final AdMessageKey MSG_NoCategories = AdMessageKey.of("de.metas.pos.CashWithdrawal.NoCategories");
	private static final AdMessageKey MSG_AmountMustBePositive = AdMessageKey.of("de.metas.pos.CashWithdrawal.AmountMustBePositive");

	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(102);
	private static final UserId CASHIER_ID = UserId.ofRepoId(100);
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(1);

	private ISysConfigBL sysConfigBL;
	private FixedPOSTerminalService posTerminalService;
	private POSCashWithdrawalService service;
	private OrgId orgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		sysConfigBL = Services.get(ISysConfigBL.class);

		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setValue("org");
		org.setName("org");
		saveRecord(org);
		orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		posTerminalService = new FixedPOSTerminalService();
		service = new POSCashWithdrawalService(
				posTerminalService,
				new POSCashJournalService(new POSCashJournalRepository()),
				new ChargeRepository());
	}

	@Test
	void journalNotOpen()
	{
		final ChargeId chargeId = configureCategory();
		posTerminalService.terminal = newTerminal(null);

		assertThrowsWithKey(() -> service.withdraw(newRequest(chargeId, Money.of(12, CURRENCY_ID))), MSG_CashJournalNotOpen);
	}

	@Test
	void amountZero()
	{
		final ChargeId chargeId = configureCategory();
		posTerminalService.terminal = newTerminal(POSCashJournalId.ofRepoId(1));

		assertThrowsWithKey(() -> service.withdraw(newRequest(chargeId, Money.zero(CURRENCY_ID))), MSG_AmountMustBePositive);
	}

	@Test
	void amountNegative()
	{
		final ChargeId chargeId = configureCategory();
		posTerminalService.terminal = newTerminal(POSCashJournalId.ofRepoId(1));

		assertThrowsWithKey(() -> service.withdraw(newRequest(chargeId, Money.of(-12, CURRENCY_ID))), MSG_AmountMustBePositive);
	}

	@Test
	void noCategories_sysconfigNotSet()
	{
		final ChargeId chargeId = createCharge(createChargeType(), true);
		posTerminalService.terminal = newTerminal(POSCashJournalId.ofRepoId(1));

		assertThat(service.getCategories(TERMINAL_ID)).isEmpty();
		assertThrowsWithKey(() -> service.withdraw(newRequest(chargeId, Money.of(12, CURRENCY_ID))), MSG_NoCategories);
	}

	@Test
	void noCategories_noActiveCharges()
	{
		final I_C_ChargeType chargeType = createChargeType();
		final ChargeId inactiveChargeId = createCharge(chargeType, false);
		setWithdrawalChargeType(chargeType);
		posTerminalService.terminal = newTerminal(POSCashJournalId.ofRepoId(1));

		assertThat(service.getCategories(TERMINAL_ID)).isEmpty();
		assertThrowsWithKey(() -> service.withdraw(newRequest(inactiveChargeId, Money.of(12, CURRENCY_ID))), MSG_NoCategories);
	}

	@Test
	void chargeOutsideCategories()
	{
		configureCategory();
		final ChargeId otherChargeId = createCharge(createChargeType(), true);
		posTerminalService.terminal = newTerminal(POSCashJournalId.ofRepoId(1));

		assertThatThrownBy(() -> service.withdraw(newRequest(otherChargeId, Money.of(12, CURRENCY_ID))))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("not a cash withdrawal category");
	}

	@Test
	void getCategories_offersActiveChargesOfConfiguredType()
	{
		final ChargeId chargeId = configureCategory();
		posTerminalService.terminal = newTerminal(null);

		assertThat(service.getCategories(TERMINAL_ID))
				.extracting(POSCashWithdrawalCategory::getChargeId)
				.containsExactly(chargeId);
	}

	private ChargeId configureCategory()
	{
		final I_C_ChargeType chargeType = createChargeType();
		final ChargeId chargeId = createCharge(chargeType, true);
		setWithdrawalChargeType(chargeType);
		return chargeId;
	}

	private void setWithdrawalChargeType(@NonNull final I_C_ChargeType chargeType)
	{
		sysConfigBL.setValue(POSCashWithdrawalService.SYSCONFIG_ChargeTypeId, chargeType.getC_ChargeType_ID(), ClientId.SYSTEM, OrgId.ANY);
	}

	private static I_C_ChargeType createChargeType()
	{
		final I_C_ChargeType chargeType = newInstance(I_C_ChargeType.class);
		chargeType.setValue("withdrawal");
		chargeType.setName("withdrawal");
		saveRecord(chargeType);
		return chargeType;
	}

	private ChargeId createCharge(@NonNull final I_C_ChargeType chargeType, final boolean isActive)
	{
		final I_C_Charge charge = newInstance(I_C_Charge.class);
		charge.setAD_Org_ID(orgId.getRepoId());
		charge.setName("charge" + chargeType.getC_ChargeType_ID());
		charge.setC_ChargeType_ID(chargeType.getC_ChargeType_ID());
		charge.setIsActive(isActive);
		saveRecord(charge);
		return ChargeId.ofRepoId(charge.getC_Charge_ID());
	}

	private static POSCashWithdrawalRequest newRequest(@NonNull final ChargeId chargeId, @NonNull final Money amount)
	{
		return POSCashWithdrawalRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.cashierId(CASHIER_ID)
				.chargeId(chargeId)
				.amount(amount)
				.build();
	}

	private POSTerminal newTerminal(@Nullable final POSCashJournalId cashJournalId)
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
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(Env.getClientId(), orgId))
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
				.cashJournalId(cashJournalId)
				.build();
	}

	private static void assertThrowsWithKey(@NonNull final ThrowableAssert.ThrowingCallable code, @NonNull final AdMessageKey expectedKey)
	{
		assertThatThrownBy(code)
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.getErrorCode())
						.as("AD_Message of the exception")
						.isEqualTo(expectedKey.toAD_Message()));
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
