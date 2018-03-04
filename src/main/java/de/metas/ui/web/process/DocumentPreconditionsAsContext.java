package de.metas.ui.web.process;

import java.util.List;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.model.Document;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class DocumentPreconditionsAsContext implements WebuiPreconditionsContext
{
	public static final DocumentPreconditionsAsContext of(final Document document, final Set<TableRecordReference> selectedIncludedRecords)
	{
		return new DocumentPreconditionsAsContext(document, selectedIncludedRecords);
	}

	private final Document document;
	private final String tableName;
	private final Set<TableRecordReference> selectedIncludedRecords;

	private DocumentPreconditionsAsContext(@NonNull final Document document, final Set<TableRecordReference> selectedIncludedRecords)
	{
		this.document = document;
		tableName = document.getEntityDescriptor().getTableName();
		
		this.selectedIncludedRecords = selectedIncludedRecords != null ? ImmutableSet.copyOf(selectedIncludedRecords) : ImmutableSet.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(document)
				.add("selectedIncludedRecords", selectedIncludedRecords)
				.toString();
	}
	
	@Override
	public int getAD_Window_ID()
	{
		return document.getDocumentPath().getAD_Window_ID(-1);
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public <T> T getSelectedModel(final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(document, modelClass);
	}
	
	@Override
	public <T> List<T> getSelectedModels(final Class<T> modelClass)
	{
		final T model = getSelectedModel(modelClass);
		return ImmutableList.of(model);
	}
	
	@Override
	public int getSingleSelectedRecordId()
	{
		return document.getDocumentIdAsInt();
	}
	
	@Override
	public int getSelectionSize()
	{
		return 1;
	}
	
	@Override
	public Set<TableRecordReference> getSelectedIncludedRecords()
	{
		return selectedIncludedRecords;
	}
}
