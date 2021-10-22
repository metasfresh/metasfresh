package de.metas.handlingunits.ddorder.movement;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.ddorder.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.picking.DDOrderPickFromService;
import lombok.NonNull;
import org.eevolution.model.I_DD_OrderLine;

public class DDOrderLinesAllocatorFactory
{
	@NonNull private final DDOrderPickFromService ddOrderPickFromService;
	@NonNull private final IHUDDOrderBL ddOrderBL;

	public DDOrderLinesAllocatorFactory(
			final @NonNull DDOrderPickFromService ddOrderPickFromService,
			final @NonNull IHUDDOrderBL ddOrderBL)
	{
		this.ddOrderPickFromService = ddOrderPickFromService;
		this.ddOrderBL = ddOrderBL;
	}

	public DDOrderLinesAllocator ofDDOrderLine(@NonNull final I_DD_OrderLine ddOrderLine)
	{
		final ImmutableList<DDOrderLineToAllocate> ddOrderLines = new DDOrderLineToAllocateFactory(ddOrderBL).ofDDOrderLine(ddOrderLine);
		return new DDOrderLinesAllocator(ddOrderPickFromService, ddOrderLines);
	}
}
