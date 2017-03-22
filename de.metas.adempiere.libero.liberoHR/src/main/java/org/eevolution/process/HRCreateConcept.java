/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is Compiere ERP & CRM Smart Business Solution. The Initial
 * Developer of the Original Code is Jorg Janke. Portions created by Jorg Janke
 * are Copyright (C) 1999-2005 Jorg Janke.
 * All parts are Copyright (C) 1999-2005 ComPiere, Inc.  All Rights Reserved.
 * Contributor(s): ______________________________________.
 *****************************************************************************/
//package org.eevolution.process;
package org.eevolution.process;

import org.compiere.model.Query;
import org.eevolution.model.MHRConcept;
import org.eevolution.model.MHRPayrollConcept;

import de.metas.process.JavaProcess;

/**
 *	Create Concept of current Payroll
 *	
 *  @author Oscar Gómez Islas
 *  @version $Id: HRCreateConcept.java,v 1.0 2005/10/24 04:58:38 ogomezi Exp $
 *  
 *  @author Cristina Ghita, www.arhipac.ro
 */
public class HRCreateConcept extends JavaProcess
{
	private int	p_HR_Payroll_ID = 0;
	
	/**
	 * 	Prepare
	 */
	protected void prepare ()
	{
		p_HR_Payroll_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		int count = 0;
		for(MHRConcept concept : MHRConcept.getConcepts(p_HR_Payroll_ID, 0, 0, null))
		{
			if(!existsPayrollConcept(concept.get_ID())) 
			{
				MHRPayrollConcept payrollConcept = new MHRPayrollConcept (concept, p_HR_Payroll_ID, get_TrxName());
				payrollConcept.saveEx();
				count++;
			}
		}
		return "@Created@/@Updated@ #" + count;
	}	//	doIt
	
	private boolean existsPayrollConcept(int HR_Concept_ID)
	{
		final String whereClause = "HR_Payroll_ID=? AND HR_Concept_ID=?";
		return new Query(getCtx(), MHRPayrollConcept.Table_Name, whereClause, get_TrxName())
			.setParameters(new Object[]{p_HR_Payroll_ID, HR_Concept_ID})
			.match();
	}
}	//	Create Concept of the current Payroll
