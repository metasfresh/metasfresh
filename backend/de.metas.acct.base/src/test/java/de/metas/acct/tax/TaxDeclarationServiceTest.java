package de.metas.acct.tax;

import de.metas.calendar.PeriodRepo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

class TaxDeclarationServiceTest
{
	private TaxDeclarationService service;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		service = new TaxDeclarationService(new TaxDeclarationRepository(), new PeriodRepo());
	}

	/** Persists a minimal {@code C_Period} (with its {@code C_Year}) so {@code PeriodRepo} can resolve it. */
	private int createPeriod()
	{
		final I_C_Year year = InterfaceWrapperHelper.newInstance(I_C_Year.class);
		year.setC_Calendar_ID(10);
		InterfaceWrapperHelper.save(year);

		final I_C_Period period = InterfaceWrapperHelper.newInstance(I_C_Period.class);
		period.setC_Year_ID(year.getC_Year_ID());
		period.setStartDate(Timestamp.valueOf("2026-01-01 00:00:00"));
		period.setEndDate(Timestamp.valueOf("2026-12-31 00:00:00"));
		InterfaceWrapperHelper.save(period);
		return period.getC_Period_ID();
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
	// createCorrection tests
	// ---------------------------------------------------------------------------

	@Test
	public void createCorrection_throws_whenOriginalNotLocked()
	{
		// Given: an Original with Processed='N' (not yet locked)
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, false, true);
		final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(original.getC_TaxDeclaration_ID());

		// When / Then: must throw because the original is not yet locked
		Assertions.assertThatThrownBy(() -> service.createCorrection(originalId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_CreateCorrection_OriginalNotLocked");
	}

	@Test
	public void createCorrection_throws_whenOriginalIsItselfACorrection()
	{
		// Given: a real Original, plus a record with IsCorrection='Y' pointing at it that is also
		// locked (Processed='Y'). The Correction needs a valid C_TaxDeclaration_Original_ID to satisfy
		// the star-topology CHECK on save; the createCorrection guard then rejects correcting a Correction.
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		final I_C_TaxDeclaration correction = createTaxDeclaration(true, original.getC_TaxDeclaration_ID(), true, true);
		final TaxDeclarationId correctionId = TaxDeclarationId.ofRepoId(correction.getC_TaxDeclaration_ID());

		// When / Then: star topology is forbidden — corrections of corrections are not allowed
		Assertions.assertThatThrownBy(() -> service.createCorrection(correctionId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_OriginalMustBeOriginal");
	}

	@Test
	public void createCorrection_returnsCorrectionWithInheritedFields()
	{
		// Given: a locked Original with specific C_AcctSchema_ID, C_Period_ID, DateAcct, Description
		final int periodId = createPeriod();
		final I_C_TaxDeclaration original = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		original.setIsCorrection(false);
		original.setProcessed(true);
		original.setIsActive(true);
		original.setC_AcctSchema_ID(1);
		original.setC_Period_ID(periodId);
		original.setDateAcct(Timestamp.valueOf("2026-01-15 00:00:00"));
		original.setDescription("Q4 Report");
		original.setDocAction("CO");
		original.setDocStatus("CO");
		InterfaceWrapperHelper.save(original);
		final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(original.getC_TaxDeclaration_ID());

		// When
		final TaxDeclarationId correctionId = service.createCorrection(originalId);

		// Then
		final I_C_TaxDeclaration correctionRecord = InterfaceWrapperHelper.load(correctionId, I_C_TaxDeclaration.class);
		Assertions.assertThat(correctionRecord).isNotNull();
		Assertions.assertThat(correctionRecord.isIsCorrection()).isTrue();
		Assertions.assertThat(correctionRecord.getC_TaxDeclaration_Original_ID()).isEqualTo(originalId.getRepoId());
		Assertions.assertThat(correctionRecord.getC_AcctSchema_ID()).isEqualTo(1);
		Assertions.assertThat(correctionRecord.getC_Period_ID()).isEqualTo(periodId);
		Assertions.assertThat(correctionRecord.getDateAcct()).isEqualTo(Timestamp.valueOf("2026-01-15 00:00:00"));
		Assertions.assertThat(correctionRecord.isProcessed()).isFalse();
		// Description is intentionally NOT set on a Correction (dropped per reviewer: the Original link
		// + IsCorrection flag already convey the relationship).
		Assertions.assertThat(correctionRecord.getDescription()).isNull();
	}
}
