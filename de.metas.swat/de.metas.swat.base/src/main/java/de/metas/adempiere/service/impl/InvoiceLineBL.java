package de.metas.adempiere.service.impl;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.TaxCategoryNotFoundException;
import org.adempiere.exceptions.TaxNotFoundException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.api.ProductPriceQuery;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MPriceList;
import org.compiere.model.MTax;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.service.IInvoiceLineBL;
import de.metas.logging.LogManager;
import de.metas.tax.api.ITaxBL;

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
		final int taxPrecision = invoiceBL.getPrecision(il);

		final I_C_Tax tax = MTax.get(ctx, taxId);
		final BigDecimal taxAmtInfo = taxBL.calculateTax(tax, lineNetAmt, taxIncluded, taxPrecision);

		il.setTaxAmtInfo(taxAmtInfo);
	}

	@Override
	public boolean setTax(final Properties ctx, final org.compiere.model.I_C_InvoiceLine il, final String trxName)
	{
		int taxCategoryId = il.getC_TaxCategory_ID();
		if (taxCategoryId <= 0 && il.getM_Product_ID() > 0)
		{
			// NOTE: we can retrieve the tax category only if we have a product
			taxCategoryId = getC_TaxCategory_ID(il);
			il.setC_TaxCategory_ID(taxCategoryId);
		}

		if (il.getM_InOutLine_ID() <= 0)
		{
			logger.debug(il + "has M_InOutLine_ID=" + il.getM_InOutLine_ID() + ": returning");
			return false;
		}

		if (il.getM_Product_ID() <= 0)
		{
			// this might be the case if a descriptional il refers to an iol.
			logger.debug(il + "has M_Product_ID=" + il.getM_Product_ID() + ": returning");
			return false;
		}

		final I_M_InOut io = il.getM_InOutLine().getM_InOut();

		final I_C_Location locationFrom = Services.get(IWarehouseBL.class).getC_Location(io.getM_Warehouse());
		final int countryFromId = locationFrom.getC_Country_ID();

		final I_C_BPartner_Location locationTo = InterfaceWrapperHelper.create(io.getC_BPartner_Location(), I_C_BPartner_Location.class);

		final Timestamp shipDate = io.getMovementDate();
		final int taxId = Services.get(ITaxBL.class).retrieveTaxIdForCategory(ctx,
				countryFromId,
				io.getAD_Org_ID(),
				locationTo,
				shipDate, taxCategoryId,
				il.getC_Invoice().isSOTrx(),
				trxName, false);

		if (taxId <= 0)
		{
			final I_C_Invoice invoice = il.getC_Invoice();
			throw new TaxNotFoundException(taxCategoryId, io.isSOTrx(),
					shipDate,
					locationFrom.getC_Location_ID(),
					locationTo.getC_Location_ID(),
					invoice.getDateInvoiced(),
					locationFrom.getC_Location_ID(),
					invoice.getC_BPartner_Location().getC_Location_ID());
		}

		final boolean taxChange = il.getC_Tax_ID() != taxId;
		if (taxChange)
		{
			logger.info("Changing C_Tax_ID to " + taxId + " for " + il);
			il.setC_Tax_ID(taxId);

			final I_C_Tax tax = il.getC_Tax();
			il.setC_TaxCategory_ID(tax.getC_TaxCategory_ID());
		}
		return taxChange;
	}

	@Override
	public boolean isPriceLocked(I_C_InvoiceLine invoiceLine)
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
	public int getC_TaxCategory_ID(final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		// FIXME: we need to retrieve the C_TaxCategory_ID by using Pricing Engine

		if (invoiceLine.getC_Charge_ID() > 0)
		{
			return invoiceLine.getC_Charge().getC_TaxCategory_ID();
		}

		final I_C_Invoice invoice = invoiceLine.getC_Invoice();

		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		final Boolean processedPLVFiltering = null; // task 09533: the user doesn't know about PLV's processed flag, so we can't filter by it

		if (invoice.getM_PriceList_ID() != 100)  // FIXME use PriceList_None constant
		{
			final I_M_PriceList priceList = invoice.getM_PriceList();

			final I_M_PriceList_Version priceListVersion = priceListDAO.retrievePriceListVersionOrNull(priceList, invoice.getDateInvoiced(), processedPLVFiltering);
			Check.errorIf(priceListVersion == null, "Missing PLV for M_PriceList and DateInvoiced of {}", invoice);

			final int m_Product_ID = invoiceLine.getM_Product_ID();
			Check.assume(m_Product_ID > 0, "M_Product_ID > 0 for {}", invoiceLine);

			final I_M_ProductPrice productPrice = ProductPriceQuery.retrieveMainProductPriceIfExists(priceListVersion, m_Product_ID)
					.orElseThrow(() -> new TaxCategoryNotFoundException(invoiceLine));

			return productPrice.getC_TaxCategory_ID();
		}

		// Fallback: try getting from Order Line
		if (invoiceLine.getC_OrderLine_ID() > 0)
		{
			return invoiceLine.getC_OrderLine().getC_TaxCategory_ID();
		}

		// Fallback: try getting from Invoice -> Order
		if (invoiceLine.getC_Invoice().getC_Order_ID() > 0)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceLine);
			final String trxName = InterfaceWrapperHelper.getTrxName(invoiceLine);

			final I_C_Order order = InterfaceWrapperHelper.create(ctx, invoiceLine.getC_Invoice().getC_Order_ID(), I_C_Order.class, trxName);

			final I_M_PriceList priceList = order.getM_PriceList();

			final I_M_PriceList_Version priceListVersion = priceListDAO.retrievePriceListVersionOrNull(priceList, invoice.getDateInvoiced(), processedPLVFiltering);
			Check.errorIf(priceListVersion == null, "Missing PLV for M_PriceList and DateInvoiced of {}", invoice);

			final int m_Product_ID = invoiceLine.getM_Product_ID();
			Check.assume(m_Product_ID > 0, "M_Product_ID > 0 for {}", invoiceLine);

			final I_M_ProductPrice productPrice = ProductPriceQuery.retrieveMainProductPriceIfExists(priceListVersion, m_Product_ID)
					.orElseThrow(() -> new TaxCategoryNotFoundException(invoiceLine));
			return productPrice.getC_TaxCategory_ID();
		}

		throw new TaxCategoryNotFoundException(invoiceLine);
	}

	@Override
	public void setQtyInvoicedInPriceUOM(final I_C_InvoiceLine invoiceLine)
	{
		final BigDecimal qtyInvoicedInPriceUOM = calculateQtyInvoicedInPriceUOM(invoiceLine);
		invoiceLine.setQtyInvoicedInPriceUOM(qtyInvoicedInPriceUOM);
	}

	private BigDecimal calculateQtyInvoicedInPriceUOM(final I_C_InvoiceLine invoiceLine)
	{
		Check.assumeNotNull(invoiceLine, "invoiceLine not null");

		final BigDecimal qty = invoiceLine.getQtyInvoiced();

		Check.assumeNotNull(qty, "qty not null");

		final I_C_UOM priceUOM = invoiceLine.getPrice_UOM();
		if (invoiceLine.getPrice_UOM_ID() <= 0)
		{
			return qty;
		}
		if (invoiceLine.getM_Product_ID() <= 0)
		{
			return qty;
		}

		final I_M_Product product = invoiceLine.getM_Product();
		if (product.getC_UOM_ID() <= 0)
		{
			return qty;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceLine);
		final BigDecimal qtyInPriceUOM = Services.get(IUOMConversionBL.class).convertFromProductUOM(ctx, product, priceUOM, qty);

		return qtyInPriceUOM;
	}

	@Override
	public IEditablePricingContext createPricingContext(final I_C_InvoiceLine invoiceLine)
	{
		final I_C_Invoice invoice = invoiceLine.getC_Invoice();
		final int priceListId = invoice.getM_PriceList_ID();

		final BigDecimal qtyInvoicedInPriceUOM = calculateQtyInvoicedInPriceUOM(invoiceLine);

		return createPricingContext(invoiceLine, priceListId, qtyInvoicedInPriceUOM);
	}

	public IEditablePricingContext createPricingContext(I_C_InvoiceLine invoiceLine,
			final int priceListId,
			final BigDecimal priceQty)
	{
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();

		final boolean isSOTrx = invoice.isSOTrx();

		final int productId = invoiceLine.getM_Product_ID();

		int bPartnerId = invoice.getC_BPartner_ID();

		final Timestamp date = invoice.getDateInvoiced();

		final IEditablePricingContext pricingCtx = Services.get(IPricingBL.class).createInitialContext(
				productId,
				bPartnerId,
				invoiceLine.getPrice_UOM_ID(),
				priceQty,
				isSOTrx);
		pricingCtx.setPriceDate(date);

		// 03152: setting the 'ol' to allow the subscription system to compute the right price
		pricingCtx.setReferencedObject(invoiceLine);

		pricingCtx.setM_PriceList_ID(priceListId);
		// PLV is only accurate if PL selected in header
		// metas: relay on M_PriceList_ID only, don't use M_PriceList_Version_ID
		// pricingCtx.setM_PriceList_Version_ID(orderLine.getM_PriceList_Version_ID());

		return pricingCtx;
	}

	@Override
	public void updateLineNetAmt(final I_C_InvoiceLine line, final BigDecimal qtyEntered)
	{
		if (qtyEntered != null)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(line);
			final I_C_Invoice invoice = line.getC_Invoice();
			final int priceListId = invoice.getM_PriceList_ID();

			//
			// We need to get the quantity in the pricing's UOM (if different)
			final BigDecimal convertedQty = calculateQtyInvoicedInPriceUOM(line);

			// this code has been borrowed from
			// org.compiere.model.CalloutOrder.amt
			final int stdPrecision = MPriceList.getStandardPrecision(ctx, priceListId);

			BigDecimal lineNetAmt = convertedQty.multiply(line.getPriceActual());

			if (lineNetAmt.scale() > stdPrecision)
			{
				lineNetAmt = lineNetAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP);
			}
			logger.info("LineNetAmt=" + lineNetAmt);
			line.setLineNetAmt(lineNetAmt);
		}
	}

	@Override
	public void updatePrices(final I_C_InvoiceLine invoiceLine)
	{
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

		pricingCtx.setManualPrice(invoiceLine.isManualPrice());

		if (pricingCtx.isManualPrice())
		{
			// Task 08908: do not calculate the prices in case the price is manually set
			return;
		}

		final IPricingResult pricingResult = Services.get(IPricingBL.class).calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			throw new ProductNotOnPriceListException(pricingCtx, invoiceLine.getLine());
		}

		//
		// PriceList
		final BigDecimal priceList = pricingResult.getPriceList();
		invoiceLine.setPriceList(priceList);

		invoiceLine.setPriceLimit(pricingResult.getPriceLimit());
		invoiceLine.setPrice_UOM_ID(pricingResult.getPrice_UOM_ID());

		invoiceLine.setPriceEntered(pricingResult.getPriceStd());
		invoiceLine.setPriceActual(pricingResult.getPriceStd());

		//
		// Discount

		invoiceLine.setDiscount(pricingResult.getDiscount());

		//
		// Calculate PriceActual from PriceEntered and Discount
		calculatePriceActual(invoiceLine, pricingResult.getPrecision());

		invoiceLine.setPrice_UOM_ID(pricingResult.getPrice_UOM_ID()); //

	}

	private void calculatePriceActual(final I_C_InvoiceLine invoiceLine, final int precision)
	{
		final BigDecimal discount = invoiceLine.getDiscount();
		final BigDecimal priceEntered = invoiceLine.getPriceEntered();

		BigDecimal priceActual;
		if (priceEntered.signum() == 0)
		{
			priceActual = priceEntered;
		}
		else
		{
			final int precisionToUse;
			if (precision >= 0)
			{
				precisionToUse = precision;
			}
			else
			{
				final I_C_Invoice invoice = invoiceLine.getC_Invoice();

				precisionToUse = invoice.getM_PriceList().getPricePrecision();
			}

			priceActual = subtractDiscount(priceEntered, discount, precisionToUse);
		}

		invoiceLine.setPriceActual(priceActual);
	}

	private BigDecimal subtractDiscount(final BigDecimal baseAmount, final BigDecimal discount, final int precision)
	{
		BigDecimal multiplier = Env.ONEHUNDRED.subtract(discount);
		multiplier = multiplier.divide(Env.ONEHUNDRED, precision * 3, RoundingMode.HALF_UP);

		final BigDecimal result = baseAmount.multiply(multiplier).setScale(precision, RoundingMode.HALF_UP);
		return result;
	}
}
