/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

import java.util.List;
import java.util.Map;

import static de.metas.procurement.base.model.I_AD_User.COLUMNNAME_IsMFProcurementUser;
import static de.metas.procurement.base.model.I_AD_User.COLUMNNAME_ProcurementPassword;
import static org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_AD_User.COLUMNNAME_EMail;
import static org.compiere.model.I_AD_User.COLUMNNAME_Language;
import static org.compiere.model.I_AD_User.COLUMNNAME_Name;
import static org.compiere.model.I_AD_User.COLUMNNAME_Password;

public class AD_User_StepDef
{
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final StepDefData<I_C_BPartner> bpartnerTable;
	private final StepDefData<I_AD_User> userTable;

	public AD_User_StepDef(
			final StepDefData<I_C_BPartner> bpartnerTable,
			final StepDefData<I_AD_User> userTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.userTable = userTable;
	}

	@Given("metasfresh contains AD_Users:")
	public void metasfresh_contains_ad_users(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String name = tableRow.get(COLUMNNAME_Name);
			final String email = tableRow.get("OPT." + COLUMNNAME_EMail);

			final I_AD_User userRecord = InterfaceWrapperHelper.loadOrNew(userDAO.retrieveUserIdByEMail(email, ClientId.METASFRESH), I_AD_User.class);
			userRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			userRecord.setName(name);
			userRecord.setEMail(email);
			userRecord.setPassword(tableRow.get("OPT." + COLUMNNAME_Password));
			userRecord.setAD_Language(tableRow.get("OPT." + COLUMNNAME_Language));

			if (tableRow.containsKey("OPT." + COLUMNNAME_IsMFProcurementUser))
			{
				final de.metas.procurement.base.model.I_AD_User procurementUserRecord = InterfaceWrapperHelper.create(userRecord, de.metas.procurement.base.model.I_AD_User.class);
				procurementUserRecord.setIsMFProcurementUser(StringUtils.toBoolean(tableRow.get("OPT." + COLUMNNAME_IsMFProcurementUser), false));
			}
			if (tableRow.containsKey("OPT." + COLUMNNAME_ProcurementPassword))
			{
				final de.metas.procurement.base.model.I_AD_User procurementUserRecord = InterfaceWrapperHelper.create(userRecord, de.metas.procurement.base.model.I_AD_User.class);
				procurementUserRecord.setProcurementPassword(tableRow.get("OPT." + COLUMNNAME_ProcurementPassword));
			}

			final String bPartnerIdentifier = tableRow.get(COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bPartnerIdentifier))
			{
				final I_C_BPartner bPartner = bpartnerTable.get(bPartnerIdentifier);
				userRecord.setC_BPartner_ID(bPartner.getC_BPartner_ID());
			}
			InterfaceWrapperHelper.saveRecord(userRecord);

			DataTableUtil.extractRecordIdentifier(tableRow, "AD_User");
		}
	}
}