package de.metas.acct.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.acct.api.IDocFactory;
import org.adempiere.acct.api.IDocMetaInfo;
import org.adempiere.acct.api.IPostingRequestBuilder.PostImmediate;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;

import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class Documents_EnqueueNotPosted extends JavaProcess
{
	private final transient IDocFactory docFactory = Services.get(IDocFactory.class);
	private final transient IPostingService postingService = Services.get(IPostingService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		for (final IDocMetaInfo docMetaInfo : docFactory.getDocMetaInfoList())
		{
			enqueueDocuments(docMetaInfo);
		}

		return MSG_OK;
	}

	private void enqueueDocuments(final IDocMetaInfo docMetaInfo)
	{
		final int adTableId = docMetaInfo.getAD_Table_ID();
		final String tableName = docMetaInfo.getTableName();
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);

		final String sql = new StringBuilder("")
				.append("SELECT ").append(keyColumnName)
				.append(" FROM ").append(tableName)
				.append(" WHERE AD_Client_ID=").append(getAD_Client_ID())
				.append(" AND Processed='Y' AND Posted='N' AND IsActive='Y'")
				.append(" ORDER BY Created")
				.toString();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();

			int countEnqueued = 0;
			while (rs.next())
			{
				final int recordId = rs.getInt(keyColumnName);

				enqueueDocument(adTableId, recordId);
				countEnqueued++;
			}

			if (countEnqueued > 0)
			{
				addLog("{}: enqueued {} documents", tableName, countEnqueued);
			}
		}
		catch (final SQLException ex)
		{
			addLog("{}: failed fetching IDs. Check log.", tableName);
			log.warn("Failed fetching IDs: \n SQL={} \n Params={}", sql, ex);
		}
		catch (final Exception ex)
		{
			addLog("{}: error: {}. Check log.", tableName, ex.getLocalizedMessage());
			log.warn("Error while processing {}", tableName, ex);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private void enqueueDocument(final int adTableId, final int recordId)
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();
		final int adClientId = getAD_Client_ID();

		postingService.newPostingRequest()
				// Post it in same context and transaction as the process
				.setContext(ctx, trxName)
				.setAD_Client_ID(adClientId)
				.setDocument(adTableId, recordId) // the document to be posted
				.setFailOnError(false) // don't fail because we don't want to fail the main document posting because one of it's depending documents are failing
				.setPostImmediate(PostImmediate.No) // no, just enqueue it
				.setForce(false) // don't force it
				.postIt();
	}
}
