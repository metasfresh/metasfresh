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
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.Env;

import de.metas.currency.ICurrencyDAO;

/**
 * Price List Model
 *
 * @author Jorg Janke
 * @version $Id: MPriceList.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 2073484 ] MPriceList.getDefault is not working correctly
 */
public class MPriceList extends X_M_PriceList
{

	public static final int M_PriceList_ID_None = 100;

	/**
	 *
	 */
	private static final long serialVersionUID = 221716795566010352L;

	/**
	 * Get Price List (cached)
	 *
	 * @param ctx context
	 * @param M_PriceList_ID id
	 * @param trxName transaction
	 * @return PriceList
	 */
	public static MPriceList get(Properties ctx, int M_PriceList_ID, String trxName)
	{
		Integer key = new Integer(M_PriceList_ID);
		MPriceList retValue = s_cache.get(key);
		if (retValue == null)
		{
			retValue = new MPriceList(ctx, M_PriceList_ID, trxName);
			s_cache.put(key, retValue);
		}
		return retValue;
	}	// get

	/**
	 * Get Default Price List for Client (cached)
	 *
	 * @param ctx context
	 * @param IsSOPriceList SO or PO
	 * @return PriceList or null
	 */
	public static MPriceList getDefault(Properties ctx, boolean IsSOPriceList)
	{
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		MPriceList retValue = null;
		// Search for it in cache
		Iterator<MPriceList> it = s_cache.values().iterator();
		while (it.hasNext())
		{
			retValue = it.next();
			if (retValue.isDefault()
					&& retValue.getAD_Client_ID() == AD_Client_ID
					&& retValue.isSOPriceList() == IsSOPriceList)
			{
				return retValue;
			}
		}

		// Get from DB
		final String whereClause = "AD_Client_ID=? AND IsDefault=? AND IsSOPriceList=?";
		retValue = new Query(ctx, Table_Name, whereClause, null)
				.setParameters(new Object[] { AD_Client_ID, "Y", IsSOPriceList ? "Y" : "N" })
				.setOrderBy("M_PriceList_ID")
				.first();

		// Return value
		if (retValue != null)
		{
			s_cache.put(retValue.get_ID(), retValue);
		}
		return retValue;
	}	// getDefault

	/**
	 * Get Default Price List for Client (cached) with given currency
	 *
	 * @param ctx context
	 * @param IsSOPriceList SO or PO
	 * @param ISOCurrency
	 * @return PriceList or null
	 */
	public static MPriceList getDefault(Properties ctx, boolean IsSOPriceList, String ISOCurrency)
	{
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrencyByISOCode(ctx, ISOCurrency);
		// If currency is null, return the default without looking at currency
		if (currency == null)
			return (getDefault(ctx, IsSOPriceList));

		final int C_Currency_ID = currency.getC_Currency_ID();

		MPriceList retValue = null;
		// Search for it in cache
		Iterator<MPriceList> it = s_cache.values().iterator();
		while (it.hasNext())
		{
			retValue = it.next();
			if (retValue.isDefault()
					&& retValue.getAD_Client_ID() == AD_Client_ID
					&& retValue.isSOPriceList() == IsSOPriceList
					&& retValue.getC_Currency_ID() == C_Currency_ID)
			{
				return retValue;
			}
		}

		// Get from DB
		final String whereClause = "AD_Client_ID=? AND IsDefault=? AND IsSOPriceList=? AND C_Currency_ID=?";
		retValue = new Query(ctx, Table_Name, whereClause, null)
				.setParameters(new Object[] { AD_Client_ID, "Y", IsSOPriceList ? "Y" : "N", Integer.valueOf(C_Currency_ID) })
				.setOrderBy("M_PriceList_ID")
				.first();

		// Return value
		if (retValue != null)
		{
			s_cache.put(retValue.get_ID(), retValue);
		}
		return retValue;
	}

	/**
	 * Get the pricePrecision set in the price list
	 *
	 * <li>(OLD BEHAVIOUR: Get Standard Currency Precision)
	 *
	 * @param ctx context
	 * @param M_PriceList_ID price list
	 * @return precision
	 */
	public static int getStandardPrecision(Properties ctx, int M_PriceList_ID)
	{
		MPriceList pl = MPriceList.get(ctx, M_PriceList_ID, null);
		return pl.getStandardPrecision();
	}	// getStandardPrecision

	/** Cache of Price Lists */
	private static CCache<Integer, MPriceList> s_cache = new CCache<Integer, MPriceList>(Table_Name, 5);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param M_PriceList_ID id
	 * @param trxName transaction
	 */
	public MPriceList(Properties ctx, int M_PriceList_ID, String trxName)
	{
		super(ctx, M_PriceList_ID, trxName);
		if (M_PriceList_ID == 0)
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

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MPriceList(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MPriceList

	/**
	 * Import Constructor
	 *
	 * @param impPL import
	 */
	public MPriceList(X_I_PriceList impPL)
	{
		this(impPL.getCtx(), 0, impPL.get_TrxName());
		setClientOrg(impPL);
		setUpdatedBy(impPL.getUpdatedBy());
		//
		setName(impPL.getName());
		setDescription(impPL.getDescription());
		setC_Currency_ID(impPL.getC_Currency_ID());
		setPricePrecision(impPL.getPricePrecision());
		setIsSOPriceList(impPL.isSOPriceList());
		setIsTaxIncluded(impPL.isTaxIncluded());
		setEnforcePriceLimit(impPL.isEnforcePriceLimit());
	}	// MPriceList

	/** Cached PLV */
	private MPriceListVersion m_plv = null;

	/**
	 * Get Price List Version
	 *
	 * @param valid date where PLV must be valid or today if null
	 * @return PLV
	 */
	public MPriceListVersion getPriceListVersion(Timestamp valid)
	{
		if (valid == null)
			valid = new Timestamp(System.currentTimeMillis());
		// Assume there is no later
		if (m_plv != null && m_plv.getValidFrom().before(valid))
			return m_plv;

		final String whereClause = "M_PriceList_ID=? AND TRUNC(ValidFrom)<=? AND IsActive=?";
		m_plv = new Query(getCtx(), MPriceListVersion.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { getM_PriceList_ID(), valid, "Y" })
				.setOrderBy("ValidFrom DESC")
				.first();
		if (m_plv == null)
			log.warn("None found M_PriceList_ID=" + getM_PriceList_ID() + " - " + valid);
		else
			log.debug(m_plv.toString());
		return m_plv;
	}	// getPriceListVersion

	/**
	 * Get the pricePrecision set in the price list
	 *
	 * <li>(OLD BEHAVIOUR: Get Standard Currency Precision)
	 *
	 * @return precision
	 * @deprecated Please use {@link #getPricePrecision()}.
	 */
	@Deprecated
	public int getStandardPrecision()
	{
		return getPricePrecision();
	}	// getStandardPrecision
}	// MPriceList
