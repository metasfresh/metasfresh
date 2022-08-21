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

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.role.AD_Role_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_AuthToken;
import org.compiere.util.DB;

import java.util.Map;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AD_User_AuthToken_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_User_AuthToken_StepDefData userAuthTokenTable;
	private final AD_User_StepDefData userTable;
	private final AD_Role_StepDefData roleTable;

	public AD_User_AuthToken_StepDef(
			@NonNull final AD_User_AuthToken_StepDefData userAuthTokenTable,
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final AD_Role_StepDefData roleTable)
	{
		this.userAuthTokenTable = userAuthTokenTable;
		this.userTable = userTable;
		this.roleTable = roleTable;
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

	@And("^no AD_User_AuthToken records for userId:(.*)$")
	public void no_AD_User_AuthToken_records(final String userIdentifier)
	{
		final I_AD_User user = userTable.get(userIdentifier);

		DB.executeUpdateEx("DELETE FROM AD_User_AuthToken where AD_User_ID=" + user.getAD_User_ID(), ITrx.TRXNAME_None);
	}

	@And("^after not more than (.*)s, validate AD_User_AuthToken record$")
	public void validate_AD_User_AuthToken(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String userIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_User_AuthToken.COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer userId = userTable.get(userIdentifier).getAD_User_ID();

			final String roleIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_User_AuthToken.COLUMNNAME_AD_Role_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer roleId = roleTable.get(roleIdentifier).getAD_Role_ID();

			final Supplier<Boolean> userAuthTokenFound = () -> {
				final ImmutableList<I_AD_User_AuthToken> userAuthTokenList = queryBL.createQueryBuilder(I_AD_User_AuthToken.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_AD_User_AuthToken.COLUMN_AD_User_ID, userId)
						.addEqualsFilter(I_AD_User_AuthToken.COLUMN_AD_Role_ID, roleId)
						.create()
						.stream()
						.collect(ImmutableList.toImmutableList());

				if (userAuthTokenList.isEmpty())
				{
					return false;
				}

				assertThat(userAuthTokenList.size()).isEqualTo(1);

				final String adUserAuthTokenIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_User_AuthToken.COLUMNNAME_AD_User_AuthToken_ID + "." + TABLECOLUMN_IDENTIFIER);
				userAuthTokenTable.putOrReplace(adUserAuthTokenIdentifier, userAuthTokenList.get(0));
				return true;
			};

			//dev-note: tryAndWait will fail if AD_User_AuthToken record is not found
			StepDefUtil.tryAndWait(timeoutSec, 1000, userAuthTokenFound);
		}
	}
}
