package de.metas.distribution.mobileui.external_services.warehouse;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocatorValueRoundRobinResolver implements NextPickFromLocatorResolver
{
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@Override
	public @NonNull LocatorId resolveNext(@NonNull final LocatorId currentLocatorId)
	{
		final List<I_M_Locator> candidates = warehouseDAO.getLocators(currentLocatorId.getWarehouseId()).stream()
				.filter(I_M_Locator::isActive)
				.sorted(Comparator.comparing(I_M_Locator::getValue, Comparator.nullsLast(String::compareTo)))
				.collect(Collectors.toList());

		if (candidates.isEmpty())
		{
			throw new AdempiereException(MSG_NO_ALTERNATIVE);
		}

		int currentIdx = -1;
		for (int i = 0; i < candidates.size(); i++)
		{
			if (LocatorId.equals(locatorId(candidates.get(i)), currentLocatorId))
			{
				currentIdx = i;
				break;
			}
		}

		if (currentIdx < 0)
		{
			return locatorId(candidates.get(0));
		}
		if (candidates.size() == 1)
		{
			throw new AdempiereException(MSG_NO_ALTERNATIVE);
		}
		return locatorId(candidates.get((currentIdx + 1) % candidates.size()));
	}

	private static LocatorId locatorId(@NonNull final I_M_Locator locator)
	{
		return LocatorId.ofRepoId(locator.getM_Warehouse_ID(), locator.getM_Locator_ID());
	}
}
