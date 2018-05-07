package de.metas.shipper.gateway.go.schema;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;

import com.google.common.collect.ImmutableMap;

import de.metas.shipper.gateway.spi.model.SelfPickup;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.go
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Self pickup: does the Recipient fetch the packages from Shipper or the Shipper is delivering the packages to Recipient
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public enum GOSelfPickup implements SelfPickup
{
	/** Shipper will deliver packages to Recipient */
	Delivery("0"),
	/** Recipient will fetch packages from Shipper */
	SelfDelivery("1");

	@Getter
	private final String code;

	private GOSelfPickup(final String code)
	{
		this.code = code;
	}

	public static GOSelfPickup forCode(@NonNull final String code)
	{
		final GOSelfPickup type = code2type.get(code);
		if (type == null)
		{
			throw new NoSuchElementException("No element found for code=" + code);
		}
		return type;
	}

	private static final ImmutableMap<String, GOSelfPickup> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(GOSelfPickup::getCode));
}
