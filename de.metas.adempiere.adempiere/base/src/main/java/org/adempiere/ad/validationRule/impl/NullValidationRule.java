package org.adempiere.ad.validationRule.impl;

import java.util.Set;

import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.INamePairPredicate;

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
};
