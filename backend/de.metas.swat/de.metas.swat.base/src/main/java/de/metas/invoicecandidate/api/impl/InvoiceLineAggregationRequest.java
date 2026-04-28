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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.invoicecandidate.api.IInvoiceLineAggregationRequest;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.util.lang.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Default immutable implementation of {@link IInvoiceLineAggregationRequest}.
 *
 * @author tsa
 *
 */
/* package */final class InvoiceLineAggregationRequest implements IInvoiceLineAggregationRequest
{
	public static Builder builder()
	{
		return new Builder();
	}

	private final I_C_Invoice_Candidate invoiceCandidate;
	private final I_C_InvoiceCandidate_InOutLine iciol;
	private final ImmutableList<Object> aggregationKeyElements;
	private final ImmutableSet<IInvoiceLineAttribute> attributesFromInoutLines;
	private final boolean allocateRemainingQty;

	private InvoiceLineAggregationRequest(@NonNull final Builder builder)
	{
		this.invoiceCandidate = builder.invoiceCandidate;
		Check.assumeNotNull(invoiceCandidate, "invoiceCandidate not null");

		this.iciol = builder.iciol;
		if (iciol != null && iciol.getC_Invoice_Candidate_ID() != invoiceCandidate.getC_Invoice_Candidate_ID())
		{
			throw new IllegalArgumentException("Invalid " + iciol + ". C_Invoice_Candidate_ID shall match."
					+ "\n Expected invoice candidate: " + invoiceCandidate);
		}

		this.aggregationKeyElements = ImmutableList.copyOf(builder.aggregationKeyElements);
		this.attributesFromInoutLines = ImmutableSet.copyOf(builder.attributesFromInoutLines);
		this.allocateRemainingQty = builder.allocateRemainingQty;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public I_C_Invoice_Candidate getC_Invoice_Candidate()
	{
		return invoiceCandidate;
	}

	@Override
	public I_C_InvoiceCandidate_InOutLine getC_InvoiceCandidate_InOutLine()
	{
		return iciol;
	}

	@Override
	public List<Object> getLineAggregationKeyElements()
	{
		return aggregationKeyElements;
	}

	@Override
	public Set<IInvoiceLineAttribute> getAttributesFromInoutLines()
	{
		return attributesFromInoutLines;
	}

	@Override
	public boolean isAllocateRemainingQty()
	{
		return this.allocateRemainingQty;
	}

	/**
	 * {@link InvoiceLineAggregationRequest} builder.
	 */
	public static class Builder
	{
		private I_C_Invoice_Candidate invoiceCandidate;
		private I_C_InvoiceCandidate_InOutLine iciol;
		private final List<Object> aggregationKeyElements = new ArrayList<>();
		private final Set<IInvoiceLineAttribute> attributesFromInoutLines = new LinkedHashSet<>();
		@Setter
		private boolean allocateRemainingQty = false;

		/* package */ InvoiceLineAggregationRequest build()
		{
		 	return new InvoiceLineAggregationRequest(this);
		}

		private Builder()
		{
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public Builder setC_Invoice_Candidate(final I_C_Invoice_Candidate invoiceCandidate)
		{
			this.invoiceCandidate = invoiceCandidate;
			return this;
		}

		public Builder setC_InvoiceCandidate_InOutLine(final I_C_InvoiceCandidate_InOutLine iciol)
		{
			this.iciol = iciol;
			return this;
		}

		/**
		 * Adds an additional element to be considered part of the line aggregation key.
		 * <p>
		 * NOTE: basically this shall be always empty because everything which is related to line aggregation
		 * shall be configured from aggregation definition,
		 * but we are also leaving this door open in case we need to implement some quick/hot fixes.
		 *
		 * @deprecated This method will be removed because we shall go entirely with standard aggregation definition.
		 */
		@Deprecated
		public Builder addLineAggregationKeyElement(@NonNull final Object aggregationKeyElement)
		{
			aggregationKeyElements.add(aggregationKeyElement);
			return this;
		}

		public void addInvoiceLineAttributes(@NonNull final Collection<IInvoiceLineAttribute> invoiceLineAttributes)
		{
			this.attributesFromInoutLines.addAll(invoiceLineAttributes);
		}

	}
}
