package de.metas.frontend_testing;

import de.metas.Profiles;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.frontend_testing.expectations.AssertExpectationsCommand;
import de.metas.frontend_testing.expectations.request.JsonExpectations;
import de.metas.frontend_testing.expectations.request.JsonExpectationsResponse;
import de.metas.frontend_testing.masterdata.CreateMasterdataCommand;
import de.metas.frontend_testing.masterdata.CreateMasterdataCommandSupportingServices;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataRequest;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import de.metas.frontend_testing.masterdata.picking_slot.JsonGetFreePickingSlotRequest;
import de.metas.frontend_testing.masterdata.picking_slot.JsonGetFreePickingSlotResponse;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.security.permissions2.PermissionNotGrantedException;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;

@RequestMapping(FrontendTestingRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class FrontendTestingRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/frontendTesting";
	private static final String SYSCONFIG_Enabled = "frontend.testing";

	@NonNull private final Logger logger = LogManager.getLogger(FrontendTestingRestController.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);
	@NonNull private final UserAuthTokenFilterConfiguration userAuthTokenFilterConfiguration;
	@NonNull private final CreateMasterdataCommandSupportingServices services;

	private boolean isEnabled()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_Enabled, false);
	}

	private void assertEnabled()
	{
		if (!isEnabled())
		{
			throw new PermissionNotGrantedException(TranslatableStrings.constant("Frontend testing REST endpoints are disabled"));
		}
	}

	@PostConstruct
	public void postConstruct()
	{
		userAuthTokenFilterConfiguration.doNotAuthenticatePathsContaining(ENDPOINT);

		if (isEnabled())
		{
			logger.warn("""
					
					
					************************************************************************************************************************
					 Frontend testing REST endpoints are active and accessible without login!
					************************************************************************************************************************"""
			);
		}
	}

	private <T> T callInContext(Supplier<T> callable)
	{
		assertEnabled();

		final Properties ctx = Env.copyCtx(Env.getCtx());
		Env.setLoggedUserId(ctx, UserId.SYSTEM);
		Env.setClientId(ctx, ClientId.METASFRESH);
		Env.setOrgId(ctx, OrgId.MAIN);

		try (final IAutoCloseable ignored = Env.switchContext(ctx))
		{
			return callable.get();
		}
	}

	@PostMapping
	public JsonCreateMasterdataResponse createMasterdata(@RequestBody final JsonCreateMasterdataRequest request)
	{
		return callInContext(
				() -> CreateMasterdataCommand.builder()
						.services(services)
						.request(request)
						.build().execute()
		);
	}

	@PostMapping("getFreePickingSlot")
	public JsonGetFreePickingSlotResponse getFreePickingSlot(@RequestBody JsonGetFreePickingSlotRequest request)
	{
		return callInContext(
				() -> {
					final BPartnerId bpartnerId = StringUtils.trimBlankToOptional(request.getBpartnerCode())
							.flatMap(partnerDAO::getBPartnerIdByValue)
							.orElse(null);

					final Set<PickingSlotIdAndCaption> pickingSlotIds = pickingSlotBL.getPickingSlotIdAndCaptions(PickingSlotQuery.builder()
							.availableForBPartnerId(bpartnerId)
							.build());
					if (pickingSlotIds.isEmpty())
					{
						throw new AdempiereException("No free picking slot found");
					}
					final PickingSlotIdAndCaption pickingSlotId = pickingSlotIds.iterator().next();

					final PickingSlotQRCode qrCode = PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlotId);
					return JsonGetFreePickingSlotResponse.builder()
							.qrCode(qrCode.toGlobalQRCodeJsonString())
							.build();
				});
	}

	@PostMapping("expect")
	public ResponseEntity<JsonExpectationsResponse> expect(@RequestBody @NonNull final JsonExpectations jsonExpectations) throws Exception
	{
		assertEnabled();

		return AssertExpectationsCommand.builder()
				.services(services.assertExpectationsCommandServices)
				.expectations(jsonExpectations)
				.build()
				.execute();
	}
}
