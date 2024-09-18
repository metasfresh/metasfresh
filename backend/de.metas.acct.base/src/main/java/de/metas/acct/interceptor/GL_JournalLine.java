package de.metas.acct.interceptor;

import de.metas.acct.gljournal.IGLJournalBL;
import de.metas.acct.gljournal.IGLJournalLineBL;
import de.metas.acct.gljournal.IGLJournalLineDAO;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;

@Interceptor(I_GL_JournalLine.class)
public class GL_JournalLine
{
	// private static final transient Logger logger = CLogMgt.getLogger(GL_JournalLine.class);
	private final IGLJournalLineBL glJournalLineBL = Services.get(IGLJournalLineBL.class);
	private final IGLJournalBL glJournalBL = Services.get(IGLJournalBL.class);
	private final IGLJournalLineDAO glJournalLineDAO = Services.get(IGLJournalLineDAO.class);

	@Init
	public void init()
	{
		CopyRecordFactory.enableForTableName(I_GL_JournalLine.Table_Name);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_GL_JournalLine glJournalLine, final int timing)
	{
		final boolean newRecord = ModelChangeType.valueOf(timing).isNew();

		final I_GL_Journal glJournal = glJournalLine.getGL_Journal();

		if (newRecord && glJournalBL.isComplete(glJournal))
		{
			throw new AdempiereException("@ParentComplete@");
		}

		// Set LineNo if not already set
		if (newRecord && glJournalLine.getLine() <= 0)
		{
			final int lastLineNo = glJournalLineDAO.retrieveLastLineNo(glJournal);
			final int lineNo = lastLineNo + 10;
			glJournalLine.setLine(lineNo);
		}

		//
		// If GL_JournalLine_Group is not set, compute it
		if (glJournalLine.getGL_JournalLine_Group() <= 0)
		{
			glJournalLineBL.setGroupNoAndFlags(glJournalLine);
		}

		// Make sure IsSplitAcctTrx flag makes sense along with other GL_JournalLine settings
		glJournalLineBL.checkValidSplitAcctTrxFlag(glJournalLine);

		//
		// Set Source Amounts precision and compute Accountable Amounts
		glJournalLineBL.setAmtSourcePrecision(glJournalLine); // metas: cg: 02476
		glJournalLineBL.setAmtAcct(glJournalLine);

		// Set Line Org to Acct Org (from parent)
		glJournalLine.setAD_Org_ID(glJournal.getAD_Org_ID());

	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_GL_JournalLine glJournalLine)
	{
		updateJournalTotal(glJournalLine);
	}	// afterSave

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void afterDelete(final I_GL_JournalLine glJournalLine)
	{
		updateJournalTotal(glJournalLine);
	}	// afterDelete

	/**
	 * Update amounts of {@link I_GL_Journal} and {@link I_GL_JournalBatch}.
	 *
	 */
	private void updateJournalTotal(final I_GL_JournalLine glJournalLine)
	{
		final int glJournalId = glJournalLine.getGL_Journal_ID();

		//
		// Update Journal Total
		{
			final String sql = DB.convertSqlToNative("UPDATE GL_Journal j"
					+ " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(AmtAcctDr),0), COALESCE(SUM(AmtAcctCr),0)" // croo Bug# 1789935
					+ " FROM GL_JournalLine jl WHERE jl.IsActive='Y' AND j.GL_Journal_ID=jl.GL_Journal_ID) "
					+ "WHERE GL_Journal_ID=" + glJournalId);
			final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
			if (no != 1)
			{
				throw new AdempiereException("afterSave - Update Journal #" + no);
			}
		}

		//
		// Update Batch Total, if there is any batch
		{
			final String sql = DB.convertSqlToNative("UPDATE GL_JournalBatch jb"
					+ " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(TotalDr),0), COALESCE(SUM(TotalCr),0)"
					+ " FROM GL_Journal j WHERE jb.GL_JournalBatch_ID=j.GL_JournalBatch_ID) "
					+ "WHERE GL_JournalBatch_ID="
					+ "(SELECT DISTINCT GL_JournalBatch_ID FROM GL_Journal WHERE GL_Journal_ID=" + glJournalId + ")");
			final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
			if (no != 0 && no != 1)
			{
				throw new AdempiereException("Update Batch #" + no);
			}
		}

		CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(
				ITrx.TRXNAME_ThreadInherited,
				CacheInvalidateMultiRequest.rootRecord(I_GL_Journal.Table_Name, glJournalId));
	}

}
