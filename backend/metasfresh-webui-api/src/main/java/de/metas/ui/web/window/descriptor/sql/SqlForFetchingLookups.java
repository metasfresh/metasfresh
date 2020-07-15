package de.metas.ui.web.window.descriptor.sql;

import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;

import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
public class SqlForFetchingLookups
{
	public static final CtxName PARAM_Offset = CtxNames.ofNameAndDefaultValue("Offset", "0");
	public static final CtxName PARAM_Limit = CtxNames.ofNameAndDefaultValue("Limit", "1000");

	private final IStringExpression sql;

	@Builder
	private SqlForFetchingLookups(
			@NonNull final IStringExpression sql)
	{
		this.sql = sql;
	}

	public IStringExpression toStringExpression()
	{
		return sql;
	}

	public Object toOneLineString()
	{
		return sql.toOneLineString();
	}

	public Set<CtxName> getParameters()
	{
		return sql.getParameters();
	}

	public String evaluate(LookupDataSourceContext evalCtx)
	{
		return sql.evaluate(evalCtx, OnVariableNotFound.Fail);
	}
}
