package de.metas.payment.sepa.api.impl;

import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.banking.mandate.DirectDebitMandateRepository;
import de.metas.banking.mandate.DirectDebitMandateType;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.sepa.api.CreatedSEPAExports;
import de.metas.payment.sepa.api.CreatedSEPAExports.CreatedSEPAExport;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.api.SEPAExportLineId;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.X_AD_OrgInfo;
import org.compiere.model.I_C_BP_BankAccount_DirectDebitMandate;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		SpringContextHolder.registerJUnitBean(DirectDebitMandateRepository.newInstanceForUnitTesting());
	}

	@Test
	public void creditTransferIsGroupTransactionTrue()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createMockData();
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getPaySelection(), true, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
		sepaTestHelper.creditTransferIsGroupTransactionTrue(command.run().getSingleExport().getExport());
	}

	@Test
	public void creditTransferIsGroupTransactionFalse()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createMockData();
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getPaySelection(), false, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
		sepaTestHelper.creditTransferIsGroupTransactionFalse(command.run().getSingleExport().getExport());
	}

	@Test
	public void debitDirectIsGroupTransactionTrue()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createMockData();
		final I_C_PaySelection paySelection = sepaTestHelper.getPaySelection();
		paySelection.setPaySelectionTrxType(PAYSELECTIONTRXTYPE_DirectDebit);
		paySelection.setPayDate(Timestamp.valueOf("2026-10-20 00:00:00")); // the collection date, needed for the mandate check
		save(paySelection);
		createActiveMandatesOfLines(paySelection);
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(paySelection, true, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
		sepaTestHelper.assertCommonDebitDirectCases(command.run().getSingleExport().getExport());
	}

	@Test
	public void debitDirectIsGroupTransactionFalse()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createMockData();
		final I_C_PaySelection paySelection = sepaTestHelper.getPaySelection();
		paySelection.setPaySelectionTrxType(PAYSELECTIONTRXTYPE_DirectDebit);
		paySelection.setPayDate(Timestamp.valueOf("2026-10-20 00:00:00")); // the collection date, needed for the mandate check
		save(paySelection);
		createActiveMandatesOfLines(paySelection);
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(paySelection, false, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
		sepaTestHelper.assertCommonDebitDirectCases(command.run().getSingleExport().getExport());
	}

	/**
	 * A direct debit pay selection line is exported only with an active mandate of its bank account.
	 */
	private static void createActiveMandatesOfLines(@NonNull final I_C_PaySelection paySelection)
	{
		createOrgInfo(paySelection);

		Services.get(IPaySelectionBL.class).retrievePaySelectionLines(paySelection).stream()
				.map(I_C_PaySelectionLine::getC_BP_BankAccount_ID)
				.distinct()
				.forEach(bankAccountId -> createActiveMandate(bankAccountId, DirectDebitMandateType.B2B));
	}

	/**
	 * AC8, AC21: a pay selection with a B2B and a CORE debtor gives one export per scheme; each line carries its mandate reference,
	 * and each used mandate's DateLastUsed is the collection date.
	 */
	@Test
	public void directDebit_b2bAndCoreLines_oneExportPerScheme()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createMockData();
		final I_C_PaySelection paySelection = sepaTestHelper.getPaySelection();
		paySelection.setPaySelectionTrxType(PAYSELECTIONTRXTYPE_DirectDebit);
		paySelection.setPayDate(Timestamp.valueOf("2026-10-20 00:00:00")); // the collection date
		save(paySelection);
		createOrgInfo(paySelection);

		// the mock data has two bank accounts: the one of the first line (two lines) becomes B2B, the other one (one line) CORE
		final List<I_C_PaySelectionLine> paySelectionLines = Services.get(IPaySelectionBL.class).retrievePaySelectionLines(paySelection);
		final int b2bBankAccountId = paySelectionLines.get(0).getC_BP_BankAccount_ID();
		final Map<Integer, I_C_BP_BankAccount_DirectDebitMandate> mandatesByBankAccountId = new HashMap<>();
		paySelectionLines.stream()
				.map(I_C_PaySelectionLine::getC_BP_BankAccount_ID)
				.distinct()
				.forEach(bankAccountId -> mandatesByBankAccountId.put(bankAccountId, createActiveMandate(bankAccountId, bankAccountId == b2bBankAccountId ? DirectDebitMandateType.B2B : DirectDebitMandateType.CORE)));
		assertThat(mandatesByBankAccountId).hasSize(2);

		final CreatedSEPAExports createdExports = new CreateSEPAExportFromPaySelectionCommand(paySelection, false, null).run();

		assertThat(createdExports.getExports()).extracting(CreatedSEPAExport::getScheme).containsExactlyInAnyOrder(DirectDebitMandateType.B2B, DirectDebitMandateType.CORE);
		final ISEPADocumentDAO sepaDocumentDAO = Services.get(ISEPADocumentDAO.class);
		for (final CreatedSEPAExport createdExport : createdExports.getExports())
		{
			final I_SEPA_Export export = createdExport.getExport();
			assertThat(export.getSEPA_Protocol()).isEqualTo("pain.008.001.08");

			final List<I_SEPA_Export_Line> lines = sepaDocumentDAO.retrieveLines(export);
			assertThat(lines).hasSize(createdExport.getScheme() == DirectDebitMandateType.B2B ? 2 : 1);
			assertThat(createdExport.getMandatesBySepaExportLineId()).hasSize(lines.size());
			for (final I_SEPA_Export_Line line : lines)
			{
				final I_C_BP_BankAccount_DirectDebitMandate mandateRecord = mandatesByBankAccountId.get(line.getC_BP_BankAccount_ID());
				assertThat(mandateRecord.getMandateType()).isEqualTo(createdExport.getScheme().getCode());
				assertThat(line.getSEPA_MandateRefNo()).isEqualTo(mandateRecord.getMandateReference());
				assertThat(createdExport.getMandatesBySepaExportLineId().get(SEPAExportLineId.ofRepoId(line.getSEPA_Export_Line_ID())).getReference())
						.isEqualTo(mandateRecord.getMandateReference());
			}
		}

		for (final I_C_BP_BankAccount_DirectDebitMandate mandateRecord : mandatesByBankAccountId.values())
		{
			InterfaceWrapperHelper.refresh(mandateRecord);
			assertThat(mandateRecord.getDateLastUsed()).isEqualTo(Timestamp.valueOf("2026-10-20 00:00:00"));
		}
	}

	private static void createOrgInfo(@NonNull final I_C_PaySelection paySelection)
	{
		// the collection date is resolved in the time zone of the export org
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(paySelection.getAD_Org_ID());
		orgInfo.setStoreCreditCardData(X_AD_OrgInfo.STORECREDITCARDDATA_Speichern);
		save(orgInfo);
	}

	private static I_C_BP_BankAccount_DirectDebitMandate createActiveMandate(final int bankAccountId, @NonNull final DirectDebitMandateType type)
	{
		final I_C_BP_BankAccount_DirectDebitMandate mandate = newInstance(I_C_BP_BankAccount_DirectDebitMandate.class);
		mandate.setAD_Org_ID(0);
		mandate.setC_BP_BankAccount_ID(bankAccountId);
		mandate.setMandateType(type.getCode());
		mandate.setMandateReference("MREF-" + bankAccountId);
		mandate.setMandateDate(Timestamp.valueOf("2026-01-10 00:00:00"));
		mandate.setIsRecurring(true);
		save(mandate);
		return mandate;
	}

	@Test
	public void commandGroupsQrIbanLinesIndividually()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createQrIbanMockData();
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getQrIbanPaySelection(), true, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
		sepaTestHelper.assertQrIbanLinesNotGrouped(command.run().getSingleExport().getExport());
	}

	@Test
	public void marshalQrIbanGroupedExport_doesNotThrow() throws Exception
	{
		final BankRepository bankRepository = SpringContextHolder.instance.getBean(BankRepository.class);
		final BankAccountService bankAccountService = new BankAccountService(bankRepository, new CurrencyRepository());
		SpringContextHolder.registerJUnitBean(bankAccountService);

		final SEPAExportContext exportContext = SEPAExportContext.builder()
				.referenceAsEndToEndId(false)
				.build();
		final SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02 marshaler =
				new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(bankRepository, exportContext, bankAccountService);

		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createQrIbanMockData();

		// sanity check: each QR reference is valid on its own (grouping OFF, so no aggregation happens) - proves the
		// fixtures are trustworthy before reproducing the grouped-export bug below. `isInvalidQRReference(..)` itself
		// is package-private to de.metas.payment.sepa.sepamarshaller.impl, so it is exercised here through the public
		// marshal(..) entry point instead. Running the command twice on the same C_PaySelection is safe - run() only
		// reads the pay-selection lines, it never mutates them.
		final I_SEPA_Export ungroupedExport = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getQrIbanPaySelection(), false, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02).run().getSingleExport().getExport();
		assertThatCode(() -> marshaler.marshal(ungroupedExport, new ByteArrayOutputStream()))
				.as("each individual QR reference must be valid before we reproduce the grouping bug")
				.doesNotThrowAnyException();

		// reproduce the bug: grouping ON aggregates both QR-IBAN lines into one comma-joined, truncated reference
		// that is no longer a valid 27-digit QRR reference.
		final I_SEPA_Export groupedExport = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getQrIbanPaySelection(), true, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02).run().getSingleExport().getExport();

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		assertThatCode(() -> marshaler.marshal(groupedExport, out))
				.as("grouping QR-IBAN lines must not corrupt the QRR reference")
				.doesNotThrowAnyException();

		final String xml = out.toString(StandardCharsets.UTF_8.name());
		assertThat(xml).contains(SEPATestHelper.QR_REFERENCE_1);
		assertThat(xml).contains(SEPATestHelper.QR_REFERENCE_2);
	}

	@Test
	void creditTransfer_exportedWith_pain_001_001_03_ch_02()
	{
		final I_SEPA_Export export = new CreateSEPAExportFromPaySelectionCommand(createCreditTransferPaySelection(), false, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02).run().getSingleExport().getExport();

		assertThat(export.getSEPA_Protocol()).isEqualTo("pain.001.001.03.ch.02");
	}

	@Test
	void creditTransfer_nothingChosen_exportedWith_pain_001_001_09_ch_03()
	{
		final I_SEPA_Export export = new CreateSEPAExportFromPaySelectionCommand(createCreditTransferPaySelection(), false, null).run().getSingleExport().getExport();

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
}
