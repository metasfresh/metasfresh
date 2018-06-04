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
package org.compiere.process;

import org.compiere.model.MInventory;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.DB;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;

/**
 *	Update existing Inventory Count List with current Book value
 *
 *  @author Jorg Janke
 *  @version $Id: InventoryCountUpdate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class InventoryCountUpdate extends JavaProcess
{
	/** Physical Inventory		*/
	private int		p_M_Inventory_ID = 0;
	/** Update to What			*/
	private boolean	p_InventoryCountSetZero = false;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("InventoryCountSet"))
				p_InventoryCountSetZero = "Z".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
		p_M_Inventory_ID = getRecord_ID();
	}	//	prepare


	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	@Override
	protected String doIt () throws Exception
	{
		log.info("M_Inventory_ID=" + p_M_Inventory_ID);
		MInventory inventory = new MInventory (getCtx(), p_M_Inventory_ID, get_TrxName());
		if (inventory.get_ID() == 0)
			throw new AdempiereSystemError ("Not found: M_Inventory_ID=" + p_M_Inventory_ID);

		//	Multiple Lines for one item
		String sql = "UPDATE M_InventoryLine SET IsActive='N' "
			+ "WHERE M_Inventory_ID=" + p_M_Inventory_ID
			+ " AND (M_Product_ID, M_Locator_ID, M_AttributeSetInstance_ID) IN "
				+ "(SELECT M_Product_ID, M_Locator_ID, M_AttributeSetInstance_ID "
				+ "FROM M_InventoryLine "
				+ "WHERE M_Inventory_ID=" + p_M_Inventory_ID
				+ " GROUP BY M_Product_ID, M_Locator_ID, M_AttributeSetInstance_ID "
				+ "HAVING COUNT(*) > 1)";
		int multiple = DB.executeUpdate(sql, get_TrxName());
		log.info("Multiple=" + multiple);

		//	ASI
		sql = DB.convertSqlToNative("UPDATE M_InventoryLine l "
			+ "SET (QtyBook,QtyCount) = "
				+ "(SELECT QtyOnHand,QtyOnHand FROM M_Storage s "
				+ "WHERE s.M_Product_ID=l.M_Product_ID AND s.M_Locator_ID=l.M_Locator_ID"
				+ " AND s.M_AttributeSetInstance_ID=l.M_AttributeSetInstance_ID),"
			+ " Updated=now(),"
			+ " UpdatedBy=" + getAD_User_ID()
			//
			+ " WHERE M_Inventory_ID=" + p_M_Inventory_ID
			+ " AND EXISTS (SELECT * FROM M_Storage s "
				+ "WHERE s.M_Product_ID=l.M_Product_ID AND s.M_Locator_ID=l.M_Locator_ID"
				+ " AND s.M_AttributeSetInstance_ID=l.M_AttributeSetInstance_ID)");
		int no = DB.executeUpdate(sql, get_TrxName());
		log.info("Update with ASI=" + no);

		//	Set Count to Zero
		if (p_InventoryCountSetZero)
		{
			sql = "UPDATE M_InventoryLine l "
				+ "SET QtyCount=0 "
				+ "WHERE M_Inventory_ID=" + p_M_Inventory_ID;
			no = DB.executeUpdate(sql, get_TrxName());
			log.info("Set Count to Zero=" + no);
		}

		if (multiple > 0)
			return "@M_InventoryLine_ID@ - #" + (no) + " --> @InventoryProductMultiple@";

		return "@M_InventoryLine_ID@ - #" + no;
	}	//	doIt


}	//	InventoryCountUpdate
