package de.metas.order.impl;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.i18n.BooleanWithReason;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Verifies that {@link OrderLinePriceCalculator#updateOrderLine()} writes the order <em>header</em>
 * currency to the order line, not the pricing-result currency.
 *
 * <p>Before the fix, {@code setC_Currency_ID} was called with {@code pricingResult.getCurrencyId()}
 * (EUR in this test), overwriting the header currency (USD).  After the fix the header value must win.
 */
class OrderLinePriceCurrencySyncTest
{
	private IPricingBL pricingBL;
	private IOrderLineBL orderLineBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Mock every Service the calculator calls eagerly on construction
		pricingBL = mock(IPricingBL.class);
		orderLineBL = mock(IOrderLineBL.class);
		final IOrderBL orderBL = mock(IOrderBL.class);
		final IBPartnerBL bpartnerBL = mock(IBPartnerBL.class);
		final IPriceListDAO priceListDAO = mock(IPriceListDAO.class);
		final IPricingConditionsRepository pricingConditionsRepo = mock(IPricingConditionsRepository.class);

		Services.registerService(IPricingBL.class, pricingBL);
		Services.registerService(IOrderBL.class, orderBL);
		Services.registerService(IBPartnerBL.class, bpartnerBL);
		Services.registerService(IPriceListDAO.class, priceListDAO);
		Services.registerService(IPricingConditionsRepository.class, pricingConditionsRepo);
		Services.registerService(IOrderLineBL.class, orderLineBL);
	}

	@Test
	void applyPricingResult_usesOrderHeaderCurrency()
	{
		// --- currencies ---
		final CurrencyId usdCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.USD);
		final CurrencyId eurCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		// --- order header with USD ---
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_Currency_ID(usdCurrencyId.getRepoId());
		order.setC_BPartner_ID(1); // non-zero: required by BPartnerId.ofRepoId in createPricingContext
		order.setDateOrdered(Timestamp.from(Instant.parse("2024-01-01T00:00:00Z")));
		InterfaceWrapperHelper.save(order);

		// --- order line linked to the order ---
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Product_ID(1);
		orderLine.setDateOrdered(Timestamp.from(Instant.parse("2024-01-01T00:00:00Z")));
		InterfaceWrapperHelper.save(orderLine);

		// --- stub the pricing context returned by IPricingBL ---
		final IEditablePricingContext pricingCtx = mock(IEditablePricingContext.class);
		when(pricingCtx.getSoTrx()).thenReturn(SOTrx.PURCHASE);
		when(pricingCtx.isConvertPriceToContextUOM()).thenReturn(false);
		// fluent setters return self
		when(pricingCtx.setReferencedObject(any())).thenReturn(pricingCtx);
		when(pricingCtx.setConvertPriceToContextUOM(anyBoolean())).thenReturn(pricingCtx);
		when(pricingCtx.setPricingSystemId(any())).thenReturn(pricingCtx);
		when(pricingCtx.setPriceListId(any())).thenReturn(pricingCtx);
		when(pricingCtx.setPriceListVersionId(any())).thenReturn(pricingCtx);
		when(pricingCtx.setCountryId(any())).thenReturn(pricingCtx);
		when(pricingCtx.setPriceDate(any())).thenReturn(pricingCtx);
		when(pricingCtx.setDisallowDiscount(anyBoolean())).thenReturn(pricingCtx);
		when(pricingCtx.setForcePricingConditionsBreak(any())).thenReturn(pricingCtx);

		when(pricingBL.createInitialContext(any(), any(), any(), any(), any()))
				.thenReturn(pricingCtx);

		// --- stub IPricingResult: currency = EUR (different from order header USD) ---
		final IPricingResult pricingResult = mock(IPricingResult.class);
		when(pricingResult.isCalculated()).thenReturn(true);
		when(pricingResult.getCurrencyId()).thenReturn(eurCurrencyId);
		when(pricingResult.getInvoicableQtyBasedOn()).thenReturn(InvoicableQtyBasedOn.NominalWeight);
		when(pricingResult.getPriceList()).thenReturn(BigDecimal.TEN);
		when(pricingResult.getPriceStd()).thenReturn(BigDecimal.TEN);
		when(pricingResult.getPriceLimit()).thenReturn(BigDecimal.ZERO);
		when(pricingResult.getDiscount()).thenReturn(Percent.ZERO);
		when(pricingResult.getPrecision()).thenReturn(CurrencyPrecision.TWO);
		when(pricingResult.getEnforcePriceLimit()).thenReturn(BooleanWithReason.falseBecause("test"));
		when(pricingResult.getPricingConditions()).thenReturn(null);
		when(pricingResult.isPriceEditable()).thenReturn(true);
		when(pricingResult.isDiscountEditable()).thenReturn(true);
		when(pricingResult.isCampaignPrice()).thenReturn(false);
		when(pricingResult.getPriceListVersionId()).thenReturn(null);
		when(pricingResult.getPriceUomId()).thenReturn(null);

		when(pricingBL.calculatePrice(any())).thenReturn(pricingResult);

		// --- stub IOrderLineBL ---
		final org.compiere.model.I_C_UOM uom = InterfaceWrapperHelper.newInstance(org.compiere.model.I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
		final Quantity qty = Quantity.of(BigDecimal.ONE, uom);
		when(orderLineBL.convertQtyEnteredToPriceUOM(any())).thenReturn(qty);
		// updateLineNetAmtFromQtyEntered is void — no stubbing needed

		// --- build and invoke the calculator ---
		final OrderLinePriceUpdateRequest request = OrderLinePriceUpdateRequest.builder()
				.orderLine(orderLine)
				.resultUOM(OrderLinePriceUpdateRequest.ResultUOM.PRICE_UOM)
				.updateLineNetAmt(false)
				.applyPriceLimitRestrictions(false)
				.build();

		OrderLinePriceCalculatorTest.builder()
				.request(request)
				.orderLineBL(orderLineBL)
				.build()
				.updateOrderLine();

		// Assert directly on the mutated order line object (no reload needed — POJO test env copies on save,
		// so mutations after the initial save are visible only on the in-memory wrapper, not on a reload).
		assertThat(orderLine.getC_Currency_ID())
				.as("order line currency must come from the order header (USD), not the pricing result (EUR)")
				.isEqualTo(usdCurrencyId.getRepoId());
	}
}
