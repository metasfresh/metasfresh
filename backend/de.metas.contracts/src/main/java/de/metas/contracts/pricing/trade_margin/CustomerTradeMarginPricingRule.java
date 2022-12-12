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
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
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
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	// loaded by applies, used by compute
	private MarginConfig marginConfig;

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

		final OrgId orgId = pricingCtx.getOrgId();
		if (orgId == null)
		{
			loggable.addLog("applies - OrgId is null; -> return false");
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

		final ProductId productId = pricingCtx.getProductId();
		if (productId == null)
		{
			loggable.addLog("applies - ProductId is null; -> return false");
			return false;
		}

		final UomId uomId = result.getPriceUomId();
		if (uomId == null)
		{
			loggable.addLog("applies - priceUomId is null; -> return false");
			return false;
		}

		final CurrencyId currencyId = result.getCurrencyId();
		if (currencyId == null)
		{
			loggable.addLog("applies - CurrencyId is null; -> return false");
			return false;
		}

		final TaxCategoryId taxCategoryId = result.getTaxCategoryId();
		if (taxCategoryId == null)
		{
			loggable.addLog("applies - TaxCategoryId is null; -> return false");
			return false;
		}

		final Optional<MarginConfig> marginConfig = getMarginConfig(
				salesRepId.get(),
				pricingCtx);
		if (!marginConfig.isPresent())
		{
			loggable.addLog("applies - salesrep has no MarginConfig; -> return false");
			return false;
		}

		this.marginConfig = marginConfig.get();
		return true;
	}

	@Override
	public void calculate(@NonNull final IPricingContext pricingCtx, @NonNull final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final Object referencedObj = Objects.requireNonNull(pricingCtx.getReferencedObject());

		final Optional<BPartnerLocationAndCaptureId> bPartnerLocationAndCaptureId = getBPartnerLocationAndCaptureId(referencedObj);

		if (!bPartnerLocationAndCaptureId.isPresent())
		{
			loggable.addLog("calculate - no bPartnerLocationAndCaptureId could be found for customer; -> skipping!");
			return;
		}

		final BPartnerId salesRepId = getSalesRepId(referencedObj)
				.orElseThrow(() -> new AdempiereException("PricingRule.applies() should've taken care of this!"));

		final CustomerPricingContext customerPricingContext = CustomerPricingContext.builder()
				.pricingContext(pricingCtx)
				.pricingResult(result)
				.bPartnerLocationAndCaptureId(bPartnerLocationAndCaptureId.get())
				.build();

		calculateCustomerPrice(customerPricingContext, salesRepId);
	}

	private void calculateCustomerPrice(@NonNull final CustomerPricingContext customerPricingContext, @NonNull final BPartnerId salesRepId)
	{
		final Optional<ProductPrice> salesRepNetUnitPriceOpt = getSalesRepNetUnitPrice(customerPricingContext, salesRepId);
		if (!salesRepNetUnitPriceOpt.isPresent())
		{
			loggable.addLog("calculate - Not applying! salesRep price could not be computed in customer currency, salesRepId={}" + salesRepId);
			return;
		}

		final BigDecimal newCustomerPrice = calculateNewCustomerPriceStd(
				customerPricingContext,
				salesRepNetUnitPriceOpt.get(),
				marginConfig /*was loaded by applies() method*/);

		final IPricingResult pricingResult = customerPricingContext.getPricingResult();

		pricingResult.setPriceStd(newCustomerPrice);
		pricingResult.setCalculated(true);
		pricingResult.setPriceEditable(false);
	}

	@NonNull
	private Optional<ProductPrice> getSalesRepNetUnitPrice(
			@NonNull final CustomerPricingContext customerPricingContext,
			@NonNull final BPartnerId salesRepId)
	{
		final I_C_UOM priceUOM = uomDao.getById(customerPricingContext.getResultUomId());

		final ComputeSalesRepPriceRequest request = ComputeSalesRepPriceRequest.builder()
				.salesRepId(salesRepId)
				.soTrx(SOTrx.SALES)
				.productId(customerPricingContext.getProductId())
				.qty(Quantity.of(BigDecimal.ONE, priceUOM))
				.customerCurrencyId(customerPricingContext.getResultCurrencyId())
				.commissionDate(customerPricingContext.getResultPriceDate())
				.build();

		return customerTradeMarginService.calculateSalesRepNetUnitPrice(request);
	}
	
	private Optional<MarginConfig> getMarginConfig(
			@NonNull final BPartnerId salesRepId, 
			@NonNull final IPricingContext pricingCtx)
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
				loggable.addLog("applies - there is no bPartnerSalesRepId on invoiceId={}; " + InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
				return Optional.empty();
			}

			return Optional.of(BPartnerId.ofRepoId(invoice.getC_BPartner_SalesRep_ID()));
		}

		return Optional.empty();
	}

	@NonNull
	private Tax getCustomerPriceTax(@NonNull final CustomerPricingContext customerPricingContext)
	{

		final TaxId taxId = taxBL.getTaxNotNull(
				null,
				customerPricingContext.getResultTaxCategory(),
				customerPricingContext.getProductId().getRepoId(),
				Objects.requireNonNull(TimeUtil.asTimestamp(customerPricingContext.getResultPriceDate())),
				customerPricingContext.getOrgId(),
				null /*WarehouseId*/,
				customerPricingContext.getBPartnerLocationAndCaptureId(),
				SOTrx.SALES);

		return taxDAO.getTaxById(taxId);
	}

	@NonNull
	private Optional<BPartnerLocationAndCaptureId> getBPartnerLocationAndCaptureId(@NonNull final Object referencedObj)
	{

		BPartnerLocationId bPartnerLocationId = null;
		if (InterfaceWrapperHelper.isInstanceOf(referencedObj, I_C_OrderLine.class))
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(referencedObj, I_C_OrderLine.class);

			bPartnerLocationId = BPartnerLocationId.ofRepoId(orderLine.getC_BPartner_ID(), orderLine.getC_BPartner_Location_ID());
		}
		else if (InterfaceWrapperHelper.isInstanceOf(referencedObj, I_C_InvoiceLine.class))
		{
			final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(referencedObj, I_C_InvoiceLine.class);

			final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));

			bPartnerLocationId = BPartnerLocationId.ofRepoId(invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID());
		}

		return Optional.ofNullable(bPartnerLocationId)
				.map(BPartnerLocationAndCaptureId::ofNullableLocationWithUnknownCapture);
	}

	@NonNull
	private BigDecimal calculateNewCustomerPriceStd(
			@NonNull final CustomerPricingContext customerPricingContext,
			@NonNull final ProductPrice salesRepNetUnitPrice,
			@NonNull final MarginConfig marginConfig)
	{
		final ProductPrice customerProductPrice = customerPricingContext.getProductPrice();

		Check.assume(salesRepNetUnitPrice.getCurrencyId().equals(customerProductPrice.getCurrencyId()), "Sales rep unit price and customer unit price are in the same currency.");
		Check.assume(salesRepNetUnitPrice.getUomId().equals(customerProductPrice.getUomId()), "Sales rep unit price and customer unit price are calculated for the same UOM.");

		final BigDecimal customerNetUnitPrice;
		Tax customerPriceTax = null;
		if (!customerPricingContext.isTaxIncluded())
		{
			customerNetUnitPrice = customerProductPrice.toBigDecimal();
		}
		else
		{
			customerPriceTax = getCustomerPriceTax(customerPricingContext);
			customerNetUnitPrice = customerPriceTax
					.calculateBaseAmt(customerProductPrice.toBigDecimal(), true, marginConfig.getPointsPrecision());
		}

		final BigDecimal salesRepMargin = customerNetUnitPrice.compareTo(salesRepNetUnitPrice.toBigDecimal()) <= 0
				? BigDecimal.ZERO
				: customerNetUnitPrice.subtract(salesRepNetUnitPrice.toBigDecimal());

		final BigDecimal tradedMargin = marginConfig
				.getTradedPercent()
				.computePercentageOf(salesRepMargin, marginConfig.getPointsPrecision());

		final BigDecimal newNetPrice = customerNetUnitPrice.subtract(tradedMargin);
		final BigDecimal newCustomerPrice = customerPriceTax == null
				? newNetPrice
				: customerPriceTax.calculateGross(newNetPrice, marginConfig.getPointsPrecision());

		loggable.addLog("calculate - Price before applying margin: {} currencyID: {}, Price after applying margin: {}",
						customerProductPrice.toBigDecimal(), customerProductPrice.getCurrencyId().getRepoId(), newCustomerPrice);

		return newCustomerPrice;
	}

	@Value
	@Builder
	private static class CustomerPricingContext
	{
		@NonNull
		IPricingResult pricingResult;
		@NonNull
		IPricingContext pricingContext;
		@NonNull
		BPartnerLocationAndCaptureId bPartnerLocationAndCaptureId;

		@NonNull
		public ProductId getProductId()
		{
			return (ProductId)assumeNotNull(pricingContext.getProductId());
		}

		@NonNull
		public BPartnerId getBPartnerId()
		{
			return (BPartnerId)assumeNotNull(pricingContext.getBPartnerId());
		}

		@NonNull
		public OrgId getOrgId()
		{
			return (OrgId)assumeNotNull(pricingContext.getOrgId());
		}

		@NonNull
		public CurrencyId getResultCurrencyId()
		{
			return (CurrencyId)assumeNotNull(pricingResult.getCurrencyId());
		}

		@NonNull
		public UomId getResultUomId()
		{
			return (UomId)assumeNotNull(pricingResult.getPriceUomId());
		}

		@NonNull
		public TaxCategoryId getResultTaxCategory()
		{
			return (TaxCategoryId)assumeNotNull(pricingResult.getTaxCategoryId());
		}

		@NonNull
		public LocalDate getResultPriceDate()
		{
			return pricingResult.getPriceDate();
		}

		public boolean isTaxIncluded()
		{
			return pricingResult.isTaxIncluded();
		}

		@NonNull
		public ProductPrice getProductPrice()
		{
			return ProductPrice.builder()
					.productId(getProductId())
					.uomId(getResultUomId())
					.money(Money.of(pricingResult.getPriceStd(), getResultCurrencyId()))
					.build();
		}

		private static Object assumeNotNull(@Nullable final Object arg)
		{
			Check.assumeNotNull(arg, "applies() - should've taken care of this!");

			return arg;
		}
	}

}
