package de.metas.payment.sepa.api.impl;

import de.metas.banking.Bank;
import de.metas.banking.BankId;
import de.metas.banking.api.BankRepository;
import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.i18n.AdMessageKey;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.interfaces.I_C_BP_BankAccount;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static org.adempiere.model.InterfaceWrapperHelper.create;
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
	private static final AdMessageKey ERR_C_BP_BankAccount_BankNotSet = AdMessageKey.of("de.metas.payment.sepa.C_BP_BankAccount_BankNotSet");
	private static final AdMessageKey ERR_C_BP_BankAccount_SEPA_CreditorIdentifierNotSet = AdMessageKey.of("de.metas.payment.sepa.C_BP_BankAccount_SEPA_CreditorIdentifierNotSet");
	private static final AdMessageKey ERR_C_Bank_SwiftCodeNotSet = AdMessageKey.of("de.metas.payment.sepa.C_Bank_SwiftCodeNotSet");

	private final IPaymentDAO paymentsRepo = Services.get(IPaymentDAO.class);
	private final IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final BankRepository bankRepo = SpringContextHolder.instance.getBean(BankRepository.class);

	private final I_C_PaySelection source;

	public CreateSEPAExportFromPaySelectionCommand(@NonNull final org.compiere.model.I_C_PaySelection source)
	{
		this.source = InterfaceWrapperHelper.create(source, I_C_PaySelection.class);
	}

	public I_SEPA_Export run()
	{
		final I_SEPA_Export header = createExportHeader(source);

		for (final I_C_PaySelectionLine line : paymentsRepo.getProcessedLines(source))
		{
			if (line.getC_BP_BankAccount_ID() <= 0)
			{
				// No Bank account. Nothing to do.
				continue;
			}
			final I_SEPA_Export_Line exportLine = createExportLine(line);
			exportLine.setSEPA_Export(header);
			save(exportLine);
		}

		return header;
	}

	private I_SEPA_Export_Line createExportLine(@NonNull final I_C_PaySelectionLine line)
	{
		final I_C_Invoice sourceInvoice = line.getC_Invoice();
		Check.assumeNotNull(line.getC_Invoice(), "Parameter line has a not-null C_Invoice; line={}", line);

		final I_C_BP_BankAccount bpBankAccount = create(line.getC_BP_BankAccount(), I_C_BP_BankAccount.class);

		final I_SEPA_Export_Line exportLine = newInstance(I_SEPA_Export_Line.class, line);

		exportLine.setAD_Org_ID(line.getAD_Org_ID());
		exportLine.setAD_Table_ID(getTableId(I_C_PaySelectionLine.class));
		exportLine.setRecord_ID(line.getC_PaySelectionLine_ID());
		exportLine.setAmt(line.getPayAmt());
		exportLine.setC_BP_BankAccount(bpBankAccount); // 07789: also setting the BP bank account so the following model validator(s) can more easily see evaluate what it is.
		exportLine.setC_Currency_ID(bpBankAccount.getC_Currency_ID());
		exportLine.setC_BPartner_ID(line.getC_BPartner_ID());
		exportLine.setDescription(sourceInvoice.getDescription());

		exportLine.setIBAN(selectIBANOrNull(bpBankAccount));

		// task 07789: note that for the CASE of ESR accounts, there is a model validator in de.metas.payment.esr which will
		// set this field
		// exportLine.setOtherAccountIdentification(OtherAccountIdentification);
		final BankId bankId = BankId.ofRepoIdOrNull(bpBankAccount.getC_Bank_ID());
		if (bankId != null)
		{
			final Bank bank = bankRepo.getById(bankId); 
			exportLine.setSwiftCode(toNullOrRemoveSpaces(bank.getSwiftCode()));
		}

		exportLine.setStructuredRemittanceInfo(line.getReference()); // task 07789

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

		final org.compiere.model.I_C_BP_BankAccount bankAccountSource = paySelectionHeader.getC_BP_BankAccount();
		Check.assumeNotNull(bankAccountSource, "bankAccountSource not null"); // mandatory column
		final I_C_BP_BankAccount bpBankAccount = create(bankAccountSource, I_C_BP_BankAccount.class);

		// task 09923: In case the bp bank account does not have a bank set, it cannot be used in a SEPA Export
		final BankId bankId = BankId.ofRepoIdOrNull(bpBankAccount.getC_Bank_ID());
		if (bankId == null)
		{
			throw new AdempiereException(ERR_C_BP_BankAccount_BankNotSet, new Object[] { bpBankAccount.toString() });
		}

		// Set corresponding data
		header.setAD_Org_ID(paySelectionHeader.getAD_Org_ID());
		header.setIBAN(selectIBANOrNull(bpBankAccount));
		header.setPaymentDate(paySelectionHeader.getPayDate());
		header.setProcessed(false);
		header.setSEPA_CreditorName(orgBP.getName());

		if (SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02.equals(sepaProtocol) && Check.isBlank(bpBankAccount.getSEPA_CreditorIdentifier()))
		{
			throw new AdempiereException(ERR_C_BP_BankAccount_SEPA_CreditorIdentifierNotSet, new Object[] { bpBankAccount.toString() });
		}
		header.setSEPA_CreditorIdentifier(bpBankAccount.getSEPA_CreditorIdentifier());

		final Bank bank = bankRepo.getById(bankId); 
		if (Check.isBlank(bank.getSwiftCode()))
		{
			throw new AdempiereException(ERR_C_Bank_SwiftCodeNotSet, new Object[] { bank.getBankName() });
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

	private String toNullOrRemoveSpaces(final String from)
	{
		if (Check.isEmpty(from, true))
		{
			return null;
		}
		return from.replace(" ", "");
	}

	private String selectIBANOrNull(@NonNull final I_C_BP_BankAccount bp_bankAccount)
	{
		return coalesceSuppliers(
				() -> toNullOrRemoveSpaces(bp_bankAccount.getIBAN()),
				() -> toNullOrRemoveSpaces(bp_bankAccount.getQR_IBAN())
		);
	}

}
