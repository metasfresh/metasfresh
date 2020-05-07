package de.metas.invoicecandidate.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.compiere.model.I_C_Tax;

/**
 * Invoice Line predecessor which is returned by {@link IAggregationBL#aggregate()}.
 *
 * One or more invoice candidates are aggregated in one instance of this class
 */
public interface IInvoiceLineRW
{
	public BigDecimal getNetLineAmt();

	public void setNetLineAmt(BigDecimal newLineAmt);

	public BigDecimal getPriceActual();

	public void setPriceActual(BigDecimal priceActual);

	public BigDecimal getQtyToInvoice();

	public void setQtyToInvoice(BigDecimal qtyToInvoice);

	void addQtyToInvoice(BigDecimal qtyToInvoiceToAdd);

	public int getC_OrderLine_ID();

	public void setC_OrderLine_ID(int C_OrderLine_ID);

	public int getC_Charge_ID();

	public void setC_Charge_ID(int C_Charge_ID);

	public int getM_Product_ID();

	public void setM_Product_ID(int M_Product_ID);

	public BigDecimal getPriceEntered();

	public void setPriceEntered(BigDecimal priceEntered);

	public BigDecimal getDiscount();

	public void setDiscount(BigDecimal discount);

	// 03439 add description
	public String getDescription();

	public void setDescription(String description);

	// end of 03439 add description

	/**
	 *
	 * @return returns a mutable collection. never returns <code>null</code>.
	 */
	public Collection<Integer> getC_InvoiceCandidate_InOutLine_IDs();

	/**
	 * Negate PriceActual and LineNetAmount
	 */
	public void negateAmounts();

	// 07442
	// add tax and activity
	public int getC_Activity_ID();

	public void setC_Activity_ID(int activityID);

	public I_C_Tax getC_Tax();

	public void setC_Tax(I_C_Tax tax);

	/**
	 * @return true if this invoice line shall be printed
	 */
	boolean isPrinted();

	void setPrinted(final boolean printed);

	int getLineNo();

	void setLineNo(int lineNo);

	void setInvoiceLineAttributes(Set<IInvoiceLineAttribute> invoiceLineAttributes);

	/** @return product attributes */
	Set<IInvoiceLineAttribute> getInvoiceLineAttributes();

	List<IInvoiceCandidateInOutLineToUpdate> getInvoiceCandidateInOutLinesToUpdate();

	public int getC_PaymentTerm_ID();

	public void setC_PaymentTerm_ID(int paymentTermId);
}
