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

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.bpartner.service.IBPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.exceptions.BPartnerNoBillToAddressException;
import org.adempiere.exceptions.BPartnerNoShipToAddressException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.service.IOrderDAO;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.currency.ICurrencyBL;
import de.metas.document.documentNo.IDocumentNoBL;
import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.engine.IDocActionBL;
import de.metas.prepayorder.service.IPrepayOrderAllocationBL;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.IStorageBL;
import de.metas.tax.api.ITaxBL;

/**
 * Order Model.
 * Please do not set DocStatus and C_DocType_ID directly.
 * They are set in the process() method.
 * Use DocAction and C_DocTypeTarget_ID instead.
 *
 * @author Jorg Janke
 *
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 * @version $Id: MOrder.java,v 1.5 2006/10/06 00:42:24 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 2419978 ] Voiding PO, requisition don't set on NULL
 *         <li>BF [ 2892578 ] Order should autoset only active price lists
 *         https://sourceforge.net/tracker/?func=detail&aid=2892578&group_id=176962&atid=879335
 * @author Michael Judd, www.akunagroup.com
 *         <li>BF [ 2804888 ] Incorrect reservation of products with attributes
 */
public class MOrder extends X_C_Order implements DocAction
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1575104995897726572L;

	private final IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);

	/**
	 * Create new Order by copying
	 *
	 * @param from order
	 * @param org the org of the new order
	 * @param dateDoc date of the document date
	 * @param C_DocTypeTarget_ID target document type
	 * @param isSOTrx sales order
	 * @param counter create counter links
	 * @param copyASI copy line attributes Attribute Set Instance, Resaouce Assignment
	 * @param trxName trx
	 * @return Order
	 */
	public static MOrder copyFrom(MOrder from,
			I_AD_Org org,
			Timestamp dateDoc,
			int C_DocTypeTarget_ID,
			boolean isSOTrx,
			boolean counter,
			boolean copyASI,
			String trxName)
	{
		final MOrder to = new MOrder(from.getCtx(), 0, trxName);
		to.set_TrxName(trxName);
		PO.copyValues(from, to, from.getAD_Client_ID(), from.getAD_Org_ID());
		to.set_ValueNoCheck("C_Order_ID", I_ZERO);
		to.set_ValueNoCheck("DocumentNo", null);

		to.setAD_Org(org); // 09700

		//
		to.setDocStatus(DOCSTATUS_Drafted);		// Draft
		to.setDocAction(DOCACTION_Complete);
		//
		to.setC_DocType_ID(0);
		to.setC_DocTypeTarget_ID(C_DocTypeTarget_ID);
		to.setIsSOTrx(isSOTrx);
		//
		// the new order needs to figure out the pricing and also the warehouse (task 9700) by itself
		InterfaceWrapperHelper.create(to, de.metas.adempiere.model.I_C_Order.class).setM_PricingSystem_ID(0);
		to.setM_PriceList_ID(0);
		to.setM_Warehouse(null);

		to.setIsSelected(false);
		to.setDateOrdered(dateDoc);
		to.setDateAcct(dateDoc);
		to.setDatePromised(dateDoc);	// assumption
		to.setDatePrinted(null);
		to.setIsPrinted(false);
		//
		to.setIsApproved(false);
		to.setIsCreditApproved(false);
		to.setC_Payment_ID(0);
		to.setC_CashLine_ID(0);
		// Amounts are updated when adding lines
		to.setGrandTotal(Env.ZERO);
		to.setTotalLines(Env.ZERO);
		//
		to.setIsDelivered(false);
		to.setIsInvoiced(false);
		to.setIsSelfService(false);
		to.setIsTransferred(false);
		to.setPosted(false);
		to.setProcessed(false);
		if (counter)
		{
			to.setRef_Order_ID(from.getC_Order_ID());
		}
		else
		{
			to.setRef_Order_ID(0);
		}

		InterfaceWrapperHelper.save(to);

		if (counter)
		{
			from.setRef_Order_ID(to.getC_Order_ID());
		}
		if (to.copyLinesFrom(from, counter, copyASI) == 0)
		{
			throw new IllegalStateException("Could not create Order Lines");
		}

		// don't copy linked PO/SO
		to.setLink_Order_ID(0);

		return to;
	}	// copyFrom

	/**************************************************************************
	 * Default Constructor
	 *
	 * @param ctx context
	 * @param C_Order_ID order to load, (0 create new order)
	 * @param trxName trx name
	 */
	public MOrder(Properties ctx, int C_Order_ID, String trxName)
	{
		super(ctx, C_Order_ID, trxName);
		// New
		if (C_Order_ID == 0)
		{
			setDocStatus(DOCSTATUS_Drafted);
			setDocAction(DOCACTION_Prepare);
			//
			setDeliveryRule(DELIVERYRULE_Availability);
			setFreightCostRule(FREIGHTCOSTRULE_FreightIncluded);
			// metas: we *never* use InvoiceRule 'Immediate', so don't use it as default.
			setInvoiceRule(INVOICERULE_AfterDelivery);
			setPaymentRule(PAYMENTRULE_OnCredit);
			setPriorityRule(PRIORITYRULE_Medium);
			setDeliveryViaRule(DELIVERYVIARULE_Pickup);
			//
			setIsDiscountPrinted(false);
			setIsSelected(false);
			setIsTaxIncluded(false);
			setIsSOTrx(true);
			setIsDropShip(false);
			setSendEMail(false);
			//
			setIsApproved(false);
			setIsPrinted(false);
			setIsCreditApproved(false);
			setIsDelivered(false);
			setIsInvoiced(false);
			setIsTransferred(false);
			setIsSelfService(false);
			//
			super.setProcessed(false);
			setProcessing(false);
			setPosted(false);

			setDateAcct(new Timestamp(System.currentTimeMillis()));
			setDatePromised(SystemTime.asDayTimestamp()); // task 06269 (see KurzBeschreibung)
			setDateOrdered(new Timestamp(System.currentTimeMillis()));

			setFreightAmt(Env.ZERO);
			setChargeAmt(Env.ZERO);
			setTotalLines(Env.ZERO);
			setGrandTotal(Env.ZERO);
		}
	}	// MOrder

	/**************************************************************************
	 * Project Constructor
	 *
	 * @param project Project to create Order from
	 * @param IsSOTrx sales order
	 * @param DocSubType if SO DocType Target (default DocSubType_OnCredit)
	 */
	public MOrder(MProject project, boolean IsSOTrx, String DocSubType)
	{
		this(project.getCtx(), 0, project.get_TrxName());
		setAD_Client_ID(project.getAD_Client_ID());
		setAD_Org_ID(project.getAD_Org_ID());
		setC_Campaign_ID(project.getC_Campaign_ID());
		setSalesRep_ID(project.getSalesRep_ID());
		//
		setC_Project_ID(project.getC_Project_ID());
		setDescription(project.getName());
		Timestamp ts = project.getDateContract();
		if (ts != null)
			setDateOrdered(ts);
		ts = project.getDateFinish();
		if (ts != null)
			setDatePromised(ts);
		//
		setC_BPartner_ID(project.getC_BPartner_ID());
		setC_BPartner_Location_ID(project.getC_BPartner_Location_ID());
		setAD_User_ID(project.getAD_User_ID());
		//
		setM_Warehouse_ID(project.getM_Warehouse_ID());
		setM_PriceList_ID(project.getM_PriceList_ID());
		setC_PaymentTerm_ID(project.getC_PaymentTerm_ID());
		//
		setIsSOTrx(IsSOTrx);
		if (IsSOTrx)
		{
			if (DocSubType == null || DocSubType.length() == 0)
				setC_DocTypeTarget_ID(DocSubType_OnCredit);
			else
				setC_DocTypeTarget_ID(DocSubType);
		}
		else
			setC_DocTypeTarget_ID();
	}	// MOrder

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set record
	 * @param trxName transaction
	 */
	public MOrder(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MOrder

	/** Order Lines */
	private MOrderLine[] m_lines = null;
	/** Tax Lines */
	private MOrderTax[] m_taxes = null;
	/** Force Creation of order */
	private boolean m_forceCreation = false;

	/**
	 * Overwrite Client/Org if required
	 *
	 * @param AD_Client_ID client
	 * @param AD_Org_ID org
	 */
	@Override
	public void setClientOrg(int AD_Client_ID, int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	// setClientOrg

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	// addDescription

	/**
	 * Set Business Partner (Ship+Bill)
	 *
	 * @param C_BPartner_ID bpartner
	 */
	@Override
	public void setC_BPartner_ID(int C_BPartner_ID)
	{
		super.setC_BPartner_ID(C_BPartner_ID);
		super.setBill_BPartner_ID(C_BPartner_ID);
	}	// setC_BPartner_ID

	/**
	 * Set Business Partner Location (Ship+Bill)
	 *
	 * @param C_BPartner_Location_ID bp location
	 */
	@Override
	public void setC_BPartner_Location_ID(int C_BPartner_Location_ID)
	{
		super.setC_BPartner_Location_ID(C_BPartner_Location_ID);
		super.setBill_Location_ID(C_BPartner_Location_ID);
	}	// setC_BPartner_Location_ID

	/**
	 * Set Business Partner Contact (Ship+Bill)
	 *
	 * @param AD_User_ID user
	 */
	@Override
	public void setAD_User_ID(int AD_User_ID)
	{
		super.setAD_User_ID(AD_User_ID);
		super.setBill_User_ID(AD_User_ID);
	}	// setAD_User_ID

	/**
	 * Set Ship Business Partner
	 *
	 * @param C_BPartner_ID bpartner
	 */
	public void setShip_BPartner_ID(int C_BPartner_ID)
	{
		super.setC_BPartner_ID(C_BPartner_ID);
	}	// setShip_BPartner_ID

	/**
	 * Set Ship Business Partner Location
	 *
	 * @param C_BPartner_Location_ID bp location
	 */
	public void setShip_Location_ID(int C_BPartner_Location_ID)
	{
		super.setC_BPartner_Location_ID(C_BPartner_Location_ID);
	}	// setShip_Location_ID

	/**
	 * Set Ship Business Partner Contact
	 *
	 * @param AD_User_ID user
	 */
	public void setShip_User_ID(int AD_User_ID)
	{
		super.setAD_User_ID(AD_User_ID);
	}	// setShip_User_ID

	/**
	 * Set Warehouse
	 *
	 * @param M_Warehouse_ID warehouse
	 */
	@Override
	public void setM_Warehouse_ID(int M_Warehouse_ID)
	{
		super.setM_Warehouse_ID(M_Warehouse_ID);
	}	// setM_Warehouse_ID

	/**
	 * Set Drop Ship
	 *
	 * @param IsDropShip drop ship
	 */
	@Override
	public void setIsDropShip(boolean IsDropShip)
	{
		super.setIsDropShip(IsDropShip);
	}	// setIsDropShip

	/*************************************************************************/

	/** Sales Order Sub Type - SO */
	public static final String DocSubType_Standard = "SO";
	/** Sales Order Sub Type - OB */
	public static final String DocSubType_Quotation = "OB";
	/** Sales Order Sub Type - ON */
	public static final String DocSubType_Proposal = "ON";
	/** Sales Order Sub Type - PR */
	public static final String DocSubType_Prepay = "PR";
	/** Sales Order Sub Type - WR */
	public static final String DocSubType_POS = "WR";
	/** Sales Order Sub Type - WP */
	public static final String DocSubType_Warehouse = "WP";
	/** Sales Order Sub Type - WI */
	public static final String DocSubType_OnCredit = "WI";
	/** Sales Order Sub Type - RM */
	public static final String DocSubType_RMA = "RM";

	/**
	 * Set Target Sales Document Type
	 *
	 * @param DocSubType_x SO sub type - see DocSubType_*
	 */
	public void setC_DocTypeTarget_ID(String DocSubType_x)
	{
		String sql = "SELECT C_DocType_ID FROM C_DocType "
				+ "WHERE AD_Client_ID=? AND AD_Org_ID IN (0," + getAD_Org_ID()
				+ ") AND DocSubType=? "
				+ "ORDER BY AD_Org_ID DESC, IsDefault DESC";
		int C_DocType_ID = DB.getSQLValue(null, sql, getAD_Client_ID(), DocSubType_x);
		if (C_DocType_ID <= 0)
			log.error("Not found for AD_Client_ID=" + getAD_Client_ID() + ", SubType=" + DocSubType_x);
		else
		{
			log.debug("(SO) - " + DocSubType_x);
			setC_DocTypeTarget_ID(C_DocType_ID);
			setIsSOTrx(true);
		}
	}	// setC_DocTypeTarget_ID

	/**
	 * Set Target Document Type.
	 * Standard Order or PO
	 *
	 * Please use {@link de.metas.adempiere.service.IOrderBL.setDocTypeTargetId(I_C_Order)}.
	 *
	 * Please, when changing this one, also change the {@link de.metas.adempiere.service.IOrderBL.setDocTypeTargetId(I_C_Order)}.
	 */
	@Deprecated
	public void setC_DocTypeTarget_ID()
	{
		if (isSOTrx())  		// SO = Std Order
		{
			setC_DocTypeTarget_ID(DocSubType_Standard);
			return;
		}
		// PO
		String sql = "SELECT C_DocType_ID FROM C_DocType "
				+ "WHERE AD_Client_ID=? AND AD_Org_ID IN (0," + getAD_Org_ID()
				+ ") AND DocBaseType='POO' "
				+ "ORDER BY AD_Org_ID DESC, IsDefault DESC, DocSubType NULLS FIRST";
		int C_DocType_ID = DB.getSQLValue(null, sql, getAD_Client_ID());
		if (C_DocType_ID <= 0)
			log.error("No POO found for AD_Client_ID=" + getAD_Client_ID());
		else
		{
			log.debug("(PO) - " + C_DocType_ID);
			setC_DocTypeTarget_ID(C_DocType_ID);
		}
	}	// setC_DocTypeTarget_ID

	/**
	 * Set Business Partner Defaults & Details.
	 * SOTrx should be set.
	 *
	 * @param bp business partner
	 */
	public void setBPartner(I_C_BPartner bp)
	{
		// FIXME: keep in sync / merge with de.metas.adempiere.service.impl.OrderBL.setBPartner(I_C_Order, I_C_BPartner)

		if (bp == null)
			return;

		setC_BPartner_ID(bp.getC_BPartner_ID());
		// Defaults Payment Term
		int ii = 0;
		if (isSOTrx())
			ii = bp.getC_PaymentTerm_ID();
		else
			ii = bp.getPO_PaymentTerm_ID();
		if (ii != 0)
			setC_PaymentTerm_ID(ii);
		// Default Price List
		if (isSOTrx())
			ii = bp.getM_PriceList_ID();
		else
			ii = bp.getPO_PriceList_ID();
		if (ii != 0)
			setM_PriceList_ID(ii);
		// Default Delivery/Via Rule
		String ss = bp.getDeliveryRule();
		if (ss != null)
			setDeliveryRule(ss);
		ss = bp.getDeliveryViaRule();
		if (ss != null)
			setDeliveryViaRule(ss);
		// Default Invoice/Payment Rule
		ss = bp.getInvoiceRule();
		if (ss != null)
			setInvoiceRule(ss);
		ss = bp.getPaymentRule();
		if (ss != null)
			setPaymentRule(ss);
		// Sales Rep
		final int salesRepId = bp.getSalesRep_ID();
		if (salesRepId > 0)
			setSalesRep_ID(salesRepId);

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

		// Set Locations
		final List<I_C_BPartner_Location> locs = InterfaceWrapperHelper.createList(
				bPartnerDAO.retrieveBPartnerLocations(bp),
				I_C_BPartner_Location.class);

		for (final I_C_BPartner_Location loc : locs)
		{
			if (loc.isShipTo())
			{
				super.setC_BPartner_Location_ID(loc.getC_BPartner_Location_ID());
			}
			if (loc.isBillTo())
			{
				setBill_Location_ID(loc.getC_BPartner_Location_ID());
			}
		}
		// set to first
		if (getC_BPartner_Location_ID() == 0 && !locs.isEmpty())
		{
			super.setC_BPartner_Location_ID(locs.get(0).getC_BPartner_Location_ID());
		}
		if (getBill_Location_ID() == 0 && !locs.isEmpty())
		{
			setBill_Location_ID(locs.get(0).getC_BPartner_Location_ID());
		}
		if (getC_BPartner_Location_ID() == 0)
		{
			throw new BPartnerNoShipToAddressException(bp);
		}

		if (getBill_Location_ID() == 0)
		{
			throw new BPartnerNoBillToAddressException(bp);
		}

		// Set Contact
		final List<I_AD_User> contacts = InterfaceWrapperHelper.createList(bPartnerDAO.retrieveContacts(bp), I_AD_User.class);
		if (!contacts.isEmpty())
		{
			setAD_User_ID(contacts.get(0).getAD_User_ID());
		}
	}	// setBPartner

	/**
	 * Copy Lines From other Order
	 *
	 * @param otherOrder order
	 * @param counter set counter info
	 * @param copyASI copy line attributes Attribute Set Instance, Resaouce Assignment
	 * @return number of lines copied
	 */
	public int copyLinesFrom(
			final MOrder otherOrder,
			final boolean counter,
			final boolean copyASI)
	{
		if (isProcessed() || isPosted() || otherOrder == null)
		{
			return 0;
		}
		final MOrderLine[] fromLines = otherOrder.getLines(false, null);
		int count = 0;
		for (int i = 0; i < fromLines.length; i++)
		{
			count = count + copyLineFrom(counter, copyASI, fromLines[i]);
		}
		if (fromLines.length != count)
		{
			log.error("Line difference - From=" + fromLines.length + " <> Saved=" + count);
		}
		return count;
	} // copyLinesFrom

	/**
	 * Creates a new order line for this order, using the given <code>fromLine</code> as a template.
	 *
	 * @param counter if <code>true</code>, then
	 *            <ul>
	 *            <li>the new other line's <code>Ref_OrderLine_ID</code> is set to <code>fromLine</code>'s ID
	 *            <li>if <code>fromLine</code> has a product with <code>AD_Org_ID!=0</code> and of fromLine's <code>AD_Org_ID</code> is different from this order's <code>AD_Org_ID</code>, then
	 *            {@link IProductDAO#retrieveMappedProductOrNull(I_M_Product, I_AD_Org)} is called, to get the other org's pendant product.
	 *            </ul>
	 * @param copyASI
	 * @param fromLine
	 * @return <code>1</code>
	 */
	public int copyLineFrom(
			final boolean counter,
			final boolean copyASI,
			final MOrderLine fromLine)
	{
		// only copy the line if it should be copied in a counter document
		if (counter)
		{
			final boolean copyCounter = Services.get(IOrderLineBL.class).isAllowedCounterLineCopy(fromLine);
			
			if (!copyCounter)
			{
				return 0;
			}
		}

		final MOrderLine line = new MOrderLine(this);
		PO.copyValues(fromLine, line, getAD_Client_ID(), getAD_Org_ID()); // note: this copies *all* columns, also those with IsCalculated='Y'
		line.setC_Order_ID(getC_Order_ID());
		line.setOrder(this);
		line.set_ValueNoCheck("C_OrderLine_ID", I_ZERO);	// new
		// References
		if (!copyASI)
		{
			line.setM_AttributeSetInstance_ID(0);
			line.setS_ResourceAssignment_ID(0);
		}
		else
		{
			final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
			attributeSetInstanceBL.cloneASI(line, fromLine);
		}
		if (counter)
		{
			Services.get(IOrderLineBL.class).copyOrderLineCounter(line, fromLine);

		}
		else
		{
			line.setRef_OrderLine_ID(0);
		}
		//
		line.setQtyDelivered(Env.ZERO);
		line.setQtyInvoiced(Env.ZERO);
		// task 09358: get rid of this; instead, update qtyReserved at one central place
		// line.setQtyReserved(Env.ZERO);
		line.setDateDelivered(null);
		line.setDateInvoiced(null);
		// don't copy linked lines
		line.setLink_OrderLine_ID(0);
		// Tax
		// MOrder otherOrder = fromLine.getC_Order ();
		// if (getC_BPartner_ID() != otherOrder.getC_BPartner_ID())
		line.setTax();		// recalculate
		//
		//
		line.setProcessed(false);
		InterfaceWrapperHelper.save(line);

		// Cross Link
		if (counter)
		{
			fromLine.setRef_OrderLine_ID(line.getC_OrderLine_ID());
			InterfaceWrapperHelper.save(fromLine);
		}

		return 1;
	}	// copyLinesFrom

	/**************************************************************************
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("C_Order[ID=").append(get_ID())
				.append("-DocumentNo=").append(getDocumentNo())
				.append(",IsSOTrx=").append(isSOTrx())
				.append(",C_DocType_ID=").append(getC_DocType_ID())
				.append(",GrandTotal=").append(getGrandTotal())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		final StringBuilder documentInfo = new StringBuilder();
		
		//
		// DocType
		I_C_DocType docType = getC_DocType();
		if(docType == null)
		{
			docType = getC_DocTypeTarget();
		}
		if(docType != null)
		{
			documentInfo.append(docType.getName());
		}
		
		//
		// DocumentNo
		if(documentInfo.length() > 0)
		{
			documentInfo.append(" ");
		}
		documentInfo.append(getDocumentNo());
		
		return documentInfo.toString();
	}	// getDocumentInfo

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
			File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (Exception e)
		{
			log.error("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	// getPDF

	/**
	 * Create PDF file
	 *
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(File file)
	{
		ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.ORDER, getC_Order_ID(), get_TrxName());
		if (re == null)
			return null;
		return re.getPDF(file);
	}	// createPDF

	/**************************************************************************
	 * Get <b>active</b> Lines of Order
	 *
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return lines
	 */
	public MOrderLine[] getLines(String whereClause, String orderClause)
	{
		// red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		final StringBuffer whereClauseFinal = new StringBuffer("(" + MOrderLine.COLUMNNAME_C_Order_ID + "=? AND " + MOrderLine.COLUMNNAME_IsActive + "='Y' )");
		if (!Check.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MOrderLine.COLUMNNAME_Line;
		//
		List<MOrderLine> list = new Query(getCtx(), MOrderLine.Table_Name, whereClauseFinal.toString(), get_TrxName())
				.setParameters(new Object[] { get_ID() })
				.setOrderBy(orderClause)
				.list();
		for (MOrderLine ol : list)
		{
			ol.setHeaderInfo(this);
		}
		//
		return list.toArray(new MOrderLine[list.size()]);
	}	// getLines

	/**
	 * Get <b>active</b> Lines of Order
	 *
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MOrderLine[] getLines(boolean requery, String orderBy)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;
		else
			orderClause += "Line";
		m_lines = getLines(null, orderClause);
		return m_lines;
	}	// getLines

	/**
	 * Get Lines of Order.
	 * (used by web store)
	 *
	 * @return lines
	 */
	public MOrderLine[] getLines()
	{
		return getLines(false, null);
	}	// getLines

	/**
	 * Renumber Lines
	 *
	 * @param step start and step
	 */
	public void renumberLines(int step)
	{
		int number = step;
		MOrderLine[] lines = getLines(true, null);	// Line is default
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			line.setLine(number);
			line.save(get_TrxName());
			number += step;
		}
		m_lines = null;
	}	// renumberLines

	/**
	 * Does the Order Line belong to this Order
	 *
	 * @param C_OrderLine_ID line
	 * @return true if part of the order
	 */
	public boolean isOrderLine(int C_OrderLine_ID)
	{
		if (m_lines == null)
			getLines();
		for (int i = 0; i < m_lines.length; i++)
			if (m_lines[i].getC_OrderLine_ID() == C_OrderLine_ID)
				return true;
		return false;
	}	// isOrderLine

	/**
	 * Get Taxes of Order
	 *
	 * @param requery requery
	 * @return array of taxes
	 */
	public MOrderTax[] getTaxes(boolean requery)
	{
		if (m_taxes != null && !requery)
			return m_taxes;
		//
		List<MOrderTax> list = new Query(getCtx(), MOrderTax.Table_Name, "C_Order_ID=?", get_TrxName())
				.setParameters(new Object[] { get_ID() })
				.list();
		m_taxes = list.toArray(new MOrderTax[list.size()]);
		return m_taxes;
	}	// getTaxes

	/**
	 * Get Invoices of Order
	 *
	 * @return invoices
	 */
	public MInvoice[] getInvoices()
	{
		final String whereClause = "EXISTS (SELECT 1 FROM C_InvoiceLine il, C_OrderLine ol"
				+ " WHERE il.C_Invoice_ID=C_Invoice.C_Invoice_ID"
				+ " AND il.C_OrderLine_ID=ol.C_OrderLine_ID"
				+ " AND ol.C_Order_ID=?)";
		List<MInvoice> list = new Query(getCtx(), MInvoice.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { get_ID() })
				.setOrderBy("C_Invoice_ID DESC")
				.list();
		return list.toArray(new MInvoice[list.size()]);
	}	// getInvoices

	/**
	 * Get latest Invoice of Order
	 *
	 * @return invoice id or 0
	 */
	public int getC_Invoice_ID()
	{
		String sql = "SELECT C_Invoice_ID FROM C_Invoice "
				+ "WHERE C_Order_ID=? AND DocStatus IN ('CO','CL') "
				+ "ORDER BY C_Invoice_ID DESC";
		int C_Invoice_ID = DB.getSQLValue(get_TrxName(), sql, get_ID());
		return C_Invoice_ID;
	}	// getC_Invoice_ID

	/**
	 * Get Shipments of Order
	 *
	 * @return shipments
	 * @deprecated Please use
	 */
	@Deprecated
	public MInOut[] getShipments()
	{
		// 05768: moved to
		final List<I_M_InOut> inOuts = Services.get(IOrderDAO.class).retrieveInOutsForMatchingOrderLines(InterfaceWrapperHelper.create(this, I_C_Order.class));
		return LegacyAdapters.convertToPOArray(inOuts, MInOut.class);
	}	// getShipments

	/**
	 * Get Currency Precision
	 *
	 * @return precision
	 *
	 * @deprecated Please use {@link IOrderBL#getPrecision(I_C_Order)}.
	 */
	@Deprecated
	public int getPrecision()
	{
		return Services.get(IOrderBL.class).getPrecision(this);
	}	// getPrecision

	/**
	 * Get Document Status
	 *
	 * @return Document Status Clear Text
	 */
	public String getDocStatusName()
	{
		return MRefList.getListName(getCtx(), 131, getDocStatus());
	}	// getDocStatusName

	/**
	 * Set DocAction
	 *
	 * @param DocAction doc action
	 */
	@Override
	public void setDocAction(String DocAction)
	{
		setDocAction(DocAction, false);
	}	// setDocAction

	/**
	 * Set DocAction
	 *
	 * @param DocAction doc action
	 * @param forceCreation force creation
	 */
	public void setDocAction(String DocAction, boolean forceCreation)
	{
		super.setDocAction(DocAction);
		m_forceCreation = forceCreation;
	}	// setDocAction

	/**
	 * Set Processed.
	 * Propagate to Lines/Taxes
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
			return;
		String set = "SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE C_Order_ID=" + getC_Order_ID();
		int noLine = DB.executeUpdateEx("UPDATE C_OrderLine " + set, get_TrxName());
		int noTax = DB.executeUpdateEx("UPDATE C_OrderTax " + set, get_TrxName());
		m_lines = null;
		m_taxes = null;
		log.debug("setProcessed - " + processed + " - Lines=" + noLine + ", Tax=" + noTax);
	}	// setProcessed

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord new
	 * @return save
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// Client/Org Check
		if (getAD_Org_ID() == 0)
		{
			int context_AD_Org_ID = Env.getAD_Org_ID(getCtx());
			if (context_AD_Org_ID != 0)
			{
				setAD_Org_ID(context_AD_Org_ID);
				log.warn("Changed Org to Context=" + context_AD_Org_ID);
			}
		}
		if (getAD_Client_ID() == 0)
		{
			m_processMsg = "AD_Client_ID = 0";
			return false;
		}

		// New Record Doc Type - make sure DocType set to 0
		if (newRecord && getC_DocType_ID() == 0)
			setC_DocType_ID(0);

		// Default Warehouse
		if (getM_Warehouse_ID() <= 0)
		{
			setM_Warehouse_ID(warehouseAdvisor.getDefaulWarehouseId(getCtx()));
		}
		// Warehouse Org
		if (newRecord
				|| is_ValueChanged("AD_Org_ID") || is_ValueChanged("M_Warehouse_ID"))
		{
			// metas: allow different warehouse-org
			// MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			// if (wh.getAD_Org_ID() != getAD_Org_ID())
			// log.saveWarning("WarehouseOrgConflict", "");
			//
		}
		// Reservations in Warehouse
		if (!newRecord && is_ValueChanged("M_Warehouse_ID"))
		{
			MOrderLine[] lines = getLines(false, null);
			for (int i = 0; i < lines.length; i++)
			{
				if (!lines[i].canChangeWarehouse(true))
					return false;
			}
		}

		// No Partner Info - set Template
		if (getC_BPartner_ID() == 0)
			setBPartner(MBPartner.getTemplate(getCtx(), getAD_Client_ID()));
		if (getC_BPartner_Location_ID() == 0)
			setBPartner(new MBPartner(getCtx(), getC_BPartner_ID(), null));
		// No Bill - get from Ship
		if (getBill_BPartner_ID() == 0)
		{
			setBill_BPartner_ID(getC_BPartner_ID());
			setBill_Location_ID(getC_BPartner_Location_ID());
		}
		if (getBill_Location_ID() == 0)
			setBill_Location_ID(getC_BPartner_Location_ID());

		//
		// Default Price List
		// metas: bpartner's pricing system (instead of price list)
		Services.get(IOrderBL.class).setM_PricingSystem_ID(this, false); // overridePricingSystem=false

		//
		// Default Currency
		if (getC_Currency_ID() <= 0)
		{
			final I_M_PriceList priceList = getM_PriceList();
			final int currencyId = priceList == null ? -1 : priceList.getC_Currency_ID();
			if (currencyId > 0)
			{
				setC_Currency_ID(currencyId);
			}
			else
			{
				setC_Currency_ID(Env.getContextAsInt(getCtx(), "#C_Currency_ID"));
			}
		}

		// Default Sales Rep
		// NOTE: we shall not set the SalesRep from context if is not set.
		// This is not a mandatory field, so leave it like it is.

		// Default Document Type
		if (getC_DocTypeTarget_ID() <= 0)
			setC_DocTypeTarget_ID(DocSubType_Standard);

		// Default Payment Term
		if (getC_PaymentTerm_ID() == 0)
		{
			int ii = Env.getContextAsInt(getCtx(), "#C_PaymentTerm_ID");
			if (ii != 0)
				setC_PaymentTerm_ID(ii);
			else
			{
				String sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE AD_Client_ID=? AND IsDefault='Y'";
				ii = DB.getSQLValue(null, sql, getAD_Client_ID());
				if (ii != 0)
					setC_PaymentTerm_ID(ii);
			}
		}

		return true;
	}	// beforeSave

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success success
	 * @return true if can be saved
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success || newRecord)
			return success;

		// Propagate Description changes
		if (is_ValueChanged("Description") || is_ValueChanged("POReference"))
		{
			String sql = DB.convertSqlToNative("UPDATE C_Invoice i"
					+ " SET (Description,POReference)="
					+ "(SELECT Description,POReference "
					+ "FROM C_Order o WHERE i.C_Order_ID=o.C_Order_ID) "
					+ "WHERE DocStatus NOT IN ('RE','CL') AND C_Order_ID=" + getC_Order_ID());
			int no = DB.executeUpdateEx(sql, get_TrxName());
			log.debug("Description -> #" + no);
		}

		// Propagate Changes of Payment Info to existing (not reversed/closed) invoices
		if (is_ValueChanged("PaymentRule") || is_ValueChanged("C_PaymentTerm_ID")
				|| is_ValueChanged("DateAcct") || is_ValueChanged("C_Payment_ID")
				|| is_ValueChanged("C_CashLine_ID"))
		{
			String sql = DB.convertSqlToNative("UPDATE C_Invoice i "
					+ "SET (PaymentRule,C_PaymentTerm_ID,DateAcct,C_Payment_ID,C_CashLine_ID)="
					+ "(SELECT PaymentRule,C_PaymentTerm_ID,DateAcct,C_Payment_ID,C_CashLine_ID "
					+ "FROM C_Order o WHERE i.C_Order_ID=o.C_Order_ID)"
					+ "WHERE DocStatus NOT IN ('RE','CL') AND C_Order_ID=" + getC_Order_ID());
			// Don't touch Closed/Reversed entries
			int no = DB.executeUpdate(sql, get_TrxName());
			log.debug("Payment -> #" + no);
		}

		// Sync Lines
		if (is_ValueChanged("AD_Org_ID")
				// metas: tsa: 01767: don't sync bp and bplocation
				/*
				 * || is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_ID)
				 * || is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_Location_ID)
				 */
				|| is_ValueChanged(MOrder.COLUMNNAME_DateOrdered)
				|| is_ValueChanged(MOrder.COLUMNNAME_DatePromised)
				|| is_ValueChanged(MOrder.COLUMNNAME_M_Warehouse_ID)
				|| is_ValueChanged(MOrder.COLUMNNAME_M_Shipper_ID)
				|| is_ValueChanged(MOrder.COLUMNNAME_C_Currency_ID))
		{
			MOrderLine[] lines = getLines();
			for (MOrderLine line : lines)
			{
				if (is_ValueChanged("AD_Org_ID"))
					line.setAD_Org_ID(getAD_Org_ID());
				// metas: tsa: 01767: don't sync bp and bplocation
				/*
				 * if (is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_ID))
				 * line.setC_BPartner_ID(getC_BPartner_ID());
				 * if (is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_Location_ID))
				 * line.setC_BPartner_Location_ID(getC_BPartner_Location_ID());
				 */
				if (is_ValueChanged(MOrder.COLUMNNAME_DateOrdered))
					line.setDateOrdered(getDateOrdered());
				if (is_ValueChanged(MOrder.COLUMNNAME_DatePromised))
					line.setDatePromised(getDatePromised());
				if (is_ValueChanged(MOrder.COLUMNNAME_M_Warehouse_ID))
					line.setM_Warehouse_ID(getM_Warehouse_ID());
				if (is_ValueChanged(MOrder.COLUMNNAME_M_Shipper_ID))
					line.setM_Shipper_ID(getM_Shipper_ID());
				if (is_ValueChanged(MOrder.COLUMNNAME_C_Currency_ID))
					line.setC_Currency_ID(getC_Currency_ID());
				line.saveEx();
			}
		}
		//
		return true;
	}	// afterSave

	/**
	 * Before Delete
	 *
	 * @return true of it can be deleted
	 */
	@Override
	protected boolean beforeDelete()
	{
		if (isProcessed())
			return false;

		for (MOrderLine line : getLines())
		{
			line.deleteEx(true);
		}
		return true;
	}	// beforeDelete

	/**************************************************************************
	 * Process document
	 *
	 * @param processAction document action
	 * @return true if performed
	 */
	@Override
	public boolean processIt(String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocActionBL.class).processIt(this, processAction); // task 09824
	}	// processIt

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
		log.debug("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}	// unlockIt

	/**
	 * Invalidate Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		log.debug(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	// invalidateIt

	/**************************************************************************
	 * Prepare Document
	 *
	 * @return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		log.debug(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}
		MDocType dt = MDocType.get(getCtx(), getC_DocTypeTarget_ID());

		// Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}

		// Lines
		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}

		// Bug 1564431
		if (getDeliveryRule() != null && getDeliveryRule().equals(MOrder.DELIVERYRULE_CompleteOrder))
		{
			for (int i = 0; i < lines.length; i++)
			{
				MOrderLine line = lines[i];
				MProduct product = line.getProduct();
				if (product != null && product.isExcludeAutoDelivery())
				{
					m_processMsg = "@M_Product_ID@ " + product.getValue() + " @IsExcludeAutoDelivery@";
					return DocAction.STATUS_Invalid;
				}
			}
		}

		// Convert DocType to Target
		if (getC_DocType_ID() != getC_DocTypeTarget_ID())
		{
			// Cannot change Std to anything else if different warehouses
			if (getC_DocType_ID() != 0)
			{
				MDocType dtOld = MDocType.get(getCtx(), getC_DocType_ID());
				if (MDocType.DOCSUBTYPE_StandardOrder.equals(dtOld.getDocSubType())		// From SO
						&& !MDocType.DOCSUBTYPE_StandardOrder.equals(dt.getDocSubType()))  	// To !SO
				{
					for (int i = 0; i < lines.length; i++)
					{
						if (lines[i].getM_Warehouse_ID() != getM_Warehouse_ID())
						{
							log.warn("different Warehouse " + lines[i]);
							m_processMsg = "@CannotChangeDocType@";
							return DocAction.STATUS_Invalid;
						}
					}
				}
			}

			// New or in Progress/Invalid
			if (DOCSTATUS_Drafted.equals(getDocStatus())
					|| DOCSTATUS_InProgress.equals(getDocStatus())
					|| DOCSTATUS_Invalid.equals(getDocStatus())
					|| getC_DocType_ID() == 0)
			{
				setC_DocType_ID(getC_DocTypeTarget_ID());
			}
			else
			// convert only if offer
			{
				if (dt.isOffer())
					setC_DocType_ID(getC_DocTypeTarget_ID());
				else
				{
					m_processMsg = "@CannotChangeDocType@";
					return DocAction.STATUS_Invalid;
				}
			}
		}  	// convert DocType

		// Mandatory Product Attribute Set Instance
		String mandatoryType = "='Y'";	// IN ('Y','S')
		String sql = "SELECT COUNT(*) "
				+ "FROM C_OrderLine ol"
				+ " INNER JOIN M_Product p ON (ol.M_Product_ID=p.M_Product_ID)"
				+ " INNER JOIN M_AttributeSet pas ON (p.M_AttributeSet_ID=pas.M_AttributeSet_ID) "
				+ "WHERE pas.MandatoryType" + mandatoryType
				+ " AND (ol.M_AttributeSetInstance_ID is NULL OR ol.M_AttributeSetInstance_ID = 0)"
				+ " AND ol.C_Order_ID=?";
		int no = DB.getSQLValue(get_TrxName(), sql, getC_Order_ID());
		if (no != 0)
		{
			m_processMsg = "@LinesWithoutProductAttribute@ (" + no + ")";
			return DocAction.STATUS_Invalid;
		}

		// Lines
		// task 09030: we don't really want to explode the BOM, least of all this uncontrolled way.
		// if (explodeBOM())
		// lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID); Note: if we don't explode, we don't need to reload the lines
		if (!reserveStock(dt, lines))
		{
			m_processMsg = "Cannot reserve Stock";
			return DocAction.STATUS_Invalid;
		}
		if (!calculateTaxTotal())
		{
			m_processMsg = "Error calculating tax";
			return DocAction.STATUS_Invalid;
		}

		// Credit Check
		if (isSOTrx())
		{
			if (MDocType.DOCSUBTYPE_POSOrder.equals(dt.getDocSubType())
					&& PAYMENTRULE_Cash.equals(getPaymentRule())
					&& !MSysConfig.getBooleanValue("CHECK_CREDIT_ON_CASH_POS_ORDER", true, getAD_Client_ID(), getAD_Org_ID()))
			{
				// ignore -- don't validate for Cash POS Orders depending on sysconfig parameter
			}
			else if (MDocType.DOCSUBTYPE_PrepayOrder.equals(dt.getDocSubType())
					&& !MSysConfig.getBooleanValue("CHECK_CREDIT_ON_PREPAY_ORDER", true, getAD_Client_ID(), getAD_Org_ID()))
			{
				// ignore -- don't validate Prepay Orders depending on sysconfig parameter
			}
			else
			{

				final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);
				final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

				final I_C_BPartner partner = InterfaceWrapperHelper.create(getCtx(), getC_BPartner_ID(), I_C_BPartner.class, get_TrxName());
				final IBPartnerStats stats = bpartnerStatsDAO.retrieveBPartnerStats(partner);

				final BigDecimal totalOpenBalance = stats.getTotalOpenBalance();
				final String soCreditStatus = stats.getSOCreditStatus();

				if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(soCreditStatus))
				{
					m_processMsg = "@BPartnerCreditStop@ - @TotalOpenBalance@="
							+ totalOpenBalance
							+ ", @SO_CreditLimit@=" + partner.getSO_CreditLimit();
					return DocAction.STATUS_Invalid;
				}
				if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold.equals(soCreditStatus))
				{
					m_processMsg = "@BPartnerCreditHold@ - @TotalOpenBalance@="
							+ totalOpenBalance
							+ ", @SO_CreditLimit@=" + partner.getSO_CreditLimit();
					return DocAction.STATUS_Invalid;
				}
				BigDecimal grandTotal = Services.get(ICurrencyBL.class).convertBase(getCtx(),
						getGrandTotal(), getC_Currency_ID(), getDateOrdered(),
						getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID());

				final String calculatedSOCreditStatus = bpartnerStatsBL.calculateSOCreditStatus(stats, grandTotal);

				if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold.equals(calculatedSOCreditStatus))
				{
					m_processMsg = "@BPartnerOverOCreditHold@ - @TotalOpenBalance@="
							+ totalOpenBalance + ", @GrandTotal@=" + grandTotal
							+ ", @SO_CreditLimit@=" + partner.getSO_CreditLimit();
					return DocAction.STATUS_Invalid;
				}

			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		// if (!DOCACTION_Complete.equals(getDocAction())) don't set for just prepare
		// setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}	// prepareIt

	// @formatter:off
//	/**
//	 * Explode non stocked BOM.
//	 * task 09030: we don't really want to explode the BOM, least of all this way
//	 *
//	 * @return true if bom exploded
//	 */
//	private boolean explodeBOM()
//	{
//		boolean retValue = false;
//		String where = "AND IsActive='Y' AND EXISTS "
//				+ "(SELECT * FROM M_Product p WHERE C_OrderLine.M_Product_ID=p.M_Product_ID"
//				+ " AND	p.IsBOM='Y' AND p.IsVerified='Y' AND p.IsStocked='N')";
//		//
//		String sql = "SELECT COUNT(*) FROM C_OrderLine "
//				+ "WHERE C_Order_ID=? " + where;
//		int count = DB.getSQLValue(get_TrxName(), sql, getC_Order_ID());
//		while (count != 0)
//		{
//			retValue = true;
//			renumberLines(1000);		// max 999 bom items
//
//			// Order Lines with non-stocked BOMs
//			MOrderLine[] lines = getLines(where, MOrderLine.COLUMNNAME_Line);
//			for (int i = 0; i < lines.length; i++)
//			{
//				MOrderLine line = lines[i];
//				MProduct product = MProduct.get(getCtx(), line.getM_Product_ID());
//				log.debug(product.getName());
//				// New Lines
//				int lineNo = line.getLine();
//				// find default BOM with valid dates and to this product
//				MPPProductBOM bom = MPPProductBOM.get(product, getAD_Org_ID(), getDatePromised(), get_TrxName());
//				if (bom != null)
//				{
//					MPPProductBOMLine[] bomlines = bom.getLines(getDatePromised());
//					for (int j = 0; j < bomlines.length; j++)
//					{
//						final I_PP_Product_BOMLine bomline = bomlines[j];
//						MOrderLine newLine = new MOrderLine(this);
//						newLine.setLine(++lineNo);
//						newLine.setM_Product_ID(bomline.getM_Product_ID());
//						newLine.setC_UOM_ID(bomline.getC_UOM_ID());
//						newLine.setQty(line.getQtyOrdered().multiply(
//								bomline.getQtyBOM()));
//						if (bomline.getDescription() != null)
//							newLine.setDescription(bomline.getDescription());
//						//
//						newLine.setPrice();
//						newLine.save(get_TrxName());
//					}
//				}
//
//				/*
//				 * MProductBOM[] boms = MProductBOM.getBOMLines (product);
//				 * for (int j = 0; j < boms.length; j++)
//				 * {
//				 * //MProductBOM bom = boms[j];
//				 * MPPProductBOMLine bom = boms[j];
//				 * MOrderLine newLine = new MOrderLine (this);
//				 * newLine.setLine (++lineNo);
//				 * //newLine.setM_Product_ID (bom.getProduct ()
//				 * // .getM_Product_ID ());
//				 * newLine.setM_Product_ID (bom.getM_Product_ID ());
//				 * //newLine.setC_UOM_ID (bom.getProduct ().getC_UOM_ID ());
//				 * newLine.setC_UOM_ID (bom.getC_UOM_ID ());
//				 * //newLine.setQty (line.getQtyOrdered ().multiply (
//				 * // bom.getBOMQty ()));
//				 * newLine.setQty (line.getQtyOrdered ().multiply (
//				 * bom.getQtyBOM()));
//				 * if (bom.getDescription () != null)
//				 * newLine.setDescription (bom.getDescription ());
//				 * //
//				 * newLine.setPrice ();
//				 * newLine.save (get_TrxName());
//				 * }
//				 */
//
//				// Convert into Comment Line
//				line.setM_Product_ID(0);
//				line.setM_AttributeSetInstance_ID(0);
//				line.setPrice(Env.ZERO);
//				line.setPriceLimit(Env.ZERO);
//				line.setPriceList(Env.ZERO);
//				line.setLineNetAmt(Env.ZERO);
//				line.setFreightAmt(Env.ZERO);
//				//
//				String description = product.getName();
//				if (product.getDescription() != null)
//					description += " " + product.getDescription();
//				if (line.getDescription() != null)
//					description += " " + line.getDescription();
//				line.setDescription(description);
//				line.save(get_TrxName());
//			}	// for all lines with BOM
//
//			m_lines = null;		// force requery
//			count = DB.getSQLValue(get_TrxName(), sql, getC_Invoice_ID());
//			renumberLines(10);
//		}	// while count != 0
//		return retValue;
//	}	// explodeBOM
	// @formatter:on

	/**
	 * Reserve Inventory.
	 * Counterpart: MInOut.completeIt()
	 *
	 * @param dt document type or null
	 * @param lines order lines (ordered by M_Product_ID for deadlock prevention)
	 * @return true if (un) reserved
	 */
	// metas: make reserveStock visible from MOrderLine to allow un-reservation
	// of stocks before delete.
	public boolean reserveStock(MDocType docType, MOrderLine[] lines)
	{
		if (docType == null)
		{
			docType = MDocType.get(getCtx(), getC_DocType_ID());
		}

		// Binding
		boolean binding = docType != null && !docType.isProposal();
		final String docSubType = docType == null ? null : docType.getDocSubType();

		// Not binding - i.e. Target=0
		if (DOCACTION_Void.equals(getDocAction())
				// Closing Binding Quotation
				|| (MDocType.DOCSUBTYPE_Quotation.equals(docSubType)
						&& DOCACTION_Close.equals(getDocAction())))   // || isDropShip() )
		{

			binding = false;
		}
		boolean isSOTrx = isSOTrx();

		log.debug("Binding=" + binding + " - IsSOTrx=" + isSOTrx);

		// Force same WH for all but SO/PO
		int header_M_Warehouse_ID = getM_Warehouse_ID();
		if (MDocType.DOCSUBTYPE_StandardOrder.equals(docSubType)
				|| MDocType.DOCBASETYPE_PurchaseOrder.equals(docSubType))
		{
			header_M_Warehouse_ID = 0;		// don't enforce
		}

		BigDecimal Volume = Env.ZERO;
		BigDecimal Weight = Env.ZERO;

		// Always check and (un) Reserve Inventory
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];

			final I_M_Warehouse lineWarehouseAdviced = warehouseAdvisor.evaluateWarehouse(line);
			final int lineWarehouseIdAdviced = lineWarehouseAdviced == null ? -1 : lineWarehouseAdviced.getM_Warehouse_ID();

			// Check/set WH/Org
			if (header_M_Warehouse_ID != 0)  	// enforce WH
			{
				if (header_M_Warehouse_ID != lineWarehouseIdAdviced
						&& lineWarehouseIdAdviced != MOrgInfo.get(getCtx(), getAD_Org_ID(), get_TrxName()).getDropShip_Warehouse_ID())   // metas 01658 removing 'isDropShip' flag
				{
					line.setM_Warehouse_ID(header_M_Warehouse_ID);
				}
				if (getAD_Org_ID() != line.getAD_Org_ID())
				{
					line.setAD_Org_ID(getAD_Org_ID());
				}
			}
			// Binding
			BigDecimal target = binding ? line.getQtyOrdered() : Env.ZERO;
			BigDecimal difference = target
					.subtract(line.getQtyReserved())
					.subtract(line.getQtyDelivered());
			if (difference.signum() == 0)
			{
				MProduct product = line.getProduct();
				if (product != null)
				{
					Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
					Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
				}
				continue;
			}

			log.debug("Line=" + line.getLine()
					+ " - Target=" + target + ",Difference=" + difference
					+ " - Ordered=" + line.getQtyOrdered()
					+ ",Reserved=" + line.getQtyReserved() + ",Delivered=" + line.getQtyDelivered());

			// Check Product - Stocked and Item
			MProduct product = line.getProduct();
			if (product != null)
			{
				if (Services.get(IProductBL.class).isStocked(product))
				{
					BigDecimal ordered = isSOTrx ? Env.ZERO : difference;
					BigDecimal reserved = isSOTrx ? difference : Env.ZERO;
					final int lineWarehouseId = (line.getM_Warehouse_ID() > 0 ? line.getM_Warehouse_ID() : Services.get(IWarehouseAdvisor.class).evaluateWarehouse(line).getM_Warehouse_ID());
					int M_Locator_ID = 0;
					// Get Locator to reserve
					if (line.getM_AttributeSetInstance_ID() != 0)  	// Get existing Location
					{
						M_Locator_ID = MStorage.getM_Locator_ID(line.getM_Warehouse_ID(),
								line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
								ordered, get_TrxName());
					}
					// Get default Location
					if (M_Locator_ID == 0)
					{
						// try to take default locator for product first
						// if it is from the selected warehouse
						MWarehouse wh = MWarehouse.get(getCtx(), lineWarehouseId);
						M_Locator_ID = product.getM_Locator_ID();
						if (M_Locator_ID != 0)
						{
							MLocator locator = new MLocator(getCtx(), product.getM_Locator_ID(), get_TrxName());
							// product has default locator defined but is not from the order warehouse
							if (locator.getM_Warehouse_ID() != wh.get_ID())
							{
								M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
							}
						}
						else
						{
							M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
						}
					}
					// Update Storage
					// task 08999: update it async
					Services.get(IStorageBL.class).addAsync(
							getCtx(),
							lineWarehouseId,
							M_Locator_ID,
							line.getM_Product_ID(),
							line.getM_AttributeSetInstance_ID(),
							line.getM_AttributeSetInstance_ID(),
							Env.ZERO,
							reserved,
							ordered,
							get_TrxName());
				}  	// stockec
				// update line

				// task 09358: get rid of this; instead, update qtyReserved only in IOrderLineBL.
				// line.setQtyReserved(line.getQtyReserved().add(difference));
				// line.saveEx(get_TrxName()); // metas: use saveEx

				//
				Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
				Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
			}  	// product
		}  	// reverse inventory

		setVolume(Volume);
		setWeight(Weight);
		return true;
	}	// reserveStock

	/**
	 * Calculate Tax and Total
	 *
	 * @return true if tax total calculated
	 */
	public boolean calculateTaxTotal()
	{
		final String trxName = get_TrxName();

		log.debug("");

		// Delete Taxes
		DB.executeUpdateEx("DELETE FROM C_OrderTax WHERE C_Order_ID=" + getC_Order_ID(), trxName);
		m_taxes = null;

		// Lines
		BigDecimal totalLines = Env.ZERO;
		final Set<Integer> taxIds = new HashSet<Integer>();
		final MOrderLine[] lines = getLines();
		for (final MOrderLine line : lines)
		{
			final int taxId = line.getC_Tax_ID();
			if (!taxIds.contains(taxId))
			{
				final int taxPrecision = Services.get(IOrderLineBL.class).getPrecision(line);
				final boolean taxIncluded = Services.get(IOrderLineBL.class).isTaxIncluded(line);
				final MOrderTax oTax = MOrderTax.get(line, taxPrecision, false, trxName);	// current Tax
				oTax.setIsTaxIncluded(taxIncluded);
				if (!oTax.calculateTaxFromLines())
				{
					return false;
				}
				Check.assume(oTax.isActive(), "OrderTax shall be active: {}", oTax);

				InterfaceWrapperHelper.save(oTax);

				taxIds.add(taxId);
			}
			totalLines = totalLines.add(line.getLineNetAmt());
		}

		// Taxes
		BigDecimal grandTotal = totalLines;
		final MOrderTax[] taxes = getTaxes(true);
		for (final MOrderTax oTax : taxes)
		{
			final MTax tax = oTax.getTax();
			if (tax.isSummary())
			{
				final MTax[] cTaxes = tax.getChildTaxes(false);
				for (final MTax cTax : cTaxes)
				{
					final int taxPrecision = Services.get(IOrderBL.class).getPrecision(this);
					final boolean taxIncluded = Services.get(IOrderBL.class).isTaxIncluded(this, cTax);
					BigDecimal taxAmt = Services.get(ITaxBL.class).calculateTax(cTax, oTax.getTaxBaseAmt(), taxIncluded, taxPrecision);
					//
					final MOrderTax newOTax = new MOrderTax(getCtx(), 0, trxName);
					newOTax.setClientOrg(this);
					newOTax.setC_Order_ID(getC_Order_ID());
					newOTax.setC_Tax_ID(cTax.getC_Tax_ID());
					newOTax.setPrecision(taxPrecision);
					newOTax.setIsTaxIncluded(taxIncluded);
					newOTax.setTaxBaseAmt(oTax.getTaxBaseAmt());
					newOTax.setTaxAmt(taxAmt);
					if (!newOTax.save(trxName))
						return false;
					//
					if (!newOTax.isTaxIncluded())
						grandTotal = grandTotal.add(taxAmt);
				}
				if (!oTax.delete(true, trxName))
					return false;
				if (!oTax.save(trxName))
					return false;
			}
			else
			{
				if (!oTax.isTaxIncluded())
					grandTotal = grandTotal.add(oTax.getTaxAmt());
			}
		}
		//
		setTotalLines(totalLines);
		setGrandTotal(grandTotal);
		return true;
	}	// calculateTaxTotal

	/**
	 * Approve Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
		log.debug("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	// approveIt

	/**
	 * Reject Approval
	 *
	 * @return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		log.debug("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}	// rejectIt

	/**************************************************************************
	 * Complete Document
	 *
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		final String result = Services.get(IPrepayOrderAllocationBL.class).orderBeforeCompleteIt(this);
		if (!Check.isEmpty(result))
		{
			return result; // there is nothing more to be done.
		}

		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		String DocSubType = dt.getDocSubType();

		// Just prepare
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			setProcessed(false);
			return DocAction.STATUS_InProgress;
		}
		// Offers
		if (MDocType.DOCSUBTYPE_Proposal.equals(DocSubType)
				|| MDocType.DOCSUBTYPE_Quotation.equals(DocSubType))
		{
			// Binding
			if (MDocType.DOCSUBTYPE_Quotation.equals(DocSubType))
				reserveStock(dt, getLines(true, MOrderLine.COLUMNNAME_M_Product_ID));
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
			if (m_processMsg != null)
				return DocAction.STATUS_Invalid;
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
			if (m_processMsg != null)
				return DocAction.STATUS_Invalid;
			// Set the definite document number after completed (if needed)
			setDefiniteDocumentNo();
			setProcessed(true);
			return DocAction.STATUS_Completed;
		}
		// Waiting Payment - until we have a payment
		if (!m_forceCreation
				&& (MDocType.DOCSUBTYPE_PrepayOrder.equals(DocSubType)
						|| de.metas.prepayorder.model.I_C_DocType.DOCSUBTYPE_PrepayOrder_metas.equals(DocSubType))  // cg fix for task US682
				&& getC_Payment_ID() == 0 && getC_CashLine_ID() == 0)
		{
			setProcessed(true);
			return DocAction.STATUS_WaitingPayment;
		}

		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Implicit Approval
		if (!isApproved())
			approveIt();
		getLines(true, null);
		log.debug("Completed: {}", this);
		StringBuffer info = new StringBuffer();

		boolean realTimePOS = false;

		// Create SO Shipment - Force Shipment
		MInOut shipment = null;
		if (MDocType.DOCSUBTYPE_OnCreditOrder.equals(DocSubType)		// (W)illCall(I)nvoice
				|| MDocType.DOCSUBTYPE_WarehouseOrder.equals(DocSubType)	// (W)illCall(P)ickup
				|| MDocType.DOCSUBTYPE_POSOrder.equals(DocSubType)			// (W)alkIn(R)eceipt
				|| MDocType.DOCSUBTYPE_PrepayOrder.equals(DocSubType))
		{
			if (!DELIVERYRULE_Force.equals(getDeliveryRule()))
				setDeliveryRule(DELIVERYRULE_Force);
			//
			shipment = createShipment(dt, realTimePOS ? null : getDateOrdered());
			if (shipment == null)
				return DocAction.STATUS_Invalid;
			info.append("@M_InOut_ID@: ").append(shipment.getDocumentNo());
			String msg = shipment.getProcessMsg();
			if (msg != null && msg.length() > 0)
				info.append(" (").append(msg).append(")");
		}  	// Shipment

		// Create SO Invoice - Always invoice complete Order
		if (MDocType.DOCSUBTYPE_POSOrder.equals(DocSubType)
				|| MDocType.DOCSUBTYPE_OnCreditOrder.equals(DocSubType)
				|| MDocType.DOCSUBTYPE_PrepayOrder.equals(DocSubType))
		{
			MInvoice invoice = createInvoice(dt, shipment, realTimePOS ? null : getDateOrdered());
			if (invoice == null)
				return DocAction.STATUS_Invalid;
			info.append(" - @C_Invoice_ID@: ").append(invoice.getDocumentNo());
			String msg = invoice.getProcessMsg();
			if (msg != null && msg.length() > 0)
				info.append(" (").append(msg).append(")");
		}  	// Invoice

		// User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			if (info.length() > 0)
				info.append(" - ");
			info.append(valid);
			m_processMsg = info.toString();
			return DocAction.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		setProcessed(true);
		m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Re_Activate); // issue #347
		return DocAction.STATUS_Completed;
	}	// completeIt

	/**
	 * Set the definite <code>DocumentNo</code> and <code>DateOrdered</code> after completed, both according to this order's <code>C_DocType</code>.<br>
	 * Also invokes {@link IOrderBL#setPOReferenceIfRequired(I_C_Order)} (task 09667).
	 */
	private void setDefiniteDocumentNo()
	{
		final I_C_DocType dt = getC_DocType();
		if (dt.isOverwriteDateOnComplete())
		{
			setDateOrdered(SystemTime.asTimestamp());
		}
		if (dt.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setTrxName(get_TrxName())
					.setPO(this)
					.setFailOnError(false)
					.build();
			if (value != null && value != IDocumentNoBuilder.NO_DOCUMENTNO)
			{
				setDocumentNo(value);
				Services.get(IDocumentNoBL.class).fireDocumentNoChange(this, value); // task 09776
			}
		}
	}

	/**
	 * Create Shipment
	 *
	 * @param dt order document type
	 * @param movementDate optional movement date (default today)
	 * @return shipment or null
	 */
	private MInOut createShipment(MDocType dt, Timestamp movementDate)
	{
		log.debug("For " + dt);
		MInOut shipment = new MInOut(this, dt.getC_DocTypeShipment_ID(), movementDate);
		// shipment.setDateAcct(getDateAcct());
		if (!shipment.save(get_TrxName()))
		{
			m_processMsg = "Could not create Shipment";
			return null;
		}
		//
		MOrderLine[] oLines = getLines(true, null);
		for (int i = 0; i < oLines.length; i++)
		{
			MOrderLine oLine = oLines[i];
			final I_C_OrderLine line = InterfaceWrapperHelper.create(oLine, I_C_OrderLine.class);
			MInOutLine ioLine = new MInOutLine(shipment);
			// Qty = Ordered - Delivered
			BigDecimal MovementQty = oLine.getQtyOrdered().subtract(oLine.getQtyDelivered());
			// Location
			final I_M_Warehouse warehouse = warehouseAdvisor.evaluateWarehouse(line);
			final int warehouseId = warehouse == null ? -1 : warehouse.getM_Warehouse_ID();
			int M_Locator_ID = MStorage.getM_Locator_ID(warehouseId,
					oLine.getM_Product_ID(), oLine.getM_AttributeSetInstance_ID(),
					MovementQty, get_TrxName());
			if (M_Locator_ID == 0)  		// Get default Location
			{
				MWarehouse wh = MWarehouse.get(getCtx(), warehouseId);
				M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
			}
			//
			ioLine.setOrderLine(oLine, M_Locator_ID, MovementQty);
			ioLine.setQty(MovementQty);
			if (oLine.getQtyEntered().compareTo(oLine.getQtyOrdered()) != 0)
				ioLine.setQtyEntered(MovementQty
						.multiply(oLine.getQtyEntered())
						.divide(oLine.getQtyOrdered(), 6, BigDecimal.ROUND_HALF_UP));
			if (!ioLine.save(get_TrxName()))
			{
				m_processMsg = "Could not create Shipment Line";
				return null;
			}
		}
		// metas: If order has no lines or all lines are comments, show error msg
		if (shipment.getLines().length == 0)
		{
			m_processMsg = Msg.getMsg(Env.getCtx(), NO_DELIVARABLE_LINES_FOUND, null);
			return null;
		}
		// metas: end
		// Manually Process Shipment
		String status = shipment.completeIt();
		shipment.setDocStatus(status);
		shipment.save(get_TrxName());
		if (!DOCSTATUS_Completed.equals(status))
		{
			m_processMsg = "@M_InOut_ID@: " + shipment.getProcessMsg();
			return null;
		}
		return shipment;
	}	// createShipment

	/**
	 * Create Invoice
	 *
	 * @param dt order document type
	 * @param shipment optional shipment
	 * @param invoiceDate invoice date
	 * @return invoice or null
	 */
	private MInvoice createInvoice(MDocType dt, MInOut shipment, Timestamp invoiceDate)
	{
		log.debug(dt.toString());
		MInvoice invoice = new MInvoice(this, dt.getC_DocTypeInvoice_ID(), invoiceDate);
		if (!invoice.save(get_TrxName()))
		{
			m_processMsg = "Could not create Invoice";
			return null;
		}

		// If we have a Shipment - use that as a base
		if (shipment != null)
		{
			if (!INVOICERULE_AfterDelivery.equals(getInvoiceRule()))
				setInvoiceRule(INVOICERULE_AfterDelivery);
			//
			MInOutLine[] sLines = shipment.getLines();
			for (int i = 0; i < sLines.length; i++)
			{
				MInOutLine sLine = sLines[i];
				//
				MInvoiceLine iLine = new MInvoiceLine(invoice);
				iLine.setShipLine(sLine);
				// Qty = Delivered
				if (sLine.sameOrderLineUOM())
					iLine.setQtyEntered(sLine.getQtyEntered());
				else
					iLine.setQtyEntered(sLine.getMovementQty());
				iLine.setQtyInvoiced(sLine.getMovementQty());
				if (!iLine.save(get_TrxName()))
				{
					m_processMsg = "Could not create Invoice Line from Shipment Line";
					return null;
				}
				//
				sLine.setIsInvoiced(true);
				if (!sLine.save(get_TrxName()))
				{
					log.warn("Could not update Shipment line: " + sLine);
				}
			}
		}
		else
		// Create Invoice from Order
		{
			if (!INVOICERULE_Immediate.equals(getInvoiceRule()))
				setInvoiceRule(INVOICERULE_Immediate);
			//
			MOrderLine[] oLines = getLines();
			for (int i = 0; i < oLines.length; i++)
			{
				MOrderLine oLine = oLines[i];
				//
				MInvoiceLine iLine = new MInvoiceLine(invoice);
				iLine.setOrderLine(oLine);
				// Qty = Ordered - Invoiced
				iLine.setQtyInvoiced(oLine.getQtyOrdered().subtract(oLine.getQtyInvoiced()));
				if (oLine.getQtyOrdered().compareTo(oLine.getQtyEntered()) == 0)
					iLine.setQtyEntered(iLine.getQtyInvoiced());
				else
					iLine.setQtyEntered(iLine.getQtyInvoiced().multiply(oLine.getQtyEntered())
							.divide(oLine.getQtyOrdered(), 12, BigDecimal.ROUND_HALF_UP));
				if (!iLine.save(get_TrxName()))
				{
					m_processMsg = "Could not create Invoice Line from Order Line";
					return null;
				}
			}
		}
		// Manually Process Invoice
		String status = invoice.completeIt();
		invoice.setDocStatus(status);
		invoice.save(get_TrxName());
		setC_CashLine_ID(invoice.getC_CashLine_ID());
		if (!DOCSTATUS_Completed.equals(status))
		{
			m_processMsg = "@C_Invoice_ID@: " + invoice.getProcessMsg();
			return null;
		}
		return invoice;
	}	// createInvoice

	/**
	 * Void Document.
	 * Set Qtys to 0 - Sales: reverse all documents
	 *
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		log.debug(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			BigDecimal old = line.getQtyOrdered();
			if (old.signum() != 0)
			{
				line.addDescription(Msg.getMsg(getCtx(), "Voided") + " (" + old + ")");
				line.setQty(Env.ZERO);
				line.setLineNetAmt(Env.ZERO);
				line.save(get_TrxName());
			}
			// AZ Goodwill
			if (!isSOTrx())
			{
				deleteMatchPOCostDetail(line);
			}
		}

		// update taxes
		MOrderTax[] taxes = getTaxes(true);
		for (MOrderTax tax : taxes)
		{
			if (!(tax.calculateTaxFromLines() && tax.save()))
				return false;
		}

		addDescription(Msg.getMsg(getCtx(), "Voided"));
		// Clear Reservations
		if (!reserveStock(null, lines))
		{
			m_processMsg = "Cannot unreserve Stock (void)";
			return false;
		}

		// UnLink All Requisitions
		MRequisitionLine.unlinkC_Order_ID(getCtx(), get_ID(), get_TrxName());

		if (!createReversals())
			return false;

		/* globalqss - 2317928 - Reactivating/Voiding order must reset posted */
		Services.get(IFactAcctDAO.class).deleteForDocument(this);
		setPosted(false);

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	// voidIt

	/**
	 * Create Shipment/Invoice Reversals
	 *
	 * @return true if success
	 */
	private boolean createReversals()
	{
		// Cancel only Sales
		if (!isSOTrx())
			return true;

		log.debug("createReversals");
		StringBuffer info = new StringBuffer();

		// Reverse All *Shipments*
		info.append("@M_InOut_ID@:");
		MInOut[] shipments = getShipments();
		for (int i = 0; i < shipments.length; i++)
		{
			MInOut ship = shipments[i];
			// if closed - ignore
			if (MInOut.DOCSTATUS_Closed.equals(ship.getDocStatus())
					|| MInOut.DOCSTATUS_Reversed.equals(ship.getDocStatus())
					|| MInOut.DOCSTATUS_Voided.equals(ship.getDocStatus()))
				continue;
			ship.set_TrxName(get_TrxName());

			// If not completed - void - otherwise reverse it
			if (!MInOut.DOCSTATUS_Completed.equals(ship.getDocStatus()))
			{
				if (ship.voidIt())
					ship.setDocStatus(MInOut.DOCSTATUS_Voided);
			}
			else if (ship.reverseCorrectIt())  	// completed shipment
			{
				ship.setDocStatus(MInOut.DOCSTATUS_Reversed);
				info.append(" ").append(ship.getDocumentNo());
			}
			else
			{
				m_processMsg = "Could not reverse Shipment " + ship;
				return false;
			}
			ship.setDocAction(MInOut.DOCACTION_None);
			ship.save(get_TrxName());
		}  	// for all shipments

		// Reverse All *Invoices*
		info.append(" - @C_Invoice_ID@:");
		MInvoice[] invoices = getInvoices();
		for (int i = 0; i < invoices.length; i++)
		{
			MInvoice invoice = invoices[i];
			// if closed - ignore
			if (MInvoice.DOCSTATUS_Closed.equals(invoice.getDocStatus())
					|| MInvoice.DOCSTATUS_Reversed.equals(invoice.getDocStatus())
					|| MInvoice.DOCSTATUS_Voided.equals(invoice.getDocStatus()))
				continue;
			invoice.set_TrxName(get_TrxName());

			// If not completed - void - otherwise reverse it
			if (!MInvoice.DOCSTATUS_Completed.equals(invoice.getDocStatus()))
			{
				if (invoice.voidIt())
					invoice.setDocStatus(MInvoice.DOCSTATUS_Voided);
			}
			else if (invoice.reverseCorrectIt())  	// completed invoice
			{
				invoice.setDocStatus(MInvoice.DOCSTATUS_Reversed);
				info.append(" ").append(invoice.getDocumentNo());
			}
			else
			{
				m_processMsg = "Could not reverse Invoice " + invoice;
				return false;
			}
			invoice.setDocAction(MInvoice.DOCACTION_None);
			invoice.save(get_TrxName());
		}  	// for all shipments

		m_processMsg = info.toString();
		return true;
	}	// createReversals

	/**
	 * Close Document.
	 * Cancel not delivered Qunatities
	 *
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		log.debug(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		// Close Not delivered Qty - SO/PO
		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			BigDecimal old = line.getQtyOrdered();
			if (old.compareTo(line.getQtyDelivered()) != 0)
			{
				line.setQtyLostSales(line.getQtyOrdered().subtract(line.getQtyDelivered()));
				line.setQtyOrdered(line.getQtyDelivered());
				// QtyEntered unchanged
				line.addDescription("Close (" + old + ")");
				InterfaceWrapperHelper.save(line, get_TrxName());
			}
		}
		// Clear Reservations
		if (!reserveStock(null, lines))
		{
			m_processMsg = "Cannot unreserve Stock (close)";
			return false;
		}
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	// closeIt

	/**
	 * @author: phib
	 *          re-open a closed order
	 *          (reverse steps of close())
	 */
	public String reopenIt()
	{
		log.debug(toString());
		if (!MOrder.DOCSTATUS_Closed.equals(getDocStatus()))
		{
			return "Not closed - can't reopen";
		}

		//
		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			if (Env.ZERO.compareTo(line.getQtyLostSales()) != 0)
			{
				line.setQtyOrdered(line.getQtyLostSales().add(line.getQtyDelivered()));
				line.setQtyLostSales(Env.ZERO);
				// QtyEntered unchanged

				// Strip Close() tags from description
				String desc = line.getDescription();
				if (desc == null)
					desc = "";
				Pattern pattern = Pattern.compile("( \\| )?Close \\(.*\\)");
				String[] parts = pattern.split(desc);
				desc = "";
				for (String s : parts)
				{
					desc = desc.concat(s);
				}
				line.setDescription(desc);
				if (!line.save(get_TrxName()))
					return "Couldn't save orderline";
			}
		}
		// Clear Reservations
		if (!reserveStock(null, lines))
		{
			m_processMsg = "Cannot unreserve Stock (close)";
			return "Failed to update reservations";
		}

		setDocStatus(MOrder.DOCSTATUS_Completed);
		setDocAction(DOCACTION_Close);
		if (!this.save(get_TrxName()))
			return "Couldn't save reopened order";
		else
			return "";
	}	// reopenIt

	/**
	 * Reverse Correction - same void
	 *
	 * @return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.debug(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return voidIt();
	}	// reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 *
	 * @return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.debug(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}	// reverseAccrualIt

	/**
	 * Re-activate.
	 *
	 * @return true if success
	 */
	@Override
	public boolean reActivateIt()
	{
		log.debug(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		String DocSubType = dt.getDocSubType();

		// Replace Prepay with POS to revert all doc
		if (MDocType.DOCSUBTYPE_PrepayOrder.equals(DocSubType))
		{
			MDocType newDT = null;
			MDocType[] dts = MDocType.getOfClient(getCtx());
			for (int i = 0; i < dts.length; i++)
			{
				MDocType type = dts[i];
				if (MDocType.DOCSUBTYPE_PrepayOrder.equals(type.getDocSubType()))
				{
					if (type.isDefault() || newDT == null)
						newDT = type;
				}
			}
			if (newDT == null)
				return false;
			else
				setC_DocType_ID(newDT.getC_DocType_ID());
		}

		// PO - just re-open
		if (!isSOTrx())
			log.debug("Existing documents not modified - " + dt);
		// Reverse Direct Documents
		else if (MDocType.DOCSUBTYPE_OnCreditOrder.equals(DocSubType)	// (W)illCall(I)nvoice
				|| MDocType.DOCSUBTYPE_WarehouseOrder.equals(DocSubType)	// (W)illCall(P)ickup
				|| MDocType.DOCSUBTYPE_POSOrder.equals(DocSubType))  			// (W)alkIn(R)eceipt
		{
			if (!createReversals())
				return false;
		}
		else
		{
			log.debug("Existing documents not modified - SubType=" + DocSubType);
		}

		/* globalqss - 2317928 - Reactivating/Voiding order must reset posted */
		Services.get(IFactAcctDAO.class).deleteForDocument(this);
		setPosted(false);
		// metas: after reactivate put to the end of this method
		setDocAction(DOCACTION_Complete);
		setProcessed(false);

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// metas: commented out (legacy purposes)
		// TODO: metas: evaluate if we can uncommented this and remove the setDocAction above
		// setDocAction(DOCACTION_Complete);
		// setProcessed(false);
		return true;
	}	// reActivateIt

	/*************************************************************************
	 * Get Summary
	 *
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		// : Grand Total = 123.00 (#1)
		sb.append(": ").append(Msg.translate(getCtx(), "GrandTotal")).append("=").append(getGrandTotal());
		if (m_lines != null)
			sb.append(" (#").append(m_lines.length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	// getSummary

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}	// getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getSalesRep_ID();
	}	// getDoc_User_ID

	/**
	 * Get Document Approval Amount
	 *
	 * @return amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return getGrandTotal();
	}	// getApprovalAmt

	// AZ Goodwill
	private String deleteMatchPOCostDetail(MOrderLine line)
	{
		// Get Account Schemas to delete MCostDetail
		MAcctSchema[] acctschemas = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID());
		for (int asn = 0; asn < acctschemas.length; asn++)
		{
			MAcctSchema as = acctschemas[asn];

			if (as.isSkipOrg(getAD_Org_ID()))
			{
				continue;
			}

			// update/delete Cost Detail and recalculate Current Cost
			MMatchPO[] mPO = MMatchPO.getOrderLine(getCtx(), line.getC_OrderLine_ID(), get_TrxName());
			// delete Cost Detail if the Matched PO has been deleted
			if (mPO.length == 0)
			{
				MCostDetail cd = MCostDetail.get(getCtx(), "C_OrderLine_ID=?",
						line.getC_OrderLine_ID(), line.getM_AttributeSetInstance_ID(),
						as.getC_AcctSchema_ID(), get_TrxName());
				if (cd != null)
				{
					cd.setProcessed(false);
					cd.delete(true);
				}
			}
		}

		return "";
	}

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
				|| DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	}	// isComplete

	// metas: begin
	public static final String NO_DELIVARABLE_LINES_FOUND = "NoDeliverableLinesFound";

	/**
	 * Is Force Creation of this order enabled
	 *
	 * @return
	 * @see #setDocAction(String, boolean)
	 */
	public boolean is_ForceCreation()
	{
		return m_forceCreation;
	}
	// metas: end
} // MOrder
