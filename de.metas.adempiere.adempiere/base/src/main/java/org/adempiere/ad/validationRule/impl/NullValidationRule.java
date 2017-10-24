package org.adempiere.ad.validationRule.impl;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationRule;
import org.apache.commons.lang3.NotImplementedException;
import org.compiere.util.ValueNamePair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Null validation rule. A null validation rule, does nothing. Is not filtering any records.
 * 
 * @author tsa
 * 
 */
public final class NullValidationRule implements IValidationRule
{
	public static final NullValidationRule instance = new NullValidationRule();

	public static final boolean isNull(final IValidationRule rule)
	{
		return rule == null || rule == instance;
	}

	private NullValidationRule()
	{
		super();
	}

	@Override
	public boolean isImmutable()
	{
		return true;
	}

	@Override
	public Set<String> getAllParameters()
	{
		return ImmutableSet.of();
	}

	@Override
	public IStringExpression getPrefilterWhereClause()
	{
		return IStringExpression.NULL;
	}

	@Override
	public INamePairPredicate getPostQueryFilter()
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
		throw new NotImplementedException("There is no implementation for registering esceptions in the null validation rule class: " + this);
	}
};
