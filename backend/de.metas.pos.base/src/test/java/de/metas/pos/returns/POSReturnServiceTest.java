package de.metas.pos.returns;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.handlingunits.inout.returns.ReturnsServiceFacade;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnInOutRecordFactory;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnsWithoutHUsProducer;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pos.POSShipFrom;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalService;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.ThrowableAssert;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Guards of {@link POSReturnService#createReturn}: each rejects the request with its AD_Message before the
 * return document (or any invoice candidate) is created.
 */
class POSReturnServiceTest
{
	private static final AdMessageKey MSG_NoLines = AdMessageKey.of("de.metas.pos.Return.NoLines");
	private static final AdMessageKey MSG_QtyMustBePositive = AdMessageKey.of("de.metas.pos.Return.QtyMustBePositive");

	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(102);
	private static final UserId CASHIER_ID = UserId.ofRepoId(100);
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(1);
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1);
	private static final UomId UOM_ID = UomId.ofRepoId(1);

	private POSReturnService service;
	private OrgId orgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		orgId = OrgId.ofRepoId(1);

		final FixedPOSTerminalService posTerminalService = new FixedPOSTerminalService();
		posTerminalService.terminal = newTerminal();

		final ReturnsServiceFacade returnsServiceFacade = new ReturnsServiceFacade(
				new CustomerReturnsWithoutHUsProducer(new CustomerReturnInOutRecordFactory()));

		service = new POSReturnService(posTerminalService, returnsServiceFacade, new POSReturnRepository());
	}

	@Test
	void noLines()
	{
		final POSReturnRequest request = POSReturnRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.cashierId(CASHIER_ID)
				.externalId(UUID.randomUUID())
				.lines(ImmutableList.of())
				.build();

		assertThrowsWithKey(() -> service.createReturn(request), MSG_NoLines);
	}

	@Test
	void qtyZero()
	{
		assertThrowsWithKey(() -> service.createReturn(newRequestWithQty(BigDecimal.ZERO)), MSG_QtyMustBePositive);
	}

	@Test
	void qtyNegative()
	{
		assertThrowsWithKey(() -> service.createReturn(newRequestWithQty(new BigDecimal("-0.3"))), MSG_QtyMustBePositive);
	}

	private static POSReturnRequest newRequestWithQty(@NonNull final BigDecimal qty)
	{
		return POSReturnRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.cashierId(CASHIER_ID)
				.externalId(UUID.randomUUID())
				.lines(ImmutableList.of(POSReturnLine.builder()
						.productId(PRODUCT_ID)
						.qty(Quantity.of(qty, mockUom()))
						.price(Money.of(BigDecimal.TEN, CURRENCY_ID))
						.priceUomId(UOM_ID)
						.build()))
				.build();
	}

	private static I_C_UOM mockUom()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setC_UOM_ID(UOM_ID.getRepoId());
		return uom;
	}

	private POSTerminal newTerminal()
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
				.cashJournalId(null)
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
			super(new CurrencyRepository());
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
