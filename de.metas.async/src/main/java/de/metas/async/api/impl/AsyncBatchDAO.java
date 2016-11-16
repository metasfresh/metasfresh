/**
 * 
 */
package de.metas.async.api.impl;

import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * de.metas.async
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


import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.Query;

import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;

/**
 * @author cg
 *
 */
public class AsyncBatchDAO implements IAsyncBatchDAO
{
	@Override
	public I_C_Async_Batch_Type retrieveAsyncBatchType(final Properties ctx, final String internalName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Async_Batch_Type.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Async_Batch_Type.COLUMN_InternalName, internalName)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyNotNull(I_C_Async_Batch_Type.class);
	}

	@Override
	public List<I_C_Queue_WorkPackage> retrieveWorkPackages(final I_C_Async_Batch asyncBatch, final Boolean processed)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(asyncBatch);
		final String trxName = InterfaceWrapperHelper.getTrxName(asyncBatch);

		String whereClause = I_C_Queue_WorkPackage.COLUMNNAME_C_Async_Batch_ID + " = ? ";
		final List<Object> params = new ArrayList<Object>();
		params.add(asyncBatch.getC_Async_Batch_ID());

		if (processed != null)
		{
			whereClause += " AND " + I_C_Queue_WorkPackage.COLUMNNAME_Processed + " = ? ";
			params.add(processed);
		}

		return new Query(ctx, I_C_Queue_WorkPackage.Table_Name, whereClause, trxName)
				.setOnlyActiveRecords(true)
				.setParameters(params)
				.setOrderBy(I_C_Queue_WorkPackage.COLUMNNAME_Updated + " DESC ")
				.list(I_C_Queue_WorkPackage.class);
	}

	@Override
	public List<I_C_Queue_WorkPackage> retrieveWorkPackages(final I_C_Async_Batch asyncBatch)
	{
		return retrieveWorkPackages(asyncBatch, null);
	}

	@Override
	public List<I_C_Queue_WorkPackage_Notified> retrieveWorkPackagesNotified(final I_C_Async_Batch asyncBatch, final boolean notified)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(asyncBatch);
		final String trxName = InterfaceWrapperHelper.getTrxName(asyncBatch);

		final List<Object> params = new ArrayList<Object>();

		final StringBuffer whereClause = new StringBuffer();
		whereClause.append(I_C_Queue_WorkPackage_Notified.COLUMNNAME_C_Async_Batch_ID + " = ? ");
		params.add(asyncBatch.getC_Async_Batch_ID());


		whereClause.append(" AND " + I_C_Queue_WorkPackage_Notified.COLUMNNAME_IsNotified + " = ? ");
		params.add(notified);

		return new Query(ctx, I_C_Queue_WorkPackage_Notified.Table_Name, whereClause.toString(), trxName)
				.setOnlyActiveRecords(true)
				.setParameters(params)
				.setOrderBy(I_C_Queue_WorkPackage_Notified.COLUMNNAME_BachWorkpackageSeqNo)
				.list(I_C_Queue_WorkPackage_Notified.class);
	}

	@Override
	public I_C_Queue_WorkPackage_Notified fetchWorkPackagesNotified(final I_C_Queue_WorkPackage workPackage)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
		final String trxName = InterfaceWrapperHelper.getTrxName(workPackage);

		final List<Object> params = new ArrayList<Object>();

		final StringBuffer whereClause = new StringBuffer();
		whereClause.append(I_C_Queue_WorkPackage_Notified.COLUMNNAME_C_Queue_WorkPackage_ID + " = ? ");
		params.add(workPackage.getC_Queue_WorkPackage_ID());

		return new Query(ctx, I_C_Queue_WorkPackage_Notified.Table_Name, whereClause.toString(), trxName)
				.setOnlyActiveRecords(true)
				.setParameters(params)
				.firstOnly(I_C_Queue_WorkPackage_Notified.class);
	}

}
