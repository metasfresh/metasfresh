package de.metas.procurement.webui.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import de.metas.procurement.sync.IAgentSync;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncBPartnersRequest;
import de.metas.procurement.sync.protocol.SyncInfoMessageRequest;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductsRequest;
import de.metas.procurement.webui.Application;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Component
public class AgentSync implements IAgentSync
{
	private static final transient Logger logger = LoggerFactory.getLogger(AgentSync.class);

	@Autowired
	@Lazy
	private SyncBPartnerImportService bpartnersImportService;

	@Autowired
	@Lazy
	private SyncProductImportService productsImportService;

	@Autowired
	@Lazy
	private SyncSettingsImportService settingsImportService;

	public AgentSync()
	{
		Application.autowire(this);
	}

	@Override
	public void syncBPartners(final SyncBPartnersRequest request)
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
			catch (Exception e)
			{
				countError++;
				logger.error("Failed importing {}. Skipped.", syncBpartner, e);
			}
		}
		logger.info("{} bpartners imported, got {} errors", countImported, countError);
	}

	@Override
	public void syncProducts(final SyncProductsRequest request)
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
			catch (Exception e)
			{
				countError++;
				logger.error("Failed importing {}. Skipped.", syncProduct, e);
			}
		}
		logger.info("{} products imported, got {} errors", countImported, countError);
	}

	@Override
	public void syncInfoMessage(final SyncInfoMessageRequest request)
	{
		logger.debug("Importing: {}", request);
		try
		{
			settingsImportService.importSyncInfoMessage(request);
		}
		catch (Exception e)
		{
			logger.error("Failed importing {}. Skipped.", request, e);
		}
	}
}
