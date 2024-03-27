/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance.docoutboundlog;

import de.metas.document.archive.DocOutboundLogId;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.organization.OrgId;
import de.metas.postfinance.model.I_C_Doc_Outbound_Log_PostFinance_Log;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class PostFinanceLogRepository
{
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PostFinanceLog create(@NonNull final PostFinanceLogCreateRequest postFinanceLogCreateRequest)
	{
		final I_C_Doc_Outbound_Log_PostFinance_Log logRecord = newInstance(I_C_Doc_Outbound_Log_PostFinance_Log.class);
		if(postFinanceLogCreateRequest.getPostFinanceExportException() != null)
		{
			final AdIssueId issueId = errorManager.createIssue(postFinanceLogCreateRequest.getPostFinanceExportException());
			logRecord.setAD_Issue_ID(issueId.getRepoId());
			logRecord.setIsError(true);
		}
		logRecord.setC_Doc_Outbound_Log_ID(postFinanceLogCreateRequest.getDocOutboundLogId().getRepoId());
		logRecord.setMsgText(postFinanceLogCreateRequest.getMessage());
		logRecord.setPostFinance_Transaction_Id(postFinanceLogCreateRequest.getTransactionId());
		save(logRecord);

		return toPostFinanceLog(logRecord);
	}

	private PostFinanceLog toPostFinanceLog(@NonNull final I_C_Doc_Outbound_Log_PostFinance_Log logRecord)
	{
		return PostFinanceLog.builder()
				.docOutboundLogId(DocOutboundLogId.ofRepoId(logRecord.getC_Doc_Outbound_Log_ID()))
				.message(logRecord.getMsgText())
				.isError(logRecord.isError())
				.adIssueId(AdIssueId.ofRepoIdOrNull(logRecord.getAD_Issue_ID()))
				.TransactionId(logRecord.getPostFinance_Transaction_Id())
				.build();
	}

	public Optional<PostFinanceLog> retrieveLatestLogWithTransactionId(@NonNull final String transactionId)
	{
		return queryBL.createQueryBuilder(I_C_Doc_Outbound_Log_PostFinance_Log.class)
				.addOnlyActiveRecordsFilter()
				.addStringLikeFilter(I_C_Doc_Outbound_Log_PostFinance_Log.COLUMNNAME_PostFinance_Transaction_Id, transactionId, false)
				.orderByDescending(I_C_Doc_Outbound_Log_PostFinance_Log.COLUMNNAME_Created)
				.orderByDescending(I_C_Doc_Outbound_Log_PostFinance_Log.COLUMNNAME_C_Doc_Outbound_Log_PostFinance_Log_ID)
				.create()
				.stream()
				.findFirst()
				.map(this::toPostFinanceLog);
	}
}
