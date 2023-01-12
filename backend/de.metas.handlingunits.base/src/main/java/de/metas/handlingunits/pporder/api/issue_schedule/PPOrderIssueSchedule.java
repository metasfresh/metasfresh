package de.metas.handlingunits.pporder.api.issue_schedule;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
public class PPOrderIssueSchedule
{
	@NonNull PPOrderIssueScheduleId id;
	@NonNull PPOrderId ppOrderId;
	@NonNull PPOrderBOMLineId ppOrderBOMLineId;

	@With
	@NonNull SeqNo seqNo;

	@NonNull ProductId productId;
	@NonNull Quantity qtyToIssue;
	@NonNull HuId issueFromHUId;
	@NonNull LocatorId issueFromLocatorId;
	boolean isAlternativeIssue;

	@With
	@Nullable
	Issued issued;

	@Value
	@Builder
	public static class Issued
	{
		@NonNull Quantity qtyIssued;
		@Nullable
		QtyRejectedWithReason qtyRejected;

		public Issued add(@NonNull final Issued toAdd)
		{
			final QtyRejectedWithReason updatedQtyRejectedWithReason;
			if (toAdd.getQtyRejected() == null)
			{
				updatedQtyRejectedWithReason = qtyRejected;
			}
			else if (qtyRejected == null)
			{
				updatedQtyRejectedWithReason = toAdd.getQtyRejected();
			}
			else
			{
				updatedQtyRejectedWithReason = qtyRejected.add(toAdd.getQtyRejected());
			}

			return Issued.builder()
					.qtyIssued(qtyIssued.add(toAdd.getQtyIssued()))
					.qtyRejected(updatedQtyRejectedWithReason)
					.build();
		}
	}

	public boolean isNoProgressRegistered()
	{
		return issued == null;
	}

	public boolean isIssuedCompletely()
	{
		return issued != null && issued.getQtyIssued().compareTo(qtyToIssue) >= 0;
	}

	@NonNull
	public PPOrderIssueSchedule addIssuedQty(@NonNull final Issued toAdd)
	{
		final Issued issuedUpdated = Optional.ofNullable(issued)
				.map(nonNullIssued -> nonNullIssued.add(toAdd))
				.orElse(toAdd);

		return withIssued(issuedUpdated);
	}
}
