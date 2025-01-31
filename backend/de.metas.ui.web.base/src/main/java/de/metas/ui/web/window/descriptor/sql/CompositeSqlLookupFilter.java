package de.metas.ui.web.window.descriptor.sql;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.NamePairPredicates;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode
@ToString(of = { "filters", "onlyScope", "onlyForAvailableParameterNames" })
class CompositeSqlLookupFilter
{
	@NonNull private final ImmutableList<SqlLookupFilter> allFilters;

	@Getter @Nullable private final LookupDescriptorProvider.LookupScope onlyScope;
	@Getter @Nullable private final ImmutableSet<String> onlyForAvailableParameterNames;

	//
	// Computed values:
	@NonNull private final ImmutableList<SqlLookupFilter> filters;
	@Getter @NonNull private final IStringExpression sqlWhereClause;
	@Getter @NonNull private final INamePairPredicate postQueryPredicate;
	@Getter @NonNull ImmutableSet<String> dependsOnTableNames;

	@Getter @NonNull ImmutableSet<String> dependsOnFieldNames;

	private CompositeSqlLookupFilter(
			@NonNull final List<SqlLookupFilter> allFilters,
			@Nullable final LookupDescriptorProvider.LookupScope onlyScope,
			@Nullable final Set<String> onlyForAvailableParameterNames)
	{
		this.allFilters = ImmutableList.copyOf(allFilters);
		this.onlyScope = onlyScope;
		this.onlyForAvailableParameterNames = onlyForAvailableParameterNames != null ? ImmutableSet.copyOf(onlyForAvailableParameterNames) : null;

		this.filters = computeActiveFilters(this.allFilters, this.onlyScope, this.onlyForAvailableParameterNames);
		this.sqlWhereClause = computeSqlWhereClause(this.filters);
		this.postQueryPredicate = computePostQueryPredicate(this.filters);
		this.dependsOnTableNames = computeDependsOnTableNames(this.filters);
		this.dependsOnFieldNames = computeDependsOnFieldNames(this.filters);
	}

	public static CompositeSqlLookupFilter ofFilters(@NonNull final List<SqlLookupFilter> allFilters)
	{
		return new CompositeSqlLookupFilter(allFilters, null, null);
	}

	@NonNull
	private static ImmutableList<SqlLookupFilter> computeActiveFilters(
			final @NonNull ImmutableList<SqlLookupFilter> allFilters,
			final @Nullable LookupDescriptorProvider.LookupScope onlyScope,
			final @Nullable ImmutableSet<String> availableParameterNames)
	{
		final ImmutableList.Builder<SqlLookupFilter> result = ImmutableList.builder();
		for (SqlLookupFilter filter : allFilters)
		{
			if (onlyScope != null && !filter.isMatchingScope(onlyScope))
			{
				continue;
			}

			if (!filter.isMatchingForAvailableParameterNames(availableParameterNames))
			{
				continue;
			}

			result.add(filter);
		}

		return result.build();
	}

	private static IStringExpression computeSqlWhereClause(@NonNull final ImmutableList<SqlLookupFilter> filters)
	{
		if (filters.isEmpty())
		{
			return IStringExpression.NULL;
		}

		final CompositeStringExpression.Builder builder = IStringExpression.composer();

		for (SqlLookupFilter filter : filters)
		{
			final IStringExpression sqlWhereClause = filter.getSqlWhereClause();
			if (sqlWhereClause != null && !sqlWhereClause.isNullExpression())
			{
				builder.appendIfNotEmpty("\n AND ").append("(").append(sqlWhereClause).append(")");
			}
		}

		return builder.build();
	}

	private static INamePairPredicate computePostQueryPredicate(@NonNull final ImmutableList<SqlLookupFilter> filters)
	{
		if (filters.isEmpty())
		{
			return NamePairPredicates.ACCEPT_ALL;
		}

		final NamePairPredicates.Composer builder = NamePairPredicates.compose();

		for (SqlLookupFilter filter : filters)
		{
			final INamePairPredicate postQueryPredicate = filter.getPostQueryPredicate();
			if (postQueryPredicate != null)
			{
				builder.add(postQueryPredicate);
			}
		}

		return builder.build();
	}

	private static ImmutableSet<String> computeDependsOnTableNames(final ImmutableList<SqlLookupFilter> filters)
	{
		return filters.stream()
				.flatMap(filter -> filter.getDependsOnTableNames().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	private static ImmutableSet<String> computeDependsOnFieldNames(final ImmutableList<SqlLookupFilter> filters)
	{
		return filters.stream()
				.flatMap(filter -> filter.getDependsOnFieldNames().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	public CompositeSqlLookupFilter withOnlyScope(@Nullable LookupDescriptorProvider.LookupScope onlyScope)
	{
		return !Objects.equals(this.onlyScope, onlyScope)
				? new CompositeSqlLookupFilter(this.allFilters, onlyScope, this.onlyForAvailableParameterNames)
				: this;
	}

	public CompositeSqlLookupFilter withOnlyForAvailableParameterNames(@Nullable Set<String> onlyForAvailableParameterNames)
	{
		return !Objects.equals(this.onlyForAvailableParameterNames, onlyForAvailableParameterNames)
				? new CompositeSqlLookupFilter(this.allFilters, this.onlyScope, onlyForAvailableParameterNames)
				: this;
	}

}
