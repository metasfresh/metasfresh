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

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;

import de.metas.invoicecandidate.api.IInvoiceCandidateInOutLineToUpdate;

public class InvoiceCandidateInOutLineToUpdate implements IInvoiceCandidateInOutLineToUpdate
{
	private final I_C_InvoiceCandidate_InOutLine iciol;
	private final BigDecimal qtyInvoiced;

	public InvoiceCandidateInOutLineToUpdate(final I_C_InvoiceCandidate_InOutLine iciol, final BigDecimal qtyInvoiced)
	{
		super();

		Check.assumeNotNull(iciol, "iciol not null");
		this.iciol = iciol;

		Check.assumeNotNull(qtyInvoiced, "qtyInvoiced not null");
		this.qtyInvoiced = qtyInvoiced;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public I_C_InvoiceCandidate_InOutLine getC_InvoiceCandidate_InOutLine()
	{
		return iciol;
	}

	@Override
	public BigDecimal getQtyInvoiced()
	{
		return qtyInvoiced;
	}

}
