package de.metas.acct.gljournal_sap.quickinput;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.ad_reference.ReferenceId;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.quickinput.config.QuickInputConfigLayout;
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
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_M_SectionCode;
import org.compiere.model.POInfo;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class SAPGLJournalLineQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final SAPGLJournalLineQuickInputConfigProvider configProvider;
	private final LookupDescriptorProviders lookupDescriptorProviders;

	public SAPGLJournalLineQuickInputDescriptorFactory(
			@NonNull final SAPGLJournalLineQuickInputConfigProvider configProvider,
			@NonNull final LookupDescriptorProviders lookupDescriptorProviders)
	{
		this.configProvider = configProvider;
		this.lookupDescriptorProviders = lookupDescriptorProviders;
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
		final QuickInputConfigLayout layoutConfig = configProvider.getLayoutConfig();
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(documentTypeId, detailId, soTrx, layoutConfig);
		final QuickInputLayoutDescriptor layout = QuickInputLayoutDescriptor.onlyFields(entityDescriptor, layoutConfig.getFieldNamesInOrder());

		return QuickInputDescriptor.of(entityDescriptor, layout, SAPGLJournalLineQuickInputProcessor.class);
	}

	private static DocumentEntityDescriptor.Builder createDescriptorBuilder(final DocumentId documentTypeId, final DetailId detailId)
	{
		return DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentTypeId)
				.disableDefaultTableCallouts()
				// Defaults:
				.setDetailId(detailId)
				.setTableName(I_SAP_GLJournalLine.Table_Name);
	}

	private DocumentEntityDescriptor createEntityDescriptor(
			final DocumentId documentTypeId,
			final DetailId detailId,
			@NonNull final Optional<SOTrx> soTrx,
			@NonNull final QuickInputConfigLayout layoutConfig)
	{
		return createDescriptorBuilder(documentTypeId, detailId)
				.setIsSOTrx(soTrx)
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_PostingSign, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.List)
						.setLookupDescriptorProvider(getPostingSignLookup())
						.setWidgetSize(WidgetSize.Small))
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_GL_Account_ID, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptorProvider(lookupDescriptorProviders.searchInTable(I_C_ValidCombination.Table_Name))
						.setWidgetSize(WidgetSize.Large))
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_Amount, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.Amount))
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_C_Activity_ID, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptorProvider(lookupDescriptorProviders.searchInTable(I_C_Activity.Table_Name)))
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_M_SectionCode_ID, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptorProvider(lookupDescriptorProviders.searchInTable(I_M_SectionCode.Table_Name)))
				.addField(prepareField(ISAPGLJournalLineQuickInput.COLUMNNAME_C_Tax_ID, layoutConfig)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptorProvider(lookupDescriptorProviders.searchInTable(I_C_Tax.Table_Name)))
				.build();
	}

	private LookupDescriptorProvider getPostingSignLookup()
	{
		final ReferenceId postingSignReferenceId = Check.assumeNotNull(
				POInfo.getPOInfoNotNull(I_SAP_GLJournalLine.Table_Name).getColumnReferenceValueId(I_SAP_GLJournalLine.COLUMNNAME_PostingSign),
				"AD_ReferenceValue_ID for SAP_GLJournalLine.PostingSign shall be set");

		return lookupDescriptorProviders.listByAD_Reference_Value_ID(postingSignReferenceId);
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
