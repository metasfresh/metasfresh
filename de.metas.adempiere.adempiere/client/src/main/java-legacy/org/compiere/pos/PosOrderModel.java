/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.pos;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrderTax;
import org.compiere.model.MPOS;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentProcessor;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;

import de.metas.document.engine.IDocument;
import de.metas.order.IOrderBL;

/**
 * Wrapper for standard order
 * @author Paul Bowden
 * Adaxa Pty Ltd
 *
 */
public class PosOrderModel extends MOrder {

	private static final long serialVersionUID = 5253837037827124425L;
	
	private MPOS m_pos;
	
	public PosOrderModel(Properties ctx, int C_Order_ID, String trxName, MPOS pos) {
		super(ctx, C_Order_ID, trxName);
		m_pos = pos;
	}

	/**
	 * Get/create Order
	 * 
	 * @return order or null
	 */
	public static PosOrderModel createOrder(MPOS pos, MBPartner partner) {
		
		PosOrderModel order = new PosOrderModel(Env.getCtx(), 0, null, pos);
		order.setAD_Org_ID(pos.getAD_Org_ID());
		order.setIsSOTrx(true);
		order.setC_POS_ID(pos.getC_POS_ID());
		if (pos.getC_DocType_ID() != 0)
			Services.get(IOrderBL.class).setDocTypeTargetIdAndUpdateDescription(order, pos.getC_DocType_ID());
		else
			Services.get(IOrderBL.class).setDocTypeTargetId(order, MOrder.DocSubType_POS);
		if (partner == null || partner.get_ID() == 0)
			partner = pos.getBPartner();
		if (partner == null || partner.get_ID() == 0) {
			throw new AdempierePOSException("No BPartner for order");
		}
		order.setBPartner(partner);
		//
		order.setM_PriceList_ID(pos.getM_PriceList_ID());
		order.setM_Warehouse_ID(pos.getM_Warehouse_ID());
		order.setSalesRep_ID(pos.getSalesRep_ID());
		order.setPaymentRule(MOrder.PAYMENTRULE_Cash);
		if (!order.save())
		{
			order = null;
			throw new AdempierePOSException("Save order failed");
		}
		
		return order;
	} //	createOrder


	/**
	 * @author Comunidad de Desarrollo OpenXpertya 
	 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
	 *         *Copyright ConSerTi
	 */
	public void setBPartner(MBPartner partner)
	{
		if (getDocStatus().equals("DR"))
		{
			if (partner == null || partner.get_ID() == 0) {
				throw new AdempierePOSException("no BPartner");
			}
			else
			{
				log.info("SubCurrentLine.getOrder -" + partner);
				super.setBPartner(partner);
				MOrderLine[] lineas = getLines();
				for (int i = 0; i < lineas.length; i++)
				{
					lineas[i].setC_BPartner_ID(partner.getC_BPartner_ID());
					lineas[i].setTax();
					lineas[i].save();
				}
				saveEx();
			}
		}

	}

	/**
	 * Create new Line
	 * 
	 * @return line or null
	 */
	public MOrderLine createLine(MProduct product, BigDecimal QtyOrdered,
			BigDecimal PriceActual) {
		
		if (!getDocStatus().equals("DR") )
			return null;
		//add new line or increase qty
		
		// catch Exceptions at order.getLines()
		int numLines = 0;
		MOrderLine[] lines = null;
		try
		{
			lines = getLines(null,"Line");
			numLines = lines.length;
			for (int i = 0; i < numLines; i++)
			{
				if (lines[i].getM_Product_ID() == product.getM_Product_ID())
				{
					//increase qty
					BigDecimal current = lines[i].getQtyEntered();
					BigDecimal toadd = QtyOrdered;
					BigDecimal total = current.add(toadd);
					lines[i].setQty(total);
					lines[i].setPrice(); //	sets List/limit
					if ( PriceActual.compareTo(Env.ZERO) > 0 )
						lines[i].setPrice(PriceActual);
					lines[i].save();
					return lines[i];
				}
			}
		}
		catch (Exception e)
		{
			log.error("Order lines cannot be created - " + e.getMessage());
		}

        //create new line
		MOrderLine line = new MOrderLine(this);
		line.setProduct(product);
		line.setQty(QtyOrdered);
			
		line.setPrice(); //	sets List/limit
		if ( PriceActual.compareTo(Env.ZERO) > 0 )
			line.setPrice(PriceActual);
		line.save();
		return line;
			
	} //	createLine
	
	
	/**
	 * Delete order from database
	 * 
	 * @author Comunidad de Desarrollo OpenXpertya 
 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 *         *Copyright � ConSerTi
	 */		
	public boolean deleteOrder () {
		if (getDocStatus().equals("DR"))
			{
				MOrderLine[] lines = getLines();
				if (lines != null)
				{
					int numLines = lines.length;
					if (numLines > 0)
						for (int i = numLines - 1; i >= 0; i--)
						{
							if (lines[i] != null)
								deleteLine(lines[i].getC_Order_ID());
						}
				}
				
				MOrderTax[] taxs = getTaxes(true);
				if (taxs != null)
				{
					int numTax = taxs.length;
					if (numTax > 0)
						for (int i = taxs.length - 1; i >= 0; i--)
						{
							if (taxs[i] != null)
								taxs[i].delete(true);
							taxs[i] = null;
						}
				}
				
				getLines(true, null);		// requery order
				return delete(true);
			}
		return false;
	} //	deleteOrder
	
