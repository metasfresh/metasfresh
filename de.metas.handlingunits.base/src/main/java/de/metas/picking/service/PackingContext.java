package de.metas.picking.service;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public final class PackingContext
{
	/**
	 * The key for {@link #packingItemsMap} under which those items are stored that are "packed".
	 */
	@NonNull
	private final PackingItemsMapKey packingItemsMapKey;

	/** map used to keep track of that is packed where while the packing takes place. */
	@NonNull
	private PackingItemsMap packingItemsMap;
}
