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
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
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
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.ShippingPackageId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.ColorId;
import de.metas.util.Services;
import java.time.Instant;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryUpdater;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Alloc;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Package;
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
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Repository Tables: M_Delivery_Planning, M_Delivery_Planning_Alloc, M_ShipperTransportation, M_ShippingPackage, M_Package
 * Repository Cluster: DeliveryPlanningRepository (sole owner of M_Delivery_Planning_Alloc; primary owner of
 * M_Delivery_Planning, which DeliveryPlanningImportProcess also writes directly), ShipperTransportationDAO,
 * PurchaseOrderToShipperTransportationRepository, MPackageRepository (the three transport and packing tables are
 * shared with the transport-order role, which knows nothing of delivery planning)
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
	 * {@code QtyTotalOpen} negative). So the sum is scoped to the lines this planning's own shipment/receipt
	 * schedule produced.
	 * <p>
	 * <b>Scoped by {@code C_OrderLine_ID}</b>, because that is the only attribution a line carries at the moment
	 * this runs. Both producers copy their schedule's order line onto every line they create, before the document
	 * is completed ({@code ShipmentLineBuilder#createShipmentLine}, {@code InOutProducer#updateReceiptLine}),
	 * and a schedule is 1:1 with its order line - so "the lines of this planning's schedule" and "the lines of
	 * this planning's order line" are the same set. The schedule-to-line allocation tables
	 * ({@code M_ShipmentSchedule_QtyPicked.M_InOutLine_ID}, written by {@code ShipmentScheduleWithHU#setM_InOut})
	 * are NOT usable here: they are written AFTER {@code processEx(ACTION_Complete)}, i.e. after this
	 * {@code TIMING_AFTER_COMPLETE} handler has already run, so they would still be empty and every planning
	 * would book zero.
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
		// The pool end depends on the whole line sharing one transport direction (today always true: a line's
		// plannings are all Incoming/Dropship -> DISCHARGE, or all Outgoing -> LOAD). retrieveForOrderLine has no
		// ORDER BY, so asserting homogeneity here - rather than reading records.get(0) from an unordered query -
		// is what actually pins the invariant, not a query-ordering fix.
		final TransportDirection singleDirection = Check.assumePresent(plannings.getSingleTransportDirection(),
				"Expected every M_Delivery_Planning of orderLineId={} to share one TransportDirection: {}", orderLineId, plannings);
		final DeliveryPlanningList.PoolEnd end = DeliveryPlanningList.PoolEnd.forDirection(singleDirection);

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

	public I_M_ShipperTransportation generateDeliveryInstruction(@NonNull final DeliveryInstructionCreateRequest request)
	{
		final I_M_ShipperTransportation deliveryInstructionRecord = newInstance(I_M_ShipperTransportation.class);

		deliveryInstructionRecord.setAD_Org_ID(request.getOrgId().getRepoId());

		deliveryInstructionRecord.setTransportDirection(request.getTransportDirection().getCode());

		deliveryInstructionRecord.setShipper_BPartner_ID(request.getShipperBPartnerId().getRepoId());
		deliveryInstructionRecord.setShipper_Location_ID(request.getShipperLocationId().getRepoId());

		deliveryInstructionRecord.setProcessed(request.isProcessed());

		deliveryInstructionRecord.setC_Incoterms_ID(IncotermsId.toRepoId(request.getIncotermsId()));
		deliveryInstructionRecord.setIncotermLocation(request.getIncotermLocation());

		deliveryInstructionRecord.setLoadingTime(request.getLoadingTime());
		deliveryInstructionRecord.setDeliveryTime(request.getDeliveryTime());

		deliveryInstructionRecord.setM_Shipper_ID(request.getShipperId().getRepoId());

		deliveryInstructionRecord.setM_MeansOfTransportation_ID(MeansOfTransportationId.toRepoId(request.getMeansOfTransportationId()));

		deliveryInstructionRecord.setETA(TimeUtil.asTimestamp(request.getDeliveryDate()));
		deliveryInstructionRecord.setATA(TimeUtil.asTimestamp(request.getAta()));
		deliveryInstructionRecord.setDateDoc(TimeUtil.asTimestamp(request.getDateDoc()));
		deliveryInstructionRecord.setC_DocType_ID(request.getDocTypeId().getRepoId());

		deliveryInstructionRecord.setETD(TimeUtil.asTimestamp(request.getLoadingDate()));
		deliveryInstructionRecord.setATD(TimeUtil.asTimestamp(request.getAtd()));

		deliveryInstructionRecord.setC_BPartner_Location_Delivery_ID(request.getDeliveryPartnerLocationId().getRepoId());
		deliveryInstructionRecord.setC_BPartner_Location_Loading_ID(request.getLoadingPartnerLocationId().getRepoId());

		dimensionService.updateRecord(deliveryInstructionRecord, request.getDimension());

		save(deliveryInstructionRecord);

		createAllocations(deliveryInstructionRecord, ImmutableList.of(toAllocCreateRequest(request)), null);

		return deliveryInstructionRecord;
	}

	private static DeliveryPlanningAllocCreateRequest toAllocCreateRequest(@NonNull final DeliveryInstructionCreateRequest request)
	{
		return DeliveryPlanningAllocCreateRequest.builder()
				.deliveryPlanningId(request.getDeliveryPlanningId())
				.shippingPackage(DeliveryPlanningAllocCreateRequest.ShippingPackageData.builder()
						.productId(request.getProductId())
						.uomId(request.getQtyLoaded().getUomId())
						.batchNo(request.getBatchNo())
						.orderLineId(request.getOrderLineId())
						.orderId(request.getOrderId())
						.toBeFetched(request.isToBeFetched())
						.build())
				// the header's ETD/ETA/LoadingTime/DeliveryTime are already set above, directly from this same
				// request, before generateDeliveryInstruction calls createAllocations with resolvedDates=null -
				// so this single-request list has nothing left to contribute to the fill-if-empty defaulting
				.headerDateCandidate(DeliveryPlanningAllocCreateRequest.HeaderDateCandidate.none())
				.build();
	}

	/**
	 * Allocates the given delivery plannings to the given delivery instruction, each with its own
	 * {@code M_ShippingPackage}. The allocations are created in the order of {@code requests}, so their ids
	 * follow that order - a caller that wants a particular order hands them over sorted.
	 *
	 * @param resolvedDates the instruction header's date fields, written verbatim; {@code null} leaves the
	 * 		header's current dates untouched.
	 */
	public ImmutableList<DeliveryPlanningAllocId> createAllocations(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final List<DeliveryPlanningAllocCreateRequest> requests,
			@Nullable final DeliveryInstructionDates resolvedDates)
	{
		return createAllocations(load(deliveryInstructionId, I_M_ShipperTransportation.class), requests, resolvedDates);
	}

	/**
	 * Creates the allocations and leaves the instruction header's dates exactly as they are.
	 */
	public ImmutableList<DeliveryPlanningAllocId> createAllocations(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final List<DeliveryPlanningAllocCreateRequest> requests)
	{
		return createAllocations(deliveryInstructionId, requests, null);
	}

	/**
	 * Package-private for the caller that already holds the instruction record, so it is not loaded twice.
	 */
	ImmutableList<DeliveryPlanningAllocId> createAllocations(
			@NonNull final I_M_ShipperTransportation deliveryInstructionRecord,
			@NonNull final List<DeliveryPlanningAllocCreateRequest> requests,
			@Nullable final DeliveryInstructionDates resolvedDates)
	{
		// BEFORE the packages are built: createShippingPackage seeds M_Package.ShipDate from the instruction's
		// ETA, so a date written now reaches this add's packages instead of only the next one's.
		if (resolvedDates != null)
		{
			applyDates(deliveryInstructionRecord, resolvedDates);
		}

		final ImmutableList.Builder<DeliveryPlanningAllocId> allocIds = ImmutableList.builder();
		for (final DeliveryPlanningAllocCreateRequest request : requests)
		{
			allocIds.add(createAllocation(deliveryInstructionRecord, request));
		}

		// DeliveredState (Task Q9): ONCE per batch call, not once per request - every request here targets the
		// SAME instruction (the method's single deliveryInstructionRecord parameter), so recomputing inside the
		// loop above would cost one query round trip per row for a result that only the LAST iteration's answer
		// survives. Combine's 3-planning case measured this: per-row would have tripled combine's getByIds calls
		// (2 -> 5); once here keeps it at the pre-existing 2 (see DeliveryPlanningBatchLoadingTest).
		recomputeDeliveredState(ShipperTransportationId.ofRepoId(deliveryInstructionRecord.getM_ShipperTransportation_ID()));

		return allocIds.build();
	}

	private DeliveryPlanningAllocId createAllocation(
			@NonNull final I_M_ShipperTransportation deliveryInstructionRecord,
			@NonNull final DeliveryPlanningAllocCreateRequest request)
	{
		// M_ShippingPackage_ID is mandatory on the allocation and uniquely indexed, so the package exists first
		final I_M_ShippingPackage shippingPackageRecord = createShippingPackage(deliveryInstructionRecord, request);

		final I_M_Delivery_Planning_Alloc allocRecord = newInstance(I_M_Delivery_Planning_Alloc.class);
		allocRecord.setAD_Org_ID(deliveryInstructionRecord.getAD_Org_ID());
		allocRecord.setM_Delivery_Planning_ID(request.getDeliveryPlanningId().getRepoId());
		allocRecord.setM_ShipperTransportation_ID(deliveryInstructionRecord.getM_ShipperTransportation_ID());
		allocRecord.setM_ShippingPackage_ID(shippingPackageRecord.getM_ShippingPackage_ID());
		saveRecord(allocRecord);

		// IsAllocated is kept in step by the M_Delivery_Planning_Alloc @ModelChange interceptor (AFTER_NEW),
		// triggered by the saveRecord above - not by an inline call here, so a future write path that inserts
		// an alloc row without going through this method still keeps the mirror correct.

		// DeliveredState (Task Q9) is recomputed once per BATCH by the caller (createAllocations), not here per
		// row - see that method's note on why.

		return DeliveryPlanningAllocId.ofRepoId(allocRecord.getM_Delivery_Planning_Alloc_ID());
	}

	/**
	 * Writes the given dates onto the instruction header field for field, unconditionally. Saved only when at
	 * least one field actually differs, so a no-op resolution costs no write and fires no {@code AFTER_CHANGE}.
	 */
	private static void applyDates(@NonNull final I_M_ShipperTransportation record, @NonNull final DeliveryInstructionDates dates)
	{
		final boolean changed = !Objects.equals(record.getETD(), dates.getEtd())
				|| !Objects.equals(record.getETA(), dates.getEta())
				|| !Objects.equals(record.getATD(), dates.getAtd())
				|| !Objects.equals(record.getATA(), dates.getAta())
				|| !Objects.equals(record.getLoadingTime(), dates.getLoadingTime())
				|| !Objects.equals(record.getDeliveryTime(), dates.getDeliveryTime());
		if (!changed)
		{
			return;
		}

		record.setETD(dates.getEtd());
		record.setETA(dates.getEta());
		record.setATD(dates.getAtd());
		record.setATA(dates.getAta());
		record.setLoadingTime(dates.getLoadingTime());
		record.setDeliveryTime(dates.getDeliveryTime());
		saveRecord(record);
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

	private static I_M_ShippingPackage createShippingPackage(
			@NonNull final I_M_ShipperTransportation deliveryInstructionRecord,
			@NonNull final DeliveryPlanningAllocCreateRequest request)
	{
		final DeliveryPlanningAllocCreateRequest.ShippingPackageData packageData = request.getShippingPackage();

		final int shipperBPartnerId = deliveryInstructionRecord.getShipper_BPartner_ID();
		final int shipperLocationId = deliveryInstructionRecord.getShipper_Location_ID();

		final I_M_Package mpackage = newInstance(I_M_Package.class);
		mpackage.setM_Shipper_ID(deliveryInstructionRecord.getM_Shipper_ID());
		mpackage.setShipDate(deliveryInstructionRecord.getETA());
		mpackage.setC_BPartner_ID(shipperBPartnerId);
		mpackage.setC_BPartner_Location_ID(shipperLocationId);
		save(mpackage);

		final I_M_ShippingPackage shippingPackageRecord = newInstance(I_M_ShippingPackage.class);
		shippingPackageRecord.setM_ShipperTransportation_ID(deliveryInstructionRecord.getM_ShipperTransportation_ID());
		shippingPackageRecord.setM_Package_ID(mpackage.getM_Package_ID());
		shippingPackageRecord.setIsToBeFetched(packageData.isToBeFetched());
		shippingPackageRecord.setM_Product_ID(packageData.getProductId().getRepoId());

		// Task Q14: the four quantity figures (planned load, planned discharge, actual load, actual
		// discharge) are derived (ColumnSQL) from the planning through the M_Delivery_Planning_Alloc
		// allocation - nothing to write here. They used to be copied from request.getQtyLoaded()/
		// getQtyDischarged() (themselves the planning's PLANNED figures), which froze the package's
		// "actual" at the planned value forever; the mirror replaces that copy, not a second derivation.
		shippingPackageRecord.setBatch(packageData.getBatchNo());
		shippingPackageRecord.setC_UOM_ID(packageData.getUomId().getRepoId());

		shippingPackageRecord.setC_BPartner_ID(shipperBPartnerId);
		shippingPackageRecord.setC_BPartner_Location_ID(shipperLocationId);

		shippingPackageRecord.setC_OrderLine_ID(OrderLineId.toRepoId(packageData.getOrderLineId()));
		shippingPackageRecord.setC_Order_ID(OrderId.toRepoId(packageData.getOrderId()));

		saveRecord(shippingPackageRecord);

		return shippingPackageRecord;
	}

	/**
	 * Deactivates - rather than deletes - the given plannings' ACTIVE allocations and the shipping packages they
	 * point at, so the record of what was once planned survives. A deactivated allocation is left alone: it
	 * records an instruction the planning was taken off earlier, which is not what the caller is undoing.
	 *
	 * @return the planning ids ACTUALLY deactivated - a subset of the input when one had no active allocation.
	 */
	public ImmutableSet<DeliveryPlanningId> deactivateAllocations(
			@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds,
			@NonNull final Instant removedAt)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return deactivateAllocationRecords(queryAllocationsByPlanningIds(deliveryPlanningIds).create().list(), removedAt).getDeallocatedPlanningIds();
	}

	/**
	 * On void or cancel of the delivery instruction: the allocations and their shipping packages are deactivated
	 * rather than deleted. {@code IsActive='N'} also releases both partial unique indexes on the allocation, so
	 * the plannings can be allocated again afterwards.
	 */
	public DeactivatedAllocations deactivateAllocations(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final Instant removedAt)
	{
		return deactivateAllocationRecords(queryActiveAllocationsByInstructionId(deliveryInstructionId).create().list(), removedAt);
	}

	/**
	 * Shared by both {@code deactivateAllocations} overloads, and the single choke point every path that ends an
	 * allocation's active life routes through - which is why {@code DateRemoved} is stamped here and nowhere
	 * else. Both entry queries select ACTIVE allocations only, so the stamp is written once per allocation.
	 * <p>
	 * Cost note: each row's {@code saveRecord(allocRecord)} below fires the {@code M_Delivery_Planning_Alloc}
	 * interceptor individually, so deactivating N allocations here costs N {@code IsAllocated} {@code UPDATE}s
	 * (via {@link #refreshIsAllocated(DeliveryPlanningId)}) - e.g. voiding one delivery instruction that
	 * carries N plannings. Not batched into one {@code UPDATE ... WHERE id IN (...)} in this round: doing so
	 * would mean either re-introducing an inline call here (defeating the interceptor's structural guarantee -
	 * a future write path outside this loop would again need to remember it) or adding a transaction-scoped
	 * collector that accumulates touched planning ids and flushes one batched statement at commit. The latter
	 * is a real option if N grows large in practice, but it is a caching-like layer with its own correctness
	 * questions (multiple accumulate-then-flush cycles per transaction, ordering against other readers of
	 * {@code IsAllocated} mid-transaction) that deserves its own deliberate decision, not one folded into this
	 * correctness fix.
	 */
	private DeactivatedAllocations deactivateAllocationRecords(
			@NonNull final List<I_M_Delivery_Planning_Alloc> allocRecords,
			@NonNull final Instant removedAt)
	{
		final ImmutableMap<ShippingPackageId, I_M_ShippingPackage> shippingPackages = getShippingPackagesOf(allocRecords);

		final ImmutableList.Builder<I_M_ShippingPackage> deactivatedShippingPackages = ImmutableList.builder();
		final ImmutableSet.Builder<DeliveryPlanningId> deallocatedPlanningIds = ImmutableSet.builder();
		final ImmutableSet.Builder<ShipperTransportationId> touchedDeliveryInstructionIds = ImmutableSet.builder();
		for (final I_M_Delivery_Planning_Alloc allocRecord : allocRecords)
		{
			final I_M_ShippingPackage shippingPackageRecord = shippingPackages.get(ShippingPackageId.ofRepoId(allocRecord.getM_ShippingPackage_ID()));
			shippingPackageRecord.setIsActive(false);
			saveRecord(shippingPackageRecord);
			deactivatedShippingPackages.add(shippingPackageRecord);

			allocRecord.setIsActive(false);
			allocRecord.setDateRemoved(TimeUtil.asTimestamp(removedAt));
			saveRecord(allocRecord);

			deallocatedPlanningIds.add(DeliveryPlanningId.ofRepoId(allocRecord.getM_Delivery_Planning_ID()));
			touchedDeliveryInstructionIds.add(ShipperTransportationId.ofRepoId(allocRecord.getM_ShipperTransportation_ID()));
		}

		final ImmutableSet<DeliveryPlanningId> deallocatedPlanningIdsSet = deallocatedPlanningIds.build();

		// IsAllocated is kept in step by the M_Delivery_Planning_Alloc @ModelChange interceptor (AFTER_CHANGE
		// on IsActive), triggered by the allocRecord.setIsActive(false) + saveRecord above - not by an inline
		// call here, so a future deactivation path (a bulk fix, an import routine) still keeps the mirror correct.

		// DeliveredState (Task Q9): recompute each touched instruction ONCE, deduplicated across allocations -
		// both deactivateAllocations overloads route through this method, and the bulk-by-planning-ids one can
		// span several instructions in one call.
		for (final ShipperTransportationId deliveryInstructionId : touchedDeliveryInstructionIds.build())
		{
			recomputeDeliveredState(deliveryInstructionId);
		}

		return DeactivatedAllocations.builder()
				.shippingPackages(deactivatedShippingPackages.build())
				.deallocatedPlanningIds(deallocatedPlanningIdsSet)
				.build();
	}

	/**
	 * Called by the {@code M_Delivery_Planning_Alloc} {@code @ModelChange} interceptor on every event that can
	 * change which planning an ACTIVE allocation points at (AFTER_NEW, AFTER_CHANGE of {@code IsActive},
	 * AFTER_DELETE) - the single place that re-derives and writes the {@code IsAllocated} mirror, so every
	 * writer of the allocation table keeps it correct automatically, including one that does not exist yet.
	 * <p>
	 * ONE SQL {@code UPDATE} per call, with the {@code EXISTS} check folded directly into that statement's
	 * {@code SET} clause (via {@link IsAllocatedFromAllocTableUpdater} below) - deliberately not a separate
	 * {@link #hasActiveAllocation(DeliveryPlanningId)} {@code SELECT} followed by a second {@code UPDATE}.
	 * Neither statement loads a {@code I_M_Delivery_Planning} row, so this adds no
	 * {@link #getById(DeliveryPlanningId)} / {@link #getByIds(Collection)} round trip - both are
	 * batch-load-discipline-tested elsewhere (see {@code DeliveryPlanningBatchLoadingTest}).
	 * <p>
	 * Still ONE statement PER PLANNING ID, not batched across several: {@link #deactivateAllocationRecords}
	 * calls this once per row inside its loop (one {@code saveRecord} per allocation fires the interceptor
	 * once), so voiding an instruction that carries N plannings costs N {@code UPDATE}s here - see that
	 * method's own note on why this round does not turn that into a transaction-scoped batch.
	 * <p>
	 * {@code updateDirectly} is a raw SQL {@code UPDATE} - it fires no {@code CacheMgt} reset and no
	 * interceptor - so the explicit {@link CacheMgt#reset(String, int)} below is required: this method exists
	 * precisely to cover allocation writers that touch only {@code M_Delivery_Planning_Alloc} (e.g. a future
	 * bulk fix or import routine looping {@code InterfaceWrapperHelper.save} over allocation rows without
	 * saving the planning record itself). Without the reset, a cached {@code I_M_Delivery_Planning} row - an
	 * operator's Lieferplanung window already holding it open, say - would keep showing the pre-change
	 * {@code IsAllocated} until an unrelated write on that same row happened to invalidate it, which is exactly
	 * the staleness this column could never have before it was a live {@code ColumnSQL} (5821150).
	 */
	public void refreshIsAllocated(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId)
				.create()
				.updateDirectly(new IsAllocatedFromAllocTableUpdater());

		CacheMgt.get().reset(I_M_Delivery_Planning.Table_Name, deliveryPlanningId.getRepoId());
	}

	/**
	 * Makes every delivery instruction the given planning is ACTIVELY allocated to refresh its
	 * {@code M_ShippingPackage} line in an already-open WebUI document (Task Q14, TC11) - the four quantity
	 * figures on that line are a {@code ColumnSQL} read-through of this planning, so a change here changes what
	 * the line must show.
	 * <p>
	 * The reason a hand-written invalidation is needed at all, and why the request is rooted at the INSTRUCTION
	 * rather than at the package, is spelled out on {@link DeliveryInstructionLineCacheInvalidation}. Broadcast
	 * on transaction commit (not immediately), the same way {@code de.metas.acct.interceptor.GL_JournalLine}
	 * pushes a line change up to its {@code GL_Journal} document: the frontend must re-read committed data.
	 * <p>
	 * Cost: one {@code SELECT} over {@code M_Delivery_Planning_Alloc} per quantity-changing save of a planning,
	 * and nothing at all for a planning that is on no instruction (the overwhelmingly common case while a
	 * planning is still being planned) - {@code requestForAllocationsOrNull} returns {@code null} and no
	 * broadcast is sent.
	 */
	public void invalidateDeliveryInstructionLinesFor(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final CacheInvalidateMultiRequest request = DeliveryInstructionLineCacheInvalidation.requestForAllocationsOrNull(
				getAllocationsByPlanningId(ImmutableList.of(deliveryPlanningId)).values());
		if (request == null)
		{
			return;
		}

		CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(ITrx.TRXNAME_ThreadInherited, request);
	}

	/**
	 * Sets {@code IsAllocated} from the same {@code EXISTS} the column's old {@code ColumnSQL} evaluated
	 * (5821150), computed in the {@code UPDATE}'s own {@code SET} clause rather than pre-fetched - an
	 * {@link ISqlQueryUpdater}, so {@link IQuery#updateDirectly} issues one raw SQL {@code UPDATE} and never
	 * calls {@link #update(I_M_Delivery_Planning)} (the load-and-save fallback for a non-SQL query engine,
	 * kept correct but never exercised against Postgres).
	 */
	private final class IsAllocatedFromAllocTableUpdater implements ISqlQueryUpdater<I_M_Delivery_Planning>
	{
		@Override
		public String getSql(final Properties ctx, final List<Object> sqlParams)
		{
			return I_M_Delivery_Planning.COLUMNNAME_IsAllocated
					+ " = (case when exists (select 1 from " + I_M_Delivery_Planning_Alloc.Table_Name
					+ " a where a." + I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID
					+ " = " + I_M_Delivery_Planning.Table_Name + "." + I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID
					+ " and a." + I_M_Delivery_Planning_Alloc.COLUMNNAME_IsActive + " = 'Y') then 'Y' else 'N' end)";
		}

		@Override
		public boolean update(final I_M_Delivery_Planning deliveryPlanningRecord)
		{
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID());
			deliveryPlanningRecord.setIsAllocated(hasActiveAllocation(deliveryPlanningId));
			return true;
		}
	}

	/**
	 * What {@link #deactivateAllocationRecords} produces: the allocations it deactivated, and which plannings
	 * that touched.
	 */
	@Value
	@Builder
	public static class DeactivatedAllocations
	{
		ImmutableList<I_M_ShippingPackage> shippingPackages;
		ImmutableSet<DeliveryPlanningId> deallocatedPlanningIds;
	}

	/**
	 * The shipping packages the given allocations point at, keyed by id, in one round trip. Every allocation has
	 * one ({@code M_ShippingPackage_ID} is mandatory and foreign-keyed), so a lookup never misses.
	 */
	private ImmutableMap<ShippingPackageId, I_M_ShippingPackage> getShippingPackagesOf(@NonNull final List<I_M_Delivery_Planning_Alloc> allocRecords)
	{
		if (allocRecords.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSet<ShippingPackageId> shippingPackageIds = allocRecords.stream()
				.map(allocRecord -> ShippingPackageId.ofRepoId(allocRecord.getM_ShippingPackage_ID()))
				.collect(ImmutableSet.toImmutableSet());

		return queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addInArrayFilter(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID, shippingPackageIds)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						shippingPackage -> ShippingPackageId.ofRepoId(shippingPackage.getM_ShippingPackage_ID()),
						shippingPackage -> shippingPackage));
	}

	/**
	 * The ACTIVE allocations of each of the given plannings, grouped by planning - a planning without one is
	 * absent from the result. A multimap rather than a one-key-per-planning map: a planning may be allocated to
	 * more than one instruction.
	 */
	public ImmutableListMultimap<DeliveryPlanningId, DeliveryPlanningAlloc> getAllocationsByPlanningId(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryAllocationsByPlanningIds(deliveryPlanningIds)
				.create()
				.stream()
				.map(DeliveryPlanningRepository::toDeliveryPlanningAlloc)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						DeliveryPlanningAlloc::getDeliveryPlanningId,
						alloc -> alloc));
	}

	/**
	 * The ACTIVE allocations the given instruction currently holds, in ONE round trip - the other direction of
	 * {@link #getAllocationsByPlanningId(Collection)}.
	 */
	public ImmutableList<DeliveryPlanningAlloc> getAllocationsOfInstruction(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return queryActiveAllocationsByInstructionId(deliveryInstructionId)
				.create()
				.stream()
				.map(DeliveryPlanningRepository::toDeliveryPlanningAlloc)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * The delivery instructions ONE planning is currently allocated to, distinct; empty for a planning on none.
	 */
	private ImmutableSet<ShipperTransportationId> getAllocatedInstructionIdsOf(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return getAllocationsByPlanningId(ImmutableList.of(deliveryPlanningId))
				.values()
				.stream()
				.map(DeliveryPlanningAlloc::getDeliveryInstructionId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Recomputes {@code M_ShipperTransportation.DeliveredState} for every delivery instruction the given planning
	 * is currently ACTIVELY allocated to (spec &sect; 5.7, Task Q9) - the entry point
	 * {@code interceptor/M_InOut#afterComplete}/{@code #afterReverseCorrect} routes through after a receipt or
	 * shipment completes or is reversed, since that is the write that can change ONE planning's
	 * {@code IsDelivered} and therefore every instruction it sits on.
	 */
	public void recomputeDeliveredStateForAllocatedInstructions(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		for (final ShipperTransportationId deliveryInstructionId : getAllocatedInstructionIdsOf(deliveryPlanningId))
		{
			recomputeDeliveredState(deliveryInstructionId);
		}
	}

	/**
	 * Recomputes and stores {@code M_ShipperTransportation.DeliveredState} for ONE delivery instruction, from
	 * {@link DeliveryPlanningList#getDeliveredState()} over its currently ACTIVE allocations - the single
	 * derivation every write point that can change which plannings are delivered, or which plannings are
	 * actively allocated to the instruction, routes through (rule 6, Task Q9): {@link #createAllocation},
	 * {@link #deactivateAllocationRecords} and {@link #recomputeDeliveredStateForAllocatedInstructions}. An
	 * instruction with no active allocation is {@code NotDelivered} - the same vacuous case the ADD COLUMN
	 * DEFAULT already gives a freshly-created instruction, so this is never a special case, only the general one.
	 */
	public void recomputeDeliveredState(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final ImmutableList<DeliveryPlanningAlloc> allocations = getAllocationsOfInstruction(deliveryInstructionId);

		final DeliveryInstructionDeliveredState deliveredState;
		if (allocations.isEmpty())
		{
			deliveredState = DeliveryInstructionDeliveredState.NotDelivered;
		}
		else
		{
			final ImmutableSet<DeliveryPlanningId> allocatedPlanningIds = allocations.stream()
					.map(DeliveryPlanningAlloc::getDeliveryPlanningId)
					.collect(ImmutableSet.toImmutableSet());

			final DeliveryPlanningList plannings = getByIds(allocatedPlanningIds).stream()
					.map(DeliveryPlanningRepository::toDeliveredStatePlanning)
					.collect(DeliveryPlanningList.collect());

			deliveredState = plannings.getDeliveredState();
		}

		final I_M_ShipperTransportation deliveryInstructionRecord = load(deliveryInstructionId, I_M_ShipperTransportation.class);
		deliveryInstructionRecord.setDeliveredState(deliveredState.getCode());
		saveRecord(deliveryInstructionRecord);
	}

	/**
	 * The minimal {@link DeliveryPlanning} {@link #recomputeDeliveredState} needs: just enough for
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

	private static DeliveryPlanningAlloc toDeliveryPlanningAlloc(@NonNull final I_M_Delivery_Planning_Alloc allocRecord)
	{
		return DeliveryPlanningAlloc.builder()
				.id(DeliveryPlanningAllocId.ofRepoId(allocRecord.getM_Delivery_Planning_Alloc_ID()))
				.deliveryPlanningId(DeliveryPlanningId.ofRepoId(allocRecord.getM_Delivery_Planning_ID()))
				.deliveryInstructionId(ShipperTransportationId.ofRepoId(allocRecord.getM_ShipperTransportation_ID()))
				.shippingPackageId(ShippingPackageId.ofRepoId(allocRecord.getM_ShippingPackage_ID()))
				.build();
	}

	/**
	 * The delivery plannings the given instruction currently holds, as ids in a stable order - a rejection that
	 * names them has to read the same on two identical runs.
	 */
	public ImmutableSet<DeliveryPlanningId> getAllocatedPlanningIds(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return queryActiveAllocationsByInstructionId(deliveryInstructionId)
				.create()
				.stream()
				.map(allocRecord -> DeliveryPlanningId.ofRepoId(allocRecord.getM_Delivery_Planning_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	public I_M_ShipperTransportation getInstructionById(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return load(deliveryInstructionId, I_M_ShipperTransportation.class);
	}

	/**
	 * The {@code DocStatus} of each of the given delivery instructions, in one round trip - read from the
	 * instruction because the allocation carries no {@code DocStatus} of its own.
	 */
	public ImmutableMap<ShipperTransportationId, DocStatus> getDeliveryInstructionDocStatuses(@NonNull final Collection<ShipperTransportationId> deliveryInstructionIds)
	{
		if (deliveryInstructionIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		return queryBL.createQueryBuilder(I_M_ShipperTransportation.class)
				.addInArrayFilter(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionIds)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> ShipperTransportationId.ofRepoId(record.getM_ShipperTransportation_ID()),
						DeliveryPlanningRepository::extractDocStatus));
	}

	public DocStatus getDeliveryInstructionDocStatus(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return extractDocStatus(load(deliveryInstructionId, I_M_ShipperTransportation.class));
	}

	/**
	 * Stamps the given plannings' {@code ReleaseNo}, instruction reference and date fields from the given delivery
	 * instruction, overwriting whatever they carried - a move off another instruction requires it, or two records
	 * would disagree about where the cargo is.
	 */
	public void updateDeliveryPlanningsFromInstruction(
			@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds,
			@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return;
		}

		updateDeliveryPlanningsFromInstruction(
				deliveryPlanningIds,
				load(deliveryInstructionId, I_M_ShipperTransportation.class));
	}

	/**
	 * Same as {@link #updateDeliveryPlanningsFromInstruction(Collection, ShipperTransportationId)}, for a caller
	 * that already holds the instruction record. The plannings are loaded in ONE round trip.
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

	private IQueryBuilder<I_M_Delivery_Planning_Alloc> queryAllocationsByPlanningIds(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningIds);
	}

	/**
	 * Whether the given planning is currently on a delivery instruction - asked of the allocation table, NOT of
	 * the denormalised {@code M_Delivery_Planning.ReleaseNo} mirror: a mirror left saying "allocated" with no
	 * allocation row behind it would refuse forever, and the planning could never be deleted nor planned again.
	 */
	public boolean hasActiveAllocation(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return queryAllocationsByPlanningIds(ImmutableList.of(deliveryPlanningId))
				.create()
				.anyMatch();
	}

	/**
	 * Removes the given planning's retired allocation rows - the cleanup a delete of the planning itself owes.
	 * Filters to {@code IsActive='N'} here rather than trusting the caller's prior check, so a concurrently
	 * inserted live row is left in place and the {@code NO ACTION} foreign key refuses the delete loudly. The
	 * shipping packages are left alone: they are the instruction's own lines, and it still exists.
	 */
	public void deleteAllocationsFor(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_IsActive, false)
				.addInArrayFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningIds)
				.create()
				.delete();
	}

	/**
	 * The delivery instruction the given shipping package is allocated to, if any - NOT filtered by
	 * {@code IsActive}, because a retired allocation names the very instruction whose history the retirement
	 * exists to keep. {@code firstOnlyOptional}: several allocation rows for one package would be a defect.
	 */
	public Optional<ShipperTransportationId> getInstructionIdByShippingPackageId(@NonNull final ShippingPackageId shippingPackageId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShippingPackage_ID, shippingPackageId)
				.create()
				.firstOnlyOptional(I_M_Delivery_Planning_Alloc.class)
				.map(allocRecord -> ShipperTransportationId.ofRepoId(allocRecord.getM_ShipperTransportation_ID()));
	}

	/**
	 * The instruction's ACTIVE allocations, in a stable allocation-id order.
	 */
	private IQueryBuilder<I_M_Delivery_Planning_Alloc> queryActiveAllocationsByInstructionId(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionId)
				.orderBy().addColumnAscending(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_Alloc_ID).endOrderBy();
	}

	private static DocStatus extractDocStatus(@NonNull final I_M_ShipperTransportation deliveryInstructionRecord)
	{
		return DocStatus.ofNullableCodeOrUnknown(deliveryInstructionRecord.getDocStatus());
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
	 * Unlinks the packages behind the JUST-DEACTIVATED allocations only - never the instruction's whole package
	 * set: a planning removed earlier left a retired package still carrying this instruction's id, and
	 * re-querying by instruction id would wipe its {@code C_OrderLine_ID} too.
	 *
	 * @return the {@link DeactivatedAllocations} of {@link #deactivateAllocations(ShipperTransportationId, Instant)}.
	 */
	public DeactivatedAllocations unlinkDeliveryPlannings(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final Instant removedAt)
	{
		final DeactivatedAllocations deactivatedAllocations = deactivateAllocations(deliveryInstructionId, removedAt);

		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = retrieveForDeliveryInstructionId(deliveryInstructionId);
		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();
			deliveryPlanningRecord.setReleaseNo(null);
			deliveryPlanningRecord.setM_ShipperTransportation_ID(-1);
			saveRecord(deliveryPlanningRecord);
		}

		deactivatedAllocations.getShippingPackages().forEach(this::unlinkShippingPackage);

		return deactivatedAllocations;
	}

	private void unlinkShippingPackage(@NonNull final I_M_ShippingPackage shippingPackage)
	{
		shippingPackage.setC_OrderLine_ID(-1);
		save(shippingPackage);
	}

	public Iterator<I_M_ShipperTransportation> retrieveForDeliveryPlanning(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final ImmutableSet<ShipperTransportationId> deliveryInstructionIds = getAllocatedInstructionIdsOf(deliveryPlanningId);

		return queryBL.createQueryBuilder(I_M_ShipperTransportation.class)
				.addInArrayFilter(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionIds)
				.create()
				.iterate(I_M_ShipperTransportation.class);
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

	public boolean hasCompleteDeliveryInstruction(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final ImmutableSet<ShipperTransportationId> deliveryInstructionIds = getAllocatedInstructionIdsOf(deliveryPlanningId);

		return queryBL.createQueryBuilder(I_M_ShipperTransportation.class)
				.addInArrayFilter(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionIds)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_DocStatus, DocStatus.Completed)
				.anyMatch();
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
