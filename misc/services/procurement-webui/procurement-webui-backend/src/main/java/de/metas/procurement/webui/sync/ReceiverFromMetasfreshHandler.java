package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
import de.metas.common.procurement.sync.protocol.dto.SyncConfirmation;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQCloseEvent;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutConfirmationToProcurementWebRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQCloseEventsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQsRequest;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Handles various {@link de.metas.common.procurement.sync.protocol.RequestToProcurementWeb}.
 */
@Component
public class ReceiverFromMetasfreshHandler
{
	private static final Logger logger = LoggerFactory.getLogger(ReceiverFromMetasfreshHandler.class);
	private final SyncBPartnerImportService bpartnersImportService;
	private final SyncProductImportService productsImportService;
	private final SyncSettingsImportService settingsImportService;
	private final SyncConfirmationsImportService confirmationsImportService;
	private final SyncRfqImportService rfqImportService;

	public ReceiverFromMetasfreshHandler(
			@NonNull final SyncBPartnerImportService bpartnersImportService,
			@NonNull final SyncProductImportService productsImportService,
			@NonNull final SyncSettingsImportService settingsImportService,
			@NonNull final SyncConfirmationsImportService confirmationsImportService,
			@NonNull final SyncRfqImportService rfqImportService)
	{
		this.bpartnersImportService = bpartnersImportService;
		this.productsImportService = productsImportService;
		this.settingsImportService = settingsImportService;
		this.confirmationsImportService = confirmationsImportService;
		this.rfqImportService = rfqImportService;
	}

	public void handlePutBPartnersRequest(final PutBPartnersRequest request)
	{
		logger.debug("Importing bpartners: {}", request);
		int countImported = 0;
		int countError = 0;
		for (final SyncBPartner syncBpartner : request.getBpartners())
		{
			try
			{
				bpartnersImportService.importBPartner(syncBpartner);
				countImported++;
			}
			catch (final Exception e)
			{
				countError++;
				logger.error("Failed importing {}. Skipped.", syncBpartner, e);
			}
		}
		logger.info("{} bpartners imported, got {} errors", countImported, countError);
	}

	public void handlePutProductsRequest(final PutProductsRequest request)
	{
		logger.debug("Importing: {}", request);
		int countImported = 0;
		int countError = 0;
		for (final SyncProduct syncProduct : request.getProducts())
		{
			try
			{
				productsImportService.importProduct(syncProduct);
				countImported++;
			}
			catch (final Exception e)
			{
				countError++;
				logger.error("Failed importing {}. Skipped.", syncProduct, e);
			}
		}
		logger.info("{} products imported, got {} errors", countImported, countError);
	}

	public void handlePutInfoMessageRequest(final PutInfoMessageRequest request)
	{
		logger.debug("Importing: {}", request);
		try
		{
			settingsImportService.importSyncInfoMessage(request);
		}
		catch (final Exception e)
		{
			logger.error("Failed importing {}. Skipped.", request, e);
		}
	}

	public void handlePutConfirmationToProcurementWebRequest(final PutConfirmationToProcurementWebRequest request)
	{
		logger.debug("Importing: {}", request);

		for (final SyncConfirmation syncConfirmation : request.getSyncConfirmations())
		{
			try
			{
				confirmationsImportService.importConfirmation(syncConfirmation);
			}
			catch (final Exception e)
			{
				logger.error("Failed importing confirmation: {}", syncConfirmation, e);
			}
		}
	}

	public void handlePutRfQsRequest(@NonNull final PutRfQsRequest request)
	{
		logger.debug("Importing: {}", request);
		if (request.isEmpty())
		{
			return;
		}

		for (final SyncRfQ syncRfq : request.getSyncRfqs())
		{
			try
			{
				rfqImportService.importRfQ(syncRfq);
			}
			catch (final Exception e)
			{
				logger.error("Failed importing RfQ: {}", syncRfq, e);
			}
		}
	}

	public void handlePutRfQCloseEventsRequest(final PutRfQCloseEventsRequest request)
	{
		logger.debug("Importing: {}", request);
		if (request.isEmpty())
		{
			return;
		}

		for (final SyncRfQCloseEvent syncRfQCloseEvent : request.getSyncRfQCloseEvents())
		{
			try
			{
				rfqImportService.importRfQCloseEvent(syncRfQCloseEvent);
			}
			catch (final Exception e)
			{
				logger.error("Failed importing: {}", syncRfQCloseEvent, e);
			}
		}
	}
}
