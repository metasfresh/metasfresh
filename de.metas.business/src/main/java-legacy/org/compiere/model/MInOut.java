/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.bpartner.service.BPartnerCreditLimitRepository;
import org.adempiere.bpartner.service.BPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsBL.CalculateSOCreditStatusRequest;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.ProductASIMandatoryException;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.misc.service.IPOService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.compiere.Adempiere;
import org.compiere.print.ReportEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.invoice.IMatchInvBL;
import de.metas.materialtransaction.IMTransactionDAO;
import de.metas.product.IProductBL;
import de.metas.product.IStorageBL;

/**
 * Shipment Model
 *
 * @author Jorg Janke
 * @version $Id: MInOut.java,v 1.4 2006/07/30 00:51:03 jjanke Exp $
 *
 *          Modifications: Added the RMA functionality (Ashley Ramdass)
 * @author Karsten Thiemann, Schaeffer AG
 *         <li>Bug [ 1759431 ] Problems with VCreateFrom
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 1948157 ] Is necessary the reference for document reverse
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http ://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id =176962
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * @see http ://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id =176962
 */
public class MInOut extends X_M_InOut implements IDocument
{
	private static final long serialVersionUID = 132321718005732306L;

	/**
	 * Create new Shipment by copying
	 *
	 * @param from shipment
	 * @param dateDoc date of the document date
	 * @param C_DocType_ID doc type
	 * @param isSOTrx sales order
	 * @param counter create counter links
	 * @param trxName trx
	 * @param setOrder set the order link
	 * @return Shipment
	 */
	public static MInOut copyFrom(final MInOut from, final Timestamp dateDoc, final Timestamp dateAcct,
			final int C_DocType_ID, final boolean isSOTrx, final boolean counter, final String trxName, final boolean setOrder)
	{
		final MInOut to = copyHeader(from, dateDoc, dateAcct, C_DocType_ID, isSOTrx, counter, trxName, setOrder);

		if (to.copyLinesFrom(from, counter, setOrder) == 0)
		{
			throw new IllegalStateException("Could not create Shipment Lines");
		}

		return to;
	} // copyFrom

	private static MInOut copyHeader(final MInOut from, final Timestamp dateDoc, final Timestamp dateAcct, final int C_DocType_ID, final boolean isSOTrx, final boolean counter, final String trxName, final boolean setOrder)
	{
		final MInOut to = new MInOut(from.getCtx(), 0, null);
		to.set_TrxName(trxName);
		copyValues(from, to, from.getAD_Client_ID(), from.getAD_Org_ID());
		to.set_ValueNoCheck("M_InOut_ID", I_ZERO);
		to.set_ValueNoCheck("DocumentNo", null);
		//
		to.setDocStatus(DOCSTATUS_Drafted); // Draft
		to.setDocAction(DOCACTION_Complete);
		//
		to.setC_DocType_ID(C_DocType_ID);
		to.setIsSOTrx(isSOTrx);

		if (isSOTrx)  // currently, only material receipts can be dropship
		{
			to.setIsDropShip(false);
		}
		if (counter)
		{
			final MDocType docType = MDocType.get(from.getCtx(), C_DocType_ID);
			if (MDocType.DOCBASETYPE_MaterialDelivery.equals(docType.getDocBaseType()))
			{
				to.setMovementType(isSOTrx ? MOVEMENTTYPE_CustomerShipment : MOVEMENTTYPE_VendorReturns);
			}
			else if (MDocType.DOCBASETYPE_MaterialReceipt.equals(docType.getDocBaseType()))
			{
				to.setMovementType(isSOTrx ? MOVEMENTTYPE_CustomerReturns : MOVEMENTTYPE_VendorReceipts);
			}
		}

		//
		to.setDateOrdered(dateDoc);
		to.setDateAcct(dateAcct);
		to.setMovementDate(dateDoc);
		to.setDatePrinted(null);
		to.setIsPrinted(false);
		to.setDateReceived(null);
		to.setPickDate(null);
		to.setIsInTransit(false);
		//
		to.setIsApproved(false);
		to.setC_Invoice_ID(0);
		to.setIsInDispute(false);
		//
		to.setPosted(false);
		to.setProcessed(false);
		// [ 1633721 ] Reverse Documents- Processing=Y
		to.setProcessing(false);
		to.setC_Order_ID(0); // Overwritten by setOrder
		to.setM_RMA_ID(0); // Overwritten by setOrder
		if (counter)
		{
			to.setC_Order_ID(0);
			to.setRef_InOut_ID(from.getM_InOut_ID());
			// Try to find Order/Invoice link
			if (from.getC_Order_ID() != 0)
			{
				final MOrder peer = new MOrder(from.getCtx(), from.getC_Order_ID(), from.get_TrxName());
				if (peer.getRef_Order_ID() != 0)
				{
					to.setC_Order_ID(peer.getRef_Order_ID());
				}
			}
			if (from.getC_Invoice_ID() != 0)
			{
				final MInvoice peer = new MInvoice(from.getCtx(), from.getC_Invoice_ID(), from.get_TrxName());
				if (peer.getRef_Invoice_ID() != 0)
				{
					to.setC_Invoice_ID(peer.getRef_Invoice_ID());
				}
			}
			// find RMA link
			if (from.getM_RMA_ID() != 0)
			{
				final MRMA peer = new MRMA(from.getCtx(), from.getM_RMA_ID(), from.get_TrxName());
				if (peer.getRef_RMA_ID() > 0)
				{
					to.setM_RMA_ID(peer.getRef_RMA_ID());
				}
			}
		}
		else
		{
			to.setRef_InOut_ID(0);
			if (setOrder)
			{
				to.setC_Order_ID(from.getC_Order_ID());
				to.setM_RMA_ID(from.getM_RMA_ID()); // Copy also RMA
			}
		}
		//
		if (!to.save(trxName))
		{
			throw new IllegalStateException("Could not create Shipment");
		}
		if (counter)
		{
			from.setRef_InOut_ID(to.getM_InOut_ID());
		}
		return to;
	} // copyHeader

	/**
	 * @deprecated Create new Shipment by copying
	 * @param from shipment
	 * @param dateDoc date of the document date
	 * @param C_DocType_ID doc type
	 * @param isSOTrx sales order
	 * @param counter create counter links
	 * @param trxName trx
	 * @param setOrder set the order link
	 * @return Shipment
	 */
	@Deprecated
	public static MInOut copyFrom(final MInOut from, final Timestamp dateDoc,
			final int C_DocType_ID, final boolean isSOTrx, final boolean counter, final String trxName, final boolean setOrder)
	{
		final MInOut to = copyFrom(from, dateDoc, dateDoc,
				C_DocType_ID, isSOTrx, counter,
				trxName, setOrder);
		return to;

	}

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param M_InOut_ID
	 * @param trxName rx name
	 */
	public MInOut(final Properties ctx, final int M_InOut_ID, final String trxName)
	{
		super(ctx, M_InOut_ID, trxName);
		if (is_new())
		{
			// setDocumentNo (null);
			// setC_BPartner_ID (0);
			// setC_BPartner_Location_ID (0);
			// setM_Warehouse_ID (0);
			// setC_DocType_ID (0);
			setIsSOTrx(false);
			setMovementDate(Env.getDate(ctx));	// use Login date (08306)
			setDateAcct(getMovementDate());
			// setMovementType (MOVEMENTTYPE_CustomerShipment);
			setDeliveryRule(DELIVERYRULE_Availability);
			setDeliveryViaRule(DELIVERYVIARULE_Pickup);
			setFreightCostRule(FREIGHTCOSTRULE_FreightIncluded);
			setDocStatus(DOCSTATUS_Drafted);
			setDocAction(DOCACTION_Complete);
			setPriorityRule(PRIORITYRULE_Medium);
			setIsInTransit(false);
			setIsPrinted(false);
			setSendEMail(false);
			setIsInDispute(false);
			//
			setIsApproved(false);
			super.setProcessed(false);
			setProcessing(false);
			setPosted(false);
		}
	} // MInOut

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set record
	 * @param trxName transaction
	 */
	public MInOut(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	} // MInOut

	/**
	 * Order Constructor - create header only
	 *
	 * @param order order
	 * @param movementDate optional movement date (default today)
	 * @param C_DocTypeShipment_ID document type or 0
	 */
	public MInOut(final MOrder order, final int C_DocTypeShipment_ID, final Timestamp movementDate)
	{
		this(order.getCtx(), 0, order.get_TrxName());
		setClientOrg(order);
		setC_BPartner_ID(order.getC_BPartner_ID());
		setC_BPartner_Location_ID(order.getC_BPartner_Location_ID()); // shipment address
		setAD_User_ID(order.getAD_User_ID());
		//

		// 04579 fixing this problem so i can create a shipment for a PO order
		final I_M_Warehouse wh = Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);
		Check.assumeNotNull(wh, "IWarehouseAdvisor finds a ware house for {}", order);
		setM_Warehouse_ID(wh.getM_Warehouse_ID());

		setIsSOTrx(order.isSOTrx());
		// metas: better setting of the movement type
		String movementType = null;
		if (order.isSOTrx())
		{
			final MDocType dt = MDocType.get(getCtx(), order.getC_DocType_ID());
			if (MDocType.DOCSUBTYPE_ReturnMaterial.equals(dt
					.getDocSubType()))
			{
				movementType = MOVEMENTTYPE_CustomerReturns;
			}
			else
			{
				movementType = MOVEMENTTYPE_CustomerShipment;
			}
		}
		else
		{
			movementType = MOVEMENTTYPE_VendorReceipts;
		}
		setMovementType(movementType);

		// metas: following a pmd recommendation
		if (C_DocTypeShipment_ID != 0)
		{
			setC_DocType_ID(C_DocTypeShipment_ID);
		}
		else
		{
			final int dbValue = DB
					.getSQLValue(
							null,
							"SELECT C_DocTypeShipment_ID FROM C_DocType WHERE C_DocType_ID=?",
							order.getC_DocType_ID());
			setC_DocType_ID(dbValue);
		}
		// metas end

