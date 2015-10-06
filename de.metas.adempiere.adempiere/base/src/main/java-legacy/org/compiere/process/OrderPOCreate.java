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
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.MOrder;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.service.IOrderDAO;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.purchasing.api.IBPartnerProductDAO;

/**
 * Generate PO from Sales Order
 *
 * @author Jorg Janke
 * @version $Id: OrderPOCreate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 *
 *          Contributor: Carlos Ruiz - globalqss Fix [1709952] - Process: "Generate PO from Sales order" bug
 */
public class OrderPOCreate extends SvrProcess
{

	private static final String MSG_ERROR_CREATING_PO_LINE_2P = "org.compiere.process.OrderPOCreate.ErrorCreatingPOLine";

	// services
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	final IMsgBL msgBL = Services.get(IMsgBL.class);

	/** Order Date From */
	private Timestamp p_DatePromised_From;
	/** Order Date To */
	private Timestamp p_DatePromised_To;
	/** Customer */
	private int p_C_BPartner_ID;
	/** Vendor */
	private int p_Vendor_ID;
	/** Sales Order */
	private int p_C_Order_ID;
	/** Drop Ship */
	private boolean p_IsDropShip = false;
	/** POReference */
	private String p_poReference = null;

	final Map<Integer, I_C_Order> partnersToOrders = new HashMap<Integer, I_C_Order>();

	final Map<ArrayKey, POLineQtysAggregator> aggregationKeysToOrderLines = new HashMap<ArrayKey, POLineQtysAggregator>();

