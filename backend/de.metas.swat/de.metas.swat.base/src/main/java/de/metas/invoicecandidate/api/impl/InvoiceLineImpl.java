package de.metas.invoicecandidate.api.impl;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_Tax;

import com.google.common.collect.ImmutableSet;

import de.metas.invoicecandidate.api.IInvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.api.IInvoiceLineRW;

/**
 * Default (bean) implementation for {@link IInvoiceLineRW}.
 *
 * NOTE to developer: if you want to add a new field:
 * <ul>
 * <li>add private field, getter and setter methods
 * <li>change {@link #equals(Object)} to consider the new field
 * <li>change {@link #hashCode()} to consider the new field
 * </ul>
 *
 * @author tsa
 *
 */
/* package */ class InvoiceLineImpl implements IInvoiceLineRW
{
	private int M_Product_ID;
	private int C_Charge_ID;
	private int C_OrderLine_ID;
	private BigDecimal qtyToInvoice;
	private BigDecimal priceActual;
	private BigDecimal priceEntered;
	private BigDecimal discount;
	private BigDecimal netLineAmt;
	private String description;
	private Collection<Integer> iciolIds = new TreeSet<>();
	private int activityID;
	private I_C_Tax tax;
	private boolean printed = true;
	private int lineNo = 0;
	private Set<IInvoiceLineAttribute> invoiceLineAttributes = Collections.emptySet();
	private List<IInvoiceCandidateInOutLineToUpdate> iciolsToUpdate = new ArrayList<>();
	private int C_PaymentTerm_ID;

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(C_Charge_ID)
				.append(C_OrderLine_ID)
				.append(M_Product_ID)
				.append(description)
				.append(netLineAmt)
				.append(priceActual)
				.append(priceEntered)
				.append(discount)
				.append(qtyToInvoice)
				.append(printed)
				.append(iciolIds)
				//.append(iciolsToUpdate)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final InvoiceLineImpl other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(C_Charge_ID, other.C_Charge_ID)
				.append(C_OrderLine_ID, other.C_OrderLine_ID)
				.append(M_Product_ID, other.M_Product_ID)
				.append(description, other.description)
				.append(netLineAmt, other.netLineAmt)
				.append(priceActual, other.priceActual)
				.append(priceEntered, other.priceEntered)
				.append(discount, other.discount)
				.append(qtyToInvoice, other.qtyToInvoice)
				.append(printed, other.printed)
				.append(iciolIds, other.iciolIds)
				//.append(iciolsToUpdate, other.iciolsToUpdate)
				.isEqual();
	}

	@Override
	public int getM_Product_ID()
	{
		return M_Product_ID;
	}

	@Override
	public int getC_Charge_ID()
	{
		return C_Charge_ID;
	}

	@Override
	public int getC_OrderLine_ID()
	{
		return C_OrderLine_ID;
	}

	@Override
	public BigDecimal getQtyToInvoice()
	{
		return qtyToInvoice;
	}

	@Override
	public BigDecimal getPriceActual()
	{
		return priceActual;
	}

	@Override
	public BigDecimal getNetLineAmt()
	{
		return netLineAmt;
	}

	@Override
	public void setM_Product_ID(final int m_Product_ID)
	{
		M_Product_ID = m_Product_ID;
	}

	@Override
	public void setC_Charge_ID(final int c_Charge_ID)
	{
		C_Charge_ID = c_Charge_ID;
	}

	@Override
	public void setC_OrderLine_ID(final int c_OrderLine_ID)
	{
		C_OrderLine_ID = c_OrderLine_ID;
	}

	@Override
	public void setQtyToInvoice(final BigDecimal qtyToInvoice)
	{
		this.qtyToInvoice = qtyToInvoice;
	}

	@Override
	public final void addQtyToInvoice(final BigDecimal qtyToInvoiceToAdd)
	{
		Check.assumeNotNull(qtyToInvoiceToAdd, "qtyToInvoiceToAdd not null");
		if (qtyToInvoiceToAdd.signum() == 0)
		{
			return; // nothing to add
		}

		final BigDecimal qtyToInvoiceOld = getQtyToInvoice();
		final BigDecimal qtyToInvoiceNew;
		if (qtyToInvoiceOld == null)
		{
			qtyToInvoiceNew = qtyToInvoiceToAdd;
		}
		else
		{
			qtyToInvoiceNew = qtyToInvoiceOld.add(qtyToInvoiceToAdd);
		}

		setQtyToInvoice(qtyToInvoiceNew);
	}

	@Override
	public void setPriceActual(final BigDecimal priceActual)
	{
		Check.assumeNotNull(priceActual, "priceActual not null");
		this.priceActual = priceActual;
	}

	@Override
	public void setNetLineAmt(final BigDecimal netLineAmt)
	{
		Check.assumeNotNull(netLineAmt, "netLineAmt not null");
		this.netLineAmt = netLineAmt;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public void setDescription(final String description)
	{
		this.description = description;
	}

	@Override
	public BigDecimal getPriceEntered()
	{
		return priceEntered;
	}

	@Override
	public void setPriceEntered(final BigDecimal priceEntered)
	{
		this.priceEntered = priceEntered;
	}

	@Override
	public BigDecimal getDiscount()
	{
		return discount;
	}

	@Override
	public void setDiscount(final BigDecimal discount)
	{
		this.discount = discount;
	}

	@Override
	public Collection<Integer> getC_InvoiceCandidate_InOutLine_IDs()
	{
		return iciolIds;
	}

	@Override
	public void negateAmounts()
	{
		setPriceActual(getPriceActual().negate());
		setNetLineAmt(getNetLineAmt().negate());
	}

	@Override
	public int getC_Activity_ID()
	{
		return activityID;
	}

	@Override
	public void setC_Activity_ID(int activityID)
	{
		this.activityID = activityID;
	}

	@Override
	public I_C_Tax getC_Tax()
	{
		return tax;
	}

	@Override
	public void setC_Tax(I_C_Tax tax)
	{
		this.tax = tax;
	}

	@Override
	public boolean isPrinted()
	{
		return printed;
	}

	@Override
	public void setPrinted(boolean printed)
	{
		this.printed = printed;
	}

	@Override
	public int getLineNo()
	{
		return lineNo;
	}

	@Override
	public void setLineNo(final int lineNo)
	{
		this.lineNo = lineNo;
	}

	@Override
	public void setInvoiceLineAttributes(final Set<IInvoiceLineAttribute> invoiceLineAttributes)
	{
		this.invoiceLineAttributes = ImmutableSet.copyOf(invoiceLineAttributes);
	}

	@Override
	public Set<IInvoiceLineAttribute> getInvoiceLineAttributes()
	{
		return invoiceLineAttributes;
	}

	@Override
	public List<IInvoiceCandidateInOutLineToUpdate> getInvoiceCandidateInOutLinesToUpdate()
	{
		return iciolsToUpdate;
	}

	@Override
	public int getC_PaymentTerm_ID()
	{
		return C_PaymentTerm_ID;
	}

	@Override
	public void setC_PaymentTerm_ID(int paymentTermId)
	{
		C_PaymentTerm_ID = paymentTermId;
	}
}
