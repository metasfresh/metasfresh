package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableMap;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.Util;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;

@Value
@Builder
public class PickingJobStepEvent
{
	@Builder.Default
	@NonNull Instant timestamp = SystemTime.asInstant();

	@NonNull PickingJobLineId pickingLineId;
	@Nullable PickingJobStepId pickingStepId;
	@Nullable PickingJobStepPickFromKey pickFromKey;

	@NonNull PickingJobStepEventType eventType;
	@NonNull IHUQRCode huQRCode;
	@Nullable BigDecimal qtyPicked;
	@Nullable BigDecimal qtyRejected;
	@Nullable QtyRejectedReasonCode qtyRejectedReasonCode;
	@Nullable BigDecimal catchWeight;
	boolean isPickWholeTU;
	@Builder.Default boolean checkIfAlreadyPacked = true;

	boolean isSetBestBeforeDate;
	@Nullable LocalDate bestBeforeDate;
	boolean isSetLotNo;
	@Nullable String lotNo;

	@Nullable HUQRCode unpickToTargetQRCode;

	public static Collection<PickingJobStepEvent> removeDuplicates(@NonNull final Collection<PickingJobStepEvent> events)
	{
		return events
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						event -> Util.ArrayKey.of(event.getPickingLineId(), event.getPickingStepId(), event.getPickFromKey()),
						event -> event,
						PickingJobStepEvent::latest))
				.values();
	}

	private static PickingJobStepEvent latest(@NonNull final PickingJobStepEvent e1, @NonNull final PickingJobStepEvent e2)
	{
		return e1.getTimestamp().isAfter(e2.getTimestamp()) ? e1 : e2;
	}

}
