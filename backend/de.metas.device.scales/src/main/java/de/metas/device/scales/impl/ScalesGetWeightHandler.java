package de.metas.device.scales.impl;

/*
 * #%L
 * de.metas.device.scales
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.device.api.DeviceException;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.IDeviceRequestHandler;
import de.metas.device.scales.endpoint.ITcpConnectionEndPoint;
import de.metas.device.scales.request.GetWeightResponse;
import de.metas.logging.LogManager;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ScalesGetWeightHandler<C extends ICmd> implements IDeviceRequestHandler<IDeviceRequest<GetWeightResponse>, GetWeightResponse>
{

	private static final Logger logger = LogManager.getLogger(ScalesGetWeightHandler.class);

	private ITcpConnectionEndPoint endpoint;

	private IParser<C> parser;

	private C cmd;

	/**
	 * @task http://dewiki908/mediawiki/index.php/09207_Wiegen_nur_eine_Nachkommastelle_%28101684670982%29
	 */
	private int roundWeightToPrecision = -1;

	private String weightFieldName;

	private String uomFieldName;

	@Override
	public GetWeightResponse handleRequest(final IDeviceRequest<GetWeightResponse> request)
	{
		final String endpointResultStr = endpoint.sendCmd(cmd.getCmd());
		logger.trace("Received result string {} from endpoint {}; command: {}", endpointResultStr, endpoint, cmd);

		final String endPointWeight = parser.parse(cmd, endpointResultStr, weightFieldName, String.class);
		logger.trace("Parsed weight number string {} from the result string; will round according to roundWeightToPrecision={}",
				endPointWeight, roundWeightToPrecision);
		final BigDecimal weight;
		try
		{
			final String formattedEndpointWeight = endPointWeight.replace(",", ".");
			weight = new BigDecimal(formattedEndpointWeight);
		}
		catch (final NumberFormatException e)
		{
			throw new DeviceException("The weight string '" + endPointWeight + "' which was parsed from the device's response string '" + endpointResultStr + "' is not a number ", e);
		}

		final String endPointUom = parser.parse(cmd, endpointResultStr, uomFieldName, String.class);
		logger.trace("Parsed uom string {} from the result string", endPointUom);

		return new GetWeightResponse(
				roundWeightToPrecision < 0  // task 09207: only round if a precision >=0 was supplied
				? weight
						: weight.setScale(roundWeightToPrecision, RoundingMode.HALF_UP),
				endPointUom);
	}

	public ScalesGetWeightHandler<C> setEndpoint(final ITcpConnectionEndPoint endpoint)
	{
		this.endpoint = endpoint;
		return this;
	}

	public ScalesGetWeightHandler<C> setParser(final IParser<C> parser)
	{
		this.parser = parser;
		return this;
	}

	public ScalesGetWeightHandler<C> setCmd(final C cmd)
	{
		this.cmd = cmd;
		return this;
	}

	public ScalesGetWeightHandler<C> setWeightFieldName(final String fieldName)
	{
		this.weightFieldName = fieldName;
		return this;
	}

	public ScalesGetWeightHandler<C> setUOMFieldName(final String fieldName)
	{
		this.uomFieldName = fieldName;
		return this;
	}

	/**
	 * The weighing result number will be round to the given precision using {@link RoundingMode#HALF_UP}.<br>
	 * If called with a value less than zero, or not called at all, then no rounding will be done.
	 *
	 * @param roundWeightToPrecision
	 * @task http://dewiki908/mediawiki/index.php/09207_Wiegen_nur_eine_Nachkommastelle_%28101684670982%29
	 */
	public ScalesGetWeightHandler<C> setroundWeightToPrecision(final int roundWeightToPrecision)
	{
		this.roundWeightToPrecision = roundWeightToPrecision;
		return this;
	}

	@Override
	public String toString()
	{
		return String.format(
				"ScalesGetWeightHandler [endpoint=%s, parser=%s, cmd=%s, weightFieldName=%s, uomFieldName=%s, weightRoundToPrecision=%s]",
				endpoint, parser, cmd, weightFieldName, uomFieldName, roundWeightToPrecision);
	}
}
