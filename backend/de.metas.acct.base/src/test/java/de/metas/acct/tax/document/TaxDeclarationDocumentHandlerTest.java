package de.metas.acct.tax.document;

import de.metas.acct.tax.TaxDeclarationRepository;
import de.metas.document.engine.DocumentTableFields;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_TaxDeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

class TaxDeclarationDocumentHandlerTest
{
	private TaxDeclarationDocumentHandler handler;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		final TaxDeclarationRepository repository = new TaxDeclarationRepository();
		handler = new TaxDeclarationDocumentHandler(repository);
	}

	// ---------------------------------------------------------------------------
	// Helper
	// ---------------------------------------------------------------------------

	private I_C_TaxDeclaration createTaxDeclaration(
			final boolean isCorrection,
			final int originalId,
			final boolean processed,
			final boolean isActive)
	{
		final I_C_TaxDeclaration record = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		record.setIsCorrection(isCorrection);
		record.setC_TaxDeclaration_Original_ID(originalId);
		record.setProcessed(processed);
		record.setIsActive(isActive);
		// Mandatory fields required to save
		record.setC_AcctSchema_ID(1);
		record.setC_Period_ID(1);
		record.setDateAcct(Timestamp.valueOf("2025-01-01 00:00:00"));
		record.setDocAction("CO");
		record.setDocStatus("DR");
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private static DocumentTableFields asDocFields(final I_C_TaxDeclaration record)
	{
		// Canonical pattern from BankStatementDocumentHandlerTest:
		// wrap the real model as DocumentTableFields so the handler's
		// InterfaceWrapperHelper.create(docFields, I_C_TaxDeclaration.class) round-trips back to the same record.
		return InterfaceWrapperHelper.create(record, DocumentTableFields.class);
	}

	// ---------------------------------------------------------------------------
	// reactivateIt tests
	// ---------------------------------------------------------------------------

	@Test
	public void reactivateIt_throws_whenCorrectionExists()
	{
		// Given: a locked Original + a draft (but active) Correction pointing back to it.
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		createTaxDeclaration(true, original.getC_TaxDeclaration_ID(), false, true);

		// When / Then: reactivateIt rejects because the Correction must be cleared first.
		Assertions.assertThatThrownBy(() -> handler.reactivateIt(asDocFields(original)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_HasCorrections");
	}

	@Test
	public void reactivateIt_succeeds_whenNoCorrection()
	{
		// Given: a locked Original with no Correction referencing it.
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);

		// When: reactivateIt is invoked
		handler.reactivateIt(asDocFields(original));

		// Then: the Original is back in draft state (Processed=N, DocAction=Complete) per the existing Iter 5/6 contract.
		Assertions.assertThat(original.isProcessed()).isFalse();
		Assertions.assertThat(original.getDocAction()).isEqualTo("CO");
	}
}
