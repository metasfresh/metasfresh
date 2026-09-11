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
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.dimension.DimensionService;
import de.metas.i18n.AdMessageKey;
import de.metas.incoterms.IncotermsId;
import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.location.CountryId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.ShipperId;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_InOut;
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
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Repository Tables: M_Delivery_Planning, M_ShipperTransportation
 * Repository Cluster: DeliveryPlanningRepository (primary owner of M_Delivery_Planning, which
 * DeliveryPlanningImportProcess also writes directly), DeliveryPlanningAllocRepository,
 * DeliveryInstructionRepository, ShipperTransportationDAO, PurchaseOrderToShipperTransportationRepository
 * (M_ShipperTransportation is shared with the transport-order role, which knows nothing of delivery planning)
 * <p>
 * M_Delivery_Planning_Alloc moved to {@link DeliveryPlanningAllocRepository}, and M_ShippingPackage / M_Package
 * to {@link DeliveryInstructionRepository} / MPackageRepository, so this class no longer owns them.
 * <p>
 * The one injected collaborator is {@link DimensionService}: a dimension is copied from the source row onto the
 * target row as that row is written, which is persistence rather than a delivery-planning decision.
 */
@Repository
public class DeliveryPlanningRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

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
	 * UPDATE, never create - creation goes through {@link #generateDeliveryPlanning}. Read-modify-write
	 * over ONE load: the records are fetched once, each is handed to the caller AS THE MODEL, and the
	 * returned model is written straight back onto the record it came from.
	 * <p>
	 * There is deliberately no single-model {@code update(DeliveryPlanning)}: it would write every column
	 * the model tracks from a possibly-DETACHED instance, so a caller building one from scratch - the
	 * mandatory fields are only a third of them - would silently blank the rest of a live row. Single-row
	 * semantics are this method with a one-element set, which cannot detach.
	 * <p>
	 * This exists because the obvious shape - read a {@link DeliveryPlanningList}, transform it, save it -
	 * reads every row TWICE, once as models and again as records to write. {@code DeliveryPlanningBatchLoadingTest}
	 * pins the load count on these paths, which is what caught that.
	 */
	public void updateByIds(
			@NonNull final Set<DeliveryPlanningId> deliveryPlanningIds,
			@NonNull final UnaryOperator<DeliveryPlanning> updater)
	{
		for (final I_M_Delivery_Planning record : getRecordsByIds(deliveryPlanningIds))
		{
			applyTo(record, updater.apply(fromRecord(record)));
			saveRecord(record);
		}
	}

	/**
	 * Stores the allocation-derived flags. Narrow on purpose: these two columns are a PROJECTION of the
	 * allocations, not planning state, so they are written by their own method rather than becoming fields on
	 * {@link DeliveryPlanning} where they could disagree with the allocations they project.
	 * <p>
	 * The computation belongs to {@link DeliveryPlanningAllocService}, which owns the leading data; this
	 * repository only owns the table the projection is stored on.
	 */
	public void updateAllocationDerivedFlags(@NonNull final Map<DeliveryPlanningId, AllocationDerivedFlags> flagsByPlanningId)
	{
		if (flagsByPlanningId.isEmpty())
		{
			return;
		}

		for (final I_M_Delivery_Planning record : getRecordsByIds(ImmutableSet.copyOf(flagsByPlanningId.keySet())))
		{
			final AllocationDerivedFlags flags = flagsByPlanningId.get(DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()));
			record.setIsAllocated(flags.isAllocated());
			record.setIsReadyForReceipt(flags.isReadyForReceipt());
			saveRecord(record);
		}
	}

	/**
	 * Single-row form, matching the {@code updateById}/{@code updateByIds} pair other repositories expose
	 * (InvoicePayScheduleRepository, OrderPayScheduleRepository, SAPGLJournalService). Those take a
	 * {@code Consumer} because their models mutate in place; {@link DeliveryPlanning} is a {@code @Value},
	 * so the equivalent for an immutable model is a {@link UnaryOperator} returning the changed copy.
	 * <p>
	 * Note this is an id plus an updater, NOT a detached model - the model handed to the updater always
	 * comes from the row it will be written back to, which is what keeps it from blanking untouched columns.
	 */
	public void updateById(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final UnaryOperator<DeliveryPlanning> updater)
	{
		updateByIds(ImmutableSet.of(deliveryPlanningId), updater);
	}

	/** The filter-shaped sibling: same one-load read-modify-write, for the selection-based writers. */
	public void updateByFilter(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter,
			@NonNull final UnaryOperator<DeliveryPlanning> updater)
	{
		for (final I_M_Delivery_Planning record : getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter).create().list())
		{
			applyTo(record, updater.apply(fromRecord(record)));
			saveRecord(record);
		}
	}

	/** One transform, shared by the by-ids and by-instruction entry points - the same operation twice over. */
	private static final UnaryOperator<DeliveryPlanning> CLEAR_INSTRUCTION_REFERENCE =
			deliveryPlanning -> deliveryPlanning.toBuilder()
					.releaseNo(null)
					.shipperTransportationId(null)
					.build();

	private static void applyTo(@NonNull final I_M_Delivery_Planning record, @NonNull final DeliveryPlanning deliveryPlanning)
	{
		record.setAD_Org_ID(deliveryPlanning.getOrgId().getRepoId());
		record.setTransportDirection(deliveryPlanning.getTransportDirection().getCode());
		record.setM_Shipper_ID(ShipperId.toRepoId(deliveryPlanning.getShipperId()));
		record.setC_Incoterms_ID(IncotermsId.toRepoId(deliveryPlanning.getIncotermsId()));
		record.setIncotermLocation(deliveryPlanning.getIncotermLocation());
		record.setM_MeansOfTransportation_ID(MeansOfTransportationId.toRepoId(deliveryPlanning.getMeansOfTransportationId()));
		record.setETD(TimeUtil.asTimestamp(deliveryPlanning.getEtd()));
		record.setIsClosed(deliveryPlanning.isClosed());
		record.setProcessed(deliveryPlanning.isProcessed());
		record.setM_InOut_ID(InOutId.toRepoId(deliveryPlanning.getInOutId()));
		record.setM_ShipperTransportation_ID(ShipperTransportationId.toRepoId(deliveryPlanning.getShipperTransportationId()));
		record.setReleaseNo(deliveryPlanning.getReleaseNo());
		record.setQtyOrdered(deliveryPlanning.getQtyOrdered().toBigDecimal());
		record.setPlannedLoadedQuantity(deliveryPlanning.getPlannedLoadedQty().toBigDecimal());
		record.setActualLoadQty(deliveryPlanning.getActualLoadedQty().toBigDecimal());
		record.setPlannedDischargeQuantity(deliveryPlanning.getPlannedDischargeQty().toBigDecimal());
		record.setActualDischargeQuantity(deliveryPlanning.getActualDischargeQty().toBigDecimal());
		record.setC_BPartner_ID(deliveryPlanning.getBpartnerId().getRepoId());
		record.setC_UOM_ID(deliveryPlanning.getUomId().getRepoId());
		record.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(deliveryPlanning.getBpartnerLocationId()));
		record.setM_Product_ID(ProductId.toRepoId(deliveryPlanning.getProductId()));
		record.setM_Warehouse_ID(WarehouseId.toRepoId(deliveryPlanning.getWarehouseId()));
		record.setC_Order_ID(OrderId.toRepoId(deliveryPlanning.getOrderId()));
		record.setC_OrderLine_ID(OrderLineId.toRepoId(deliveryPlanning.getOrderLineId()));
		record.setM_ReceiptSchedule_ID(ReceiptScheduleId.toRepoId(deliveryPlanning.getReceiptScheduleId()));
		record.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(deliveryPlanning.getShipmentScheduleId()));
		record.setC_OriginCountry_ID(CountryId.toRepoId(deliveryPlanning.getOriginCountryId()));
		record.setC_DestinationCountry_ID(CountryId.toRepoId(deliveryPlanning.getDestinationCountryId()));
		record.setATA(TimeUtil.asTimestamp(deliveryPlanning.getAta()));
		record.setATD(TimeUtil.asTimestamp(deliveryPlanning.getAtd()));
		record.setETA(TimeUtil.asTimestamp(deliveryPlanning.getEta()));
		record.setLoadingTime(deliveryPlanning.getLoadingTime());
		record.setDeliveryTime(deliveryPlanning.getDeliveryTime());
		record.setOrderStatus(deliveryPlanning.getOrderStatus());
		record.setBatch(deliveryPlanning.getBatch());
		record.setWayBillNo(deliveryPlanning.getWayBillNo());
		record.setTransportDetails(deliveryPlanning.getTransportDetails());
		record.setQtyTotalOpen(deliveryPlanning.getQtyTotalOpen().toBigDecimal());
		record.setQtyTotalOpenPlanned(deliveryPlanning.getQtyTotalOpenPlanned() != null ? deliveryPlanning.getQtyTotalOpenPlanned().toBigDecimal() : null);
	}

	/**
	 * The RECORDS, for the write paths that mutate and save them. Read paths take {@link #getByIds}, which
	 * hands out the model and keeps the records inside this repository.
	 */
	protected ImmutableList<I_M_Delivery_Planning> getRecordsByIds(@NonNull final Set<DeliveryPlanningId> deliveryPlanningIds)
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
		if (!transportDirection.isIncomingOrDropship())
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
		return transportDirection.isIncomingOrDropship()
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
				//
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.build();
	}

	private static void updateRecordFromReceiptInfo(final I_M_Delivery_Planning record, final DeliveryPlanningReceiptInfo from)
	{
		assertHasReceipt(record);
		record.setM_InOut_ID(InOutId.toRepoId(from.getReceiptId()));
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
				//
				.build();
	}

	private static void updateRecordFromShipmentInfo(final I_M_Delivery_Planning record, final DeliveryPlanningShipmentInfo from)
	{
		assertHasOwnShipment(record);
		record.setM_InOut_ID(InOutId.toRepoId(from.getShipmentId()));
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
	 * Writes the actual quantities a completed document booked onto this planning, and marks it
	 * {@code Processed}. Which end(s) a document occupies is a business decision and is made by
	 * {@link DeliveryPlanningService#recordActualQtyOnComplete}, which passes the resolved values in.
	 * <p>
	 * Takes the RECORD, not the id: that same caller has already loaded it to resolve the direction and the
	 * booked quantity, so an id parameter here would make this method load the very same row a second time.
	 *
	 * @param actualLoadQty the load end, or {@code null} to leave it untouched
	 */
	public void recordActualQuantities(
			@NonNull final I_M_Delivery_Planning record,
			@Nullable final BigDecimal actualLoadQty,
			@NonNull final BigDecimal actualDischargeQuantity)
	{
		if (actualLoadQty != null)
		{
			record.setActualLoadQty(actualLoadQty);
		}
		record.setActualDischargeQuantity(actualDischargeQuantity);

		if (!record.isProcessed())
		{
			record.setProcessed(true);
		}

		saveRecord(record);
	}

	/**
	 * Clears {@code Processed} unless the planning is closed, so the invariant
	 * {@code Processed == (IsClosed || IsDelivered)} keeps holding; without it a reversal would leave the planning
	 * permanently {@code Processed}.
	 */
	public void clearActualQtyOnReverse(@NonNull final DeliveryPlanningId deliveryPlanningId, final boolean isReceipt)
	{
		updateById(deliveryPlanningId, deliveryPlanning -> {
			final Quantity zero = deliveryPlanning.getActualDischargeQty().toZero();

			final DeliveryPlanning.DeliveryPlanningBuilder builder = deliveryPlanning.toBuilder();
			if (isReceipt)
			{
				builder.actualDischargeQty(zero);
			}
			else if (hasOwnShipment(deliveryPlanning.getTransportDirection()))
			{
				// The load end goes back to zero - that end IS ours, and after the reversal nothing is loaded. The
				// discharge end goes back to MIRRORING THE PLAN rather than to a literal zero: we never see the
				// customer unload, so on an outgoing planning that column is an assumption from the plan, and a
				// reversed planning is back to being merely planned. Zeroing it would state "nothing was ever
				// reported" on the one end that is never reported at all.
				builder.actualLoadedQty(deliveryPlanning.getActualLoadedQty().toZero())
						.actualDischargeQty(deliveryPlanning.getPlannedDischargeQty());
			}
			else
			{
				// Dropship shipment: unreachable today, mirrors recordActualQtyOnComplete's refusal - see its comment.
				throw new AdempiereException("Dropship planning with its own shipment is not supported yet: " + deliveryPlanning);
			}

			if (!deliveryPlanning.isClosed())
			{
				builder.processed(false);
			}
			return builder.build();
		});
	}

	public <T> T getShipmentOrReceiptInfo(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Function<DeliveryPlanningReceiptInfo, T> receiptInfoMapper,
			@NonNull final Function<DeliveryPlanningShipmentInfo, T> shipmentInfoMapper)
	{
		final I_M_Delivery_Planning record = getById(deliveryPlanningId);
		final TransportDirection transportDirection = extractTransportDirection(record);
		// The two predicates OVERLAP on Dropship, so this ordering decides that case: a dropship planning resolves
		// receipt-side, because it carries no shipment schedule of its own.
		if (transportDirection.isIncomingOrDropship())
		{
			return receiptInfoMapper.apply(toDeliveryPlanningReceiptInfo(record));
		}
		else if (transportDirection.isOutgoingOrDropship())
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

		saveRecord(deliveryPlanningRecord);
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
	 * Every planning of the given order line, fully populated - see {@link #fromRecordBuilder}. This used to
	 * carry only the quantity fields the pool needs, with every other field a placeholder; it no longer does.
	 */
	public DeliveryPlanningList getByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return retrieveForOrderLine(orderLineId)
				.map(DeliveryPlanningRepository::fromRecord)
				.collect(DeliveryPlanningList.collect());
	}

	/**
	 * The ONE record-to-model mapper: sets every field readable off {@code M_Delivery_Planning} itself. Only the
	 * two location fields (which need the batch-loaded addresses) and the allocations (which need the allocation
	 * multimap) are added on top, by {@link DeliveryPlanningService}.
	 * <p>
	 * There used to be three partial variants - one per caller, each carrying "only what my guard reads" and
	 * stamping placeholders into the rest, including a hardcoded {@code TransportDirection.Outgoing} for records
	 * that were frequently Incoming. That is a hazard, not an optimisation: none of them saved a query (all read
	 * the same already-loaded record), every new field had to be hand-copied into all of them, and any
	 * downstream code that began reading a placeholder field would silently get a fabricated value. Adding
	 * {@code IsReadyForReceipt} demonstrated it - one of the four sites was missed on the first pass, and for
	 * that flag the unset value is the restrictive one.
	 * <p>
	 * Does NOT populate {@code allocations} - the alloc rows live in another table and are not read here. So
	 * {@link DeliveryPlanning#isAllocated()} and {@link DeliveryPlanningList#allocatedOnes()} /
	 * {@link DeliveryPlanningList#unallocatedOnes()} answer as if nothing were allocated on any model built
	 * through this path ({@link #getByIds}). Read the stored {@code IsReadyForReceipt} instead, or load the
	 * allocations explicitly; see REPO-REFACTOR-PLAN.md, which tracks closing this properly.
	 */
	static DeliveryPlanning.DeliveryPlanningBuilder fromRecordBuilder(@NonNull final I_M_Delivery_Planning record)
	{
		// C_UOM_ID carries AD_IsMandatory='N' while all five quantity columns carry 'Y'. A mandatory quantity
		// without a UOM is a broken record, so this throws rather than quietly yielding a planning with no
		// quantities - which is what the old partial mappers did by simply omitting them.
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.transportDirection(extractTransportDirection(record))
				.shipperId(ShipperId.ofRepoIdOrNull(record.getM_Shipper_ID()))
				.incotermsId(IncotermsId.ofRepoIdOrNull(record.getC_Incoterms_ID()))
				.incotermLocation(record.getIncotermLocation())
				.meansOfTransportationId(MeansOfTransportationId.ofRepoIdOrNull(record.getM_MeansOfTransportation_ID()))
				.etd(TimeUtil.asInstant(record.getETD()))
				.closed(record.isClosed())
				.processed(record.isProcessed())
				.readyForReceipt(record.isReadyForReceipt())
				.inOutId(InOutId.ofRepoIdOrNull(record.getM_InOut_ID()))
				.shipperTransportationId(ShipperTransportationId.ofRepoIdOrNull(record.getM_ShipperTransportation_ID()))
				.releaseNo(record.getReleaseNo())
				.bpartnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.uomId(uomId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.warehouseId(WarehouseId.ofRepoIdOrNull(record.getM_Warehouse_ID()))
				.orderId(OrderId.ofRepoIdOrNull(record.getC_Order_ID()))
				.orderLineId(OrderLineId.ofRepoIdOrNull(record.getC_OrderLine_ID()))
				.receiptScheduleId(ReceiptScheduleId.ofRepoIdOrNull(record.getM_ReceiptSchedule_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(record.getM_ShipmentSchedule_ID()))
				.originCountryId(CountryId.ofRepoIdOrNull(record.getC_OriginCountry_ID()))
				.destinationCountryId(CountryId.ofRepoIdOrNull(record.getC_DestinationCountry_ID()))
				.ata(TimeUtil.asInstant(record.getATA()))
				.atd(TimeUtil.asInstant(record.getATD()))
				.eta(TimeUtil.asInstant(record.getETA()))
				.loadingTime(record.getLoadingTime())
				.deliveryTime(record.getDeliveryTime())
				.orderStatus(record.getOrderStatus())
				.batch(record.getBatch())
				.wayBillNo(record.getWayBillNo())
				.transportDetails(record.getTransportDetails())
				.qtyTotalOpen(Quantitys.of(record.getQtyTotalOpen(), uomId))
				.qtyTotalOpenPlanned(record.getQtyTotalOpenPlanned() != null ? Quantitys.of(record.getQtyTotalOpenPlanned(), uomId) : null)
				.qtyOrdered(Quantitys.of(record.getQtyOrdered(), uomId))
				.plannedLoadedQty(Quantitys.of(record.getPlannedLoadedQuantity(), uomId))
				.actualLoadedQty(Quantitys.of(record.getActualLoadQty(), uomId))
				.plannedDischargeQty(Quantitys.of(record.getPlannedDischargeQuantity(), uomId))
				.actualDischargeQty(Quantitys.of(record.getActualDischargeQuantity(), uomId));
	}

	static DeliveryPlanning fromRecord(@NonNull final I_M_Delivery_Planning record)
	{
		return fromRecordBuilder(record).build();
	}

	/**
	 * Writes both totals onto EVERY planning of the line: they are order-line totals redundantly displayed per
	 * row, not per-row figures. Not floored at zero - a negative one is the over-planned/over-delivered signal
	 * (D16), not an error to hide.
	 */
	public void recomputeOpenQuantitiesForOrderLine(@NonNull final OrderLineId orderLineId)
	{
		// ONE load: the same records feed the computation and the write-back. Reading a DeliveryPlanningList
		// here and saving it afterwards would fetch every row a second time.
		final ImmutableList<I_M_Delivery_Planning> records = retrieveForOrderLine(orderLineId).collect(ImmutableList.toImmutableList());
		if (records.isEmpty())
		{
			return;
		}

		final DeliveryPlanningList plannings = records.stream().map(DeliveryPlanningRepository::fromRecord).collect(DeliveryPlanningList.collect());

		// Incoming and Dropship both net DISCHARGE, so a line mixing them is computable and must not be rejected:
		// this runs from M_Delivery_Planning's AFTER_* interceptors, where a throw makes such a line unsavable.
		// Asserting one pool end - rather than reading the first row - is what pins it: the query has no ORDER BY.
		final DeliveryPlanningList.PoolEnd end = Check.assumePresent(plannings.getSinglePoolEnd(),
				"Expected every M_Delivery_Planning of orderLineId={} to net one PoolEnd: {}", orderLineId, plannings);

		// One helper on the list rather than two calls plus two locals here: the pair is always written together,
		// so the list is the place that knows how to produce it.
		final DeliveryPlanningList.OpenTotals openTotals = plannings.openTotals(end);
		final UomId uomId = plannings.iterator().next().getQtyOrdered().getUomId();

		for (final I_M_Delivery_Planning record : records)
		{
			applyTo(record, fromRecord(record).toBuilder()
					.qtyTotalOpen(Quantitys.of(openTotals.getQtyTotalOpen(), uomId))
					.qtyTotalOpenPlanned(Quantitys.of(openTotals.getQtyTotalOpenPlanned(), uomId))
					.build());
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
		assertNoneMatches(selectedDeliveryPlanningsFilter, DeliveryPlanning::isClosed, alreadyClosedMessage);

		updateByFilter(selectedDeliveryPlanningsFilter, deliveryPlanning -> deliveryPlanning.toBuilder()
				.closed(true)
				// Processed tracks IsClosed || IsDelivered, so closing always processes
				.processed(true)
				.build());
	}

	/**
	 * The counterpart of {@link #closeSelectedDeliveryPlannings}, all-or-nothing in the same way: a planning that
	 * is still open is refused by name, before anything is written.
	 * <p>
	 * Reads {@code M_InOut_ID} rather than the virtual-column {@code isDelivered()} getter, which only evaluates
	 * against Postgres and would answer {@code false} in a unit test.
	 *
	 * @param stillOpenMessage what to raise for a still-open row - the caller passes the message
	 * 		{@code M_Delivery_Planning_ReOpen}'s precondition uses to keep the button off a mixed selection, so both
	 * 		say it alike. Its {@code {0}} is that row's id.
	 */
	public void reOpenSelectedDeliveryPlannings(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter,
			@NonNull final AdMessageKey stillOpenMessage)
	{
		assertNoneMatches(selectedDeliveryPlanningsFilter, deliveryPlanning -> !deliveryPlanning.isClosed(), stillOpenMessage);

		updateByFilter(selectedDeliveryPlanningsFilter, deliveryPlanning -> deliveryPlanning.toBuilder()
				.closed(false)
				// ... and re-opening only un-processes a planning that has no receipt/shipment of its own
				.processed(deliveryPlanning.getInOutId() != null)
				.build());
	}

	/**
	 * Refuses the WHOLE selection by name before anything is written, so a mixed selection leaves no row
	 * half-changed - the all-or-nothing shape every selection-shaped action here uses.
	 */
	private void assertNoneMatches(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter,
			@NonNull final Predicate<DeliveryPlanning> refuseWhen,
			@NonNull final AdMessageKey message)
	{
		getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter).create().list().stream()
				.map(DeliveryPlanningRepository::fromRecord)
				.filter(refuseWhen)
				.findFirst()
				.ifPresent(deliveryPlanning -> {
					throw new AdempiereException(message, deliveryPlanning.getId().getRepoId());
				});
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

			record.setETD(TimeUtil.asTimestamp(dates.getEtd()));
			record.setATD(TimeUtil.asTimestamp(dates.getAtd()));
			record.setETA(TimeUtil.asTimestamp(dates.getEta()));
			record.setATA(TimeUtil.asTimestamp(dates.getAta()));
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
	 * The given plannings as models. Replaces the former getDeliveredStatePlannings / getProcessedStatePlannings
	 * pair: while three partial mappers existed those two carried different subsets of the record, but with one
	 * mapper they became the same method twice, differing only in name.
	 */
	public DeliveryPlanningList getByIds(@NonNull final Set<DeliveryPlanningId> deliveryPlanningIds)
	{
		return getRecordsByIds(deliveryPlanningIds).stream()
				.map(DeliveryPlanningRepository::fromRecord)
				.collect(DeliveryPlanningList.collect());
	}

	/**
	 * Stamps the given plannings' {@code ReleaseNo}, instruction reference and date fields from the given delivery
	 * instruction, overwriting whatever they carried - a move off another instruction requires it, or two records
	 * would disagree about where the cargo is.
	 */
	public void updateDeliveryPlanningsFromInstruction(
			@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds,
			@NonNull final I_M_ShipperTransportation deliveryInstruction)
	{
		final String created = new SimpleDateFormat("yyyyMMdd-HHmm").format(deliveryInstruction.getCreated());

		updateByIds(ImmutableSet.copyOf(deliveryPlanningIds), deliveryPlanning -> deliveryPlanning.toBuilder()
				// the release number embeds the planning's OWN id, which is why this is a per-model transform
				// and not a set-based update of one constant
				.releaseNo(deliveryInstruction.getDocumentNo() + "-" + deliveryPlanning.getId().getRepoId() + "-" + created)
				.shipperTransportationId(ShipperTransportationId.ofRepoId(deliveryInstruction.getM_ShipperTransportation_ID()))
				.etd(TimeUtil.asInstant(deliveryInstruction.getETD()))
				.eta(TimeUtil.asInstant(deliveryInstruction.getETA()))
				.atd(TimeUtil.asInstant(deliveryInstruction.getATD()))
				.ata(TimeUtil.asInstant(deliveryInstruction.getATA()))
				.loadingTime(deliveryInstruction.getLoadingTime())
				.deliveryTime(deliveryInstruction.getDeliveryTime())
				.build());
	}

	/**
	 * Clears the given plannings' {@code ReleaseNo} and instruction reference: they are on no delivery instruction
	 * any more, and are therefore planable onto one again.
	 */
	public void clearInstructionReference(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		updateByIds(ImmutableSet.copyOf(deliveryPlanningIds), CLEAR_INSTRUCTION_REFERENCE);
	}

	public void clearInstructionReferenceOfInstruction(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		updateByIds(getPlanningIdsOfInstruction(deliveryInstructionId), CLEAR_INSTRUCTION_REFERENCE);
	}

	/**
	 * The records matching a selection filter, streamed rather than materialised - the callers walk a whole view
	 * selection, which can be large.
	 */
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

	/** The ids of every planning currently stamped with the given instruction. */
	private ImmutableSet<DeliveryPlanningId> getPlanningIdsOfInstruction(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return ImmutableSet.copyOf(queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionId)
				.create()
				.listIds(DeliveryPlanningId::ofRepoId));
	}

	/**
	 * Zeroes the planned quantities only when the planning is not currently allocated to a delivery instruction
	 * (D8/D19). The actual quantities are never written here, allocated or not - a booked receipt or shipment is
	 * history, not a plan.
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
		saveRecord(deliveryPlanningRecord);
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
		// the Quantity carries its own UOM, so C_UOM_ID no longer has to be remembered alongside it
		updateById(deliveryPlanningId, deliveryPlanning -> deliveryPlanning.toBuilder()
				.plannedLoadedQty(quantity)
				.uomId(quantity.getUomId())
				.build());
	}

	public void setPlannedDischargeQuantity(@NonNull final DeliveryPlanningId deliveryPlanningId, @NonNull final Quantity quantity)
	{
		updateById(deliveryPlanningId, deliveryPlanning -> deliveryPlanning.toBuilder()
				.plannedDischargeQty(quantity)
				.uomId(quantity.getUomId())
				.build());
	}

}
