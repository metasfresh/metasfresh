package de.metas.inoutcandidate.qty_reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_OrderLine;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QtyReservationService
{
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
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

		if (request.getProjectId() != null)
		{
			orderLine.setC_Project_ID(request.getProjectId().getRepoId());
			// assume the ProjectValue attribute will be automatically set/updated in ASI 
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

}
