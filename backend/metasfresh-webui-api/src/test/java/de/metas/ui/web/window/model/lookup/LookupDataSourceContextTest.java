package de.metas.ui.web.window.model.lookup;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;
import org.compiere.util.Evaluatees;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class LookupDataSourceContextTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testContextDates()
	{
		testContextDate(LocalDate.of(2018, 11, 5));
		testContextDate(LocalDate.of(2018, 11, 5).atTime(LocalTime.of(13, 46)));
		testContextDate(LocalDate.of(2018, 11, 5).atTime(LocalTime.of(13, 46)).atZone(ZoneId.of("Europe/Berlin")));
		testContextDate(new Date());

		final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		testContextDate(timestamp);
		testContextDate(timestamp.toString(), TimeUtil.asDate(timestamp));
		testContextDate(Env.toString(timestamp), TimeUtil.asDate(TimeUtil.trunc(timestamp, TimeUtil.TRUNC_SECOND)));

		testContextDate("wrong value", null);
	}

	private static void testContextDate(final Object dateObj)
	{
		final Date expectedDate = TimeUtil.asDate(dateObj);
		testContextDate(dateObj, expectedDate);
	}

	private static void testContextDate(final Object dateObj, final Date expectedDate)
	{
		final LookupDataSourceContext evalCtx = LookupDataSourceContext.builder("TestTableName")
				.requiresParameter(CtxNames.parse("TestDate"))
				.setParentEvaluatee(Evaluatees.mapBuilder()
						.put("TestDate", dateObj)
						.build())
				.build();

		assertThat(evalCtx.get_ValueAsDate("TestDate", null)).isEqualTo(expectedDate);
	}

	@Test
	public void testContextInteger()
	{
		testContextInteger(1, 1);
		testContextInteger("1", 1);
		testContextInteger(BigDecimal.valueOf(1), 1);
		testContextInteger(IntegerLookupValue.of(1, "bla"), 1);

		testContextInteger("wrong value", null);
	}

	private static void testContextInteger(final Object intObj, final Integer expectedInteger)
	{
		final LookupDataSourceContext evalCtx = LookupDataSourceContext.builder("TestTableName")
				.requiresParameter(CtxNames.parse("TestInt"))
				.setParentEvaluatee(Evaluatees.mapBuilder()
						.put("TestInt", intObj)
						.build())
				.build();

		assertThat(evalCtx.get_ValueAsInt("TestInt", null)).isEqualTo(expectedInteger);
	}

	@Test
	public void testContextString()
	{
		testContextString("string", "string");
		testContextString(IntegerLookupValue.of(1, "one"), "1");
		testContextString(StringLookupValue.of("1", "one"), "1");
		testContextString(Boolean.TRUE, "Y");
		testContextString(Boolean.FALSE, "N");
	}

	private static void testContextString(final Object stringObj, final String expectedString)
	{
		final LookupDataSourceContext evalCtx = LookupDataSourceContext.builder("TestTableName")
				.requiresParameter(CtxNames.parse("TestString"))
				.setParentEvaluatee(Evaluatees.mapBuilder()
						.put("TestString", stringObj)
						.build())
				.build();

		assertThat(evalCtx.get_ValueAsString("TestString")).isEqualTo(expectedString);
	}

}
