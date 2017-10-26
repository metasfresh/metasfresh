package org.adempiere.ad.validationRule.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.util.Services;
import org.compiere.util.ValueNamePair;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

/**
 * Immutable SQL Validation Rule is a validation rule which has only an SQL Where Clause.
 *
 * @author tsa
 *
 */
/* package */final class SQLValidationRule implements IValidationRule
{
	private final String name;
	private final IStringExpression whereClause;

	public SQLValidationRule(final String name, final String whereClause)
	{
		super();
		this.name = name;
		this.whereClause = Services.get(IExpressionFactory.class)
				.compileOrDefault(whereClause, IStringExpression.NULL, IStringExpression.class);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", name)
				.add("whereClause", whereClause)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(whereClause, name);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}

		final SQLValidationRule other = (SQLValidationRule)obj;
		return Objects.equals(name, other.name)
				&& Objects.equals(whereClause, other.whereClause);
	}

	@Override
	public Set<String> getAllParameters()
	{
		return whereClause.getParameterNames();
	}

	@Override
	public boolean isImmutable()
	{
		return whereClause.getParameterNames().isEmpty();
	}

	@Override
	public IStringExpression getPrefilterWhereClause()
	{
		return whereClause;
	}

	@Override
	public final INamePairPredicate getPostQueryFilter()
	{
		return INamePairPredicate.NULL;
	}

	@Override
	public List<ValueNamePair> getExceptionTableAndColumns()
	{
		return ImmutableList.of();
	}

	@Override
	public void registerException(final String tableName, final String columnName)
	{
		throw new UnsupportedOperationException("There is no implementation for registering exceptions in the SQL validation rule class: " + this);
	}

}
