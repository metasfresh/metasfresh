package de.metas.inventory.mobileui.launchers;

import de.metas.handlingunits.inventory.InventoryReference;
import de.metas.inventory.mobileui.deps.warehouse.WarehouseInfo;
import de.metas.inventory.mobileui.deps.warehouse.WarehousesLoadingCache;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Stream;

@Builder(access = AccessLevel.PACKAGE)
class InventoryJobReferenceMapper
{
	@NonNull private final WarehousesLoadingCache warehouses;

	public Stream<InventoryJobReference> toInventoryJobReferencesStream(final List<InventoryReference> inventoryReferences)
	{
		warehouses.warnUp(inventoryReferences, InventoryReference::getWarehouseId);
		return inventoryReferences.stream().map(this::toInventoryJobReference);
	}

	private InventoryJobReference toInventoryJobReference(final InventoryReference inventoryReference)
	{
		final WarehouseInfo warehouse = warehouses.getById(inventoryReference.getWarehouseId());

		return InventoryJobReference.builder()
				.inventoryId(inventoryReference.getInventoryId())
				.documentNo(inventoryReference.getDocumentNo())
				.movementDate(inventoryReference.getMovementDateAsLocalDate())
				.warehouseName(warehouse.getWarehouseName())
				.isJobStarted(inventoryReference.isAssignedToResponsible())
				.build();
	}

}
