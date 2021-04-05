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
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Nullable;

import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.report.PrintFormatId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Logo hook: called by {@link JasperClassLoader} in order to intercept logo picture resources and provide the actual logo for current organization.
 * 
 * @author cg
 *
 */
final class AttachmentImageFileClassLoaderHook
{
	public static AttachmentImageFileClassLoaderHook newInstance()
	{
		return new AttachmentImageFileClassLoaderHook();
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(AttachmentImageFileClassLoaderHook.class);

	//
	// Attachment image resource matchers
	private static final String SYSCONFIG_ResourceNameEndsWith = "de.metas.adempiere.report.jasperAttachmentImageFileClassLoaderHook.ResourceNameEndsWith";
	private static final String DEFAULT_ResourceNameEndsWith = "de/metas/generics/watermark.png";
	private final ImmutableSet<String> resourceNameEndsWithMatchers;

	private final AttachmentImageFileLoader attachmentImageFileLoader;

	private AttachmentImageFileClassLoaderHook()
	{
		this.resourceNameEndsWithMatchers = buildResourceNameEndsWithMatchers();
		this.attachmentImageFileLoader = AttachmentImageFileLoader.newInstance();
	}

	private static ImmutableSet<String> buildResourceNameEndsWithMatchers()
	{
		final String resourceNameEndsWithStr = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_ResourceNameEndsWith, DEFAULT_ResourceNameEndsWith);
		if (Check.isEmpty(resourceNameEndsWithStr, true))
		{
			return ImmutableSet.of();
		}

		return ImmutableSet.copyOf(Splitter.on(';')
				.trimResults()
				.omitEmptyStrings()
				.splitToList(resourceNameEndsWithStr));
	}

	public URL getResourceURLOrNull(
			@Nullable final PrintFormatId printFormatId,
			@Nullable final String resourceName)
	{
		// Skip if it's not about our hooked image attachment resources
		if (!isAttachmentImageResourceName(resourceName))
		{
			return null;
		}

		if (printFormatId == null)
		{
			return null;
		}
		
		//
		// Get the local image file
		final File imageFile = geImageFile(printFormatId);
		if (imageFile == null)
		{
			return null;
		}

		//
		// Convert the attachment file to URL
		try
		{
			return imageFile.toURI().toURL();
		}
		catch (MalformedURLException e)
		{
			logger.warn("Failed converting the image file to URL: {}", imageFile, e);
		}
		return null;
	}

	private File geImageFile(@NonNull final PrintFormatId printFormatId)
	{
		return attachmentImageFileLoader
				.loadImageForPrintFormat(printFormatId)
				.orElse(null);
	}

	/**
	 * @return true if given resourceName is an attachment image
	 */
	private boolean isAttachmentImageResourceName(final String resourceName)
	{
		// Skip if no resourceName
		if (resourceName == null || resourceName.isEmpty())
		{
			return false;
		}

		// Check if our resource name ends with one of our predefined matchers
		for (final String resourceNameEndsWithMatcher : resourceNameEndsWithMatchers)
		{
			if (resourceName.endsWith(resourceNameEndsWithMatcher))
			{
				return true;
			}
		}

		// Fallback: not a attachment resource
		return false;
	}
}
