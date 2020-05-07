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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.acct.api.IFactAcctListenersService;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_Fact_Acct;

import de.metas.document.engine.IDocument;
import lombok.NonNull;

public class FactAcctDAO implements IFactAcctDAO
{
	@Override
	public int deleteForDocument(final IDocument document)
	{
		final int countDeleted = retrieveQueryForDocument(document)
				.create()
				.deleteDirectly();

		Services.get(IFactAcctListenersService.class).fireAfterUnpost(document);

		return countDeleted;
	}
	
	@Override
	public int deleteForDocumentModel(@NonNull final Object documentObj)
	{
		Properties ctx = InterfaceWrapperHelper.getCtx(documentObj);
		int adTableId = InterfaceWrapperHelper.getModelTableId(documentObj);
		int recordId = InterfaceWrapperHelper.getId(documentObj);
		String trxName = InterfaceWrapperHelper.getTrxName(documentObj);
		final int countDeleted = retrieveQueryForDocument(ctx, adTableId, recordId, trxName)
				.create()
				.deleteDirectly();

		Services.get(IFactAcctListenersService.class).fireAfterUnpost(documentObj);

		return countDeleted;
	}

	@Override
	public IQueryBuilder<I_Fact_Acct> retrieveQueryForDocument(@NonNull final IDocument document)
	{
		final Properties ctx = document.getCtx();
		final String trxName = document.get_TrxName();
		final int adTableId = document.get_Table_ID();
		final int recordId = document.get_ID();
		return retrieveQueryForDocument(ctx, adTableId, recordId, trxName);
	}

	private IQueryBuilder<I_Fact_Acct> retrieveQueryForDocument(final Properties ctx, final int adTableId, final int recordId, final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Record_ID, recordId)
				.orderBy()
				.addColumn(I_Fact_Acct.COLUMN_Fact_Acct_ID) // make sure we have a predictable order
				.endOrderBy();
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
	public void updateDocStatusForDocument(final IDocument document)
	{
		final String docStatus = document.getDocStatus();
		retrieveQueryForDocument(document)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_Fact_Acct.COLUMNNAME_DocStatus, docStatus)
				.execute();
	}

	@Override
	public int updateActivityForDocumentLine(final Properties ctx, final int adTableId, final int recordId, final int lineId, final int activityId)
	{
		// Make sure we are updating the Fact_Acct records in a transaction
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final int countUpdated = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Record_ID, recordId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Line_ID, lineId)
				.addNotEqualsFilter(I_Fact_Acct.COLUMN_C_Activity_ID, activityId)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_Fact_Acct.COLUMNNAME_C_Activity_ID, activityId)
				.execute();

		return countUpdated;
	}
}
