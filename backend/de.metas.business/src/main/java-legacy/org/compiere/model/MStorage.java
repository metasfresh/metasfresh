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

import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Inventory Storage Model
 *
 * @author Jorg Janke
 * @version $Id: MStorage.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MStorage extends X_M_Storage
{
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 9086223702645715061L;

	/**
	 * Get Storage Info
	 *
	 * @param ctx context
	 * @param M_Locator_ID locator
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID instance
	 * @param trxName transaction
	 * @return existing or null
	 */
	public static MStorage get(final Properties ctx, final int M_Locator_ID,
			final int M_Product_ID, final int M_AttributeSetInstance_ID, @Nullable final String trxName)
	{
		MStorage retValue = null;
		String sql = "SELECT * FROM M_Storage "
				+ "WHERE M_Locator_ID=? AND M_Product_ID=? AND ";
		if (M_AttributeSetInstance_ID == 0)
		{
			sql += "(M_AttributeSetInstance_ID=? OR M_AttributeSetInstance_ID IS NULL)";
		}
		else
		{
			sql += "M_AttributeSetInstance_ID=?";
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, M_Locator_ID);
			pstmt.setInt(2, M_Product_ID);
			pstmt.setInt(3, M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = new MStorage(ctx, rs, trxName);
			}
		}
		catch (final SQLException ex)
		{
			s_log.error(sql, ex);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		if (retValue == null)
		{
			s_log.debug("Not Found - M_Locator_ID=" + M_Locator_ID
					+ ", M_Product_ID=" + M_Product_ID + ", M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID);
		}
		else
		{
			s_log.debug("M_Locator_ID=" + M_Locator_ID
					+ ", M_Product_ID=" + M_Product_ID + ", M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID);
		}
		return retValue;
	}	// get

	/**
	 * Get all Storages for Product where QtyOnHand <> 0
	 *
	 * @param ctx context
	 * @param M_Product_ID product
	 * @param M_Locator_ID locator
	 * @param trxName transaction
	 * @return existing or null
	 */
	public static MStorage[] getAll(final Properties ctx,
			final int M_Product_ID, final int M_Locator_ID, final String trxName)
	{
		final ArrayList<MStorage> list = new ArrayList<>();
		final String sql = "SELECT * FROM M_Storage "
				+ "WHERE M_Product_ID=? AND M_Locator_ID=?"
				+ " AND QtyOnHand <> 0 "
				+ "ORDER BY M_AttributeSetInstance_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, M_Product_ID);
			pstmt.setInt(2, M_Locator_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MStorage(ctx, rs, trxName));
			}
		}
		catch (final SQLException ex)
		{
			s_log.error(sql, ex);
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
	}	// getAll

	/**
	 * Get Storage Info for Product across warehouses
	 *
	 * @param ctx context
	 * @param M_Product_ID product
	 * @param trxName transaction
	 * @return existing or null
	 */
	public static MStorage[] getOfProduct(final Properties ctx, final int M_Product_ID, final String trxName)
	{
		final ArrayList<MStorage> list = new ArrayList<>();
		final String sql = "SELECT * FROM M_Storage "
				+ "WHERE M_Product_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, M_Product_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MStorage(ctx, rs, trxName));
			}
		}
		catch (final SQLException ex)
		{
			s_log.error(sql, ex);
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
	}	// getOfProduct

	/**
	 * Get Storage Info for Warehouse
	 *
	 * @param ctx context
	 * @param M_Warehouse_ID
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID instance
	 * @param M_AttributeSet_ID attribute set
	 * @param allAttributeInstances if true, all attribute set instances
	 * @param FiFo first in-first-out
	 * @param trxName transaction
	 * @return existing - ordered by location priority (desc) and/or guarantee date
	 *
	 * @deprecated
	 */
	@Deprecated
	public static MStorage[] getWarehouse(final Properties ctx, final int M_Warehouse_ID,
			final int M_Product_ID, final int M_AttributeSetInstance_ID, final int M_AttributeSet_ID,
			final boolean allAttributeInstances,
			final boolean FiFo, final String trxName)
	{
		return getWarehouse(ctx, M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,
				FiFo, false, 0, trxName);
	}

	/**
	 * Get Storage Info for Warehouse or locator
	 *
	 * @param ctx context
	 * @param M_Warehouse_ID ignore if M_Locator_ID > 0
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID instance id, 0 to retrieve all instance
	 * @param FiFo first in-first-out
	 * @param positiveOnly if true, only return storage records with qtyOnHand > 0
	 * @param M_Locator_ID optional locator id
	 * @param trxName transaction
	 * @return existing - ordered by location priority (desc) and/or guarantee date
	 */
	public static MStorage[] getWarehouse(final Properties ctx, final int M_Warehouse_ID,
			final int M_Product_ID, final int M_AttributeSetInstance_ID,
			final boolean FiFo, final boolean positiveOnly, final int M_Locator_ID, final String trxName)
	{
		if (M_Warehouse_ID == 0 && M_Locator_ID == 0 || M_Product_ID == 0)
		{
			return new MStorage[0];
		}

		boolean allAttributeInstances = false;
		if (M_AttributeSetInstance_ID == 0)
		{
			allAttributeInstances = true;
		}

		final ArrayList<MStorage> list = new ArrayList<>();
		// Specific Attribute Set Instance
		String sql = "SELECT s.M_Product_ID,s.M_Locator_ID,s.M_AttributeSetInstance_ID,"
				+ "s.AD_Client_ID,s.AD_Org_ID,s.IsActive,s.Created,s.CreatedBy,s.Updated,s.UpdatedBy,"
				+ "s.QtyOnHand,s.QtyReserved,s.QtyOrdered,s.DateLastInventory "
				+ "FROM M_Storage s"
				+ " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID) ";
		if (M_Locator_ID > 0)
		{
			sql += "WHERE l.M_Locator_ID = ?";
		}
		else
		{
			sql += "WHERE l.M_Warehouse_ID=?";
		}
		sql += " AND s.M_Product_ID=?"
				+ " AND COALESCE(s.M_AttributeSetInstance_ID,0)=? ";
		if (positiveOnly)
		{
			sql += " AND s.QtyOnHand > 0 ";
		}
		else
		{
			sql += " AND s.QtyOnHand <> 0 ";
		}
		sql += "ORDER BY l.PriorityNo DESC, M_AttributeSetInstance_ID";
		if (!FiFo)
		{
			sql += " DESC";
		}
		// All Attribute Set Instances
		if (allAttributeInstances)
		{
			sql = "SELECT s.M_Product_ID,s.M_Locator_ID,s.M_AttributeSetInstance_ID,"
					+ "s.AD_Client_ID,s.AD_Org_ID,s.IsActive,s.Created,s.CreatedBy,s.Updated,s.UpdatedBy,"
					+ "s.QtyOnHand,s.QtyReserved,s.QtyOrdered,s.DateLastInventory "
					+ "FROM M_Storage s"
					+ " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID)"
					+ " LEFT OUTER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID) ";
			if (M_Locator_ID > 0)
			{
				sql += "WHERE l.M_Locator_ID = ?";
			}
			else
			{
				sql += "WHERE l.M_Warehouse_ID=?";
			}
			sql += " AND s.M_Product_ID=? ";
			if (positiveOnly)
			{
				sql += " AND s.QtyOnHand > 0 ";
			}
			else
			{
				sql += " AND s.QtyOnHand <> 0 ";
			}

				sql += "ORDER BY l.PriorityNo DESC, l.M_Locator_ID, s.M_AttributeSetInstance_ID";
				if (!FiFo)
				{
					sql += " DESC";
				}
				sql += ", s.QtyOnHand DESC";
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, M_Locator_ID > 0 ? M_Locator_ID : M_Warehouse_ID);
			pstmt.setInt(2, M_Product_ID);
			if (!allAttributeInstances)
			{
				pstmt.setInt(3, M_AttributeSetInstance_ID);
			}
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (rs.getBigDecimal(11).signum() != 0)
				{
					list.add(new MStorage(ctx, rs, trxName));
				}
			}
		}
		catch (final Exception e)
		{
			s_log.error(sql, e);
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
	}	// getWarehouse

	/**
	 * Create or Get Storage Info
	 *
	 * @param ctx context
	 * @param M_Locator_ID locator
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID instance
	 * @param trxName transaction
	 * @return existing/new or null
	 */
	private static MStorage getCreate(final Properties ctx, final int M_Locator_ID,
			final int M_Product_ID, final int M_AttributeSetInstance_ID, final String trxName)
	{
		if (M_Locator_ID == 0)
		{
			throw new IllegalArgumentException("M_Locator_ID=0");
		}
		if (M_Product_ID == 0)
		{
			throw new IllegalArgumentException("M_Product_ID=0");
		}
		MStorage retValue = get(ctx, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID, trxName);
		if (retValue != null)
		{
			return retValue;
		}

		// Insert row based on locator
		final I_M_Locator locator = Services.get(IWarehouseDAO.class).getLocatorByRepoId(M_Locator_ID);
		//
		retValue = new MStorage(locator, M_Product_ID, M_AttributeSetInstance_ID);
		retValue.save(trxName);
		s_log.debug("New " + retValue);
		return retValue;
	}	// getCreate

	/**
	 * Update Storage Info add.
	 * Called from MProjectIssue
	 *
	 * @param ctx context
	 * @param M_Warehouse_ID warehouse
	 * @param M_Locator_ID locator
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID AS Instance
	 * @param reservationAttributeSetInstance_ID reservation AS Instance
	 * @param diffQtyOnHand add on hand
	 * @param diffQtyReserved add reserved
	 * @param diffQtyOrdered add order
	 * @param trxName transaction
	 */
	public static void add(final Properties ctx,
			final int M_Warehouse_ID,
			final int M_Locator_ID,
			final int M_Product_ID,
			final int M_AttributeSetInstance_ID,
			int reservationAttributeSetInstance_ID,
			final BigDecimal diffQtyOnHand,
			final BigDecimal diffQtyReserved,
			final BigDecimal diffQtyOrdered,
			final String trxName)
	{
		MStorage storage = null;
		final StringBuilder diffText = new StringBuilder("(");

		// Get Storage
		if (storage == null)
		{
			storage = getCreate(ctx, M_Locator_ID,
					M_Product_ID, M_AttributeSetInstance_ID, trxName);
		}

		final ILoggable loggable = Loggables.get();

		// Verify
		if (storage.getM_Locator_ID() != M_Locator_ID
				&& storage.getM_Product_ID() != M_Product_ID
				&& storage.getM_AttributeSetInstance_ID() != M_AttributeSetInstance_ID)
		{
			final String msg = "No Storage found - M_Locator_ID=" + M_Locator_ID
					+ ",M_Product_ID=" + M_Product_ID + ",ASI=" + M_AttributeSetInstance_ID;
			loggable.addLog(msg);
			throw new AdempiereException(msg);
		}

		// CarlosRuiz - globalqss - Fix [ 1725383 ] QtyOrdered wrongly updated
		if (Services.get(IProductBL.class).getAttributeSetId(M_Product_ID).isNone())
		{
			// Product doesn't manage attribute set, always reserved with 0
			reservationAttributeSetInstance_ID = 0;
		}
		//

		MStorage storage0 = null;
		if (M_AttributeSetInstance_ID != reservationAttributeSetInstance_ID)
		{
			storage0 = get(ctx, M_Locator_ID, M_Product_ID, reservationAttributeSetInstance_ID, trxName);
			if (storage0 == null)	// create if not existing - should not happen
			{
				final LocatorId xM_Locator_ID = Services.get(IWarehouseBL.class).getOrCreateDefaultLocatorId((WarehouseId.ofRepoId(M_Warehouse_ID)));
				storage0 = getCreate(ctx, xM_Locator_ID.getRepoId(),
						M_Product_ID, reservationAttributeSetInstance_ID, trxName);
			}
		}
		boolean changed = false;
		if (diffQtyOnHand != null && diffQtyOnHand.signum() != 0)
		{
			storage.setQtyOnHand(storage.getQtyOnHand().add(diffQtyOnHand));
			diffText.append("OnHand=").append(diffQtyOnHand);
			changed = true;
		}
		if (diffQtyReserved != null && diffQtyReserved.signum() != 0)
		{
			if (storage0 == null)
			{
				storage.setQtyReserved(storage.getQtyReserved().add(diffQtyReserved));
			}
			else
			{
				storage0.setQtyReserved(storage0.getQtyReserved().add(diffQtyReserved));
			}
			diffText.append(" Reserved=").append(diffQtyReserved);
			changed = true;
		}
		if (diffQtyOrdered != null && diffQtyOrdered.signum() != 0)
		{
			if (storage0 == null)
			{
				storage.setQtyOrdered(storage.getQtyOrdered().add(diffQtyOrdered));
			}
			else
			{
				storage0.setQtyOrdered(storage0.getQtyOrdered().add(diffQtyOrdered));
			}
			diffText.append(" Ordered=").append(diffQtyOrdered);
			changed = true;
		}

		if (changed)
		{
			diffText.append(") -> ").append(storage.toString());

			final String logMsg = diffText.toString();
			s_log.debug(logMsg);
			loggable.addLog(logMsg);

			if (storage0 != null)
			{
				// metas: using saveEx to be notified of trouble
				storage0.saveEx(trxName);		// No AttributeSetInstance (reserved/ordered)
			}
			storage.saveEx(trxName);
		}
		else
		{
			loggable.addLog("No storage changes were done");
		}
	}	// add

	/**************************************************************************
	 * Get Location with highest Locator Priority and a sufficient OnHand Qty
	 *
	 * @param M_Warehouse_ID warehouse
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID asi
	 * @param Qty qty
	 * @param trxName transaction
	 * @return id
	 */
	public static int getM_Locator_ID(final int M_Warehouse_ID,
			final int M_Product_ID, final int M_AttributeSetInstance_ID, final BigDecimal Qty,
			final String trxName)
	{
		int M_Locator_ID = 0;
		int firstM_Locator_ID = 0;
		final String sql = "SELECT s.M_Locator_ID, s.QtyOnHand "
				+ "FROM M_Storage s"
				+ " INNER JOIN M_Locator l ON (s.M_Locator_ID=l.M_Locator_ID)"
				+ " INNER JOIN M_Product p ON (s.M_Product_ID=p.M_Product_ID)"
				+ " LEFT OUTER JOIN M_AttributeSet mas ON (p.M_AttributeSet_ID=mas.M_AttributeSet_ID) "
				+ "WHERE l.M_Warehouse_ID=?"
				+ " AND s.M_Product_ID=?"
				+ " AND (mas.IsInstanceAttribute IS NULL OR mas.IsInstanceAttribute='N' OR s.M_AttributeSetInstance_ID=?)"
				+ " AND l.IsActive='Y' "
				+ "ORDER BY l.PriorityNo DESC, s.QtyOnHand DESC";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, M_Warehouse_ID);
			pstmt.setInt(2, M_Product_ID);
			pstmt.setInt(3, M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final BigDecimal QtyOnHand = rs.getBigDecimal(2);
				if (QtyOnHand != null && Qty.compareTo(QtyOnHand) <= 0)
				{
					M_Locator_ID = rs.getInt(1);
					break;
				}
				if (firstM_Locator_ID == 0)
				{
					firstM_Locator_ID = rs.getInt(1);
				}
			}
		}
		catch (final SQLException ex)
		{
			s_log.error(sql, ex);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		if (M_Locator_ID != 0)
		{
			return M_Locator_ID;
		}
		return firstM_Locator_ID;
	}	// getM_Locator_ID

	/**
	 * Get Available Qty.
	 * The call is accurate only if there is a storage record
	 * and assumes that the product is stocked
	 *
	 * @param M_Warehouse_ID wh
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID masi
	 * @param trxName transaction
	 * @return qty available (QtyOnHand-QtyReserved) or null
	 * @deprecated Since 331b. Please use {@link #getQtyAvailable(int, int, int, int, String)}.
	 */
	@Deprecated
	public static BigDecimal getQtyAvailable(final int M_Warehouse_ID,
			final int M_Product_ID, final int M_AttributeSetInstance_ID, final String trxName)
	{
		return getQtyAvailable(M_Warehouse_ID, 0, M_Product_ID, M_AttributeSetInstance_ID, trxName);
	}

	/**
	 * Get Warehouse/Locator Available Qty.
	 * The call is accurate only if there is a storage record
	 * and assumes that the product is stocked
	 *
	 * @param M_Warehouse_ID wh (if the M_Locator_ID!=0 then M_Warehouse_ID is ignored)
	 * @param M_Locator_ID locator (if 0, the whole warehouse will be evaluated)
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID masi
	 * @param trxName transaction
	 * @return qty available (QtyOnHand-QtyReserved) or null if error
	 */
	public static BigDecimal getQtyAvailable(final int M_Warehouse_ID, final int M_Locator_ID,
			final int M_Product_ID, final int M_AttributeSetInstance_ID, final String trxName)
	{
		final ArrayList<Object> params = new ArrayList<>();
		final StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(s.QtyOnHand-s.QtyReserved),0)")
				.append(" FROM M_Storage s")
				.append(" WHERE s.M_Product_ID=?");
		params.add(M_Product_ID);
		// Warehouse level
		if (M_Locator_ID == 0)
		{
			sql.append(" AND EXISTS (SELECT 1 FROM M_Locator l WHERE s.M_Locator_ID=l.M_Locator_ID AND l.M_Warehouse_ID=?)");
			params.add(M_Warehouse_ID);
		}
		// Locator level
		else
		{
			sql.append(" AND s.M_Locator_ID=?");
			params.add(M_Locator_ID);
		}
		// With ASI
		if (M_AttributeSetInstance_ID != 0)
		{
			sql.append(" AND s.M_AttributeSetInstance_ID=?");
			params.add(M_AttributeSetInstance_ID);
		}
		//
		final BigDecimal retValue = DB.getSQLValueBD(trxName, sql.toString(), params);
		if (LogManager.isLevelFine())
		{
			s_log.debug("M_Warehouse_ID=" + M_Warehouse_ID + ", M_Locator_ID=" + M_Locator_ID
					+ ",M_Product_ID=" + M_Product_ID + " = " + retValue);
		}
		return retValue;
	}	// getQtyAvailable

	/**************************************************************************
	 * Persistency Constructor
	 *
	 * @param ctx context
	 * @param ignored ignored
	 * @param trxName transaction
	 */
	public MStorage(final Properties ctx, final int ignored, final String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
		{
			throw new IllegalArgumentException("Multi-Key");
		}
		//
		setQtyOnHand(Env.ZERO);
		setQtyOrdered(Env.ZERO);
		setQtyReserved(Env.ZERO);
	}	// MStorage

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MStorage(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MStorage

	/**
	 * Full NEW Constructor
	 *
	 * @param locator (parent) locator
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID attribute
	 */
	private MStorage(final I_M_Locator locator, final int M_Product_ID, final int M_AttributeSetInstance_ID)
	{
		this(Env.getCtx(), 0, ITrx.TRXNAME_ThreadInherited);
		setClientOrgFromModel(locator);
		setM_Locator_ID(locator.getM_Locator_ID());
		setM_Product_ID(M_Product_ID);
		setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
	}	// MStorage

	/** Log */
	private static Logger s_log = LogManager.getLogger(MStorage.class);
	/** Warehouse */
	private int m_M_Warehouse_ID = 0;

	/**
	 * Change Qty OnHand
	 *
	 * @param qty quantity
	 * @param add add if true
	 */
	public void changeQtyOnHand(final BigDecimal qty, final boolean add)
	{
		if (qty == null || qty.signum() == 0)
		{
			return;
		}
		if (add)
		{
			setQtyOnHand(getQtyOnHand().add(qty));
		}
		else
		{
			setQtyOnHand(getQtyOnHand().subtract(qty));
		}
	}	// changeQtyOnHand

	/**
	 * Get M_Warehouse_ID of Locator
	 *
	 * @return warehouse
	 */
	public int getM_Warehouse_ID()
	{
		if (m_M_Warehouse_ID <= 0)
		{
			final I_M_Locator loc = Services.get(IWarehouseDAO.class).getLocatorByRepoId(getM_Locator_ID());
			m_M_Warehouse_ID = loc.getM_Warehouse_ID();
		}
		return m_M_Warehouse_ID;
	}	// getM_Warehouse_ID

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MStorage[")
				.append("M_Locator_ID=").append(getM_Locator_ID())
				.append(",M_Product_ID=").append(getM_Product_ID())
				.append(",M_AttributeSetInstance_ID=").append(getM_AttributeSetInstance_ID())
				.append(": OnHand=").append(getQtyOnHand())
				.append(",Reserved=").append(getQtyReserved())
				.append(",Ordered=").append(getQtyOrdered())
				.append("]");
		return sb.toString();
	}	// toString

}	// MStorage
