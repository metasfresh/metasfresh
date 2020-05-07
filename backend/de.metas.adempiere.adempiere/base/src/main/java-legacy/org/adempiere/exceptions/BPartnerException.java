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

import org.compiere.model.I_C_BPartner;

/**
 * Thrown when an exception related to a BPartner happened.
 * @author Teo Sarca, www.arhipac.ro
 */
public abstract class BPartnerException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4311798678799373821L;
	private final int C_BPartner_ID;
	
	BPartnerException(String ad_message, I_C_BPartner bp)
	{
		super(buildMsg(ad_message, bp));
		if (bp != null)
		{
			this.C_BPartner_ID = bp.getC_BPartner_ID();
		}
		else
		{
			this.C_BPartner_ID = -1;
		}
	}
	
	private static final String buildMsg(final String ad_message, final I_C_BPartner bp)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("@").append(ad_message).append("@");
		
		sb.append(" - @C_BPartner_ID@: ");
		if(bp == null)
		{
			sb.append("none");
		}
		else
		{
			sb.append(bp.getValue()).append("_").append(bp.getName());
			final int bpartnerId = bp.getC_BPartner_ID();
			sb.append(" (ID=").append(bpartnerId <= 0 ? "new" : bpartnerId).append(")");
		}
		
		return sb.toString();
	}
	
	/**
	 * @return the c_BPartner_ID
	 */
	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}
}
