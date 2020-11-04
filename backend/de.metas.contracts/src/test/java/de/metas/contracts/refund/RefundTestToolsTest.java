package de.metas.contracts.refund;

import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class RefundTestToolsTest
{

	/**
	 * make sure that RefundTestTools#computeInvoiceScheduleDayOfMonth() is always between 1 and 28
	 */
	@Test
	public void computeInvoiceScheduleDayOfMonth()
	{
		assertResultOk("2019-02-22", 28);
		assertResultOk("2019-02-23", 1);
		assertResultOk("2019-02-24", 2);
		assertResultOk("2019-02-25", 3);
		assertResultOk("2019-02-26", 4);
		assertResultOk("2019-02-27", 5);
		assertResultOk("2019-02-28", 6);
		assertResultOk("2019-03-01", 7);

		assertResultOk("2020-02-28", 6);
		assertResultOk("2020-02-29", 6);
		assertResultOk("2020-03-01", 7);
	}

	private void assertResultOk(
			@NonNull final String date,
			final int expected)
	{
		SystemTime.setFixedTimeSource(LocalDate.parse(date)
				.atStartOfDay(ZoneId.systemDefault()));

		final int result = RefundTestTools.computeInvoiceScheduleDayOfMonth();
		assertThat(result).isEqualTo(expected);
	}

}
