/******************************************************************************
 * Copyright (C) 2009 Metas                                                   *
 * Copyright (C) 2009 Carlos Ruiz                                             *
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package de.metas.shipping.model;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 *	Shipping Package model
 *	
 *  @author Carlos Ruiz
 */
public class MMShippingPackage extends X_M_ShippingPackage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -150601859277117444L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_ShippingPackage_ID id
	 *	@param trxName transaction
	 */
	public MMShippingPackage (Properties ctx, int M_ShippingPackage_ID, String trxName)
	{
		super (ctx, M_ShippingPackage_ID, trxName);
		if (is_new())
		{
			// setC_BPartner_Location_ID (0);
			// setM_Package_ID (0);
			// setM_ShipperTransportation_ID (0);
			// setM_ShippingPackage_ID (0);
			setPackageNetTotal (BigDecimal.ZERO);
			setProcessed (false);
		}
	}	//	MMShippingPackage

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MMShippingPackage (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MMShippingPackage
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return saved
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		return updateHeader();
	}	//	afterSave

	/**
	 *	Update Header
	 *	@return true if header updated
	 */
	private boolean updateHeader()
	{
		// Update header only if the document is not processed
		if (isProcessed() && !is_ValueChanged(COLUMNNAME_Processed))
			return true;

		//	Update Shipper Transportation Header
		String sql = DB.convertSqlToNative("UPDATE M_ShipperTransportation st"
			+ " SET (PackageWeight,PackageNetTotal)="
				+ "(SELECT COALESCE(SUM(PackageWeight),0), COALESCE(SUM(PackageNetTotal),0) FROM M_ShippingPackage sp WHERE sp.M_ShipperTransportation_ID=st.M_ShipperTransportation_ID) "
			+ "WHERE st.M_ShipperTransportation_ID=?");
		int no = DB.executeUpdateEx(sql, new Object[]{getM_ShipperTransportation_ID()}, get_TrxName());
		if (no != 1)
			log.warn("(1) #" + no);

		return no == 1;
	}	//	updateHeader

}	//	MMShippingPackage
