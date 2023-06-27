/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.ad.dao.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class DateIntervalIntersectionQueryFilterTest
{
	@Nullable
	private static Instant instant(@Nullable final String datePart)
	{
		return datePart != null ? LocalDate.parse(datePart).atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant() : null;
	}

	@Nullable
	private static Timestamp ts(@Nullable final String datePart)
	{
		final Instant instant = instant(datePart);
		return instant != null ? Timestamp.from(instant) : null;
	}

	private static DateIntervalIntersectionQueryFilter<I_TestRecord> filter(
			@Nullable final String date1,
			@Nullable final String date2)
	{
		return new DateIntervalIntersectionQueryFilter<>(
				ModelColumnNameValue.forColumnName(I_TestRecord.COLUMNNAME_Date1),
				ModelColumnNameValue.forColumnName(I_TestRecord.COLUMNNAME_Date2),
				date1 != null ? instant(date1) : null,
				date2 != null ? instant(date2) : null
		);
	}

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Nested
	class getSql_and_getSqlParams
	{
		@Test
		void regularCase()
		{
			final DateIntervalIntersectionQueryFilter<I_TestRecord> filter = filter("2020-02-10", "2020-02-20");
			assertThat(filter.getSql()).isEqualTo("NOT ISEMPTY(TSTZRANGE(Date1, Date2, '[)') * TSTZRANGE(?, ?, '[)'))");
			assertThat(filter.getSqlParams()).containsExactly(instant("2020-02-10"), instant("2020-02-20"));
		}

		@Test
		void acceptAll()
		{
			final DateIntervalIntersectionQueryFilter<I_TestRecord> filter = filter(null, null);
			assertThat(filter.getSql()).isEqualTo("1=1");
			assertThat(filter.getSqlParams()).isEmpty();
		}
	}

	@Nested
	class accept
	{
		void test(final boolean expected,
				  @Nullable final String range1_lower,
				  @Nullable final String range1_upper,
				  @Nullable final String range2_lower,
				  @Nullable final String range2_upper)
		{
			final I_TestRecord record = InterfaceWrapperHelper.newInstance(I_TestRecord.class);
			record.setDate1(ts(range1_lower));
			record.setDate2(ts(range1_upper));
			final DateIntervalIntersectionQueryFilter<I_TestRecord> filter = filter(range2_lower, range2_upper);

			assertThat(filter.accept(record)).isEqualTo(expected);
		}

		@Test
		void range1_before_range2()
		{
			test(false, "2020-02-01", "2020-02-10", "2020-02-20", "2020-02-25");
		}

		@Test
		void range1_before_range2_usingInfiniteBounds()
		{
			test(false, null, "2020-02-10", "2020-02-20", null);
		}

		@Test
		void range1_connectedTo_range2_onLowerEnd()
		{
			test(false, "2020-02-01", "2020-02-10", "2020-02-10", "2020-02-20");
		}

		@Test
		void range1_intersectsWith_range2_onLowerEnd()
		{
			test(true, "2020-02-01", "2020-02-10", "2020-02-09", "2020-02-20");
		}

		@Test
		void range1_includedIn_range2()
		{
			test(true, "2020-02-11", "2020-02-12", "2020-02-10", "2020-02-20");
		}

		@Test
		void range1_contains_range2()
		{
			test(true, "2020-02-01", "2020-02-25", "2020-02-10", "2020-02-20");
		}

		@Test
		void range1_intersectsWith_range2_onUpperEnd()
		{
			test(true, "2020-02-15", "2020-02-25", "2020-02-10", "2020-02-20");
		}

		@Test
		void range1_connectedTo_range2_onUpperEnd()
		{
			test(false, "2020-02-20", "2020-02-25", "2020-02-10", "2020-02-20");
		}

		@Test
		void range1_after_range2()
		{
			test(false, "2020-02-21", "2020-02-25", "2020-02-10", "2020-02-20");
		}
	}

	//
	//
	//

	interface I_TestRecord
	{
		String Table_Name = "TestRecord";

		String COLUMNNAME_Date1 = "Date1";

		// Timestamp getDate1();

		void setDate1(@Nullable Timestamp date1);

		String COLUMNNAME_Date2 = "Date2";

		// Timestamp getDate2();

		void setDate2(@Nullable Timestamp date2);
	}
}