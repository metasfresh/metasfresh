package de.metas.handlingunits.mobileui;

import de.metas.JsonObjectMapperHolder;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.AdRefListRepositoryMocked;
import de.metas.ad_reference.AdRefTableRepositoryMocked;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.grai.HUPIGraiRepository;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.mobileui.config.HUManagerProfileRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.rest_api.HandlingUnitsService;
import de.metas.handlingunits.rest_api.createfromlabel.CreateHUFromLabelService;
import de.metas.handlingunits.rest_api.createfromlabel.JsonCreateHUFromScannedCodeRequest;
import de.metas.handlingunits.rest_api.createfromlabel.JsonProposeHUFromScannedCodeRequest;
import de.metas.handlingunits.rest_api.createfromlabel.LabelValuesService;
import de.metas.handlingunits.rest_api.createfromlabel.LabelTargetLocatorResolver;
import de.metas.inventory.InventoryCandidateRepository;
import de.metas.inventory.InventoryCandidateService;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.mobile.application.repository.MobileApplicationInfoRepository;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.security.RoleId;
import de.metas.uom.IUOMDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_Mobile_Application;
import org.compiere.model.I_Mobile_Application_Access;
import org.compiere.model.I_Mobile_Application_Action;
import org.compiere.model.I_Mobile_Application_Action_Access;
import org.compiere.model.X_AD_Role;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The create-from-label endpoints are open only to a role whose access to the HU Manager application includes the action
 * {@code createHU} (granted explicitly, or by "allow all actions"). The logged-in role is the real in-memory role, built from
 * {@code Mobile_Application_Access} / {@code Mobile_Application_Action_Access} records, so the whole permission path of the controller runs;
 * "the service was not reached" is asserted by its outcome (the refusal, and no HU / inventory booked).
 * The UI flow per role stays proven by the HU Manager Playwright specs.
 */
class HUManagerRestControllerCreateHUAccessTest
{
	private static final String LABEL = "01" + "02001234567817";

