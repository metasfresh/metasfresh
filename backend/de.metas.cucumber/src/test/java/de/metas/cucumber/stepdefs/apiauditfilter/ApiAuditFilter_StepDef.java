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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.util.web.audit.ApiRequestReplayService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApiAuditFilter_StepDef
{
	private final ApiRequestAuditRepository apiRequestAuditRepository = SpringContextHolder.instance.getBean(ApiRequestAuditRepository.class);
	private final ApiRequestReplayService apiRequestReplayService = SpringContextHolder.instance.getBean(ApiRequestReplayService.class);
	private final TestContext testContext;
<<<<<<< HEAD

	public ApiAuditFilter_StepDef(@NonNull final TestContext testContext)
=======
	private final API_Audit_Config_StepDefData apiAuditConfigTable;


	public ApiAuditFilter_StepDef(
			@NonNull final TestContext testContext,
			@NonNull final API_Audit_Config_StepDefData apiAuditConfigTable)
>>>>>>> 01acf328a21 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
	{
		this.testContext = testContext;
	}

	@And("all the API audit data is reset")
	public void reset_data()
	{
		DB.executeUpdateAndThrowExceptionOnFail("TRUNCATE TABLE API_Response_Audit cascade", ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail("TRUNCATE TABLE API_Request_Audit_Log cascade", ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail("TRUNCATE TABLE API_Request_Audit cascade", ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail("TRUNCATE TABLE API_Audit_Config cascade", ITrx.TRXNAME_None);
	}

	@When("invoke replay audit")
	public void invoke_replay_audit()
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final ImmutableList<ApiRequestAudit> responseAuditRecords = ImmutableList.of(apiRequestAuditRepository.getById(ApiRequestAuditId.ofRepoId(requestId.getValue())));

		apiRequestReplayService.replayApiRequests(responseAuditRecords);
	}

	@Then("validate api response error message")
	public void validate_api_response_error_message(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String message = DataTableUtil.extractStringForColumnName(row, "JsonErrorItem.message");
			final JsonErrorItem jsonErrorItem;
			final String responseContent = testContext.getApiResponse().getContent();
			try
			{
				jsonErrorItem = objectMapper.readValue(responseContent, JsonErrorItem.class);
			}
			catch (final RuntimeException rte)
			{
				fail("Error parsing string into class " + JsonErrorItem.class + "; string=" + responseContent);
				throw rte;
			}
			assertThat(jsonErrorItem.getMessage()).isEqualTo(message);
		}
	}
}
