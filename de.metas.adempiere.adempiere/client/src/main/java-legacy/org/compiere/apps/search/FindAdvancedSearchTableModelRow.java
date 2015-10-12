package org.compiere.apps.search;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.MQuery;
import org.compiere.util.ValueNamePair;

import com.google.common.collect.ImmutableMap;

/**
 * Advanced search table row model.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
class FindAdvancedSearchTableModelRow
{
	public static enum Join
	{
		AND("AND"),
		OR("OR");

		private static final ImmutableMap<String, Join> codeToJoin = ImmutableMap.<String, FindAdvancedSearchTableModelRow.Join> builder()
				.put(AND.getCode(), AND)
				.put(OR.getCode(), OR)
				.build();

		private final String code;

		Join(final String code)
		{
			this.code = code;
		}

		public String getCode()
		{
			return code;
		}

		public static final Join forCodeOrAND(final String code)
		{
			final Join join = codeToJoin.get(code);
			return join != null ? join : AND;
		}
	}

	private Join join = Join.AND;
	private FindPanelSearchField searchField;
	private ValueNamePair operator = MQuery.OPERATORS[MQuery.EQUAL_INDEX];
	private Object value;
	private Object valueTo;

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public Join getJoin()
	{
		return join;
	}

	public void setJoin(final Join join)
	{
		this.join = join;
	}

	public FindPanelSearchField getSearchField()
	{
		return searchField;
	}

	public void setSearchField(final FindPanelSearchField searchField)
	{
		this.searchField = searchField;
	}

	public ValueNamePair getOperator()
	{
		return operator;
	}

	public final boolean isBinaryOperator()
	{
		final ValueNamePair operator = getOperator();
		if (operator == null)
		{
			return false;
		}
		return operator.equals(MQuery.OPERATORS[MQuery.BETWEEN_INDEX]);
	}

	public void setOperator(final ValueNamePair operator)
	{
		this.operator = operator;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(final Object value)
	{
		this.value = value;
	}

	public Object getValueTo()
	{
		return valueTo;
	}

	public void setValueTo(final Object valueTo)
	{
		this.valueTo = valueTo;
	}
}
