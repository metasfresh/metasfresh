package de.metas.acct.callout;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.acct.api.IGLJournalLineBL;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.util.Services;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;

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
