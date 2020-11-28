package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.store;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.attachments.storeattachment.StoreAttachmentServiceImpl;
import de.metas.cache.CCache;
import de.metas.dunning_gateway.spi.DunningExportClientFactory;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.invoice_gateway.spi.model.BPartnerId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.xml.XmlIntrospectionUtil;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model.I_HC_Forum_Datenaustausch_Config;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.CrossVersionServiceRegistry;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ConfigRepositoryUtil.ConfigQuery;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.StoreConfig;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.StoreConfigRepository;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlPayload.PayloadMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest.RequestMod;
import lombok.NonNull;

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
	private final CrossVersionServiceRegistry crossVersionServiceRegistry;

	private final CCache<AttachmentEntry, Optional<StoreConfig>> cache = CCache.newCache(
			I_HC_Forum_Datenaustausch_Config.Table_Name + "#by#AttachmentEntry",
			10,
			5 /* expireAfterMinutes */);

	public StoreForumDatenaustauschAttachmentService(
			@NonNull final StoreConfigRepository configRepository,
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull CrossVersionServiceRegistry crossVersionServiceRegistry)
	{
		this.configRepository = configRepository;
		this.attachmentEntryService = attachmentEntryService;
		this.crossVersionServiceRegistry = crossVersionServiceRegistry;
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
		final AttachmentTags tags = attachmentEntry.getTags();
		final boolean isDocument = tags.hasTagSetToTrue(
				AttachmentTags.TAGNAME_IS_DOCUMENT);
		if (!isDocument)
		{
			return Optional.empty();
		}

		final boolean isForumDatenaustauschInvoice = tags.hasTagSetToString(
				InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXPORT_PROVIDER,
				ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID);
		final boolean isForumDatenaustauschDunning = tags.hasTagSetToString(
				DunningExportClientFactory.ATTATCHMENT_TAGNAME_EXPORT_PROVIDER,
				ForumDatenaustauschChConstants.DUNNING_EXPORT_PROVIDER_ID);
		if (!isForumDatenaustauschInvoice && !isForumDatenaustauschDunning)
		{
			return Optional.empty();
		}

		final String bPartnerIdStr = tags.getTagValueOrNull(AttachmentTags.TAGNAME_BPARTNER_RECIPIENT_ID);
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
		final boolean attachmentIsStoredForThefirstTime = !attachmentEntry
				.getTags()
				.toMap()
				.keySet()
				.stream()
				.anyMatch(key -> key.startsWith(AttachmentTags.TAGNAME_STORED_PREFIX));

		byte[] attachmentDataToStore;
		if (attachmentIsStoredForThefirstTime)
		{
			attachmentDataToStore = attachmentEntryService.retrieveData(attachmentEntry.getId());
		}
		else
		{
			attachmentDataToStore = computeDataWithCopyFlagSetToTrue(attachmentEntry);
		}

		final String directory = retrieveDirectoryOrNull(attachmentEntry);
		final File file = new File(directory, attachmentEntry.getFilename());

		try (final FileOutputStream fileOutputStream = new FileOutputStream(file))
		{
			fileOutputStream.write(attachmentDataToStore);
			return file.toURI();
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Unable to write attachment data to file", e).appendParametersToMessage()
					.setParameter("file", file)
					.setParameter("attachmentEntry", attachmentEntry);
		}
	}

	private byte[] computeDataWithCopyFlagSetToTrue(@NonNull final AttachmentEntry attachmentEntry)
	{
		byte[] attachmentData = attachmentEntryService.retrieveData(attachmentEntry.getId());

		// get the converter to use
		final String xsdName = XmlIntrospectionUtil.extractXsdValueOrNull(new ByteArrayInputStream(attachmentData));
		final CrossVersionRequestConverter converter = crossVersionServiceRegistry.getRequestConverterForXsdName(xsdName);
		Check.assumeNotNull(converter, "Missing CrossVersionRequestConverter for XSD={}; attachmentEntry={}", xsdName, attachmentEntry);

		// convert to crossVersion data
		final XmlRequest crossVersionRequest = converter.toCrossVersionRequest(new ByteArrayInputStream(attachmentData));

		// patch
		final XmlRequest updatedCrossVersionRequest = updateCrossVersionRequest(crossVersionRequest);

		// convert back to byte[]
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		converter.fromCrossVersionRequest(updatedCrossVersionRequest, outputStream);

		return outputStream.toByteArray();
	}

	private XmlRequest updateCrossVersionRequest(@NonNull final XmlRequest crossVersionRequest)
	{
		final RequestMod mod = RequestMod
				.builder()
				.payloadMod(PayloadMod
						.builder()
						.copy(true)
						.build())
				.build();
		return crossVersionRequest.withMod(mod);
	}
}
