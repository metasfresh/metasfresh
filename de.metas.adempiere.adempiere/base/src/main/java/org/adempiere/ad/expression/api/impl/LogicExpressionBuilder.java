package org.adempiere.ad.expression.api.impl;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;

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

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.impl.LogicExpressionEvaluator.BooleanEvaluator;
import org.adempiere.ad.expression.api.impl.LogicExpressionEvaluator.BooleanValueSupplier;
import org.adempiere.ad.expression.exceptions.ExpressionCompileException;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;

/**
 * Internal helper used to compile {@link ILogicExpression}s
 *
 * @author tsa
 *
 */
public final class LogicExpressionBuilder
{
	public static final ILogicExpression build(final ILogicExpression left, final String operator, final ILogicExpression right)
	{
		return new LogicExpressionBuilder()
				.setLeft(left)
				.setOperator(operator)
				.setRight(right)
				.build();
	}

	private static final Logger logger = LogManager.getLogger(LogicExpressionBuilder.class);

	private ILogicExpression left;
	private ILogicExpression right;
	private String operator;

	public LogicExpressionBuilder()
	{
		super();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("left", left)
				.add("operator", operator)
				.add("right", right)
				.toString();
	}

	public ILogicExpression build()
	{
		//
		// Only left side of expression was set, returning it
		if (right == null && operator == null)
		{
			return toConstantExpressionIfPossible(left);
		}
		//
		// Only right side of expression was set, returning it
		if (left == null && operator == null)
		{
			return toConstantExpressionIfPossible(right);
		}

		// Everything was set, building the expression
		final ILogicExpression expr = toConstantExpressionIfPossible(left, operator, right);
		return toConstantExpressionIfPossible(expr);
	}

	/**
	 * Build this expression and compose it using <code>operator</code> and <code>right</code> expression.
	 * 
	 * @param operator
	 * @param right
	 * @return new builder which has this built expression on the left and given expression on the right
	 */
	public LogicExpressionBuilder buildAndCompose(final String operator, final ILogicExpression right)
	{
		final ILogicExpression left = build();
		return new LogicExpressionBuilder()
				.setLeft(left)
				.setOperator(operator)
				.setRight(right);
	}

	/**
	 * Converts given expression to a constant expression if possible
	 *
	 * i.e. if the expression is does not depend on external parameters then evaluate it now and replace it with a constant logic expression.
	 *
	 * @param expr
	 * @return constant expression or original expression
	 */
	private static final ILogicExpression toConstantExpressionIfPossible(final ILogicExpression expr)
	{
		try
		{
			// Check if already a constant expression
			if (expr.isConstant())
			{
				return expr;
			}

			// If the expression depends on external parameters, there is nothing we can do.
			if (!expr.getParameters().isEmpty())
			{
				return expr;
			}

			// Evaluate the expression now and replace it with a constant expression, but preserve the expression string.
			final Evaluatee ctx = null; // not needed
			final boolean constantValue = expr.evaluate(ctx, OnVariableNotFound.Fail);
			return expr.toConstantExpression(constantValue);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed converting {} to constant. Skip optimization.", expr, ex);
			return expr;
		}
	}

	private static ILogicExpression toConstantExpressionIfPossible(final ILogicExpression leftExpr, final String operator, final ILogicExpression rightExpr)
	{
		//
		// Get the boolean evaluator
		final BooleanEvaluator booleanEvaluator = LogicExpressionEvaluator.EVALUATORS_ByOperator.get(operator);
		if (booleanEvaluator == null)
		{
			throw new ExpressionCompileException("Unknown operator: " + operator);
		}

		//
		// Try reducing the expression to constant
		final BooleanValueSupplier left = () -> toConstantValueIfPossible(leftExpr);
		final BooleanValueSupplier right = () -> toConstantValueIfPossible(rightExpr);
		final Boolean constantValue = booleanEvaluator.evaluateOrNull(left, right);

		return new LogicExpression(constantValue, toConstantExpressionIfPossible(leftExpr), operator, toConstantExpressionIfPossible(rightExpr));
	}

	/**
	 * @param expr
	 * @return boolean value or <code>null</code> if the expression could not be evaluated to constant boolean value
	 */
	private static final Boolean toConstantValueIfPossible(final ILogicExpression expr)
	{
		try
		{
			if (expr.isConstant())
			{
				return expr.constantValue();
			}

			if (!expr.getParameters().isEmpty())
			{
				return null;
			}

			// Evaluate the expression now and replace it with a constant expression, but preserve the expression string.
			final Evaluatee ctx = null; // not needed
			final boolean value = expr.evaluate(ctx, OnVariableNotFound.Fail);
			return value;
		}
		catch (final Exception ex)
		{
			logger.warn("Failed evaluating {} to constant. Skipped.", expr, ex);
			return null;
		}
	}

	public LogicExpressionBuilder setLeft(final ILogicExpression left)
	{
		this.left = left;
		return this;
	}

	public LogicExpressionBuilder setRight(final ILogicExpression right)
	{
		this.right = right;
		return this;
	}

	/**
	 * Sets given <code>child</code> expression to
	 * <ul>
	 * <li>left, if left was not already set
	 * <li>right, if right was not already set
	 * </ul>
	 * 
	 * @param child
	 * @return this
	 * @throws ExpressionCompileException if both left and right were already set
	 */
	public LogicExpressionBuilder addChild(final ILogicExpression child)
	{
		if (getLeft() == null)
		{
			setLeft(child);
		}
		else if (getRight() == null)
		{
			setRight(child);
		}
		else
		{
			throw new ExpressionCompileException("Cannot add " + child + " to " + this + " because both left and right are already filled");
		}
		return this;
	}

	public LogicExpressionBuilder setOperator(final String operator)
	{
		this.operator = operator;
		return this;
	}

	public ILogicExpression getLeft()
	{
		return left;
	}

	public ILogicExpression getRight()
	{
		return right;
	}

	public String getOperator()
	{
		return operator;
	}
}
