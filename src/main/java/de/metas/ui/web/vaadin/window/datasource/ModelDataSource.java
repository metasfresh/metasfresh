package de.metas.ui.web.vaadin.window.datasource;

import java.util.List;

import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.lang.ITableRecordReference;

import de.metas.ui.web.vaadin.window.datasource.sql.ModelDataSourceQuery;
import de.metas.ui.web.vaadin.window.shared.datatype.LazyPropertyValuesListDTO;
import de.metas.ui.web.vaadin.window.shared.datatype.PropertyValuesDTO;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

public interface ModelDataSource
{
	PropertyValuesDTO getRecord(int index);
	
	int getRecordsCount();

	SaveResult saveRecord(int index, PropertyValuesDTO values);

	LazyPropertyValuesListDTO retrieveRecordsSupplier(ModelDataSourceQuery query);

	PropertyValuesDTO retrieveRecordById(Object recordId);
	
	List<ZoomInfo> retrieveZoomAccrossInfos(final int recordIndex);

	ITableRecordReference getTableRecordReference(int recordIndex);
}
