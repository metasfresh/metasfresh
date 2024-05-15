/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.contracts.modular.computing.facades.manufacturing;

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
public class ManufacturingFacadeService
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

	public ManufacturingProcessedReceipt getManufacturingProcessedReceipt(@NonNull final TableRecordReference recordRef)
	{
		return getManufacturingProcessedReceiptIfApplies(recordRef)
				.orElseThrow(() -> new AdempiereException("Cannot extract manufacturing processed product receipt from " + recordRef));
	}

	@NonNull
	public Optional<ManufacturingProcessedReceipt> getManufacturingProcessedReceiptIfApplies(@NonNull final TableRecordReference recordRef)
	{
		if (!recordRef.tableNameEqualsTo(I_PP_Cost_Collector.Table_Name))
		{
			return Optional.empty();
		}

		final PPCostCollectorId costCollectorId = getManufacturingReceiptOrIssuedId(recordRef);
		final I_PP_Cost_Collector ppCostCollector = ppCostCollectorBL.getById(costCollectorId);

		if (!CostCollectorType.ofCode(ppCostCollector.getCostCollectorType()).isMaterialReceipt())
		{
			return Optional.empty();
		}

		return Optional.of(toManufacturingProcessedReceipt(ppCostCollector));
	}

	@NonNull
	private ManufacturingProcessedReceipt toManufacturingProcessedReceipt(@NonNull final I_PP_Cost_Collector record)
	{
		return ManufacturingProcessedReceipt.builder()
				.id(PPCostCollectorId.ofRepoId(record.getPP_Cost_Collector_ID()))
				.manufacturingOrderId(PPOrderId.ofRepoId(record.getPP_Order_ID()))
				.transactionDate(InstantAndOrgId.ofInstant(record.getMovementDate().toInstant(), OrgId.ofRepoId(record.getAD_Org_ID())))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.processedProductId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyReceived(ppCostCollectorBL.getMovementQty(record))
				.build();
	}

	public ManufacturingCoReceipt getManufacturingCoReceipt(@NonNull final TableRecordReference recordRef)
	{
		return getManufacturingCoReceiptIfApplies(recordRef)
				.orElseThrow(() -> new AdempiereException("Cannot extract manufacturing co-product receipt from " + recordRef));
	}

	@NonNull
	public Optional<ManufacturingCoReceipt> getManufacturingCoReceiptIfApplies(@NonNull final TableRecordReference recordRef)
	{
		if (!recordRef.tableNameEqualsTo(I_PP_Cost_Collector.Table_Name))
		{
			return Optional.empty();
		}

		final PPCostCollectorId costCollectorId = getManufacturingReceiptOrIssuedId(recordRef);
		final I_PP_Cost_Collector ppCostCollector = ppCostCollectorBL.getById(costCollectorId);

		if (!CostCollectorType.ofCode(ppCostCollector.getCostCollectorType()).isCoOrByProductReceipt())
		{
			return Optional.empty();
		}

		return Optional.of(toManufacturingCoReceipt(ppCostCollector));
	}

	@NonNull
	private ManufacturingCoReceipt toManufacturingCoReceipt(@NonNull final I_PP_Cost_Collector record)
	{
		return ManufacturingCoReceipt.builder()
				.id(PPCostCollectorId.ofRepoId(record.getPP_Cost_Collector_ID()))
				.manufacturingOrderId(PPOrderId.ofRepoId(record.getPP_Order_ID()))
				.transactionDate(InstantAndOrgId.ofInstant(record.getMovementDate().toInstant(), OrgId.ofRepoId(record.getAD_Org_ID())))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.coProductId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyReceived(ppCostCollectorBL.getMovementQty(record))
				.build();
	}

	public ManufacturingRawIssued getManufacturingRawIssued(@NonNull final TableRecordReference recordRef)
	{
		return getManufacturingRawIssuedIfApplies(recordRef)
				.orElseThrow(() -> new AdempiereException("Cannot extract manufacturing raw product issued from " + recordRef));
	}

	@NonNull
	public Optional<ManufacturingRawIssued> getManufacturingRawIssuedIfApplies(@NonNull final TableRecordReference recordRef)
	{
		if (!recordRef.tableNameEqualsTo(I_PP_Cost_Collector.Table_Name))
		{
			return Optional.empty();
		}

		final PPCostCollectorId costCollectorId = getManufacturingReceiptOrIssuedId(recordRef);
		final I_PP_Cost_Collector ppCostCollector = ppCostCollectorBL.getById(costCollectorId);

		if (!CostCollectorType.ofCode(ppCostCollector.getCostCollectorType()).isComponentIssue())
		{
			return Optional.empty();
		}

		return Optional.of(toManufacturingRawIssued(ppCostCollector));
	}

	@NonNull
	private ManufacturingRawIssued toManufacturingRawIssued(@NonNull final I_PP_Cost_Collector record)
	{
		return ManufacturingRawIssued.builder()
				.id(PPCostCollectorId.ofRepoId(record.getPP_Cost_Collector_ID()))
				.manufacturingOrderId(PPOrderId.ofRepoId(record.getPP_Order_ID()))
				.transactionDate(InstantAndOrgId.ofInstant(record.getMovementDate().toInstant(), OrgId.ofRepoId(record.getAD_Org_ID())))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.rawProductId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyIssued(ppCostCollectorBL.getMovementQty(record))
				.build();
	}

	@NonNull
	public static PPCostCollectorId getManufacturingReceiptOrIssuedId(final @NonNull TableRecordReference recordRef)
	{
		return recordRef.getIdAssumingTableName(I_PP_Cost_Collector.Table_Name, PPCostCollectorId::ofRepoId);
	}
}
