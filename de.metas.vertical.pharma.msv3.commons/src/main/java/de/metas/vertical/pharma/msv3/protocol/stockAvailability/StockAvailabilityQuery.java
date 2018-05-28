package de.metas.vertical.pharma.msv3.protocol.stockAvailability;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class StockAvailabilityQuery
{
	String id;
	BPartnerId bpartner;
	ImmutableList<StockAvailabilityQueryItem> items;

	@Builder
	private StockAvailabilityQuery(
			@NonNull final String id,
			@NonNull final BPartnerId bpartner,
			@NonNull @Singular final ImmutableList<StockAvailabilityQueryItem> items)
	{
		if (items.isEmpty())
		{
			throw new IllegalArgumentException("Query shall have at least one item");
		}

		this.id = id;
		this.bpartner = bpartner;
		this.items = items;
	}

}
