/*
 * #%L
 * de-metas-camel-externalsystems-common
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

package de.metas.camel.externalsystems.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public enum RestServiceRoutes
{
	WOO("/woocommerce", RestServiceAuthority.WOO),
	GRS("/grs", RestServiceAuthority.GRS),
	METASFRESH("/metasfresh", RestServiceAuthority.METASFRESH);

	@NonNull
	private final String path;
	@NonNull
	private final RestServiceAuthority restServiceAuthority;

	@NonNull
	public String getStringAuthority()
	{
		return restServiceAuthority.getValue();
	}
}
