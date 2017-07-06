package de.metas.handlingunits.trace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	/**
	 * Creates one event for the given cost collector and adds it to the {@link HUTraceRepository}.<br>
	 * The event has no source VHU ID, even in case of a 1:1 PP_Order.
	 * 
	 * @param costCollector
	 */
	public void createAndAddFor(
			@NonNull final I_PP_Cost_Collector costCollector)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.costCollectorId(costCollector.getPP_Order_ID())
				.ppOrderId(costCollector.getPP_Order_ID())
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

	public void createAndAddFor(
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

	public void createAndAddFor(
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

	public void createAndAddFor(
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
		final List<I_M_HU> vhus;
		if (shipmentScheduleQtyPicked.getVHU_ID() > 0)
		{
			vhus = ImmutableList.of(shipmentScheduleQtyPicked.getVHU());
		}
		else if (shipmentScheduleQtyPicked.getM_TU_HU_ID() > 0)
		{
			vhus = retrieveVhus(shipmentScheduleQtyPicked.getM_TU_HU());
		}
		else if (shipmentScheduleQtyPicked.getM_LU_HU_ID() > 0)
		{
			vhus = retrieveVhus(shipmentScheduleQtyPicked.getM_LU_HU());
		}
		else
		{
			Check.errorIf(true, "None of the three HU fields are set; can't happen because we already checked the all");
			return;
		}

		for (final I_M_HU vhu : vhus)
		{
			builder.vhuId(vhu.getM_HU_ID())
					.vhuStatus(vhu.getHUStatus());

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
	public Map<Boolean, List<HUTraceEvent>> createAndAddFor(
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
		final Function<I_M_HU_Trx_Line, List<I_M_HU>> getVhus = huTrxLine ->
			{
				if (huTrxLine.getVHU_Item_ID() > 0)
				{
					return ImmutableList.of(huTrxLine.getVHU_Item().getM_HU());
				}
				else
				{
					return retrieveVhus(huTrxLine.getM_HU());
				}
			};

		final Map<Boolean, List<HUTraceEvent>> result = new HashMap<>();
		result.put(true, new ArrayList<>());
		result.put(false, new ArrayList<>());

		// iterate the lines and create an every per vhuId and sourceVhuId
		for (final I_M_HU_Trx_Line trxLine : trxLinesToUse)
		{
			builder.huTrxLineId(trxLine.getM_HU_Trx_Line_ID());

			final int topLevelHuId = retrieveTopLevelHuId(trxLine.getM_HU());
			builder.topLevelHuId(topLevelHuId);

			final List<I_M_HU> vhus = getVhus.apply(trxLine);
			for (final I_M_HU vhu : vhus)
			{
				builder.vhuStatus(trxLine.getHUStatus()) // we use the trx line's status here, because when creating traces for "old" HUs, the line's HUStatus is as it was at the time
						.vhuId(vhu.getM_HU_ID());

				final List<I_M_HU> sourceVhus = getVhus.apply(trxLine.getParent_HU_Trx_Line());
				for (final I_M_HU sourceVhu : sourceVhus)
				{
					if (sourceVhu.getM_HU_ID() != vhu.getM_HU_ID())
					{
						builder.vhuSourceId(sourceVhu.getM_HU_ID());
					}
					final HUTraceEvent event = builder.build();

					final boolean eventWasInserted = huTraceRepository.addEvent(event);
					result.get(eventWasInserted).add(event);
				}
			}
		}
		return result;
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

				final List<I_M_HU> vhus;
				if (huAssignment.getVHU_ID() > 0)
				{
					vhus = ImmutableList.of(huAssignment.getVHU());
				}
				else if (huAssignment.getM_TU_HU_ID() > 0)
				{
					vhus = retrieveVhus(huAssignment.getM_TU_HU());
				}
				else if (huAssignment.getM_LU_HU_ID() > 0)
				{
					vhus = retrieveVhus(huAssignment.getM_LU_HU());
				}
				else
				{
					vhus = retrieveVhus(huAssignment.getM_HU());
				}

				for (final I_M_HU vhu : vhus)
				{
					builder.vhuId(vhu.getM_HU_ID());
					builder.vhuStatus(vhu.getHUStatus());
					huTraceRepository.addEvent(builder.build());
				}
			}
		}
	}

	private List<I_M_HU> retrieveVhus(@NonNull final I_M_HU hu)
	{
		final List<I_M_HU> vhus = new ArrayList<>();

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		new HUIterator().setEnableStorageIteration(false)
				.setListener(new HUIteratorListenerAdapter()
				{
					public Result afterHU(final I_M_HU currentHu)
					{
						if (handlingUnitsBL.isVirtual(currentHu))
						{
							vhus.add(currentHu);
						}
						return Result.CONTINUE;
					}
				}).iterate(hu);

		return vhus;
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
