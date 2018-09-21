package de.metas.picking.service;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public final class PackingContext
{
	@NonNull
	private final Properties ctx;

	/**
	 * The key for {@link #packingItemsMap} under which those items are stored that are "packed".
	 */
	@NonNull
	private final PackingItemsMapKey packingItemsMapKey;

	/** map used to keep track of that is packed where while the packing takes place. */
	@NonNull
	private PackingItemsMap packingItemsMap;
}
