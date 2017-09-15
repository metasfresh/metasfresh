package de.metas.ui.web.window.descriptor.sql;

import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableSet;

import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.ui.web.window.WindowConstants;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Default value expression used to provide a preliminary auto-sequence for "Value"-like fields.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/2303
 */
public class AutoSequenceDefaultValueExpression implements IStringExpression
{
	public static final AutoSequenceDefaultValueExpression of(final String tableName)
	{
		return new AutoSequenceDefaultValueExpression(tableName);
	}

	private static final String PARAMETER_AD_Client_ID = WindowConstants.FIELDNAME_AD_Client_ID;
	private static final String PARAMETER_AD_Org_ID = WindowConstants.FIELDNAME_AD_Org_ID;
	private static final Set<CtxName> PARAMETERS = ImmutableSet.of(
			CtxNames.parse(PARAMETER_AD_Client_ID),
			CtxNames.parse(PARAMETER_AD_Org_ID));

	private final String tableName;

	private AutoSequenceDefaultValueExpression(@NonNull final String tableName)
	{
		this.tableName = tableName;
	}

	/**
	 * Returns the constant {@code "@Value@"}. Not important, just to have something not null
	 */
	@Override
	public String getExpressionString()
	{
		return "@Value@";
	}

	@Override
	public Set<CtxName> getParameters()
	{
		return PARAMETERS;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		Integer adClientId = ctx.get_ValueAsInt(PARAMETER_AD_Client_ID, null);
		if (adClientId == null || adClientId < 0)
		{
			adClientId = Env.getAD_Client_ID(Env.getCtx());
		}

		Integer adOrgId = ctx.get_ValueAsInt(PARAMETER_AD_Org_ID, null);
		if (adOrgId == null || adOrgId < 0)
		{
			adOrgId = Env.getAD_Org_ID(Env.getCtx());
		}

		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
		final String value = documentNoFactory.forTableName(tableName, adClientId, adOrgId)
				.setFailOnError(onVariableNotFound == OnVariableNotFound.Fail)
				.setUsePreliminaryDocumentNo(true)
				.build();

		if (value == null && onVariableNotFound == OnVariableNotFound.Fail)
		{
			throw new AdempiereException("No auto value sequence found for " + tableName + ", AD_Client_ID=" + adClientId + ", AD_Org_ID=" + adOrgId);
		}

		return value;
	}
}
