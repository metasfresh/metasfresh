package de.metas.frontend_testing;

import de.metas.Profiles;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyRepository;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.frontend_testing.masterdata.CreateMasterdataCommand;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataRequest;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import de.metas.frontend_testing.masterdata.picking_slot.JsonGetFreePickingSlotRequest;
import de.metas.frontend_testing.masterdata.picking_slot.JsonGetFreePickingSlotResponse;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.logging.LogManager;
import de.metas.mobile.MobileConfigService;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;

@RequestMapping(FrontendTestingRestController.ENDPOINT)
@RestController
@ConditionalOnProperty("frontend.testing")
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class FrontendTestingRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/frontendTesting";

	@NonNull private final Logger logger = LogManager.getLogger(FrontendTestingRestController.class);
	@NonNull private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);
	@NonNull private final UserAuthTokenFilterConfiguration userAuthTokenFilterConfiguration;
	@NonNull private final MobileConfigService mobileConfigService;
	@NonNull private final MobileUIPickingUserProfileRepository mobilePickingConfigRepository;
	@NonNull private final MobileUIDistributionConfigRepository mobileDistributionConfigRepository;
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final DDOrderService ddOrderService;

	@PostConstruct
	public void postConstruct()
	{
		userAuthTokenFilterConfiguration.doNotAuthenticatePathsContaining(ENDPOINT);

		logger.warn("\n"
				+ "\n************************************************************************************************************************"
				+ "\n Frontend testing REST endpoints are active and accessible without login!"
				+ "\n************************************************************************************************************************"
		);
	}

	private <T> T callInContext(Supplier<T> callable)
	{
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
						.mobileConfigService(mobileConfigService)
						.mobilePickingConfigRepository(mobilePickingConfigRepository)
						.mobileDistributionConfigRepository(mobileDistributionConfigRepository)
						.inventoryService(inventoryService)
						.huQRCodesService(huQRCodesService)
						.currencyRepository(currencyRepository)
						.ddOrderService(ddOrderService)
						//
						.request(request)
						//
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
}
