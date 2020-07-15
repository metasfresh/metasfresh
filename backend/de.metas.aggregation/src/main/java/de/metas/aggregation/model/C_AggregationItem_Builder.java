package de.metas.aggregation.model;

import static de.metas.util.Check.isEmpty;

import javax.annotation.Nullable;

import org.adempiere.ad.table.api.AdTableId;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_AD_Column;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Builder class for {@link I_C_AggregationItem}
 *
 * @author tsa
 *
 */
public class C_AggregationItem_Builder
{
	private final C_Aggregation_Builder parentBuilder;
	private final AdTableId adTableId;

	private I_C_Aggregation aggregation;
	private String type;
	private I_AD_Column adColumn;
	private I_C_Aggregation includedAggregation;
	private I_C_Aggregation_Attribute attribute;
	private String includeLogic;

	public C_AggregationItem_Builder(
			@NonNull final C_Aggregation_Builder parentBuilder,
			@NonNull final AdTableId adTableId)
	{
		this.parentBuilder = parentBuilder;
		this.adTableId = adTableId;
	}

	public I_C_AggregationItem build()
	{
		final I_C_Aggregation aggregation = getC_Aggregation();
		final I_C_AggregationItem aggregationItem = InterfaceWrapperHelper.newInstance(I_C_AggregationItem.class, aggregation);
		aggregationItem.setC_Aggregation(aggregation);
		aggregationItem.setType(getType());
		aggregationItem.setAD_Column(getAD_Column());
		aggregationItem.setIncluded_Aggregation(getIncluded_Aggregation());
		aggregationItem.setC_Aggregation_Attribute(getC_Aggregation_Attribute());
		aggregationItem.setIncludeLogic(getIncludeLogic());
		InterfaceWrapperHelper.save(aggregationItem);
		return aggregationItem;
	}

	public C_Aggregation_Builder end()
	{
		return parentBuilder;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public C_AggregationItem_Builder setC_Aggregation(final I_C_Aggregation aggregation)
	{
		this.aggregation = aggregation;
		return this;
	}

	private I_C_Aggregation getC_Aggregation()
	{
		Check.assumeNotNull(aggregation, "aggregation not null");
		return aggregation;
	}

	public C_AggregationItem_Builder setType(final String type)
	{
		this.type = type;
		return this;
	}

	private String getType()
	{
		Check.assumeNotEmpty(type, "type not empty");
		return type;
	}

	public C_AggregationItem_Builder setAD_Column(@Nullable final String columnName)
	{
		if (isEmpty(columnName, true))
		{
			this.adColumn = null;
		}
		else
		{

			this.adColumn = Services.get(IADTableDAO.class).retrieveColumn(adTableId, columnName);
		}
		return this;
	}

	private I_AD_Column getAD_Column()
	{
		return adColumn;
	}

	public C_AggregationItem_Builder setIncluded_Aggregation(final I_C_Aggregation includedAggregation)
	{
		this.includedAggregation = includedAggregation;
		return this;
	}

	private I_C_Aggregation getIncluded_Aggregation()
	{
		return includedAggregation;
	}

	public C_AggregationItem_Builder setIncludeLogic(final String includeLogic)
	{
		this.includeLogic = includeLogic;
		return this;
	}

	private String getIncludeLogic()
	{
		return includeLogic;
	}

	public C_AggregationItem_Builder setC_Aggregation_Attribute(I_C_Aggregation_Attribute attribute)
	{
		this.attribute = attribute;
		return this;
	}

	private I_C_Aggregation_Attribute getC_Aggregation_Attribute()
	{
		return this.attribute;
	}
}
