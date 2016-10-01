package org.compiere.apps.search;

import org.compiere.model.MQuery.Operator;

import com.google.common.base.MoreObjects;

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

final class UserQueryRestriction implements IUserQueryRestriction
{
	private Join join = Join.AND;
	private IUserQueryField searchField;
	private Operator operator = Operator.EQUAL;
	private Object value;
	private Object valueTo;

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("join", join)
				.add("searchField", searchField)
				.add("operator", operator)
				.add("value", value)
				.add("valueTo", valueTo)
				.toString();
	}

	@Override
	public Join getJoin()
	{
		return join;
	}

	@Override
	public void setJoin(final Join join)
	{
		this.join = join;
	}

	@Override
	public IUserQueryField getSearchField()
	{
		return searchField;
	}

	@Override
	public void setSearchField(final IUserQueryField searchField)
	{
		this.searchField = searchField;
	}

	@Override
	public Operator getOperator()
	{
		return operator;
	}

	@Override
	public void setOperator(final Operator operator)
	{
		this.operator = operator;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public void setValue(final Object value)
	{
		this.value = value;
	}

	@Override
	public Object getValueTo()
	{
		return valueTo;
	}

	@Override
	public void setValueTo(final Object valueTo)
	{
		this.valueTo = valueTo;
	}

}
