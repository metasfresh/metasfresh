/*
 * #%L
 * de-metas-camel-metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.metasfresh;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static de.metas.camel.externalsystems.metasfresh.bpartner.FileBPartnersRouteBuilder.PROCESS_BPARTNERS_FROM_FILE_ROUTE_ID;

@AllArgsConstructor
@Getter
public enum ProcessingRoutes
{
	BPARTNER("bpartnerComposite", PROCESS_BPARTNERS_FROM_FILE_ROUTE_ID);

	private final String itemType;
	private final String targetRoute;

	private static final Map<String, ProcessingRoutes> type2ProcessingRoute = Maps.uniqueIndex(Arrays.asList(values()), ProcessingRoutes::getItemType);

	@NonNull
	public static ProcessingRoutes ofItemType(@NonNull final String itemType)
	{
		return Optional.ofNullable(type2ProcessingRoute.get(itemType))
				.orElseThrow(() -> new RuntimeException("Unknown itemType! ItemType = " + itemType));
	}
}
