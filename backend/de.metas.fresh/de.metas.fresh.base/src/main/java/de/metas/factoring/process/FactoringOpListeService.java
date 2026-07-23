/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.factoring.process;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * Loads and aggregates the Factoring OP-Liste export data using {@code IQueryBL}.
 *
 * <p>Fetches all open invoices/credit notes ({@code C_Invoice.OpenAmt != 0}) of
 * factoring-customer BPs ({@code C_BPartner.IsFactoring='Y'}) in the given org and currency,
 * mapped to typed {@link FactoringOpListeDetailRow} rows sorted by BPartner value then
 * invoice date. Aggregates the D/C totals and picks the header contract number from the
 * first detail row.
 */
@Service
public class FactoringOpListeService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final CurrencyRepository currencyRepo;

	public FactoringOpListeService(@NonNull final CurrencyRepository currencyRepo)
	{
		this.currencyRepo = currencyRepo;
	}

	/**
	 * Loads the export data for the given org + currency.
	 *
	 * @return an export-data value object; {@link FactoringOpListeExportData#getDetailRows()}
	 *         is empty when no open invoices match — the caller renders a header-only file
	 *         in that case.
	 * @throws NoFactoringDataException when there is at least one factoring customer but none
	 *         have a {@code FactoringContractNo}/{@code FactoringClientAccountId} set — the
	 *         process wraps this in a user-visible {@code AdempiereException}.
	 */
	@NonNull
	public FactoringOpListeExportData buildExportData(
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId currencyId)
	{
		final CurrencyCode currencyCode = currencyRepo.getCurrencyCodeById(currencyId);
		final String currencyIso = currencyCode.toThreeLetterCode();

		final ImmutableList<FactoringOpListeDetailRow> detailRows = loadDetailRows(orgId, currencyId, currencyIso);

		final String contractNo;
		final String clientAccountId;
		if (detailRows.isEmpty())
		{
			// Header-only file: fall back to the org's first factoring-customer configuration so the
			// filename and header still resolve. If NO factoring customer with config exists, the
			// process class rejects the run before calling this method.
			final I_C_BPartner anyFactoringBp = firstFactoringBpartnerWithConfig(orgId);
			if (anyFactoringBp == null)
			{
				throw new NoFactoringDataException();
			}
			contractNo = anyFactoringBp.getFactoringContractNo();
			clientAccountId = anyFactoringBp.getFactoringClientAccountId();
		}
		else
		{
			contractNo = detailRows.get(0).getContractNo();
			clientAccountId = detailRows.get(0).getClientAccountId();
		}

		final BigDecimal sumD = detailRows.stream()
				.filter(r -> r.getDebitCreditFlag() == FactoringOpListeDetailRow.DebitCreditFlag.D)
				.map(FactoringOpListeDetailRow::getGrandTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		final BigDecimal sumC = detailRows.stream()
				.filter(r -> r.getDebitCreditFlag() == FactoringOpListeDetailRow.DebitCreditFlag.C)
				.map(FactoringOpListeDetailRow::getGrandTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return new FactoringOpListeExportData(
				contractNo,
				clientAccountId,
				currencyIso,
				LocalDate.now(),
				detailRows,
				sumD,
				sumC);
	}

	private ImmutableList<FactoringOpListeDetailRow> loadDetailRows(
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId currencyId,
			@NonNull final String currencyIso)
	{
		final IQueryBuilder<I_C_Invoice> invoiceQuery = queryBL
				.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_AD_Org_ID, orgId.getRepoId())
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_Currency_ID, currencyId.getRepoId())
				.addNotEqualsFilter(I_C_Invoice.COLUMNNAME_OpenAmt, BigDecimal.ZERO);

		// Filter to invoices of factoring customers via a subquery on C_BPartner
		invoiceQuery.addInSubQueryFilter(
				I_C_Invoice.COLUMNNAME_C_BPartner_ID,
				I_C_BPartner.COLUMNNAME_C_BPartner_ID,
				queryBL.createQueryBuilder(I_C_BPartner.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsFactoring, true)
						.create());

		final List<I_C_Invoice> invoices = invoiceQuery
				.orderBy(I_C_Invoice.COLUMNNAME_DateInvoiced)
				.create()
				.list();

		final List<FactoringOpListeDetailRow> rows = new java.util.ArrayList<>(invoices.size());
		for (final I_C_Invoice invoice : invoices)
		{
			final I_C_BPartner bp = InterfaceWrapperHelper.load(invoice.getC_BPartner_ID(), I_C_BPartner.class);
			final I_C_DocType docType = InterfaceWrapperHelper.load(invoice.getC_DocType_ID(), I_C_DocType.class);

			rows.add(FactoringOpListeDetailRow.builder()
					.debitorNo(safeLeft(bp.getValue(), 20))
					.debitorName(safeLeft(bp.getName(), 50))
					.contractNo(bp.getFactoringContractNo())
					.clientAccountId(bp.getFactoringClientAccountId())
					.documentNo(nullSafe(invoice.getDocumentNo()))
					.dateInvoiced(toLocalDate(invoice.getDateInvoiced()))
					.dueDate(toLocalDate(invoice.getDueDate()))
					.currencyIso(currencyIso)
					.grandTotal(nullToZero(invoice.getGrandTotal()).abs())
					.openAmount(nullToZero(invoice.getOpenAmt()).abs())
					.debitCreditFlag(FactoringOpListeDetailRow.DebitCreditFlag.fromDocBaseType(
							nullSafe(docType.getDocBaseType())))
					.build());
		}

		// Sort by BPartner.Value then DateInvoiced
		rows.sort(Comparator
				.comparing(FactoringOpListeDetailRow::getDebitorNo)
				.thenComparing(FactoringOpListeDetailRow::getDateInvoiced));

		return ImmutableList.copyOf(rows);
	}

	@javax.annotation.Nullable
	private I_C_BPartner firstFactoringBpartnerWithConfig(@NonNull final OrgId orgId)
	{
		return queryBL
				.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, orgId.getRepoId())
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsFactoring, true)
				.addNotNull(I_C_BPartner.COLUMNNAME_FactoringContractNo)
				.addNotNull(I_C_BPartner.COLUMNNAME_FactoringClientAccountId)
				.create()
				.first();
	}

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private static String nullSafe(final String s) { return s != null ? s : ""; }
	private static String safeLeft(final String s, final int max) { final String v = nullSafe(s); return v.length() <= max ? v : v.substring(0, max); }
	private static BigDecimal nullToZero(final BigDecimal v) { return v != null ? v : BigDecimal.ZERO; }
	private static LocalDate toLocalDate(final java.sql.Timestamp ts) { return ts != null ? ts.toLocalDateTime().toLocalDate() : LocalDate.now(); }

	/**
	 * Marker exception thrown when {@link #buildExportData} runs against an org that
	 * has no factoring customers with the required config. The JavaProcess translates
	 * this into a user-visible {@code AdempiereException}.
	 */
	public static class NoFactoringDataException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
	}
}
