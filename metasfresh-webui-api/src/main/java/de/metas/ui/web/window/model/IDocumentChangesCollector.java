package de.metas.ui.web.window.model;

import java.util.Map;
import java.util.Set;

import org.adempiere.ad.expression.api.LogicExpressionResult;

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DetailId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IDocumentChangesCollector
{
	Set<String> getFieldNames(DocumentPath documentPath);

	Map<DocumentPath, DocumentChanges> getDocumentChangesByPath();

	void collectValueChanged(IDocumentFieldView documentField, ReasonSupplier reason);

	void collectValueIfChanged(IDocumentFieldView documentField, final Object valueOld, ReasonSupplier reason);

	void collectReadonlyIfChanged(IDocumentFieldView documentField, LogicExpressionResult valueOld, ReasonSupplier reason);

	void collectMandatoryIfChanged(IDocumentFieldView documentField, LogicExpressionResult valueOld, ReasonSupplier reason);

	void collectDisplayedIfChanged(IDocumentFieldView documentField, LogicExpressionResult valueOld, ReasonSupplier reason);

	void collectLookupValuesStaled(IDocumentFieldView documentField, ReasonSupplier reason);

	void collectFrom(IDocumentChangesCollector fromCollector);

	/**
	 * Collect changes from given document (only those which were not yet collected).
	 *
	 * @param fromCollector
	 * @return set of field names which were collected
	 */
	Set<String> collectFrom(Document document, ReasonSupplier reason);

	void collectDocumentValidStatusChanged(DocumentPath documentPath, DocumentValidStatus documentValidStatus);

	void collectValidStatus(IDocumentFieldView documentField);

	void collectDocumentSaveStatusChanged(DocumentPath documentPath, DocumentSaveStatus documentSaveStatus);

	void collectStaleDetailId(DocumentPath documentPath, DetailId detailId);

	@FunctionalInterface
	public interface ReasonSupplier
	{
		/**
		 * @return actual reason string
		 */
		String get();

		default ReasonSupplier add(final String name, final Object value)
		{
			return () -> this.get() + " | " + name + "=" + value;
		}

		default ReasonSupplier addPreviousReason(final ReasonSupplier previousReason)
		{
			final Object previousValue = null;
			return addPreviousReason(previousReason, previousValue);
		}

		default ReasonSupplier addPreviousReason(final ReasonSupplier previousReason, final Object previousValue)
		{
			if (previousReason == null && previousValue == null)
			{
				return this;
			}

			return () -> {
				final String reason = this.get();
				final StringBuilder reasonNew = new StringBuilder();
				reasonNew.append(reason == null ? "unknown reason" : reason);

				if (previousReason != null)
				{
					reasonNew.append(" | previous reason: ").append(previousReason);
				}
				if (previousValue != null)
				{
					reasonNew.append(" | previous value: ").append(previousValue);
				}
				return reasonNew.toString();
			};
		}

		static String toDebugString(final ReasonSupplier reasonSupplier)
		{
			if (reasonSupplier == null)
			{
				return null;
			}

			// Extract the reason only if debugging is enabled
			if (!WindowConstants.isProtocolDebugging())
			{
				return null;
			}

			return reasonSupplier.get();
		}

	}
}