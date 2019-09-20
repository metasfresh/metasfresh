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

import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.banking.payment.spi.exception.PaymentStringParseException;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.invoice.InvoiceId;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;

import java.math.BigDecimal;

/**
 * IProcessParametersCallout is used to update frontend data from backend inside "onParameterChanged. Nice!
 */
public class WEBUI_Import_Payment_Request_For_Purchase_Invoice extends JavaProcess implements IProcessParametersCallout, IProcessPrecondition
{

	private static final String PARAM_fullPaymentString = "FullPaymentString";
	@Param(parameterName = PARAM_fullPaymentString)
	private String fullPaymentString;

	private static final String PARAM_C_BPartner_ID = "C_BPartner_ID";
	@Param(parameterName = PARAM_C_BPartner_ID)
	private I_C_BPartner bPartner;

	private static final String PARAM_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";
	@Param(parameterName = PARAM_C_BP_BankAccount_ID)
	private I_C_BP_BankAccount bankAccount;

	private static final String PARAM_Amount = "Amount";
	@Param(parameterName = PARAM_Amount)
	private BigDecimal amount;

	// services
	private final transient AlmightyKeeperOfEverything almightyKeeperOfEverything = SpringContextHolder.instance.getBean(AlmightyKeeperOfEverything.class);
	final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	@Override protected String doIt() throws Exception
	{
		log.info("{}\n{}\n{}\n{}\n{}\n", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz", fullPaymentString, bPartner.getName(), bankAccount.getCreditCardNumber(), amount.toString());
		return null;
	}

	@Override public void onParameterChanged(final String parameterName)
	{

		if (!PARAM_fullPaymentString.equals(parameterName))
		{
			// nothing to do here
			return;
		}

		if (Check.isEmpty(fullPaymentString, true))
		{
			return; // do nothing if it's empty
		}

		final IPaymentStringDataProvider dataProvider;
		try
		{
			dataProvider = almightyKeeperOfEverything.parsePaymentString(Env.getCtx(), fullPaymentString);
		}
		catch (final PaymentStringParseException pspe)
		{
			final String adMessage = pspe.getLocalizedMessage() + " (\"" + fullPaymentString + "\")";
			throw new AdempiereException(adMessage);
		}

		final IPaymentString paymentString = dataProvider.getPaymentString();

		final I_C_Invoice actualInvoice = invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(getRecord_ID()));
		final I_C_BP_BankAccount bpBankAccountExisting = almightyKeeperOfEverything.getAndVerifyBPartnerAccountOrNull(dataProvider, actualInvoice.getC_BPartner_ID());
		if (bpBankAccountExisting != null)
		{
			bPartner = bPartnerDAO.getById(bpBankAccountExisting.getC_BPartner_ID());
			bankAccount = bpBankAccountExisting;
		}

		amount = paymentString.getAmount();

		//		currentParsedPaymentString = paymentString;
	}

	@Override public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return ProcessPreconditionsResolution.accept();
	}
}
