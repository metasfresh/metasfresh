package org.adempiere.ad.validationRule.impl;

<<<<<<< HEAD
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.IValidationRule;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/**
 * Immutable SQL Validation Rule is a validation rule which has only an SQL Where Clause.
 *
 * @author tsa
 *
 */
@Value
<<<<<<< HEAD
/* package */final class SQLValidationRule implements IValidationRule
{
	private final String name;
	private final IStringExpression prefilterWhereClause;
	private ImmutableSet<String> dependsOnTableNames;
=======
public class SQLValidationRule implements IValidationRule
{
	String name;
	@NonNull IStringExpression prefilterWhereClause;
	@NonNull ImmutableSet<String> dependsOnTableNames;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@Builder
	private SQLValidationRule(
			final String name,
<<<<<<< HEAD
			final String prefilterWhereClause,
			@Singular final Set<String> dependsOnTableNames)
	{
		Check.assumeNotEmpty(prefilterWhereClause, "prefilterWhereClause is not empty");
		this.name = name;
		this.prefilterWhereClause = Services.get(IExpressionFactory.class)
				.compileOrDefault(prefilterWhereClause, IStringExpression.NULL, IStringExpression.class);
		this.dependsOnTableNames = dependsOnTableNames != null ? ImmutableSet.copyOf(dependsOnTableNames) : ImmutableSet.of();
	}

=======
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

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Override
	public Set<String> getAllParameters()
	{
		return prefilterWhereClause.getParameterNames();
<<<<<<< HEAD
=======
		// NOTE: we are not checking post-filter params because that's always empty
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public boolean isImmutable()
	{
<<<<<<< HEAD
		return prefilterWhereClause.getParameterNames().isEmpty();
	}

	@Override
	public Set<String> getDependsOnTableNames()
	{
		return dependsOnTableNames;
=======
		return getAllParameters().isEmpty();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
