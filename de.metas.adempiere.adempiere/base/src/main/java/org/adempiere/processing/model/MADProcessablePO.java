package org.adempiere.processing.model;

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


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.processing.exception.ProcessingException;
import org.adempiere.processing.interfaces.IProcessablePO;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.TrxRunnable;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Services;

public class MADProcessablePO extends X_AD_ProcessablePO implements IProcessablePO
{

	private static final Logger logger = LogManager.getLogger(MADProcessablePO.class);

	private ModelValidator modelValidator;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3710171397490757701L;

	public MADProcessablePO(final Properties ctx, final int AD_ProcessablePO_ID, final String trxName)
	{
		super(ctx, AD_ProcessablePO_ID, trxName);
	}

	public MADProcessablePO(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	private MADProcessablePO(final PO poToProcess, final ModelValidator validator, final String trxName)
	{
		super(poToProcess.getCtx(), 0, trxName);

		setClientOrg(poToProcess);
		setAD_Table_ID(poToProcess.get_Table_ID());
		setRecord_ID(poToProcess.get_ID());
		setTrxName(poToProcess.get_TrxName());
		setModelValidationClass(validator.getClass().getName());
		modelValidator = validator;
	}

	public static MADProcessablePO createOrRetrieveFor(final PO po, final ModelValidator validator)
	{
		final MADProcessablePO existing = retrieveFor(po, validator);
		if (existing != null)
		{
			return existing;
		}

		final MADProcessablePO[] newOne = new MADProcessablePO[] { null };

		try
		{
			// attempting to create the new MADProcessablePO in a local trx, because we want to commit it right after
			// creation.
			Services.get(ITrxManager.class).runInNewTrx((TrxRunnable)trxName -> {
				newOne[0] = new MADProcessablePO(po, validator, trxName);
				newOne[0].saveEx();
			});
		}
		catch (RuntimeException e)
		{
			logger.info("Caught " + e + " when trying to create new MADProcessablePO. Assuming that is has already been created concurrently and trying to load it again.");
			
			newOne[0] = retrieveFor(po, validator);
			assert newOne[0] != null : "po=" + po + "; validator-class=" + validator.getClass().getName();
		}
		// setting the po's trxName to the (new) MADProcessablePO, because further DB-actions are supposed to take place
		// in the trx that 'po' has.
		newOne[0].set_TrxName(po.get_TrxName());
		return newOne[0];
	}

	public static MADProcessablePO retrieveFor(final PO po, final ModelValidator validator)
	{
		final String wc =
				COLUMNNAME_AD_Table_ID + "=?"
						+ " AND " + COLUMNNAME_Record_ID + "=?"
						+ " AND " + COLUMNNAME_ModelValidationClass + "=?"
						+ " AND " + I_AD_ProcessablePO.COLUMNNAME_Processed + "<>?";
		final String orderBy = I_AD_ProcessablePO.COLUMNNAME_AD_ProcessablePO_ID;

		final MADProcessablePO result =
				new Query(po.getCtx(), Table_Name, wc, po.get_TrxName())
						.setOnlyActiveRecords(true)
						.setOrderBy(orderBy)
						.setParameters(po.get_Table_ID(), po.get_ID(), validator.getClass().getName(), true)
						.first();

		logger.info("Retrieved " + result);
		return result;
	}

	public static void deleteProcessedFor(final PO po, final ModelValidator validator)
	{
		final String sql =
				"DELETE FROM " + Table_Name
						+ " WHERE " + COLUMNNAME_AD_Table_ID + "=? AND " + COLUMNNAME_Record_ID + "=? AND "
						+ COLUMNNAME_ModelValidationClass + "=? AND "
						+ I_AD_ProcessablePO.COLUMNNAME_IsError + "='N' AND "
						+ I_AD_ProcessablePO.COLUMNNAME_Processed + "='Y' ";

		final int deleteCnt = DB.executeUpdateEx(sql, po.get_TrxName());
		logger.info("Deleted " + deleteCnt + " records");
	}

	public static void deleteAllFor(final PO po)
	{
		final String sql =
				"DELETE FROM " + Table_Name
						+ " WHERE " + COLUMNNAME_AD_Table_ID + "=? AND " + COLUMNNAME_Record_ID + "=? AND "
						+ I_AD_ProcessablePO.COLUMNNAME_IsError + "='N'";

		final int deleteCnt = DB.executeUpdateEx(sql, po.get_TrxName());
		logger.info("Deleted " + deleteCnt + " records");
	}

	public static MADProcessablePO retrieveOldest(final Properties ctx, final String trxName)
	{
		return retrieveOldest(ctx,
				-1, // adTableId,
				-1, // minAdProcessablePoId,
				trxName);
	}

	public static MADProcessablePO retrieveOldest(final Properties ctx, final int adTableId, int minAdProcessablePoId, final String trxName)
	{
		final StringBuffer whereClause = new StringBuffer();
		final List<Object> params = new ArrayList<>();
		
		whereClause.append(I_AD_ProcessablePO.COLUMNNAME_Processed).append("=?");
		params.add(false);
		
		whereClause.append(" AND ").append(I_AD_ProcessablePO.COLUMNNAME_IsError).append("=?");
		params.add(false);
		
		if (adTableId > 0)
		{
			whereClause.append(" AND ").append(I_AD_ProcessablePO.COLUMNNAME_AD_Table_ID).append("=?");
			params.add(adTableId);
		}

		if (minAdProcessablePoId > 0)
		{
			whereClause.append(" AND ").append(I_AD_ProcessablePO.COLUMNNAME_AD_ProcessablePO_ID).append(">=?");
			params.add(minAdProcessablePoId);
		}

		return retrieve(ctx, whereClause.toString(), params.toArray(), trxName);
	}

	private static MADProcessablePO retrieve(final Properties ctx, final String whereClause, final Object[] params, final String trxName)
	{
		final String orderBy = I_AD_ProcessablePO.COLUMNNAME_AD_ProcessablePO_ID;

		final MADProcessablePO result =
				new Query(ctx, Table_Name, whereClause, trxName)
						.setOnlyActiveRecords(true)
						.setOrderBy(orderBy)
						.setParameters(params)
						.first();

		logger.info("Retrieved " + result);
		return result;
	}

	/**
	 * 
	 * @param throwEx
	 *            if true and the referenced record doesn't exist anymore, a {@link ProcessingException} is thrown. If
	 *            false, just <code>null</code> is returned.
	 * @return
	 */
	public PO retrievePO(final boolean throwEx)
	{
		final int tableId = getAD_Table_ID();
		final int recordId = getRecord_ID();
		
		final MTable table = MTable.get(getCtx(), tableId);
		final String[] keyColumns = table.getKeyColumns();
		if (keyColumns == null || keyColumns.length == 0)
		{
			throw new ProcessingException("Table "+table+" has no key columns defined", null, null);
		}
		if (keyColumns.length > 1)
		{
			throw new ProcessingException("Table "+table+" has more then one key column defined", null, null);
		}
		final String keyColumn = keyColumns[0];
		
		PO po = new Query(getCtx(), table.getTableName(), keyColumn+"=?", get_TrxName())
		.setParameters(recordId)
		.firstOnly();

		if (po == null || po.get_ID() != recordId)
		{
			if (throwEx)
			{
				// TODO -> AD_Message
				throw new ProcessingException("Der referenzierte Datensatz existiert nicht mehr", null, null);
			}
			else
			{
				return null;
			}
		}
		return po;
	}

	public ModelValidator getModelValidator()
	{
		if (modelValidator == null)
		{
			modelValidator = Util.getInstance(ModelValidator.class, getModelValidationClass());
		}
		return modelValidator;
	}

}
