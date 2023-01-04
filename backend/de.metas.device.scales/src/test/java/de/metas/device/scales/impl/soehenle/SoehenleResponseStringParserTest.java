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

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SoehenleResponseStringParserTest
{
	private final Map<String, String> responseToResultMap = new HashMap<>();

	{
		responseToResultMap.put("001001N 10,095 kg ", "10,095");//happy flow
		responseToResultMap.put("A1234567001001N 10,095 kg ", "10,095");//happy flow in Alibispeicher format
		responseToResultMap.put("100001N    0,0 kg ", "0");//underweight
		responseToResultMap.put("A1234567100001N    0,0 kg ", "0");//underweight in Alibispeicher format
		responseToResultMap.put("010001N    0,0 kg ", "0");//overweight
		responseToResultMap.put("A1234567010001N    0,0 kg ", "0");//overweight in Alibispeicher format
	}

	@Test
	public void checkResponseStringParser()
	{
		final SoehenleResponseStringParser parser = new SoehenleResponseStringParser();
		final ISoehenleCmd cmd = SoehenleInstantGrossWeightCmd.getInstance();
		final SoftAssertions assertions = new SoftAssertions();
		for (final String response : responseToResultMap.keySet())
		{
			assertions.assertThat(responseToResultMap.get(response)).isEqualTo(parser.parse(cmd, response, ISoehenleCmd.RESULT_ELEMENT_WEIGHT_VALUE, String.class));
		}
		assertions.assertAll();
	}
}
