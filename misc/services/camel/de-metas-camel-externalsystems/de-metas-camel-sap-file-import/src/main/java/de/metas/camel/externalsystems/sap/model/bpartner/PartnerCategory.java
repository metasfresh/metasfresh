/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.model.bpartner;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum PartnerCategory
{
	MC_JAPAN("1"),
	GENERAL_PARTNER("2"),
	STORAGE_LOCATION("4");

	@Getter
	final String code;

	@Nullable
	public static PartnerCategory ofCodeOrNull(@Nullable final String code)
	{
		return Optional.ofNullable(code)
				.map(typesByCode::get)
				.orElse(null);
	}

	private static final ImmutableMap<String, PartnerCategory> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PartnerCategory::getCode);
}