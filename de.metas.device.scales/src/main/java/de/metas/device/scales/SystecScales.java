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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.lang.ObjectUtils;

import de.metas.device.scales.impl.ScalesGetGrossWeightHandler;
import de.metas.device.scales.impl.systec.ISystecCmd;
import de.metas.device.scales.impl.systec.SystecCmdRN;
import de.metas.device.scales.impl.systec.SystecResponseStringParser;
import de.metas.device.scales.request.GetGrossWeighRequest;

public class SystecScales extends AbstractTcpScales
{
	@Override
	public void configureStatic()
	{
		final ScalesGetGrossWeightHandler<ISystecCmd> handler = new ScalesGetGrossWeightHandler<ISystecCmd>()
				.setCmd(SystecCmdRN.getInstance())
				.setWeightFieldName("Bruttogewicht")
				.setUOMFieldName("Einheit")
				.setEndpoint(getEndPoint())
				.setParser(new SystecResponseStringParser())
				.setroundWeightToPrecision(getRoundToPrecision()) // task 09207; note that in future we might not configure the parser like this, but send some according command to the scale itself
		;
		registerHandler(GetGrossWeighRequest.class, handler);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
