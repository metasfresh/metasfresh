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
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 *	Payroll Concept for HRayroll Module
 *	
 *  @author Oscar Gómez Islas
 *  @version $Id: HRPayrollConcept.java,v 1.0 2005/10/05 ogomezi
 *  
 *  @author Cristina Ghita, www.arhipac.ro
 */
public class MHRPayrollConcept extends X_HR_PayrollConcept
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4335196239535511224L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param HR_Concept_ID id
	 */
	public MHRPayrollConcept (Properties ctx, int HR_Concept_ID, String trxName)
	{
		super (ctx, HR_Concept_ID, trxName);
	}	//	HRConcept

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public MHRPayrollConcept (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	/**
	 * Concept constructor
	 * @param concept
	 */
	public MHRPayrollConcept (MHRConcept concept, int payroll_id, String trxName)
	{
		super(concept.getCtx(), 0, trxName);
		setHR_Payroll_ID(payroll_id);
		setHR_Concept_ID(concept.get_ID());
		setName(concept.getName());
		setSeqNo(concept.getSeqNo());
		//setAD_Rule_Engine_ID(concept.getAD_Rule_Engine_ID());
		//setIsIncluded(true); 
		setIsActive(true);
	}

	/**
	 * 	Get Concept's of Payroll Type
	 * 	@param p HR process
	 * 	@return array of HR concepts
	 */
	public static MHRPayrollConcept[] getPayrollConcepts (MHRProcess p)
	{
		List<MHRPayrollConcept> list = new Query(p.getCtx(), Table_Name, COLUMNNAME_HR_Payroll_ID+"=?", null)
												.setOnlyActiveRecords(true)
												.setParameters(new Object[]{p.getHR_Payroll_ID()})
												.setOrderBy(COLUMNNAME_SeqNo)
												.list(MHRPayrollConcept.class);
		return list.toArray(new MHRPayrollConcept[list.size()]);
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getSeqNo() == 0)
		{
			String sql = "SELECT COALESCE(MAX(SeqNo),0) FROM HR_PayrollConcept WHERE HR_Payroll_ID=?";
			int lastSeqNo = DB.getSQLValueEx(get_TrxName(), sql, getHR_Payroll_ID());
			if (lastSeqNo < 0)
				lastSeqNo = 0;
			setSeqNo(lastSeqNo + 10);
		}

		return true;
	}
	
	
}