/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.config;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Evaluatee;

@Value
public class ESQueryTemplate
{
	public static final CtxName PARAM_query = CtxNames.parse("query");
	public static final CtxName PARAM_queryStartsWith = CtxNames.parse("queryStartsWith");
	public static final CtxName PARAM_orgFilter = CtxNames.parse("OrgFilter");

	@NonNull IStringExpression expression;

	public static ESQueryTemplate ofJsonString(final String json)
	{
		return new ESQueryTemplate(IStringExpression.compile(json));
	}

	private ESQueryTemplate(@NonNull final IStringExpression expression)
	{
		this.expression = expression;
	}

	public boolean isOrgFilterParameterRequired()
	{
		return isParameterRequired(PARAM_orgFilter);
	}

	public boolean isParameterRequired(@NonNull final CtxName ctxName)
	{
		return expression.requiresParameter(ctxName.getName());
	}

	public String resolve(@NonNull final Evaluatee evalCtx)
	{
		final ToJsonEvaluatee evalCtxEffective = new ToJsonEvaluatee(evalCtx);
		evalCtxEffective.skipConvertingToJson(PARAM_orgFilter.getName());
		return expression.evaluate(evalCtxEffective, IExpressionEvaluator.OnVariableNotFound.Fail);
	}
}
