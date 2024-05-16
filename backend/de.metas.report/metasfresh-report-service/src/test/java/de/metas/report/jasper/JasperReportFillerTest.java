package de.metas.report.jasper;

/*
 * #%L
 * de.metas.report.jasper.server.base
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

import com.google.common.base.Stopwatch;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JasperReportFillerTest
{
	private static JasperReport paramsTestReport;

	@BeforeAll
	public static void beforeAll() throws Exception
	{
		paramsTestReport = loadJasperReport("paramsTestReport");
	}

	@SuppressWarnings("SameParameterValue")
	private static JasperReport loadJasperReport(String name) throws JRException
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final String resourceName = name + ".jasper";
		final InputStream jasperInputStream = JasperReportFillerTest.class.getClassLoader().getResourceAsStream(resourceName);
		if (jasperInputStream == null)
		{
			throw new RuntimeException("Resource not found: " + resourceName);
		}

		final JasperReport jasperReport = (JasperReport)JRLoader.loadObject(jasperInputStream);
		stopwatch.stop();
		System.out.println("Loaded " + resourceName + " in " + stopwatch);

		return jasperReport;
	}

	private static void assertJRParameterType(final JasperReport jasperReport, final String parameterName, final Class<?> expectedType)
	{
		final Class<?> valueClass = Arrays.stream(jasperReport.getParameters())
				.filter(jrParam -> jrParam.getName().equals(parameterName))
				.findFirst()
				.map(JRParameter::getValueClass)
				.orElseThrow(() -> new AdempiereException("Parameter not found: " + parameterName));
		assertThat(valueClass).isEqualTo(expectedType);
	}

	private static void assertParameterValue(Map<String, Object> params, String parameterName, Object expectedValueAndType)
	{
		final Object actualValue = params.get(parameterName);
		assertThat(actualValue)
				.isEqualTo(expectedValueAndType)
				.hasSameClassAs(expectedValueAndType);
	}

	@Nested
	class fixParameterTypes
	{
		@Test
		public void julDate()
		{
			final HashMap<String, Object> params = new HashMap<>();

			final java.util.Date date = new java.util.Date();
			final java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());

			params.put("PARAM_Date", ts);
			JasperReportFiller.fixParameterTypes(paramsTestReport, params);

			// NOTE: there is no need to convert Timestamp->Date because Timestamp is already a Date
			assertJRParameterType(paramsTestReport, "PARAM_Date", java.util.Date.class);
			assertThat(params.get("PARAM_Date")).isSameAs(ts);
		}

		@Test
		public void sqlTimestamp()
		{
			final HashMap<String, Object> params = new HashMap<>();

			final java.util.Date date = new java.util.Date();
			final java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());

			params.put("PARAM_Timestamp", date);
			JasperReportFiller.fixParameterTypes(paramsTestReport, params);

			assertJRParameterType(paramsTestReport, "PARAM_Timestamp", java.sql.Timestamp.class);
			assertParameterValue(params, "PARAM_Timestamp", ts);
		}

		@Test
		public void booleanParameter()
		{
			final String parameterName = "PARAM_Boolean";
			assertJRParameterType(paramsTestReport, parameterName, Boolean.class);

			final HashMap<String, Object> params = new HashMap<>();

			assertThat(params).doesNotContainKey(parameterName);

			params.put(parameterName, false);
			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			assertParameterValue(params, parameterName, Boolean.FALSE);

			params.put(parameterName, true);
			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			assertParameterValue(params, parameterName, Boolean.TRUE);

			params.put(parameterName, "false");
			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			assertParameterValue(params, parameterName, Boolean.FALSE);

			params.put(parameterName, "true");
			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			assertParameterValue(params, parameterName, Boolean.TRUE);

			params.put(parameterName, "N");
			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			assertParameterValue(params, parameterName, Boolean.FALSE);

			params.put(parameterName, "Y");
			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			assertParameterValue(params, parameterName, Boolean.TRUE);

			params.put(parameterName, "any other string");
			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			assertParameterValue(params, parameterName, Boolean.FALSE);

			params.put(parameterName, null);
			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			//noinspection AssertThatCollectionOrMapExpression
			assertThat(params.get(parameterName)).isNull();
		}

		@Test
		public void bigDecimalParam()
		{
			final HashMap<String, Object> params = new HashMap<>();
			params.put("PARAM_BigDecimal", 10);

			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			assertJRParameterType(paramsTestReport, "PARAM_BigDecimal", BigDecimal.class);
			assertParameterValue(params, "PARAM_BigDecimal", BigDecimal.valueOf(10));
		}

		@Test
		public void integerParam()
		{
			final HashMap<String, Object> params = new HashMap<>();
			params.put("PARAM_Integer", new BigDecimal("20"));

			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			assertJRParameterType(paramsTestReport, "PARAM_Integer", Integer.class);
			assertParameterValue(params, "PARAM_Integer", 20);
		}

		@Test
		public void stringParam_differentCase()
		{
			final HashMap<String, Object> params = new HashMap<>();
			//noinspection StringOperationCanBeSimplified
			final String stringValue = new String("string"); // to make sure we get a new instance
			params.put("PARAM_StRiNg", stringValue);

			JasperReportFiller.fixParameterTypes(paramsTestReport, params);
			assertJRParameterType(paramsTestReport, "PARAM_String", String.class);
			assertThat(params.get("PARAM_String")).isSameAs(stringValue);
		}
	}
}
