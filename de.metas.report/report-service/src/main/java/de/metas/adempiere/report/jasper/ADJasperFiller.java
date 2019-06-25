package de.metas.adempiere.report.jasper;

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

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Helper class used to fill a {@link JasperReport} and produce {@link JasperPrint}.
 */
/* package */final class ADJasperFiller
{
	private static final transient Logger log = LogManager.getLogger(ADJasperFiller.class);

	private static final ADJasperFiller instance = new ADJasperFiller();

	public static ADJasperFiller getInstance()
	{
		return instance;
	}

	private ADJasperFiller()
	{
		super();
	}

	public JasperPrint fillReport(
			final JasperReport jasperReport,
			final Map<String, Object> parameters,
			final Connection connection,
			final ClassLoader jasperLoader) throws JRException
	{
		final Map<String, Object> paramsFixed = new HashMap<String, Object>(parameters);
		fixParameterTypes(jasperReport, paramsFixed);

		final Thread currentThread = Thread.currentThread();
		final ClassLoader classLoaderOld = currentThread.getContextClassLoader();

		// Set the jasper loader as thread context classloader.
		// We do this to workaround the issue from net.sf.jasperreports.engine.fill.JRFillDataset.loadResourceBundle(),
		// which is not fetching the right classloader.
		// More, that method is executed a separate thread for sub-reports, so fetching resource bundles will fail.
		currentThread.setContextClassLoader(jasperLoader);

		try
		{
			if (connection == null)
			{
				return JasperFillManager.fillReport(jasperReport, paramsFixed);
			}
			else
			{
				return JasperFillManager.fillReport(jasperReport, paramsFixed, connection);
			}
		}
		finally
		{
			// restore the original class loader
			currentThread.setContextClassLoader(classLoaderOld);
		}
	}


	public JasperPrint fillReport(
			final JasperReport jasperReport,
			final Map<String, Object> parameters,
			final ClassLoader jasperLoader)  throws JRException
	{


		return fillReport(jasperReport, parameters, null, jasperLoader);
	}

	protected void fixParameterTypes(final JasperReport jasperReport, final Map<String, Object> params)
	{
		final JRParameter[] jrParameters = jasperReport.getParameters();
		if (jrParameters == null || jrParameters.length == 0)
		{
			// no JR parameters, nothing to do
			return;
		}

		for (final JRParameter jrParam : jrParameters)
		{
			//
			// Search for Adempiere Parameter name
			final String jrParamName = jrParam.getName();
			String adParamName = null;
			if (params.containsKey(jrParamName))
			{
				// direct: parameter name in Jasper report is same as in Adempiere
				adParamName = jrParamName;
			}
			else
			{
				// indirect: parameter name in Jasper report differs from Adempiere by upper/lower case
				for (final String name : params.keySet())
				{
					if (jrParamName.equalsIgnoreCase(name))
					{
						adParamName = name;
						break;
					}
				}
			}

			if (adParamName == null)
			{
				if (jrParam.isForPrompting() && !jrParam.isSystemDefined())
				{
					log.info("No parameter found for JR paramter: " + jrParamName);
				}
				continue;
			}

			final Object adParamValue = params.get(adParamName);
			final Class<?> jrValueClass = jrParam.getValueClass();
			final Object jrParamValue = convertValue(adParamValue, jrValueClass);

			params.remove(adParamName);
			params.put(jrParamName, jrParamValue);
		}
	}

	private Object convertValue(final Object value, final Class<?> targetClass)
	{
		if (value == null)
		{
			// null parameter, no conversion is needed
			return null;
		}

		final Class<?> fromClass = value.getClass();
		if (targetClass.isAssignableFrom(fromClass))
		{
			// compatible types, no conversion is needed
			return value;
		}

		// If target class is String, convert it right away
		if (String.class.equals(targetClass))
		{
			return value.toString();
		}

		// Convert from BigDecimal to other values
		if (BigDecimal.class.isAssignableFrom(fromClass))
		{
			final BigDecimal bd = (BigDecimal)value;
			if (Integer.class.equals(targetClass))
			{
				return bd.intValueExact();
			}
			else if (Double.class.equals(targetClass))
			{
				return bd.doubleValue();
			}
			else if (Float.class.equals(targetClass))
			{
				return bd.floatValue();
			}
		}
		//
		else if (Integer.class.isAssignableFrom(fromClass))
		{
			final Integer ii = (Integer)value;
			if (BigDecimal.class.equals(targetClass))
			{
				return BigDecimal.valueOf(ii);
			}
		}
		else if (java.util.Date.class.isAssignableFrom(fromClass))
		{
			final java.util.Date date = (java.util.Date)value;
			if (java.sql.Timestamp.class.equals(targetClass))
			{
				return new java.sql.Timestamp(date.getTime());
			}
		}
		else if (String.class.isAssignableFrom(fromClass))
		{
			final String str = (String)value;
			if (Integer.class.equals(targetClass))
			{
				return Integer.parseInt(str);
			}
			else if (BigDecimal.class.equals(targetClass))
			{
				return new BigDecimal(str);
			}
			else if (Boolean.class.equals(targetClass))
			{
				return Boolean.parseBoolean(str)
						|| "Y".equals(str); // ADempiere standard
			}
		}

		// Nothing matched, log warning and return original value
		log.warn("Cannot convert value '" + value + "' from " + fromClass + " to " + targetClass + ". Ignore");
		return value;
	}
}
