/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.apiauditfilter;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_API_Request_Audit_Log;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class API_Request_Audit_Log_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final TestContext testContext;

	public API_Request_Audit_Log_StepDef(@NonNull final TestContext testContext)
	{
		this.testContext = testContext;
	}

	@And("there are no records in API_Request_Audit_Log for the API_Request_Audit from context")
	public void API_Request_Audit_Log_noRecordsById_validation()
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();

		assertThat(requestId).isNotNull();
		assertThat(this.getApiLogRecordsByRequestAuditId(requestId).size()).isEqualTo(0);
	}

	@And("there are no records in API_Request_Audit_Log")
	public void API_Request_Audit_Log_noRecords()
	{
		assertThat(this.getAllApiLogRecords().size()).isEqualTo(0);
	}

	@And("^after not more than (.*)s, there are added records in API_Request_Audit_Log$")
	public void validate_API_Request_Audit_Log_with_timeout(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		validateApiRequestAuditLog(table, timeoutSec);
	}

	@And("there are added records in API_Request_Audit_Log")
	public void directly_validate_API_Request_Audit_Log(@NonNull final DataTable table) throws InterruptedException
	{
		validateApiRequestAuditLog(table, null);
	}

	@NonNull
	private List<I_API_Request_Audit_Log> getApiLogRecordsByRequestAuditId(@NonNull final JsonMetasfreshId apiRequestAuditId)
	{
		return queryBL.createQueryBuilder(I_API_Request_Audit_Log.class)
				.addEqualsFilter(I_API_Request_Audit_Log.COLUMN_API_Request_Audit_ID, apiRequestAuditId.getValue())
				.create()
				.list();
	}

	@NonNull
	private List<I_API_Request_Audit_Log> getAllApiLogRecords()
	{
		return queryBL.createQueryBuilder(I_API_Request_Audit_Log.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	private boolean isApiRequestAuditLogFound(@NonNull final JsonMetasfreshId apiRequestAuditId, @NonNull final String logMessage)
	{
		return getApiRequestAuditLogOptional(apiRequestAuditId, logMessage).isPresent();
	}

	@NonNull
	private I_API_Request_Audit_Log getApiRequestAuditLog(@NonNull final JsonMetasfreshId apiRequestAuditId, @NonNull final String logMessage)
	{
		final Optional<I_API_Request_Audit_Log> logMessageRecord = getApiRequestAuditLogOptional(apiRequestAuditId, logMessage);

		assertThat(logMessageRecord).isPresent();

		return logMessageRecord.get();
	}

	@NonNull
	private Optional<I_API_Request_Audit_Log> getApiRequestAuditLogOptional(@NonNull final JsonMetasfreshId apiRequestAuditId, @NonNull final String logMessage)
	{
		return queryBL.createQueryBuilder(I_API_Request_Audit_Log.class)
				.addEqualsFilter(I_API_Request_Audit_Log.COLUMN_API_Request_Audit_ID, apiRequestAuditId.getValue())
				.addStringLikeFilter(I_API_Request_Audit_Log.COLUMNNAME_Logmessage, "%" + logMessage + "%", true)
				.create()
				.firstOnlyOptional(I_API_Request_Audit_Log.class);
	}

	public void validateApiRequestAuditLog(@NonNull final DataTable table, @Nullable final Integer timeoutSec) throws InterruptedException
	{
		for (final Map<String, String> row : table.asMaps())
		{
			final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
			assertThat(requestId).isNotNull();

			final String logMessage = DataTableUtil.extractStringForColumnName(row, I_API_Request_Audit_Log.COLUMNNAME_Logmessage);
			final String adIssueSummary = DataTableUtil.extractStringOrNullForColumnName(row, "AD_Issue.Summary");

			if (timeoutSec != null)
			{
				StepDefUtil.tryAndWait(timeoutSec, 500, () -> this.isApiRequestAuditLogFound(requestId, logMessage));
			}

			final I_API_Request_Audit_Log apiRequestAuditLogRecord = getApiRequestAuditLog(requestId, logMessage);

			if (!EmptyUtil.isEmpty(adIssueSummary))
			{
				final I_AD_Issue adIssueRecord = InterfaceWrapperHelper.load(apiRequestAuditLogRecord.getAD_Issue_ID(), I_AD_Issue.class);

				assertThat(adIssueRecord).isNotNull();
				assertThat(adIssueRecord.getIssueSummary()).contains(adIssueSummary);
			}
		}
	}
}
