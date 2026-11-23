package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.picking.slot.PickingSlotQueues;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.springframework.stereotype.Component;

/**
 * One rule for "Scan (GRAI)" being offered, on the job screen and on the slot screen:
 * at least one of the crates queued in the given picking slots is an active top-level HU that carries a GRAI.
 */
@Component
public class HUConsolidationGRAIScanPolicy
{
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	public boolean isGRAIScanEnabled(@NonNull final PickingSlotQueues queues)
	{
		return HUConsolidationCrateQuery.ofHuIds(handlingUnitsDAO, queues.getHuIds())
				.map(query -> query.addOnlyWithAttributeNotNull(AttributeConstants.ATTR_GRAI).anyMatch())
				.orElse(false);
	}
}
