package de.metas.payment.sepa.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.payment.sepa
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.sepa.api.IPaymentBL;
import de.metas.payment.sepa.interfaces.I_C_BP_BankAccount;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import lombok.NonNull;


public class PaymentBL implements IPaymentBL
{

	private static final String ERR_C_BP_BankAccount_BankNotSet = "de.metas.payment.sepa.C_BP_BankAccount_BankNotSet";

	@Override
	public I_SEPA_Export createSEPAExport(@NonNull final I_C_PaySelection source)
	{
		final I_SEPA_Export header = createExportHeader(source);

		for (final I_C_PaySelectionLine line : Services.get(IPaymentDAO.class).getProcessedLines(source))
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

	private I_SEPA_Export_Line createExportLine(final I_C_PaySelectionLine line)
	{
		Check.assumeNotNull(line, "Line not null");

		final I_C_Invoice sourceInvoice = line.getC_Invoice();
		final I_C_BPartner bpartner = line.getC_BPartner();
		final I_C_BP_BankAccount bpBankAccount = create(line.getC_BP_BankAccount(), I_C_BP_BankAccount.class);

		Check.assumeNotNull(sourceInvoice, "Source invoice not null");

		final I_SEPA_Export_Line exportLine = newInstance(I_SEPA_Export_Line.class, line);

		exportLine.setAD_Org_ID(line.getAD_Org_ID());
		exportLine.setAD_Table_ID(getTableId(I_C_PaySelectionLine.class));
		exportLine.setRecord_ID(line.getC_PaySelectionLine_ID());
		exportLine.setAmt(line.getPayAmt());
		exportLine.setC_BP_BankAccount(bpBankAccount); // 07789: also setting the BP bank account so the following model validator(s) can more easily see evaluate what it is.
		exportLine.setC_Currency(bpBankAccount.getC_Currency());
		exportLine.setC_BPartner(bpartner);
		exportLine.setDescription(sourceInvoice.getDescription());

		exportLine.setIBAN(toNullOrRemoveSpaces(bpBankAccount.getIBAN()));

		// task 07789: note that for the CASE of ESR accounts, there is a model validator in de.metas.payment.esr which will
		// set this field
		// exportLine.setOtherAccountIdentification(OtherAccountIdentification);

		if (bpBankAccount.getC_Bank_ID() > 0)
		{
			exportLine.setSwiftCode(toNullOrRemoveSpaces(bpBankAccount.getC_Bank().getSwiftCode()));
		}

		exportLine.setStructuredRemittanceInfo(line.getReference()); // task 07789

		return exportLine;
	}

	private I_SEPA_Export createExportHeader(final I_C_PaySelection paySelectionHeader)
	{
		final I_SEPA_Export header = newInstance(I_SEPA_Export.class, paySelectionHeader);

		final Properties ctx = getCtx(paySelectionHeader);
		final String trxName = getTrxName(paySelectionHeader);

		//
		// We need the source org BP.
		final I_AD_Org sourceOrg = create(ctx, paySelectionHeader.getAD_Org_ID(), I_AD_Org.class, trxName);
		final I_C_BPartner orgBP = Services.get(IBPartnerOrgBL.class).retrieveLinkedBPartner(sourceOrg);

		final org.compiere.model.I_C_BP_BankAccount bankAccountSource = paySelectionHeader.getC_BP_BankAccount();
		Check.assumeNotNull(bankAccountSource, "bankAccountSource not null");
		final I_C_BP_BankAccount bpBankAccount = create(bankAccountSource, I_C_BP_BankAccount.class);

		// task 09923: In case the bp bank account does not have a bank set, it cannot be used in a SEPA Export
		if (bpBankAccount.getC_Bank() == null)
		{
			throw new AdempiereException(ERR_C_BP_BankAccount_BankNotSet, new Object[] { bpBankAccount.toString() });
		}

		// Set corresponding data
		header.setAD_Org(sourceOrg);
		final String iban = bpBankAccount.getIBAN();
		if (!Check.isEmpty(iban, true))
		{
			header.setIBAN(iban.replaceAll(" ", ""));
		}

		header.setPaymentDate(paySelectionHeader.getPayDate());
		header.setProcessed(false);
		header.setSEPA_CreditorIdentifier(orgBP.getName());
		header.setSwiftCode(bpBankAccount.getC_Bank().getSwiftCode());

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
}
