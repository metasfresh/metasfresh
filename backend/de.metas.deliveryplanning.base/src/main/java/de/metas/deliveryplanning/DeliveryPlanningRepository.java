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
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.ShippingPackageId;
import de.metas.util.Check;
import de.metas.util.ColorId;
import de.metas.util.Services;
import java.time.Instant;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
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

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
	 * The records of the given delivery plannings, in ONE round trip, in the caller's id order - the order the
	 * allocations are numbered in. Unfiltered by {@code IsActive}: a selection can legitimately name a closed planning.
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
	 * All-or-nothing over the selection: an already-closed planning is refused by name, and the check runs before
	 * anything is written, so a mixed selection leaves no row half-closed.
	 * <p>
	 * The runtime backstop behind {@code M_Delivery_Planning_Close}'s precondition, which refuses the same
	 * selection before the button is offered. It raises the SAME message the precondition does, so a planner who
	 * reaches this far - the process can be invoked past its precondition - reads the same sentence rather than a
	 * developer token carrying a record's {@code toString()}.
	 */
	public void closeSelectedDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final List<I_M_Delivery_Planning> deliveryPlanningRecords = getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.create()
				.list();

		for (final I_M_Delivery_Planning deliveryPlanningRecord : deliveryPlanningRecords)
		{
			if (deliveryPlanningRecord.isClosed())
			{
				throw new AdempiereException(
						DeliveryPlanningService.MSG_M_Delivery_Planning_Closed,
						deliveryPlanningRecord.getM_Delivery_Planning_ID());
			}
		}

		for (final I_M_Delivery_Planning deliveryPlanningRecord : deliveryPlanningRecords)
		{
			deliveryPlanningRecord.setIsClosed(true);
			deliveryPlanningRecord.setProcessed(true);
			save(deliveryPlanningRecord);
		}
	}

	/**
	 * The counterpart of {@link #closeSelectedDeliveryPlannings}, all-or-nothing in the same way: a planning that
	 * is still open is refused by name, before anything is written, and with the same message
	 * {@code M_Delivery_Planning_ReOpen}'s precondition uses to keep the button off a mixed selection.
	 */
	public void reOpenSelectedDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final List<I_M_Delivery_Planning> deliveryPlanningRecords = getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.create()
				.list();

		for (final I_M_Delivery_Planning deliveryPlanningRecord : deliveryPlanningRecords)
		{
			if (!deliveryPlanningRecord.isClosed())
			{
				throw new AdempiereException(
						DeliveryPlanningService.MSG_M_Delivery_Planning_Open,
						deliveryPlanningRecord.getM_Delivery_Planning_ID());
			}
		}

		for (final I_M_Delivery_Planning deliveryPlanningRecord : deliveryPlanningRecords)
		{
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
				.productId(request.getProductId())
				.qtyLoaded(request.getQtyLoaded())
				.qtyDischarged(request.getQtyDischarged())
				.batchNo(request.getBatchNo())
				.orderLineId(request.getOrderLineId())
				.orderId(request.getOrderId())
				.toBeFetched(request.isToBeFetched())
				.etd(TimeUtil.asTimestamp(request.getLoadingDate()))
				.eta(TimeUtil.asTimestamp(request.getDeliveryDate()))
				.loadingTime(request.getLoadingTime())
				.deliveryTime(request.getDeliveryTime())
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
		shippingPackageRecord.setC_Order_ID(OrderId.toRepoId(request.getOrderId()));

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
	 */
	private DeactivatedAllocations deactivateAllocationRecords(
			@NonNull final List<I_M_Delivery_Planning_Alloc> allocRecords,
			@NonNull final Instant removedAt)
	{
		final ImmutableMap<ShippingPackageId, I_M_ShippingPackage> shippingPackages = getShippingPackagesOf(allocRecords);

		final ImmutableList.Builder<I_M_ShippingPackage> deactivatedShippingPackages = ImmutableList.builder();
		final ImmutableSet.Builder<DeliveryPlanningId> deallocatedPlanningIds = ImmutableSet.builder();
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
		}

		return DeactivatedAllocations.builder()
				.shippingPackages(deactivatedShippingPackages.build())
				.deallocatedPlanningIds(deallocatedPlanningIds.build())
				.build();
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
	 * The delivery plannings the given instruction currently holds, as ids.
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
	 * Cancels ONE delivery planning: closes it, marks it processed, sets its order status to {@code Canceled} and
	 * zeroes its planned/actual quantities. The caller decides per row which plannings are eligible.
	 */
	public void cancelDeliveryPlanning(@NonNull final I_M_Delivery_Planning deliveryPlanningRecord)
	{
		deliveryPlanningRecord.setIsClosed(true);
		deliveryPlanningRecord.setProcessed(true);
		deliveryPlanningRecord.setOrderStatus(X_M_Delivery_Planning.ORDERSTATUS_Canceled);
		deliveryPlanningRecord.setPlannedLoadedQuantity(BigDecimal.ZERO);
		deliveryPlanningRecord.setPlannedDischargeQuantity(BigDecimal.ZERO);
		deliveryPlanningRecord.setActualLoadQty(BigDecimal.ZERO);
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

}
