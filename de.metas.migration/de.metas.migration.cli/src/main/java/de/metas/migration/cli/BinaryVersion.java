package de.metas.migration.cli;

/*
 * #%L
 * de.metas.migration.cli
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
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class gets the binary tool's version from its {@code MANIFEST.MF}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class BinaryVersion
{
	private static final String RESOURCENAME_MANIFEST = "META-INF/MANIFEST.MF";
	private static final Name NAME_CI_BUILD_TAG = new Name("jenkinsBuildTag");
	private static final Name NAME_CI_BUILD_NO = new Name("jenkinsBuildNo");

	// NOTE: keep this line after all constants init because else, those constants will be NULL
	public static final transient BinaryVersion instance = new BinaryVersion();

	protected final transient Logger logger = LoggerFactory.getLogger(getClass());

	private String implementationVendor;
	private String implementationTitle;
	private String implementationVersion;
	private String ciBuildNo;
	private String ciBuildTag;

	private BinaryVersion()
	{
		load();
	}

	private final void load()
	{
		URLClassLoader classLoader = null;

		try
		{
			classLoader = (URLClassLoader)getClass().getClassLoader();
		}
		catch (final Exception e)
		{
			logger.warn("Cannot load manifests. Only URLClassLoader is supported");
			return;
		}

		final URL resource = classLoader.findResource(RESOURCENAME_MANIFEST);
		if (resource == null)
		{
			logger.warn("No " + RESOURCENAME_MANIFEST + " found. Skip version info loading");
			return;
		}

		logger.debug("Loading version info from {}", resource);

		InputStream in = null;
		try
		{
			in = resource.openStream();
			final Manifest manifest = new Manifest(in);
			load(manifest);
		}
		catch (final IOException e)
		{
			logger.error("Error while loading " + RESOURCENAME_MANIFEST, e);
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (final IOException e)
				{
					// nothing
				}
			}
		}
	}

	private final void load(final Manifest manifest)
	{
		final Attributes attributes = manifest.getMainAttributes();

		if (attributes.containsKey(Name.IMPLEMENTATION_VENDOR))
		{
			implementationVendor = attributes.getValue(Name.IMPLEMENTATION_VENDOR);
		}

		if (attributes.containsKey(Name.IMPLEMENTATION_TITLE))
		{
			implementationTitle = attributes.getValue(Name.IMPLEMENTATION_TITLE);
		}

		if (attributes.containsKey(Name.IMPLEMENTATION_VERSION))
		{
			implementationVersion = attributes.getValue(Name.IMPLEMENTATION_VERSION);
		}

		if (attributes.containsKey(NAME_CI_BUILD_NO))
		{
			ciBuildNo = attributes.getValue(NAME_CI_BUILD_NO.toString());
		}

		if (attributes.containsKey(NAME_CI_BUILD_TAG))
		{
			ciBuildTag = attributes.getValue(NAME_CI_BUILD_TAG);
		}
	}

	@Override
	public String toString()
	{
		return "implementationVendor=" + implementationVendor
				+ ", implementationTitle=" + implementationTitle
				+ ", implementationVersion=" + implementationVersion
				+ ", ciBuildNo=" + ciBuildNo
				+ ", ciBuildTag=" + ciBuildTag;
	}

	public String getImplementationVendor()
	{
		return implementationVendor;
	}

	public String getImplementationTitle()
	{
		return implementationTitle;
	}

	public String getImplementationVersion()
	{
		return implementationVersion;
	}

	public String getCiBuildNo()
	{
		return ciBuildNo;
	}

	public String getCiBuildTag()
	{
		return ciBuildTag;
	}
}
