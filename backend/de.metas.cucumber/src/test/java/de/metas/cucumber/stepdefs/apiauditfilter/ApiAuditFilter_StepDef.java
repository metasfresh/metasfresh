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
import de.metas.audit.HttpMethod;
import de.metas.audit.config.ApiAuditConfigId;
import de.metas.audit.request.ApiRequestAudit;
import de.metas.audit.request.ApiRequestAuditId;
import de.metas.audit.request.ApiRequestAuditRepository;
import de.metas.audit.request.Status;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
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
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class ApiAuditFilter_StepDef
{
	private final ApiRequestAuditRepository apiRequestAuditRepository = SpringContextHolder.instance.getBean(ApiRequestAuditRepository.class);
	private final ApiRequestReplayService apiRequestReplayService = SpringContextHolder.instance.getBean(ApiRequestReplayService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final TestContext testContext;

	public ApiAuditFilter_StepDef(final TestContext testContext)
	{
		this.testContext = testContext;
	}

	@And("all the data is reset to default")
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
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final int id = DataTableUtil.extractIntForColumnName(row, "API_Audit_Config_ID");
			final int seqNo = DataTableUtil.extractIntForColumnName(row, "SeqNo");
			final String method = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.Method");
			final String pathPrefix = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.PathPrefix");
			final String isInvokerWaitsForResult = DataTableUtil.extractStringForColumnName(row, "IsInvokerWaitsForResult");

			final boolean isInvokerWaits = isInvokerWaitsForResult.equals("Y");//set Y as default value

			final I_API_Audit_Config auditConfig = InterfaceWrapperHelper.newInstance(I_API_Audit_Config.class);

			auditConfig.setAPI_Audit_Config_ID(id);
			auditConfig.setSeqNo(seqNo);
			auditConfig.setMethod(method);
			auditConfig.setPathPrefix(pathPrefix);
			auditConfig.setIsInvokerWaitsForResult(isInvokerWaits);

			saveRecord(auditConfig);
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

				final I_API_Request_Audit_Log auditLogRecord = getLogRecordByLogmessage(auditLogRecords, logmessage);

				assertThat(auditLogRecord).isNotNull();
				assertThat(logmessage).isEqualTo(auditLogRecord.getLogmessage());

				if (!EmptyUtil.isEmpty(adIssueSummary))
				{
					assertThat(adIssueSummary).isNotNull();

					final I_API_Request_Audit_Log record = getLogRecordByLogmessage(auditLogRecords, adIssueSummary);

					assertThat(record).isNotNull();

					final I_AD_Issue adIssueRecord = queryBL
							.createQueryBuilder(I_AD_Issue.class)
							.addEqualsFilter(I_AD_Issue.COLUMN_AD_Issue_ID, record.getAD_Issue_ID())
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

		final List<I_API_Response_Audit> responseAuditRecords =
				queryBL.createQueryBuilder(I_API_Response_Audit.class)
						.addEqualsFilter(I_API_Response_Audit.COLUMN_API_Request_Audit_ID, requestId.getValue())
						.create()
						.list();

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
						.filter(responseAudit -> responseAudit.getHttpCode().equals(httpCode) && responseAudit.getBody().equals(body))
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

	@And("the API_Request_Audit record is changed to")
	public void API_Request_Audit_update(@NonNull final DataTable table)
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);

		final String path = DataTableUtil.extractStringForColumnName(row, "Path");

		final ApiRequestAudit existingApiRequestAudit = apiRequestAuditRepository.getById(ApiRequestAuditId.ofRepoId(requestId.getValue()));

		final ApiRequestAudit updatedApiRequestAudit = existingApiRequestAudit.toBuilder()
				.path(path)
				.build();

		apiRequestAuditRepository.save(updatedApiRequestAudit);
	}

	@When("invoke replay audit")
	public void invoke_replay_audit()
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final ImmutableList<ApiRequestAudit> responseAuditRecords = queryBL.createQueryBuilder(I_API_Request_Audit.class)
				.addEqualsFilter(I_API_Request_Audit.COLUMN_API_Request_Audit_ID, requestId.getValue())
				.create()
				.stream()
				.map(this::recordToRequestAudit)
				.collect(ImmutableList.toImmutableList());

		apiRequestReplayService.replayApiRequests(responseAuditRecords);
	}

	@NonNull
	public ApiRequestAudit recordToRequestAudit(@NonNull final I_API_Request_Audit record)
	{
		return ApiRequestAudit.builder()
				.apiRequestAuditId(ApiRequestAuditId.ofRepoId(record.getAPI_Request_Audit_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.roleId(RoleId.ofRepoId(record.getAD_Role_ID()))
				.userId(UserId.ofRepoId(record.getAD_User_ID()))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(record.getAPI_Audit_Config_ID()))
				.status(Status.ofCode(record.getStatus()))
				.isErrorAcknowledged(record.isErrorAcknowledged())
				.body(record.getBody())
				.method(HttpMethod.ofNullableCode(record.getMethod()))
				.path(record.getPath())
				.remoteAddress(record.getRemoteAddr())
				.remoteHost(record.getRemoteHost())
				.time(TimeUtil.asInstant(record.getTime()))
				.httpHeaders(record.getHttpHeaders())
				.requestURI(record.getRequestURI())
				.build();
	}

	@Nullable
	private I_API_Request_Audit_Log getLogRecordByLogmessage(
			@NonNull final List<I_API_Request_Audit_Log> records,
			@NonNull final String logmessage)
	{
		return records.stream()
				.filter(log -> log.getLogmessage().contains(logmessage))
				.findFirst().orElse(null);
	}
}
