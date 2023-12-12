/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web.audit;

import de.metas.util.web.audit.dto.ApiResponse;
import lombok.EqualsAndHashCode;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@EqualsAndHashCode
public class HttpCallScheduler
{
	private CompletableFuture<Void> executorFuture;
	private final LinkedBlockingQueue<ScheduledRequest> requestsQueue;
	private final Executor executor;

	public HttpCallScheduler()
	{
		this.requestsQueue = new LinkedBlockingQueue<>();
		this.executor = Executors.newFixedThreadPool(1);

		this.executorFuture = new CompletableFuture<>();
		this.executorFuture.complete(null);
	}

	public synchronized void schedule(final ScheduledRequest request)
	{
		this.requestsQueue.add(request);

		if (executorFuture.isDone())
		{
			executorFuture = executorFuture.thenRunAsync(this::run, executor);
		}
	}

	private void run()
	{
		try
		{
			while (!requestsQueue.isEmpty())
			{
				final ScheduledRequest scheduleRequest = requestsQueue.poll();
				try
				{
					final ApiResponse response = scheduleRequest.getHttpResponseSupplier().get();

					scheduleRequest.getCompletableFuture().complete(response);
				}
				catch (final Exception exception)
				{
					scheduleRequest.getCompletableFuture().completeExceptionally(exception);
				}
			}
		}
		finally
		{
			executorFuture.complete(null);
		}
	}
}
