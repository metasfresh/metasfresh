package de.metas.fresh.picking;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.UUID;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

public class PickingSlotKeyGroup
{
	private final int bpartnerId;
	private final int bpartnerLocationId;

	/**
	 * Key to be used in case we really want to make this group unique (i.e. when BPartnerId and BPartnerLocationId are not set)
	 */
	private final String uniqueKey;

	/* package */PickingSlotKeyGroup(final int bpartnerId, final int bpartnerLocationId)
	{
		super();
		this.bpartnerId = bpartnerId <= 0 ? -1 : bpartnerId;
		this.bpartnerLocationId = bpartnerLocationId <= 0 ? -1 : bpartnerLocationId;

		//
		// NOTE: In case BP and BPL is not set, we shall have unique groups for each Picking Slot
		if (this.bpartnerId <= 0 && this.bpartnerLocationId <= 0)
		{
			uniqueKey = UUID.randomUUID().toString();
		}
		else
		{
			uniqueKey = null;
		}
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(bpartnerId)
				.append(bpartnerLocationId)
				.append(uniqueKey)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final PickingSlotKeyGroup other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.bpartnerId, other.bpartnerId)
				.append(this.bpartnerLocationId, other.bpartnerLocationId)
				.append(this.uniqueKey, other.uniqueKey)
				.isEqual();
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "bpartnerId=" + bpartnerId
				+ ", bpartnerLocationId=" + bpartnerLocationId
				+ ", uniqueKey=" + uniqueKey
				+ "]";
	}

	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}

	public int getC_BPartner_Location_ID()
	{
		return bpartnerLocationId;
	}
}
