/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.payment.paymentallocation;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.apps.search.FindHelper;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class PaymentAllocationRepository
{
	private static final Logger logger = LogManager.getLogger(PaymentAllocationRepository.class);

	public List<InvoiceToAllocate> retrieveInvoicesToAllocate(@NonNull final Set<InvoiceToAllocateQuery> queries)
	{
		Check.assumeNotEmpty(queries, "queries is not empty");

		final List<InvoiceToAllocate> allInvoicesToAllocate = new ArrayList<>();
		final Set<InvoiceId> allInvoiceIds = new HashSet<>();

		for (final InvoiceToAllocateQuery query : queries)
		{
			final InvoiceToAllocateQuery queryEffective = query.toBuilder()
					.excludeInvoiceIds(allInvoiceIds)
					.build();

			final List<InvoiceToAllocate> invoicesToAllocate = retrieveInvoicesToAllocate(queryEffective);
			final Set<InvoiceId> invoiceIds = invoicesToAllocate.stream()
					.map(InvoiceToAllocate::getInvoiceId)
					.collect(ImmutableSet.toImmutableSet());

			allInvoicesToAllocate.addAll(invoicesToAllocate);
			allInvoiceIds.addAll(invoiceIds);
		}

		return allInvoicesToAllocate;
	}

	public List<InvoiceToAllocate> retrieveInvoicesToAllocate(@NonNull final InvoiceToAllocateQuery query)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSelectInvoiceToAllocateSql(query, sqlParams);

		final ZonedDateTime evaluationDate = query.getEvaluationDate();

		return DB.retrieveRows(sql, sqlParams, rs -> retrieveInvoiceToAllocateOrNull(rs, evaluationDate));
	}

	private static String buildSelectInvoiceToAllocateSql(
			@NonNull final InvoiceToAllocateQuery query,
			@NonNull final List<Object> sqlParams)
	{
		final CurrencyId convertToCurrencyId = query.getCurrencyId();
		final boolean multiCurrency = convertToCurrencyId == null;

		final ClientAndOrgId clientAndOrgId = query.getClientAndOrgId();
		final OrgId orgId = clientAndOrgId != null ? clientAndOrgId.getOrgId() : null;

		final StringBuilder sql = new StringBuilder();

		//
		// Main query
		if (query.getBpartnerId() != null)
		{
			sql.append("SELECT * FROM GetOpenInvoices(?, ?, ?, ?, ?, ?, ?)");
			sqlParams.addAll(Arrays.asList(
					query.getBpartnerId(),
					convertToCurrencyId,
					multiCurrency, // multicurrency
					orgId,
					query.getEvaluationDate(),
					null, // C_Invoice_ID
					null // C_Order_ID
			));

			if (!Check.isEmpty(query.getPoReference(), true))
			{
				sql.append(" WHERE ");
				sql.append(FindHelper.buildStringRestriction("POReference", query.getPoReference(), true, sqlParams));
			}
		}

		//
		// Add particular invoices mentioned to be included
		for (final InvoiceId invoiceId : query.getAdditionalInvoiceIdsToInclude())
		{
			if (sql.length() > 0)
			{
				sql.append(" UNION ");
			}

			sql.append("SELECT * FROM GetOpenInvoices(?, ?, ?, ?, ?, ?, ?)");
			sqlParams.addAll(Arrays.asList(
					null, // no C_BPartner_ID
					convertToCurrencyId,
					multiCurrency, // multicurrency
					orgId,
					query.getEvaluationDate(),
					invoiceId, // C_Invoice_ID
					null // C_Order_ID
			));
		}

		//
		// Add particular prepay orders mentioned to be included
		for (final OrderId prepayOrderId : query.getAdditionalPrepayOrderIdsToInclude())
		{
			if (sql.length() > 0)
			{
				sql.append(" UNION ");
			}

			sql.append("SELECT * FROM GetOpenInvoices(?, ?, ?, ?, ?, ?, ?)");
			sqlParams.addAll(Arrays.asList(
					null, // no C_BPartner_ID
					convertToCurrencyId,
					multiCurrency, // multicurrency
					orgId,
					query.getEvaluationDate(),
					null, // C_Invoice_ID
					prepayOrderId // C_Order_ID
			));
		}

		if (sql.length() <= 0)
		{
			return null;
		}

		//
		// Builder the final outer SQL:
		sql.insert(0, "SELECT * FROM ( ").append(") i");

		sql.append(" WHERE TRUE");

		if (clientAndOrgId != null)
		{
			sql.append(" AND i.AD_Client_ID=?");
			sqlParams.add(clientAndOrgId.getClientId());
		}

		if (!query.getExcludeInvoiceIds().isEmpty())
		{
			sql.append(" AND NOT (").append(DB.buildSqlList("i.C_Invoice_ID", query.getExcludeInvoiceIds(), sqlParams)).append(")");
		}

		sql.append(" ORDER BY i.InvoiceDate, i.DocNo ");

		return sql.toString();
	}

	private InvoiceToAllocate retrieveInvoiceToAllocateOrNull(
			@NonNull final ResultSet rs,
			@NonNull final ZonedDateTime evaluationDate) throws SQLException
	{
		final InvoiceId invoiceId;
		final OrderId prepayOrderId;
		final boolean isPrePayOrder = DisplayType.toBoolean(rs.getBoolean("isprepayorder")); // is Source document an order?
		if (isPrePayOrder)
		{
			invoiceId = null;
			// FIXME: NOTE: i know the following is fucked up, but this is how is implemented in "getopeninvoices" database function
			prepayOrderId = OrderId.ofRepoId(rs.getInt("c_invoice_id"));
		}
		else
		{
			invoiceId = InvoiceId.ofRepoId(rs.getInt("c_invoice_id"));
			prepayOrderId = null;
		}

		final String documentNo = rs.getString("docno");
		final DocTypeId docTypeId = DocTypeId.ofRepoId(rs.getInt("C_DocType_ID"));

		//
		// Fetch amounts
		// NOTE: we assume those amounts are already CreditMemo adjusted but not AP adjusted
		final CurrencyCode documentCurrencyCode = CurrencyCode.ofThreeLetterCode(rs.getString("iso_code"));
		final CurrencyCode convertedToCurrencyCode = CurrencyCode.ofThreeLetterCode(rs.getString("ConvertTo_Currency_ISO_Code"));

		final Amount grandTotal = retrieveAmount(rs, "orig_total", documentCurrencyCode);
		final Amount grandTotalConv = retrieveAmount(rs, "conv_total", convertedToCurrencyCode);
		final Amount openAmtConv = retrieveAmount(rs, "conv_open", convertedToCurrencyCode);
		final Amount discountAmountConv = retrieveAmount(rs, "discount", convertedToCurrencyCode);

		final BigDecimal multiplierAP = rs.getBigDecimal("multiplierap"); // Vendor=-1, Customer=+1
		final SOTrx soTrx = multiplierAP.signum() < 0 ? SOTrx.PURCHASE : SOTrx.SALES;

		final BigDecimal multiplierCreditMemo = rs.getBigDecimal("multiplier"); // CreditMemo=-1, Regular Invoice=+1
		final boolean isCreditMemo = multiplierCreditMemo.signum() < 0 || grandTotalConv.signum() < 0; // task 09429: also if grandTotal<0

		final CurrencyConversionTypeId currencyConversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(rs.getInt(I_C_Invoice.COLUMNNAME_C_ConversionType_ID));

		if (!isPrePayOrder && grandTotal.signum() == 0)
		{
			// nothing - i.e. allow zero amount invoices to be visible for allocation
			// see: http://dewiki908/mediawiki/index.php/01955:_Fenster_Zahlung-Zuordnung_zeigt_Gutschrift_nicht_an_%282011080910000037%29
			logger.trace("allowing not paid zero amount invoice: {}", documentNo);
		}
		else if (openAmtConv.signum() == 0)
		{
			return null;
		}

		return InvoiceToAllocate.builder()
				.invoiceId(invoiceId)
				.prepayOrderId(prepayOrderId)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(rs.getInt("AD_Client_ID"), rs.getInt("AD_Org_ID")))
				.documentNo(documentNo)
				.bpartnerId(BPartnerId.ofRepoId(rs.getInt("C_BPartner_ID")))
				.bpartnerName(rs.getString("bpartnername"))
				.dateInvoiced(TimeUtil.asLocalDate(rs.getTimestamp("invoicedate")))
				.dateAcct(TimeUtil.asLocalDate(rs.getTimestamp("dateacct"))) // task 09643
				.documentCurrencyCode(documentCurrencyCode)
				.evaluationDate(evaluationDate)
				.grandTotal(grandTotal)
				.grandTotalConverted(grandTotalConv)
				.openAmountConverted(openAmtConv)
				.discountAmountConverted(discountAmountConv)
				.docTypeId(docTypeId)
				.docBaseType(InvoiceDocBaseType.ofSOTrxAndCreditMemo(soTrx, isCreditMemo))
				.poReference(rs.getString("POReference"))
				.currencyConversionTypeId(currencyConversionTypeId)
				.build();
	}

	private static Amount retrieveAmount(
			@NonNull final ResultSet rs,
			@NonNull final String columnName,
			@NonNull final CurrencyCode currencyCode) throws SQLException
	{
		final BigDecimal amountBD = rs.getBigDecimal(columnName);
		return amountBD != null
				? Amount.of(amountBD, currencyCode)
				: Amount.zero(currencyCode);
	}

	public List<PaymentToAllocate> retrievePaymentsToAllocate(@NonNull final PaymentToAllocateQuery query)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSelectPaymentsToAllocateSql(query, sqlParams);

		return DB.retrieveRows(sql, sqlParams, this::retrievePaymentToAllocateOrNull);
	}

	public ImmutableSet<PaymentId> retrievePaymentIdsToAllocate(final PaymentToAllocateQuery query)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSelectPaymentsToAllocateSql(query, sqlParams);

		final List<PaymentId> paymentIds = DB.retrieveRows(sql, sqlParams, rs -> retrievePaymentId(rs));
		return ImmutableSet.copyOf(paymentIds);
	}

	private String buildSelectPaymentsToAllocateSql(final PaymentToAllocateQuery query, final List<Object> sqlParams)
	{
		final CurrencyId currencyId = query.getCurrencyId();
		final boolean isMultiCurrency = currencyId == null;
		final ClientAndOrgId clientAndOrgId = query.getClientAndOrgId();
		final OrgId orgId = clientAndOrgId != null ? clientAndOrgId.getOrgId() : null;

		final StringBuilder sql = new StringBuilder();

		if (query.getBpartnerId() != null)
		{
			sql.append("SELECT * FROM GetOpenPayments(?, ?, ?, ?, ?, ?)");
			sqlParams.addAll(Arrays.asList(
					query.getBpartnerId(),
					currencyId,
					isMultiCurrency,
					orgId,
					query.getEvaluationDate(),
					null // c_payment_id
			));
		}

		//
		// Add particular documents mentioned to be included
		for (final PaymentId paymentId : query.getAdditionalPaymentIdsToInclude())
		{
			if (sql.length() > 0)
			{
				sql.append("\n UNION \n");
			}
			sql.append("SELECT * FROM GetOpenPayments(?, ?, ?, ?, ?, ?)");
			sqlParams.addAll(Arrays.asList(
					null, // don't filter by BPartner context.getC_BPartner_ID()
					null, // currency
					true, // multiCurrency
					null, // org
					query.getEvaluationDate(),
					paymentId // c_payment_id
			));
		}

		//
		// Builder the final outer SQL:
		sql.insert(0, "SELECT * FROM ( ").append(") p WHERE true");

		if (clientAndOrgId != null)
		{
			sql.append(" AND p.AD_Client_ID = ?");
			sqlParams.add(clientAndOrgId.getClientId());
		}
		sql.append(" ORDER BY p.paymentDate, p.DocNo");

		return sql.toString();
	}

	private PaymentToAllocate retrievePaymentToAllocateOrNull(@NonNull final ResultSet rs) throws SQLException
	{
		// final CurrencyCode documentCurrencyCode = CurrencyCode.ofThreeLetterCode(rs.getString("iso_code"));
		final CurrencyCode convertedToCurrencyCode = CurrencyCode.ofThreeLetterCode(rs.getString("ConvertTo_Currency_ISO_Code"));

		final BigDecimal multiplierAP = rs.getBigDecimal("multiplierap"); // +1=Inbound Payment, -1=Outbound Payment
		final boolean inboundPayment = multiplierAP.signum() > 0;

		// NOTE: we assume the amounts were already AP adjusted, so we are converting them back to relative values (i.e. not AP adjusted)
		// final Amount payAmt = retrieveAmount(rs, "orig_total", documentCurrencyCode).negateIf(!inboundPayment);
		final Amount payAmtConv = retrieveAmount(rs, "conv_total", convertedToCurrencyCode).negateIf(!inboundPayment);
		final Amount openAmtConv = retrieveAmount(rs, "conv_open", convertedToCurrencyCode).negateIf(!inboundPayment);

		return PaymentToAllocate.builder()
				.paymentId(retrievePaymentId(rs))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(rs.getInt("AD_Client_ID"), rs.getInt("AD_Org_ID")))
				.documentNo(rs.getString("DocNo"))
				.bpartnerId(BPartnerId.ofRepoId(rs.getInt("c_bpartner_id")))
				.dateTrx(TimeUtil.asLocalDate(rs.getTimestamp("paymentdate")))
				.dateAcct(TimeUtil.asLocalDate(rs.getTimestamp("dateacct"))) // task 09643
				.payAmt(payAmtConv)
				.openAmt(openAmtConv)
				.paymentDirection(PaymentDirection.ofInboundPaymentFlag(inboundPayment))
				.currencyConversionTypeId(CurrencyConversionTypeId.ofRepoIdOrNull(rs.getInt(I_C_ConversionType.COLUMNNAME_C_ConversionType_ID)))
				.build();
	}

	private static PaymentId retrievePaymentId(final ResultSet rs) throws SQLException
	{
		return PaymentId.ofRepoId(rs.getInt("c_payment_id"));
	}
}
