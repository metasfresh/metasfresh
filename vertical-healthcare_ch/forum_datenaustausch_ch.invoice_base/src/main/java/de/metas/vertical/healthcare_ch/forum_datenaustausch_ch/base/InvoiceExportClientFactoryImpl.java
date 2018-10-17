package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base;

import lombok.NonNull;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.metas.invoice_gateway.spi.InvoiceExportClient;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.invoice_gateway.spi.model.InvoiceAttachment;
import de.metas.invoice_gateway.spi.model.InvoiceToExport;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ConfigRepositoryUtil.ConfigQuery;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ExportConfig;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ExportConfigRepository;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;

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
	public static final String INVOICE_EXPORT_PROVIDER_ID = "forum-datenaustausch.ch";

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
		return INVOICE_EXPORT_PROVIDER_ID;
	}

	@Override
	public Optional<InvoiceExportClient> newClientForInvoice(@NonNull final InvoiceToExport invoice)
	{
		final boolean supported = invoice.getInvoiceAttachments()
				.stream()
				.map(InvoiceAttachment::getInvoiceExportProviderId)
				.anyMatch(id -> INVOICE_EXPORT_PROVIDER_ID.equals(id));
		if (!supported)
		{
			return Optional.empty();
		}

		final ConfigQuery query = ConfigQuery
				.builder()
				.bpartnerId(invoice.getRecipient().getId())
				.build();
		final ExportConfig config = configRepository.getForQueryOrNull(query);

		final InvoiceExportClientImpl client = new InvoiceExportClientImpl(crossVersionServiceRegistry, config.getExportXmlVersion());
		if(!client.canExport(invoice))
		{
			return Optional.empty();
		}

		return Optional.of(client);
	}
}
