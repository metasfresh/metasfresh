package de.metas.acct.tax;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_TaxDeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

class TaxDeclarationRepositoryTest
{
	private TaxDeclarationRepository repository;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new TaxDeclarationRepository();
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

	// ---------------------------------------------------------------------------
	// existsCorrectionFor tests
	// ---------------------------------------------------------------------------

	@Test
	public void existsCorrectionFor_returnsFalse_whenNoCorrection()
	{
		// Given: an Original with no Correction
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, false, true);
		final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(original.getC_TaxDeclaration_ID());

		// When / Then
		Assertions.assertThat(repository.existsCorrectionFor(originalId)).isFalse();
	}

	@Test
	public void existsCorrectionFor_returnsTrue_whenCorrectionExists_evenIfDraft()
	{
		// Given: an Original + a Correction with Processed='N' (draft) and IsActive='Y'
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, false, true);
		final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(original.getC_TaxDeclaration_ID());

		// Correction: IsCorrection=true, points to original, not yet processed, active
		createTaxDeclaration(true, originalId.getRepoId(), false, true);

		// When / Then: Reopen must be blocked even by a draft Correction
		Assertions.assertThat(repository.existsCorrectionFor(originalId)).isTrue();
	}

	@Test
	public void existsCorrectionFor_returnsFalse_whenCorrectionInactive()
	{
		// Given: an Original + an INACTIVE Correction
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, false, true);
		final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(original.getC_TaxDeclaration_ID());

		createTaxDeclaration(true, originalId.getRepoId(), false, false); // IsActive=false

		// When / Then: inactive Correction must NOT block
		Assertions.assertThat(repository.existsCorrectionFor(originalId)).isFalse();
	}

	// ---------------------------------------------------------------------------
	// getLatestInChain tests
	// ---------------------------------------------------------------------------

	@Test
	public void getLatestInChain_returnsOriginal_whenNoCorrection()
	{
		// Given: an Original alone (no Corrections)
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(original.getC_TaxDeclaration_ID());

		// When / Then: falls back to Original
		final I_C_TaxDeclaration result = repository.getLatestInChain(originalId);
		Assertions.assertThat(result.getC_TaxDeclaration_ID()).isEqualTo(originalId.getRepoId());
	}

	@Test
	public void getLatestInChain_returnsLatestCompletedCorrection()
	{
		// Given: an Original + 2 completed Corrections (Created at different times — the later one wins)
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(original.getC_TaxDeclaration_ID());

		final I_C_TaxDeclaration correction1 = createTaxDeclaration(true, originalId.getRepoId(), true, true);
		// Force correction1.Created to be earlier than correction2 via InterfaceWrapperHelper.setValue
		InterfaceWrapperHelper.setValue(correction1, I_C_TaxDeclaration.COLUMNNAME_Created, Timestamp.valueOf("2025-01-01 10:00:00"));
		InterfaceWrapperHelper.save(correction1);

		final I_C_TaxDeclaration correction2 = createTaxDeclaration(true, originalId.getRepoId(), true, true);
		InterfaceWrapperHelper.setValue(correction2, I_C_TaxDeclaration.COLUMNNAME_Created, Timestamp.valueOf("2025-01-02 10:00:00"));
		InterfaceWrapperHelper.save(correction2);

		// When / Then: should return the LATER Correction
		final I_C_TaxDeclaration result = repository.getLatestInChain(originalId);
		Assertions.assertThat(result.getC_TaxDeclaration_ID()).isEqualTo(correction2.getC_TaxDeclaration_ID());
	}

	@Test
	public void getLatestInChain_skipsDraftCorrection_returnsOriginal()
	{
		// Given: an Original + a Correction with Processed='N' (draft, not live)
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(original.getC_TaxDeclaration_ID());

		createTaxDeclaration(true, originalId.getRepoId(), false, true); // Processed=false

		// When / Then: draft Corrections don't count as "live"; falls back to Original
		final I_C_TaxDeclaration result = repository.getLatestInChain(originalId);
		Assertions.assertThat(result.getC_TaxDeclaration_ID()).isEqualTo(originalId.getRepoId());
	}

	// ---------------------------------------------------------------------------
	// isLatestInChain tests
	// ---------------------------------------------------------------------------

	@Test
	void isLatestInChain_originalWithNoProcessedCorrection_isLatest()
	{
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		Assertions.assertThat(repository.isLatestInChain(idOf(original))).isTrue();
	}

	@Test
	void isLatestInChain_originalSupersededByProcessedCorrection_isNotLatest()
	{
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		createTaxDeclaration(true, original.getC_TaxDeclaration_ID(), true, true);
		Assertions.assertThat(repository.isLatestInChain(idOf(original))).isFalse();
	}

	@Test
	void isLatestInChain_latestProcessedCorrection_isLatest()
	{
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		final I_C_TaxDeclaration corr = createTaxDeclaration(true, original.getC_TaxDeclaration_ID(), true, true);
		Assertions.assertThat(repository.isLatestInChain(idOf(corr))).isTrue();
	}

	private static TaxDeclarationId idOf(final I_C_TaxDeclaration record)
	{
		return TaxDeclarationId.ofRepoId(record.getC_TaxDeclaration_ID());
	}

	// ---------------------------------------------------------------------------
	// hasUnprocessedCorrectionFor tests
	// ---------------------------------------------------------------------------

	@Test
	void hasUnprocessedCorrectionFor_noCorrection_false()
	{
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		Assertions.assertThat(repository.hasUnprocessedCorrectionFor(idOf(original), -1)).isFalse();
	}

	@Test
	void hasUnprocessedCorrectionFor_draftCorrectionExists_true()
	{
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		createTaxDeclaration(true, original.getC_TaxDeclaration_ID(), false, true); // draft (not processed) correction
		Assertions.assertThat(repository.hasUnprocessedCorrectionFor(idOf(original), -1)).isTrue();
	}

	@Test
	void hasUnprocessedCorrectionFor_onlyProcessedCorrection_false()
	{
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		createTaxDeclaration(true, original.getC_TaxDeclaration_ID(), true, true); // processed correction
		Assertions.assertThat(repository.hasUnprocessedCorrectionFor(idOf(original), -1)).isFalse();
	}

	@Test
	void hasUnprocessedCorrectionFor_excludesSelf()
	{
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		final I_C_TaxDeclaration draft = createTaxDeclaration(true, original.getC_TaxDeclaration_ID(), false, true);
		Assertions.assertThat(repository.hasUnprocessedCorrectionFor(idOf(original), draft.getC_TaxDeclaration_ID())).isFalse();
	}
}
