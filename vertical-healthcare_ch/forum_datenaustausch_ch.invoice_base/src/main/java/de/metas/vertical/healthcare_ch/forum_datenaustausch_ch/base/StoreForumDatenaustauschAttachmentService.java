package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base;

import lombok.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CCache;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.metas.attachments.AttachmentConstants;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.storeattachment.StoreAttachmentServiceImpl;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.invoice_gateway.spi.model.BPartnerId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model.I_HC_Forum_Datenaustausch_Config;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ConfigRepositoryUtil.ConfigQuery;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.StoreConfig;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.StoreConfigRepository;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
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

@Component
@Profile(ForumDatenaustauschChConstants.PROFILE)
public class StoreForumDatenaustauschAttachmentService implements StoreAttachmentServiceImpl
{
	private final StoreConfigRepository configRepository;
	private final AttachmentEntryService attachmentEntryService;

	private final CCache<AttachmentEntry, Optional<StoreConfig>> cache = CCache.newCache(
			I_HC_Forum_Datenaustausch_Config.Table_Name + "#by#AttachmentEntry",
			10,
			5 /* expireAfterMinutes */);

	public StoreForumDatenaustauschAttachmentService(
			@NonNull final StoreConfigRepository configRepository,
			@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.configRepository = configRepository;
		this.attachmentEntryService = attachmentEntryService;
	}

	@Override
	public boolean appliesTo(@NonNull final AttachmentEntry attachmentEntry)
	{
		final boolean hasConfigWithDirectory = !Check.isEmpty(retrieveDirectoryOrNull(attachmentEntry));
		return hasConfigWithDirectory;
	}

	private String retrieveDirectoryOrNull(@NonNull final AttachmentEntry attachmentEntry)
	{
		final StoreConfig config = retrieveForAttachmentEntryOrNull(attachmentEntry);
		if (config == null)
		{
			return null;
		}
		return config.getDirectory();
	}

	private StoreConfig retrieveForAttachmentEntryOrNull(@NonNull final AttachmentEntry attachmentEntry)
	{
		return cache
				.getOrLoad(attachmentEntry, this::retrieveForAttachmentEntry0)
				.orElse(null);
	}

	private Optional<StoreConfig> retrieveForAttachmentEntry0(@NonNull final AttachmentEntry attachmentEntry)
	{
		final boolean isDocument = attachmentEntry.hasTagSetToTrue(
				AttachmentConstants.TAGNAME_IS_DOCUMENT);
		if (!isDocument)
		{
			return Optional.empty();
		}

		final boolean isForumDatenaustausch = attachmentEntry.hasTagSetToString(
				InvoiceExportClientFactory.ATTATCHMENT_TAGNAME_EXPORT_PROVIDER,
				InvoiceExportClientFactoryImpl.INVOICE_EXPORT_PROVIDER_ID);
		if (!isForumDatenaustausch)
		{
			return Optional.empty();
		}

		final String bPartnerIdStr = attachmentEntry.getTagValueOrNull(AttachmentConstants.TAGNAME_BPARTNER_RECIPIENT_ID);
		if (Check.isEmpty(bPartnerIdStr))
		{
			return Optional.empty();
		}

		final int bPartnerRepoId = StringUtils.toIntegerOrZero(bPartnerIdStr);
		if (bPartnerRepoId <= 0)
		{
			return Optional.empty();
		}

		final ConfigQuery configQuery = ConfigQuery
				.builder()
				.bpartnerId(BPartnerId.ofRepoId(bPartnerRepoId))
				.build();
		final StoreConfig config = configRepository.getForQueryOrNull(configQuery);
		return Optional.ofNullable(config);
	}

	@Override
	public boolean isAttachmentStorable(@NonNull final AttachmentEntry attachmentEntry)
	{
		return appliesTo(attachmentEntry);
	}

	@Override
	public URI storeAttachment(@NonNull final AttachmentEntry attachmentEntry)
	{
		final String directory = retrieveDirectoryOrNull(attachmentEntry);
		final File file = new File(directory, attachmentEntry.getFilename());

		try (final FileOutputStream fileOutputStream = new FileOutputStream(file))
		{
			fileOutputStream.write(attachmentEntryService.retrieveData(attachmentEntry.getId()));
			return file.toURI();
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Unable to write attachment data to file", e).appendParametersToMessage()
					.setParameter("file", file)
					.setParameter("attachmentEntry", attachmentEntry);
		}
	}
}
