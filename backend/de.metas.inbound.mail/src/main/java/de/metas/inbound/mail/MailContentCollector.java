package de.metas.inbound.mail;

import de.metas.logging.LogManager;
import de.metas.util.FileUtil;
import groovy.transform.ToString;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/*
 * #%L
 * de.metas.inbound.mail
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ToString
final class MailContentCollector
{
	public static MailContentCollector newInstance()
	{
		return new MailContentCollector();
	}

	public static MailContent toMailContent(final Object contentObj)
	{
		if (contentObj instanceof MailContent)
		{
			return (MailContent)contentObj;
		}
		else
		{
			return newInstance()
					.collectObject(contentObj)
					.toMailContent();
		}
	}

	private static final Logger logger = LogManager.getLogger(MailContentCollector.class);

	private final StringBuilder text = new StringBuilder();
	private final StringBuilder html = new StringBuilder();
	private final ArrayList<InboundEMailAttachment> attachments = new ArrayList<>();

	private MailContentCollector()
	{
	}

	public MailContent toMailContent()
	{
		return MailContent.builder()
				.text(text.toString())
				.html(html.toString())
				.attachments(attachments)
				.build();
	}

	public MailContentCollector collectObject(final Object contentObj)
	{
		final ContentType contentType = null;
		return collectObject(contentObj, contentType);
	}

	private MailContentCollector collectObject(final Object contentObj, final ContentType contentType)
	{
		if (contentObj == null)
		{
			// nothing
		}
		else if (contentObj instanceof String)
		{
			final String content = (String)contentObj;
			collectText(content, contentType);
		}
		else if (contentObj instanceof Multipart)
		{
			final Multipart content = (Multipart)contentObj;
			collectMultipart(content);
		}
		else if (contentObj instanceof Part)
		{
			final Part content = (Part)contentObj;
			collectPart(content);
		}
		else if (contentObj instanceof MailContent)
		{
			final MailContent content = (MailContent)contentObj;
			collectMailContent(content);
		}
		else
		{
			collectUnknown(contentObj, contentType);
		}

		return this;
	}

	private void collectMultipart(final Multipart content)
	{
		try
		{
			for (int partIdx = 0, partsCount = content.getCount(); partIdx < partsCount; partIdx++)
			{
				final BodyPart part = content.getBodyPart(partIdx);
				collectPart(part);
			}
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private void collectPart(final Part part)
	{
		try
		{
			final ContentType contentType = new ContentType(part.getContentType());
			final String disposition = part.getDisposition();

			if (Part.ATTACHMENT.equalsIgnoreCase(disposition))
			{
				collectAttachment(part.getFileName(), contentType, part.getInputStream());
			}
			else if (Part.INLINE.equalsIgnoreCase(disposition))
			{
				final Object contentObj = part.getContent();
				if (contentObj instanceof InputStream)
				{
					collectAttachment(part.getFileName(), contentType, (InputStream)contentObj);
				}
				else
				{
					collectObject(contentObj, contentType);
				}
			}
			else
			{
				final Object contentObj = part.getContent();
				collectObject(contentObj, contentType);
			}
		}
		catch (final javax.mail.FolderClosedException ex)
		{
			throw new AdempiereException(ex.getLocalizedMessage(), ex)
					.appendParametersToMessage()
					.setParameter("part", part);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private void collectAttachment(final String fileName, final ContentType contentType, final InputStream inputStream)
	{
		final String fileExt = FileUtil.getFileExtension(fileName);
		final String fileBaseName = FileUtil.getFileBaseName(fileName);

		try
		{
			final Path tempFile = Files.createTempFile(fileBaseName, "." + fileExt);
			Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

			attachments.add(InboundEMailAttachment.builder()
					.filename(fileName)
					.contentType(contentType.getBaseType())
					.tempFile(tempFile)
					.build());
		}
		catch (final IOException ex)
		{
			logger.warn("Failed collecting attachment for fileName={}, contentType={}. Ignore.", fileName, contentType, ex);
		}
	}

	private void collectText(final String text, final ContentType contentType)
	{
		if (text == null || text.isEmpty())
		{
			return;
		}

		if (contentType == null || contentType.match("text/plain"))
		{
			logger.debug("Collecting text: {}", text);
			this.text.append(text);
		}
		else if (contentType == null || contentType.match("text/html"))
		{
			logger.debug("Collecting html: {}", text);
			this.html.append(text);
		}
		else
		{
			collectUnknown(text, contentType);
		}
	}

	private void collectMailContent(final MailContent mailContent)
	{
		text.append(mailContent.getText());
		html.append(mailContent.getHtml());
		attachments.addAll(mailContent.getAttachments());
	}

	private void collectUnknown(@NonNull final Object contentObj, final ContentType contentType)
	{
		logger.debug("Ignore unknown content: contentType={}, contentObj={}, contentClass={}", contentType, contentObj, contentObj.getClass());
	}
}
