package de.metas.picking.workflow;

import de.metas.common.util.time.SystemTime;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.workflow.model.PickingJobStepId;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class PickingJobStepEvent
{
	@Builder.Default
	@NonNull Instant timestamp = SystemTime.asInstant();

	@NonNull WFProcessId wfProcessId;
	@NonNull WFActivityId wfActivityId;
	@NonNull PickingJobStepId pickingStepId;

	@NonNull PickingJobStepEventType eventType;
	@Nullable BigDecimal qtyPicked;

	public static PickingJobStepEvent ofJson(@NonNull final JsonPickingStepEvent json)
	{
		return builder()
				.timestamp(SystemTime.asInstant())
				.wfProcessId(WFProcessId.ofString(json.getWfProcessId()))
				.wfActivityId(WFActivityId.ofString(json.getWfActivityId()))
				.pickingStepId(PickingJobStepId.ofString(json.getPickingStepId()))
				.eventType(PickingJobStepEventType.ofJson(json.getType()))
				.qtyPicked(json.getQtyPicked())
				.build();
	}

	public static PickingJobStepEvent latest(
			@NonNull final PickingJobStepEvent e1,
			@NonNull final PickingJobStepEvent e2)
	{
		return e1.getTimestamp().isAfter(e2.getTimestamp()) ? e1 : e2;
	}

}
