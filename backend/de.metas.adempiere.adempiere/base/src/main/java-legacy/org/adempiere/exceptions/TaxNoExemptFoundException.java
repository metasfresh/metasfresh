/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
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
package org.adempiere.exceptions;

import org.compiere.model.MOrg;
import org.compiere.util.Env;

/**
 * Throw by Tax Engine where no tax found for given criteria
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2758097 ] Implement TaxNotFoundException
 */
public class TaxNoExemptFoundException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5489066603806460132L;
	
	private static final String AD_Message = "TaxNoExemptFound";

	public TaxNoExemptFoundException(int AD_Org_ID)
	{
		super(buildMessage(AD_Org_ID));
	}
	
	private static final String buildMessage (int AD_Org_ID)
	{
		StringBuffer msg = new StringBuffer("@").append(AD_Message).append("@");
		msg.append("@AD_Org_ID@:").append(getOrgString(AD_Org_ID));
		//
		return msg.toString();
	}
	
	private static final String getOrgString(int AD_Org_ID)
	{
		if (AD_Org_ID <= 0)
		{
			return "*";
		}
		MOrg org = MOrg.get(Env.getCtx(), AD_Org_ID);
		if (org == null || org.get_ID() != AD_Org_ID)
		{
			return "?";
		}
		return org.getName();
	}

}
