package de.metas.handlingunits.trace.repository;

import static org.adempiere.model.InterfaceWrapperHelper.isNull;

import java.util.OptionalInt;

import org.compiere.util.TimeUtil;

import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEvent;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import lombok.NonNull;

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
				.orgId(dbRecord.getAD_Org_ID())
				.ppCostCollectorId(dbRecord.getPP_Cost_Collector_ID())
				.ppOrderId(dbRecord.getPP_Order_ID())
				.docStatus(dbRecord.getDocStatus())
				.eventTime(dbRecord.getEventTime().toInstant()) // EeventTime is a mandatory column, so no NPE
				.vhuId(dbRecord.getVHU_ID())
				.productId(dbRecord.getM_Product_ID())
				.qty(dbRecord.getQty())
				.huTrxLineId(dbRecord.getM_HU_Trx_Line_ID())
				.vhuStatus(dbRecord.getVHUStatus())
				.topLevelHuId(dbRecord.getM_HU_ID())
				.vhuSourceId(dbRecord.getVHU_Source_ID())
				.inOutId(dbRecord.getM_InOut_ID())
				.movementId(dbRecord.getM_Movement_ID())
				.shipmentScheduleId(dbRecord.getM_ShipmentSchedule_ID())
				.type(HUTraceType.valueOf(dbRecord.getHUTraceType())); // HUTraceType is also a mandatory column, so no NPE

		if (dbRecord.getM_HU_Trace_ID() > 0)
		{
			builder.huTraceEventId(OptionalInt.of(dbRecord.getM_HU_Trace_ID()));
		}

		if (isNull(dbRecord, I_M_HU_Trace.COLUMNNAME_C_DocType_ID))
		{
			builder.docTypeId(OptionalInt.empty());
		}
		else
		{
			builder.docTypeId(OptionalInt.of(dbRecord.getC_DocType_ID())); // note that zero means "new", and not "nothing" or null
		}

		return builder.build();
	}
	
	public static void copyToDbRecord(
			@NonNull final HUTraceEvent huTraceRecord,
			@NonNull final I_M_HU_Trace dbRecord)
	{
		if (huTraceRecord.getDocTypeId().isPresent())
		{
			dbRecord.setC_DocType_ID(huTraceRecord.getDocTypeId().getAsInt()); // note that zero means "new", and not "nothing" or null
		}
		// HU_TraceEvent_ID is not copied to the dbRecord! because the dbREcord is where it always comes from
		dbRecord.setAD_Org_ID(huTraceRecord.getOrgId());
		dbRecord.setDocStatus(huTraceRecord.getDocStatus());
		dbRecord.setEventTime(TimeUtil.asTimestamp(huTraceRecord.getEventTime()));
		dbRecord.setHUTraceType(huTraceRecord.getType().toString());
		dbRecord.setVHU_ID(huTraceRecord.getVhuId());
		dbRecord.setM_Product_ID(huTraceRecord.getProductId());
		dbRecord.setQty(huTraceRecord.getQty());
		dbRecord.setVHUStatus(huTraceRecord.getVhuStatus());
		dbRecord.setM_HU_Trx_Line_ID(huTraceRecord.getHuTrxLineId());
		dbRecord.setM_HU_ID(huTraceRecord.getTopLevelHuId());
		dbRecord.setVHU_Source_ID(huTraceRecord.getVhuSourceId());
		dbRecord.setM_InOut_ID(huTraceRecord.getInOutId());
		dbRecord.setM_Movement_ID(huTraceRecord.getMovementId());
		dbRecord.setM_ShipmentSchedule_ID(huTraceRecord.getShipmentScheduleId());
		dbRecord.setPP_Cost_Collector_ID(huTraceRecord.getPpCostCollectorId());
		dbRecord.setPP_Order_ID(huTraceRecord.getPpOrderId());
	}
}
