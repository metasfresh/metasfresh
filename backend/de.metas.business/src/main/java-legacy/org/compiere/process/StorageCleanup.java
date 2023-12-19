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
package org.compiere.process;

import de.metas.ad_reference.ADReferenceService;
import de.metas.i18n.ITranslatableString;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.IStorageBL;
import de.metas.util.Services;
import org.adempiere.exceptions.DBException;
import org.compiere.SpringContextHolder;
import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MStorage;
import org.compiere.model.X_M_Movement;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * StorageCleanup
 *
 * @author Jorg Janke
 * @version $Id: StorageCleanup.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class StorageCleanup extends JavaProcess
{
	/** Movement Document Type */
	private int p_C_DocType_ID = 0;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();
		for (final ProcessInfoParameter element : para)
		{
			final String name = element.getParameterName();
			if (element.getParameter() == null)
			{
				return;
			}
			else if (name.equals("C_DocType_ID"))
			{
				p_C_DocType_ID = element.getParameterAsInt();
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}	// prepare

	/**
	 * Process
	 * 
	 * @return message
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("");
		// Clean up empty Storage
		String sql = "DELETE FROM M_Storage "
				+ "WHERE QtyOnHand = 0 AND QtyReserved = 0 AND QtyOrdered = 0"
				+ " AND Created < now()-3";
		final int no = DB.executeUpdateAndSaveErrorOnFail(sql, get_TrxName());
		log.info("Deleted Empty #" + no);

		//
		sql = "SELECT * "
				+ "FROM M_Storage s "
				+ "WHERE AD_Client_ID = ?"
				+ " AND QtyOnHand < 0"
				// Instance Attribute
				+ " AND EXISTS (SELECT * FROM M_Product p"
				+ " INNER JOIN M_AttributeSet mas ON (p.M_AttributeSet_ID=mas.M_AttributeSet_ID) "
				+ "WHERE s.M_Product_ID=p.M_Product_ID AND mas.IsInstanceAttribute='Y')"
				// Stock in same location
				// + " AND EXISTS (SELECT * FROM M_Storage sl "
				// + "WHERE sl.QtyOnHand > 0"
				// + " AND s.M_Product_ID=sl.M_Product_ID"
				// + " AND s.M_Locator_ID=sl.M_Locator_ID)"
				// Stock in same Warehouse
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
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, Env.getAD_Client_ID(getCtx()));
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				lines += move(new MStorage(getCtx(), rs, get_TrxName()));
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return "#" + lines;
	}	// doIt

	/**
	 * Move stock to location
	 * 
	 * @param target target storage
	 * @return no of movements
	 */
	private int move(MStorage target)
	{
		log.info(target.toString());
		BigDecimal qty = target.getQtyOnHand().negate();

		// Create Movement
		final MMovement mh = new MMovement(getCtx(), 0, get_TrxName());
		mh.setAD_Org_ID(target.getAD_Org_ID());
		mh.setC_DocType_ID(p_C_DocType_ID);
		mh.setDescription(getName());
		if (!mh.save())
		{
			return 0;
		}

		int lines = 0;
		final MStorage[] sources = getSources(target.getM_Product_ID(), target.getM_Locator_ID());
		for (final MStorage source : sources)
		{
			// Movement Line
			final MMovementLine ml = new MMovementLine(mh);
			ml.setM_Product_ID(target.getM_Product_ID());
			ml.setM_LocatorTo_ID(target.getM_Locator_ID());
			ml.setM_AttributeSetInstanceTo_ID(0 /* target.getM_AttributeSetInstance_ID() */);
			// From
			ml.setM_Locator_ID(source.getM_Locator_ID());
			ml.setM_AttributeSetInstance_ID(0 /* source.getM_AttributeSetInstance_ID() */);

			BigDecimal qtyMove = qty;
			if (qtyMove.compareTo(source.getQtyOnHand()) > 0)
			{
				qtyMove = source.getQtyOnHand();
			}
			ml.setMovementQty(qtyMove);
			//
			lines++;
			ml.setLine(lines * 10);
			if (!ml.save())
			{
				return 0;
			}

			qty = qty.subtract(qtyMove);
			if (qty.signum() <= 0)
			{
				break;
			}
		}	// for all movements

		// Process
		mh.processIt(MMovement.ACTION_Complete);
		mh.save();

		final ITranslatableString docStatus = ADReferenceService.get()
				.retrieveListNameTranslatableString(
						X_M_Movement.DOCSTATUS_AD_Reference_ID,
						mh.getDocStatus());

		addLog(0,
				null,
				new BigDecimal(lines),
				"@M_Movement_ID@ " + mh.getDocumentNo() + " (" + docStatus.translate(Env.getAD_Language()) + ")");

		eliminateReservation(target);
		return lines;
	}	// move

	/**
	 * Eliminate Reserved/Ordered
	 * 
	 * @param target target Storage
	 */
	private void eliminateReservation(final MStorage target)
	{
		// Negative Ordered / Reserved Qty
		if (target.getQtyReserved().signum() != 0 || target.getQtyOrdered().signum() != 0)
		{
			int M_Locator_ID = target.getM_Locator_ID();
			MStorage storage0 = MStorage.get(getCtx(), M_Locator_ID,
					target.getM_Product_ID(), 0, get_TrxName());
			if (storage0 == null)
			{
				final MLocator defaultLoc = findOldestLocatorWithSameWarehouse(M_Locator_ID);
				if (M_Locator_ID != defaultLoc.getM_Locator_ID())
				{
					M_Locator_ID = defaultLoc.getM_Locator_ID();
					storage0 = MStorage.get(getCtx(), M_Locator_ID,
							target.getM_Product_ID(), 0, get_TrxName());
				}
			}
			if (storage0 != null)
			{
				BigDecimal reserved = BigDecimal.ZERO;
				BigDecimal ordered = BigDecimal.ZERO;
				if (target.getQtyReserved().add(storage0.getQtyReserved()).signum() >= 0)
				 {
					reserved = target.getQtyReserved();		// negative
				}
				if (target.getQtyOrdered().add(storage0.getQtyOrdered()).signum() >= 0)
				 {
					ordered = target.getQtyOrdered();		// negative
				}
				// Eliminate Reservation
				if (reserved.signum() != 0 || ordered.signum() != 0)
				{
					final IStorageBL storageBL = Services.get(IStorageBL.class);
					storageBL.add(getCtx(), target.getM_Warehouse_ID(), target.getM_Locator_ID(),
							target.getM_Product_ID(),
							target.getM_AttributeSetInstance_ID(), target.getM_AttributeSetInstance_ID(),
							BigDecimal.ZERO, reserved.negate(), ordered.negate(), get_TrxName());

					storageBL.add(getCtx(), storage0.getM_Warehouse_ID(), storage0.getM_Locator_ID(),
							storage0.getM_Product_ID(),
							storage0.getM_AttributeSetInstance_ID(), storage0.getM_AttributeSetInstance_ID(),
							BigDecimal.ZERO, reserved, ordered, get_TrxName());
				}
			}
		}
	}	// eliminateReservation

	private MLocator findOldestLocatorWithSameWarehouse(final int M_Locator_ID)
	{
		final String trxName = null;
		MLocator retValue = null;
		final String sql = "SELECT * FROM M_Locator l "
				+ "WHERE IsActive = 'Y' AND  IsDefault='Y'"
				+ " AND EXISTS (SELECT * FROM M_Locator lx "
				+ "WHERE l.M_Warehouse_ID=lx.M_Warehouse_ID AND lx.M_Locator_ID=?) "
				+ "ORDER BY Created";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, M_Locator_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				retValue = new MLocator(getCtx(), rs, trxName);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return retValue;
	}	// getDefault

	/**
	 * Get Storage Sources
	 * 
	 * @param M_Product_ID product
	 * @param M_Locator_ID locator
	 * @return sources
	 */
	private MStorage[] getSources(final int M_Product_ID, final int M_Locator_ID)
	{
		ArrayList<MStorage> list = new ArrayList<>();
		String sql = "SELECT * "
				+ "FROM M_Storage s "
				+ "WHERE QtyOnHand > 0"
				+ " AND M_Product_ID=?"
				// Empty ASI
				+ " AND (M_AttributeSetInstance_ID=0"
				+ " OR EXISTS (SELECT * FROM M_AttributeSetInstance asi "
				+ "WHERE s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID"
				+ " AND asi.Description IS NULL) )"
				// Stock in same Warehouse
				+ " AND EXISTS (SELECT * FROM M_Locator sl, M_Locator x "
				+ "WHERE s.M_Locator_ID=sl.M_Locator_ID"
				+ " AND x.M_Locator_ID=?"
				+ " AND sl.M_Warehouse_ID=x.M_Warehouse_ID) "
				+ "ORDER BY M_AttributeSetInstance_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, M_Product_ID);
			pstmt.setInt(2, M_Locator_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MStorage(getCtx(), rs, get_TrxName()));
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		final MStorage[] retValue = new MStorage[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getSources

}	// StorageCleanup
