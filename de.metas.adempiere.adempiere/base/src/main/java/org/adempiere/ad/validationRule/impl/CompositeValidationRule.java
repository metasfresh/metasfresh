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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.util.Check;
import org.compiere.util.NamePair;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

/**
 * Immutable composite validation rule consist of a collection of child validation rules.
 *
 * @author tsa
 *
 */
public final class CompositeValidationRule implements IValidationRule
{
	public static final IValidationRule compose(final IValidationRule rule1, final IValidationRule rule2)
	{
		return builder()
				.add(rule1)
				.add(rule2)
				.build();
	}

	public static final Builder builder()
	{
		return new Builder();
	}

	private final List<IValidationRule> rules;
	private final boolean immutable;
	private final List<String> parameters;

	private CompositeValidationRule(final Builder builder)
	{
		super();
		rules = ImmutableList.copyOf(builder.rules); // at this point, we assume that we have more than one rule
		immutable = builder.immutable;
		parameters = ImmutableList.copyOf(builder.parameters);
	}

	public List<IValidationRule> getValidationRules()
	{
		return rules;
	}

	public boolean isEmpty()
	{
		return rules.isEmpty();
	}

	/**
	 * @return true if all child validation rules are immutable
	 */
	@Override
	public boolean isImmutable()
	{
		return immutable;
	}

	@Override
	public String getPrefilterWhereClause(final IValidationContext evalCtx)
	{
		final StringBuilder wc = new StringBuilder();
		for (final IValidationRule rule : rules)
		{
			final String ruleWhereClause = rule.getPrefilterWhereClause(evalCtx);
			if (Check.isEmpty(ruleWhereClause, true))
			{
				continue;
			}

			if (IValidationRule.WHERECLAUSE_ERROR == ruleWhereClause)
			{
				return IValidationRule.WHERECLAUSE_ERROR;
			}

			if (wc.length() > 0)
			{
				wc.append(" AND ");
			}
			wc.append("(").append(ruleWhereClause).append(")");
		}

		return wc.toString();
	}

	@Override
	public List<String> getParameters()
	{
		return parameters;
	}

	@Override
	public boolean accept(final IValidationContext evalCtx, final NamePair item)
	{
		for (final IValidationRule rule : rules)
		{
			if (!rule.accept(evalCtx, item))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(rules)
				.toString();
	}

	/**
	 * For composite validation rules, returns the first not null valid value of its children.
	 *
	 * @param currentValue Current value of the field to be changed.
	 * @return
	 */
	@Override
	public NamePair getValidValue(final Object currentValue)
	{
		for (final IValidationRule rule : rules)
		{
			final NamePair value = rule.getValidValue(currentValue);
			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	public static final class Builder
	{
		private final List<IValidationRule> rules = new ArrayList<>();
		private final LinkedHashSet<String> parameters = new LinkedHashSet<>();
		private boolean immutable = true;

		private Builder()
		{
			super();
		}

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

		public Builder add(final IValidationRule rule)
		{
			final boolean explodeComposite = false;
			add(rule, explodeComposite);
			return this;
		}

		public Builder addExploded(final IValidationRule rule)
		{
			final boolean explodeComposite = true;
			add(rule, explodeComposite);
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
				compositeRule.getValidationRules().stream().forEach(includedRule -> add(includedRule, explodeComposite));
			}
			else
			{
				rules.add(rule);
				immutable = immutable && rule.isImmutable();
				parameters.addAll(rule.getParameters());
			}

			return this;
		}
	}
}
