package de.metas.aggregation.callout;

/*
 * #%L
 * de.metas.aggregation
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

import de.metas.aggregation.api.IAggregationDAO;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.aggregation.model.I_C_Aggregation_Attribute;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.MinimalColumnInfo;

@Callout(I_C_AggregationItem.class)
public class C_AggregationItem
{
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	@CalloutMethod(columnNames = I_C_AggregationItem.COLUMNNAME_Included_Aggregation_ID)
	public void onIncluded_Aggregation_ID(final I_C_AggregationItem aggregationItem, final ICalloutField field)
	{
		if (aggregationItem.getIncluded_Aggregation_ID() <= 0)
		{
			return;
		}

		//
		// Make sure we are not introducing cycles
		Services.get(IAggregationDAO.class).checkIncludedAggregationCycles(aggregationItem);

		final I_C_Aggregation includedAggregation = aggregationItem.getIncluded_Aggregation();
		aggregationItem.setEntityType(includedAggregation.getEntityType());
	}

	@CalloutMethod(columnNames = I_C_AggregationItem.COLUMNNAME_AD_Column_ID)
	public void onAD_Column_ID(final I_C_AggregationItem aggregationItem, final ICalloutField field)
	{
		final AdColumnId adColumnId = AdColumnId.ofRepoIdOrNull(aggregationItem.getAD_Column_ID());
		if (adColumnId == null)
		{
			return;
		}

		//
		final MinimalColumnInfo column = adTableDAO.getMinimalColumnInfo(adColumnId);
		aggregationItem.setEntityType(column.getEntityType());
	}

	@CalloutMethod(columnNames = I_C_AggregationItem.COLUMNNAME_C_Aggregation_Attribute_ID)
	public void onC_Aggregation_Attribute_ID(final I_C_AggregationItem aggregationItem, final ICalloutField field)
	{
		if (aggregationItem.getC_Aggregation_Attribute_ID() <= 0)
		{
			return;
		}

		//
		final I_C_Aggregation_Attribute attribute = aggregationItem.getC_Aggregation_Attribute();
		aggregationItem.setEntityType(attribute.getEntityType());
	}

}
