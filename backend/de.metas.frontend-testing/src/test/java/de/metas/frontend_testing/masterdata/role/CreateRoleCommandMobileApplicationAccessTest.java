package de.metas.frontend_testing.masterdata.role;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.mobile.application.MobileApplicationActionId;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.mobile.application.repository.MobileApplicationInfoRepository;
import de.metas.security.RoleId;
import de.metas.security.mobile_application.MobileApplicationPermissions;
import de.metas.security.mobile_application.MobileApplicationPermissionsRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Mobile_Application;
import org.compiere.model.I_Mobile_Application_Action;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The login fixture can create a role whose access to a mobile application does not allow all actions, with or without
 * granted actions (test infrastructure for the per-role HU Manager action {@code createHU}).
 */
class CreateRoleCommandMobileApplicationAccessTest
{
	private MasterdataContext context;
	private MobileApplicationInfoRepository applicationInfoRepository;
	private MobileApplicationRepoId applicationId;
	private MobileApplicationActionId createHUActionId;
	private MobileApplicationActionId disposeActionId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, MasterdataContext.CLIENT_ID.getRepoId());
		context = new MasterdataContext();
		applicationInfoRepository = new MobileApplicationInfoRepository();

		final I_Mobile_Application application = InterfaceWrapperHelper.newInstance(I_Mobile_Application.class);
		application.setValue("huManager");
		application.setName("HU Manager");
		InterfaceWrapperHelper.saveRecord(application);
		applicationId = MobileApplicationRepoId.ofRepoId(application.getMobile_Application_ID());
		createHUActionId = createAction(application, "createHU");
		disposeActionId = createAction(application, "dispose");
	}

	private static MobileApplicationActionId createAction(final I_Mobile_Application application, final String internalName)
	{
		final I_Mobile_Application_Action action = InterfaceWrapperHelper.newInstance(I_Mobile_Application_Action.class);
		action.setMobile_Application_ID(application.getMobile_Application_ID());
		action.setInternalName(internalName);
		InterfaceWrapperHelper.saveRecord(action);
		return MobileApplicationActionId.ofRepoId(action.getMobile_Application_Action_ID());
	}

	private RoleId createRole(final JsonRoleMobileApplicationAccessRequest access)
	{
		return createRole(ImmutableList.of(access));
	}

	private RoleId createRole(final ImmutableList<JsonRoleMobileApplicationAccessRequest> access)
	{
		return CreateRoleCommand.builder()
				.context(context)
				.mobileApplicationInfoRepository(applicationInfoRepository)
				.identifier(Identifier.ofString("r"))
				.request(JsonCreateRoleRequest.builder()
						.includedRoles(ImmutableList.of())
						.mobileApplicationAccess(access)
						.build())
				.build()
				.execute()
				.getRoleId();
	}

	private MobileApplicationPermissions permissionsOf(final RoleId roleId)
	{
		return new MobileApplicationPermissionsRepository().getMobileApplicationPermissions(roleId);
	}

	@Test
	void notAllowAll_noGrantedAction_appAccessButNoAction()
	{
		final RoleId roleId = createRole(JsonRoleMobileApplicationAccessRequest.builder()
				.application("huManager").allowAllActions(false).build());

		final MobileApplicationPermissions permissions = permissionsOf(roleId);
		assertThat(permissions.isAllowAccess(applicationId)).isTrue();
		assertThat(permissions.isAllowAction(applicationId, createHUActionId)).isFalse();
		assertThat(permissions.isAllowAction(applicationId, disposeActionId)).isFalse();
	}

	@Test
	void notAllowAll_onlyCreateHUGranted_onlyThatAction()
	{
		final RoleId roleId = createRole(JsonRoleMobileApplicationAccessRequest.builder()
				.application("huManager").allowAllActions(false).grantedActions(ImmutableList.of("createHU")).build());

		final MobileApplicationPermissions permissions = permissionsOf(roleId);
		assertThat(permissions.isAllowAccess(applicationId)).isTrue();
		assertThat(permissions.isAllowAction(applicationId, createHUActionId)).isTrue();
		assertThat(permissions.isAllowAction(applicationId, disposeActionId)).isFalse();
	}

	@Test
	void allowAllDefault_allActions()
	{
		final RoleId roleId = createRole(JsonRoleMobileApplicationAccessRequest.builder().application("huManager").build());

		final MobileApplicationPermissions permissions = permissionsOf(roleId);
		assertThat(permissions.isAllowAction(applicationId, createHUActionId)).isTrue();
		assertThat(permissions.isAllowAction(applicationId, disposeActionId)).isTrue();
	}

	@Test
	void unknownGrantedAction_failsNamingIt()
	{
		assertThatThrownBy(() -> createRole(JsonRoleMobileApplicationAccessRequest.builder()
				.application("huManager").allowAllActions(false).grantedActions(ImmutableList.of("noSuchAction")).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("noSuchAction");
	}

	@Test
	void sameApplicationTwice_failsNamingIt()
	{
		assertThatThrownBy(() -> createRole(ImmutableList.of(
				JsonRoleMobileApplicationAccessRequest.builder().application("huManager").build(),
				JsonRoleMobileApplicationAccessRequest.builder().application("huManager").allowAllActions(false).build())))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("huManager")
				.hasMessageContaining("more than once");
	}

	@Test
	void grantedActionsWithAllowAll_failsNamingIt()
	{
		assertThatThrownBy(() -> createRole(JsonRoleMobileApplicationAccessRequest.builder()
				.application("huManager").allowAllActions(true).grantedActions(ImmutableList.of("createHU")).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("grantedActions")
				.hasMessageContaining("allowAllActions");
	}

	@Test
	void unknownApplication_failsNamingIt()
	{
		assertThatThrownBy(() -> createRole(JsonRoleMobileApplicationAccessRequest.builder().application("noSuchApp").build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("noSuchApp");
	}

	/** what the masterdata spec sends: no {@code allowAllActions} means allow all */
	@Test
	void json_allowAllActionsDefaultsToTrue() throws Exception
	{
		final JsonRoleMobileApplicationAccessRequest request = new ObjectMapper()
				.readValue("{\"application\":\"huManager\"}", JsonRoleMobileApplicationAccessRequest.class);

		assertThat(request.isAllowAllActions()).isTrue();
		assertThat(request.getGrantedActions()).isNull();
	}
}
