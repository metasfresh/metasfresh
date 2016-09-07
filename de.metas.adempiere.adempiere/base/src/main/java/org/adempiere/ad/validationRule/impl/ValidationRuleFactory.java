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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleDAO;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GridRowCtx;
import org.adempiere.util.Services;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.Lookup;
import org.compiere.model.X_AD_Val_Rule;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

public class ValidationRuleFactory implements IValidationRuleFactory
{
	private final Logger logger = LogManager.getLogger(getClass());

	private static final int ROWINDEX_None = -1;

	private final Map<String, List<Class<? extends IValidationRule>>> tableRuleClassesMap = new HashMap<String, List<Class<? extends IValidationRule>>>();

	@Override
	public void registerTableValidationRule(final String tableName, final Class<? extends IValidationRule> ruleClass)
	{
		Check.assume(ruleClass != null, "ruleClass not null");
		List<Class<? extends IValidationRule>> rules = tableRuleClassesMap.get(tableName);
		if (rules == null)
		{
			rules = new ArrayList<Class<? extends IValidationRule>>();
			tableRuleClassesMap.put(tableName, rules);
		}
		if (!rules.contains(ruleClass))
		{
			rules.add(ruleClass);
			logger.info("Registered rule class " + ruleClass + " for " + tableName);
		}
	}

	@Override
	public void unregisterTableValidationRule(final String tableName, final Class<? extends IValidationRule> ruleClass)
	{
		Check.assume(ruleClass != null, "ruleClass not null");
		final List<Class<? extends IValidationRule>> rules = tableRuleClassesMap.get(tableName);
		if (rules == null)
		{
			return;
		}

		final boolean removed = rules.remove(ruleClass);

		if (removed)
		{
			logger.info("Unregistered rule class " + ruleClass + " for " + tableName);
		}
	}

	@Override
	public IValidationRule create(final Properties ctx, final String tableName, final int adValRuleId)
	{
		final CompositeValidationRule.Builder builder = CompositeValidationRule.builder();

		//
		// Add primary validation rule
		if(adValRuleId > 0)
		{
			final I_AD_Val_Rule valRule = Services.get(IValidationRuleDAO.class).retriveValRule(ctx, adValRuleId);
			final IValidationRule validationRule = create(tableName, valRule);
			builder.addExploded(validationRule);
		}

		//
		// Add table specific validation rules
		for (final IValidationRule tableValidationRule : retrieveTableValidationRules(tableName))
		{
			builder.addExploded(tableValidationRule);
		}

		return builder.build();
	}

	private IValidationRule create(final String tableName, final I_AD_Val_Rule valRule)
	{
		if (valRule == null)
		{
			return NullValidationRule.instance;
		}

		final String type = valRule.getType();
		if (X_AD_Val_Rule.TYPE_SQL.equals(type))
		{
			return createSQLValidationRule(valRule.getName(), valRule.getCode());
		}
		else if (X_AD_Val_Rule.TYPE_Java.equals(type))
		{
			return createJavaValidationRule(valRule);
		}
		else if (X_AD_Val_Rule.TYPE_Composite.equals(type))
		{
			return createCompositeValidationRule(tableName, valRule);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Type@:" + type + " (@AD_Val_Rule_ID@:" + valRule.getName() + ")");
		}
	}

	private IValidationRule createJavaValidationRule(final I_AD_Val_Rule valRule)
	{
		final String classname = valRule.getClassname();
		if (Check.isEmpty(classname, true))
		{
			logger.warn("No Classname found for " + valRule.getName());
			return NullValidationRule.instance;
		}

		final IValidationRule rule = Util.getInstance(IValidationRule.class, classname);
		return rule;
	}

	@Override
	public IValidationRule createSQLValidationRule(final String whereClause)
	{
		final String name = null; // N/A
		return createSQLValidationRule(name, whereClause);
		
	}
	
	public IValidationRule createSQLValidationRule(final String name, final String whereClause)
	{
		if (Check.isEmpty(whereClause, true))
		{
			return NullValidationRule.instance;
		}
		
		return new SQLValidationRule(name, whereClause);
	}

