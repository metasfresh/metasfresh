package de.metas.payment.esr.model.validator;

import de.metas.payment.esr.actionhandler.impl.UnknownInvoiceESRActionHandler;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;

import de.metas.banking.bankstatement.match.api.IPaymentBatchFactory;
import de.metas.banking.bankstatement.match.spi.impl.ESRPaymentBatchProvider;
import de.metas.banking.payment.IPaymentStringParserFactory;
import de.metas.banking.payment.PaymentParserType;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.payment.esr.actionhandler.impl.DiscountESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.DunningESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.DuplicatePaymentESRActionHandler;
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
import de.metas.payment.spi.impl.QRCodeStringParser;
import de.metas.util.Services;

public class ESR_Main_Validator extends AbstractModuleInterceptor
{
	@Override
	protected void onAfterInit()
	{
		registerFactories();
	}

	@Override
	protected void registerInterceptors(IModelValidationEngine engine)
	{
		engine.addModelValidator(new ESR_Import());
		engine.addModelValidator(new ESR_ImportLine());
		engine.addModelValidator(new C_PaySelection());
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
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Discount, new DiscountESRActionHandler());
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment, new DuplicatePaymentESRActionHandler());
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unknown_Invoice, new UnknownInvoiceESRActionHandler());
		
		//
		// Register ESR Payment Parsers
		final IPaymentStringParserFactory paymentStringParserFactory = Services.get(IPaymentStringParserFactory.class);
		paymentStringParserFactory.registerParser(PaymentParserType.ESRRegular.getType(), ESRRegularLineParser.instance);
		paymentStringParserFactory.registerParser(PaymentParserType.ESRCreaLogix.getType(), ESRCreaLogixStringParser.instance);
		paymentStringParserFactory.registerParser(PaymentParserType.QRCode.getType(), new QRCodeStringParser());

		//
		// Payment batch provider for Bank Statement matching
		Services.get(IPaymentBatchFactory.class).addPaymentBatchProvider(new ESRPaymentBatchProvider());

		//
		// Bank statement listener
		Services.get(IBankStatementListenerService.class).addListener(new ESRBankStatementListener(esrImportBL));

		//
		// ESR match listener
		Services.get(IESRLineHandlersService.class).registerESRLineListener(new DefaultESRLineHandler()); // 08741
	}
}
