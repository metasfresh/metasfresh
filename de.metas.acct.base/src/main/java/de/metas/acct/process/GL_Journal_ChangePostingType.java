package de.metas.acct.process;

import java.util.List;

import org.adempiere.acct.api.IGLJournalBL;
import org.adempiere.acct.api.IGLJournalDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;

import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Change the PostingType of {@link I_GL_JournalBatch}/{@link I_GL_Journal} and re-post the GL_Journals.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class GL_Journal_ChangePostingType extends JavaProcess
{
	// services
	private final transient IGLJournalDAO glJournalDAO = Services.get(IGLJournalDAO.class);
	private final transient IGLJournalBL glJournalBL = Services.get(IGLJournalBL.class);

	private static final String PARAM_PostingType = "PostingType";
	private String p_PostingType;

	@Override
	protected void prepare()
	{
		p_PostingType = getParameterAsIParams().getParameterAsString(PARAM_PostingType);
		Check.assumeNotEmpty(p_PostingType, "p_PostingType not empty");
	}

	@Override
	protected String doIt() throws Exception
	{
		final String tableName = getTableName();
		if (I_GL_JournalBatch.Table_Name.equals(tableName))
		{
			final I_GL_JournalBatch glJournalBatch = getRecord(I_GL_JournalBatch.class);
			changePostingType(glJournalBatch);
		}
		else if (I_GL_Journal.Table_Name.equals(tableName))
		{
			final I_GL_Journal glJournal = getRecord(I_GL_Journal.class);
			changePostingType(glJournal);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @TableName@: " + tableName);
		}

		return MSG_OK;
	}

	private void changePostingType(final I_GL_JournalBatch glJournalBatch)
	{
		glJournalBatch.setPostingType(p_PostingType);
		InterfaceWrapperHelper.save(glJournalBatch);

		final List<I_GL_Journal> glJournals = glJournalDAO.retrieveJournalsForBatch(glJournalBatch);
		for (final I_GL_Journal glJournal : glJournals)
		{
			changePostingType(glJournal);
		}
	}

	private void changePostingType(final I_GL_Journal glJournal)
	{
		glJournal.setPostingType(p_PostingType);

		glJournalBL.unpost(glJournal);
		InterfaceWrapperHelper.save(glJournal);
	}
}
