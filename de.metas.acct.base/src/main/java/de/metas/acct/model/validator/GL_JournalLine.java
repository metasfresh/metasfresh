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


import org.adempiere.acct.api.IGLJournalBL;
import org.adempiere.acct.api.IGLJournalLineBL;
import org.adempiere.acct.api.IGLJournalLineDAO;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;

import de.metas.acct.spi.impl.GLJournalLineCopyRecordSupport;
import de.metas.util.Services;

@Interceptor(I_GL_JournalLine.class)
public class GL_JournalLine
{
	// private static final transient Logger logger = CLogMgt.getLogger(GL_JournalLine.class);

	@Init
	public void init()
	{
		CopyRecordFactory.registerCopyRecordSupport(I_GL_JournalLine.Table_Name, GLJournalLineCopyRecordSupport.class);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_GL_JournalLine glJournalLine, final int timing)
	{
		// Services
		final IGLJournalLineBL glJournalLineBL = Services.get(IGLJournalLineBL.class);
		final IGLJournalBL glJournalBL = Services.get(IGLJournalBL.class);

		final boolean newRecord = ModelChangeType.valueOf(timing).isNew();

		final I_GL_Journal glJournal = glJournalLine.getGL_Journal();

		if (newRecord && glJournalBL.isComplete(glJournal))
		{
			throw new AdempiereException("@ParentComplete@");
		}

		// Set LineNo if not already set
		if (newRecord && glJournalLine.getLine() <= 0)
		{
			final int lastLineNo = Services.get(IGLJournalLineDAO.class).retrieveLastLineNo(glJournal);
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

	/**
	 * After Delete
	 *
	 * @param success true if deleted
	 * @return true if success
	 */
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
		final String trxName = InterfaceWrapperHelper.getTrxName(glJournalLine);

		// Update Journal Total
		{
			final String sql = DB.convertSqlToNative("UPDATE GL_Journal j"
					+ " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(AmtAcctDr),0), COALESCE(SUM(AmtAcctCr),0)" // croo Bug# 1789935
					+ " FROM GL_JournalLine jl WHERE jl.IsActive='Y' AND j.GL_Journal_ID=jl.GL_Journal_ID) "
					+ "WHERE GL_Journal_ID=" + glJournalId);
			final int no = DB.executeUpdateEx(sql, trxName);
			if (no != 1)
			{
				throw new AdempiereException("afterSave - Update Journal #" + no);
			}
		}

		// Update Batch Total
		{
			final String sql = DB.convertSqlToNative("UPDATE GL_JournalBatch jb"
					+ " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(TotalDr),0), COALESCE(SUM(TotalCr),0)"
					+ " FROM GL_Journal j WHERE jb.GL_JournalBatch_ID=j.GL_JournalBatch_ID) "
					+ "WHERE GL_JournalBatch_ID="
					+ "(SELECT DISTINCT GL_JournalBatch_ID FROM GL_Journal WHERE GL_Journal_ID=" + glJournalId + ")");
			final int no = DB.executeUpdateEx(sql, trxName);
			if (no != 1)
			{
				throw new AdempiereException("Update Batch #" + no);
			}
		}
	}

}
