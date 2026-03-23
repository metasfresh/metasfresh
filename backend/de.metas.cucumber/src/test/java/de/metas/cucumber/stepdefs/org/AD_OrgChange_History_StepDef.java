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

package de.metas.cucumber.stepdefs.org;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgChange_History;
import org.compiere.model.I_C_BPartner;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class AD_OrgChange_History_StepDef
{
	private final AD_OrgChange_History_StepDefData orgChangeHistoryTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final AD_Org_StepDefData orgTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AD_OrgChange_History_StepDef(
			@NonNull final AD_OrgChange_History_StepDefData orgChangeHistoryTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final AD_Org_StepDefData orgTable
	)
	{
		this.orgChangeHistoryTable = orgChangeHistoryTable;
		this.bPartnerTable = bPartnerTable;
		this.orgTable = orgTable;
	}

	@And("^after not more than (.*)s, AD_OrgChange_History are found:$")
	public void validate_created_org_change_histories(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> tableRow : dataTable)
		{
			final String orgChangeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_OrgChange_History.COLUMNNAME_AD_OrgChange_History_ID + "." + TABLECOLUMN_IDENTIFIER);

			final String bPartnerFromIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_OrgChange_History.COLUMNNAME_C_BPartner_From_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedFromBPartnerId = bPartnerTable.getOptional(bPartnerFromIdentifier)
					.map(I_C_BPartner::getC_BPartner_ID)
					.orElseGet(() -> Integer.parseInt(bPartnerFromIdentifier));

			final String bPartnerToIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_OrgChange_History.COLUMNNAME_C_BPartner_To_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedToBPartnerId = bPartnerTable.getOptional(bPartnerToIdentifier)
					.map(I_C_BPartner::getC_BPartner_ID)
					.orElseGet(() -> Integer.parseInt(bPartnerToIdentifier));

				final String orgFromIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_OrgChange_History.COLUMNNAME_AD_Org_From_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedFromOrgId = orgTable.getOptional(orgFromIdentifier)
					.map(I_AD_Org::getAD_Org_ID)
					.orElseGet(() -> Integer.parseInt(orgFromIdentifier));

			final String orgToIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_OrgChange_History.COLUMNNAME_AD_OrgTo_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedToOrgId = orgTable.getOptional(orgToIdentifier)
					.map(I_AD_Org::getAD_Org_ID)
					.orElseGet(() -> Integer.parseInt(orgToIdentifier));

			final Supplier<Boolean> QueryExecutor = () -> {

				final I_AD_OrgChange_History orgChangeRecord = queryBL.createQueryBuilder(I_AD_OrgChange_History.class)
						.addEqualsFilter(I_AD_OrgChange_History.COLUMNNAME_C_BPartner_From_ID, expectedFromBPartnerId)
						.addEqualsFilter(I_AD_OrgChange_History.COLUMNNAME_C_BPartner_To_ID, expectedToBPartnerId)
						.addEqualsFilter(I_AD_OrgChange_History.COLUMNNAME_AD_Org_From_ID, expectedFromOrgId)
						.addEqualsFilter(I_AD_OrgChange_History.COLUMNNAME_AD_OrgTo_ID, expectedToOrgId)
						.create()
						.firstOnly(I_AD_OrgChange_History.class);


				if (orgChangeRecord == null)
				{
					return false;
				}

				orgChangeHistoryTable.putOrReplace(orgChangeIdentifier, orgChangeRecord);
				return true;
			};

			StepDefUtil.tryAndWait(timeoutSec, 500, QueryExecutor);
		}
	}
}
