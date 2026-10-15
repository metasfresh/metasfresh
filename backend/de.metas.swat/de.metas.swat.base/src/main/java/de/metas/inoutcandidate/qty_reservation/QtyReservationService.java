package de.metas.inoutcandidate.qty_reservation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.QtyTU;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QtyReservationService
{
	private static final String SYSCONFIG_COPY_STORAGE_RELEVANT_ATTRS_TO_ORDER_LINE_ASI = "de.metas.handlingunits.order.CopyStorageRelevantAttributesToOrderLineASI";
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL;
	@NonNull private final QtyReservationRepository repository;

	public QtyReservation getById(@NonNull QtyReservationId id)
	{
		return repository.getById(id);
	}

	public QtyReservationId makeReservation(@NonNull final CreateQtyReservationRequest request)
	{
		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(request.getOrderAndLineId());
		final ProductId orderLineProductId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		if (!ProductId.equals(request.getProductId(), orderLineProductId))
		{
			throw new AdempiereException("Product mismatch: reservation product "
					+ request.getProductId().getRepoId()
					+ " != order line product "
					+ orderLineProductId.getRepoId());
		}

		final QtyReservationId qtyReservationId = repository.createReservation(request);

		boolean orderLineChanged = false;
		if (request.getAttributesKey() != null && sysConfigBL.getBooleanValue(SYSCONFIG_COPY_STORAGE_RELEVANT_ATTRS_TO_ORDER_LINE_ASI, false))
		{
			// the attributesKey already contains only storage relevant attributes
			// attributesKey may be NONE, in which case M_AttributeSetInstance_ID is reset to 0 (clearing existing ASI)
			final AttributeSetInstanceId attributeSetInstanceFromAttributesKey = AttributesKeys.createAttributeSetInstanceFromAttributesKey(request.getAttributesKey());
			orderLine.setM_AttributeSetInstance_ID(attributeSetInstanceFromAttributesKey.getRepoId());
			orderLineChanged = true;
		}
		if (request.getProjectId() != null)
		{
			// assume the ProjectValue attribute will be automatically set/updated in ASI
			orderLine.setC_Project_ID(request.getProjectId().getRepoId());
			orderLineChanged = true;
		}
		if (orderLineChanged)
		{
			orderLineBL.save(orderLine);
		}

		invalidateShipmentSchedulesForSalesOrderLine(orderLine);

		return qtyReservationId;
	}

	public void deleteReservation(@NonNull final DeleteQtyReservationRequest request)
	{
		final boolean deleted = repository.deleteReservation(request);

		if (deleted)
		{
			invalidateShipmentSchedulesForSalesOrderLineId(request.getOrderAndLineId());
		}
	}

	/**
	 * Partially reduces the reservation of one bucket (order line + supply type + date promised) by
	 * {@code request.qtyToUnreserveTU} TUs, spread across the bucket's rows in reservation-id order.
	 * A row is never shrunk below its already-delivered qty; rows that reach zero are deleted.
	 * Shipment schedules of the order line are invalidated afterwards.
	 * <p>
	 * Like {@link #deleteReservation}, this assumes an ambient (caller-provided) transaction: the update and the delete are
	 * not wrapped here.
	 */
	public void reduceReservation(@NonNull final ReduceQtyReservationRequest request)
	{
		final ImmutableList<QtyReservation> rows = repository.getActiveByBucket(
				request.getOrderAndLineId(), request.getSupplyType(), request.getDatePromised());

		final int totalReversibleTU = sumReversibleTU(rows);
		int remainingTU = request.getQtyToUnreserveTU().toInt();
		if (remainingTU > totalReversibleTU)
		{
			throw new AdempiereException("Cannot unreserve " + remainingTU + " TU: only " + totalReversibleTU
					+ " TU of the reservation are reversible (the rest is already delivered)")
					.setParameter("request", request);
		}

		final Map<QtyReservationId, QtyReservationShrinker.Result> resultsById = new HashMap<>();
		for (final QtyReservation row : rows)
		{
			if (remainingTU <= 0)
			{
				break;
			}
			final int takeTU = Math.min(remainingTU, QtyReservationShrinker.getReversibleTU(row));
			if (takeTU <= 0)
			{
				continue; // nothing reducible on this row (e.g. fully delivered)
			}

			resultsById.put(row.getId(), QtyReservationShrinker.shrinkByTU(row, takeTU));
			remainingTU -= takeTU;
		}

		if (resultsById.isEmpty())
		{
			return;
		}

		final Set<QtyReservationId> idsToDelete = resultsById.entrySet().stream()
				.filter(e -> e.getValue().isDelete())
				.map(Map.Entry::getKey)
				.collect(ImmutableSet.toImmutableSet());

		if (resultsById.size() > idsToDelete.size())
		{
			repository.updateByOrderLineIds(
					ImmutableSet.of(request.getOrderAndLineId().getOrderLineId()),
					before -> {
						final QtyReservationShrinker.Result result = resultsById.get(before.getId());
						return result != null && !result.isDelete()
								? before.withQty(result.getNewQty(), result.getNewQtyTU())
								: before;
					});
		}
		repository.deleteByIds(idsToDelete);

		invalidateShipmentSchedulesForSalesOrderLineId(request.getOrderAndLineId());
	}

	/**
	 * Total TUs of the bucket (order line + supply type + optional date promised) that can still be unreserved,
	 * i.e. reserved minus already delivered. This is the upper bound {@link #reduceReservation} enforces.
	 */
	@NonNull
	public QtyTU getTotalReversibleQtyTU(
			@NonNull final OrderAndLineId orderAndLineId,
			@NonNull final SupplyType supplyType,
			@Nullable final Instant datePromised)
	{
		return QtyTU.ofInt(sumReversibleTU(repository.getActiveByBucket(orderAndLineId, supplyType, datePromised)));
	}

	private static int sumReversibleTU(@NonNull final List<QtyReservation> rows)
	{
		return rows.stream().mapToInt(QtyReservationShrinker::getReversibleTU).sum();
	}

	private void invalidateShipmentSchedulesForSalesOrderLineId(@NonNull final OrderAndLineId orderLineId)
	{
		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(orderLineId);
		invalidateShipmentSchedulesForSalesOrderLine(orderLine);
	}

	private void invalidateShipmentSchedulesForSalesOrderLine(final I_C_OrderLine orderLine)
	{
		shipmentScheduleInvalidateBL.flagForRecomputeStorageSegment(
				ShipmentScheduleSegments.builder()
						.anyBPartnerId()
						.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
						// .attributeSetInstanceId(orderLine.getM_AttributeSetInstance_ID())
						.warehouseId(WarehouseId.ofRepoId(orderLine.getM_Warehouse_ID()))
						.build()
		);
	}

	public QtyTU getReservedQtyTU(@NonNull final OrderAndLineId orderLineId)
	{
		return repository.getReservedQtyTU(orderLineId);
	}

	/**
	 * The sales-order line's <b>remaining unreserved ordered qty</b>, expressed in {@code targetUomId}:
	 * {@code QtyOrdered − Σ(active, unprocessed reservations' Qty on the line)}, floored at 0.
	 * <p>
	 * Used as the order-need upper bound when creating a new reservation (REQUIREMENTS AC3a), so the
	 * line's TOTAL reserved CU never exceeds its {@code QtyOrdered}. The bound is the <i>remaining</i>
	 * qty (not the full {@code QtyOrdered}) so multiple reservations on one line are supported without
	 * over-counting. All arithmetic is performed in {@code targetUomId}; each reservation's Qty and the
	 * line's QtyOrdered are converted into it.
	 *
	 * @param orderAndLineId the sales order line
	 * @param targetUomId    the UOM the result is expressed in (typically the new reservation's stock UOM)
	 * @return the remaining ordered qty in {@code targetUomId}; never negative
	 */
	public Quantity computeRemainingOrderedQty(
			@NonNull final OrderAndLineId orderAndLineId,
			@NonNull final UomId targetUomId)
	{
		// de.metas.interfaces.I_C_OrderLine (the richer type getOrderLineById returns) so the already-loaded
		// record can be passed to getQtyOrdered(I_C_OrderLine) — the OrderAndLineId overload would re-fetch it.
		final de.metas.interfaces.I_C_OrderLine orderLine = orderLineBL.getOrderLineById(orderAndLineId);
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());

		final Quantity qtyOrdered = orderLineBL.getQtyOrdered(orderLine);
		Quantity remaining = uomConversionBL.convertQuantityTo(qtyOrdered, productId, targetUomId);

		for (final QtyReservation reservation : repository.getActiveByOrderLineId(orderAndLineId.getOrderLineId()))
		{
			final Quantity reservedInTarget = uomConversionBL.convertQuantityTo(
					reservation.getQty(), reservation.getProductId(), targetUomId);
			remaining = remaining.subtract(reservedInTarget);
		}

		// floor at 0: a fully/over-reserved line has no remaining ordered qty
		return remaining.toZeroIfNegative();
	}

	/**
	 * Shrinks the active reservations of each sales-order line of the given order so that
	 * the line's total reserved Qty never exceeds the line's current {@code QtyOrdered}.
	 * One-directional: only reduces, never grows; a reservation is never shrunk below its
	 * already-delivered qty. Shipment schedules of changed lines are invalidated afterwards.
	 */
	public void reconcileToOrderedQty(@NonNull final OrderId orderId)
	{
		final ImmutableSet<OrderLineId> changedOrderLineIds = ReconcileQtyReservationsCommand.builder()
				.qtyReservationRepository(repository)
				.orderId(orderId)
				.build()
				.execute();

		// invalidate shipment schedules of the lines whose reservations actually changed,
		// consistent with makeReservation()/deleteReservation()
		for (final OrderLineId orderLineId : changedOrderLineIds)
		{
			final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(orderLineId);
			invalidateShipmentSchedulesForSalesOrderLine(orderLine);
		}
	}

}
