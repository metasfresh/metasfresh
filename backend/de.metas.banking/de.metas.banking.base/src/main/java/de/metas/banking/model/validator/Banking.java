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

import com.google.common.collect.ImmutableSet;
import de.metas.acct.posting.IDocumentRepostingSupplierService;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.impexp.BankStatementImportProcess;
import de.metas.banking.model.I_I_Datev_Payment;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.impexp.DatevPaymentImportProcess;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.banking.service.ICashStatementBL;
import de.metas.banking.spi.impl.BankStatementDocumentRepostingSupplier;
import de.metas.currency.ICurrencyBL;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_I_BankStatement;

import java.util.Set;

/**
 * Banking module activator
 *
 * @author ts
 */
public class Banking extends AbstractModuleInterceptor
{
	@Override
	protected Set<String> getTableNamesToSkipOnMigrationScriptsLogging()
	{
		return ImmutableSet.of(I_I_BankStatement.Table_Name, I_I_Datev_Payment.Table_Name);
	}

	@Override
	protected void onAfterInit()
	{
		final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

		// Register the Document Reposting Handler
		final IDocumentRepostingSupplierService documentBL = Services.get(IDocumentRepostingSupplierService.class);
		documentBL.registerSupplier(new BankStatementDocumentRepostingSupplier(bankStatementDAO));

		final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
		final IBankStatementListenerService bankStatementListenerService = Services.get(IBankStatementListenerService.class);
		final IImportProcessFactory importProcessFactory = Services.get(IImportProcessFactory.class);

		bankStatementListenerService.addListener(new PaySelectionBankStatementListener(paySelectionBL));

		importProcessFactory.registerImportProcess(I_I_Datev_Payment.class, DatevPaymentImportProcess.class);
		importProcessFactory.registerImportProcess(I_I_BankStatement.class, BankStatementImportProcess.class);
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final ICashStatementBL cashStatementBL = Services.get(ICashStatementBL.class);
		final BankAccountService bankAccountService = SpringContextHolder.instance.getBean(BankAccountService.class);

		// Bank statement:
		{
			engine.addModelValidator(new de.metas.banking.model.validator.C_BankStatementLine(bankStatementBL));
		}

		// de.metas.banking.payment sub-module (code moved from swat main validator)
		{
			engine.addModelValidator(new de.metas.banking.payment.modelvalidator.C_Payment(bankStatementBL, paymentBL, sysConfigBL, cashStatementBL)); // 04203
			engine.addModelValidator(new de.metas.banking.payment.modelvalidator.C_PaySelection(bankAccountService)); // 04203
			engine.addModelValidator(new de.metas.banking.payment.modelvalidator.C_PaySelectionLine()); // 04203
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_Payment_Request.instance); // 08596
			engine.addModelValidator(de.metas.banking.payment.modelvalidator.C_AllocationHdr.instance); // 08972
		}
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

		calloutsRegistry.registerAnnotatedCallout(new de.metas.banking.callout.C_BankStatement(bankStatementBL));
		calloutsRegistry.registerAnnotatedCallout(de.metas.banking.payment.callout.C_PaySelectionLine.instance);
		calloutsRegistry.registerAnnotatedCallout(new de.metas.banking.callout.C_BankStatementLine(bankStatementBL, currencyConversionBL));
	}
}
