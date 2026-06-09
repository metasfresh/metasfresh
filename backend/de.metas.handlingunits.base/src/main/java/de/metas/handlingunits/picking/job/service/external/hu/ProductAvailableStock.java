package de.metas.handlingunits.picking.job.service.external.hu;

import de.metas.quantity.MixedQuantity;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.NonNull;

class ProductAvailableStock
{
	@NonNull private MixedQuantity qtyOnHand = MixedQuantity.EMPTY;
	@NonNull private MixedQuantity qtyAllocated = MixedQuantity.EMPTY;

	public void addQtyOnHand(@NonNull final Quantity qty)
	{
		qtyOnHand = qtyOnHand.add(qty);
	}

	public Quantity allocateQty(final Quantity qty)
	{
		final UomId uomId = qty.getUomId();
		final Quantity qtyOnHandOfUOM = this.qtyOnHand.getByUOM(uomId);
		final Quantity qtyAllocatedOfUOM = this.qtyAllocated.getByUOM(uomId);
		final Quantity qtyAvailableToAllocate = qtyOnHandOfUOM.subtract(qtyAllocatedOfUOM).toZeroIfNegative();
		final Quantity qtyToAllocate = qty.min(qtyAvailableToAllocate);

		this.qtyAllocated = this.qtyAllocated.add(qtyToAllocate);
		return qtyToAllocate;
	}
}
