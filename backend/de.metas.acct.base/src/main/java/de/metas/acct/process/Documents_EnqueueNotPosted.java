package de.metas.acct.process;

import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.api.IPostingService;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

@Deprecated
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

		final String sql = "SELECT " + keyColumnName
				+ " FROM " + docTableName
				+ " WHERE AD_Client_ID=" + getAD_Client_ID()
				+ " AND Processed='Y' AND Posted='N' AND IsActive='Y'"
				+ " ORDER BY Created";

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
			log.warn("Failed fetching IDs: \n SQL={}", sql, ex);
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
		postingService.schedule(
				DocumentPostRequest.builder()
						.record(TableRecordReference.of(tableName, recordId)) // the document to be posted
						.clientId(getClientId())
						.build()
		);
	}
}
