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

package de.metas.camel.externalsystems.grssignum.from_grs.restapi;

import com.google.common.collect.Maps;
import de.metas.camel.externalsystems.grssignum.from_grs.product.PushRawMaterialsRouteBuilder;
import de.metas.camel.externalsystems.grssignum.from_grs.vendor.PushBPartnerRouteBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static de.metas.camel.externalsystems.grssignum.from_grs.bom.PushBOMProductsRouteBuilder.PUSH_BOM_PRODUCTS_ROUTE_ID;
import static de.metas.camel.externalsystems.grssignum.from_grs.hu.UpdateHURouteBuilder.UPDATE_HU_ROUTE_ID;

@AllArgsConstructor
@Getter
public enum Endpoint
{
	BPARTNER(100, PushBPartnerRouteBuilder.PUSH_BPARTNERS_ROUTE_ID),
	PRODUCT(200, PushRawMaterialsRouteBuilder.PUSH_RAW_MATERIALS_ROUTE_ID),
	BOM(300, PUSH_BOM_PRODUCTS_ROUTE_ID),
	HU_UPDATE(999, UPDATE_HU_ROUTE_ID);

	private final int flag;
	private final String targetRoute;

	private static final Map<Integer, Endpoint> flag2Endpoint = Maps.uniqueIndex(Arrays.asList(values()), Endpoint::getFlag);

	@NonNull
	public static Endpoint ofFlag(@NonNull final Integer flag)
	{
		return ofFlagOptional(flag)
				.orElseThrow(() -> new RuntimeException("Unknown flag! Flag = " + flag));
	}

	@NonNull
	public static Optional<Endpoint> ofFlagOptional(@Nullable final Integer flag)
	{
		if (flag == null)
		{
			return Optional.empty();
		}

		return Optional.ofNullable(flag2Endpoint.get(flag));
	}
}
