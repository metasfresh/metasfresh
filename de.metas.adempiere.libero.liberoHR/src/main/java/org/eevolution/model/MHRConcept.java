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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 *	Payroll Concept for HRayroll Module
 *	
 *  @author Oscar Gómez Islas
 *  @version $Id: HRPayroll.java,v 1.0 2005/10/05 ogomezi
 *  
 *  @author Cristina Ghita, www.arhipac.ro
 */
public class MHRConcept extends X_HR_Concept
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8736925494645172953L;
	
	/** Cache */
	private static CCache<Integer, MHRConcept> s_cache = new CCache<Integer, MHRConcept>(Table_Name, 100);
	/** Cache by Value */
	private static CCache<String, MHRConcept> s_cacheValue = new CCache<String, MHRConcept>(Table_Name+"_Value", 100);
	
	public static MHRConcept get(Properties ctx, int HR_Concept_ID)
	{
		if (HR_Concept_ID <= 0)
			return null;
		//
		MHRConcept concept = s_cache.get(HR_Concept_ID);
		if (concept != null)
			return concept;
		//
		concept = new MHRConcept(ctx, HR_Concept_ID, null);
		if (concept.get_ID() == HR_Concept_ID)
		{
			s_cache.put(HR_Concept_ID, concept);
		}
		else
		{
			concept = null;	
		}
		return concept; 
	}

	/**
	 * Get Concept by Value
	 * @param ctx
	 * @param value
	 * @return
	 */
	public static MHRConcept forValue(Properties ctx, String value)
	{
		if (Util.isEmpty(value, true))
		{
			return null;
		}
		
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final String key = AD_Client_ID+"#"+value;
		MHRConcept concept = s_cacheValue.get(key);
		if (concept != null)
		{
			return concept;
		}
		
		final String whereClause = COLUMNNAME_Value+"=? AND AD_Client_ID IN (?,?)"; 
		concept = new Query(ctx, Table_Name, whereClause, null)
							.setParameters(new Object[]{value, 0, AD_Client_ID})
							.setOnlyActiveRecords(true)
							.setOrderBy("AD_Client_ID DESC")
							.first();
		if (concept != null)
		{
			s_cacheValue.put(key, concept);
			s_cache.put(concept.get_ID(), concept);
		}
		return concept;
	}

	/**
	 * 	Get Employee's of Payroll Type
	 *  @param payroll_id Payroll ID
	 *  @param department_id Department ID
	 *  @param employee_id Employee_ID
	 * 	@param sqlwhere Clause SQLWhere
	 * 	@return lines
	 */
	public static MHRConcept[] getConcepts (int payroll_id, int department_id, int employee_id, String sqlWhere)
	{
		Properties ctx = Env.getCtx();
		List<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		
		whereClause.append("AD_Client_ID in (?,?)");   
		params.add(0);
		params.add(Env.getAD_Client_ID(Env.getCtx()));
		
		whereClause.append(" AND (" + COLUMNNAME_HR_Payroll_ID + " =? OR "
				+COLUMNNAME_HR_Payroll_ID +" IS NULL)");
		params.add(payroll_id);
		
		if (department_id != 0 )
		{
			whereClause.append(" AND HR_Concept.HR_Department_ID=?");
			params.add(department_id);
		}
		
		if (!Util.isEmpty(sqlWhere))
		{
			whereClause.append(sqlWhere);
		}
		
		List<MHRConcept> list = new Query(ctx, Table_Name, whereClause.toString(), null)
										.setParameters(params)
										.setOnlyActiveRecords(true)
										.setOrderBy("COALESCE("+COLUMNNAME_SeqNo + ",999999999999) DESC, " + COLUMNNAME_Value)
										.list();
		return list.toArray(new MHRConcept[list.size()]);
	}	//	getConcept	

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param HR_Concept_ID
	 *	@param trxName
	 */
	public MHRConcept (Properties ctx, int HR_Concept_ID, String trxName)
	{
		super (ctx, HR_Concept_ID, trxName);
		if (HR_Concept_ID == 0)
		{
			setValue("");
			setName("");
			setDescription("");
			setIsEmployee(false);
			setIsPrinted(false);
			setHR_Payroll_ID(0);
			setHR_Job_ID(0);
			setHR_Department_ID(0);
		}		
	}	//	HRConcept

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName
	 */
	public MHRConcept (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public int getConceptAccountCR() 
	{
		String sql = " HR_Expense_Acct FROM HR_Concept c " +
		" INNER JOIN HR_Concept_Acct ca ON (c.HR_Concept_ID=ca.HR_Concept_ID)" +
		" WHERE c.HR_Concept_ID " + getHR_Concept_ID();
		int result = DB.getSQLValue("ConceptCR", sql);
		if (result > 0)
			return result;
		return 0;
	}

	public int getConceptAccountDR() 
	{
		String sql = " HR_Revenue_Acct FROM HR_Concept c " +
		" INNER JOIN HR_Concept_Acct ca ON (c.HR_Concept_ID=ca.HR_Concept_ID)" +
		" WHERE c.HR_Concept_ID " + getHR_Concept_ID();
		int result = DB.getSQLValue("ConceptCR", sql);
		if (result > 0)
			return result;
		return 0;
	}

}	//	HRConcept