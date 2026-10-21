package de.metas.payment.sepa.api.impl;

import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.api.BankAccountAcctRepository;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.currency.CurrencyCode;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.model.I_SEPA_Export;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_PaySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateSEPAExportFromPaySelectionCommandTest
{
	private static final int ORG_ID = 1000000;

	private I_C_PaySelection paySelection;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final BankRepository bankRepository = new BankRepository();
		SpringContextHolder.registerJUnitBean(bankRepository);
		SpringContextHolder.registerJUnitBean(new BankAccountService(bankRepository, new BankAccountAcctRepository(), new CurrencyRepository()));

		final I_C_BPartner orgBP = newInstance(I_C_BPartner.class);
		orgBP.setName("org");
		orgBP.setAD_OrgBP_ID(ORG_ID);
		save(orgBP);

		final Bank bank = bankRepository.createBank(BankCreateRequest.builder()
				.bankName("myBank")
				.routingNo("routingNo")
				.swiftCode("INGBNL2A")
				.build());

		final I_C_BP_BankAccount bankAccount = newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_Bank_ID(bank.getBankId().getRepoId());
		bankAccount.setC_Currency_ID(PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR).getRepoId());
		bankAccount.setIBAN("NL31INGB0000000044");
		bankAccount.setA_Name("account");
		save(bankAccount);

		paySelection = newInstance(I_C_PaySelection.class);
		paySelection.setAD_Org_ID(ORG_ID);
		paySelection.setPaySelectionTrxType(PaySelectionTrxType.CREDIT_TRANSFER.getCode());
		paySelection.setC_BP_BankAccount_ID(bankAccount.getC_BP_BankAccount_ID());
		save(paySelection);
	}

	@Test
	void creditTransfer_exportedWith_pain_001_001_03_ch_02()
	{
		final I_SEPA_Export export = new CreateSEPAExportFromPaySelectionCommand(paySelection, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02).run();

		assertThat(export.getSEPA_Protocol()).isEqualTo("pain.001.001.03.ch.02");
	}

	@Test
	void creditTransfer_nothingChosen_exportedWith_pain_001_001_09_ch_03()
	{
		final I_SEPA_Export export = new CreateSEPAExportFromPaySelectionCommand(paySelection, null).run();

		assertThat(export.getSEPA_Protocol()).isEqualTo("pain.001.001.09.ch.03");
	}
}
