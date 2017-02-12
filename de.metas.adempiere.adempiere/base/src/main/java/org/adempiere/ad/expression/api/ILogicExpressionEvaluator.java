package org.adempiere.ad.expression.api;

import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.Evaluatee;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Logic expression evaluator
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ILogicExpressionEvaluator extends IExpressionEvaluator<ILogicExpression, Boolean>
{
	@Override
	Boolean evaluate(Evaluatee ctx, ILogicExpression expression, IExpressionEvaluator.OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException;

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
	 */
	LogicExpressionResult evaluateToResult(Evaluatee ctx, ILogicExpression expression, OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException;
}
