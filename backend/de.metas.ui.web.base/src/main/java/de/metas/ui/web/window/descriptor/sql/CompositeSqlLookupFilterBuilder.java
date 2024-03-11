package de.metas.ui.web.window.descriptor.sql;

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.model.sql.DocActionValidationRule;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class CompositeSqlLookupFilterBuilder
{
	TableName lookupTableName;
	String ctxTableName;
	String ctxColumnName;
	int displayType;
	private final HashMap<LookupDescriptorProvider.LookupScope, AdValRuleId> adValRuleIdByScope = new HashMap<>();
	private final ArrayList<SqlLookupFilter> filters = new ArrayList<>();

	private CompositeSqlLookupFilter _built = null; // lazy

	public CompositeSqlLookupFilter getOrBuild()
	{
		CompositeSqlLookupFilter built = this._built;
		if (built == null)
		{
			built = this._built = build();
		}
		return built;
	}

	private void assertNotBuilt()
	{
		if (_built != null)
		{
			throw new AdempiereException("Filters were already built");
		}
	}

	private CompositeSqlLookupFilter build()
	{
		final IValidationRuleFactory validationRuleFactory = Services.get(IValidationRuleFactory.class);

		final ArrayList<SqlLookupFilter> filters = new ArrayList<>();

		for (LookupDescriptorProvider.LookupScope scope : adValRuleIdByScope.keySet())
		{
			final AdValRuleId adValRuleId = adValRuleIdByScope.get(scope);
			if (adValRuleId != null)
			{
				final IValidationRule valRule = validationRuleFactory.create(lookupTableName.getAsString(), adValRuleId, ctxTableName, ctxColumnName);
				for (final IValidationRule childValRule : CompositeValidationRule.unbox(valRule))
				{
					filters.add(SqlLookupFilter.of(childValRule, scope));
				}
			}
		}

		//
		// Case: DocAction button => inject the DocActionValidationRule
		// FIXME: hardcoded
		if (displayType == DisplayType.Button && WindowConstants.FIELDNAME_DocAction.equals(ctxColumnName))
		{
			filters.add(SqlLookupFilter.of(DocActionValidationRule.instance, LookupDescriptorProvider.LookupScope.DocumentField));
		}

		//
		// Additional validation rules registered
		filters.addAll(this.filters);

		return CompositeSqlLookupFilter.ofFilters(filters);
	}

	public void setAdValRuleId(@NonNull final LookupDescriptorProvider.LookupScope lookupScope, @Nullable final AdValRuleId adValRuleId)
	{
		if (adValRuleId != null)
		{
			adValRuleIdByScope.put(lookupScope, adValRuleId);
		}
		else
		{
			adValRuleIdByScope.remove(lookupScope);
		}
	}

	public void setAdValRuleIds(@NonNull final Map<LookupDescriptorProvider.LookupScope, AdValRuleId> adValRuleIds)
	{
		adValRuleIdByScope.clear();
		adValRuleIdByScope.putAll(adValRuleIds);
	}

	public void setLookupTableName(final TableName lookupTableName)
	{
		assertNotBuilt();
		this.lookupTableName = lookupTableName;
	}

	public void setCtxTableName(final String ctxTableName)
	{
		assertNotBuilt();
		this.ctxTableName = ctxTableName;
	}

	public void setCtxColumnName(final String ctxColumnName)
	{
		assertNotBuilt();
		this.ctxColumnName = ctxColumnName;
	}

	public void setDisplayType(final int displayType)
	{
		assertNotBuilt();
		this.displayType = displayType;
	}

	public void addFilter(@NonNull final Collection<IValidationRule> validationRules, @Nullable LookupDescriptorProvider.LookupScope scope)
	{
		for (final IValidationRule valRule : CompositeValidationRule.unbox(validationRules))
		{
			addFilter(SqlLookupFilter.of(valRule, scope));
		}
	}

	public void addFilter(@Nullable final IValidationRule validationRule, @Nullable LookupDescriptorProvider.LookupScope scope)
	{
		for (final IValidationRule valRule : CompositeValidationRule.unbox(validationRule))
		{
			addFilter(SqlLookupFilter.of(valRule, scope));
		}
	}

	private void addFilter(@NonNull final SqlLookupFilter filter)
	{
		assertNotBuilt();
		filters.add(filter);
	}

}
