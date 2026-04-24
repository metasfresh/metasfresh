package de.metas.payment.sepa.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.banking.Bank;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.api.BankRepository;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.PaySelectionLineType;
import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
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
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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

	private final I_C_PaySelection source;
	private final boolean isGroupTransactions;

	public CreateSEPAExportFromPaySelectionCommand(
			@NonNull final org.compiere.model.I_C_PaySelection source,
			final boolean isGroupTransactions)
	{
		this.source = InterfaceWrapperHelper.create(source, I_C_PaySelection.class);
		this.isGroupTransactions = isGroupTransactions;
	}

	public I_SEPA_Export run()
	{
		final I_SEPA_Export header = createExportHeader(source);
		final PaySelectionTrxType paySelectionTrxType = PaySelectionTrxType.ofNullableCode(source.getPaySelectionTrxType());
		final List<I_C_PaySelectionLine> paySelectionLines = paySelectionBL.retrievePaySelectionLines(source);

		if (isGroupTransactions && PaySelectionTrxType.CREDIT_TRANSFER.equals(paySelectionTrxType))
		{
			handleInvoicePaySelectionLines(paySelectionLines, header);
		}
		else
		{
			handleUngroupedPaySelectionLines(paySelectionLines, header);
		}

		return header;
	}

	private I_SEPA_Export_Line createExportLine(@NonNull final I_C_PaySelectionLine line)
	{
		final I_C_Invoice sourceInvoice = invoiceBL.getById(InvoiceId.ofRepoId(line.getC_Invoice_ID()));
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
		final SEPAProtocol sepaProtocol = SEPAProtocol.ofPaySelectionTrxType(paySelectionTrxType);
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
		if (SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02.equals(sepaProtocol) && Check.isBlank(sepaCreditorIdentifier))
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

		save(header);
		return header;
	}

	private void handleInvoicePaySelectionLines(
			@NonNull final List<I_C_PaySelectionLine> paySelectionLines,
			@NonNull final I_SEPA_Export header)
	{
		final Map<InvoicePaySelectionLinesAggregationKey, AggregatedInvoicePaySelectionLines> keyToAggregatedPaySelectionLines = getInvoiceKeyToGroupedPaySelectionLines(paySelectionLines);
		final List<I_C_PaySelectionLine> ungroupedPaySelectionLines = getUngroupedPaySelectionLines(keyToAggregatedPaySelectionLines);

		handleUngroupedPaySelectionLines(ungroupedPaySelectionLines, header);
		handleGroupedPaySelectionLines(keyToAggregatedPaySelectionLines, header);
	}

	private void handleUngroupedPaySelectionLines(
			@NonNull final List<I_C_PaySelectionLine> paySelectionLines,
			@NonNull final I_SEPA_Export header)
	{
		paySelectionLines.stream()
				// No Bank account. Nothing to do.
				.filter(this::hasBusinessPartnerBankAccount)
				.forEach(line -> {
					final I_SEPA_Export_Line exportLine = createExportLine(line);
					exportLine.setSEPA_Export(header);
					save(exportLine);
				});
	}

	private void handleGroupedPaySelectionLines(
			@NonNull final Map<InvoicePaySelectionLinesAggregationKey, AggregatedInvoicePaySelectionLines> sepaExportLineRefs,
			@NonNull final I_SEPA_Export header)
	{
		sepaExportLineRefs
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue().isMultiplePaySelectionLinesGroup())
				.forEach(entry -> {
					final InvoicePaySelectionLinesAggregationKey sepaExportGroupLinesKey = entry.getKey();
					final AggregatedInvoicePaySelectionLines lines = entry.getValue();
					final I_SEPA_Export_Line exportLine = createAndSaveExportLine(sepaExportGroupLinesKey, lines, header);

					lines.forEach(line -> createAndSaveExportLineRef(line, header, exportLine));
				});
	}

	private void createAndSaveExportLineRef(@NonNull final I_C_PaySelectionLine line,
											@NonNull final I_SEPA_Export header,
											@NonNull final I_SEPA_Export_Line exportLine)
	{
		final I_SEPA_Export_Line_Ref ref = newInstance(I_SEPA_Export_Line_Ref.class);

		final I_C_Invoice sourceInvoice = invoiceBL.getById(InvoiceId.ofRepoId(line.getC_Invoice_ID()));
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

		save(ref);
	}

	@NonNull
	private I_SEPA_Export_Line createAndSaveExportLine(@NonNull final CreateSEPAExportFromPaySelectionCommand.InvoicePaySelectionLinesAggregationKey sepaExportGroupLinesKey,
													   @NonNull final AggregatedInvoicePaySelectionLines refList,
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
		exportLine.setDescription(refList.getAggregatedDescription(invoiceBL::getByIds));
		exportLine.setStructuredRemittanceInfo(refList.getAggregatedRemittanceInfo());
		exportLine.setNumberOfReferences(refList.size());

		save(exportLine);

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
				.filter(this::isInvoicePaySelectionLine)
				.collect(Collectors.groupingBy(this::extractPaySelectionLinesKey, LinkedHashMap::new, AggregatedInvoicePaySelectionLines.collect()));
	}

	private boolean hasBusinessPartnerBankAccount(@NonNull final I_C_PaySelectionLine line)
	{
		return BankAccountId.ofRepoIdOrNull(line.getC_BP_BankAccount_ID()) != null;
	}

	private boolean isInvoicePaySelectionLine(@NonNull final I_C_PaySelectionLine line)
	{
		return paySelectionBL.extractType(line) == PaySelectionLineType.Invoice;
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
