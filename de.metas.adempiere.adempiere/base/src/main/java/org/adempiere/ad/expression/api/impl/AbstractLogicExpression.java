package org.adempiere.ad.expression.api.impl;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.Evaluatee;

public abstract class AbstractLogicExpression implements ILogicExpression
{
	@Override
	public final Boolean evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound)
	{
		return LogicExpressionEvaluator.instance.evaluate(ctx, this, onVariableNotFound);
	}

	@Override
	public LogicExpressionResult evaluateToResult(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		return LogicExpressionEvaluator.instance.evaluateToResult(ctx, this, onVariableNotFound);
	}
}
