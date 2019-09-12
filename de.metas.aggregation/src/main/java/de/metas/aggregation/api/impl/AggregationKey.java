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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.Evaluatee;
import org.compiere.util.Util.ArrayKey;

import de.metas.aggregation.api.IAggregationKey;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "keyString", doNotUseGetters = true)
public final class AggregationKey implements IAggregationKey
{
	public static final transient AggregationKey NULL = new AggregationKey((String)null, -1);

	private final String keyString;
	private final IStringExpression keyStringExpr;
	private final int aggregationId;

	public AggregationKey(final ArrayKey key, final int aggregationId)
	{
		this(key == null ? null : key.toString(), aggregationId);
	}

	public AggregationKey(final String keyString, final int aggregationId)
	{
		this.keyString = keyString;
		keyStringExpr = Services.get(IExpressionFactory.class).compile(keyString, IStringExpression.class);
		this.aggregationId = aggregationId <= 0 ? -1 : aggregationId;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAggregationKeyString();
	}

	@Override
	public String getAggregationKeyString()
	{
		return keyString;
	}

	@Override
	public int getC_Aggregation_ID()
	{
		return aggregationId;
	}

	@Override
	public IAggregationKey parse(final Evaluatee ctx)
	{
		final String keyStringNew = keyStringExpr.evaluate(ctx, OnVariableNotFound.Preserve);
		return new AggregationKey(keyStringNew, aggregationId);
	}

	@Override
	public IAggregationKey append(final String keyPart)
	{
		Check.assumeNotEmpty(keyPart, "keyPart is not empty");

		final String keyStringNew = this.keyString + ArrayKey.SEPARATOR + keyPart;
		return new AggregationKey(keyStringNew, aggregationId);
	}
}
