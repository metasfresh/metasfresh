package de.metas.device.scales.endpoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

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

/**
 * Note that this is a subclass of {@link TcpConnectionEndPoint}. This way we don't have to duplicate the setters for host and port. Feel free to add getters or "mock-controls" to influence the mock's
 * return values and behavior.
 * 
 * @author ts
 * 
 */
public class MockedEndpoint extends TcpConnectionEndPoint
{
	private int delayMillis = 1 * 1000;
	private final Random random = new Random(System.currentTimeMillis());

	@Override
	public String sendCmd(final String cmd)
	{
		System.out.println("Waiting " + delayMillis + "ms before returning the " + this + " result for command '" + cmd + "'");
		try
		{
			if (delayMillis > 0)
				Thread.sleep(delayMillis);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		// final String returnString = generateConstantResult();
		final String returnString = generateRandomResult();

		System.out.println("Returning: " + returnString);
		return returnString;
	}

	@SuppressWarnings("unused")
	private String generateConstantResult()
	{
		// return string for the busch
		// return "<000002.07.1413:25 111 0.0 90.0 -90.0kgPT2 1 11540>";

		// return string for the mettler
		final String returnString = "S S     561.348 kg";
		return returnString;
	}

	private String generateRandomResult()
	{
		//
		// Randomly throw exception
		if (random.nextInt(10) == 0)
		{
			throw new RuntimeException("Randomly generated exception");
		}

		//
		// Generate and return a random weight
		final BigDecimal weight = BigDecimal.valueOf(random.nextInt(1000000)).divide(new BigDecimal("1000"), 3, RoundingMode.HALF_UP);

		final DecimalFormat weightFormat = new DecimalFormat("#########.000");
		String weightStr = weightFormat.format(weight);
		while (weightStr.length() < 11)
			weightStr = " " + weightStr;

		final String resultString = "S S " + weightStr + " kg";
		return resultString;
	}
}