		// Default - Today
		if (movementDate != null)
		{
			setMovementDate(movementDate);
		}
		setDateAcct(getMovementDate());

		// Copy from Order
		setC_Order_ID(order.getC_Order_ID());
		setDeliveryRule(order.getDeliveryRule());
		setDeliveryViaRule(order.getDeliveryViaRule());
		setM_Shipper_ID(order.getM_Shipper_ID());
		setFreightCostRule(order.getFreightCostRule());
		setFreightAmt(order.getFreightAmt());
		setSalesRep_ID(order.getSalesRep_ID());
		//
		setC_Activity_ID(order.getC_Activity_ID());
		setC_Campaign_ID(order.getC_Campaign_ID());
		setC_Charge_ID(order.getC_Charge_ID());
		setChargeAmt(order.getChargeAmt());
		//
		setC_Project_ID(order.getC_Project_ID());
		setDateOrdered(order.getDateOrdered());
		setDescription(order.getDescription());
		setPOReference(order.getPOReference());
		setSalesRep_ID(order.getSalesRep_ID());
		setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		setUser1_ID(order.getUser1_ID());
		setUser2_ID(order.getUser2_ID());
		setPriorityRule(order.getPriorityRule());
		// Drop shipment
		// metas start: cg: 01717
		if (order.isSOTrx())
		{
			setIsDropShip(false);
			setDropShip_BPartner_ID(0);
			setDropShip_Location_ID(0);
			setDropShip_User_ID(0);
		}
		else
		{
			setIsDropShip(order.isDropShip());
			setDropShip_BPartner_ID(order.getDropShip_BPartner_ID());
			setDropShip_Location_ID(order.getDropShip_Location_ID());
			setDropShip_User_ID(order.getDropShip_User_ID());
		}
		// metas end: cg: 01717
		// metas
		copyAdditionalCols(order);
		// metas end
	} // MInOut

	private void copyAdditionalCols(final Object order)
	{

		final IPOService poService = Services.get(IPOService.class);

		poService.copyValue(order, this, I_M_InOut.COLUMNNAME_Incoterm);
		poService.copyValue(order, this, I_M_InOut.COLUMNNAME_IncotermLocation);
		poService.copyValue(order, this, I_M_InOut.COLUMNNAME_DescriptionBottom);

		poService.setValue(this, I_M_InOut.COLUMNNAME_BPartnerAddress, poService.getValue(order, de.metas.adempiere.model.I_C_Order.COLUMNNAME_BPartnerAddress));
		poService.setValue(this, I_M_InOut.COLUMNNAME_IsUseBPartnerAddress, poService.getValue(order, de.metas.adempiere.model.I_C_Order.COLUMNNAME_IsUseBPartnerAddress));
	}

	/**
	 * Invoice Constructor - create header only
	 *
	 * @param invoice invoice
	 * @param C_DocTypeShipment_ID document type or 0
	 * @param movementDate optional movement date (default today)
	 * @param M_Warehouse_ID warehouse
	 */
	public MInOut(final MInvoice invoice, final int C_DocTypeShipment_ID, final Timestamp movementDate, final int M_Warehouse_ID)
	{
		this(invoice.getCtx(), 0, invoice.get_TrxName());
		setClientOrg(invoice);
		setC_BPartner_ID(invoice.getC_BPartner_ID());
		setC_BPartner_Location_ID(invoice.getC_BPartner_Location_ID()); // shipment address
		setAD_User_ID(invoice.getAD_User_ID());
		//
		setM_Warehouse_ID(M_Warehouse_ID);
		setIsSOTrx(invoice.isSOTrx());
		setMovementType(invoice.isSOTrx() ? MOVEMENTTYPE_CustomerShipment : MOVEMENTTYPE_VendorReceipts);
		MOrder order = null;
		if (invoice.getC_Order_ID() != 0)
		{
			order = new MOrder(invoice.getCtx(), invoice.getC_Order_ID(), invoice.get_TrxName());
		}
		int docTypeId = C_DocTypeShipment_ID;
		if (docTypeId == 0 && order != null)
		{
			docTypeId = DB.getSQLValue(null,
					"SELECT C_DocTypeShipment_ID FROM C_DocType WHERE C_DocType_ID=?",
					order.getC_DocType_ID());
		}
		if (docTypeId != 0)
		{
			setC_DocType_ID(docTypeId);
		}
		else
		{
			setC_DocType_ID();
		}

		// Default - Today
		if (movementDate != null)
		{
			setMovementDate(movementDate);
		}
		setDateAcct(getMovementDate());

		// Copy from Invoice
		setC_Order_ID(invoice.getC_Order_ID());
		setSalesRep_ID(invoice.getSalesRep_ID());
		//
		setC_Activity_ID(invoice.getC_Activity_ID());
		setC_Campaign_ID(invoice.getC_Campaign_ID());
		setC_Charge_ID(invoice.getC_Charge_ID());
		setChargeAmt(invoice.getChargeAmt());
		//
		setC_Project_ID(invoice.getC_Project_ID());
		setDateOrdered(invoice.getDateOrdered());
		setDescription(invoice.getDescription());
		setPOReference(invoice.getPOReference());
		setAD_OrgTrx_ID(invoice.getAD_OrgTrx_ID());
		setUser1_ID(invoice.getUser1_ID());
		setUser2_ID(invoice.getUser2_ID());

		// metas
		copyAdditionalCols(order);
		// metas end

		if (order != null)
		{
			setDeliveryRule(order.getDeliveryRule());
			setDeliveryViaRule(order.getDeliveryViaRule());
			setM_Shipper_ID(order.getM_Shipper_ID());
			setFreightCostRule(order.getFreightCostRule());
			setFreightAmt(order.getFreightAmt());

			// Drop Shipment
			setIsDropShip(order.isDropShip());
			setDropShip_BPartner_ID(order.getDropShip_BPartner_ID());
			setDropShip_Location_ID(order.getDropShip_Location_ID());
			setDropShip_User_ID(order.getDropShip_User_ID());
		}
	} // MInOut

	/**
	 * Copy Constructor - create header only
	 *
	 * @param original original
	 * @param movementDate optional movement date (default today)
	 * @param C_DocTypeShipment_ID document type or 0
	 */
	public MInOut(final MInOut original, final int C_DocTypeShipment_ID, final Timestamp movementDate)
	{
		this(original.getCtx(), 0, original.get_TrxName());
		setClientOrg(original);

		setC_BPartner_ID(original.getC_BPartner_ID());
		setC_BPartner_Location_ID(original.getC_BPartner_Location_ID()); // shipment address
		setAD_User_ID(original.getAD_User_ID());
		//
		setM_Warehouse_ID(original.getM_Warehouse_ID());
		setIsSOTrx(original.isSOTrx());
		setMovementType(original.getMovementType());
		if (C_DocTypeShipment_ID == 0)
		{
			setC_DocType_ID(original.getC_DocType_ID());
		}
		else
		{
			setC_DocType_ID(C_DocTypeShipment_ID);
		}

		// Default - Today
		if (movementDate != null)
		{
			setMovementDate(movementDate);
		}
		setDateAcct(getMovementDate());

		// Copy from Order
		setC_Order_ID(original.getC_Order_ID());
		setDeliveryRule(original.getDeliveryRule());
		setDeliveryViaRule(original.getDeliveryViaRule());
		setM_Shipper_ID(original.getM_Shipper_ID());
		setFreightCostRule(original.getFreightCostRule());
		setFreightAmt(original.getFreightAmt());
		setSalesRep_ID(original.getSalesRep_ID());
		//
		setC_Activity_ID(original.getC_Activity_ID());
		setC_Campaign_ID(original.getC_Campaign_ID());
		setC_Charge_ID(original.getC_Charge_ID());
		setChargeAmt(original.getChargeAmt());
		//
		setC_Project_ID(original.getC_Project_ID());
		setDateOrdered(original.getDateOrdered());
		setDescription(original.getDescription());
		setPOReference(original.getPOReference());
		setSalesRep_ID(original.getSalesRep_ID());
		setAD_OrgTrx_ID(original.getAD_OrgTrx_ID());
		setUser1_ID(original.getUser1_ID());
		setUser2_ID(original.getUser2_ID());

		// DropShipment
		setIsDropShip(original.isDropShip());
		setDropShip_BPartner_ID(original.getDropShip_BPartner_ID());
		setDropShip_Location_ID(original.getDropShip_Location_ID());
		setDropShip_User_ID(original.getDropShip_User_ID());

		// metas
		copyAdditionalCols(original);
		// metas end
	} // MInOut

	/** Lines */
	private MInOutLine[] m_lines = null;
	/** Confirmations */
	private MInOutConfirm[] m_confirms = null;
	/** BPartner */
	private MBPartner m_partner = null;

	/**
	 * Get Document Status
	 *
	 * @return Document Status Clear Text
	 */
	public String getDocStatusName()
	{
		return Services.get(IADReferenceDAO.class).retrieveListNameTrl(getCtx(), X_M_InOut.DOCSTATUS_AD_Reference_ID, getDocStatus());
	} // getDocStatusName

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(final String description)
	{
		final String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	} // addDescription

	/**
	 * String representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MInOut[")
				.append(get_ID()).append("-").append(getDocumentNo())
				.append(",DocStatus=").append(getDocStatus())
				.append("]");
		return sb.toString();
	} // toString

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	} // getDocumentInfo

	/**
	 * Create PDF
	 *
	 * @return File or null
	 */
	@Override
	public File createPDF()
	{
		try
		{
			final File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (final Exception e)
		{
			log.error("Could not create PDF", e);
		}
		return null;
	} // getPDF

	/**
	 * Create PDF file
	 *
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(final File file)
	{
		final ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.SHIPMENT, getM_InOut_ID(), get_TrxName());
		if (re == null)
		{
			return null;
		}
		return re.getPDF(file);
	} // createPDF

	/**
	 * Get Lines of Shipment
	 *
	 * @param requery refresh from db
	 * @return lines
	 */
	public MInOutLine[] getLines(final boolean requery)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}

		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		// calling retrieveAllLines because the old code didn't filter by isActive either
		m_lines = LegacyAdapters.convertToPOArray(inOutDAO.retrieveAllLines(this), MInOutLine.class);

		return m_lines;
	} // getMInOutLines

	/**
	 * Get Lines of Shipment
	 *
	 * @return lines
	 */
	public MInOutLine[] getLines()
	{
		return getLines(true);
	} // getLines

	/**
	 * Get Confirmations
	 *
	 * @param requery requery
	 * @return array of Confirmations
	 */
	public MInOutConfirm[] getConfirmations(final boolean requery)
	{
		if (m_confirms != null && !requery)
		{
			set_TrxName(m_confirms, get_TrxName());
			return m_confirms;
		}
		final List<MInOutConfirm> list = new Query(getCtx(), MInOutConfirm.Table_Name, "M_InOut_ID=?", get_TrxName())
				.setParameters(new Object[] { getM_InOut_ID() })
				.list();
		m_confirms = new MInOutConfirm[list.size()];
		list.toArray(m_confirms);
		return m_confirms;
	} // getConfirmations

	/**
	 * Copy Lines From other Shipment
	 *
	 * @param otherShipment shipment
	 * @param counter set counter info
	 * @param setOrder set order link
	 * @return number of lines copied
	 */
	public int copyLinesFrom(final MInOut otherShipment, final boolean counter, final boolean setOrder)
	{
		if (isProcessed() || isPosted() || otherShipment == null)
		{
			return 0;
		}
		final MInOutLine[] fromLines = otherShipment.getLines();
		int count = 0;
		for (final MInOutLine fromLine : fromLines)
		{
			final MInOutLine line = new MInOutLine(this);
			line.set_TrxName(get_TrxName());
			if (counter)
			{
				PO.copyValues(fromLine, line, getAD_Client_ID(), getAD_Org_ID());
			}
			else
			{
				PO.copyValues(fromLine, line, fromLine.getAD_Client_ID(), fromLine.getAD_Org_ID());
			}
			line.setM_InOut_ID(getM_InOut_ID());
			line.set_ValueNoCheck("M_InOutLine_ID", I_ZERO); // new
			// Reset
			if (!setOrder)
			{
				line.setC_OrderLine_ID(0);
				line.setM_RMALine_ID(0); // Reset RMA Line
			}
			if (!counter)
			{
				line.setM_AttributeSetInstance_ID(0);
			}
			// line.setS_ResourceAssignment_ID(0);
			line.setRef_InOutLine_ID(0);
			line.setIsInvoiced(false);
			//
			line.setConfirmedQty(BigDecimal.ZERO);
			line.setPickedQty(BigDecimal.ZERO);
			line.setScrappedQty(BigDecimal.ZERO);
			line.setTargetQty(BigDecimal.ZERO);
			// Set Locator based on header Warehouse
			if (getM_Warehouse_ID() != otherShipment.getM_Warehouse_ID())
			{
				line.setM_Locator_ID(0);
				line.setM_Locator_ID(BigDecimal.ZERO);
			}
			//
			if (counter)
			{
				line.setRef_InOutLine_ID(fromLine.getM_InOutLine_ID());
				if (fromLine.getC_OrderLine_ID() != 0)
				{
					final MOrderLine peer = new MOrderLine(getCtx(), fromLine.getC_OrderLine_ID(), get_TrxName());
					if (peer.getRef_OrderLine_ID() != 0)
					{
						line.setC_OrderLine_ID(peer.getRef_OrderLine_ID());
					}
				}
				// RMALine link
				if (fromLine.getM_RMALine_ID() != 0)
				{
					final MRMALine peer = new MRMALine(getCtx(), fromLine.getM_RMALine_ID(), get_TrxName());
					if (peer.getRef_RMALine_ID() > 0)
					{
						line.setM_RMALine_ID(peer.getRef_RMALine_ID());
					}
				}
			}
			//
			line.setProcessed(false);
			if (line.save(get_TrxName()))
			{
				count++;
			}
			// Cross Link
			if (counter)
			{
				fromLine.setRef_InOutLine_ID(line.getM_InOutLine_ID());
				fromLine.save(get_TrxName());
			}
		}
		if (fromLines.length != count)
		{
			log.error("Line difference - From=" + fromLines.length + " <> Saved=" + count);
		}
		return count;
	} // copyLinesFrom

	/** Reversal Flag */
	private boolean m_reversal = false;

	/**
	 * Set Reversal
	 *
	 * @param reversal reversal
	 */
	private void setReversal(final boolean reversal)
	{
		m_reversal = reversal;
	} // setReversal

	/**
	 * Is Reversal
	 *
	 * @return reversal
	 */
	private boolean isReversal()
	{
		return m_reversal;
	} // isReversal

	/**
	 * Set Processed. Propagate to Lines/Taxes
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(final boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
		{
			return;
		}
		final String sql = "UPDATE M_InOutLine SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE M_InOut_ID=" + getM_InOut_ID();
		final int noLine = DB.executeUpdate(sql, get_TrxName());
		m_lines = null;
		log.debug("{} - Lines={}", processed, noLine);
	} // setProcessed

	/**
	 * Get BPartner
	 *
	 * @return partner
	 */
	public MBPartner getBPartner()
	{
		if (m_partner == null)
		{
			m_partner = new MBPartner(getCtx(), getC_BPartner_ID(), get_TrxName());
		}
		return m_partner;
	} // getPartner

	/**
	 * Set Document Type
	 *
	 * @param DocBaseType doc type MDocType.DOCBASETYPE_
	 */
	public void setC_DocType_ID(final String DocBaseType)
	{
		final String sql = "SELECT C_DocType_ID FROM C_DocType "
				+ "WHERE AD_Client_ID=? AND DocBaseType=?"
				+ " AND IsActive='Y'"
				+ " AND IsSOTrx='" + (isSOTrx() ? "Y" : "N") + "' "
				+ "ORDER BY IsDefault DESC";
		final int C_DocType_ID = DB.getSQLValue(null, sql, getAD_Client_ID(), DocBaseType);
		if (C_DocType_ID <= 0)
		{
			log.error("Not found for AC_Client_ID=" + getAD_Client_ID() + " - " + DocBaseType);
		}
		else
		{
			log.debug("DocBaseType={} - C_DocType_ID={}", DocBaseType, C_DocType_ID);
			setC_DocType_ID(C_DocType_ID);
			final boolean isSOTrx = MDocType.DOCBASETYPE_MaterialDelivery.equals(DocBaseType);
			setIsSOTrx(isSOTrx);
		}
	} // setC_DocType_ID

	/**
	 * Set Default C_DocType_ID. Based on SO flag
	 */
	public void setC_DocType_ID()
	{
		if (isSOTrx())
		{
			setC_DocType_ID(MDocType.DOCBASETYPE_MaterialDelivery);
		}
		else
		{
			setC_DocType_ID(MDocType.DOCBASETYPE_MaterialReceipt);
		}
	} // setC_DocType_ID

	/**
	 * Set Business Partner Defaults & Details
	 *
	 * @param bp business partner
	 */
	public void setBPartner(final MBPartner bp)
	{
		if (bp == null)
		{
			return;
		}

		setC_BPartner_ID(bp.getC_BPartner_ID());

		// Set Locations
		final MBPartnerLocation[] locs = bp.getLocations(false);
		if (locs != null)
		{
			for (final MBPartnerLocation loc : locs)
			{
				if (loc.isShipTo())
				{
					setC_BPartner_Location_ID(loc.getC_BPartner_Location_ID());
				}
			}
			// set to first if not set
			if (getC_BPartner_Location_ID() == 0 && locs.length > 0)
			{
				setC_BPartner_Location_ID(locs[0].getC_BPartner_Location_ID());
			}
		}
		if (getC_BPartner_Location_ID() == 0)
		{
			log.error("Has no To Address: {}", bp);
		}

		// Set Contact
		final List<I_AD_User> contacts = bp.getContacts(false);
		if (contacts != null && contacts.size() > 0)
		{
			setAD_User_ID(contacts.get(0).getAD_User_ID());
		}
	} // setBPartner

	/**
	 * Create the missing next Confirmation
	 */
	public void createConfirmation()
	{
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		final boolean pick = dt.isPickQAConfirm();
		final boolean ship = dt.isShipConfirm();
		// Nothing to do
		if (!pick && !ship)
		{
			log.trace("Create confirmations not need");
			return;
		}

		// Create Both .. after each other
		if (pick && ship)
		{
			boolean havePick = false;
			boolean haveShip = false;
			final MInOutConfirm[] confirmations = getConfirmations(false);
			for (final MInOutConfirm confirmation : confirmations)
			{
				final MInOutConfirm confirm = confirmation;
				if (MInOutConfirm.CONFIRMTYPE_PickQAConfirm.equals(confirm.getConfirmType()))
				{
					if (!confirm.isProcessed())  // wait intil done
					{
						log.debug("Unprocessed: {}", confirm);
						return;
					}
					havePick = true;
				}
				else if (MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm.equals(confirm.getConfirmType()))
				{
					haveShip = true;
				}
			}
			// Create Pick
			if (!havePick)
			{
				MInOutConfirm.create(this, MInOutConfirm.CONFIRMTYPE_PickQAConfirm, false);
				return;
			}
			// Create Ship
			if (!haveShip)
			{
				MInOutConfirm.create(this, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, false);
				return;
			}
			return;
		}
		// Create just one
		if (pick)
		{
			MInOutConfirm.create(this, MInOutConfirm.CONFIRMTYPE_PickQAConfirm, true);
		}
		else if (ship)
		{
			MInOutConfirm.create(this, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, true);
		}
	} // createConfirmation

	/**
	 * Set Warehouse and check/set Organization
	 *
	 * @param M_Warehouse_ID id
	 */
	@Override
	public void setM_Warehouse_ID(final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID == 0)
		{
			log.error("Ignored - Cannot set AD_Warehouse_ID to 0");
			return;
		}
		super.setM_Warehouse_ID(M_Warehouse_ID);
		//
		// metas: suppress overwriting of the ord id
		// MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
		// if (wh.getAD_Org_ID() != getAD_Org_ID())
		// {
		// log.warn("M_Warehouse_ID=" + M_Warehouse_ID
		// + ", Overwritten AD_Org_ID=" + getAD_Org_ID() + "->" +
		// wh.getAD_Org_ID());
		// setAD_Org_ID(wh.getAD_Org_ID());
		// }
		//
	} // setM_Warehouse_ID

	@Override
	protected boolean beforeDelete()
	{
		for (final I_M_InOutLine inoutLine : Services.get(IInOutDAO.class).retrieveAllLines(this))
		{
			InterfaceWrapperHelper.delete(inoutLine);
		}

		return true;
	}

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true or false
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// Warehouse Org
		// metas: allow differend org ids
		// if (newRecord)
		// {
		// MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
		// if (wh.getAD_Org_ID() != getAD_Org_ID())
		// {
		// log.error("WarehouseOrgConflict", "");
		// return false;
		// }
		// metas end
		// }

		// Shipment/Receipt can have either Order/RMA (For Movement type)
		if (getC_Order_ID() != 0 && getM_RMA_ID() != 0)
		{
			throw new AdempiereException("@OrderOrRMA@");
		}

		// // Shipment - Needs Order/RMA
		// if (!getMovementType().contentEquals(MInOut.MOVEMENTTYPE_CustomerReturns) && isSOTrx() && getC_Order_ID() == 0 && getM_RMA_ID() == 0)
		// {
		// log.error("FillMandatory", Msg.translate(getCtx(), "C_Order_ID"));
		// return false;
		// }

		if (isSOTrx() && getM_RMA_ID() != 0)
		{
			// Set Document and Movement type for this Receipt
			final MRMA rma = new MRMA(getCtx(), getM_RMA_ID(), get_TrxName());
			final MDocType docType = MDocType.get(getCtx(), rma.getC_DocType_ID());
			setC_DocType_ID(docType.getC_DocTypeShipment_ID());
		}

		// metas
		final IPOService poService = Services.get(IPOService.class);
		if ("".equals(poService.getValue(this, I_M_InOut.COLUMNNAME_Incoterm)))
		{
			poService.setValue(this, I_M_InOut.COLUMNNAME_IncotermLocation, "");
		}

		return true;
	} // beforeSave

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!success || newRecord)
		{
			return success;
		}

		if (is_ValueChanged("AD_Org_ID"))
		{
			final String sql = "UPDATE M_InOutLine ol" + " SET AD_Org_ID ="
					+ "(SELECT AD_Org_ID"
					+ " FROM M_InOut o WHERE ol.M_InOut_ID=o.M_InOut_ID) "
					+ "WHERE M_InOut_ID=" + getC_Order_ID();
			final int no = DB.executeUpdate(sql, get_TrxName());
			log.debug("Lines -> #{}", no);
		}
		return true;
	} // afterSave

	/**************************************************************************
	 * Process document
	 *
	 * @param processAction document action
	 * @return true if performed
	 */
	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
	}

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	/**
	 * Unlock Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean unlockIt()
	{
		setProcessing(false);
		return true;
	} // unlockIt

	/**
	 * Invalidate Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		setDocAction(DOCACTION_Prepare);
		return true;
	} // invalidateIt

	/**
	 * Prepare Document
	 *
	 * @return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		// Order OR RMA can be processed on a shipment/receipt
		if (getC_Order_ID() != 0 && getM_RMA_ID() != 0)
		{
			m_processMsg = "@OrderOrRMA@";
			return IDocument.STATUS_Invalid;
		}
		// Std Period open?
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());

		// Credit Check
		checkCreditLimit();

		// Lines
		final MInOutLine[] lines = getLines(true);
		if (lines == null || lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return IDocument.STATUS_Invalid;
		}
		BigDecimal Volume = BigDecimal.ZERO;
		BigDecimal Weight = BigDecimal.ZERO;

		// Mandatory Attributes
		for (final MInOutLine line : lines)
		{
			final MProduct product = line.getProduct();
			if (product != null)
			{
				Volume = Volume.add(product.getVolume().multiply(line.getMovementQty()));
				Weight = Weight.add(product.getWeight().multiply(line.getMovementQty()));
			}
			//
			if (line.getM_AttributeSetInstance_ID() != 0)
			{
				continue;
			}
			if (product != null && product.isASIMandatory(isSOTrx()))
			{
				throw new ProductASIMandatoryException(this, product, line.getLine());
			}
		}
		setVolume(Volume);
		setWeight(Weight);

		if (!isReversal())  // don't change reversal
		{
			createConfirmation();
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}
		return IDocument.STATUS_InProgress;
	} // prepareIt


	private void checkCreditLimit()
	{
		if (!isCheckCreditLimitNeeded())
		{
			return;
		}

		final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);
		final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);

		final I_C_BPartner partner = InterfaceWrapperHelper.create(getCtx(), getC_BPartner_ID(), I_C_BPartner.class, get_TrxName());
		final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(partner);
		final String soCreditStatus = stats.getSOCreditStatus();
		final BigDecimal creditUsed = stats.getSOCreditUsed();

		final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(getC_BPartner_ID(), getMovementDate());

		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(soCreditStatus))
		{
			throw new AdempiereException("@BPartnerCreditStop@ - @SO_CreditUsed@="
					+ creditUsed
					+ ", @SO_CreditLimit@=" + creditLimit);
		}
		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold.equals(soCreditStatus))
		{
			throw new AdempiereException("@BPartnerCreditHold@ - @SO_CreditUsed@="
					+ creditUsed
					+ ", @SO_CreditLimit@=" + creditLimit);
		}

		final BigDecimal notInvoicedAmt = MBPartner.getNotInvoicedAmt(getC_BPartner_ID());

		final CalculateSOCreditStatusRequest request = CalculateSOCreditStatusRequest.builder()
				.stat(stats)
				.additionalAmt(notInvoicedAmt)
				.date(getMovementDate())
				.build();
		final String calculatedCreditStatus = bpartnerStatsBL.calculateSOCreditStatus(request);
		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold.equals(calculatedCreditStatus))
		{
			throw new AdempiereException("@BPartnerOverSCreditHold@ - @TotalOpenBalance@="
					+ creditUsed + ", @NotInvoicedAmt@=" + notInvoicedAmt
					+ ", @SO_CreditLimit@=" + creditLimit);
		}
	}

	private boolean isCheckCreditLimitNeeded()
	{
		if (!(isSOTrx() && !isReversal()))
		{
			return false;
		}

		final I_C_Order order = getC_Order();
		final boolean checkCreditOnPrepayOorder = Services.get(ISysConfigBL.class).getBooleanValue("CHECK_CREDIT_ON_PREPAY_ORDER", true, getAD_Client_ID(), getAD_Org_ID());
		// ignore -- don't validate Prepay Orders depending on sysconfig parameter
		return (!(order != null && order.getC_Order_ID() > 0
				&& MDocType.DOCSUBTYPE_PrepayOrder.equals(order.getC_DocType().getDocSubType())
				&& !checkCreditOnPrepayOorder));
	}

	/**
	 * Approve Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
		setIsApproved(true);
		return true;
	} // approveIt

	/**
	 * Reject Approval
	 *
	 * @return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		setIsApproved(false);
		return true;
	} // rejectIt

	/**
	 * Complete Document
	 *
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			final String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		// Outstanding (not processed) Incoming Confirmations ?
		final MInOutConfirm[] confirmations = getConfirmations(true);
		for (final MInOutConfirm confirmation : confirmations)
		{
			final MInOutConfirm confirm = confirmation;
			if (!confirm.isProcessed())
			{
				if (MInOutConfirm.CONFIRMTYPE_CustomerConfirmation.equals(confirm.getConfirmType()))
				{
					continue;
				}
				//
				m_processMsg = "Open @M_InOutConfirm_ID@: " +
						confirm.getConfirmTypeName() + " - " + confirm.getDocumentNo();
				return IDocument.STATUS_InProgress;
			}
		}

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}
		log.debug("Completed: {}", this);
		final StringBuilder info = new StringBuilder();

		// For all lines
		final MInOutLine[] lines = getLines();

		sortByProductAndASI(lines); // task 08999

		for (final MInOutLine line : lines)
		{
			final MInOutLine sLine = line;
			final MProduct product = sLine.getProduct();

			// Qty & Type
			final String MovementType = getMovementType();
			BigDecimal Qty = sLine.getMovementQty();
			if (MovementType.charAt(1) == '-')
			{
				Qty = Qty.negate();
			}
			BigDecimal QtySO = BigDecimal.ZERO;
			BigDecimal QtyPO = BigDecimal.ZERO;

			// Update Order Line
			MOrderLine oLine = null;
			if (sLine.getC_OrderLine_ID() != 0)
			{
				oLine = new MOrderLine(getCtx(), sLine.getC_OrderLine_ID(), get_TrxName());
				log.debug("OrderLine - Reserved={}, Delivered={}", oLine.getQtyReserved(), oLine.getQtyDelivered());
				if (isSOTrx())
				{
					QtySO = mkQtyReservedDiff(oLine, sLine); // metas us1251: omit negative qtyReserved
				}
				else
				{
					QtyPO = sLine.getMovementQty();
				}
			}

			// Load RMA Line
			MRMALine rmaLine = null;

			if (sLine.getM_RMALine_ID() != 0)
			{
				rmaLine = new MRMALine(getCtx(), sLine.getM_RMALine_ID(), get_TrxName());
			}

			log.debug("Line={} - Qty={}", sLine.getLine(), sLine.getMovementQty());

			// Stock Movement - Counterpart MOrder.reserveStock
			if (product != null
					&& Services.get(IProductBL.class).isStocked(product))
			{
				// Ignore the Material Policy when is Reverse Correction
				if (!isReversal())
				{
					checkMaterialPolicy(sLine);
				}

				log.debug("Material Transaction");
				MTransaction mtrx = null;
				// same warehouse in order and receipt?
				boolean sameWarehouse = true;
				// Reservation ASI - assume none
				int reservationAttributeSetInstance_ID = 0; // sLine.getM_AttributeSetInstance_ID();
				if (oLine != null)
				{
					reservationAttributeSetInstance_ID = oLine.getM_AttributeSetInstance_ID();
					sameWarehouse = Services.get(IWarehouseAdvisor.class).evaluateWarehouse(oLine).getM_Warehouse_ID() == getM_Warehouse_ID();
				}

				final IStorageBL storageBL = Services.get(IStorageBL.class);

				//
				if (sLine.getM_AttributeSetInstance_ID() == 0)
				{
					final MInOutLineMA mas[] = MInOutLineMA.get(getCtx(),
							sLine.getM_InOutLine_ID(), get_TrxName());
					for (final MInOutLineMA ma : mas)
					{
						BigDecimal QtyMA = ma.getMovementQty();
						if (MovementType.charAt(1) == '-')
						{
							QtyMA = QtyMA.negate();
						}
						BigDecimal reservedDiff = BigDecimal.ZERO;
						BigDecimal orderedDiff = BigDecimal.ZERO;
						if (sLine.getC_OrderLine_ID() != 0)
						{
							if (isSOTrx())
							{
								reservedDiff = ma.getMovementQty().negate();
							}
							else
							{
								orderedDiff = ma.getMovementQty().negate();
							}
						}

						// Update Storage - see also VMatch.createMatchRecord
						// task 08999 : update the storage async
						storageBL.addAsync(
								getCtx(),
								getM_Warehouse_ID(),
								sLine.getM_Locator_ID(),
								sLine.getM_Product_ID(),
								ma.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
								QtyMA,
								sameWarehouse ? reservedDiff : BigDecimal.ZERO,
								sameWarehouse ? orderedDiff : BigDecimal.ZERO,
								get_TrxName());
						if (!sameWarehouse)
						{
							// correct qtyOrdered in warehouse of order
							final MWarehouse wh = MWarehouse.get(getCtx(), Services.get(IWarehouseAdvisor.class).evaluateWarehouse(oLine).getM_Warehouse_ID());
							// task 08999 : update the storage async
							storageBL.addAsync(
									getCtx(),
									Services.get(IWarehouseAdvisor.class).evaluateWarehouse(oLine).getM_Warehouse_ID(),
									Services.get(IWarehouseBL.class).getDefaultLocator(wh).getM_Locator_ID(),
									sLine.getM_Product_ID(),
									ma.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
									BigDecimal.ZERO, reservedDiff, orderedDiff, get_TrxName());
						}
						// Create Transaction
						mtrx = new MTransaction(getCtx(),
								sLine.getAD_Org_ID(),
								MovementType,
								sLine.getM_Locator_ID(),
								sLine.getM_Product_ID(),

								// #gh489: M_Storage is a legacy and currently doesn't really work.
								// In this case, its use of M_AttributeSetInstance_ID (which is forwarded from storage to 'ma') introduces a coupling between random documents.
								// this coupling is a big problem, so we don't forward the ASI-ID to the M_Transaction
								0, // ma.getM_AttributeSetInstance_ID(),

								QtyMA,
								getMovementDate(),
								get_TrxName());
						mtrx.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
						InterfaceWrapperHelper.save(mtrx);
					}
				}
				// sLine.getM_AttributeSetInstance_ID() != 0
				if (mtrx == null)
				{
					final BigDecimal reservedDiff = sameWarehouse ? QtySO.negate() : BigDecimal.ZERO;
					final BigDecimal orderedDiff = sameWarehouse ? QtyPO.negate() : BigDecimal.ZERO;

					// Fallback: Update Storage - see also VMatch.createMatchRecord
					// task 08999 : update the storage async
					storageBL.addAsync(
							getCtx(),
							getM_Warehouse_ID(),
							sLine.getM_Locator_ID(),
							sLine.getM_Product_ID(),
							sLine.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
							Qty, reservedDiff, orderedDiff, get_TrxName());
					if (!sameWarehouse)
					{
						// correct qtyOrdered in warehouse of order
						final MWarehouse wh = MWarehouse.get(getCtx(), Services.get(IWarehouseAdvisor.class).evaluateWarehouse(oLine).getM_Warehouse_ID());
						// task 08999 : update the storage async
						storageBL.addAsync(
								getCtx(),
								Services.get(IWarehouseAdvisor.class).evaluateWarehouse(oLine).getM_Warehouse_ID(),
								Services.get(IWarehouseBL.class).getDefaultLocator(wh).getM_Locator_ID(), +sLine.getM_Product_ID(),
								sLine.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
								BigDecimal.ZERO, QtySO.negate(), QtyPO.negate(), get_TrxName());
					}
					// FallBack: Create Transaction
					mtrx = new MTransaction(getCtx(), sLine.getAD_Org_ID(),
							MovementType, sLine.getM_Locator_ID(),
							sLine.getM_Product_ID(), sLine.getM_AttributeSetInstance_ID(),
							Qty, getMovementDate(), get_TrxName());
					mtrx.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
					InterfaceWrapperHelper.save(mtrx);
				}
			}  // stock movement

			// Correct Order Line
			// task 09358: get rid of this; instead, update qtyReserved at one central place
			// if (product != null && oLine != null) // other in VMatch.createMatchRecord
			// {
			// oLine.setQtyReserved(oLine.getQtyReserved().subtract(
			// mkQtyReservedDiff(oLine, sLine))); // metas us1251: ommit negative qtyReserved value
			// }
			// Update Sales Order Line
			if (oLine != null)
			{
				if (isSOTrx() // PO is done by MatchPO (further down this method)
						|| sLine.getM_Product_ID() == 0)  // PO Charges, empty lines
				{
					BigDecimal qtyDeliveredAbs = Qty;

					// in case of no product_ID (charge) the qtyDelivered must be negated
					if (isSOTrx())
					{
						qtyDeliveredAbs = qtyDeliveredAbs.negate();
					}

					oLine.setQtyDelivered(oLine.getQtyDelivered().add(qtyDeliveredAbs));
					oLine.setDateDelivered(getMovementDate()); // overwrite=last
				}
				InterfaceWrapperHelper.save(oLine);
			}
			// Update RMA Line Qty Delivered
			else if (rmaLine != null)
			{
				if (isSOTrx())
				{
					rmaLine.setQtyDelivered(rmaLine.getQtyDelivered().add(Qty));
				}
				else
				{
					rmaLine.setQtyDelivered(rmaLine.getQtyDelivered().subtract(Qty));
				}
				InterfaceWrapperHelper.save(rmaLine);
			}

			// Create Asset for SO
			if (product != null
					&& isSOTrx()
					&& product.isCreateAsset()
					&& sLine.getMovementQty().signum() > 0
					&& !isReversal())
			{
				log.debug("Asset");
				info.append("@A_Asset_ID@: ");
				int noAssets = sLine.getMovementQty().intValue();
				if (!product.isOneAssetPerUOM())
				{
					noAssets = 1;
				}
				for (int i = 0; i < noAssets; i++)
				{
					if (i > 0)
					{
						info.append(" - ");
					}
					int deliveryCount = i + 1;
					if (!product.isOneAssetPerUOM())
					{
						deliveryCount = 0;
					}
					final MAsset asset = new MAsset(this, sLine, deliveryCount);
					if (!asset.save(get_TrxName()))
					{
						m_processMsg = "Could not create Asset";
						return IDocument.STATUS_Invalid;
					}
					info.append(asset.getValue());
				}
			}  // Asset

			//
			// Matching

			MInvoiceLine iLine = MInvoiceLine.getOfInOutLine(sLine);
			final BigDecimal qtyMoved = sLine.getMovementQty();

			if (sLine.getM_Product_ID() > 0
					&& !isReversal())
			{
				//
				// MatchPO
				if (!isSOTrx())
				{
					// Invoice - Receipt Match (requires Product)

					// Link to Order
					if (sLine.getC_OrderLine_ID() > 0)
					{
						log.debug("PO Matching");
						// Ship - PO
						final MMatchPO po = MMatchPO.create(null, sLine, getMovementDate(), qtyMoved);
						InterfaceWrapperHelper.save(po, get_TrxName());

						// Update PO with ASI
						if (oLine != null && oLine.getM_AttributeSetInstance_ID() == 0
								&& sLine.getMovementQty().compareTo(oLine.getQtyOrdered()) == 0)  // just if full match [
						// 1876965 ]
						{
							oLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
							oLine.saveEx(get_TrxName()); // metas: using saveEx to be notified of trouble
						}
					}
					// No Order - Try finding links via Invoice
					else
					{
						// Invoice has an Order Link
						if (iLine != null && iLine.getC_OrderLine_ID() > 0)
						{
							// Invoice is created before Shipment
							log.debug("PO(Inv) Matching");
							// Ship - Invoice
							final MMatchPO po = MMatchPO.create(iLine, sLine, getMovementDate(), qtyMoved);
							InterfaceWrapperHelper.save(po, get_TrxName());

							// Update PO with ASI
							oLine = new MOrderLine(getCtx(), po.getC_OrderLine_ID(), get_TrxName());
							if (oLine != null && oLine.getM_AttributeSetInstance_ID() == 0
									&& sLine.getMovementQty().compareTo(oLine.getQtyOrdered()) == 0)  // just if full match [
							// 1876965 ]
							{
								oLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
								oLine.saveEx(get_TrxName()); // metas: using saveEx to be notified of trouble
							}
						}
					}  // No Order
				}  // PO Matching

				// Note: we now also create a MatchInv for SO-InOuts.
				// However, we only do it "if (sLine.getM_Product_ID() != 0 && !isReversal())" as before.
				// I don't really understand what the "!isReversal()" part about, so i'll leave it too
				// 07742: Load line again in case it was changed by the MMatchPO
				iLine = MInvoiceLine.getOfInOutLine(sLine);
				if (iLine != null && iLine.getM_Product_ID() > 0)
				{
					final boolean matchInvCreated = Services.get(IMatchInvBL.class).createMatchInvBuilder()
							.setContext(this)
							.setC_InvoiceLine(iLine)
							.setM_InOutLine(sLine)
							.setDateTrx(getMovementDate())
							.setConsiderQtysAlreadyMatched(false) // backward compatibility
							.setAllowQtysOfOppositeSigns(true) // backward compatibility
							.setSkipIfMatchingsAlreadyExist(true) // backward compatibility
							.build();

					// Update matched invoice line's ASI
					if (matchInvCreated && iLine.getM_AttributeSetInstance_ID() != sLine.getM_AttributeSetInstance_ID())
					{
						iLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
						iLine.save();
					}
				}
			}
		}  // for all lines

		// Counter Documents
		final MInOut counter = createCounterDoc();
		if (counter != null)
		{
			info.append(" - @CounterDoc@: @M_InOut_ID@=").append(counter.getDocumentNo());
		}

		// task 08921: we don't want an automatically created dropship shipment. They are created via shipmentschedule, just like all the other shipments!
// @formatter:off
//		// Drop Shipments
//		for (final MInOut dropShipment : createDropShipment())
//		{
//			info.append(" - @DropShipment@: @M_InOut_ID@=").append(dropShipment.getDocumentNo());
//		}
// @formatter:on

		// User Validation
		final String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return IDocument.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		m_processMsg = info.toString();
		setProcessed(true);
		setDocAction(DOCACTION_Re_Activate); // 08656: Default action Re-Activate
		return IDocument.STATUS_Completed;
	} // completeIt

	/**
	 * Order the lines to avoid deadlocks in MStorage, when multiple concurrent threads try to acquire locks on on the same set of storages, but in different orderings
	 *
	 * @param lines
	 * @task http://dewiki908/mediawiki/index.php/08999_Lieferdisposition_a.frieden_%28104263801724%29
	 */
	private static void sortByProductAndASI(final MInOutLine[] lines)
	{
		final ComparatorChain<MInOutLine> c = new ComparatorChain<>();
		c.addComparator((o1, o2) -> Integer.compare(o1.getM_Product_ID(), o2.getM_Product_ID()));
		c.addComparator((o1, o2) -> Integer.compare(o1.getM_AttributeSetInstance_ID(), o2.getM_AttributeSetInstance_ID()));
		Arrays.sort(lines, c);
	}

	// metas us1251: ommit negative qtyReserved value
	/**
	 * Helper method to omit negative qtyreserved values Returns either ol.getQtyReserved() or iol.getMovementQty()
	 */
	private BigDecimal mkQtyReservedDiff(final I_C_OrderLine ol, final I_M_InOutLine iol)
	{
		Check.assumeNotNull(iol, "Param 'iol' not null");
		if (ol == null)
		{
			return iol.getMovementQty();
		}
		if (ol.getQtyReserved().compareTo(iol.getMovementQty()) >= 0)
		{
			return iol.getMovementQty();
		}
		return ol.getQtyReserved();
	}

	// metas us1251: end

	/**
	 * Renumber Lines Without Comment
	 *
	 * @param step start and step
	 */
	public void renumberLinesWithoutComment(final int step)
	{
		//
		// Services
		final IInOutBL inOutBL = Services.get(IInOutBL.class);

		int number = step;

		// task 08295: sort the lines by their referenced order lines
		final List<I_M_InOutLine> linesSorted = inOutBL.sortLines(this);

		for (final I_M_InOutLine line : linesSorted)
		{
			if (line.getLine() % step == 0)
			{
				line.setLine(number);
				number += step;
			}
			else
			{
				if (line.getLine() % 2 == 0)
				{
					line.setLine(number - 2);
				}
				else
				{
					line.setLine(number - 1);
				}
			}
			InterfaceWrapperHelper.save(line);
		}
		m_lines = null;
	} // renumberLinesWithoutComment

	// task 08921: we don't want an automatically created dropship shipment. They are created via shipmentschedule, just like all the other shipments!
