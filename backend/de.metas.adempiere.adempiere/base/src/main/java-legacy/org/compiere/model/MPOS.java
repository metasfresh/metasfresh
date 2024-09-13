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

import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * POS Terminal definition
 *
 * @author Jorg Janke
 * @version $Id: MPOS.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MPOS extends X_C_POS
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1568195843844720536L;

	public MPOS(Properties ctx, int C_POS_ID, String trxName)
	{
		super(ctx, C_POS_ID, trxName);
		if (is_new())
		{
			//	setName (null);
			//	setSalesRep_ID (0);
			//	setC_CashBook_ID (0);
			//	setM_PriceList_ID (0);
			setIsModifyPrice(false);    // N
			//	setM_Warehouse_ID (0);
		}
	}

	@SuppressWarnings("unused")
	public MPOS(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//	Org Consistency
		if (newRecord
				|| is_ValueChanged(COLUMNNAME_C_CashBook_ID) || is_ValueChanged(COLUMNNAME_M_Warehouse_ID))
		{
			MCashBook cb = MCashBook.get(getCtx(), getC_CashBook_ID(), get_TrxName());
			if (cb.getAD_Org_ID() != getAD_Org_ID())
			{
				throw new AdempiereException("@AD_Org_ID@: @C_CashBook_ID@");
			}

			final I_M_Warehouse wh = Services.get(IWarehouseDAO.class).getById(WarehouseId.ofRepoId(getM_Warehouse_ID()));
			if (wh.getAD_Org_ID() != getAD_Org_ID())
			{
				throw new AdempiereException("@AD_Org_ID@: @M_Warehouse_ID@");
			}
		}
		return true;
	}    //	beforeSave

	@Override
	public String toString()
	{
		return super.getName();
	}

}

