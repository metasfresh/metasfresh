package org.adempiere.ad.expression.api.impl;

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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.ILogicExpressionCompiler;
import org.adempiere.ad.expression.exceptions.ExpressionCompileException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

public class LogicExpressionCompiler implements ILogicExpressionCompiler
{
	public static final LogicExpressionCompiler instance = new LogicExpressionCompiler();

	// private final transient Logger logger = CLogMgt.getLogger(getClass());

	private LogicExpressionCompiler()
	{
		super();
	}

	@Override
	public boolean isUseOperatorPrecedence()
	{
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		return Services.get(ISysConfigBL.class)
				.getBooleanValue(SYSCONFIG_UseOperatorPrecedence, DEFAULT_UseOperatorPrecedence, adClientId, adOrgId);
	}

	@Override
	public void setUseOperatorPrecedence(boolean enabled)
	{
		final int adOrgId = 0;
		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_UseOperatorPrecedence, enabled, adOrgId);
	}

	@Override
	public ILogicExpression compile(String expressionStr)
	{
		Check.assume(!Check.isEmpty(expressionStr, true), "expressionStr is not empty");

		// NOTE: we shall not trim nor replace all whitespaces (i.e. replaceAll(" ", "")) from expressionStr because
		// there can be values which really need to contain white spaces
		final StringTokenizer st = new StringTokenizer(expressionStr, "&|()", true);

		final List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens())
		{
			final String token = st.nextToken();
			if (Check.isEmpty(token, true))
			{
				// we shall ignore empty tokens because those are not really tokens
				continue;
			}
			tokens.add(token);
		}

		// only uneven arguments
		if (tokens.size() % 2 == 0)
		{
			throw new ExpressionCompileException("Logic does not comply with format '<expression> [<logic> <expression>]' => " + expressionStr);
		}

		return compile(tokens.iterator(), false);
	}

	private ILogicExpression compile(final Iterator<String> tokens, final boolean goingDown)
	{
		LogicExpressionBuilder result = new LogicExpressionBuilder();
		while (tokens.hasNext())
		{
			final String token = tokens.next();

			//
			// Sub-expression start
			if ("(".equals(token))
			{
				final ILogicExpression child = compile(tokens, false);
				addChild(result, child);
			}
			//
			// Operator
			else if (("&".equals(token)) || ("|".equals(token)))
			{
				final String operator = token;
				if (result.getRight() == null)
				{
					result.setOperator(operator);
				}
				else
				{
					if (isUseOperatorPrecedence() && "&".equals(operator))
					{
						// If precedence is enabled, & nodes are sent down the tree, | nodes up.
						final ILogicExpression right = new LogicExpression(result.getRight(), operator, compile(tokens, false));
						result.setRight(right);
					}
					else
					{
						result = new LogicExpressionBuilder(
								result.build(),
								operator,
								compile(tokens, true));
					}
				}
			}
			//
			// Sub-expression end
			else if (")".equals(token))
			{
				return result.build();
			}
			//
			// Tuple
			else if (isTuple(token))
			{
				final LogicTuple tuple = compileTuple(token);
				addChild(result, tuple);
				if (goingDown)
				{
					return result.build();
				}
			}
			//
			// Error
			else
			{
				throw new ExpressionCompileException("Unexpected token(2): " + token);
			}
		}

		return result.build();
	}

	private LogicTuple compileTuple(final String tokenParam)
	{
		// Fix common mistakes
		String token = tokenParam.replace("!=", "!");
		token = token.trim();

		StringTokenizer s1 = new StringTokenizer(token, "!=~^><", true);
		if (s1.countTokens() != 3)
		{
			throw new ExpressionCompileException("Logic tuple does not comply with format "
					+ "'@context@=value' where operand could be one of '=!^~><' => " + tokenParam);
		}

		final LogicTuple tuple = new LogicTuple(s1.nextToken(), s1.nextToken(), s1.nextToken());
		return tuple;
	}

	private boolean isTuple(String token)
	{
		return token.indexOf("!") > 0
				|| token.indexOf("=") > 0
				|| token.indexOf("^") > 0 // metas: cg: support legacy NOT operator
				|| token.indexOf("~") > 0 // metas: cg: support legacy NOT operator
				|| token.indexOf(">") > 0
				|| token.indexOf("<") > 0;
	}

	private void addChild(final LogicExpressionBuilder expr, final ILogicExpression child)
	{
		if (expr.getLeft() == null)
		{
			expr.setLeft(child);
		}
		else if (expr.getRight() == null)
		{
			expr.setRight(child);
		}
		else
		{
			throw new ExpressionCompileException("Unexpected expression: " + child);
		}

	}
}
