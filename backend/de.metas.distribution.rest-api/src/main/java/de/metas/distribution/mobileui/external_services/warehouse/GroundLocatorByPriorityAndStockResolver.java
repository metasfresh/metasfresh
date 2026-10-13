package de.metas.distribution.mobileui.external_services.warehouse;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.storage.LocatorIdAndQty;
import de.metas.handlingunits.storage.ProductAvailableStockPerLocator;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.Warehouse;
import org.adempiere.warehouse.WarehouseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroundLocatorByPriorityAndStockResolver implements NextPickFromLocatorResolver
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final WarehouseRepository warehouseRepository;

	@Override
	@NonNull
	public LocatorId resolveNext(@NonNull final LocatorId currentLocatorId, @NonNull ProductId productId)
	{
		final Warehouse warehouse = warehouseRepository.getById(currentLocatorId.getWarehouseId());
		final List<LocatorId> locatorIds = warehouse.getGroundFloorLocatorIdsOrderedByPriority();
		if (locatorIds.isEmpty())
		{
			throw new AdempiereException(MSG_NO_ALTERNATIVE);
		}

		final List<LocatorId> candidatesInRoundRobinOrder = orderedRoundRobinAfter(locatorIds, currentLocatorId);

		return ProductAvailableStockPerLocator.newInstance(handlingUnitsBL)
				.streamLocatorQtyOnHandOrdered(productId, 10, candidatesInRoundRobinOrder)
				.map(LocatorIdAndQty::getLocatorId)
				.findFirst()
				.orElseThrow(() -> new AdempiereException(MSG_NO_ALTERNATIVE));
	}

	/**
	 * Returns the {@code locatorIds} reordered round-robin starting at {@code currentLocatorId + 1},
	 * excluding {@code currentLocatorId} itself. Empty if {@code currentLocatorId} is not in the list.
	 */
	private static List<LocatorId> orderedRoundRobinAfter(
			@NonNull final List<LocatorId> locatorIds,
			@NonNull final LocatorId currentLocatorId)
	{
		final int startIdx = locatorIds.indexOf(currentLocatorId);
		if (startIdx < 0)
		{
			return ImmutableList.of();
		}

		final int size = locatorIds.size();
		final ImmutableList.Builder<LocatorId> result = ImmutableList.builderWithExpectedSize(size - 1);
		for (int step = 1; step < size; step++)
		{
			result.add(locatorIds.get((startIdx + step) % size));
		}
		return result.build();
	}
}
