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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.InvoiceCandidateInOutLineToUpdate;
import de.metas.money.Money;
import de.metas.product.ProductPrice;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.Tax;
import de.metas.util.lang.Percent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

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

// The excludes are here because they were this way in the former code. I'm not really sure that we really "must" include e.g. "C_PaymentTerm_ID"..
@EqualsAndHashCode(exclude = { "activityID", "tax", "lineNo", "invoiceLineAttributes", "iciolsToUpdate", "C_PaymentTerm_ID" })
@ToString(doNotUseGetters = true)
/* package */ class InvoiceLineImpl implements IInvoiceLineRW
{
	private int M_Product_ID;
	private int C_Charge_ID;
	private int C_OrderLine_ID;

	@Getter @Setter
	private StockQtyAndUOMQty qtysToInvoice;

	@Getter @Setter
	private ProductPrice priceActual;

	@Getter @Setter
	private ProductPrice priceEntered;

	@Getter @Setter
	private Percent discount;

	@Getter @Setter
	private Money netLineAmt;

	private String description;
	private Collection<Integer> iciolIds = new TreeSet<>();
	private int activityID;
	private Tax tax;
	private boolean printed = true;
	private int lineNo = 0;
	private List<IInvoiceLineAttribute> invoiceLineAttributes = ImmutableList.of();
	private final List<InvoiceCandidateInOutLineToUpdate> iciolsToUpdate = new ArrayList<>();
	private int C_PaymentTerm_ID;
	@Getter @Setter
	private int C_VAT_Code_ID;

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
	public final void addQtysToInvoice(@NonNull final StockQtyAndUOMQty qtysToInvoiceToAdd)
	{
		final StockQtyAndUOMQty qtysToInvoiceOld = getQtysToInvoice();
		final StockQtyAndUOMQty qtysToInvoiceNew;
		if (qtysToInvoiceOld == null)
		{
			qtysToInvoiceNew = qtysToInvoiceToAdd;
		}
		else
		{
			qtysToInvoiceNew = qtysToInvoiceOld.add(qtysToInvoiceToAdd);
		}
		setQtysToInvoice(qtysToInvoiceNew);

	}


	@Nullable
	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public void setDescription(@Nullable final String description)
	{
		this.description = description;
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
	public void setC_Activity_ID(final int activityID)
	{
		this.activityID = activityID;
	}

	@Override
	public Tax getC_Tax()
	{
		return tax;
	}

	@Override
	public void setC_Tax(final Tax tax)
	{
		this.tax = tax;
	}

	@Override
	public boolean isPrinted()
	{
		return printed;
	}

	@Override
	public void setPrinted(final boolean printed)
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
	public void setInvoiceLineAttributes(@NonNull final List<IInvoiceLineAttribute> invoiceLineAttributes)
	{
		this.invoiceLineAttributes = ImmutableList.copyOf(invoiceLineAttributes);
	}

	@Override
	public List<IInvoiceLineAttribute> getInvoiceLineAttributes()
	{
		return invoiceLineAttributes;
	}

	@Override
	public List<InvoiceCandidateInOutLineToUpdate> getInvoiceCandidateInOutLinesToUpdate()
	{
		return iciolsToUpdate;
	}

	@Override
	public int getC_PaymentTerm_ID()
	{
		return C_PaymentTerm_ID;
	}

	@Override
	public void setC_PaymentTerm_ID(final int paymentTermId)
	{
		C_PaymentTerm_ID = paymentTermId;
	}

}
