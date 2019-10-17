package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.export.invoice;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.metas.invoice_gateway.spi.InvoiceExportClient;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.invoice_gateway.spi.model.BPartnerId;
import de.metas.invoice_gateway.spi.model.InvoiceAttachment;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
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
public class InvoiceExportClientFactoryImpl implements InvoiceExportClientFactory
{
	private final ExportConfigRepository configRepository;
	private final CrossVersionServiceRegistry crossVersionServiceRegistry;

	public InvoiceExportClientFactoryImpl(
			@NonNull final ExportConfigRepository configRepository,
			@NonNull final CrossVersionServiceRegistry crossVersionServiceRegistry)
	{
		this.configRepository = configRepository;
		this.crossVersionServiceRegistry = crossVersionServiceRegistry;
	}

	@Override
	public String getInvoiceExportProviderId()
	{
		return ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID;
	}

	@Override
	public Optional<InvoiceExportClient> newClientForInvoice(@NonNull final InvoiceToExport invoice)
	{
		final String requiredAttachmentTag = ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID;

		final boolean supported = invoice.getInvoiceAttachments()
				.stream()
				.map(InvoiceAttachment::getInvoiceExportProviderId)
				.anyMatch(id -> requiredAttachmentTag.equals(id));
		if (!supported)
		{
			Loggables.addLog("forum_datenaustausch_ch - The invoice with id={} has no attachment with an {}-tag", invoice.getId(), requiredAttachmentTag);
			return Optional.empty();
		}

		final BPartnerId recipientId = invoice.getRecipient().getId();
		final ConfigQuery query = ConfigQuery
				.builder()
				.bpartnerId(recipientId)
				.build();
		final ExportConfig config = configRepository.getForQueryOrNull(query);
		if (config == null)
		{
			Loggables.addLog("forum_datenaustausch_ch - There is no export config for the recipiend-id={} of the invoice with id={}", recipientId, invoice.getId());
			return Optional.empty();
		}
		final InvoiceExportClientImpl client = new InvoiceExportClientImpl(crossVersionServiceRegistry, config);
		if (!client.canExport(invoice))
		{
			Loggables.addLog("forum_datenaustausch_ch - the export-client {} claims that it can't export the invoice with id={}", client.getClass().getSimpleName(), invoice.getId());
			return Optional.empty();
		}

		return Optional.of(client);
	}
}
