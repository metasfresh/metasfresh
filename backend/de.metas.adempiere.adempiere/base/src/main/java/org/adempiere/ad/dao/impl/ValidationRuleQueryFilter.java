package org.adempiere.ad.dao.impl;

import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Query filter for validation rules
 *
 * @param <T>
 * @author al
 */
public class ValidationRuleQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private static final Logger logger = LogManager.getLogger(ValidationRuleQueryFilter.class);

	private final Evaluatee evaluatee;

	private final String tableName;
	private final AdValRuleId adValRuleId;

	public ValidationRuleQueryFilter(@NonNull final Object model, @NonNull final AdValRuleId adValRuleId)
	{
		this.tableName = InterfaceWrapperHelper.getModelTableName(model);
		this.adValRuleId = adValRuleId;
		this.evaluatee = InterfaceWrapperHelper.getEvaluatee(model);
	}

	public ValidationRuleQueryFilter(@NonNull final String tableName, @NonNull final AdValRuleId adValRuleId)
	{
		this.tableName = tableName;
		this.adValRuleId = adValRuleId;
		this.evaluatee = Evaluatees.ofCtx(Env.getCtx());
	}

	@Override
	public boolean accept(final T model)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSql()
	{
		final IValidationRuleFactory validationRuleFactory = Services.get(IValidationRuleFactory.class);
		final IValidationContext evalCtx = validationRuleFactory.createValidationContext(evaluatee);

		final IValidationRule valRule = validationRuleFactory.create(
				tableName,
				adValRuleId,
				null, // ctx table name
				null // ctx column name
		);

		final IStringExpression prefilterWhereClauseExpr = valRule.getPrefilterWhereClause();
		final String prefilterWhereClause = prefilterWhereClauseExpr.evaluate(evalCtx, OnVariableNotFound.ReturnNoResult);
		if (prefilterWhereClauseExpr.isNoResult(prefilterWhereClause))
		{
			final String prefilterWhereClauseDefault = "1=0";
			logger.warn("Cannot evaluate {} using {}. Returning {}.", prefilterWhereClauseExpr, evalCtx, prefilterWhereClauseDefault);
			return prefilterWhereClauseDefault;
		}

		return prefilterWhereClause;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		return Collections.emptyList();
	}
}
