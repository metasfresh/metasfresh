/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;

/**
 * Price List Model
 *
 * @author Jorg Janke
 * @version $Id: MPriceList.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 2073484 ] MPriceList.getDefault is not working correctly
 */
@Deprecated
public class MPriceList extends X_M_PriceList
{
	private static final long serialVersionUID = 221716795566010352L;

	@Deprecated
	public static MPriceList get(Properties ctx_IGNORED, int M_PriceList_ID, String trxName_IGNORED)
	{
		final I_M_PriceList pl = Services.get(IPriceListDAO.class).getById(M_PriceList_ID);
		return LegacyAdapters.convertToPO(pl);
	}	// get

	public MPriceList(Properties ctx, int M_PriceList_ID, String trxName)
	{
		super(ctx, M_PriceList_ID, trxName);
		if (is_new())
		{
			setEnforcePriceLimit(false);
			setIsDefault(false);
			setIsSOPriceList(false);
			setIsTaxIncluded(false);
			setPricePrecision(2);	// 2
			// setName (null);
			// setC_Currency_ID (0);
		}
	}	// MPriceList

	public MPriceList(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MPriceList
}	// MPriceList
