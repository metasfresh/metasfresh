package de.metas.vertical.pharma.securpharm.actions;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * #%L
 * metasfresh-pharma.securpharm
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

@Component
@Primary
public class AsyncSecurPharmActionsDispatcher implements SecurPharmActionsDispatcher
{
	private static final Topic TOPIC = Topic.distributed("de.metas.vertical.pharma.securpharm.actions");

	private final IEventBus eventBus;
	private final Executor executor;

	public AsyncSecurPharmActionsDispatcher(@NonNull final IEventBusFactory eventBusFactory)
	{
		eventBus = eventBusFactory.getEventBus(TOPIC);
		executor = createAsyncExecutor();
	}

	private static Executor createAsyncExecutor()
	{
		final CustomizableThreadFactory asyncThreadFactory = new CustomizableThreadFactory(SecurPharmActionProcessor.class.getSimpleName());
		asyncThreadFactory.setDaemon(true);

		return Executors.newSingleThreadExecutor(asyncThreadFactory);
	}

	@Override
	public void subscribe(@NonNull final SecurPharmActionsHandler handler)
	{
		final AsyncSecurPharmActionsHandler asyncHandler = new AsyncSecurPharmActionsHandler(handler, executor);
		eventBus.subscribeOn(SecurPharmaActionRequest.class, asyncHandler::handleActionRequest);
	}

	@Override
	public void post(@NonNull final SecurPharmaActionRequest request)
	{
		eventBus.enqueueObject(request);
	}

	@ToString
	private static class AsyncSecurPharmActionsHandler implements SecurPharmActionsHandler
	{
		private final SecurPharmActionsHandler delegate;
		private final Executor executor;

		private AsyncSecurPharmActionsHandler(
				@NonNull final SecurPharmActionsHandler delegate,
				@NonNull final Executor executor)
		{
			this.delegate = delegate;
			this.executor = executor;
		}

		@Override
		public void handleActionRequest(@NonNull final SecurPharmaActionRequest request)
		{
			executor.execute(() -> delegate.handleActionRequest(request));
		}

	}
}
