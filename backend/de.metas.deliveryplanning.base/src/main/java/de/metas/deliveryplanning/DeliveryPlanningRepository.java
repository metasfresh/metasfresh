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
import de.metas.document.engine.DocStatus;
import de.metas.incoterms.IncotermsId;
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
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
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
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Alloc;
import org.compiere.model.I_M_Package;
import org.compiere.model.X_M_Delivery_Planning;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
 * Repository Tables: M_Delivery_Planning, M_Delivery_Planning_Alloc, M_ShipperTransportation, M_ShippingPackage, M_Package
 * Repository Cluster: DeliveryPlanningRepository (sole owner of M_Delivery_Planning_Alloc; primary owner of
 * M_Delivery_Planning, which DeliveryPlanningImportProcess also writes directly), ShipperTransportationDAO,
 * PurchaseOrderToShipperTransportationRepository, MPackageRepository (the three transport and packing tables are
 * shared with the transport-order role, which knows nothing of delivery planning)
 */
@Repository
public class DeliveryPlanningRepository
{
	/**
	 * The step between two consecutive allocation {@code LineNo}s, following the house convention for document lines.
	 */
	private static final int ALLOCATION_LINE_NO_STEP = 10;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

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
	 * The records of the given delivery plannings, in ONE round trip, in the order the ids were given.
	 * <p>
	 * The order is the caller's because it is the order the allocations are numbered in; the encounter order of a
	 * query is not one. Deliberately unfiltered by {@code IsActive}, exactly like the single-row
	 * {@link #getById(DeliveryPlanningId)} it replaces: a selection can legitimately name a closed planning, and
	 * whether that is admissible is a rule of its own, not something a loader may decide by dropping the row.
	 *
	 * @throws AdempiereException for an id with no matching row. A resolved id without a record is a DANGLING
	 * 		reference - which {@code getById} also throws for - and not a planning that may be quietly dropped from
	 * 		the result, which is what a bare map lookup would silently turn it into.
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
		final I_M_Delivery_Planning record = recordsById.get(deliveryPlanningId);
		if (record == null)
		{
			throw new AdempiereException("No " + I_M_Delivery_Planning.Table_Name + " found")
					.appendParametersToMessage()
					.setParameter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId.getRepoId());
		}
		return record;
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
		return transportDirection.hasShipment() && !transportDirection.isDropship();
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
		deliveryPlanningRecord.setTransportDirection(TransportDirection.toCodeOrNull(request.getTransportDirection()));

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

