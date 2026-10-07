package de.metas.document.engine.impl;

import de.metas.document.engine.IDocument;
import lombok.NonNull;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.POWrapper;
import org.compiere.model.PO;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

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

/**
 * Provides the document instance for each attempt of a document action that is retried on a DB deadlock.
 * <p>
 * Rolling back a failed attempt reverts the database, but not the document instance the attempt ran on: that instance
 * keeps the column values the attempt changed (which it now considers saved), its in-memory {@code DocStatus}, and
 * per-instance flags such as {@code MOrder}'s "just prepared" flag. Re-running the action on it would skip {@code prepareIt()}
 * (and with it the {@code BEFORE_PREPARE}/{@code AFTER_PREPARE} interceptors) and would not write values that the attempt
 * had already "saved". Therefore every retry runs on a freshly loaded instance that carries the caller's unsaved changes,
 * and after the retried action succeeded, the caller's instance is reloaded and receives the retry instance's unsaved changes
 * (e.g. the new {@code DocStatus}), so that the caller can save it as usual.
 * <p>
 * Limits:
 * <ul>
 * <li>Documents that are not backed by a saved {@link PO} (non-PO models, new records) are retried on the same instance, i.e. with the rolled back attempt's in-memory state.</li>
 * <li>The transfer resets only the caller's column values; the caller's per-instance fields (e.g. {@code MOrder}'s "just prepared" flag, cached lines)
 * stay as the first attempt left them - just as they stay as a successful first attempt leaves them.</li>
 * <li>If no attempt succeeds, nothing is transferred and the caller's instance keeps the first attempt's in-memory state.</li>
 * </ul>
 */
final class DocumentProcessingAttempts
{
	@NonNull private final IDocument callerDocument;
	@NonNull private final Function<Object, IDocument> documentFactory;

	@Nullable private final PO callerPO;
	@NonNull private final Map<String, Object> callerUnsavedValues;

	@Nullable private PO retryPO = null;
	private int attemptCount = 0;

	private DocumentProcessingAttempts(
			@NonNull final IDocument callerDocument,
			@NonNull final Function<Object, IDocument> documentFactory)
	{
		this.callerDocument = callerDocument;
		this.documentFactory = documentFactory;

		final PO po = POWrapper.getStrictPO(callerDocument.getDocumentModel());
		if (po != null && !po.is_new() && po.get_ID() > 0)
		{
			this.callerPO = po;
			this.callerUnsavedValues = Collections.unmodifiableMap(extractUnsavedValues(po));
		}
		else
		{
			this.callerPO = null;
			this.callerUnsavedValues = Collections.emptyMap();
		}
	}

	static DocumentProcessingAttempts of(
			@NonNull final IDocument callerDocument,
			@NonNull final Function<Object, IDocument> documentFactory)
	{
		return new DocumentProcessingAttempts(callerDocument, documentFactory);
	}

	/**
	 * @return the caller's document for the first attempt, a fresh instance carrying the caller's unsaved changes for every further attempt.
	 * To be called outside the attempt's transaction.
	 */
	IDocument nextAttemptDocument()
	{
		attemptCount++;
		if (attemptCount == 1 || callerPO == null)
		{
			return callerDocument;
		}

		final PO freshPO = TableModelLoader.instance.getPO(callerPO.getCtx(), callerPO.get_TableName(), callerPO.get_ID(), ITrx.TRXNAME_None);
		if (freshPO == null)
		{
			throw new AdempiereException("Cannot reload the document for retrying its processing")
					.setParameter("tableName", callerPO.get_TableName())
					.setParameter("recordId", callerPO.get_ID());
		}
		callerUnsavedValues.forEach(freshPO::set_ValueNoCheck);

		this.retryPO = freshPO;
		return documentFactory.apply(freshPO);
	}

	/**
	 * If the action was retried, reloads the caller's instance (the retry was committed) and applies the retry instance's unsaved changes to it.
	 * To be called after the action succeeded, outside its transaction.
	 */
	void transferOutcomeToCallerDocument()
	{
		final PO callerPO = this.callerPO;
		final PO retryPO = this.retryPO;
		if (callerPO == null || retryPO == null)
		{
			return;
		}

		callerPO.load(callerPO.get_TrxName());
		extractUnsavedValues(retryPO).forEach(callerPO::set_ValueNoCheck);
	}

	private static Map<String, Object> extractUnsavedValues(@NonNull final PO po)
	{
		final LinkedHashMap<String, Object> unsavedValues = new LinkedHashMap<>(); // may contain null values
		for (int i = 0, columnCount = po.get_ColumnCount(); i < columnCount; i++)
		{
			if (po.is_ValueChanged(i))
			{
				unsavedValues.put(po.get_ColumnName(i), po.get_Value(i));
			}
		}
		return unsavedValues;
	}
}
