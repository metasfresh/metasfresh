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

import org.compiere.util.Util;

/**
 * Throw when tax not found for given charge
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2758097 ] Implement TaxNotFoundException
 */
public class TaxForChangeNotFoundException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6553174922970467775L;
	
	private static final String AD_Message = "TaxForChargeNotFound"; // TODO : translate

	public TaxForChangeNotFoundException(int C_Charge_ID, int AD_Org_ID, int M_Warehouse_ID,
			int billC_BPartner_Location_ID, int shipC_BPartner_Location_ID, String additionalMsg)
	{
		super(buildMessage(C_Charge_ID, AD_Org_ID, M_Warehouse_ID,
				billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
				additionalMsg));
	}
	
	private static final String buildMessage(int C_Charge_ID, int AD_Org_ID, int M_Warehouse_ID,
			int billC_BPartner_Location_ID, int shipC_BPartner_Location_ID, String additionalMsg)
	{
		StringBuffer msg = new StringBuffer("@").append(AD_Message).append("@");
		if (!Util.isEmpty(additionalMsg, true))
		{
			msg.append(" ").append(additionalMsg).append(" - ");
		}
		msg.append(" @C_Charge_ID@:").append(C_Charge_ID);
		msg.append(", @AD_Org_ID@:").append(AD_Org_ID);
		msg.append(", @M_Warehouse_ID@:").append(M_Warehouse_ID);
		msg.append(", @C_BPartner_Location_ID@:").append(billC_BPartner_Location_ID) 
							.append("/").append(shipC_BPartner_Location_ID);
		//
		return msg.toString();
	}
}
