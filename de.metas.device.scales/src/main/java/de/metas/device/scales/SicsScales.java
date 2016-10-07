package de.metas.device.scales;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.device.scales.impl.ScalesGetGrossWeightHandler;
import de.metas.device.scales.impl.sics.ISiscCmd;
import de.metas.device.scales.impl.sics.SicsResponseStringParser;
import de.metas.device.scales.impl.sics.SicsWeighCmdS;
import de.metas.device.scales.impl.sics.SicsWeighCmdSI;
import de.metas.device.scales.request.GetGrossWeighRequest;
import de.metas.device.scales.request.GetInstantGrossWeighRequest;
import de.metas.device.scales.request.GetStableGrossWeighRequest;

public class SicsScales extends AbstractTcpScales
{

	@SuppressWarnings({ "unchecked" })
	@Override
	public void configureStatic()
	{
		registerHandler(GetInstantGrossWeighRequest.class, mkHandler(SicsWeighCmdSI.getInstance()));
		registerHandler(GetGrossWeighRequest.class, mkHandler(SicsWeighCmdSI.getInstance()));

		registerHandler(GetStableGrossWeighRequest.class,  mkHandler(SicsWeighCmdS.getInstance()));
	}

	@SuppressWarnings({ "rawtypes" })
	private ScalesGetGrossWeightHandler mkHandler(final ISiscCmd cmd)
	{
		return new ScalesGetGrossWeightHandler<ISiscCmd>()
				.setCmd(cmd)
				.setWeightFieldName("WeightValue")
				.setUOMFieldName("Unit")
				.setEndpoint(getEndPoint())
				.setParser(new SicsResponseStringParser())
				.setroundWeightToPrecision(getRoundToPrecision())// task 09207; note that in future we might not configure the parser like this, but send some according command to the scale itself.
				;
	}
}
