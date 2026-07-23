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

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * Loads and aggregates the Factoring OP-Liste export data using {@code IQueryBL}.
 *
 * <p>Domain model:
 * <ul>
 *   <li>The <b>factorer BP</b> ({@code IsFactorer='Y'}, unique per org) carries the tenant's
 *       {@code FactoringContractNo} + {@code FactoringClientAccountId} — these feed the header
 *       row and the filename. This lookup is validated before the invoice query runs;
 *       missing/ambiguous/incomplete configuration aborts the export with a user-visible error.</li>
 *   <li>The <b>factoring customers</b> ({@code IsFactoring='Y'}) are the BPs whose open invoices
 *       feed the detail rows.</li>
 * </ul>
 */
@Service
public class FactoringOpListeService
{
	private static final AdMessageKey MSG_NoFactorer =
			AdMessageKey.of("Factoring_OP_Liste_EXT_NoFactorer");
	private static final AdMessageKey MSG_MultipleFactorers =
			AdMessageKey.of("Factoring_OP_Liste_EXT_MultipleFactorers");
	private static final AdMessageKey MSG_MissingContractNo =
			AdMessageKey.of("Factoring_OP_Liste_EXT_MissingContractNo");
	private static final AdMessageKey MSG_MissingClientAccountId =
			AdMessageKey.of("Factoring_OP_Liste_EXT_MissingClientAccountId");

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final CurrencyRepository currencyRepo;

	public FactoringOpListeService(@NonNull final CurrencyRepository currencyRepo)
	{
		this.currencyRepo = currencyRepo;
	}

	/**
	 * Loads the export data for the given org + currency.
	 *
	 * @return an export-data value object; {@link FactoringOpListeExportData#getDetailRows()}
	 * is empty when no open invoices match — the caller renders a header-only file
	 * in that case.
	 * @throws AdempiereException (with a {@code @key@} marker) when the factorer BP is missing,
	 *                            ambiguous, or has {@code FactoringContractNo}/{@code FactoringClientAccountId}
	 *                            empty.
	 */
	@NonNull
	public FactoringOpListeExportData buildExportData(
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId currencyId)
	{
		final I_C_BPartner factorer = resolveFactorerOrThrow(orgId);

		final CurrencyCode currencyCode = currencyRepo.getCurrencyCodeById(currencyId);
		final String currencyIso = currencyCode.toThreeLetterCode();

		final ImmutableList<FactoringOpListeDetailRow> detailRows =
				loadDetailRows(orgId, currencyId, currencyIso);

		final BigDecimal sumD = detailRows.stream()
				.filter(r -> r.getDebitCreditFlag() == FactoringOpListeDetailRow.DebitCreditFlag.D)
				.map(FactoringOpListeDetailRow::getGrandTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		final BigDecimal sumC = detailRows.stream()
				.filter(r -> r.getDebitCreditFlag() == FactoringOpListeDetailRow.DebitCreditFlag.C)
				.map(FactoringOpListeDetailRow::getGrandTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return new FactoringOpListeExportData(
				factorer.getFactoringContractNo(),
				factorer.getFactoringClientAccountId(),
				currencyIso,
				LocalDate.now(),
				detailRows,
				sumD,
				sumC);
	}

	// -------------------------------------------------------------------------
	// Factorer BP resolution + validation
	// -------------------------------------------------------------------------

	@NonNull
	private I_C_BPartner resolveFactorerOrThrow(@NonNull final OrgId orgId)
	{
		final List<I_C_BPartner> factorers = bpartnerDAO.retrieveFactorerBPartnersForOrg(orgId);

		if (factorers.isEmpty())
		{
			throw new AdempiereException(
					MSG_NoFactorer.toAD_MessageWithMarkers() + " " + orgDAO.getOrgName(orgId))
					.markAsUserValidationError();
		}
		if (factorers.size() > 1)
		{
			final String names = factorers.stream()
					.map(I_C_BPartner::getName)
					.reduce((a, b) -> a + ", " + b)
					.orElse("");
			throw new AdempiereException(
					MSG_MultipleFactorers.toAD_MessageWithMarkers() + " " + orgDAO.getOrgName(orgId) + ": " + names)
					.markAsUserValidationError();
		}

		final I_C_BPartner factorer = factorers.get(0);
		if (Check.isBlank(factorer.getFactoringContractNo()))
		{
			throw new AdempiereException(
					MSG_MissingContractNo.toAD_MessageWithMarkers() + " " + factorer.getName())
					.markAsUserValidationError();
		}
		if (Check.isBlank(factorer.getFactoringClientAccountId()))
		{
			throw new AdempiereException(
					MSG_MissingClientAccountId.toAD_MessageWithMarkers() + " " + factorer.getName())
					.markAsUserValidationError();
		}

		return factorer;
	}

	// -------------------------------------------------------------------------
	// Detail-row load
	// -------------------------------------------------------------------------

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
					.debitorNo(StringUtils.trunc(Strings.nullToEmpty(bp.getValue()), 20))
					.debitorName(StringUtils.trunc(Strings.nullToEmpty(bp.getName()), 50))
					.documentNo(Strings.nullToEmpty(invoice.getDocumentNo()))
					.dateInvoiced(TimeUtil.asLocalDate(invoice.getDateInvoiced()))
					.dueDate(TimeUtil.asLocalDate(invoice.getDueDate()))
					.currencyIso(currencyIso)
					.grandTotal(MoreObjects.firstNonNull(invoice.getGrandTotal(), BigDecimal.ZERO).abs())
					.openAmount(MoreObjects.firstNonNull(invoice.getOpenAmt(), BigDecimal.ZERO).abs())
					.debitCreditFlag(FactoringOpListeDetailRow.DebitCreditFlag.fromDocBaseType(
							Strings.nullToEmpty(docType.getDocBaseType())))
					.build());
		}

		rows.sort(Comparator
				.comparing(FactoringOpListeDetailRow::getDebitorNo)
				.thenComparing(FactoringOpListeDetailRow::getDateInvoiced));

		return ImmutableList.copyOf(rows);
	}

}
