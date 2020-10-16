package de.metas.invoice.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getCtx;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.TaxCategoryNotFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MTax;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public class InvoiceLineBL implements IInvoiceLineBL
{
	private static final Logger logger = LogManager.getLogger(InvoiceLineBL.class);

	@Override
	public void setTaxAmtInfo(final Properties ctx, final I_C_InvoiceLine il, final String getTrxName)
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final ITaxBL taxBL = Services.get(ITaxBL.class);

		final int taxId = il.getC_Tax_ID();

		final boolean taxIncluded = invoiceBL.isTaxIncluded(il);
		final BigDecimal lineNetAmt = il.getLineNetAmt();
		final CurrencyPrecision taxPrecision = invoiceBL.getTaxPrecision(il);

		final I_C_Tax tax = MTax.get(ctx, taxId);
		final BigDecimal taxAmtInfo = taxBL.calculateTax(tax, lineNetAmt, taxIncluded, taxPrecision.toInt());

		il.setTaxAmtInfo(taxAmtInfo);
	}

	@Override
	public boolean setTaxBasedOnShipment(final org.compiere.model.I_C_InvoiceLine il, final String trxName)
	{
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final InvoiceId invoiceId = InvoiceId.ofRepoId(il.getC_Invoice_ID());
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		if (il.getM_InOutLine_ID() <= 0)
		{
			logger.debug(il + "has M_InOutLine_ID=" + il.getM_InOutLine_ID() + ": returning");
			return false;
		}

		final InOutLineId inoutLineId = InOutLineId.ofRepoId(il.getM_InOutLine_ID());
		final I_M_InOutLine inoutLineRecord = inoutDAO.getLineById(inoutLineId);

		final I_M_InOut io = inoutDAO.getById(InOutId.ofRepoId(inoutLineRecord.getM_InOut_ID()));

		final OrgId inOutOrgId = OrgId.ofRepoId(io.getAD_Org_ID());

		final Timestamp shipDate = io.getMovementDate();

		final BPartnerLocationId shipToPartnerLocationId = BPartnerLocationId.ofRepoId(io.getC_BPartner_ID(), io.getC_BPartner_Location_ID());

		final boolean isSOTrx = io.isSOTrx();

		final CountryId countryFromId = invoiceBL.getFromCountryId(invoice, il);

		return setTaxForInvoiceLine(il, inOutOrgId, shipDate, countryFromId, shipToPartnerLocationId, isSOTrx);
	}

	@Override
	public boolean setTaxForInvoiceLine(
			final org.compiere.model.I_C_InvoiceLine il,
			final OrgId orgId,
			final Timestamp taxDate,
			final CountryId countryFromId,
			final BPartnerLocationId partnerLocationId,
			final boolean isSOTrx)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final ITaxBL taxBL = Services.get(ITaxBL.class);
		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final InvoiceId invoiceId = InvoiceId.ofRepoId(il.getC_Invoice_ID());
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		final I_C_BPartner_Location locationTo = bpartnerDAO.getBPartnerLocationById(partnerLocationId);

		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(il))
		{
			TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoIdOrNull(il.getC_TaxCategory_ID());
			if (taxCategoryId == null && il.getM_Product_ID() > 0)
			{
				// NOTE: we can retrieve the tax category only if we have a product
				taxCategoryId = getTaxCategoryId(il);
				il.setC_TaxCategory_ID(TaxCategoryId.toRepoId(taxCategoryId));
			}

			if (il.getM_Product_ID() <= 0)
			{
				// this might be the case if a descriptional il refers to an iol.
				logger.debug(il + "has M_Product_ID=" + il.getM_Product_ID() + ": returning");
				return false;
			}

			final Properties ctx = getCtx(invoice);

			final int taxId = taxBL.retrieveTaxIdForCategory(ctx,
					countryFromId,
					orgId,
					locationTo,
					taxDate,
					taxCategoryId,
					isSOTrx,
					false);

			if (taxId <= 0)
			{
				final I_C_BPartner_Location bPartnerLocationRecord = bpartnerDAO.getBPartnerLocationById(BPartnerLocationId.ofRepoId(invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID()));

				throw TaxNotFoundException.builder()
						.taxCategoryId(taxCategoryId)
						.isSOTrx(isSOTrx)
						.shipDate(taxDate)
						.shipFromCountryId(countryFromId)
						.shipToC_Location_ID(locationTo.getC_Location_ID())
						.billDate(invoice.getDateInvoiced())
						.billFromCountryId(countryFromId)
						.billToC_Location_ID(bPartnerLocationRecord.getC_Location_ID())
						.build();
			}

			final boolean taxChange = il.getC_Tax_ID() != taxId;
			if (taxChange)
			{
				logger.info("Changing C_Tax_ID to " + taxId + " for " + il);
				il.setC_Tax_ID(taxId);

				final I_C_Tax tax = taxDAO.getTaxByIdOrNull(taxId);

				il.setC_TaxCategory_ID(tax.getC_TaxCategory_ID());
			}
			return taxChange;
		}
	}

	@Override
	public boolean isPriceLocked(final I_C_InvoiceLine invoiceLine)
	{
		// // Introduced by US1184, because having the same price on Order and Invoice
		// no - invoice does not generally have to have the same prive not generally
		// // is enforced by German Law
		// if (invoiceLine.getC_OrderLine_ID() > 0)
		// return true;
		//
		// return false;
		return false;
	}

	@Override
	public TaxCategoryId getTaxCategoryId(final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		// FIXME: we need to retrieve the C_TaxCategory_ID by using Pricing Engine

		if (invoiceLine.getC_Charge_ID() > 0)
		{
			return TaxCategoryId.ofRepoId(invoiceLine.getC_Charge().getC_TaxCategory_ID());
		}

		final I_C_Invoice invoice = invoiceLine.getC_Invoice();
		if (invoice.getM_PriceList_ID() != IPriceListDAO.M_PriceList_ID_None)
		{
			return getTaxCategoryFromProductPrice(invoiceLine, invoice);
		}

		// Fallback: try getting from Order Line
		if (invoiceLine.getC_OrderLine_ID() > 0)
		{
			return TaxCategoryId.ofRepoIdOrNull(invoiceLine.getC_OrderLine().getC_TaxCategory_ID());
		}

		// Fallback: try getting from Invoice -> Order
		if (invoiceLine.getC_Invoice().getC_Order_ID() > 0)
		{
			return getTaxCategoryFromOrder(invoiceLine, invoice);
		}

		throw new TaxCategoryNotFoundException(invoiceLine);
	}

	private TaxCategoryId getTaxCategoryFromProductPrice(
			final org.compiere.model.I_C_InvoiceLine invoiceLine,
			final I_C_Invoice invoice)
	{
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final Boolean processedPLVFiltering = null; // task 09533: the user doesn't know about PLV's processed flag, so we can't filter by it

		final PriceListId priceListId = PriceListId.ofRepoId(invoice.getM_PriceList_ID());

		final I_M_PriceList_Version priceListVersion = priceListDAO.retrievePriceListVersionOrNull(
				priceListId,
				TimeUtil.asZonedDateTime(invoice.getDateInvoiced()),
				processedPLVFiltering);
		Check.errorIf(priceListVersion == null, "Missing PLV for M_PriceList and DateInvoiced of {}", invoice);

		final ProductId productId = ProductId.ofRepoIdOrNull(invoiceLine.getM_Product_ID());
		Check.assumeNotNull(productId, "M_Product_ID > 0 for {}", invoiceLine);

		final I_M_ProductPrice productPrice = Optional
				.ofNullable(ProductPrices.retrieveMainProductPriceOrNull(priceListVersion, productId))
				.orElseThrow(() -> new TaxCategoryNotFoundException(invoiceLine));

		return TaxCategoryId.ofRepoId(productPrice.getC_TaxCategory_ID());
	}

	private TaxCategoryId getTaxCategoryFromOrder(
			final org.compiere.model.I_C_InvoiceLine invoiceLine,
			final I_C_Invoice invoice)
	{
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final Boolean processedPLVFiltering = null; // task 09533: the user doesn't know about PLV's processed flag, so we can't filter by it

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoiceLine);

		final I_C_Order order = InterfaceWrapperHelper.create(ctx, invoiceLine.getC_Invoice().getC_Order_ID(), I_C_Order.class, trxName);

		final I_M_PriceList priceList = priceListDAO.getById(order.getM_PriceList_ID());

		final I_M_PriceList_Version priceListVersion = priceListDAO.retrievePriceListVersionOrNull(
				priceList,
				TimeUtil.asZonedDateTime(invoice.getDateInvoiced()),
				processedPLVFiltering);
		Check.errorIf(priceListVersion == null, "Missing PLV for M_PriceList and DateInvoiced of {}", invoice);

		final ProductId productId = ProductId.ofRepoIdOrNull(invoiceLine.getM_Product_ID());
		Check.assumeNotNull(productId, "M_Product_ID > 0 for {}", invoiceLine);

		final I_M_ProductPrice productPrice = Optional
				.ofNullable(ProductPrices.retrieveMainProductPriceOrNull(priceListVersion, productId))
				.orElseThrow(() -> new TaxCategoryNotFoundException(invoiceLine));
		return TaxCategoryId.ofRepoId(productPrice.getC_TaxCategory_ID());
	}

	@Override
	public void setQtyInvoicedInPriceUOM(final I_C_InvoiceLine invoiceLine)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(invoiceLine))
		{
			final Quantity qtyInvoicedInPriceUOM = calculateQtyInvoicedInPriceUOM(invoiceLine);
			invoiceLine.setQtyInvoicedInPriceUOM(qtyInvoicedInPriceUOM.toBigDecimal());
		}
	}

	private Quantity calculateQtyInvoicedInPriceUOM(@NonNull final I_C_InvoiceLine ilRecord)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final BigDecimal qtyEntered = ilRecord.getQtyEntered();
		Check.assumeNotNull(qtyEntered, "qtyEntered not null; ilRecord={}", ilRecord);

		final UomId priceUomId = UomId.ofRepoIdOrNull(ilRecord.getPrice_UOM_ID());
		final UomId uomId = UomId.ofRepoIdOrNull(ilRecord.getC_UOM_ID());
		final ProductId productId = ProductId.ofRepoIdOrNull(ilRecord.getM_Product_ID());

		if (priceUomId != null && uomId != null)
		{

			final Quantity qtyToConvert = Quantitys.create(qtyEntered, uomId);
			final Quantity result = uomConversionBL.convertQuantityTo(qtyToConvert, UOMConversionContext.of(productId), priceUomId);
			logger.debug("invoice line has both Price_UOM_ID={} and C_UOM_ID={}; return result={}", priceUomId.getRepoId(), uomId.getRepoId(), result);
			return result;
		}
		else if (uomId != null)
		{
			final Quantity result = Quantitys.create(qtyEntered, uomId);
			logger.debug("invoice line has Price_UOM_ID=null and C_UOM_ID={}; return result ={}", uomId.getRepoId(), result);
			return result;
		}
		else if (productId != null)
		{
			final UomId stockUomId = productBL.getStockUOMId(productId);
			final Quantity result = Quantitys.create(ilRecord.getQtyInvoiced(), stockUomId);
			logger.debug("invoice line has Price_UOM_ID=null, C_UOM_ID=null and M_Product_ID={}; return result={}", productId.getRepoId(), result);
			return result;
		}

		// this private method shouldn't have called
		throw new AdempiereException("given param 'invoiceLine' needs to have at least M_Product_ID>0 or C_UOM_ID>0")
				.appendParametersToMessage()
				.setParameter("invoiceLine", ilRecord);
	}

	@Override
	public IEditablePricingContext createPricingContext(final I_C_InvoiceLine invoiceLine)
	{
		final I_C_Invoice invoice = invoiceLine.getC_Invoice();
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(invoiceLine);
				final MDCCloseable ignored1 = TableRecordMDC.putTableRecordReference(invoice))
		{
			final PriceListId priceListId = PriceListId.ofRepoIdOrNull(invoice.getM_PriceList_ID());

			final Quantity qtyInvoicedInPriceUOM = calculateQtyInvoicedInPriceUOM(invoiceLine);

			return createPricingContext(invoiceLine, priceListId, qtyInvoicedInPriceUOM);
		}
	}

	public IEditablePricingContext createPricingContext(
			@NonNull final I_C_InvoiceLine invoiceLine,
			@NonNull final PriceListId priceListId,
			@NonNull final Quantity priceQty)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		final I_C_Invoice invoice = invoiceLine.getC_Invoice();

		final SOTrx isSOTrx = SOTrx.ofBoolean(invoice.isSOTrx());

		final ProductId productId = ProductId.ofRepoId(invoiceLine.getM_Product_ID()); // without a product-id, this method is not called

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());

		final LocalDate date = TimeUtil.asLocalDate(invoice.getDateInvoiced());

		final IEditablePricingContext pricingCtx = pricingBL
				.createInitialContext(
						OrgId.ofRepoIdOrAny(invoiceLine.getAD_Org_ID()),
						productId,
						bPartnerId,
						priceQty,
						isSOTrx)
				.setFailIfNotCalculated();

		pricingCtx.setPriceDate(date);

		// 03152: setting the 'ol' to allow the subscription system to compute the right price
		pricingCtx.setReferencedObject(invoiceLine);

		pricingCtx.setPriceListId(priceListId);
		// PLV is only accurate if PL selected in header
		// metas: relay on M_PriceList_ID only, don't use M_PriceList_Version_ID
		// pricingCtx.setM_PriceList_Version_ID(orderLine.getM_PriceList_Version_ID());

		final CountryId countryId = getCountryIdOrNull(invoiceLine);
		pricingCtx.setCountryId(countryId);

		return pricingCtx;
	}

	private CountryId getCountryIdOrNull(@NonNull final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_Invoice invoice = invoiceLine.getC_Invoice();

		if (invoice.getC_BPartner_Location_ID() <= 0)
		{
			return null;
		}

		if (invoice.getC_BPartner_Location_ID() <= 0)
		{
			return null;
		}
		final I_C_BPartner_Location bPartnerLocationRecord = bpartnerDAO.getBPartnerLocationById(BPartnerLocationId.ofRepoId(invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID()));

		return CountryId.ofRepoId(bPartnerLocationRecord.getC_Location().getC_Country_ID());
	}

	@Override
	public void updateLineNetAmt(final I_C_InvoiceLine line, final BigDecimal qtyEntered)
	{
		final IPriceListBL priceListBL = Services.get(IPriceListBL.class);

		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(line))
		{
			if (qtyEntered != null)
			{
				final I_C_Invoice invoice = line.getC_Invoice();
				final PriceListId priceListId = PriceListId.ofRepoId(invoice.getM_PriceList_ID());

				//
				// We need to get the quantity in the pricing's UOM (if different)
				final Quantity convertedQty = calculateQtyInvoicedInPriceUOM(line);

				// this code has been borrowed from
				// org.compiere.model.CalloutOrder.amt
				final CurrencyPrecision netPrecision = priceListBL.getAmountPrecision(priceListId);

				BigDecimal lineNetAmt = netPrecision.roundIfNeeded(convertedQty.toBigDecimal().multiply(line.getPriceActual()));
				logger.debug("LineNetAmt={}", lineNetAmt);
				line.setLineNetAmt(lineNetAmt);
			}
		}
	}

	@Override
	public void updatePrices(final I_C_InvoiceLine invoiceLine)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		// Product was not set yet. There is no point to calculate the prices
		if (invoiceLine.getM_Product_ID() <= 0)
		{
			return;
		}

		//
		// Calculate Pricing Result
		final IEditablePricingContext pricingCtx = createPricingContext(invoiceLine);
		final boolean usePriceUOM = InterfaceWrapperHelper.isNew(invoiceLine);
		pricingCtx.setConvertPriceToContextUOM(!usePriceUOM);

		pricingCtx.setManualPriceEnabled(invoiceLine.isManualPrice());

		if (pricingCtx.getManualPriceEnabled().isTrue())
		{
			// Task 08908: do not calculate the prices in case the price is manually set
			return;
		}

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx.setFailIfNotCalculated());

		//
		// PriceList
		final BigDecimal priceList = pricingResult.getPriceList();
		invoiceLine.setPriceList(priceList);

		invoiceLine.setPriceLimit(pricingResult.getPriceLimit());
		invoiceLine.setPrice_UOM_ID(UomId.toRepoId(pricingResult.getPriceUomId()));

		invoiceLine.setPriceEntered(pricingResult.getPriceStd());
		invoiceLine.setPriceActual(pricingResult.getPriceStd()); // will be updated in a few lines, if there is a discount

		// Issue https://github.com/metasfresh/metasfresh/issues/2400:
		// If the line has a discount, we assume it was manually added and stick with it
		// When invoices are created by the system, there is no need to change an already-set discount (and this code is executed only once anyways)
		if (invoiceLine.getDiscount().signum() == 0)
		{
			invoiceLine.setDiscount(pricingResult.getDiscount().toBigDecimal());
		}

		final PricingConditionsResult pricingConditions = pricingResult.getPricingConditions();
		invoiceLine.setBase_PricingSystem_ID(pricingConditions != null ? PricingSystemId.toRepoId(pricingConditions.getBasePricingSystemId()) : -1);

		//
		// Calculate PriceActual from PriceEntered and Discount
		calculatePriceActual(invoiceLine, pricingResult.getPrecision());

		invoiceLine.setPrice_UOM_ID(UomId.toRepoId(pricingResult.getPriceUomId())); //
	}

	private static void calculatePriceActual(final I_C_InvoiceLine invoiceLine, final CurrencyPrecision precision)
	{
		final IPriceListBL priceListBL = Services.get(IPriceListBL.class);

		final Percent discount = Percent.of(invoiceLine.getDiscount());
		final BigDecimal priceEntered = invoiceLine.getPriceEntered();

		BigDecimal priceActual;
		if (priceEntered.signum() == 0)
		{
			priceActual = priceEntered;
		}
		else
		{
			final CurrencyPrecision pricePrecision;
			if (precision != null)
			{
				pricePrecision = precision;
			}
			else
			{

				final I_C_Invoice invoice = invoiceLine.getC_Invoice();
				pricePrecision = priceListBL.getPricePrecision(PriceListId.ofRepoId(invoice.getM_PriceList_ID()));
			}

			priceActual = discount.subtractFromBase(priceEntered, pricePrecision.toInt(), pricePrecision.getRoundingMode());
		}

		invoiceLine.setPriceActual(priceActual);
	}
}
