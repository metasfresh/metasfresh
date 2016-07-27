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
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;

import de.metas.acct.api.IDocumentBL;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.banking.spi.impl.BankStatementDocumentRepostingHandler;

/**
 * Banking module activator
 *
 * @author ts
 *
 */
public class Banking extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(IModelValidationEngine engine, I_AD_Client client)
	{
		super.onInit(engine, client);

		//
		// Register default bank statement listeners
		Services.get(IBankStatementListenerService.class).addListener(PaySelectionBankStatementListener.instance);
	}

	@Override
	protected void onAfterInit()
	{

		// Register the Document Reposting Handler
		final IDocumentBL documentBL = Services.get(IDocumentBL.class);
		documentBL.registerHandler(new BankStatementDocumentRepostingHandler());
	}

	@Override
	protected void registerInterceptors(IModelValidationEngine engine, I_AD_Client client)
	{
		// Bank statement:
		{
			engine.addModelValidator(de.metas.banking.model.validator.C_BankStatement.instance, client);
			engine.addModelValidator(de.metas.banking.model.validator.C_BankStatementLine.instance, client);
			engine.addModelValidator(de.metas.banking.model.validator.C_BankStatementLine_Ref.instance, client);
		}

		// de.metas.banking.payment sub-module (code moved from swat main validator)
		{
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_Payment.instance, client); // 04203
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_PaySelection.instance, client); // 04203
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_PaySelectionLine.instance, client); // 04203
			// engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_PaySelectionCheck.instance, client);
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_Payment_Request.instance, client); // 08596
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_AllocationHdr.instance, client); // 08972
		}
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(de.metas.banking.callout.C_BankStatement.instance);
		calloutsRegistry.registerAnnotatedCallout(de.metas.banking.payment.callout.C_PaySelectionLine.instance);
	}
}
