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


import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Evaluatee;

import de.metas.aggregation.api.IAggregationAttribute;
import de.metas.aggregation.api.IAggregationItem;
import de.metas.util.Check;

/**
 * Immutable {@link IAggregationItem} implementation
 *
 * @author tsa
 *
 */
/* package */final class AggregationItem implements IAggregationItem
{
	private final String id;
	private final ILogicExpression includeLogic;
	private final Type type;
	private final String columnName;
	private final int displayType;
	private final IAggregationAttribute attribute;

	/* package */AggregationItem(
			final int aggregationItemId,
			final Type type,
			final String columnName,
			final int displayType,
			final IAggregationAttribute attribute,
			final ILogicExpression includeLogic)
	{
		super();

		Check.assume(aggregationItemId > 0, "aggregationItemId > 0");
		id = String.valueOf(aggregationItemId);

		Check.assumeNotNull(type, "type not null");
		this.type = type;
		if (type == Type.ModelColumn)
		{
			Check.assumeNotEmpty(columnName, "columnName not empty");
		}
		else if (type == Type.Attribute)
		{
			Check.assumeNotNull(attribute, "attribute not null");
		}

		Check.assumeNotNull(includeLogic, "includeLogic not null");
		this.includeLogic = includeLogic;

		this.columnName = columnName;
		this.displayType = displayType;
		this.attribute = attribute;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Type getType()
	{
		return type;
	}

	@Override
	public String getColumnName()
	{
		return columnName;
	}

	@Override
	public int getDisplayType()
	{
		return displayType;
	}

	@Override
	public ILogicExpression getIncludeLogic()
	{
		return includeLogic;
	}

	@Override
	public boolean isInclude(final Evaluatee context)
	{
		final ILogicExpression includeLogic = getIncludeLogic();
		final Boolean include = includeLogic.evaluate(context, OnVariableNotFound.ReturnNoResult);
		if (include == null || !include)
		{
			return false;
		}

		return true;
	}

	@Override
	public IAggregationAttribute getAttribute()
	{
		return attribute;
	}
}
