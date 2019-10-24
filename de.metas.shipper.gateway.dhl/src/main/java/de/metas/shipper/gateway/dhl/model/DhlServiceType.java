/*
 * #%L
 * de.metas.shipper.gateway.dhl
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.dhl.model;

import com.google.common.collect.ImmutableMap;
import de.metas.shipper.gateway.spi.model.ServiceType;
import de.metas.util.GuavaCollectors;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.stream.Stream;

/**
 * as of 2019.10.18 there's no way for the customer to change the service type!
 * It is hardcoded inside CreateDraftDeliveryOrder, and that's it.
 */
public enum DhlServiceType implements ServiceType
{

	Dhl_Paket("V01PAK"),
	Dhl_PaketInternational("V53WPAK");

	@Getter
	private final String code;

	DhlServiceType(final String code)
	{
		this.code = code;
	}

	@NonNull
	public DhlServiceType forCode(final String code)
	{
		final DhlServiceType type = code2type.get(code);
		if (type == null)
		{
			throw new AdempiereException("No serviceType for code=" + code + " exists.");
		}
		return type;
	}

	private static final ImmutableMap<String, DhlServiceType> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(DhlServiceType::getCode));
}
