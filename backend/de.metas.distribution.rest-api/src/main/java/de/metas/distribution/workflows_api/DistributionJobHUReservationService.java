package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import org.springframework.stereotype.Service;

@Service
public class DistributionJobHUReservationService
{
	private final HUReservationService huReservationService;

	public DistributionJobHUReservationService(final HUReservationService huReservationService)
	{
		this.huReservationService = huReservationService;
	}

	public void reservePickFromHUs(final DistributionJob job)
	{
		// FIXME: don't reserve the VHUs for now because that's breaking the QR code logic
		// i.e. QR codes get lost

		// for (final DistributionJobLine line : job.getLines())
		// {
		// 	for (final DistributionJobStep step : line.getSteps())
		// 	{
		// 		huReservationService.makeReservation(ReserveHUsRequest.builder()
		// 						.qtyToReserve(step.getQtyToMoveTarget())
		// 						.documentRef(HUReservationDocRef.ofDDOrderLineId(line.getDdOrderLineId()))
		// 						.productId(line.getProduct().getProductId())
		// 						.customerId(job.getCustomerId())
		// 						.huId(step.getPickFromHU().getId())
		// 						.build())
		// 				.orElseThrow(() -> new AdempiereException("Failed reserving HUs for " + step));
		// 	}
		// }
	}

	public void releaseAllReservations(final DistributionJob job)
	{
		final ImmutableSet<HUReservationDocRef> huReservationDocRefs = job.getLines().stream()
				.map(DistributionJobLine::getDdOrderLineId)
				.map(HUReservationDocRef::ofDDOrderLineId)
				.collect(ImmutableSet.toImmutableSet());

		huReservationService.deleteReservationsByDocumentRefs(huReservationDocRefs);
	}
}
