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
import de.metas.common.util.StringUtils;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_API_Response_Audit;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class API_Response_Audit_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final TestContext testContext;

	public API_Response_Audit_StepDef(@NonNull final TestContext testContext)
	{
		this.testContext = testContext;
	}

	@And("^after not more than (.*)s, there are added records in API_Response_Audit$")
	public void validate_API_Response_Audit_with_timeout(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		validateApiResponse(table, timeoutSec);
	}

	@And("there are added records in API_Response_Audit")
	public void directly_validate_API_Response_Audit(@NonNull final DataTable table) throws InterruptedException
	{
		validateApiResponse(table, null);
	}

	@And("there are no records in API_Response_Audit for the API_Request_Audit from context")
	public void validate_API_Response_Audit_no_records_for_request()
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final List<I_API_Response_Audit> responseAuditRecords = getApiResponseRecordsByRequestAuditId(requestId);

		assertThat(responseAuditRecords.size()).isEqualTo(0);
	}

	@And("there are no records in API_Response_Audit")
	public void validate_API_Response_Audit_no_records()
	{
		assertThat(this.getAllApiResponseAuditRecords().size()).isEqualTo(0);
	}

	@NonNull
	private List<I_API_Response_Audit> getAllApiResponseAuditRecords()
	{
		return queryBL.createQueryBuilder(I_API_Response_Audit.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	private boolean isApiResponseAuditFound(
			@NonNull final JsonMetasfreshId apiRequestAuditId,
			@NonNull final String httpCode,
			@Nullable final String body)
	{
		return this.getApiResponseAuditRecord(apiRequestAuditId, httpCode, body).isPresent();
	}

	@NonNull
	private Optional<I_API_Response_Audit> getApiResponseAuditRecord(
			@NonNull final JsonMetasfreshId apiRequestAuditId,
			@NonNull final String httpCode,
			@Nullable final String body)
	{
		return getApiResponseRecordsByRequestAuditId(apiRequestAuditId)
				.stream()
				.filter(responseAudit -> httpCode.equals(responseAudit.getHttpCode())
						&& (body == null && responseAudit.getBody() == null
						   || responseAudit.getBody() != null && body != null && responseAudit.getBody().contains(body)))
				.findFirst();
	}

	@NonNull
	private List<I_API_Response_Audit> getApiResponseRecordsByRequestAuditId(@NonNull final JsonMetasfreshId apiRequestAuditId)
	{
		return queryBL.createQueryBuilder(I_API_Response_Audit.class)
				.addEqualsFilter(I_API_Response_Audit.COLUMN_API_Request_Audit_ID, apiRequestAuditId.getValue())
				.create()
				.list();
	}

	private void validateApiResponse(@NonNull final DataTable table, @Nullable final Integer timeoutSec) throws InterruptedException
	{
		for (final Map<String, String> row : table.asMaps())
		{
			final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
			assertThat(requestId).isNotNull();

			final String httpCode = DataTableUtil.extractStringForColumnName(row, "HttpCode");
			final String body = DataTableUtil.extractStringOrNullForColumnName(row, "Body");

			if (timeoutSec != null)
			{
				StepDefUtil.tryAndWait(timeoutSec, 500, () -> isApiResponseAuditFound(requestId, httpCode, StringUtils.trimBlankToNull(body)));
			}

			final Optional<I_API_Response_Audit> record = getApiResponseAuditRecord(requestId, httpCode, body);

			assertThat(record).isPresent();
		}
	}
}
