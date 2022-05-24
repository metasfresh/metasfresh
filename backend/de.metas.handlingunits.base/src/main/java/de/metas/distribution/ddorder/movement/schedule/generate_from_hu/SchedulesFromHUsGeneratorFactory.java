package de.metas.distribution.ddorder.movement.schedule.generate_from_hu;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import lombok.NonNull;
import de.metas.distribution.ddorder.DDOrderLineId;
import org.eevolution.model.I_DD_OrderLine;

public class SchedulesFromHUsGeneratorFactory
{
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DDOrderLineToAllocateFactory ddOrderLineToAllocateFactory;

	public SchedulesFromHUsGeneratorFactory(
			final @NonNull DDOrderMoveScheduleService ddOrderMoveScheduleService,
			final @NonNull DDOrderService ddOrderService)
	{
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.ddOrderService = ddOrderService;
		this.ddOrderLineToAllocateFactory = new DDOrderLineToAllocateFactory(ddOrderService);
	}

	public SchedulesFromHUsGenerator toDDOrderLineId(@NonNull final DDOrderLineId ddOrderLineId)
	{
		final I_DD_OrderLine ddOrderLine = ddOrderService.getLineById(ddOrderLineId);
		return toDDOrderLine(ddOrderLine);
	}

	public SchedulesFromHUsGenerator toDDOrderLine(@NonNull final I_DD_OrderLine ddOrderLine)
	{
		final ImmutableList<DDOrderLineToAllocate> ddOrderLines = ddOrderLineToAllocateFactory.ofDDOrderLine(ddOrderLine);
		return new SchedulesFromHUsGenerator(ddOrderService, ddOrderMoveScheduleService, ddOrderLines);
	}
}
