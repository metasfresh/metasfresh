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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * 	Cost Detail Model
 *	
 *  @author Jorg Janke
 *  @author Armen Rizal, Goodwill Consulting
 *  	<li>BF: 2431123 Return Trx changes weighted average cost
 *  	<li>BF: 1568752 Average invoice costing: landed costs incorrectly applied
 *  @author Armen Rizal & Bayu Cahya
 *  	<li>BF [ 2129781 ] Cost Detail not created properly for multi acc schema
 *  @author Teo Sarca
 *  	<li>BF [ 2847648 ] Manufacture & shipment cost errors
 *  		https://sourceforge.net/tracker/?func=detail&aid=2847648&group_id=176962&atid=934929
 *  @version $Id: MCostDetail.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 *  
 */
public class MCostDetail extends X_M_CostDetail
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5452006110417178583L;


	/**
	 * 	Create New Order Cost Detail for Purchase Orders.
	 * 	Called from Doc_MatchPO
	 *	@param as accounting schema
	 *	@param AD_Org_ID org
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param C_OrderLine_ID order
	 *	@param M_CostElement_ID optional cost element for Freight
	 *	@param Amt amt total amount
	 *	@param Qty qty
	 *	@param Description optional description
	 *	@param trxName transaction
	 *	@return true if created
	 */
	public static boolean createOrder (MAcctSchema as, int AD_Org_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID,
		int C_OrderLine_ID, int M_CostElement_ID, 
		BigDecimal Amt, BigDecimal Qty,
		String Description, String trxName)
	{
		//	Delete Unprocessed zero Differences
		String sql = "DELETE FROM M_CostDetail "
			+ "WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
			+ " AND C_OrderLine_ID=" + C_OrderLine_ID
			+ " AND C_AcctSchema_ID =" + as.getC_AcctSchema_ID()
			+ " AND M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID;
		int no = DB.executeUpdate(sql, trxName);
		if (no != 0)
			s_log.debug("Deleted #{}", no);
		MCostDetail cd = get (as.getCtx(), "C_OrderLine_ID=?", 
			C_OrderLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trxName);
			cd.setC_OrderLine_ID (C_OrderLine_ID);
		}
		else
		{
			// MZ Goodwill
			// set deltaAmt=Amt, deltaQty=qty, and set Cost Detail for Amt and Qty	 
			cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
			cd.setDeltaQty(Qty.subtract(cd.getQty()));
			if (cd.isDelta())
			{
				cd.setProcessed(false);
				cd.setAmt(Amt);
				cd.setQty(Qty);
			}
			// end MZ
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			MClient client = MClient.get(as.getCtx(), as.getAD_Client_ID());
			if (client.isCostImmediate())
				cd.process();
		}
		return ok;
	}	//	createOrder

	
	/**
	 * 	Create New Invoice Cost Detail for AP Invoices.
	 * 	Called from Doc_Invoice - for Invoice Adjustments
	 *	@param as accounting schema
	 *	@param AD_Org_ID org
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param C_InvoiceLine_ID invoice
	 *	@param M_CostElement_ID optional cost element for Freight
	 *	@param Amt amt
	 *	@param Qty qty
	 *	@param Description optional description
	 *	@param trxName transaction
	 *	@return true if created
	 */
	public static boolean createInvoice (MAcctSchema as, int AD_Org_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID,
		int C_InvoiceLine_ID, int M_CostElement_ID, 
		BigDecimal Amt, BigDecimal Qty,
		String Description, String trxName)
	{
		//	Delete Unprocessed zero Differences
		String sql = "DELETE FROM M_CostDetail "
			+ "WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
			+ " AND C_InvoiceLine_ID=" + C_InvoiceLine_ID
			+ " AND C_AcctSchema_ID =" + as.getC_AcctSchema_ID()			
			+ " AND M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID;
		int no = DB.executeUpdate(sql, trxName);
		if (no != 0)
			s_log.debug("Deleted #{}", no);
		MCostDetail cd = get (as.getCtx(), "C_InvoiceLine_ID=?", 
			C_InvoiceLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trxName);
			cd.setC_InvoiceLine_ID (C_InvoiceLine_ID);
		}
		else
		{
			// MZ Goodwill
			// set deltaAmt=Amt, deltaQty=qty, and set Cost Detail for Amt and Qty	 
			cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
			cd.setDeltaQty(Qty.subtract(cd.getQty()));
			if (cd.isDelta())
			{
				cd.setProcessed(false);
				cd.setAmt(Amt);
				cd.setQty(Qty);
			}
			// end MZ
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			MClient client = MClient.get(as.getCtx(), as.getAD_Client_ID());
			if (client.isCostImmediate())
				cd.process();
		}
		return ok;
	}	//	createInvoice
	
	/**
	 * 	Create New Shipment Cost Detail for SO Shipments.
	 * 	Called from Doc_MInOut - for SO Shipments  
	 *	@param as accounting schema
	 *	@param AD_Org_ID org
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param M_InOutLine_ID shipment
	 *	@param M_CostElement_ID optional cost element for Freight
	 *	@param Amt amt
	 *	@param Qty qty
	 *	@param Description optional description
	 *	@param IsSOTrx sales order
	 *	@param trxName transaction
	 *	@return true if no error
	 */
	public static boolean createShipment (MAcctSchema as, int AD_Org_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID,
		int M_InOutLine_ID, int M_CostElement_ID, 
		BigDecimal Amt, BigDecimal Qty,
		String Description, boolean IsSOTrx, String trxName)
	{
		//	Delete Unprocessed zero Differences
		String sql = "DELETE FROM M_CostDetail "
			+ "WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
			+ " AND M_InOutLine_ID=" + M_InOutLine_ID
			+ " AND C_AcctSchema_ID =" + as.getC_AcctSchema_ID()
			+ " AND M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID;
		int no = DB.executeUpdate(sql, trxName);
		if (no != 0)
			s_log.debug("Deleted #{}", no);
		MCostDetail cd = get (as.getCtx(), "M_InOutLine_ID=?", 
			M_InOutLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trxName);
			cd.setM_InOutLine_ID(M_InOutLine_ID);
			cd.setIsSOTrx(IsSOTrx);
		}
		else
		{
			// MZ Goodwill
		  // set deltaAmt=Amt, deltaQty=qty, and set Cost Detail for Amt and Qty	 
			cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
			cd.setDeltaQty(Qty.subtract(cd.getQty()));
			if (cd.isDelta())
			{
				cd.setProcessed(false);
				cd.setAmt(Amt);
				cd.setQty(Qty);
			}
			// end MZ
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			MClient client = MClient.get(as.getCtx(), as.getAD_Client_ID());
			if (client.isCostImmediate())
				cd.process();
		}
		return ok;
	}	//	createShipment
	
	public static List<I_M_CostDetail> retrieveForInOutLine(final I_M_InOutLine inoutLine)
	{
		Check.assumeNotNull(inoutLine, "inoutLine not null");
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostDetail.class, inoutLine)
				.addEqualsFilter(I_M_CostDetail.COLUMNNAME_M_InOutLine_ID, inoutLine.getM_InOutLine_ID())
				.create()
				.list();
	}

	/**
	 * 	Create New Order Cost Detail for Physical Inventory.
	 * 	Called from Doc_Inventory
	 *	@param as accounting schema
	 *	@param AD_Org_ID org
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param M_InventoryLine_ID order
	 *	@param M_CostElement_ID optional cost element
	 *	@param Amt amt total amount
	 *	@param Qty qty
	 *	@param Description optional description
	 *	@param trxName transaction
	 *	@return true if no error
	 */
	public static boolean createInventory (MAcctSchema as, int AD_Org_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID,
		int M_InventoryLine_ID, int M_CostElement_ID, 
		BigDecimal Amt, BigDecimal Qty,
		String Description, String trxName)
	{
		//	Delete Unprocessed zero Differences
		String sql = "DELETE FROM M_CostDetail "
			+ "WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
			+ " AND M_InventoryLine_ID=" + M_InventoryLine_ID
			+ " AND C_AcctSchema_ID =" + as.getC_AcctSchema_ID()
			+ " AND M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID;
		int no = DB.executeUpdate(sql, trxName);
		if (no != 0)
			s_log.debug("Deleted #{}", no);
		MCostDetail cd = get (as.getCtx(), "M_InventoryLine_ID=?", 
			M_InventoryLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trxName);
			cd.setM_InventoryLine_ID(M_InventoryLine_ID);
		}
		else
		{
			// MZ Goodwill
			// set deltaAmt=Amt, deltaQty=qty, and set Cost Detail for Amt and Qty	
			cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
			cd.setDeltaQty(Qty.subtract(cd.getQty()));
			if (cd.isDelta())
			{
				cd.setProcessed(false);
				cd.setAmt(Amt);
				cd.setQty(Qty);
			}
			// end MZ
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			MClient client = MClient.get(as.getCtx(), as.getAD_Client_ID());
			if (client.isCostImmediate())
				cd.process();
		}
		return ok;
	}	//	createInventory
	
	/**
	 * 	Create New Order Cost Detail for Movements.
	 * 	Called from Doc_Movement
	 *	@param as accounting schema
	 *	@param AD_Org_ID org
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param M_MovementLine_ID movement
	 *	@param M_CostElement_ID optional cost element for Freight
	 *	@param Amt amt total amount
	 *	@param Qty qty
	 *	@param from if true the from (reduction)
	 *	@param Description optional description
	 *	@param trxName transaction
	 *	@return true if no error
	 */
	public static boolean createMovement (MAcctSchema as, int AD_Org_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID,
		int M_MovementLine_ID, int M_CostElement_ID, 
		BigDecimal Amt, BigDecimal Qty, boolean from,
		String Description, String trxName)
	{
		//	Delete Unprocessed zero Differences
		String sql = "DELETE FROM M_CostDetail "
			+ "WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
			+ " AND M_MovementLine_ID=" + M_MovementLine_ID 
			+ " AND IsSOTrx=" + (from ? "'Y'" : "'N'")
			+ " AND C_AcctSchema_ID =" + as.getC_AcctSchema_ID()
			+ " AND M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID;
		int no = DB.executeUpdate(sql, trxName);
		if (no != 0)
			s_log.debug("Deleted #{}", no);
		MCostDetail cd = get (as.getCtx(), "M_MovementLine_ID=? AND IsSOTrx=" 
			+ (from ? "'Y'" : "'N'"), 
			M_MovementLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trxName);
			cd.setM_MovementLine_ID (M_MovementLine_ID);
			cd.setIsSOTrx(from);
		}
		else
		{
			// MZ Goodwill
			// set deltaAmt=Amt, deltaQty=qty, and set Cost Detail for Amt and Qty	
			cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
			cd.setDeltaQty(Qty.subtract(cd.getQty()));
			if (cd.isDelta())
			{
				cd.setProcessed(false);
				cd.setAmt(Amt);
				cd.setQty(Qty);
			}
			// end MZ
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			MClient client = MClient.get(as.getCtx(), as.getAD_Client_ID());
			if (client.isCostImmediate())
				cd.process();
		}
		return ok;
	}	//	createMovement

	/**
	 * 	Create New Order Cost Detail for Production.
	 * 	Called from Doc_Production
	 *	@param as accounting schema
	 *	@param AD_Org_ID org
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param M_ProductionLine_ID production line
	 *	@param M_CostElement_ID optional cost element
	 *	@param Amt amt total amount
	 *	@param Qty qty
	 *	@param Description optional description
	 *	@param trxName transaction
	 *	@return true if no error
	 */
	public static boolean createProduction (MAcctSchema as, int AD_Org_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID,
		int M_ProductionLine_ID, int M_CostElement_ID, 
		BigDecimal Amt, BigDecimal Qty,
		String Description, String trxName)
	{
		//	Delete Unprocessed zero Differences
		String sql = "DELETE FROM M_CostDetail "
			+ "WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
			+ " AND M_ProductionLine_ID=" + M_ProductionLine_ID
			+ " AND C_AcctSchema_ID =" + as.getC_AcctSchema_ID()
			+ " AND M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID;
		int no = DB.executeUpdate(sql, trxName);
		if (no != 0)
			s_log.debug("Deleted #{}", no);
		MCostDetail cd = get (as.getCtx(), "M_ProductionLine_ID=?", 
			M_ProductionLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trxName);
			cd.setM_ProductionLine_ID(M_ProductionLine_ID);
		}
		else
		{
			// MZ Goodwill
			// set deltaAmt=Amt, deltaQty=qty, and set Cost Detail for Amt and Qty	 
			cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
			cd.setDeltaQty(Qty.subtract(cd.getQty()));
			if (cd.isDelta())
			{
				cd.setProcessed(false);
				cd.setAmt(Amt);
				cd.setQty(Qty);
			}
			// end MZ
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			MClient client = MClient.get(as.getCtx(), as.getAD_Client_ID());
			if (client.isCostImmediate())
				cd.process();
		}
		return ok;
	}	//	createProduction
	
	
	/**************************************************************************
	 * 	Get Cost Detail
	 *	@param ctx context
	 *	@param whereClause where clause
	 *	@param ID 1st parameter
	 *  @param M_AttributeSetInstance_ID ASI
	 *	@param trxName trx
	 *	@return cost detail
	 */
	public static MCostDetail get (Properties ctx, String whereClause, 
		int ID, int M_AttributeSetInstance_ID, int C_AcctSchema_ID,
		final String trxName)
	{
		final String sql = "SELECT * FROM M_CostDetail WHERE " + whereClause
			+ " AND M_AttributeSetInstance_ID=?"
			+ " AND C_AcctSchema_ID=?";
		MCostDetail retValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, ID);
			pstmt.setInt (2, M_AttributeSetInstance_ID);
			pstmt.setInt (3, C_AcctSchema_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = new MCostDetail (ctx, rs, trxName);
			}
			if (rs.next())
			{
				s_log.error("More then one M_CostDetail found for ID=" + ID + ", SQL=sql. Returning the first one");
			}
		}
		catch (Exception e)
		{
			s_log.error(sql + " - " + ID, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return retValue;
	}	//	get
	
	/**
	 * 	Process Cost Details for product
	 *	@param product product
	 *	@param trxName transaction
	 *	@return true if no error
	 */
	public static boolean processProduct (final I_M_Product product, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		final String sql = "SELECT * FROM M_CostDetail "
			+ "WHERE M_Product_ID=?"
			+ " AND Processed='N' "
			+ "ORDER BY C_AcctSchema_ID, M_CostElement_ID, AD_Org_ID, M_AttributeSetInstance_ID, Created";
		int counterOK = 0;
		int counterError = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, product.getM_Product_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				final MCostDetail cd = new MCostDetail(ctx, rs, trxName);
				if (cd.process())	//	saves
					counterOK++;
				else
					counterError++;
			}
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
			counterError++;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		s_log.debug("processProduct: OK={}, Errors={}", counterOK, counterError);
		return counterError == 0;
	}	//	processProduct
	
	/**	Logger	*/
	private static Logger 	s_log = LogManager.getLogger(MCostDetail.class);
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_CostDetail_ID id
	 *	@param trxName trx
	 */
	public MCostDetail (Properties ctx, int M_CostDetail_ID, String trxName)
	{
		super (ctx, M_CostDetail_ID, trxName);
		if (M_CostDetail_ID <= 0)
		{
		//	setC_AcctSchema_ID (0);
		//	setM_Product_ID (0);
			setM_AttributeSetInstance_ID (0);
		//	setC_OrderLine_ID (0);
		//	setM_InOutLine_ID(0);
		//	setC_InvoiceLine_ID (0);
			setProcessed (false);
			setAmt (Env.ZERO);
			setQty (Env.ZERO);
			setIsSOTrx (false);
			setDeltaAmt (Env.ZERO);
			setDeltaQty (Env.ZERO);
		}	
	}	//	MCostDetail

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MCostDetail (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MCostDetail

	/**
	 * 	New Constructor
	 *	@param as accounting schema
	 *	@param AD_Org_ID org
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param M_CostElement_ID optional cost element for Freight
	 *	@param Amt amt
	 *	@param Qty qty
	 *	@param Description optional description
	 *	@param trxName transaction
	 */
	public MCostDetail (I_C_AcctSchema as, int AD_Org_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID,
		int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty,
		String Description, String trxName)
	{
		this (InterfaceWrapperHelper.getCtx(as),
				0,
				trxName);
		setClientOrg(as.getAD_Client_ID(), AD_Org_ID);
		setC_AcctSchema_ID (as.getC_AcctSchema_ID());
		setM_Product_ID (M_Product_ID);
		setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
		//
		setM_CostElement_ID(M_CostElement_ID);
		//
		setAmt (Amt);
		setQty (Qty);
		setDescription(Description);
	}	//	MCostDetail
	
	/**
	 * 	Set Amt
	 *	@param Amt amt
	 */
	@Override
	public void setAmt (BigDecimal Amt)
	{
		if (isProcessed())
			throw new IllegalStateException("Cannot change Amt - processed");
		if (Amt == null)
			super.setAmt (Env.ZERO);
		else
			super.setAmt (Amt);
	}	//	setAmt
	
	/**
	 * 	Set Qty
	 *	@param Qty qty
	 */
	@Override
	public void setQty (BigDecimal Qty)
	{
		if (isProcessed())
			throw new IllegalStateException("Cannot change Qty - processed");
		if (Qty == null)
			super.setQty (Env.ZERO);
		else
			super.setQty (Qty);
	}	//	setQty

	/**
	 * 	Is Order
	 *	@return true if order line
	 */
	public boolean isOrder()
	{
		return getC_OrderLine_ID() != 0;
	}	//	isOrder

	/**
	 * 	Is Invoice
	 *	@return true if invoice line
	 */
	public boolean isInvoice()
	{
		return getC_InvoiceLine_ID() != 0;
	}	//	isInvoice

	/**
	 * 	Is Shipment
	 *	@return true if sales order shipment
	 */
	public boolean isShipment()
	{
		return isSOTrx() && getM_InOutLine_ID() != 0;
	}	//	isShipment
	
	/**
	 * 	Is this a Delta Record (previously processed)?
	 *	@return true if delta is not null
	 */
	public boolean isDelta()
	{
		return !(getDeltaAmt().signum() == 0 
			&& getDeltaQty().signum() == 0);
	}	//	isDelta
	
	
	/**
	 * 	Before Delete
	 *	@return false if processed
	 */
	@Override
	protected boolean beforeDelete ()
	{
		return !isProcessed();
	}	//	beforeDelete
	
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MCostDetail[");
		sb.append (get_ID());
		if (getC_OrderLine_ID() != 0)
			sb.append (",C_OrderLine_ID=").append (getC_OrderLine_ID());
		if (getM_InOutLine_ID() != 0)
			sb.append (",M_InOutLine_ID=").append (getM_InOutLine_ID());
		if (getC_InvoiceLine_ID() != 0)
			sb.append (",C_InvoiceLine_ID=").append (getC_InvoiceLine_ID());
		if (getC_ProjectIssue_ID() != 0)
			sb.append (",C_ProjectIssue_ID=").append (getC_ProjectIssue_ID());
		if (getM_MovementLine_ID() != 0)
			sb.append (",M_MovementLine_ID=").append (getM_MovementLine_ID());
		if (getM_InventoryLine_ID() != 0)
			sb.append (",M_InventoryLine_ID=").append (getM_InventoryLine_ID());
		if (getM_ProductionLine_ID() != 0)
			sb.append (",M_ProductionLine_ID=").append (getM_ProductionLine_ID());
		sb.append(",Amt=").append(getAmt())
			.append(",Qty=").append(getQty());
		if (isDelta())
			sb.append(",DeltaAmt=").append(getDeltaAmt())
				.append(",DeltaQty=").append(getDeltaQty());
		sb.append ("]");
		return sb.toString ();
	}	//	toString
	
	
	/**************************************************************************
	 * 	Process Cost Detail Record.
	 * 	The record is saved if processed.
	 *	@return true if processed
	 */
	public synchronized boolean process()
	{
		if (isProcessed())
		{
			log.debug("Already processed");
			return true;
		}
		boolean ok = false;

		//	get costing level for product
		MAcctSchema as = MAcctSchema.get(getCtx(), getC_AcctSchema_ID());
		MProduct product = MProduct.get(getCtx(), getM_Product_ID());
		String CostingLevel = product.getCostingLevel(as);
		//	Org Element
		int Org_ID = getAD_Org_ID();
		int M_ASI_ID = getM_AttributeSetInstance_ID();
		if (MAcctSchema.COSTINGLEVEL_Client.equals(CostingLevel))
		{
			Org_ID = 0;
			M_ASI_ID = 0;
		}
		else if (MAcctSchema.COSTINGLEVEL_Organization.equals(CostingLevel))
			M_ASI_ID = 0;
		else if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(CostingLevel))
			Org_ID = 0;

		//	Create Material Cost elements
		if (getM_CostElement_ID() <= 0)
		{
			final MCostElement[] ces = MCostElement.getCostingMethods(this);
			for (int i = 0; i < ces.length; i++)
			{
				MCostElement ce = ces[i];
				ok = process (as, product, ce, Org_ID, M_ASI_ID);
				if (!ok)
					break;
			}
		}	//	Material Cost elements
		else
		{
			MCostElement ce = MCostElement.get(getCtx(), getM_CostElement_ID());
			ok = process (as, product, ce, Org_ID, M_ASI_ID);
		}
		
		//	Save it
		if (ok)
		{
			setDeltaAmt(null);
			setDeltaQty(null);
			setProcessed(true);
			ok = save();
		}
		log.debug("processed: ok={} - {}", ok, this);
		return ok;
	}	//	process
	
	/**
	 * 	Process cost detail for cost record
	 *	@param as accounting schema
	 *	@param product product
	 *	@param ce cost element
	 *	@param Org_ID org - corrected for costing level
	 *	@param M_ASI_ID - asi corrected for costing level
	 *	@return true if cost ok
	 */
	private boolean process (MAcctSchema as, MProduct product, MCostElement ce, 
		int Org_ID, int M_ASI_ID)
	{
		final String trxName = get_TrxName();
		
		MCost cost = MCost.get(product, M_ASI_ID, as, 
			Org_ID, ce.getM_CostElement_ID(), trxName);
	//	if (cost == null)
	//		cost = new MCost(product, M_ASI_ID, 
	//			as, Org_ID, ce.getM_CostElement_ID());
		
		// MZ Goodwill
		// used deltaQty and deltaAmt if exist 
		final BigDecimal qty;
		final BigDecimal amt;
		if (isDelta())
		{
			qty = getDeltaQty();
			amt = getDeltaAmt();
		}
		else
		{
			qty = getQty();
			amt = getAmt();
		}
		// end MZ
		
		int precision = as.getCostingPrecision();
		BigDecimal price = amt;
		if (qty.signum() != 0)
			price = amt.divide(qty, precision, BigDecimal.ROUND_HALF_UP);
		
		/** All Costing Methods
		if (ce.isAverageInvoice())
		else if (ce.isAveragePO())
		else if (ce.isFifo())
		else if (ce.isLifo())
		else if (ce.isLastInvoice())
		else if (ce.isLastPOPrice())
		else if (ce.isStandardCosting())
		else if (ce.isUserDefined())
		else if (!ce.isCostingMethod())
		**/
		
		//	*** Purchase Order Detail Record ***
		if (getC_OrderLine_ID() != 0)
		{		
			boolean isReturnTrx = qty.signum() < 0;
			
			if (ce.isAveragePO())
			{
				cost.setWeightedAverage(amt, qty);
				log.trace("PO - AveragePO - " + cost);
			}
			else if (ce.isLastPOPrice())
			{
				if(!isReturnTrx)
				{
					if (qty.signum() != 0)
						cost.setCurrentCostPrice(price);
					else
					{
						BigDecimal cCosts = cost.getCurrentCostPrice().add(amt);
						cost.setCurrentCostPrice(cCosts);
					}
				}
				cost.add(amt, qty);
				log.trace("PO - LastPO - " + cost);
			}
			else if (ce.isUserDefined())
			{
				//	Interface
				log.trace("PO - UserDef - " + cost);
			}
			else if (!ce.isCostingMethod())
			{
				log.trace("PO - " + ce + " - " + cost);
			}
		//	else
		//		log.warn("PO - " + ce + " - " + cost);
		}
		
		//	*** AP Invoice Detail Record ***
		else if (getC_InvoiceLine_ID() != 0)
		{
			boolean isReturnTrx = qty.signum() < 0;
			
			if (ce.isAverageInvoice())
			{
				cost.setWeightedAverage(amt, qty);
				log.trace("Inv - AverageInv - " + cost);
			}
			else if (ce.isFifo()
				|| ce.isLifo())
			{
				//	Real ASI - costing level Org
				MCostQueue cq = MCostQueue.get(product, getM_AttributeSetInstance_ID(), 
					as, Org_ID, ce.getM_CostElement_ID(), trxName);
				cq.setCosts(amt, qty, precision);
				cq.save();
				//	Get Costs - costing level Org/ASI
				MCostQueue[] cQueue = MCostQueue.getQueue(product, M_ASI_ID, 
					as, Org_ID, ce, trxName);
				if (cQueue != null && cQueue.length > 0)
					cost.setCurrentCostPrice(cQueue[0].getCurrentCostPrice());
				cost.add(amt, qty);
				log.trace("Inv - FiFo/LiFo - " + cost);
			}
			else if (ce.isLastInvoice())
			{
				if (!isReturnTrx)
				{
					if (qty.signum() != 0)
						cost.setCurrentCostPrice(price);
					else
					{
						BigDecimal cCosts = cost.getCurrentCostPrice().add(amt);
						cost.setCurrentCostPrice(cCosts);
					}
				}
				cost.add(amt, qty);
				log.trace("Inv - LastInv - " + cost);
			}
			else if (ce.isStandardCosting())
			{
				// Update cost record only if newly created.
				// Elsewhere we risk to set the CurrentCostPrice to an undesired price. 
				if (cost.is_new()
						&& cost.getCurrentCostPrice().signum() == 0
						&& cost.getCurrentCostPriceLL().signum() == 0)
				{
					cost.setCurrentCostPrice(price);
					//	seed initial price
					if (cost.getCurrentCostPrice().signum() == 0)
					{
						cost.setCurrentCostPrice(MCost.getSeedCosts(product, M_ASI_ID, 
								as, Org_ID, ce.getCostingMethod(), getC_OrderLine_ID(),
								trxName));
						log.trace("Inv - Standard - CurrentCostPrice(seed)="+cost.getCurrentCostPrice()+", price="+price);
					}
				}
				cost.add(amt, qty);
				log.trace("Inv - Standard - " + cost);
			}
			else if (ce.isUserDefined())
			{
				//	Interface
				cost.add(amt, qty);
				log.trace("Inv - UserDef - " + cost);
			}
			else if (!ce.isCostingMethod())		//	Cost Adjustments
			{
				// AZ Goodwill
				//get costing method for product
				String costingMethod = product.getCostingMethod(as);				
				if (MAcctSchema.COSTINGMETHOD_AveragePO.equals(costingMethod) ||
					MAcctSchema.COSTINGMETHOD_AverageInvoice.equals(costingMethod))
				{
					/**	Problem with Landed Costs: certain cost element may not occur in every purchases, 
					 *  causing the average calculation of that cost element wrongly took the current qty.
					 *  
					 *  Solution:
					 *  Make sure the current qty is reflecting the actual qty in storage
					 */
					String sql = "SELECT COALESCE(SUM(QtyOnHand),0) FROM M_Storage"					
						+ " WHERE AD_Client_ID=" + cost.getAD_Client_ID()
						+ " AND M_Product_ID=" + cost.getM_Product_ID();
					//Costing Level
					String CostingLevel = product.getCostingLevel(as);			
					if (MAcctSchema.COSTINGLEVEL_Organization.equals(CostingLevel))
						sql += " AND AD_Org_ID=" + cost.getAD_Org_ID();
					else if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(CostingLevel))
						sql += " AND M_AttributeSetInstance_ID=" + M_ASI_ID;	
					//
					BigDecimal qtyOnhand = DB.getSQLValueBD(trxName, sql);					
					if (qtyOnhand.signum() != 0)
					{
						BigDecimal oldSum = cost.getCurrentCostPrice().multiply(cost.getCurrentQty());
						BigDecimal sumAmt = oldSum.add(amt);	//	amt is total already
						BigDecimal costs = sumAmt.divide(qtyOnhand, precision, BigDecimal.ROUND_HALF_UP);
						cost.setCurrentCostPrice(costs);
					}
					cost.setCumulatedAmt(cost.getCumulatedAmt().add(amt));
					cost.setCumulatedQty(cost.getCumulatedQty().add(qty));
					cost.setCurrentQty(qtyOnhand);						
				}
				else //original logic from Compiere
				{
					BigDecimal cCosts = cost.getCurrentCostPrice().add(amt);
					cost.setCurrentCostPrice(cCosts);
					cost.add(amt, qty);
				}
				// end AZ
				log.trace("Inv - Landed Costs - " + cost);
			}
		//	else
		//		log.warn("Inv - " + ce + " - " + cost);
		}
		
		//	*** Qty Adjustment Detail Record ***
		else if (getM_InOutLine_ID() != 0 		//	AR Shipment Detail Record  
			|| getM_MovementLine_ID() != 0 
			|| getM_InventoryLine_ID() != 0
			|| getM_ProductionLine_ID() != 0
			|| getC_ProjectIssue_ID() != 0
			|| getPP_Cost_Collector_ID() != 0)
		{
			boolean addition = qty.signum() > 0;
			//
			if (ce.isAverageInvoice())
			{
				if (addition)
					cost.setWeightedAverage(amt, qty);
				else
					cost.setCurrentQty(cost.getCurrentQty().add(qty));
				log.trace("QtyAdjust - AverageInv - " + cost);
			}
			else if (ce.isAveragePO())
			{
				if (addition)
					cost.setWeightedAverage(amt, qty);
				else
					cost.setCurrentQty(cost.getCurrentQty().add(qty));
				log.trace("QtyAdjust - AveragePO - " + cost);
			}
			else if (ce.isFifo() || ce.isLifo())
			{
				if (addition)
				{
					//	Real ASI - costing level Org
					MCostQueue cq = MCostQueue.get(product, getM_AttributeSetInstance_ID(), 
						as, Org_ID, ce.getM_CostElement_ID(), trxName);
					cq.setCosts(amt, qty, precision);
					cq.save();
				}
				else
				{
					//	Adjust Queue - costing level Org/ASI
					MCostQueue.adjustQty(product, M_ASI_ID, 
						as, Org_ID, ce, qty.negate(), trxName);
				}
				//	Get Costs - costing level Org/ASI
				MCostQueue[] cQueue = MCostQueue.getQueue(product, M_ASI_ID, 
					as, Org_ID, ce, trxName);
				if (cQueue != null && cQueue.length > 0)
					cost.setCurrentCostPrice(cQueue[0].getCurrentCostPrice());
				cost.setCurrentQty(cost.getCurrentQty().add(qty));
				log.trace("QtyAdjust - FiFo/Lifo - " + cost);
			}
			else if (ce.isLastInvoice())
			{
				cost.setCurrentQty(cost.getCurrentQty().add(qty));
				log.trace("QtyAdjust - LastInv - " + cost);
			}
			else if (ce.isLastPOPrice())
			{
				cost.setCurrentQty(cost.getCurrentQty().add(qty));
				log.trace("QtyAdjust - LastPO - " + cost);
			}
			else if (ce.isStandardCosting())
			{
				if (addition)
				{
					cost.add(amt, qty);
					//	Initial
					if (cost.getCurrentCostPrice().signum() == 0
						&& cost.getCurrentCostPriceLL().signum() == 0
						&& cost.is_new())
					{
						cost.setCurrentCostPrice(price);
						log.trace("QtyAdjust - Standard - CurrentCostPrice="+price);
					}
				}
				else
				{
					cost.setCurrentQty(cost.getCurrentQty().add(qty));
				}
				log.trace("QtyAdjust - Standard - " + cost);
			}
			else if (ce.isUserDefined())
			{
				//	Interface
				if (addition)
					cost.add(amt, qty);
				else
					cost.setCurrentQty(cost.getCurrentQty().add(qty));
				log.trace("QtyAdjust - UserDef - " + cost);
			}
			else if (!ce.isCostingMethod())
			{
			//	Should not happen
				log.trace("QtyAdjust - ?none? - " + cost);
			}
			else
				log.warn("QtyAdjust - " + ce + " - " + cost);
			
			//AZ Goodwill
			//Also update Landed Costs to reflect the actual qty in storage
			String costingMethod = ce.getCostingMethod();
			if (MAcctSchema.COSTINGMETHOD_AveragePO.equals(costingMethod) ||
				MAcctSchema.COSTINGMETHOD_AverageInvoice.equals(costingMethod))
			{				
				MCostElement[] lce = MCostElement.getNonCostingMethods(this);
				if (lce.length > 0)
				{					
					String sql = "SELECT COALESCE(SUM(QtyOnHand),0) FROM M_Storage"					
						+ " WHERE AD_Client_ID=" + cost.getAD_Client_ID()
						+ " AND M_Product_ID=" + cost.getM_Product_ID();
					//Costing Level
					String CostingLevel = product.getCostingLevel(as);
					if (MAcctSchema.COSTINGLEVEL_Organization.equals(CostingLevel))
						sql += " AND AD_Org_ID=" + cost.getAD_Org_ID();
					else if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(CostingLevel))
						sql += " AND M_AttributeSetInstance_ID=" + M_ASI_ID;	
					//
					BigDecimal qtyOnhand = DB.getSQLValueBD(trxName, sql);
					for (int i = 0 ; i < lce.length ; i++)
					{
						MCost lCost = MCost.get(getCtx(), cost.getAD_Client_ID(), cost.getAD_Org_ID(), 
							cost.getM_Product_ID(), cost.getM_CostType_ID(), cost.getC_AcctSchema_ID(), 
							lce[i].getM_CostElement_ID(), cost.getM_AttributeSetInstance_ID(), trxName);
						if (lCost != null)
						{
							if (qtyOnhand.signum() != 0)
							{
								// new average cost
								BigDecimal oldSum = lCost.getCurrentCostPrice().multiply(lCost.getCurrentQty());
								BigDecimal costs = oldSum.divide(qtyOnhand, precision, BigDecimal.ROUND_HALF_UP);
								lCost.setCurrentCostPrice(costs);	
							}
							lCost.setCurrentQty(qtyOnhand);
							if (!lCost.save())
							{
								log.warn("Update Landed Costs (Qty) fail: " + lce + " - " + lCost);
								return false;
							}
						}
					}					
				}//end-if
			}
			//end AZ
		}
		else	//	unknown or no id
		{
			log.warn("Unknown Type: " + toString());
			return false;
		}
		return cost.save();
	}	//	process
	
	// Elaine 2008/6/20	
	@Override
	protected boolean afterDelete (boolean success)
	{
		if(success)
		{
			// recalculate MCost			
			boolean ok = false;
			//	get costing level for product
			MAcctSchema as = new MAcctSchema (getCtx(), getC_AcctSchema_ID(), null);
			MProduct product = MProduct.get(getCtx(), getM_Product_ID());
			String CostingLevel = product.getCostingLevel(as);
			//	Org Element
			int Org_ID = getAD_Org_ID();
			int M_ASI_ID = getM_AttributeSetInstance_ID();
			if (MAcctSchema.COSTINGLEVEL_Client.equals(CostingLevel))
			{
				Org_ID = 0;
				M_ASI_ID = 0;
			}
			else if (MAcctSchema.COSTINGLEVEL_Organization.equals(CostingLevel))
				M_ASI_ID = 0;
			else if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(CostingLevel))
				Org_ID = 0;

			//	Create Material Cost elements
			if (getM_CostElement_ID() == 0)
			{
				MCostElement[] ces = MCostElement.getCostingMethods(this);
				for (int i = 0; i < ces.length; i++)
				{
					MCostElement ce = ces[i];
					ok = process (as, product, ce, Org_ID, M_ASI_ID);
					if (!ok)
						break;
				}
			}	//	Material Cost elements
			else
			{
				MCostElement ce = MCostElement.get(getCtx(), getM_CostElement_ID());
				ok = process (as, product, ce, Org_ID, M_ASI_ID);
			}
			
			return ok;
		}
		
		return super.afterDelete(success);
	}
	//

	public static void reverseAndDelete(final I_M_CostDetail cd)
	{
		if (!cd.isProcessed())
		{
			InterfaceWrapperHelper.delete(cd);
			return;
		}

		final MCostDetail cdPO = LegacyAdapters.convertToPO(cd);
		cdPO.setProcessed(false);
		cdPO.setDeltaAmt(cdPO.getAmt());
		cdPO.setDeltaQty(cdPO.getQty());
		if (!cdPO.process())
		{
			throw new AdempiereException("Cannot reverse " + cd + "."
					+ "\nCheck console for more details.");
		}
		Check.assume(cdPO.isProcessed(), "Cost detail {} shall be processed at this point", cdPO); // shall not happen

		cdPO.setProcessed(false);
		InterfaceWrapperHelper.delete(cdPO);
	}
}	//	MCostDetail
