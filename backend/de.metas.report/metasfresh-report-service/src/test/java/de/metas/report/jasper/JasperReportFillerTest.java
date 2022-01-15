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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JasperReportFillerTest
{
	@SuppressWarnings("SameParameterValue")
	private JasperReport loadJasperReport(String name) throws JRException
	{
		final String resourceName = name + ".jasper";
		final InputStream jasperInputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
		if (jasperInputStream == null)
		{
			throw new RuntimeException("Resource not found: " + resourceName);
		}

		return (JasperReport)JRLoader.loadObject(jasperInputStream);
	}

	@Test
	public void test_fixParameterTypes() throws Exception
	{
		final JasperReport jasperReport = loadJasperReport("paramsTestReport");
		final HashMap<String, Object> params = new HashMap<>();
		
		final Date date = new Date();
		final Timestamp ts = new Timestamp(date.getTime());

		params.put("PARAM_BigDecimal", 10);
		params.put("PARAM_Integer", new BigDecimal("20"));
		params.put("PARAM_StRiNg", "string");
		params.put("PARAM_Date", ts);
		params.put("PARAM_Timestamp", date);
		params.put("PARAM_Boolean", "Y");

		JasperReportFiller.fixParameterTypes(jasperReport, params);

		assertParameterValue(params, "PARAM_BigDecimal", BigDecimal.valueOf(10));
		assertParameterValue(params, "PARAM_Integer", 20);
		assertParameterValue(params, "PARAM_String", "string");
		assertParameterValue(params, "PARAM_Date", date);
		assertParameterValue(params, "PARAM_Timestamp", ts);
		assertParameterValue(params, "PARAM_Boolean", Boolean.TRUE);
	}
	
	@Test
	public void test_fixParameterTypes_Boolean() throws Exception
	{
		final JasperReport jasperReport = loadJasperReport("paramsTestReport");
		final HashMap<String, Object> params = new HashMap<>();
		final String parameterName = "PARAM_Boolean";
		
		assertParameterValue(params, parameterName, null);
		
		params.put(parameterName, false);
		JasperReportFiller.fixParameterTypes(jasperReport, params);
		assertParameterValue(params, parameterName, Boolean.FALSE);
		
		params.put(parameterName, true);
		JasperReportFiller.fixParameterTypes(jasperReport, params);
		assertParameterValue(params, parameterName, Boolean.TRUE);
		
		params.put(parameterName, "false");
		JasperReportFiller.fixParameterTypes(jasperReport, params);
		assertParameterValue(params, parameterName, Boolean.FALSE);
		
		params.put(parameterName, "true");
		JasperReportFiller.fixParameterTypes(jasperReport, params);
		assertParameterValue(params, parameterName, Boolean.TRUE);
		
		params.put(parameterName, "N");
		JasperReportFiller.fixParameterTypes(jasperReport, params);
		assertParameterValue(params, parameterName, Boolean.FALSE);
		
		params.put(parameterName, "Y");
		JasperReportFiller.fixParameterTypes(jasperReport, params);
		assertParameterValue(params, parameterName, Boolean.TRUE);

		params.put(parameterName, "any other string");
		JasperReportFiller.fixParameterTypes(jasperReport, params);
		assertParameterValue(params, parameterName, Boolean.FALSE);
		
		params.put(parameterName, null);
		JasperReportFiller.fixParameterTypes(jasperReport, params);
		assertParameterValue(params, parameterName, null);
	}

	
	private void assertParameterValue(Map<String, Object> params, String parameterName, Object expectedValue)
	{
		final Object actualValue = params.get(parameterName);
		assertThat(actualValue).as("Invalid value for parameter " + parameterName).isEqualTo(expectedValue);
	}
}
