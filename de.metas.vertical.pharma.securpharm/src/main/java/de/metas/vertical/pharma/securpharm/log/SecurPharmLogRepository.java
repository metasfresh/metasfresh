package de.metas.vertical.pharma.securpharm.log;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmActionResultId;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Log;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class SecurPharmLogRepository
{
	public void saveProductLogs(
			@NonNull final Collection<SecurPharmLog> logs,
			@NonNull final SecurPharmProductId productId)
	{
		final SecurPharmActionResultId actionId = null;
		saveLogs(logs, productId, actionId);
	}

	public void saveActionLog(
			@NonNull final SecurPharmLog log,
			@NonNull final SecurPharmProductId productId,
			@NonNull final SecurPharmActionResultId actionId)
	{
		final ImmutableList<SecurPharmLog> logs = ImmutableList.of(log);
		saveLogs(logs, productId, actionId);
	}

	private void saveLogs(
			@NonNull final Collection<SecurPharmLog> logs,
			@Nullable final SecurPharmProductId productId,
			@Nullable final SecurPharmActionResultId actionId)
	{
		if (logs.isEmpty())
		{
			return;
		}

		final Set<SecurPharmLogId> existingLogIds = logs.stream()
				.map(SecurPharmLog::getId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<SecurPharmLogId, I_M_Securpharm_Log> existingLogRecords = retrieveLogRecordsByIds(existingLogIds);

		for (final SecurPharmLog log : logs)
		{
			final I_M_Securpharm_Log existingLogRecord = existingLogRecords.get(log.getId());
			saveLog(log, existingLogRecord, productId, actionId);
		}
	}

	private ImmutableMap<SecurPharmLogId, I_M_Securpharm_Log> retrieveLogRecordsByIds(final Collection<SecurPharmLogId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableMap.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Securpharm_Log.class)
				.addInArrayFilter(I_M_Securpharm_Log.COLUMN_M_Securpharm_Log_ID, ids)
				.create()
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(record -> SecurPharmLogId.ofRepoId(record.getM_Securpharm_Log_ID())));
	}

	private void saveLog(
			@NonNull final SecurPharmLog log,
			@Nullable final I_M_Securpharm_Log existingLogRecord,
			@Nullable final SecurPharmProductId productId,
			@Nullable final SecurPharmActionResultId actionId)
	{
		final I_M_Securpharm_Log record;
		if (existingLogRecord != null)
		{
			record = existingLogRecord;
		}
		else
		{
			record = newInstance(I_M_Securpharm_Log.class);
		}

		record.setIsActive(true);

		record.setIsError(log.isError());

		//
		// Request
		record.setRequestUrl(log.getRequestUrl());
		record.setRequestMethod(log.getRequestMethod() != null ? log.getRequestMethod().name() : null);
		record.setRequestStartTime(TimeUtil.asTimestamp(log.getRequestTime()));

		//
		// Response
		record.setRequestEndTime(TimeUtil.asTimestamp(log.getResponseTime()));
		record.setResponseCode(log.getResponseCode() != null ? log.getResponseCode().value() : 0);
		record.setResponseText(log.getResponseData());

		//
		record.setTransactionIDClient(log.getClientTransactionId());
		record.setTransactionIDServer(log.getServerTransactionId());

		//
		// Links
		if (productId != null)
		{
			record.setM_Securpharm_Productdata_Result_ID(productId.getRepoId());
		}
		if (actionId != null)
		{
			record.setM_Securpharm_Action_Result_ID(actionId.getRepoId());
		}

		saveRecord(record);
		log.setId(SecurPharmLogId.ofRepoId(record.getM_Securpharm_Log_ID()));
	}

}
