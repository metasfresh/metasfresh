package de.metas.handlingunits.picking.job.service.commands.unpick;

import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.Comparator;

@Value
class CandidateHU
{
	static final Comparator<CandidateHU> ORDERBY_Created_DESC =
			Comparator.comparing(CandidateHU::getCreatedAt).reversed();

	@NonNull PickingJobStepId stepId;
	@NonNull PickingJobStepPickFromKey pickFromKey;
	@NonNull PickingJobStepPickedToHU pickedToHU;

	Quantity getQtyPicked() {return pickedToHU.getQtyPicked();}

	Instant getCreatedAt() {return pickedToHU.getCreatedAt();}
}
