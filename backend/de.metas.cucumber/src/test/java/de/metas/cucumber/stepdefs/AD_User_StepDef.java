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

package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.security.IRoleDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.procurement.base.model.I_AD_User.COLUMNNAME_IsMFProcurementUser;
import static de.metas.procurement.base.model.I_AD_User.COLUMNNAME_ProcurementPassword;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_AD_User.COLUMNNAME_AD_Language;
import static org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID;
import static org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_Location_ID;
import static org.compiere.model.I_AD_User.COLUMNNAME_EMail;
import static org.compiere.model.I_AD_User.COLUMNNAME_IsBillToContact_Default;
import static org.compiere.model.I_AD_User.COLUMNNAME_Login;
import static org.compiere.model.I_AD_User.COLUMNNAME_Name;
import static org.compiere.model.I_AD_User.COLUMNNAME_NotificationType;
import static org.compiere.model.I_AD_User.COLUMNNAME_Password;
import static org.compiere.model.I_AD_User.COLUMNNAME_Phone;

@RequiredArgsConstructor
public class AD_User_StepDef
{
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final TestContext restTestContext;

	private final AD_User_StepDefData userTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;

	@Given("metasfresh contains AD_Users:")
	public void metasfresh_contains_ad_users(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createUser(tableRow);
		}
	}

	@And("load AD_User:")
	public void load_ad_user(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String login = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Login);

			final I_AD_User userRecord = queryBL.createQueryBuilder(I_AD_User.class)
					.addEqualsFilter(COLUMNNAME_Login, login)
					.create()
					.firstOnlyNotNull(I_AD_User.class);

			final String userIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
			userTable.put(userIdentifier, userRecord);
		}
	}

	@And("update AD_User:")
	public void update_ad_user(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String userIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_AD_User user = userTable.get(userIdentifier);
			assertThat(user).isNotNull();

			final String email = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_EMail);
			if (Check.isNotBlank(email))
			{
				user.setEMail(email);
			}

			final String phone = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Phone);
			if (Check.isNotBlank(phone))
			{
				user.setPhone(phone);
			}

			InterfaceWrapperHelper.saveRecord(user);
			userTable.putOrReplace(userIdentifier, user);
		}
	}

	private void createUser(final Map<String, String> tableRowMap)
	{
		final DataTableRow tableRow = DataTableRow.singleRow(tableRowMap);

		final ValueAndName valueAndName = tableRow.suggestValueAndName();
		final String email = tableRow.getAsOptionalString(COLUMNNAME_EMail).orElse(null);

		final I_AD_User userRecord = retrieveOrCreateUserRecord(email, valueAndName);

		if (InterfaceWrapperHelper.isNew(userRecord))
		{
			Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(tableRowMap, "OPT." + COLUMNNAME_AD_User_ID))
					.ifPresent(userRecord::setAD_User_ID);
		}

		userRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		userRecord.setName(valueAndName.getName());
		userRecord.setEMail(email);
		
		tableRow.getAsOptionalString(COLUMNNAME_Password).ifPresent(userRecord::setPassword);
		tableRow.getAsOptionalString(COLUMNNAME_AD_Language).ifPresent(userRecord::setAD_Language);
		tableRow.getAsOptionalBoolean(COLUMNNAME_IsMFProcurementUser).ifPresent(b -> {
			final de.metas.procurement.base.model.I_AD_User procurementUserRecord = InterfaceWrapperHelper.create(userRecord, de.metas.procurement.base.model.I_AD_User.class);
			procurementUserRecord.setIsMFProcurementUser(b);
		});

		if (tableRowMap.containsKey("OPT." + COLUMNNAME_ProcurementPassword))
		{
			final de.metas.procurement.base.model.I_AD_User procurementUserRecord = InterfaceWrapperHelper.create(userRecord, de.metas.procurement.base.model.I_AD_User.class);
			procurementUserRecord.setProcurementPassword(tableRowMap.get("OPT." + COLUMNNAME_ProcurementPassword));
		}

		final String bPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRowMap, "OPT." + COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bPartnerIdentifier))
		{
			final I_C_BPartner bPartner = bpartnerTable.get(bPartnerIdentifier);
			userRecord.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		}

		final String bpLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRowMap, "OPT." + COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bpLocationIdentifier))
		{
			final I_C_BPartner_Location bPartnerLocation = bpartnerLocationTable.get(bpLocationIdentifier);
			assertThat(bPartnerLocation).isNotNull();

			userRecord.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());
		}

		final String phone = DataTableUtil.extractStringOrNullForColumnName(tableRowMap, "OPT." + I_AD_User.COLUMNNAME_Phone);
		if (Check.isNotBlank(phone))
		{
			userRecord.setPhone(phone);
		}

		final String login = DataTableUtil.extractStringOrNullForColumnName(tableRowMap, "OPT." + COLUMNNAME_Login);
		if (Check.isNotBlank(login))
		{
			userRecord.setLogin(login);
		}

		final Boolean isBillToContactDefault = DataTableUtil.extractBooleanForColumnNameOr(tableRowMap, "OPT." + COLUMNNAME_IsBillToContact_Default, null);
		if (isBillToContactDefault != null)
		{
			userRecord.setIsBillToContact_Default(isBillToContactDefault);
		}

		final String notificationType = DataTableUtil.extractStringOrNullForColumnName(tableRowMap, "OPT." + COLUMNNAME_NotificationType);
		if (Check.isNotBlank(notificationType))
		{
			userRecord.setNotificationType(notificationType);
		}

		InterfaceWrapperHelper.saveRecord(userRecord);

		final Integer roleId = DataTableUtil.extractIntegerOrNullForColumnName(tableRowMap, "OPT.Role_ID");
		if (roleId != null)
		{
			roleDAO.createUserRoleAssignmentIfMissing(UserId.ofRepoId(userRecord.getAD_User_ID()), RoleId.ofRepoId(roleId));
		}

		tableRow.getAsOptionalIdentifier("REST.Context.AD_User_ID")
				.ifPresent(id -> restTestContext.setVariable(id.getAsString(), userRecord.getAD_User_ID()));
		tableRow.getAsIdentifier(COLUMNNAME_AD_User_ID).putOrReplace(userTable, userRecord);
	}

	private I_AD_User retrieveOrCreateUserRecord(
			@Nullable final String email,
			@NonNull final ValueAndName valueAndName)
	{
		I_AD_User userRecord = null;
		{
			final UserId exitingUserIdPerMail = userDAO.retrieveUserIdByEMail(email, ClientId.METASFRESH);
			if (exitingUserIdPerMail != null)
			{
				userRecord = InterfaceWrapperHelper.load(exitingUserIdPerMail, I_AD_User.class);
			}

			if (userRecord == null)
			{
				userRecord = queryBL.createQueryBuilder(I_AD_User.class).addOnlyActiveRecordsFilter()
						.addEqualsFilter(COLUMNNAME_Name, valueAndName.getName())
						.create()
						.firstOnly(I_AD_User.class);
			}
			if (userRecord == null)
			{
				userRecord = InterfaceWrapperHelper.newInstance(I_AD_User.class);
			}
		}
		return userRecord;
	}
}