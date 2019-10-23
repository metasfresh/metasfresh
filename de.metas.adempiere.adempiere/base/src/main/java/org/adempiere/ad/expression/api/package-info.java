/**
 * Expressions evaluations package.
 * 
 * To compile and expression please use {@link org.adempiere.ad.expression.api.IExpressionFactory} service.
 * 
 * <h2>String expressions</h2>
 * See {@link org.adempiere.ad.expression.api.IStringExpression}.
 * 
 * <h2>Logic expressions</h2> 
 * 
 * The packages permits the evaluation of logical functions. It converts strings to an expression tree, which it can evaluate.
 * Parentheses are permitted and expressions can be evaluated with or without operator precedence. 
 * Each expression is converted to a tree once, then evaluated whenever parameters are changed.
 * 
 * Expression have the following format:
 * <pre>
 * format := [parenthesis] {expression} [parenthesis] [{logic} [parenthesis] {expression} [parenthesis]]
 * expression := @{context}@{operand}{value} or @{context}@{operand}@{context}@
 * logic := {|}|{&}
 * parenthesis := {(}|{)}
 * context := any global or window context 
 * value := strings or numbers
 * operand := eq{=}, gt{>}, le{<}, not{^!}
 * </pre>
 *  
 * Examples:
 * <pre>
 * ((@a@='5' | @b@!@c@) & @d@>3)| (   @x@<'10'& (@y@!@z@) )
 * @AD_Table_ID@=14 | @Language@!GERGER 
 * @PriceLimit@>10 | @PriceList@>@PriceActual@
 * @Name@>J Strings may be in single quotes (optional)
 * </pre>
 * 
 * @see org.adempiere.ad.expression.api.ILogicExpression
 */
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


