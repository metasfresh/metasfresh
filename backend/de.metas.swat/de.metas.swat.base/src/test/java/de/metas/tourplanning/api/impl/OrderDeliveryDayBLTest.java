/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.tourplanning.api.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

class OrderDeliveryDayBLTest
{
	@Nested
	class computePreparationTime
	{
		@Test
		void daylightSaving_beginningOfSummerTime_minus_24h()
		{
			Assertions.assertThat(OrderDeliveryDayBL.computePreparationTime(ZonedDateTime.parse("2023-03-26T23:59:59+03:00[Europe/Bucharest]"), -24))
					.isEqualTo(ZonedDateTime.parse("2023-03-25T23:59:59+02:00[Europe/Bucharest]"));

			Assertions.assertThat(OrderDeliveryDayBL.computePreparationTime(ZonedDateTime.parse("2023-03-26T23:59:59+02:00[Europe/Berlin]"), -24))
					.isEqualTo(ZonedDateTime.parse("2023-03-25T23:59:59+01:00[Europe/Berlin]"));
		}
	}
}
