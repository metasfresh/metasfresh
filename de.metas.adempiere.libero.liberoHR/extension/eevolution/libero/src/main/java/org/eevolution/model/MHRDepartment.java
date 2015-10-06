/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.Env;

/**
 * @author Cristina Ghita, www.arhipac.ro
 *
 */
public class MHRDepartment extends X_HR_Department
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 83878114891519775L;

	public static List<MHRDepartment> getAll(Properties ctx)
	{
		List<MHRDepartment> list = new Query(Env.getCtx(), X_HR_Department.Table_Name, "AD_Client_ID=?", null)
											.setParameters(new Object[] {Env.getAD_Client_ID(ctx)})
											.setOrderBy(COLUMNNAME_Name)
											.list();
		for (MHRDepartment dep : list)
		{
			s_cache.put(dep.get_ID(), dep);
		}
		return list;
	}
	
	public static MHRDepartment get(Properties ctx, int HR_Department_ID)
	{
		if (HR_Department_ID <= 0)
		{
			return null;
		}
		
		if (s_cache.size() == 0)
		{
			getAll(ctx);
		}
		MHRDepartment dep = s_cache.get(HR_Department_ID);
		if (dep != null)
		{
			return dep;
		}
		dep = new MHRDepartment(ctx, HR_Department_ID, null);
		if (dep.get_ID() == HR_Department_ID)
		{
			s_cache.put(HR_Department_ID, dep);
		}
		return dep;
	}
	
	private static CCache<Integer, MHRDepartment> s_cache = new CCache<Integer, MHRDepartment>(Table_Name, 50, 0);
	
	/**
	 * @param ctx
	 * @param HR_Department_ID
	 * @param trxName
	 */
	public MHRDepartment(Properties ctx, int HR_Department_ID, String trxName)
	{
		super(ctx, HR_Department_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRDepartment(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
}
