package de.metas.handlingunits.trace;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.pair.IPair;
import de.metas.document.DocTypeId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

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

	private final transient HUTraceRepository huTraceRepository;
	private final transient HUAccessService huAccessService;

	private final transient InventoryRepository inventoryRepository;

	private final transient IInventoryBL inventoryBL = Services.get(IInventoryBL.class);

	private final transient IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private final IHUAttributesBL huAttributeService = Services.get(IHUAttributesBL.class);

	final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

	final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);

	public HUTraceEventsService(
			@NonNull final HUTraceRepository huTraceRepository,
			@NonNull final HUAccessService huAccessService,
			@NonNull final InventoryRepository inventoryRepository)
	{
		this.huAccessService = huAccessService;
		this.huTraceRepository = huTraceRepository;
		this.inventoryRepository = inventoryRepository;
	}

	/**
	 * Creates one event for the given cost collector and adds it to the {@link HUTraceRepository}.<br>
	 * The event has no source VHU ID, even in case of a 1:1 PP_Order.
	 *
	 * @param costCollector
	 */
	public void createAndAddFor(@NonNull final I_PP_Cost_Collector costCollector)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.ppCostCollectorId(costCollector.getPP_Cost_Collector_ID())
				.ppOrderId(costCollector.getPP_Order_ID())
				.docTypeId(DocTypeId.optionalOfRepoId(costCollector.getC_DocType_ID()))
				.docStatus(costCollector.getDocStatus())
				.eventTime(costCollector.getMovementDate().toInstant());

		final CostCollectorType costCollectorType = CostCollectorType.ofCode(costCollector.getCostCollectorType());
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(costCollector.getPP_Order_BOMLine_ID());
		if (costCollectorType.isAnyComponentIssueOrCoProduct(orderBOMLineId))
		{
			builder.type(HUTraceType.PRODUCTION_ISSUE);

			createAndAddEventsForPOIssueOrReceipt(builder, costCollector);
		}
		else
		{
			builder.type(HUTraceType.PRODUCTION_RECEIPT);

			createAndAddEventsForPOIssueOrReceipt(builder, costCollector);
		}
	}

	public void createAndAddFor(
			@NonNull final I_M_InOut inOut,
			@NonNull final List<I_M_InOutLine> iols)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.inOutId(inOut.getM_InOut_ID())
				.docTypeId(DocTypeId.optionalOfRepoId(inOut.getC_DocType_ID()))
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
				.docTypeId(DocTypeId.optionalOfRepoId(movement.getC_DocType_ID()))
				.docStatus(movement.getDocStatus())
				.type(HUTraceType.MATERIAL_MOVEMENT)
				.eventTime(movement.getMovementDate().toInstant());

		createAndAddEvents(builder, movementLines);
	}

	public void createAndAddFor(
			@NonNull final I_M_Inventory inventory,
			final List<I_M_InventoryLine> inventoryLines)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.inventoryId(InventoryId.ofRepoId(inventory.getM_Inventory_ID()))
				.docTypeId(DocTypeId.optionalOfRepoId(inventory.getC_DocType_ID()))
				.docStatus(inventory.getDocStatus())
				.type(HUTraceType.MATERIAL_INVENTORY)

				.orgId(OrgId.ofRepoIdOrNull(inventory.getAD_Org_ID()));

		createAndAddEventsForInventoryLines(builder, inventoryLines);
	}

	@VisibleForTesting
	void createAndAddEventsForInventoryLines(
			@NonNull final HUTraceEventBuilder builder,
			@NonNull final List<I_M_InventoryLine> inventoryLines)
	{
		for (final I_M_InventoryLine inventoryLineRecord : inventoryLines)
		{
			final InventoryLine inventoryLine = inventoryRepository.toInventoryLine(inventoryLineRecord);

			final ImmutableList<InventoryLineHU> inventoryLineHUs = inventoryLine.getInventoryLineHUs();

			for (final InventoryLineHU inventoryLineHU : inventoryLineHUs)
			{
				final HuId huId = inventoryLineHU.getHuId();
				final I_M_HU huRecord = handlingUnitsBL.getById(huId);

				final HuId topLevelHuId = HuId.ofRepoIdOrNull(huAccessService.retrieveTopLevelHuId(huRecord));
				if (topLevelHuId != null)
				{
					builder.topLevelHuId(topLevelHuId);
				}
				else
				{
					builder.topLevelHuId(huId); // HU was already unloaded from the parent, possibly destroyed. Consider it the top level HU
				}

				final boolean isQtyDifference = !inventoryLineRecord.getQtyBook().equals(inventoryLineRecord.getQtyCount());

				if (topLevelHuId == null || isQtyDifference)
				{
					createTraceForInventoryLine(builder, inventoryLineRecord, huRecord);
				}

				if (!isQtyDifference) // no need to trace vhus if the inventory was for qty difference
				{
					final List<I_M_HU> vhus = huAccessService.retrieveVhus(huId);

					for (final I_M_HU vhu : vhus)
					{
						createTraceForInventoryLine(builder, inventoryLineRecord, vhu);
					}
				}
			}
		}
	}

	private void createTraceForInventoryLine(
			@NonNull final HUTraceEventBuilder builder,
			@NonNull final I_M_InventoryLine inventoryLineRecord,
			@NonNull final I_M_HU hu)
	{
		final Optional<IPair<ProductId, Quantity>> productAndQty = huAccessService.retrieveProductAndQty(hu);
		if (!productAndQty.isPresent())
		{
			// skip such cases for now. To be handled in a followup
			return;
		}
		builderSetVhuProductAndQty(builder, hu)
				.vhuStatus(hu.getHUStatus());
		builder.eventTime(inventoryLineRecord.getUpdated().toInstant());

		final UomId uomId = UomId.ofRepoId(inventoryLineRecord.getC_UOM_ID());

		final BigDecimal qtyCountMinusBooked = inventoryLineRecord.getQtyCount()
				.subtract(inventoryLineRecord.getQtyBook());

		// shortage, overage
		if (!(qtyCountMinusBooked.signum() == 0))
		{
			builder.qty(Quantitys.of(qtyCountMinusBooked, uomId));
		}
		// disposal, new inventory with aggregated HUs
		else if (inventoryBL.isInternalUseInventory(inventoryLineRecord))
		{
			final BigDecimal qtyInternalUse = inventoryLineRecord.getQtyInternalUse();
			builder.qty(Quantitys.of(qtyInternalUse, uomId));
		}

		huTraceRepository.addEvent(builder.build());
	}

	public void createAndAddFor(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.orgId(OrgId.ofRepoId(shipmentScheduleQtyPicked.getAD_Org_ID()))
				.eventTime(shipmentScheduleQtyPicked.getUpdated().toInstant())
				.shipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(shipmentScheduleQtyPicked.getM_ShipmentSchedule_ID()))
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
		builder.topLevelHuId(HuId.ofRepoId(topLevelHuId));

		// the one or more VHU that are assigned
		final List<I_M_HU> vhus;
		if (shipmentScheduleQtyPicked.getVHU_ID() > 0)
		{

			final HuId vhuId = HuId.ofRepoId(shipmentScheduleQtyPicked.getVHU_ID());
			final I_M_HU vhu = handlingUnitsBL.getById(vhuId);
			vhus = ImmutableList.of(vhu);
		}
		else if (shipmentScheduleQtyPicked.getM_TU_HU_ID() > 0)
		{
			final HuId tuHUId = HuId.ofRepoId(shipmentScheduleQtyPicked.getM_TU_HU_ID());
			vhus = huAccessService.retrieveVhus(tuHUId);
		}
		else if (shipmentScheduleQtyPicked.getM_LU_HU_ID() > 0)
		{
			final HuId luHUId = HuId.ofRepoId(shipmentScheduleQtyPicked.getM_LU_HU_ID());
			vhus = huAccessService.retrieveVhus(luHUId);
		}
		else
		{
			Check.errorIf(true, "None of the three HU fields are set; can't happen because we already checked them all");
			return;
		}

		for (final I_M_HU vhu : vhus)
		{
			final Optional<IPair<ProductId, Quantity>> productAndQty = huAccessService.retrieveProductAndQty(vhu);
			if (!productAndQty.isPresent())
			{
				// skip such cases for now. To be handled in a followup
				continue;
			}

			builderSetVhuProductAndQty(builder, vhu)
					.vhuStatus(vhu.getHUStatus());

			huTraceRepository.addEvent(builder.build());
		}
	}

	public void createAndAddFor(
			@NonNull final HUTraceForReturnedQtyRequest returnedQtyRequest)
	{

		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.orgId(returnedQtyRequest.getOrgId())
				.eventTime(returnedQtyRequest.getEventTime())
				.docStatus(returnedQtyRequest.getDocStatus())
				.inOutId(returnedQtyRequest.getCustomerReturnId().getRepoId())
				.type(HUTraceType.MATERIAL_RECEIPT)
				.topLevelHuId(returnedQtyRequest.getTopLevelReturnedHUId())
				.vhuId(HuId.ofRepoId(returnedQtyRequest.getReturnedVirtualHU().getM_HU_ID()))
				.topLevelHuId(returnedQtyRequest.getTopLevelReturnedHUId())
				.qty(returnedQtyRequest.getQty())
				.productId(returnedQtyRequest.getProductId())
				.vhuStatus(returnedQtyRequest.getReturnedVirtualHU().getHUStatus());

		setSourceVHUAndAddEvent(returnedQtyRequest.getSourceShippedVHUIds(), builder);
	}

	private void setSourceVHUAndAddEvent(
			@NonNull final Set<HuId> sourceShippedVHUIds, @NonNull HUTraceEventBuilder huTraceEventBuilder)
	{
		sourceShippedVHUIds.stream()
				.map(huTraceEventBuilder::vhuSourceId)
				.map(HUTraceEventBuilder::build)
				.forEach(huTraceRepository::addEvent);
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
	 * @return a map with two lists that contains all events which were created and given to the repository.<br>
	 * The events that were actually inserted are in the list with the {@code true} key.
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
				final HuId huId = HuId.ofRepoId(huTrxLine.getM_HU_ID());
				return huAccessService.retrieveVhus(huId);
			}
			else
			{
				return ImmutableList.of();
			}
		};

		final Map<Boolean, List<HUTraceEvent>> result = new HashMap<>();
		result.put(true, new ArrayList<>());
		result.put(false, new ArrayList<>());

		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

		// iterate the lines and create an every per vhuId and sourceVhuId
		for (final I_M_HU_Trx_Line trxLine : trxLinesToUse)
		{
			traceEventBuilder.huTrxLineId(trxLine.getM_HU_Trx_Line_ID());

			final List<I_M_HU> vhus = getVhus.apply(trxLine);
			for (final I_M_HU vhu : vhus)
			{
				if (!huStatusBL.isPhysicalHU(vhu))
				{
					logger.info("vhu of the current trxLine has status={}; nothing to do with that trxLine; vhu={}; trxLine={}", vhu.getHUStatus(), vhu, trxLine);
					continue;
				}

				final Optional<IPair<ProductId, Quantity>> productAndQty = huAccessService.retrieveProductAndQty(vhu);
				if (!productAndQty.isPresent())
				{
					logger.info("vhu of the current trxLine has no product and quantity; nothing to do with that trxLine; vhu={}; trxLine={}", vhu, trxLine);
					continue;
				}

				final int vhuTopLevelHuId = huAccessService.retrieveTopLevelHuId(vhu);
				Check.errorIf(vhuTopLevelHuId <= 0, "vhuTopLevelHuId returned by HUAccessService.retrieveTopLevelHuId has to be >0, but is {}; vhu={}", vhuTopLevelHuId, vhu);

				final String lotNumberHUAttributeValue = huAttributeService.getHUAttributeValue(vhu, AttributeConstants.ATTR_LotNumber);

				traceEventBuilder
						.vhuId(HuId.ofRepoId(vhu.getM_HU_ID()))
						.topLevelHuId(HuId.ofRepoId(vhuTopLevelHuId))
						.productId(productAndQty.get().getLeft())
						.qty(productAndQty.get().getRight())
						.vhuStatus(trxLine.getHUStatus()) // we use the trx line's status here, because when creating traces for "old" HUs, the line's HUStatus is as it was at the time
						.lotNumber(lotNumberHUAttributeValue);

				final I_M_HU_Trx_Line sourceTrxLine = trxLine.getParent_HU_Trx_Line();
				final List<I_M_HU> sourceVhus = getVhus.apply(sourceTrxLine);
				for (final I_M_HU sourceVhu : sourceVhus)
				{
					if (!huStatusBL.isPhysicalHU(sourceVhu))
					{
						logger.info("sourceVhu of the current trxLine's sourceTrxLine (Parent_HU_Trx_Line) has status={}; nothing to do with that sourceVhu; sourceVhu={}; sourceTrxLine={}; trxLine={}",
									sourceVhu.getHUStatus(), sourceVhu, sourceTrxLine, trxLine);
						continue;
					}

					traceEventBuilder.vhuSourceId(HuId.ofRepoId(sourceVhu.getM_HU_ID()));

					if (sourceVhu.getM_HU_ID() == vhu.getM_HU_ID())
					{
						logger.info("sourceVhu of the current trxLine's sourceTrxLine (Parent_HU_Trx_Line) is the same as vhu of the current trxLine itself; nothing to do with that sourceVhu; vhu/sourceVhu={}; sourceTrxLine={}; trxLine={}",
									sourceVhu, sourceTrxLine, trxLine);
						continue; // the source-HU might be the same if e.g. only the status was changed
					}

					// create a trace record for the split's "destination"
					final HUTraceEvent splitDestEvent = traceEventBuilder
							.orgId(OrgId.ofRepoIdOrNull(trxLine.getAD_Org_ID()))
							.build();

					final int sourceVhuTopLevelHuId = huAccessService.retrieveTopLevelHuId(sourceVhu);
					Check.errorIf(sourceVhuTopLevelHuId <= 0, "sourceVhuTopLevelHuId returned by HUAccessService.retrieveTopLevelHuId has to be >0, but is {}; vhu={}", sourceVhuTopLevelHuId, sourceVhu);

					// create a trace record for the split's "source"
					final HUTraceEvent splitSourceEvent = traceEventBuilder
							.orgId(OrgId.ofRepoIdOrNull(sourceTrxLine.getAD_Org_ID()))
							.huTrxLineId(sourceTrxLine.getM_HU_Trx_Line_ID())
							.vhuId(HuId.ofRepoId(sourceVhu.getM_HU_ID()))
							.topLevelHuId(HuId.ofRepoId(sourceVhuTopLevelHuId))
							.vhuSourceId(null)
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
	List<I_M_HU_Trx_Line> filterTrxLinesToUse(
			@NonNull final List<I_M_HU_Trx_Line> trxLines)
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
				final String tableName = adTableDAO.retrieveTableName(trxLine.getAD_Table_ID());
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
	 */
	public void createAndAddForHuParentChanged(
			@NonNull final I_M_HU hu,
			@Nullable final I_M_HU_Item parentHUItemOld)
	{
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
		if (!huStatusBL.isPhysicalHU(hu))
		{
			logger.info("Param hu has status={}; nothing to do; hu={}", hu.getHUStatus(), hu);
			return;
		}

		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.orgId(OrgId.ofRepoIdOrNull(hu.getAD_Org_ID()))
				.type(HUTraceType.TRANSFORM_PARENT)
				.eventTime(Instant.now());

		final List<I_M_HU> vhus = huAccessService.retrieveVhus(hu);
		final HuId newTopLevelHuId = HuId.ofRepoIdOrNull(huAccessService.retrieveTopLevelHuId(hu));

		final HuId oldTopLevelHuId;
		if (parentHUItemOld == null)
		{
			// the given 'hu' had no parent and was therefore our top level HU
			oldTopLevelHuId = HuId.ofRepoIdOrNull(hu.getM_HU_ID());
			Check.errorIf(oldTopLevelHuId == null, "oldTopLevelHuId returned by hu.getM_HU_ID() has to be >0, but is {}; hu={}", oldTopLevelHuId, hu);
		}
		else
		{
			oldTopLevelHuId = HuId.ofRepoIdOrNull(huAccessService.retrieveTopLevelHuId(parentHUItemOld.getM_HU()));
			if (oldTopLevelHuId == null)
			{
				// this might happen if the HU is in the process of being destructed
				logger.info("parentHUItemOld={} has M_HU_ID={} whichout a top-levelHU; -> nothing to do", parentHUItemOld, parentHUItemOld.getM_HU_ID());
				return;
			}
		}

		for (final I_M_HU vhu : vhus)
		{
			final Optional<IPair<ProductId, Quantity>> productAndQty = huAccessService.retrieveProductAndQty(vhu);

			if (!productAndQty.isPresent())
			{
				logger.info("vhu has no product and quantity (yet), so skipping it; vhu={}", vhu);
				continue;
			}

			final String lotNumberHUAttributeValue = huAttributeService.getHUAttributeValue(vhu, AttributeConstants.ATTR_LotNumber);

			builder.vhuId(HuId.ofRepoId(vhu.getM_HU_ID()))
					.vhuStatus(vhu.getHUStatus())
					.productId(productAndQty.get().getLeft())
					.topLevelHuId(oldTopLevelHuId)
					.qty(productAndQty.get().getRight().negate())
					.lotNumber(lotNumberHUAttributeValue);
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
	 * @param models  stream of objects that might be associated with HUs (e.g. inout lines).
	 */
	@VisibleForTesting
	void createAndAddEvents(
			@NonNull final HUTraceEventBuilder builder,
			@NonNull final List<?> models)
	{
		for (final Object model : models)
		{
			final List<I_M_HU_Assignment> huAssignments = huAccessService.retrieveHuAssignments(model);

			for (final I_M_HU_Assignment huAssignment : huAssignments)
			{
				final HuId topLevelHuId = HuId.ofRepoIdOrNull(huAccessService.retrieveTopLevelHuId(huAssignment.getM_HU()));
				if (topLevelHuId != null)
				{
					builder.topLevelHuId(topLevelHuId);
				}
				else
				{
					builder.topLevelHuId(HuId.ofRepoId(huAssignment.getM_HU_ID()));
				}
				builder.orgId(OrgId.ofRepoIdOrNull(huAssignment.getAD_Org_ID()))
						.eventTime(huAssignment.getUpdated().toInstant());

				final List<I_M_HU> vhus;
				if (huAssignment.getVHU_ID() > 0)
				{
					final HuId vhuId = HuId.ofRepoId(huAssignment.getVHU_ID());
					final I_M_HU vhu = handlingUnitsBL.getById(vhuId);
					vhus = ImmutableList.of(vhu);
				}
				else if (huAssignment.getM_TU_HU_ID() > 0)
				{
					final HuId tuHUId = HuId.ofRepoId(huAssignment.getM_TU_HU_ID());
					vhus = huAccessService.retrieveVhus(tuHUId);
				}
				else if (huAssignment.getM_LU_HU_ID() > 0)
				{
					final HuId luHUId = HuId.ofRepoId(huAssignment.getM_LU_HU_ID());
					vhus = huAccessService.retrieveVhus(luHUId);
				}
				else
				{
					final HuId huId = HuId.ofRepoId(huAssignment.getM_HU_ID());
					vhus = huAccessService.retrieveVhus(huId);
				}

				for (final I_M_HU vhu : vhus)
				{
					final Optional<IPair<ProductId, Quantity>> productAndQty = huAccessService.retrieveProductAndQty(vhu);
					if (!productAndQty.isPresent())
					{
						// skip such cases for now. To be handled in a followup
						continue;
					}

					builderSetVhuProductAndQty(builder, vhu)
							.vhuStatus(vhu.getHUStatus());

					huTraceRepository.addEvent(builder.build());
				}
			}
		}
	}

	@VisibleForTesting
	void createAndAddEventsForPOIssueOrReceipt(
			@NonNull final HUTraceEventBuilder builder,
			@NonNull final I_PP_Cost_Collector ppCostCollector)
	{
		final List<I_M_HU_Assignment> huAssignments = huAccessService.retrieveHuAssignments(ppCostCollector);

		for (final I_M_HU_Assignment huAssignment : huAssignments)
		{
			final HuId huId = HuId.ofRepoId(huAssignment.getM_HU_ID());
			final I_M_HU huRecord = handlingUnitsBL.getById(huId);

			builder.orgId(OrgId.ofRepoIdOrNull(ppCostCollector.getAD_Org_ID()))
					.eventTime(ppCostCollector.getUpdated().toInstant());

			final HuId topLevelHuId = HuId.ofRepoIdOrNull(huAccessService.retrieveTopLevelHuId(huRecord));
			if (topLevelHuId != null)
			{
				builder.topLevelHuId(topLevelHuId);
			}
			else
			{
				builder.topLevelHuId(huId);
				createTraceForPOIssueOrReceiptHU(builder, ppCostCollector, huRecord);
			}

			final List<I_M_HU> vhus = huAccessService.retrieveVhus(huId);

			for (final I_M_HU vhu : vhus)
			{
				createTraceForPOIssueOrReceiptHU(builder, ppCostCollector, vhu);
			}
		}
	}

	private void createTraceForPOIssueOrReceiptHU(final @NonNull HUTraceEventBuilder builder, @NonNull final I_PP_Cost_Collector ppCostCollector,
			@NonNull final I_M_HU hu)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppCostCollector.getPP_Order_ID());

		final Optional<I_PP_Order_Qty> ppOrderQty = huPPOrderQtyDAO.retrieveOrderQtyForHu(
				ppOrderId,
				HuId.ofRepoId(hu.getM_HU_ID()));

		if (!ppOrderQty.isPresent())
		{
			createAndAddEvents(builder, ImmutableList.of(ppCostCollector));

		}
		else
		{

			final Quantity qtyToSet = Quantitys.of(ppOrderQty.get().getQty(), UomId.ofRepoId(ppOrderQty.get().getC_UOM_ID()));

			final Optional<IPair<ProductId, Quantity>> productAndQty = huAccessService.retrieveProductAndQty(hu);
			if (!productAndQty.isPresent())
			{
				// skip such cases for now. To be handled in a followup
				return;
			}

			builderSetVhuProductAndQty(builder, hu)
					.vhuStatus(hu.getHUStatus())
					.qty(qtyToSet);

			huTraceRepository.addEvent(builder.build());
		}
	}

	private HUTraceEventBuilder builderSetVhuProductAndQty(
			@NonNull final HUTraceEventBuilder builder,
			@NonNull final I_M_HU vhu)
	{
		final Optional<IPair<ProductId, Quantity>> productAndQty = huAccessService.retrieveProductAndQty(vhu);
		Check.errorUnless(productAndQty.isPresent(), "Missing product and quantity for vhu={}", vhu);

		final String lotNumberHUAttributeValue = huAttributeService.getHUAttributeValue(vhu, AttributeConstants.ATTR_LotNumber);

		return builder
				.vhuId(HuId.ofRepoId(vhu.getM_HU_ID()))
				.productId(productAndQty.get().getLeft())
				.qty(productAndQty.get().getRight())
				.lotNumber(lotNumberHUAttributeValue);
	}
}
