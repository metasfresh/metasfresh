/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.organization.OrgId;
import de.metas.request.RequestId;
import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.rest_api.request.JsonRRequestUpsertResponse;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_R_Request;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;

@RequiredArgsConstructor
public class R_Request_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);

	@NonNull private final AD_Org_StepDefData orgTable;
	@NonNull private final TestContext restTestContext;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final R_Request_StepDefData requestTable;

	@And("validate a new request has been created:")
	public void newRequestExists(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final DataTableRows dataTableRows = DataTableRows.of(dataTable);
		if (dataTableRows.size() != 1)
		{
			throw new AdempiereException("This does not support multiple rows");
		}
		final JsonRRequestUpsertResponse request = restTestContext.getApiResponseBodyAs(JsonRRequestUpsertResponse.class);

		assertNewRequest(dataTableRows.singleRow(), request.getRequestId());
	}

	private void assertNewRequest(@NonNull final DataTableRow dataTableRow, final JsonMetasfreshId requestId)
	{
		final OrgId orgId = dataTableRow.getAsIdentifier(I_R_Request.COLUMNNAME_AD_Org_ID).lookupIdIn(orgTable);
		final RequestTypeId requestTypeId = requestTypeDAO.retrieveRequestTypeIdByInternalName(dataTableRow.getAsString(I_R_Request.COLUMNNAME_R_RequestType_ID));
		final String summary = dataTableRow.getAsString(I_R_Request.COLUMNNAME_Summary);
		final Timestamp dateDelivered = TimeUtil.asTimestamp(dataTableRow.getAsLocalDate(I_R_Request.COLUMNNAME_DateDelivered));
		final String priority = dataTableRow.getAsString(I_R_Request.COLUMNNAME_Priority);
		final String confidentialType = dataTableRow.getAsString(I_R_Request.COLUMNNAME_ConfidentialType);

		final I_R_Request request = queryBL.createQueryBuilder(I_R_Request.class)
				.addEqualsFilter(I_R_Request.COLUMNNAME_R_Request_ID, requestId.getValue())
				.create()
				.firstOnly();
		final SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(orgId).as(I_R_Request.COLUMNNAME_AD_Org_ID).isEqualTo(OrgId.ofRepoId(request.getAD_Org_ID()));
		softAssertions.assertThat(requestTypeId).as(I_R_Request.COLUMNNAME_R_RequestType_ID).isEqualTo(RequestTypeId.ofRepoIdOrNull(request.getR_RequestType_ID()));
		softAssertions.assertThat(summary).as(I_R_Request.COLUMNNAME_Summary).isEqualTo(request.getSummary());
		softAssertions.assertThat(dateDelivered).as(I_R_Request.COLUMNNAME_DateDelivered).isEqualTo(request.getDateDelivered());
		softAssertions.assertThat(priority).as(I_R_Request.COLUMNNAME_Priority).isEqualTo(request.getPriority());
		softAssertions.assertThat(confidentialType).as(I_R_Request.COLUMNNAME_ConfidentialType).isEqualTo(request.getConfidentialType());

		dataTableRow.getAsOptionalIdentifier(I_C_Order.COLUMNNAME_C_Order_ID)
				.map(orderTable::getId)
				.ifPresent(orderId -> {
					softAssertions.assertThat(orderId.getRepoId()).as(I_R_Request.COLUMNNAME_C_Order_ID).isEqualTo(request.getC_Order_ID());
					softAssertions.assertThat(TableIdsCache.instance.getTableIdNotNull(I_C_Order.Table_Name).getRepoId()).as(I_R_Request.COLUMNNAME_AD_Table_ID).isEqualTo(request.getAD_Table_ID());
					softAssertions.assertThat(orderId.getRepoId()).as(I_R_Request.COLUMNNAME_Record_ID).isEqualTo(request.getRecord_ID());
				});

		softAssertions.assertAll();

		restTestContext.setIdVariableFromRow(dataTableRow, RequestId.ofRepoId(request.getR_Request_ID()));
		requestTable.putOrReplace(dataTableRow.getAsIdentifier(), request);
	}
}
