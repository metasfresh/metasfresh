package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

@Service
public class QtyReservationService
{
	private final QtyReservationRepository repository;
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	public QtyReservationService(@NonNull final QtyReservationRepository repository)
	{
		this.repository = repository;
	}

	public QtyReservationId makeReservation(@NonNull final CreateQtyReservationRequest request)
	{
		validateProductMatchesSalesOrderLine(request);
		return repository.createReservation(request);
	}

	public void deleteReservation(@NonNull final QtyReservationId reservationId)
	{
		repository.deleteById(reservationId);
	}

	public void deleteReservationsForOrderLine(@NonNull final OrderLineId orderLineId)
	{
		repository.deleteByOrderLineId(orderLineId);
	}

	public QtyTU getReservedQtyTU(@NonNull final OrderLineId orderLineId)
	{
		return repository.getReservedQtyTU(orderLineId);
	}

	private void validateProductMatchesSalesOrderLine(@NonNull final CreateQtyReservationRequest request)
	{
		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(request.getOrderLineId());
		final ProductId orderLineProductId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		if (!request.getProductId().equals(orderLineProductId))
		{
			throw new AdempiereException("Product mismatch: reservation product "
					+ request.getProductId().getRepoId()
					+ " != order line product "
					+ orderLineProductId.getRepoId());
		}
	}
}
