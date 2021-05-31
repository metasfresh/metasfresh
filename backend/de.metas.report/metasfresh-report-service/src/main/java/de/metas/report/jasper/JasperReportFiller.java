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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import de.metas.util.Services;
import lombok.NonNull;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
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
/* package */final class JasperReportFiller
{
	private static final transient Logger logger = LogManager.getLogger(JasperReportFiller.class);

	private static final JasperReportFiller instance = new JasperReportFiller();
	
	private static final String SYSCONFIG_JRSWAP_FILE_VIRTUALIZER_ACTIVE = "de.metas.report.jasper.JRSwapFileVirtualizer.active";
	private static final String SYSCONFIG_JRSWAP_FILE_VIRTUALIZER_MAX_SIZE = "de.metas.report.jasper.JRSwapFileVirtualizer.maxSize";
	private static final String SYSCONFIG_JRSWAP_FILE_BLOCK_SIZE = "de.metas.report.jasper.JRSwapFile.blockSize";
	private static final String SYSCONFIG_JRSWAP_FILE_MIN_GROW_COUNT = "de.metas.report.jasper.JRSwapFile.minGrowCount";
	private static final String SYSCONFIG_JRSWAP_FILE_TEMP_DIR_PREFIX = "de.metas.report.jasper.JRSwapFile.tempDirPrefix";

	public static JasperReportFiller getInstance()
	{
		return instance;
	}
	
	private JasperReportFiller()
	{
	}

	public JasperPrint fillReport(
			final JasperReport jasperReport,
			final Map<String, Object> parameters,
			final Connection connection,
			final ClassLoader jasperLoader) throws JRException
	{
		final Thread currentThread = Thread.currentThread();
		final ClassLoader classLoaderOld = currentThread.getContextClassLoader();

		try
		{
			final Map<String, Object> paramsFixed = new HashMap<>(parameters);
			fixParameterTypes(jasperReport, paramsFixed);
			setupAndPutVirtualizer(paramsFixed);

			// Set the jasper loader as thread context classloader.
			// We do this to workaround the issue from net.sf.jasperreports.engine.fill.JRFillDataset.loadResourceBundle(),
			// which is not fetching the right classloader.
			// More, that method is executed a separate thread for sub-reports, so fetching resource bundles will fail.
			currentThread.setContextClassLoader(jasperLoader);

			if (connection == null)
			{
				return JasperFillManager.fillReport(jasperReport, paramsFixed);
			}
			else
			{
				return JasperFillManager.fillReport(jasperReport, paramsFixed, connection);
			}
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
					.setParameter("jasperReport.name", jasperReport.getName());
		}
		finally
		{
			// restore the original class loader
			currentThread.setContextClassLoader(classLoaderOld);
		}
	}

	// thx to https://piotrminkowski.wordpress.com/2017/06/12/generating-large-pdf-files-using-jasperreports/
	private void setupAndPutVirtualizer(@NonNull final Map<String, Object> paramsFixed)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		
		final boolean useSwap = sysConfigBL.getBooleanValue(SYSCONFIG_JRSWAP_FILE_VIRTUALIZER_ACTIVE, true);
		if (!useSwap)
		{
			return;
		}

		try
		{
			final Path tempDirWithPrefix = Files.createTempDirectory(sysConfigBL.getValue(SYSCONFIG_JRSWAP_FILE_TEMP_DIR_PREFIX, "jasperSwapFiles"));
			final String directory = tempDirWithPrefix.toString();

			final int maxSize = sysConfigBL.getIntValue(SYSCONFIG_JRSWAP_FILE_VIRTUALIZER_MAX_SIZE, 200);
			final int blockSize = sysConfigBL.getIntValue(SYSCONFIG_JRSWAP_FILE_BLOCK_SIZE, 1024);
			final int minGrowCount = sysConfigBL.getIntValue(SYSCONFIG_JRSWAP_FILE_MIN_GROW_COUNT, 100);
			final boolean swapOwner = true;
			paramsFixed.put(JRParameter.REPORT_VIRTUALIZER, new JRSwapFileVirtualizer(maxSize, new JRSwapFile(directory, blockSize, minGrowCount), swapOwner));
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
					.setParameter("paramsFixed", paramsFixed);
		}
	}

	public JasperPrint fillReport(
			final JasperReport jasperReport,
			final Map<String, Object> parameters,
			final ClassLoader jasperLoader) throws JRException
	{
		final Connection connection = null;
		return fillReport(jasperReport, parameters, connection, jasperLoader);
	}

	protected void fixParameterTypes(
			@NonNull final JasperReport jasperReport,
			@NonNull final Map<String, Object> params)
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
			// Search for metasfresh Parameter name
			final String jrParamName = jrParam.getName();
			String adParamName = null;
			if (params.containsKey(jrParamName))
			{
				// direct: parameter name in Jasper report is same as in Adempiere
				adParamName = jrParamName;
			}
			else
			{
				// indirect: parameter name in Jasper report differs from metasfresh by upper/lower case
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
					logger.info("No parameter found for JR paramter: {}", jrParamName);
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
		logger.warn("Cannot convert value '{}' from {} to {}. Ignore", value, fromClass, targetClass);
		return value;
	}
}