	/** 
	 * to erase the lines from order
	 * @return true if deleted
	 */
	public void deleteLine (int C_OrderLine_ID) {
		if ( C_OrderLine_ID != -1 )
		{
			for ( MOrderLine line : getLines(true, "M_Product_ID") )
			{
				if ( line.getC_OrderLine_ID() == C_OrderLine_ID )
				{
					line.delete(true);	
				}
			}
		}
	} //	deleteLine

	/**
	 * 	Process Order
	 *  @author Comunidad de Desarrollo OpenXpertya 
	 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
	 *         *Copyright � ConSerTi
	 */
	public boolean processOrder()
	{		
		//Returning orderCompleted to check for order completeness
		boolean orderCompleted = false;
		// check if order completed OK
		if (getDocStatus().equals("DR") || getDocStatus().equals("IP") )
		{ 
			setDocAction(IDocument.ACTION_Complete);
			try
			{
				if (processIt(IDocument.ACTION_Complete) )
				{
					save();
				}
				else
				{
					log.info( "Process Order FAILED");		
				}
			}
			catch (Exception e)
			{
				log.error("Order can not be completed - " + e.getMessage());
			}
			finally
			{ // When order failed convert it back to draft so it can be processed
				if( getDocStatus().equals("IN") )
				{
					setDocStatus("DR");
				}
				else if( getDocStatus().equals("CO") )
				{
					orderCompleted = true;
					log.info( "SubCheckout - processOrder OK");	 
				}			
				else
				{
					log.info( "SubCheckout - processOrder - unrecognized DocStatus"); 
				}					
			} // try-finally

		}

		return orderCompleted;
	}	// processOrder

	public BigDecimal getTaxAmt()	{
		BigDecimal taxAmt = Env.ZERO;
		for (MOrderTax tax : getTaxes(true))
		{
			taxAmt = taxAmt.add(tax.getTaxAmt());
		}
		return taxAmt;
	}
	
	public BigDecimal getSubtotal() {
		return getGrandTotal().subtract(getTaxAmt());
	}

	public BigDecimal getPaidAmt()
	{
		String sql = "SELECT sum(PayAmt) FROM C_Payment WHERE C_Order_ID = ? AND DocStatus IN ('CO','CL')";
		BigDecimal received = DB.getSQLValueBD(null, sql, getC_Order_ID());
		if ( received == null )
			received = Env.ZERO;
		
		sql = "SELECT sum(Amount) FROM C_CashLine WHERE C_Invoice_ID = ? ";
		BigDecimal cashline = DB.getSQLValueBD(null, sql, getC_Invoice_ID());
		if ( cashline != null )
			received = received.add(cashline);
		
		return received;
	}

	public boolean payCash(BigDecimal amt) {

		MPayment payment = createPayment(MPayment.TENDERTYPE_Cash);
		payment.setC_CashBook_ID(m_pos.getC_CashBook_ID());
		payment.setAmount(getC_Currency_ID(), amt);
		payment.setC_BP_BankAccount_ID(m_pos.getC_BP_BankAccount_ID());
		payment.save();
		payment.setDocAction(MPayment.DOCACTION_Complete);
		payment.setDocStatus(MPayment.DOCSTATUS_Drafted);
		if ( payment.processIt(MPayment.DOCACTION_Complete) )
		{
			payment.save();
			return true;
		}
		else return false;
	} // payCash

	public boolean payCheck(BigDecimal amt, String accountNo, String routingNo, String checkNo) 
	{
		MPayment payment = createPayment(MPayment.TENDERTYPE_Check);
		payment.setAmount(getC_Currency_ID(), amt);
		payment.setC_BP_BankAccount_ID(m_pos.getC_BP_BankAccount_ID());
		payment.setAccountNo(accountNo);
		payment.setRoutingNo(routingNo);
		payment.setCheckNo(checkNo);
		payment.saveEx();
		payment.setDocAction(MPayment.DOCACTION_Complete);
		payment.setDocStatus(MPayment.DOCSTATUS_Drafted);
		if ( payment.processIt(MPayment.DOCACTION_Complete) )
		{
			payment.saveEx();
			return true;
		}
		else return false;
	} // payCheck
	
