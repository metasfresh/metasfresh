package de.metas.inoutcandidate.api;

import java.util.List;
import java.util.Set;

import de.metas.inoutcandidate.ShipmentScheduleId;
import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
@EqualsAndHashCode
public class ShipmentScheduleUserChangeRequestsList
{
	public static ShipmentScheduleUserChangeRequestsList of(@NonNull final List<ShipmentScheduleUserChangeRequest> userChanges)
	{
		return new ShipmentScheduleUserChangeRequestsList(userChanges);
	}

	private final ImmutableMap<ShipmentScheduleId, ShipmentScheduleUserChangeRequest> userChanges;

	private ShipmentScheduleUserChangeRequestsList(@NonNull final List<ShipmentScheduleUserChangeRequest> userChanges)
	{
		Check.assumeNotEmpty(userChanges, "userChanges is not empty");
		this.userChanges = Maps.uniqueIndex(userChanges, ShipmentScheduleUserChangeRequest::getShipmentScheduleId);
	}

	public Set<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return userChanges.keySet();
	}

	public ShipmentScheduleUserChangeRequest getByShipmentScheduleId(final ShipmentScheduleId shipmentScheduleId)
	{
		final ShipmentScheduleUserChangeRequest userChange = userChanges.get(shipmentScheduleId);
		if (userChange == null)
		{
			throw new AdempiereException("No user change found for " + shipmentScheduleId);
		}
		return userChange;
	}
}
