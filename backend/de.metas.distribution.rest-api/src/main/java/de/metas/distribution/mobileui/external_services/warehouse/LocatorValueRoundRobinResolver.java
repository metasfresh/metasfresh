package de.metas.distribution.mobileui.external_services.warehouse;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.storage.ProductAvailableStockPerLocator;
import de.metas.handlingunits.storage.ProductQtyOnHandByLocator;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.Warehouse;
import org.adempiere.warehouse.WarehouseRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LocatorValueRoundRobinResolver implements NextPickFromLocatorResolver
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

		final ProductQtyOnHandByLocator qtyOnHandByLocator = getQtyOnHandByLocator(productId, locatorIds);

		final LocatorId nextLocatorId = CollectionUtils.getNextRoundRobin(locatorIds, currentLocatorId, qtyOnHandByLocator::hasStock);
		if (nextLocatorId == null)
		{
			throw new AdempiereException(MSG_NO_ALTERNATIVE);
		}

		return nextLocatorId;
	}

	private ProductQtyOnHandByLocator getQtyOnHandByLocator(@NonNull final ProductId productId, @NonNull final Collection<LocatorId> sourceLocatorIds)
	{
		final ProductAvailableStockPerLocator productAvailableStockPerLocator = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL);
		return productAvailableStockPerLocator.getQtyOnHandByLocator(productId, ImmutableSet.copyOf(sourceLocatorIds));
	}
}
