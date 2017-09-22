package org.adempiere.ad.table.process;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.impl.CopyColumnsProducer;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Table;

import de.metas.process.JavaProcess;
import de.metas.process.Param;

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

/**
 * This process is used for adding the document specific details to a given table.
 * A document table must have some specific columns that are copied from the table AD_Table_Document_Template.
 * AD_Table_Document_Template is only defined in the Application dictionary, it is not a real table in the database (at least yet).
 * If the definition of a document table is to change (it needs extra columns), change AD_Table_Document_Template directly and run this process again for each document table that is to be extended.
 * Except from the extra columns, the document table must also have its own AD_Workflow which is specified in an AD_Process, linked with DocAction and Processing columns.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AD_Table_ConvertToDocument extends JavaProcess
{
	private static transient IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	@Param(parameterName = I_AD_Table.COLUMNNAME_AD_Table_ID, mandatory = true)
	private I_AD_Table table;

	@Param(parameterName = I_AD_Table.COLUMNNAME_EntityType, mandatory = false)
	private String entityType;

	@Override
	protected String doIt() throws Exception
	{
		final CopyColumnsProducer producer = new CopyColumnsProducer(this);
		producer.setLogger(this);
		producer.setTargetTable(table);
		producer.setSourceColumns(tableDAO.retrieveColumnsForTable(table));
		producer.create();
		
		return "@Created@ #" + producer.getCountCreated();
	}
}
