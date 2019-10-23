package org.compiere.apps.search;

import org.compiere.model.MQuery.Operator;

import lombok.Data;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Data
final class UserQueryRestriction implements IUserQueryRestriction
{
	private Join join = Join.AND;
	private IUserQueryField searchField;
	private Operator operator = Operator.EQUAL;
	private Object value;
	private Object valueTo;
	private boolean mandatory;
	private boolean internalParameter;

	@Override
	public boolean isEmpty()
	{
		// NOTE: don't check if getJoin() == null because that's always set
		// NOTE: don't check if getOperator() == null because that's always set
		return getSearchField() == null
				&& getValue() == null
				&& getValueTo() == null;
	}

	public boolean hasNullValues()
	{
		if (operator != null && operator.isRangeOperator())
		{
			return value == null || valueTo == null;
		}
		else
		{
			return value == null;
		}
	}
}
