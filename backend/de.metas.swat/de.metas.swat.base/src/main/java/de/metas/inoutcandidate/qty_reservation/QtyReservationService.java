package de.metas.inoutcandidate.qty_reservation;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.QtyTU;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_OrderLine;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class QtyReservationService
{
	private static final String SYSCONFIG_COPY_STORAGE_RELEVANT_ATTRS_TO_ORDER_LINE_ASI = "de.metas.handlingunits.order.CopyStorageRelevantAttributesToOrderLineASI";
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
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

	public QtyTU getReservedQtyTU(final @NotNull DeleteQtyReservationRequest request)
	{
		return repository.getReservedQtyTU(request);
	}

	public QtyTU getReservedQtyTU(@NonNull final OrderAndLineId orderLineId)
	{
		return repository.getReservedQtyTU(orderLineId);
	}

	/**
	 * Shrinks active (Processed=false, IsActive=true) M_QtyReservation rows for the given order line
	 * so that Σ Qty ≤ max(0, orderLine.QtyOrdered − orderLine.QtyDelivered).
	 *
	 * Excess is removed oldest-first (by M_QtyReservation_ID). If a reservation row's Qty reaches 0
	 * after the reduction, the row is left at Qty=0 (NOT deleted) — interceptors handle the
	 * shipment-schedule invalidation.
	 *
	 * No-op if Σ Qty already fits.
	 *
	 * Used by the "split order line" process (me03 #29261) to keep reservations consistent
	 * when the order line's QtyOrdered is reduced.
	 *
	 * @return the new total qty reserved on the line after shrinking (may be 0)
	 */
	public BigDecimal shrinkToFitOpenQty(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(orderLineId);
		final BigDecimal openQty = orderLine.getQtyOrdered()
				.subtract(orderLine.getQtyDelivered())
				.max(BigDecimal.ZERO);

		final ImmutableList<QtyReservation> reservations = repository.getActiveByOrderLineId(orderLineId);

		BigDecimal total = reservations.stream()
				.map(r -> r.getQty().toBigDecimal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (total.compareTo(openQty) <= 0)
		{
			return total;
		}

		BigDecimal excess = total.subtract(openQty);
		for (final QtyReservation reservation : reservations)
		{
			if (excess.signum() <= 0)
			{
				break;
			}

			final BigDecimal currentQty = reservation.getQty().toBigDecimal();
			if (currentQty.signum() <= 0)
			{
				continue;
			}

			final BigDecimal reduction = excess.min(currentQty);
			final BigDecimal newQty = currentQty.subtract(reduction);

			final Quantity newQtyObj = reservation.getQty().toZero().add(newQty); // toZero() preserves UOM, then add(BigDecimal) sets the new value
			final QtyReservation updated = reservation.withQty(newQtyObj);
			repository.saveReservationQty(updated);

			excess = excess.subtract(reduction);
			total = total.subtract(reduction);
		}

		return total;
	}

}
