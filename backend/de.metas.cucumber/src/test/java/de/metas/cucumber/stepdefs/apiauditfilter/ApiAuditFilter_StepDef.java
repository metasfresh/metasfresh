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

import com.google.common.collect.ImmutableList;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.util.Services;
import de.metas.util.web.audit.ApiRequestReplayService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_API_Audit_Config;
import org.compiere.model.I_API_Request_Audit;
import org.compiere.model.I_API_Request_Audit_Log;
import org.compiere.model.I_API_Response_Audit;
import org.compiere.util.DB;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class ApiAuditFilter_StepDef
{
	private final ApiRequestAuditRepository apiRequestAuditRepository = SpringContextHolder.instance.getBean(ApiRequestAuditRepository.class);
	private final ApiRequestReplayService apiRequestReplayService = SpringContextHolder.instance.getBean(ApiRequestReplayService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final TestContext testContext;
	private final StepDefData<I_API_Audit_Config> apiAuditConfigTable;


	public ApiAuditFilter_StepDef(
			@NonNull final TestContext testContext,
			@NonNull final StepDefData<I_API_Audit_Config> apiAuditConfigTable)
	{
		this.testContext = testContext;
		this.apiAuditConfigTable = apiAuditConfigTable;
	}

	@And("all the API audit data is reset")
	public void reset_data()
	{
		DB.executeUpdateEx("TRUNCATE TABLE API_Response_Audit cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE API_Request_Audit_Log cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE API_Request_Audit cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE API_Audit_Config cascade", ITrx.TRXNAME_None);
	}

	@And("the following API_Audit_Config record is set")
	public void API_Audit_Config_insert(@NonNull final DataTable table)
	{
		final List<Map<String, String>> tableRows = table.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final int seqNo = DataTableUtil.extractIntForColumnName(tableRow, "SeqNo");
			final String method = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.Method");
			final String pathPrefix = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.PathPrefix");
			final boolean isInvokerWaitsForResult = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "IsInvokerWaitsForResult", false);

			final I_API_Audit_Config auditConfig = InterfaceWrapperHelper.newInstance(I_API_Audit_Config.class);

			auditConfig.setSeqNo(seqNo);
			auditConfig.setMethod(method);
			auditConfig.setPathPrefix(pathPrefix);
			auditConfig.setIsInvokerWaitsForResult(isInvokerWaitsForResult);

			saveRecord(auditConfig);

			final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "API_Audit_Config");
			apiAuditConfigTable.put(recordIdentifier, auditConfig);
		}
	}

	@And("there are added records in API_Request_Audit")
	public void API_Request_Audit_validation(@NonNull final DataTable table)
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);

		final String method = DataTableUtil.extractStringForColumnName(row, "Method");
		final String path = DataTableUtil.extractStringForColumnName(row, "Path");
		final String name = DataTableUtil.extractStringForColumnName(row, "AD_User.Name");
		final String status = DataTableUtil.extractStringForColumnName(row, "Status");

		final I_API_Request_Audit requestAuditRecord = queryBL
				.createQueryBuilder(I_API_Request_Audit.class)
				.addEqualsFilter(I_API_Request_Audit.COLUMN_API_Request_Audit_ID, requestId.getValue())
				.create()
				.firstOnly(I_API_Request_Audit.class);

		assertThat(requestAuditRecord).isNotNull();
		assertThat(method).isEqualTo(requestAuditRecord.getMethod());
		assertThat(requestAuditRecord.getPath()).contains(path);
		assertThat(status).isEqualTo(requestAuditRecord.getStatus());

		final I_AD_User adUserRecord = queryBL
				.createQueryBuilder(I_AD_User.class)
				.addEqualsFilter(I_AD_User.COLUMN_AD_User_ID, requestAuditRecord.getAD_User_ID())
				.create()
				.firstOnly(I_AD_User.class);

		assertThat(adUserRecord).isNotNull();
		assertThat(name).isEqualTo(adUserRecord.getLogin());
	}

	@And("^there are (added|no) records in API_Request_Audit_Log$")
	public void API_Request_Audit_Log_validation(
			@NonNull final String action,
			@NonNull final DataTable table)
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final List<I_API_Request_Audit_Log> auditLogRecords =
				queryBL.createQueryBuilder(I_API_Request_Audit_Log.class)
						.addEqualsFilter(I_API_Request_Audit_Log.COLUMN_API_Request_Audit_ID, requestId.getValue())
						.create()
						.list();

		final List<Map<String, String>> dataTable = table.asMaps();

		if (dataTable.size() > 0)
		{
			for (final Map<String, String> row : dataTable)
			{
				final String logmessage = DataTableUtil.extractStringForColumnName(row, "Logmessage");
				final String adIssueSummary = DataTableUtil.extractStringOrNullForColumnName(row, "AD_Issue.Summary");

				final I_API_Request_Audit_Log auditLogRecord = auditLogRecords.stream()
						.filter(log -> log.getLogmessage() != null)
						.filter(log -> log.getLogmessage().contains(logmessage))
						.findFirst().orElse(null);

				assertThat(auditLogRecord).isNotNull();

				if (!EmptyUtil.isEmpty(adIssueSummary))
				{
					assertThat(auditLogRecord.getAD_Issue_ID()).isNotNull();

					final I_AD_Issue adIssueRecord = queryBL
							.createQueryBuilder(I_AD_Issue.class)
							.addEqualsFilter(I_AD_Issue.COLUMN_AD_Issue_ID, auditLogRecord.getAD_Issue_ID())
							.create()
							.firstOnly(I_AD_Issue.class);

					assertThat(adIssueRecord).isNotNull();
					assertThat(adIssueRecord.getIssueSummary()).contains(adIssueSummary);
				}
			}
		}
		else
		{
			assertThat(auditLogRecords.size()).isEqualTo(0);
		}
	}

	@And("^there are (added|no) records in API_Response_Audit$")
	public void API_Response_Audit_validation(
			@NonNull final String action,
			@NonNull final DataTable table)
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final List<I_API_Response_Audit> responseAuditRecords = getApiResponseRecordsByRequestAuditId(ApiRequestAuditId.ofRepoId(requestId.getValue()));

		final List<Map<String, String>> dataTable = table.asMaps();
		if (dataTable.size() > 0)
		{
			assertThat(responseAuditRecords.size()).isGreaterThan(0);

			for (final Map<String, String> row : dataTable)
			{
				final String httpCode = DataTableUtil.extractStringForColumnName(row, "HttpCode");
				final String body = DataTableUtil.extractStringOrNullForColumnName(row, "Body");

				final I_API_Response_Audit record = responseAuditRecords
						.stream()
						.filter(responseAudit -> Objects.equals(responseAudit.getHttpCode(), httpCode) && Objects.equals(responseAudit.getBody(), body))
						.findFirst().orElse(null);

				assertThat(record).isNotNull();
				assertThat(httpCode).isEqualTo(record.getHttpCode());
				assertThat(body).isEqualTo(record.getBody());
			}
		}
		else
		{
			assertThat(responseAuditRecords.size()).isEqualTo(0);
		}
	}

	@And("on API_Request_Audit record we update the statusCode value from path")
	public void API_Request_Audit_update(@NonNull final DataTable table)
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);

		final String statusCode = DataTableUtil.extractStringForColumnName(row, "statusCode");

		final ApiRequestAudit existingApiRequestAudit = apiRequestAuditRepository.getById(ApiRequestAuditId.ofRepoId(requestId.getValue()));

		final String existingPath = existingApiRequestAudit.getPath();

		assertThat(existingPath).isNotNull();

		final String updatedPath = existingPath.replace("404", statusCode);

		final ApiRequestAudit updatedApiRequestAudit = existingApiRequestAudit.toBuilder()
				.path(updatedPath)
				.build();

		apiRequestAuditRepository.save(updatedApiRequestAudit);
	}

	@When("invoke replay audit")
	public void invoke_replay_audit()
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final ImmutableList<ApiRequestAudit> responseAuditRecords = ImmutableList.of(apiRequestAuditRepository.getById(ApiRequestAuditId.ofRepoId(requestId.getValue())));

		apiRequestReplayService.replayApiRequests(responseAuditRecords);
	}

	@NonNull
	private List<I_API_Response_Audit> getApiResponseRecordsByRequestAuditId(@NonNull final ApiRequestAuditId apiRequestAuditId)
	{
		return queryBL.createQueryBuilder(I_API_Response_Audit.class)
				.addEqualsFilter(I_API_Response_Audit.COLUMN_API_Request_Audit_ID, apiRequestAuditId.getRepoId())
				.create()
				.list();
	}
}
