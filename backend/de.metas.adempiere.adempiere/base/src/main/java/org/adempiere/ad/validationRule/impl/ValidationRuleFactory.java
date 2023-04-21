package org.adempiere.ad.validationRule.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleDAO;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GridRowCtx;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTab;
import org.compiere.model.Lookup;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Util;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ValidationRuleFactory implements IValidationRuleFactory
{
	private final Logger logger = LogManager.getLogger(getClass());
	private final IValidationRuleDAO validationRuleDAO = Services.get(IValidationRuleDAO.class);

	private static final int ROWINDEX_None = -1;

	private final ConcurrentHashMap<String, CopyOnWriteArrayList<IValidationRule>> tableRulesMap = new ConcurrentHashMap<>();

	private final Map<String, IValidationRule> classname2rulesCache = new ConcurrentHashMap<>();

	@Override
	public void registerTableValidationRule(final String tableName, @NonNull final IValidationRule rule)
	{
		final boolean added = tableRulesMap
				.computeIfAbsent(tableName, key -> new CopyOnWriteArrayList<>())
				.addIfAbsent(rule);
		if (added)
		{
			logger.info("Registered rule class {} for {}", rule, tableName);
		}
	}

	@Override
	public void registerValidationRuleException(
			@NonNull final IValidationRule rule,
			@NonNull final String ctxTableName,
			@NonNull final String ctxColumnName,
			@Nullable final String help)
	{
		rule.registerException(ctxTableName, ctxColumnName, help);
	}

	@Override
	public IValidationRule create(
			@NonNull final String tableName,
			@Nullable final AdValRuleId adValRuleId,
			@Nullable final String ctxTableName,
			@Nullable final String ctxColumnName)
	{
		final CompositeValidationRule.Builder builder = CompositeValidationRule.builder();

		//
		// Add primary validation rule
		if (adValRuleId != null)
		{
			final ValidationRuleDescriptor valRuleDescriptor = validationRuleDAO.getById(adValRuleId);
			final IValidationRule validationRule = create(tableName, valRuleDescriptor);
			builder.addExploded(validationRule);
		}

		//
		// Add table specific validation rules
		for (final IValidationRule tableValidationRule : retrieveTableValidationRules(tableName, ctxTableName, ctxColumnName))
		{
			builder.addExploded(tableValidationRule);
		}

		return builder.build();
	}

	private IValidationRule create(@NonNull final String tableName, @Nullable final ValidationRuleDescriptor valRuleDescriptor)
	{
		if (valRuleDescriptor == null || !valRuleDescriptor.isActive())
		{
			return NullValidationRule.instance;
		}

		final ValidationRuleType type = valRuleDescriptor.getType();
		if (type == ValidationRuleType.SQL)
		{
			return createSQLValidationRule(valRuleDescriptor);
		}
		else if (type == ValidationRuleType.JAVA)
		{
			return createJavaValidationRule(valRuleDescriptor);
		}
		else if (type == ValidationRuleType.COMPOSITE)
		{
			return createCompositeValidationRule(tableName, valRuleDescriptor);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Type@:" + type + " (@AD_Val_Rule_ID@:" + valRuleDescriptor.getName() + ")");
		}
	}

	private IValidationRule createSQLValidationRule(@NonNull final ValidationRuleDescriptor valRuleDescriptor)
	{
		final IStringExpression sqlWhereClause = valRuleDescriptor.getSqlWhereClause();
		if (sqlWhereClause == null || sqlWhereClause.isNullExpression()) // shall not happen
		{
			logger.warn("Validation rule {} has no WHERE clause. Returning null.", valRuleDescriptor);
			return NullValidationRule.instance;
		}

		return SQLValidationRule.builder()
				.name(valRuleDescriptor.getName())
				.prefilterWhereClause(sqlWhereClause)
				.dependsOnTableNames(valRuleDescriptor.getDependsOnTableNames())
				.build();
	}

	private IValidationRule createJavaValidationRule(@NonNull final ValidationRuleDescriptor valRuleDescriptor)
	{
		final String classname = StringUtils.trimBlankToNull(valRuleDescriptor.getJavaClassname());
		if (classname == null || Check.isBlank(classname))
		{
			logger.warn("No Classname found for {}", valRuleDescriptor);
			return NullValidationRule.instance;
		}

		return classname2rulesCache.computeIfAbsent(classname, newClassname -> Util.getInstance(IValidationRule.class, newClassname));
	}

	@Override
	public IValidationRule createSQLValidationRule(@Nullable final String whereClause)
	{
		return SQLValidationRule.ofNullableSqlWhereClause(whereClause);
	}

	private IValidationRule createCompositeValidationRule(final String tableName, final ValidationRuleDescriptor valRuleDescriptor)
	{
		final ImmutableSet<AdValRuleId> includedValRuleIds = valRuleDescriptor.getIncludedValRuleIds();
		if (includedValRuleIds == null || includedValRuleIds.isEmpty())
		{
			return NullValidationRule.instance;
		}

		final CompositeValidationRule.Builder builder = CompositeValidationRule.builder();
		for (final AdValRuleId childValRuleId : includedValRuleIds)
		{
			final ValidationRuleDescriptor childValRule = validationRuleDAO.getById(childValRuleId);
			if (childValRule == null || !childValRule.isActive())
			{
				continue;
			}

			final IValidationRule childRule = create(tableName, childValRule);
			builder.addExploded(childRule);
		}

		return builder.build();
	}

	private List<IValidationRule> retrieveTableValidationRules(
			@NonNull final String tableName,
			@Nullable final String ctxTableName,
			@Nullable final String ctxColumnName)
	{
		final List<IValidationRule> rules = tableRulesMap.get(tableName);
		if (rules == null || rules.isEmpty())
		{
			return ImmutableList.of();
		}

		if (ctxColumnName != null && ctxTableName != null)
		{
			return removeExceptionsFromRules(rules, ctxTableName, ctxColumnName);
		}
		else
		{
			return ImmutableList.copyOf(rules);
		}
	}

	private List<IValidationRule> removeExceptionsFromRules(final List<IValidationRule> rules, final String ctxTableName, final String ctxColumnName)
	{
		final List<IValidationRule> rulesWithoutExceptions = new ArrayList<>();

		for (final IValidationRule rule : rules)
		{

			if (noExceptionForTableAndColumn(rule, ctxTableName, ctxColumnName))
			{
				rulesWithoutExceptions.add(rule);
			}

		}

		return rulesWithoutExceptions;
	}

	private boolean noExceptionForTableAndColumn(final IValidationRule rule, final String ctxTableName, final String ctxColumnName)
	{
		final List<ValueNamePair> exceptionTableAndColumns = rule.getExceptionTableAndColumns();

		for (final ValueNamePair exceptionTableAndColumn : exceptionTableAndColumns)
		{
			if (exceptionTableAndColumn.getValue().equals(ctxTableName) && exceptionTableAndColumn.getName().equals(ctxColumnName))
			{
				return false;
			}
		}

		return true;
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
	public IValidationContext createValidationContext(@NonNull final GridField gridField)
	{
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
			return createValidationContext(ctx, gridField.getWindowNo(), Env.TAB_None, tableName);
		}

		final GridTab gridTab = gridField.getGridTab();
		if (gridTab != null)
		{
			return createValidationContext(ctx, tableName, gridTab, rowIndex);
		}

		return IValidationContext.NULL;
	}

	@Override
	public IValidationContext createValidationContext(final Evaluatee evaluatee)
	{
		return new EvaluateeValidationContext(evaluatee);
	}
}
