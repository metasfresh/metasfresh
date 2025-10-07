package de.metas.inventory.mobileui.job.repository;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryReference;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.inventory.InventoryQuery;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobId;
import de.metas.inventory.mobileui.job.InventoryJobLine;
import de.metas.inventory.mobileui.launchers.InventoryJobReference;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Builder
public class InventoryJobLoaderAndSaver
{
	@NonNull private final IUOMDAO uomDAO;
	@NonNull private final InventoryService inventoryService;

	private final Products products = new Products();
	private final Warehouses warehouses = new Warehouses();

	public InventoryJob loadById(final InventoryJobId jobId)
	{
		final Inventory inventory = inventoryService.getById(jobId.toInventoryId());

		products.warnUp(inventory.getLines(), InventoryLine::getProductId);

		return toInventoryJob(inventory);
	}

	private InventoryJob toInventoryJob(final Inventory inventory)
	{
		final WarehouseInfo warehouseInfo = warehouses.getById(Check.assumeNotNull(inventory.getWarehouseId(), "warehouse is set"));
		return InventoryJob.builder()
				.id(InventoryJobId.ofInventoryId(inventory.getId()))
				.responsibleId(inventory.getResponsibleId())
				.documentNo(inventory.getDocumentNo())
				.movementDate(inventory.getMovementDate().toLocalDate())
				.warehouseId(warehouseInfo.getWarehouseId())
				.warehouseName(warehouseInfo.getWarehouseName())
				.lines(inventory.getLines()
						.stream()
						.map(this::toInventoryJobLine)
						.collect(ImmutableList.toImmutableList())
				)
				.build();
	}

	private InventoryJobLine toInventoryJobLine(final InventoryLine line)
	{
		final ProductInfo productInfo = products.getById(line.getProductId());

		return InventoryJobLine.builder()
				.id(line.getIdNonNull())
				.productId(productInfo.getProductId())
				.productNo(productInfo.getProductNo())
				.productName(productInfo.getProductName())
				.locatorId(line.getLocatorId())
				.locatorName(warehouses.getLocatorName(line.getLocatorId()))
				.isCounted(line.isCounted())
				.qtyBooked(line.getQtyBookFixed())
				.qtyCount(line.getQtyCountFixed())
				.build();
	}

	public InventoryJob updateById(final InventoryJobId inventoryJobId, final UnaryOperator<InventoryJob> updater)
	{
		inventoryService.updateById(inventoryJobId.toInventoryId(), inventory -> {
			
		});
	}

	public Stream<InventoryJobReference> streamReferences(InventoryQuery query)
	{
		final ImmutableList<InventoryReference> inventoryReferences = inventoryService.streamReferences(query).collect(ImmutableList.toImmutableList());
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
