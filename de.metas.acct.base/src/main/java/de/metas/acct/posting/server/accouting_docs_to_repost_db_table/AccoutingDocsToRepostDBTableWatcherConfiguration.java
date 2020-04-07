package de.metas.acct.posting.server.accouting_docs_to_repost_db_table;

import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import de.metas.acct.api.IPostingService;
import de.metas.logging.LogManager;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Configuration
@Profile(Profiles.PROFILE_AccountingService)
public class AccoutingDocsToRepostDBTableWatcherConfiguration implements InitializingBean
{
	private static final Logger logger = LogManager.getLogger(AccoutingDocsToRepostDBTableWatcherConfiguration.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IPostingService postingService = Services.get(IPostingService.class);
	private final AccoutingDocsToRepostDBTableRepository accoutingDocsToRepostDBTableRepository;

	public AccoutingDocsToRepostDBTableWatcherConfiguration()
	{
		accoutingDocsToRepostDBTableRepository = new AccoutingDocsToRepostDBTableRepository();
	}

	@Override
	public void afterPropertiesSet()
	{
		startAccoutingDocsToRepostDBTableWatcher();
	}

	private void startAccoutingDocsToRepostDBTableWatcher()
	{
		final AccoutingDocsToRepostDBTableWatcher watcher = AccoutingDocsToRepostDBTableWatcher.builder()
				.sysConfigBL(sysConfigBL)
				.postingService(postingService)
				.accoutingDocsToRepostDBTableRepository(accoutingDocsToRepostDBTableRepository)
				.build();

		final Thread thread = new Thread(watcher);
		thread.setDaemon(true);
		thread.setName(AccoutingDocsToRepostDBTableWatcher.class.getName());
		thread.start();
		logger.info("Started {}", thread);
	}
}
