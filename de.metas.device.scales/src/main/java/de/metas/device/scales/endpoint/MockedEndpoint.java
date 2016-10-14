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
// TODO: make it configurable in a way similar to a normal endpoint
public class MockedEndpoint extends TcpConnectionEndPoint
{
	private static final boolean throwRandomException = false;
	private int delayMillis = 30; // was set to 1 * 1000, but 30ms max is more realistic.
	private final Random random = new Random(System.currentTimeMillis());

	private static int mockedEndpointInstanceCount = 0;

	private static int weightSequenceIdx = 0;

	private final int mockedEndpointInstanceNumber;

	private final static BigDecimal[] weightSequence = {
			new BigDecimal("450"),
			new BigDecimal("460"),
			new BigDecimal("470"),
			new BigDecimal("480"),
			new BigDecimal("490")
	};

	public MockedEndpoint()
	{
		synchronized (MockedEndpoint.class)
		{
			mockedEndpointInstanceNumber = mockedEndpointInstanceCount;
			mockedEndpointInstanceCount++;
		}
	}

	@Override
	public String sendCmd(final String cmd)
	{
		System.out.println("Waiting " + delayMillis + "ms before returning the " + this + " result for command '" + cmd + "'");
		try
		{
			if (delayMillis > 0)
			{
				Thread.sleep(delayMillis);
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		// final String returnString = generateConstantResult();
		// final String returnString = generateRandomResult();
		final String returnString = generateSequenceResult();

		// System.out.println("Returning: " + returnString);
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

	@SuppressWarnings("unused")
	private String generateRandomResult()
	{
		//
		// Randomly throw exception
		if (throwRandomException && random.nextInt(10) == 0)
		{
			throw new RuntimeException("Randomly generated exception");
		}

		//
		// Generate and return a random weight
		final BigDecimal weight = BigDecimal.valueOf(random.nextInt(1000000)).divide(new BigDecimal("1000"), 3, RoundingMode.HALF_UP);

		final String resultString = createWeightString(weight);
		return resultString;
	}

	/**
	 * Returns a number of predefined results in a round-robin fashion.
	 * The result will be one of <code>450, 460, ... , 490, 450, ...</code> <b>plus</b> an instance-specific offset (e.g. for the 2nd instance, it will be <code>451, 461, ...</code>)
	 *
	 * @return
	 */
	private String generateSequenceResult()
	{
		synchronized (MockedEndpoint.class)
		{
			weightSequenceIdx++;
			if (weightSequenceIdx >= weightSequence.length)
			{
				weightSequenceIdx = 0;
			}

			final BigDecimal weighFromArray = weightSequence[weightSequenceIdx];
			final BigDecimal weightToUse = weighFromArray.add(new BigDecimal(mockedEndpointInstanceNumber));

			final String returnString = createWeightString(weightToUse);

			return returnString;
		}
	}

	public static String createWeightString(final BigDecimal weight)
	{
		final DecimalFormat weightFormat = new DecimalFormat("#########.000");
		String weightStr = weightFormat.format(weight);
		while (weightStr.length() < 11)
		{
			weightStr = " " + weightStr;
		}
		final String resultString = "S S " + weightStr + " kg";
		return resultString;
	}
}
