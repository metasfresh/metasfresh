package de.metas.inoutcandidate.qty_reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Command that creates a quantity reservation from a {@link MaterialCockpitV2RowVO}.
 *
 * <p>Extracted from the WebUI process {@code MD_CockpitV2_MakeQtyReservation}
 * so it can be called from non-WebUI contexts such as Cucumber step definitions.
 */
@Value
@Builder
public class MakeQtyReservationCommand
{
	@NonNull IOrderLineBL orderLineBL;
	@NonNull QtyReservationService qtyReservationService;
	@Nullable ProjectRepository projectRepository;
	@NonNull MaterialCockpitV2RowVO rowVO;
	@NonNull OrderAndLineId salesOrderAndLineId;
	@NonNull QtyTU qtyToReserveTU;

	public QtyReservationId execute()
	{
		return qtyReservationService.makeReservation(
				CreateQtyReservationRequest.builder()
						.orderAndLineId(salesOrderAndLineId)
						.productId(rowVO.getProductId())
						.warehouseId(rowVO.getWarehouseId())
						.supplyType(rowVO.getSupplyType())
						.datePromised(rowVO.getDatePromised())
						.vendorBPartnerId(rowVO.getVendorBPartnerId())
						.attributesKey(rowVO.getAttributesKey())
						.projectId(extractProjectId())
						.qtyTU(qtyToReserveTU)
						.qty(computeQtyCUToReserve())
						.build());
	}

	/**
	 * Compute the CU quantity to reserve, capped at the order line's QtyOrdered.
	 * <p>
	 * The cockpit row's CU/TU ratio reflects the average fill level of stock TUs.
	 * When the order line has a partially-filled last TU (e.g., 20 CU in 3 TU with
	 * capacity 10 CU/TU), the stock-derived CU (3 × 10 = 30) would exceed the
	 * actual order need (20). Capping at QtyOrdered prevents over-reservation.
	 */
	private Quantity computeQtyCUToReserve()
	{
		final Quantity qtyCUFromStock = rowVO.computeQtyCUToReserve(qtyToReserveTU);
		final Quantity qtyOrdered = orderLineBL.getQtyOrdered(salesOrderAndLineId);
		return qtyCUFromStock.min(qtyOrdered);
	}

	@Nullable
	private ProjectId extractProjectId()
	{
		if (rowVO.getProjectValue() == null || projectRepository == null)
		{
			return null;
		}
		return projectRepository.getIdByValueOrNull(rowVO.getProjectValue());
	}
}
