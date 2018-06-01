package de.metas.vendor.gateway.api.availability;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.vendor.gateway.api
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

/**
 * An external ID used to track availability requests.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
public class TrackingId
{
	public static TrackingId random()
	{
		return new TrackingId(UUID.randomUUID().toString());
	}

	String value;

	private TrackingId(@NonNull final String value)
	{
		this.value = value;
	}
}
