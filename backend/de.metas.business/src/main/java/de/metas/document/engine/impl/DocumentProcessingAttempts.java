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
import org.adempiere.model.POWrapper;
import org.compiere.model.PO;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Provides the document instance for each attempt of a document action that is retried on a DB deadlock.
 * <p>
 * A rolled back attempt leaves its in-memory state (column values it considers saved, {@code DocStatus}, flags like "just prepared")
 * in the instance it ran on, so a retry on that instance would e.g. skip {@code prepareIt()}. Therefore each retry runs on a new
 * instance taken from a snapshot of the caller's model before the first attempt, and after a retry the caller's model and document
 * take over the complete state of the retry's instances (see {@link InstanceStateCopier}).
 * <p>
 * Limit: a model that the {@link RetryModelSnapshotter} does not support (e.g. a new record, or one that is not a {@link PO}, like a
 * {@code GridTab}-backed model) is retried on the caller's instance.
 */
final class DocumentProcessingAttempts
{
	private static final Logger logger = LogManager.getLogger(DocumentProcessingAttempts.class);

	@NonNull private final IDocument callerDocument;
	@NonNull private final Function<Object, IDocument> documentFactory;
	@Nullable private final Supplier<Object> retryModelSupplier;

	private int attemptCount = 0;
	@Nullable private IDocument retryDocument = null;

	private DocumentProcessingAttempts(
			@NonNull final IDocument callerDocument,
			@NonNull final Function<Object, IDocument> documentFactory,
			@NonNull final RetryModelSnapshotter snapshotter)
	{
		this.callerDocument = callerDocument;
		this.documentFactory = documentFactory;
		this.retryModelSupplier = snapshotter.snapshot(callerDocument.getDocumentModel());
	}

	/**
	 * To be created before the first attempt.
	 */
	static DocumentProcessingAttempts of(
			@NonNull final IDocument callerDocument,
			@NonNull final Function<Object, IDocument> documentFactory,
			@NonNull final RetryModelSnapshotter snapshotter)
	{
		return new DocumentProcessingAttempts(callerDocument, documentFactory, snapshotter);
	}

	/**
	 * @return the caller's document for the first attempt, a new instance from the snapshot for every further attempt
	 */
	IDocument nextAttemptDocument()
	{
		attemptCount++;
		if (attemptCount == 1 || retryModelSupplier == null)
		{
			return callerDocument;
		}

		retryDocument = documentFactory.apply(retryModelSupplier.get());
		return retryDocument;
	}

	/**
	 * To be called after the action returned (i.e. its transaction was committed): if it was retried, the caller's model and document
	 * take over the state of the last attempt's instances. A failure to do so is logged, but does not fail the committed action.
	 */
	void transferOutcomeToCallerDocument()
	{
		final IDocument retryDocument = this.retryDocument;
		if (retryDocument == null)
		{
			return;
		}

		try
		{
			final Object callerModel = unwrapPO(callerDocument.getDocumentModel());
			InstanceStateCopier.copyState(unwrapPO(retryDocument.getDocumentModel()), callerModel);
			if (callerDocument != callerModel)
			{
				// e.g. a DocumentWrapper, which keeps its own processing state
				InstanceStateCopier.copyState(retryDocument, callerDocument);
			}
		}
		catch (final Exception ex)
		{
			logger.warn("The document action on {} was retried and committed, but its result could not be transferred to the caller's instance;"
					+ " that instance is stale and needs to be reloaded", callerDocument, ex);
		}
	}

	private static Object unwrapPO(@NonNull final Object model)
	{
		final PO po = POWrapper.getStrictPO(model);
		return po != null ? po : model;
	}
}
