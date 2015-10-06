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


import java.util.Date;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;

import de.metas.invoicecandidate.api.IInvoiceCandidateQuery;

/* package */class InvoiceCandidateQuery implements IInvoiceCandidateQuery
{
	private int Bill_BPartner_ID = -1;
	private Date DateToInvoice = null;
	private String headerAggregationKey = null;
	private int excludeC_Invoice_Candidate_ID = -1;
	private int maxManualC_Invoice_Candidate_ID = -1;
	private Boolean processed = null;

	@Override
	public InvoiceCandidateQuery copy()
	{
		final InvoiceCandidateQuery newQuery = new InvoiceCandidateQuery();
		newQuery.Bill_BPartner_ID = Bill_BPartner_ID;
		newQuery.DateToInvoice = DateToInvoice == null ? null : (Date)DateToInvoice.clone();
		newQuery.headerAggregationKey = headerAggregationKey;
		newQuery.excludeC_Invoice_Candidate_ID = excludeC_Invoice_Candidate_ID;
		newQuery.maxManualC_Invoice_Candidate_ID = maxManualC_Invoice_Candidate_ID;
		newQuery.processed = processed;
		return newQuery;
	}

	@Override
	public Object clone()
	{
		return copy();
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(Bill_BPartner_ID)
				.append(DateToInvoice)
				.append(headerAggregationKey)
				.append(excludeC_Invoice_Candidate_ID)
				.append(maxManualC_Invoice_Candidate_ID)
				.append(processed)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final InvoiceCandidateQuery other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(Bill_BPartner_ID, other.Bill_BPartner_ID)
				.append(DateToInvoice, other.DateToInvoice)
				.append(headerAggregationKey, other.headerAggregationKey)
				.append(excludeC_Invoice_Candidate_ID, other.excludeC_Invoice_Candidate_ID)
				.append(maxManualC_Invoice_Candidate_ID, other.maxManualC_Invoice_Candidate_ID)
				.append(processed, other.processed)
				.isEqual();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
	
	@Override
	public int getBill_BPartner_ID()
	{
		return Bill_BPartner_ID;
	}

	@Override
	public void setBill_BPartner_ID(int bill_BPartner_ID)
	{
		Bill_BPartner_ID = bill_BPartner_ID;
	}

	@Override
	public Date getDateToInvoice()
	{
		return DateToInvoice;
	}

	@Override
	public void setDateToInvoice(Date dateToInvoice)
	{
		DateToInvoice = dateToInvoice;
	}

	@Override
	public String getHeaderAggregationKey()
	{
		return headerAggregationKey;
	}

	@Override
	public void setHeaderAggregationKey(String headerAggregationKey)
	{
		this.headerAggregationKey = headerAggregationKey;
	}

	@Override
	public int getExcludeC_Invoice_Candidate_ID()
	{
		return excludeC_Invoice_Candidate_ID;
	}

	@Override
	public void setExcludeC_Invoice_Candidate_ID(int excludeC_Invoice_Candidate_ID)
	{
		this.excludeC_Invoice_Candidate_ID = excludeC_Invoice_Candidate_ID;
	}

	@Override
	public int getMaxManualC_Invoice_Candidate_ID()
	{
		return maxManualC_Invoice_Candidate_ID;
	}

	@Override
	public void setMaxManualC_Invoice_Candidate_ID(int maxManualC_Invoice_Candidate_ID)
	{
		this.maxManualC_Invoice_Candidate_ID = maxManualC_Invoice_Candidate_ID;
	}
	
	@Override
	public Boolean getProcessed()
	{
		return processed;
	}

	@Override
	public void setProcessed(Boolean processed)
	{
		this.processed = processed;
	}
}
