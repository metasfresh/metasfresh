package de.metas.aggregation.api;

/*
 * #%L
 * de.metas.aggregation
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
import org.compiere.util.Evaluatee;

/**
 * Aggregation Item (part) model
 *
 * @author tsa
 *
 */
public interface IAggregationItem
{
	enum Type
	{
		ModelColumn,
		Attribute
	}

	String getId();

	/** @return item type */
	Type getType();

	/** @return model column name */
	String getColumnName();

	/** @return model column display type */
	int getDisplayType();

	IAggregationAttribute getAttribute();

	/** @return logic to evaluate if this item/part shall be included in aggregation; never returns null */
	ILogicExpression getIncludeLogic();

	/**
	 * Checks if this item shall be include based on {@link #getIncludeLogic()} and given evaluation context.
	 * 
	 * @param context evaluation context or <code>null</code>
	 * @return <code>true</code> if this item shall be included
	 */
	boolean isInclude(final Evaluatee context);

}
