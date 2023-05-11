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

import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_API_Request_Audit;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class API_Request_Audit_StepDef
{
	private final ApiRequestAuditRepository apiRequestAuditRepository = SpringContextHolder.instance.getBean(ApiRequestAuditRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final TestContext testContext;

	public API_Request_Audit_StepDef(@NonNull final TestContext testContext)
	{
		this.testContext = testContext;
	}

	@And("^after not more than (.*)s, there are added records in API_Request_Audit$")
	public void validate_API_Request_Audit_with_timeout(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		validateApiRequestAudit(table, timeoutSec);
	}

	@And("there are added records in API_Request_Audit")
	public void directly_validate_API_Request_Audit(@NonNull final DataTable table) throws InterruptedException
	{
		validateApiRequestAudit(table, null);
	}

	@And("on API_Request_Audit record we update the statusCode value from path")
	public void update_API_Request_Audit(@NonNull final DataTable table)
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

	@And("^after not more than (.*)s, find and add last API_Request_Audit_ID to context$")
	public void get_last_API_Request_Audit_Id(final int timeoutSec) throws InterruptedException
	{
		StepDefUtil.tryAndWait(timeoutSec, 500, () -> this.getLastApiRequestAuditId().isPresent());

		final JsonMetasfreshId apiRequestAuditId = this.getLastApiRequestAuditId()
				.map(I_API_Request_Audit::getAPI_Request_Audit_ID)
				.map(JsonMetasfreshId::of)
				.orElse(null);

		assertThat(apiRequestAuditId).isNotNull();

		testContext.setApiResponse(testContext
										   .getApiResponse()
										   .toBuilder()
										   .requestId(apiRequestAuditId)
										   .build());
	}

	private boolean isApiAuditRequestFound(@NonNull final JsonMetasfreshId apiRequestAuditId, @NonNull final String[] allowedStatuses)
	{
		return queryBL
				.createQueryBuilder(I_API_Request_Audit.class)
				.addEqualsFilter(I_API_Request_Audit.COLUMN_API_Request_Audit_ID, apiRequestAuditId.getValue())
				.addInArrayFilter(I_API_Request_Audit.COLUMNNAME_Status, allowedStatuses)
				.create()
				.firstOnlyOptional(I_API_Request_Audit.class)
				.isPresent();
	}

	@NonNull
	private Optional<I_API_Request_Audit> getLastApiRequestAuditId()
	{
		return queryBL.createQueryBuilder(I_API_Request_Audit.class)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_API_Request_Audit.COLUMNNAME_API_Request_Audit_ID)
				.create()
				.firstOptional(I_API_Request_Audit.class);
	}

	private void validateApiRequestAudit(@NonNull final DataTable table, @Nullable final Integer timeoutSec) throws InterruptedException
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);

		final String method = DataTableUtil.extractStringForColumnName(row, "Method");
		final String path = DataTableUtil.extractStringForColumnName(row, "Path");
		final String name = DataTableUtil.extractStringForColumnName(row, "AD_User.Name");
		
		final String statuses = DataTableUtil.extractStringForColumnName(row, "Status");
		final String[] allowedStatuses = statuses.split(" *OR *");

		final Integer pInstanceId = DataTableUtil.extractIntegerOrNullForColumnName(row,"OPT." + I_API_Request_Audit.COLUMNNAME_AD_PInstance_ID);
		
		if (timeoutSec != null)
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> isApiAuditRequestFound(requestId, allowedStatuses));
		}

		final I_API_Request_Audit requestAuditRecord = InterfaceWrapperHelper.load(requestId.getValue(), I_API_Request_Audit.class);
		assertThat(requestAuditRecord).isNotNull();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(requestAuditRecord.getPath()).as("path").contains(path);
		softly.assertThat(requestAuditRecord.getMethod()).as("method").contains(method);
		softly.assertThat(requestAuditRecord.getStatus()).as("status").isIn((Object[])allowedStatuses);

		if (pInstanceId != null)
		{
			softly.assertThat(requestAuditRecord.getAD_PInstance_ID()).isEqualTo(pInstanceId);
		}

		final I_AD_User adUserRecord = InterfaceWrapperHelper.load(requestAuditRecord.getAD_User_ID(), I_AD_User.class);

		assertThat(adUserRecord).isNotNull();
		assertThat(adUserRecord.getLogin()).as("AD_User.Login of API_Request_Audit_ID=%s", requestAuditRecord.getAPI_Request_Audit_ID()).isEqualTo(name);
	}
}