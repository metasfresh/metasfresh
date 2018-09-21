package de.metas.document.refid.api.impl;

/*
 * #%L
 * de.metas.document.refid
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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MTable;
import org.compiere.model.Query;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.model.I_C_ReferenceNo_Type_Table;
import de.metas.util.Check;

public class ReferenceNoDAO extends AbstractReferenceNoDAO
{

	@Override
	// @Cached(cacheName = I_C_ReferenceNo_Type.Table_Name + "_ForClient")
	public List<I_C_ReferenceNo_Type> retrieveReferenceNoTypes(@CacheCtx Properties ctx)
	{
		final List<I_C_ReferenceNo_Type> result = new Query(ctx, I_C_ReferenceNo_Type.Table_Name, null, ITrx.TRXNAME_None)
				// .setApplyAccessFilterRW(false)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_ReferenceNo_Type.COLUMNNAME_C_ReferenceNo_Type_ID)
				.list(I_C_ReferenceNo_Type.class);
		return result;
	}

	@Override
	public List<I_C_ReferenceNo_Type_Table> retrieveTableAssignments(I_C_ReferenceNo_Type type)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(type);
		final String trxName = InterfaceWrapperHelper.getTrxName(type);
		final int referenceNoTypeId = type.getC_ReferenceNo_Type_ID();

		return retrieveTableAssignments(ctx, referenceNoTypeId, trxName);
	}

	// @Cached(cacheName = I_C_ReferenceNo_Type_Table.Table_Name + "_ByReferenceType")
	private List<I_C_ReferenceNo_Type_Table> retrieveTableAssignments(@CacheCtx Properties ctx, int referenceNoTypeId, @CacheTrx String trxName)
	{
		final String whereClause = I_C_ReferenceNo_Type_Table.COLUMNNAME_C_ReferenceNo_Type_ID + "=?";
		final List<I_C_ReferenceNo_Type_Table> result = new Query(ctx, I_C_ReferenceNo_Type_Table.Table_Name, whereClause, trxName)
				.setParameters(referenceNoTypeId)
				// .setApplyAccessFilterRW(false)
				// .setOnlyActiveRecords(true) // all shall be retrieve because we use this method to delete existing assignments
				.setOrderBy(I_C_ReferenceNo_Type_Table.COLUMNNAME_AD_Table_ID)
				.list(I_C_ReferenceNo_Type_Table.class);
		return result;
	}

	@Override
	public I_C_ReferenceNo getCreateReferenceNo(final Properties ctx, final I_C_ReferenceNo_Type type, final String referenceNo, final String trxName)
	{
		final String whereClause =
				I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_Type_ID + "=?"
						+ " AND " + I_C_ReferenceNo.COLUMNNAME_ReferenceNo + "=?";

		I_C_ReferenceNo reference = new Query(ctx, I_C_ReferenceNo.Table_Name, whereClause, trxName)
				.setParameters(type.getC_ReferenceNo_Type_ID(), referenceNo)
				.setApplyAccessFilterRW(true)
				.firstOnly(I_C_ReferenceNo.class); // there is a UC on C_ReferenceNo_Type_ID and ReferenceNo

		if (reference == null)
		{
			reference = InterfaceWrapperHelper.create(ctx, I_C_ReferenceNo.class, trxName);
			reference.setC_ReferenceNo_Type(type);
			reference.setReferenceNo(referenceNo);
		}

		reference.setIsActive(true);

		// Don't save because we let this pleasure to getCreateReferenceNoDoc method.
		// We do this for optimization: in this way getCreateReferenceNoDoc will know that is a new reference and don't need to search for assignments
		// InterfaceWrapperHelper.save(reference);

		return reference;
	}

	@Override
	public I_C_ReferenceNo_Doc getCreateReferenceNoDoc(final I_C_ReferenceNo referenceNo, final int tableId, final int recordId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(referenceNo);
		final String trxName = InterfaceWrapperHelper.getTrxName(referenceNo);

		boolean isNewReference = false;
		if (referenceNo.getC_ReferenceNo_ID() <= 0)
		{
			InterfaceWrapperHelper.save(referenceNo);
			isNewReference = true;
		}

		I_C_ReferenceNo_Doc assignment = null;

		//
		// Search for an already existing assignment, only if the reference was not new
		if (!isNewReference)
		{
			final String whereClause = I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID + "=?"
					+ " AND " + I_C_ReferenceNo_Doc.COLUMNNAME_AD_Table_ID + "=?"
					+ " AND " + I_C_ReferenceNo_Doc.COLUMNNAME_Record_ID + "=?";

			assignment = new Query(ctx, I_C_ReferenceNo_Doc.Table_Name, whereClause, trxName)
					.setParameters(referenceNo.getC_ReferenceNo_ID(), tableId, recordId)
					.firstOnly(I_C_ReferenceNo_Doc.class);
		}

		//
		// Creating new referenceNo assignment
		if (assignment == null)
		{
			assignment = InterfaceWrapperHelper.create(ctx, I_C_ReferenceNo_Doc.class, trxName);
			assignment.setC_ReferenceNo(referenceNo);
			assignment.setAD_Table_ID(tableId);
			assignment.setRecord_ID(recordId);
		}

		assignment.setIsActive(true);
		InterfaceWrapperHelper.save(assignment);

		return assignment;
	}

	@Override
	public List<I_C_ReferenceNo_Doc> retrieveDocAssignments(final Properties ctx, final int referenceNoTypeId, final int tableId, final int recordId, final String trxName)
	{
		final List<Object> params = new ArrayList<Object>();
		final StringBuilder whereClause = new StringBuilder();

		whereClause.append(I_C_ReferenceNo_Doc.COLUMNNAME_AD_Table_ID + "=?");
		params.add(tableId);

		whereClause.append(" AND " + I_C_ReferenceNo_Doc.COLUMNNAME_Record_ID + "=?");
		params.add(recordId);

		if (referenceNoTypeId > 0)
		{
			whereClause.append(" AND " + I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID + " IN ("
					+ "		SELECT " + I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_ID
					+ "		FROM " + I_C_ReferenceNo.Table_Name
					+ "		WHERE " + I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_Type_ID + "=?"
					+ ")");
			params.add(referenceNoTypeId);
		}

		final List<I_C_ReferenceNo_Doc> result = new Query(ctx, I_C_ReferenceNo_Doc.Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.list(I_C_ReferenceNo_Doc.class);

		return result;
	}

	@Override
	public List<I_C_ReferenceNo> retrieveReferenceNos(final Object model, final I_C_ReferenceNo_Type type)
	{
		Check.assumeNotNull(model, "Param 'model' not null");
		Check.assumeNotNull(type, "Param 'type' not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		final String whereClause = I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_Type_ID + "=? "
				+ " AND EXISTS (" // there is at least one C_ReferenceNo_Doc referencing both 'model' and the C_ReferenceNo we search for 
				+ "   select 1 "
				+ "   from " + I_C_ReferenceNo_Doc.Table_Name + " rd "
				+ "   where rd." + I_C_ReferenceNo_Doc.COLUMNNAME_AD_Table_ID + "=?"
				+ "     and rd." + I_C_ReferenceNo_Doc.COLUMNNAME_Record_ID + "=?"
				+ "     and rd." + I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID + "=" + I_C_ReferenceNo.Table_Name + "." + I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_ID
				+ ")";

		return new Query(ctx, I_C_ReferenceNo.Table_Name, whereClause, trxName)
				.setParameters(
						type.getC_ReferenceNo_Type_ID(),
						MTable.getTable_ID(InterfaceWrapperHelper.getModelTableName(model)),
						InterfaceWrapperHelper.getId(model))
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_ID)
				.list(I_C_ReferenceNo.class);
	}

	@Override
	protected List<I_C_ReferenceNo_Doc> retrieveRefNoDocByRefNoAndTableName(final I_C_ReferenceNo referenceNo, final String tableName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(referenceNo);
		final String trxName = InterfaceWrapperHelper.getTrxName(referenceNo);

		final String whereClause =
				I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID + "=? AND "
						+ I_C_ReferenceNo_Doc.COLUMNNAME_AD_Table_ID + "=?";

		final List<I_C_ReferenceNo_Doc> refNoDocs = new Query(ctx, I_C_ReferenceNo_Doc.Table_Name, whereClause, trxName)
				.setParameters(referenceNo.getC_ReferenceNo_ID(), MTable.getTable_ID(tableName))
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_Doc_ID)
				.list(I_C_ReferenceNo_Doc.class);

		return refNoDocs;
	}

	@Override
	public List<I_C_ReferenceNo_Doc> retrieveAllDocAssignments(final I_C_ReferenceNo referenceNo)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(referenceNo);
		final String trxName = InterfaceWrapperHelper.getTrxName(referenceNo);

		final String whereClause = I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID + "=?";

		final List<I_C_ReferenceNo_Doc> result = new Query(ctx, I_C_ReferenceNo_Doc.Table_Name, whereClause, trxName)
				.setParameters(referenceNo.getC_ReferenceNo_ID())
				.list(I_C_ReferenceNo_Doc.class);

		return result;
	}
}
