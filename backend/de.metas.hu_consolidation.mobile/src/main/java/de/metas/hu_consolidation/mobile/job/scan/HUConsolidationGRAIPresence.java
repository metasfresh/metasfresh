package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * One rule for "Scan (GRAI)" being offered, on the job screen and on the slot screen:
 * at least one of the given crates is an active top-level HU that carries a GRAI.
 */
@Component
public class HUConsolidationGRAIPresence
{
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	public boolean isAnyGRAIPresent(@NonNull final Collection<HuId> huIds)
	{
		return HUConsolidationCrateQuery.ofHuIds(handlingUnitsDAO, huIds)
				.map(query -> query.addOnlyWithAttributeNotNull(AttributeConstants.ATTR_GRAI).anyMatch())
				.orElse(false);
	}
}
