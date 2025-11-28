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

import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.organization.OrgId;
import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.model.I_R_Request;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
public class R_Request_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);

	@NonNull private final AD_Org_StepDefData orgTable;

	@And("a new request exists:")
	public void newRequestExists(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::assertNewRequest);
	}

	private void assertNewRequest(@NonNull final DataTableRow dataTableRow, final Integer ignored)
	{
		final OrgId orgId = dataTableRow.getAsIdentifier(I_R_Request.COLUMNNAME_AD_Org_ID).lookupIdIn(orgTable);
		final RequestTypeId requestTypeId = requestTypeDAO.retrieveRequestTypeIdByInternalName(dataTableRow.getAsString(I_R_Request.COLUMNNAME_R_RequestType_ID));
		final String summary = dataTableRow.getAsString(I_R_Request.COLUMNNAME_Summary);
		final Timestamp dateDelivered = TimeUtil.asTimestamp(dataTableRow.getAsLocalDate(I_R_Request.COLUMNNAME_DateDelivered));
		final String priority = dataTableRow.getAsString(I_R_Request.COLUMNNAME_Priority);
		final String confidentialType = dataTableRow.getAsString(I_R_Request.COLUMNNAME_ConfidentialType);

		final IQuery<I_R_Request> requestQuery = queryBL.createQueryBuilder(I_R_Request.class)
				.addEqualsFilter(I_R_Request.COLUMN_AD_Org_ID, orgId)
				.addEqualsFilter(I_R_Request.COLUMNNAME_R_RequestType_ID, requestTypeId)
				.addEqualsFilter(I_R_Request.COLUMNNAME_Summary, summary)
				.addEqualsFilter(I_R_Request.COLUMNNAME_DateDelivered, dateDelivered)
				.addEqualsFilter(I_R_Request.COLUMNNAME_Priority, priority)
				.addEqualsFilter(I_R_Request.COLUMNNAME_ConfidentialType, confidentialType)
				.addCompareFilter(I_R_Request.COLUMN_Created, CompareQueryFilter.Operator.EQUAL, SystemTime.asTimestamp())
				.create();
		final List<I_R_Request> requests = requestQuery.list();

		if (requests.isEmpty())
		{
			throw new AdempiereException("No new request found")
					.setParameter("query", requestQuery);
		}

	}
}
