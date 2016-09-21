package org.adempiere.ad.expression.api.impl;

import java.util.List;

import org.adempiere.ad.expression.api.ExpressionContext;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.CtxName;

public final class StringExpressionCompiler extends AbstractChunkBasedExpressionCompiler<String, IStringExpression>
{
	public static final transient StringExpressionCompiler instance = new StringExpressionCompiler();

	// NOTE to developer: make sure there are no variables here since we are using a shared instance

	private StringExpressionCompiler()
	{
		super();
	}

	@Override
	protected IStringExpression getNullExpression()
	{
		return IStringExpression.NULL;
	}

	@Override
	protected IStringExpression createConstantExpression(final ExpressionContext context, final String expressionStr)
	{
		return ConstantStringExpression.of(expressionStr);
	}

	@Override
	protected IStringExpression createSingleParamaterExpression(final ExpressionContext context, final String expressionStr, final CtxName parameter)
	{
		return new SingleParameterStringExpression(expressionStr, parameter);
	}

	@Override
	protected IStringExpression createGeneralExpression(final ExpressionContext context, final String expressionStr, final List<Object> expressionChunks)
	{
		return new StringExpression(expressionStr, expressionChunks);
	}

}
