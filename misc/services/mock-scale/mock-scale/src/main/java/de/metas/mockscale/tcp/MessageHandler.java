/*
 * #%L
 * mock-scale
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

package de.metas.mockscale.tcp;

import de.metas.mockscale.service.ScaleService;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import reactor.util.annotation.NonNull;

@Component
@MessageEndpoint
public class MessageHandler
{
	private final ScaleService scaleService;

	public MessageHandler(@NonNull final ScaleService scaleService)
	{
		this.scaleService = scaleService;
	}

	@ServiceActivator(inputChannel = "scaleRequestMessageChannel")
	public byte[] handleMessage(
			@NonNull final byte[] message,
			@NonNull final MessageHeaders ignored)
	{
		return scaleService.handleCommand(new String(message))
				.getBytes();
	}
}
