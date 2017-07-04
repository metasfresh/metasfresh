package de.metas.handlingunits.trace;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.api.IPPCostCollectorBL;

import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@UtilityClass

public class HUTraceUtil
{

	public void createdAndAddFor(@NonNull final I_PP_Cost_Collector costCollector)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.costCollectorId(costCollector.getPP_Order_ID())
				.docTypeId(costCollector.getC_DocType_ID())
				.docStatus(costCollector.getDocStatus())
				.eventTime(costCollector.getMovementDate().toInstant());

		final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);
		if (costCollectorBL.isMaterialIssue(costCollector, true))
		{
			builder.type(HUTraceType.PRODUCTION_ISSUE);
		}
		else
		{
			builder.type(HUTraceType.PRODUCTION_RECEIPT);
		}
		HUTraceUtil.createAndAddEvents(builder, Stream.of(costCollector));
	}

	public void createdAndAddFor(
			@NonNull final I_M_InOut inOut,
			@NonNull final Stream<I_M_InOutLine> iols)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.inOutId(inOut.getM_InOut_ID())
				.docTypeId(inOut.getC_DocType_ID())
				.docStatus(inOut.getDocStatus())
				.eventTime(inOut.getMovementDate().toInstant());

		final String plusOrMinus = inOut.getMovementType().substring(1);
		if ("+".equals(plusOrMinus))
		{
			builder.type(HUTraceType.MATERIAL_RECEIPT);
		}
		else
		{
			builder.type(HUTraceType.MATERIAL_SHIPMENT);
		}

		HUTraceUtil.createAndAddEvents(builder, iols);
	}

	public void createdAndAddFor(
			@NonNull final I_M_Movement movement,
			@NonNull final Stream<I_M_MovementLine> movementLines)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.movementId(movement.getM_Movement_ID())
				.docTypeId(movement.getC_DocType_ID())
				.docStatus(movement.getDocStatus())
				.type(HUTraceType.MATERIAL_MOVEMENT)
				.eventTime(movement.getMovementDate().toInstant());

		HUTraceUtil.createAndAddEvents(builder, movementLines);
	}

	public static void createdAndAddFor(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		// get the top-level HU's ID for our event
		final int huId;
		if (shipmentScheduleQtyPicked.getM_LU_HU_ID() > 0)
		{
			huId = shipmentScheduleQtyPicked.getM_LU_HU_ID();
		}
		else if (shipmentScheduleQtyPicked.getM_TU_HU_ID() > 0)
		{
			huId = shipmentScheduleQtyPicked.getM_TU_HU_ID();
		}
		else if (shipmentScheduleQtyPicked.getVHU_ID() > 0)
		{
			huId = shipmentScheduleQtyPicked.getVHU_ID();
		}
		else
		{
			huId = -1;
		}

		if (huId < 0)
		{
			return;
		}

		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.eventTime(shipmentScheduleQtyPicked.getUpdated().toInstant())
				.shipmentScheduleId(shipmentScheduleQtyPicked.getM_ShipmentSchedule_ID())
				.type(HUTraceType.MATERIAL_PICKING)
				.huId(huId);

		final HUTraceRepository huTraceRepository = getHUTraceRepository();
		huTraceRepository.addEvent(builder.build());
	}

	public static void createdAndAddFor(
			@NonNull final I_M_HU_Trx_Hdr trxHeader,
			@NonNull final Stream<I_M_HU_Trx_Line> trxLines)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.type(HUTraceType.TRANSFORMATION)
				.eventTime(trxHeader.getUpdated().toInstant());

		final HUTraceRepository huTraceRepository = getHUTraceRepository();

		trxLines
				.filter(huTrxLine -> huTrxLine.getM_HU_ID() > 0)
				.filter(huTrxLine -> huTrxLine.getQty().signum() > 0)
				.filter(huTrxLine -> huTrxLine.getParent_HU_Trx_Line_ID() > 0)
				.filter(huTrxLine -> huTrxLine.getParent_HU_Trx_Line().getM_HU_ID() > 0)
				.forEach(huTrxLine ->
					{
						final HUTraceEvent huTraceEvent = builder
								.huId(huTrxLine.getM_HU_ID())
								.huSourceId(huTrxLine.getParent_HU_Trx_Line().getM_HU_ID())
								.build();

						huTraceRepository.addEvent(huTraceEvent);
					});
	}

	/**
	 * Takes the "mostly complete" builder and stream of model instances and creates {@link HUTraceEvent}s for the models'
	 * {@link I_M_HU_Assignment}s' top level HUs.
	 * 
	 * @param builder a pre fabricated builder. {@code huId}s and {@code eventTime} will be set by this method.
	 * @param models stream of objects that might be associated with HUs (e.g. inout lines).
	 */
	public void createAndAddEvents(
			@NonNull final HUTraceEventBuilder builder,
			@NonNull final Stream<?> models)
	{
		final HUTraceRepository huTraceRepository = getHUTraceRepository();

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Stream<I_M_HU_Assignment> huAssignments = models
				.flatMap(model ->
					{
						return queryBL.createQueryBuilder(I_M_HU_Assignment.class)
								.addOnlyActiveRecordsFilter()
								.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getModelTableId(model))
								.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, InterfaceWrapperHelper.getId(model))
								.orderBy().addColumn(I_M_HU_Assignment.COLUMN_M_HU_ID).endOrderBy()
								.create().stream();
					});

		final List<Integer> assignedTopLevelHuIds = huAssignments
				.map(huAssignment -> huAssignment.getM_HU_ID())
				.sorted()
				.distinct()
				.collect(Collectors.toList());

		for (final int assignedTopLevelHuId : assignedTopLevelHuIds)
		{
			final HUTraceEvent event = builder
					.huId(assignedTopLevelHuId)
					.build();
			huTraceRepository.addEvent(event);
		}
	}

	private HUTraceRepository getHUTraceRepository()
	{
		if (Adempiere.isUnitTestMode())
		{
			return new HUTraceRepository(); // no need to fire up spring just to unit-test this class
		}

		final HUTraceRepository huTraceRepository = Adempiere.getBean(HUTraceRepository.class);
		return huTraceRepository;
	}
}
