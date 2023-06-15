package de.metas.acct.gljournal_sap.quickinput;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.Account;
import de.metas.acct.api.AccountId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournalLineId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalLineCreateRequest;
import de.metas.acct.gljournal_sap.service.SAPGLJournalLoaderAndSaver;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.document.dimension.Dimension;
import de.metas.product.acct.api.ActivityId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.TaxId;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;
import org.compiere.SpringContextHolder;

import java.util.Set;

public class SAPGLJournalLineQuickInputProcessor implements IQuickInputProcessor
{
	private final SAPGLJournalService glJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);

	@Override
	public Set<DocumentId> process(final QuickInput quickInput)
	{
		final I_SAP_GLJournal headerRecord = quickInput.getRootDocumentAs(I_SAP_GLJournal.class);
		final ISAPGLJournalLineQuickInput lineQuickInput = quickInput.getQuickInputDocumentAs(ISAPGLJournalLineQuickInput.class);

		final SAPGLJournalLineId glJournalLineId = glJournalService.createLine(SAPGLJournalLineCreateRequest.builder()
				.postingSign(PostingSign.ofCode(lineQuickInput.getPostingSign()))
				.account(Account.ofId(AccountId.ofRepoId(lineQuickInput.getGL_Account_ID())))
				.amount(lineQuickInput.getAmount())
				.dimension(Dimension.builder()
						.sectionCodeId(SectionCodeId.ofRepoIdOrNull(lineQuickInput.getM_SectionCode_ID()))
						.activityId(ActivityId.ofRepoIdOrNull(lineQuickInput.getC_Activity_ID()))
						.build())
				.taxId(TaxId.ofRepoIdOrNull(lineQuickInput.getC_Tax_ID()))
				.build(), SAPGLJournalLoaderAndSaver.extractId(headerRecord));

		final DocumentId documentId = DocumentId.of(glJournalLineId);
		return ImmutableSet.of(documentId);
	}
}
