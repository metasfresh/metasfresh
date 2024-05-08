package de.metas.order.voidorderandrelateddocs;

import java.util.List;
import java.util.Optional;

import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ImmutablePair;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsHandler.RecordsToHandleKey;
import de.metas.util.RelatedRecordsProvider;
import de.metas.util.RelatedRecordsProvider.SourceRecordsKey;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

@Service
public class VoidOrderAndRelatedDocsService
{
	private final ImmutableListMultimap<RecordsToHandleKey, VoidOrderAndRelatedDocsHandler> key2handlers;
	private final ImmutableListMultimap<SourceRecordsKey, RelatedRecordsProvider> key2providers;

	public VoidOrderAndRelatedDocsService(
			@NonNull final Optional<List<VoidOrderAndRelatedDocsHandler>> handlers,
			@NonNull final Optional<List<RelatedRecordsProvider>> providers)
	{

		this.key2handlers = Multimaps.index(
				handlers.orElse(ImmutableList.of()),
				VoidOrderAndRelatedDocsHandler::getRecordsToHandleKey);

		this.key2providers = Multimaps.index(
				providers.orElse(ImmutableList.of()),
				RelatedRecordsProvider::getSourceRecordsKey);

	}

	public void invokeHandlers(@NonNull final VoidOrderAndRelatedDocsRequest request)
	{
		final IPair<RecordsToHandleKey, List<ITableRecordReference>> recordsToHandle = request.getRecordsToHandle();

		final RecordsToHandleKey currentKey = recordsToHandle.getLeft();

		final SourceRecordsKey sourceRecordsKey = currentKey.toSourceRecordsKey();
		for (final RelatedRecordsProvider currentProvider : key2providers.get(sourceRecordsKey))
		{
			final List<ITableRecordReference> inputForProvider = recordsToHandle.getRight();
			final IPair<SourceRecordsKey, List<ITableRecordReference>> relatedRecords = currentProvider.provideRelatedRecords(inputForProvider);

			// recurse
			if (relatedRecords.getRight().isEmpty())
			{
				continue;
			}
			final VoidOrderAndRelatedDocsRequest recursionRequest = createRecursionRequest(request, relatedRecords);
			invokeHandlers(recursionRequest);
		}

		final ImmutableList<VoidOrderAndRelatedDocsHandler> handlersForKey = key2handlers.get(currentKey); // returns empty list if there are no handlers
		for (final VoidOrderAndRelatedDocsHandler handlerForCurrentKey : handlersForKey)
		{
			handlerForCurrentKey.handleOrderVoided(request);
		}
	}

	private VoidOrderAndRelatedDocsRequest createRecursionRequest(
			@NonNull final VoidOrderAndRelatedDocsRequest originalRequest,
			@NonNull final IPair<SourceRecordsKey, List<ITableRecordReference>> relatedRecords)
	{
		final RecordsToHandleKey recordsToHandleKey = RecordsToHandleKey.ofSourceRecordsKey(relatedRecords.getLeft());

		final IPair<RecordsToHandleKey, List<ITableRecordReference>> //
		recordsToHandle = ImmutablePair.of(recordsToHandleKey, relatedRecords.getRight());

		final VoidOrderAndRelatedDocsRequest recursionRequest = originalRequest
				.toBuilder()
				.recordsToHandle(recordsToHandle)
				.build();
		return recursionRequest;
	}
}
