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

import static org.adempiere.util.CustomColNames.C_Invoice_BPARTNERADDRESS;
import static org.adempiere.util.CustomColNames.C_Invoice_DESCRIPTION_BOTTOM;
import static org.adempiere.util.CustomColNames.C_Invoice_INCOTERM;
import static org.adempiere.util.CustomColNames.C_Invoice_INCOTERMLOCATION;
import static org.adempiere.util.CustomColNames.C_Invoice_ISUSE_BPARTNER_ADDRESS;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.bpartner.service.BPartnerCreditLimitRepository;
import org.adempiere.bpartner.service.BPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.BPartnerNoAddressException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.misc.service.IPOService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.Adempiere;
import org.compiere.print.ReportEngine;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Joiner;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_Order;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Msg;
import de.metas.invoice.IMatchInvBL;
import de.metas.logging.LogManager;
import de.metas.prepayorder.service.IPrepayOrderAllocationBL;
import de.metas.tax.api.ITaxBL;

/**
 * Invoice Model.
 * Please do not set DocStatus and C_DocType_ID directly.
 * They are set in the process() method.
 * Use DocAction and C_DocTypeTarget_ID instead.
 *
 * @author Jorg Janke
 * @version $Id: MInvoice.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * @see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962
 *      <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *      Modifications: Added RMA functionality (Ashley Ramdass)
 */
