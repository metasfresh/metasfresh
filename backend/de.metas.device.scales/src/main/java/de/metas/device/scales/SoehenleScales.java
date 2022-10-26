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

import de.metas.device.scales.impl.ScalesGetWeightHandler;
import de.metas.device.scales.impl.soehenle.ISoehenleCmd;
import de.metas.device.scales.impl.soehenle.SoehenleInstantNetWeightCmd;
import de.metas.device.scales.impl.soehenle.SoehenleResponseStringParser;
import de.metas.device.scales.impl.soehenle.SoehenleStableNetWeightCmd;
import de.metas.device.scales.request.GetGrossWeighRequest;
import de.metas.device.scales.request.GetInstantNetWeighRequest;
import de.metas.device.scales.request.GetStableNetWeighRequest;

public class SoehenleScales extends AbstractTcpScales
{

	@SuppressWarnings({ "unchecked" })
	@Override
	public void configureStatic()
	{
		registerHandler(GetInstantNetWeighRequest.class, mkHandler(SoehenleInstantNetWeightCmd.getInstance()));
		registerHandler(GetGrossWeighRequest.class, mkHandler(SoehenleStableNetWeightCmd.getInstance()));

		registerHandler(GetStableNetWeighRequest.class, mkHandler(SoehenleStableNetWeightCmd.getInstance()));
	}

	@SuppressWarnings({ "rawtypes" })
	private ScalesGetWeightHandler mkHandler(final ISoehenleCmd cmd)
	{
		return new ScalesGetWeightHandler<ISoehenleCmd>()
				.setCmd(cmd)
				.setWeightFieldName("WeightValue")
				.setUOMFieldName("Unit")
				.setEndpoint(getEndPoint())
				.setParser(new SoehenleResponseStringParser())
				.setroundWeightToPrecision(getRoundToPrecision())// task 09207; note that in future we might not configure the parser like this, but send some according command to the scale itself.
				;
	}
}
