package de.metas.acct.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;

import de.metas.acct.gljournal.IGLJournalLineBL;
import de.metas.util.Services;

public class GL_JournalLine_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_GL_JournalLine glJournalLine = calloutRecord.getModel(I_GL_JournalLine.class);

		Services.get(IGLJournalLineBL.class).setGroupNoAndFlags(glJournalLine);

		//
		// 07569: copy description from glJournal to glJournalLine
		final I_GL_Journal glJournal = glJournalLine.getGL_Journal();
		glJournalLine.setDescription(glJournal.getDescription());
	}
}
