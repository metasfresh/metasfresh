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
		final BPartnerId pickingSlotBPartnerId = extractBPartnerId(pickingSlot);
		return pickingSlotBPartnerId == null
				&& pickingSlot.getM_Picking_Job_ID() <= 0;
	}

	@Nullable
	private static BPartnerId extractBPartnerId(final @NonNull I_M_PickingSlot pickingSlot)
	{
		return BPartnerId.ofRepoIdOrNull(pickingSlot.getC_BPartner_ID());
	}

	@Override
	public boolean isAvailableForBPartnerId(@NonNull final I_M_PickingSlot pickingSlot, @Nullable final BPartnerId bpartnerId)
	{
		//
		// General use Picking Slot, accept it right away
		if (isAvailableForAnyBPartner(pickingSlot))
		{
			return true;
		}

		//
		// Check BPartner
		final BPartnerId pickingSlotBPartnerId = extractBPartnerId(pickingSlot);
		// Any BPartner Picking Slot
		if (pickingSlotBPartnerId == null)
		{
			// accept any partner
		}
		// Picking slot specific for BP
		else
		{
			if (bpartnerId == null)
			{
				// no particular partner was requested, (i.e. M_HU_PI_Item_Product does not have a BP set), accept it
			}
			else if (BPartnerId.equals(bpartnerId, pickingSlotBPartnerId))
			{
				// same BP, accept it
			}
			else
			{
				// not same BP, don't accept it
				return false;
			}
		}

		// If we reach this point, we passed all validation rules
		return true;
	}

	@Override
	public boolean isAvailableForBPartnerAndLocation(
			@NonNull final I_M_PickingSlot pickingSlot,
			final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId)
	{
		//
		// General use Picking Slot, accept it right away
		if (isAvailableForAnyBPartner(pickingSlot))
		{
			return true;
		}

		//
		// Check if is available for BPartner
		if (!isAvailableForBPartnerId(pickingSlot, bpartnerId))
		{
			return false;
		}

		//
		// Check BPartner Location
		final BPartnerLocationId pickingSlotBPartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(pickingSlot.getC_BPartner_ID(), pickingSlot.getC_BPartner_Location_ID());

		// Any BP Location Picking Slot
		if (pickingSlotBPartnerLocationId == null)
		{
			// accept any location
		}
		// Picking slot specific for BP Location
		else
		{
			if (bpartnerLocationId == null)
			{
				// no particular location was requested, accept it
			}
			else if (BPartnerLocationId.equals(bpartnerLocationId, pickingSlotBPartnerLocationId))
			{
				// same BP Location, accept it
			}
			else
			{
				// not same BP Location, don't accept it
				return false;
			}
		}

		// If we reach this point, we passed all validation rules
		return true;
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

}
