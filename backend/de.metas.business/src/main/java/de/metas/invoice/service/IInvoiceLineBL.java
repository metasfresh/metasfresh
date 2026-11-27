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
import de.metas.location.CountryId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxQuery;
import de.metas.util.ISingletonService;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;
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
	 * The tax query {@link #setTaxBasedOnShipment(org.compiere.model.I_C_InvoiceLine, String)} uses for an invoice line of the given shipment:
	 * the shipment's org, movement date, delivery location and SO flag, plus the given ship-from country, warehouse and tax category.
	 * Shared with callers that must predict that tax before the invoice line exists.
	 */
	TaxQuery createShipmentTaxQuery(I_M_InOut shipment, CountryId countryFromId, @Nullable WarehouseId warehouseId, @Nullable TaxCategoryId taxCategoryId);

	/**
	 * The tax query of the given re-tax inputs (see {@link IInvoiceBL#getReTaxInput}) and tax category; {@link #createShipmentTaxQuery} builds the same query.
	 */
	TaxQuery createTaxQuery(InvoiceLineReTaxInput reTaxInput, @Nullable TaxCategoryId taxCategoryId);

	/**
	 * The tax query with which {@link #setTaxForInvoiceLine} re-taxes the given line: the given inputs and the line's tax category,
	 * else (looked up, not set on the line) the one of its charge, pricing or order line ({@link #getTaxCategoryId}); read-only.
	 *
	 * @return empty if the re-tax leaves the line alone (no product)
	 */
	Optional<TaxQuery> createReTaxQuery(org.compiere.model.I_C_InvoiceLine il, InvoiceLineReTaxInput reTaxInput);

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

	void recomputePriceActual(I_C_InvoiceLine invoiceLine);

	/**
	 * Invoke the pricing engine to update the given invoiceLine's prices
	 */
	void updatePrices(I_C_InvoiceLine invoiceLine);

	/**
	 * Sets the tax of the given re-tax inputs (see {@link IInvoiceBL#getReTaxInput}) on the given line, without saving it.
	 *
	 * @return true if the line's tax changed
	 */
	boolean setTaxForInvoiceLine(org.compiere.model.I_C_InvoiceLine il, InvoiceLineReTaxInput reTaxInput);

	Quantity getQtyEnteredInStockUOM(I_C_InvoiceLine invoiceLine);

	Quantity getQtyInvoicedStockUOM(org.compiere.model.I_C_InvoiceLine invoiceLine);
}
