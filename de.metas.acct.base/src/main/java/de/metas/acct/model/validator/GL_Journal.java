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


import org.adempiere.acct.api.IGLJournalLineDAO;
import org.adempiere.acct.api.IGLJournalLineGroup;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.IImportProcessFactory;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.I_I_GLJournal;
import org.compiere.model.ModelValidator;

import de.metas.acct.impexp.GLJournalImportProcess;
import de.metas.util.Services;

@Interceptor(I_GL_Journal.class)
public class GL_Journal
{
	@Init
	public void init()
	{
		Services.get(IImportProcessFactory.class).registerImportProcess(I_I_GLJournal.class, GLJournalImportProcess.class);
	}
	
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_PREPARE })
	public void assertAllGroupsAreBalances(final I_GL_Journal glJournal)
	{
		final IGLJournalLineGroup group = Services.get(IGLJournalLineDAO.class).retrieveFirstUnballancedJournalLineGroup(glJournal);
		if (group == null)
		{
			return;
		}

		throw new AdempiereException("@" + I_GL_JournalLine.COLUMNNAME_AmtAcctGroupDr + "@ <> @" + I_GL_JournalLine.COLUMNNAME_AmtAcctGroupCr + "@"
				+ "(@" + I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group + "@: " + group.getGroupNo() + ")");
	}
}
