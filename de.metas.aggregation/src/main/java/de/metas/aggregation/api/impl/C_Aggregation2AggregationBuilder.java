package de.metas.aggregation.api.impl;

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


import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Column;

import de.metas.aggregation.api.IAggregation;
import de.metas.aggregation.api.IAggregationAttribute;
import de.metas.aggregation.api.IAggregationDAO;
import de.metas.aggregation.api.IAggregationItem;
import de.metas.aggregation.api.IAggregationItem.Type;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.aggregation.model.I_C_Aggregation_Attribute;
import de.metas.aggregation.model.X_C_AggregationItem;
import de.metas.aggregation.model.X_C_Aggregation_Attribute;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Creates {@link IAggregation} from {@link I_C_Aggregation} structure.
 *
 * @author tsa
 *
 */
/* package */class C_Aggregation2AggregationBuilder
{
	// services
	private final transient IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
	private final transient IAggregationDAO aggregationDAO = Services.get(IAggregationDAO.class);

	//
	// Parameters
	private I_C_Aggregation _rootAggregation;
	private String _tableName = null;

	//
	// Status
	private final Set<Integer> seenAggregationIds = new HashSet<>();

	public IAggregation build()
	{
		return createAggregation(getRootAggregation());
	}

	public C_Aggregation2AggregationBuilder setC_Aggregation(final I_C_Aggregation aggregation)
	{
		Check.assumeNotNull(aggregation, "aggregation not null");
		_rootAggregation = aggregation;
		_tableName = getRootAggregation().getAD_Table().getTableName();
		return this;
	}

	private final I_C_Aggregation getRootAggregation()
	{
		Check.assumeNotNull(_rootAggregation, "rootAggregation not null");
		return _rootAggregation;
	}

	private final String getTableName()
	{
		Check.assumeNotEmpty(_tableName, "_tableName not empty");
		return _tableName;
	}

	private final IAggregation createAggregation(final I_C_Aggregation aggregationDef)
	{
		Check.assumeNotNull(aggregationDef, "aggregationDef not null");

		// Avoid cycles (i.e. self including an aggregation, direct or indirect)
		if (!checkEligible(aggregationDef))
		{
			throw new AdempiereException("Aggregation cycle detected: " + aggregationDef);
		}

		final String tableName = aggregationDef.getAD_Table().getTableName();
		final List<I_C_AggregationItem> aggregationItemsDef = aggregationDAO.retrieveAllItems(aggregationDef);

		final LinkedHashMap<String, IAggregationItem> aggregationItems = createAggregationItems(aggregationItemsDef);

		//
		// Create and return the aggregation
		final IAggregation aggregation = new Aggregation(tableName, aggregationItems.values(), aggregationDef.getC_Aggregation_ID());
		return aggregation;
	}

	private final LinkedHashMap<String, IAggregationItem> createAggregationItems(final List<I_C_AggregationItem> aggregationItemsDef)
	{
		//
		// Create aggregation items
		final LinkedHashMap<String, IAggregationItem> aggregationItemsAll = new LinkedHashMap<>(aggregationItemsDef.size());
		for (final I_C_AggregationItem aggregationItemDef : aggregationItemsDef)
		{
			// Skip inactive items
			if (!aggregationItemDef.isActive())
			{
				continue;
			}

			final String itemType = aggregationItemDef.getType();
			final Map<String, IAggregationItem> aggregationItems;
			if (X_C_AggregationItem.TYPE_Column.equals(itemType))
			{
				final IAggregationItem aggregationItem = createAggregationItem_TypeColumn(aggregationItemDef);
				aggregationItems = aggregationItem == null ? null : Collections.singletonMap(aggregationItem.getId(), aggregationItem);
			}
			else if (X_C_AggregationItem.TYPE_IncludedAggregation.equals(itemType))
			{
				aggregationItems = createAggregationItem_TypeIncludedAggregation(aggregationItemDef);
			}
			else if (X_C_AggregationItem.TYPE_Attribute.equals(itemType))
			{
				aggregationItems = createAggregationItem_TypeAttribute(aggregationItemDef);
			}
			else
			{
				throw new AdempiereException("Unknown aggregation item type '" + itemType + "' for " + aggregationItemDef);
			}

			// Add it to items list
			if (aggregationItems != null && !aggregationItems.isEmpty())
			{
				aggregationItemsAll.putAll(aggregationItems);
			}
		}
		return aggregationItemsAll;
	}

	private final IAggregationItem createAggregationItem_TypeColumn(final I_C_AggregationItem aggregationItemDef)
	{
		// Skip items with inactive records (because those are not present in model, for sure)
		final I_AD_Column column = aggregationItemDef.getAD_Column();
		if (column == null || column.getAD_Column_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Column_ID@"
					+ "\n @C_Aggregation_ID@: " + aggregationItemDef.getC_Aggregation().getName()
					+ "\n @C_AggregationItem_ID@: " + aggregationItemDef);
		}
		if (!column.isActive())
		{
			return null;
		}

		//
		// Create aggregation item
		final String columnName = column.getColumnName();
		final int displayType = column.getAD_Reference_ID();
		final ILogicExpression includeLogic = getLogicExpression(aggregationItemDef);
		final IAggregationItem aggregationItem = new AggregationItem(
				aggregationItemDef.getC_AggregationItem_ID() // aggregationId
				, Type.ModelColumn
				, columnName
				, displayType
				, (IAggregationAttribute)null // attribute
				, includeLogic);
		return aggregationItem;
	}

	private ILogicExpression getLogicExpression(final I_C_AggregationItem aggregationItemDef)
	{
		final String includeLogicStr = aggregationItemDef.getIncludeLogic();
		final ILogicExpression includeLogic;
		if (Check.isEmpty(includeLogicStr, true))
		{
			includeLogic = ConstantLogicExpression.TRUE;
		}
		else
		{
			includeLogic = expressionFactory.compile(includeLogicStr, ILogicExpression.class);
		}
		return includeLogic;
	}

	private LinkedHashMap<String, IAggregationItem> createAggregationItem_TypeIncludedAggregation(final I_C_AggregationItem aggregationItemDef)
	{
		final I_C_Aggregation includedAggregationDef = aggregationItemDef.getIncluded_Aggregation();
		if (includedAggregationDef == null)
		{
			return null;
		}
		if (!includedAggregationDef.isActive())
		{
			return null;
		}
		if (!checkEligible(includedAggregationDef))
		{
			return null;
		}

		// Make sure there is no IncludeLogic because that's not yet supported.
		// NOTE: when we will implement it we need to merge the IncludeLogic defined on this include item
		// into the IncludeLogic defined on included items.
		if (!Check.isEmpty(aggregationItemDef.getIncludeLogic(), true))
		{
			throw new AdempiereException("Defining IncludeLogic for an included aggregation is not supported yet");
		}

		final List<I_C_AggregationItem> includedAggregationItemsDef = aggregationDAO.retrieveAllItems(includedAggregationDef);
		final LinkedHashMap<String, IAggregationItem> includedAggregationItems = createAggregationItems(includedAggregationItemsDef);
		return includedAggregationItems;
	}

	private Map<String, IAggregationItem> createAggregationItem_TypeAttribute(final I_C_AggregationItem aggregationItemDef)
	{
		final I_C_Aggregation_Attribute attributeDef = aggregationItemDef.getC_Aggregation_Attribute();
		if (attributeDef == null || attributeDef.getC_Aggregation_Attribute_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_Aggregation_Attribute_ID@"
					+ "\n @C_Aggregation_ID@: " + aggregationItemDef.getC_Aggregation().getName()
					+ "\n @C_AggregationItem_ID@: " + aggregationItemDef);
		}
		if (!attributeDef.isActive())
		{
			return null;
		}

		final String attributeType = attributeDef.getType();
		final IAggregationAttribute aggregationAttribute;
		if (X_C_Aggregation_Attribute.TYPE_Attribute.equals(attributeType))
		{
			final String attributeName = attributeDef.getCode();
			aggregationAttribute = new AggregationAttribute_Attribute(attributeName);
		}
		else
		{
			throw new AdempiereException("@Unknown@ @Type@: " + attributeType
					+ "\n @C_Aggregation_ID@: " + aggregationItemDef.getC_Aggregation().getName()
					+ "\n @C_AggregationItem_ID@: " + aggregationItemDef
					+ "\n @C_Aggregation_Attribute_ID@: " + attributeDef);
		}

		//
		// Create aggregation item
		final ILogicExpression includeLogic = getLogicExpression(aggregationItemDef);
		final IAggregationItem aggregationItem = new AggregationItem(
				aggregationItemDef.getC_AggregationItem_ID() // aggregationId
				, Type.Attribute
				, null // columnName
				, -1 // displayType
				, aggregationAttribute // attribute
				, includeLogic);
		return Collections.singletonMap(aggregationItem.getId(), aggregationItem);
	}

	/**
	 *
	 * @param aggregationDef
	 * @return true if aggregation is eligible and shall be considered
	 * @throws AdempiereException if aggregation is not eligible and this is not acceptable
	 */
	private final boolean checkEligible(final I_C_Aggregation aggregationDef)
	{
		if (aggregationDef == null)
		{
			return false; // not eligible
		}
		final int aggregationId = aggregationDef.getC_Aggregation_ID();
		if (aggregationId <= 0)
		{
			return false; // not eligible
		}

		// Avoid cycles (i.e. self including an aggregation, direct or indirect)
		if (!seenAggregationIds.add(aggregationId))
		{
			throw new AdempiereException("Aggregation cycle detected: " + aggregationDef);
			// return false; // not eligible
		}

		final String tableName = aggregationDef.getAD_Table().getTableName();
		final String tableNameExpected = getTableName();
		if (!Objects.equals(tableNameExpected, tableName))
		{
			throw new AdempiereException("Aggregation's table name does not match"
					+ "\n @C_Aggregation_ID@: " + aggregationDef
					+ "\n @TableName@: " + tableName
					+ "\n @TableName@ (expected): " + tableNameExpected);
		}

		return true; // eligible
	}
}