// @formatter:off
//	/**
//	 * Automatically creates a customer shipment for any drop shipment material receipt Based on createCounterDoc() by JJ
//	 *
//	 * @return shipment if created else null
//	 */
//	private List<MInOut> createDropShipment()
//	{
//		if (isSOTrx() || getC_Order_ID() == 0)
//		{
//			return Collections.emptyList();
//		}
//
//		if (getM_Warehouse_ID() != retrieveDropShipWarehouseId(getAD_Org_ID()))
//		{
//			return Collections.emptyList();
//		}
//
//		// Document Type
//		int C_DocTypeTarget_ID = 0;
//		final MDocType[] shipmentTypes = MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_MaterialDelivery);
//
//		for (int i = 0; i < shipmentTypes.length; i++)
//		{
//			if (shipmentTypes[i].isSOTrx() && (C_DocTypeTarget_ID == 0 || shipmentTypes[i].isDefault()))
//			{
//				C_DocTypeTarget_ID = shipmentTypes[i].getC_DocType_ID();
//			}
//		}
//
//		final Map<Integer, List<MOrderLine>> linkedOrderID2soOl = new HashMap<Integer, List<MOrderLine>>();
//		final Map<Integer, List<MOrderLine>> poOlID2soOl = new HashMap<Integer, List<MOrderLine>>();
//		final Map<Integer, MInOutLine> soOlId2iol = new HashMap<Integer, MInOutLine>();
//		final Map<MInOutLine, BigDecimal> iol2QtyLeft = new HashMap<MInOutLine, BigDecimal>();
//
//		final I_AD_RelationType relType = MRelationType.retrieveForInternalName(getCtx(), MMPurchaseSchedule.RELTYPE_SO_LINE_PO_LINE_INT_NAME, get_TrxName());
//
//		for (final MInOutLine iol : this.getLines(true))
//		{
//			iol2QtyLeft.put(iol, iol.getMovementQty());
//
//			final List<MOrderLine> soOls = new ArrayList<>();
//
//			final MOrderLine poOl = (MOrderLine)iol.getC_OrderLine();
//			boolean hasPurchaseOrderLine = poOl != null && poOl.getC_OrderLine_ID() > 0;
//			if (hasPurchaseOrderLine)
//			{
//				soOls.addAll(MRelation.<MOrderLine>retrieveDestinations(getCtx(), relType, poOl, get_TrxName()));
//			}
//
//			if (hasPurchaseOrderLine && soOls.isEmpty())
//			{
//				if (poOl.getLink_OrderLine_ID() > 0)
//				{
//					// falling back on Link_OrderLine
//					soOls.add((MOrderLine)poOl.getLink_OrderLine());
//				}
//			}
//			if (hasPurchaseOrderLine && !soOls.isEmpty())
//			{
//				poOlID2soOl.put(poOl.getC_OrderLine_ID(), soOls);
//
//				for (final MOrderLine soOl : soOls)
//				{
//					List<MOrderLine> soOlsForLinkedOrder = linkedOrderID2soOl.get(soOl.getC_Order_ID());
//
//					if (soOlsForLinkedOrder == null)
//					{
//						soOlsForLinkedOrder = new ArrayList<MOrderLine>();
//						linkedOrderID2soOl.put(soOl.getC_Order_ID(), soOlsForLinkedOrder);
//					}
//					soOlsForLinkedOrder.add(soOl);
//
//					assert !soOlId2iol.containsKey(soOl.getC_OrderLine_ID());
//					soOlId2iol.put(soOl.getC_OrderLine_ID(), iol);
//				}
//			}
//		}
//
//		final List<MInOut> dropShipments = new ArrayList<MInOut>();
//
//		MInOut currentDropShipment = null;
//		for (final int linkedOrderID : linkedOrderID2soOl.keySet())
//		{
//			for (final MOrderLine soOl : linkedOrderID2soOl.get(linkedOrderID))
//			{
//				final BigDecimal qtyMax = soOl.getQtyReserved();
//				final MInOutLine iol = soOlId2iol.get(soOl.getC_OrderLine_ID());
//
//				final BigDecimal qtyLeft = iol2QtyLeft.get(iol);
//
//				final BigDecimal qty;
//				if (qtyLeft.compareTo(qtyMax) > 0)
//				{
//					qty = qtyMax;
//					iol2QtyLeft.put(iol, qtyLeft.subtract(qtyMax)); // store the new value in iol2QtyLeft for later use
//				}
//				else
//				{
//					qty = qtyLeft;
//					iol2QtyLeft.put(iol, BigDecimal.ZERO); // store the new value in iol2QtyLeft for later use
//				}
//				if (qty.signum() <= 0)
//				{ // no more qty to allocate
//					continue;
//				}
//
//				if (currentDropShipment == null)
//				{
//					currentDropShipment = mkDropshipmentHeader(C_DocTypeTarget_ID, linkedOrderID);
//				}
//
//				assert currentDropShipment != null;
//				final MInOutLine dropshipLine = new MInOutLine(currentDropShipment);
//				dropshipLine.setOrderLine(soOl, iol.getM_Locator_ID(), qty);
//				dropshipLine.setRef_InOutLine_ID(iol.getM_InOutLine_ID());
//				dropshipLine.setQty(qty);
//
//				dropshipLine.saveEx();
//			}
//
//			if (currentDropShipment != null)
//			{
//				log.debug("Completing " + currentDropShipment.toString());
//
//				currentDropShipment.setDocAction(DocAction.ACTION_Complete);
//				if (!currentDropShipment.processIt(DocAction.ACTION_Complete))
//				{
//					throw new AdempiereException("Unable to complete " + currentDropShipment + "; Msg: " + currentDropShipment.getProcessMsg());
//				}
//				currentDropShipment.saveEx();
//
//				dropShipments.add(currentDropShipment);
//				currentDropShipment = null;
//			}
//		}
//
//		return dropShipments;
//	}
//
//	private MInOut mkDropshipmentHeader(int C_DocTypeTarget_ID, final int linkedOrderID)
//	{
//		final MInOut dropShipment = copyHeader(this, getMovementDate(), getDateAcct(), C_DocTypeTarget_ID, !isSOTrx(), false, get_TrxName(), true);
//
//		final MOrder linkedOrder = new MOrder(getCtx(), linkedOrderID, get_TrxName());
//		dropShipment.copyAdditionalCols(linkedOrder);
//		dropShipment.setC_Order_ID(linkedOrderID);
//
//		// get invoice id from linked order
//		int invID = linkedOrder.getC_Invoice_ID();
//		if (invID != 0)
//			dropShipment.setC_Invoice_ID(invID);
//
//		dropShipment.setC_BPartner_ID(getDropShip_BPartner_ID());
//		dropShipment.setC_BPartner_Location_ID(getDropShip_Location_ID());
//		dropShipment.setAD_User_ID(getDropShip_User_ID());
//		dropShipment.setIsDropShip(false);
//		dropShipment.setDropShip_BPartner_ID(0);
//		dropShipment.setDropShip_Location_ID(0);
//		dropShipment.setDropShip_User_ID(0);
//		dropShipment.setMovementType(MOVEMENTTYPE_CustomerShipment);
//
//		// References (Should not be required
//		dropShipment.setSalesRep_ID(getSalesRep_ID());
//		dropShipment.save(get_TrxName());
//		dropShipment.saveEx();
//		return dropShipment;
//	}
//
//	private int retrieveDropShipWarehouseId(final int adOrgId)
//	{
//		return MOrgInfo.get(getCtx(), adOrgId, get_TrxName()).getDropShip_Warehouse_ID();
//	}
// @formatter:on

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo()
	{
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete())
		{
			setMovementDate(SystemTime.asTimestamp());
		}
		if (dt.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setTrxName(get_TrxName())
					.setDocumentModel(this)
					.setFailOnError(false)
					.build();
			if (value != null && value != IDocumentNoBuilder.NO_DOCUMENTNO)
			{
				setDocumentNo(value);
			}
		}
	}

	/**
	 * Check Material Policy Sets line ASI
	 */
	private void checkMaterialPolicy(final MInOutLine line)
	{
		MInOutLineMA.deleteInOutLineMA(line.getM_InOutLine_ID(), get_TrxName());

		// Incoming Trx
		final String MovementType = getMovementType();
		final boolean inTrx = MovementType.charAt(1) == '+'; // V+ Vendor Receipt

		boolean needSave = false;
		final MProduct product = line.getProduct();

		// Need to have Location
		if (product != null
				&& line.getM_Locator_ID() == 0)
		{
			// MWarehouse w = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			line.setM_Warehouse_ID(getM_Warehouse_ID());
			line.setM_Locator_ID(inTrx ? BigDecimal.ZERO : line.getMovementQty()); // default Locator
			needSave = true;
		}

		// Attribute Set Instance
		// Create an Attribute Set Instance to any receipt FIFO/LIFO
		if (product != null && line.getM_AttributeSetInstance_ID() == 0)
		{
			// Validate Transaction
			if (getMovementType().compareTo(MInOut.MOVEMENTTYPE_CustomerReturns) == 0
					|| getMovementType().compareTo(MInOut.MOVEMENTTYPE_VendorReceipts) == 0)
			{
				MAttributeSetInstance asi = null;
				// always create asi so fifo/lifo work.
				if (asi == null)
				{
					asi = MAttributeSetInstance.create(getCtx(), product, get_TrxName());
				}
				line.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
				log.debug("New ASI={}", line);
				needSave = true;
			}
			// Create consume the Attribute Set Instance using policy FIFO/LIFO
			else if (getMovementType().compareTo(MInOut.MOVEMENTTYPE_VendorReturns) == 0 || getMovementType().compareTo(MInOut.MOVEMENTTYPE_CustomerShipment) == 0)
			{
				final String MMPolicy = Services.get(IProductBL.class).getMMPolicy(product);
				final Timestamp minGuaranteeDate = getMovementDate();
				final MStorage[] storages = MStorage.getWarehouse(getCtx(),
						getM_Warehouse_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
						minGuaranteeDate, MClient.MMPOLICY_FiFo.equals(MMPolicy), true, line.getM_Locator_ID(), get_TrxName());
				BigDecimal qtyToDeliver = line.getMovementQty();
				for (final MStorage storage : storages)
				{
					if (storage.getQtyOnHand().compareTo(qtyToDeliver) >= 0)
					{
						final MInOutLineMA ma = new MInOutLineMA(line,
								0, // storage.getM_AttributeSetInstance_ID(),
								qtyToDeliver);
						ma.saveEx();
						qtyToDeliver = BigDecimal.ZERO;
					}
					else
					{
						final MInOutLineMA ma = new MInOutLineMA(line,
								0, // storage.getM_AttributeSetInstance_ID(),
								storage.getQtyOnHand());
						ma.saveEx();
						qtyToDeliver = qtyToDeliver.subtract(storage.getQtyOnHand());
						log.debug("{}, QtyToDeliver={}", ma, qtyToDeliver);
					}

					if (qtyToDeliver.signum() == 0)
					{
						break;
					}
				}

				if (qtyToDeliver.signum() != 0)
				{
					// deliver using new asi
					final MAttributeSetInstance asi = MAttributeSetInstance.create(getCtx(), product, get_TrxName());
					final int M_AttributeSetInstance_ID = asi.getM_AttributeSetInstance_ID();
					final MInOutLineMA ma = new MInOutLineMA(line, M_AttributeSetInstance_ID, qtyToDeliver);
					ma.saveEx();
					log.debug("##: {}", ma);
				}
			}  // outgoing Trx
		}  // attributeSetInstance

		if (needSave)
		{
			line.saveEx();
		}
	} // checkMaterialPolicy

	/**************************************************************************
	 * Create Counter Document
	 *
	 * @return InOut
	 */
	private MInOut createCounterDoc()
	{
		// Is this a counter doc ?
		if (getRef_InOut_ID() != 0)
		{
			return null;
		}

		// Org Must be linked to BPartner
		final MOrg org = MOrg.get(getCtx(), getAD_Org_ID());
		final int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(get_TrxName());
		if (counterC_BPartner_ID == 0)
		{
			return null;
		}
		// Business Partner needs to be linked to Org
		final MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(), get_TrxName());
		final int counterAD_Org_ID = bp.getAD_OrgBP_ID_Int();
		if (counterAD_Org_ID == 0)
		{
			return null;
		}

		final MBPartner counterBP = new MBPartner(getCtx(), counterC_BPartner_ID, null);
		final I_AD_OrgInfo counterOrgInfo = MOrgInfo.get(getCtx(), counterAD_Org_ID, null);
		log.debug("Counter BP={}", counterBP);

		// Document Type
		int C_DocTypeTarget_ID = 0;
		final MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(), getC_DocType_ID());
		if (counterDT != null)
		{
			log.debug("Counter docType: {}", counterDT);
			if (!counterDT.isCreateCounter() || !counterDT.isValid())
			{
				return null;
			}
			C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
		}
		else
		// indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(), getC_DocType_ID());
			log.debug("Indirect C_DocTypeTarget_ID={}", C_DocTypeTarget_ID);
			if (C_DocTypeTarget_ID <= 0)
			{
				return null;
			}
		}

		// Deep Copy
		final MInOut counter = copyFrom(this, getMovementDate(), getDateAcct(),
				C_DocTypeTarget_ID, !isSOTrx(), true, get_TrxName(), true);

		//
		counter.setAD_Org_ID(counterAD_Org_ID);
		counter.setM_Warehouse_ID(counterOrgInfo.getM_Warehouse_ID());
		//
		counter.setBPartner(counterBP);

		if (isDropShip())
		{
			counter.setIsDropShip(true);
			counter.setDropShip_BPartner_ID(getDropShip_BPartner_ID());
			counter.setDropShip_Location_ID(getDropShip_Location_ID());
			counter.setDropShip_User_ID(getDropShip_User_ID());
		}

		// metas
		final IPOService poService = Services.get(IPOService.class);
		poService.copyValue(this, counter, I_M_InOut.COLUMNNAME_Incoterm);
		poService.copyValue(this, counter, I_M_InOut.COLUMNNAME_IncotermLocation);
		poService.copyValue(this, counter, I_M_InOut.COLUMNNAME_DescriptionBottom);
		// metas end

		// Refernces (Should not be required
		counter.setSalesRep_ID(getSalesRep_ID());
		counter.save(get_TrxName());

		final String MovementType = counter.getMovementType();
		final boolean inTrx = MovementType.charAt(1) == '+'; // V+ Vendor Receipt

		// Update copied lines
		final MInOutLine[] counterLines = counter.getLines();
		for (final MInOutLine counterLine : counterLines)
		{
			counterLine.setClientOrg(counter);
			counterLine.setM_Warehouse_ID(counter.getM_Warehouse_ID());
			counterLine.setM_Locator_ID(0);
			counterLine.setM_Locator_ID(inTrx ? BigDecimal.ZERO : counterLine.getMovementQty());
			//
			counterLine.save(get_TrxName());
		}

		log.debug("Counter document: {}", counter);

		// Document Action
		if (counterDT != null)
		{
			if (counterDT.getDocAction() != null)
			{
				counter.setDocAction(counterDT.getDocAction());
				counter.processIt(counterDT.getDocAction());
				counter.save(get_TrxName());
			}
		}
		return counter;
	} // createCounterDoc

	/**
	 * Void Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}

		// Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Approved.equals(getDocStatus())
				|| DOCSTATUS_NotApproved.equals(getDocStatus()))
		{
			// Set lines to 0
			final MInOutLine[] lines = getLines();
			for (final MInOutLine line : lines)
			{
				final BigDecimal old = line.getMovementQty();
				if (old.signum() != 0)
				{
					line.setQty(BigDecimal.ZERO);
					line.addDescription("Void (" + old + ")");
					line.save(get_TrxName());
				}
			}
		}
		else
		{
			return reverseCorrectIt();
		}

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	} // voidIt

	/**
	 * Close Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	} // closeIt

	/**
	 * Reverse Correction - same date
	 *
	 * @return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());

		//
		// Delete invoice matching records
		// (no matter is IsSOTrx or not, because we are creating them for both cases)
		Services.get(IInOutBL.class).deleteMatchInvs(this);

		// reverse/unlink Matching
		deleteOrUnLinkMatchPOs();

		// Deep Copy
		final MInOut reversal = copyFrom(this,
				getMovementDate(),
				getDateAcct(),
				getC_DocType_ID(),
				isSOTrx(),
				false,  // counter
				get_TrxName(),
				true // setOrder
		);
		if (reversal == null)
		{
			m_processMsg = "Could not create Ship Reversal";
			return false;
		}
		reversal.setReversal(true);
		// FR1948157
		reversal.setReversal_ID(getM_InOut_ID());
		setReversal_ID(reversal.getM_InOut_ID());

		// Reverse Line Qty
		final MInOutLine[] sLines = getLines();
		final MInOutLine[] rLines = reversal.getLines();
		for (int i = 0; i < rLines.length; i++)
		{
			final MInOutLine rLine = rLines[i];
			final I_M_InOutLine sLine = sLines[i];

			final BigDecimal movementQty = rLine.getMovementQty();

			rLine.setQtyEntered(rLine.getQtyEntered().negate());
			rLine.setMovementQty(movementQty.negate());

			Services.get(IAttributeSetInstanceBL.class).cloneASI(rLine, sLine);
			// rLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
			// Goodwill: store original (voided/reversed) document line
			rLine.setReversalLine_ID(sLine.getM_InOutLine_ID());
			InterfaceWrapperHelper.save(rLine);

			// Link back the ReversalLine_ID
			sLine.setReversalLine_ID(rLine.getM_InOutLine_ID());
			InterfaceWrapperHelper.save(sLine);

			// We need to copy MA
			if (rLine.getM_AttributeSetInstance_ID() <= 0)
			{
				final MInOutLineMA mas[] = MInOutLineMA.get(getCtx(),
						sLine.getM_InOutLine_ID(), get_TrxName());
				for (final MInOutLineMA ma2 : mas)
				{
					final MInOutLineMA ma = new MInOutLineMA(rLine,
							ma2.getM_AttributeSetInstance_ID(),
							ma2.getMovementQty().negate());
					ma.saveEx();
				}
			}
			// De-Activate Asset
			final MAsset asset = MAsset.getFromShipment(getCtx(), sLine.getM_InOutLine_ID(), get_TrxName());
			if (asset != null)
			{
				asset.setIsActive(false);
				asset.addDescription("(" + reversal.getDocumentNo() + " #" + rLine.getLine() + "<-)");
				asset.save();
			}
		}
		reversal.setC_Order_ID(getC_Order_ID());
		// Set M_RMA_ID
		reversal.setM_RMA_ID(getM_RMA_ID());
		reversal.addDescription("{->" + getDocumentNo() + ")");
		//
		if (!reversal.processIt(IDocument.ACTION_Complete)
				|| !reversal.getDocStatus().equals(IDocument.STATUS_Completed))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return false;
		}
		reversal.closeIt();
		reversal.setProcessing(false);
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.saveEx(get_TrxName());
		//
		addDescription("(" + reversal.getDocumentNo() + "<-)");

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		m_processMsg = reversal.getDocumentNo();
		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed); // may come from void
		setDocAction(DOCACTION_None);
		return true;
	} // reverseCorrectionIt

	private void deleteOrUnLinkMatchPOs()
	{
		if (isSOTrx())
		{
			return; // nothing to do
		}

		for (final I_M_MatchPO matchPO : MMatchPO.getInOut(getCtx(), getM_InOut_ID(), get_TrxName()))
		{
			if (matchPO.getC_InvoiceLine_ID() <= 0)
			{
				matchPO.setProcessed(false);
				InterfaceWrapperHelper.delete(matchPO);
			}
			else
			{
				matchPO.setM_InOutLine(null);
				InterfaceWrapperHelper.save(matchPO);
			}
		}
	}

	/**
	 * Reverse Accrual - none
	 *
	 * @return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		return false;
	} // reverseAccrualIt

	/**
	 * Re-activate
	 *
	 * @return false
	 */
	@Override
	public boolean reActivateIt()
	{
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		// Services
		final IMTransactionDAO transactionDAO = Services.get(IMTransactionDAO.class);

		// Std Period open?
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getAD_Org_ID());

		//
		// Make sure it's not a reversal or reversed document.
		// We cannot and we shall not reactivate those
		if (getReversal_ID() > 0)
		{
			throw new AdempiereException("@CannotReActivate@ (@IsReversal@=@Y@)");
		}

		//
		// Delete confirmations
		final MInOutConfirm[] confirmations = getConfirmations(true);
		for (final I_M_InOutConfirm confirmation : confirmations)
		{
			if (confirmation.isProcessed())
			{
				throw new AdempiereException("@CannotReActivate@ (@M_InOutConfirm_ID@ @Processed@)");
			}
			InterfaceWrapperHelper.delete(confirmation);
		}

		//
		// Delete accounting
		Services.get(IFactAcctDAO.class).deleteForDocument(this);
		setPosted(false);

		//
		// For all lines
		for (final I_M_InOutLine inoutLine : getLines(true))
		{
			final I_M_Product product = inoutLine.getM_Product();
			final BigDecimal movementQty = inoutLine.getMovementQty();

			// RMA
			if (inoutLine.getM_RMALine_ID() > 0)
			{
				throw new AdempiereException("@CannotReActivate@ (@M_RMALine_ID@)");
			}

			//
			// Make sure is not invoiced
			if (inoutLine.isInvoiced())
			{
				throw new AdempiereException("@CannotReActivate@ (@IsInvoiced@=@Y@)");
			}

			boolean foundInvoice = false;
			final List<I_C_InvoiceLine> existingInvoiceLines = Services.get(IInvoiceDAO.class).retrieveLines(inoutLine);
			for (final I_C_InvoiceLine existingInvoiceLine : existingInvoiceLines)
			{
				if (!Services.get(IDocumentBL.class).isDocumentStatusOneOf(existingInvoiceLine.getC_Invoice(), IDocument.STATUS_Reversed, IDocument.STATUS_Voided))
				{
					foundInvoice = true;
				}
			}
			if (foundInvoice)
			{
				throw new AdempiereException("@CannotReActivate@ (@IsInvoiced@=@Y@)");
			}

			// TODO: check if there are more places where to look and check if the inout line was already invoiced

			// Delete material allocations
			final MInOutLineMA mas[] = MInOutLineMA.get(getCtx(), inoutLine.getM_InOutLine_ID(), get_TrxName());
			for (final I_M_InOutLineMA ma : mas)
			{
				InterfaceWrapperHelper.delete(ma);
			}

			// Delete M_Transactions
			for (final I_M_Transaction mtrx : transactionDAO.retrieveReferenced(inoutLine))
			{
				InterfaceWrapperHelper.delete(mtrx);
			}

			// Delete M_CostDetails
			for (final I_M_CostDetail costDetail : MCostDetail.retrieveForInOutLine(inoutLine))
			{
				MCostDetail.reverseAndDelete(costDetail);
			}

			// Update Order Line
			final I_C_OrderLine orderLine = inoutLine.getC_OrderLine();
			if (isSOTrx() // task 09266: the order lines for PO-inouts are handeled in the MatchPO business logic
					&& product != null && orderLine != null && orderLine.getC_OrderLine_ID() > 0)
			{
				// task 09358: get rid of this; instead, update qtyReserved at one central place
				// orderLine.setQtyReserved(orderLine.getQtyReserved().add(movementQty));
				orderLine.setQtyDelivered(orderLine.getQtyDelivered().subtract(movementQty));
				// NOTE: we cannot just set the DateDelivered to null because maybe this is not the only shipment/receipt for that orderline
				// orderLine.setDateDelivered(null);
				InterfaceWrapperHelper.save(orderLine);
			}

			// task 09266: delete MatchInvs also on reactivate
			Services.get(IInOutBL.class).deleteMatchInvs(this);

			// task 09266: unlink or delete MatchPOs also on reactivate
			deleteOrUnLinkMatchPOs();

			//
			// Save the inout line
			inoutLine.setProcessed(false);
			InterfaceWrapperHelper.save(inoutLine);
		}

		//
		// Update status
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		setIsApproved(false);

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		return true;
	} // reActivateIt

	/*************************************************************************
	 * Get Summary
	 *
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		// : Total Lines = 123.00 (#1)
		sb.append(":")
				// .append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
				.append(" (#").append(getLines(false).length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
		return sb.toString();
	} // getSummary

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	} // getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getSalesRep_ID();
	} // getDoc_User_ID

	/**
	 * Get Document Approval Amount
	 *
	 * @return amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return BigDecimal.ZERO;
	} // getApprovalAmt

	/**
	 * Get C_Currency_ID
	 *
	 * @return Accounting Currency
	 */
	@Override
	public int getC_Currency_ID()
	{
		return Env.getContextAsInt(getCtx(), "$C_Currency_ID");
	} // getC_Currency_ID

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		final String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
				|| DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	} // isComplete

} // MInOut
