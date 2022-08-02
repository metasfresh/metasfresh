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

package de.metas.cucumber.stepdefs.authorization;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_AuthToken;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AD_User_AuthToken_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_User_AuthToken_StepDefData userAuthTokenTable;
	private final AD_User_StepDefData userTable;

	public AD_User_AuthToken_StepDef(
			@NonNull final AD_User_AuthToken_StepDefData userAuthTokenTable,
			@NonNull final AD_User_StepDefData userTable)
	{
		this.userAuthTokenTable = userAuthTokenTable;
		this.userTable = userTable;
	}

	@Given("metasfresh contains AD_User_AuthToken:")
	public void add_user_authtoken(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String userAuthTokenIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_User_AuthToken.COLUMNNAME_AD_User_AuthToken_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final String userIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_User_AuthToken.COLUMNNAME_AD_User_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final Integer userId = userTable.getOptional(userIdentifier)
					.map(I_AD_User::getAD_User_ID)
					.orElseGet(() -> Integer.parseInt(userIdentifier));

			final int roleId = DataTableUtil.extractIntForColumnName(row, I_AD_User_AuthToken.COLUMNNAME_AD_Role_ID);
			final String authToken = DataTableUtil.extractStringForColumnName(row, I_AD_User_AuthToken.COLUMNNAME_AuthToken);

			final I_AD_User_AuthToken userAuthToken = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_AD_User_AuthToken.class)
							.addEqualsFilter(I_AD_User_AuthToken.COLUMNNAME_AuthToken, authToken)
							.addEqualsFilter(I_AD_User_AuthToken.COLUMNNAME_AD_User_ID, userId)
							.create()
							.firstOnly(I_AD_User_AuthToken.class),
					() -> InterfaceWrapperHelper.newInstance(I_AD_User_AuthToken.class));

			assertThat(userAuthToken).isNotNull();

			userAuthToken.setAuthToken(authToken);
			userAuthToken.setAD_User_ID(userId);
			userAuthToken.setAD_Role_ID(roleId);

			InterfaceWrapperHelper.saveRecord(userAuthToken);

			userAuthTokenTable.putOrReplace(userAuthTokenIdentifier, userAuthToken);
		}
	}
}
