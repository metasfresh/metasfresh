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

import java.text.DateFormat;
import java.util.Date;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_Product;
import org.compiere.model.MLocation;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import lombok.Builder;

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
	
	private final int productId;
	private final int chargeId;
	
	private final int taxCategoryId;
	private final boolean isSOTrx;
	
	private final Date shipDate;
	private final int shipFromC_Location_ID;
	private final int shipToC_Location_ID;
	
	private final Date billDate;
	private final int billFromC_Location_ID;
	private final int billToC_Location_ID;

	
	@Builder
	private TaxNotFoundException( //
			int productId, int chargeId //
			, int taxCategoryId //
			, boolean isSOTrx //
			, Date shipDate, int shipFromC_Location_ID, int shipToC_Location_ID //
			, Date billDate, int billFromC_Location_ID, int billToC_Location_ID //
			)
	{
		this.productId = productId;
		this.chargeId = chargeId;
		
		this.taxCategoryId = taxCategoryId;
		this.isSOTrx = isSOTrx;
		
		this.shipDate = shipDate;
		this.shipFromC_Location_ID = shipFromC_Location_ID;
		this.shipToC_Location_ID = shipToC_Location_ID;
		
		this.billDate = billDate;
		this.billFromC_Location_ID = billFromC_Location_ID;
		this.billToC_Location_ID = billToC_Location_ID;
	}
	
	@Override
	protected String buildMessage()
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
		if(taxCategoryId > 0)
		{
			msg.append(" - @C_TaxCategory_ID@:").append(getTaxCategoryString(taxCategoryId));
		}
		
		msg.append(", @IsSOTrx@:@").append(isSOTrx ? "Y":"N").append("@");

		//
		// Ship info
		if(shipDate != null || shipFromC_Location_ID > 0 || shipToC_Location_ID > 0)
		{
			msg.append(", @Shipment@ (")
				.append(shipDate != null ? df.format(shipDate) : "unknown ship date")
				.append(", ")
				.append(getLocationString(shipFromC_Location_ID))
				.append(" -> ")
				.append(getLocationString(shipToC_Location_ID))
				.append(")");
		}
		
		//
		// Bill info
		if(billDate != null || billFromC_Location_ID > 0 || billToC_Location_ID > 0)
		{
			msg.append(", @Invoice@ (")
				.append(billDate != null ? df.format(billDate) : "unknown bill date")
				.append(", ")
				.append(getLocationString(billFromC_Location_ID))
				.append(" -> ")
				.append(getLocationString(billToC_Location_ID))
				.append(")");
		}
		
		//
		//
		return msg.toString();
	}
	
	private static final String getTaxCategoryString(final int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID <= 0)
		{
			return "?";
		}
		final I_C_TaxCategory taxCategory = InterfaceWrapperHelper.loadOutOfTrx(C_TaxCategory_ID, I_C_TaxCategory.class);
		if (taxCategory == null)
		{
			return "?";
		}
		
		return taxCategory.getName();
	}
	
	private static final String getLocationString(final int C_Location_ID)
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
