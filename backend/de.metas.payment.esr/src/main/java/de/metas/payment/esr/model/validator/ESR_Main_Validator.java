package de.metas.payment.esr.model.validator;

/*
 * #%L
 * de.metas.payment.esr
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;

import de.metas.banking.bankstatement.match.api.IPaymentBatchFactory;
import de.metas.banking.bankstatement.match.spi.impl.ESRPaymentBatchProvider;
import de.metas.banking.payment.IPaymentStringParserFactory;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.payment.esr.actionhandler.impl.DunningESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.MoneyTransferedBackESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.UnableToAssignESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.WithCurrenttInvoiceESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.WithNextInvoiceESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.WriteoffESRActionHandler;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRLineHandlersService;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.payment.esr.spi.impl.DefaultESRLineHandler;
import de.metas.payment.spi.impl.ESRCreaLogixStringParser;
import de.metas.payment.spi.impl.ESRRegularLineParser;

public class ESR_Main_Validator extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		super.onInit(engine, client);
		
		registerFactories();
	}
	
	@Override
	protected void registerInterceptors(IModelValidationEngine engine, I_AD_Client client)
	{
		engine.addModelValidator(new ESR_Import(), client);
		engine.addModelValidator(new ESR_ImportLine(), client);
		engine.addModelValidator(new C_PaySelection(), client);
	}

	public void registerFactories()
	{
		//
		// Register payment action handlers.
		final IESRImportBL esrImportBL = Services.get(IESRImportBL.class);
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount, new WriteoffESRActionHandler());
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning, new DunningESRActionHandler());
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, new MoneyTransferedBackESRActionHandler());
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, new WithNextInvoiceESRActionHandler());
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice, new WithCurrenttInvoiceESRActionHandler());
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, new UnableToAssignESRActionHandler());

		//
		// Register ESR Payment Parsers
		Services.get(IPaymentStringParserFactory.class).registerParser(ESRRegularLineParser.TYPE, ESRRegularLineParser.instance);
		Services.get(IPaymentStringParserFactory.class).registerParser(ESRCreaLogixStringParser.TYPE, ESRCreaLogixStringParser.instance);
		
		//
		// Payment batch provider for Bank Statement matching
		Services.get(IPaymentBatchFactory.class).addPaymentBatchProvider(new ESRPaymentBatchProvider());
		
		//
		// Bank statement listener
		Services.get(IBankStatementListenerService.class).addListener(ESRBankStatementListener.instance);
		
		//
		// ESR match listener
		Services.get(IESRLineHandlersService.class).registerESRLineListener(new DefaultESRLineHandler()); // 08741
	}
}
