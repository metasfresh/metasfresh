package de.metas.materialtracking.model.agg.key.impl;

/*
 * #%L
 * de.metas.materialtracking
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.aggregation.api.AbstractAggregationKeyBuilder;
import de.metas.aggregation.api.AggregationId;
import de.metas.aggregation.api.AggregationKey;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * AggregationKey value handler for Invoice Candidates in Material Tracking
 *
 * @author al
 */
public class MaterialTrackingICHeaderAggregationKeyBuilder_OLD extends AbstractAggregationKeyBuilder<I_C_Invoice_Candidate>
{
	private static final List<String> columnNames = Arrays.asList(
			de.metas.materialtracking.model.I_C_Invoice_Candidate.COLUMNNAME_M_Material_Tracking_ID
			);

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getTableName()
	{
		return I_C_Invoice_Candidate.Table_Name;
	}

	@Override
	public List<String> getDependsOnColumnNames()
	{
		return columnNames;
	}

	@Override
	public AggregationKey buildAggregationKey(I_C_Invoice_Candidate model)
	{
		final List<Object> keyValues = extractKeyValues(model);
		final ArrayKey key = Util.mkKey(keyValues.toArray());
		final AggregationId aggregationId = null;
		return new AggregationKey(key, aggregationId);
	}

	private final List<Object> extractKeyValues(final I_C_Invoice_Candidate ic)
	{
		final List<Object> values = new ArrayList<>();

		final de.metas.materialtracking.model.I_C_Invoice_Candidate icExt = InterfaceWrapperHelper.create(ic, de.metas.materialtracking.model.I_C_Invoice_Candidate.class);

		//
		// Bind the Material Tracking ID for the reference of the Invoice Candidate
		values.add(icExt.getM_Material_Tracking_ID());

		return values;
	}
}
