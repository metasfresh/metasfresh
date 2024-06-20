package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class PickingJobStepPickedTo
{
	@Nullable QtyRejectedWithReason qtyRejected;
	@NonNull ImmutableList<PickingJobStepPickedToHU> actualPickedHUs;

	@NonNull Quantity qtyPicked;
	@Nullable Quantity catchWeight;

	@Builder
	@Jacksonized
	private PickingJobStepPickedTo(
			@NonNull final ImmutableList<PickingJobStepPickedToHU> actualPickedHUs,
			@Nullable final QtyRejectedWithReason qtyRejected,
			@NonNull final ProductId productId)
	{
		// NOTE: empty actualPickedHUs is also OK for the case when the whole HU was rejected
		//Check.assumeNotEmpty(actualPickedHUs, "actualPickedHUs not empty");

		Maps.uniqueIndex(actualPickedHUs, PickingJobStepPickedToHU::getActualPickedHUId); // make sure there are no duplicates

		this.qtyRejected = qtyRejected;
		this.actualPickedHUs = actualPickedHUs;

		final Quantity qtyPicked = actualPickedHUs.stream()
				.map(PickingJobStepPickedToHU::getQtyPicked)
				.reduce(Quantity::add)
				.orElse(null);
		if (qtyPicked == null)
		{
			if (qtyRejected == null)
			{
				throw new AdempiereException("qtyPicked and qtyRejected cannot be both null");
			}
			this.qtyPicked = qtyRejected.toQuantity().toZero();
		}
		else
		{
			this.qtyPicked = qtyPicked;
		}

		this.catchWeight = actualPickedHUs.stream()
				.map(PickingJobStepPickedToHU::getActualPickedHUId)
				.map(pickedHuId -> CatchWeightHelper.extractQtysOrNull(productId, pickedHuId))
				.filter(Objects::nonNull)
				.map(StockQtyAndUOMQty::getUOMQtyOpt)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.reduce(Quantity::add)
				.orElse(null);

		Quantity.assertSameUOM(this.qtyPicked, this.qtyRejected != null ? this.qtyRejected.toQuantity() : null);
	}
}
