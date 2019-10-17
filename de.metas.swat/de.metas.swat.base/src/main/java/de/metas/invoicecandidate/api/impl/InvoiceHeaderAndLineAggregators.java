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

import java.util.Collection;
import java.util.HashMap;

import de.metas.aggregation.api.AggregationKey;
import de.metas.invoicecandidate.spi.IAggregator;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString(doNotUseGetters = true)
/* package */class InvoiceHeaderAndLineAggregators
{
	private final AggregationKey headerAggregationKey;
	@Getter
	private final InvoiceHeaderImplBuilder invoiceHeader;

	/** Map: C_Invoice_Candidate_Agg_ID to invoice line aggregator ({@link IAggregator}) */
	private final HashMap<Object, IAggregator> lineAggregators = new HashMap<>();

	public InvoiceHeaderAndLineAggregators(@NonNull final AggregationKey headerAggregationKey)
	{
		this.headerAggregationKey = headerAggregationKey;
		this.invoiceHeader = InvoiceHeaderImpl.builder();
	}

	public IAggregator getLineAggregator(final int invoiceCandidateAggId)
	{
		final Object key = mkLineAggregatorKey(invoiceCandidateAggId);
		return lineAggregators.get(key);
	}

	public void setLineAggregator(final int invoiceCandidateAggId, final IAggregator lineAggregator)
	{
		Check.assumeNotNull(lineAggregator, "lineAggregator not null");
		final Object key = mkLineAggregatorKey(invoiceCandidateAggId);
		lineAggregators.put(key, lineAggregator);
	}

	private final Object mkLineAggregatorKey(final int invoiceCandidateAggId)
	{
		return invoiceCandidateAggId > 0 ? invoiceCandidateAggId : 0;
	}

	public Collection<IAggregator> getLineAggregators()
	{
		return lineAggregators.values();
	}

}
