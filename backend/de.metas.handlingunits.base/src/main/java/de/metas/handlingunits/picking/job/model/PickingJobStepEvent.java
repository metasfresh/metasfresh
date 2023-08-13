package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableMap;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;

@Value
@Builder
public class PickingJobStepEvent
{
	@Builder.Default
	@NonNull Instant timestamp = SystemTime.asInstant();

	@NonNull PickingJobStepId pickingStepId;
	@NonNull PickingJobStepPickFromKey pickFromKey;

	@NonNull PickingJobStepEventType eventType;
	@NonNull HUQRCode huQRCode;
	@Nullable BigDecimal qtyPicked;
	@Nullable BigDecimal qtyRejected;
	@Nullable QtyRejectedReasonCode qtyRejectedReasonCode;

	public static ImmutableMap<PickingJobStepIdAndPickFromKey, PickingJobStepEvent> aggregateByStepIdAndPickFromKey(@NonNull final Collection<PickingJobStepEvent> events)
	{
		return events
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						event -> PickingJobStepIdAndPickFromKey.of(event.getPickingStepId(), event.getPickFromKey()),
						event -> event,
						PickingJobStepEvent::latest));
	}

	private static PickingJobStepEvent latest(@NonNull final PickingJobStepEvent e1, @NonNull final PickingJobStepEvent e2)
	{
		return e1.getTimestamp().isAfter(e2.getTimestamp()) ? e1 : e2;
	}

}
