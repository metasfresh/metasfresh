package org.adempiere.ad.expression.api;

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

import java.util.List;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.impl.LogicExpressionBuilder;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.expression.json.JsonLogicExpressionDeserializer;
import org.compiere.util.Evaluatee;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableList;

/**
 * Logic expression
 *
 * NOTE: business logic expects that implementation of this interface to be immutable.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@JsonDeserialize(using = JsonLogicExpressionDeserializer.class)
public interface ILogicExpression extends IExpression<Boolean>
{
	ILogicExpression TRUE = ConstantLogicExpression.TRUE;
	ILogicExpression FALSE = ConstantLogicExpression.FALSE;

	String LOGIC_OPERATOR_AND = "&";
	String LOGIC_OPERATOR_OR = "|";
	String LOGIC_OPERATOR_XOR = "^";
	List<String> LOGIC_OPERATORS = ImmutableList.of(LOGIC_OPERATOR_AND, LOGIC_OPERATOR_OR, LOGIC_OPERATOR_XOR);

	@Override
	default Class<Boolean> getValueClass()
	{
		return Boolean.class;
	}

	@Override
	String getExpressionString();

	@Override
	String getFormatedExpressionString();

	@Override
	Set<String> getParameters();

	@Override
	default Boolean evaluate(final Evaluatee ctx, final boolean ignoreUnparsable)
	{
		// backward compatibility
		final OnVariableNotFound onVariableNotFound = ignoreUnparsable ? OnVariableNotFound.ReturnNoResult : OnVariableNotFound.Fail;
		return evaluate(ctx, onVariableNotFound);
	}

	/**
	 * Evaluates given expression and returns {@link LogicExpressionResult}.
	 *
	 * Use this method if you need more informations about the evaluation (e.g. which were the parameters used etc).
	 *
	 * If you are just interested about the boolean result, please use {@link #evaluate(Evaluatee, OnVariableNotFound)}.
	 *
	 * @param ctx
	 * @param onVariableNotFound
	 * @return
	 * @throws ExpressionEvaluationException
	 * @see {@link ILogicExpressionEvaluator#evaluateToResult(Evaluatee, ILogicExpression, OnVariableNotFound)}
	 */
	LogicExpressionResult evaluateToResult(Evaluatee ctx, OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException;

	/**
	 * @return true if this expression is constant. In this case you can call {@link #constantValue()} to get it's value
	 */
	boolean isConstant();

	/**
	 * @return expression's constant value
	 * @throws ExpressionEvaluationException in case this is not a constant expression (see {@link #isConstant()})
	 */
	boolean constantValue();

	/**
	 * Converts this expression to a constant expression
	 *
	 * @param constantValue
	 * @return constant expression
	 */
	ILogicExpression toConstantExpression(final boolean constantValue);

	/**
	 * @return true if the expression is constant and it's value is true
	 */
	default boolean isConstantTrue()
	{
		return isConstant() && constantValue() == true;
	}

	/**
	 * @return true if the expression is constant and it's value is false
	 */
	default boolean isConstantFalse()
	{
		return isConstant() && constantValue() == false;
	}

	@Override
	default boolean isNoResult(final Object result)
	{
		// because evaluation is throwing exception in case of failure, the only "no result" would be the NULL
		return result == null;
	}

	@Override
	default boolean isNullExpression()
	{
		// there is no such thing for logic expressions
		return false;
	}

	/** Compose this logic expression with the given one, using logic AND and return it */
	default ILogicExpression and(final ILogicExpression expression)
	{
		return LogicExpressionBuilder.build(this, LOGIC_OPERATOR_AND, expression);
	}

	default ILogicExpression andNot(final ILogicExpression expression)
	{
		return LogicExpressionBuilder.build(this, LOGIC_OPERATOR_AND, expression.negate());
	}

	/** Compose this logic expression with the given one, using logic OR and return it */
	default ILogicExpression or(final ILogicExpression expression)
	{
		return LogicExpressionBuilder.build(this, LOGIC_OPERATOR_OR, expression);
	}

	default ILogicExpression negate()
	{
		// NOTE: because we don't have unary operator support atm, we will use XOR as : !a = a XOR true
		return xor(TRUE);
	}

	default ILogicExpression xor(final ILogicExpression expression)
	{
		return LogicExpressionBuilder.build(this, LOGIC_OPERATOR_XOR, expression);
	}
}
