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
		// Without this guard HUQueryBuilder.addOnlyHUIds(empty) drops the filter and the query would run over all active HUs
		if (huIds.isEmpty())
		{
			return false;
		}

		return handlingUnitsDAO
				.createHUQueryBuilder()
				.setOnlyActiveHUs(true)
				.setOnlyTopLevelHUs()
				.addOnlyHUIds(huIds)
				.addOnlyWithAttributeNotNull(AttributeConstants.ATTR_GRAI)
				.firstId()
				.isPresent();
	}
}
