/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.importer;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.serviceprovider.importer.info.ImportIssueInfo;
import de.metas.util.Loggables;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static de.metas.serviceprovider.importer.ImportConstants.IMPORT_LOG_MESSAGE_PREFIX;
import static de.metas.serviceprovider.importer.ImportConstants.ISSUE_QUEUE_CAPACITY;

@Component
public class ImportIssuesQueue
{
	private static final Logger log = LogManager.getLogger(ImportIssuesQueue.class);

	private final LinkedBlockingQueue<ImportIssueInfo> queue = new LinkedBlockingQueue<>(ISSUE_QUEUE_CAPACITY);

	public ImmutableList<ImportIssueInfo> drainAll()
	{
		final ArrayList<ImportIssueInfo> issues = new ArrayList<>();

		try
		{
			final Optional<ImportIssueInfo> importIssueInfo = Optional.ofNullable(queue.poll(1, TimeUnit.MINUTES));
			importIssueInfo.ifPresent(issues::add);

			queue.drainTo(issues);

			log.debug(" {} drained {} issues for processing!", IMPORT_LOG_MESSAGE_PREFIX, issues.size());
		}
		catch (final InterruptedException e)
		{
			Loggables.withLogger(log, Level.ERROR).addLog(IMPORT_LOG_MESSAGE_PREFIX + e.getMessage(),e);
		}

		return ImmutableList.copyOf(issues);
	}

	public boolean isEmpty()
	{
		return queue.isEmpty();
	}

	public void add(final ImportIssueInfo importIssueInfo)
	{
		try
		{
			queue.put(importIssueInfo);
		}
		catch (final InterruptedException e)
		{
			Loggables.withLogger(log, Level.ERROR).addLog(IMPORT_LOG_MESSAGE_PREFIX + e.getMessage(),e);
		}
	}
}
