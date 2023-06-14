package de.metas.acct.model.validator;

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


import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import de.metas.copy_with_details.CopyRecordFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;
import org.compiere.model.ModelValidator;

import de.metas.acct.gljournal.IGLJournalDAO;
import de.metas.util.Services;

@Interceptor(I_GL_JournalBatch.class)
public class GL_JournalBatch
{
	private final IGLJournalDAO glJournalDAO = Services.get(IGLJournalDAO.class);
	
	@Init
	public void init()
	{
		CopyRecordFactory.enableForTableName(I_GL_JournalBatch.Table_Name);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_GL_JournalBatch.COLUMNNAME_DateAcct, I_GL_JournalBatch.COLUMNNAME_DateDoc })
	public void updateDateAcct(final I_GL_JournalBatch journalBatch)
	{
		final List<I_GL_Journal> journals = glJournalDAO.retrieveJournalsForBatch(journalBatch);

		if (journals.isEmpty())
		{
			// do nothing
			return;
		}

		final Timestamp dateAcct = journalBatch.getDateAcct();
		final Timestamp dateDoc = journalBatch.getDateDoc();

		for (final I_GL_Journal journal : journals)
		{
			// Don't change processed journals
			if (journal.isProcessed())
			{
				continue;
			}

			journal.setDateAcct(dateAcct);
			journal.setDateDoc(dateDoc);
			InterfaceWrapperHelper.save(journal);
		}
	}

}
