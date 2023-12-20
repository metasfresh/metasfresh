/*
 * #%L
 * de.metas.device.scales
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

package de.metas.device.scales.impl.soehenle;

import de.metas.device.api.IDeviceRequestHandler;
import de.metas.device.scales.endpoint.ITcpConnectionEndPoint;
import de.metas.device.scales.request.NoDeviceResponse;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;

import static de.metas.device.scales.impl.sics.ISiscCmd.SICS_CMD_TERMINATOR;

@Value(staticConstructor = "of")
public class SendTargetWeightHandler implements IDeviceRequestHandler<SoehenleSendTargetWeightRequest, NoDeviceResponse>
{
	private static final Logger logger = LogManager.getLogger(SendTargetWeightHandler.class);

	@NonNull
	ITcpConnectionEndPoint endpoint;

	@Override
	public NoDeviceResponse handleRequest(@NonNull final SoehenleSendTargetWeightRequest request)
	{
		logger.info("*** Sending SoehenleSendTargetWeightRequest request " + request.getCmd());

		endpoint.sendCmd(request.getCmd() + SICS_CMD_TERMINATOR);

		return new NoDeviceResponse();
	}
}
