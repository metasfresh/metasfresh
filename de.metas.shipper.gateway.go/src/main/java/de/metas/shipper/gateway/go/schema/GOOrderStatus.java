package de.metas.shipper.gateway.go.schema;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.collect.ImmutableMap;

import de.metas.shipper.gateway.api.model.OrderStatus;
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
 * The status of the shipment to be imported can be managed via the field "status".
 *
 * IMPORTANT: The transmission of the AX4 shipment number is mandatory for updates and cancellations.
 * The AX4 shipment number is provided within the initial data transmission.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public enum GOOrderStatus implements OrderStatus
{
	/**
	 * New: if you transmit status "1", the shipment will be generated with status "new".
	 * The shipment can be edited within this status and updates/cancellations are
	 * enabled. There is no transmission to GO! and, thus, no pickup of this shipment yet.
	 */
	NEW("1"),

	/**
	 * Approved: if you transmit status "3", the shipment will be updated with status "approved".
	 * You cannot edit or cancel the order anymore within this status.
	 * Once approved, the order will be transmitted to GO!.
	 */
	APPROVED("3"),

	/**
	 * Cancellation: if you transmit status "20", the existing shipment will be cancelled.
	 * GO! receives this cancellation and will not pickup the shipment.
	 */
	CANCELLATION("20");

	@Getter
	private final String code;

	private GOOrderStatus(final String code)
	{
		this.code = code;
	}

	public static GOOrderStatus forCode(@NonNull final String code)
	{
		final GOOrderStatus type = code2type.get(code);
		if (type == null)
		{
			throw new NoSuchElementException("No element found for code=" + code);
		}
		return type;
	}

	public static GOOrderStatus forNullableCode(final String code)
	{
		if (Check.isEmpty(code, true))
		{
			return null;
		}
		return forCode(code);
	}

	@Override
	public boolean isFinalState()
	{
		return this == APPROVED || this == CANCELLATION;
	}

	private static final ImmutableMap<String, GOOrderStatus> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(GOOrderStatus::getCode));
}