public class MInvoice extends X_C_Invoice implements IDocument
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5406556271212363271L;

	private static final String ERR_NoBaseConversionBetweenCurrencies = "NoBaseConversionBetweenCurrencies";

	/**
	 * Get Payments Of BPartner
	 *
	 * @param ctx context
	 * @param C_BPartner_ID id
	 * @param trxName transaction
	 * @return array
	 */
	public static MInvoice[] getOfBPartner(final Properties ctx, final int C_BPartner_ID, final String trxName)
	{
		final List<MInvoice> list = new Query(ctx, Table_Name, COLUMNNAME_C_BPartner_ID + "=?", trxName)
				.setParameters(new Object[] { C_BPartner_ID })
				.list();
		return list.toArray(new MInvoice[list.size()]);
	}	// getOfBPartner

	/**
	 * Create new Invoice by copying
	 *
	 * @param from invoice
	 * @param dateDoc date of the document date
	 * @param acctDate original account date
	 * @param C_DocTypeTarget_ID target doc type
	 * @param isSOTrx sales order
	 * @param counter create counter links
	 * @param trxName trx
	 * @param setOrder set Order links
	 * @return Invoice
	 *
	 * @deprecated please use {@link IInvoiceBL#copyFrom(I_C_Invoice, Timestamp, int, boolean, boolean, boolean, boolean, boolean)} instead.
	 */
	@Deprecated
	public static MInvoice copyFrom(final MInvoice from, final Timestamp dateDoc, final Timestamp dateAcct,
			final int C_DocTypeTarget_ID, final boolean isSOTrx, final boolean counter,
			final String trxName, final boolean setOrder)
	{
		// ts: 04054: moving copyFrom business logic to the implementors of IInvoiceBL
		// NOTE: the old crap is deleted from here.... search it in SCM history
		final I_C_Invoice to = Services.get(IInvoiceBL.class).copyFrom(from, dateDoc, C_DocTypeTarget_ID, isSOTrx, counter, setOrder,
				false,  // setInvoiceRef == false
				true); // copyLines == true

		// Make sure DateAcct is set (08356)
		to.setDateAcct(dateAcct);
		InterfaceWrapperHelper.save(to);

		return LegacyAdapters.convertToPO(to);
	}

	/**
	 * Get PDF File Name
	 *
	 * @param documentDir directory
	 * @param C_Invoice_ID invoice
	 * @return file name
	 */
	public static String getPDFFileName(final String documentDir, final int C_Invoice_ID)
	{
		final StringBuffer sb = new StringBuffer(documentDir);
		if (sb.length() == 0)
		{
			sb.append(".");
		}
		if (!sb.toString().endsWith(File.separator))
		{
			sb.append(File.separator);
		}
		sb.append("C_Invoice_ID_")
				.append(C_Invoice_ID)
				.append(".pdf");
		return sb.toString();
	}	// getPDFFileName

	/**
	 * Get MInvoice from Cache
	 *
	 * @param ctx context
	 * @param C_Invoice_ID id
	 * @return MInvoice
	 */
	public static MInvoice get(final Properties ctx, final int C_Invoice_ID)
	{
		final Integer key = new Integer(C_Invoice_ID);
		MInvoice retValue = s_cache.get(key);
		if (retValue != null)
		{
			return retValue;
		}
		retValue = new MInvoice(ctx, C_Invoice_ID, null);
		if (retValue.get_ID() != 0)
		{
			s_cache.put(key, retValue);
		}
		return retValue;
	} // get

	/** Cache */
	private static CCache<Integer, MInvoice> s_cache = new CCache<>("C_Invoice", 20, 2);	// 2 minutes

	/**************************************************************************
	 * Invoice Constructor
	 *
	 * @param ctx context
	 * @param C_Invoice_ID invoice or 0 for new
	 * @param trxName trx name
	 */
	public MInvoice(final Properties ctx, final int C_Invoice_ID, final String trxName)
	{
		super(ctx, C_Invoice_ID, trxName);
		if (C_Invoice_ID == 0)
		{
			setDocStatus(DOCSTATUS_Drafted);		// Draft
			setDocAction(DOCACTION_Complete);
			//
			// FRESH-488: Get the default payment rule form the system configuration
			setPaymentRule(Services.get(IInvoiceBL.class).getDefaultPaymentRule());	// Payment Terms

			setDateInvoiced(new Timestamp(System.currentTimeMillis()));
			setDateAcct(new Timestamp(System.currentTimeMillis()));
			//
			setChargeAmt(Env.ZERO);
			setTotalLines(Env.ZERO);
			setGrandTotal(Env.ZERO);
			//
			setIsSOTrx(true);
			setIsTaxIncluded(false);
			setIsApproved(false);
			setIsDiscountPrinted(false);
			setIsPaid(false);
			setSendEMail(false);
			setIsPrinted(false);
			setIsTransferred(false);
			setIsSelfService(false);
			setIsPayScheduleValid(false);
			setIsInDispute(false);
			setPosted(false);
			super.setProcessed(false);
			setProcessing(false);
		}
	}	// MInvoice

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set record
	 * @param trxName transaction
	 */
	public MInvoice(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MInvoice

	/**
	 * Create Invoice from Order
	 *
	 * @param order order
	 * @param C_DocTypeTarget_ID target document type
	 * @param invoiceDate date or null
	 */
	@Deprecated
	public MInvoice(final MOrder order, final int C_DocTypeTarget_ID, final Timestamp invoiceDate)
	{
		this(order.getCtx(), 0, order.get_TrxName());
		setClientOrg(order);
		setOrder(order);	// set base settings
		//
		int docTypeId = C_DocTypeTarget_ID;
		if (docTypeId <= 0)
		{
			final MDocType odt = MDocType.get(order.getCtx(), order.getC_DocType_ID());
			if (odt != null)
			{
				docTypeId = odt.getC_DocTypeInvoice_ID();
				if (docTypeId <= 0)
				{
					throw new AdempiereException("@NotFound@ @C_DocTypeInvoice_ID@ - @C_DocType_ID@:" + odt.get_Translation(MDocType.COLUMNNAME_Name));
				}
			}
		}
		Services.get(IInvoiceBL.class).setDocTypeTargetIdAndUpdateDescription(this, docTypeId);
		if (invoiceDate != null)
		{
			setDateInvoiced(invoiceDate);
		}
		setDateAcct(getDateInvoiced());
		//
		setSalesRep_ID(order.getSalesRep_ID());
		//
		setC_BPartner_ID(order.getBill_BPartner_ID());
		setC_BPartner_Location_ID(order.getBill_Location_ID());
		setAD_User_ID(order.getBill_User_ID());
	}	// MInvoice

	/**
	 * Create Invoice from Shipment
	 *
	 * @param ship shipment
	 * @param invoiceDate date or null
	 */
	public MInvoice(final MInOut ship, final Timestamp invoiceDate)
	{
		this(ship.getCtx(), 0, ship.get_TrxName());
		setClientOrg(ship);
		setShipment(ship);	// set base settings
		//
		Services.get(IInvoiceBL.class).setDocTypeTargetIdIfNotSet(this);
		if (invoiceDate != null)
		{
			setDateInvoiced(invoiceDate);
		}
		setDateAcct(getDateInvoiced());
		//
		setSalesRep_ID(ship.getSalesRep_ID());
		setAD_User_ID(ship.getAD_User_ID()); // metas

		// metas: additional fields
		final IPOService poService = Services.get(IPOService.class);
		poService.copyValue(ship, this, I_C_Order.COLUMNNAME_Incoterm);
		poService.copyValue(ship, this, C_Invoice_INCOTERMLOCATION);
		ship.setDescriptionBottom(getDescriptionBottom());
		poService.copyValue(ship, this, C_Invoice_ISUSE_BPARTNER_ADDRESS);
		poService.copyValue(ship, this, C_Invoice_BPARTNERADDRESS);

		InterfaceWrapperHelper.setDynAttribute(this, I_M_InOut.Table_Name, ship); // task 07286
		// metas end
	}	// MInvoice

	/**
	 * Create Invoice from Batch Line
	 *
	 * @param batch batch
	 * @param line batch line
	 */
	public MInvoice(final MInvoiceBatch batch, final MInvoiceBatchLine line)
	{
		this(line.getCtx(), 0, line.get_TrxName());
		setClientOrg(line);
		setDocumentNo(line.getDocumentNo());
		//
		setIsSOTrx(batch.isSOTrx());
		final MBPartner bp = new MBPartner(line.getCtx(), line.getC_BPartner_ID(), line.get_TrxName());
		setBPartner(bp);	// defaults
		//
		setIsTaxIncluded(line.isTaxIncluded());
		// May conflict with default price list
		setC_Currency_ID(batch.getC_Currency_ID());
		setC_ConversionType_ID(batch.getC_ConversionType_ID());
		//
		// setPaymentRule(order.getPaymentRule());
		// setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
		// setPOReference("");
		setDescription(batch.getDescription());
		// setDateOrdered(order.getDateOrdered());

		// metas
		set_Value(C_Invoice_INCOTERM, batch.get_Value(C_Invoice_INCOTERM));
		set_Value(C_Invoice_INCOTERMLOCATION, batch.get_Value(C_Invoice_INCOTERMLOCATION));
		// TODO: DescriptionBottom?
		// metas end

		//
		setAD_OrgTrx_ID(line.getAD_OrgTrx_ID());
		setC_Project_ID(line.getC_Project_ID());
		// setC_Campaign_ID(line.getC_Campaign_ID());
		setC_Activity_ID(line.getC_Activity_ID());
		setUser1_ID(line.getUser1_ID());
		setUser2_ID(line.getUser2_ID());
		//
		Services.get(IInvoiceBL.class).setDocTypeTargetIdAndUpdateDescription(this, line.getC_DocType_ID());
		setDateInvoiced(line.getDateInvoiced());
		setDateAcct(line.getDateAcct());
		//
		setSalesRep_ID(batch.getSalesRep_ID());
		//
		setC_BPartner_ID(line.getC_BPartner_ID());
		setC_BPartner_Location_ID(line.getC_BPartner_Location_ID());
		setAD_User_ID(line.getAD_User_ID());
	}	// MInvoice

	// /** Open Amount */
	// private BigDecimal m_openAmt = null; // ts: 04054: moving OpenAmt business logic to the implementors of IInvoiceBL

	/** Invoice Lines */
	private MInvoiceLine[] m_lines;
	/** Invoice Taxes */
	private MInvoiceTax[] m_taxes;
	/** Logger */
	private static Logger s_log = LogManager.getLogger(MInvoice.class);

	/**
	 * Overwrite Client/Org if required
	 *
	 * @param AD_Client_ID client
	 * @param AD_Org_ID org
	 */
	@Override
	public void setClientOrg(final int AD_Client_ID, final int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	// setClientOrg


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
		// Set Defaults
		final int paymentTermID = isSOTrx() ? bp.getC_PaymentTerm_ID() : bp.getPO_PaymentTerm_ID();
		if (paymentTermID != 0)
		{
			setC_PaymentTerm_ID(paymentTermID);
		}
		//
		final int priceLisId = isSOTrx() ? bp.getM_PriceList_ID() : bp.getPO_PriceList_ID();
		if (priceLisId != 0)
		{
			setM_PriceList_ID(priceLisId);
		}
		//
		final String ss = bp.getPaymentRule();
		if (!Check.isEmpty(ss, true))
		{
			setPaymentRule(ss);
		}

		// Set Locations
		final MBPartnerLocation[] locs = bp.getLocations(false);
		if (locs != null)
		{
			for (final MBPartnerLocation loc : locs)
			{
				if ((loc.isBillTo() && isSOTrx())
						|| (loc.isPayFrom() && !isSOTrx()))
				{
					setC_BPartner_Location_ID(loc.getC_BPartner_Location_ID());
				}
			}
			// set to first
			if (getC_BPartner_Location_ID() == 0 && locs.length > 0)
			{
				setC_BPartner_Location_ID(locs[0].getC_BPartner_Location_ID());
			}
		}
		if (getC_BPartner_Location_ID() == 0)
		{
			log.error(new BPartnerNoAddressException(bp).getLocalizedMessage()); // TODO: throw exception?
		}

		// Set Contact
		final List<I_AD_User> contacts = bp.getContacts(false);
		if (contacts != null && contacts.size() > 0) 	// get first User
		{
			setAD_User_ID(contacts.get(0).getAD_User_ID());
		}
	}	// setBPartner

	/**
	 * Set Order References
	 *
	 * @param order order
	 */
	public void setOrder(final MOrder order)
	{
		Services.get(IInvoiceBL.class).setFromOrder(this, order);
	}	// setOrder

	/**
	 * Set Shipment References
	 *
	 * @param ship shipment
	 */
	public void setShipment(final MInOut ship)
	{
		if (ship == null)
		{
			return;
		}

		setIsSOTrx(ship.isSOTrx());
		//
		final MBPartner bp = new MBPartner(getCtx(), ship.getC_BPartner_ID(), null);
		setBPartner(bp);
		//
		setAD_User_ID(ship.getAD_User_ID());
		//
		setSendEMail(ship.isSendEMail());
		//
		setPOReference(ship.getPOReference());
		setDescription(ship.getDescription());
		setDateOrdered(ship.getDateOrdered());
		//
		setAD_OrgTrx_ID(ship.getAD_OrgTrx_ID());
		setC_Project_ID(ship.getC_Project_ID());
		setC_Campaign_ID(ship.getC_Campaign_ID());
		setC_Activity_ID(ship.getC_Activity_ID());
		setUser1_ID(ship.getUser1_ID());
		setUser2_ID(ship.getUser2_ID());

		// metas
		final IPOService poService = Services.get(IPOService.class);
		poService.copyValue(ship, this, I_M_InOut.COLUMNNAME_Incoterm);
		poService.copyValue(ship, this, I_M_InOut.COLUMNNAME_IncotermLocation);
		poService.copyValue(ship, this, I_M_InOut.COLUMNNAME_DescriptionBottom);

		poService.copyValue(ship, this, C_Invoice_ISUSE_BPARTNER_ADDRESS);
		poService.copyValue(ship, this, C_Invoice_BPARTNERADDRESS);

		// metas end

		//
		if (ship.getC_Order_ID() != 0)
		{
			setC_Order_ID(ship.getC_Order_ID());
			final MOrder order = new MOrder(getCtx(), ship.getC_Order_ID(), get_TrxName());
			setIsDiscountPrinted(order.isDiscountPrinted());
			setM_PriceList_ID(order.getM_PriceList_ID());
			setIsTaxIncluded(order.isTaxIncluded());
			setC_Currency_ID(order.getC_Currency_ID());
			setC_ConversionType_ID(order.getC_ConversionType_ID());
			setPaymentRule(order.getPaymentRule());
			setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
			//
			final MDocType dt = MDocType.get(getCtx(), order.getC_DocType_ID());
			if (dt.getC_DocTypeInvoice_ID() != 0)
			{
				Services.get(IInvoiceBL.class).setDocTypeTargetIdAndUpdateDescription(this, dt.getC_DocTypeInvoice_ID());
			}
			// Overwrite Invoice BPartner
			setC_BPartner_ID(order.getBill_BPartner_ID());
			// Overwrite Invoice Address
			setC_BPartner_Location_ID(order.getBill_Location_ID());
			// Overwrite Contact
			setAD_User_ID(order.getBill_User_ID());
			//
		}
		// Check if Shipment/Receipt is based on RMA
		if (ship.getM_RMA_ID() != 0)
		{

			final MRMA rma = new MRMA(getCtx(), ship.getM_RMA_ID(), get_TrxName());
			final MOrder rmaOrder = rma.getOriginalOrder();
			setM_RMA_ID(ship.getM_RMA_ID());
			setIsSOTrx(rma.isSOTrx());
			setM_PriceList_ID(rmaOrder.getM_PriceList_ID());
			setIsTaxIncluded(rmaOrder.isTaxIncluded());
			setC_Currency_ID(rmaOrder.getC_Currency_ID());
			setC_ConversionType_ID(rmaOrder.getC_ConversionType_ID());
			setPaymentRule(rmaOrder.getPaymentRule());
			setC_PaymentTerm_ID(rmaOrder.getC_PaymentTerm_ID());

			// Retrieves the invoice DocType
			final MDocType dt = MDocType.get(getCtx(), rma.getC_DocType_ID());
			if (dt.getC_DocTypeInvoice_ID() != 0)
			{
				Services.get(IInvoiceBL.class).setDocTypeTargetIdAndUpdateDescription(this, dt.getC_DocTypeInvoice_ID());
			}
			setC_BPartner_Location_ID(rmaOrder.getBill_Location_ID());
		}

	}	// setShipment

	/**
	 * Get Grand Total
	 *
	 * @param creditMemoAdjusted adjusted for CM (negative)
	 * @return grand total
	 */
	public BigDecimal getGrandTotal(final boolean creditMemoAdjusted)
	{
		if (!creditMemoAdjusted)
		{
			return super.getGrandTotal();
		}
		//
		final BigDecimal amt = getGrandTotal();
		if (isCreditMemo())
		{
			return amt.negate();
		}
		return amt;
	}	// getGrandTotal

	/**
	 * Get Invoice Lines of Invoice
	 *
	 * @param whereClause starting with AND
	 * @return lines
	 */
	private MInvoiceLine[] getLines(final String whereClause)
	{
		String whereClauseFinal = "C_Invoice_ID=? ";
		if (whereClause != null)
		{
			whereClauseFinal += whereClause;
		}
		final List<MInvoiceLine> list = new Query(getCtx(), MInvoiceLine.Table_Name, whereClauseFinal, get_TrxName())
				.setParameters(new Object[] { getC_Invoice_ID() })
				.setOrderBy(MInvoiceLine.COLUMNNAME_Line)
				.list();
		// optimization: link the C_Invoice
		for (final I_C_InvoiceLine invoiceLine : list)
		{
			invoiceLine.setC_Invoice(this);
		}

		return list.toArray(new MInvoiceLine[list.size()]);
	}	// getLines

	/**
	 * Get Invoice Lines
	 *
	 * @param requery
	 * @return lines
	 */
	public MInvoiceLine[] getLines(final boolean requery)
	{
		if (m_lines == null || m_lines.length == 0 || requery)
		{
			m_lines = getLines(null);
		}
		set_TrxName(m_lines, get_TrxName());
		return m_lines;
	}	// getLines

	/**
	 * Get Lines of Invoice
	 *
	 * @return lines
	 */
	public MInvoiceLine[] getLines()
	{
		return getLines(false);
	}	// getLines

	/**
	 * Renumber Lines
	 *
	 * @param step start and step
	 */
	public void renumberLines(final int step)
	{
		int number = step;
		final MInvoiceLine[] lines = getLines(false);
		for (final MInvoiceLine line : lines)
		{
			line.setLine(number);
			line.saveEx();
			number += step;
		}
		m_lines = null;
	}	// renumberLines

	/**
	 * Copy Lines From other Invoice.
	 *
	 * @param otherInvoice invoice
	 * @param counter create counter links
	 * @param setOrder set order links
	 * @return number of lines copied
	 * @deprecated pls use {@link IInvoiceBL#copyLinesFrom(I_C_Invoice, I_C_Invoice, boolean, boolean, boolean)}
	 */
	@Deprecated
	public int copyLinesFrom(final MInvoice otherInvoice, final boolean counter, final boolean setOrder)
	{
		// ts: 04054: moving copyLinesFrom business logic to the implementors of IInvoiceBL
		return Services.get(IInvoiceBL.class).copyLinesFrom(otherInvoice, this, counter, setOrder,
				false); // setInvoiceRef == false
	}	// copyLinesFrom

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
	}	// setReversal

	/**
	 * Is Reversal
	 *
	 * @return reversal
	 */
	public boolean isReversal()
	{
		return m_reversal;
	}	// isReversal

	/**
	 * Get Taxes
	 *
	 * @param requery requery
	 * @return array of taxes
	 */
	public MInvoiceTax[] getTaxes(final boolean requery)
	{
		if (m_taxes != null && !requery)
		{
			return m_taxes;
		}

		final String whereClause = MInvoiceTax.COLUMNNAME_C_Invoice_ID + "=?";
		final List<MInvoiceTax> list = new Query(getCtx(), MInvoiceTax.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { get_ID() })
				.list();
		m_taxes = list.toArray(new MInvoiceTax[list.size()]);
		return m_taxes;
	}	// getTaxes

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
	}	// addDescription

	/**
	 * Is it a Credit Memo?
	 *
	 * @return true if CM
	 * @deprecated Please use {@link IInvoiceBL#isCreditMemo(I_C_Invoice)}
	 */
	@Deprecated
	public boolean isCreditMemo()
	{
		// metas: use our service instead of old code
		return Services.get(IInvoiceBL.class).isCreditMemo(this);
		// MDocType dt = MDocType.get(getCtx(),
		// getC_DocType_ID()==0 ? getC_DocTypeTarget_ID() : getC_DocType_ID());
		// return MDocType.DOCBASETYPE_APCreditMemo.equals(dt.getDocBaseType())
		// || MDocType.DOCBASETYPE_ARCreditMemo.equals(dt.getDocBaseType());
	}	// isCreditMemo

	/**
	 * Set Processed.
	 * Propergate to Lines/Taxes
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
		final String set = "SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE C_Invoice_ID=" + getC_Invoice_ID();
		final int noLine = DB.executeUpdate("UPDATE C_InvoiceLine " + set, get_TrxName());
		final int noTax = DB.executeUpdate("UPDATE C_InvoiceTax " + set, get_TrxName());
		m_lines = null;
		m_taxes = null;
		log.debug(processed + " - Lines=" + noLine + ", Tax=" + noTax);
	}	// setProcessed

	/**
	 * Validate Invoice Pay Schedule
	 *
	 * @return pay schedule is valid
	 */
	public boolean validatePaySchedule()
	{
		final MInvoicePaySchedule[] schedule = MInvoicePaySchedule.getInvoicePaySchedule(getCtx(), getC_Invoice_ID(), 0, get_TrxName());
		log.debug("#" + schedule.length);
		if (schedule.length == 0)
		{
			setIsPayScheduleValid(false);
			return false;
		}
		// Add up due amounts
		BigDecimal total = Env.ZERO;
		for (final MInvoicePaySchedule element : schedule)
		{
			element.setParent(this);
			final BigDecimal due = element.getDueAmt();
			if (due != null)
			{
				total = total.add(due);
			}
		}
		final boolean valid = getGrandTotal().compareTo(total) == 0;
		setIsPayScheduleValid(valid);

		// Update Schedule Lines
		for (final MInvoicePaySchedule element : schedule)
		{
			if (element.isValid() != valid)
			{
				element.setIsValid(valid);
				element.saveEx(get_TrxName());
			}
		}
		return valid;
	}	// validatePaySchedule

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		log.debug("");
		// No Partner Info - set Template
		if (getC_BPartner_ID() == 0)
		{
			setBPartner(MBPartner.getTemplate(getCtx(), getAD_Client_ID()));
		}
		if (getC_BPartner_Location_ID() == 0)
		{
			setBPartner(new MBPartner(getCtx(), getC_BPartner_ID(), null));
		}

		// Price List
		if (getM_PriceList_ID() == 0)
		{
			int ii = Env.getContextAsInt(getCtx(), "#M_PriceList_ID");
			if (ii != 0)
			{
				setM_PriceList_ID(ii);
			}
			else
			{
				final String sql = "SELECT M_PriceList_ID FROM M_PriceList WHERE AD_Client_ID=? AND IsDefault='Y'";
				ii = DB.getSQLValue(null, sql, getAD_Client_ID());
				if (ii != 0)
				{
					setM_PriceList_ID(ii);
				}
			}
		}

		// Currency
		if (getC_Currency_ID() == 0)
		{
			final String sql = "SELECT C_Currency_ID FROM M_PriceList WHERE M_PriceList_ID=?";
			final int ii = DB.getSQLValue(null, sql, getM_PriceList_ID());
			if (ii != 0)
			{
				setC_Currency_ID(ii);
			}
			else
			{
				setC_Currency_ID(Env.getContextAsInt(getCtx(), "#C_Currency_ID"));
			}
		}

		// Default Sales Rep
		// NOTE: we shall not set the SalesRep from context if is not set.
		// This is not a mandatory field, so leave it like it is.

		// Document Type
		if (getC_DocType_ID() == 0)
		 {
			setC_DocType_ID(0);	// make sure it's set to 0
		}
		Services.get(IInvoiceBL.class).setDocTypeTargetIdIfNotSet(this);

		// Payment Term
		if (getC_PaymentTerm_ID() == 0)
		{
			int ii = Env.getContextAsInt(getCtx(), "#C_PaymentTerm_ID");
			if (ii != 0)
			{
				setC_PaymentTerm_ID(ii);
			}
			else
			{
				final String sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE AD_Client_ID=? AND IsDefault='Y'";
				ii = DB.getSQLValue(null, sql, getAD_Client_ID());
				if (ii != 0)
				{
					setC_PaymentTerm_ID(ii);
				}
			}
		}

		// metas
		if ("".equals(get_Value(C_Invoice_INCOTERM)))
		{
			set_Value(C_Invoice_INCOTERMLOCATION, "");
		}
		return true;
	}	// beforeSave

	/**
	 * Before Delete
	 *
	 * @return true if it can be deleted
	 */
	@Override
	protected boolean beforeDelete()
	{
		if (getC_Order_ID() != 0)
		{
			throw new AdempiereException("@CannotDelete@");
		}
		return true;
	}	// beforeDelete

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer("MInvoice[")
				.append(get_ID()).append("-").append(getDocumentNo())
				.append(",GrandTotal=").append(getGrandTotal());
		if (m_lines != null)
		{
			sb.append(" (#").append(m_lines.length).append(")");
		}
		sb.append("]");
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
		final I_C_DocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		final String docTypeName = dt != null ? dt.getName() : null;
		return Joiner.on(" ").skipNulls().join(docTypeName, getDocumentNo());
	}

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
			final String sql = "UPDATE C_InvoiceLine ol"
					+ " SET AD_Org_ID ="
					+ "(SELECT AD_Org_ID"
					+ " FROM C_Invoice o WHERE ol.C_Invoice_ID=o.C_Invoice_ID) "
					+ "WHERE C_Invoice_ID=" + getC_Invoice_ID();
			final int no = DB.executeUpdate(sql, get_TrxName());
			log.debug("Lines -> #" + no);
		}
		return true;
	}	// afterSave

	/**
	 * Set Price List (and Currency) when valid
	 *
	 * @param M_PriceList_ID price list
	 */
	@Override
	public void setM_PriceList_ID(final int M_PriceList_ID)
	{
		final MPriceList pl = MPriceList.get(getCtx(), M_PriceList_ID, null);
		if (pl != null)
		{
			setC_Currency_ID(pl.getC_Currency_ID());
			super.setM_PriceList_ID(M_PriceList_ID);
		}
	}	// setM_PriceList_ID

	/**
	 * Get Allocated Amt in Invoice Currency
	 *
	 * @return pos/neg amount or null.
	 *         metas: null is returned when there are no allocations
	 */
	public BigDecimal getAllocatedAmt()
	{
		// ts: 04054: moving getAllocatedAmt business logic to the implementors of IInvoicePA

		// BigDecimal retValue = null;
		// String sql = "SELECT SUM(currencyConvert(al.Amount+al.DiscountAmt+al.WriteOffAmt,"
		// + "ah.C_Currency_ID, i.C_Currency_ID,ah.DateTrx,COALESCE(i.C_ConversionType_ID,0), al.AD_Client_ID,al.AD_Org_ID)) "
		// + "FROM C_AllocationLine al"
		// + " INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID=ah.C_AllocationHdr_ID)"
		// + " INNER JOIN C_Invoice i ON (al.C_Invoice_ID=i.C_Invoice_ID) "
		// + "WHERE al.C_Invoice_ID=?"
		// + " AND ah.IsActive='Y' AND al.IsActive='Y'";
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		// try
		// {
		// pstmt = DB.prepareStatement(sql, get_TrxName());
		// pstmt.setInt(1, getC_Invoice_ID());
		// rs = pstmt.executeQuery();
		// if (rs.next())
		// {
		// retValue = rs.getBigDecimal(1);
		// }
		// rs.close();
		// pstmt.close();
		// pstmt = null;
		// }
		// catch (SQLException e)
		// {
		// throw new DBException(e, sql);
		// }
		// finally
		// {
		// DB.close(rs, pstmt);
		// rs = null; pstmt = null;
		// }
		// // log.debug("getAllocatedAmt - " + retValue);
		// // ? ROUND(COALESCE(v_AllocatedAmt,0), 2);
		// // metas: tsa: 01955: please let the retValue to be NULL if there were no allocation found!
		// return retValue;
		return Services.get(IAllocationDAO.class).retrieveAllocatedAmt(InterfaceWrapperHelper.create(this, I_C_Invoice.class));
	}	// getAllocatedAmt

	/**
	 * Test Allocation (and set paid flag)
	 *
	 * @return true if updated
	 */
	public boolean testAllocation()
	{
		final boolean ignoreProcessed = false;
		return Services.get(IInvoiceBL.class).testAllocation(this, ignoreProcessed);

		// tsa: 04098: moving getAllocatedAmt business logic to the implementors of IInvoiceBL
		// boolean change = false;
		//
		// if ( isProcessed() ) {
		// BigDecimal alloc = getAllocatedAmt(); // absolute
		// boolean hasAllocations = alloc != null; // metas: tsa: 01955
		// if (alloc == null)
		// alloc = Env.ZERO;
		// BigDecimal total = getGrandTotal();
		// // metas: tsa: begin: 01955:
		// // If is an zero invoice, it has no allocations and the AutoPayZeroAmt is not set
		// // then don't touch the invoice
		// if (total.signum() == 0 && !hasAllocations
		// && !MSysConfig.getBooleanValue("org.compiere.model.MInvoice.AutoPayZeroAmt", true, getAD_Client_ID()) )
		// {
		// // don't touch the IsPaid flag, return not changed
		// return false;
		// }
		// // metas: tsa: end: 01955
		// if (!isSOTrx())
		// total = total.negate();
		// if (isCreditMemo())
		// total = total.negate();
		// boolean test = total.compareTo(alloc) == 0;
		// change = test != isPaid();
		// if (change)
		// setIsPaid(test);
		// log.debug("Paid=" + test
		// + " (" + alloc + "=" + total + ")");
		// }
		//
		// return change;
	}	// testAllocation

	/**
	 * Set Paid Flag for invoices
	 *
	 * @param ctx context
	 * @param C_BPartner_ID if 0 all
	 * @param trxName transaction
	 */
	public static void setIsPaid(final Properties ctx, final int C_BPartner_ID, final String trxName)
	{
		final List<Object> params = new ArrayList<>();
		final StringBuffer whereClause = new StringBuffer("IsPaid='N' AND DocStatus IN ('CO','CL')");
		if (C_BPartner_ID > 1)
		{
			whereClause.append(" AND C_BPartner_ID=?");
			params.add(C_BPartner_ID);
		}
		else
		{
			whereClause.append(" AND AD_Client_ID=?");
			params.add(Env.getAD_Client_ID(ctx));
		}

		final POResultSet<MInvoice> rs = new Query(ctx, MInvoice.Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.scroll();
		int counter = 0;
		try
		{
			while (rs.hasNext())
			{
				final MInvoice invoice = rs.next();
				if (invoice.testAllocation())
				{
					if (invoice.save())
					{
						counter++;
					}
				}
			}
		}
		finally
		{
			DB.close(rs);
		}
		s_log.debug("Updated IsPaid flag for {} invoices.", counter);
		/**/
	}	// setIsPaid

	/**
	 * Get Open Amount.
	 * Used by web interface
	 *
	 * @return Open Amt
	 */
	public BigDecimal getOpenAmt()
	{
		return getOpenAmt(true, null);
	}	// getOpenAmt

	/**
	 * Get Open Amount
	 *
	 * @param creditMemoAdjusted adjusted for CM (negative)
	 * @param paymentDate ignored Payment Date
	 * @return Open Amt
	 */
	public BigDecimal getOpenAmt(final boolean creditMemoAdjusted, final Timestamp paymentDate)
	{
		// ts: 04054: moving OpenAmt business logic to the implementors of IInvoiceBL
		// if (isPaid())
		// return Env.ZERO;
		// //
		// if (m_openAmt == null)
		// {
		// m_openAmt = getGrandTotal();
		// if (paymentDate != null)
		// {
		// // Payment Discount
		// // Payment Schedule
		// }
		// BigDecimal allocated = getAllocatedAmt();
		// if (allocated != null)
		// {
		// allocated = allocated.abs(); // is absolute
		// m_openAmt = m_openAmt.subtract(allocated);
		// }
		// }
		// //
		// if (!creditMemoAdjusted)
		// return m_openAmt;
		// if (isCreditMemo())
		// return m_openAmt.negate();
		// return m_openAmt;

		return Services.get(IAllocationDAO.class).retrieveOpenAmt(this, creditMemoAdjusted);
	}	// getOpenAmt

	/**
	 * Get Document Status
	 *
	 * @return Document Status Clear Text
	 */
	public String getDocStatusName()
	{
		return MRefList.getListName(getCtx(), 131, getDocStatus());
	}	// getDocStatusName

	/**************************************************************************
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
	public File createPDF(final File file)
	{
		final ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.INVOICE, getC_Invoice_ID(), get_TrxName());
		if (re == null)
		{
			return null;
		}
		return re.getPDF(file);
	}	// createPDF

	/**
	 * Get PDF File Name
	 *
	 * @param documentDir directory
	 * @return file name
	 */
	public String getPDFFileName(final String documentDir)
	{
		return getPDFFileName(documentDir, getC_Invoice_ID());
	}	// getPDFFileName

	/**
	 * Get ISO Code of Currency
	 *
	 * @return Currency ISO
	 */
	public String getCurrencyISO()
	{
		return Services.get(ICurrencyDAO.class).getISO_Code(getCtx(), getC_Currency_ID());
	}	// getCurrencyISO

	/**
	 * Get Currency Precision
	 *
	 * @return precision
	 * @deprecated Please use {@link IInvoiceBL#getPrecision(I_C_Invoice)}
	 */
	@Deprecated
	public int getPrecision()
	{
		return Services.get(IInvoiceBL.class).getPrecision(this);
	}	// getPrecision

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
	}	// process

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
		log.debug("invalidateIt - " + toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	// invalidateIt

	/**
	 * Prepare Document
	 *
	 * @return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		log.debug("{}", toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocTypeTarget_ID(), getAD_Org_ID());

		// Lines
		final MInvoiceLine[] lines = getLines(true);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return IDocument.STATUS_Invalid;
		}
		// No Cash Book
		if (PAYMENTRULE_Cash.equals(getPaymentRule())
				&& MCashBook.get(getCtx(), getAD_Org_ID(), getC_Currency_ID()) == null)
		{
			m_processMsg = "@NoCashBook@";
			return IDocument.STATUS_Invalid;
		}

		// Convert/Check DocType
		if (getC_DocType_ID() != getC_DocTypeTarget_ID())
		{
			setC_DocType_ID(getC_DocTypeTarget_ID());
		}
		if (getC_DocType_ID() == 0)
		{
			m_processMsg = "No Document Type";
			return IDocument.STATUS_Invalid;
		}

		// explodeBOM(); // task 09030: we don't really want to explode the BOM, least of all this uncontrolled way after invoice-candidates-way.

		if (!calculateTaxTotal()) 	// setTotals
		{
			m_processMsg = "Error calculating Tax";
			return IDocument.STATUS_Invalid;
		}

		createPaySchedule();

		// Credit Status
		if (isSOTrx() && !isReversal())
		{
			// task FRESH-152
			final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

			final I_C_BPartner partner = InterfaceWrapperHelper.create(getCtx(), getC_BPartner_ID(), I_C_BPartner.class, get_TrxName());
			final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(partner);
			final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
			final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(getC_BPartner_ID(), getDateInvoiced());

			if (Services.get(IBPartnerStatsBL.class).isCreditStopSales(stats, getGrandTotal(true),  getDateInvoiced()))
			{
				throw new AdempiereException("@BPartnerCreditStop@ - @SO_CreditUsed@="
						+ stats.getSOCreditUsed()
						+ ", @SO_CreditLimit@=" + creditLimit);
			}
		}

		// Landed Costs
		if (!isSOTrx())
		{
			for (final MInvoiceLine line : lines)
			{
				final String error = line.allocateLandedCosts();
				if (error != null && error.length() > 0)
				{
					m_processMsg = error;
					return IDocument.STATUS_Invalid;
				}
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		// Add up Amounts
		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}
		return IDocument.STATUS_InProgress;
	}	// prepareIt

	// @formatter:off
//	/**
//	 * Explode non stocked BOM.
//	 * task 09030: we don't really want to explode the BOM, least of all this uncontrolled way after invoice-candidates-way.
//	 */
//	private void explodeBOM ()
//	{
//		String where = "AND IsActive='Y' AND EXISTS "
//			+ "(SELECT * FROM M_Product p WHERE C_InvoiceLine.M_Product_ID=p.M_Product_ID"
//			+ " AND	p.IsBOM='Y' AND p.IsVerified='Y' AND p.IsStocked='N')";
//		//
//		String sql = "SELECT COUNT(*) FROM C_InvoiceLine "
//			+ "WHERE C_Invoice_ID=? " + where;
//		int count = DB.getSQLValueEx(get_TrxName(), sql, getC_Invoice_ID());
//		while (count != 0)
//		{
//			renumberLines (100);
//
//			//	Order Lines with non-stocked BOMs
//			MInvoiceLine[] lines = getLines (where);
//			for (int i = 0; i < lines.length; i++)
//			{
//				MInvoiceLine line = lines[i];
//				MProduct product = MProduct.get (getCtx(), line.getM_Product_ID());
//				log.debug(product.getName());
//				//	New Lines
//				int lineNo = line.getLine ();
//
//				//find default BOM with valid dates and to this product
//				MPPProductBOM bom = MPPProductBOM.get(product, getAD_Org_ID(),getDateInvoiced(), get_TrxName());
//				if(bom != null)
//				{
//					MPPProductBOMLine[] bomlines = bom.getLines(getDateInvoiced());
//					for (int j = 0; j < bomlines.length; j++)
//					{
//						final I_PP_Product_BOMLine bomline = bomlines[j];
//						MInvoiceLine newLine = new MInvoiceLine (this);
//						newLine.setLine (++lineNo);
//						newLine.setM_Product_ID (bomline.getM_Product_ID ());
//						newLine.setC_UOM_ID (bomline.getC_UOM_ID ());
//						newLine.setQty (line.getQtyInvoiced().multiply(
//								bomline.getQtyBOM ()));		//	Invoiced/Entered
//						if (bomline.getDescription () != null)
//							newLine.setDescription (bomline.getDescription ());
//						//
//						newLine.setPrice ();
//						newLine.saveEx (get_TrxName());
//					}
//				}
//
//				/*MProductBOM[] boms = MProductBOM.getBOMLines (product);
//				for (int j = 0; j < boms.length; j++)
//				{
//					MProductBOM bom = boms[j];
//					MInvoiceLine newLine = new MInvoiceLine (this);
//					newLine.setLine (++lineNo);
//					newLine.setM_Product_ID (bom.getProduct().getM_Product_ID(),
//						bom.getProduct().getC_UOM_ID());
//					newLine.setQty (line.getQtyInvoiced().multiply(
//						bom.getBOMQty ()));		//	Invoiced/Entered
//					if (bom.getDescription () != null)
//						newLine.setDescription (bom.getDescription ());
//					//
//					newLine.setPrice ();
//					newLine.save (get_TrxName());
//				}*/
//
//				//	Convert into Comment Line
//				line.setM_Product_ID (0);
//				line.setM_AttributeSetInstance_ID (0);
//				line.setPriceEntered (Env.ZERO);
//				line.setPriceActual (Env.ZERO);
//				line.setPriceLimit (Env.ZERO);
//				line.setPriceList (Env.ZERO);
//				line.setLineNetAmt (Env.ZERO);
//				//
//				String description = product.getName ();
//				if (product.getDescription () != null)
//					description += " " + product.getDescription ();
//				if (line.getDescription () != null)
//					description += " " + line.getDescription ();
//				line.setDescription (description);
//				line.saveEx (get_TrxName());
//			} //	for all lines with BOM
//
//			m_lines = null;
//			count = DB.getSQLValue (get_TrxName(), sql, getC_Invoice_ID ());
//			renumberLines (10);
//		}	//	while count != 0
//	}	//	explodeBOM
	// @formatter:on

	/**
	 * Calculate Tax and Total
	 *
	 * @return true if calculated
	 */
	public boolean calculateTaxTotal()
	{
		final String trxName = get_TrxName();

		log.debug("");

		// Delete Taxes
		DB.executeUpdateEx("DELETE FROM C_InvoiceTax WHERE C_Invoice_ID=" + getC_Invoice_ID(), trxName);
		m_taxes = null;

		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final int taxPrecision = invoiceBL.getPrecision(this);

		// Lines
		BigDecimal totalLines = Env.ZERO;
		final Set<Integer> taxIds = new HashSet<>();
		final MInvoiceLine[] lines = getLines(false);
		for (final MInvoiceLine line : lines)
		{
			totalLines = totalLines.add(line.getLineNetAmt());

			final int taxId = line.getC_Tax_ID();
			if (taxIds.contains(taxId))
			{
				continue;
			}

			final MInvoiceTax iTax = MInvoiceTax.get(line, taxPrecision, false, trxName); // current Tax
			if (iTax == null)
			{
				continue;
			}

			iTax.setIsTaxIncluded(invoiceBL.isTaxIncluded(line));
			if (!iTax.calculateTaxFromLines())
			{
				return false;
			}
			Check.assume(iTax.isActive(), "InvoiceTax shall be active: {}", iTax);
			InterfaceWrapperHelper.save(iTax);
			taxIds.add(taxId);
		}

		// Taxes
		BigDecimal grandTotal = totalLines;
		final MInvoiceTax[] taxes = getTaxes(true);
		for (final MInvoiceTax iTax : taxes)
		{
			final MTax tax = iTax.getTax();
			if (tax.isSummary())
			{
				final MTax[] cTaxes = tax.getChildTaxes(false);	// Multiple taxes
				for (final MTax cTax : cTaxes)
				{
					final boolean taxIncluded = Services.get(IInvoiceBL.class).isTaxIncluded(this, cTax);
					final BigDecimal taxBaseAmt = iTax.getTaxBaseAmt();
					final BigDecimal taxAmt = Services.get(ITaxBL.class).calculateTax(cTax, taxBaseAmt, taxIncluded, taxPrecision);
					//
					final MInvoiceTax newITax = new MInvoiceTax(getCtx(), 0, trxName);
					newITax.setClientOrg(this);
					newITax.setC_Invoice(this);
					newITax.setC_Tax(cTax);
					newITax.setPrecision(taxPrecision);
					newITax.setIsTaxIncluded(taxIncluded);
					newITax.setTaxBaseAmt(taxBaseAmt);
					newITax.setTaxAmt(taxAmt);
					newITax.saveEx(trxName);
					//
					if (!taxIncluded)
					{
						grandTotal = grandTotal.add(taxAmt);
					}
				}

				iTax.setProcessed(false);
				InterfaceWrapperHelper.delete(iTax);
			}
			else
			{
				if (!iTax.isTaxIncluded())
				{
					grandTotal = grandTotal.add(iTax.getTaxAmt());
				}
			}
		}
		//
		setTotalLines(totalLines);
		setGrandTotal(grandTotal);
		return true;
	}	// calculateTaxTotal

	/**
	 * (Re) Create Pay Schedule
	 *
	 * @return true if valid schedule
	 */
	private boolean createPaySchedule()
	{
		if (getC_PaymentTerm_ID() == 0)
		{
			return false;
		}
		final MPaymentTerm pt = new MPaymentTerm(getCtx(), getC_PaymentTerm_ID(), null);
		log.debug(pt.toString());
		return pt.apply(this);		// calls validate pay schedule
	}	// createPaySchedule

	/**
	 * Approve Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
		log.debug("{}", toString());
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
		log.debug("{}", toString());
		setIsApproved(false);
		return true;
	}	// rejectIt

	@Override
	public String completeIt()
	{
		final String result = completeIt0();
		Services.get(IPrepayOrderAllocationBL.class).invoiceAfterCompleteIt(this);

		return result;
	}

	/**
	 * Complete Document
	 *
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt0()
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

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}
		log.debug("Completed: {}", this);
		final StringBuffer info = new StringBuffer();

		// POS supports multiple payments
		boolean fromPOS = false;
		if (getC_Order_ID() > 0)
		{
			fromPOS = getC_Order().getC_POS_ID() > 0;
		}

		// Create Cash
		if (PAYMENTRULE_Cash.equals(getPaymentRule()) && !fromPOS)
		{
			// Modifications for POSterita
			//
			// MCash cash = MCash.get (getCtx(), getAD_Org_ID(),
			// getDateInvoiced(), getC_Currency_ID(), get_TrxName());

			MCash cash;

			final int posId = Env.getContextAsInt(getCtx(), Env.POS_ID);

			if (posId != 0)
			{
				final MPOS pos = new MPOS(getCtx(), posId, get_TrxName());
				final int cashBookId = pos.getC_CashBook_ID();
				cash = MCash.get(getCtx(), cashBookId, getDateInvoiced(), get_TrxName());
			}
			else
			{
				cash = MCash.get(getCtx(), getAD_Org_ID(),
						getDateInvoiced(), getC_Currency_ID(), get_TrxName());
			}

			// End Posterita Modifications

			if (cash == null || cash.get_ID() == 0)
			{
				m_processMsg = "@NoCashBook@";
				return IDocument.STATUS_Invalid;
			}
			final MCashLine cl = new MCashLine(cash);
			cl.setInvoice(this);
			if (!cl.save(get_TrxName()))
			{
				m_processMsg = "Could not save Cash Journal Line";
				return IDocument.STATUS_Invalid;
			}
			info.append("@C_Cash_ID@: " + cash.getName() + " #" + cl.getLine());
			setC_CashLine_ID(cl.getC_CashLine_ID());
		} 	// CashBook

		// Update Order & Match
		int matchInv = 0;
		int matchPO = 0;
		final MInvoiceLine[] lines = getLines(false);
		for (final MInvoiceLine line : lines)
		{
			// Update Order Line
			MOrderLine ol = null;
			if (line.getC_OrderLine_ID() > 0)
			{
				if (isSOTrx() // note: the PO invoices' order lines are updated by the MatchPO business logic
						|| line.getM_Product_ID() <= 0)
				{
					ol = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), get_TrxName());
					if (line.getQtyInvoiced() != null)
					{
						ol.setQtyInvoiced(ol.getQtyInvoiced().add(line.getQtyInvoiced()));
					}

					ol.saveEx(get_TrxName());
				}
				// Order Invoiced Qty updated via Matching Inv-PO
				else if (!isSOTrx()
						&& line.getM_Product_ID() > 0
						&& !isReversal())
				{
					// MatchPO is created also from MInOut when Invoice exists before Shipment
					final BigDecimal matchQty = line.getQtyInvoiced();
					final MMatchPO po = MMatchPO.create(line, null, getDateInvoiced(), matchQty);
					po.saveEx(get_TrxName());
					matchPO++;
				}
			}

			// Update QtyInvoiced RMA Line
			if (line.getM_RMALine_ID() > 0)
			{
				final MRMALine rmaLine = new MRMALine(getCtx(), line.getM_RMALine_ID(), get_TrxName());
				if (rmaLine.getQtyInvoiced() != null)
				{
					rmaLine.setQtyInvoiced(rmaLine.getQtyInvoiced().add(line.getQtyInvoiced()));
				}
				else
				{
					rmaLine.setQtyInvoiced(line.getQtyInvoiced());
				}

				rmaLine.saveEx(get_TrxName());
			}
			//

			// Matching - Inv-Shipment
			// task: 08529 Note that we also do this for SOTrx invoices as of now
			if (line.getM_InOutLine_ID() > 0
					&& line.getM_Product_ID() > 0
					&& !isReversal() // in case of reversal, the job is done by IInvoiceBL.handleReversalForInvoice()
			)
			{
				final boolean matchInvCreated = Services.get(IMatchInvBL.class).createMatchInvBuilder()
						.setContext(this)
						.setC_InvoiceLine(line)
						.setM_InOutLine(line.getM_InOutLine())
						.setDateTrx(getDateInvoiced())
						.setConsiderQtysAlreadyMatched(false) // backward compatibility
						.setAllowQtysOfOppositeSigns(true)// backward compatibility
						.build();
				if (matchInvCreated)
				{
					matchInv++;
				}
			}
		} 	// for all lines
		if (matchInv > 0)
		{
			info.append(" @M_MatchInv_ID@#").append(matchInv).append(" ");
		}
		if (matchPO > 0)
		{
			info.append(" @M_MatchPO_ID@#").append(matchPO).append(" ");
		}

		// verify that we can deal with the invoice's currency
		// Update total revenue and balance / credit limit (reversed on AllocationLine.processIt)
		final BigDecimal invAmt = Services.get(ICurrencyBL.class).convertBase(
				getCtx(),
				getGrandTotal(true), 	// CM adjusted
				getC_Currency_ID(),
				getDateAcct(),
				getC_ConversionType_ID(),
				getAD_Client_ID(),
				getAD_Org_ID());

		if (invAmt == null)
		{
			final I_C_Currency currency = InterfaceWrapperHelper.create(getCtx(), getC_Currency_ID(), I_C_Currency.class, get_TrxName());
			final I_C_Currency currencyTo = Services.get(ICurrencyBL.class).getBaseCurrency(getCtx(), getAD_Client_ID(), getAD_Org_ID());
			final I_C_BPartner bp = getC_BPartner();

			m_processMsg = Services.get(IMsgBL.class).getMsg(getCtx(),
					ERR_NoBaseConversionBetweenCurrencies,
					new Object[] { bp.getName(),
							bp.getValue(),
							currency.getISO_Code(),
							currencyTo.getISO_Code() });

			return IDocument.STATUS_Invalid;
		}

		// Update Project
		if (isSOTrx() && getC_Project_ID() != 0)
		{
			final MProject project = new MProject(getCtx(), getC_Project_ID(), get_TrxName());
			BigDecimal amt = getGrandTotal(true);
			final int C_CurrencyTo_ID = project.getC_Currency_ID();
			if (C_CurrencyTo_ID != getC_Currency_ID())
			{
				amt = Services.get(ICurrencyBL.class).convert(getCtx(), amt, getC_Currency_ID(), C_CurrencyTo_ID,
						getDateAcct(), 0, getAD_Client_ID(), getAD_Org_ID());
			}
			if (amt == null)
			{
				m_processMsg = "Could not convert C_Currency_ID=" + getC_Currency_ID()
						+ " to Project C_Currency_ID=" + C_CurrencyTo_ID;
				return IDocument.STATUS_Invalid;
			}
			BigDecimal newAmt = project.getInvoicedAmt();
			if (newAmt == null)
			{
				newAmt = amt;
			}
			else
			{
				newAmt = newAmt.add(amt);
			}
			log.debug("GrandTotal=" + getGrandTotal(true) + "(" + amt
					+ ") Project " + project.getName()
					+ " - Invoiced=" + project.getInvoicedAmt() + "->" + newAmt);
			project.setInvoicedAmt(newAmt);
			project.saveEx(get_TrxName());
		} 	// project

		// User Validation
		final String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return IDocument.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		// Counter Documents
		final MInvoice counter = createCounterDoc();
		if (counter != null)
		{
			info.append(" - @CounterDoc@: @C_Invoice_ID@=").append(counter.getDocumentNo());
		}

		m_processMsg = info.toString().trim();
		setProcessed(true);
		setDocAction(DOCACTION_Reverse_Correct); // issue #347
		return IDocument.STATUS_Completed;
	}	// completeIt

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo()
	{
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete())
		{
			setDateInvoiced(SystemTime.asTimestamp());  // metas: use SystemTime
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
	 * Create Counter Document
	 *
	 * @return counter invoice
	 */
	private MInvoice createCounterDoc()
	{
		// Is this a counter doc ?
		if (getRef_Invoice_ID() != 0)
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
		final MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(), get_TrxName()); // metas: load BP in transaction
		final int counterAD_Org_ID = bp.getAD_OrgBP_ID_Int();
		if (counterAD_Org_ID == 0)
		{
			return null;
		}

		final MBPartner counterBP = new MBPartner(getCtx(), counterC_BPartner_ID, get_TrxName()); // metas: load BP in transaction
		// MOrgInfo counterOrgInfo = MOrgInfo.get(getCtx(), counterAD_Org_ID);
		log.debug("Counter BP=" + counterBP.getName());

		// Document Type
		int C_DocTypeTarget_ID = 0;
		final MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(), getC_DocType_ID());
		if (counterDT != null)
		{
			log.debug(counterDT.toString());
			if (!counterDT.isCreateCounter() || !counterDT.isValid())
			{
				return null;
			}
			C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
		}
		else	// indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(), getC_DocType_ID());
			log.debug("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
			if (C_DocTypeTarget_ID <= 0)
			{
				return null;
			}
		}

		// Deep Copy
		final MInvoice counter = copyFrom(this, getDateInvoiced(), getDateAcct(),
				C_DocTypeTarget_ID, !isSOTrx(), true, get_TrxName(), true);
		//
		counter.setAD_Org_ID(counterAD_Org_ID);
		// counter.setM_Warehouse_ID(counterOrgInfo.getM_Warehouse_ID());
		//
		counter.setBPartner(counterBP);
		// Refernces (Should not be required
		counter.setSalesRep_ID(getSalesRep_ID());

		// metas
		final IPOService poService = Services.get(IPOService.class);
		poService.copyValue(this, counter, C_Invoice_INCOTERM);
		poService.copyValue(this, counter, C_Invoice_INCOTERMLOCATION);
		poService.copyValue(this, counter, C_Invoice_DESCRIPTION_BOTTOM);
		// metas end

		counter.save(get_TrxName());

		// Update copied lines
		final MInvoiceLine[] counterLines = counter.getLines(true);
		for (final MInvoiceLine counterLine : counterLines)
		{
			counterLine.setClientOrg(counter);
			counterLine.setInvoice(counter);	// copies header values (BP, etc.)
			counterLine.setPrice();
			counterLine.setTax();
			//
			counterLine.save(get_TrxName());
		}

		log.debug(counter.toString());

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
	}	// createCounterDoc

	/**
	 * Void Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		log.debug("{}", toString());
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
			setDocAction(DOCACTION_None);
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
			final MInvoiceLine[] lines = getLines(false);
			for (final MInvoiceLine line : lines)
			{
				final BigDecimal old = line.getQtyInvoiced();
				if (old.compareTo(Env.ZERO) != 0)
				{
					line.setQty(Env.ZERO);
					line.setTaxAmt(Env.ZERO);
					line.setLineNetAmt(Env.ZERO);
					line.setLineTotalAmt(Env.ZERO);
					line.addDescription(Msg.getMsg(getCtx(), "Voided") + " (" + old + ")");
					// Unlink Shipment
					if (line.getM_InOutLine_ID() > 0)
					{
						final I_M_InOutLine ioLine = line.getM_InOutLine();
						ioLine.setIsInvoiced(false);
						InterfaceWrapperHelper.save(ioLine);
						line.setM_InOutLine(null);
					}
					//
					if (line.getC_OrderLine_ID() > 0)
					{
						final I_C_OrderLine ol = line.getC_OrderLine();
						ol.setQtyInvoiced(Env.ZERO);
						InterfaceWrapperHelper.save(ol);
					}
					line.save(get_TrxName());
				}
			}
			addDescription(Msg.getMsg(getCtx(), "Voided"));
			setIsPaid(true);
			setC_Payment_ID(0);
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
	}	// voidIt

	/**
	 * Close Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		log.debug("{}", toString());
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
	}	// closeIt

	/**
	 * Reverse Correction - same date
	 *
	 * @return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		Services.get(IPrepayOrderAllocationBL.class).invoiceBeforeReverseCorrectIt(this);

		log.debug("{}", toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		//
		final MAllocationHdr[] allocations = MAllocationHdr.getOfInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		for (final MAllocationHdr allocation : allocations)
		{
			allocation.setDocAction(IDocument.ACTION_Reverse_Correct);
			allocation.reverseCorrectIt();
			allocation.saveEx(get_TrxName()); // metas: tsa: always use saveEx
		}
		// Reverse/Delete Matching
		if (!isSOTrx())
		{
			// task 08529: don't delete the MMatchInv; there is even accounting done with it! Instead, IInvoiceCand.handleReversalForInvoice() creates a counter matchInv
			// final MMatchInv[] mInv = MMatchInv.getInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
			// for (int i = 0; i < mInv.length; i++)
			// mInv[i].delete(true);

			final MMatchPO[] mPO = MMatchPO.getInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
			for (final MMatchPO element : mPO)
			{
				if (element.getM_InOutLine_ID() == 0)
				{
					element.delete(true);
				}
				else
				{
					element.setC_InvoiceLine(null);
					element.saveEx(get_TrxName()); // metas: tsa: always use saveEx
				}
			}
		}
		//
		load(get_TrxName());	// reload allocation reversal info

		// Deep Copy
		final boolean setOrder = true;
		final boolean counter = false;
		final MInvoice reversal = copyFrom(this, getDateInvoiced(), getDateAcct(), getC_DocType_ID(), isSOTrx(), counter, get_TrxName(), setOrder);
		if (reversal == null)
		{
			m_processMsg = "Could not create Invoice Reversal";
			return false;
		}
		reversal.setReversal(true);

		// Reverse Line Qty
		final MInvoiceLine[] rLines = reversal.getLines(false); // don't requery, work with the reversals that we just created and have have in memory
		for (final MInvoiceLine rLine2 : rLines)
		{
			final MInvoiceLine rLine = rLine2;
			rLine.setQtyEntered(rLine.getQtyEntered().negate());
			rLine.setQtyInvoiced(rLine.getQtyInvoiced().negate());
			rLine.setLineNetAmt(rLine.getLineNetAmt().negate());
			if (rLine.getTaxAmt() != null && rLine.getTaxAmt().compareTo(Env.ZERO) != 0)
			{
				rLine.setTaxAmt(rLine.getTaxAmt().negate());
			}
			if (rLine.getLineTotalAmt() != null && rLine.getLineTotalAmt().compareTo(Env.ZERO) != 0)
			{
				rLine.setLineTotalAmt(rLine.getLineTotalAmt().negate());
			}
			if (!rLine.save(get_TrxName()))
			{
				m_processMsg = "Could not correct Invoice Reversal Line";
				return false;
			}
		}
		reversal.setC_Order_ID(getC_Order_ID());
		reversal.addDescription("{->" + getDocumentNo() + ")");
		// FR1948157
		// metas: we need to set the Reversal_ID, before we process (and other model validators are invoked)
		reversal.setReversal_ID(getC_Invoice_ID());
		//
		if (!reversal.processIt(IDocument.ACTION_Complete))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return false;
		}
		reversal.setC_Payment_ID(0);
		reversal.setIsPaid(true);
		reversal.closeIt();
		reversal.setProcessing(false);
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		InterfaceWrapperHelper.save(reversal);

		m_processMsg = reversal.getDocumentNo();
		//
		addDescription("(" + reversal.getDocumentNo() + "<-)");

		// Clean up Reversed (this)
		final MInvoiceLine[] iLines = getLines(false);
		for (final MInvoiceLine iLine : iLines)
		{
			if (iLine.getM_InOutLine_ID() > 0)
			{
				final I_M_InOutLine ioLine = iLine.getM_InOutLine();
				ioLine.setIsInvoiced(false);
				InterfaceWrapperHelper.save(ioLine);

				// Reconsiliation
				iLine.setM_InOutLine(null);
				InterfaceWrapperHelper.save(iLine);
			}
			if ((isSOTrx() // task 06022: the orderlines for purchase invoices are updated in the MatchPO business logic
					|| iLine.getM_Product_ID() <= 0) // ts: i don't really understand this or-condition, but it is also in completeIt() and i'm convinced it should be in sync
					&& iLine.getC_OrderLine_ID() > 0)
			{
				final I_C_OrderLine ol = iLine.getC_OrderLine();
				// task 09266: not setting the qty to zero, but decreasing it here, the same way it is increased in completeIt()
				ol.setQtyInvoiced(ol.getQtyInvoiced().subtract(iLine.getQtyInvoiced()));
				InterfaceWrapperHelper.save(ol);
			}
		}
		setProcessed(true);
		// FR1948157
		setReversal_ID(reversal.getC_Invoice_ID());
		setDocStatus(DOCSTATUS_Reversed);	// may come from void
		setDocAction(DOCACTION_None);
		setC_Payment_ID(0);
		setIsPaid(true);

		//
		// Create Allocation: allocate the reversal invoice against the original invoice
		final MAllocationHdr alloc = new MAllocationHdr(getCtx(), false, getDateAcct(),
				getC_Currency_ID(),
				Msg.translate(getCtx(), "C_Invoice_ID") + ": " + getDocumentNo() + "/" + reversal.getDocumentNo(),
				get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		alloc.saveEx();  // metas: tsa: always use saveEx
		// if (alloc.save()) // metas: tsa: always use saveEx
		{
			// Amount
			BigDecimal gt = getGrandTotal(true);
			if (!isSOTrx())
			{
				gt = gt.negate();
			}
			// Orig Line
			final MAllocationLine aLine = new MAllocationLine(alloc, gt, Env.ZERO, Env.ZERO, Env.ZERO);
			aLine.setC_Invoice_ID(getC_Invoice_ID());
			aLine.saveEx(); // metas: tsa: always use saveEx
			// Reversal Line
			final MAllocationLine rLine = new MAllocationLine(alloc, gt.negate(), Env.ZERO, Env.ZERO, Env.ZERO);
			rLine.setC_Invoice_ID(reversal.getC_Invoice_ID());
			rLine.saveEx(); // metas: tsa: always use saveEx
			// Process It
			if (alloc.processIt(IDocument.ACTION_Complete))
			 {
				alloc.saveEx(); // metas: tsa: always use saveEx
			}
		}

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		return true;
	}	// reverseCorrectIt

	/**
	 * Reverse Accrual - none
	 *
	 * @return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.debug("{}", toString());
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
	}	// reverseAccrualIt

	/**
	 * Re-activate
	 *
	 * @return false
	 */
	@Override
	public boolean reActivateIt()
	{
		log.debug("{}", toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		return false;
	}	// reActivateIt

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
		// : Grand Total = 123.00 (#1)
		sb.append(": ").append(Msg.translate(getCtx(), "GrandTotal")).append("=").append(getGrandTotal())
				.append(" (#").append(getLines(false).length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
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

	/**
	 *
	 * @param rma
	 */
	public void setRMA(final MRMA rma)
	{
		setM_RMA_ID(rma.getM_RMA_ID());
		setAD_Org_ID(rma.getAD_Org_ID());
		setDescription(rma.getDescription());
		setC_BPartner_ID(rma.getC_BPartner_ID());
		setSalesRep_ID(rma.getSalesRep_ID());

		setGrandTotal(rma.getAmt());
		setIsSOTrx(rma.isSOTrx());
		setTotalLines(rma.getAmt());

		final MInvoice originalInvoice = rma.getOriginalInvoice();

		if (originalInvoice == null)
		{
			throw new IllegalStateException("Not invoiced - RMA: " + rma.getDocumentNo());
		}

		setC_BPartner_Location_ID(originalInvoice.getC_BPartner_Location_ID());
		setAD_User_ID(originalInvoice.getAD_User_ID());
		setC_Currency_ID(originalInvoice.getC_Currency_ID());
		setIsTaxIncluded(originalInvoice.isTaxIncluded());
		setM_PriceList_ID(originalInvoice.getM_PriceList_ID());
		setC_Project_ID(originalInvoice.getC_Project_ID());
		setC_Activity_ID(originalInvoice.getC_Activity_ID());
		setC_Campaign_ID(originalInvoice.getC_Campaign_ID());
		setUser1_ID(originalInvoice.getUser1_ID());
		setUser2_ID(originalInvoice.getUser2_ID());
	}

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 * @deprecated Please use {@link IInvoiceBL#isComplete(I_C_Invoice)}
	 */
	@Deprecated
	public boolean isComplete()
	{
		return Services.get(IInvoiceBL.class).isComplete(this);
	}	// isComplete

}	// MInvoice
