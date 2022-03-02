package de.metas.report.jasper.class_loader;

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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import de.metas.attachments.AttachmentEntryService;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.report.PrintFormatId;
import de.metas.report.jasper.class_loader.images.ad_image.AdImageClassLoaderHook;
import de.metas.report.jasper.class_loader.images.attachment.AttachmentImageFileClassLoaderHook;
import de.metas.report.jasper.class_loader.images.org.OrgImageClassLoaderHook;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Jasper class loader: basically it will resolve {@link #PLACEHOLDER} from resource names and will fetch the resources from remote HTTP servers.
 */
public final class JasperClassLoader extends ClassLoader
{
	// services
	private static final transient Logger logger = LogManager.getLogger(JasperClassLoader.class);

	public static final String PLACEHOLDER = "@PREFIX@";

	private final OrgId adOrgId;
	@Nullable
	private final PrintFormatId printFormatId; 
	private String reportsPathPrefix;
	private boolean alwaysPrependPrefix = false;

	// Hooks
	private final OrgImageClassLoaderHook orgImages;
	private final AttachmentImageFileClassLoaderHook attachmentImages;
	private final AdImageClassLoaderHook adImages;

	@Builder
	private JasperClassLoader(
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull final OrgId adOrgId,
			@NonNull final ClassLoader parent,
			@Nullable final PrintFormatId printFormatId)
	{
		super(parent);

		this.adOrgId = adOrgId;
		this.reportsPathPrefix = retrieveReportPrefix(adOrgId);
		this.orgImages = OrgImageClassLoaderHook.newInstance();
		this.attachmentImages = new AttachmentImageFileClassLoaderHook(attachmentEntryService);
		this.adImages = new AdImageClassLoaderHook();
		this.printFormatId = printFormatId;
	}

	private static String retrieveReportPrefix(@NonNull final OrgId adOrgId)
	{
		final String reportPrefix = Services.get(IOrgDAO.class).getOrgInfoById(adOrgId).getReportsPathPrefix();
		logger.info("Reports Path Prefix: {} (AD_Org_ID={})", reportPrefix, adOrgId);

		return reportPrefix;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("adOrgId", adOrgId)
				.add("reportsPathPrefix", reportsPathPrefix)
				.add("parent", getParent())
				.toString();
	}

	@Override
	protected URL findResource(final String name)
	{
		// guard against null
		if (name == null || Check.isBlank(name))
		{
			return null;
		}

		final String urlStr = convertResourceNameToURLString(name);
		try
		{
			final URL url = new URL(urlStr);
			logger.trace("URL: {} for {}", url, name);

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
		catch (final MalformedURLException e)
		{
			logger.trace("Got invalid URL '{}' for '{}'. Returning null.", urlStr, name, e);
		}
		return null;
	}

	@Override
	public URL getResource(final String name)
	{
		if (name == null)
		{
			return null;
		}

		final URL orgImageURL = orgImages.getResourceURLOrNull(adOrgId, name);
		if (orgImageURL != null)
		{
			return orgImageURL;
		}

		
		final URL attachmentImageURL = attachmentImages.getResourceURLOrNull(printFormatId, name);
		if (attachmentImageURL != null)
		{
			return attachmentImageURL;
		}


		final URL adImageURL = adImages.getResourceURLOrNull(name);
		if(adImageURL != null)
		{
			return adImageURL;
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
		logger.trace("URL: {} for {}", url, name);

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
			FileUtil.copy(is, out);
			is.close();
			jasperFile.close();

			return new ByteArrayInputStream(out.toByteArray());
		}
		catch (final org.apache.commons.vfs2.FileNotFoundException e)
		{
			logger.debug("Resource not found. Skipping.", e);
			return getParent().getResourceAsStream(name);
		}
		catch (final FileSystemException e)
		{
			logger.warn("Error while retrieving bytes for resource " + url + ". Skipping.", e);
			return getParent().getResourceAsStream(name);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("IO error while retrieving bytes for resource " + url, e);
		}
	}

	/**
	 * Returns true, e.g. for <code>file:/opt/metasfresh/metasfresh-server.jar!/lib/spring-beans-4.2.5.RELEASE.jar</code>.<br>
	 * Such URLs can't be handled by our vfs implementation.
	 */
	@VisibleForTesting
	/* package */ static boolean isJarInJarURL(@Nullable final URL url)
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
	 * @return resource's URL string
	 */
	private String convertResourceNameToURLString(final String resourceName)
	{
		if (Check.isEmpty(reportsPathPrefix))
		{
			return resourceName;
		}

		final StringBuilder urlStr = new StringBuilder();

		if (resourceName.startsWith(PLACEHOLDER))
		{

			if (resourceName.startsWith(PLACEHOLDER + "/"))
			{

				urlStr.append(resourceName.replace(PLACEHOLDER, reportsPathPrefix));
			}
			else
			{
				if (reportsPathPrefix.endsWith("/"))
				{
					urlStr.append(resourceName.replace(PLACEHOLDER, reportsPathPrefix));
				}
				else
				{
					urlStr.append(resourceName.replace(PLACEHOLDER, reportsPathPrefix + "/"));
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

				reportsPathPrefix = matcher.group(1);
				urlStr.append(reportsPathPrefix);

				final String report = matcher.group(2);

				if (!reportsPathPrefix.endsWith("/") && !report.startsWith("/"))
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
			urlStr.append(reportsPathPrefix);

			if (!reportsPathPrefix.endsWith("/") && !resourceName.startsWith("/"))
			{
				urlStr.append("/");
			}
		}
		urlStr.append(resourceName);
		return urlStr.toString();
	}
}
