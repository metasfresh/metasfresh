package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.ZoomInfoFactory;
import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.util.Evaluatee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.document.filter.MQueryDocumentFilterHelper;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;

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
	@Autowired
	private DocumentCollection documentCollection;

	public List<DocumentReference> getDocumentReferences(final DocumentPath documentPath)
	{
		return documentCollection.forDocumentReadonly(documentPath, document -> {
			if (document.isNew())
			{
				return ImmutableList.of();
			}

			final DocumentAsZoomSource zoomSource = new DocumentAsZoomSource(document);
			final ITranslatableString filterCaption = extractFilterCaption(document);

			return ZoomInfoFactory.get()
					.streamZoomInfos(zoomSource)
					.map(zoomInfo -> createDocumentReference(zoomInfo, filterCaption))
					.collect(ImmutableList.toImmutableList());
		});
	}

	public DocumentReference getDocumentReference(final DocumentPath sourceDocumentPath, final WindowId targetWindowId)
	{
		return documentCollection.forDocumentReadonly(sourceDocumentPath, sourceDocument -> {
			if (sourceDocument.isNew())
			{
				throw new IllegalArgumentException("New documents cannot be referenced: " + sourceDocument);
			}

			final DocumentAsZoomSource zoomSource = new DocumentAsZoomSource(sourceDocument);

			final ZoomInfo zoomInfo = ZoomInfoFactory.get().retrieveZoomInfo(zoomSource, targetWindowId.toInt());
			final ITranslatableString filterCaption = extractFilterCaption(sourceDocument);
			return createDocumentReference(zoomInfo, filterCaption);
		});
	}

	private final ITranslatableString extractFilterCaption(final Document sourceDocument)
	{
		//
		// Window caption
		final ITranslatableString windowCaption = sourceDocument.getEntityDescriptor().getCaption();

		//
		// Document info
		// TODO: i think we shall use lookup to fetch the document description
		final ITranslatableString documentSummary;
		if (sourceDocument.hasField(WindowConstants.FIELDNAME_DocumentSummary))
		{
			final String documentSummaryStr = sourceDocument.getFieldView(WindowConstants.FIELDNAME_DocumentSummary).getValueAs(String.class);
			documentSummary = ImmutableTranslatableString.constant(documentSummaryStr);
		}
		else if (sourceDocument.hasField(WindowConstants.FIELDNAME_DocumentNo))
		{
			final String documentNoStr = sourceDocument.getFieldView(WindowConstants.FIELDNAME_DocumentNo).getValueAs(String.class);
			documentSummary = ImmutableTranslatableString.constant(documentNoStr);
		}
		else if (sourceDocument.hasField(WindowConstants.FIELDNAME_Name))
		{
			final String nameStr = sourceDocument.getFieldView(WindowConstants.FIELDNAME_Name).getValueAs(String.class);
			documentSummary = ImmutableTranslatableString.constant(nameStr);
		}
		else
		{
			documentSummary = ImmutableTranslatableString.constant(sourceDocument.getDocumentId().toString());
		}

		// Window caption + document info
		return ITranslatableString.compose(" ", windowCaption, documentSummary);
	}

	private static final DocumentReference createDocumentReference(final ZoomInfo zoomInfo, ITranslatableString filterCaption)
	{
		return DocumentReference.builder()
				.id(zoomInfo.getId())
				.caption(zoomInfo.getLabel())
				.windowId(WindowId.of(zoomInfo.getAD_Window_ID()))
				.documentsCount(zoomInfo.getRecordCount())
				.filter(MQueryDocumentFilterHelper.createDocumentFilterFromMQuery(zoomInfo.getQuery(), filterCaption))
				.loadDuration(zoomInfo.getRecordCountDuration())
				.build();
	}

	private static final class DocumentAsZoomSource implements IZoomSource
	{
		private final Properties ctx;
		private final Evaluatee evaluationContext;

		private final int adWindowId;
		private final String tableName;
		private final int adTableId;
		private final int recordId;
		private final String keyColumnName;
		private final Document document;

		private DocumentAsZoomSource(final Document document)
		{
			ctx = document.getCtx();
			this.document = document;
			evaluationContext = document.asEvaluatee();

			final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
			adWindowId = entityDescriptor.getWindowId().toInt();
			tableName = entityDescriptor.getTableName();
			adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
			recordId = document.getDocumentId().toInt();
			keyColumnName = extractSingleKeyColumNameOrNull(entityDescriptor);
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

			final String tableName = entityDescriptor.getTableName();
			final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
			final I_AD_Column idColumn = adTableDAO.retrieveColumn(tableName, keyColumnName);
			return idColumn.isGenericZoomOrigin() ? keyColumnName : null;
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
		public int getAD_Window_ID()
		{
			return adWindowId;
		}

		@Override
		public String getTableName()
		{
			return tableName;
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
