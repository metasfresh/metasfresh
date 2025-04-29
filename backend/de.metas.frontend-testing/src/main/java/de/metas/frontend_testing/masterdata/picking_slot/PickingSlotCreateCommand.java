package de.metas.frontend_testing.masterdata.picking_slot;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.PickingSlotCreateRequest;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

@Builder
public class PickingSlotCreateCommand
{
	@NonNull private final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonPickingSlotCreateRequest request;
	@NonNull private final Identifier identifier;

	public JsonPickingSlotCreateResponse execute()
	{
		final String code = identifier.toUniqueString();
		final LocatorId locatorId = request.getLocator() != null
				? context.getId(request.getLocator(), LocatorId.class)
				: context.getIdOfType(LocatorId.class);

		final PickingSlotIdAndCaption pickingSlot = pickingSlotDAO.createPickingSlot(PickingSlotCreateRequest.builder()
				.pickingSlotCode(code)
				.locatorId(locatorId)
				.isDynamic(true)
				.build());

		return JsonPickingSlotCreateResponse.builder()
				.code(code)
				.qrCode(PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlot).toGlobalQRCodeJsonString())
				.build();
	}
}
