package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

@Service
public class PickingJobHUReservationService
{
	private final HUReservationService huReservationService;

	public PickingJobHUReservationService(final HUReservationService huReservationService) {this.huReservationService = huReservationService;}

	public void reservePickFromHUs(final PickingJob pickingJob)
	{
		final BPartnerId customerId = pickingJob.getDeliveryBPLocationId().getBpartnerId();

		for (final PickingJobLine line : pickingJob.getLines())
		{
			for (final PickingJobStep step : line.getSteps())
			{
				reservePickFromHU(step, customerId);
			}
		}
	}

	private void reservePickFromHU(@NonNull final PickingJobStep step, @NonNull final BPartnerId customerId)
	{
		huReservationService.makeReservation(
						ReserveHUsRequest.builder()
								.customerId(customerId)
								.documentRef(HUReservationDocRef.ofPickingJobStepId(step.getId()))
								.productId(step.getProductId())
								.qtyToReserve(step.getQtyToPick())
								.huId(step.getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHUId())
								.build())
				.orElseThrow(() -> new AdempiereException("Cannot reserve HU for " + step)); // shall not happen
	}

	public void releaseAllReservations(@NonNull final PickingJob pickingJob)
	{
		final ImmutableSet<HUReservationDocRef> reservationDocRefs = pickingJob
				.getLines().stream()
				.flatMap(line -> line.getSteps().stream())
				.map(step -> HUReservationDocRef.ofPickingJobStepId(step.getId()))
				.collect(ImmutableSet.toImmutableSet());

		huReservationService.deleteReservationsByDocumentRefs(reservationDocRefs);
	}
}