	private int counter = 0;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		// kometar
		final ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				;
			}
			else if (name.equals("DatePromised_From"))
			{
				p_DatePromised_From = (Timestamp)para[i].getParameter();
			}
			else if (name.equals("DatePromised_To"))
			{
				p_DatePromised_To = (Timestamp)para[i].getParameter();
			}
			else if (name.equals("C_BPartner_ID"))
			{
				p_C_BPartner_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("Vendor_ID"))
			{
				p_Vendor_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("C_Order_ID"))
			{
				p_C_Order_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("IsDropShip"))
			{
				p_IsDropShip = ((String)para[i].getParameter()).equals("Y");
			}
			else if (name.equals("POReference"))
			{
				p_poReference = (para[i].getParameterAsString());
			}
			else
			{
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
		}

		// // called from order window w/o parameters
		// if (getTable_ID() == MOrder.Table_ID && getRecord_ID() > 0)
		// p_C_Order_ID = getRecord_ID();

	}	// prepare

	/**
	 * Perform process.
	 *
	 * @return Message
	 * @throws Exception if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("DatePromised=" + p_DatePromised_From + " - " + p_DatePromised_To
				+ " - C_BPartner_ID=" + p_C_BPartner_ID + " - Vendor_ID=" + p_Vendor_ID
				+ " - IsDropShip=" + p_IsDropShip + " - C_Order_ID=" + p_C_Order_ID);
		if (p_C_Order_ID == 0
				&& p_DatePromised_From == null && p_DatePromised_To == null
				&& p_C_BPartner_ID == 0 && p_Vendor_ID == 0)
		{
			throw new AdempiereUserError("You need to restrict selection");
		}
		//
		String sql = "SELECT * FROM C_Order o "
				+ "WHERE o.IsSOTrx='Y' AND o.IsActive = 'Y' ";

		// 07228
		// Allow duplicates

		// Commented this out, maybe the requirements will change one day
		// // No Duplicates
		// // " AND o.Link_Order_ID IS NULL"
		// + " AND NOT EXISTS (SELECT * FROM C_OrderLine ol WHERE o.C_Order_ID=ol.C_Order_ID AND ol.Link_OrderLine_ID IS NOT NULL)"
		// ;
		if (p_C_Order_ID != 0)
		{
			sql += " AND o.C_Order_ID=?";
		}
		else
		{
			if (p_C_BPartner_ID != 0)
			{
				sql += " AND o.C_BPartner_ID=?";
			}
			if (p_Vendor_ID != 0)
			{
				sql += " AND EXISTS (SELECT * FROM  "
						+ I_C_OrderLine.Table_Name + " ol"
						+ " INNER JOIN "
						+ I_C_BPartner_Product.Table_Name + " po ON (ol."
						+ I_C_OrderLine.COLUMNNAME_M_Product_ID + " = po."
						+ I_C_BPartner_Product.COLUMNNAME_M_Product_ID + ") "
						+ "WHERE o."
						+ I_C_Order.COLUMNNAME_C_Order_ID + "= ol."
						+ I_C_OrderLine.COLUMNNAME_C_Order_ID + " AND ((po."
						+ I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID + "=? )"
						+ " OR (po."
						+ I_C_BPartner_Product.COLUMNNAME_C_BPartner_Vendor_ID + "=?)))";
			}
			if (p_poReference != null)
			{
				sql += " AND o.POReference = ?";
			}
			if (p_DatePromised_From != null && p_DatePromised_To != null)
			{
				sql += " AND TRUNC(o.DatePromised) BETWEEN ? AND ?";
			}
			else if (p_DatePromised_From != null && p_DatePromised_To == null)
			{
				sql += " AND TRUNC(o.DatePromised) >= ?";
			}
			else if (p_DatePromised_From == null && p_DatePromised_To != null)
			{
				sql += " AND TRUNC(o.DatePromised) <= ?";
			}
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			if (p_C_Order_ID != 0)
			{
				pstmt.setInt(1, p_C_Order_ID);
			}
			else
			{
				int index = 1;
				if (p_C_BPartner_ID != 0)
				{
					pstmt.setInt(index++, p_C_BPartner_ID);
				}
				if (p_Vendor_ID != 0)
				{
					pstmt.setInt(index++, p_Vendor_ID);
					// make sure we also add the bp vendor in equation
					pstmt.setInt(index++, p_Vendor_ID);
				}

				if (p_poReference != null)
				{
					pstmt.setString(index++, p_poReference);
				}
				if (p_DatePromised_From != null && p_DatePromised_To != null)
				{
					pstmt.setTimestamp(index++, p_DatePromised_From);
					pstmt.setTimestamp(index++, p_DatePromised_To);
				}
				else if (p_DatePromised_From != null && p_DatePromised_To == null)
				{
					pstmt.setTimestamp(index++, p_DatePromised_From);
				}
				else if (p_DatePromised_From == null && p_DatePromised_To != null)
				{
					pstmt.setTimestamp(index++, p_DatePromised_To);
				}
			}
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				// TODO make a normal bl method for resultSet
				// OR rewrite the sql using IQueryBuilder
				final MOrder mOrder = new MOrder(getCtx(), rs, get_TrxName());
				final I_C_Order salesOrder = InterfaceWrapperHelper.create(mOrder, I_C_Order.class);
				
				createPOFromSO (salesOrder);
			}

			// perform db update only at the end
			savePOLines();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		if (counter == 0)
		{
			log.fine(sql);
		}
		return "@Created@ " + counter;
	}	// doIt

	/**
	 * Create PO From SO
	 *
	 * @param salesOrder sales order
	 * @return number of POs created
	 * @throws Exception
	 */
	private void createPOFromSO(final I_C_Order salesOrder) throws Exception
	{
		// services
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
		final IBPartnerProductDAO bpProductDAO = Services.get(IBPartnerProductDAO.class);
		
		log.info(salesOrder.toString());
		final List<de.metas.interfaces.I_C_OrderLine> soLines = orderDAO.retrieveOrderLines(salesOrder);
		if (soLines.isEmpty())
		{
			log.warning("No Lines - " + salesOrder);
			return;
		}

		for(final I_C_OrderLine soLine: soLines)
		{
			// make sure only active soLines are considered

			if (!soLine.isActive())
			{
				continue;
			}
			final I_C_BPartner soPartner = soLine.getC_BPartner();
			final I_M_Product product = soLine.getM_Product();
			final I_C_BPartner_Product bpProduct = bpProductDAO.retrieveBPProductForCustomer(soPartner, product);

			if (bpProduct == null)
			{
				continue;
			}

			final I_C_BPartner poPartner;
			if (bpProduct.getC_BPartner_Vendor() != null)
			{
				poPartner = bpProduct.getC_BPartner_Vendor();
			}
			else
			{
				poPartner = bpProduct.getC_BPartner();
			}

			// extra validation for the vendor

			if (p_Vendor_ID > 0)
			{
				if (poPartner.getC_BPartner_ID() != p_Vendor_ID)
				{
					continue;
				}
			}
			// If the order for this partner was already created, add the new line to it
			I_C_Order currentPurchaseOrder = partnersToOrders.get(poPartner.getC_BPartner_ID());

			if (currentPurchaseOrder == null)
			{
				// In case it wasn't created yet, create it and put it in the map so lines can be added to it
				currentPurchaseOrder = createPOForVendor(poPartner, salesOrder);
				partnersToOrders.put(poPartner.getC_BPartner_ID(), currentPurchaseOrder);
				addLog(0, null, null, currentPurchaseOrder.getDocumentNo());
				counter++;
			}

			final I_M_AttributeSetInstance attributeSetInstance = soLine.getM_AttributeSetInstance();
			final int attributeSetID = attributeSetInstance == null ? -1 : attributeSetInstance.getM_AttributeSet_ID();

			final ArrayKey poLineKey = createPOLineAggregationKey(
					currentPurchaseOrder.getC_Order_ID(),
					soLine.getM_Product_ID(),
					attributeSetID
					);

			POLineQtysAggregator poLineAggregator = aggregationKeysToOrderLines.get(poLineKey);

			if (poLineAggregator != null)
			{
				updateExistingPOLine(poLineAggregator, soLine);
			}

			else
			{
				final I_M_AttributeSetInstance poASI;
				if (soLine.getM_AttributeSetInstance_ID() > 0)
				{
					final I_M_AttributeSetInstance soASI = soLine.getM_AttributeSetInstance();
					poASI = attributeDAO.copy(soASI);
				}
				else
				{
					poASI = null;
				}

				try
				{
					final I_C_OrderLine poLine = orderLineBL.createOrderLine(currentPurchaseOrder);
					poLine.setLink_OrderLine_ID(soLine.getC_OrderLine_ID());
					poLine.setM_Product_ID(soLine.getM_Product_ID());
					poLine.setC_Charge_ID(soLine.getC_Charge_ID());
					poLine.setC_UOM_ID(soLine.getC_UOM_ID());
					poLine.setQtyEntered(soLine.getQtyEntered());
					poLine.setQtyOrdered(soLine.getQtyOrdered());
					poLine.setDescription(soLine.getDescription());
					poLine.setDatePromised(soLine.getDatePromised());

					poLine.setC_BPartner(currentPurchaseOrder.getC_BPartner());
					poLine.setC_BPartner_Location(currentPurchaseOrder.getC_BPartner_Location());

					poLine.setM_AttributeSetInstance(poASI);
					IModelAttributeSetInstanceListener.DYNATTR_DisableASIUpdateOnModelChange.setValue(poLine, true); // (08091)

					// Use the price setter from the BL
					orderLineBL.setPrices(InterfaceWrapperHelper.create(poLine, de.metas.interfaces.I_C_OrderLine.class));
					// poLine.setPrice();

					InterfaceWrapperHelper.save(poLine);

					final POLineQtysAggregator currentAggregator = new POLineQtysAggregator(
							poLine,
							poLine.getQtyEntered(),
							poLine.getQtyOrdered(),
							poLine.getPriceEntered());

					aggregationKeysToOrderLines.put(poLineKey, currentAggregator);

					soLine.setLink_OrderLine_ID(poLine.getC_OrderLine_ID());
					InterfaceWrapperHelper.save(soLine);
				}
				catch (final AdempiereException e)
				{
					// adding a log message to the user can see where the problem ocurred
					addLog(msgBL.getMsg(getCtx(),
							MSG_ERROR_CREATING_PO_LINE_2P,
							new Object[] { soLine.getM_Product().getValue(), poPartner.getValue() })
							+ ": " + e.getLocalizedMessage());
					throw e;
				}
			}
		}

	}	// createPOFromSO

	private void savePOLines()
	{
		for (final ArrayKey key : aggregationKeysToOrderLines.keySet())
		{
			final POLineQtysAggregator poLineAggregator = aggregationKeysToOrderLines.get(key);

			final I_C_OrderLine poLine = poLineAggregator.getPoLine();

			Check.assumeNotNull(poLine, "PO Line not null for aggregation key: ", key);

			poLine.setQtyEntered(poLineAggregator.getQtyEntered());
			poLine.setQtyOrdered(poLineAggregator.getQtyOrdered());
			poLine.setPriceEntered(poLineAggregator.getPriceEntered());

			InterfaceWrapperHelper.save(poLine);

		}

	}

	private static class POLineQtysAggregator
	{
		private I_C_OrderLine poLine;
		private BigDecimal qtyEntered;
		private BigDecimal qtyOrdered;
		private BigDecimal priceEntered;

		public POLineQtysAggregator(I_C_OrderLine poLine, BigDecimal qtyEntered, BigDecimal qtyOrdered, BigDecimal priceEntered)
		{
			super();
			this.poLine = poLine;
			this.qtyEntered = qtyEntered;
			this.qtyOrdered = qtyOrdered;
			this.priceEntered = priceEntered;
		}

		public I_C_OrderLine getPoLine()
		{
			return poLine;
		}

		public BigDecimal getQtyEntered()
		{
			return qtyEntered;
		}

		public void setQtyEntered(BigDecimal qtyEntered)
		{
			this.qtyEntered = qtyEntered;
		}

		public BigDecimal getQtyOrdered()
		{
			return qtyOrdered;
		}

		public void setQtyOrdered(BigDecimal qtyOrdered)
		{
			this.qtyOrdered = qtyOrdered;
		}

		public BigDecimal getPriceEntered()
		{
			return priceEntered;
		}

		public void setPriceEntered(BigDecimal priceEntered)
		{
			this.priceEntered = priceEntered;
		}

	}

	private void updateExistingPOLine(final POLineQtysAggregator poLineAggregator, final I_C_OrderLine soLine)
	{
		final BigDecimal poLineQtyOrdered = poLineAggregator.getQtyOrdered().add(soLine.getQtyOrdered());
		poLineAggregator.setQtyOrdered(poLineQtyOrdered);

		final BigDecimal poLineQtyEntered = poLineAggregator.getQtyEntered().add(soLine.getQtyEntered());
		poLineAggregator.setQtyEntered(poLineQtyEntered);

		final BigDecimal poLinePriceEntered = poLineAggregator.getPriceEntered().add(soLine.getPriceEntered());
		poLineAggregator.setPriceEntered(poLinePriceEntered);

		// let the Model Validators take care of the rest
		// poLine.saveEx();

	}

	private ArrayKey createPOLineAggregationKey(final int orderID, final int productID, final int asID)
	{
		final ArrayKey aggKey = Util.mkKey(
				orderID,
				productID,
				asID
				);

		return aggKey;

	}

	/**
	 * Create PO for Vendor
	 *
	 * @param C_BPartner_ID vendor
	 * @param salesOrder sales order
	 */
	public I_C_Order createPOForVendor(final I_C_BPartner vendor, final I_C_Order salesOrder)
	{
		// services
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(salesOrder);
		final String trxName = InterfaceWrapperHelper.getTrxName(salesOrder);

		final I_C_Order purchaseOrder = InterfaceWrapperHelper.newInstance(I_C_Order.class, salesOrder);

		final I_AD_Org org = salesOrder.getAD_Org();

		purchaseOrder.setAD_Org(org);
		purchaseOrder.setLink_Order_ID(salesOrder.getC_Order_ID());
		purchaseOrder.setIsSOTrx(false);
		orderBL.setDocTypeTargetId(purchaseOrder);
		//
		purchaseOrder.setDescription(salesOrder.getDescription());

		if (salesOrder.getPOReference() == null)
		{
			purchaseOrder.setPOReference(salesOrder.getDocumentNo());
		}
		else
		{
			purchaseOrder.setPOReference(salesOrder.getPOReference());
		}
		purchaseOrder.setPriorityRule(salesOrder.getPriorityRule());
		purchaseOrder.setSalesRep_ID(salesOrder.getSalesRep_ID());
		purchaseOrder.setM_Warehouse_ID(salesOrder.getM_Warehouse_ID());
	
		// 08812: Make sure the users are correctly set

		orderBL.setBPartner(purchaseOrder, vendor);
		orderBL.setBill_User_ID(purchaseOrder);
		
		// Drop Ship
		if (p_IsDropShip)
		{
			purchaseOrder.setIsDropShip(p_IsDropShip);

			if (salesOrder.isDropShip() && salesOrder.getDropShip_BPartner_ID() != 0)
			{
				purchaseOrder.setDropShip_BPartner_ID(salesOrder.getDropShip_BPartner_ID());
				purchaseOrder.setDropShip_Location_ID(salesOrder.getDropShip_Location_ID());
				purchaseOrder.setDropShip_User_ID(salesOrder.getDropShip_User_ID());
			}
			else
			{
				purchaseOrder.setDropShip_BPartner_ID(salesOrder.getC_BPartner_ID());
				purchaseOrder.setDropShip_Location_ID(salesOrder.getC_BPartner_Location_ID());
				purchaseOrder.setDropShip_User_ID(salesOrder.getAD_User_ID());
			}
			
			// get default drop ship warehouse
			final I_AD_OrgInfo orginfo = orgDAO.retrieveOrgInfo(ctx, org.getAD_Org_ID(), trxName);

			if (orginfo.getDropShip_Warehouse_ID() != 0)
			{
				purchaseOrder.setM_Warehouse_ID(orginfo.getDropShip_Warehouse_ID());
			}
			else
			{
				log.log(Level.SEVERE, "Must specify drop ship warehouse in org info.");
			}
		}
		// References
		purchaseOrder.setC_Activity_ID(salesOrder.getC_Activity_ID());
		purchaseOrder.setC_Campaign_ID(salesOrder.getC_Campaign_ID());
		purchaseOrder.setC_Project_ID(salesOrder.getC_Project_ID());
		purchaseOrder.setUser1_ID(salesOrder.getUser1_ID());
		purchaseOrder.setUser2_ID(salesOrder.getUser2_ID());
		purchaseOrder.setC_Currency_ID(salesOrder.getC_Currency_ID());
		//
		
		InterfaceWrapperHelper.save(purchaseOrder);
		return purchaseOrder;
	}	// createPOForVendor

}	// doIt
