package de.metas.ui.web.mail;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
public class WebuiMailAttachmentsRepository implements InitializingBean
{
	private static final transient Logger logger = LogManager.getLogger(WebuiMailAttachmentsRepository.class);

	private static final String PROPERTY_AttachmentsDir = "metasfresh.webui.email.attachmentsDir";
	@Value("${metasfresh.webui.email.attachmentsDir:}")
	private String attachmentsFilePath;

	private File attachmentsDir; // lazy

	@Override
	public void afterPropertiesSet() throws Exception
	{
		attachmentsDir = checkCreateAttachmentsDir(attachmentsFilePath);
		logger.info("Attachments directory: {}", attachmentsDir);
	}

	private static File checkCreateAttachmentsDir(final String attachmentsFilePath)
	{
		final File attachmentsDir;
		if (Check.isEmpty(attachmentsFilePath, true))
		{
			logger.warn("Using default attachments directory. It's highly recommended to define a proper one. To configure it, please set '{}' property.", PROPERTY_AttachmentsDir);
			String tmpdir = System.getProperty("java.io.tmpdir");
			attachmentsDir = new File(tmpdir, "metasfresh-webui/email_attachments");
		}
		else
		{
			attachmentsDir = new File(attachmentsFilePath);
		}

		if (!attachmentsDir.exists() && !attachmentsDir.mkdirs())
		{
			throw new AdempiereException("Cannot create " + attachmentsDir);
		}

		return attachmentsDir;
	}

	@NonNull
	private File getAttachmentsDir()
	{
		return attachmentsDir;
	}

	private File getAttachmentFile(final String attachmentId)
	{
		if (attachmentId.contains("/") || attachmentId.contains("\\"))
		{
			throw new IllegalArgumentException("Invalid attachmentId: " + attachmentId);
		}
		return new File(getAttachmentsDir(), attachmentId);
	}

	public LookupValue createAttachment(final String emailId, final MultipartFile file)
	{
		final String attachmentId = UUID.randomUUID().toString();

		//
		// Extract the original filename
		String originalFilename = file.getOriginalFilename();
		if (Check.isEmpty(originalFilename, true))
		{
			originalFilename = file.getName();
		}
		if (Check.isEmpty(originalFilename, true))
		{
			throw new AdempiereException("Filename not provided");
		}

		//
		// Store it to internal attachments storage
		final File attachmentFile = getAttachmentFile(attachmentId);
		try
		{
			FileCopyUtils.copy(file.getBytes(), attachmentFile);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed storing " + originalFilename)
					.setParameter("originalFilename", originalFilename)
					.setParameter("attachmentFile", attachmentFile);
		}

		//
		return StringLookupValue.of(attachmentId, originalFilename);
	}

	public byte[] getAttachmentAsByteArray(@NonNull final LookupValue attachment)
	{
		final File attachmentFile = getAttachmentFile(attachment.getIdAsString());
		return Util.readBytes(attachmentFile);
	}

	public void deleteAttachments(@NonNull final LookupValuesList attachmentsList)
	{
		attachmentsList.stream().forEach(this::deleteAttachment);
	}

	public void deleteAttachment(@NonNull final LookupValue attachment)
	{
		final String attachmentId = attachment.getIdAsString();

		final File attachmentFile = getAttachmentFile(attachmentId);
		if (!attachmentFile.exists())
		{
			logger.debug("Attachment file {} is missing. Nothing to delete", attachmentFile);
			return;
		}

		if (!attachmentFile.delete())
		{
			attachmentFile.deleteOnExit();
			logger.warn("Cannot delete attachment file {}. Scheduled to be deleted on exit", attachmentFile);
		}
		else
		{
			logger.debug("Deleted attachment file {}", attachmentFile);
		}
	}

}
