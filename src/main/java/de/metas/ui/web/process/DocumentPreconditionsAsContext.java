package de.metas.ui.web.process;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.model.Document;

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
	public static final DocumentPreconditionsAsContext of(final Document document)
	{
		return new DocumentPreconditionsAsContext(document);
	}

	private final Document document;
	private final String tableName;

	private DocumentPreconditionsAsContext(final Document document)
	{
		super();
		Check.assumeNotNull(document, "Parameter document is not null");
		this.document = document;
		tableName = document.getEntityDescriptor().getTableName();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(document)
				.toString();
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
	public int getSelectionSize()
	{
		return 1;
	}

}
