package de.metas.payment.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Payment;
import org.compiere.model.Query;
import org.compiere.model.X_C_DocType;
import org.compiere.util.DB;

import de.metas.adempiere.model.I_C_Invoice;

public class PaymentDAO extends AbstractPaymentDAO
{
	@Override
	public BigDecimal getAvailableAmount(I_C_Payment payment)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);

		final String sql = "SELECT paymentAvailable(?)";
		final BigDecimal amt = DB.getSQLValueBDEx(trxName, sql, payment.getC_Payment_ID());

		// Return zero if null (shall not happen)
		if (amt == null)
		{
			return BigDecimal.ZERO;
		}

		return amt;
	}

	@Override
	public List<I_C_AllocationLine> retrieveAllocationLines(I_C_Payment payment)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);

		final List<I_C_AllocationLine> allocations = new Query(ctx, I_C_AllocationLine.Table_Name, I_C_AllocationLine.COLUMNNAME_C_Payment_ID + " = ? ", trxName)
				.setParameters(payment.getC_Payment_ID())
				.list(I_C_AllocationLine.class);

		return allocations;

	}

	@Override
	public BigDecimal getAllocatedAmt(final I_C_Payment payment)
	{
		if (payment.getC_Charge_ID() > 0)
		{
			return payment.getPayAmt();
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(payment);
		final String sql =
				"SELECT "
						// task 09342/09373: also take into account the payment-writeoff-amount, i.e. "al.PaymentWriteOffAmt"
						+ "SUM(currencyConvert(al.Amount + al.PaymentWriteOffAmt, ah.C_Currency_ID, p.C_Currency_ID, ah.DateTrx, p.C_ConversionType_ID, al.AD_Client_ID, al.AD_Org_ID))"
						+ "FROM C_AllocationLine al"
						+ "   INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID=ah.C_AllocationHdr_ID) "
						+ "   INNER JOIN C_Payment p ON (al.C_Payment_ID=p.C_Payment_ID) "
						+ "WHERE al.C_Payment_ID=?"
						+ "   AND ah.IsActive='Y' AND al.IsActive='Y'";

		final BigDecimal sqlValueBD = DB.getSQLValueBD(trxName,
				sql,
				payment.getC_Payment_ID());
		if (sqlValueBD == null)
		{
			return BigDecimal.ZERO;
		}
		return sqlValueBD;
	}

	@Override
	public void updateDiscountAndPayment(I_C_Payment payment, int c_Invoice_ID, I_C_DocType c_DocType)
	{
		String sql = "SELECT C_BPartner_ID,C_Currency_ID," // 1..2
				+ " invoiceOpen(C_Invoice_ID, ?)," // 3 #1
				+ " invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx " // 4..5 #2/3
				+ "FROM C_Invoice WHERE C_Invoice_ID=?"; // #4
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, c_Invoice_ID);
			pstmt.setTimestamp(2, payment.getDateTrx());
			pstmt.setInt(3, c_Invoice_ID);
			pstmt.setInt(4, c_Invoice_ID);
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

				BigDecimal payAmt = InvoiceOpen.subtract(DiscountAmt);
				if (X_C_DocType.DOCBASETYPE_APCreditMemo.equals(c_DocType.getDocBaseType())
						|| X_C_DocType.DOCBASETYPE_ARCreditMemo.equals(c_DocType.getDocBaseType()))
				{
					if (payAmt.signum() < 0)
					{
						payAmt = payAmt.abs();
					}
				}

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
	}
}
