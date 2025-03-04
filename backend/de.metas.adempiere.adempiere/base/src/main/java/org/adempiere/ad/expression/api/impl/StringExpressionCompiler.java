package org.adempiere.ad.expression.api.impl;

import de.metas.util.Check;
import org.adempiere.ad.expression.api.ExpressionContext;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.CtxName;

import java.util.List;

public final class StringExpressionCompiler extends AbstractChunkBasedExpressionCompiler<String, IStringExpression>
{
	public static final transient StringExpressionCompiler instance = new StringExpressionCompiler();

	// NOTE to developer: make sure there are no variables here since we are using a shared instance

	/**
	 * Escape '@' char, by replacing one @ with double @@  
	 */
	public static String escape(final String str)
	{
		if (Check.isEmpty(str, true))
		{
			return str;
		}
		
		return str.replace(PARAMETER_TAG, PARAMETER_DOUBLE_TAG);
	}
	
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
