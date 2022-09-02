package de.metas.ui.web.window.descriptor.sql;

import de.metas.reflist.ReferenceId;
import de.metas.security.permissions.Access;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

@ToString
public class SqlLookupDescriptorProviderBuilder
{
	//
	// Parameters
	@Nullable private String ctxColumnName;
	@Nullable private String ctxTableName;

	private DocumentFieldWidgetType widgetType;
	private ReferenceId displayType;
	private ReferenceId AD_Reference_Value_ID = null;
	private final HashMap<LookupDescriptorProvider.LookupScope, AdValRuleId> adValRuleIdByScope = new HashMap<>();
	private final ArrayList<IValidationRule> additionalValidationRules = new ArrayList<>();
	private Access requiredAccess = null;

	public LookupDescriptor buildForDefaultScope()
	{
		return build()
				.provide()
				.orElseThrow(() -> new AdempiereException("No lookup for " + this));
	}

	public LookupDescriptorProvider build()
	{
		final int displayType = Check.assumeNotNull(this.displayType, "Parameter displayType is not null").getRepoId();

		if (widgetType == DocumentFieldWidgetType.ProcessButton)
		{
			return LookupDescriptorProviders.NULL;
		}
		else if (DisplayType.isAnyLookup(displayType)
				|| DisplayType.Button == displayType && AD_Reference_Value_ID != null)
		{
			return LookupDescriptorProviders.fromMemoizingFunction(scope -> SqlLookupDescriptor.builder()
					.setScope(scope)
					.setCtxTableName(ctxTableName)
					.setCtxColumnName(ctxColumnName)
					.setDisplayType(displayType)
					.setAD_Reference_Value_ID(AD_Reference_Value_ID)
					.setAD_Val_Rule_ID(adValRuleIdByScope.get(scope))
					.addValidationRules(additionalValidationRules)
					.build());
		}
		else
		{
			return LookupDescriptorProviders.NULL;
		}
	}

	public SqlLookupDescriptorProviderBuilder setCtxColumnName(@Nullable final String columnName)
	{
		this.ctxColumnName = columnName;
		return this;
	}

	public SqlLookupDescriptorProviderBuilder setCtxTableName(@Nullable final String tableName)
	{
		this.ctxTableName = tableName;
		return this;
	}

	public SqlLookupDescriptorProviderBuilder setDisplayType(final int displayType) {return setDisplayType(ReferenceId.ofRepoId(displayType));}

	public SqlLookupDescriptorProviderBuilder setDisplayType(final ReferenceId displayType)
	{
		this.displayType = displayType;
		return this;
	}

	public SqlLookupDescriptorProviderBuilder setWidgetType(final DocumentFieldWidgetType widgetType)
	{
		this.widgetType = widgetType;
		return this;
	}

	public SqlLookupDescriptorProviderBuilder setAD_Reference_Value_ID(final ReferenceId AD_Reference_Value_ID)
	{
		this.AD_Reference_Value_ID = AD_Reference_Value_ID;
		return this;
	}

	public SqlLookupDescriptorProviderBuilder setAD_Val_Rule_ID(@Nullable final AdValRuleId AD_Val_Rule_ID)
	{
		return setAD_Val_Rule_ID(LookupDescriptorProvider.LookupScope.DocumentField, AD_Val_Rule_ID);
	}

	public SqlLookupDescriptorProviderBuilder setAD_Val_Rule_ID(@NonNull final LookupDescriptorProvider.LookupScope scope, @Nullable final AdValRuleId AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID == null)
		{
			adValRuleIdByScope.remove(scope);
		}
		else
		{
			adValRuleIdByScope.put(scope, AD_Val_Rule_ID);
		}
		return this;
	}

	public SqlLookupDescriptorProviderBuilder addValidationRule(final IValidationRule validationRule)
	{
		additionalValidationRules.add(validationRule);
		return this;
	}

	public SqlLookupDescriptorProviderBuilder setReadOnlyAccess()
	{
		this.requiredAccess = Access.READ;
		return this;
	}
}
