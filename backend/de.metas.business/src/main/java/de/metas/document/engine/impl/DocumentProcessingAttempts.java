/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.document.engine.impl;


import de.metas.document.engine.IDocument;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/**
 * Prepares the caller's document for each attempt of a document action that is retried on a DB deadlock.
 * <p>
 * All attempts run on the caller's instance. Rolling back an attempt reverts the database, but not the in-memory state the attempt left in
 * that instance (column values it considers saved, {@code DocStatus}, the "just prepared" flag, cached lines). So before each retry,
 * the instance's column state is restored from a snapshot taken before the first attempt, and {@link IDocument#resetEngineStateForRetry()}
 * drops the state the action built up.
 * <p>
 * If the action finally fails (its transaction was rolled back), the column state is restored as well, so that the instance matches the database;
 * the process message is kept. If an attempt returns {@code false}, its transaction is committed, so nothing is restored.
 * <p>
 * If the model is not supported by the {@link RetryStateSnapshotter} (new record, POJO, {@code GridTab}), or taking or restoring the snapshot fails,
 * the column state is not restored; the reset hook is still called before a retry.
 */
final class DocumentProcessingAttempts
{
	private static final Logger logger = LogManager.getLogger(DocumentProcessingAttempts.class);

	@NonNull private final IDocument document;
	@Nullable private final Runnable restoreSnapshot;
	private int attemptCount = 0;

	private DocumentProcessingAttempts(@NonNull final IDocument document, @NonNull final RetryStateSnapshotter snapshotter)
	{
		this.document = document;
		this.restoreSnapshot = snapshotOrNull(document, snapshotter);
	}

	/**
	 * To be created before the first attempt.
	 */
	static DocumentProcessingAttempts of(@NonNull final IDocument document, @NonNull final RetryStateSnapshotter snapshotter)
	{
		return new DocumentProcessingAttempts(document, snapshotter);
	}

	@Nullable
	private static Runnable snapshotOrNull(@NonNull final IDocument document, @NonNull final RetryStateSnapshotter snapshotter)
	{
		try
		{
			return snapshotter.snapshot(document.getDocumentModel());
		}
		catch (final Exception ex)
		{
			logger.warn("Cannot snapshot {} before processing it; if the processing is retried, its in-memory column state won't be restored", document, ex);
			return null;
		}
	}

	/**
	 * @return the document for the next attempt: the caller's document, reset to its state before the first attempt if this is a retry
	 */
	IDocument nextAttemptDocument()
	{
		attemptCount++;
		if (attemptCount > 1)
		{
			restoreColumnState();
			document.resetEngineStateForRetry();
		}
		return document;
	}

	/**
	 * To be called when the action failed, i.e. its last attempt was rolled back: restores the column state to the one before the first attempt.
	 */
	void onFailure()
	{
		restoreColumnState();
	}

	private void restoreColumnState()
	{
		if (restoreSnapshot == null)
		{
			return;
		}

		try
		{
			restoreSnapshot.run();
		}
		catch (final Exception ex)
		{
			// don't fail (and so hide the original problem); the next attempt or the caller just works with the instance as it is
			logger.warn("Cannot restore {} to its state before processing it", document, ex);
		}
	}
}
