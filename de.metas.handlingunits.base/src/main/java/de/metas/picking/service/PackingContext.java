package de.metas.picking.service;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public final class PackingContext
{
	/**
	 * The slot where the packed items shall be placed, inside {@link #packingItems}.
	 */
	@NonNull
	@Default
	private final PackingSlot packedItemsSlot = PackingSlot.DEFAULT_PACKED;

	/** map used to keep track of that is packed where while the packing takes place. */
	@NonNull
	private PackingItemsMap packingItems;
}
