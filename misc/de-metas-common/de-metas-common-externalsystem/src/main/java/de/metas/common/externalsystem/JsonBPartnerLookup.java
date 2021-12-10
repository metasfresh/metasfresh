/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem;

import de.metas.common.util.Check;
import lombok.AllArgsConstructor;

import javax.annotation.Nullable;

/**
 * Json representation of `de.metas.externalsystem.shopware6.BPartnerLookup`
 */

@AllArgsConstructor
public enum JsonBPartnerLookup
{
	MetasfreshId,
	ExternalReference;

	@Nullable
	public static JsonBPartnerLookup valueOfNullable(@Nullable final String value)
	{
		if (Check.isBlank(value))
		{
			return null;
		}

		return JsonBPartnerLookup.valueOf(value);
	}
}

