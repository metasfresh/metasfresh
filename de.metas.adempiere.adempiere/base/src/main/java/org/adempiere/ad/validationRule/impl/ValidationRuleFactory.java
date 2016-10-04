package org.adempiere.ad.validationRule.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleDAO;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GridRowCtx;
import org.adempiere.util.Services;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
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

	private final Map<String, CopyOnWriteArrayList<IValidationRule>> tableRulesMap = new ConcurrentHashMap<>();
	
	private final Map<String, IValidationRule> classname2rulesCache = new ConcurrentHashMap<>();

	@Override
	public void registerTableValidationRule(final String tableName, IValidationRule rule)
	{
		Check.assume(rule != null, "rule not null");
		
		final boolean added = tableRulesMap
				.computeIfAbsent(tableName, key->new CopyOnWriteArrayList<>())
				.addIfAbsent(rule);
		if (added)
		{
			logger.info("Registered rule class {} for {}", rule, tableName);
		}
	}

	@Override
	public IValidationRule create(final String tableName, final int adValRuleId)
	{
		final CompositeValidationRule.Builder builder = CompositeValidationRule.builder();

		//
		// Add primary validation rule
		if(adValRuleId > 0)
		{
			final I_AD_Val_Rule valRule = Services.get(IValidationRuleDAO.class).retriveValRule(adValRuleId);
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
			logger.warn("No Classname found for {}", valRule.getName());
			return NullValidationRule.instance;
		}
		
		return classname2rulesCache.computeIfAbsent(classname, newClassname -> Util.getInstance(IValidationRule.class, newClassname));
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
		final List<IValidationRule> rules = tableRulesMap.get(tableName);
		if (rules == null || rules.isEmpty())
		{
			return ImmutableList.of();
		}
		
		return ImmutableList.copyOf(rules);
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
		// Check if is a Process Parameter/Form field
		final GridFieldVO gridFieldVO = gridField.getVO();
		if (gridFieldVO.isProcessParameter() || gridFieldVO.isFormField())
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
