package de.metas.acct.tax.document;

import de.metas.acct.tax.TaxDeclarationRepository;
import de.metas.document.engine.DocumentTableFields;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationLine;
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

	private static void addLine(final I_C_TaxDeclaration parent)
	{
		// completeIt rejects with TaxDeclaration_NoLinesYet unless the declaration has at least one Line.
		// (Iter 4 + 5 contract — exercised by 'Build' before 'Complete' in the real workflow.)
		final I_C_TaxDeclarationLine line = InterfaceWrapperHelper.newInstance(I_C_TaxDeclarationLine.class);
		line.setC_TaxDeclaration_ID(parent.getC_TaxDeclaration_ID());
		line.setIsActive(true);
		InterfaceWrapperHelper.save(line);
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

	// ---------------------------------------------------------------------------
	// completeIt tests
	// ---------------------------------------------------------------------------

	@Test
	public void completeIt_onCorrection_clearsOriginalIsCorrectionNeeded()
	{
		// Given: a LOCKED Original with IsCorrectionNeeded='Y' + CorrectionNeededReason='something'
		// (must be Processed=Y so the Iter 7 Correction-lifecycle precondition holds.)
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		original.setIsCorrectionNeeded(true);
		original.setCorrectionNeededReason("Test correction reason");
		InterfaceWrapperHelper.save(original);

		// AND: a draft Correction pointing to it, with a Line so completeIt's NoLinesYet check passes
		final I_C_TaxDeclaration correction = createTaxDeclaration(true, original.getC_TaxDeclaration_ID(), false, true);
		addLine(correction);

		// When: completeIt is called on the Correction
		final String result = handler.completeIt(asDocFields(correction));

		// Then: the Correction is marked as completed
		Assertions.assertThat(result).isEqualTo("CO");
		Assertions.assertThat(correction.isProcessed()).isTrue();
		Assertions.assertThat(correction.getDocAction()).isEqualTo("RE");

		// AND: the Original's IsCorrectionNeeded flag is cleared (reload from DB — completeIt loads a fresh
		// copy of the Original via repo.getById, so the local 'original' reference is stale.)
		final I_C_TaxDeclaration reloadedOriginal = InterfaceWrapperHelper.load(original.getC_TaxDeclaration_ID(), I_C_TaxDeclaration.class);
		Assertions.assertThat(reloadedOriginal.isCorrectionNeeded()).isFalse();
		Assertions.assertThat(reloadedOriginal.getCorrectionNeededReason()).isNull();
	}

	@Test
	public void completeIt_onOriginal_doesNotMutateAnyOriginal()
	{
		// Given: a draft Original with IsCorrectionNeeded='Y' and a Line so completeIt's NoLinesYet check passes
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, false, true);
		original.setIsCorrectionNeeded(true);
		InterfaceWrapperHelper.save(original);
		addLine(original);
		final boolean expectedIsCorrectionNeeded = original.isCorrectionNeeded();

		// When: completeIt is called on the Original (not a Correction)
		final String result = handler.completeIt(asDocFields(original));

		// Then: the Original is marked as completed
		Assertions.assertThat(result).isEqualTo("CO");
		Assertions.assertThat(original.isProcessed()).isTrue();
		Assertions.assertThat(original.getDocAction()).isEqualTo("RE");

		// AND: the Original's IsCorrectionNeeded flag is NOT mutated
		Assertions.assertThat(original.isCorrectionNeeded()).isEqualTo(expectedIsCorrectionNeeded);
	}
}
