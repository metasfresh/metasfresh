package de.metas.handlingunits.trace;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IPPCostCollectorBL;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.logging.LogManager;
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

/**
 * The job of this service is to create {@link HUTraceEvent}s from the inout lines etc it is invoked with<br>
 * and add those events to the {@link HUTraceRepository}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class HUTraceEventsService
{
	private static final Logger logger = LogManager.getLogger(HUTraceEventsService.class);

	/**
	 * The method {@link #createAndAddFor(I_M_HU_Trx_Hdr, List)} will ignore hu transaction lines that reference one of these tables, because there is already dedicated code to handles events around those tables.
	 */
	private static final Set<String> TABLE_NAMES_IGNORED_FOR_TRANSFORMATION_TRACING = ImmutableSet.of(
			I_M_InOutLine.Table_Name,
			I_M_MovementLine.Table_Name,
			I_PP_Cost_Collector.Table_Name,
			I_M_ShipmentSchedule_QtyPicked.Table_Name);

	private final HUTraceRepository huTraceRepository;
	private final HUAccessService huAccessService;

	public HUTraceEventsService(
			@NonNull final HUTraceRepository huTraceRepository,
			@NonNull final HUAccessService huAccessService)
	{
		this.huAccessService = huAccessService;
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
				.ppCostCollectorId(costCollector.getPP_Cost_Collector_ID())
				.ppOrderId(costCollector.getPP_Order_ID())
				.docTypeId(OptionalInt.of(costCollector.getC_DocType_ID()))
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
				.docTypeId(OptionalInt.of(inOut.getC_DocType_ID()))
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
				.docTypeId(OptionalInt.of(movement.getC_DocType_ID()))
				.docStatus(movement.getDocStatus())
				.type(HUTraceType.MATERIAL_MOVEMENT)
				.eventTime(movement.getMovementDate().toInstant());

		createAndAddEvents(builder, movementLines);
	}

	public void createAndAddFor(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.orgId(shipmentScheduleQtyPicked.getAD_Org_ID())
				.eventTime(shipmentScheduleQtyPicked.getUpdated().toInstant())
				.shipmentScheduleId(shipmentScheduleQtyPicked.getM_ShipmentSchedule_ID())
				.type(HUTraceType.MATERIAL_PICKING);

		// get the top-level HU's ID for our event
		final int topLevelHuId;
		if (shipmentScheduleQtyPicked.getM_LU_HU_ID() > 0)
		{
			topLevelHuId = huAccessService.retrieveTopLevelHuId(shipmentScheduleQtyPicked.getM_LU_HU());
		}
		else if (shipmentScheduleQtyPicked.getM_TU_HU_ID() > 0)
		{
			topLevelHuId = huAccessService.retrieveTopLevelHuId(shipmentScheduleQtyPicked.getM_TU_HU());
		}
		else if (shipmentScheduleQtyPicked.getVHU_ID() > 0)
		{
			topLevelHuId = huAccessService.retrieveTopLevelHuId(shipmentScheduleQtyPicked.getVHU());
		}
		else
		{
			logger.info("Given shipmentScheduleQtyPicked has none of its 3 HU fields set; returning; shipmentScheduleQtyPicked={}", shipmentScheduleQtyPicked);
			return;
		}

		if (topLevelHuId <= 0)
		{
			logger.info("Given shipmentScheduleQtyPicked has HUs, but they are not \"physical\"; returning; shipmentScheduleQtyPicked={}", shipmentScheduleQtyPicked);
			return; // there is no top level HU with the correct status; this means that the HU is still in the planning status.
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
			vhus = huAccessService.retrieveVhus(shipmentScheduleQtyPicked.getM_TU_HU());
		}
		else if (shipmentScheduleQtyPicked.getM_LU_HU_ID() > 0)
		{
			vhus = huAccessService.retrieveVhus(shipmentScheduleQtyPicked.getM_LU_HU());
		}
		else
		{
			Check.errorIf(true, "None of the three HU fields are set; can't happen because we already checked them all");
			return;
		}

		for (final I_M_HU vhu : vhus)
		{
			builderSetVhuProductAndQty(builder, vhu)
					.vhuStatus(vhu.getHUStatus());

			huTraceRepository.addEvent(builder.build());
		}
	}

	/**
	 * Iterate the given {@link I_M_HU_Trx_Line}s and add events for those lines that
	 * <ul>
	 * <li>have a {@link IHandlingUnitsBL#isPhysicalHU(String) physical} {@code M_HU_ID}.<br>
	 * We don't care about planned HUs and we assume that destroyed or shipped HUs won't be altered anymore.
	 * <li>have a partner ({@code Parent_HU_Trx_Line_ID > 0}) which also has a a M_HU_ID
	 * <li>have {@code Quantity > 0}
	 * </ul>
	 *
	 * @param trxHeader needed because we use its {@code updated} timestamp for our eventTime.
	 * @param trxLines
	 *
	 * @return a map with two lists that contains all events which were created and given to the repository.<br>
	 *         The events that were actually inserted are in the list with the {@code true} key.
	 */
	public Map<Boolean, List<HUTraceEvent>> createAndAddFor(
			@NonNull final I_M_HU_Trx_Hdr trxHeader,
			@NonNull final List<I_M_HU_Trx_Line> trxLines)
	{
		final HUTraceEventBuilder traceEventBuilder = HUTraceEvent.builder()
				.type(HUTraceType.TRANSFORM_LOAD)
				.eventTime(trxHeader.getUpdated().toInstant());

		final List<I_M_HU_Trx_Line> trxLinesToUse = filterTrxLinesToUse(trxLines);

		// gets the VHU IDs
		// this code is called twice, but I don't want to pollute the class with a method
		final Function<I_M_HU_Trx_Line, List<I_M_HU>> getVhus = huTrxLine -> {
			if (huTrxLine.getVHU_Item_ID() > 0)
			{
				return ImmutableList.of(huTrxLine.getVHU_Item().getM_HU());
			}
			else if (huTrxLine.getM_HU_ID() > 0)
			{
				return huAccessService.retrieveVhus(huTrxLine.getM_HU());
			}
			else
			{
				return ImmutableList.of();
			}
		};

		final Map<Boolean, List<HUTraceEvent>> result = new HashMap<>();
		result.put(true, new ArrayList<>());
		result.put(false, new ArrayList<>());

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		// iterate the lines and create an every per vhuId and sourceVhuId
		for (final I_M_HU_Trx_Line trxLine : trxLinesToUse)
		{
			traceEventBuilder.huTrxLineId(trxLine.getM_HU_Trx_Line_ID());

			final List<I_M_HU> vhus = getVhus.apply(trxLine);
			for (final I_M_HU vhu : vhus)
			{
				if (!handlingUnitsBL.isPhysicalHU(vhu.getHUStatus()))
				{
					logger.info("vhu of the current trxLine has status={}; nothing to do with that trxLine; vhu={}; trxLine={}", vhu.getHUStatus(), vhu, trxLine);
					continue;
				}

				final Optional<IPair<I_M_Product, BigDecimal>> productAndQty = huAccessService.retrieveProductAndQty(vhu);
				if (!productAndQty.isPresent())
				{
					logger.info("vhu of the current trxLine has no product and quantity; nothing to do with that trxLine; vhu={}; trxLine={}", vhu, trxLine);
					continue;
				}

				final int vhuTopLevelHuId = huAccessService.retrieveTopLevelHuId(vhu);
				Check.errorIf(vhuTopLevelHuId <= 0, "vhuTopLevelHuId returned by HUAccessService.retrieveTopLevelHuId has to be >0, but is {}; vhu={}", vhuTopLevelHuId, vhu);

				traceEventBuilder
						.vhuId(vhu.getM_HU_ID())
						.topLevelHuId(vhuTopLevelHuId)
						.productId(productAndQty.get().getLeft().getM_Product_ID())
						.qty(productAndQty.get().getRight())
						.vhuStatus(trxLine.getHUStatus()); // we use the trx line's status here, because when creating traces for "old" HUs, the line's HUStatus is as it was at the time

				final I_M_HU_Trx_Line sourceTrxLine = trxLine.getParent_HU_Trx_Line();
				final List<I_M_HU> sourceVhus = getVhus.apply(sourceTrxLine);
				for (final I_M_HU sourceVhu : sourceVhus)
				{
					if (!handlingUnitsBL.isPhysicalHU(sourceVhu.getHUStatus()))
					{
						logger.info("sourceVhu of the current trxLine's sourceTrxLine (Parent_HU_Trx_Line) has status={}; nothing to do with that sourceVhu; sourceVhu={}; sourceTrxLine={}; trxLine={}",
								sourceVhu.getHUStatus(), sourceVhu, sourceTrxLine, trxLine);
						continue;
					}

					traceEventBuilder.vhuSourceId(sourceVhu.getM_HU_ID());

					if (sourceVhu.getM_HU_ID() == vhu.getM_HU_ID())
					{
						logger.info("sourceVhu of the current trxLine's sourceTrxLine (Parent_HU_Trx_Line) is the same as vhu of the current trxLine itself; nothing to do with that sourceVhu; vhu/sourceVhu={}; sourceTrxLine={}; trxLine={}",
								sourceVhu, sourceTrxLine, trxLine);
						continue; // the source-HU might be the same if e.g. only the status was changed
					}

					// create a trace record for the split's "destination"
					final HUTraceEvent splitDestEvent = traceEventBuilder
							.orgId(trxLine.getAD_Org_ID())
							.build();

					final int sourceVhuTopLevelHuId = huAccessService.retrieveTopLevelHuId(sourceVhu);
					Check.errorIf(sourceVhuTopLevelHuId <= 0, "sourceVhuTopLevelHuId returned by HUAccessService.retrieveTopLevelHuId has to be >0, but is {}; vhu={}", sourceVhuTopLevelHuId, sourceVhu);

					// create a trace record for the split's "source"
					final HUTraceEvent splitSourceEvent = traceEventBuilder
							.orgId(sourceTrxLine.getAD_Org_ID())
							.huTrxLineId(sourceTrxLine.getM_HU_Trx_Line_ID())
							.vhuId(sourceVhu.getM_HU_ID())
							.topLevelHuId(sourceVhuTopLevelHuId)
							.vhuSourceId(0)
							.qty(productAndQty.get().getRight().negate())
							.build();

					// add the source before the destination because I think it's nicer if it has the lower ID
					final boolean sourceEventWasInserted = huTraceRepository.addEvent(splitSourceEvent);
					result.get(sourceEventWasInserted).add(splitSourceEvent);

					final boolean eventWasInserted = huTraceRepository.addEvent(splitDestEvent);
					result.get(eventWasInserted).add(splitDestEvent);
				}
			}
		}
		return result;
	}

	/**
	 * Filters for the trx lines we actually want to create events from.
	 *
	 * @param trxLines
	 * @return
	 */
	@VisibleForTesting
	List<I_M_HU_Trx_Line> filterTrxLinesToUse(@NonNull final List<I_M_HU_Trx_Line> trxLines)
	{
		final List<I_M_HU_Trx_Line> trxLinesToUse = new ArrayList<>();
		for (final I_M_HU_Trx_Line trxLine : trxLines)
		{
			if (trxLine.getM_HU_ID() <= 0 && trxLine.getVHU_Item_ID() <= 0)
			{
				continue;
			}
			if (trxLine.getAD_Table_ID() > 0)
			{
				final String tableName = trxLine.getAD_Table().getTableName();
				if (TABLE_NAMES_IGNORED_FOR_TRANSFORMATION_TRACING.contains(tableName))
				{
					continue; // we only care for "standalone" HU-transactions. for the others, we have other means to trace them
				}
			}
			if (trxLine.getQty().signum() <= 0)
			{
				continue; // for values less than zero, we will get the respective record via its > zero pendant
			}
			if (trxLine.getParent_HU_Trx_Line_ID() <= 0)
			{
				continue;
			}
			trxLinesToUse.add(trxLine);
		}
		return trxLinesToUse;
	}

	/**
	 * Creates two trace records for the given {@code hu} which is moved from one parent to another parent (source or target parent might also be null).
	 *
	 * @param hu
	 * @param parentHUItemOld
	 */
	public void createAndAddForHuParentChanged(@NonNull final I_M_HU hu, final I_M_HU_Item parentHUItemOld)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		if (!handlingUnitsBL.isPhysicalHU(hu.getHUStatus()))
		{
			logger.info("Param hu has status={}; nothing to do; hu={}", hu.getHUStatus(), hu);
			return;
		}

		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.orgId(hu.getAD_Org_ID())
				.type(HUTraceType.TRANSFORM_PARENT)
				.eventTime(Instant.now());

		final List<I_M_HU> vhus = huAccessService.retrieveVhus(hu);
		final int newTopLevelHuId = huAccessService.retrieveTopLevelHuId(hu);

		final int oldTopLevelHuId;
		if (parentHUItemOld == null)
		{
			// the given 'hu' had no parent and was therefore our top level HU
			oldTopLevelHuId = hu.getM_HU_ID();
			Check.errorIf(oldTopLevelHuId <= 0, "oldTopLevelHuId returned by hu.getM_HU_ID() has to be >0, but is {}; hu={}", oldTopLevelHuId, hu);
		}
		else
		{
			oldTopLevelHuId = huAccessService.retrieveTopLevelHuId(parentHUItemOld.getM_HU());
			Check.errorIf(oldTopLevelHuId <= 0, "oldTopLevelHuId returned by HUAccessService.retrieveTopLevelHuId has to be >0, but is {}; parentHUItemOld={}", oldTopLevelHuId, parentHUItemOld);
		}

		for (final I_M_HU vhu : vhus)
		{
			final Optional<IPair<I_M_Product, BigDecimal>> productAndQty = huAccessService.retrieveProductAndQty(vhu);
			if (!productAndQty.isPresent())
			{
				logger.info("vhu has no product and quantity (yet), so skipping it; vhu={}", vhu);
				continue;
			}

			builder.vhuId(vhu.getM_HU_ID())
					.vhuStatus(vhu.getHUStatus())
					.productId(productAndQty.get().getLeft().getM_Product_ID())
					.topLevelHuId(oldTopLevelHuId)
					.qty(productAndQty.get().getRight().negate());
			huTraceRepository.addEvent(builder.build());

			builder.topLevelHuId(newTopLevelHuId)
					.qty(productAndQty.get().getRight());
			huTraceRepository.addEvent(builder.build());
		}
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
		for (final Object model : models)
		{
			final List<I_M_HU_Assignment> huAssignments = huAccessService.retrieveHuAssignments(model);

			for (final I_M_HU_Assignment huAssignment : huAssignments)
			{
				final int topLevelHuId = huAccessService.retrieveTopLevelHuId(huAssignment.getM_HU());
				Check.errorIf(topLevelHuId <= 0, "topLevelHuId returned by HUAccessService.retrieveTopLevelHuId has to be > 0, but is {}; huAssignment={}", topLevelHuId, huAssignment);

				builder.orgId(huAssignment.getAD_Org_ID())
						.eventTime(huAssignment.getUpdated().toInstant())
						.topLevelHuId(topLevelHuId);

				final List<I_M_HU> vhus;
				if (huAssignment.getVHU_ID() > 0)
				{
					vhus = ImmutableList.of(huAssignment.getVHU());
				}
				else if (huAssignment.getM_TU_HU_ID() > 0)
				{
					vhus = huAccessService.retrieveVhus(huAssignment.getM_TU_HU());
				}
				else if (huAssignment.getM_LU_HU_ID() > 0)
				{
					vhus = huAccessService.retrieveVhus(huAssignment.getM_LU_HU());
				}
				else
				{
					vhus = huAccessService.retrieveVhus(huAssignment.getM_HU());
				}

				for (final I_M_HU vhu : vhus)
				{
					builderSetVhuProductAndQty(builder, vhu)
							.vhuStatus(vhu.getHUStatus());

					huTraceRepository.addEvent(builder.build());
				}
			}
		}
	}

	private HUTraceEventBuilder builderSetVhuProductAndQty(
			@NonNull final HUTraceEventBuilder builder,
			@NonNull final I_M_HU vhu)
	{
		final Optional<IPair<I_M_Product, BigDecimal>> productAndQty = huAccessService.retrieveProductAndQty(vhu);
		Check.errorUnless(productAndQty.isPresent(), "Missing product and quantity for vhu={}", vhu);

		return builder
				.vhuId(vhu.getM_HU_ID())
				.productId(productAndQty.get().getLeft().getM_Product_ID())
				.qty(productAndQty.get().getRight());
	}
}
