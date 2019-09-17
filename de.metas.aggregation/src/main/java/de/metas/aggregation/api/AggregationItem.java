package de.metas.aggregation.api;

import javax.annotation.Nullable;

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

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.util.Evaluatee;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public final class AggregationItem
{
	public enum Type
	{
		ModelColumn, Attribute
	}

	private final AggregationItemId id;
	private final ILogicExpression includeLogic;
	private final Type type;
	private final String columnName;
	private final int displayType;
	private final AggregationAttribute attribute;

	@Builder
	private AggregationItem(
			@NonNull final AggregationItemId id,
			@NonNull final Type type,
			@Nullable final String columnName,
			final int displayType,
			@Nullable final AggregationAttribute attribute,
			@NonNull final ILogicExpression includeLogic)
	{
		if (type == Type.ModelColumn)
		{
			Check.assumeNotEmpty(columnName, "columnName not empty");
		}
		else if (type == Type.Attribute)
		{
			Check.assumeNotNull(attribute, "attribute not null");
		}

		this.id = id;
		this.type = type;
		this.includeLogic = includeLogic;
		this.columnName = columnName;
		this.displayType = displayType;
		this.attribute = attribute;
	}

	public boolean isInclude(final Evaluatee context)
	{
		final ILogicExpression includeLogic = getIncludeLogic();
		final Boolean include = includeLogic.evaluate(context, OnVariableNotFound.ReturnNoResult);
		return include != null && include;
	}

}
