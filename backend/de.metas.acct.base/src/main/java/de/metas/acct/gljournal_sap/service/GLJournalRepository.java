package de.metas.acct.gljournal_sap.service;

import de.metas.acct.gljournal_sap.DebitAndCreditAmountsBD;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.cache.CacheMgt;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

@Repository
public class GLJournalRepository
{
	public void updateTotals(
			@NonNull final SAPGLJournalId glJournalId,
			@NonNull final DebitAndCreditAmountsBD totals)
	{
		final String sql = "UPDATE " + I_SAP_GLJournal.Table_Name
				+ " SET " + I_SAP_GLJournal.COLUMNNAME_TotalDr + "=?"
				+ ", " + I_SAP_GLJournal.COLUMNNAME_TotalCr + "=?"
				+ " WHERE " + I_SAP_GLJournal.COLUMNNAME_SAP_GLJournal_ID + "=?";

		DB.executeUpdateEx(
				sql,
				new Object[] { totals.getDebit(), totals.getCredit(), glJournalId },
				ITrx.TRXNAME_ThreadInherited);

		CacheMgt.get().reset(I_SAP_GLJournal.Table_Name, glJournalId.getRepoId());
	}
}
