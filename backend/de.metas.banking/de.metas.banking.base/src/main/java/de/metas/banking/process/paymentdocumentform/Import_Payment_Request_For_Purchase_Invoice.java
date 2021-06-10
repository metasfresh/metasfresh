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

import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;

/**
 * IProcessParametersCallout is used to update frontend data from backend inside "onParameterChanged. Nice!
 */
public abstract class Import_Payment_Request_For_Purchase_Invoice extends JavaProcess implements IProcessParametersCallout, IProcessPrecondition
{

	// services
	protected final transient PaymentStringProcessService paymentStringProcessService = SpringContextHolder.instance.getBean(PaymentStringProcessService.class);
	protected final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	protected final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	// trl
	protected static final AdMessageKey MSG_CouldNotFindOrCreateBPBankAccount = AdMessageKey.of("de.metas.payment.CouldNotFindOrCreateBPBankAccount");


	protected static final String PARAM_fullPaymentString = "FullPaymentString";
	protected static final String PARAM_C_BPartner_ID = "C_BPartner_ID";
	protected static final String PARAM_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";
	protected static final String PARAM_Amount = "Amount";
	protected static final String PARAM_Will_Create_a_new_Bank_Account = "Will_Create_a_new_Bank_Account";
	protected static final String PARAM_BankAccountNo = "BankAccountNo";
	

	protected I_C_Invoice getActualInvoice()
	{
		return invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(getRecord_ID()));
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return paymentStringProcessService.checkPreconditionsApplicable(context);
	}
}
