package de.metas.order.compensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.AdMessageId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.money.Money;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLineReasonForWithoutCharge;
import de.metas.order.compensationGroup.GroupRepository.RetrieveOrCreateGroupRequest;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Tests for {@link OrderGroupRepository#createRegularLineFromTemplate},
 * specifically the IsWithoutCharge / Reason auto-flagging logic (F00127.1).
 */
public class OrderGroupRepositoryTest
{
	private static final String REASON_TEXT = OrderLineReasonForWithoutCharge.BundleComponent.getCode();

	private UomId uomId;
	private ProductId productId;
	private I_C_Order order;
	private OrderGroupRepository repo;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// UOM
		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
		uomId = UomId.ofRepoId(uomRecord.getC_UOM_ID());

		// Product
		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		// Order
		order = newInstance(I_C_Order.class);
		saveRecord(order);

		// Register stub IOrderLineBL (concrete class, not Mockito mock, to avoid proxy-cast issues).
		Services.registerService(IOrderLineBL.class, new StubOrderLineBL(order));

		// Build repo (no advisors needed for this test).
		repo = new OrderGroupRepository(
				Mockito.mock(GroupCompensationLineCreateRequestFactory.class),
				Optional.empty());
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 1 — template-line flag=Y → order-line IsWithoutCharge='Y' + Reason set
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void componentLineAutoFlaggedWhenTemplateLineFlagSet()
	{
		final I_C_OrderLine result = repo.createRegularLineFromTemplate(
				buildTemplateLine(true), order, minimalRequest());

		assertThat(result.isWithoutCharge()).isTrue();
		assertThat(result.getReason()).isEqualTo(REASON_TEXT);
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 2 — template-line flag=N → order-line IsWithoutCharge stays false, Reason stays null
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void componentLineNotFlaggedWhenTemplateLineFlagClear()
	{
		final I_C_OrderLine result = repo.createRegularLineFromTemplate(
				buildTemplateLine(false), order, minimalRequest());

		assertThat(result.isWithoutCharge()).isFalse();
		assertThat(result.getReason()).isNull();
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 3 — two template lines (one Y, one N) → flags are independent
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void mixedTemplateLinesProduceMixedFlags()
	{
		final I_C_OrderLine lineWithFlag = repo.createRegularLineFromTemplate(
				buildTemplateLine(true), order, minimalRequest());
		final I_C_OrderLine lineWithoutFlag = repo.createRegularLineFromTemplate(
				buildTemplateLine(false), order, minimalRequest());

		assertThat(lineWithFlag.isWithoutCharge()).isTrue();
		assertThat(lineWithFlag.getReason()).isEqualTo(REASON_TEXT);

		assertThat(lineWithoutFlag.isWithoutCharge()).isFalse();
		assertThat(lineWithoutFlag.getReason()).isNull();
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 4 — compensation order line (IsGroupCompensationLine=true) defaults to IsWithoutCharge=false.
	// The compensation code path (updateOrderLineFromCompensationLine) never touches IsWithoutCharge.
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void compensationLineNeverAutoFlagged()
	{
		final I_C_OrderLine compensationLine = newInstance(I_C_OrderLine.class);
		compensationLine.setIsGroupCompensationLine(true);

		assertThat(compensationLine.isWithoutCharge()).isFalse();
		assertThat(compensationLine.getReason()).isNull();
	}

	// ── helpers ─────────────────────────────────────────────────────────────────────────────────

	private GroupTemplateRegularLine buildTemplateLine(final boolean isWithoutCharge)
	{
		final I_C_UOM uom = org.adempiere.model.InterfaceWrapperHelper.load(uomId.getRepoId(), I_C_UOM.class);
		return GroupTemplateRegularLine.builder()
				.id(GroupTemplateRegularLineId.ofRepoId(1))
				.productId(productId)
				.qty(Quantity.of(BigDecimal.ONE, uom))
				.isWithoutCharge(isWithoutCharge)
				.build();
	}

	private RetrieveOrCreateGroupRequest minimalRequest()
	{
		// newGroupTemplate is @NonNull in the request builder.
		// createRegularLineFromTemplate only reads qtyMultiplier + contractConditionsId from the request.
		final GroupTemplate minimalTemplate = GroupTemplate.builder()
				.name("test-template")
				.regularLinesToAdd(Collections.emptyList())
				.build();
		return RetrieveOrCreateGroupRequest.builder()
				.newGroupTemplate(minimalTemplate)
				.build();
	}

	// ── stubs ────────────────────────────────────────────────────────────────────────────────────

	/**
	 * Minimal IOrderLineBL stub: createOrderLine returns a fresh POJO; save is a no-op.
	 * Using a concrete class (not a Mockito proxy) avoids class-loader/proxy issues with
	 * the Services/TestingClassInstanceProvider infrastructure.
	 */
	private static class StubOrderLineBL implements IOrderLineBL
	{
		private final I_C_Order order;

		StubOrderLineBL(final I_C_Order order) { this.order = order; }

		@Override
		public de.metas.interfaces.I_C_OrderLine createOrderLine(final org.compiere.model.I_C_Order targetOrder)
		{
			final de.metas.interfaces.I_C_OrderLine ol = newInstance(de.metas.interfaces.I_C_OrderLine.class);
			ol.setC_Order_ID(order.getC_Order_ID());
			return ol;
		}

		@Override
		public <T extends de.metas.interfaces.I_C_OrderLine> T createOrderLine(
				final org.compiere.model.I_C_Order targetOrder,
				final Class<T> orderLineClass)
		{
			@SuppressWarnings("unchecked")
			final T result = (T) createOrderLine(targetOrder);
			return result;
		}

		@Override public void save(final org.compiere.model.I_C_OrderLine orderLine) { /* no-op */ }

		// ─── all remaining abstract methods — throw to surface accidental calls ────────────────
		@Override public List<de.metas.interfaces.I_C_OrderLine> getByOrderIds(Set<de.metas.order.OrderId> orderIds) { throw new UnsupportedOperationException(); }
		@Override public de.metas.interfaces.I_C_OrderLine getOrderLineById(OrderLineId orderLineId) { throw new UnsupportedOperationException(); }
		@Override public de.metas.interfaces.I_C_OrderLine getOrderLineById(OrderAndLineId orderLineId) { throw new UnsupportedOperationException(); }
		@Override public Quantity getQtyEntered(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Quantity getQtyOrdered(OrderAndLineId orderAndLineId) { throw new UnsupportedOperationException(); }
		@Override public Quantity getQtyOrdered(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Quantity getQtyToDeliver(OrderAndLineId orderAndLineId) { throw new UnsupportedOperationException(); }
		@Override public Quantity getQtyDelivered(OrderAndLineId orderAndLineId) { throw new UnsupportedOperationException(); }
		@Override public void setOrder(org.compiere.model.I_C_OrderLine ol, org.compiere.model.I_C_Order order) { throw new UnsupportedOperationException(); }
		@Override public void setTaxAmtInfo(de.metas.interfaces.I_C_OrderLine ol) { throw new UnsupportedOperationException(); }
		@Override public void setShipper(de.metas.interfaces.I_C_OrderLine ol) { throw new UnsupportedOperationException(); }
		@Override public void updatePriceActual(de.metas.interfaces.I_C_OrderLine orderLine, CurrencyPrecision precision) { throw new UnsupportedOperationException(); }
		@Override public BigDecimal calculatePriceEnteredFromPriceActualAndDiscount(BigDecimal priceActual, BigDecimal discount, int precision) { throw new UnsupportedOperationException(); }
		@Override public TaxCategoryId getTaxCategoryId(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updatePrices(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updatePrices(OrderLinePriceUpdateRequest request) { throw new UnsupportedOperationException(); }
		@Override public IPricingResult computePrices(OrderLinePriceUpdateRequest request) { throw new UnsupportedOperationException(); }
		@Override public PriceLimitRuleResult computePriceLimit(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void setProductId(org.compiere.model.I_C_OrderLine orderLine, ProductId productId, boolean setUomFromProduct) { throw new UnsupportedOperationException(); }
		@Override public I_M_PriceList_Version getPriceListVersion(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updateLineNetAmtFromQtyEntered(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updateLineNetAmtFromQty(Quantity qty, org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updateQtyReserved(de.metas.interfaces.I_C_OrderLine ol) { throw new UnsupportedOperationException(); }
		@Override public Quantity convertQtyEnteredToPriceUOM(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Quantity convertQtyToPriceUOM(Quantity qty, org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Quantity convertQtyToUOM(Quantity qty, org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Quantity convertQtyEnteredToStockUOM(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public boolean isTaxIncluded(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public CurrencyPrecision getPricePrecision(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public CurrencyPrecision getAmountPrecision(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public CurrencyPrecision getTaxPrecision(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void copyOrderLineCounter(org.compiere.model.I_C_OrderLine line, org.compiere.model.I_C_OrderLine fromLine) { throw new UnsupportedOperationException(); }
		@Override public boolean isAllowedCounterLineCopy(org.compiere.model.I_C_OrderLine fromLine) { throw new UnsupportedOperationException(); }
		@Override public ProductPrice getCostPrice(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public ProductPrice getPriceActual(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public PaymentTermId getPaymentTermId(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Map<OrderAndLineId, Quantity> getQtyToDeliver(Collection<OrderAndLineId> orderAndLineIds) { throw new UnsupportedOperationException(); }
		@Override public void updateProductDescriptionFromProductBOMIfConfigured(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updateProductDocumentNote(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public BigDecimal computeQtyNetPriceFromOrderLine(org.compiere.model.I_C_OrderLine orderLine, Quantity qty) { throw new UnsupportedOperationException(); }
		@Override public CurrencyPrecision extractPricePrecision(org.compiere.model.I_C_OrderLine olRecord) { throw new UnsupportedOperationException(); }
		@Override public void setBPLocation(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public boolean isCatchWeight(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Optional<BPartnerId> getBPartnerId(OrderLineId orderLineId) { throw new UnsupportedOperationException(); }
		@Override public Optional<BPartnerId> getBPartnerId(OrderAndLineId orderLineId) { throw new UnsupportedOperationException(); }
		@Override public void setTax(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void setGrossWeightInKg(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Money getLineGrossAmt(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
	}

}
