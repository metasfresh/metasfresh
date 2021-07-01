package org.adempiere.ad.table.process;

import de.metas.process.ADProcessService;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.impl.CopyColumnsProducer;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Table;

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
	private static final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private static final ADProcessService processBL = SpringContextHolder.instance.getBean(ADProcessService.class);
	private static final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final I_AD_Table documentTable = getRecord(I_AD_Table.class);

		// Get the document table template
		final I_AD_Table documentTemplateTable = tableDAO.retrieveDocumentTableTemplate(documentTable);

		// Create the document specific columns and add them to the document
		CopyColumnsProducer.newInstance()
				.setLogger(this)
				.setTargetTable(documentTable)
				.setSourceColumns(tableDAO.retrieveColumnsForTable(documentTemplateTable))
				.create();

		final I_AD_Process adProcess = trxManager.callInNewTrx(() -> processBL.createAndLinkDocumentSpecificProcess(documentTable));
		addLog("@Created@ @AD_Process_ID@: {}", adProcess);

		return MSG_OK;
	}
}
