package de.metas.acct.gljournal_sap.interceptor;

import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.SAPGLJournalLineId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.acct.open_items.FAOpenItemsService;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_SAP_GLJournalLine.class)
@Component
public class SAP_GLJournalLine
{
	private final SAPGLJournalService glJournalService;
	private final ElementValueService elementValueService;
	private final FAOpenItemsService faOpenItemsService;

	public SAP_GLJournalLine(
			@NonNull final SAPGLJournalService glJournalService,
			@NonNull final ElementValueService elementValueService,
			@NonNull final FAOpenItemsService faOpenItemsService)
	{
		this.glJournalService = glJournalService;
		this.elementValueService = elementValueService;
		this.faOpenItemsService = faOpenItemsService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_SAP_GLJournalLine record, final ModelChangeType timing)
	{
		if (InterfaceWrapperHelper.isValueChanged(record, I_SAP_GLJournalLine.COLUMNNAME_C_ValidCombination_ID))
		{
			final ElementValueId elementValueId = ElementValueId.ofRepoId(record.getC_ValidCombination().getAccount_ID());
			final ElementValue elementValue = elementValueService.getById(elementValueId);
			record.setIsOpenItem(elementValue.isOpenItem());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_SAP_GLJournalLine record, final ModelChangeType timing)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			if (timing.isNew() || InterfaceWrapperHelper.isValueChanged(record, I_SAP_GLJournalLine.COLUMNNAME_PostingSign, I_SAP_GLJournalLine.COLUMNNAME_AmtAcct))
			{
				final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(record.getSAP_GLJournal_ID());
				glJournalService.updateTotalsFromLines(glJournalId);
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void beforeDelete(final I_SAP_GLJournalLine record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(record.getSAP_GLJournal_ID());
			final SAPGLJournalLineId glJournalLineId = SAPGLJournalLineId.ofRepoId(glJournalId, record.getSAP_GLJournalLine_ID());
			glJournalService.updateById(
					glJournalId,
					glJournal -> glJournal.removeLinesIf(line -> SAPGLJournalLineId.equals(line.getParentId(), glJournalLineId)));
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void afterDelete(final I_SAP_GLJournalLine record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(record.getSAP_GLJournal_ID());
			glJournalService.updateById(glJournalId, SAPGLJournal::updateTotals);
		}
	}

}
