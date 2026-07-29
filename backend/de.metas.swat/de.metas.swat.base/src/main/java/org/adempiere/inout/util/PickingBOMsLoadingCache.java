package org.adempiere.inout.util;

import de.metas.util.Services;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.QtyCalculationsBOM;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class PickingBOMsLoadingCache
{
	private final IPPOrderBL ppOrdersBL = Services.get(IPPOrderBL.class);

	private final Map<PPOrderId, Optional<QtyCalculationsBOM>> cache = new HashMap<>();

	public Optional<QtyCalculationsBOM> getPickingBOM(@Nullable final PPOrderId pickingOrderId)
	{
		return pickingOrderId != null
				? cache.computeIfAbsent(pickingOrderId, ppOrdersBL::getOpenPickingOrderBOM)
				: Optional.empty();
	}

}
