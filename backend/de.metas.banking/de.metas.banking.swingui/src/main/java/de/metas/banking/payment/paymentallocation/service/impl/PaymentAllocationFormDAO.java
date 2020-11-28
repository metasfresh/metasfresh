/*
 * #%L
 * de.metas.banking.swingui
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.banking.payment.paymentallocation.service.impl;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.model.I_C_Payment_Request;
import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.banking.payment.paymentallocation.model.AllocableDocType;
import de.metas.banking.payment.paymentallocation.model.IInvoiceCandidateRow;
import de.metas.banking.payment.paymentallocation.model.IInvoiceRow;
import de.metas.banking.payment.paymentallocation.model.IPaymentRow;
import de.metas.banking.payment.paymentallocation.model.InvoiceCandidateRow;
import de.metas.banking.payment.paymentallocation.model.InvoiceRow;
import de.metas.banking.payment.paymentallocation.model.PaymentAllocationContext;
import de.metas.banking.payment.paymentallocation.model.PaymentRow;
import de.metas.banking.payment.paymentallocation.service.IPaymentAllocationFormDAO;
import de.metas.currency.CurrencyCode;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.search.FindHelper;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PaymentAllocationFormDAO implements IPaymentAllocationFormDAO
{
	private static final transient Logger logger = LogManager.getLogger(PaymentAllocationFormDAO.class);

	@Override
	public List<IPaymentRow> retrievePaymentRows(PaymentAllocationContext context)
	{
		final StringBuilder sql = new StringBuilder();
		final List<Object> sqlParams = new ArrayList<>();

		sql.append("SELECT * FROM GetOpenPayments(?, ?, ?, ?, ?, ?)");
		sqlParams.addAll(Arrays.asList(
				context.getC_BPartner_ID(), context.getC_Currency_ID(), context.isMultiCurrency(), context.getAD_Org_ID(), context.getDate(), null // c_payment_id
		));

		final int filterPaymentId = context.getFilter_Payment_ID();
		if (filterPaymentId > 0)
		{
			sql.append(" WHERE C_Payment_ID=?");
			sqlParams.add(filterPaymentId);
		}

		//
		// Add particular documents mentioned to be included
		for (final int paymentId : context.getDocumentIdsToIncludeWhenQuering(AllocableDocType.Payment))
		{
			sql.append(" UNION ");
			sql.append(" SELECT * FROM GetOpenPayments(?, ?, ?, ?, ?, ?)");
			sqlParams.addAll(Arrays.asList(
					null // don't filter by BPartner context.getC_BPartner_ID()
					, context.getC_Currency_ID(), context.isMultiCurrency(), context.getAD_Org_ID(), context.getDate(), paymentId // c_payment_id
			));
		}

		//
		// Builder the final outer SQL:
		sql.insert(0, "SELECT * FROM ( ")
				.append(") p")
				.append(" WHERE p.AD_Client_ID = ?")
				.append(" ORDER BY p.paymentDate, p.DocNo");
		sqlParams.add(context.getAD_Client_ID());

		// role security
		// sql = new StringBuilder( MRole.getDefault(Env.getCtx(), false) .addAccessSQL( sql.toString(), "p", MRole.SQL_FULLYQUALIFIED, MRole.SQL_RO ));
		// role Security has been removed because addAccessSQL has major problems with Function calls in the FROM section

		final List<IPaymentRow> rows = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final IPaymentRow row = createPaymentRow(rs);
				if (row == null)
				{
					continue;
				}
				rows.add(row);
			}
		}
		catch (final SQLException e)
		{
			logger.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return rows;
	}

	private final IPaymentRow createPaymentRow(final ResultSet rs) throws SQLException
	{
		final BigDecimal openAmtConv = rs.getBigDecimal("conv_open");
		if (openAmtConv == null || openAmtConv.signum() == 0)
		{
			return null;
		}

		final BigDecimal multiplierAP = rs.getBigDecimal("multiplierap");
		final BigDecimal payAmt = rs.getBigDecimal("orig_total");
		final BigDecimal payAmtConv = rs.getBigDecimal("conv_total");

		return PaymentRow.builder()
				.setDocTypeName(rs.getString("doctype"))
				.setC_Payment_ID(rs.getInt("c_payment_id"))
				.setDocumentNo(rs.getString("DocNo"))
				.setC_BPartner_ID(rs.getInt("c_bpartner_id"))
				.setBPartnerName(rs.getString("bpartnername"))
				.setPaymentDate(rs.getTimestamp("paymentdate"))
				.setDateAcct(rs.getTimestamp("dateacct")) // task 09643
				.setCurrencyISOCode(getCurrencyCode(rs, "iso_code"))
				// Amounts
				// NOTE: we assume the amounts were already AP adjusted, so we are converting them back to relative values (i.e. not AP adjusted)
				.setMultiplierAP(multiplierAP)
				.setPayAmt(payAmt.multiply(multiplierAP))
				.setPayAmtConv(payAmtConv.multiply(multiplierAP))
				.setOpenAmtConv(openAmtConv.multiply(multiplierAP))
				//
				.build();
	}

	@Override
	public List<IInvoiceRow> retrieveInvoiceRows(final PaymentAllocationContext context)
	{
		final StringBuilder sql = new StringBuilder();
		final List<Object> sqlParams = new ArrayList<>();

		//
		// Main query
		sql.append("SELECT * FROM GetOpenInvoices(?, ?, ?, ?, ?, ?, ?)");
		sqlParams.addAll(Arrays.asList(
				context.getC_BPartner_ID(), context.getC_Currency_ID(), context.isMultiCurrency(), context.getAD_Org_ID(), context.getDate(), null // C_Invoice_ID
				, null // C_Order_ID
		));

		final String filterPOReference = context.getFilterPOReference();
		if (!Check.isEmpty(filterPOReference, true))
		{
			sql.append(" WHERE ");
			sql.append(FindHelper.buildStringRestriction("POReference", filterPOReference, true, sqlParams));
		}

		//
		// Add particular invoices mentioned to be included
		for (final int invoiceId : context.getDocumentIdsToIncludeWhenQuering(AllocableDocType.Invoice))
		{
			sql.append(" UNION ");
			sql.append("SELECT * FROM GetOpenInvoices(?, ?, ?, ?, ?, ?, ?)");
			sqlParams.addAll(Arrays.asList(
					null // no C_BPartner_ID
					, context.getC_Currency_ID(), context.isMultiCurrency(), context.getAD_Org_ID(), context.getDate(), invoiceId // C_Invoice_ID
					, null // C_Order_ID
			));
		}

		//
		// Add particular invoices mentioned to be included
		for (final int orderId : context.getDocumentIdsToIncludeWhenQuering(AllocableDocType.PrepayOrder))
		{
			sql.append(" UNION ");
			sql.append("SELECT * FROM GetOpenInvoices(?, ?, ?, ?, ?, ?, ?)");
			sqlParams.addAll(Arrays.asList(
					null // no C_BPartner_ID
					, context.getC_Currency_ID(), context.isMultiCurrency(), context.getAD_Org_ID(), context.getDate(), null // C_Invoice_ID
					, orderId // C_Order_ID
			));
		}

		//
		// Builder the final outer SQL:
		sql.insert(0, "SELECT * "
				+ "FROM ( ")
				.append(") i ")
				.append(" WHERE i.AD_Client_ID = ?")
				.append(" ORDER BY i.invoiceDate, i.DocNo ");
		sqlParams.add(context.getAD_Client_ID());
		logger.debug("InvSQL={}", sql);

		// role security
		// sql = new StringBuilder( MRole.getDefault(Env.getCtx(), false) .addAccessSQL( sql.toString(), "i", MRole.SQL_FULLYQUALIFIED, MRole.SQL_RO ) );
		// role Security has been removed because addAccessSQL has major problems with Function calls in the FROM section

		final List<IInvoiceRow> rows = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				final IInvoiceRow row = createInvoiceRow(rs);
				if (row == null)
				{
					continue;
				}
				rows.add(row);
			}
		}
		catch (final SQLException e)
		{
			logger.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return rows;
	}

	private IInvoiceRow createInvoiceRow(final ResultSet rs) throws SQLException
	{
		final boolean isPaid = false;  // assumed in above query where clause

		final int invoiceId;
		final int orderId;
		final boolean isPrePayOrder = DisplayType.toBoolean(rs.getBoolean("isprepayorder")); // is Source document an order?
		if (isPrePayOrder)
		{
			invoiceId = -1;
			// FIXME: NOTE: i know the following is fucked up, but this is how is implemented in "getopeninvoices" database function
			orderId = rs.getInt("c_invoice_id");
		}
		else
		{
			invoiceId = rs.getInt("c_invoice_id");
			orderId = -1;
		}

		final String documentNo = rs.getString("docno");

		//
		// Fetch amounts
		// NOTE: we assume those amounts are already CreditMemo adjusted but not AP adjusted
		final BigDecimal grandTotalOrig = rs.getBigDecimal("orig_total");  // grand total in document's currency
		final BigDecimal grandTotal = rs.getBigDecimal("conv_total");
		BigDecimal openAmt = rs.getBigDecimal("conv_open");
		if (openAmt == null)
		{
			openAmt = BigDecimal.ZERO;
		}

		final BigDecimal multiplierAP = rs.getBigDecimal("multiplierap"); // Vendor=-1, Customer=+1
		final BigDecimal multiplierCreditMemo = rs.getBigDecimal("multiplier"); // CreditMemo=-1, Regular Invoice=+1
		final boolean isCreditMemo = multiplierCreditMemo.signum() < 0 || grandTotal.signum() < 0; // task 09429: also if grandTotal<0

		if (!isPrePayOrder && grandTotalOrig.signum() == 0 && !isPaid)
		{
			// nothing - i.e. allow zero amount invoices to be visible for allocation
			// see: http://dewiki908/mediawiki/index.php/01955:_Fenster_Zahlung-Zuordnung_zeigt_Gutschrift_nicht_an_%282011080910000037%29
			logger.trace("allowing not paid zero amount invoice: " + documentNo);
		}
		else if (openAmt.signum() == 0)
		{
			return null;
		}

		BigDecimal discount = rs.getBigDecimal("discount");
		if (discount == null)
		{
			discount = BigDecimal.ZERO;
		}

		// NOTE: because this takes some time to calculate and it's not always needed, we use a supplier to calculate the value only if needed.
		final Supplier<BigDecimal> paymentRequestAmtSupplier = Suppliers.memoize(() -> {
			try
			{
				return getPaymentRequestSum(invoiceId);
			}
			catch (Exception e)
			{
				// NOTE: we are catching the exception, logging it and return ZERO,
				// because there is a big chance this method will be invoked from UI and we don't want to fail there...
				logger.warn(e.getLocalizedMessage(), e);
				return BigDecimal.ZERO;
			}
		});

		return InvoiceRow.builder()
				.setDocTypeName(rs.getString("doctype"))
				.setC_Invoice_ID(invoiceId)
				.setC_Order_ID(orderId)
				.setDocumentNo(documentNo)
				.setC_BPartner_ID(rs.getInt("C_BPartner_ID"))
				.setBPartnerName(rs.getString("bpartnername"))
				.setDateInvoiced(rs.getTimestamp("invoicedate"))
				.setDateAcct(rs.getTimestamp("dateacct")) // task 09643
				.setCurrencyISOCode(getCurrencyCode(rs, "iso_code"))
				.setGrandTotal(grandTotalOrig)
				.setGrandTotalConv(grandTotal)
				.setOpenAmtConv(openAmt)
				.setDiscount(discount)
				.setPaymentRequestAmt(paymentRequestAmtSupplier)
				.setMultiplierAP(multiplierAP)
				.setCreditMemo(isCreditMemo)
				.setIsPrepayOrder(isPrePayOrder)
				.setPOReference(rs.getString("POReference"))
				.build();
	}

	private CurrencyCode getCurrencyCode(final ResultSet rs, final String columnName) throws SQLException
	{
		final String code = rs.getString(columnName);
		return !Check.isEmpty(code, true)
				? CurrencyCode.ofThreeLetterCode(code)
				: null;
	}

	private final BigDecimal getPaymentRequestSum(final int invoiceId)
	{
		// NOTE: this method is not optimized at all, but it's fine for now because we are not retrieving too much data...

		if (invoiceId <= 0)
		{
			return BigDecimal.ZERO;
		}

		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_None;
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, invoiceId, I_C_Invoice.class, trxName);

		final I_C_Payment_Request paymentRequest = Services.get(IPaymentRequestDAO.class).retrieveSingularRequestOrNull(invoice);
		final BigDecimal amount;
		if (paymentRequest != null)
		{
			amount = paymentRequest.getAmount();
		}
		else
		{
			amount = BigDecimal.ZERO;
		}
		return amount;
	}

	@Override
	public List<IInvoiceCandidateRow> retrieveInvoiceCandidateRows(final PaymentAllocationContext context)
	{
		final Properties ctx = context.getCtx();
		final StringBuilder sql = new StringBuilder("SELECT ic.*, c.ISO_Code, billbp.Name as bpartnername " +
				"FROM C_Invoice_Candidate ic " +
				"INNER JOIN C_BPartner billbp ON billbp.C_BPartner_ID=ic.Bill_BPartner_ID " +
				"INNER JOIN C_Currency c ON c.C_Currency_ID=ic.C_Currency_ID " +
				"WHERE ic.AD_Client_ID = " + Env.getAD_Client_ID(ctx) + " " +
				"AND ic.AD_Org_ID = ? " +
				"AND ic.Bill_BPartner_ID = ? " +
				"AND ic.C_Currency_ID = ? " +
				"AND ic.DateToInvoice = ? " +
				//
				// Filter out invalid invoice candidates
				//
				"AND (COALESCE (QtyToInvoice_Override, QtyToInvoice) <> 0 OR QtyOrdered = 0)" +
				"AND IsToClear = 'N'" +
				"AND Processed = 'N'" +
				"AND IsError = 'N'" +
				"AND IsInDispute = 'N'" +
				"ORDER BY ic.DateInvoiced, COALESCE(ic.DateToInvoice_Override, ic.DateToInvoice) ");
		logger.debug("InvSQL=" + sql.toString());

		// role security
		/*
		 * sql = new StringBuilder( MRole.getDefault(getCtx(), false) .addAccessSQL( sql.toString(), "i", MRole.SQL_FULLYQUALIFIED, MRole.SQL_RO ) );
		 */
		// role Security has been removed because addAccessSQL has major problems with Function calls in the FROM section

		final List<IInvoiceCandidateRow> invoiceCandidateRows = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			DB.setParameters(pstmt, new Object[] {
					context.getAD_Org_ID(), context.getC_BPartner_ID(), context.getC_Currency_ID(), context.getDate()
			});

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final IInvoiceCandidateRow row = createInvoiceCandidateRow(rs);
				if (row == null)
				{
					continue;
				}
				invoiceCandidateRows.add(row);
			}
		}
		catch (final SQLException e)
		{
			logger.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return invoiceCandidateRows;
	}

	private IInvoiceCandidateRow createInvoiceCandidateRow(final ResultSet rs) throws SQLException
	{
		BigDecimal discount = rs.getBigDecimal("discount");
		if (discount == null)
		{
			discount = BigDecimal.ZERO;
		}

		return InvoiceCandidateRow.builder()
				.setC_Invoice_Candidate_ID(rs.getInt("c_invoice_candidate_id"))
				.setBPartnerName(rs.getString("bpartnername"))
				.setDocumentDate(rs.getTimestamp("dateordered"))
				.setDateAcct(rs.getTimestamp("dateacct"))
				.setDateToInvoice(rs.getTimestamp("datetoinvoice"))
				.setDateInvoiced(rs.getTimestamp("dateinvoiced"))
				//
				.setNetAmtToInvoice(rs.getBigDecimal("netamttoinvoice"))
				.setNetAmtInvoiced(rs.getBigDecimal("netamtinvoiced"))
				//
				.setDiscount(discount)
				.setCurrencyISOCode(rs.getString("iso_code"))
				.setInvoiceRuleName(rs.getString("invoicerule"))
				.setHeaderAggregationKey(rs.getString("headeraggregationkey"))
				//
				.build();
	}
}
