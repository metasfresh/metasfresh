package de.metas.banking.service;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.LocalDate;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.util.Trace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.banking.BankAccountId;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.BankId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.api.BankAccountAcctRepository;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.banking.payment.impl.BankStatementPaymentBL;
import de.metas.banking.service.impl.BankStatementBL;
import de.metas.banking.service.impl.C_BankStatementLine_MockedInterceptor;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocumentTableFields;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Builder;

/*
 * #%L
 * de.metas.banking.base
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

public class BankStatementDocumentHandlerTest
{
	private BankStatementDocumentHandler handler;

	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private BankRepository bankRepo;

	private final String metasfreshIban = "123456";
	private final LocalDate statementDate = SystemTime.asLocalDate();
	private final LocalDate valutaDate = SystemTime.asLocalDate();

	private CurrencyId euroCurrencyId;
	private BankAccountId euroOrgBankAccountId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final BankAccountService bankAccountService = BankAccountService.newInstanceForUnitTesting();
		final BankStatementBL bankStatementBL = new BankStatementBL(bankAccountService)
		{
			public void unpost(I_C_BankStatement bankStatement)
			{
				System.out.println("In JUnit test BankStatementBL.unpost() does nothing"
						+ "\n\t bank statement: " + bankStatement
						+ "\n\t called via " + Trace.toOneLineStackTraceString());
			}
		};

		// bankStatementListenerService = Services.get(IBankStatementListenerService.class);
		final BankStatementPaymentBL bankStatementPaymentBL = new BankStatementPaymentBL(
				bankStatementBL,
				new MoneyService(new CurrencyRepository()));

		final BankStatementDocumentHandlerRequiredServicesFacade servicesFacade = new BankStatementDocumentHandlerRequiredServicesFacade(
				bankStatementPaymentBL,
				bankStatementBL);
		handler = new BankStatementDocumentHandler(servicesFacade);

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);
		modelInterceptorRegistry.addModelInterceptor(new C_BankStatementLine_MockedInterceptor(bankStatementBL));

		bankRepo = new BankRepository();
		SpringContextHolder.registerJUnitBean(bankRepo);

		final BankAccountAcctRepository bankAccountAcctRepo = new BankAccountAcctRepository();
		final CurrencyRepository currencyRepo = new CurrencyRepository();
		SpringContextHolder.registerJUnitBean(new BankAccountService(bankRepo, bankAccountAcctRepo, currencyRepo));

		createMasterData();
	}

	private void createMasterData()
	{
		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		euroOrgBankAccountId = createOrgBankAccount(euroCurrencyId);
	}

	private BPartnerId createCustomer()
	{
		I_C_BPartner customer = BusinessTestHelper.createBPartner("le customer");
		return BPartnerId.ofRepoId(customer.getC_BPartner_ID());
	}

	private BankAccountId createOrgBankAccount(final CurrencyId euroCurrencyId)
	{
		final I_C_BPartner orgBPartner = BusinessTestHelper.createBPartner("metasfresh");
		final BankId bankId = createBank();

		final I_C_BP_BankAccount orgBankAccount = BusinessTestHelper.createBpBankAccount(
				BPartnerId.ofRepoId(orgBPartner.getC_BPartner_ID()),
				euroCurrencyId,
				metasfreshIban);
		orgBankAccount.setC_Bank_ID(bankId.getRepoId());
		saveRecord(orgBankAccount);

		return BankAccountId.ofRepoId(orgBankAccount.getC_BP_BankAccount_ID());
	}

	private BankId createBank()
	{
		return bankRepo.createBank(BankCreateRequest.builder()
				.bankName("MyBank")
				.routingNo("routingNo")
				.build())
				.getBankId();
	}

	private I_C_BankStatement createBankStatement(final BankAccountId orgBankAccountId)
	{
		final BankStatementId bankStatementId = bankStatementDAO.createBankStatement(BankStatementCreateRequest.builder()
				.orgId(OrgId.ANY)
				.orgBankAccountId(orgBankAccountId)
				.name("Bank Statement 1")
				.statementDate(statementDate)
				.build());

		return bankStatementDAO.getById(bankStatementId);
	}

	@Builder(builderMethodName = "bankStatementLine", builderClassName = "BankStatementLineBuilder")
	private I_C_BankStatementLine createBankStatementLine(
			final BankStatementId bankStatementId,
			final BPartnerId bpartnerId,
			final Money stmtAmt,
			final Money trxAmt,
			final Money bankFeeAmt,
			final boolean processed)
	{
		final BankStatementLineId bankStatementLineId = bankStatementDAO.createBankStatementLine(BankStatementLineCreateRequest.builder()
				.bankStatementId(bankStatementId)
				.orgId(OrgId.ANY)
				.bpartnerId(bpartnerId)
				.lineNo(10)
				.statementLineDate(statementDate)
				.valutaDate(valutaDate)
				.statementAmt(stmtAmt)
				.trxAmt(trxAmt != null ? trxAmt : stmtAmt)
				.bankFeeAmt(bankFeeAmt)
				.build());

		final I_C_BankStatementLine bankStatementLine = bankStatementDAO.getLineById(bankStatementLineId);

		if (processed)
		{
			bankStatementLine.setProcessed(true);
			bankStatementDAO.save(bankStatementLine);
		}

		return bankStatementLine;
	}

	@Test
	public void completeIt_twoIdenticalStatementLines()
	{
		final BPartnerId customerId = createCustomer();

		final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);
		bankStatementLine()
				.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
				.bpartnerId(customerId)
				.stmtAmt(Money.of(100, euroCurrencyId))
				.build();

		bankStatementLine()
				.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
				.bpartnerId(customerId)
				.stmtAmt(Money.of(100, euroCurrencyId))
				.build();

		handler.completeIt(InterfaceWrapperHelper.create(bankStatement, DocumentTableFields.class));
	}
}