	private IValidationRule createCompositeValidationRule(final String tableName, final I_AD_Val_Rule valRule)
	{
		final CompositeValidationRule.Builder builder = CompositeValidationRule.builder();
		for (final I_AD_Val_Rule childValRule : Services.get(IValidationRuleDAO.class).retrieveChildValRules(valRule))
		{
			final IValidationRule childRule = create(tableName, childValRule);
			builder.addExploded(childRule);
		}

		return builder.build();
	}

	private List<IValidationRule> retrieveTableValidationRules(final String tableName)
	{
		final List<Class<? extends IValidationRule>> ruleClasses = tableRuleClassesMap.get(tableName);
		if (ruleClasses == null || ruleClasses.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<IValidationRule> rules = new ArrayList<>(ruleClasses.size());
		for (final Class<? extends IValidationRule> ruleClass : ruleClasses)
		{
			try
			{
				final IValidationRule rule = ruleClass.newInstance();
				rules.add(rule);
			}
			catch (final Exception e)
			{
				logger.warn("Cannot instantiate rule class " + ruleClass + " [SKIP]", e);
			}
		}
		return rules;
	}

	@Override
	public IValidationContext createValidationContext(final Properties ctx, final int windowNo, final int tabNo, final String tableName)
	{
		return new GridTabValidationContext(ctx, windowNo, tabNo, tableName);
	}

	@Override
	public IValidationContext createValidationContext(final Properties ctx, final String tableName, final GridTab gridTab, final int rowIndex)
	{
		final GridRowCtx ctxToUse = new GridRowCtx(ctx, gridTab, rowIndex);
		final int windowNo = gridTab.getWindowNo();
		final int tabNo = gridTab.getTabNo();

		return createValidationContext(ctxToUse, windowNo, tabNo, tableName);
	}

	@Override
	public IValidationContext createValidationContext(final GridField gridField)
	{
		Check.assumeNotNull(gridField, "gridField not null");

		final GridTab gridTab = gridField.getGridTab();
		// Check.assumeNotNull(gridTab, "gridTab not null");
		if (gridTab == null)
		{
			return createValidationContext(gridField, ROWINDEX_None);
		}

		// If GridTab is not open we don't have an Row Index anyways
		// Case: open a window with High Volume. Instead of getting the window opened,
		// you will get a popup to filter records before window actually opens.
		// If you try to set on of the filtering fields you will get "===========> GridTab.verifyRow: Table not open".
		if (!gridTab.isOpen())
		{
			return createValidationContext(gridField, ROWINDEX_None);
		}

		final int rowIndex = gridTab.getCurrentRow();
		return createValidationContext(gridField, rowIndex);
	}

	@Override
	public IValidationContext createValidationContext(final GridField gridField, final int rowIndex)
	{
		final Properties ctx = Env.getCtx();

		String tableName = null;

		final Lookup lookup = gridField.getLookup();
		if (lookup != null)
		{
			final IValidationContext parentEvalCtx = lookup.getValidationContext();
			if (parentEvalCtx != null)
			{
				tableName = parentEvalCtx.getTableName();
			}
		}

		if (tableName == null)
		{
			return IValidationContext.DISABLED;
		}

		//
		// Check if is a Process Parameter
		if (gridField.getVO().isProcessParameter())
		{
			final IValidationContext evalCtx = createValidationContext(ctx, gridField.getWindowNo(), Env.TAB_None, tableName);
			return evalCtx;
		}

		final GridTab gridTab = gridField.getGridTab();
		if (gridTab != null)
		{
			final IValidationContext evalCtx = createValidationContext(ctx, tableName, gridTab, rowIndex);
			return evalCtx;
		}

		return IValidationContext.NULL;
	}

	@Override
	public IValidationContext createValidationContext(final Evaluatee evaluatee)
	{
		return new EvaluateeValidationContext(evaluatee);
	}

}
