package de.metas.hu_consolidation.mobile.service;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class HUConsolidationShipmentService
{
	@NonNull private final IShipmentService shipmentService;

	public void createShipmentForLUs(@NonNull final HUConsolidationJob job, @NonNull final Set<HuId> luIds)
	{
		// TODO implement
	}

}
