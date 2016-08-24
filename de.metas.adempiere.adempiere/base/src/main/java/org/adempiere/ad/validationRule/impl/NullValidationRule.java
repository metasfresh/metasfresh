package org.adempiere.ad.validationRule.impl;

import java.util.List;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.compiere.util.NamePair;

import com.google.common.collect.ImmutableList;

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
	public String getPrefilterWhereClause(IValidationContext evalCtx)
	{
		return null;
	}

	@Override
	public boolean accept(IValidationContext evalCtx, NamePair item)
	{
		return true;
	}

	@Override
	public List<String> getParameters()
	{
		return ImmutableList.of();
	}

	@Override
	public NamePair getValidValue(Object currentValue)
	{
		return null;
	}
};
