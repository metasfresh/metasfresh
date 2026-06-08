package de.metas.inoutcandidate.qty_reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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

	/**
	 * Optional packing capacity per TU (in the product's stock UOM), used only when the order
	 * line carries no positive {@code QtyItemCapacity}. The WebUI caller resolves this from the
	 * order line's {@code M_HU_LUTU_Configuration} (the handling-units machinery is not reachable
	 * from this module). When {@code null} and the order line has no capacity, reservation fails.
	 */
	@Nullable Quantity capacityPerTUFallback;

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
	 * Compute the CU quantity to reserve as {@code qtyToReserveTU x packing-capacity}
	 * (in the product's stock UOM).
	 * <p>
	 * The capacity comes from the order line's packing-item capacity ({@code QtyItemCapacity}),
	 * falling back to {@link #capacityPerTUFallback} (resolved by the caller from the order line's
	 * {@code M_HU_LUTU_Configuration.QtyCUsPerTU}) when the order line carries no explicit capacity.
	 * Deriving the CU qty from the order line's packing capacity -- rather than from the cockpit
	 * row's on-hand stock -- is what lets a planned-supply row (which has zero on-hand stock)
	 * reserve a non-zero CU quantity.
	 * <p>
	 * For an {@link SupplyType#ON_HAND} row the result is capped at the row's on-hand stock
	 * ({@code qtyStock}); for {@link SupplyType#PLANNED_SUPPLY} it is not capped.
	 */
	private Quantity computeQtyCUToReserve()
	{
		final ProductId productId = rowVO.getProductId();
		final UomId stockUomId = Services.get(IProductBL.class).getStockUOMId(productId);

		final Quantity capacityPerTU = computeCapacityPerTU(stockUomId);

		Quantity qty = capacityPerTU.multiply(qtyToReserveTU.toInt());

		// For on-hand supply, never reserve more than the on-hand stock the cockpit row carries.
		// For planned supply, the reservation may exceed the (zero) on-hand stock.
		if (rowVO.getSupplyType() == SupplyType.ON_HAND)
		{
			qty = qty.min(rowVO.getQtyStock());
		}

		return qty;
	}

	/**
	 * The packing capacity per TU, in the product's stock UOM.
	 *
	 * @throws AdempiereException (as a user-validation error) when neither the order line's
	 *         {@code QtyItemCapacity} nor the caller-supplied {@link #capacityPerTUFallback}
	 *         yields a positive capacity.
	 */
	@NonNull
	private Quantity computeCapacityPerTU(@NonNull final UomId stockUomId)
	{
		final de.metas.interfaces.I_C_OrderLine orderLineRecord = orderLineBL.getOrderLineById(salesOrderAndLineId);

		final BigDecimal qtyItemCapacity = orderLineRecord.getQtyItemCapacity();
		if (qtyItemCapacity != null && qtyItemCapacity.signum() > 0)
		{
			return Quantitys.of(qtyItemCapacity, stockUomId);
		}

		// Fallback to the capacity derived (by the caller) from the order line's LU/TU configuration.
		if (capacityPerTUFallback != null && capacityPerTUFallback.signum() > 0)
		{
			return capacityPerTUFallback;
		}

		throw new AdempiereException("Cannot reserve in TUs: order line has no packing-item capacity")
				.markAsUserValidationError();
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
