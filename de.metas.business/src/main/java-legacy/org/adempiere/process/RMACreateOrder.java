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

package org.adempiere.process;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;

import de.metas.adempiere.model.I_C_Order;
import de.metas.process.SvrProcess;

/**
 *  Creates Order from RMA document
 *
 *  @author Ashley Ramdass
 */

public class RMACreateOrder extends SvrProcess
{
	
	private int rmaId = 0;
    @Override
    protected void prepare()
    {
        rmaId = getRecord_ID();
    }
    
    @Override
    protected String doIt() throws Exception
    {
        // Load RMA
        MRMA rma = new MRMA(getCtx(), rmaId, get_TrxName());
        
        // Load Original Order
        // metas c.ghita@metas.ro
        // load the original metas order
//        MOrder originalOrder = rma.getOriginalOrder();
        I_C_Order originalOrder = InterfaceWrapperHelper.create(rma.getOriginalOrder(), I_C_Order.class);
        
        if (rma.get_ID() == 0)
        {
            throw new Exception("No RMA defined");
        }
        
        if (originalOrder == null)
        {
            throw new Exception("Could not load the original order");
        }
        
        // Create new order and set the different values based on original order/RMA doc
        MOrder order = new MOrder(getCtx(), 0, get_TrxName());
        order.setAD_Org_ID(rma.getAD_Org_ID());
        order.setC_BPartner_ID(originalOrder.getC_BPartner_ID());
        order.setC_BPartner_Location_ID(originalOrder.getC_BPartner_Location_ID());
        order.setAD_User_ID(originalOrder.getAD_User_ID());
        order.setBill_BPartner_ID(originalOrder.getBill_BPartner_ID());
        order.setBill_Location_ID(originalOrder.getBill_Location_ID());
        order.setBill_User_ID(originalOrder.getBill_User_ID());
        order.setSalesRep_ID(rma.getSalesRep_ID());
        order.setM_PriceList_ID(originalOrder.getM_PriceList_ID());
        order.setM_Warehouse_ID(originalOrder.getM_Warehouse_ID());
        order.setC_DocTypeTarget_ID(originalOrder.getC_DocTypeTarget_ID());
        order.setC_PaymentTerm_ID(originalOrder.getC_PaymentTerm_ID());
        order.setDeliveryRule(originalOrder.getDeliveryRule());
        // start: metas c.ghita@metas.ro : set fields for metas order
        I_C_Order mOrder = InterfaceWrapperHelper.create(order, I_C_Order.class);   // metas c.ghita@metas.ro : metas order
        order.setM_Shipper_ID(originalOrder.getM_Shipper_ID());
        order.setC_DocTypeTarget_ID(originalOrder.getC_DocTypeTarget_ID());
        mOrder.setBPartnerAddress(originalOrder.getBPartnerAddress());
        mOrder.setBillToAddress(originalOrder.getBillToAddress());
        mOrder.setC_Sponsor_ID(originalOrder.getC_Sponsor_ID());
        mOrder.setFreightCostRule(originalOrder.getFreightCostRule());
        mOrder.setDeliveryViaRule(originalOrder.getDeliveryViaRule());
        mOrder.setFreightAmt(originalOrder.getFreightAmt());
        mOrder.setM_Warehouse_ID(originalOrder.getM_Warehouse_ID());
        mOrder.setM_PricingSystem_ID(originalOrder.getM_PricingSystem_ID());
        mOrder.setC_PaymentTerm_ID(originalOrder.getC_PaymentTerm_ID());
        mOrder.setRef_RMA_ID(rmaId);
        // end: metas c.ghita@metas.ro
        
        if (!order.save())
        {
            throw new IllegalStateException("Could not create order");
        }
        
        MRMALine lines[] = rma.getLines(true);
                
        for (MRMALine line : lines)
        {
            if (line.getShipLine() != null && line.getShipLine().getC_OrderLine_ID() != 0)
            {
                // Create order lines if the RMA Doc line has a shipment line 
                MOrderLine orderLine = new MOrderLine(order);
                MOrderLine originalOLine = new MOrderLine(getCtx(), line.getShipLine().getC_OrderLine_ID(), null);
                orderLine.setAD_Org_ID(line.getAD_Org_ID());
                orderLine.setM_Product_ID(originalOLine.getM_Product_ID());
                orderLine.setM_AttributeSetInstance_ID(originalOLine.getM_AttributeSetInstance_ID());
                orderLine.setC_UOM_ID(originalOLine.getC_UOM_ID());
                orderLine.setC_Tax_ID(originalOLine.getC_Tax_ID());
                orderLine.setC_TaxCategory_ID(originalOLine.getC_TaxCategory_ID());
                orderLine.setM_Warehouse_ID(Services.get(IWarehouseAdvisor.class).evaluateWarehouse(originalOLine).getM_Warehouse_ID());
                orderLine.setC_Currency_ID(originalOLine.getC_Currency_ID());
                orderLine.setQty(line.getQty());
                orderLine.setC_Project_ID(originalOLine.getC_Project_ID());
                orderLine.setC_Activity_ID(originalOLine.getC_Activity_ID());
                orderLine.setC_Campaign_ID(originalOLine.getC_Campaign_ID());
                orderLine.setPrice();
                orderLine.setPrice(line.getAmt());
                
                if (!orderLine.save())
                {
                    throw new IllegalStateException("Could not create Order Line");
                }
            }
        }
        
        rma.setC_Order_ID(order.getC_Order_ID());
        if (!rma.save())
        {
            throw new IllegalStateException("Could not update RMA document");
        }
        
        return "Order Created: " + order.getDocumentNo();
    }

}
