/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.eevolution.model;

import de.metas.cache.CCache;
import de.metas.util.Check;
import org.compiere.model.Query;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Payroll for HRayroll Module
 *
 * @author Oscar Gómez Islas
 * @author Cristina Ghita, www.arhipac.ro
 * @version $Id: HRPayroll.java,v 1.0 2005/10/05 ogomezi
 */
public class MHRPayroll extends X_HR_Payroll
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1407037967021019961L;
	/**
	 * Cache
	 */
	private static final CCache<Integer, MHRPayroll> s_cache = new CCache<>(Table_Name, 10);
	/**
	 * Cache
	 */
	private static final CCache<String, MHRPayroll> s_cacheValue = new CCache<>(Table_Name + "_Value", 10);

	@Nullable
	public static MHRPayroll forValue(final Properties ctx, final String value)
	{
		if (Check.isEmpty(value, true))
		{
			return null;
		}

		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final String key = AD_Client_ID + "#" + value;
		MHRPayroll payroll = s_cacheValue.get(key);
		if (payroll != null)
		{
			return payroll;
		}

		final String whereClause = COLUMNNAME_Value + "=? AND AD_Client_ID IN (?,?)";
		payroll = new Query(ctx, Table_Name, whereClause, null)
				.setParameters(value, 0, AD_Client_ID)
				.setOnlyActiveRecords(true)
				.setOrderBy("AD_Client_ID DESC")
				.first();
		if (payroll != null)
		{
			s_cacheValue.put(key, payroll);
			s_cache.put(payroll.get_ID(), payroll);
		}
		return payroll;
	}

	@Nullable
	public static MHRPayroll get(final Properties ctx, final int HR_Payroll_ID)
	{
		if (HR_Payroll_ID <= 0)
			return null;
		//
		MHRPayroll payroll = s_cache.get(HR_Payroll_ID);
		if (payroll != null)
			return payroll;
		//
		payroll = new MHRPayroll(ctx, HR_Payroll_ID, null);
		if (payroll.get_ID() == HR_Payroll_ID)
		{
			s_cache.put(HR_Payroll_ID, payroll);
		}
		else
		{
			payroll = null;
		}
		return payroll;
	}

	/**
	 * Standard Constructor
	 *
	 * @param ctx           context
	 * @param HR_Payroll_ID id
	 */
	public MHRPayroll(final Properties ctx, final int HR_Payroll_ID, final String trxName)
	{
		super(ctx, HR_Payroll_ID, trxName);
		if (HR_Payroll_ID == 0)
		{
			setProcessing(false);    // N
		}
	}    //	HRPayroll

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs  result set
	 */
	public MHRPayroll(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}
}    //	MPayroll
