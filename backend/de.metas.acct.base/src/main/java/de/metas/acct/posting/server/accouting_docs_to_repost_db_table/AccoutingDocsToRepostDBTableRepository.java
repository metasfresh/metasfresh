package de.metas.acct.posting.server.accouting_docs_to_repost_db_table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;

import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

final class AccoutingDocsToRepostDBTableRepository
{
	private static final String Table_Name = "\"de_metas_acct\".accounting_docs_to_repost";

	public List<AccountingDocToRepost> retrieve(final int limit)
	{
		Check.assumeGreaterThanZero(limit, "limit");

		return DB.retrieveRowsOutOfTrx(
				"SELECT * FROM " + Table_Name + " ORDER BY seqno LIMIT ?",
				Arrays.asList(limit),
				this::retrieveRow);
	}

	private AccountingDocToRepost retrieveRow(final ResultSet rs) throws SQLException
	{
		return AccountingDocToRepost.builder()
				.seqNo(rs.getInt("SeqNo"))
				.recordRef(TableRecordReference.of(
						rs.getString("TableName"),
						rs.getInt("Record_ID")))
				.clientId(ClientId.ofRepoId(rs.getInt("AD_Client_ID")))
				.force(StringUtils.toBoolean(rs.getString("force")))
				.onErrorNotifyUserId(UserId.ofRepoIdOrNullIfSystem(rs.getInt("on_error_notify_user_id")))
				.build();
	}

	public void delete(@NonNull final AccountingDocToRepost docToRepost)
	{
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM " + Table_Name + " WHERE SeqNo=?",
				new Object[] { docToRepost.getSeqNo() },
				ITrx.TRXNAME_None);
	}
}
