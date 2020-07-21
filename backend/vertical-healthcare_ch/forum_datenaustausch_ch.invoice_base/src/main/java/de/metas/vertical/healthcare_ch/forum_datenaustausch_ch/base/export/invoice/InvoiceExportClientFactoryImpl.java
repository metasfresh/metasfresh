package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.export.invoice;

import java.util.Optional;

import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Level;
import de.metas.invoice_gateway.spi.InvoiceExportClient;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.invoice_gateway.spi.model.BPartnerId;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.ILoggable;
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

	private static final Logger logger = LogManager.getLogger(InvoiceExportClientFactoryImpl.class);

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
		try (final MDCCloseable invoiceMDC = TableRecordMDC.putTableRecordReference(I_C_Invoice.Table_Name, invoice.getId()))
		{
			final String requiredAttachmentTag = ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID;

			final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

			final boolean supported = invoice.getInvoiceAttachments()
					.stream()
					.map(attachment -> attachment.getTags().get(InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXPORT_PROVIDER)) // might be null
					.anyMatch(providerId -> requiredAttachmentTag.equals(providerId));
			if (!supported)
			{
				loggable.addLog("forum_datenaustausch_ch - The invoice with id={} has no attachment with an {}-tag", invoice.getId(), requiredAttachmentTag);
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
				loggable.addLog("forum_datenaustausch_ch - There is no export config for the recipiend-id={} of the invoice with id={}", recipientId, invoice.getId());
				return Optional.empty();
			}
			final InvoiceExportClientImpl client = new InvoiceExportClientImpl(crossVersionServiceRegistry, config);
			if (!client.applies(invoice))
			{
				loggable.addLog("forum_datenaustausch_ch - the export-client {} said that it won't export the invoice with id={}", client.getClass().getSimpleName(), invoice.getId());
				return Optional.empty();
			}

			return Optional.of(client);
		}
	}
}
