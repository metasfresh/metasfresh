package org.adempiere.misc.service.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.IADProcessDAO;
import org.adempiere.db.IDBService;
import org.adempiere.db.IDatabaseBL;
import org.adempiere.misc.service.IProcessPA;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.Query;
import org.compiere.model.X_AD_PInstance;
import org.compiere.process.SvrProcess;
import org.compiere.util.KeyNamePair;
import org.compiere.util.ValueNamePair;

import de.metas.ordercandidate.model.I_C_OLCandProcessor;

/**
 * 
 * @deprecated Please use {@link IADProcessDAO}
 */
@Deprecated
public class ProcessPA implements IProcessPA
{

	private final String SQL_INSTANCES = //
			" SELECT pi.* " //
					+ " FROM AD_PInstance pi " //
					+ "    LEFT JOIN AD_Process p ON p.AD_Process_ID=pi.AD_Process_ID " //
					+ " WHERE pi.IsProcessing='Y' AND p.classname=? " //
					+ " ORDER BY pi.Created ";

	private final String SELECT_PROCESS_ID = //
	" SELECT AD_Process_ID " //
			+ " FROM  " + I_AD_Process.Table_Name //
			+ " WHERE " + I_AD_Process.COLUMNNAME_Classname + "=?";

	@Override
	public List<? extends I_AD_PInstance> retrieveRunningInstances(
			final Class<? extends SvrProcess> clazz,
			final String trxName)
	{

		final Object[] params = new Object[] { clazz.getName() };

		final IDatabaseBL databaseBL = Services.get(IDatabaseBL.class);

		return databaseBL.retrieveList(SQL_INSTANCES, params, X_AD_PInstance.class, trxName);
	}

	/**
	 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
	 */
	@Override
	public int retrieveProcessId(final Class<? extends SvrProcess> clazz, final String trxName)
	{

		final IDBService db = Services.get(IDBService.class);
		return db.getSQLValueEx(trxName, SELECT_PROCESS_ID, clazz.getName());
	}

	@Override
	public I_AD_Process_Para retrieveProcessPara(
			final Properties ctx,
			final int adProcessId,
			final String trxName)
	{
		final String wc =
				I_AD_Process_Para.COLUMNNAME_ColumnName + "='" + I_C_OLCandProcessor.COLUMNNAME_C_OLCandProcessor_ID + "'"
						+ " AND " + I_AD_Process_Para.COLUMNNAME_AD_Process_ID + "=?";

		return new Query(ctx, I_AD_Process_Para.Table_Name, wc, trxName)
				.setParameters(adProcessId)
				.setOrderBy(I_AD_Process_Para.COLUMNNAME_AD_Process_Para_ID)
				.first();
	}

	@Override
	public I_AD_Process retrieveProcess(
			final Properties ctx,
			final String value,
			final String trxName)
	{
		final String wc = I_AD_Process.COLUMNNAME_Value + "=?";

		return new Query(ctx, I_AD_Process.Table_Name, wc, trxName)
				.setParameters(value)
				.setOnlyActiveRecords(true)
				.setApplyAccessFilter(true)
				.firstOnly(I_AD_Process.class);
	}

	/**
	 * Retrieves the AD_Process referencing the given AD_Form_ID. Note: this method assumes that there is only one such
	 * process!
	 */
	@Override
	public I_AD_Process retrieveProcessByForm(final Properties ctx, final int AD_Form_ID, final String trxName)
	{
		return new Query(ctx, I_AD_Process.Table_Name, I_AD_Process.COLUMNNAME_AD_Form_ID+"=?", trxName)
			.setOnlyActiveRecords(true)
			.setParameters(AD_Form_ID)
			.firstOnly(I_AD_Process.class);
	}
	
	@Override
	public boolean setPInstanceParaValue(final I_AD_PInstance_Para pipa, final Object value)
	{
		if (value instanceof String)
		{
			pipa.setP_String(value.toString());
		}
		else if (value instanceof BigDecimal)
		{
			pipa.setP_Number((BigDecimal)value);
		}
		else if (value instanceof Number)
		{
			pipa.setP_Number(new BigDecimal(value.toString()));
		}
		else if (value instanceof java.sql.Timestamp)
		{
			pipa.setP_Date((Timestamp)value);
		}
		else if (value instanceof java.util.Date)
		{
			final Timestamp date = new Timestamp(((java.util.Date)value).getTime());
			pipa.setP_Date(date);
		}
		else if (value instanceof Boolean)
		{
			final boolean b = (Boolean)value;
			pipa.setP_String(b ? "Y" : "N");
		}
		else if (value instanceof KeyNamePair)
		{
			final KeyNamePair knp = (KeyNamePair)value;
			pipa.setP_Number(new BigDecimal(knp.getKey()));
			pipa.setInfo(knp.getName());
		}
		else if (value instanceof ValueNamePair)
		{
			final ValueNamePair vnp = (ValueNamePair)value;
			pipa.setP_String(vnp.getValue());
			pipa.setInfo(vnp.getName());
		}
		else
		{
			return false;
		}

		return true;
	}

}
