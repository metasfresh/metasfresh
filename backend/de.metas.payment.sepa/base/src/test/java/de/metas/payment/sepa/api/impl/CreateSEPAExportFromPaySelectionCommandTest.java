package de.metas.payment.sepa.api.impl;

import de.metas.banking.api.BankRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_PaySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.compiere.model.X_C_PaySelection.PAYSELECTIONTRXTYPE_DirectDebit;

public class CreateSEPAExportFromPaySelectionCommandTest
{
	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new BankRepository());
	}

	@Test
	public void creditTransferIsGroupTransactionTrue()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createMockData();
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getPaySelection(), true);
		sepaTestHelper.creditTransferIsGroupTransactionTrue(command.run());
	}

	@Test
	public void creditTransferIsGroupTransactionFalse()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createMockData();
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getPaySelection(), false);
		sepaTestHelper.creditTransferIsGroupTransactionFalse(command.run());
	}

	@Test
	public void debitDirectIsGroupTransactionTrue()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createMockData();
		final I_C_PaySelection paySelection = sepaTestHelper.getPaySelection();
		paySelection.setPaySelectionTrxType(PAYSELECTIONTRXTYPE_DirectDebit);
		save(paySelection);
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(paySelection, true);
		sepaTestHelper.assertCommonDebitDirectCases(command.run());
	}

	@Test
	public void debitDirectIsGroupTransactionFalse()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createMockData();
		final I_C_PaySelection paySelection = sepaTestHelper.getPaySelection();
		paySelection.setPaySelectionTrxType(PAYSELECTIONTRXTYPE_DirectDebit);
		save(paySelection);
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(paySelection, false);
		sepaTestHelper.assertCommonDebitDirectCases(command.run());
	}
}
