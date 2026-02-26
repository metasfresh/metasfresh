package de.metas.handlingunits.picking.job.shipment;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import lombok.NonNull;
import lombok.Value;

@Value
public class PickingShipmentCandidateKey
{
	@NonNull BPartnerId customerId;

	public static PickingShipmentCandidateKey of(final PickingJobLine line)
	{
		return new PickingShipmentCandidateKey(line.getCustomerId());
	}
}
