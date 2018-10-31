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


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.io.Closeables;

import de.metas.util.Check;
import de.metas.util.FileUtils;
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 * Alternative class loader to be used when doing dev-tests on a local machine.<br>
 * This class loader will be used by {@link JasperEngine} if we run in developer mode.
 * 
 * @author tsa
 *
 */
public class JasperCompileClassLoader extends ClassLoader
{
	public static final String jasperExtension = ".jasper";
	public static final String jrxmlExtension = ".jrxml";
	public static final String propertiesExtension = ".properties";

	public JasperCompileClassLoader()
	{
		super();
	}

	public JasperCompileClassLoader(ClassLoader parent)
	{
		super(parent);
	}

	@Override
	protected URL findResource(final String name)
	{
		if (Check.isEmpty(name, true))
		{
			return null;
		}

		final String nameNormalized = name.trim();
		if (nameNormalized.endsWith(jasperExtension))
		{
			return findJaserResource(nameNormalized);
		}
		// handle Excel reporting templates
		else if (nameNormalized.endsWith(".xls"))
		{
			return findMiscResource(nameNormalized);
		}
		// handle property files (i.e. resource bundles)
		else if (nameNormalized.endsWith(propertiesExtension))
		{
			return findMiscResource(nameNormalized);
		}

		// other resources will be handled by parent ClassLoader
		return null;
	}

	private Map<String, URL> jrxml2jasper = new ConcurrentHashMap<String, URL>();

	private URL findJaserResource(final String name)
	{
		final String jasperReportJrxmlPath = toLocalPath(name, jrxmlExtension);
		if (jasperReportJrxmlPath == null)
		{
			return null;
		}

		if (jrxml2jasper.containsKey(jasperReportJrxmlPath))
		{
			return jrxml2jasper.get(jasperReportJrxmlPath);
		}

		//
		// Get resource's input stream
		InputStream jrxmlStream = getResourceAsStream(jasperReportJrxmlPath);
		// TODO: fix this fucked up
		if (jrxmlStream == null && jasperReportJrxmlPath.startsWith("/"))
		{
			jrxmlStream = getResourceAsStream(jasperReportJrxmlPath.substring(1));
		}
		if (jrxmlStream == null)
		{
			// jrxml2jasper.put(jasperReportJrxmlPath, null);
			return null;
		}

		final URL jasperURL = compileJRXML(jrxmlStream);
		if (jasperURL != null)
		{
			jrxml2jasper.put(jasperReportJrxmlPath, jasperURL);
		}

		return jasperURL;
	}

	private URL findMiscResource(final String name)
	{
		String fileExtension = FileUtils.getFileExtension(name);
		final String resourcePath = toLocalPath(name, fileExtension);

		final ClassLoader parentClassLoader = getParent();
		URL url = parentClassLoader.getResource(resourcePath);
		if (url != null)
		{
			return url;
		}

		if (resourcePath.startsWith("/"))
		{
			url = parentClassLoader.getResource(resourcePath.substring(1));
		}

		return url;
	}

	private String toLocalPath(final String resourceName, final String fileExtension)
	{
		String resourcePath = resourceName.trim()
				.replace(JasperClassLoader.PLACEHOLDER, "")
				.replace("//", "/");

		if (!resourcePath.startsWith("/"))
		{
			resourcePath = "/" + resourcePath;
		}

		final String jasperReportJrxmlPath = FileUtils.changeFileExtension(resourcePath, fileExtension);

		return jasperReportJrxmlPath;
	}

	private URL compileJRXML(final InputStream jrxmlStream)
	{
		FileOutputStream jasperStream = null;
		try
		{
			final File jasperFile = File.createTempFile("JasperReport", jasperExtension);
			jasperStream = new FileOutputStream(jasperFile);
			JasperCompileManager.compileReportToStream(jrxmlStream, jasperStream);
			jasperStream.flush();
			jasperStream.close();
			return jasperFile.toURI().toURL();
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			Closeables.closeQuietly(jrxmlStream);
		}
	}

	@Override
	protected Enumeration<URL> findResources(String name) throws IOException
	{
		final URL url = findResource(name);
		if (url == null)
		{
			return super.findResources(name);
		}

		return Collections.enumeration(Arrays.asList(url));
	}
}
