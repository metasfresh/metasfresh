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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.NoVendorForProductException;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.MBPartner;
import org.compiere.model.MCharge;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.POResultSet;
import org.compiere.model.Query;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.purchasing.api.IBPartnerProductDAO;

/**
 * 	Create PO from Requisition 
 *	
 *	
 *  @author Jorg Janke
 *  @version $Id: RequisitionPOCreate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 *  
 *  @author Teo Sarca, www.arhipac.ro
 *  		<li>BF [ 2609760 ] RequisitionPOCreate not using DateRequired
 *  		<li>BF [ 2605888 ] CreatePOfromRequisition creates more PO than needed
 *  		<li>BF [ 2811718 ] Create PO from Requsition without any parameter teminate in NPE
 *  			http://sourceforge.net/tracker/?func=detail&atid=879332&aid=2811718&group_id=176962
 *  		<li>FR [ 2844074  ] Requisition PO Create - more selection fields
 *  			https://sourceforge.net/tracker/?func=detail&aid=2844074&group_id=176962&atid=879335
 */
public class RequisitionPOCreate extends SvrProcess
{
	/** Org					*/
	private int			p_AD_Org_ID = 0;
	/** Warehouse			*/
	private int			p_M_Warehouse_ID = 0;
	/**	Doc Date From		*/
	private Timestamp	p_DateDoc_From;
	/**	Doc Date To			*/
	private Timestamp	p_DateDoc_To;
	/**	Doc Date From		*/
	private Timestamp	p_DateRequired_From;
	/**	Doc Date To			*/
	private Timestamp	p_DateRequired_To;
	/** Priority			*/
	private String		p_PriorityRule = null;
	/** User				*/
	private int			p_AD_User_ID = 0;
	/** Product				*/
	private int			p_M_Product_ID = 0;
	/** Product	Category	*/
	private int			p_M_Product_Category_ID = 0;
	/** BPartner Group	*/
	private int			p_C_BP_Group_ID = 0;
	/** Requisition			*/
	private int 		p_M_Requisition_ID = 0;

	/** Consolidate			*/
	private boolean		p_ConsolidateDocument = false;

	/** Order				*/
	private MOrder		m_order = null;
	/** Order Line			*/
	private MOrderLine	m_orderLine = null;
	/** Orders Cache : (C_BPartner_ID, DateRequired, M_PriceList_ID) -> MOrder */
	private HashMap<ArrayKey, MOrder> m_cacheOrders = new HashMap<ArrayKey, MOrder>();
	
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
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID = para[i].getParameterAsInt();
			else if (name.equals("DateDoc"))
			{
				p_DateDoc_From = (Timestamp)para[i].getParameter();
				p_DateDoc_To = (Timestamp)para[i].getParameter_To();
			}
			else if (name.equals("DateRequired"))
			{
				p_DateRequired_From = (Timestamp)para[i].getParameter();
				p_DateRequired_To = (Timestamp)para[i].getParameter_To();
			}
			else if (name.equals("PriorityRule"))
				p_PriorityRule = (String)para[i].getParameter();
			else if (name.equals("AD_User_ID"))
				p_AD_User_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Product_ID"))
				p_M_Product_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Product_Category_ID"))
				p_M_Product_Category_ID = para[i].getParameterAsInt();
			else if (name.equals("C_BP_Group_ID"))
				p_C_BP_Group_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Requisition_ID"))
				p_M_Requisition_ID = para[i].getParameterAsInt();
			else if (name.equals("ConsolidateDocument"))
				p_ConsolidateDocument = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare
	
	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		//	Specific
		if (p_M_Requisition_ID != 0)
		{
			log.info("M_Requisition_ID=" + p_M_Requisition_ID);
			MRequisition req = new MRequisition(getCtx(), p_M_Requisition_ID, get_TrxName());
			if (!MRequisition.DOCSTATUS_Completed.equals(req.getDocStatus()))
			{
				throw new AdempiereUserError("@DocStatus@ = " + req.getDocStatus());
			}
			MRequisitionLine[] lines = req.getLines();
			for (int i = 0; i < lines.length; i++)
			{
				if (lines[i].getC_OrderLine_ID() == 0)
				{
					process (lines[i]);
				}
			}
			closeOrder();
			return "";
		}	//	single Requisition
		
