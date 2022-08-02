package de.metas.invoice.service;

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

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.ISingletonService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

/**
 *
 * Note that many methods you might be searching here might be in {@link IInvoiceBL}.
 *
 */
public interface IInvoiceLineBL extends ISingletonService
{

	void setTaxAmtInfo(Properties ctx, I_C_InvoiceLine il, String getTrxName);

	/**
	 * Retrieves the il's C_Tax_ID if the il has an inout line. MInvoiceLine.getTax() only uses the bill location date and address, which is not correct (for us).
	 * <p/>
	 * <b>IMPORTANT:</b> if the il has M_InoutLine_ID<=0, the method does nothing!
	 */
	boolean setTaxBasedOnShipment(org.compiere.model.I_C_InvoiceLine il, String getTrxName);

	/**
	 * @return true if invoice line's prices are locked (i.e. should be the same as linked OrderLine)
	 */
	boolean isPriceLocked(I_C_InvoiceLine invoiceLine);

	/**
	 * Retrieves the the {@code M_PriceList_Version} for the {@code DateInvoiced} and {@code M_PriceList} values of the given {@code invoiceLine}'s invoice. Then retrieves the {@code M_ProductPrice}
	 * for the invoiceLine's {@code M_Product_ID} and the M_PriceList_Version. Finally returns that pp's {@code C_TaxCategory_ID}.
	 * <p>
	 * This method assumes that
	 * <ul>
	 * <li>invoiceLine.getM_Product_ID() > 0
	 * <li>invoiceLine.getInvoice().getM_PriceList_ID() > 0
	 * <li>a M_PriceList_Version exists for the DateInvoiced and M_PriceList if the invoiceLine's invoice
	 * <li>a M_ProductPrice exists for the invoiceLine's product and the PLV
	 * </ul>
	 *
	 * @return C_TaxCategory_ID
	 * @see de.metas.util.Check#assume(boolean, String, Object...)
	 */
	TaxCategoryId getTaxCategoryId(org.compiere.model.I_C_InvoiceLine invoiceLine);

	IEditablePricingContext createPricingContext(I_C_InvoiceLine invoiceLine);

	/**
	 * Uses the given <code>invoiceLine</code>'s <code>QtyInvoiced</code>, <code>C_UOM</code> and <code>Price_UOM</code> to compute and set the given line's <code>QtyInvoicedInPriceUOM</code>.
	 */
	void setQtyInvoicedInPriceUOM(I_C_InvoiceLine invoiceLine);

	/**
	 * Update the line net amount. Mainly introduced for manual invoices
	 */
	void updateLineNetAmt(I_C_InvoiceLine line, BigDecimal qtyEntered);

	/**
	 * Invoke the pricing engine to update the given invoiceLine's prices
	 */
	void updatePrices(I_C_InvoiceLine invoiceLine);

	boolean setTaxForInvoiceLine(org.compiere.model.I_C_InvoiceLine il, OrgId orgId, Timestamp taxDate, CountryId countryFromId, BPartnerLocationAndCaptureId taxPartnerLocationId, boolean isSOTrx);

	Quantity getQtyEnteredInStockUOM(I_C_InvoiceLine invoiceLine);

	Quantity getQtyInvoicedStockUOM(I_C_InvoiceLine invoiceLine);
}
