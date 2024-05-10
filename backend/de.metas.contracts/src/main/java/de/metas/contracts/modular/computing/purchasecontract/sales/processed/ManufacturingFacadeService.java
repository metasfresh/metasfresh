package de.metas.contracts.modular.computing.purchasecontract.sales.processed;

import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service used to hide behind all manufacturing BLs & models and present the manufacturing in modular contracts ubiquitous language
 */
@Service
class ManufacturingFacadeService
{
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

	public boolean isModularOrder(@NonNull final PPOrderId id)
	{
		return ppOrderBL.isModularOrder(id);
	}

	@NonNull
	public ManufacturingOrder getManufacturingOrder(@NonNull final PPOrderId id)
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(id);
		return toManufacturingOrder(ppOrder);
	}

	@NonNull
	private ManufacturingOrder toManufacturingOrder(@NonNull final I_PP_Order record)
	{
		return ManufacturingOrder.builder()
				.id(PPOrderId.ofRepoId(record.getPP_Order_ID()))
				.processedProductId(ProductId.ofRepoId(record.getM_Product_ID()))
				.build();
	}

	public ManufacturingReceipt getManufacturingReceipt(@NonNull final TableRecordReference recordRef)
	{
		return getManufacturingReceiptIfApplies(recordRef)
				.orElseThrow(() -> new AdempiereException("Cannot extract manufacturing receipt from " + recordRef));
	}

	@NonNull
	public Optional<ManufacturingReceipt> getManufacturingReceiptIfApplies(@NonNull final TableRecordReference recordRef)
	{
		if (!recordRef.tableNameEqualsTo(I_PP_Cost_Collector.Table_Name))
		{
			return Optional.empty();
		}

		final PPCostCollectorId costCollectorId = getManufacturingReceiptId(recordRef);
		final I_PP_Cost_Collector ppCostCollector = ppCostCollectorBL.getById(costCollectorId);

		if (!CostCollectorType.ofCode(ppCostCollector.getCostCollectorType()).isMaterialReceipt())
		{
			return Optional.empty();
		}

		return Optional.of(toManufacturingReceipt(ppCostCollector));
	}

	@NonNull
	private ManufacturingReceipt toManufacturingReceipt(@NonNull final I_PP_Cost_Collector record)
	{
		return ManufacturingReceipt.builder()
				.id(PPCostCollectorId.ofRepoId(record.getPP_Cost_Collector_ID()))
				.manufacturingOrderId(PPOrderId.ofRepoId(record.getPP_Order_ID()))
				.transactionDate(InstantAndOrgId.ofInstant(record.getMovementDate().toInstant(), OrgId.ofRepoId(record.getAD_Org_ID())))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyReceived(ppCostCollectorBL.getMovementQty(record))
				.build();
	}

	@NonNull
	public static PPCostCollectorId getManufacturingReceiptId(final @NonNull TableRecordReference recordRef)
	{
		return recordRef.getIdAssumingTableName(I_PP_Cost_Collector.Table_Name, PPCostCollectorId::ofRepoId);
	}
}
