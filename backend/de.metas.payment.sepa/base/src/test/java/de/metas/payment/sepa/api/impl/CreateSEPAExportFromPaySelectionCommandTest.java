package de.metas.payment.sepa.api.impl;

import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.currency.CurrencyRepository;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_PaySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

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

	@Test
	public void commandGroupsQrIbanLinesIndividually()
	{
		final SEPATestHelper sepaTestHelper = new SEPATestHelper();
		sepaTestHelper.createQrIbanMockData();
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getQrIbanPaySelection(), true);
		sepaTestHelper.assertQrIbanLinesNotGrouped(command.run());
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
		final I_SEPA_Export ungroupedExport = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getQrIbanPaySelection(), false).run();
		assertThatCode(() -> marshaler.marshal(ungroupedExport, new ByteArrayOutputStream()))
				.as("each individual QR reference must be valid before we reproduce the grouping bug")
				.doesNotThrowAnyException();

		// reproduce the bug: grouping ON aggregates both QR-IBAN lines into one comma-joined, truncated reference
		// that is no longer a valid 27-digit QRR reference.
		final I_SEPA_Export groupedExport = new CreateSEPAExportFromPaySelectionCommand(sepaTestHelper.getQrIbanPaySelection(), true).run();

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		assertThatCode(() -> marshaler.marshal(groupedExport, out))
				.as("grouping QR-IBAN lines must not corrupt the QRR reference")
				.doesNotThrowAnyException();

		final String xml = out.toString(StandardCharsets.UTF_8.name());
		assertThat(xml).contains(SEPATestHelper.QR_REFERENCE_1);
		assertThat(xml).contains(SEPATestHelper.QR_REFERENCE_2);
	}
}
