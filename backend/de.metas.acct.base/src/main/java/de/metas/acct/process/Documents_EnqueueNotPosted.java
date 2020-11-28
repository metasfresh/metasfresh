package de.metas.acct.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.DB;

import de.metas.acct.api.IPostingRequestBuilder.PostImmediate;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.api.IPostingService;
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
	private final transient AcctDocRegistry docFactory = Adempiere.getBean(AcctDocRegistry.class);
	private final transient IPostingService postingService = Services.get(IPostingService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		for (final String docTableName : docFactory.getDocTableNames())
		{
			enqueueDocuments(docTableName);
		}

		return MSG_OK;
	}

	private void enqueueDocuments(final String docTableName)
	{
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(docTableName);

		final String sql = new StringBuilder("")
				.append("SELECT ").append(keyColumnName)
				.append(" FROM ").append(docTableName)
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

				enqueueDocument(docTableName, recordId);
				countEnqueued++;
			}

			if (countEnqueued > 0)
			{
				addLog("{}: enqueued {} documents", docTableName, countEnqueued);
			}
		}
		catch (final SQLException ex)
		{
			addLog("{}: failed fetching IDs. Check log.", docTableName);
			log.warn("Failed fetching IDs: \n SQL={} \n Params={}", sql, ex);
		}
		catch (final Exception ex)
		{
			addLog("{}: error: {}. Check log.", docTableName, ex.getLocalizedMessage());
			log.warn("Error while processing {}", docTableName, ex);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private void enqueueDocument(final String tableName, final int recordId)
	{
		postingService.newPostingRequest()
				// Post it in same context and transaction as the process
				.setClientId(ClientId.ofRepoId(getAD_Client_ID()))
				.setDocumentRef(TableRecordReference.of(tableName, recordId)) // the document to be posted
				.setFailOnError(false) // don't fail because we don't want to fail the main document posting because one of it's depending documents are failing
				.setPostImmediate(PostImmediate.No) // no, just enqueue it
				.setForce(false) // don't force it
				.postIt();
	}
}
