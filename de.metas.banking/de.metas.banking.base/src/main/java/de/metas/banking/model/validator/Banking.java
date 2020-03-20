package de.metas.banking.model.validator;

/*
 * #%L
 * de.metas.banking.base
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

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_I_BankStatement;

import de.metas.acct.posting.IDocumentRepostingSupplierService;
import de.metas.banking.impexp.BankStatementImportProcess;
import de.metas.banking.model.I_I_Datev_Payment;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.impexp.DatevPaymentImportProcess;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.banking.spi.impl.BankStatementDocumentRepostingSupplier;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.util.Services;

/**
 * Banking module activator
 *
 * @author ts
 *
 */
public class Banking extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		super.onInit(engine, client);

		//
		// Register default bank statement listeners
		final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
		Services.get(IBankStatementListenerService.class).addListener(new PaySelectionBankStatementListener(paySelectionBL));

		Services.get(IImportProcessFactory.class).registerImportProcess(I_I_Datev_Payment.class, DatevPaymentImportProcess.class);

		Services.get(IImportProcessFactory.class).registerImportProcess(I_I_BankStatement.class, BankStatementImportProcess.class);
	}

	@Override
	protected void onAfterInit()
	{

		// Register the Document Reposting Handler
		final IDocumentRepostingSupplierService documentBL = Services.get(IDocumentRepostingSupplierService.class);
		documentBL.registerSupplier(new BankStatementDocumentRepostingSupplier());
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		// Bank statement:
		{
			engine.addModelValidator(de.metas.banking.model.validator.C_BankStatementLine.instance, client);
		}

		// de.metas.banking.payment sub-module (code moved from swat main validator)
		{
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_Payment.instance, client); // 04203
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_PaySelection.instance, client); // 04203
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_PaySelectionLine.instance, client); // 04203
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_Payment_Request.instance, client); // 08596
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_AllocationHdr.instance, client); // 08972
		}
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(de.metas.banking.callout.C_BankStatement.instance);
		calloutsRegistry.registerAnnotatedCallout(de.metas.banking.payment.callout.C_PaySelectionLine.instance);
		calloutsRegistry.registerAnnotatedCallout(de.metas.banking.callout.C_BankStatementLine.instance);
	}
}
