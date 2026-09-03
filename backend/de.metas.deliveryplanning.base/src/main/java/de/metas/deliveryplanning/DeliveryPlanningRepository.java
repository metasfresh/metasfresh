/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.dimension.DimensionService;
import de.metas.i18n.AdMessageKey;
import de.metas.incoterms.IncotermsId;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.location.CountryId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.ShipperId;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.ColorId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_M_Delivery_Planning;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Repository Tables: M_Delivery_Planning
 * Repository Cluster: DeliveryPlanningRepository (primary owner of M_Delivery_Planning, which
 * DeliveryPlanningImportProcess also writes directly), DeliveryPlanningAllocRepository
 * (M_Delivery_Planning_Alloc), DeliveryInstructionRepository (M_ShipperTransportation and its M_ShippingPackage
 * lines), MPackageRepository (M_Package). Anything spanning more than one of those is composed by
 * {@link DeliveryInstructionService}, never by a repository calling another repository.
 * <p>
 * Injected collaborators: {@link DimensionService} (a dimension is copied from the source row onto the target row
 * as that row is written - persistence rather than a delivery-planning decision), plus {@code IInOutBL} and
 * {@code IUOMConversionBL} (via {@code Services.get}), used to resolve the booked quantity a completed receipt or
 * shipment writes onto the planning and to branch that write by {@link TransportDirection} / {@code IsClosed}
 * (Task Q11 - {@code recordActualQtyOnComplete} / {@code clearActualQtyOnReverse}).
 */
