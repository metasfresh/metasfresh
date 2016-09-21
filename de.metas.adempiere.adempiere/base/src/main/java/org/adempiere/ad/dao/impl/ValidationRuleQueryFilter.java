package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Query filter for validation rules
 *
 * @author al
 *
 * @param <T>
 */
public class ValidationRuleQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private static final Logger logger = LogManager.getLogger(ValidationRuleQueryFilter.class);

	// private final Object model;
	private final Properties ctx;
	private final String tableName;
	private final int adValRuleId;
	private final Integer windowNo;
	private final Integer tabNo;

	public ValidationRuleQueryFilter(final Object model, final int adValRuleId)
	{
		super();

		Check.assumeNotNull(model, "model not null");
		Check.assume(adValRuleId > 0, "Given AD_Val_Rule exists");

		// this.model = model;
		this.ctx = InterfaceWrapperHelper.getCtx(model);
		this.tableName = InterfaceWrapperHelper.getModelTableName(model);
		this.adValRuleId = adValRuleId;
		this.windowNo = InterfaceWrapperHelper.getDynAttribute(model, Env.DYNATTR_WindowNo);
		this.tabNo = InterfaceWrapperHelper.getDynAttribute(model, Env.DYNATTR_TabNo);
	}

	@Override
	public boolean accept(final T model)
	{
		if (isInvalid())
		{
			// NOTE: Because we could deal with a huge amount of data, it's better to filter everything out
			// then running in some performance issues because we need to retrieve a lot of records.
			return false;
		}
		
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String getSql()
	{
		if (isInvalid())
		{
			// not usable if we don't have information about windowNo and tabNo
			// Because we could deal with a huge amount of data, it's better to filter everything out
			// then running in some performance issues because we need to retrieve a lot of records.
			return "1=0";
		}

		final IValidationRuleFactory validationRuleFactory = Services.get(IValidationRuleFactory.class);
		final IValidationContext evalCtx = validationRuleFactory.createValidationContext(ctx, windowNo, tabNo, tableName);
		final IValidationRule valRule = validationRuleFactory.create(tableName, adValRuleId);
		
		final IStringExpression prefilterWhereClauseExpr = valRule.getPrefilterWhereClause();
		final String prefilterWhereClause = prefilterWhereClauseExpr.evaluate(evalCtx, OnVariableNotFound.ReturnNoResult);
		if(prefilterWhereClauseExpr.isNoResult(prefilterWhereClause))
		{
			final String prefilterWhereClauseDefault = "1=0";
			logger.warn("Cannot evaluate {} using {}. Returing {}.", prefilterWhereClauseExpr, evalCtx, prefilterWhereClauseDefault);
			return prefilterWhereClauseDefault;
		}
		
		return prefilterWhereClause;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		return Collections.emptyList();
	}

	/**
	 * 
	 * @return true if this filter is NOT valid
	 */
	private final boolean isInvalid()
	{
		if (windowNo == null || tabNo == null)
		{
			return true;
		}

		return false;
	}
}
