package de.metas.report.jasper.class_loader.images.attachment;

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

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.logging.LogManager;
import de.metas.report.PrintFormatId;
import de.metas.report.jasper.class_loader.JasperClassLoader;
import de.metas.report.jasper.class_loader.images.ImageUtils;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PrintFormat;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Logo hook: called by {@link JasperClassLoader} in order to intercept logo picture resources and provide the actual logo for current organization.
 * 
 * @author cg
 *
 */
public final class AttachmentImageFileClassLoaderHook
{
	// services
	private static final transient Logger logger = LogManager.getLogger(AttachmentImageFileClassLoaderHook.class);
	private final AttachmentEntryService attachmentEntryService;

	//
	// Attachment image resource matchers
	private static final String SYSCONFIG_ResourceNameEndsWith = "de.metas.adempiere.report.jasperAttachmentImageFileClassLoaderHook.ResourceNameEndsWith";
	private static final String DEFAULT_ResourceNameEndsWith = "de/metas/generics/watermark.png";
	private final ImmutableSet<String> resourceNameEndsWithMatchers;

	public AttachmentImageFileClassLoaderHook(@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;

		this.resourceNameEndsWithMatchers = buildResourceNameEndsWithMatchers();
	}

	private static ImmutableSet<String> buildResourceNameEndsWithMatchers()
	{
		final String resourceNameEndsWithStr = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_ResourceNameEndsWith, DEFAULT_ResourceNameEndsWith);
		if (resourceNameEndsWithStr == null || Check.isBlank(resourceNameEndsWithStr))
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
		final AttachmentEntryId attachmentEntryId = getFirstAttachmentEntryIdByPrintFormatId(printFormatId);
		if(attachmentEntryId == null)
		{
			logger.warn("Cannot find image for {}, please add a file to the Print format. Returning empty PNG file", this);
			return ImageUtils.getEmptyPNGFile();
		}
		else
		{
			final byte[] data = attachmentEntryService.retrieveData(attachmentEntryId);
			return ImageUtils.createTempPNGFile("attachmentEntry", data);
		}
	}

	/**
	 * get one attachment entry; does not matter if are several
	 */
	@Nullable
	private AttachmentEntryId getFirstAttachmentEntryIdByPrintFormatId(@NonNull final PrintFormatId printFormatId)
	{
		final List<AttachmentEntry> entries = attachmentEntryService.getByReferencedRecord(TableRecordReference.of(I_AD_PrintFormat.Table_Name, printFormatId));

		if (!entries.isEmpty())
		{
			final AttachmentEntry entry = entries.get(0);
			return entry.getId();
		}
		else
		{
			return null;
		}
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
