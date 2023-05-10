package de.metas.acct.interceptor;

import de.metas.acct.gljournal.IGLJournalLineDAO;
import de.metas.acct.gljournal.IGLJournalLineGroup;
import de.metas.acct.impexp.GLJournalImportProcess;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.I_I_GLJournal;
import org.compiere.model.ModelValidator;

@Interceptor(I_GL_Journal.class)
public class GL_Journal
{
	private final IGLJournalLineDAO glJournalLineDAO = Services.get(IGLJournalLineDAO.class);
	private final IImportProcessFactory importProcessFactory;

	public GL_Journal(@NonNull final IImportProcessFactory importProcessFactory)
	{
		this.importProcessFactory = importProcessFactory;
	}

	@Init
	public void init()
	{
		CopyRecordFactory.enableForTableName(I_GL_Journal.Table_Name);
		importProcessFactory.registerImportProcess(I_I_GLJournal.class, GLJournalImportProcess.class);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_PREPARE })
	public void assertAllGroupsAreBalances(final I_GL_Journal glJournal)
	{
		final IGLJournalLineGroup group = glJournalLineDAO.retrieveFirstUnballancedJournalLineGroup(glJournal);
		if (group == null)
		{
			return;
		}

		throw new AdempiereException("@" + I_GL_JournalLine.COLUMNNAME_AmtAcctGroupDr + "@ <> @" + I_GL_JournalLine.COLUMNNAME_AmtAcctGroupCr + "@"
				+ "(@" + I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group + "@: " + group.getGroupNo() + ")");
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_GL_Journal.COLUMNNAME_M_SectionCode_ID })
	@CalloutMethod(columnNames = I_GL_Journal.COLUMNNAME_M_SectionCode_ID)
	public void updateSectionCode(@NonNull final I_GL_Journal glJournal)
	{
		for (final I_GL_JournalLine glJournalLine : glJournalLineDAO.retrieveLines(glJournal))
		{
			glJournalLine.setM_SectionCode_ID(glJournal.getM_SectionCode_ID());
			glJournalLineDAO.save(glJournalLine);
		}
	}
}
