package de.metas.inventory.mobileui.job.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.InventoryQuery;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobId;
import de.metas.inventory.mobileui.job.InventoryJobLine;
import de.metas.inventory.mobileui.launchers.InventoryJobReference;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class InventoryJobLoaderAndSaver
{
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final Products products = new Products();
	private final Warehouses warehouses = new Warehouses();
	private final HashMap<InventoryId, I_M_Inventory> inventoryRecordsByInventoryId = new HashMap<>();
	private final HashMap<InventoryId, ArrayList<I_M_InventoryLine>> inventoryLineRecordsByInventoryId = new HashMap<>();

	public InventoryJob loadById(final InventoryJobId jobId)
	{
		final InventoryId inventoryId = jobId.toInventoryId();
		final I_M_Inventory inventoryRecord = getInventoryRecordById(inventoryId);
		final List<I_M_InventoryLine> lineRecords = getLineRecords(inventoryId);

		products.warnUp(lineRecords, lineRecord -> ProductId.ofRepoId(lineRecord.getM_Product_ID()));

		return fromRecord(inventoryRecord, lineRecords);
	}

	private ArrayList<I_M_InventoryLine> getLineRecords(final InventoryId inventoryId)
	{
		return inventoryLineRecordsByInventoryId.computeIfAbsent(inventoryId, this::retrieveLineRecords);
	}

	private ArrayList<I_M_InventoryLine> retrieveLineRecords(final InventoryId inventoryId)
	{
		final List<I_M_InventoryLine> lines = inventoryDAO.retrieveLinesForInventoryId(inventoryId, I_M_InventoryLine.class);
		return new ArrayList<>(lines);
	}

	private I_M_Inventory getInventoryRecordById(final InventoryId inventoryId)
	{
		return inventoryRecordsByInventoryId.computeIfAbsent(inventoryId, inventoryDAO::getById);
	}

	private InventoryJob fromRecord(final I_M_Inventory inventoryRecord, final List<I_M_InventoryLine> lineRecords)
	{
		final WarehouseInfo warehouse = warehouses.getById(extractWarehouseId(inventoryRecord));

		return InventoryJob.builder()
				.id(InventoryJobId.ofRepoId(inventoryRecord.getM_Inventory_ID()))
				.responsibleId(extractResponsibleId(inventoryRecord))
				.documentNo(inventoryRecord.getDocumentNo())
				.movementDate(TimeUtil.asLocalDate(inventoryRecord.getMovementDate()))
				.warehouseId(warehouse.getWarehouseId())
				.warehouseName(warehouse.getWarehouseName())
				.lines(lineRecords.stream()
						.map((record) -> fromRecord(record, warehouse.getWarehouseId()))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private InventoryJobLine fromRecord(final I_M_InventoryLine record, @NonNull final WarehouseId warehouseId)
	{
		final ProductInfo productInfo = products.getById(ProductId.ofRepoId(record.getM_Product_ID()));
		final I_C_UOM uom = uomDAO.getById(UomId.ofRepoId(record.getC_UOM_ID()));

		final LocatorId locatorId = LocatorId.ofRepoId(warehouseId, record.getM_Locator_ID());

		return InventoryJobLine.builder()
				.id(InventoryLineId.ofRepoId(record.getM_InventoryLine_ID()))
				.locatorId(locatorId)
				.locatorName(warehouses.getLocatorName(locatorId))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.productNo(productInfo.getProductNo())
				.productName(productInfo.getProductName())
				.qtyBooked(Quantity.of(record.getQtyBook(), uom))
				.qtyCount(Quantity.of(record.getQtyCount(), uom))
				.build();
	}

	public void save(@NonNull final InventoryJob job)
	{
		final InventoryId inventoryId = job.getId().toInventoryId();
		final I_M_Inventory headerRecord = getInventoryRecordById(inventoryId);
		updateRecord(headerRecord, job);
		InterfaceWrapperHelper.save(headerRecord);

		final ArrayList<I_M_InventoryLine> lineRecords = getLineRecords(inventoryId);
		final ImmutableMap<InventoryLineId, I_M_InventoryLine> lineRecordsById = Maps.uniqueIndex(lineRecords, InventoryJobLoaderAndSaver::extractInventoryLineId);

		//
		// NEW/UPDATE
		final HashSet<InventoryLineId> savedLineIds = new HashSet<>();
		for (InventoryJobLine line : job.getLines())
		{
			final InventoryLineId lineId = line.getId();
			final I_M_InventoryLine lineRecord = lineRecordsById.get(line.getId());
			if (lineRecord == null)
			{
				throw new AdempiereException("No line record found for " + lineId);
			}
			updateRecord(lineRecord, line);
			InterfaceWrapperHelper.save(lineRecord);

			savedLineIds.add(lineId);
		}

		//
		// DELETE
		for (Iterator<I_M_InventoryLine> it = lineRecords.iterator(); it.hasNext(); )
		{
			final I_M_InventoryLine lineRecord = it.next();
			final InventoryLineId id = extractInventoryLineId(lineRecord);
			if (!savedLineIds.contains(id))
			{
				it.remove();
				InterfaceWrapperHelper.delete(lineRecord);
			}
		}
	}

	private static InventoryLineId extractInventoryLineId(org.compiere.model.I_M_InventoryLine inventoryRecord)
	{
		return InventoryLineId.ofRepoId(inventoryRecord.getM_InventoryLine_ID());
	}

	private static void updateRecord(final I_M_Inventory record, final @NonNull InventoryJob job)
	{
		record.setAD_User_Responsible_ID(UserId.toRepoId(job.getResponsibleId()));
	}

	private static void updateRecord(final I_M_InventoryLine lineRecord, final InventoryJobLine from)
	{
		lineRecord.setM_Product_ID(from.getProductId().getRepoId());
		lineRecord.setC_UOM_ID(from.getUomId().getRepoId());
		lineRecord.setQtyBook(from.getQtyBooked().toBigDecimal());
		lineRecord.setQtyCount(from.getQtyCount().toBigDecimal());
	}

	public InventoryJob updateById(final InventoryJobId jobId, final UnaryOperator<InventoryJob> updater)
	{
		final InventoryJob job = loadById(jobId);
		final InventoryJob jobChanged = updater.apply(job);
		if (Objects.equals(job, jobChanged))
		{
			return job;
		}
		else
		{
			save(jobChanged);
			return jobChanged;
		}
	}

	public Stream<InventoryJobReference> streamReferences(InventoryQuery query)
	{
		final ImmutableList<I_M_Inventory> inventories = inventoryDAO.stream(query).collect(ImmutableList.toImmutableList());
		warehouses.warnUp(inventories, InventoryJobLoaderAndSaver::extractWarehouseId);

		return inventories.stream().map(this::toInventoryJobReference);
	}

	private InventoryJobReference toInventoryJobReference(final I_M_Inventory inventory)
	{
		final WarehouseInfo warehouse = warehouses.getById(extractWarehouseId(inventory));
		final UserId responsibleId = extractResponsibleId(inventory);
		return InventoryJobReference.builder()
				.inventoryId(InventoryId.ofRepoId(inventory.getM_Inventory_ID()))
				.documentNo(inventory.getDocumentNo())
				.movementDate(TimeUtil.asLocalDate(inventory.getMovementDate()))
				.warehouseName(warehouse.getWarehouseName())
				.isJobStarted(responsibleId != null)
				.build();
	}

	private static @Nullable UserId extractResponsibleId(final I_M_Inventory inventory)
	{
		return UserId.ofRepoIdOrNullIfSystem(inventory.getAD_User_Responsible_ID());
	}

	private static WarehouseId extractWarehouseId(final I_M_Inventory inventory)
	{
		return WarehouseId.ofRepoId(inventory.getM_Warehouse_ID());
	}

	//
	//
	//
	//
	//

}
