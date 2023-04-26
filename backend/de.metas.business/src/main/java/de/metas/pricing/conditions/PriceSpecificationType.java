package de.metas.pricing.conditions;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import de.metas.ad_reference.ReferenceId;
import lombok.Getter;

/*
 * #%L
 * de.metas.business
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

public enum PriceSpecificationType
{
	NONE("N"),

	/**
	 * The price is specified via price condition (together with the respective bPartner, country, date etc etc).
	 * In addition, there might be a surcharge amount with a currency.
	 */
	BASE_PRICING_SYSTEM("P"),

	/** The price is specified by a fixed amount and a currency */
	FIXED_PRICE("F");

	public static final ReferenceId AD_Reference_ID = ReferenceId.ofRepoId(540862);

	@Getter
	private final String code;

	PriceSpecificationType(final String code)
	{
		this.code = code;
	}

	public static PriceSpecificationType ofCode(final String code)
	{
		final PriceSpecificationType type = valuesByCode.get(code);
		if (type == null)
		{
			throw new NoSuchElementException("No " + PriceSpecificationType.class + " for " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PriceSpecificationType> valuesByCode = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(PriceSpecificationType::getCode, Function.identity()));
}
