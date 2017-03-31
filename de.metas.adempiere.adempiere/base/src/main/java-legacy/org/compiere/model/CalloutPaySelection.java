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
 * Contributor(s): Teo Sarca
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

/**
 * Payment Selection Callouts
 * 
 * @author Jorg Janke
 * @version $Id: CalloutPaySelection.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 * 
 *          globalqss - integrate Teo Sarca bug fix
 *          [ 1623598 ] Payment Selection Line problem when selecting invoice
 */
// TODO: refactor it and move it to de.metas.banking
public class CalloutPaySelection extends CalloutEngine
{
	/**
	 * Payment Selection Line - Payment Amount.
	 * - called from C_PaySelectionLine.PayAmt
	 * - update DifferenceAmt
	 */
	public String payAmt(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final I_C_PaySelectionLine paySelectionLine = calloutField.getModel(I_C_PaySelectionLine.class);

		final I_C_Invoice invoice = paySelectionLine.getC_Invoice();
		if (invoice == null || invoice.getC_Invoice_ID() <= 0)
		{
			return NO_ERROR;
		}

		final BigDecimal payAmt = paySelectionLine.getPayAmt();

		final I_C_PaySelection paySelection = paySelectionLine.getC_PaySelection();
		final Timestamp payDate = paySelection.getPayDate();

		final BigDecimal discountAmt = calculateDiscountAmt(invoice, payAmt, payDate);
		paySelectionLine.setDiscountAmt(discountAmt);

		setDifferenceAmt(paySelectionLine);

		return NO_ERROR;
	}	// payAmt

	private static final BigDecimal calculateDiscountAmt(final I_C_Invoice invoice, final BigDecimal payAmt, final Timestamp payDate)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		final int currencyId = invoice.getC_Currency_ID();
		final int paymentTermId = invoice.getC_PaymentTerm_ID();
		final Timestamp dateInvoiced = invoice.getDateInvoiced();
		final BigDecimal discountAmt = DB.getSQLValueBDEx(trxName,
				"SELECT paymentTermDiscount(?, ?, ?, ?, ?)",
				payAmt, currencyId, paymentTermId, dateInvoiced, payDate);
		if (discountAmt == null)
		{
			return BigDecimal.ZERO;
		}

		return discountAmt;
	}

	public String discountAmt(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final I_C_PaySelectionLine paySelectionLine = calloutField.getModel(I_C_PaySelectionLine.class);
		setDifferenceAmt(paySelectionLine);

		return NO_ERROR;
	}

	private void setDifferenceAmt(final I_C_PaySelectionLine paySelectionLine)
	{
		final BigDecimal OpenAmt = paySelectionLine.getOpenAmt();
		final BigDecimal PayAmt = paySelectionLine.getPayAmt();
		final BigDecimal DiscountAmt = paySelectionLine.getDiscountAmt();
		final BigDecimal DifferenceAmt = OpenAmt.subtract(PayAmt).subtract(DiscountAmt);

		paySelectionLine.setDifferenceAmt(DifferenceAmt);
	}

	/**
	 * Payment Selection Line - Invoice.
	 * - called from C_PaySelectionLine.C_Invoice_ID
	 * - update PayAmt & DifferenceAmt
	 */
	public String invoice(final ICalloutField calloutField)
	{
		// FIXME: refactor it and use de.metas.banking.payment.impl.PaySelectionUpdater. In meantime pls keep in sync.

		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		// get invoice
		final I_C_PaySelectionLine psl = calloutField.getModel(I_C_PaySelectionLine.class);
		final int C_Invoice_ID = psl.getC_Invoice_ID();
		if (C_Invoice_ID <= 0)
		{
			return NO_ERROR;
		}

		final I_C_PaySelection paySelection = psl.getC_PaySelection();
		final int C_BP_BankAccount_ID = paySelection.getC_BP_BankAccount_ID();

		Timestamp PayDate = paySelection.getPayDate();
		if (PayDate == null)
		{
			PayDate = new Timestamp(System.currentTimeMillis());
		}

		BigDecimal OpenAmt = BigDecimal.ZERO;
		BigDecimal DiscountAmt = BigDecimal.ZERO;
		boolean IsSOTrx = false;
		final String sql = "SELECT currencyConvert(invoiceOpen(i.C_Invoice_ID, 0), i.C_Currency_ID,"
				+ "ba.C_Currency_ID, i.DateInvoiced, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID),"
				+ " paymentTermDiscount(i.GrandTotal,i.C_Currency_ID,i.C_PaymentTerm_ID,i.DateInvoiced, ?), i.IsSOTrx "
				+ "FROM C_Invoice_v i, C_BP_BankAccount ba "
				+ "WHERE i.C_Invoice_ID=? AND ba.C_BP_BankAccount_ID=?";	// #1..2
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setTimestamp(1, PayDate);
			pstmt.setInt(2, C_Invoice_ID);
			pstmt.setInt(3, C_BP_BankAccount_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				OpenAmt = rs.getBigDecimal(1);
				DiscountAmt = rs.getBigDecimal(2);
				IsSOTrx = DisplayType.toBoolean(rs.getString(3));
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		psl.setOpenAmt(OpenAmt);
		psl.setPayAmt(OpenAmt.subtract(DiscountAmt));
		psl.setDiscountAmt(DiscountAmt);
		psl.setDifferenceAmt(BigDecimal.ZERO);
		psl.setIsSOTrx(IsSOTrx);
		return NO_ERROR;
	}	// invoice

}	// CalloutPaySelection
