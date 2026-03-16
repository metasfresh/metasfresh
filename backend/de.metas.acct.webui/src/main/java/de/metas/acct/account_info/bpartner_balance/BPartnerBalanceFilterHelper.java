package de.metas.acct.account_info.bpartner_balance;

import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_Fact_Acct;

@UtilityClass
class BPartnerBalanceFilterHelper
{
	static final String FILTER_ID = "default";
	static final String PARAM_C_BPartner_ID = I_Fact_Acct.COLUMNNAME_C_BPartner_ID;
	static final String PARAM_DateAcct = I_Fact_Acct.COLUMNNAME_DateAcct;

	static DocumentFilterDescriptor createFilterDescriptor(@NonNull final LookupDescriptorProviders lookupDescriptorProviders)
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID)
				.setFrequentUsed(false)
				.setDisplayName("default")
				.addParameter(newParamDescriptor(PARAM_C_BPartner_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_C_BPartner.Table_Name).provideForFilter())
						.mandatory(true))
				.addParameter(newParamDescriptor(PARAM_DateAcct)
						.widgetType(DocumentFieldWidgetType.LocalDate)
						.operator(DocumentFilterParam.Operator.BETWEEN))
				.build();
	}

	private static DocumentFilterParamDescriptor.Builder newParamDescriptor(final String fieldName)
	{
		return DocumentFilterParamDescriptor.builder()
				.fieldName(fieldName)
				.displayName(TranslatableStrings.adElementOrMessage(fieldName));
	}
}
