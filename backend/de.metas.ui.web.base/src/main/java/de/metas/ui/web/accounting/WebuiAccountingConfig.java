package de.metas.ui.web.accounting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import de.metas.acct.doc.AcctDocRegistry;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.accounting.process.WEBUI_Fact_Acct_Repost_SingleDocument;
import de.metas.ui.web.accounting.process.WEBUI_Fact_Acct_Repost_ViewRows;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-webui-api
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
public class WebuiAccountingConfig
{
	private static final Logger logger = LogManager.getLogger(WebuiAccountingConfig.class);
	private final AcctDocRegistry acctDocRegistry;

	public WebuiAccountingConfig(final AcctDocRegistry acctDocRegistry)
	{
		this.acctDocRegistry = acctDocRegistry;
	}

	@PostConstruct
	public void registerRepostProcess()
	{
		registerRepostProcessForViewRows();
		registerRepostProcessForSingleDocuments();
	}

	private void registerRepostProcessForViewRows()
	{
		final IADProcessDAO adProcessesRepo = Services.get(IADProcessDAO.class);
		final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);

		final AdProcessId repostProcessId = adProcessesRepo.retrieveProcessIdByClassIfUnique(WEBUI_Fact_Acct_Repost_ViewRows.class);
		if (repostProcessId == null)
		{
			logger.warn("No AD_Process_ID found for {}", WEBUI_Fact_Acct_Repost_ViewRows.class);
			return;
		}

		//
		final List<String> linkToTableNames = new ArrayList<>();
		linkToTableNames.addAll(acctDocRegistry.getDocTableNames());
		linkToTableNames.add(WEBUI_Fact_Acct_Repost_ViewRows.TABLENAME_RV_UnPosted);

		//
		// Link Repost process to all accountable documents
		linkToTableNames
				.stream()
				.map(adTablesRepo::retrieveTableId)
				.map(AdTableId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.forEach(adTableId -> adProcessesRepo.registerTableProcess(RelatedProcessDescriptor.builder()
						.processId(repostProcessId)
						.tableId(adTableId)
						.anyWindow()
						.displayPlace(DisplayPlace.ViewQuickActions)
						.displayPlace(DisplayPlace.ViewActionsMenu)
						.build()));
		logger.info("Registered {} ({}) to {} tables", WEBUI_Fact_Acct_Repost_ViewRows.class.getName(), repostProcessId, linkToTableNames.size());
	}

	private void registerRepostProcessForSingleDocuments()
	{
		final IADProcessDAO adProcessesRepo = Services.get(IADProcessDAO.class);
		final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);

		final AdProcessId repostProcessId = adProcessesRepo.retrieveProcessIdByClassIfUnique(WEBUI_Fact_Acct_Repost_SingleDocument.class);
		if (repostProcessId == null)
		{
			logger.warn("No AD_Process_ID found for {}", WEBUI_Fact_Acct_Repost_SingleDocument.class);
			return;
		}

		//
		final List<String> linkToTableNames = new ArrayList<>();
		linkToTableNames.addAll(acctDocRegistry.getDocTableNames());
		linkToTableNames.add(WEBUI_Fact_Acct_Repost_ViewRows.TABLENAME_RV_UnPosted);

		//
		// Link Repost process to all accountable documents
		linkToTableNames
				.stream()
				.map(adTablesRepo::retrieveTableId)
				.map(AdTableId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.forEach(adTableId -> adProcessesRepo.registerTableProcess(RelatedProcessDescriptor.builder()
						.processId(repostProcessId)
						.tableId(adTableId)
						.anyWindow()
						.displayPlace(DisplayPlace.SingleDocumentActionsMenu)
						.build()));
		logger.info("Registered {} ({}) to {} tables", WEBUI_Fact_Acct_Repost_SingleDocument.class.getName(), repostProcessId, linkToTableNames.size());
	}

}
