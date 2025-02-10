package de.metas.frontend_testing;

import com.google.common.collect.ImmutableMap;
import de.metas.Profiles;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyRepository;
import de.metas.frontend_testing.masterdata.CreateMasterdataCommand;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataRequest;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHURequest;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHUResponse;
import de.metas.frontend_testing.masterdata.picking_slot.JsonGetFreePickingSlotRequest;
import de.metas.frontend_testing.masterdata.picking_slot.JsonGetFreePickingSlotResponse;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateRequest;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateResponse;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.logging.LogManager;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Set;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/frontendTesting")
@RestController
@ConditionalOnProperty("frontend_testing")
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class FrontendTestingRestController
{
	@NonNull private final Logger logger = LogManager.getLogger(FrontendTestingRestController.class);
	@NonNull private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final CurrencyRepository currencyRepository;

	@PostConstruct
	public void postConstruct()
	{
		logger.info("\n"
				+ "\n************************************************************************************************************************"
				+ "\n Frontend testing REST endpoints are active"
				+ "\n************************************************************************************************************************"
		);
	}

	@PostMapping
	public JsonCreateMasterdataResponse createMasterdata(@RequestBody final JsonCreateMasterdataRequest request)
	{
		return CreateMasterdataCommand.builder()
				.inventoryService(inventoryService)
				.huQRCodesService(huQRCodesService)
				.currencyRepository(currencyRepository)
				.request(request)
				.build()
				.execute();
	}

	@PostMapping("salesOrder")
	public JsonSalesOrderCreateResponse createOrder(@RequestBody final JsonSalesOrderCreateRequest request)
	{
		final JsonCreateMasterdataResponse response = createMasterdata(JsonCreateMasterdataRequest.builder()
				.salesOrders(ImmutableMap.of("salesOrder", request))
				.build());
		return response.getSalesOrders().get("salesOrder");
	}

	@PostMapping("hu")
	public JsonCreateHUResponse createHU(@RequestBody final JsonCreateHURequest request)
	{
		final JsonCreateMasterdataResponse response = createMasterdata(JsonCreateMasterdataRequest.builder()
				.handlingUnits(ImmutableMap.of("hu", request))
				.build());
		return response.getHandlingUnits().get("hu");
	}

	@PostMapping("getFreePickingSlot")
	public JsonGetFreePickingSlotResponse getFreePickingSlot(@RequestBody JsonGetFreePickingSlotRequest request)
	{
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
	}
}
