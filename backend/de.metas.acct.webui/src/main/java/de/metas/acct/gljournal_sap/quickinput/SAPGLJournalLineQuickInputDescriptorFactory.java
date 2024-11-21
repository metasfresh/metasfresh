package de.metas.acct.gljournal_sap.quickinput;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.ad_reference.ReferenceId;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.quickinput.config.QuickInputConfigLayout;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptorProviderBuilder;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_M_SectionCode;
import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SAPGLJournalLineQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	@NonNull private static final String SYS_CONFIG_FilterAccount = "de.metas.acct.gljournal_sap.quickinput.SAPGLJournalLineQuickInputDescriptorFactory.AccountValRuleId";
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final LookupDescriptorProviders lookupDescriptorProviders;

	@VisibleForTesting
	static final QuickInputConfigLayout DEFAULT_LayoutConfig = QuickInputConfigLayout.builder()
			.field(QuickInputConfigLayout.Field.builder().fieldName(ISAPGLJournalLineQuickInput.COLUMNNAME_PostingSign).mandatory(true).build())
			.field(QuickInputConfigLayout.Field.builder().fieldName(ISAPGLJournalLineQuickInput.COLUMNNAME_GL_Account_ID).mandatory(true).build())
			.field(QuickInputConfigLayout.Field.builder().fieldName(ISAPGLJournalLineQuickInput.COLUMNNAME_Amount).mandatory(true).build())
			.build();

	static
	{
		SAPGLJournalLineQuickInputConfigValidator.assertValid(DEFAULT_LayoutConfig);
	}

	@Override
	public Set<MatchingKey> getMatchingKeys()
	{
		return ImmutableSet.of(MatchingKey.ofTableName(I_SAP_GLJournalLine.Table_Name));
	}

	@Override
	public QuickInputDescriptor createQuickInputDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final DetailId detailId,
			@NonNull final Optional<SOTrx> soTrx)
	{
		final QuickInputConfigLayout layoutConfig = getLayoutConfig(documentType, detailId);
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(documentTypeId, detailId, soTrx, layoutConfig);
		final QuickInputLayoutDescriptor layout = QuickInputLayoutDescriptor.onlyFields(entityDescriptor, layoutConfig.getFieldNamesInOrder());

		return QuickInputDescriptor.of(entityDescriptor, layout, SAPGLJournalLineQuickInputProcessor.class);
	}

	private QuickInputConfigLayout getLayoutConfig(
			final DocumentType documentType,
			final DetailId detailId)
	{
		if (documentType.isWindow() && detailId != null)
		{
			final AdTabId adTabId = detailId.toAdTabId();
			final QuickInputConfigLayout quickInputLayout = adWindowDAO.getQuickInputConfigLayout(adTabId).orElse(null);
			if (quickInputLayout != null)
			{
				return quickInputLayout;
			}
		}

		return DEFAULT_LayoutConfig;
	}

	private DocumentEntityDescriptor createEntityDescriptor(
			final DocumentId documentTypeId,
			final DetailId detailId,
			@NonNull final Optional<SOTrx> soTrx,
			@NonNull final QuickInputConfigLayout layoutConfig)
	{
		return DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentTypeId)
				.disableDefaultTableCallouts()
				.setDetailId(detailId)
				.setTableName(I_SAP_GLJournalLine.Table_Name)
				.setIsSOTrx(soTrx)

				// PostingSign
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_PostingSign, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.List)
						.setLookupDescriptorProvider(getPostingSignLookup())
						.setWidgetSize(WidgetSize.Small))

				// GL_Account_ID
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_GL_Account_ID, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptorProvider(getAccountProvider())
						.setWidgetSize(WidgetSize.Large))

				// Amount
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_Amount, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.Amount))

				// C_Activity_ID
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_C_Activity_ID, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptorProvider(lookupDescriptorProviders.searchInTable(I_C_Activity.Table_Name)))

				// M_SectionCode_ID
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_M_SectionCode_ID, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptorProvider(lookupDescriptorProviders.searchInTable(I_M_SectionCode.Table_Name)))

				// C_Tax_ID
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_C_Tax_ID, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptorProvider(lookupDescriptorProviders.searchInTable(I_C_Tax.Table_Name)))

				// IsTaxIncluded
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_IsTaxIncluded, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.YesNo))
				//
				.build();
	}

	private LookupDescriptorProvider getPostingSignLookup()
	{
		final ReferenceId postingSignReferenceId = Check.assumeNotNull(
				POInfo.getPOInfoNotNull(I_SAP_GLJournalLine.Table_Name).getColumnReferenceValueId(I_SAP_GLJournalLine.COLUMNNAME_PostingSign),
				"AD_ReferenceValue_ID for SAP_GLJournalLine.PostingSign shall be set");

		return lookupDescriptorProviders.listByAD_Reference_Value_ID(postingSignReferenceId);
	}

	@NonNull
	private LookupDescriptorProvider getAccountProvider()
	{
		final AdValRuleId valueRuleId = AdValRuleId.ofRepoIdOrNull(sysConfigBL.getIntValue(SYS_CONFIG_FilterAccount, -1));

		if (valueRuleId == null)
		{
			return lookupDescriptorProviders.searchInTable(I_C_ValidCombination.Table_Name);
		}

		final SqlLookupDescriptorProviderBuilder descriptorProviderBuilder = lookupDescriptorProviders.sql()
				.setCtxTableName(I_C_ValidCombination.Table_Name)
				.setCtxColumnName(I_C_ValidCombination.COLUMNNAME_C_ValidCombination_ID)
				.setDisplayType(DisplayType.Search)
				.setAD_Val_Rule_ID(valueRuleId);

		return descriptorProviderBuilder.build();
	}

	private DocumentFieldDescriptor.Builder prepareField(@NonNull final String fieldName, @NonNull final QuickInputConfigLayout layoutConfig)
	{
		return DocumentFieldDescriptor.builder(fieldName)
				.setCaption(msgBL.translatable(fieldName))
				.setMandatoryLogic(layoutConfig.isMandatory(fieldName))
				.setAlwaysUpdateable(true)
				.addCharacteristic(DocumentFieldDescriptor.Characteristic.PublicField);
	}

}