		//	
		log.info("AD_Org_ID=" + p_AD_Org_ID
			+ ", M_Warehouse_ID=" + p_M_Warehouse_ID
			+ ", DateDoc=" + p_DateDoc_From + "/" + p_DateDoc_To
			+ ", DateRequired=" + p_DateRequired_From + "/" + p_DateRequired_To
			+ ", PriorityRule=" + p_PriorityRule
			+ ", AD_User_ID=" + p_AD_User_ID
			+ ", M_Product_ID=" + p_M_Product_ID
			+ ", ConsolidateDocument" + p_ConsolidateDocument);
		
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer("C_OrderLine_ID IS NULL");
		if (p_AD_Org_ID > 0)
		{
			whereClause.append(" AND AD_Org_ID=?");
			params.add(p_AD_Org_ID);
		}
		if (p_M_Product_ID > 0)
		{
			whereClause.append(" AND M_Product_ID=?");
			params.add(p_M_Product_ID);
		}
		else if (p_M_Product_Category_ID > 0)
		{
			whereClause.append(" AND EXISTS (SELECT 1 FROM M_Product p WHERE M_RequisitionLine.M_Product_ID=p.M_Product_ID")
				.append(" AND p.M_Product_Category_ID=?)");
			params.add(p_M_Product_Category_ID);
		}
		
		if (p_C_BP_Group_ID > 0)
		{
			whereClause.append(" AND (")
			.append("M_RequisitionLine.C_BPartner_ID IS NULL")
			.append(" OR EXISTS (SELECT 1 FROM C_BPartner bp WHERE M_RequisitionLine.C_BPartner_ID=bp.C_BPartner_ID AND bp.C_BP_Group_ID=?)")
			.append(")");
			params.add(p_C_BP_Group_ID);
		}
		
		//
		//	Requisition Header
		whereClause.append(" AND EXISTS (SELECT 1 FROM M_Requisition r WHERE M_RequisitionLine.M_Requisition_ID=r.M_Requisition_ID")
			.append(" AND r.DocStatus=?");
		params.add(MRequisition.DOCSTATUS_Completed);
		if (p_M_Warehouse_ID > 0)
		{
			whereClause.append(" AND r.M_Warehouse_ID=?");
			params.add(p_M_Warehouse_ID);
		}
		if (p_DateDoc_From != null)
		{
			whereClause.append(" AND r.DateDoc => ?");
			params.add(p_DateDoc_From);
		}
		if (p_DateDoc_To != null)
		{
			whereClause.append(" AND r.DateDoc <= ?");
			params.add(p_DateDoc_To);
		}
		if (p_DateRequired_From != null)
		{
			whereClause.append(" AND r.DateRequired => ?");
			params.add(p_DateRequired_From);
		}
		if (p_DateRequired_To != null)
		{
			whereClause.append(" AND r.DateRequired <= ?");
			params.add(p_DateRequired_To);
		}
		if (p_PriorityRule != null)
		{
			whereClause.append(" AND r.PriorityRule => ?");
			params.add(p_PriorityRule);
		}
		if (p_AD_User_ID > 0)
		{
			whereClause.append(" AND r.AD_User_ID=?");
			params.add(p_AD_User_ID);
		}
		whereClause.append(")"); // End Requisition Header
		//
		// ORDER BY clause
		StringBuffer orderClause = new StringBuffer();
		if (!p_ConsolidateDocument)
		{
			orderClause.append("M_Requisition_ID, ");
		}
		orderClause.append("(SELECT DateRequired FROM M_Requisition r WHERE M_RequisitionLine.M_Requisition_ID=r.M_Requisition_ID),");
		orderClause.append("M_Product_ID, C_Charge_ID, M_AttributeSetInstance_ID");
		
