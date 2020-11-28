package de.metas.ui.web.document.references.service;

import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Column;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.document.references.IZoomSource;
import de.metas.document.references.ZoomInfo;
import de.metas.document.references.ZoomInfoFactory;
import de.metas.document.references.ZoomInfoPermissions;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.references.DocumentReference;
import de.metas.ui.web.document.references.DocumentReferenceCandidate;
import de.metas.ui.web.document.references.DocumentReferenceId;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class DocumentReferencesService
{
	private static final Logger logger = LogManager.getLogger(DocumentReferencesService.class);
	private final DocumentCollection documentCollection;

	public DocumentReferencesService(@NonNull final DocumentCollection documentCollection)
	{
		this.documentCollection = documentCollection;
	}

	public ImmutableList<DocumentReferenceCandidate> getDocumentReferenceCandidates(
			@NonNull final DocumentPath documentPath,
			@NonNull final ZoomInfoPermissions permissions)
	{
		// Document with composed keys does not support references
		if (documentPath.isComposedKey())
		{
			return ImmutableList.of();
		}

		final ZoomInfoFactory zoomInfoFactory = ZoomInfoFactory.get();

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final ImmutableList<DocumentReferenceCandidate> documentReferences = documentCollection.forDocumentReadonly(
				documentPath,
				document -> {
					if (document.isNew())
					{
						return ImmutableList.of();
					}

					final ITranslatableString filterCaption = extractFilterCaption(document, (ZoomInfo)null);

					final DocumentAsZoomSource zoomSource = new DocumentAsZoomSource(document);
					return zoomInfoFactory
							.getZoomInfoCandidates(zoomSource, permissions)
							.stream()
							.map(zoomInfoCandidate -> new DocumentReferenceCandidate(zoomInfoCandidate, filterCaption))
							.collect(ImmutableList.toImmutableList());
				});
		stopwatch.stop();

		logger.debug("Fetched initial document references stream for {} in {}", documentPath, stopwatch);

		return documentReferences;
	}

	public DocumentFilter getDocumentReferenceFilter(
			@NonNull final DocumentPath sourceDocumentPath,
			@NonNull final WindowId targetWindowId,
			@Nullable final DocumentReferenceId documentReferenceId,
			@NonNull final ZoomInfoPermissions permissions)
	{
		final DocumentReference documentReference = getDocumentReference(sourceDocumentPath, targetWindowId, documentReferenceId, permissions);
		return documentReference.getFilter();
	}

	private DocumentReference getDocumentReference(
			@NonNull final DocumentPath sourceDocumentPath,
			@NonNull final WindowId targetWindowId,
			@Nullable final DocumentReferenceId documentReferenceId,
			@NonNull final ZoomInfoPermissions permissions)
	{
		final ZoomInfoFactory zoomInfoFactory = ZoomInfoFactory.get();

		return documentCollection.forDocumentReadonly(sourceDocumentPath, sourceDocument -> {
			if (sourceDocument.isNew())
			{
				throw new AdempiereException("New documents cannot be referenced: " + sourceDocument);
			}

			final DocumentAsZoomSource zoomSource = new DocumentAsZoomSource(sourceDocument);
			final ZoomInfo zoomInfo = zoomInfoFactory.retrieveZoomInfo(
					zoomSource,
					targetWindowId.toAdWindowId(),
					documentReferenceId != null ? documentReferenceId.toZoomInfoId() : null,
					permissions);

			final ITranslatableString filterCaption = extractFilterCaption(sourceDocument, zoomInfo);

			return DocumentReferenceCandidate.toDocumentReference(zoomInfo, filterCaption);
		});
	}

	private final ITranslatableString extractFilterCaption(
			@NonNull final Document sourceDocument,
			@Nullable final ZoomInfo zoomInfo)
	{
		final TranslatableStringBuilder result = TranslatableStrings.builder();

		//
		// Window caption
		result.append(sourceDocument.getEntityDescriptor().getCaption());

		//
		// Document info
		// TODO: i think we shall use lookup to fetch the document description
		// final ITranslatableString documentSummary;
		if (sourceDocument.hasField(WindowConstants.FIELDNAME_DocumentSummary))
		{
			final String documentSummaryStr = sourceDocument.getFieldView(WindowConstants.FIELDNAME_DocumentSummary).getValueAs(String.class);
			result.append(" ").append(documentSummaryStr);
		}
		else if (sourceDocument.hasField(WindowConstants.FIELDNAME_DocumentNo))
		{
			final String documentNoStr = sourceDocument.getFieldView(WindowConstants.FIELDNAME_DocumentNo).getValueAs(String.class);
			result.append(" ").append(documentNoStr);
		}
		else if (sourceDocument.hasField(WindowConstants.FIELDNAME_Name))
		{
			final String nameStr = sourceDocument.getFieldView(WindowConstants.FIELDNAME_Name).getValueAs(String.class);
			result.append(" ").append(nameStr);
		}
		else
		{
			result.append(" ").append(sourceDocument.getDocumentId().toString());
		}

		//
		// Category
		if (zoomInfo != null)
		{
			final ITranslatableString categoryDisplayName = zoomInfo.getTargetWindow().getCategoryDisplayName();
			if (!TranslatableStrings.isBlank(categoryDisplayName))
			{
				result.append(" / ").append(categoryDisplayName);
			}
		}

		return result.build();
	}

	private static final class DocumentAsZoomSource implements IZoomSource
	{
		private final Properties ctx;
		private final Evaluatee evaluationContext;

		private final AdWindowId adWindowId;
		private final int adTableId;
		private final int recordId;
		private final String keyColumnName;
		private final Document document;

		@Getter
		private final boolean genericZoomOrigin;

		@Getter
		private final String tableName;

		private DocumentAsZoomSource(@NonNull final Document document)
		{
			ctx = document.getCtx();
			this.document = document;
			evaluationContext = document.asEvaluatee();

			final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
			adWindowId = entityDescriptor.getWindowId().toAdWindowId();
			tableName = entityDescriptor.getTableName();

			adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
			recordId = document.getDocumentId().toInt();
			keyColumnName = extractSingleKeyColumNameOrNull(entityDescriptor);

			genericZoomOrigin = extractGenericZoomOrigin(tableName, keyColumnName);
		}

		private static String extractSingleKeyColumNameOrNull(final DocumentEntityDescriptor entityDescriptor)
		{
			final DocumentFieldDescriptor idField = entityDescriptor.getSingleIdFieldOrNull();
			if (idField == null)
			{
				return null;
			}

			final DocumentFieldDataBindingDescriptor idFieldBinding = idField.getDataBinding().orElse(null);
			if (idFieldBinding == null)
			{
				return null;
			}

			final String keyColumnName = idFieldBinding.getColumnName();
			return keyColumnName;
		}

		private boolean extractGenericZoomOrigin(
				@NonNull final String tableName,
				@Nullable final String keyColumnName)
		{
			if (keyColumnName != null)
			{
				final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
				final I_AD_Column idColumn = adTableDAO.retrieveColumn(tableName, keyColumnName);
				return idColumn.isGenericZoomOrigin();
			}
			return false;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("tableName", tableName)
					.add("recordId", recordId)
					.add("AD_Window_ID", adWindowId)
					.toString();
		}

		@Override
		public Properties getCtx()
		{
			return ctx;
		}

		@Override
		public String getTrxName()
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		public AdWindowId getAD_Window_ID()
		{
			return adWindowId;
		}

		@Override
		public int getAD_Table_ID()
		{
			return adTableId;
		}

		@Override
		public String getKeyColumnNameOrNull()
		{
			return keyColumnName;
		}

		@Override
		public int getRecord_ID()
		{
			return recordId;
		}

		@Override
		public Evaluatee createEvaluationContext()
		{
			return evaluationContext;
		}

		@Override
		public boolean hasField(final String columnName)
		{
			return document.hasField(columnName);
		}

		@Override
		public Object getFieldValue(final String columnName)
		{
			return document.getFieldView(columnName).getValue();
		}

		@Override
		public boolean getFieldValueAsBoolean(final String columnName)
		{
			return document.getFieldView(columnName).getValueAsBoolean();
		}
	}
}
