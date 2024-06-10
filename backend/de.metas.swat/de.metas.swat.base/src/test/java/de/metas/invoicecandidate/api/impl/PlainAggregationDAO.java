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

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;

import java.util.List;

public class PlainAggregationDAO extends AggregationDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	private I_C_Invoice_Candidate_Agg defaultAgg = null;

	@Override
	public I_C_Invoice_Candidate_Agg retrieveAggregate(@NonNull I_C_Invoice_Candidate ic)
	{
		if (defaultAgg != null)
		{
			return defaultAgg;
		}
		
		final List<I_C_Invoice_Candidate_Agg> list = db.getRecords(I_C_Invoice_Candidate_Agg.class);
		if (list.isEmpty())
		{
			return null;
		}
		return list.get(0);
	}

	public I_C_Invoice_Candidate_Agg getDefaultAgg()
	{
		return defaultAgg;
	}

	public void setDefaultAgg(I_C_Invoice_Candidate_Agg defaultAgg)
	{
		this.defaultAgg = defaultAgg;
	}
}
