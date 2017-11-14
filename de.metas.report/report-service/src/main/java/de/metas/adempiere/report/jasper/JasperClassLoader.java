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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.adempiere.report.jasper.model.I_AD_OrgInfo;
import de.metas.logging.LogManager;

/**
 * Jasper class loader: basically it will resolve {@link #PLACEHOLDER} from resource names and will fetch the resources from remote HTTP servers.
 */
public final class JasperClassLoader extends ClassLoader
{
	// services
	private static final transient Logger logger = LogManager.getLogger(JasperClassLoader.class);

	public static final String PLACEHOLDER = "@PREFIX@";

	private String prefix;
	private boolean alwaysPrependPrefix = false;

	// Hooks
	private final OrgLogoClassLoaderHook logoHook;

	public JasperClassLoader(final int adOrgId, final ClassLoader parent)
	{
		super(parent);
		Check.assumeNotNull(parent, "Param 'parent' is not null");

		this.prefix = retrieveReportPrefix(adOrgId);
		this.logoHook = OrgLogoClassLoaderHook.forAD_Org_ID(adOrgId);
	}

	private static final String retrieveReportPrefix(final int adOrgId)
	{
		final String whereClause = I_AD_OrgInfo.COLUMNNAME_AD_Org_ID + "=?";
		final I_AD_OrgInfo orgInfo = new Query(Env.getCtx(), I_AD_OrgInfo.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setParameters(adOrgId)
				.firstOnly(I_AD_OrgInfo.class);
		final String reportPrefix = orgInfo.getReportPrefix();

		logger.info("ReportPrefix: " + reportPrefix + " (AD_Org_ID=" + adOrgId + ")");

		return reportPrefix;
	}

	@Override
	protected URL findResource(final String name)
	{
		// guard against null
		if (Check.isEmpty(name, true))
		{
			return null;
		}

		final String urlStr = convertResourceNameToURLString(name);
		try
		{
			final URL url = new URL(urlStr);
			logger.debug("URL: {} for {}", new Object[] { url, name });

			if (isJarInJarURL(url))
			{
				logger.debug("Returning null, because this class loader won't be able to open this resource.");
				// return null to avoid errors like
				// org.apache.commons.vfs2.FileSystemException: Could not replicate "file:///opt/metasfresh/metasfresh-server.jar!/lib/spring-beans-4.2.5.RELEASE.jar" as it does not exist.
				return null;
			}
			return url;
		}
		// Task FRESH-517
		// Keeping this log for debug mode only.
		// In the past this could not happen so often but now, this class loader can try to find any kind of resource for a jasper report and they are not all URLs.
		//
		catch (MalformedURLException e)
		{
			logger.debug("Got invalid URL '{}' for '{}'. Returning null.", urlStr, name, e);
		}
		return null;
	}

	@Override
	public URL getResource(String name)
	{
		final URL url = logoHook.getResourceURLOrNull(name);
		if (url != null)
		{
			return url;
		}

		final URL resource = super.getResource(name);
		if (isJarInJarURL(resource))
		{
			logger.debug("Returning null, because this class loader won't be able to open this resource.");
			// return null to avoid errors like
			// org.apache.commons.vfs2.FileSystemException: Could not replicate "file:///opt/metasfresh/metasfresh-server.jar!/lib/spring-beans-4.2.5.RELEASE.jar" as it does not exist.
			return getParent().getResource(name);
		}

		return resource;
	}

	@Override
	public InputStream getResourceAsStream(final String name)
	{
		//
		// Get resource's URL
		final URL url = getResource(name);
		logger.debug("URL: {} for {}", new Object[] { url, name });

		if (url == null)
		{
			return null; // no resource URL found
		}
		if (isJarInJarURL(url))
		{
			logger.debug("Returning null, because this class loader won't be able to open this resource.");
			// invoke the parent to avoid errors like
			// org.apache.commons.vfs2.FileSystemException: Could not replicate "file:///opt/metasfresh/metasfresh-server.jar!/lib/spring-beans-4.2.5.RELEASE.jar" as it does not exist.
			return getParent().getResourceAsStream(name);
		}

		try
		{
			final FileSystemManager fsManager = VFS.getManager();
			final FileObject jasperFile = fsManager.resolveFile(url.toString());
			final FileContent jasperData = jasperFile.getContent();
			final InputStream is = jasperData.getInputStream();

			// copy the stream data to a local stream
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			JasperUtil.copy(is, out);
			is.close();
			jasperFile.close();

			final InputStream result = new ByteArrayInputStream(out.toByteArray());

			return result;
		}
		catch (org.apache.commons.vfs2.FileNotFoundException e)
		{
			logger.debug("Resource not found. Skipping.", e);
			return getParent().getResourceAsStream(name);
		}
		catch (FileSystemException e)
		{
			logger.warn("Error while retrieving bytes for resource " + url + ". Skipping.", e);
			return getParent().getResourceAsStream(name);
		}
		catch (IOException e)
		{
			throw new AdempiereException("IO error while retrieving bytes for resource " + url, e);
		}
	}

	/**
	 * Returns true, e.g. for <code>file:/opt/metasfresh/metasfresh-server.jar!/lib/spring-beans-4.2.5.RELEASE.jar</code>.<br>
	 * Such URLs can't be handled by our vfs implementation.
	 *
	 * @param url
	 * @return
	 */
	@VisibleForTesting
	/* package */ static boolean isJarInJarURL(final URL url)
	{
		if (url == null)
		{
			return false;
		}

		final String urlStr = url.toString();
		return urlStr.matches("(jar:)?file:/.*!.*");
	}

	/**
	 * Converts given resource name to URL string. Mainly it will parse {@link #PLACEHOLDER}.
	 *
	 * @param resourceName
	 * @return resource's URL string
	 */
	private final String convertResourceNameToURLString(final String resourceName)
	{
		if (Check.isEmpty(prefix))
		{
			return resourceName;
		}

		final StringBuilder urlStr = new StringBuilder();

		if (resourceName.startsWith(PLACEHOLDER))
		{

			if (resourceName.startsWith(PLACEHOLDER + "/"))
			{

				urlStr.append(resourceName.replace(PLACEHOLDER, prefix));
			}
			else
			{
				if (prefix.endsWith("/"))
				{
					urlStr.append(resourceName.replace(PLACEHOLDER, prefix));
				}
				else
				{
					urlStr.append(resourceName.replace(PLACEHOLDER, prefix + "/"));
				}
			}
			alwaysPrependPrefix = true;
			return urlStr.toString();
		}
		else
		{
			final Pattern pattern = Pattern.compile("@([\\S]+)@([\\S]+)");
			final Matcher matcher = pattern.matcher(resourceName);

			if (matcher.find())
			{

				prefix = matcher.group(1);
				urlStr.append(prefix);

				final String report = matcher.group(2);

				if (!prefix.endsWith("/") && !report.startsWith("/"))
				{
					urlStr.append("/");
				}
				urlStr.append(report);
				alwaysPrependPrefix = true;

				return urlStr.toString();
			}
		}

		if (alwaysPrependPrefix)
		{
			urlStr.append(prefix);

			if (!prefix.endsWith("/") && !resourceName.startsWith("/"))
			{
				urlStr.append("/");
			}
		}
		urlStr.append(resourceName);
		return urlStr.toString();
	}
}
