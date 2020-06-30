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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
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
	private static final Logger logger = LogManager.getLogger(JasperCompileClassLoader.class);

	private static final String jasperExtension = ".jasper";
	private static final String jrxmlExtension = ".jrxml";
	private static final String propertiesExtension = ".properties";
	private static final String xlsExtension = ".xls";

	private final ImmutableSet<File> additionalResourceDirNames;
	private final Map<String, Optional<JasperEntry>> jasperEntriesByJrxmlPath = new ConcurrentHashMap<>();

	@Builder
	private JasperCompileClassLoader(
			@Nullable final ClassLoader parentClassLoader,
			@NonNull @Singular final List<File> additionalResourceDirNames)
	{
		super(parentClassLoader);

		this.additionalResourceDirNames = ImmutableSet.copyOf(additionalResourceDirNames);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("additionalResourceDirNames", additionalResourceDirNames)
				.add("parent", getParent())
				.toString();
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
			return getJaserResource(nameNormalized);
		}
		else if (nameNormalized.endsWith(xlsExtension))
		{
			return findMiscResource(nameNormalized);
		}
		// handle property files (i.e. resource bundles)
		else if (nameNormalized.endsWith(propertiesExtension))
		{
			return findMiscResource(nameNormalized);
		}
		else
		{
			return findResourceInAdditionalPathsOrNull(nameNormalized);
		}
	}

	private URL getJaserResource(final String name)
	{
		final String jrxmlPath = toLocalPath(name, jrxmlExtension);
		if (jrxmlPath == null)
		{
			return null;
		}

		return jasperEntriesByJrxmlPath.computeIfAbsent(jrxmlPath, this::computeJasperEntry)
				.map(JasperEntry::getJasperUrl)
				.orElse(null);
	}

	private Optional<JasperEntry> computeJasperEntry(@NonNull final String jrxmlPath)
	{
		logger.trace("Computing jasper report for {}", jrxmlPath);

		//
		// Get resource's input stream
		String jrxmlPathNorm = jrxmlPath;
		URL jrxmlUrl = getResource(jrxmlPathNorm);

		// TODO: fix this fucked up
		if (jrxmlUrl == null && jrxmlPath.startsWith("/"))
		{
			jrxmlPathNorm = jrxmlPath.substring(1);
			jrxmlUrl = getResource(jrxmlPathNorm);
		}

		if (jrxmlUrl == null)
		{
			logger.trace("No JRXML resource found for {}", jrxmlPath);
			return Optional.empty();
		}

		final File jrxmlFile = toLocalFile(jrxmlUrl);
		final File jasperFile = compileJrxml(jrxmlFile);
		logger.trace("Compiled jasper report: {} <- {}", jasperFile, jrxmlFile);

		return Optional.of(JasperEntry.builder()
				.jrxmlFile(jrxmlFile)
				.jasperFile(jasperFile)
				.build());
	}

	private URL findMiscResource(final String name)
	{
		final String resourcePath = toLocalPath(name, FileUtil.getFileExtension(name));

		URL url = findResourceInAdditionalPathsOrNull(resourcePath);
		if (url != null)
		{
			return url;
		}

		final ClassLoader parentClassLoader = getParent();
		url = parentClassLoader.getResource(resourcePath);
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

	private URL findResourceInAdditionalPathsOrNull(final String resourceName)
	{
		for (final File resourceDir : additionalResourceDirNames)
		{
			final File resourceFile = new File(resourceDir, resourceName);
			if (resourceFile.exists() && resourceFile.isFile())
			{
				try
				{
					return resourceFile.toURI().toURL();
				}
				catch (final MalformedURLException e)
				{
					logger.trace("Not considering resourceFile={} for resourceName={} because it cannot be converted to URL", resourceFile, resourceName, e);
				}
			}
		}

		return null;
	}

	private static File toLocalFile(@NonNull final URL url)
	{
		try
		{
			return new File(url.toURI());
		}
		catch (URISyntaxException ex)
		{
			throw new AdempiereException("Cannot convert URL to local File: " + url, ex);
		}
	}

	private static String toLocalPath(final String resourceName, final String fileExtension)
	{
		String resourcePath = resourceName.trim()
				.replace(JasperClassLoader.PLACEHOLDER, "")
				.replace("//", "/");

		if (!resourcePath.startsWith("/"))
		{
			resourcePath = "/" + resourcePath;
		}

		final String jasperReportJrxmlPath = FileUtil.changeFileExtension(resourcePath, fileExtension);

		return jasperReportJrxmlPath;
	}

	private static File compileJrxml(final File jrxmlFile)
	{
		try (InputStream jrxmlStream = new FileInputStream(jrxmlFile))
		{
			final File jasperFile = File.createTempFile("JasperReport", jasperExtension);

			try (FileOutputStream jasperStream = new FileOutputStream(jasperFile))
			{
				JasperCompileManager.compileReportToStream(jrxmlStream, jasperStream);
			}

			return jasperFile;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed compiling jasper report: " + jrxmlFile, ex);
		}
	}

	@Override
	protected Enumeration<URL> findResources(final String name) throws IOException
	{
		final URL url = findResource(name);
		if (url == null)
		{
			return super.findResources(name);
		}

		return Collections.enumeration(Arrays.asList(url));
	}

	@Value
	@Builder
	private static class JasperEntry
	{
		@NonNull
		File jrxmlFile;

		@NonNull
		File jasperFile;

		public URL getJasperUrl()
		{
			try
			{
				return jasperFile.toURI().toURL();
			}
			catch (final MalformedURLException e)
			{
				throw new AdempiereException("Cannot convert " + jasperFile + " to URL", e);
			}
		}
	}
}
