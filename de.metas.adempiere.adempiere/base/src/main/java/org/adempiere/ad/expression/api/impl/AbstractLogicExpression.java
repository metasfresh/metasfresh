package org.adempiere.ad.expression.api.impl;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.Evaluatee;

public abstract class AbstractLogicExpression implements ILogicExpression
{
	@Override
	public final Boolean evaluate(final Evaluatee ctx, final boolean ignoreUnparsable)
	{
		// backward compatibility
		final OnVariableNotFound onVariableNotFound = ignoreUnparsable ? OnVariableNotFound.ReturnNoResult : OnVariableNotFound.Fail;
		return evaluate(ctx, onVariableNotFound);
	}

	@Override
	public final Boolean evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound)
	{
		final LogicExpressionEvaluator evaluator = getEvaluator();
		return evaluator.evaluate(ctx, this, onVariableNotFound);
	}

	@Override
	public LogicExpressionResult evaluateToResult(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		final LogicExpressionEvaluator evaluator = getEvaluator();
		return evaluator.evaluateToResult(ctx, this, onVariableNotFound);
	}

	@Override
	public final LogicExpressionEvaluator getEvaluator()
	{
		return LogicExpressionEvaluator.instance;
	}
}
