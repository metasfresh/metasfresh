package de.metas.payment.sepa.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.banking.Bank;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.PaySelectionId;
import de.metas.banking.PaySelectionLineId;
import de.metas.banking.api.BankRepository;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.banking.mandate.DirectDebitMandate;
import de.metas.banking.mandate.DirectDebitMandateRepository;
import de.metas.banking.mandate.DirectDebitMandateType;
import de.metas.banking.mandate.DirectDebitMandatesMap;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.location.LocationId;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.sepa.api.CreatedSEPAExports;
import de.metas.payment.sepa.api.CreatedSEPAExports.CreatedSEPAExport;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.api.SEPAExportLineId;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.model.I_SEPA_Export_Line_Ref;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

/*
 * #%L
 * de.metas.payment.sepa.base
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

class CreateSEPAExportFromPaySelectionCommand
{
	@NonNull private static final AdMessageKey ERR_C_BP_BankAccount_BankNotSet = AdMessageKey.of("de.metas.payment.sepa.C_BP_BankAccount_BankNotSet");
	@NonNull private static final AdMessageKey ERR_C_BP_BankAccount_IBANNotSet = AdMessageKey.of("de.metas.payment.sepa.C_BP_BankAccount_IBANNotSet");
	@NonNull private static final AdMessageKey ERR_C_BP_BankAccount_SEPA_CreditorIdentifierNotSet = AdMessageKey.of("de.metas.payment.sepa.C_BP_BankAccount_SEPA_CreditorIdentifierNotSet");
	@NonNull private static final AdMessageKey ERR_C_Bank_SwiftCodeNotSet = AdMessageKey.of("de.metas.payment.sepa.C_Bank_SwiftCodeNotSet");

	@NonNull private final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
	@NonNull private final IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final BankRepository bankRepo = SpringContextHolder.instance.getBean(BankRepository.class);
	@NonNull private final DirectDebitMandateRepository mandateRepo = SpringContextHolder.instance.getBean(DirectDebitMandateRepository.class);

	@NonNull private final ISEPADocumentDAO sepaDocumentDAO = Services.get(ISEPADocumentDAO.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final I_C_PaySelection source;
	private final boolean isGroupTransactions;
	@Nullable private final SEPAProtocol chosenProtocol;

	public CreateSEPAExportFromPaySelectionCommand(
			@NonNull final org.compiere.model.I_C_PaySelection source,
			final boolean isGroupTransactions,
			@Nullable final SEPAProtocol chosenProtocol)
	{
		this.source = InterfaceWrapperHelper.create(source, I_C_PaySelection.class);
		this.isGroupTransactions = isGroupTransactions;
		this.chosenProtocol = chosenProtocol;
	}

	/**
	 * Creates one export for a credit transfer, and one export per mandate type (scheme) for a direct debit,
	 * because one direct debit file must not mix local instruments.
	 * For a direct debit, it also sets each line's mandate reference and records the collection date as the last use of each mandate.
	 */
	public CreatedSEPAExports run()
	{
		final PaySelectionTrxType paySelectionTrxType = PaySelectionTrxType.ofNullableCode(source.getPaySelectionTrxType());
		final List<I_C_PaySelectionLine> paySelectionLines = paySelectionBL.retrievePaySelectionLines(source);

		if (PaySelectionTrxType.DIRECT_DEBIT.equals(paySelectionTrxType))
		{
			// refuse the whole export before anything is saved
			final ImmutableMap<PaySelectionLineId, DirectDebitMandate> mandatesByLineId = resolveAndCheckMandates(paySelectionLines);
			return createDirectDebitExports(paySelectionLines, mandatesByLineId);
		}

		final I_SEPA_Export header = createExportHeader(source);
		final Map<InvoiceId, I_C_Invoice> invoicesById = loadInvoices(paySelectionLines);

		if (isGroupTransactions && PaySelectionTrxType.CREDIT_TRANSFER.equals(paySelectionTrxType))
		{
			handleInvoicePaySelectionLines(paySelectionLines, invoicesById, header);
		}
		else
		{
			handleUngroupedPaySelectionLines(paySelectionLines, invoicesById, header);
		}

		return CreatedSEPAExports.builder()
				.export(CreatedSEPAExport.withoutMandates(header))
				.build();
	}

	private CreatedSEPAExports createDirectDebitExports(
			@NonNull final List<I_C_PaySelectionLine> paySelectionLines,
			@NonNull final ImmutableMap<PaySelectionLineId, DirectDebitMandate> mandatesByLineId)
	{
		// lines without a bank account are not exported; every other line has a checked mandate
		final Map<DirectDebitMandateType, List<I_C_PaySelectionLine>> linesByScheme = new EnumMap<>(DirectDebitMandateType.class);
		for (final I_C_PaySelectionLine line : paySelectionLines)
		{
			if (!hasBusinessPartnerBankAccount(line))
			{
				continue;
			}
			final DirectDebitMandate mandate = getMandate(mandatesByLineId, line);
			linesByScheme.computeIfAbsent(mandate.getType(), scheme -> new ArrayList<>()).add(line);
		}

		final CreatedSEPAExports.CreatedSEPAExportsBuilder result = CreatedSEPAExports.builder();
		if (linesByScheme.isEmpty())
		{
			// nothing to collect: one export without lines, as for a credit transfer
			result.export(CreatedSEPAExport.withoutMandates(createExportHeader(source)));
			return result.build();
		}

		// load every invoice once: it feeds both the export line and the debtor's address
		final Map<InvoiceId, I_C_Invoice> invoicesById = loadInvoices(linesByScheme.values().stream()
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList()));

		final Set<DirectDebitMandate> usedMandates = new LinkedHashSet<>();
		linesByScheme.forEach((scheme, lines) -> {
			final I_SEPA_Export header = createExportHeader(source);
			final ImmutableMap.Builder<SEPAExportLineId, DirectDebitMandate> mandatesBySepaExportLineId = ImmutableMap.builder();
			final ImmutableMap.Builder<SEPAExportLineId, LocationId> debtorLocationIdsBySepaExportLineId = ImmutableMap.builder();
			for (final I_C_PaySelectionLine line : lines)
			{
				final DirectDebitMandate mandate = getMandate(mandatesByLineId, line);

				final I_C_Invoice invoice = invoicesById.get(InvoiceId.ofRepoId(line.getC_Invoice_ID()));
				final I_SEPA_Export_Line exportLine = createExportLine(line, invoice);
				exportLine.setSEPA_Export(header);
				exportLine.setSEPA_MandateRefNo(mandate.getReference());
				sepaDocumentDAO.save(exportLine);

				final SEPAExportLineId exportLineId = SEPAExportLineId.ofRepoId(exportLine.getSEPA_Export_Line_ID());
				mandatesBySepaExportLineId.put(exportLineId, mandate);
				debtorLocationIdsBySepaExportLineId.put(exportLineId, getDebtorLocationId(invoice));
				usedMandates.add(mandate);
			}

			result.export(CreatedSEPAExport.builder()
					.export(header)
					.scheme(scheme)
					.mandatesBySepaExportLineId(mandatesBySepaExportLineId.build())
					.debtorLocationIdsBySepaExportLineId(debtorLocationIdsBySepaExportLineId.build())
					.build());
		});

		final LocalDate collectionDate = getCollectionDate();
		final ImmutableList<DirectDebitMandate> mandatesWithNewLastUse = usedMandates.stream()
				.map(mandate -> mandate.withUsedOn(collectionDate))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableList.toImmutableList());
		mandateRepo.saveDateLastUsed(mandatesWithNewLastUse);

		return result.build();
	}

	@NonNull
	private static DirectDebitMandate getMandate(
			@NonNull final ImmutableMap<PaySelectionLineId, DirectDebitMandate> mandatesByLineId,
			@NonNull final I_C_PaySelectionLine line)
	{
		final DirectDebitMandate mandate = mandatesByLineId.get(toPaySelectionLineId(line));
		if (mandate == null)
		{
			// the mandate check refuses every line with a bank account but without a mandate
			throw new AdempiereException("No checked direct debit mandate for C_PaySelectionLine_ID=" + line.getC_PaySelectionLine_ID());
		}
		return mandate;
	}

	@NonNull
	private static PaySelectionLineId toPaySelectionLineId(@NonNull final I_C_PaySelectionLine line)
	{
		return PaySelectionLineId.ofRepoId(PaySelectionId.ofRepoId(line.getC_PaySelection_ID()), line.getC_PaySelectionLine_ID());
	}

	/**
	 * @return the pay date of the pay selection, as a date in the time zone of the export org
	 */
	@NonNull
	private LocalDate getCollectionDate()
	{
		return TimeUtil.asLocalDate(source.getPayDate(), orgDAO.getTimeZone(OrgId.ofRepoId(source.getAD_Org_ID())));
	}

	/**
	 * Loads the mandates of all bank accounts of the given lines at once, checks them and returns them per pay selection line.
	 * Lines without a bank account are skipped (they are not exported).
	 *
	 * @throws AdempiereException if a line has no valid mandate
	 */
	private ImmutableMap<PaySelectionLineId, DirectDebitMandate> resolveAndCheckMandates(@NonNull final List<I_C_PaySelectionLine> paySelectionLines)
	{
		final OrgId exportOrgId = OrgId.ofRepoId(source.getAD_Org_ID());
		final List<I_C_PaySelectionLine> linesWithBankAccount = paySelectionLines.stream()
				.filter(this::hasBusinessPartnerBankAccount)
				.collect(ImmutableList.toImmutableList());

		final Set<BankAccountId> bankAccountIds = linesWithBankAccount.stream()
				.map(line -> BankAccountId.ofRepoId(line.getC_BP_BankAccount_ID()))
				.collect(ImmutableSet.toImmutableSet());
		final DirectDebitMandatesMap mandates = mandateRepo.getMandates(bankAccountIds, exportOrgId);

		final ImmutableSet<BPartnerId> partnerIds = linesWithBankAccount.stream()
				.map(line -> BPartnerId.ofRepoId(line.getC_BPartner_ID()))
				.collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<BPartnerId, I_C_BPartner> partnersById = Maps.uniqueIndex(
				bpartnerDAO.retrieveByIds(partnerIds),
				partner -> BPartnerId.ofRepoId(partner.getC_BPartner_ID()));

		final ImmutableMap.Builder<PaySelectionLineId, DirectDebitMandate> mandatesByLineId = ImmutableMap.builder();
		final ImmutableList.Builder<DirectDebitMandateChecker.LineWithMandate> linesWithMandate = ImmutableList.builder();
		for (final I_C_PaySelectionLine line : linesWithBankAccount)
		{
			final BankAccountId bankAccountId = BankAccountId.ofRepoId(line.getC_BP_BankAccount_ID());
			final DirectDebitMandate mandate = mandates.getMandate(bankAccountId).orElse(null);
			if (mandate != null)
			{
				mandatesByLineId.put(toPaySelectionLineId(line), mandate);
			}

			final I_C_BPartner partner = partnersById.get(BPartnerId.ofRepoId(line.getC_BPartner_ID()));
			linesWithMandate.add(DirectDebitMandateChecker.LineWithMandate.builder()
					.partnerValue(partner.getValue())
					.partnerName(partner.getName())
					.iban(extractIBAN(bankAccountDAO.getById(bankAccountId)))
					.mandate(mandate)
					.build());
		}

		final LocalDate collectionDate = getCollectionDate();
		final DirectDebitMandateChecker checker = new DirectDebitMandateChecker();
		final ImmutableList<DirectDebitMandateChecker.DirectDebitMandateProblem> problems = checker.check(linesWithMandate.build(), collectionDate);
		if (!problems.isEmpty())
		{
			throw checker.toException(problems);
		}

		return mandatesByLineId.build();
	}

	/**
	 * @return the bill-to address captured on the line's (completed) invoice, so that later changes of the partner's master data do not change it
	 */
	private LocationId getDebtorLocationId(@NonNull final I_C_Invoice invoice)
	{
		return LocationId.ofRepoId(Check.assumeGreaterThanZero(invoice.getC_BPartner_Location_Value_ID(), I_C_Invoice.COLUMNNAME_C_BPartner_Location_Value_ID));
	}

	/**
	 * Loads the invoices of all given lines with one query; lines without an invoice are skipped.
	 */
	private Map<InvoiceId, I_C_Invoice> loadInvoices(@NonNull final List<I_C_PaySelectionLine> lines)
	{
		return invoiceBL.getByIds(lines.stream()
						.map(line -> InvoiceId.ofRepoIdOrNull(line.getC_Invoice_ID()))
						.filter(Objects::nonNull)
						.collect(ImmutableSet.toImmutableSet()))
				.stream()
				.collect(ImmutableMap.toImmutableMap(invoice -> InvoiceId.ofRepoId(invoice.getC_Invoice_ID()), invoice -> invoice));
	}

	private static I_C_Invoice getInvoice(@NonNull final Map<InvoiceId, I_C_Invoice> invoicesById, @NonNull final I_C_PaySelectionLine line)
	{
		final I_C_Invoice invoice = invoicesById.get(InvoiceId.ofRepoId(line.getC_Invoice_ID()));
		Check.assumeNotNull(invoice, "Invoice of {} exists", line);
		return invoice;
	}

	private I_SEPA_Export_Line createExportLine(@NonNull final I_C_PaySelectionLine line, @NonNull final I_C_Invoice sourceInvoice)
	{
		final String description = sourceInvoice.getDescription();
		final String structuredRemittanceInfo = line.getReference();

		final BankAccount bpBankAccount = bankAccountDAO.getById(BankAccountId.ofRepoId(line.getC_BP_BankAccount_ID()));

		final I_SEPA_Export_Line exportLine = newInstance(I_SEPA_Export_Line.class, line);

		exportLine.setAD_Org_ID(line.getAD_Org_ID());
		exportLine.setAD_Table_ID(getTableId(I_C_PaySelectionLine.class));
		exportLine.setRecord_ID(line.getC_PaySelectionLine_ID());
		exportLine.setAmt(line.getPayAmt());
		exportLine.setC_BP_BankAccount_ID(bpBankAccount.getId().getRepoId());
		exportLine.setC_Currency_ID(bpBankAccount.getCurrencyId().getRepoId());
		exportLine.setC_BPartner_ID(line.getC_BPartner_ID());
		exportLine.setIBAN(extractIBAN(bpBankAccount));

		// task 07789: note that for the CASE of ESR accounts, there is a model validator in de.metas.payment.esr which will
		// set this field
		// exportLine.setOtherAccountIdentification(OtherAccountIdentification);
		final BankId bankId = bpBankAccount.getBankId();
		if (bankId != null)
		{
			final Bank bank = bankRepo.getById(bankId);
			exportLine.setSwiftCode(toNullOrRemoveSpaces(bank.getSwiftCode()));
		}

		exportLine.setDescription(description);
		exportLine.setStructuredRemittanceInfo(structuredRemittanceInfo);

		return exportLine;
	}

	private I_SEPA_Export createExportHeader(@NonNull final I_C_PaySelection paySelectionHeader)
	{
		final PaySelectionTrxType paySelectionTrxType = PaySelectionTrxType.ofNullableCode(paySelectionHeader.getPaySelectionTrxType());
		if (paySelectionTrxType == null)
		{
			throw new FillMandatoryException(false, I_C_PaySelection.COLUMNNAME_PaySelectionTrxType);
		}

		final I_SEPA_Export header = newInstance(I_SEPA_Export.class, paySelectionHeader);
		final SEPAProtocol sepaProtocol = SEPAProtocol.ofPaySelectionTrxType(paySelectionTrxType, chosenProtocol);
		header.setSEPA_Protocol(sepaProtocol.getCode());

		//
		// We need the source org BP.
		final I_C_BPartner orgBP = partnerOrgBL.retrieveLinkedBPartner(paySelectionHeader.getAD_Org_ID());

		final BankAccount bpBankAccount = bankAccountDAO.getById(BankAccountId.ofRepoId(paySelectionHeader.getC_BP_BankAccount_ID()));

		// task 09923: In case the bp bank account does not have a bank set, it cannot be used in a SEPA Export
		final BankId bankId = bpBankAccount.getBankId();
		if (bankId == null)
		{
			throw new AdempiereException(ERR_C_BP_BankAccount_BankNotSet, bpBankAccount);
		}

		// Set corresponding data
		header.setAD_Org_ID(paySelectionHeader.getAD_Org_ID());
		header.setIBAN(extractIBAN(bpBankAccount));
		header.setPaymentDate(paySelectionHeader.getPayDate());
		header.setProcessed(false);
		header.setSEPA_CreditorName(orgBP.getName());

		final String sepaCreditorIdentifier = bpBankAccount.getSEPA_CreditorIdentifier();
		if (!sepaProtocol.isCreditTransfer() && Check.isBlank(sepaCreditorIdentifier))
		{
			throw new AdempiereException(ERR_C_BP_BankAccount_SEPA_CreditorIdentifierNotSet, bpBankAccount.toString());
		}
		if (Check.isNotBlank(sepaCreditorIdentifier))
		{
			header.setSEPA_CreditorIdentifier(sepaCreditorIdentifier);
		}

		final Bank bank = bankRepo.getById(bankId);
		if (Check.isBlank(bank.getSwiftCode()))
		{
			throw new AdempiereException(ERR_C_Bank_SwiftCodeNotSet, bank.getBankName());
		}
		header.setSwiftCode(bank.getSwiftCode());

		final int paySelectionTableID = getTableId(I_C_PaySelection.class);
		header.setAD_Table_ID(paySelectionTableID);
		header.setRecord_ID(paySelectionHeader.getC_PaySelection_ID());

		header.setDescription(paySelectionHeader.getDescription());

		header.setIsExportBatchBookings(paySelectionHeader.isExportBatchBookings());

		sepaDocumentDAO.save(header);
		return header;
	}

	private void handleInvoicePaySelectionLines(
			@NonNull final List<I_C_PaySelectionLine> paySelectionLines,
			@NonNull final Map<InvoiceId, I_C_Invoice> invoicesById,
			@NonNull final I_SEPA_Export header)
	{
		// QR-IBAN (QRR) payments carry a per-invoice QR reference and cannot be aggregated;
		// export each such line individually, exactly as if grouping were off.
		final List<I_C_PaySelectionLine> qrIbanLines = paySelectionLines.stream()
				.filter(this::hasQrIban)
				.collect(ImmutableList.toImmutableList());
		final List<I_C_PaySelectionLine> groupableLines = paySelectionLines.stream()
				.filter(line -> !hasQrIban(line))
				.collect(ImmutableList.toImmutableList());

		handleUngroupedPaySelectionLines(qrIbanLines, invoicesById, header);

		final Map<InvoicePaySelectionLinesAggregationKey, AggregatedInvoicePaySelectionLines> keyToAggregatedPaySelectionLines = getInvoiceKeyToGroupedPaySelectionLines(groupableLines);
		final List<I_C_PaySelectionLine> ungroupedPaySelectionLines = getUngroupedPaySelectionLines(keyToAggregatedPaySelectionLines);

		handleUngroupedPaySelectionLines(ungroupedPaySelectionLines, invoicesById, header);
		handleGroupedPaySelectionLines(keyToAggregatedPaySelectionLines, invoicesById, header);
	}

	private boolean hasQrIban(@NonNull final I_C_PaySelectionLine line)
	{
		final BankAccountId bankAccountId = BankAccountId.ofRepoIdOrNull(line.getC_BP_BankAccount_ID());
		if (bankAccountId == null)
		{
			return false;
		}
		return Check.isNotBlank(bankAccountDAO.getById(bankAccountId).getQR_IBAN());
	}

	private void handleUngroupedPaySelectionLines(
			@NonNull final List<I_C_PaySelectionLine> paySelectionLines,
			@NonNull final Map<InvoiceId, I_C_Invoice> invoicesById,
			@NonNull final I_SEPA_Export header)
	{
		paySelectionLines.stream()
				// No Bank account. Nothing to do.
				.filter(this::hasBusinessPartnerBankAccount)
				.forEach(line -> {
					final I_SEPA_Export_Line exportLine = createExportLine(line, getInvoice(invoicesById, line));
					exportLine.setSEPA_Export(header);
					sepaDocumentDAO.save(exportLine);
				});
	}

	private void handleGroupedPaySelectionLines(
			@NonNull final Map<InvoicePaySelectionLinesAggregationKey, AggregatedInvoicePaySelectionLines> sepaExportLineRefs,
			@NonNull final Map<InvoiceId, I_C_Invoice> invoicesById,
			@NonNull final I_SEPA_Export header)
	{
		sepaExportLineRefs
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue().isMultiplePaySelectionLinesGroup())
				.forEach(entry -> {
					final InvoicePaySelectionLinesAggregationKey sepaExportGroupLinesKey = entry.getKey();
					final AggregatedInvoicePaySelectionLines lines = entry.getValue();
					final I_SEPA_Export_Line exportLine = createAndSaveExportLine(sepaExportGroupLinesKey, lines, invoicesById, header);

					lines.forEach(line -> createAndSaveExportLineRef(line, getInvoice(invoicesById, line), header, exportLine));
				});
	}

	private void createAndSaveExportLineRef(@NonNull final I_C_PaySelectionLine line,
											@NonNull final I_C_Invoice sourceInvoice,
											@NonNull final I_SEPA_Export header,
											@NonNull final I_SEPA_Export_Line exportLine)
	{
		final I_SEPA_Export_Line_Ref ref = newInstance(I_SEPA_Export_Line_Ref.class);

		final String description = sourceInvoice.getDescription();
		final String structuredRemittanceInfo = line.getReference();

		ref.setAD_Org_ID(line.getAD_Org_ID());
		ref.setAD_Table_ID(getTableId(I_C_PaySelectionLine.class));
		ref.setRecord_ID(line.getC_PaySelectionLine_ID());
		ref.setSEPA_Export_ID(header.getSEPA_Export_ID());
		ref.setSEPA_Export_Line_ID(exportLine.getSEPA_Export_Line_ID());
		ref.setDescription(description);
		ref.setStructuredRemittanceInfo(structuredRemittanceInfo);
		ref.setAmt(line.getPayAmt());
		ref.setC_Currency_ID(exportLine.getC_Currency_ID());

		sepaDocumentDAO.save(ref);
	}

	@NonNull
	private I_SEPA_Export_Line createAndSaveExportLine(@NonNull final CreateSEPAExportFromPaySelectionCommand.InvoicePaySelectionLinesAggregationKey sepaExportGroupLinesKey,
													   @NonNull final AggregatedInvoicePaySelectionLines refList,
													   @NonNull final Map<InvoiceId, I_C_Invoice> invoicesById,
													   @NonNull final I_SEPA_Export header)
	{
		final I_SEPA_Export_Line exportLine = newInstance(I_SEPA_Export_Line.class);

		exportLine.setAD_Org_ID(sepaExportGroupLinesKey.getOrgId().getRepoId());
		exportLine.setAmt(refList.getAmt());
		exportLine.setC_BP_BankAccount_ID(sepaExportGroupLinesKey.getBankAccountId().getRepoId());
		exportLine.setC_Currency_ID(sepaExportGroupLinesKey.getCurrencyId().getRepoId());
		exportLine.setC_BPartner_ID(sepaExportGroupLinesKey.getPartnerId().getRepoId());
		exportLine.setIBAN(sepaExportGroupLinesKey.getIban());
		exportLine.setSwiftCode(sepaExportGroupLinesKey.getSwiftCode());
		exportLine.setSEPA_Export_ID(header.getSEPA_Export_ID());
		exportLine.setIsGroupLine(true);
		exportLine.setDescription(refList.getAggregatedDescription(invoiceIds -> invoiceIds.stream()
				.map(invoiceId -> Check.assumeNotNull(invoicesById.get(invoiceId), "Invoice {} is loaded", invoiceId))
				.collect(ImmutableList.toImmutableList())));
		exportLine.setStructuredRemittanceInfo(refList.getAggregatedRemittanceInfo());
		exportLine.setNumberOfReferences(refList.size());

		sepaDocumentDAO.save(exportLine);

		return exportLine;
	}

	@NonNull
	private List<I_C_PaySelectionLine> getUngroupedPaySelectionLines(@NonNull final Map<InvoicePaySelectionLinesAggregationKey, AggregatedInvoicePaySelectionLines> keyToGroupedPaySelectionLines)
	{
		return keyToGroupedPaySelectionLines.values()
				.stream()
				.filter(AggregatedInvoicePaySelectionLines::isSinglePaySelectionLineGroup)
				.flatMap(AggregatedInvoicePaySelectionLines::stream)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Map<InvoicePaySelectionLinesAggregationKey, AggregatedInvoicePaySelectionLines> getInvoiceKeyToGroupedPaySelectionLines(@NonNull final List<I_C_PaySelectionLine> paySelectionLines)
	{
		return paySelectionLines.stream()
				// No Bank account. Nothing to do.
				.filter(this::hasBusinessPartnerBankAccount)
				.collect(Collectors.groupingBy(this::extractPaySelectionLinesKey, LinkedHashMap::new, AggregatedInvoicePaySelectionLines.collect()));
	}

	private boolean hasBusinessPartnerBankAccount(@NonNull final I_C_PaySelectionLine line)
	{
		return BankAccountId.ofRepoIdOrNull(line.getC_BP_BankAccount_ID()) != null;
	}

	private static @Nullable String toNullOrRemoveSpaces(@Nullable final String from)
	{
		final String fromNorm = StringUtils.trimBlankToNull(from);
		return fromNorm != null ? fromNorm.replace(" ", "") : null;
	}

	@NonNull
	private static String extractIBAN(@NonNull final BankAccount bpBankAccount)
	{
		final String iban = coalesceSuppliers(
				() -> toNullOrRemoveSpaces(bpBankAccount.getIBAN()),
				() -> toNullOrRemoveSpaces(bpBankAccount.getQR_IBAN())
		);
		if (Check.isBlank(iban))
		{
			throw new AdempiereException(ERR_C_BP_BankAccount_IBANNotSet, bpBankAccount);
		}
		return iban;
	}

	@NonNull
	private CreateSEPAExportFromPaySelectionCommand.InvoicePaySelectionLinesAggregationKey extractPaySelectionLinesKey(@NonNull final I_C_PaySelectionLine paySelectionLine)
	{
		final BankAccountId bankAccountId = BankAccountId.ofRepoId(paySelectionLine.getC_BP_BankAccount_ID());
		final BankAccount bpBankAccount = bankAccountDAO.getById(bankAccountId);

		return InvoicePaySelectionLinesAggregationKey.builder()
				.orgId(OrgId.ofRepoId(paySelectionLine.getAD_Org_ID()))
				.partnerId(BPartnerId.ofRepoId(paySelectionLine.getC_BPartner_ID()))
				.bankAccountId(bankAccountId)
				.currencyId(bpBankAccount.getCurrencyId())
				.iban(extractIBAN(bpBankAccount))
				.swiftCode(extractSwiftCode(bpBankAccount))
				.build();
	}

	private @Nullable String extractSwiftCode(@NonNull final BankAccount bpBankAccount)
	{
		return Optional.ofNullable(bpBankAccount.getBankId())
				.map(bankRepo::getById)
				.map(bank -> toNullOrRemoveSpaces(bank.getSwiftCode()))
				.orElse(null);
	}

	@Value
	@Builder
	private static class InvoicePaySelectionLinesAggregationKey
	{
		@NonNull OrgId orgId;
		@NonNull BPartnerId partnerId;
		@NonNull BankAccountId bankAccountId;
		@NonNull CurrencyId currencyId;
		@NonNull String iban;
		@Nullable String swiftCode;
	}
}
