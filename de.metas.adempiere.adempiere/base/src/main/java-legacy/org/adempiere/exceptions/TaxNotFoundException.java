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

import java.sql.Timestamp;
import java.text.DateFormat;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_M_Product;
import org.compiere.model.MLocation;
import org.compiere.model.MTaxCategory;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 * Throw by Tax Engine where no tax found for given criteria
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2758097 ] Implement TaxNotFoundException
 */
public class TaxNotFoundException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5471615720092096644L;
	
	/** AD_Message code */
	private static final String AD_Message = "TaxNotFound";
	
	public TaxNotFoundException(int C_TaxCategory_ID, boolean IsSOTrx,
			Timestamp shipDate, int shipFromC_Location_ID, int shipToC_Location_ID,
			Timestamp billDate, int billFromC_Location_ID, int billToC_Location_ID)
	{
		super(buildMessage(C_TaxCategory_ID, 0, 0, IsSOTrx,
				shipDate, shipFromC_Location_ID, shipToC_Location_ID,
				billDate, billFromC_Location_ID, billToC_Location_ID));
	}
	
	public TaxNotFoundException(int productId, int chargeId, int C_TaxCategory_ID, boolean IsSOTrx,
			Timestamp shipDate, int shipFromC_Location_ID, int shipToC_Location_ID,
			Timestamp billDate, int billFromC_Location_ID, int billToC_Location_ID)
	{
		super(buildMessage(C_TaxCategory_ID, productId, chargeId, IsSOTrx,
				shipDate, shipFromC_Location_ID, shipToC_Location_ID,
				billDate, billFromC_Location_ID, billToC_Location_ID));
	}
	

	private static final String buildMessage (int C_TaxCategory_ID, int productId, int chargeId, boolean IsSOTrx,
			Timestamp shipDate, int shipFromC_Location_ID, int shipToC_Location_ID,
			Timestamp billDate, int billFromC_Location_ID, int billToC_Location_ID)
	{
		final DateFormat df = DisplayType.getDateFormat();
		final StringBuilder msg = new StringBuilder("@").append(AD_Message).append("@");
		
		//
		if (productId > 0)
		{
			msg.append(" - @M_Product_ID@:").append(InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None).getValue());
		}
		if (chargeId > 0)
		{
			msg.append(" - @C_Charge_ID@:").append(InterfaceWrapperHelper.create(Env.getCtx(), chargeId, I_C_Charge.class, ITrx.TRXNAME_None).getName());
		}

		//
		if(C_TaxCategory_ID>0)
		{
			msg.append(" - @C_TaxCategory_ID@:").append(getTaxCategoryString(C_TaxCategory_ID));
		}
		
		msg.append(", @IsSOTrx@:@").append(IsSOTrx ? "Y":"N").append("@");
		//
		msg.append(", @Shipment@ (").append(df.format(shipDate)).append(", ")
			.append(getLocationString(shipFromC_Location_ID))
			.append(" -> ")
			.append(getLocationString(shipToC_Location_ID))
			.append(")");
		//
		msg.append(", @Invoice@ (").append(df.format(billDate)).append(", ")
			.append(getLocationString(billFromC_Location_ID))
			.append(" -> ")
			.append(getLocationString(billToC_Location_ID))
			.append(")");
		//
		//
		return msg.toString();
	}
	
	private static final String getTaxCategoryString(int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID <= 0)
		{
			return "?";
		}
		MTaxCategory cat = new MTaxCategory(Env.getCtx(), C_TaxCategory_ID, null);
		if (cat.get_ID() != C_TaxCategory_ID)
		{
			return "?";
		}
		return cat.getName();
	}
	
	private static final String getLocationString(int C_Location_ID)
	{
		if (C_Location_ID <= 0)
		{
			return "?";
		}
		MLocation loc = MLocation.get(Env.getCtx(), C_Location_ID, null);
		if (loc == null || loc.get_ID() != C_Location_ID)
		{
			return "?";
		}
		return loc.toString();
	}

}
