/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.handlingunits.picking.slot;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.requests.ReleasePickingSlotRequest;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.SpringContextHolder.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickingSlotService
{
	private final static AdMessageKey QUEUED_HUS_ON_SLOT_ERR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.QUEUED_HUS_ON_SLOT_ERR_MSG");
	private final static AdMessageKey SLOT_CANNOT_BE_RELEASED = AdMessageKey.of("de.metas.handlingunits.picking.process.SLOT_CANNOT_BE_RELEASED");

	@NonNull private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	@NonNull private final Lazy<PickingSlotListenersDispatcher> listenersHolder = SpringContextHolder.lazyBean(PickingSlotListenersDispatcher.class);
	@NonNull private final PickingSlotRepository pickingSlotRepository;
	@NonNull private final PickingSlotQueueRepository pickingSlotQueueRepository;

	public static PickingSlotService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new PickingSlotService(
				new PickingSlotRepository(),
				new PickingSlotQueueRepository()
		);
	}

	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotRepository.getPickingSlotIdAndCaption(pickingSlotId);
	}

	public Set<PickingSlotIdAndCaption> getPickingSlotIdAndCaptions(@NonNull final Set<PickingSlotId> pickingSlotIds)
	{
		return pickingSlotRepository.getPickingSlotIdAndCaptions(pickingSlotIds);
	}

	public List<I_M_PickingSlot> list(@NonNull final PickingSlotQuery query)
	{
		return pickingSlotRepository.list(query);
	}

	public PickingSlotQRCode getPickingSlotQRCode(@NonNull final PickingSlotId pickingSlotId)
	{
		final PickingSlotIdAndCaption pickingSlotIdAndCaption = pickingSlotRepository.getPickingSlotIdAndCaption(pickingSlotId);
		return PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlotIdAndCaption);
	}

	public BooleanWithReason allocatePickingSlotIfPossible(@NonNull final PickingSlotAllocateRequest request)
	{
		return huPickingSlotBL.allocatePickingSlotIfPossible(request);
	}

	public void releasePickingSlotIfPossible(final PickingSlotId pickingSlotId)
	{
		huPickingSlotBL.releasePickingSlotIfPossible(pickingSlotId);
	}

	/**
	 * @return true, if the picking slot was released, false otherwise
	 */
	public boolean releasePickingSlot(@NonNull final ReleasePickingSlotRequest request)
	{
		listenersHolder.get().beforeReleasePickingSlot(request);

		final boolean clearedAllQueuedHUs = huPickingSlotBL.clearPickingSlotQueue(request.getPickingSlotId(), request.isRemoveQueuedHUsFromSlot());
		if (!clearedAllQueuedHUs)
		{
			throw new AdempiereException(QUEUED_HUS_ON_SLOT_ERR_MSG).markAsUserValidationError();
		}

		huPickingSlotBL.releasePickingSlotIfPossible(request.getPickingSlotId());

		return huPickingSlotBL.isAvailableForAnyBPartner(request.getPickingSlotId());
	}

	public void switchDynamicAllocation(final PickingSlotId pickingSlotId, final boolean isDynamic)
	{
		final I_M_PickingSlot pickingSlot = pickingSlotRepository.getById(pickingSlotId);
		if (isDynamic == pickingSlot.isDynamic())
		{
			//nothing to do
			return;
		}

		if (pickingSlot.isDynamic())
		{
			turnOffDynamicAllocation(pickingSlot);
		}
		else
		{
			turnOnDynamicAllocation(pickingSlot);
		}
	}

	private void turnOnDynamicAllocation(@NonNull final I_M_PickingSlot pickingSlot)
	{
		pickingSlot.setIsDynamic(true);
		pickingSlotRepository.save(pickingSlot);
	}

	private void turnOffDynamicAllocation(@NonNull final I_M_PickingSlot pickingSlot)
	{
		final PickingSlotId pickingSlotId = PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID());

		final boolean released = releasePickingSlot(ReleasePickingSlotRequest.ofPickingSlotId(pickingSlotId));
		if (!released)
		{
			throw new AdempiereException(SLOT_CANNOT_BE_RELEASED)
					.markAsUserValidationError();
		}

		pickingSlot.setIsDynamic(false);
		pickingSlotRepository.save(pickingSlot);
	}

	public PickingSlotQueues getNotEmptyQueues(@NonNull final PickingSlotQueueQuery query)
	{
		return pickingSlotQueueRepository.getNotEmptyQueues(query);
	}

	public PickingSlotQueue getPickingSlotQueue(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotQueueRepository.getPickingSlotQueue(pickingSlotId);
	}

	public void removeFromPickingSlotQueue(@NonNull final PickingSlotId pickingSlotId, @NonNull final Set<HuId> huIdsToRemove)
	{
		huPickingSlotBL.removeFromPickingSlotQueue(pickingSlotId, huIdsToRemove);
	}

	public List<PickingSlotReservation> getPickingSlotReservations(@NonNull Set<PickingSlotId> pickingSlotIds)
	{
		return pickingSlotRepository.getByIds(pickingSlotIds)
				.stream()
				.map(PickingSlotService::extractReservation)
				.collect(ImmutableList.toImmutableList());
	}

	private static PickingSlotReservation extractReservation(final I_M_PickingSlot pickingSlot)
	{
		return PickingSlotReservation.builder()
				.pickingSlotId(PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID()))
				.reservationValue(extractReservationValue(pickingSlot))
				.build();
	}

	private static PickingSlotReservationValue extractReservationValue(final I_M_PickingSlot pickingSlot)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(pickingSlot.getC_BPartner_ID());
		return PickingSlotReservationValue.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(bpartnerId, pickingSlot.getC_BPartner_Location_ID()))
				.build();
	}

	public void addToPickingSlotQueue(@NonNull final PickingSlotId pickingSlotId, @NonNull final Set<HuId> huIds)
	{
		huPickingSlotBL.addToPickingSlotQueue(pickingSlotId, huIds);
	}

	public PickingSlotQueuesSummary getNotEmptyQueuesSummary(@NonNull final PickingSlotQueueQuery query)
	{
		return pickingSlotQueueRepository.getNotEmptyQueuesSummary(query);
	}

}
