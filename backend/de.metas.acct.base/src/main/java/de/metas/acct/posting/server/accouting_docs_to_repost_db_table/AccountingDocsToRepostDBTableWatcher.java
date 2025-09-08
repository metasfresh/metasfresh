package de.metas.acct.posting.server.accouting_docs_to_repost_db_table;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.DocumentPostMultiRequest;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.api.IPostingService;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;

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

public final class AccountingDocsToRepostDBTableWatcher implements Runnable
{
	private static final Logger logger = LogManager.getLogger(AccountingDocsToRepostDBTableWatcher.class);
	private final ISysConfigBL sysConfigBL;
	private final IPostingService postingService;
	private final AccountingDocsToRepostDBTableRepository accountingDocsToRepostDBTableRepository;

	private static final String SYSCONFIG_RetrieveChunkSize = "de.metas.acct.accounting_docs_to_repost.retrieveChunkSize";
	private static final int DEFAULT_RetrieveChunkSize = 100;
	
	private static final String SYSCONFIG_PollIntervalInSeconds = "de.metas.acct.accounting_docs_to_repost.pollIntervalInSeconds";
	private static final Duration DEFAULT_PollInterval = Duration.ofSeconds(10);

	@Builder
	private AccountingDocsToRepostDBTableWatcher(
			@NonNull final ISysConfigBL sysConfigBL,
			@NonNull final IPostingService postingService)
	{
		this.sysConfigBL = sysConfigBL;
		this.postingService = postingService;
		this.accountingDocsToRepostDBTableRepository = new AccountingDocsToRepostDBTableRepository();
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
				//noinspection BusyWait
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
		boolean tryAgain;
		do
		{
			final int chunkSize = getRetrieveChunkSize();
			final List<AccountingDocToRepost> docsToRepost = accountingDocsToRepostDBTableRepository.retrieve(chunkSize);
			if (docsToRepost.isEmpty())
			{
				return;
			}

			logger.info("Enqueueing for reposting {} documents: {}", docsToRepost.size(), docsToRepost);
			final Stopwatch stopwatch = Stopwatch.createStarted();

			try
			{
				postingService.schedule(toDocumentPostMultiRequest(docsToRepost));
			}
			catch (Exception ex)
			{
				logger.warn("Failed enqueueing {}", docsToRepost, ex);
			}
			finally
			{
				accountingDocsToRepostDBTableRepository.delete(docsToRepost);
			}

			tryAgain = docsToRepost.size() >= chunkSize;

			stopwatch.stop();
			logger.info("Done enqueueing {} documents in {} (tryAgain={})", docsToRepost.size(), stopwatch, tryAgain);
		}
		while (tryAgain);
	}

	private static DocumentPostMultiRequest toDocumentPostMultiRequest(final List<AccountingDocToRepost> docsToRepost)
	{
		return DocumentPostMultiRequest.ofNonEmptyCollection(
				docsToRepost.stream()
						.map(AccountingDocsToRepostDBTableWatcher::toDocumentPostRequest)
						.collect(ImmutableSet.toImmutableSet())
		);
	}

	private static DocumentPostRequest toDocumentPostRequest(@NonNull final AccountingDocToRepost docToRepost)
	{
		return DocumentPostRequest.builder()
				.record(docToRepost.getRecordRef())
				.clientId(docToRepost.getClientId())
				.force(docToRepost.isForce())
				.onErrorNotifyUserId(docToRepost.getOnErrorNotifyUserId())
				.build();
	}

	private Duration getPollInterval()
	{
		final int pollIntervalInSeconds = sysConfigBL.getIntValue(SYSCONFIG_PollIntervalInSeconds, -1);
		return pollIntervalInSeconds > 0
				? Duration.ofSeconds(pollIntervalInSeconds)
				: DEFAULT_PollInterval;
	}

	private int getRetrieveChunkSize()
	{
		final int retrieveChunkSize = sysConfigBL.getIntValue(SYSCONFIG_RetrieveChunkSize, -1);
		return retrieveChunkSize > 0 ? retrieveChunkSize : DEFAULT_RetrieveChunkSize;
	}
}
