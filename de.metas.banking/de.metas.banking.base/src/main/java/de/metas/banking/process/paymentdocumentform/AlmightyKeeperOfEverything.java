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

package de.metas.banking.process.paymentdocumentform;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.model.I_C_Payment_Request;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.banking.payment.IPaymentStringBL;
import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.interfaces.I_C_BP_Relation;
import de.metas.logging.LogManager;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_C_BP_BankAccount;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;
import java.util.Properties;

@Service
public class AlmightyKeeperOfEverything
{
	private static final transient Logger logger = LogManager.getLogger(AlmightyKeeperOfEverything.class);

	private final IPaymentStringBL paymentStringBL = Services.get(IPaymentStringBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IPaymentRequestDAO paymentRequestDAO = Services.get(IPaymentRequestDAO.class);
	private final IPaymentRequestBL paymentRequestBL = Services.get(IPaymentRequestBL.class);

	private static final String SYSCONFIG_PaymentStringParserType = "de.metas.paymentallocation.form.ReadPaymentDocumentDialog.PaymentStringParserType";

	public AlmightyKeeperOfEverything()
	{
	}

	public IPaymentStringDataProvider parsePaymentString(final Properties ctx, final String currentPaymentString)
	{
		final IPaymentStringDataProvider dataProvider;
		dataProvider = paymentStringBL.getDataProvider(ctx, paymentStringBL.getParserForSysConfig(SYSCONFIG_PaymentStringParserType), currentPaymentString);
		return dataProvider;
	}

	/**
	 * Calls {@link IPaymentStringDataProvider#getC_BP_BankAccounts()} and
	 * <li>filters out accounts where {@link #validateAgainstContextBPartner(I_C_BP_BankAccount, int)} returned a {@code 3}
	 * <li>orders by the result of {@link #validateAgainstContextBPartner(I_C_BP_BankAccount, int)}, i.e. prefers 1s order 2s
	 * <li>returns the first match.
	 */
	@Nullable public I_C_BP_BankAccount getAndVerifyBPartnerAccountOrNull(@NonNull final IPaymentStringDataProvider dataProvider, final int contextBPartner_ID)
	{
		final Optional<I_C_BP_BankAccount> firstBankAccount = dataProvider.getC_BP_BankAccounts().stream()
				.map(bankAccount -> ImmutablePair.of(bankAccount, validateAgainstContextBPartner(bankAccount, contextBPartner_ID)))
				.filter(pair -> pair.getRight() < 3)
				.sorted(Comparator.comparing(IPair::getRight))
				.map(ImmutablePair::getLeft)
				.findFirst();

		return firstBankAccount.orElse(null);
	}

	/**
	 * Checks the given {@code bpBankAccount} against the partner that was set via contextBPartner_ID (if any) and returns:
	 * <li>{@code 1} if no contextBPartner_ID was set, or if the given {@code bpBankAccount}'s bPartner is the one that was set
	 * <li>{@code 2} else, if the given {@code bpBankAccount}'s bPartner is a {@code RemitTo} partner of the contextBPartner_ID partner
	 * <li>{@code 3} else
	 */
	private int validateAgainstContextBPartner(@NonNull final I_C_BP_BankAccount bpBankAccount, final int contextBPartner_ID)
	{
		if (contextBPartner_ID <= 0)
		{
			// we have no BPartner-Context-Info, so we can't verify the bank account.
			return 1;
		}

		if (contextBPartner_ID == bpBankAccount.getC_BPartner_ID())
		{
			// the BPartner from the account we looked up is thae one from context
			return 1;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final boolean existsRelation = queryBL.createQueryBuilder(I_C_BP_Relation.class, bpBankAccount)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_IsRemitTo, true)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, contextBPartner_ID)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID, bpBankAccount.getC_BPartner_ID())
				.create()
				.match();
		if (existsRelation)
		{
			return 2;
		}
		return 3;
	}

	// todo migration to webui process
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final I_C_Invoice invoice = context.getSelectedModel(I_C_Invoice.class);
		if (invoice == null)
		{
			return ProcessPreconditionsResolution.reject("no invoice selected");
		}

		// only completed invoiced
		if (!invoiceBL.isComplete(invoice))
		{
			return ProcessPreconditionsResolution.reject("invoice is not completed");
		}

		return ProcessPreconditionsResolution.acceptIf(!invoice.isSOTrx()); // only PO Invoices (Eingangsrechnung)
	}

	// todo migration to webui process
	public void createPaymentRequest(final org.compiere.model.I_C_Invoice invoice, final I_C_Payment_Request template)
	{

		if (template == null)
		{
			// todo @teo: what to do with MSG_PREFIX?? is it needed?
			//			throw new AdempiereException("@" + MSG_PREFIX + "SelectPaymentRequestFirstException" + "@");
			throw new AdempiereException("@" + "SelectPaymentRequestFirstException" + "@");
		}

		//
		// Get the selected invoice
		if (paymentRequestDAO.hasPaymentRequests(invoice))
		{
			//			throw new AdempiereException("@" + MSG_PREFIX + "PaymentRequestForInvoiceAlreadyExistsException" + "@");
			throw new AdempiereException("@" + "PaymentRequestForInvoiceAlreadyExistsException" + "@");
		}

		//
		// Create the payment request
		paymentRequestBL.createPaymentRequest(invoice, template);
	}

}
