package de.metas.security.mobile_application;

import com.google.common.collect.ImmutableSet;
import de.metas.mobile.application.MobileApplicationActionId;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.security.requests.CreateMobileApplicationAccessRequest;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Mobile_Application;
import org.compiere.model.I_Mobile_Application_Access;
import org.compiere.model.I_Mobile_Application_Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@code createMobileApplicationAccess} is an upsert: a role usually already has an allow-all access row to every
 * application (the DB function {@code role_access_update()} creates them when the role is created), so a request
 * that restricts the access has to change that row instead of inserting a second one (unique per role and application).
 */
class MobileApplicationPermissionsRepositoryCreateAccessTest
{
	private static final RoleId ROLE_ID = RoleId.ofRepoId(1000046);

	private final MobileApplicationPermissionsRepository repository = new MobileApplicationPermissionsRepository();
	private MobileApplicationRepoId applicationId;
	private MobileApplicationActionId actionId;
	private MobileApplicationActionId otherActionId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		final I_Mobile_Application application = InterfaceWrapperHelper.newInstance(I_Mobile_Application.class);
		application.setValue("huManager");
		application.setName("HU Manager");
		InterfaceWrapperHelper.saveRecord(application);
		applicationId = MobileApplicationRepoId.ofRepoId(application.getMobile_Application_ID());

		final I_Mobile_Application_Action action = InterfaceWrapperHelper.newInstance(I_Mobile_Application_Action.class);
		action.setMobile_Application_ID(applicationId.getRepoId());
		action.setInternalName("createHU");
		InterfaceWrapperHelper.saveRecord(action);
		actionId = MobileApplicationActionId.ofRepoId(action.getMobile_Application_Action_ID());

		final I_Mobile_Application_Action otherAction = InterfaceWrapperHelper.newInstance(I_Mobile_Application_Action.class);
		otherAction.setMobile_Application_ID(applicationId.getRepoId());
		otherAction.setInternalName("dispose");
		InterfaceWrapperHelper.saveRecord(otherAction);
		otherActionId = MobileApplicationActionId.ofRepoId(otherAction.getMobile_Application_Action_ID());
	}

	private CreateMobileApplicationAccessRequest.CreateMobileApplicationAccessRequestBuilder request()
	{
		return CreateMobileApplicationAccessRequest.builder()
				.roleId(ROLE_ID).clientId(ClientId.METASFRESH).orgId(OrgId.MAIN).applicationId(applicationId);
	}

	private long accessRowCount()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_Mobile_Application_Access.class).create().count();
	}

	@Test
	void noAllowAllActionsGiven_newRowAllowsAll()
	{
		repository.createMobileApplicationAccess(request().build());

		assertThat(repository.getMobileApplicationPermissions(ROLE_ID).isAllowAction(applicationId, actionId)).isTrue();
	}

	@Test
	void noAllowAllActionsGiven_existingRestrictedRowStaysRestricted()
	{
		repository.createMobileApplicationAccess(request().allowAllActions(false).build());

		repository.createMobileApplicationAccess(request().build()); // e.g. the POS grant or the Mobile_Application interceptor

		assertThat(repository.getMobileApplicationPermissions(ROLE_ID).isAllowAction(applicationId, actionId)).isFalse();
	}

	@Test
	void notAllowAll_existingAllowAllRowIsRestricted_noSecondRow()
	{
		repository.createMobileApplicationAccess(request().build()); // as role_access_update() leaves it

		repository.createMobileApplicationAccess(request().allowAllActions(false).build());

		assertThat(accessRowCount()).isEqualTo(1);
		final MobileApplicationPermissions permissions = repository.getMobileApplicationPermissions(ROLE_ID);
		assertThat(permissions.isAllowAccess(applicationId)).isTrue();
		assertThat(permissions.isAllowAction(applicationId, actionId)).isFalse();
	}

	@Test
	void notAllowAll_withAllowedAction_onlyThatActionIsAllowed_idempotent()
	{
		repository.createMobileApplicationAccess(request().build());

		repository.createMobileApplicationAccess(request().allowAllActions(false).allowedActionIds(ImmutableSet.of(actionId)).build());
		repository.createMobileApplicationAccess(request().allowAllActions(false).allowedActionIds(ImmutableSet.of(actionId)).build());

		final MobileApplicationPermissions permissions = repository.getMobileApplicationPermissions(ROLE_ID);
		assertThat(permissions.isAllowAction(applicationId, actionId)).isTrue();
		assertThat(permissions.isAllowAction(applicationId, otherActionId)).isFalse();
	}
}
