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
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.util.Check;
import org.compiere.util.NamePair;

/**
 * Composite validation rule consist of a collection of child validation rules.
 * 
 * @author tsa
 * 
 */
public class CompositeValidationRule implements IValidationRule
{
	public static final IValidationRule compose(final IValidationRule rule1, final IValidationRule rule2)
	{
		if (rule1 == null || rule1 == NullValidationRule.instance)
		{
			return rule2 == null ? NullValidationRule.instance : rule2;
		}
		if (rule2 == null || rule2 == NullValidationRule.instance)
		{
			return NullValidationRule.instance;
		}
		
		final CompositeValidationRule composite = new CompositeValidationRule();
		composite.addValidationRule(rule1);
		composite.addValidationRule(rule2);
		return composite;
	}
	
	private final List<IValidationRule> rules = new ArrayList<IValidationRule>();
	private final List<IValidationRule> rulesRO = Collections.unmodifiableList(rules);

	private boolean immutable = true;

	public void addValidationRule(IValidationRule rule)
	{
		if (rule == null)
		{
			return;
		}

		if (rule instanceof NullValidationRule)
		{
			return;
		}

		if (rules.contains(rule))
		{
			return;
		}

		//
		// Update immutable flag
		this.immutable = this.immutable && rule.isImmutable();

		rules.add(rule);
	}

	public List<IValidationRule> getValidationRules()
	{
		return rulesRO;
	}

	@Override
	public boolean isValidationRequired(IValidationContext evalCtx)
	{
		for (final IValidationRule rule : rules)
		{
			if (rule.isValidationRequired(evalCtx))
			{
				return true;
			}
		}

		return false;
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
	public String getPrefilterWhereClause(IValidationContext evalCtx)
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
	public List<String> getParameters(IValidationContext evalCtx)
	{
		final List<String> parameters = new ArrayList<String>();

		//
		// Add parameters
		for (IValidationRule rule : rules)
		{
			for (String p : rule.getParameters(evalCtx))
			{
				if (!parameters.contains(p))
				{
					parameters.add(p);
				}
			}
		}

		return parameters;
	}

	@Override
	public boolean accept(IValidationContext evalCtx, NamePair item)
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
		return "CompositeValidationRule [rules=" + rules + "]";
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
}
