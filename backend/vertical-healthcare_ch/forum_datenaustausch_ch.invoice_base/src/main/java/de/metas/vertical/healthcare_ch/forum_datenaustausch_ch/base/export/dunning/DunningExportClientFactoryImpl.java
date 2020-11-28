package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.export.dunning;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.metas.dunning_gateway.spi.DunningExportClient;
import de.metas.dunning_gateway.spi.DunningExportClientFactory;
import de.metas.dunning_gateway.spi.model.BPartnerId;
import de.metas.dunning_gateway.spi.model.DunningAttachment;
import de.metas.dunning_gateway.spi.model.DunningToExport;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.util.Loggables;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.CrossVersionServiceRegistry;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ConfigRepositoryUtil.ConfigQuery;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ExportConfig;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ExportConfigRepository;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import lombok.NonNull;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_commons
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

@Service
@Profile(ForumDatenaustauschChConstants.PROFILE)
public class DunningExportClientFactoryImpl implements DunningExportClientFactory
{
	private final ExportConfigRepository configRepository;
	private final CrossVersionServiceRegistry crossVersionServiceRegistry;

	public DunningExportClientFactoryImpl(
			@NonNull final ExportConfigRepository configRepository,
			@NonNull final CrossVersionServiceRegistry crossVersionServiceRegistry)
	{
		this.configRepository = configRepository;
		this.crossVersionServiceRegistry = crossVersionServiceRegistry;
	}

	@Override
	public String getDunningExportProviderId()
	{
		return ForumDatenaustauschChConstants.DUNNING_EXPORT_PROVIDER_ID;
	}

	@Override
	public Optional<DunningExportClient> newClientForDunning(@NonNull final DunningToExport dunning)
	{
		final String requiredAttachmentTagName = InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXPORT_PROVIDER;
		final String requiredAttachmentTagValue = ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID;

		boolean atLeastOneattachmentWasCreatedByForumDatenaustausch = false;
		for (final DunningAttachment dunningAttachment : dunning.getDunningAttachments())
		{
			final String invoiceExportProvider = dunningAttachment.getTags().get(requiredAttachmentTagName);
			if (requiredAttachmentTagValue.equals(invoiceExportProvider))
			{
				atLeastOneattachmentWasCreatedByForumDatenaustausch = true;
				break;
			}
		}

		if (!atLeastOneattachmentWasCreatedByForumDatenaustausch)
		{
			Loggables.addLog("forum_datenaustausch_ch - The dunning with id={} has no attachment with an {}={}-tag",
					dunning.getId(), requiredAttachmentTagName, requiredAttachmentTagValue);
			return Optional.empty();
		}

		final BPartnerId recipientId = dunning.getRecipientId();
		final ConfigQuery query = ConfigQuery
				.builder()
				.bpartnerId(recipientId)
				.build();
		final ExportConfig config = configRepository.getForQueryOrNull(query);
		if (config == null)
		{
			Loggables.addLog("forum_datenaustausch_ch - There is no export config for the recipiend-id={} of the invoice with id={}", recipientId, dunning.getId());
			return Optional.empty();
		}
		final DunningExportClientImpl client = new DunningExportClientImpl(crossVersionServiceRegistry, config);
		if (!client.canExport(dunning))
		{
			Loggables.addLog("forum_datenaustausch_ch - the export-client {} claims that it can't export the dunning with id={}", client.getClass().getSimpleName(), dunning.getId());
			return Optional.empty();
		}

		return Optional.of(client);
	}
}
