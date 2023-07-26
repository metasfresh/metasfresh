package de.metas.ui.web.order.pricingconditions.view;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowsLoader.PricingConditionsBreaksExtractor;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowsLoader.SourceDocumentLine;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
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
	protected void onViewClosedByUser(@NonNull final PricingConditionsView view)
	{
		view.updateSalesOrderLineIfPossible();
	}

	@Override
	protected PricingConditionsRowData createPricingConditionsRowData(@NonNull final CreateViewRequest request)
	{
		final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

		final int orderLineId = CollectionUtils.singleElement(request.getFilterOnlyIds());
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

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(salesOrderLine.getM_AttributeSetInstance_ID());
		final ImmutableAttributeSet attributes = attributesRepo.getImmutableAttributeSetById(asiId);
		final BigDecimal qty = salesOrderLine.getQtyOrdered();
		final BigDecimal price = salesOrderLine.getPriceActual();

		return PricingConditionsBreakQuery.builder()
				.product(product)
				.attributes(attributes)
				.qty(qty)
				.price(price)
				.build();
	}

	private final SourceDocumentLine createSourceDocumentLine(@NonNull final I_C_OrderLine orderLineRecord, @NonNull final SOTrx soTrx)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final ProductId productId = ProductId.ofRepoId(orderLineRecord.getM_Product_ID());
		final ProductCategoryId productCategoryId = productsRepo.retrieveProductCategoryByProductId(productId);

		final Money priceEntered = Money.of(orderLineRecord.getPriceEntered(), CurrencyId.ofRepoId(orderLineRecord.getC_Currency_ID()));

		return SourceDocumentLine.builder()
				.orderLineId(OrderLineId.ofRepoIdOrNull(orderLineRecord.getC_OrderLine_ID()))
				.soTrx(soTrx)
				.bpartnerId(BPartnerId.ofRepoId(orderLineRecord.getC_BPartner_ID()))
				.productId(productId)
				.productCategoryId(productCategoryId)
				.priceEntered(priceEntered)
				.discount(Percent.of(orderLineRecord.getDiscount()))
				.paymentTermId(PaymentTermId.ofRepoIdOrNull(orderLineRecord.getC_PaymentTerm_Override_ID()))
				.pricingConditionsBreakId(PricingConditionsBreakId.ofOrNull(orderLineRecord.getM_DiscountSchema_ID(), orderLineRecord.getM_DiscountSchemaBreak_ID()))
				.build();
	}

	private static class OrderLineBasePricingSystemPriceCalculator implements BasePricingSystemPriceCalculator
	{
		private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		private final I_C_OrderLine orderLine;

		private final ConcurrentHashMap<PricingConditionsBreak, Money> basePricesCache = new ConcurrentHashMap<>();

		public OrderLineBasePricingSystemPriceCalculator(@NonNull final I_C_OrderLine orderLine)
		{
			this.orderLine = orderLine;
		}

		@Override
		public Money calculate(final BasePricingSystemPriceCalculatorRequest request)
		{
			final PricingConditionsBreak pricingConditionsBreak = request.getPricingConditionsBreak();
			return basePricesCache.computeIfAbsent(pricingConditionsBreak, this::calculate);
		}

		private Money calculate(final PricingConditionsBreak pricingConditionsBreak)
		{
			final IPricingResult pricingResult = orderLineBL.computePrices(OrderLinePriceUpdateRequest.builder()
					.orderLine(orderLine)
					.pricingConditionsBreakOverride(pricingConditionsBreak)
					.resultUOM(ResultUOM.PRICE_UOM_IF_ORDERLINE_IS_NEW)
					.build());

			return Money.of(pricingResult.getPriceStd(), pricingResult.getCurrencyId());
		}
	}
}
