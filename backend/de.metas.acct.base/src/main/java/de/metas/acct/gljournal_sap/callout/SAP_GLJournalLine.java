package de.metas.acct.gljournal_sap.callout;

import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.util.lang.SeqNo;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.TabCallout;
import org.springframework.stereotype.Component;

//@Callout(value = I_SAP_GLJournalLine.class, recursionAvoidanceLevel = Callout.RecursionAvoidanceLevel.CalloutMethod)
@TabCallout(I_SAP_GLJournalLine.class)
@Component
public class SAP_GLJournalLine implements ITabCallout
{
	private final SAPGLJournalService glJournalService;

	public SAP_GLJournalLine(final SAPGLJournalService glJournalService) {this.glJournalService = glJournalService;}

	// @PostConstruct
	// public void postConstruct()
	// {
	// 	Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	// }

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_SAP_GLJournalLine glJournalLine = calloutRecord.getModel(I_SAP_GLJournalLine.class);
		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(glJournalLine.getSAP_GLJournal_ID());
		final SeqNo seqNo = glJournalService.getNextSeqNo(glJournalId);
		glJournalLine.setLine(seqNo.toInt());
	}
}
