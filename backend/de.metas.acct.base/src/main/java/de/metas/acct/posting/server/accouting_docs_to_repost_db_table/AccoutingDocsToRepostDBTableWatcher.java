package de.metas.acct.posting.server.accouting_docs_to_repost_db_table;

import java.time.Duration;
import java.util.List;

import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;

import de.metas.acct.api.IPostingRequestBuilder.PostImmediate;
import de.metas.acct.api.IPostingService;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;

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

public final class AccoutingDocsToRepostDBTableWatcher implements Runnable
{
	private static final Logger logger = LogManager.getLogger(AccoutingDocsToRepostDBTableWatcher.class);
	private final ISysConfigBL sysConfigBL;
	private final IPostingService postingService;
	private final AccoutingDocsToRepostDBTableRepository accoutingDocsToRepostDBTableRepository;

	private static final int RETRIEVE_CHUNK_SIZE = 100;
	private static final String SYSCONFIG_PollIntervalInSeconds = "de.metas.acct.accounting_docs_to_repost.pollIntervalInSeconds";
	private static final Duration DEFAULT_PollInterval = Duration.ofSeconds(10);

	@Builder
	private AccoutingDocsToRepostDBTableWatcher(
			@NonNull final ISysConfigBL sysConfigBL,
			@NonNull final IPostingService postingService)
	{
		this.sysConfigBL = sysConfigBL;
		this.postingService = postingService;
		this.accoutingDocsToRepostDBTableRepository = new AccoutingDocsToRepostDBTableRepository();
	}

	@Override
	public void run()
	{
		while (true)
		{
			final Duration pollInterval = getPollInterval();
			logger.debug("Sleeping {}", pollInterval);
			try
			{
				Thread.sleep(getPollInterval().toMillis());
			}
			catch (InterruptedException e)
			{
				logger.info("Got interrupt request. Exiting.");
				return;
			}

			try
			{
				enqueueAllForReposting();
			}
			catch (final Exception ex)
			{
				logger.warn("Failed to process. Ignored.", ex);
			}
		}
	}

	private void enqueueAllForReposting()
	{
		boolean tryAgain = false;
		do
		{
			final List<AccountingDocToRepost> docsToRepost = accoutingDocsToRepostDBTableRepository.retrieve(RETRIEVE_CHUNK_SIZE);
			if (docsToRepost.isEmpty())
			{
				return;
			}

			logger.info("Enqueueing for reposting {} documents: {}", docsToRepost.size(), docsToRepost);
			final Stopwatch stopwatch = Stopwatch.createStarted();

			for (final AccountingDocToRepost docToRepost : docsToRepost)
			{
				enqueueForReposting(docToRepost);
			}
			tryAgain = docsToRepost.size() >= 100;

			stopwatch.stop();
			logger.info("Done enqueueing {} documents in {} (tryAgain={})", docsToRepost.size(), stopwatch, tryAgain);
		}
		while (tryAgain);
	}

	private void enqueueForReposting(@NonNull final AccountingDocToRepost docToRepost)
	{
		try
		{
			postingService.newPostingRequest()
					.setClientId(docToRepost.getClientId())
					.setDocumentRef(docToRepost.getRecordRef())
					.setForce(docToRepost.isForce())
					.setFailOnError(false) // don't fail because we don't want to fail this thread
					.onErrorNotifyUser(docToRepost.getOnErrorNotifyUserId())
					.setPostImmediate(PostImmediate.No) // no, just enqueue it
					.postIt();
		}
		catch (Exception ex)
		{
			logger.warn("Failed enqueueing {}", docToRepost, ex);
		}
		finally
		{
			accoutingDocsToRepostDBTableRepository.delete(docToRepost);
		}
	}

	private Duration getPollInterval()
	{
		final int pollIntervalInSeconds = sysConfigBL.getIntValue(SYSCONFIG_PollIntervalInSeconds, -1);
		return pollIntervalInSeconds > 0
				? Duration.ofSeconds(pollIntervalInSeconds)
				: DEFAULT_PollInterval;
	}
}
