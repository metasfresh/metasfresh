package org.adempiere.ad.validationRule.impl;

import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * Immutable SQL Validation Rule is a validation rule which has only an SQL Where Clause.
 *
 * @author tsa
 *
 */
@Value
/* package */final class SQLValidationRule implements IValidationRule
{
	private final String name;
	private final IStringExpression prefilterWhereClause;
	private ImmutableSet<String> dependsOnTableNames;

	@Builder
	private SQLValidationRule(
			final String name,
			final String prefilterWhereClause,
			@Singular final Set<String> dependsOnTableNames)
	{
		Check.assumeNotEmpty(prefilterWhereClause, "prefilterWhereClause is not empty");
		this.name = name;
		this.prefilterWhereClause = Services.get(IExpressionFactory.class)
				.compileOrDefault(prefilterWhereClause, IStringExpression.NULL, IStringExpression.class);
		this.dependsOnTableNames = dependsOnTableNames != null ? ImmutableSet.copyOf(dependsOnTableNames) : ImmutableSet.of();
	}

	@Override
	public Set<String> getAllParameters()
	{
		return prefilterWhereClause.getParameterNames();
	}

	@Override
	public boolean isImmutable()
	{
		return prefilterWhereClause.getParameterNames().isEmpty();
	}

	@Override
	public Set<String> getDependsOnTableNames()
	{
		return dependsOnTableNames;
	}
}
