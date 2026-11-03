package de.metas.payment.api.impl;

import de.metas.payment.PaymentId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO extends AbstractPaymentDAO
{
	@Override
	public BigDecimal getAvailableAmount(@NonNull final PaymentId paymentId)
	{
		final BigDecimal amt = DB.getSQLValueBDEx(ITrx.TRXNAME_ThreadInherited,
				"SELECT paymentAvailable(?)",
				paymentId);

		return amt != null ? amt : BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getAllocatedAmt(final I_C_Payment payment)
	{
		final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
		return getAllocatedAmt(paymentId);
	}

	@Override
	public BigDecimal getAllocatedAmt(final PaymentId paymentId)
	{
		final BigDecimal amt = DB.getSQLValueBDEx(ITrx.TRXNAME_ThreadInherited,
				"SELECT paymentAllocatedAmt(?)",
				paymentId);

		return amt != null ? amt : BigDecimal.ZERO;
	}

	@Override
	public void updateDiscountAndPayment(final I_C_Payment payment, final int c_Invoice_ID, final I_C_DocType c_DocType)
	{
		// the 2nd argument of invoiceOpen and the 3rd of invoiceDiscount is the C_InvoicePaySchedule_ID; 0 means the whole invoice, like CalloutPayment.invoice without a pay schedule
		final String sql = "SELECT C_BPartner_ID,C_Currency_ID," // 1..2
				+ " invoiceOpen(C_Invoice_ID, 0)," // 3
				+ " invoiceDiscount(C_Invoice_ID,?,0), IsSOTrx " // 4..5 #1
				+ "FROM C_Invoice WHERE C_Invoice_ID=?"; // #2
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(1, payment.getDateTrx());
			pstmt.setInt(2, c_Invoice_ID);
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
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
