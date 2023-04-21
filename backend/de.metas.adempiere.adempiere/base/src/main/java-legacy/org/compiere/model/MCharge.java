/******************************************************************************
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
package org.compiere.model;

import de.metas.cache.CCache;
import org.adempiere.ad.trx.api.ITrx;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

@Deprecated
public class MCharge extends X_C_Charge
{
	private static final CCache<Integer, MCharge> s_cache = CCache.<Integer, MCharge>builder()
			.tableName(I_C_Charge.Table_Name)
			.initialCapacity(10)
			.build();

	public static MCharge get(Properties ctx, int C_Charge_ID)
	{
		MCharge retValue = s_cache.get(C_Charge_ID);
		if (retValue != null)
			return retValue;
		retValue = new MCharge(ctx, C_Charge_ID, ITrx.TRXNAME_None);
		if (retValue.get_ID() > 0 && retValue.get_ID() == C_Charge_ID)
			s_cache.put(C_Charge_ID, retValue);
		return retValue;
	}

	public MCharge(Properties ctx, int C_Charge_ID, String trxName)
	{
		super(ctx, C_Charge_ID, trxName);
		if (is_new())
		{
			setChargeAmt(BigDecimal.ZERO);
			setIsSameCurrency(false);
			setIsSameTax(false);
			setIsTaxIncluded(false);    // N
			//	setName (null);
			//	setC_TaxCategory_ID (0);
		}
	}

	@SuppressWarnings("unused")
	public MCharge(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
}
