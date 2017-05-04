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
package org.eevolution.model;

import java.util.Properties;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;

/**
 *	Payroll Callouts.
 *	org.eevolution.model.CalloutPayroll.*
 *	
 *  
 *  @version $Id: CalloutPayment.java,v 1.17 2005/11/06 01:17:27 jjanke Exp $
 */
public class CalloutPayroll extends CalloutEngine
{
	public String ColumnType (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (value == null)
			return "";
		final int HR_Concept_ID = (Integer) value;
		if (HR_Concept_ID == 0)
			return "";
		//
		final String columnType = DB.getSQLValueStringEx(null,
				"SELECT ColumnType FROM HR_Concept WHERE HR_Concept_ID=?",
				HR_Concept_ID);
		mTab.setValue(MHRAttribute.COLUMNNAME_ColumnType, columnType);
		return "";
	}
}	//	CalloutPayroll
