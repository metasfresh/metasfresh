package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.QtyTU;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.project.ProjectValue;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * @see MaterialCockpitV2RowVOs
 */
@Value
@Builder
public class MaterialCockpitV2RowVO
{
	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@NonNull de.metas.inoutcandidate.qty_reservation.SupplyType supplyType;
	@NonNull de.metas.inoutcandidate.qty_reservation.AvailabilityType availabilityType;
	@Nullable Instant datePromised;
	@Nullable BPartnerId vendorBPartnerId;
	@Nullable AttributesKey attributesKey;
	@Nullable ProjectValue projectValue;
	@NonNull QtyTU qtyTU;
	@NonNull Quantity qtyStock;

	public Quantity computeQtyCUToReserve(@NonNull final QtyTU qtyTUToReserve)
	{
		if (!qtyTUToReserve.isPositive()) {return qtyStock.toZero();}
		if (!qtyTU.isPositive() || qtyStock.signum() <= 0) {return qtyStock.toZero();}

		return getQtyCUsPerTU().multiply(qtyTUToReserve.toInt());
	}

	private Quantity getQtyCUsPerTU()
	{
		if (!qtyTU.isPositive()) {throw new AdempiereException("qtyTU shall be positive");}
		return qtyStock.divide(qtyTU.toInt());
	}

	public boolean isAvailableForReservation()
	{
		return availabilityType == de.metas.inoutcandidate.qty_reservation.AvailabilityType.AVAILABLE
				&& qtyTU.isPositive();
	}

	public boolean isAvailableForUnReservation()
	{
		return availabilityType == de.metas.inoutcandidate.qty_reservation.AvailabilityType.RESERVED
				&& qtyTU.isPositive();
	}

}