		POResultSet<MRequisitionLine> rs = new Query(getCtx(), MRequisitionLine.Table_Name, whereClause.toString(), get_TrxName())
											.setParameters(params)
											.setOrderBy(orderClause.toString())
											.setClient_ID()
											.scroll();
		try
		{
			while (rs.hasNext())
			{
				process(rs.next());
			}
		}
		finally
		{
			DB.close(rs); rs = null;
		}
		closeOrder();
		return "";
	}	//	doit
	
	private int 		m_M_Requisition_ID = 0;
	private int 		m_M_Product_ID = 0;
	private int			m_M_AttributeSetInstance_ID = 0;
	/** BPartner				*/
	private MBPartner	m_bpartner = null;
	
	/**
	 * 	Process Line
	 *	@param rLine request line
	 * 	@throws Exception
	 */
	private void process (MRequisitionLine rLine) throws Exception
	{
		if (rLine.getM_Product_ID() == 0 && rLine.getC_Charge_ID() == 0)
		{
			log.warn("Ignored Line" + rLine.getLine() 
				+ " " + rLine.getDescription()
				+ " - " + rLine.getLineNetAmt());
			return;
		}
		
		if (!p_ConsolidateDocument && rLine.getM_Requisition_ID() != m_M_Requisition_ID)
		{
			closeOrder();
		}
		if (m_orderLine == null
			|| rLine.getM_Product_ID() != m_M_Product_ID
			|| rLine.getM_AttributeSetInstance_ID() != m_M_AttributeSetInstance_ID
			|| rLine.getC_Charge_ID() != 0		//	single line per charge
			|| m_order == null
			|| m_order.getDatePromised().compareTo(rLine.getDateRequired()) != 0
			)
		{
			newLine(rLine);
			// No Order Line was produced (vendor was not valid/allowed) => SKIP
			if (m_orderLine == null)
				return;
		}

		//	Update Order Line
		m_orderLine.setQty(m_orderLine.getQtyOrdered().add(rLine.getQty()));
		//	Update Requisition Line
		rLine.setC_OrderLine_ID(m_orderLine.getC_OrderLine_ID());
		rLine.saveEx();
	}	//	process
	
	/**
	 * 	Create new Order
	 *	@param rLine request line
	 *	@param C_BPartner_ID b.partner
	 * 	@throws Exception
	 */
	private void newOrder(MRequisitionLine rLine, int C_BPartner_ID) throws Exception
	{
		if (m_order != null)
		{
			closeOrder();
		}
		
		//	BPartner
		if (m_bpartner == null || C_BPartner_ID != m_bpartner.get_ID())
		{
			m_bpartner = MBPartner.get(getCtx(), C_BPartner_ID);
		}
		
		
		//	Order
		Timestamp DateRequired = rLine.getDateRequired();
		int M_PriceList_ID = rLine.getParent().getM_PriceList_ID(); 
		if (!isPOPriceList(M_PriceList_ID))
		{
			M_PriceList_ID = 0;
		}
		
		ArrayKey key = new ArrayKey(C_BPartner_ID, DateRequired, M_PriceList_ID);
		m_order = m_cacheOrders.get(key);
		if (m_order == null)
		{
			m_order = new MOrder(getCtx(), 0, get_TrxName());
			m_order.setDatePromised(DateRequired);
			m_order.setIsSOTrx(false);
			m_order.setC_DocTypeTarget_ID();
			m_order.setBPartner(m_bpartner);
			m_order.setM_PriceList_ID(M_PriceList_ID); 
			//	References
			m_order.setC_Currency_ID(rLine.getParent().getC_Currency_ID()); // task 05914 : currency is mandatory
			
			//	default po document type
			if (!p_ConsolidateDocument)
			{
				m_order.setDescription(Msg.getElement(getCtx(), "M_Requisition_ID") 
					+ ": " + rLine.getParent().getDocumentNo());
			}
			
			//	Prepare Save
			m_order.saveEx();
			// Put to cache
			m_cacheOrders.put(key, m_order);
		}
		m_M_Requisition_ID = rLine.getM_Requisition_ID();
	}	//	newOrder

	/**
	 * 	Close Order
	 * 	@throws Exception
	 */
	private void closeOrder() throws Exception
	{
		if (m_orderLine != null)
		{
			m_orderLine.saveEx();
		}
		if (m_order != null)
		{
			m_order.load(get_TrxName());
			addLog(0, null, m_order.getGrandTotal(), m_order.getDocumentNo());
		}
		m_order = null;
		m_orderLine = null;
	}	//	closeOrder

	
	/**
	 * 	New Order Line (different Product)
	 *	@param rLine request line
	 * 	@throws Exception
	 */
	private void newLine(MRequisitionLine rLine) throws Exception
	{
		if (m_orderLine != null)
		{
			m_orderLine.saveEx();
		}
		m_orderLine = null;
		MProduct product = MProduct.get(getCtx(), rLine.getM_Product_ID());

		//	Get Business Partner
		int C_BPartner_ID = rLine.getC_BPartner_ID();
		if (C_BPartner_ID != 0 && isVendorForProduct(C_BPartner_ID, product)) // // task 05914 : is is vendor, the partner is ok; can be used
		{
			;
		}
		else if (rLine.getC_Charge_ID() != 0)
		{
			MCharge charge = MCharge.get(getCtx(), rLine.getC_Charge_ID());
			C_BPartner_ID = charge.getC_BPartner_ID();
			if (C_BPartner_ID == 0)
			{
				throw new AdempiereUserError("No Vendor for Charge " + charge.getName());
			}
		}
		else
		{
			C_BPartner_ID = 0; // reset partner, since the one form line is not vendor
			// Find Strategic Vendor for Product
			
			// task 05914: start
			//FRESH-334: Make sure the BP_Product if of the product's org or org * 
			final int orgId = product.getAD_Org_ID();
			final int productId = product.getM_Product_ID();
			
			final List<de.metas.interfaces.I_C_BPartner_Product> partnerProducts = Services.get(IBPartnerProductDAO.class).retrieveBPartnerForProduct(getCtx(), 0, productId, orgId);
	
			if (partnerProducts.size() > 0)
			{
				C_BPartner_ID = partnerProducts.get(0).getC_BPartner_ID();
			}
			if (C_BPartner_ID == 0)
			{
				throw new NoVendorForProductException(product.getName());
			}
			// task 05914: end
		}
		
		if (!isGenerateForVendor(C_BPartner_ID))
		{
			log.info("Skip for partner "+C_BPartner_ID);
			return;
		}

		//	New Order - Different Vendor
		if (m_order == null 
			|| m_order.getC_BPartner_ID() != C_BPartner_ID
			|| m_order.getDatePromised().compareTo(rLine.getDateRequired()) != 0
			)
		{
			newOrder(rLine, C_BPartner_ID);
		}
		
		//	No Order Line
		m_orderLine = new MOrderLine(m_order);
		m_orderLine.setDatePromised(rLine.getDateRequired());
		if (product != null)
		{
			m_orderLine.setProduct(product);
			m_orderLine.setM_AttributeSetInstance_ID(rLine.getM_AttributeSetInstance_ID());
		}
		else
		{
			m_orderLine.setC_Charge_ID(rLine.getC_Charge_ID());
			m_orderLine.setPriceActual(rLine.getPriceActual());
		}
		m_orderLine.setAD_Org_ID(rLine.getAD_Org_ID());
				
		
		//	Prepare Save
		m_M_Product_ID = rLine.getM_Product_ID();
		m_M_AttributeSetInstance_ID = rLine.getM_AttributeSetInstance_ID();
		m_orderLine.setM_Warehouse_ID(rLine.getParent().getM_Warehouse_ID()); // task 05914 : warehouse is mandatory
		m_orderLine.saveEx();
	}	//	newLine

	/**
	 * Do we need to generate Purchase Orders for given Vendor 
	 * @param C_BPartner_ID
	 * @return true if it's allowed
	 */
	private boolean isGenerateForVendor(int C_BPartner_ID)
	{
		// No filter group was set => generate for all vendors
		if (p_C_BP_Group_ID <= 0)
			return true;
		
		if (m_excludedVendors.contains(C_BPartner_ID))
			return false;
		//
		boolean match = new Query(getCtx(), MBPartner.Table_Name, "C_BPartner_ID=? AND C_BP_Group_ID=?", get_TrxName())
		.setParameters(new Object[]{C_BPartner_ID, p_C_BP_Group_ID})
		.match();
		if (!match)
		{
			m_excludedVendors.add(C_BPartner_ID);
		}
		return match;
	}
	private List<Integer> m_excludedVendors = new ArrayList<Integer>();
	
	/**
	 * check if the partner is vendor for specific product
	 * @param C_BPartner_ID
	 * @param product
	 * @return
	 */

	private boolean isVendorForProduct(final int C_BPartner_ID, final MProduct product)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_BPartner_Product.class, product)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, C_BPartner_ID)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.create()
				.match();
		
	}
	
	private boolean isPOPriceList(final int M_PriceList_ID)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_PriceList.class, getProcessInfo())
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_M_PriceList_ID, M_PriceList_ID)
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_IsSOPriceList, false)
				.create()
				.match();
		
	}
	
}	//	RequisitionPOCreate
