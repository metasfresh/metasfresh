package de.metas.ui.web.window.descriptor.sql;

import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationRule;

import javax.annotation.Nullable;
import java.util.Set;

@Value
class SqlLookupFilter
{
	@Nullable LookupDescriptorProvider.LookupScope onlyScope;
	@Nullable IStringExpression sqlWhereClause;
	@Nullable INamePairPredicate postQueryPredicate;
	@Getter @NonNull ImmutableSet<String> dependsOnTableNames;

	@Getter @NonNull ImmutableSet<String> dependsOnFieldNames;

	@lombok.Builder
	private SqlLookupFilter(
			@Nullable final LookupDescriptorProvider.LookupScope onlyScope,
			@Nullable final IStringExpression sqlWhereClause,
			@Nullable final INamePairPredicate postQueryPredicate,
			@Nullable final Set<String> dependsOnTableNames)
	{
		this.onlyScope = onlyScope;
		this.sqlWhereClause = sqlWhereClause;
		this.postQueryPredicate = postQueryPredicate;
		this.dependsOnTableNames = dependsOnTableNames != null ? ImmutableSet.copyOf(dependsOnTableNames) : ImmutableSet.of();

		this.dependsOnFieldNames = computeDependsOnFieldNames(sqlWhereClause, postQueryPredicate);
	}

	private static ImmutableSet<String> computeDependsOnFieldNames(
			final @Nullable IStringExpression sqlWhereClause,
			final @Nullable INamePairPredicate postQueryPredicate)
	{
		final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
		if (sqlWhereClause != null)
		{
			builder.addAll(sqlWhereClause.getParameterNames());
		}
		if (postQueryPredicate != null)
		{
			builder.addAll(postQueryPredicate.getParameters(null));
		}

		return builder.build();
	}

	static SqlLookupFilter of(@NonNull final IValidationRule valRule, @Nullable final LookupDescriptorProvider.LookupScope onlyScope)
	{
		return builder()
				.onlyScope(onlyScope)
				.sqlWhereClause(valRule.getPrefilterWhereClause())
				.postQueryPredicate(valRule.getPostQueryFilter())
				.dependsOnTableNames(valRule.getDependsOnTableNames())
				.build();
	}

	public boolean isMatchingScope(@NonNull final LookupDescriptorProvider.LookupScope scope)
	{
		return onlyScope == null || onlyScope.equals(scope);
	}

	public boolean isMatchingForAvailableParameterNames(@Nullable final ImmutableSet<String> availableParameterNames)
	{
		// TODO: instead of checking all `dependsOnFieldNames` i think we shall remove parameters that will be always provided (e.g. FilterSql etc)
		return availableParameterNames == null || availableParameterNames.containsAll(this.dependsOnFieldNames);
	}
}
