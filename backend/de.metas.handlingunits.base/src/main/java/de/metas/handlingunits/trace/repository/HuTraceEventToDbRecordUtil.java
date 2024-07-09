package de.metas.handlingunits.trace.repository;

import de.metas.document.DocTypeId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEvent;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inventory.InventoryId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.compiere.util.TimeUtil;

import java.util.Optional;
import java.util.OptionalInt;

import static org.adempiere.model.InterfaceWrapperHelper.isNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class HuTraceEventToDbRecordUtil
{
	public static HUTraceEvent fromDbRecord(@NonNull final I_M_HU_Trace dbRecord)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.orgId(OrgId.ofRepoIdOrAny(dbRecord.getAD_Org_ID()))
				.ppCostCollectorId(dbRecord.getPP_Cost_Collector_ID())
				.ppOrderId(dbRecord.getPP_Order_ID())
				.docStatus(dbRecord.getDocStatus())
				.eventTime(dbRecord.getEventTime().toInstant()) // EeventTime is a mandatory column, so no NPE
				.vhuId(HuId.ofRepoId(dbRecord.getVHU_ID()))
				.productId(ProductId.ofRepoId(dbRecord.getM_Product_ID()))
				.qty(Quantitys.of(dbRecord.getQty(), UomId.ofRepoId(dbRecord.getC_UOM_ID())))
				.huTrxLineId(dbRecord.getM_HU_Trx_Line_ID())
				.vhuStatus(dbRecord.getVHUStatus())
				.topLevelHuId(HuId.ofRepoId(dbRecord.getM_HU_ID()))
				.vhuSourceId(HuId.ofRepoIdOrNull(dbRecord.getVHU_Source_ID()))
				.inOutId(dbRecord.getM_InOut_ID())
				.movementId(dbRecord.getM_Movement_ID())
				.shipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(dbRecord.getM_ShipmentSchedule_ID()))
				.type(HUTraceType.ofCode(dbRecord.getHUTraceType())) // HUTraceType is also a mandatory column, so no NPE
				.lotNumber(dbRecord.getLotNumber())
				.inventoryId(InventoryId.ofRepoIdOrNull(dbRecord.getM_Inventory_ID()));

		if (dbRecord.getM_HU_Trace_ID() > 0)
		{
			builder.huTraceEventId(OptionalInt.of(dbRecord.getM_HU_Trace_ID()));
		}

		if (isNull(dbRecord, I_M_HU_Trace.COLUMNNAME_C_DocType_ID))
		{
			builder.docTypeId(Optional.empty());
		}
		else
		{
			builder.docTypeId(DocTypeId.optionalOfRepoId(dbRecord.getC_DocType_ID()));
		}

		return builder.build();
	}

	public static void copyToDbRecord(
			@NonNull final HUTraceEvent huTraceRecord,
			@NonNull final I_M_HU_Trace dbRecord)
	{
		if (huTraceRecord.getDocTypeId().isPresent())
		{
			dbRecord.setC_DocType_ID(huTraceRecord.getDocTypeId().get().getRepoId()); // note that zero means "new", and not "nothing" or null
		}
		// HU_TraceEvent_ID is not copied to the dbRecord! because the dbRecord is where it always comes from
		dbRecord.setAD_Org_ID(OrgId.toRepoIdOrAny(huTraceRecord.getOrgId()));
		dbRecord.setDocStatus(huTraceRecord.getDocStatus());
		dbRecord.setEventTime(TimeUtil.asTimestamp(huTraceRecord.getEventTime()));
		dbRecord.setHUTraceType(huTraceRecord.getType().toString());
		dbRecord.setVHU_ID(huTraceRecord.getVhuId().getRepoId());
		dbRecord.setM_Product_ID(huTraceRecord.getProductId().getRepoId());
		dbRecord.setQty(huTraceRecord.getQty().toBigDecimal());
		dbRecord.setC_UOM_ID(UomId.toRepoId(huTraceRecord.getQty().getUomId()));
		dbRecord.setVHUStatus(huTraceRecord.getVhuStatus());
		dbRecord.setM_HU_Trx_Line_ID(huTraceRecord.getHuTrxLineId());
		dbRecord.setM_HU_ID(huTraceRecord.getTopLevelHuId().getRepoId());
		dbRecord.setVHU_Source_ID(HuId.toRepoId(huTraceRecord.getVhuSourceId()));
		dbRecord.setM_InOut_ID(huTraceRecord.getInOutId());
		dbRecord.setM_Movement_ID(huTraceRecord.getMovementId());
		dbRecord.setM_Inventory_ID(InventoryId.toRepoId(huTraceRecord.getInventoryId()));
		dbRecord.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(huTraceRecord.getShipmentScheduleId()));
		dbRecord.setPP_Cost_Collector_ID(huTraceRecord.getPpCostCollectorId());
		dbRecord.setPP_Order_ID(huTraceRecord.getPpOrderId());
		dbRecord.setLotNumber(huTraceRecord.getLotNumber());
	}
}
