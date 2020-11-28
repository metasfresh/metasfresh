package de.metas.device.scales.util;

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


import de.metas.device.scales.SystecScales;
import de.metas.device.scales.endpoint.ITcpConnectionEndPoint;
import de.metas.device.scales.endpoint.MockedEndpoint;
import de.metas.device.scales.request.GetStableGrossWeighRequest;
import de.metas.device.scales.request.GetWeightResponse;

public class CmdLineTestingTool
{
	public static void main(final String[] args)
	{

		final ITcpConnectionEndPoint endPoint = new MockedEndpoint();
		// new TcpConnectionEndPoint()

		// for the SICS scales
		// .setHost("10.10.1.143")
		// .setPort(8000);

		// for the systec scales
		// .setHost("10.10.1.145")
		// .setPort(1234);

		final SystecScales scales = new SystecScales();
		scales.setEndPoint(endPoint);
		scales.configureStatic();

		System.out.println("Querying scales..");
		final GetWeightResponse response = scales.accessDevice(new GetStableGrossWeighRequest());
		System.out.println("Scales responded: " + response.getWeight() + " " + response.getUom());
	}
}
