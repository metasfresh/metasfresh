package de.metas.cucumber.stepdefs.importorder;

import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_Roles;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for I_User (user import) staging table and role assignment validation.
 */
@RequiredArgsConstructor
public class I_User_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Given("all I_User staging records are deleted")
	public void all_I_User_staging_records_are_deleted()
	{
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM I_User WHERE AD_Client_ID=" + StepDefConstants.CLIENT_ID.getRepoId(),
				ITrx.TRXNAME_None);
	}

	@Then("the imported AD_User with login {string} has no role assignments")
	public void the_imported_AD_User_with_login_has_no_role_assignments(@NonNull final String login)
	{
		final I_AD_User user = lookupImportedUserByLogin(login);

		final long roleCount = queryBL.createQueryBuilderOutOfTrx(I_AD_User_Roles.class)
				.addEqualsFilter(I_AD_User_Roles.COLUMNNAME_AD_User_ID, user.getAD_User_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.count();

		assertThat(roleCount)
				.as("Expected no role assignments for user with login '%s' (AD_User_ID=%s)", login, user.getAD_User_ID())
				.isZero();
	}

	@Then("the imported AD_User with login {string} has role {string}")
	public void the_imported_AD_User_with_login_has_role(@NonNull final String login, @NonNull final String roleName)
	{
		final I_AD_User user = lookupImportedUserByLogin(login);

		final boolean hasRole = queryBL.createQueryBuilderOutOfTrx(I_AD_User_Roles.class)
				.addEqualsFilter(I_AD_User_Roles.COLUMNNAME_AD_User_ID, user.getAD_User_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.anyMatch(userRole -> {
					final String assignedRoleName = DB.getSQLValueStringEx(
							ITrx.TRXNAME_None,
							"SELECT Name FROM AD_Role WHERE AD_Role_ID=?",
							userRole.getAD_Role_ID());
					return roleName.equals(assignedRoleName);
				});

		assertThat(hasRole)
				.as("Expected user with login '%s' (AD_User_ID=%s) to have role '%s'", login, user.getAD_User_ID(), roleName)
				.isTrue();
	}

	private I_AD_User lookupImportedUserByLogin(@NonNull final String login)
	{
		final I_AD_User user = queryBL.createQueryBuilderOutOfTrx(I_AD_User.class)
				.addEqualsFilter(I_AD_User.COLUMNNAME_Login, login)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_Client_ID, StepDefConstants.CLIENT_ID)
				.create()
				.firstOnly(I_AD_User.class);

		assertThat(user)
				.as("Expected to find an imported AD_User with login '%s'", login)
				.isNotNull();

		return user;
	}
}