	public boolean payCreditCard(BigDecimal amt, String accountName, int month, int year,
			String cardNo, String cvc, String cardtype) 
	{

		MPayment payment = createPayment(MPayment.TENDERTYPE_Check);
		payment.setAmount(getC_Currency_ID(), amt);
		payment.setC_BP_BankAccount_ID(m_pos.getC_BP_BankAccount_ID());
		payment.setCreditCard(MPayment.TRXTYPE_Sales, cardtype,
				cardNo, cvc, month, year);
		payment.saveEx();
		payment.setDocAction(MPayment.DOCACTION_Complete);
		payment.setDocStatus(MPayment.DOCSTATUS_Drafted);
		if ( payment.processIt(MPayment.DOCACTION_Complete) )
		{
			payment.saveEx();
			return true;
		}
		else return false;
	} // payCheck

	private MPayment createPayment(String tenderType)
	{
		MPayment payment = new MPayment(getCtx(), 0, null);
		payment.setAD_Org_ID(m_pos.getAD_Org_ID());
		payment.setTenderType(tenderType);
		payment.setC_Order_ID(getC_Order_ID());
		payment.setIsReceipt(true);
		payment.setC_BPartner_ID(getC_BPartner_ID());
		return payment;
	}

	public void reload() {
		load( get_TrxName());
		getLines(true, "");
	}
	
	/**
	 * Duplicated from MPayment
	 * 	Get Accepted Credit Cards for amount
	 *	@param amt trx amount
	 *	@return credit cards
	 */
	public ValueNamePair[] getCreditCards (BigDecimal amt)
	{
		try
		{
			MPaymentProcessor[] m_mPaymentProcessors = MPaymentProcessor.find (getCtx (), null, null, 
					getAD_Client_ID (), getAD_Org_ID(), getC_Currency_ID (), amt, get_TrxName());
			//
			HashMap<String,ValueNamePair> map = new HashMap<>(); //	to eliminate duplicates
			for (int i = 0; i < m_mPaymentProcessors.length; i++)
			{
				if (m_mPaymentProcessors[i].isAcceptAMEX ())
					map.put (MPayment.CREDITCARDTYPE_Amex, getCreditCardPair (MPayment.CREDITCARDTYPE_Amex));
				if (m_mPaymentProcessors[i].isAcceptDiners ())
					map.put (MPayment.CREDITCARDTYPE_Diners, getCreditCardPair (MPayment.CREDITCARDTYPE_Diners));
				if (m_mPaymentProcessors[i].isAcceptDiscover ())
					map.put (MPayment.CREDITCARDTYPE_Discover, getCreditCardPair (MPayment.CREDITCARDTYPE_Discover));
				if (m_mPaymentProcessors[i].isAcceptMC ())
					map.put (MPayment.CREDITCARDTYPE_MasterCard, getCreditCardPair (MPayment.CREDITCARDTYPE_MasterCard));
				if (m_mPaymentProcessors[i].isAcceptCorporate ())
					map.put (MPayment.CREDITCARDTYPE_PurchaseCard, getCreditCardPair (MPayment.CREDITCARDTYPE_PurchaseCard));
				if (m_mPaymentProcessors[i].isAcceptVisa ())
					map.put (MPayment.CREDITCARDTYPE_Visa, getCreditCardPair (MPayment.CREDITCARDTYPE_Visa));
			} //	for all payment processors
			//
			ValueNamePair[] retValue = new ValueNamePair[map.size ()];
			map.values ().toArray (retValue);
			log.debug("getCreditCards - #" + retValue.length + " - Processors=" + m_mPaymentProcessors.length);
			return retValue;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}	//	getCreditCards
	
	/**
	 * 
	 * Duplicated from MPayment
	 * 	Get Type and name pair
	 *	@param CreditCardType credit card Type
	 *	@return pair
	 */
	private ValueNamePair getCreditCardPair (String CreditCardType)
	{
		return new ValueNamePair (CreditCardType, getCreditCardName(CreditCardType));
	}	//	getCreditCardPair

	/**
	 * 
	 * Duplicated from MPayment
	 *	Get Name of Credit Card
	 * 	@param CreditCardType credit card type
	 *	@return Name
	 */
	public String getCreditCardName(String CreditCardType)
	{
		if (CreditCardType == null)
			return "--";
		else if (MPayment.CREDITCARDTYPE_MasterCard.equals(CreditCardType))
			return "MasterCard";
		else if (MPayment.CREDITCARDTYPE_Visa.equals(CreditCardType))
			return "Visa";
		else if (MPayment.CREDITCARDTYPE_Amex.equals(CreditCardType))
			return "Amex";
		else if (MPayment.CREDITCARDTYPE_ATM.equals(CreditCardType))
			return "ATM";
		else if (MPayment.CREDITCARDTYPE_Diners.equals(CreditCardType))
			return "Diners";
		else if (MPayment.CREDITCARDTYPE_Discover.equals(CreditCardType))
			return "Discover";
		else if (MPayment.CREDITCARDTYPE_PurchaseCard.equals(CreditCardType))
			return "PurchaseCard";
		return "?" + CreditCardType + "?";
	}	//	getCreditCardName
	
} // PosOrderModel.class
