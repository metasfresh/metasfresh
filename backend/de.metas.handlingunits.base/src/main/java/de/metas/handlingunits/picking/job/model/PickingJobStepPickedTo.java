package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class PickingJobStepPickedTo
{
	@Nullable QtyRejectedWithReason qtyRejected;
	@NonNull ImmutableList<PickingJobStepPickedToHU> actualPickedHUs;

	@NonNull Quantity qtyPicked;

	@Builder
	@Jacksonized
	private PickingJobStepPickedTo(
			@NonNull final ImmutableList<PickingJobStepPickedToHU> actualPickedHUs,
			@Nullable final QtyRejectedWithReason qtyRejected)
	{
		Check.assumeNotEmpty(actualPickedHUs, "actualPickedHUs not empty");
		Maps.uniqueIndex(actualPickedHUs, PickingJobStepPickedToHU::getActualPickedHUId); // make sure there are no duplicates

		this.qtyRejected = qtyRejected;
		this.actualPickedHUs = actualPickedHUs;

		qtyPicked = actualPickedHUs.stream()
				.map(PickingJobStepPickedToHU::getQtyPicked)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("We shall have at least one PickedTo HU"));

		Quantity.assertSameUOM(qtyPicked, qtyRejected != null ? qtyRejected.toQuantity() : null);
	}
}
