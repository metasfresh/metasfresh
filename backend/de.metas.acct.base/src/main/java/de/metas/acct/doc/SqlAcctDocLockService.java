package de.metas.acct.doc;

import de.metas.error.AdIssueId;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.acct.PostingStatus;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class SqlAcctDocLockService implements AcctDocLockService
{
	@Override
	public boolean lock(final AcctDocModel docModel, final boolean force, final boolean repost)
	{
		final String tableName = docModel.getTableName();
		final int recordId = docModel.getId();

		final StringBuilder sql = new StringBuilder("UPDATE ");
		sql.append(tableName).append(" SET Processing='Y' WHERE ")
				.append(tableName).append("_ID=").append(recordId)
				.append(" AND Processed='Y' AND IsActive='Y'");
		if (!force)
		{
			sql.append(" AND (Processing='N' OR Processing IS NULL)");
		}
		if (!repost)
		{
			sql.append(" AND Posted='N'");
		}

		final int updatedCount = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		return updatedCount == 1;
	}

	@Override
	public boolean unlock(final AcctDocModel docModel,
						  @Nullable final PostingStatus newPostingStatus,
						  @Nullable final AdIssueId postingErrorIssueId)
	{
		final String tableName = docModel.getTableName();
		final POInfo poInfo = POInfo.getPOInfoNotNull(tableName);
		final String keyColumnName = poInfo.getKeyColumnName();
		final int recordId = docModel.getId();

		final StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ")
				.append("Processing='N'"); // Processing (i.e. unlock it)

		//
		// Posted
		if (newPostingStatus != null)
		{
			sql.append(", Posted=").append(DB.TO_STRING(newPostingStatus));
		}

		//
		// PostingError_Issue_ID
		final String COLUMNNAME_PostingError_Issue_ID = "PostingError_Issue_ID";
		final boolean hasPostingIssueColumn = poInfo.hasColumnName(COLUMNNAME_PostingError_Issue_ID);
		if (hasPostingIssueColumn)
		{
			if (postingErrorIssueId != null)
			{

				sql.append(", ").append(COLUMNNAME_PostingError_Issue_ID).append("=").append(postingErrorIssueId.getRepoId());
			}
			else
			{
				sql.append(", ").append(COLUMNNAME_PostingError_Issue_ID).append(" = NULL ");
			}
		}

		sql.append("\n WHERE ").append(keyColumnName).append("=").append(recordId);

		final int updateCount = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		return updateCount == 1;
	}

}