	public void closeSelectedDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_IsClosed, false)
				.create()
				.iterate(I_M_Delivery_Planning.class);

		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();
			deliveryPlanningRecord.setIsClosed(true);
			deliveryPlanningRecord.setProcessed(true);
			save(deliveryPlanningRecord);
		}
	}

	public void reOpenSelectedDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_IsClosed, true)
				.create()
				.iterate(I_M_Delivery_Planning.class);

		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();
			deliveryPlanningRecord.setIsClosed(false);
			deliveryPlanningRecord.setProcessed(false);
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

		createAllocations(deliveryInstructionRecord, ImmutableList.of(toAllocCreateRequest(request)));

		return deliveryInstructionRecord;
	}

	private static DeliveryPlanningAllocCreateRequest toAllocCreateRequest(@NonNull final DeliveryInstructionCreateRequest request)
	{
		return DeliveryPlanningAllocCreateRequest.builder()
				.deliveryPlanningId(request.getDeliveryPlanningId())
				.productId(request.getProductId())
				.qtyLoaded(request.getQtyLoaded())
				.qtyDischarged(request.getQtyDischarged())
				.batchNo(request.getBatchNo())
				.orderLineId(request.getOrderLineId())
				.toBeFetched(request.isToBeFetched())
				.build();
	}

	/**
	 * Allocates the given delivery plannings to the given delivery instruction, each with its own
	 * {@code M_ShippingPackage}.
	 * <p>
	 * The {@code LineNo}s continue in tens after the instruction's highest existing one, following the order of
	 * {@code requests} - so a caller that wants a particular print order has to hand the requests over already
	 * sorted; the encounter order of a query is not one.
	 */
	public ImmutableList<DeliveryPlanningAllocId> createAllocations(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final List<DeliveryPlanningAllocCreateRequest> requests)
	{
		return createAllocations(load(deliveryInstructionId, I_M_ShipperTransportation.class), requests);
	}

	private ImmutableList<DeliveryPlanningAllocId> createAllocations(
			@NonNull final I_M_ShipperTransportation deliveryInstructionRecord,
			@NonNull final List<DeliveryPlanningAllocCreateRequest> requests)
	{
		final ShipperTransportationId deliveryInstructionId = ShipperTransportationId.ofRepoId(deliveryInstructionRecord.getM_ShipperTransportation_ID());

		int lineNo = getMaxAllocationLineNo(deliveryInstructionId);

		final ImmutableList.Builder<DeliveryPlanningAllocId> allocIds = ImmutableList.builder();
		for (final DeliveryPlanningAllocCreateRequest request : requests)
		{
			lineNo += ALLOCATION_LINE_NO_STEP;
			allocIds.add(createAllocation(deliveryInstructionRecord, request, lineNo));
		}
		return allocIds.build();
	}

	private DeliveryPlanningAllocId createAllocation(
			@NonNull final I_M_ShipperTransportation deliveryInstructionRecord,
			@NonNull final DeliveryPlanningAllocCreateRequest request,
			final int lineNo)
	{
		// M_ShippingPackage_ID is mandatory on the allocation and uniquely indexed, so the package exists first
		final I_M_ShippingPackage shippingPackageRecord = createShippingPackage(deliveryInstructionRecord, request);

		final I_M_Delivery_Planning_Alloc allocRecord = newInstance(I_M_Delivery_Planning_Alloc.class);
		allocRecord.setAD_Org_ID(deliveryInstructionRecord.getAD_Org_ID());
		allocRecord.setM_Delivery_Planning_ID(request.getDeliveryPlanningId().getRepoId());
		allocRecord.setM_ShipperTransportation_ID(deliveryInstructionRecord.getM_ShipperTransportation_ID());
		allocRecord.setM_ShippingPackage_ID(shippingPackageRecord.getM_ShippingPackage_ID());
		allocRecord.setLineNo(lineNo);
		saveRecord(allocRecord);

		return DeliveryPlanningAllocId.ofRepoId(allocRecord.getM_Delivery_Planning_Alloc_ID());
	}

	private static I_M_ShippingPackage createShippingPackage(
			@NonNull final I_M_ShipperTransportation deliveryInstructionRecord,
			@NonNull final DeliveryPlanningAllocCreateRequest request)
	{
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
		shippingPackageRecord.setIsToBeFetched(request.isToBeFetched());
		shippingPackageRecord.setM_Product_ID(request.getProductId().getRepoId());

		shippingPackageRecord.setActualDischargeQuantity(request.getQtyDischarged().toBigDecimal());
		shippingPackageRecord.setActualLoadQty(request.getQtyLoaded().toBigDecimal());
		shippingPackageRecord.setBatch(request.getBatchNo());
		shippingPackageRecord.setC_UOM_ID(request.getQtyLoaded().getUomId().getRepoId());

		shippingPackageRecord.setC_BPartner_ID(shipperBPartnerId);
		shippingPackageRecord.setC_BPartner_Location_ID(shipperLocationId);

		shippingPackageRecord.setC_OrderLine_ID(OrderLineId.toRepoId(request.getOrderLineId()));

		saveRecord(shippingPackageRecord);

		return shippingPackageRecord;
	}

	/**
	 * On remove from the instruction, and on the source half of a move: the allocations of the given plannings and
	 * the shipping packages they point at are deleted, not deactivated, so nothing survives to say the cargo was
	 * ever on that document.
	 * <p>
	 * Only active allocations are touched: a deactivated one records an instruction that was voided, and voiding
	 * is not what the planner is undoing here.
	 * <p>
	 * "A planning cannot be removed from a completed instruction" is enforced entirely at the service layer, by
	 * each caller's own rejection-reason check BEFORE this method is ever called -
	 * {@link DeliveryPlanningService#getRemoveFromRejectionReason} for the remove-from path,
	 * {@link DeliveryPlanningService#getAddToRejectionReason} for the source half of a move. The allocation itself
	 * carries no status to check here: it is not a document.
	 */
	public void deleteAllocations(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return;
		}

		final List<I_M_Delivery_Planning_Alloc> allocRecords = queryAllocationsByPlanningIds(deliveryPlanningIds).create().list();
		final ImmutableMap<Integer, I_M_ShippingPackage> shippingPackages = getShippingPackagesOf(allocRecords);

		for (final I_M_Delivery_Planning_Alloc allocRecord : allocRecords)
		{
			final I_M_ShippingPackage shippingPackageRecord = shippingPackages.get(allocRecord.getM_ShippingPackage_ID());

			// the allocation references the package, so it goes first
			InterfaceWrapperHelper.delete(allocRecord);
			InterfaceWrapperHelper.delete(shippingPackageRecord);
		}
	}

	/**
	 * On void or cancel of the delivery instruction: the allocations and their shipping packages are deactivated
	 * rather than deleted, so the record of what was once planned survives.
	 * <p>
	 * {@code IsActive='N'} is also what releases both partial unique indexes on the allocation, so the plannings
	 * can be allocated again afterwards.
	 */
	public void deactivateAllocations(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final List<I_M_Delivery_Planning_Alloc> allocRecords = queryActiveAllocationsByInstructionId(deliveryInstructionId).create().list();
		final ImmutableMap<Integer, I_M_ShippingPackage> shippingPackages = getShippingPackagesOf(allocRecords);

		for (final I_M_Delivery_Planning_Alloc allocRecord : allocRecords)
		{
			final I_M_ShippingPackage shippingPackageRecord = shippingPackages.get(allocRecord.getM_ShippingPackage_ID());
			shippingPackageRecord.setIsActive(false);
			saveRecord(shippingPackageRecord);

			allocRecord.setIsActive(false);
			saveRecord(allocRecord);
		}
	}

	/**
	 * The shipping packages the given allocations point at, keyed by id, in one round trip.
	 * <p>
	 * Every allocation has one - {@code M_ShippingPackage_ID} is mandatory and foreign-keyed - so a lookup in the
	 * result never misses.
	 */
	private ImmutableMap<Integer, I_M_ShippingPackage> getShippingPackagesOf(@NonNull final List<I_M_Delivery_Planning_Alloc> allocRecords)
	{
		if (allocRecords.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSet<Integer> shippingPackageIds = allocRecords.stream()
				.map(I_M_Delivery_Planning_Alloc::getM_ShippingPackage_ID)
				.collect(ImmutableSet.toImmutableSet());

		return queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addInArrayFilter(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID, shippingPackageIds)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(I_M_ShippingPackage::getM_ShippingPackage_ID, shippingPackage -> shippingPackage));
	}

	/**
	 * The delivery instruction each of the given plannings is currently allocated to. A planning without an active
	 * allocation is absent from the result.
	 * <p>
	 * One key per planning is safe because {@code M_Delivery_Planning_Alloc_Planning_UQ} permits only one active
	 * allocation per planning.
	 */
	public ImmutableMap<DeliveryPlanningId, ShipperTransportationId> getAllocatedInstructionIds(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		return queryAllocationsByPlanningIds(deliveryPlanningIds)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						allocRecord -> DeliveryPlanningId.ofRepoId(allocRecord.getM_Delivery_Planning_ID()),
						allocRecord -> ShipperTransportationId.ofRepoId(allocRecord.getM_ShipperTransportation_ID())));
	}

	/**
	 * The delivery plannings the given instruction currently holds, in ONE round trip - the other direction of
	 * {@link #getAllocatedInstructionIds(Collection)}.
	 * <p>
	 * ACTIVE allocations only, which is what "currently holds" means: a deactivated allocation is what a voided
	 * instruction leaves behind, and its planning is no longer cargo of this document.
	 */
	public ImmutableSet<DeliveryPlanningId> getAllocatedPlanningIds(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return queryActiveAllocationsByInstructionId(deliveryInstructionId)
				.create()
				.stream()
				.map(allocRecord -> DeliveryPlanningId.ofRepoId(allocRecord.getM_Delivery_Planning_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * The {@code DocStatus} of each of the given delivery instructions, in one round trip.
	 * <p>
	 * The allocation itself carries no {@code DocStatus} - it is not a document - so this is read from the
	 * instruction directly, the only authority on whether it is still a draft.
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
	 * Stamps the given plannings' {@code ReleaseNo} and instruction reference from the given delivery instruction.
	 * <p>
	 * Whatever they carried before is overwritten, which is what a move off another instruction requires: the old
	 * release number names a document the cargo is no longer on, so keeping it would leave two records disagreeing
	 * about where the cargo is.
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
	 * that has just created the instruction and therefore already holds its record.
	 * <p>
	 * The plannings are loaded in ONE round trip: a whole grid selection is stamped here, and the planner is
	 * waiting on it.
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
	 * <p>
	 * The allocation is not touched here - {@link #deleteAllocations(Collection)} is what removes it, and this is
	 * the planning-side half of the same removal.
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

	private int getMaxAllocationLineNo(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		// deliberately not filtered by IsActive: a deactivated allocation's LineNo stays taken, so a later
		// allocation on the same instruction never reuses a number that was already printed
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionId)
				.create()
				.maxInt(I_M_Delivery_Planning_Alloc.COLUMNNAME_LineNo);
	}

	private IQueryBuilder<I_M_Delivery_Planning_Alloc> queryAllocationsByPlanningIds(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningIds);
	}

	private IQueryBuilder<I_M_Delivery_Planning_Alloc> queryActiveAllocationsByInstructionId(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionId);
	}

	private static DocStatus extractDocStatus(@NonNull final I_M_ShipperTransportation deliveryInstructionRecord)
	{
		return DocStatus.ofNullableCodeOrUnknown(deliveryInstructionRecord.getDocStatus());
	}

	/**
	 * Deliberately takes the already-loaded record and is PRIVATE: the only way in from outside is
	 * {@link #updateDeliveryPlanningsFromInstruction(Collection, I_M_ShipperTransportation)}, which loads its whole
	 * argument in ONE round trip. There is no id-taking public counterpart on purpose - the last one was reached
	 * for from a loop over a collection, which is precisely the per-row load the collection method exists to
	 * prevent, and a javadoc saying "use the batch version" is what failed to stop it.
	 */
	private static void updateDeliveryPlanningFromInstruction(@NonNull final I_M_Delivery_Planning deliveryPlanningRecord,
			@NonNull final I_M_ShipperTransportation deliveryInstruction)
	{
		final String created = new SimpleDateFormat("yyyyMMdd-HHmm").format(deliveryInstruction.getCreated());
		deliveryPlanningRecord.setReleaseNo(deliveryInstruction.getDocumentNo() + "-"
													+ deliveryPlanningRecord.getM_Delivery_Planning_ID()
													+ "-" + created);
		deliveryPlanningRecord.setM_ShipperTransportation_ID(deliveryInstruction.getM_ShipperTransportation_ID());
		saveRecord(deliveryPlanningRecord);
	}

	public Iterator<I_M_Delivery_Planning> extractDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.create()
				.iterate(I_M_Delivery_Planning.class);
	}

	@NonNull
	public IQueryBuilder<I_M_Delivery_Planning> getDeliveryPlanningQueryBuilder(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
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

	public void unlinkDeliveryPlannings(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		deactivateAllocations(deliveryInstructionId);

		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = retrieveForDeliveryInstructionId(deliveryInstructionId);
		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();
			deliveryPlanningRecord.setReleaseNo(null);
			deliveryPlanningRecord.setM_ShipperTransportation_ID(-1);
			saveRecord(deliveryPlanningRecord);

			shipperTransportationDAO.retrieveShippingPackages(deliveryInstructionId)
					.forEach(this::unlinkShippingPackage);

		}
	}

	private void unlinkShippingPackage(@NonNull final I_M_ShippingPackage shippingPackage)
	{
		shippingPackage.setC_OrderLine_ID(-1);
		save(shippingPackage);
	}

	public Iterator<I_M_ShipperTransportation> retrieveForDeliveryPlanning(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final Collection<ShipperTransportationId> deliveryInstructionIds = getAllocatedInstructionIds(ImmutableList.of(deliveryPlanningId)).values();

		return queryBL.createQueryBuilder(I_M_ShipperTransportation.class)
				.addInArrayFilter(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionIds)
				.create()
				.iterate(I_M_ShipperTransportation.class);
	}

	public void cancelSelectedDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final ICompositeQueryFilter<I_M_Delivery_Planning> dpFilter = queryBL
				.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.setJoinAnd()
				.addFilter(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_IsClosed, false);

		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = extractDeliveryPlannings(dpFilter);

		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();
			deliveryPlanningRecord.setIsClosed(true);
			deliveryPlanningRecord.setProcessed(true);
			deliveryPlanningRecord.setOrderStatus(X_M_Delivery_Planning.ORDERSTATUS_Canceled);
			deliveryPlanningRecord.setPlannedLoadedQuantity(BigDecimal.ZERO);
			deliveryPlanningRecord.setPlannedDischargeQuantity(BigDecimal.ZERO);
			deliveryPlanningRecord.setActualLoadQty(BigDecimal.ZERO);
			save(deliveryPlanningRecord);
		}
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

	public boolean hasCompleteDeliveryInstruction(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final Collection<ShipperTransportationId> deliveryInstructionIds = getAllocatedInstructionIds(ImmutableList.of(deliveryPlanningId)).values();

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

}
