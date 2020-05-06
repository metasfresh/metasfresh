package de.metas.acct.model.validator;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.I_I_GLJournal;
import org.compiere.model.ModelValidator;

import de.metas.acct.gljournal.IGLJournalLineDAO;
import de.metas.acct.gljournal.IGLJournalLineGroup;
import de.metas.acct.impexp.GLJournalImportProcess;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.util.Services;

@Interceptor(I_GL_Journal.class)
public class GL_Journal
{
	@Init
	public void init()
	{
		CopyRecordFactory.enableForTableName(I_GL_Journal.Table_Name);
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
