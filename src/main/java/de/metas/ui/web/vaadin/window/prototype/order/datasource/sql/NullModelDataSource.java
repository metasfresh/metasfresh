package de.metas.ui.web.vaadin.window.prototype.order.datasource.sql;

import java.util.List;

import com.google.common.base.Supplier;

import de.metas.ui.web.vaadin.window.prototype.order.datasource.ModelDataSource;
import de.metas.ui.web.vaadin.window.prototype.order.datasource.SaveResult;
import de.metas.ui.web.vaadin.window.prototype.order.model.PropertyValuesDTO;

/*
 * #%L
 * metasfresh-webui
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

public final class NullModelDataSource implements ModelDataSource
{
	public static final transient NullModelDataSource instance = new NullModelDataSource();
	
	private NullModelDataSource()
	{
		super();
	}

	@Override
	public PropertyValuesDTO getRecord(int index)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRecordsCount()
	{
		return 0;
	}

	@Override
	public SaveResult saveRecord(int index, PropertyValuesDTO values)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Supplier<List<PropertyValuesDTO>> retrieveRecordsSupplier(final ModelDataSourceQuery query)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PropertyValuesDTO retrieveRecordById(Object recordId)
	{
		throw new UnsupportedOperationException();
	}
}
