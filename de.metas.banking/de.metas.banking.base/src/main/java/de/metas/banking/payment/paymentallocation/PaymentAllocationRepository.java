package de.metas.banking.payment.paymentallocation;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.compiere.apps.search.FindHelper;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.invoice.InvoiceId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class PaymentAllocationRepository
{
	private static final Logger logger = LogManager.getLogger(PaymentAllocationRepository.class);
	private final CurrencyRepository currencyRepo;

	public PaymentAllocationRepository(
			@NonNull final CurrencyRepository currencyRepo)
	{
		this.currencyRepo = currencyRepo;
	}

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
		final CurrencyId convertToCurrencyId = query.getCurrencyId();
		final CurrencyCode convertToCurrencyCode = convertToCurrencyId != null
				? currencyRepo.getCurrencyCodeById(convertToCurrencyId)
				: null;

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSelectInvoiceToAllocateSql(query, sqlParams);

		return DB.retrieveRows(sql, sqlParams, rs -> retrieveInvoiceRowOrNull(rs, convertToCurrencyCode));
	}

	private static String buildSelectInvoiceToAllocateSql(
			@NonNull final InvoiceToAllocateQuery query,
			@NonNull final List<Object> sqlParams)
	{
		final CurrencyId convertToCurrencyId = query.getCurrencyId();
		final boolean multiCurrency = convertToCurrencyId == null;

		final StringBuilder sql = new StringBuilder();

		//
		// Main query
		sql.append("SELECT * FROM GetOpenInvoices(?, ?, ?, ?, ?, ?, ?)");
		sqlParams.addAll(Arrays.asList(
				query.getBpartnerId(),
				convertToCurrencyId,
				multiCurrency, // multicurrency
				query.getOrgId(),
				query.getDate(),
				null, // C_Invoice_ID
				null // C_Order_ID
		));

		if (!Check.isEmpty(query.getPoReference(), true))
		{
			sql.append(" WHERE ");
			sql.append(FindHelper.buildStringRestriction("POReference", query.getPoReference(), true, sqlParams));
		}

		//
		// Add particular invoices mentioned to be included
		for (final InvoiceId invoiceId : query.getAdditionalInvoiceIdsToInclude())
		{
			sql.append(" UNION ");
			sql.append("SELECT * FROM GetOpenInvoices(?, ?, ?, ?, ?, ?, ?)");
			sqlParams.addAll(Arrays.asList(
					null, // no C_BPartner_ID
					convertToCurrencyId,
					multiCurrency, // multicurrency
					query.getOrgId(),
					query.getDate(),
					invoiceId, // C_Invoice_ID
					null // C_Order_ID
			));
		}

		//
		// Add particular prepay orders mentioned to be included
		for (final OrderId prepayOrderId : query.getAdditionalPrepayOrderIdsToInclude())
		{
			sql.append(" UNION ");
			sql.append("SELECT * FROM GetOpenInvoices(?, ?, ?, ?, ?, ?, ?)");
			sqlParams.addAll(Arrays.asList(
					null, // no C_BPartner_ID
					convertToCurrencyId,
					multiCurrency, // multicurrency
					query.getOrgId(),
					query.getDate(),
					null, // C_Invoice_ID
					prepayOrderId // C_Order_ID
			));
		}

		//
		// Builder the final outer SQL:
		sql.insert(0, "SELECT * FROM ( ").append(") i");

		sql.append(" WHERE i.AD_Client_ID=?");
		sqlParams.add(query.getAdClientId());

		if (!query.getConsiderOnlyInvoiceIds().isEmpty())
		{
			sql.append(" AND ").append(DB.buildSqlList("i.C_Invoice_ID", query.getConsiderOnlyInvoiceIds(), sqlParams));
		}

		if (!query.getExcludeInvoiceIds().isEmpty())
		{
			sql.append(" AND NOT (").append(DB.buildSqlList("i.C_Invoice_ID", query.getExcludeInvoiceIds(), sqlParams)).append(")");
		}

		sql.append(" ORDER BY i.invoiceDate, i.DocNo ");

		return sql.toString();
	}

	private InvoiceToAllocate retrieveInvoiceRowOrNull(
			@NonNull final ResultSet rs,
			@Nullable final CurrencyCode convertedToCurrencyCode) throws SQLException
	{
		final boolean isPaid = false;  // assumed in above query where clause

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

		//
		// Fetch amounts
		// NOTE: we assume those amounts are already CreditMemo adjusted but not AP adjusted
		final CurrencyCode documentCurrencyCode = CurrencyCode.ofThreeLetterCode(rs.getString("iso_code"));
		final Amount grandTotalOrig = retrieveAmount(rs, "orig_total", documentCurrencyCode);
		final Amount grandTotalConv = convertedToCurrencyCode != null
				? retrieveAmount(rs, "conv_total", convertedToCurrencyCode)
				: null;
		final Amount openAmtConv = convertedToCurrencyCode != null
				? retrieveAmount(rs, "conv_open", convertedToCurrencyCode)
				: Amount.zero(documentCurrencyCode);

		// final BigDecimal multiplierAP = rs.getBigDecimal("multiplierap"); // Vendor=-1, Customer=+1
		final BigDecimal multiplierCreditMemo = rs.getBigDecimal("multiplier"); // CreditMemo=-1, Regular Invoice=+1
		final boolean isCreditMemo = multiplierCreditMemo.signum() < 0 || grandTotalConv.signum() < 0; // task 09429: also if grandTotal<0

		if (!isPrePayOrder && grandTotalOrig.signum() == 0 && !isPaid)
		{
			// nothing - i.e. allow zero amount invoices to be visible for allocation
			// see: http://dewiki908/mediawiki/index.php/01955:_Fenster_Zahlung-Zuordnung_zeigt_Gutschrift_nicht_an_%282011080910000037%29
			logger.trace("allowing not paid zero amount invoice: {}", documentNo);
		}
		else if (openAmtConv.signum() == 0)
		{
			return null;
		}

		final Amount discountAmountConv = convertedToCurrencyCode != null
				? retrieveAmount(rs, "discount", convertedToCurrencyCode)
				: Amount.zero(documentCurrencyCode);

		return InvoiceToAllocate.builder()
				.invoiceId(invoiceId)
				.prepayOrderId(prepayOrderId)
				.documentNo(documentNo)
				.bpartnerId(BPartnerId.ofRepoId(rs.getInt("C_BPartner_ID")))
				.bpartnerName(rs.getString("bpartnername"))
				.dateInvoiced(TimeUtil.asLocalDate(rs.getTimestamp("invoicedate")))
				.dateAcct(TimeUtil.asLocalDate(rs.getTimestamp("dateacct"))) // task 09643
				.currencyCode(documentCurrencyCode)
				.grandTotal(grandTotalOrig)
				.grandTotalConverted(grandTotalConv)
				.openAmountConverted(openAmtConv)
				.discountAmountConverted(discountAmountConv)
				// .setMultiplierAP(multiplierAP)
				.creditMemo(isCreditMemo)
				.poReference(rs.getString("POReference"))
				.build();
	}

	private static final Amount retrieveAmount(
			@NonNull final ResultSet rs,
			@NonNull final String columnName,
			@NonNull final CurrencyCode currencyCode) throws SQLException
	{
		final BigDecimal amountBD = rs.getBigDecimal(columnName);
		return amountBD != null
				? Amount.of(amountBD, currencyCode)
				: Amount.zero(currencyCode);
	}

}
