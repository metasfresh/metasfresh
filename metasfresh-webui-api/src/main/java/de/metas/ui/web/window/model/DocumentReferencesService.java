package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.ZoomInfoFactory;
import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.util.Evaluatee;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;

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

@Service
public class DocumentReferencesService
{
	public Map<String, DocumentReference> getDocumentReferences(final Document document)
	{
		if (document == null || document.isNew())
		{
			return ImmutableMap.of();
		}

		final DocumentAsZoomSource zoomSource = new DocumentAsZoomSource(document);

		return ZoomInfoFactory.get()
				.retrieveZoomInfos(zoomSource)
				.stream()
				.map(zoomInfo -> DocumentReference.of(zoomInfo))
				.collect(GuavaCollectors.toImmutableMapByKey(DocumentReference::getId));
	}

	private static final class DocumentAsZoomSource implements IZoomSource
	{
		private final Properties ctx;
		private final DocumentEvaluatee evaluationContext;
		
		private final int adWindowId;
		private final String tableName;
		private final int adTableId;
		private final int recordId;
		private final String keyColumnName;
		private final List<String> keyColumnNames;

		private DocumentAsZoomSource(final Document document)
		{
			super();
			ctx = document.getCtx();
			evaluationContext = document.asEvaluatee();
			
			final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
			adWindowId = entityDescriptor.getAD_Window_ID();
			tableName = entityDescriptor.getTableName();
			adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
			recordId = document.getDocumentId().toInt();
			keyColumnName = entityDescriptor.getIdFieldName();
			keyColumnNames = keyColumnName == null ? ImmutableList.of() : ImmutableList.of(keyColumnName);
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
		public String getKeyColumnName()
		{
			return keyColumnName;
		}

		@Override
		public List<String> getKeyColumnNames()
		{
			return keyColumnNames;
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
	}
}
