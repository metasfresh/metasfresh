/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AD_OrgInfo_StepDef
{
	private final AD_Org_StepDefData orgTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final AD_OrgInfo_StepDefData orgInfoTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AD_OrgInfo_StepDef(
			@NonNull final AD_Org_StepDefData orgTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final AD_OrgInfo_StepDefData orgInfoTable)
	{
		this.orgTable = orgTable;
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.orgInfoTable = orgInfoTable;
	}

	@Given("metasfresh contains AD_OrgInfo:")
	public void metasfresh_contains_ad_org_info(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orgIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Org.COLUMNNAME_AD_Org_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_AD_Org org = orgTable.get(orgIdentifier);

			final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_OrgInfo.COLUMNNAME_Org_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner orgBPartner = bPartnerTable.get(bPartnerIdentifier);

			final String bPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_OrgInfo.COLUMNNAME_OrgBP_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location orgBPartnerLocation = bPartnerLocationTable.get(bPartnerLocationIdentifier);

			final I_AD_OrgInfo orgInfoRecord = CoalesceUtil.coalesce(queryBL.createQueryBuilder(I_AD_OrgInfo.class)
																			 .addEqualsFilter(I_AD_OrgInfo.COLUMNNAME_AD_Org_ID, org.getAD_Org_ID())
																			 .create()
																			 .firstOnly(I_AD_OrgInfo.class),
																	 InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_OrgInfo.class));

			assertThat(orgInfoRecord).isNotNull();

			orgInfoRecord.setAD_Org_ID(org.getAD_Org_ID());
			orgInfoRecord.setOrg_BPartner_ID(orgBPartner.getC_BPartner_ID());
			orgInfoRecord.setOrgBP_Location_ID(orgBPartnerLocation.getC_BPartner_Location_ID());

			InterfaceWrapperHelper.saveRecord(orgInfoRecord);

			final String orgInfoIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_OrgInfo.COLUMNNAME_AD_OrgInfo_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			orgInfoTable.putOrReplace(orgInfoIdentifier, orgInfoRecord);
		}
	}
}
