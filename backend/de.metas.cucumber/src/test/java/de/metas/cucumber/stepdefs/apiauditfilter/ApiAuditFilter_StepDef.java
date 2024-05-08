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
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.util.web.audit.ApiRequestReplayService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.*;

public class ApiAuditFilter_StepDef
{
	private final ApiRequestAuditRepository apiRequestAuditRepository = SpringContextHolder.instance.getBean(ApiRequestAuditRepository.class);
	private final ApiRequestReplayService apiRequestReplayService = SpringContextHolder.instance.getBean(ApiRequestReplayService.class);
	private final TestContext testContext;

	public ApiAuditFilter_StepDef(@NonNull final TestContext testContext)
	{
		this.testContext = testContext;
	}

	@And("all the API audit data is reset")
	public void reset_data()
	{
		DB.executeUpdateEx("TRUNCATE TABLE API_Response_Audit cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE API_Request_Audit_Log cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE API_Request_Audit cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE API_Audit_Config cascade", ITrx.TRXNAME_None);
	}

	@When("invoke replay audit")
	public void invoke_replay_audit()
	{
		final JsonMetasfreshId requestId = testContext.getApiResponse().getRequestId();
		assertThat(requestId).isNotNull();

		final ImmutableList<ApiRequestAudit> responseAuditRecords = ImmutableList.of(apiRequestAuditRepository.getById(ApiRequestAuditId.ofRepoId(requestId.getValue())));

		apiRequestReplayService.replayApiRequests(responseAuditRecords);
	}
}
