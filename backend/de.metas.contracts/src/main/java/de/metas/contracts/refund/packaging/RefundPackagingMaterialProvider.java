package de.metas.contracts.refund.packaging;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Tells which packing material goods are delivered in, given their packing instruction.
 * <p>
 * This is an SPI: the packing instructions live in a module that depends on this one, so this module cannot resolve them itself.
 * Without an implementation, no packing instruction has a packing material.
 */
public interface RefundPackagingMaterialProvider
{
	/**
	 * @return the {@code M_HU_PackingMaterial_ID} of the packing instruction; empty if it has none
	 */
	@NonNull
	Optional<Integer> getPackingMaterialId(@NonNull HUPIItemProductId huPIItemProductId, @Nullable BPartnerId bpartnerId);
}
