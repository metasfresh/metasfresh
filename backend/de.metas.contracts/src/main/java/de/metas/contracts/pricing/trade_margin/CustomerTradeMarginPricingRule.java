/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.contracts.pricing.trade_margin;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.margin.MarginConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigProvider;
import de.metas.contracts.commission.commissioninstance.services.margin.MarginCommissionConfigFactory;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Allows a sales rep offer a lower price to a customer and in turn receive a lower commission.
 */
public class CustomerTradeMarginPricingRule implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(CustomerTradeMarginPricingRule.class);

	private final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

	private final CustomerTradeMarginService customerTradeMarginService = SpringContextHolder.instance.getBean(CustomerTradeMarginService.class);
	private final MarginCommissionConfigFactory marginCommissionConfigFactory = SpringContextHolder.instance.getBean(MarginCommissionConfigFactory.class);

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);

	@Override
	public boolean applies(@NonNull final IPricingContext pricingCtx, @NonNull final IPricingResult result)
	{
		if (!result.isCalculated())
		{
			loggable.addLog("applies - pricingResult.isCalculated=false -> return false");
			return false;
		}

		final Object referencedObj = pricingCtx.getReferencedObject();
		if (referencedObj == null)
		{
			loggable.addLog("applies - there is no referenced object in order to obtain salesRepId; -> return false");
			return false;
		}

		final Optional<BPartnerId> salesRepId = getSalesRepId(referencedObj);

		if (!salesRepId.isPresent())
		{
			loggable.addLog("applies - SalesRepId is null for referenced object={}; -> return false", pricingCtx.getReferencedObject());
			return false;
		}

		final BPartnerId customerId = pricingCtx.getBPartnerId();
		if (customerId == null)
		{
			loggable.addLog("applies - CustomerId is null; -> return false");
			return false;
		}

		return true;
	}

	@Override
	public void calculate(@NonNull final IPricingContext pricingCtx,@NonNull final IPricingResult result)
	{
		final Object referencedObj = pricingCtx.getReferencedObject();
		if (referencedObj == null)
		{
			loggable.addLog("calculate - Not applying! No referencedObj found pricingCtx={}" + pricingCtx);
			return;
		}

		final Optional<BPartnerId> optionalSalesRepId = getSalesRepId(referencedObj);

		if (!optionalSalesRepId.isPresent())
		{
			loggable.addLog("calculate - Not applying! No bpartnerSalesRepId found for referencedObject={} found" + referencedObj);
			return;
		}

		final BPartnerId salesRepId = optionalSalesRepId.get();

		final Optional<ProductPrice> salesRepNetUnitPrice = getSalesRepNetUnitPrice(pricingCtx, salesRepId);

		if (!salesRepNetUnitPrice.isPresent())
		{
			loggable.addLog("calculate - Not applying! salesRep price could not be computed in customer currency, salesRepId={}" + salesRepId);
			return;
		}

		final Optional<MarginConfig> marginConfig = getMarginConfig(pricingCtx, salesRepId);

		if (!marginConfig.isPresent())
		{
			loggable.addLog("calculate - Not applying! no margin config found for sales rep, salesRepId={}" + salesRepId);
			return;
		}

		final ProductPrice salesRepPriceInCustomerCurrency = salesRepNetUnitPrice.get();

		Check.assume(salesRepPriceInCustomerCurrency.getCurrencyId().equals(result.getCurrencyId()), "Sales rep unit price and customer unit price are in the same currency.");
		Check.assume(salesRepPriceInCustomerCurrency.getUomId().equals(result.getPriceUomId()), "Sales rep unit price and customer unit price are calculated for the same UOM.");

		//todo fp


		final BigDecimal salesRepMargin = result.getPriceStd().compareTo(salesRepPriceInCustomerCurrency.toBigDecimal()) <= 0
				? BigDecimal.ZERO
				: result.getPriceStd().subtract(salesRepPriceInCustomerCurrency.toBigDecimal());

		final BigDecimal tradedMargin = marginConfig.get()
				.getTradedPercent()
				.computePercentageOf(salesRepMargin, marginConfig.get().getPointsPrecision());

		final ProductPrice priceBeforeApplyingMargin = ProductPrice
				.builder()
				.productId(pricingCtx.getProductId())
				.money(Money.of(result.getPriceStd(), result.getCurrencyId()))
				.uomId(result.getPriceUomId())
				.build();

		final BigDecimal newPrice = result.getPriceStd().subtract(tradedMargin);

		result.setPriceStd(newPrice);
		result.setCalculated(true);
		result.setPriceStd(newPrice);
		result.setPriceEditable(false);

		loggable.addLog("calculate - Price before applying margin: {} currencyID: {}, Price after applying margin: {} currencyID :{}",
						priceBeforeApplyingMargin.toBigDecimal(), priceBeforeApplyingMargin.getCurrencyId().getRepoId(),
						result.getPriceStd(), result.getCurrencyId().getRepoId());
	}

	@NonNull
	private Optional<BPartnerId> getSalesRepId(@NonNull final Object referencedObj)
	{
		if (InterfaceWrapperHelper.isInstanceOf(referencedObj, I_C_OrderLine.class))
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(referencedObj, I_C_OrderLine.class);

			final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()), I_C_Order.class);

			if (order.getC_BPartner_SalesRep_ID() <= 0)
			{
				loggable.addLog("applies - there is no bPartnerSalesRepId on orderId={}; " + OrderId.ofRepoId(order.getC_Order_ID()));
				return Optional.empty();
			}

			return Optional.of(BPartnerId.ofRepoId(order.getC_BPartner_SalesRep_ID()));
		}
		else if (InterfaceWrapperHelper.isInstanceOf(referencedObj, I_C_InvoiceLine.class))
		{
			final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(referencedObj, I_C_InvoiceLine.class);

			final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));

			if (invoice.getC_BPartner_SalesRep_ID() <= 0)
			{
				loggable.addLog("applies - there is no salesRepId on invoiceId={}; " + InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
				return Optional.empty();
			}

			return Optional.of(BPartnerId.ofRepoId(invoice.getC_BPartner_SalesRep_ID()));
		}

		return Optional.empty();
	}

	@NonNull
	private Optional<ProductPrice> getSalesRepNetUnitPrice(@NonNull final IPricingContext pricingCtx, @NonNull final BPartnerId salesRepId)
	{
		final I_C_UOM priceUOM = uomDao.getById(pricingCtx.getUomId());

		final SalesRepPricingResultRequest request = SalesRepPricingResultRequest.builder()
				.salesRepId(salesRepId)
				.soTrx(SOTrx.SALES)
				.productId(pricingCtx.getProductId())
				.qty(Quantity.of(pricingCtx.getQty(), priceUOM))
				.customerCurrencyId(pricingCtx.getCurrencyId())
				.commissionDate(pricingCtx.getPriceDate())
				.build();

		return customerTradeMarginService.calculateSalesRepNetUnitPrice(request);
	}

	@NonNull
	private Optional<MarginConfig> getMarginConfig(@NonNull final IPricingContext pricingCtx, @NonNull final BPartnerId salesRepId)
	{
		final CommissionConfigProvider.ConfigRequestForNewInstance configRequest = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.orgId(pricingCtx.getOrgId())
				.salesRepBPartnerId(salesRepId)
				.commissionTriggerType(CommissionTriggerType.Plain)
				.salesProductId(pricingCtx.getProductId())
				.customerBPartnerId(pricingCtx.getBPartnerId())
				.commissionHierarchy(Hierarchy.EMPTY_HIERARCHY)
				.commissionDate(pricingCtx.getPriceDate())
				.build();

		final List<CommissionConfig> marginCommissionConfig = marginCommissionConfigFactory.createForNewCommissionInstances(configRequest);

		if (marginCommissionConfig.isEmpty())
		{
			return Optional.empty();
		}

		final MarginConfig marginConfig = MarginConfig.cast(CollectionUtils.singleElement(marginCommissionConfig));
		return Optional.of(marginConfig);
	}
}
