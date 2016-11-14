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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MRefList;
import org.compiere.model.MStorage;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * 	StorageCleanup
 *	
 *  @author Jorg Janke
 *  @version $Id: StorageCleanup.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class StorageCleanup extends SvrProcess
{
	/** Movement Document Type	*/
	private int	p_C_DocType_ID = 0;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		log.info("");
		//	Clean up empty Storage
		String sql = "DELETE FROM M_Storage "
			+ "WHERE QtyOnHand = 0 AND QtyReserved = 0 AND QtyOrdered = 0"
			+ " AND Created < now()-3";
		int no = DB.executeUpdate(sql, get_TrxName());
		log.info("Deleted Empty #" + no);
		
		//
		sql = "SELECT * "
			+ "FROM M_Storage s "
			+ "WHERE AD_Client_ID = ?"
			+ " AND QtyOnHand < 0"
			//	Instance Attribute
			+ " AND EXISTS (SELECT * FROM M_Product p"
				+ " INNER JOIN M_AttributeSet mas ON (p.M_AttributeSet_ID=mas.M_AttributeSet_ID) "
				+ "WHERE s.M_Product_ID=p.M_Product_ID AND mas.IsInstanceAttribute='Y')"
			//	Stock in same location
		//	+ " AND EXISTS (SELECT * FROM M_Storage sl "
		//		+ "WHERE sl.QtyOnHand > 0"
		//		+ " AND s.M_Product_ID=sl.M_Product_ID"
		//		+ " AND s.M_Locator_ID=sl.M_Locator_ID)"
			//	Stock in same Warehouse
			+ " AND EXISTS (SELECT * FROM M_Storage sw"
				+ " INNER JOIN M_Locator swl ON (sw.M_Locator_ID=swl.M_Locator_ID), M_Locator sl "
				+ "WHERE sw.QtyOnHand > 0"
				+ " AND s.M_Product_ID=sw.M_Product_ID"
				+ " AND s.M_Locator_ID=sl.M_Locator_ID"
				+ " AND sl.M_Warehouse_ID=swl.M_Warehouse_ID)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int lines = 0;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, Env.getAD_Client_ID(getCtx()));
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				lines += move (new MStorage(getCtx(), rs, get_TrxName()));
			}
 		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return "#" + lines;
	}	//	doIt

	/**
	 * 	Move stock to location
	 *	@param target target storage
	 *	@return no of movements
	 */
	private int move (MStorage target)
	{
		log.info(target.toString());
		BigDecimal qty = target.getQtyOnHand().negate();

		//	Create Movement
		MMovement mh = new MMovement (getCtx(), 0, get_TrxName());
		mh.setAD_Org_ID(target.getAD_Org_ID());
		mh.setC_DocType_ID(p_C_DocType_ID);
		mh.setDescription(getName());
		if (!mh.save())
			return 0;

		int lines = 0;
		MStorage[] sources = getSources(target.getM_Product_ID(), target.getM_Locator_ID());
		for (int i = 0; i < sources.length; i++)
		{
			MStorage source = sources[i];
			
			//	Movement Line
			MMovementLine ml = new MMovementLine(mh);
			ml.setM_Product_ID(target.getM_Product_ID());
			ml.setM_LocatorTo_ID(target.getM_Locator_ID());
			ml.setM_AttributeSetInstanceTo_ID(target.getM_AttributeSetInstance_ID());
			//	From
			ml.setM_Locator_ID(source.getM_Locator_ID());
			ml.setM_AttributeSetInstance_ID(source.getM_AttributeSetInstance_ID());
			
			BigDecimal qtyMove = qty;
			if (qtyMove.compareTo(source.getQtyOnHand()) > 0)
				qtyMove = source.getQtyOnHand();
			ml.setMovementQty(qtyMove);
			//
			lines++;
			ml.setLine(lines*10);
			if (!ml.save())
				return 0;
			
			qty = qty.subtract(qtyMove);
			if (qty.signum() <= 0)
				break;
		}	//	for all movements
		
		//	Process
		mh.processIt(MMovement.ACTION_Complete);
		mh.save();
		
		addLog(0, null, new BigDecimal(lines), "@M_Movement_ID@ " + mh.getDocumentNo() + " (" 
			+ MRefList.get(getCtx(), MMovement.DOCSTATUS_AD_Reference_ID, 
				mh.getDocStatus(), get_TrxName()) + ")");

		eliminateReservation(target);
		return lines;
	}	//	move

	/**
	 * 	Eliminate Reserved/Ordered
	 *	@param target target Storage
	 */
	private void eliminateReservation(MStorage target)
	{
		//	Negative Ordered / Reserved Qty
		if (target.getQtyReserved().signum() != 0 || target.getQtyOrdered().signum() != 0)
		{
			int M_Locator_ID = target.getM_Locator_ID();
			MStorage storage0 = MStorage.get(getCtx(), M_Locator_ID, 
				target.getM_Product_ID(), 0, get_TrxName());
			if (storage0 == null)
			{
				MLocator defaultLoc = MLocator.getDefault(getCtx(), M_Locator_ID);
				if (M_Locator_ID != defaultLoc.getM_Locator_ID())
				{
					M_Locator_ID = defaultLoc.getM_Locator_ID();
					storage0 = MStorage.get(getCtx(), M_Locator_ID, 
						target.getM_Product_ID(), 0, get_TrxName());
				}
			}
			if (storage0 != null)
			{
				BigDecimal reserved = Env.ZERO;
				BigDecimal ordered = Env.ZERO;
				if (target.getQtyReserved().add(storage0.getQtyReserved()).signum() >= 0)
					reserved = target.getQtyReserved();		//	negative
				if (target.getQtyOrdered().add(storage0.getQtyOrdered()).signum() >= 0)
					ordered = target.getQtyOrdered();		//	negative
				//	Eliminate Reservation
				if (reserved.signum() != 0 || ordered.signum() != 0)
				{
					if (MStorage.add(getCtx(), target.getM_Warehouse_ID(), target.getM_Locator_ID(), 
						target.getM_Product_ID(), 
						target.getM_AttributeSetInstance_ID(), target.getM_AttributeSetInstance_ID(),
						Env.ZERO, reserved.negate(), ordered.negate(), get_TrxName()))
					{
						if (MStorage.add(getCtx(), storage0.getM_Warehouse_ID(), storage0.getM_Locator_ID(), 
							storage0.getM_Product_ID(), 
							storage0.getM_AttributeSetInstance_ID(), storage0.getM_AttributeSetInstance_ID(),
							Env.ZERO, reserved, ordered, get_TrxName()))
							log.info("Reserved=" + reserved + ",Ordered=" + ordered);
						else
							log.warn("Failed Storage0 Update");
					}
					else
						log.warn("Failed Target Update");
				}
			}
		}
	}	//	eliminateReservation
	
	/**
	 * 	Get Storage Sources
	 *	@param M_Product_ID product
	 *	@param M_Locator_ID locator
	 *	@return sources
	 */
	private MStorage[] getSources (int M_Product_ID, int M_Locator_ID)
	{
		ArrayList<MStorage> list = new ArrayList<MStorage>();
		String sql = "SELECT * "
			+ "FROM M_Storage s "
			+ "WHERE QtyOnHand > 0"
			+ " AND M_Product_ID=?"
			//	Empty ASI
			+ " AND (M_AttributeSetInstance_ID=0"
			+ " OR EXISTS (SELECT * FROM M_AttributeSetInstance asi "
				+ "WHERE s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID"
				+ " AND asi.Description IS NULL) )"
			//	Stock in same Warehouse
			+ " AND EXISTS (SELECT * FROM M_Locator sl, M_Locator x "
				+ "WHERE s.M_Locator_ID=sl.M_Locator_ID"
				+ " AND x.M_Locator_ID=?"
				+ " AND sl.M_Warehouse_ID=x.M_Warehouse_ID) "
			+ "ORDER BY M_AttributeSetInstance_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, M_Product_ID);
			pstmt.setInt (2, M_Locator_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MStorage (getCtx(), rs, get_TrxName()));
			}
 		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		MStorage[] retValue = new MStorage[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getSources
	
}	//	StorageCleanup
