/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.util.List;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;


/**
 *	Expression Evaluator	
 *	
 *  @author Jorg Janke
 *  @version $Id: Evaluator.java,v 1.3 2006/07/30 00:54:36 jjanke Exp $
 */
public class Evaluator
{
	/**	Static Logger	*/
	private static final Logger s_log = LogManager.getLogger(Evaluator.class);
	
	/**
	 * 	Check if All Variables are Defined
	 *	@param source source
	 *	@param logic logic info
	 *	@return true if fully defined
	 */
	public static boolean isAllVariablesDefined (Evaluatee source, String logic)
	{
		if (logic == null || logic.length() == 0)
			return true;
		//
		int pos = 0;
		while (pos < logic.length())
		{
			int first = logic.indexOf('@', pos);
			if (first == -1)
				return true;
			int second = logic.indexOf('@', first+1);
			if (second == -1)
			{
				s_log.error("No second @ in Logic: " + logic);
				return false;
			}
			String variable = logic.substring(first+1, second-1);
			String eval = getValue(source, variable);
			s_log.trace(variable + "=" + eval);
			if (eval == null || eval.length() == 0)
				return false;
			//	
			pos = second + 1;
		}
		return true;
	}	//	isAllVariablesDefined
	
	/**
	 *	Evaluate Logic.
	 *  <code>
	 *	format		:= <expression> [<logic> <expression>]
	 *	expression	:= @<context>@<exLogic><value>
	 *	logic		:= <|> | <&>
	 *  exLogic		:= <=> | <!> | <^> | <<> | <>>
	 *
	 *	context		:= any global or window context
	 *	value		:= strings can be with ' or "
	 *	logic operators	:= AND or OR with the prevoius result from left to right
	 *
	 *	Example	'@AD_Table@=Test | @Language@=GERGER
	 *  </code>
	 *  @param source class implementing get_ValueAsString(variable)
	 *  @param logic logic string
	 *  @return logic result
	 *  
	 *  @deprecated metas - Please use {@link IExpressionFactory} with {@link ILogicExpression}
	 */
	@Deprecated
	public static boolean evaluateLogic (final Evaluatee source, final String logic)
	{
		final ILogicExpression expression = Services.get(IExpressionFactory.class).compileOrDefault(logic, ConstantLogicExpression.FALSE, ILogicExpression.class);

		// don't fail on missing parameter because we want to be compatible with old org.compiere.util.Evaluator.evaluateLogic(Evaluatee, String) method
		final boolean ignoreUnparsable = true;
		return expression.evaluate(source, ignoreUnparsable);
	}   //  evaluateLogic

	/**
	 *  Parse String and add variables with @ to the list.
	 *  @param list list to be added to
	 *  @param parseString string to parse for variables
	 *  @deprecated metas - Please use {@link IExpressionFactory} or {@link #parseDepends(List, IExpression)}
	 */
	@Deprecated
	public static void parseDepends (List<String> list, String parseString)
	{
		final IStringExpression expression = Services.get(IExpressionFactory.class).compile(parseString, IStringExpression.class);
		parseDepends(list, expression);
	}   //  parseDepends

	// metas:
	private static String getValue(Evaluatee source, String variable)
	{
		return CtxNames.parse(variable).getValueAsString(source);
	}
	
	public static String parseContext(Evaluatee source, String expression)
	{
		if (expression == null || expression.length() == 0)
			return null;
		//
		int pos = 0;
		String finalExpression = expression;
		while (pos < expression.length())
		{
			int first = expression.indexOf('@', pos);
			if (first == -1)
				return expression;
			int second = expression.indexOf('@', first+1);
			if (second == -1)
			{
				s_log.error("No second @ in Logic: {}", expression);
				return null;
			}
			String variable = expression.substring(first+1, second);
			String eval = getValue(source, variable);
			s_log.trace("{}={}", variable, eval);
			if (Check.isEmpty(eval, true))
			{
				eval = Env.getContext(Env.getCtx(), variable);
			}
			finalExpression = finalExpression.replaceFirst("@"+variable+"@", eval);
			//	
			pos = second + 1;
		}
		return finalExpression;
	}
	
	public static boolean hasVariable(Evaluatee source, String variableName)
	{
		if (source instanceof Evaluatee2)
		{
			final Evaluatee2 source2 = (Evaluatee2)source;
			return source2.has_Variable(variableName);
		}
		else
		{
			return !Check.isEmpty(source.get_ValueAsString(variableName));
		}
	}
	
	public static void parseDepends (final List<String> list, final IExpression<?> expr)
	{
		if (expr == null)
		{
			return;
		}
		
		list.addAll(expr.getParameterNames());
	}
}	//	Evaluator
