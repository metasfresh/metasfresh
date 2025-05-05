package org.adempiere.ad.validationRule.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Immutable SQL Validation Rule is a validation rule which has only an SQL Where Clause.
 *
 * @author tsa
 */
@Value
public class SQLValidationRule implements IValidationRule
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
		final String sqlWhereClauseNorm = StringUtils.trimBlankToNull(sqlWhereClause);
		if (sqlWhereClauseNorm == null)
		{
			return NullValidationRule.instance;
		}

		return ofSqlWhereClause(sqlWhereClauseNorm);
	}

	public static IValidationRule ofSqlWhereClause(@NonNull final String sqlWhereClause)
	{
		final String sqlWhereClauseNorm = StringUtils.trimBlankToNull(sqlWhereClause);
		if (sqlWhereClauseNorm == null)
		{
			throw new AdempiereException("sqlWhereClause cannot be blank");
		}

		final IStringExpression sqlWhereClauseExpr = IStringExpression.compileOrDefault(sqlWhereClauseNorm, IStringExpression.NULL);
		if (sqlWhereClauseExpr == null || sqlWhereClauseExpr.isNullExpression())
		{
			return NullValidationRule.instance;
		}

		return builder().prefilterWhereClause(sqlWhereClauseExpr).build();
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
