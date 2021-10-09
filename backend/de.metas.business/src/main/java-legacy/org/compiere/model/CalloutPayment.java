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

import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Payment Callouts. org.compiere.model.CalloutPayment.*
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1803316 ] CalloutPayment: use C_Order.Bill_BPartner_ID
 * @author j2garcia - GlobalQSS
 * <li>BF [ 2021745 ] Cannot assign project to payment with charge
 * @version $Id: CalloutPayment.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class CalloutPayment extends CalloutEngine
{
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	/**
	 * Payment_Invoice. when Invoice selected - set C_Currency_ID -
	 * C_BPartner_ID - DiscountAmt = C_Invoice_Discount (ID, DateTrx) - PayAmt =
	 * invoiceOpen (ID) - Discount - WriteOffAmt = 0
	 */
	public String invoice(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final I_C_Payment payment = calloutField.getModel(I_C_Payment.class);
		final I_C_Invoice invoice = payment.getC_Invoice();
		if (invoice == null || invoice.getC_Invoice_ID() <= 0)// assuming it is resetting value
		{
			return NO_ERROR;
		}

		payment.setC_Order(null);
		payment.setC_Charge_ID(0);
		payment.setIsPrepayment(false);
		//
		payment.setDiscountAmt(BigDecimal.ZERO);
		payment.setWriteOffAmt(BigDecimal.ZERO);
		payment.setIsOverUnderPayment(false);
		payment.setOverUnderAmt(BigDecimal.ZERO);
		int C_InvoicePaySchedule_ID = 0;
		if (calloutField.getTabInfoContextAsInt("C_Invoice_ID") == invoice.getC_Invoice_ID()
				&& calloutField.getTabInfoContextAsInt("C_InvoicePaySchedule_ID") > 0)
		{
			C_InvoicePaySchedule_ID = calloutField.getTabInfoContextAsInt("C_InvoicePaySchedule_ID");
		}
		// Payment Date
		Timestamp ts = payment.getDateTrx();
		if (ts == null)
			ts = new Timestamp(System.currentTimeMillis());
		//
		String sql = "SELECT C_BPartner_ID,C_Currency_ID," // 1..2
				+ " invoiceOpen(C_Invoice_ID, ?)," // 3 #1
				+ " invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx " // 4..5 #2/3
				+ "FROM C_Invoice WHERE C_Invoice_ID=?"; // #4
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_InvoicePaySchedule_ID);
			pstmt.setTimestamp(2, ts);
			pstmt.setInt(3, C_InvoicePaySchedule_ID);
			pstmt.setInt(4, invoice.getC_Invoice_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final int bpartnerId = rs.getInt(1);
				payment.setC_BPartner_ID(bpartnerId);

				// Set Invoice Currency
				final int C_Currency_ID = rs.getInt(2);
				payment.setC_Currency_ID(C_Currency_ID);

				//
				BigDecimal InvoiceOpen = rs.getBigDecimal(3); // Set Invoice
				// OPen Amount
				if (InvoiceOpen == null)
				{
					InvoiceOpen = BigDecimal.ZERO;
				}
				BigDecimal DiscountAmt = rs.getBigDecimal(4); // Set Discount
				// Amt
				if (DiscountAmt == null)
				{
					DiscountAmt = BigDecimal.ZERO;
				}

				// 07564
				// In case of a credit memo invoice, if the amount is negative, we have to make is positive
				BigDecimal payAmt = InvoiceOpen.subtract(DiscountAmt);

				final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
				final I_C_DocType invoiceDocType = docTypeDAO.getById(invoice.getC_DocType_ID());

				if (X_C_DocType.DOCBASETYPE_APCreditMemo.equals(invoiceDocType.getDocBaseType())
						|| X_C_DocType.DOCBASETYPE_ARCreditMemo.equals(invoiceDocType.getDocBaseType()))
				{
					if (payAmt.signum() < 0)
					{
						payAmt = payAmt.abs();
					}
				}

				//
				payment.setPayAmt(payAmt);
				payment.setDiscountAmt(DiscountAmt);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return docType(calloutField);
	} // invoice

	// 2008/07/18 Globalqss [ 2021745 ]
	// Deleted project method

	/**
	 * Payment_Charge. - reset - C_BPartner_ID, Invoice, Order, Project,
	 * Discount, WriteOff
	 *
	 * @param ctx      context
	 * @param WindowNo current Window No
	 * @param mTab     Grid Tab
	 * @param mField   Grid Field
	 * @param value    New Value
	 * @return null or error message
	 */
	public String charge(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final I_C_Payment payment = calloutField.getModel(I_C_Payment.class);

		final int C_Charge_ID = payment.getC_Charge_ID();
		if (C_Charge_ID <= 0) // assuming it is resetting value
		{
			return NO_ERROR;
		}

		payment.setC_Invoice(null);
		payment.setC_Order(null);
		// 2008/07/18 Globalqss [ 2021745 ]
		// mTab.setValue ("C_Project_ID", null);
		payment.setIsPrepayment(false);
		//
		payment.setDiscountAmt(BigDecimal.ZERO);
		payment.setWriteOffAmt(BigDecimal.ZERO);
		payment.setIsOverUnderPayment(Boolean.FALSE);
		payment.setOverUnderAmt(BigDecimal.ZERO);

		return NO_ERROR;
	} // charge

	/**
	 * Payment_Document Type. Verify that Document Type (AP/AR) and Invoice
	 * (SO/PO) are in sync
	 */
	public String docType(final ICalloutField calloutField)
	{
		final I_C_Payment payment = calloutField.getModel(I_C_Payment.class);

		final I_C_DocType docType = InterfaceWrapperHelper.load(payment.getC_DocType_ID(), I_C_DocType.class);

		if (docType != null)
		{
			calloutField.putWindowContext("IsSOTrx", docType.isSOTrx());

			final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
					.createPreliminaryDocumentNoBuilder()
					.setNewDocType(docType)
					.setOldDocumentNo(payment.getDocumentNo())
					.setDocumentModel(payment)
					.buildOrNull();

			if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
			{
				payment.setDocumentNo(documentNoInfo.getDocumentNo());
			}
		}

		paymentBL.validateDocTypeIsInSync(payment);
		return NO_ERROR;
	} // docType

	/**
	 * Payment_Amounts. Change of: - IsOverUnderPayment -> set OverUnderAmt to 0 -
	 * C_Currency_ID, C_ConvesionRate_ID -> convert all - PayAmt, DiscountAmt,
	 * WriteOffAmt, OverUnderAmt -> PayAmt make sure that add up to
	 * InvoiceOpenAmt
	 */
	public String amounts(final ICalloutField calloutField)
	{
		if (isCalloutActive()) // assuming it is resetting value
		{
			return NO_ERROR;
		}

		final I_C_Payment payment = calloutField.getModel(I_C_Payment.class);
		paymentBL.updateAmounts(payment, calloutField.getColumnName(), true);
		return NO_ERROR;
	} // amounts
} // CalloutPayment
