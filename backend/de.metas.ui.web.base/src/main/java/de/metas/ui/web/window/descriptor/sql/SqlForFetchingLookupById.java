package de.metas.ui.web.window.descriptor.sql;

import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Evaluatees;

import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
@ToString
public class SqlForFetchingLookupById
{
	public static final CtxName SQL_PARAM_KeyId = CtxNames.parse("SqlKeyId");

	public static final String SQL_PARAM_VALUE_ShowInactive_Yes = "Y"; // i.e. show all
	public static final String SQL_PARAM_VALUE_ShowInactive_No = "N";
	public static final CtxName SQL_PARAM_ShowInactive = CtxNames.ofNameAndDefaultValue("SqlShowInactive", SQL_PARAM_VALUE_ShowInactive_No);

	private final IStringExpression sql;

	@Builder
	private SqlForFetchingLookupById(
			@NonNull final IStringExpression sql)
	{
		this.sql = sql;
	}

	public IStringExpression toStringExpression()
	{
		return sql;
	}

	public IStringExpression toStringExpression(@NonNull final String joinOnColumnNameFQ)
	{
		return sql.resolvePartial(Evaluatees
				.mapBuilder()
				.put(SQL_PARAM_KeyId, joinOnColumnNameFQ)
				.put(SQL_PARAM_ShowInactive, SQL_PARAM_VALUE_ShowInactive_Yes)
				.build());
	}

	public boolean requiresParameter(@NonNull final String parameterName)
	{
		return sql.requiresParameter(parameterName);
	}

	public Set<CtxName> getParameters()
	{
		return sql.getParameters();
	}

	public String evaluate(@NonNull final LookupDataSourceContext evalCtx)
	{
		return sql.evaluate(evalCtx, OnVariableNotFound.Fail);
	}
}