@Repository
public class DeliveryPlanningRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/** Task Q11: resolving the booked quantity a completed receipt or shipment writes onto the planning. */
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final DimensionService dimensionService;

	public DeliveryPlanningRepository(@NonNull final DimensionService dimensionService)
	{
		this.dimensionService = dimensionService;
	}

	protected I_M_Delivery_Planning getById(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return load(deliveryPlanningId, I_M_Delivery_Planning.class);
	}

	/**
	 * The records of the given delivery plannings, in ONE round trip, in the caller's id order.
	 * Unfiltered by {@code IsActive}: a selection can legitimately name a closed planning.
	 *
	 * @throws AdempiereException for an id with no matching row - a dangling reference, not a row to drop silently.
	 */
	protected ImmutableList<I_M_Delivery_Planning> getByIds(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableMap<DeliveryPlanningId, I_M_Delivery_Planning> recordsById = queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addInArrayFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningIds)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()),
						Function.identity()));

		return deliveryPlanningIds.stream()
				.map(deliveryPlanningId -> getOrThrow(recordsById, deliveryPlanningId))
				.collect(ImmutableList.toImmutableList());
	}

	private static I_M_Delivery_Planning getOrThrow(
			@NonNull final Map<DeliveryPlanningId, I_M_Delivery_Planning> recordsById,
			@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		// the map was just loaded from a query over these very ids, so a miss is a programmer error
		return Check.assumeNotNull(recordsById.get(deliveryPlanningId),
				"No {} found for {}={}", I_M_Delivery_Planning.Table_Name,
				I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId.getRepoId());
	}

	public List<I_M_Delivery_Planning> getByReleaseNo(@NonNull final String releaseNo)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo, releaseNo)
				.create()
				.list();
	}

	protected List<DeliveryPlanningShipmentInfo> getShipmentInfosByOrderLineIds(@NonNull final Set<OrderAndLineId> salesOrderAndLineId)
	{
		if (salesOrderAndLineId.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<OrderLineId> salesOrderLineIds = salesOrderAndLineId.stream().map(OrderAndLineId::getOrderLineId).collect(ImmutableSet.toImmutableSet());

		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID, salesOrderLineIds)
				.stream()
				.map(DeliveryPlanningRepository::toDeliveryPlanningShipmentInfo)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	static TransportDirection extractTransportDirection(final I_M_Delivery_Planning record)
	{
		return TransportDirection.ofCode(record.getTransportDirection());
	}

	@NonNull
	private static TransportDirection assertHasReceipt(final I_M_Delivery_Planning record)
	{
		final TransportDirection transportDirection = extractTransportDirection(record);
		if (!transportDirection.hasReceipt())
		{
			throw new AdempiereException("Expected the delivery planning to have a receipt: " + record);
		}
		return transportDirection;
	}

	private static void assertHasOwnShipment(final I_M_Delivery_Planning record)
	{
		final TransportDirection transportDirection = extractTransportDirection(record);
		if (!hasOwnShipment(transportDirection))
		{
			throw new AdempiereException("Expected the delivery planning to have its own shipment: " + record);
		}
	}

	/**
	 * A {@link TransportDirection#Dropship} planning does have a shipment, but it is carried by the paired
	 * sales-side planning, so this record's own shipment schedule and movement are not set.
	 */
	static boolean hasOwnShipment(@NonNull final TransportDirection transportDirection)
	{
		return transportDirection.isOutgoing();
	}

	public Optional<DeliveryPlanningReceiptInfo> getReceiptInfoIfHasReceipt(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning record = getById(deliveryPlanningId);
		final TransportDirection transportDirection = extractTransportDirection(record);
		return transportDirection.hasReceipt()
				? Optional.of(toDeliveryPlanningReceiptInfo(record))
				: Optional.empty();
	}

	@NonNull
	private static DeliveryPlanningReceiptInfo toDeliveryPlanningReceiptInfo(final I_M_Delivery_Planning record)
	{
		final TransportDirection transportDirection = assertHasReceipt(record);
		return DeliveryPlanningReceiptInfo.builder()
				.deliveryPlanningId(DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()))
				.purchaseOrderAndLineId(OrderAndLineId.ofRepoIdsOrNull(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.receiptScheduleId(ReceiptScheduleId.ofRepoId(record.getM_ReceiptSchedule_ID()))
				.dropship(transportDirection.isDropship())
				//
				.receiptId(InOutId.ofRepoIdOrNull(record.getM_InOut_ID()))
				.receivedStatusColorId(ColorId.ofRepoIdOrNull(record.getDeliveryStatus_Color_ID()))
				//
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.build();
	}

	private static void updateRecordFromReceiptInfo(final I_M_Delivery_Planning record, final DeliveryPlanningReceiptInfo from)
	{
		assertHasReceipt(record);
		record.setM_InOut_ID(InOutId.toRepoId(from.getReceiptId()));
		record.setDeliveryStatus_Color_ID(ColorId.toRepoId(from.getReceivedStatusColorId()));
	}

	public void updateReceiptInfoById(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Consumer<DeliveryPlanningReceiptInfo> updater)
	{
		final I_M_Delivery_Planning record = getById(deliveryPlanningId);
		final DeliveryPlanningReceiptInfo receiptInfo = toDeliveryPlanningReceiptInfo(record);
		updater.accept(receiptInfo);
		updateRecordFromReceiptInfo(record, receiptInfo);
		InterfaceWrapperHelper.save(record);
	}

	public Optional<DeliveryPlanningShipmentInfo> getShipmentInfoIfOutgoingType(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning record = getById(deliveryPlanningId);
		final TransportDirection transportDirection = extractTransportDirection(record);
		return hasOwnShipment(transportDirection)
				? Optional.of(toDeliveryPlanningShipmentInfo(record))
				: Optional.empty();
	}

	private static DeliveryPlanningShipmentInfo toDeliveryPlanningShipmentInfo(final I_M_Delivery_Planning record)
	{
		assertHasOwnShipment(record);
		return DeliveryPlanningShipmentInfo.builder()
				.deliveryPlanningId(DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIdsOrNull(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.customerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				//
				.shipmentId(InOutId.ofRepoIdOrNull(record.getM_InOut_ID()))
				.shippedStatusColorId(ColorId.ofRepoIdOrNull(record.getDeliveryStatus_Color_ID()))
				//
				.build();
	}

	private static void updateRecordFromShipmentInfo(final I_M_Delivery_Planning record, final DeliveryPlanningShipmentInfo from)
	{
		assertHasOwnShipment(record);
		record.setM_InOut_ID(InOutId.toRepoId(from.getShipmentId()));
		record.setDeliveryStatus_Color_ID(ColorId.toRepoId(from.getShippedStatusColorId()));
	}

	public void updateShipmentInfoById(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Consumer<DeliveryPlanningShipmentInfo> updater)
	{
		final I_M_Delivery_Planning record = getById(deliveryPlanningId);
		final DeliveryPlanningShipmentInfo shipmentInfo = toDeliveryPlanningShipmentInfo(record);
		updater.accept(shipmentInfo);
		updateRecordFromShipmentInfo(record, shipmentInfo);
		InterfaceWrapperHelper.save(record);
	}

	/**
	 * Task Q11: writes the actual quantity onto the end(s) THIS receipt or shipment occupies (the plan's
	 * write-by-the-END table), and marks the planning {@code Processed} - it is now delivered.
	 * <p>
	 * A shipment is the only document a strictly {@link TransportDirection#Outgoing} planning ever gets, so
	 * nobody else ever reports the customer's unload: completion books the SAME booked quantity onto BOTH
	 * ends (the "arrives as shipped unless told otherwise" assumption) - {@link #hasOwnShipment} is exactly
	 * that condition.
	 * <p>
	 * A receipt ALWAYS writes discharge, {@link TransportDirection#Dropship} included: today a Dropship
	 * planning is created and driven exactly like {@link TransportDirection#Incoming} (only
	 * {@code GenerateIncomingDeliveryPlanningCommand} creates it, seeding {@code ActualLoadQty} from the
	 * planned load the same way, and {@code PoolEnd.forDirection} groups it with Incoming) - it IS the
	 * purchase leg until the consolidated planning lands. {@code ActualLoadQty} is Task Q7c's derived
	 * placeholder for the never-reported vendor load and must never be touched by a receipt's completion,
	 * for either direction.
	 * <p>
	 * A shipment on a {@link TransportDirection#Dropship} planning is unreachable today - no generate process
	 * ever creates a Dropship planning's OWN shipment ({@code GenerateOutgoingDeliveryPlanningCommand} hardcodes
	 * {@link TransportDirection#Outgoing}) - so the branch below refuses rather than guess which end such a
	 * shipment would occupy; see its own comment.
	 *
	 * @param isReceipt {@code true} for a receipt (a purchase-side {@code M_InOut}), {@code false} for a shipment
	 */
	public void recordActualQtyOnComplete(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			final boolean isReceipt,
			@NonNull final I_M_InOut inout)
	{
		final I_M_Delivery_Planning record = getById(deliveryPlanningId);
		final TransportDirection direction = extractTransportDirection(record);
		final BigDecimal bookedQty = resolveBookedQty(inout, record).toBigDecimal();

		if (isReceipt)
		{
			record.setActualDischargeQuantity(bookedQty);
		}
		else if (hasOwnShipment(direction))
		{
			record.setActualLoadQty(bookedQty);
			record.setActualDischargeQuantity(bookedQty);
		}
		else
		{
			// Dropship shipment: unreachable today (see the Javadoc above), and not safe to guess at - a receipt
			// owns this planning's discharge end, so a shipment writing to it here would silently overwrite the
			// wrong end. Pending the consolidated-planning design that would give a Dropship planning its own
			// shipment; refuse rather than repeat that bug.
			throw new AdempiereException("Dropship planning with its own shipment is not supported yet: " + record);
		}

		if (!record.isProcessed())
		{
			record.setProcessed(true);
		}

		save(record);
	}

	/**
	 * The reversal mirror of {@link #recordActualQtyOnComplete}: clears every end completion wrote back to
	 * empty, and clears {@code Processed} unless the planning is closed - the mirror of ReOpen's rule
	 * (Task Q10), so the invariant {@code Processed == (IsClosed || IsDelivered)} keeps holding here too.
	 * Without this, a reversed receipt/shipment would leave the planning permanently {@code Processed} with
	 * no route back except Close-then-ReOpen. Direction handling mirrors {@link #recordActualQtyOnComplete}
	 * exactly - see its Javadoc for why a receipt always clears discharge, Dropship included.
	 *
	 * @param isReceipt {@code true} for a receipt, {@code false} for a shipment - same meaning as {@link
	 * 		#recordActualQtyOnComplete}
	 */
	public void clearActualQtyOnReverse(@NonNull final DeliveryPlanningId deliveryPlanningId, final boolean isReceipt)
	{
		final I_M_Delivery_Planning record = getById(deliveryPlanningId);
		final TransportDirection direction = extractTransportDirection(record);

		if (isReceipt)
		{
			record.setActualDischargeQuantity(BigDecimal.ZERO);
		}
		else if (hasOwnShipment(direction))
		{
			record.setActualLoadQty(BigDecimal.ZERO);
			record.setActualDischargeQuantity(BigDecimal.ZERO);
		}
		else
		{
			// Dropship shipment: unreachable today, mirrors recordActualQtyOnComplete's refusal - see its comment.
			throw new AdempiereException("Dropship planning with its own shipment is not supported yet: " + record);
		}

		if (!record.isClosed())
		{
			record.setProcessed(false);
		}

		save(record);
	}

	/**
	 * The lines of the receipt or shipment that belong to THIS planning, summed into the planning's UOM - what
	 * {@link #recordActualQtyOnComplete} writes onto the end(s) it occupies. {@link IInOutBL#getMovementQty}
	 * (not the raw column) keeps a return's negated sign.
	 * <p>
	 * <b>The document is NOT the planning.</b> A shipment schedule whose partner allows consolidation
	 * ({@code C_BPartner.AllowConsolidateInOut}, the default) is put onto an already-drafted shipment of the same
	 * org / partner / partner-location / warehouse / consolidation period rather than onto a fresh one - see
	 * {@code InOutProducerFromShipmentScheduleWithHU#getCreateShipmentHeader}. Summing the whole document would
	 * then book the OTHER schedules' lines onto this planning as its own actual quantity (and drive
	 * {@code QtyTotalOpen} negative). So the sum is scoped, by the attribution and under the assumption spelled
	 * out below.
	 * <p>
	 * <b>Scoped by {@code C_OrderLine_ID}</b>, because that is the only attribution a line carries at the moment
	 * this runs. Both producers copy their schedule's order line onto every line they create, before the document
	 * is completed ({@code ShipmentLineBuilder#createShipmentLine}, {@code InOutProducer#updateReceiptLine}).
	 * The schedule-to-line allocation tables ({@code M_ShipmentSchedule_QtyPicked.M_InOutLine_ID}, written by
	 * {@code ShipmentScheduleWithHU#setM_InOut}) are NOT usable here: they are written AFTER
	 * {@code processEx(ACTION_Complete)}, i.e. after this {@code TIMING_AFTER_COMPLETE} handler has already run,
	 * so they would still be empty and every planning would book zero.
	 * <p>
	 * <b>The guarantee is therefore "this planning's ORDER LINE", not "this planning"</b> - a schedule is 1:1
	 * with its ORDER LINE, not with the planning, so two plannings SPLIT from one order line share both. What
	 * makes the order line nevertheless yield this planning's own share is that <b>a delivery-planning document
	 * carries exactly one line of that order line</b>: each generate run completes its own document before it
	 * returns ({@code DeliveryPlanningGenerateProcessesHelper#generateShipment} passes
	 * {@code isCompleteShipment=TRUE} with {@code waitForShipments=true}; {@code generateReceipt} goes through
	 * {@code processReceiptSchedules}), and consolidation only ever joins a line onto a header that is still
	 * {@code DocStatus='DR'} ({@code HUShipmentScheduleBL#getOpenShipmentOrNull}) - receipts never consolidate at
	 * all ({@code InOutProducer#newChunk} always creates a fresh header). So a split sibling's document is never
	 * a consolidation target for the next sibling's, and the quantity on the one line found here is the
	 * caller-supplied {@code Qty} of THIS planning's own generate run. Pinned by the cucumber scenario
	 * {@code S31789_TC_Q11_SplitSiblingsBookOnlyTheirOwnShare}.
	 * <p>
	 * Residual, and deliberately not guessed at: a shipment DRAFTED outside delivery planning on the same order
	 * line, still open when a planning's generate run consolidates onto it, would be summed in here. Nothing on
	 * a line distinguishes it at this timing (see the allocation tables above), so there is no attribution that
	 * would separate it; widening the scope back to the whole document would be strictly worse.
	 * <p>
	 * A planning without an order line cannot be scoped this way; it also cannot reach a generate process
	 * ({@code DeliveryPlanningGenerateProcessesHelper#checkEligibleToCreateReceipt}/{@code ...Shipment} reject a
	 * planning that is not order based), so such a document was stamped by hand and is not a consolidation
	 * target - the unscoped sum stays the best available answer there rather than a throw in the middle of a
	 * document's completion.
	 */
	private Quantity resolveBookedQty(@NonNull final I_M_InOut inout, @NonNull final I_M_Delivery_Planning planningRecord)
	{
		final ProductId productId = ProductId.ofRepoId(planningRecord.getM_Product_ID());
		final I_C_UOM uom = uomDAO.getById(planningRecord.getC_UOM_ID());
		final OrderLineId planningOrderLineId = OrderLineId.ofRepoIdOrNull(planningRecord.getC_OrderLine_ID());

		return inOutDAO.retrieveLines(inout).stream()
				.filter(line -> line.getM_Product_ID() == productId.getRepoId())
				.filter(line -> belongsToPlanning(line, planningOrderLineId))
				.map(inOutBL::getMovementQty)
				.map(qty -> uomConversionBL.convertQuantityTo(qty, productId, uom))
				.reduce(Quantity.zero(uom), Quantity::add);
	}

	/** @see #resolveBookedQty for why the order line is the attribution used here, and why a planning without one is not scoped. */
	private static boolean belongsToPlanning(@NonNull final I_M_InOutLine line, @Nullable final OrderLineId planningOrderLineId)
	{
		return planningOrderLineId == null || line.getC_OrderLine_ID() == planningOrderLineId.getRepoId();
	}

	public <T> T getShipmentOrReceiptInfo(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Function<DeliveryPlanningReceiptInfo, T> receiptInfoMapper,
			@NonNull final Function<DeliveryPlanningShipmentInfo, T> shipmentInfoMapper)
	{
		final I_M_Delivery_Planning record = getById(deliveryPlanningId);
		final TransportDirection transportDirection = extractTransportDirection(record);
		if (transportDirection.hasReceipt())
		{
			return receiptInfoMapper.apply(toDeliveryPlanningReceiptInfo(record));
		}
		else if (transportDirection.hasShipment())
		{
			return shipmentInfoMapper.apply(toDeliveryPlanningShipmentInfo(record));
		}
		else
		{
			throw new AdempiereException("Unknown type: " + transportDirection);
		}
	}

	public void generateDeliveryPlanning(@NonNull final DeliveryPlanningCreateRequest request)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = newInstance(I_M_Delivery_Planning.class);

		deliveryPlanningRecord.setAD_Org_ID(request.getOrgId().getRepoId());
		deliveryPlanningRecord.setM_ReceiptSchedule_ID(ReceiptScheduleId.toRepoId(request.getReceiptScheduleId()));
		deliveryPlanningRecord.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(request.getShipmentScheduleId()));
		deliveryPlanningRecord.setDeliveryStatus_Color_ID(ColorId.toRepoId(request.getDeliveryStatusColorId()));
		deliveryPlanningRecord.setC_Order_ID(OrderId.toRepoId(request.getOrderId()));
		deliveryPlanningRecord.setC_OrderLine_ID(OrderLineId.toRepoId(request.getOrderLineId()));
		deliveryPlanningRecord.setM_Product_ID(ProductId.toRepoId(request.getProductId()));
		deliveryPlanningRecord.setM_Warehouse_ID(WarehouseId.toRepoId(request.getWarehouseId()));
		deliveryPlanningRecord.setC_BPartner_ID(BPartnerId.toRepoId(request.getPartnerId()));
		deliveryPlanningRecord.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(request.getBPartnerLocationId()));
		deliveryPlanningRecord.setC_Incoterms_ID(IncotermsId.toRepoId(request.getIncotermsId()));
		deliveryPlanningRecord.setIncotermLocation(request.getIncotermLocation());

		deliveryPlanningRecord.setETA(TimeUtil.asTimestamp(request.getPlannedDeliveryDate()));
		deliveryPlanningRecord.setATA(TimeUtil.asTimestamp(request.getActualDeliveryDate()));
		deliveryPlanningRecord.setETD(TimeUtil.asTimestamp(request.getPlannedLoadingDate()));
		deliveryPlanningRecord.setATD(TimeUtil.asTimestamp(request.getActualLoadingDate()));

		deliveryPlanningRecord.setLoadingTime(request.getLoadingTime());
		deliveryPlanningRecord.setDeliveryTime(request.getDeliveryTime());

		final Quantity qtyOrdered = request.getQtyOrdered();
		final Quantity qtyTotalOpen = request.getQtyTotalOpen();
		final Quantity actualLoadedQty = request.getActualLoadedQty();

		final Quantity plannedLoadedQty = request.getPlannedLoadedQty();
		final Quantity plannedDischargeQty = request.getPlannedDischargeQty();
		final Quantity actualDischargeQty = request.getActualDischargeQty();

		deliveryPlanningRecord.setC_UOM_ID(request.getUom().getC_UOM_ID());

		deliveryPlanningRecord.setQtyOrdered(qtyOrdered.toBigDecimal());
		deliveryPlanningRecord.setQtyTotalOpen(qtyTotalOpen.toBigDecimal());
		deliveryPlanningRecord.setActualLoadQty(actualLoadedQty.toBigDecimal());

		deliveryPlanningRecord.setPlannedLoadedQuantity(plannedLoadedQty.toBigDecimal());
		deliveryPlanningRecord.setPlannedDischargeQuantity(plannedDischargeQty.toBigDecimal());
		deliveryPlanningRecord.setActualDischargeQuantity(actualDischargeQty.toBigDecimal());

		deliveryPlanningRecord.setM_Shipper_ID(ShipperId.toRepoId(request.getShipperId()));

		deliveryPlanningRecord.setWayBillNo(request.getWayBillNo());
		deliveryPlanningRecord.setReleaseNo(request.getReleaseNo());
		deliveryPlanningRecord.setTransportDetails(request.getTransportDetails());

		deliveryPlanningRecord.setM_MeansOfTransportation_ID(MeansOfTransportationId.toRepoId(request.getMeansOfTransportationId()));
		deliveryPlanningRecord.setOrderStatus(OrderStatus.toCodeOrNull(request.getOrderStatus()));
		deliveryPlanningRecord.setTransportDirection(request.getTransportDirection().getCode());

		deliveryPlanningRecord.setBatch(request.getBatch());
		deliveryPlanningRecord.setC_OriginCountry_ID(CountryId.toRepoId(request.getOriginCountryId()));
		deliveryPlanningRecord.setC_DestinationCountry_ID(CountryId.toRepoId(request.getDestinationCountryId()));

		dimensionService.updateRecord(deliveryPlanningRecord, request.getDimension());

		save(deliveryPlanningRecord);
	}

	public boolean isOtherDeliveryPlanningsExistForOrderLine(@NonNull final OrderLineId orderLineId, @NonNull final DeliveryPlanningId excludeDeliveryPlanningId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.addNotEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, excludeDeliveryPlanningId)
				.create()
				.anyMatch();
	}

	public void deleteForReceiptSchedule(@NonNull final ReceiptScheduleId receiptScheduleId)
	{
		queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_ReceiptSchedule_ID, receiptScheduleId)
				.create()
				.delete();
	}

	public void deleteForShipmentSchedule(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.delete();
	}

	public Stream<I_M_Delivery_Planning> retrieveForOrderLine(@NonNull final OrderLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.create()
				.stream();
	}

	/**
	 * Every planning of the given order line, as the in-memory value objects
	 * {@link de.metas.deliveryplanning.DeliveryPlanningList#openPlanQty} is answered against - unlike
	 * {@link #retrieveForOrderLine}, which returns records, not value objects.
	 * <p>
	 * Carries only the quantity fields the pool needs ({@code id}, {@code qtyOrdered} and the two load/discharge
	 * pairs) plus the {@code id}/{@code orgId}/{@code transportDirection} the shared {@link DeliveryPlanning}
	 * value object requires - no addresses, no allocations, unlike {@link DeliveryPlanningService}'s own mapper,
	 * which this deliberately does not reuse (that one batch-loads addresses this caller never needs).
	 */
	public DeliveryPlanningList getByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return retrieveForOrderLine(orderLineId)
				.map(DeliveryPlanningRepository::toPoolPlanning)
				.collect(DeliveryPlanningList.collect());
	}

	private static DeliveryPlanning toPoolPlanning(@NonNull final I_M_Delivery_Planning record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		return DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.transportDirection(extractTransportDirection(record))
				.qtyOrdered(Quantitys.of(record.getQtyOrdered(), uomId))
				.plannedLoadedQty(Quantitys.of(record.getPlannedLoadedQuantity(), uomId))
				.actualLoadedQty(Quantitys.of(record.getActualLoadQty(), uomId))
				.plannedDischargeQty(Quantitys.of(record.getPlannedDischargeQuantity(), uomId))
				.actualDischargeQty(Quantitys.of(record.getActualDischargeQuantity(), uomId))
				.build();
	}

	/**
	 * The write-point every path that changes a planning's planned/actual figures, or adds a planning to an
	 * order line, owes (Task Q8): recomputes {@code QtyTotalOpen} ({@code QtyOrdered - actual}, summed over every
	 * planning of the line) and {@code QtyTotalOpenPlanned} ({@code QtyOrdered - planned}, summed the same way)
	 * and writes both onto EVERY planning of the line - they are order-line totals redundantly displayed on each
	 * row, not a per-row figure, so a planning created or edited elsewhere on the line must move every sibling's
	 * copy too, not just its own.
	 * <p>
	 * Not floored at zero (unlike {@link #getByOrderLineId}'s pool use in the split): these are DISPLAY columns,
	 * and a negative one is the over-planned/over-delivered signal D16 calls for, not an error to hide.
	 */
	public void recomputeOpenQuantitiesForOrderLine(@NonNull final OrderLineId orderLineId)
	{
		final ImmutableList<I_M_Delivery_Planning> records = retrieveForOrderLine(orderLineId).collect(ImmutableList.toImmutableList());
		if (records.isEmpty())
		{
			return;
		}

		final DeliveryPlanningList plannings = records.stream().map(DeliveryPlanningRepository::toPoolPlanning).collect(DeliveryPlanningList.collect());
		// What this computation needs is a single POOL END, not a single TransportDirection: Incoming and
		// Dropship are different directions that both net DISCHARGE, so a line mixing them is still perfectly
		// computable and must not be rejected - this runs from M_Delivery_Planning's AFTER_NEW/AFTER_CHANGE
		// interceptors, so a throw here would make every planning on such a line unsavable. retrieveForOrderLine
		// has no ORDER BY, so asserting the end is single-valued - rather than reading records.get(0) from an
		// unordered query - is what actually pins the invariant, not a query-ordering fix.
		final DeliveryPlanningList.PoolEnd end = Check.assumePresent(plannings.getSinglePoolEnd(),
				"Expected every M_Delivery_Planning of orderLineId={} to net one PoolEnd: {}", orderLineId, plannings);

		final BigDecimal qtyTotalOpen = plannings.qtyTotalOpen(end).toBigDecimal();
		final BigDecimal qtyTotalOpenPlanned = plannings.qtyTotalOpenPlanned(end).toBigDecimal();

		for (final I_M_Delivery_Planning record : records)
		{
			record.setQtyTotalOpen(qtyTotalOpen);
			record.setQtyTotalOpenPlanned(qtyTotalOpenPlanned);
			saveRecord(record);
		}
	}

	/**
	 * All-or-nothing over the selection: an already-closed planning is refused by name, and the check runs before
	 * anything is written, so a mixed selection leaves no row half-closed.
	 * <p>
	 * The runtime backstop behind {@code M_Delivery_Planning_Close}'s precondition, which refuses the same
	 * selection before the button is offered. The caller hands in the very message that precondition rejects
	 * with, so a planner who reaches this far - the process can be invoked past its precondition - reads the same
	 * sentence rather than a developer token carrying a record's {@code toString()}.
	 *
	 * @param alreadyClosedMessage what to raise for an already-closed row; its {@code {0}} is that row's id.
	 */
	public void closeSelectedDeliveryPlannings(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter,
			@NonNull final AdMessageKey alreadyClosedMessage)
	{
		final List<I_M_Delivery_Planning> deliveryPlanningRecords = getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.create()
				.list();

		for (final I_M_Delivery_Planning deliveryPlanningRecord : deliveryPlanningRecords)
		{
			if (deliveryPlanningRecord.isClosed())
			{
				throw new AdempiereException(alreadyClosedMessage, deliveryPlanningRecord.getM_Delivery_Planning_ID());
			}
		}

		for (final I_M_Delivery_Planning deliveryPlanningRecord : deliveryPlanningRecords)
		{
			deliveryPlanningRecord.setIsClosed(true);
			if (!deliveryPlanningRecord.isProcessed())
			{
				// skip the redundant write when it is already set (e.g. a delivered planning) - no-op change-log
				// row avoided, and the invariant (Processed == IsClosed || IsDelivered) holds either way
				deliveryPlanningRecord.setProcessed(true);
			}
			save(deliveryPlanningRecord);
		}
	}

	/**
	 * The counterpart of {@link #closeSelectedDeliveryPlannings}, all-or-nothing in the same way: a planning that
	 * is still open is refused by name, before anything is written.
	 * <p>
	 * {@code Processed} is cleared only when the planning is NOT delivered (Task Q10): a delivered planning stays
	 * {@code Processed} through a reopen, so the invariant {@code Processed == (IsClosed || IsDelivered)} keeps
	 * holding - reopening only ever lifts the {@code IsClosed} half of that OR, it never overrides the
	 * {@code IsDelivered} half. Reads {@code M_InOut_ID} directly rather than the generated (virtual-column)
	 * {@code isDelivered()} getter - the two are equally correct (E3 defines {@code IsDelivered} as exactly this
	 * check) and this form is unit-testable without a real DB, since a virtual column only evaluates against
	 * Postgres.
	 *
	 * @param stillOpenMessage what to raise for a still-open row - the caller passes the message
	 * 		{@code M_Delivery_Planning_ReOpen}'s precondition uses to keep the button off a mixed selection, so both
	 * 		say it alike. Its {@code {0}} is that row's id.
	 */
	public void reOpenSelectedDeliveryPlannings(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter,
			@NonNull final AdMessageKey stillOpenMessage)
	{
		final List<I_M_Delivery_Planning> deliveryPlanningRecords = getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.create()
				.list();

		for (final I_M_Delivery_Planning deliveryPlanningRecord : deliveryPlanningRecords)
		{
			if (!deliveryPlanningRecord.isClosed())
			{
				throw new AdempiereException(stillOpenMessage, deliveryPlanningRecord.getM_Delivery_Planning_ID());
			}
		}

		for (final I_M_Delivery_Planning deliveryPlanningRecord : deliveryPlanningRecords)
		{
			deliveryPlanningRecord.setIsClosed(false);
			if (deliveryPlanningRecord.getM_InOut_ID() <= 0)
			{
				deliveryPlanningRecord.setProcessed(false);
			}
			save(deliveryPlanningRecord);
		}
	}

	public boolean isExistNoShipperDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Shipper_ID, null)
				.create()
				.anyMatch();
	}

	public boolean isExistDeliveryPlanningsWithoutReleaseNo(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo, null)
				.create()
				.anyMatch();
	}

	public boolean isExistDeliveryPlanningsWithReleaseNo(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.addNotNull(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo)
				.create()
				.anyMatch();
	}

	/**
	 * Writes the given dates onto each of the given ALREADY-LOADED plannings, verbatim. Takes the records rather
	 * than their ids because the caller already batch-loaded them; re-loading by id would repeat the round trip.
	 */
	public void writePlanningDates(
			@NonNull final Collection<I_M_Delivery_Planning> deliveryPlanningRecords,
			@NonNull final Map<DeliveryPlanningId, DeliveryInstructionDates> resolvedDatesByPlanningId)
	{
		for (final I_M_Delivery_Planning record : deliveryPlanningRecords)
		{
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
			final DeliveryInstructionDates dates = getResolvedDatesOrThrow(resolvedDatesByPlanningId, deliveryPlanningId);

			record.setETD(dates.getEtd());
			record.setATD(dates.getAtd());
			record.setETA(dates.getEta());
			record.setATA(dates.getAta());
			record.setLoadingTime(dates.getLoadingTime());
			record.setDeliveryTime(dates.getDeliveryTime());
			saveRecord(record);
		}
	}

	private static DeliveryInstructionDates getResolvedDatesOrThrow(
			@NonNull final Map<DeliveryPlanningId, DeliveryInstructionDates> resolvedDatesByPlanningId,
			@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		// an invariant: the caller resolves the dates for exactly the planning ids it then looks up here
		return Check.assumeNotNull(resolvedDatesByPlanningId.get(deliveryPlanningId),
				"No resolved {} found for {}={}", DeliveryInstructionDates.class.getSimpleName(),
				I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId.getRepoId());
	}

	/**
	 * The given plannings, carrying just enough for {@link DeliveryPlanning#isDelivered()} - what
	 * {@link DeliveryInstructionService#recomputeDeliveredState} derives an instruction's {@code DeliveredState}
	 * from. ONE round trip, via {@link #getByIds(Collection)}.
	 */
	public DeliveryPlanningList getDeliveredStatePlannings(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		return getByIds(deliveryPlanningIds).stream()
				.map(DeliveryPlanningRepository::toDeliveredStatePlanning)
				.collect(DeliveryPlanningList.collect());
	}

	/**
	 * The minimal {@link DeliveryPlanning} {@link #getDeliveredStatePlannings} needs: just enough for
	 * {@link DeliveryPlanning#isDelivered()} - unlike {@link #toPoolPlanning}, which carries the quantity pool's
	 * fields instead and is used by an unrelated caller.
	 */
	private static DeliveryPlanning toDeliveredStatePlanning(@NonNull final I_M_Delivery_Planning record)
	{
		return DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				// isDelivered() below never reads transportDirection - it exists only to satisfy the shared
				// value object's @NonNull contract, so a blank/unset column (a real persisted row always has
				// one; this covers a caller whose fixture record does not) falls back rather than throwing.
				.transportDirection(TransportDirection.ofNullableCode(record.getTransportDirection(), TransportDirection.Outgoing))
				.inOutId(InOutId.ofRepoIdOrNull(record.getM_InOut_ID()))
				.build();
	}

	/**
	 * Stamps the given plannings' {@code ReleaseNo}, instruction reference and date fields from the given delivery
	 * instruction, overwriting whatever they carried - a move off another instruction requires it, or two records
	 * would disagree about where the cargo is. The plannings are loaded in ONE round trip.
	 * <p>
	 * The instruction record is handed in by {@link DeliveryInstructionService}, which owns it: this repository
	 * reads its fields but never queries {@code M_ShipperTransportation} itself. An id-taking counterpart lives
	 * on that service.
	 */
	public void updateDeliveryPlanningsFromInstruction(
			@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds,
			@NonNull final I_M_ShipperTransportation deliveryInstruction)
	{
		for (final I_M_Delivery_Planning deliveryPlanningRecord : getByIds(deliveryPlanningIds))
		{
			updateDeliveryPlanningFromInstruction(deliveryPlanningRecord, deliveryInstruction);
		}
	}

	/**
	 * Clears the given plannings' {@code ReleaseNo} and instruction reference: they are on no delivery instruction
	 * any more, and are therefore planable onto one again.
	 */
	public void clearInstructionReference(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		for (final I_M_Delivery_Planning deliveryPlanningRecord : getByIds(deliveryPlanningIds))
		{
			deliveryPlanningRecord.setReleaseNo(null);
			deliveryPlanningRecord.setM_ShipperTransportation_ID(-1);
			saveRecord(deliveryPlanningRecord);
		}
	}

	/**
	 * Clears the {@code ReleaseNo} and instruction reference of every planning currently pointing at the given
	 * delivery instruction - what a void of that instruction owes the plannings it was carrying.
	 */
	public void clearInstructionReferenceOfInstruction(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = retrieveForDeliveryInstructionId(deliveryInstructionId);
		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();
			deliveryPlanningRecord.setReleaseNo(null);
			deliveryPlanningRecord.setM_ShipperTransportation_ID(-1);
			saveRecord(deliveryPlanningRecord);
		}
	}

	/**
	 * Private and record-taking: the only way in is
	 * {@link #updateDeliveryPlanningsFromInstruction(Collection, I_M_ShipperTransportation)}, which loads its whole
	 * argument in ONE round trip; an id-taking counterpart invites the per-row load it exists to prevent.
	 * <p>
	 * Also conforms the planning's own date fields to the instruction's - unconditionally overwritten, and only in
	 * that direction: instruction to planning, never back.
	 */
	private static void updateDeliveryPlanningFromInstruction(@NonNull final I_M_Delivery_Planning deliveryPlanningRecord,
			@NonNull final I_M_ShipperTransportation deliveryInstruction)
	{
		final String created = new SimpleDateFormat("yyyyMMdd-HHmm").format(deliveryInstruction.getCreated());
		deliveryPlanningRecord.setReleaseNo(deliveryInstruction.getDocumentNo() + "-"
													+ deliveryPlanningRecord.getM_Delivery_Planning_ID()
													+ "-" + created);
		deliveryPlanningRecord.setM_ShipperTransportation_ID(deliveryInstruction.getM_ShipperTransportation_ID());

		deliveryPlanningRecord.setETD(deliveryInstruction.getETD());
		deliveryPlanningRecord.setETA(deliveryInstruction.getETA());
		deliveryPlanningRecord.setATD(deliveryInstruction.getATD());
		deliveryPlanningRecord.setATA(deliveryInstruction.getATA());
		deliveryPlanningRecord.setLoadingTime(deliveryInstruction.getLoadingTime());
		deliveryPlanningRecord.setDeliveryTime(deliveryInstruction.getDeliveryTime());

		saveRecord(deliveryPlanningRecord);
	}

	public Iterator<I_M_Delivery_Planning> extractDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.create()
				.iterate(I_M_Delivery_Planning.class);
	}

	@NonNull
	private IQueryBuilder<I_M_Delivery_Planning> getDeliveryPlanningQueryBuilder(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.filter(selectedDeliveryPlanningsFilter);
	}

	private Iterator<I_M_Delivery_Planning> retrieveForDeliveryInstructionId(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionId)
				.create()
				.iterate(I_M_Delivery_Planning.class);
	}

	/**
	 * Cancels ONE delivery planning: closes it, marks it processed and sets its order status to {@code Canceled} -
	 * unconditionally, on every row the caller passes in. The planned quantities are zeroed only when
	 * {@code zeroPlannedQuantities} says the planning is NOT currently committed to a delivery instruction
	 * (D8/D19): a planning still allocated when cancel runs has its {@code PlannedLoadedQuantity}/
	 * {@code PlannedDischargeQuantity} left exactly as they were, the same committed-cargo rule the split
	 * applies. The actual quantities ({@code ActualLoadQty}/{@code ActualDischargeQuantity}) are NEVER written
	 * here, allocated or not: once a receipt or shipment happened, that figure is history, not a plan cancel
	 * gets to erase.
	 */
	public void cancelDeliveryPlanning(@NonNull final I_M_Delivery_Planning deliveryPlanningRecord, final boolean zeroPlannedQuantities)
	{
		deliveryPlanningRecord.setIsClosed(true);
		deliveryPlanningRecord.setProcessed(true);
		deliveryPlanningRecord.setOrderStatus(X_M_Delivery_Planning.ORDERSTATUS_Canceled);
		if (zeroPlannedQuantities)
		{
			deliveryPlanningRecord.setPlannedLoadedQuantity(BigDecimal.ZERO);
			deliveryPlanningRecord.setPlannedDischargeQuantity(BigDecimal.ZERO);
		}
		save(deliveryPlanningRecord);
	}

	public ICompositeQueryFilter<I_M_Delivery_Planning> excludeUnsuitableForInstruction(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return queryBL
				.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addFilter(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo, null)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_IsClosed, false);
	}

	public ICompositeQueryFilter<I_M_Delivery_Planning> excludeDeliveryPlanningsWithoutInstruction(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return queryBL
				.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addFilter(selectedDeliveryPlanningsFilter)
				.addNotNull(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_IsClosed, false);
	}

	/**
	 * Same applicability gate as {@link #excludeDeliveryPlanningsWithoutInstruction}, but WITHOUT the
	 * {@code IsClosed} filter, so a caller can report a closed planning per row instead of never seeing it.
	 */
	public ICompositeQueryFilter<I_M_Delivery_Planning> excludeDeliveryPlanningsWithoutReleaseNo(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return queryBL
				.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addFilter(selectedDeliveryPlanningsFilter)
				.addNotNull(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo);
	}

	public void setPlannedLoadedQuantity(@NonNull final DeliveryPlanningId deliveryPlanningId, @NonNull final Quantity quantity)
	{
		final I_M_Delivery_Planning deliveryPlanning = getById(deliveryPlanningId);
		deliveryPlanning.setPlannedLoadedQuantity(quantity.toBigDecimal());
		deliveryPlanning.setC_UOM_ID(quantity.getUomId().getRepoId());
		save(deliveryPlanning);
	}

	/**
	 * The discharge-side sibling of {@link #setPlannedLoadedQuantity}: overwrites the planning's own
	 * {@code PlannedDischargeQuantity} with its remainder share after a split.
	 */
	public void setPlannedDischargeQuantity(@NonNull final DeliveryPlanningId deliveryPlanningId, @NonNull final Quantity quantity)
	{
		final I_M_Delivery_Planning deliveryPlanning = getById(deliveryPlanningId);
		deliveryPlanning.setPlannedDischargeQuantity(quantity.toBigDecimal());
		deliveryPlanning.setC_UOM_ID(quantity.getUomId().getRepoId());
		save(deliveryPlanning);
	}

}
