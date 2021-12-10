package de.metas.distribution.workflows_api;

import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import org.adempiere.exceptions.AdempiereException;
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
		for (final DistributionJobLine line : job.getLines())
		{
			for (final DistributionJobStep step : line.getSteps())
			{
				huReservationService.makeReservation(ReserveHUsRequest.builder()
								.qtyToReserve(step.getQtyToMoveTarget())
								.documentRef(HUReservationDocRef.ofDDOrderLineId(line.getDdOrderLineId()))
								.productId(line.getProduct().getProductId())
								//.customerId(null)
								.huId(step.getPickFromHUId())
								.build())
						.orElseThrow(() -> new AdempiereException("Failed reserving HUs for " + step));
			}
		}
	}

}
