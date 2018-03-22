package de.metas.vertical.pharma.msv3.protocol.types;

import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
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
public class BPartnerId
{
	public static BPartnerId of(final int bpartnerId, final int bpartnerLocationId)
	{
		return new BPartnerId(bpartnerId, bpartnerLocationId);
	}

	private final int bpartnerId;
	private final int bpartnerLocationId;

	private BPartnerId(final int bpartnerId, final int bpartnerLocationId)
	{
		if (bpartnerId < 1)
		{
			throw new IllegalArgumentException("bpartnerId shall be > 0");
		}
		if (bpartnerLocationId < 1)
		{
			throw new IllegalArgumentException("bpartnerLocationId shall be > 0");
		}

		this.bpartnerId = bpartnerId;
		this.bpartnerLocationId = bpartnerLocationId;
	}
}
