package de.metas.distribution.mobileui.external_services.warehouse;

import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocatorValueRoundRobinResolver implements NextPickFromLocatorResolver
{
	static final AdMessageKey MSG_NO_ALTERNATIVE = AdMessageKey.of("MobileUI_DDOrder_SwitchPickFromLocator_NoAlternative");

	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@Override
	public @NonNull LocatorId resolveNext(@NonNull final WarehouseId warehouseId, @NonNull final LocatorId currentLocatorId)
	{
		final List<I_M_Locator> allLocators = warehouseDAO.getLocators(warehouseId);

		// Filter active only, sort by Value ascending (nulls last for safety)
		final List<I_M_Locator> candidates = allLocators.stream()
				.filter(I_M_Locator::isActive)
				.sorted(Comparator.comparing(I_M_Locator::getValue, Comparator.nullsLast(String::compareTo)))
				.collect(Collectors.toList());

		if (candidates.isEmpty())
		{
			throw new AdempiereException(MSG_NO_ALTERNATIVE);
		}

		// Find index of currentLocatorId in the sorted candidates
		int currentIdx = -1;
		for (int i = 0; i < candidates.size(); i++)
		{
			final I_M_Locator candidate = candidates.get(i);
			final LocatorId candidateId = LocatorId.ofRepoId(candidate.getM_Warehouse_ID(), candidate.getM_Locator_ID());
			if (candidateId.equals(currentLocatorId))
			{
				currentIdx = i;
				break;
			}
		}

		// If currentLocatorId is NOT in the set → return the first candidate
		if (currentIdx < 0)
		{
			return locatorId(candidates.get(0));
		}

		// If currentLocatorId IS the only candidate → throw
		if (candidates.size() == 1)
		{
			throw new AdempiereException(MSG_NO_ALTERNATIVE);
		}

		// Otherwise return candidates[(idx+1) % size]
		return locatorId(candidates.get((currentIdx + 1) % candidates.size()));
	}

	private static LocatorId locatorId(final I_M_Locator locator)
	{
		return LocatorId.ofRepoId(locator.getM_Warehouse_ID(), locator.getM_Locator_ID());
	}
}
