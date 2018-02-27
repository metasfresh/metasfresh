package org.adempiere.ad.table.api;

import java.util.List;

import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.util.ISingletonService;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface ITableRecordIdDAO extends ISingletonService
{

	/**
	 * @param zoomSource
	 * @return All the TableRecordId references that can be reached form the table given as tablename
	 */
	List<TableRecordIdDescriptor> retrieveTableRecordIdReferences(final String tableName);

	/**
	 *
	 * @return a map of AD_Table_ID => List-of-AD_Table_ID where the
	 *         key-<code>AD_Table_ID</code>'s DB-records are referenced by
	 *         records from the <code>*Table_ID</code> and
	 *         <code>*Record_ID</code> columns of the tables from
	 *         value-<code>List<AD_Table_ID></code>.
	 */
	List<TableRecordIdDescriptor> retrieveAllTableRecordIdReferences();

}
