package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotQueues;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Decides whether "Scan (GRAI)" is offered for the given picking slot queues:
 * true if at least one of the crates queued in the given picking slots is an active top-level HU that carries a GRAI.
 */
@Component
public class HUConsolidationGRAIScanPolicy
{
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	public boolean isGRAIScanEnabled(@NonNull final PickingSlotQueues queues)
	{
		return isGRAIScanEnabled(queues.getHuIds());
	}

	public boolean isGRAIScanEnabled(@NonNull final PickingSlotQueue queue)
	{
		return isGRAIScanEnabled(queue.getHuIds());
	}

	private boolean isGRAIScanEnabled(@NonNull final Collection<HuId> crateHuIds)
	{
		return HUConsolidationCrateQuery.ofHuIds(handlingUnitsDAO, crateHuIds)
				.map(query -> query.addOnlyWithAttributeNotNull(AttributeConstants.ATTR_GRAI).anyMatch())
				.orElse(false);
	}
}
