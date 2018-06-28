package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeInstance;

import de.metas.bpartner.BPartnerId;
import de.metas.lang.Percent;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.payment.api.PaymentTermId;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowsLoader.PricingConditionsBreaksExtractor;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowsLoader.SourceDocumentLine;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ViewFactory(windowId = OrderLinePricingConditionsViewFactory.WINDOW_ID_STRING)
public class OrderLinePricingConditionsViewFactory extends PricingConditionsViewFactoryTemplate
{
	public static final String WINDOW_ID_STRING = "orderLinePricingConditions";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	public OrderLinePricingConditionsViewFactory()
	{
		super(WINDOW_ID);
	}

	@Override
	protected void onViewClosedByUser(final PricingConditionsView view)
	{
		view.updateSalesOrderLineIfPossible();
	}

	@Override
	protected PricingConditionsRowData createPricingConditionsRowData(final CreateViewRequest request)
	{
		final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

		final int orderLineId = ListUtils.singleElement(request.getFilterOnlyIds());
		Check.assumeGreaterThanZero(orderLineId, "salesOrderLineId");
		final I_C_OrderLine orderLine = ordersRepo.getOrderLineById(orderLineId);
		final I_C_Order order = orderLine.getC_Order();
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());

		final PricingConditionsRowData rowsData = preparePricingConditionsRowData()
				.pricingConditionsBreaksExtractor(createPricingConditionsBreaksExtractor(orderLine))
				.basePricingSystemPriceCalculator(new OrderLineBasePricingSystemPriceCalculator(orderLine))
				.filters(extractFilters(request))
				.sourceDocumentLine(createSourceDocumentLine(orderLine, soTrx))
				.load();
		return rowsData;
	}

	private final PricingConditionsBreaksExtractor createPricingConditionsBreaksExtractor(final I_C_OrderLine salesOrderLine)
	{
		final PricingConditionsBreakQuery pricingConditionsBreakQuery = createPricingConditionsBreakQuery(salesOrderLine);

		return pricingConditions -> {
			final PricingConditionsBreak matchingBreak = pricingConditions.pickApplyingBreak(pricingConditionsBreakQuery);
			return matchingBreak != null ? Stream.of(matchingBreak) : Stream.empty();
		};
	}

	private final PricingConditionsBreakQuery createPricingConditionsBreakQuery(final I_C_OrderLine salesOrderLine)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

		final ProductId productId = ProductId.ofRepoId(salesOrderLine.getM_Product_ID());
		final ProductAndCategoryAndManufacturerId product = productsRepo.retrieveProductAndCategoryAndManufacturerByProductId(productId);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(salesOrderLine.getM_AttributeSetInstance_ID());
		final List<I_M_AttributeInstance> attributeInstances = attributesRepo.retrieveAttributeInstances(asiId);
		final BigDecimal qty = salesOrderLine.getQtyOrdered();
		final BigDecimal price = salesOrderLine.getPriceActual();

		return PricingConditionsBreakQuery.builder()
				.product(product)
				.attributeInstances(attributeInstances)
				.qty(qty)
				.price(price)
				.build();
	}

	private final SourceDocumentLine createSourceDocumentLine(final I_C_OrderLine orderLine, final SOTrx soTrx)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final ProductCategoryId productCategoryId = productsRepo.retrieveProductCategoryByProductId(productId);

		return SourceDocumentLine.builder()
				.orderLineId(OrderLineId.ofRepoIdOrNull(orderLine.getC_OrderLine_ID()))
				.soTrx(soTrx)
				.bpartnerId(BPartnerId.ofRepoId(orderLine.getC_BPartner_ID()))
				.productId(productId)
				.productCategoryId(productCategoryId)
				.priceEntered(orderLine.getPriceEntered())
				.discount(Percent.of(orderLine.getDiscount()))
				.paymentTermId(PaymentTermId.ofRepoIdOrNull(orderLine.getC_PaymentTerm_Override_ID()))
				.pricingConditionsBreakId(PricingConditionsBreakId.ofOrNull(orderLine.getM_DiscountSchema_ID(), orderLine.getM_DiscountSchemaBreak_ID()))
				.build();
	}

	private static class OrderLineBasePricingSystemPriceCalculator implements BasePricingSystemPriceCalculator
	{
		private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		private final I_C_OrderLine orderLine;

		private final ConcurrentHashMap<PricingConditionsBreak, BigDecimal> basePricesCache = new ConcurrentHashMap<>();

		public OrderLineBasePricingSystemPriceCalculator(@NonNull final I_C_OrderLine orderLine)
		{
			this.orderLine = orderLine;
		}

		@Override
		public BigDecimal calculate(final BasePricingSystemPriceCalculatorRequest request)
		{
			final PricingConditionsBreak pricingConditionsBreak = request.getPricingConditionsBreak();
			return basePricesCache.computeIfAbsent(pricingConditionsBreak, this::calculate);
		}

		private BigDecimal calculate(final PricingConditionsBreak pricingConditionsBreak)
		{
			return orderLineBL.computePrices(OrderLinePriceUpdateRequest.builder()
					.orderLine(orderLine)
					.pricingConditionsBreakOverride(pricingConditionsBreak)
					.resultUOM(ResultUOM.PRICE_UOM_IF_ORDERLINE_IS_NEW)
					.build())
					.getPriceStd();
		}

	}
}
