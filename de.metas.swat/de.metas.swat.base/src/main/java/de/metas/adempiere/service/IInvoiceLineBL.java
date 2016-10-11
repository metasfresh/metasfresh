package de.metas.adempiere.service;

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
import java.util.Properties;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.util.ISingletonService;
import org.compiere.model.MInvoiceLine;

import de.metas.adempiere.model.I_C_InvoiceLine;

/**
 *
 * Note that many methods you might be searching here might be in {@link IInvoiceBL}.
 *
 */
public interface IInvoiceLineBL extends ISingletonService
{

	void setTaxAmtInfo(Properties ctx, I_C_InvoiceLine il, String getTrxName);

	/**
	 * Retrieves the il's C_Tax_ID if the il has an inout line. {@link MInvoiceLine#getTax()} only uses the bill location date and address, which is not correct (for us).
	 * <p/>
	 * <b>IMPORTANT:</b> if the il has M_InoutLine_ID<=0, the method does nothing!
	 *
	 * @param ctx
	 * @param il
	 * @param getTrxName
	 */
	boolean setTax(Properties ctx, org.compiere.model.I_C_InvoiceLine il, String getTrxName);

	/**
	 * @param invoiceLine
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
	 * @param invoiceLine
	 * @return C_TaxCategory_ID
	 * @see org.adempiere.util.Check#assume(boolean, String, Object...)
	 */
	int getC_TaxCategory_ID(org.compiere.model.I_C_InvoiceLine invoiceLine);

	IEditablePricingContext createPricingContext(I_C_InvoiceLine invoiceLine);

	/**
	 * Uses the given <code>invoiceLine</code>'s <code>QtyInvoiced</code>, <code>C_UOM</code> and <code>Price_UOM</code> to compute and set the given line's <code>QtyInvoicedInPriceUOM</code>.
	 * <p>
	 * Note that this method makes use of {@link #calculatedQtyInPriceUOM(BigDecimal, I_C_InvoiceLine)}.
	 *
	 * @param invoiceLine
	 * @see #calculatedQtyInPriceUOM(BigDecimal, I_C_InvoiceLine)
	 */
	void setQtyInvoicedInPriceUOM(I_C_InvoiceLine invoiceLine);

	/**
	 * Update the line net amount. Mainly introduced for manual invoices
	 *
	 * @param line
	 * @param qtyEntered
	 */
	void updateLineNetAmt(I_C_InvoiceLine line, BigDecimal qtyEntered);

	void updatePrices(I_C_InvoiceLine invoiceLine);
}
