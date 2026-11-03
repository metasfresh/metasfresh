package de.metas.acct.interceptor;

import de.metas.acct.tax.TaxDeclarationRepository;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatNoException;

class C_TaxDeclarationInterceptorTest
{
	private TaxDeclarationRepository repository;
	private C_TaxDeclaration interceptor;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new TaxDeclarationRepository();
		interceptor = new C_TaxDeclaration(repository);
	}

	// ---------------------------------------------------------------------------
	// Helper
	// ---------------------------------------------------------------------------

	private I_C_TaxDeclaration createTaxDeclaration(
			final boolean isCorrection,
			final int originalId,
			final int periodId,
			final int acctSchemaId,
			final Timestamp dateAcct)
	{
		final I_C_TaxDeclaration record = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		record.setIsCorrection(isCorrection);
		record.setC_TaxDeclaration_Original_ID(originalId);
		record.setC_Period_ID(periodId);
		record.setC_AcctSchema_ID(acctSchemaId);
		record.setDateAcct(dateAcct);
		record.setProcessed(false);
		record.setIsActive(true);
		record.setDocAction("CO");
		record.setDocStatus("DR");
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private static final Timestamp DATE_ACCT = Timestamp.valueOf("2025-01-01 00:00:00");
	private static final int PERIOD_ID = 10;
	private static final int ACCT_SCHEMA_ID = 100;

	// ---------------------------------------------------------------------------
	// Tests
	// ---------------------------------------------------------------------------

	@Test
	public void enforceCorrectionInvariants_passes_whenNotACorrection()
	{
		// Given: a non-Correction record
		final I_C_TaxDeclaration td = createTaxDeclaration(false, 0, PERIOD_ID, ACCT_SCHEMA_ID, DATE_ACCT);

		// When / Then: no exception
		assertThatNoException().isThrownBy(() -> interceptor.enforceCorrectionInvariants(td));
	}

	@Test
	public void enforceCorrectionInvariants_throws_whenOriginalRequired()
	{
		// Given: IsCorrection='Y' but no Original set.
		// The row is NOT saved here: the interceptor (BEFORE_NEW) is the guard under test, and it must
		// reject this combination before it ever reaches the DB star-topology CHECK. We invoke the
		// interceptor directly on an unsaved instance (same pattern as the other invariant tests below).
		final I_C_TaxDeclaration td = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		td.setIsCorrection(true);
		td.setC_TaxDeclaration_Original_ID(0);
		td.setC_Period_ID(PERIOD_ID);
		td.setC_AcctSchema_ID(ACCT_SCHEMA_ID);
		td.setDateAcct(DATE_ACCT);
		td.setDocAction("CO");
		td.setDocStatus("DR");

		// When / Then: must throw with OriginalRequired message
		assertThatThrownBy(() -> interceptor.enforceCorrectionInvariants(td))
				.hasMessageContaining("TaxDeclaration_OriginalRequired");
	}

	@Test
	public void enforceCorrectionInvariants_throws_whenStarTopologyViolated()
	{
		// Given: Original is itself a Correction (chain of corrections — not allowed)
		final I_C_TaxDeclaration grandOriginal = createTaxDeclaration(false, 0, PERIOD_ID, ACCT_SCHEMA_ID, DATE_ACCT);
		final I_C_TaxDeclaration original = createTaxDeclaration(true, grandOriginal.getC_TaxDeclaration_ID(), PERIOD_ID, ACCT_SCHEMA_ID, DATE_ACCT);

		// New correction pointing to 'original' which is itself a correction
		final I_C_TaxDeclaration td = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		td.setIsCorrection(true);
		td.setC_TaxDeclaration_Original_ID(original.getC_TaxDeclaration_ID());
		td.setC_Period_ID(PERIOD_ID);
		td.setC_AcctSchema_ID(ACCT_SCHEMA_ID);
		td.setDateAcct(DATE_ACCT);
		td.setDocAction("CO");
		td.setDocStatus("DR");
		// Note: NOT saved — interceptor is called BEFORE_NEW; we call it directly without saving

		// When / Then: must throw with OriginalMustBeOriginal message
		assertThatThrownBy(() -> interceptor.enforceCorrectionInvariants(td))
				.hasMessageContaining("TaxDeclaration_OriginalMustBeOriginal");
	}

	@Test
	public void enforceCorrectionInvariants_throws_whenPeriodNotInherited()
	{
		// Given: a valid (non-correction) Original
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, PERIOD_ID, ACCT_SCHEMA_ID, DATE_ACCT);

		// Correction with different C_Period_ID
		final I_C_TaxDeclaration td = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		td.setIsCorrection(true);
		td.setC_TaxDeclaration_Original_ID(original.getC_TaxDeclaration_ID());
		td.setC_Period_ID(PERIOD_ID + 99); // different period
		td.setC_AcctSchema_ID(ACCT_SCHEMA_ID);
		td.setDateAcct(DATE_ACCT);
		td.setDocAction("CO");
		td.setDocStatus("DR");

		// When / Then: must throw with CorrectionInheritsPeriod message
		assertThatThrownBy(() -> interceptor.enforceCorrectionInvariants(td))
				.hasMessageContaining("TaxDeclaration_CorrectionInheritsPeriod");
	}

	@Test
	public void enforceCorrectionInvariants_passes_whenInheritanceCorrect()
	{
		// Given: a valid (non-correction) Original
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, PERIOD_ID, ACCT_SCHEMA_ID, DATE_ACCT);

		// Correction with matching (C_Period_ID, DateAcct, C_AcctSchema_ID)
		final I_C_TaxDeclaration td = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		td.setIsCorrection(true);
		td.setC_TaxDeclaration_Original_ID(original.getC_TaxDeclaration_ID());
		td.setC_Period_ID(PERIOD_ID);
		td.setC_AcctSchema_ID(ACCT_SCHEMA_ID);
		td.setDateAcct(DATE_ACCT);
		td.setDocAction("CO");
		td.setDocStatus("DR");

		// When / Then: no exception
		assertThatNoException().isThrownBy(() -> interceptor.enforceCorrectionInvariants(td));
	}

}
