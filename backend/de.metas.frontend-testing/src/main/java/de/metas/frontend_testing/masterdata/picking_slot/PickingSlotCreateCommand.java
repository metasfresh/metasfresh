package de.metas.frontend_testing.masterdata.picking_slot;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.PickingSlotCreateRequest;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
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
		final String code = request.getCode() != null ? request.getCode() : identifier.toUniqueString();
		assertNoPickingSlotWithCode(code);
		final LocatorId locatorId = request.getLocator() != null
				? context.getId(request.getLocator(), LocatorId.class)
				: context.getIdOfType(LocatorId.class);
		final BPartnerLocationId bpartnerLocationId = request.getBpartnerLocation() != null
				? context.getBPartnerLocationId(request.getBpartnerLocation())
				: null;

		final PickingSlotIdAndCaption pickingSlot = pickingSlotDAO.createPickingSlot(PickingSlotCreateRequest.builder()
				.pickingSlotCode(code)
				.locatorId(locatorId)
				.isDynamic(true)
				.bpartnerAndLocationId(bpartnerLocationId)
				.build());
		context.putIdentifier(identifier, pickingSlot.getPickingSlotId());

		return JsonPickingSlotCreateResponse.builder()
				.id(pickingSlot.getPickingSlotId())
				.code(code)
				.qrCode(PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlot).toGlobalQRCodeJsonString())
				.build();
	}

	/** A caller-supplied code must not collide with another slot (shared DB across runs): fail clearly instead of creating a duplicate. */
	private void assertNoPickingSlotWithCode(final String code)
	{
		final boolean exists = pickingSlotDAO.getPickingSlotIdAndCaptionByCode(code).isPresent();
		if (exists)
		{
			throw new AdempiereException("A picking slot with code '" + code + "' already exists; pick another code");
		}
	}
}
