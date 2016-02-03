package org.adempiere.acct.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.List;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.acct.api.IFactAcctListenersService;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_Fact_Acct;

public class FactAcctDAO implements IFactAcctDAO
{
	@Override
	public int deleteForDocument(final Object document)
	{
		final int countDeleted = retrieveQueryForDocument(document)
				.create()
				.deleteDirectly();
		
		Services.get(IFactAcctListenersService.class).fireAfterUnpost(document);

		return countDeleted;
	}

	@Override
	public List<I_Fact_Acct> retrieveForDocument(final Object document)
	{
		return retrieveQueryForDocument(document)
				.create()
				.list();
	}

	@Override
	public IQueryBuilder<I_Fact_Acct> retrieveQueryForDocument(final Object document)
	{
		Check.assumeNotNull(document, "document not null");
		final int adTableId = InterfaceWrapperHelper.getModelTableId(document);
		final int recordId = InterfaceWrapperHelper.getId(document);

		final IQueryBuilder<I_Fact_Acct> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class, document)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Record_ID, recordId);

		// make sure we have a predictable order
		queryBuilder.orderBy()
				.addColumn(I_Fact_Acct.COLUMN_Fact_Acct_ID);

		return queryBuilder;
	}

	@Override
	public List<I_Fact_Acct> retrieveForDocumentLine(final String tableName, final int recordId, final Object documentLine)
	{
		Check.assumeNotNull(documentLine, "documentLine not null");
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final int lineId = InterfaceWrapperHelper.getId(documentLine);

		final IQueryBuilder<I_Fact_Acct> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class, documentLine)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Record_ID, recordId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Line_ID, lineId);

		// make sure we have a predictable order
		queryBuilder.orderBy()
				.addColumn(I_Fact_Acct.COLUMN_Fact_Acct_ID);

		return queryBuilder.create().list();
	}

	@Override
	public void updateDocStatusForDocument(final Object document, final String docStatus)
	{
		retrieveQueryForDocument(document)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_Fact_Acct.COLUMNNAME_DocStatus, docStatus)
				.execute();
	}
}
