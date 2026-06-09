/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.mapping;

import com.google.common.collect.ImmutableList;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

public class ShipperMappingConfigList implements Iterable<ShipperMappingConfig>
{
	public static final ShipperMappingConfigList EMPTY = new ShipperMappingConfigList(ImmutableList.of());

	@NonNull private final ImmutableList<ShipperMappingConfig> list;

	private ShipperMappingConfigList(@NonNull final ImmutableList<ShipperMappingConfig> list) {this.list = list;}

	@NotNull
	@Override
	public Iterator<ShipperMappingConfig> iterator() {return list.iterator();}

	public static ShipperMappingConfigList ofCollection(@NonNull final Collection<ShipperMappingConfig> list)
	{
		return list.isEmpty() ? ShipperMappingConfigList.EMPTY : new ShipperMappingConfigList(ImmutableList.copyOf(list));
	}

	public ShipperMappingConfigList subsetOf(@NonNull final ShipperId shipperId)
	{
		return ShipperMappingConfigList.ofCollection(list.stream()
				.filter(config -> ShipperId.equals(shipperId, config.getShipperId()))
				.collect(Collectors.toList())
		);
	}
}