	private IQueryBL queryBL;
	private HUManagerRestController controller;
	private I_Mobile_Application application;
	private I_Mobile_Application_Action createHUAction;
	private I_Mobile_Application_Action disposeAction;
	private int roleCounter = 0;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);
		// the role's permissions are built with the client record
		final I_AD_Client client = InterfaceWrapperHelper.newInstance(I_AD_Client.class);
		client.setName("client");
		InterfaceWrapperHelper.saveRecord(client);
		final I_AD_ClientInfo clientInfo = InterfaceWrapperHelper.newInstance(I_AD_ClientInfo.class);
		InterfaceWrapperHelper.setValue(clientInfo, I_AD_ClientInfo.COLUMNNAME_AD_Client_ID, client.getAD_Client_ID());
		InterfaceWrapperHelper.saveRecord(clientInfo);
		Env.setClientId(Env.getCtx(), ClientId.ofRepoId(client.getAD_Client_ID()));
		Env.setOrgId(Env.getCtx(), AdempiereTestHelper.createOrgWithTimeZone());

		// the label lookup also tries the code as an ExternalBarcode attribute value
		final I_M_Attribute externalBarcode = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		externalBarcode.setValue(AttributeConstants.ATTR_ExternalBarcode.getCode());
		externalBarcode.setName("ExternalBarcode");
		externalBarcode.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		InterfaceWrapperHelper.saveRecord(externalBarcode);

		application = InterfaceWrapperHelper.newInstance(I_Mobile_Application.class);
		application.setValue(HUManagerMobileApplication.APPLICATION_ID.getAsString());
		application.setName("HU Manager");
		InterfaceWrapperHelper.saveRecord(application);
		createHUAction = newAction(HUManagerAction.CreateHU.getInternalName());
		disposeAction = newAction(HUManagerAction.Dispose.getInternalName());

		final HUQRCodesService huQRCodesService = HUQRCodesService.newInstanceForUnitTesting();
		final HUQtyService huQtyService = HUQtyService.newInstanceForUnitTesting();
		final InventoryCandidateService inventoryCandidateService = new InventoryCandidateService(new ADReferenceService(new AdRefListRepositoryMocked(), new AdRefTableRepositoryMocked()), new InventoryCandidateRepository());
		controller = new HUManagerRestController(
				new MobileApplicationService(new MobileApplicationInfoRepository(), Optional.empty()),
				new HUManagerProfileRepository(),
				new HandlingUnitsService(
						huQRCodesService,
						huQtyService,
						HULabelService.newInstanceForUnitTesting(),
						inventoryCandidateService,
						new HUGraiService(new HUPIGraiRepository())),
				inventoryCandidateService,
				new CreateHUFromLabelService(huQtyService, new LabelValuesService(huQRCodesService, Services.get(IProductBL.class), Services.get(IUOMDAO.class)), new LabelTargetLocatorResolver(new WorkplaceService(WorkplaceRepository.newInstanceForUnitTesting(), new WorkplaceUserAssignRepository()), Services.get(IWarehouseDAO.class), Services.get(IOrgDAO.class))));
	}

	private I_Mobile_Application_Action newAction(final String internalName)
	{
		final I_Mobile_Application_Action action = InterfaceWrapperHelper.newInstance(I_Mobile_Application_Action.class);
		action.setMobile_Application_ID(application.getMobile_Application_ID());
		action.setInternalName(internalName);
		InterfaceWrapperHelper.saveRecord(action);
		return action;
	}

	/** logs in a new role with access to the HU Manager; {@code grantedAction} only matters when not {@code allowAllActions} */
	private void loginWithHUManagerAccess(final boolean allowAllActions, final I_Mobile_Application_Action grantedAction)
	{
		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setName("user" + (roleCounter + 1));
		InterfaceWrapperHelper.saveRecord(user);
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(user.getAD_User_ID())); // the role's UpdatedBy

		final I_AD_Role role = InterfaceWrapperHelper.newInstance(I_AD_Role.class);
		role.setName("role" + (++roleCounter));
		role.setUserLevel(X_AD_Role.USERLEVEL_Organization);
		role.setPreferenceType(X_AD_Role.PREFERENCETYPE_Organization);
		InterfaceWrapperHelper.saveRecord(role);
		final I_Mobile_Application_Access access = InterfaceWrapperHelper.newInstance(I_Mobile_Application_Access.class);
		access.setAD_Role_ID(role.getAD_Role_ID());
		access.setMobile_Application_ID(application.getMobile_Application_ID());
		access.setIsAllowAllActions(allowAllActions);
		InterfaceWrapperHelper.saveRecord(access);
		if (grantedAction != null)
		{
			final I_Mobile_Application_Action_Access actionAccess = InterfaceWrapperHelper.newInstance(I_Mobile_Application_Action_Access.class);
			actionAccess.setAD_Role_ID(role.getAD_Role_ID());
			actionAccess.setMobile_Application_ID(application.getMobile_Application_ID());
			actionAccess.setMobile_Application_Action_ID(grantedAction.getMobile_Application_Action_ID());
			InterfaceWrapperHelper.saveRecord(actionAccess);
		}

		Env.setLoggedRoleId(Env.getCtx(), RoleId.ofRepoId(role.getAD_Role_ID()));

		// the role resolves from the records, so the controller below reads its real mobile-application permissions
		assertThat(Env.getUserRolePermissions().getMobileApplicationPermissions()
				.isAllowAccess(MobileApplicationRepoId.ofRepoId(application.getMobile_Application_ID()))).isTrue();
	}

	private JsonProposeHUFromScannedCodeRequest proposeRequest() throws IOException
	{
		return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue("{\"scannedCode\":\"" + LABEL + "\"}", JsonProposeHUFromScannedCodeRequest.class);
	}

	private JsonCreateHUFromScannedCodeRequest createRequest() throws IOException
	{
		return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue("{\"scannedCode\":\"" + LABEL + "\",\"qty\":\"1\",\"locatorQRCode\":\"x\"}", JsonCreateHUFromScannedCodeRequest.class);
	}

	private void assertNothingBooked()
	{
		assertThat(queryBL.createQueryBuilder(I_M_HU.class).create().count()).isZero();
		assertThat(queryBL.createQueryBuilder(I_M_Inventory.class).create().count()).isZero();
	}

	/** the call got past the access check: whatever it does next (a proposal, a rejection of this made-up label), it is not the refusal */
	private static void assertPassesAccessCheck(final ThrowingRunnable call)
	{
		try
		{
			call.run();
		}
		catch (final Exception ex)
		{
			assertThat(ex).as("the access check must not refuse").hasMessageNotContaining("Access denied");
		}
	}

	@FunctionalInterface
	private interface ThrowingRunnable
	{
		void run() throws Exception;
	}

	/** (1) application access, but no createHU grant; (2) allow-all role: createHU like every other action; (3) explicit createHU grant */
	@Nested
	class ProposeFromScannedCode
	{
		@Test
		void appAccessWithoutCreateHUGrant_accessDenied() throws IOException
		{
			loginWithHUManagerAccess(false, disposeAction); // another action granted, createHU not
			final JsonProposeHUFromScannedCodeRequest request = proposeRequest();

			assertThatThrownBy(() -> controller.proposeFromScannedCode(request))
					.isInstanceOf(AdempiereException.class)
					.hasMessage("Access denied");
			assertNothingBooked();
		}

		@Test
		void allowAllRole_passesAccessCheck() throws IOException
		{
			loginWithHUManagerAccess(true, null);
			final JsonProposeHUFromScannedCodeRequest request = proposeRequest();

			assertPassesAccessCheck(() -> controller.proposeFromScannedCode(request));
		}

		@Test
		void createHUGranted_passesAccessCheck() throws IOException
		{
			loginWithHUManagerAccess(false, createHUAction);
			final JsonProposeHUFromScannedCodeRequest request = proposeRequest();

			assertPassesAccessCheck(() -> controller.proposeFromScannedCode(request));
		}
	}

	@Nested
	class CreateFromScannedCode
	{
		@Test
		void appAccessWithoutCreateHUGrant_accessDenied() throws IOException
		{
			loginWithHUManagerAccess(false, null);
			final JsonCreateHUFromScannedCodeRequest request = createRequest();

			assertThatThrownBy(() -> controller.createFromScannedCode(request))
					.isInstanceOf(AdempiereException.class)
					.hasMessage("Access denied");
			assertNothingBooked();
		}

		@Test
		void allowAllRole_passesAccessCheck() throws IOException
		{
			loginWithHUManagerAccess(true, null);
			final JsonCreateHUFromScannedCodeRequest request = createRequest();

			assertPassesAccessCheck(() -> controller.createFromScannedCode(request));
		}

		@Test
		void createHUGranted_passesAccessCheck() throws IOException
		{
			loginWithHUManagerAccess(false, createHUAction);
			final JsonCreateHUFromScannedCodeRequest request = createRequest();

			assertPassesAccessCheck(() -> controller.createFromScannedCode(request));
		}
	}
}
