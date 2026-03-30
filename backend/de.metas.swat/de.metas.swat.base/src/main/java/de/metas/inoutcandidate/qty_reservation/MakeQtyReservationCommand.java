package de.metas.inoutcandidate.qty_reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.order.OrderAndLineId;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
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
						.qty(rowVO.computeQtyCUToReserve(qtyToReserveTU))
						.build());
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
