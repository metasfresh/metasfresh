package de.metas.shipper.gateway.api.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.shipper.gateway.api
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

// TODO: move AdempiereException to de.metas.util project and extend it here
@SuppressWarnings("serial")
public class ShipperGatewayException extends RuntimeException
{
	private final ImmutableList<ShipperErrorMessage> shipperErrorMessages;

	public ShipperGatewayException(final String message)
	{
		super(message);
		this.shipperErrorMessages = ImmutableList.of();
	}

	public ShipperGatewayException(final List<ShipperErrorMessage> shipperErrorMessages)
	{
		super(extractMessage(shipperErrorMessages));
		this.shipperErrorMessages = shipperErrorMessages != null ? ImmutableList.copyOf(shipperErrorMessages) : ImmutableList.of();
	}

	private static String extractMessage(final List<ShipperErrorMessage> shipperErrorMessages)
	{
		if (shipperErrorMessages == null || shipperErrorMessages.isEmpty())
		{
			return "Unknown shipper gateway error";
		}
		else if (shipperErrorMessages.size() == 1)
		{
			final ShipperErrorMessage shipperErrorMessage = shipperErrorMessages.get(0);
			return shipperErrorMessage.getMessage();
		}
		else
		{
			return shipperErrorMessages.stream()
					.map(ShipperErrorMessage::getMessage)
					.collect(Collectors.joining("; "));
		}
	}

	public List<ShipperErrorMessage> getShipperErrorMessages()
	{
		return shipperErrorMessages;
	}
}
