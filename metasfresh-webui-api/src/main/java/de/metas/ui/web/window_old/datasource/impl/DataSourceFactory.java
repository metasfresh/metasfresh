package de.metas.ui.web.window_old.datasource.impl;

import de.metas.ui.web.window_old.datasource.IDataSourceFactory;
import de.metas.ui.web.window_old.datasource.LookupDataSource;
import de.metas.ui.web.window_old.datasource.ModelDataSource;
import de.metas.ui.web.window_old.datasource.sql.SqlLazyLookupDataSource;
import de.metas.ui.web.window_old.datasource.sql.SqlModelDataSource;
import de.metas.ui.web.window_old.descriptor.PropertyDescriptor;
import de.metas.ui.web.window_old.descriptor.PropertyDescriptorDataBindingInfo;
import de.metas.ui.web.window_old.descriptor.SqlDataBindingInfo;
import de.metas.ui.web.window_old.descriptor.SqlLookupDescriptor;

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
		final PropertyDescriptorDataBindingInfo dataBindingInfo = propertyDescriptor.getDataBindingInfo();
		if(dataBindingInfo == null)
		{
			throw new IllegalArgumentException("No data binding info for "+propertyDescriptor);
		}
		
		//
		// SQL
		final SqlDataBindingInfo sqlDataBindingInfo = SqlDataBindingInfo.extractFromOrNull(propertyDescriptor);
		if (sqlDataBindingInfo != null)
		{
			final SqlLookupDescriptor sqlLookupDescriptor = sqlDataBindingInfo.getSqlLookupDescriptor();
			return new SqlLazyLookupDataSource(sqlLookupDescriptor);
		}

		throw new IllegalArgumentException("Cannot create lookup data source for " + propertyDescriptor);
	}
}
