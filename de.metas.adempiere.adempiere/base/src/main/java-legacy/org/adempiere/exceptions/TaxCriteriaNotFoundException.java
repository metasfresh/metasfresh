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


/**
 * Throw when a tax criteria was not found
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2758097 ] Implement TaxNotFoundException
 */
public class TaxCriteriaNotFoundException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8192276006656371964L;
	
	private static final String AD_Message = "TaxCriteriaNotFound";
	
	public TaxCriteriaNotFoundException(String criteriaName, int criteria_ID)
	{
		super(buildMessage(criteriaName, criteria_ID));
	}
	
	private static final String buildMessage (String criteriaName, int criteria_ID)
	{
		StringBuffer msg = new StringBuffer("@").append(AD_Message).append("@");
		msg.append(" @").append(criteriaName).append("@");
		msg.append(" (ID ").append(criteria_ID).append(")");
		return msg.toString();
	}
	

}
