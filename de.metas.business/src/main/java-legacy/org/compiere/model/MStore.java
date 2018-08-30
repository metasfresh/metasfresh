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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Web Store
 *
 * @author Jorg Janke
 * @version $Id: MStore.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 */
public class MStore extends X_W_Store
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5836212836465405633L;

	/**
	 * Get WStore from Cache
	 *
	 * @param ctx context
	 * @param W_Store_ID id
	 * @return WStore
	 */
	public static MStore get(Properties ctx, int W_Store_ID)
	{
		Integer key = new Integer(W_Store_ID);
		MStore retValue = s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MStore(ctx, W_Store_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	}	// get

	/**
	 * Get WStore from Cache
	 *
	 * @param ctx context
	 * @param contextPath web server context path
	 * @return WStore
	 */
	public static MStore get(Properties ctx, String contextPath)
	{
		MStore wstore = null;
		Iterator<MStore> it = s_cache.values().iterator();
		while (it.hasNext())
		{
			wstore = it.next();
			if (wstore.getWebContext().equals(contextPath))
				return wstore;
		}

		// Search by context
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM W_Store WHERE WebContext=?";
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, contextPath);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				wstore = new MStore(ctx, rs, null);
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		// Try client
		if (wstore == null)
		{
			sql = "SELECT * FROM W_Store WHERE AD_Client_ID=? AND IsActive='Y' ORDER BY W_Store_ID";
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, Env.getAD_Client_ID(ctx));
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
				{
					wstore = new MStore(ctx, rs, null);
					s_log.warn("Context " + contextPath
							+ " Not found - Found via AD_Client_ID=" + Env.getAD_Client_ID(ctx));
				}
				rs.close();
				pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				s_log.error(sql, e);
			}
			try
			{
				if (pstmt != null)
					pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				pstmt = null;
			}
		}
		// Nothing
		if (wstore == null)
			return null;

		// Save
		Integer key = new Integer(wstore.getW_Store_ID());
		s_cache.put(key, wstore);
		return wstore;
	}	// get

	/**
	 * Get active Web Stores of Clieny
	 *
	 * @param client client
	 * @return array of web stores
	 */
	public static MStore[] getOfClient(MClient client)
	{
		ArrayList<MStore> list = new ArrayList<>();
		String sql = "SELECT * FROM W_Store WHERE AD_Client_ID=? AND IsActive='Y'";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, client.get_TrxName());
			pstmt.setInt(1, client.getAD_Client_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MStore(client.getCtx(), rs, client.get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//
		MStore[] retValue = new MStore[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getOfClient

	/**
	 * Get Active Web Stores
	 *
	 * @return cached web stores
	 */
	public static MStore[] getActive()
	{
		s_log.info("");
		try
		{
			Collection<MStore> cc = s_cache.values();
			Object[] oo = cc.toArray();
			for (int i = 0; i < oo.length; i++)
				s_log.info(i + ": " + oo[i]);
			MStore[] retValue = new MStore[oo.length];
			for (int i = 0; i < oo.length; i++)
				retValue[i] = (MStore)oo[i];
			return retValue;
		}
		catch (Exception e)
		{
			s_log.error(e.toString());
		}
		return new MStore[] {};
	}	// getActive

	/** Cache */
	private static CCache<Integer, MStore> s_cache = new CCache<>("W_Store", 2);
	/** Logger */
	private static Logger s_log = LogManager.getLogger(MStore.class);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param W_Store_ID id
	 * @param trxName trx
	 */
	public MStore(Properties ctx, int W_Store_ID, String trxName)
	{
		super(ctx, W_Store_ID, trxName);
		if (W_Store_ID == 0)
		{
			setIsDefault(false);
			setIsMenuAssets(true);	// Y
			setIsMenuContact(true);	// Y
			setIsMenuInterests(true);	// Y
			setIsMenuInvoices(true);	// Y
			setIsMenuOrders(true);	// Y
			setIsMenuPayments(true);	// Y
			setIsMenuRegistrations(true);	// Y
			setIsMenuRequests(true);	// Y
			setIsMenuRfQs(true);	// Y
			setIsMenuShipments(true);	// Y

			// setC_PaymentTerm_ID (0);
			// setM_PriceList_ID (0);
			// setM_Warehouse_ID (0);
			// setName (null);
			// setSalesRep_ID (0);
			// setURL (null);
			// setWebContext (null);
		}
	}	// MWStore

	public MStore(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MWStore

	/**
	 * Get Web Context
	 *
	 * @param full if true fully qualified
	 * @return web context
	 */
	public String getWebContext(boolean full)
	{
		if (!full)
			return super.getURL();
		String url = super.getURL();
		if (url == null || url.length() == 0)
			url = "http://localhost";
		if (url.endsWith("/"))
			url += url.substring(0, url.length() - 1);
		return url + getWebContext();	// starts with /
	}	// getWebContext

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("WStore[");
		sb.append(getWebContext(true))
				.append("]");
		return sb.toString();
	}	// toString

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// Context to start with /
		if (!getWebContext().startsWith("/"))
			setWebContext("/" + getWebContext());

		// Org to Warehouse
		if (newRecord || is_ValueChanged("M_Warehouse_ID") || getAD_Org_ID() == 0)
		{
			MWarehouse wh = new MWarehouse(getCtx(), getM_Warehouse_ID(), get_TrxName());
			setAD_Org_ID(wh.getAD_Org_ID());
		}

		String url = getURL();
		if (url == null)
			url = "";
		boolean urlOK = url.startsWith("http://") || url.startsWith("https://");
		if (!urlOK) // || url.indexOf("localhost") != -1)
		{
			throw new FillMandatoryException("URL");
		}

		return true;
	}	// beforeSave
}	// MWStore
