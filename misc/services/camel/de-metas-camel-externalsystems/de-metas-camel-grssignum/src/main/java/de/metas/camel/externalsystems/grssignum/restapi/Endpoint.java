/*
 * #%L
 * de-metas-camel-grssignum
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.grssignum.restapi;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Map;

import static de.metas.camel.externalsystems.grssignum.vendor.PushBPartnerRouteBuilder.PUSH_BPARTNERS_ROUTE_ID;

@AllArgsConstructor
@Getter
public enum Endpoint
{
	BPARTNER(100, PUSH_BPARTNERS_ROUTE_ID);

	private final int flag;
	private final String targetRoute;

	private static final Map<Integer, Endpoint> flag2Endpoint = Maps.uniqueIndex(Arrays.asList(values()), Endpoint::getFlag);

	@NonNull
	public static Endpoint ofFlag(@NonNull final Integer flag)
	{
		final Endpoint endpoint = flag2Endpoint.get(flag);

		if (endpoint == null)
		{
			throw new RuntimeException("Unknown flag! Flag = " + flag);
		}

		return endpoint;
	}
}
