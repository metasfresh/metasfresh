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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Cost Element Model
 *
 * @author Jorg Janke
 * @version $Id: MCostElement.java,v 1.2 2006/07/30 00:58:04 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 2664529 ] More then one Labor/Burden//Overhead is not allowed
 *         <li>BF [ 2667470 ] MCostElement.getMaterialCostElement should check only material
 */
public class MCostElement extends X_M_CostElement
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8676787942212800906L;

	/**
	 * Get Material Cost Element or create it
	 *
	 * @param po parent
	 * @param CostingMethod method
	 * @return cost element
	 */
	public static MCostElement getMaterialCostElement(PO po, String CostingMethod)
	{
		if (CostingMethod == null || CostingMethod.length() == 0)
		{
			s_log.error("No CostingMethod");
			return null;
		}
		//
		final String whereClause = "AD_Client_ID=? AND CostingMethod=? AND CostElementType=?";
		MCostElement retValue = new Query(po.getCtx(), Table_Name, whereClause, po.get_TrxName())
				.setParameters(new Object[] { po.getAD_Client_ID(), CostingMethod, COSTELEMENTTYPE_Material })
				.setOrderBy("AD_Org_ID")
				.firstOnly();
		if (retValue != null)
			return retValue;

		// Create New
		retValue = new MCostElement(po.getCtx(), 0, po.get_TrxName());
		retValue.setClientOrg(po.getAD_Client_ID(), 0);
		String name = MRefList.getListName(po.getCtx(), COSTINGMETHOD_AD_Reference_ID, CostingMethod);
		if (name == null || name.length() == 0)
			name = CostingMethod;
		retValue.setName(name);
		retValue.setCostElementType(COSTELEMENTTYPE_Material);
		retValue.setCostingMethod(CostingMethod);
		retValue.saveEx();

		//
		return retValue;
	}	// getMaterialCostElement

	/**
	 * Get first Material Cost Element
	 *
	 * @param ctx context
	 * @param CostingMethod costing method
	 * @return Cost Element or null
	 */
	public static MCostElement getMaterialCostElement(Properties ctx, String CostingMethod)
	{
		MCostElement retValue = null;
		String sql = "SELECT * FROM M_CostElement WHERE AD_Client_ID=? AND CostingMethod=? ORDER BY AD_Org_ID";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, Env.getAD_Client_ID(ctx));
			pstmt.setString(2, CostingMethod);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				retValue = new MCostElement(ctx, rs, null);
			if (rs.next())
				s_log.info("More then one Material Cost Element for CostingMethod=" + CostingMethod);
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
		return retValue;
	}	// getMaterialCostElement

	/**
	 * Get active Material Cost Element for client
	 *
	 * @param contextAwareObj parent
	 * @return cost element array
	 */
	public static Collection<MCostElement> getCostElementsWithCostingMethods(final Object contextAwareObj)
	{
		final IContextAware context = InterfaceWrapperHelper.getContextAware(contextAwareObj);

		final String whereClause = "CostingMethod IS NOT NULL";
		return new Query(context.getCtx(), MCostElement.Table_Name, whereClause, context.getTrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list();
	}	// getCostElementCostingMethod

	/**
	 * Get active Material Cost Element for client
	 *
	 * @param po parent
	 * @return cost element array
	 */
	public static MCostElement[] getCostingMethods(PO po)
	{
		ArrayList<MCostElement> list = new ArrayList<MCostElement>();
		String sql = "SELECT * FROM M_CostElement "
				+ "WHERE AD_Client_ID=?"
				+ " AND IsActive='Y' AND CostElementType='M' AND CostingMethod IS NOT NULL";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, po.get_TrxName());
			pstmt.setInt(1, po.getAD_Client_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MCostElement(po.getCtx(), rs, po.get_TrxName()));
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
		MCostElement[] retValue = new MCostElement[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getMaterialCostElement

	// MZ Goodwill
	/**
	 * Get active non Material Cost Element for client
	 *
	 * @param po parent
	 * @return cost element array
	 */
	public static MCostElement[] getNonCostingMethods(PO po)
	{
		ArrayList<MCostElement> list = new ArrayList<MCostElement>();
		String sql = "SELECT * FROM M_CostElement "
				+ "WHERE AD_Client_ID=?"
				+ " AND IsActive='Y' AND CostingMethod IS NULL";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, po.get_TrxName());
			pstmt.setInt(1, po.getAD_Client_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MCostElement(po.getCtx(), rs, po.get_TrxName()));
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
		MCostElement[] retValue = new MCostElement[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getMaterialCostElement
	// end MZ

	/**
	 * Get Cost Element from Cache
	 *
	 * @param ctx context
	 * @param M_CostElement_ID id
	 * @return Cost Element
	 */
	public static MCostElement get(Properties ctx, int M_CostElement_ID)
	{
		Integer key = new Integer(M_CostElement_ID);
		MCostElement retValue = s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MCostElement(ctx, M_CostElement_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	} // get

	/**
	 * Get All Cost Elements for current AD_Client_ID
	 *
	 * @param ctx context
	 * @param trxName transaction
	 * @return array cost elements
	 */
	public static MCostElement[] getElements(Properties ctx, String trxName)
	{
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		int AD_Org_ID = 0; // Org is always ZERO - see beforeSave

		String whereClause = "AD_Client_ID = ? AND AD_Org_ID = ?";
		List<MCostElement> list = new Query(ctx, Table_Name, whereClause, trxName)
				.setParameters(new Object[] { AD_Client_ID, AD_Org_ID })
				.list();
		MCostElement[] retValue = new MCostElement[list.size()];
		list.toArray(retValue);
		return retValue;
	}

	/**
	 * Get All Cost Elements for current AD_Client_ID
	 *
	 * @param ctx context
	 * @param trxName transaction
	 * @return array cost elements
	 **/
	public static Collection<I_M_CostElement> getByCostingMethod(Properties ctx, String CostingMethod)
	{
		// modernizing this to get rid of the debug-message
		// org.adempiere.exceptions.AdempiereException: Query does not have a modelClass defined and no 'clazz' was specified as parameter.We need to avoid this case, but for now we are trying to do a force casting
		// in the log
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_CostElement.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_CostElement.COLUMNNAME_CostingMethod, CostingMethod)
				.create()
				.list();
	}

	/** Cache */
	private static CCache<Integer, MCostElement> s_cache = new CCache<Integer, MCostElement>("M_CostElement", 20);

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MCostElement.class);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param M_CostElement_ID id
	 * @param trxName trx
	 */
	public MCostElement(Properties ctx, int M_CostElement_ID, String trxName)
	{
		super(ctx, M_CostElement_ID, trxName);
		if (M_CostElement_ID == 0)
		{
			// setName (null);
			setCostElementType(COSTELEMENTTYPE_Material);
			setIsCalculated(false);
		}
	}	// MCostElement

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName trx
	 */
	public MCostElement(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MCostElement

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// Check Unique Costing Method
		if ((COSTELEMENTTYPE_Material.equals(getCostElementType())
				// || COSTELEMENTTYPE_Resource.equals(getCostElementType())
				// || COSTELEMENTTYPE_BurdenMOverhead.equals(getCostElementType())
				// || COSTELEMENTTYPE_Overhead.equals(getCostElementType())
				|| COSTELEMENTTYPE_OutsideProcessing.equals(getCostElementType()))
				&& (newRecord || is_ValueChanged(COLUMNNAME_CostingMethod)))
		{
			String sql = "SELECT  COALESCE(MAX(M_CostElement_ID),0) FROM M_CostElement "
					+ "WHERE AD_Client_ID=? AND CostingMethod=? AND CostElementType=?";
			int id = DB.getSQLValue(get_TrxName(), sql, getAD_Client_ID(), getCostingMethod(), getCostElementType());
			if (id > 0 && id != get_ID())
			{
				throw new AdempiereException("@AlreadyExists@ @CostingMethod@");
			}
		}

		// Maintain Calclated
		/*
		 * if (COSTELEMENTTYPE_Material.equals(getCostElementType()))
		 * {
		 * String cm = getCostingMethod();
		 * if (cm == null || cm.length() == 0
		 * || COSTINGMETHOD_StandardCosting.equals(cm))
		 * setIsCalculated(false);
		 * else
		 * setIsCalculated(true);
		 * }
		 * else
		 * {
		 * if (isCalculated())
		 * setIsCalculated(false);
		 * if (getCostingMethod() != null)
		 * setCostingMethod(null);
		 * }
		 */

		if (getAD_Org_ID() != 0)
			setAD_Org_ID(0);
		return true;
	}	// beforeSave

	/**
	 * Before Delete
	 *
	 * @return true if can be deleted
	 */
	@Override
	protected boolean beforeDelete()
	{
		String cm = getCostingMethod();
		if (cm == null
				|| !COSTELEMENTTYPE_Material.equals(getCostElementType()))
			return true;

		// Costing Methods on AS level
		MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID());
		for (int i = 0; i < ass.length; i++)
		{
			if (ass[i].getCostingMethod().equals(getCostingMethod()))
			{
				throw new AdempiereException("@CannotDeleteUsed@ @C_AcctSchema_ID@");
			}
		}

		// Costing Methods on PC level
		String sql = "SELECT M_Product_Category_ID FROM M_Product_Category_Acct WHERE AD_Client_ID=? AND CostingMethod=?";
		int M_Product_Category_ID = 0;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, getAD_Client_ID());
			pstmt.setString(2, getCostingMethod());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				M_Product_Category_ID = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
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
		if (M_Product_Category_ID != 0)
		{
			throw new AdempiereException("@CannotDeleteUsed@ @M_Product_Category_ID@ (ID=" + M_Product_Category_ID + ")");
		}
		return true;
	}	// beforeDelete

	/**
	 * Is this a Costing Method
	 *
	 * @return true if not Material cost or no costing method.
	 */
	public boolean isCostingMethod()
	{
		return COSTELEMENTTYPE_Material.equals(getCostElementType())
				&& getCostingMethod() != null;
	}	// isCostingMethod

	/**
	 * Is Avg Invoice Costing Method
	 *
	 * @return true if AverageInvoice
	 */
	public boolean isAverageInvoice()
	{
		String cm = getCostingMethod();
		return cm != null
				&& cm.equals(COSTINGMETHOD_AverageInvoice)
				&& COSTELEMENTTYPE_Material.equals(getCostElementType());
	}	// isAverageInvoice

	/**
	 * Is Avg PO Costing Method
	 *
	 * @return true if AveragePO
	 */
	public boolean isAveragePO()
	{
		String cm = getCostingMethod();
		return cm != null
				&& cm.equals(COSTINGMETHOD_AveragePO)
				&& COSTELEMENTTYPE_Material.equals(getCostElementType());
	}	// isAveragePO

	/**
	 * Is FiFo Costing Method
	 *
	 * @return true if Fifo
	 */
	public boolean isFifo()
	{
		String cm = getCostingMethod();
		return cm != null
				&& cm.equals(COSTINGMETHOD_Fifo)
				&& COSTELEMENTTYPE_Material.equals(getCostElementType());
	}	// isFifo

	/**
	 * Is Last Invoice Costing Method
	 *
	 * @return true if LastInvoice
	 */
	public boolean isLastInvoice()
	{
		String cm = getCostingMethod();
		return cm != null
				&& cm.equals(COSTINGMETHOD_LastInvoice)
				&& COSTELEMENTTYPE_Material.equals(getCostElementType());
	}	// isLastInvoice

	/**
	 * Is Last PO Costing Method
	 *
	 * @return true if LastPOPrice
	 */
	public boolean isLastPOPrice()
	{
		String cm = getCostingMethod();
		return cm != null
				&& cm.equals(COSTINGMETHOD_LastPOPrice)
				&& COSTELEMENTTYPE_Material.equals(getCostElementType());
	}	// isLastPOPrice

	/**
	 * Is LiFo Costing Method
	 *
	 * @return true if Lifo
	 */
	public boolean isLifo()
	{
		String cm = getCostingMethod();
		return cm != null
				&& cm.equals(COSTINGMETHOD_Lifo)
				&& COSTELEMENTTYPE_Material.equals(getCostElementType());
	}	// isLiFo

	/**
	 * Is Std Costing Method
	 *
	 * @return true if StandardCosting
	 */
	public boolean isStandardCosting()
	{
		String cm = getCostingMethod();
		return cm != null
				&& cm.equals(COSTINGMETHOD_StandardCosting)
				&& COSTELEMENTTYPE_Material.equals(getCostElementType());
	}	// isStandardCosting

	/**
	 * Is User Costing Method
	 *
	 * @return true if User Defined
	 */
	public boolean isUserDefined()
	{
		String cm = getCostingMethod();
		return cm != null
				&& cm.equals(COSTINGMETHOD_UserDefined)
				&& COSTELEMENTTYPE_Material.equals(getCostElementType());
	}	// isAveragePO

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MCostElement[");
		sb.append(get_ID())
				.append("-").append(getName())
				.append(",Type=").append(getCostElementType())
				.append(",Method=").append(getCostingMethod())
				.append("]");
		return sb.toString();
	} // toString

}	// MCostElement
