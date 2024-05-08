/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.currency;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
@Getter(AccessLevel.NONE)
@JsonDeserialize(builder = CurrencyInfoProvider.CurrencyInfoProviderBuilder.class)
public class CurrencyInfoProvider implements Serializable
{
	@NonNull
	ImmutableMap<String, String> currencyId2IsoCode;

	@NonNull
	public String getIsoCodeByCurrencyIdNotNull(@NonNull final String currencyId)
	{
		final String isoCode = currencyId2IsoCode.get(currencyId);

		if (isoCode == null || isoCode.isBlank())
		{
			throw new RuntimeException("Missing currency iso code for currencyId=" + currencyId);
		}

		return isoCode;
	}
}
