package de.metas.contracts.refund.packaging;

import de.metas.order.OrderLineId;
import lombok.NonNull;

import java.util.Optional;

/**
 * Tells which packing material an order line is delivered in.
 * <p>
 * This is an SPI: the packing instructions live in a module that depends on this one, so this module cannot resolve them itself.
 * Without an implementation, no order line has a packing material.
 */
public interface RefundPackagingMaterialProvider
{
	/**
	 * @return the {@code M_HU_PackingMaterial_ID} of the order line's packing instruction; empty if the line has none
	 */
	@NonNull
	Optional<Integer> getPackingMaterialId(@NonNull OrderLineId orderLineId);
}
