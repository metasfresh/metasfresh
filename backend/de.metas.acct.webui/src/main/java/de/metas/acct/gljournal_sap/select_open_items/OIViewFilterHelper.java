package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.FactAcctQuery;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_ElementValue;

import javax.annotation.Nullable;

@UtilityClass
class OIViewFilterHelper
{
	static final String FILTER_ID = "default";
	static String PARAM_Account_ID = "Account_ID";
	static String PARAM_C_BPartner_ID = "C_BPartner_ID";
	static String PARAM_DateAcct = "DateAcct";
	static String PARAM_DocumentNo = "DocumentNo";
	static String PARAM_Description = "Description";

	public static DocumentFilterDescriptor createFilterDescriptor(@NonNull final LookupDescriptorProviders lookupDescriptorProviders)
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID)
				.setFrequentUsed(false)
				.setDisplayName("default")
				.addParameter(newParamDescriptor(PARAM_Account_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_C_ElementValue.Table_Name).provideForFilter()))
				.addParameter(newParamDescriptor(PARAM_C_BPartner_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_C_BPartner.Table_Name).provideForFilter()))
				.addParameter(newParamDescriptor(PARAM_DateAcct)
						.widgetType(DocumentFieldWidgetType.LocalDate))
				.addParameter(newParamDescriptor(PARAM_DocumentNo)
						.widgetType(DocumentFieldWidgetType.Text))
				.addParameter(newParamDescriptor(PARAM_Description)
						.widgetType(DocumentFieldWidgetType.Text))
				.build();
	}

	private static DocumentFilterParamDescriptor.Builder newParamDescriptor(final String fieldName)
	{
		return DocumentFilterParamDescriptor.builder()
				.fieldName(fieldName)
				.displayName(TranslatableStrings.adElementOrMessage(fieldName));
	}

	public static FactAcctQuery toFactAcctQuery(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final PostingType postingType,
			@Nullable final DocumentFilter filter)
	{

		final FactAcctQuery.FactAcctQueryBuilder builder = FactAcctQuery.builder()
				.acctSchemaId(acctSchemaId)
				.postingType(postingType)
				.isOpenItem(true)
				//.isOpenItemReconciled(false) // TODO
				;

		if (filter != null)
		{
			final BPartnerId bpartnerId = filter.getParameterValueAsRepoIdOrNull(PARAM_C_BPartner_ID, BPartnerId::ofRepoIdOrNull);
			final InSetPredicate<BPartnerId> bpartnerIds = bpartnerId != null ? InSetPredicate.only(bpartnerId) : InSetPredicate.any();

			builder.accountId(filter.getParameterValueAsRepoIdOrNull(PARAM_Account_ID, ElementValueId::ofRepoIdOrNull))
					.bpartnerIds(bpartnerIds)
					.dateAcct(filter.getParameterValueAsInstantOrNull(PARAM_DateAcct))
					.documentNoLike(filter.getParameterValueAsString(PARAM_DocumentNo, null))
					.descriptionLike(filter.getParameterValueAsString(PARAM_Description, null));
		}

		return builder.build();
	}
}
