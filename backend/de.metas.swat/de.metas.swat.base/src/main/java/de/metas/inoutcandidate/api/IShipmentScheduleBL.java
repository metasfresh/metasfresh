package de.metas.inoutcandidate.api;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.shippingnotification.ShippingNotificationFromShipmentScheduleProducer;
import de.metas.order.OrderId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.storage.IStorageQuery;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IShipmentScheduleBL extends ISingletonService
{
	String MSG_ShipmentSchedules_To_Recompute = "ShipmentSchedules_To_Recompute";

	/**
	 * Please use this method to avoid unneeded work packages.
	 *
	 * @return {@code true} if the caller can (and should) skip scheduling a workpackage processor.
	 */
	boolean allMissingSchedsWillBeCreatedLater();

	/**
	 * Use this closable in a try-with-resource block if you create a number of shipment-schedule related records and want to avoid some model
	 * interceptors etc to schedule one {@link CreateMissingShipmentSchedulesWorkpackageProcessor} for each of them.<br>
	 * The closable will schedule one work package at the end.
	 * <p>
	 * Note:
	 * <li>This behavior is "thread-local"
	 * <li>calling this method multiple times within the same stack is no problem.
	 */
	IAutoCloseable postponeMissingSchedsCreationUntilClose();

	void updateCapturedLocationsAndRenderedAddresses(I_M_ShipmentSchedule sched);

	/**
	 * Returns the UOM of QtyOrdered, QtyToDeliver, QtyPicked etc (i.e. the stock UOM)
	 */
	I_C_UOM getUomOfProduct(I_M_ShipmentSchedule sched);

	UomId getUomIdOfProduct(I_M_ShipmentSchedule sched);

	/**
	 * Evaluates if the given shipment schedule's order and effective bPartner allow that different orders' schedules to go into one and the same shipment.
	 * <p>
	 * <b>IMPORTANT</b> this column does not evaluate the actual schedule's own {@link I_M_ShipmentSchedule#isAllowConsolidateInOut()} value. As of now, that flag is only for the user's information.
	 */
	boolean isSchedAllowsConsolidate(I_M_ShipmentSchedule sched);

	/**
	 * Creates a new aggregation key builder which can be used to decide if two given shipment schedules can go into the same shipment.
	 */
	ShipmentScheduleHeaderAggregationKeyBuilder mkShipmentHeaderAggregationKeyBuilder();

	void updateHeaderAggregationKey(I_M_ShipmentSchedule sched);

	/**
	 * If the given <code>shipmentSchedule</code> has its {@link I_M_ShipmentSchedule#COLUMN_QtyOrdered_Override QtyOrdered_Override} set, then override its <code>QtyOrdered</code> value with it. If
	 * QtyOrdered_Override is <code>null</code>, then reset <code>QtyOrdered</code> to the value of <code>QtyOrdered_Calculated</code>.
	 * <p>
	 * Task 08255
	 */
	void updateQtyOrdered(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Close the given Shipment Schedule.
	 * <p>
	 * Closing a shipment schedule means overriding its QtyOrdered to the qty which was already delivered.
	 */
	void closeShipmentSchedule(I_M_ShipmentSchedule schedule);

	void closeShipmentSchedule(ShipmentScheduleId shipmentScheduleId);

	void closeShipmentSchedules(@NonNull Set<ShipmentScheduleId> shipmentScheduleIds);

	/**
	 * Reopen the closed shipment schedule given as parameter
	 */
	void openShipmentSchedule(I_M_ShipmentSchedule shipmentScheduleRecord);

	void closeShipmentSchedulesFor(ImmutableList<TableRecordReference> orderLineRecordRefs);

	void openShipmentSchedulesFor(ImmutableList<TableRecordReference> recordRefs);

	/**
	 * Used by a model interceptor to figure out if the given {@code shipmentSchedule}'s {@code IsClosed} value is jsut cange from {@code true} to {@code false}.
	 */
	boolean isJustOpened(I_M_ShipmentSchedule shipmentScheduleRecord);

	/**
	 * Creates a storage query for the given {@code shipmentSchedule}.
	 *
	 * @param considerAttributes {@code true} if the query shall be strict with respect to the given {@code shipmentSchedule}'s ASI.
	 * @param excludeAllReserved if {@code true}, then even exclude HUs that are reserved to the given {@code shipmentSchedule}'s order line itself.
	 */
	IStorageQuery createStorageQuery(I_M_ShipmentSchedule shipmentSchedule, boolean considerAttributes, boolean excludeAllReserved);

	@NonNull
	Quantity getQtyToDeliver(I_M_ShipmentSchedule shipmentScheduleRecord);

	Optional<Quantity> getCatchQtyOverride(I_M_ShipmentSchedule shipmentScheduleRecord);

	void resetCatchQtyOverride(I_M_ShipmentSchedule shipmentSchedule);

	void updateCatchUoms(ProductId productId, long delayMs);

	I_M_ShipmentSchedule getById(ShipmentScheduleId id);

	Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIds(Set<ShipmentScheduleId> ids);

	Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIdsOutOfTrx(Set<ShipmentScheduleId> ids);

	<T extends I_M_ShipmentSchedule> Map<ShipmentScheduleId, T> getByIdsOutOfTrx(Set<ShipmentScheduleId> ids, Class<T> modelType);

	Collection<I_M_ShipmentSchedule> getByOrderId(@NonNull OrderId orderId);

	Collection<I_M_ShipmentSchedule> getByOrderIds(@NonNull Collection<OrderId> orderIds);

	boolean anyMatchByOrderId(OrderId salesOrderId);

	boolean anyMatchByOrderIds(@NonNull Collection<OrderId> orderIds);

	BPartnerId getBPartnerId(I_M_ShipmentSchedule schedule);

	BPartnerLocationId getBPartnerLocationId(I_M_ShipmentSchedule schedule);

	WarehouseId getWarehouseId(I_M_ShipmentSchedule schedule);

	ZonedDateTime getPreparationDate(I_M_ShipmentSchedule schedule);

	ShipmentAllocationBestBeforePolicy getBestBeforePolicy(ShipmentScheduleId id);

	void applyUserChangesInTrx(ShipmentScheduleUserChangeRequestsList userChanges);

	IAttributeSetInstanceAware toAttributeSetInstanceAware(I_M_ShipmentSchedule shipmentSchedule);

	void applyShipmentScheduleChanges(ApplyShipmentScheduleChangesRequest request);

	boolean isDoNotInvalidateOnChange(@NonNull I_M_ShipmentSchedule sched);

	/**
	 * Close linked shipment schedules if they were partially invoiced
	 * Note: This behavior is determined by the value of the sys config {@code M_ShipmentSchedule_Close_PartiallyInvoice}.
	 * The scheds will be closed only if the sys config is set to 'Y'
	 */
	void closePartiallyShipped_ShipmentSchedules(I_M_InOut inoutRecord);

	void updateExportStatus(@NonNull I_M_ShipmentSchedule schedRecord);

	void updateCanBeExportedAfter(@NonNull final I_M_ShipmentSchedule schedRecord);

	Quantity getQtyOrdered(I_M_ShipmentSchedule shipmentScheduleRecord);

	Quantity getQtyDelivered(I_M_ShipmentSchedule shipmentScheduleRecord);

	void updateExportStatus(@NonNull final APIExportStatus newExportStatus, @NonNull final PInstanceId pinstanceId);

	void setAsyncBatch(ShipmentScheduleId shipmentScheduleId, AsyncBatchId asyncBatchId);

	ImmutableSet<OrderId> getOrderIds(@NonNull IQueryFilter<? extends I_M_ShipmentSchedule> filter);

	ShippingNotificationFromShipmentScheduleProducer newShippingNotificationProducer();

	void setPhysicalClearanceDate(@NonNull Set<ShipmentScheduleId> shipmentScheduleIds, @Nullable Instant physicalClearanceDate);
}
