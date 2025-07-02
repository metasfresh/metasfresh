package de.metas.picking.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.Set;

public class PickingSlotBL implements IPickingSlotBL
{
	protected final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);

	@Override
	public boolean isAvailableForAnyBPartner(@NonNull final I_M_PickingSlot pickingSlot)
	{
		return PickingSlotUtils.isAvailableForAnyBPartner(pickingSlot);
	}

	@Override
	public boolean isAvailableForBPartnerId(@NonNull final I_M_PickingSlot pickingSlot, @Nullable final BPartnerId bpartnerId)
	{
		return PickingSlotUtils.isAvailableForBPartnerId(pickingSlot, bpartnerId);
	}

	@Override
	public boolean isAvailableForBPartnerAndLocation(
			@NonNull final I_M_PickingSlot pickingSlot,
			final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId)
	{
		return PickingSlotUtils.isAvailableForBPartnerAndLocation(pickingSlot, bpartnerId, bpartnerLocationId);
	}

	@Override
	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotDAO.getPickingSlotIdAndCaption(pickingSlotId);
	}

	@Override
	public Set<PickingSlotIdAndCaption> getPickingSlotIdAndCaptions(@NonNull final PickingSlotQuery query)
	{
		return pickingSlotDAO.retrievePickingSlotIdAndCaptions(query);
	}

	@Override
	public QRCodePDFResource createQRCodesPDF(@NonNull final Set<PickingSlotIdAndCaption> pickingSlotIdAndCaptions)
	{
		Check.assumeNotEmpty(pickingSlotIdAndCaptions, "pickingSlotIdAndCaptions is not empty");

		final ImmutableList<PrintableQRCode> qrCodes = pickingSlotIdAndCaptions.stream()
				.map(PickingSlotQRCode::ofPickingSlotIdAndCaption)
				.map(PickingSlotQRCode::toPrintableQRCode)
				.collect(ImmutableList.toImmutableList());

		final GlobalQRCodeService globalQRCodeService = SpringContextHolder.instance.getBean(GlobalQRCodeService.class);
		return globalQRCodeService.createPDF(qrCodes);
	}

	@Override
	public boolean isAvailableForAnyBPartner(@NonNull final PickingSlotId pickingSlotId)
	{
		return isAvailableForAnyBPartner(pickingSlotDAO.getById(pickingSlotId));
	}

	@NonNull
	public I_M_PickingSlot getById(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotDAO.getById(pickingSlotId);
	}

	@Override
	public boolean isPickingRackSystem(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotDAO.isPickingRackSystem(pickingSlotId);
	}
}
