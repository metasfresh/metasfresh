package de.metas.ui.web.window.datasource.impl;

import de.metas.ui.web.window.datasource.IDataSourceFactory;
import de.metas.ui.web.window.datasource.LookupDataSource;
import de.metas.ui.web.window.datasource.ModelDataSource;
import de.metas.ui.web.window.datasource.sql.SqlLazyLookupDataSource;
import de.metas.ui.web.window.datasource.sql.SqlModelDataSource;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.descriptor.SqlLookupDescriptor;

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

public class DataSourceFactory implements IDataSourceFactory
{
	@Override
	public ModelDataSource createDataSource(final PropertyDescriptor rootPropertyDescriptor)
	{
		return new SqlModelDataSource(rootPropertyDescriptor);
	}

	@Override
	public LookupDataSource createLookupDataSource(final PropertyDescriptor propertyDescriptor)
	{
		final SqlLookupDescriptor sqlLookupDescriptor = propertyDescriptor.getSqlLookupDescriptor();
		return new SqlLazyLookupDataSource(sqlLookupDescriptor);
	}
}
