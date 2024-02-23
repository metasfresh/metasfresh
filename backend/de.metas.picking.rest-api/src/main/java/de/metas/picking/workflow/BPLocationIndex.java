/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.picking.workflow;

import de.metas.bpartner.BPartnerLocationId;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BPLocationIndex<T>
{
	private final Map<BPartnerLocationId, T> locationId2IndexedData = new HashMap<>();

	@NonNull
	public T getOrCompute(@NonNull final BPartnerLocationId locationId, @NonNull final Function<BPartnerLocationId, T> computeFunction)
	{
		return locationId2IndexedData.computeIfAbsent(locationId, computeFunction);
	}
}
