package de.metas.acct.account_info.hierarchy;

import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_Fact_Acct;

@UtilityClass
class AccountHierarchyFilterHelper
{
	static final String FILTER_ID = "default";
	static final String PARAM_DateAcct = I_Fact_Acct.COLUMNNAME_DateAcct;
	static final String PARAM_AD_Org_ID = I_Fact_Acct.COLUMNNAME_AD_Org_ID;

	static DocumentFilterDescriptor createFilterDescriptor(@NonNull final LookupDescriptorProviders lookupDescriptorProviders)
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID)
				.setFrequentUsed(false)
				.setDisplayName("default")
				.addParameter(newParamDescriptor(PARAM_DateAcct)
						.widgetType(DocumentFieldWidgetType.LocalDate)
						.mandatory(true))
				.addParameter(newParamDescriptor(PARAM_AD_Org_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_AD_Org.Table_Name).provideForFilter()))
				.build();
	}

	private static DocumentFilterParamDescriptor.Builder newParamDescriptor(final String fieldName)
	{
		return DocumentFilterParamDescriptor.builder()
				.fieldName(fieldName)
				.displayName(TranslatableStrings.adElementOrMessage(fieldName));
	}
}
