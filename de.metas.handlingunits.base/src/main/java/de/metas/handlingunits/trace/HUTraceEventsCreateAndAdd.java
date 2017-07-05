package de.metas.handlingunits.trace;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.api.IPPCostCollectorBL;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class HUTraceEventsCreateAndAdd
{

	private final HUTraceRepository huTraceRepository;

	public HUTraceEventsCreateAndAdd(@NonNull final HUTraceRepository huTraceRepository)
	{
		this.huTraceRepository = huTraceRepository;

	}

	public void createdAndAddFor(
			@NonNull final I_PP_Cost_Collector costCollector)
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
		createAndAddEvents(builder, ImmutableList.of(costCollector));
	}

	public void createdAndAddFor(
			@NonNull final I_M_InOut inOut,
			@NonNull final List<I_M_InOutLine> iols)
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

		createAndAddEvents(builder, iols);
	}

	public void createdAndAddFor(
			@NonNull final I_M_Movement movement,
			@NonNull final List<I_M_MovementLine> movementLines)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.movementId(movement.getM_Movement_ID())
				.docTypeId(movement.getC_DocType_ID())
				.docStatus(movement.getDocStatus())
				.type(HUTraceType.MATERIAL_MOVEMENT)
				.eventTime(movement.getMovementDate().toInstant());

		createAndAddEvents(builder, movementLines);
	}

	public void createdAndAddFor(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.eventTime(shipmentScheduleQtyPicked.getUpdated().toInstant())
				.shipmentScheduleId(shipmentScheduleQtyPicked.getM_ShipmentSchedule_ID())
				.type(HUTraceType.MATERIAL_PICKING);

		// get the top-level HU's ID for our event
		final int topLevelHuId;
		if (shipmentScheduleQtyPicked.getM_LU_HU_ID() > 0)
		{
			topLevelHuId = retrieveTopLevelHuId(shipmentScheduleQtyPicked.getM_LU_HU());
		}
		else if (shipmentScheduleQtyPicked.getM_TU_HU_ID() > 0)
		{
			topLevelHuId = retrieveTopLevelHuId(shipmentScheduleQtyPicked.getM_TU_HU());
		}
		else if (shipmentScheduleQtyPicked.getVHU_ID() > 0)
		{
			topLevelHuId = retrieveTopLevelHuId(shipmentScheduleQtyPicked.getVHU());
		}
		else
		{
			return; // none of the 3 HU fields is set; nothing to do
		}
		builder.topLevelHuId(topLevelHuId);

		// the one or more VHU that are assigned
		final List<Integer> vhuIds;
		if (shipmentScheduleQtyPicked.getVHU_ID() > 0)
		{
			vhuIds = ImmutableList.of(shipmentScheduleQtyPicked.getVHU_ID());
		}
		else if (shipmentScheduleQtyPicked.getM_TU_HU_ID() > 0)
		{
			vhuIds = retrieveVhuIds(shipmentScheduleQtyPicked.getM_TU_HU());
		}
		else if (shipmentScheduleQtyPicked.getM_LU_HU_ID() > 0)
		{
			vhuIds = retrieveVhuIds(shipmentScheduleQtyPicked.getM_LU_HU());
		}
		else
		{
			Check.errorIf(true, "None of the three HU fields are set; can't happen because we already checked the all");
			return;
		}

		for (final int vhuId : vhuIds)
		{
			builder.vhuId(vhuId);
			huTraceRepository.addEvent(builder.build());
		}
	}

	/**
	 * Iterate the given {@link I_M_HU_Trx_Line}s and add events for those lines that
	 * <ul>
	 * <li>have a {@code M_HU_ID}
	 * <li>have a partner ({@code Parent_HU_Trx_Line_ID > 0}) which also has a a M_HU_ID
	 * <li>have {@code Quantity > 0}
	 * </ul>
	 *
	 * @param trxHeader
	 * @param trxLines
	 */
	public void createdAndAddFor(
			@NonNull final I_M_HU_Trx_Hdr trxHeader,
			@NonNull final Stream<I_M_HU_Trx_Line> trxLines)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.type(HUTraceType.TRANSFORMATION)
				.eventTime(trxHeader.getUpdated().toInstant());

		// filter for the trx lines we actually want to create events from
		final List<I_M_HU_Trx_Line> trxLinesToUse = trxLines
				.filter(huTrxLine -> huTrxLine.getM_HU_ID() > 0)
				.filter(huTrxLine -> huTrxLine.getQty().signum() > 0)
				.filter(huTrxLine -> huTrxLine.getParent_HU_Trx_Line_ID() > 0)
				.filter(huTrxLine -> huTrxLine.getParent_HU_Trx_Line().getM_HU_ID() > 0)
				.collect(Collectors.toList());

		// gets the VHU IDs
		// this code is called twice, but I don't want to pollute the class with a method
		final Function<I_M_HU_Trx_Line, List<Integer>> getVhuIds = huTrxLine ->
			{
				if (huTrxLine.getVHU_Item_ID() > 0)
				{
					return ImmutableList.of(huTrxLine.getVHU_Item().getM_HU_ID());
				}
				else
				{
					return retrieveVhuIds(huTrxLine.getM_HU());
				}
			};

		// iterate the lines and create an every per vhuId and sourceVhuId
		for (final I_M_HU_Trx_Line trxLine : trxLinesToUse)
		{
			final int topLevelHuId = retrieveTopLevelHuId(trxLine.getM_HU());
			builder.topLevelHuId(topLevelHuId);

			final List<Integer> vhuIds = getVhuIds.apply(trxLine);
			for (final int vhuId : vhuIds)
			{
				builder.vhuId(vhuId);

				final List<Integer> sourceVhuIds = getVhuIds.apply(trxLine.getParent_HU_Trx_Line());
				for (final int soureVhuId : sourceVhuIds)
				{
					builder.vhuSourceId(soureVhuId);
					huTraceRepository.addEvent(builder.build());
				}
			}
		} ;
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
			@NonNull final List<?> models)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		for (final Object model : models)
		{
			final TableRecordReference modelRef = TableRecordReference.of(model);

			final List<I_M_HU_Assignment> huAssignments = queryBL.createQueryBuilder(I_M_HU_Assignment.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, modelRef.getAD_Table_ID())
					.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, modelRef.getRecord_ID())
					.orderBy()
					.addColumn(I_M_HU_Assignment.COLUMN_M_HU_ID).endOrderBy()
					.create()
					.list();

			for (final I_M_HU_Assignment huAssignment : huAssignments)
			{
				final int topLevelHuId = retrieveTopLevelHuId(huAssignment.getM_HU());
				builder.topLevelHuId(topLevelHuId);
				builder.eventTime(huAssignment.getUpdated().toInstant());

				final List<Integer> vhuIds;
				if (huAssignment.getVHU_ID() > 0)
				{
					vhuIds = ImmutableList.of(huAssignment.getVHU_ID());
				}
				else if (huAssignment.getM_TU_HU_ID() > 0)
				{
					vhuIds = retrieveVhuIds(huAssignment.getM_TU_HU());
				}
				else if (huAssignment.getM_LU_HU_ID() > 0)
				{
					vhuIds = retrieveVhuIds(huAssignment.getM_LU_HU());
				}
				else
				{
					vhuIds = retrieveVhuIds(huAssignment.getM_HU());
				}

				for (final int vhuId : vhuIds)
				{
					builder.vhuId(vhuId);
					huTraceRepository.addEvent(builder.build());
				}
			}
		}
	}

	private List<Integer> retrieveVhuIds(@NonNull final I_M_HU hu)
	{
		final List<Integer> vhuIds = new ArrayList<>();

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		new HUIterator().setEnableStorageIteration(false)
				.setListener(new HUIteratorListenerAdapter()
				{
					public Result afterHU(final I_M_HU currentHu)
					{
						if (handlingUnitsBL.isVirtual(currentHu))
						{
							vhuIds.add(currentHu.getM_HU_ID());
						}
						return Result.CONTINUE;
					}
				}).iterate(hu);

		return vhuIds;
	}

	private int retrieveTopLevelHuId(@NonNull final I_M_HU hu)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		I_M_HU parent = hu;
		I_M_HU greaterParent = handlingUnitsDAO.retrieveParent(parent);
		while (greaterParent != null)
		{
			parent = greaterParent;
			greaterParent = handlingUnitsDAO.retrieveParent(parent);
		}
		return parent.getM_HU_ID();
	}
}
