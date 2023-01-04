package org.adempiere.ad.validationRule.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.IValidationRule;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Immutable SQL Validation Rule is a validation rule which has only an SQL Where Clause.
 *
 * @author tsa
 */
@Value
		/* package */ class SQLValidationRule implements IValidationRule
{
	String name;
	@NonNull IStringExpression prefilterWhereClause;
	@NonNull ImmutableSet<String> dependsOnTableNames;

	@Builder
	private SQLValidationRule(
			final String name,
			@NonNull final IStringExpression prefilterWhereClause,
			@Singular final Set<String> dependsOnTableNames)
	{
		Check.assume(!prefilterWhereClause.isNullExpression(), "prefilterWhereClause is not empty");
		this.name = name;
		this.prefilterWhereClause = prefilterWhereClause;
		this.dependsOnTableNames = dependsOnTableNames != null ? ImmutableSet.copyOf(dependsOnTableNames) : ImmutableSet.of();
	}

	public static IValidationRule ofNullableSqlWhereClause(@Nullable final String sqlWhereClause)
	{
		if (sqlWhereClause == null || Check.isBlank(sqlWhereClause))
		{
			return NullValidationRule.instance;
		}

		final IStringExpression whereClauseExpression = IStringExpression.compileOrDefault(sqlWhereClause, IStringExpression.NULL);
		if (whereClauseExpression == null || whereClauseExpression.isNullExpression())
		{
			return NullValidationRule.instance;
		}

		return builder().prefilterWhereClause(whereClauseExpression).build();
	}

	@Override
	public Set<String> getAllParameters()
	{
		return prefilterWhereClause.getParameterNames();
		// NOTE: we are not checking post-filter params because that's always empty
	}

	@Override
	public boolean isImmutable()
	{
		return getAllParameters().isEmpty();
	}
}
