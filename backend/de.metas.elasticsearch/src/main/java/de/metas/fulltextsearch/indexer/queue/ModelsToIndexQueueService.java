/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.indexer.queue;

import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ModelsToIndexQueueService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ModelToIndexRepository repository;

	public ModelsToIndexQueueService(
			@NonNull final ModelToIndexRepository repository)
	{
		this.repository = repository;
	}

	public void enqueue(@NonNull final Collection<ModelToIndexEnqueueRequest> requests)
	{
		if (!requests.isEmpty())
		{
			getRequestsCollector().addRequests(requests);
		}
	}

	private RequestsCollector getRequestsCollector()
	{
		return trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail) // at this point we always run in trx
				.getPropertyAndProcessAfterCommit(
						RequestsCollector.class.getName(),
						RequestsCollector::new,
						this::enqueueNow);
	}

	private void enqueueNow(@NonNull final RequestsCollector requestsCollector)
	{
		final List<ModelToIndexEnqueueRequest> requests = requestsCollector.getRequestsAndMarkedProcessed();
		enqueueNow(requests);
	}

	public void enqueueNow(@NonNull final List<ModelToIndexEnqueueRequest> requests)
	{
		repository.addToQueue(requests);
	}

	@ToString
	private static class RequestsCollector
	{
		private boolean processed = false;
		private final ArrayList<ModelToIndexEnqueueRequest> requests = new ArrayList<>();

		public synchronized void addRequests(@NonNull final Collection<ModelToIndexEnqueueRequest> requests)
		{
			assertNotProcessed();
			this.requests.addAll(requests);
		}

		private void assertNotProcessed()
		{
			if (processed)
			{
				throw new AdempiereException("already processed: " + this);
			}
		}

		public synchronized List<ModelToIndexEnqueueRequest> getRequestsAndMarkedProcessed()
		{
			assertNotProcessed();
			this.processed = true;
			return requests;
		}
	}
}
