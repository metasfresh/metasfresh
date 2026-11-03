package de.metas.distribution.mobileui.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobLine;
import de.metas.distribution.mobileui.external_services.hu.DistributionHUService;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistributionJobHUReservationService
{
	@NonNull private final DistributionHUService huService;

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

		huService.deleteReservationsByDocumentRefs(huReservationDocRefs);
	}
}
