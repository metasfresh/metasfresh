package org.adempiere.ad.validationRule.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.NamePairPredicates;
import org.compiere.util.ValueNamePair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Immutable composite validation rule consist of a collection of child validation rules.
 *
 * @author tsa
 */
public final class CompositeValidationRule implements IValidationRule
{
	public static IValidationRule compose(final IValidationRule rule1, final IValidationRule rule2)
	{
		return builder().add(rule1).add(rule2).build();
	}

	public static Builder builder()
	{
		return new Builder();
	}

	private final ImmutableList<IValidationRule> rules;
	@Getter private final boolean immutable;
	@Getter @NonNull private final IStringExpression prefilterWhereClause;
	@Getter @NonNull private final INamePairPredicate postQueryPredicates;
	@Getter @NonNull private final ImmutableSet<String> dependsOnTableNames;

	@Getter @NonNull private final ImmutableSet<String> allParameters;

	private CompositeValidationRule(final Builder builder)
	{
		this.rules = ImmutableList.copyOf(builder.rules); // at this point, we assume that we have more than one rule

		this.immutable = this.rules.stream().allMatch(IValidationRule::isImmutable);
		this.prefilterWhereClause = buildPrefilterWhereClause(this.rules);
		this.postQueryPredicates = buildPostQueryPredicates(this.rules);
		this.dependsOnTableNames = this.rules.stream()
				.flatMap(rule -> rule.getDependsOnTableNames().stream())
				.collect(ImmutableSet.toImmutableSet());
		this.allParameters = ImmutableSet.<String>builder()
				.addAll(this.prefilterWhereClause.getParameterNames())
				.addAll(this.postQueryPredicates.getParameters(null))
				.build();
	}

	private static IStringExpression buildPrefilterWhereClause(@NonNull final ImmutableList<IValidationRule> rules)
	{
		final CompositeStringExpression.Builder builder = CompositeStringExpression.builder();
		for (final IValidationRule rule : rules)
		{
			final IStringExpression ruleWhereClause = rule.getPrefilterWhereClause();
			if (ruleWhereClause == null || ruleWhereClause == IStringExpression.NULL)
			{
				continue;
			}

			builder.appendIfNotEmpty(" AND ");
			builder.append("(").append(ruleWhereClause).append(")");
		}

		return builder.build();
	}

	private static INamePairPredicate buildPostQueryPredicates(@NonNull final ImmutableList<IValidationRule> rules)
	{
		final NamePairPredicates.Composer builder = NamePairPredicates.compose();
		for (final IValidationRule rule : rules)
		{
			builder.add(rule.getPostQueryFilter());
		}

		return builder.build();
	}

	private List<IValidationRule> getValidationRules()
	{
		return rules;
	}

	public boolean isEmpty()
	{
		return rules.isEmpty();
	}

	@Override
	public List<ValueNamePair> getExceptionTableAndColumns()
	{
		final List<ValueNamePair> exceptionTableAndColumns = new ArrayList<>();

		for (final IValidationRule rule : rules)
		{
			exceptionTableAndColumns.addAll(rule.getExceptionTableAndColumns());
		}

		return exceptionTableAndColumns;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(rules)
				.toString();
	}

	public static List<IValidationRule> unbox(@Nullable final IValidationRule rule)
	{
		if (rule == null || NullValidationRule.isNull(rule))
		{
			return ImmutableList.of();
		}

		final ArrayList<IValidationRule> result = new ArrayList<>();
		unboxAndAppendToList(rule, result);

		return result;
	}

	public static List<IValidationRule> unbox(@Nullable final Collection<IValidationRule> rules)
	{
		if (rules == null || rules.isEmpty())
		{
			return ImmutableList.of();
		}

		final ArrayList<IValidationRule> result = new ArrayList<>();
		for (final IValidationRule rule : rules)
		{
			unboxAndAppendToList(rule, result);
		}

		return result;
	}

	private static void unboxAndAppendToList(@Nullable final IValidationRule rule, @NonNull final ArrayList<IValidationRule> list)
	{
		if (rule instanceof CompositeValidationRule)
		{
			final CompositeValidationRule compositeRule = (CompositeValidationRule)rule;
			for (final IValidationRule childRule : compositeRule.rules)
			{
				unboxAndAppendToList(childRule, list);
			}
		}
		else if (rule != null && !NullValidationRule.isNull(rule))
		{
			list.add(rule);
		}
	}

	@SuppressWarnings("UnusedReturnValue")
	public static final class Builder
	{
		private final List<IValidationRule> rules = new ArrayList<>();

		private Builder() {}

		public IValidationRule build()
		{
			if (rules.isEmpty())
			{
				return NullValidationRule.instance;
			}
			else if (rules.size() == 1)
			{
				return rules.get(0);
			}
			else
			{
				return new CompositeValidationRule(this);
			}
		}

		public Builder addAll(@NonNull final Collection<IValidationRule> rules)
		{
			rules.forEach(this::add);
			return this;
		}

		public Builder add(final IValidationRule rule)
		{
			add(rule, false);
			return this;
		}

		public Builder addExploded(final IValidationRule rule)
		{
			add(rule, true);
			return this;
		}

		private Builder add(final IValidationRule rule, final boolean explodeComposite)
		{
			// Don't add null rules
			if (NullValidationRule.isNull(rule))
			{
				return this;
			}

			// Don't add if already exists
			if (rules.contains(rule))
			{
				return this;
			}

			if (explodeComposite && rule instanceof CompositeValidationRule)
			{
				final CompositeValidationRule compositeRule = (CompositeValidationRule)rule;
				compositeRule.getValidationRules().forEach(includedRule -> add(includedRule, explodeComposite));
			}
			else
			{
				rules.add(rule);
			}

			return this;
		}
	}
}
