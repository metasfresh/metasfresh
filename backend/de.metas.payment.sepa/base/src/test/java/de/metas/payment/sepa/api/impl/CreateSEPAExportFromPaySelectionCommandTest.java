package de.metas.payment.sepa.api.impl;

import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAMarshaler;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_PaySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
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
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getPaySelection(), true, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
		sepaTestHelper.creditTransferIsGroupTransactionTrue(command.run());
	}

	@Test
	public void creditTransferIsGroupTransactionFalse()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createMockData();
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getPaySelection(), false, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
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
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(paySelection, true, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
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
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(paySelection, false, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
		sepaTestHelper.assertCommonDebitDirectCases(command.run());
	}

	@Test
	void creditTransfer_exportedWith_pain_001_001_03_ch_02()
	{
		final I_SEPA_Export export = new CreateSEPAExportFromPaySelectionCommand(createCreditTransferPaySelection(), false, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02).run();

		assertThat(export.getSEPA_Protocol()).isEqualTo("pain.001.001.03.ch.02");
	}

	@Test
	void creditTransfer_nothingChosen_exportedWith_pain_001_001_09_ch_03()
	{
		final I_SEPA_Export export = new CreateSEPAExportFromPaySelectionCommand(createCreditTransferPaySelection(), false, null).run();

		assertThat(export.getSEPA_Protocol()).isEqualTo("pain.001.001.09.ch.03");
	}

	private static I_C_PaySelection createCreditTransferPaySelection()
	{
		final int orgId = 1000000;

		final BankRepository bankRepository = SpringContextHolder.instance.getBean(BankRepository.class);
		SpringContextHolder.registerJUnitBean(new BankAccountService(bankRepository, new CurrencyRepository()));

		final I_C_BPartner orgBP = newInstance(I_C_BPartner.class);
		orgBP.setName("org");
		orgBP.setAD_OrgBP_ID(orgId);
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

		final I_C_PaySelection paySelection = newInstance(I_C_PaySelection.class);
		paySelection.setAD_Org_ID(orgId);
		paySelection.setPaySelectionTrxType(PaySelectionTrxType.CREDIT_TRANSFER.getCode());
		paySelection.setC_BP_BankAccount_ID(bankAccount.getC_BP_BankAccount_ID());
		save(paySelection);
		return paySelection;
	}

	@Test
	public void commandGroupsQrIbanLinesIndividually()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createQrIbanMockData();
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getQrIbanPaySelection(), true, null);
		sepaTestHelper.assertQrIbanLinesNotGrouped(command.run());
	}

	/**
	 * An order pay-selection line is already exported individually by the order-line handling;
	 * with grouping ON it must not be exported a second time just because its bank account has a QR-IBAN.
	 */
	@Test
	public void groupingOn_qrIbanOrderLine_exportedExactlyOnce()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createQrIbanMockData();
		sepaTestHelper.createQrIbanOrderPaySelectionLine();

		final I_SEPA_Export export = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getQrIbanPaySelection(), true, null).run();

		sepaTestHelper.assertEachPaySelectionLineExportedOnce(export, 3);
	}

	@Test
	public void marshalQrIbanGroupedExport_pain_001_001_03_ch_02_doesNotThrow()
	{
		final BankRepository bankRepository = SpringContextHolder.instance.getBean(BankRepository.class);
		final BankAccountService bankAccountService = new BankAccountService(bankRepository, new CurrencyRepository());
		SpringContextHolder.registerJUnitBean(bankAccountService);

		assertQrIbanGroupedExportMarshals(new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(bankRepository, newExportContext(), bankAccountService));
	}

	@Test
	public void marshalQrIbanGroupedExport_pain_001_001_09_ch_03_doesNotThrow()
	{
		final BankRepository bankRepository = SpringContextHolder.instance.getBean(BankRepository.class);
		final BankAccountService bankAccountService = new BankAccountService(bankRepository, new CurrencyRepository());
		SpringContextHolder.registerJUnitBean(bankAccountService);

		assertQrIbanGroupedExportMarshals(new SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03(bankRepository, newExportContext(), bankAccountService));
	}

	private static SEPAExportContext newExportContext()
	{
		return SEPAExportContext.builder()
				.referenceAsEndToEndId(false)
				.build();
	}

	/**
	 * Vendor with two QR-bills, "group transactions" ON: both QR references must reach the file as two payments and the export must not fail.
	 */
	private static void assertQrIbanGroupedExportMarshals(final SEPAMarshaler marshaler)
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createQrIbanMockData();

		// sanity check: each QR reference is valid on its own (grouping OFF, so no aggregation happens) - proves the
		// fixtures are trustworthy before reproducing the grouped-export bug below.
		// Running the command twice on the same C_PaySelection is safe - run() only reads the pay-selection lines, it never mutates them.
		final I_SEPA_Export ungroupedExport = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getQrIbanPaySelection(), false, null).run();
		assertThatCode(() -> marshaler.marshal(ungroupedExport, new ByteArrayOutputStream()))
				.as("each individual QR reference must be valid before we reproduce the grouping bug")
				.doesNotThrowAnyException();

		// reproduce the bug: grouping ON aggregates both QR-IBAN lines into one comma-joined, truncated reference
		// that is no longer a valid 27-digit QRR reference.
		final I_SEPA_Export groupedExport = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getQrIbanPaySelection(), true, null).run();

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		assertThatCode(() -> marshaler.marshal(groupedExport, out))
				.as("grouping QR-IBAN lines must not corrupt the QRR reference")
				.doesNotThrowAnyException();

		final String xml = new String(out.toByteArray(), StandardCharsets.UTF_8);
		assertThat(xml).contains(SEPATestHelper.QR_REFERENCE_1);
		assertThat(xml).contains(SEPATestHelper.QR_REFERENCE_2);
	}
}
