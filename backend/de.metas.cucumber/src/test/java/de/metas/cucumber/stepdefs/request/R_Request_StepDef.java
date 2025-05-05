/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_R_Request;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;


public class R_Request_StepDef
{
	private final R_Request_StepDefData requestTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final AD_Org_StepDefData orgTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	public  R_Request_StepDef(
			@NonNull final R_Request_StepDefData requestTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final AD_Org_StepDefData orgTable
	)
	{
		this.requestTable = requestTable;
		this.bPartnerTable = bPartnerTable;
		this.orgTable = orgTable;
	}

	@And("^after not more than (.*)s, R_Request are found:$")
	public void validate_created_r_requests(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> tableRow : dataTable)
		{
			final String requestIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_R_Request.COLUMNNAME_R_RequestType_ID + "." + TABLECOLUMN_IDENTIFIER);

			final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_R_Request.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedBPartnerId = bPartnerTable.getOptional(bPartnerIdentifier)
					.map(I_C_BPartner::getC_BPartner_ID)
					.orElseGet(() -> Integer.parseInt(bPartnerIdentifier));

			final String orgIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_R_Request.COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedOrgId = orgTable.getOptional(orgIdentifier)
					.map(I_AD_Org::getAD_Org_ID)
					.orElseGet(() -> Integer.parseInt(orgIdentifier));

			final String internalName = DataTableUtil.extractStringForColumnName(tableRow, I_R_Request.COLUMNNAME_R_RequestType_InternalName);

			final Supplier<Boolean> QueryExecutor = () -> {

				final I_R_Request requestRecord = queryBL.createQueryBuilder(I_R_Request.class)
						.addEqualsFilter(I_R_Request.COLUMNNAME_C_BPartner_ID, expectedBPartnerId)
						.addEqualsFilter(I_R_Request.COLUMNNAME_AD_Org_ID, expectedOrgId)
						.addEqualsFilter(I_R_Request.COLUMNNAME_R_RequestType_InternalName, internalName)
						.create()
						.firstOnly(I_R_Request.class);


				if (requestRecord == null)
				{
					return false;
				}

				requestTable.putOrReplace(requestIdentifier, requestRecord);
				return true;
			};

			StepDefUtil.tryAndWait(timeoutSec, 500, QueryExecutor);
		}
	}
}
