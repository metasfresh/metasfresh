package de.metas.acct.tax.document;

import de.metas.acct.tax.TaxDeclarationId;
import de.metas.acct.tax.TaxDeclarationRepository;
import de.metas.document.engine.DocumentTableFields;
import de.metas.i18n.AdMessageKey;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_TaxDeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;

class TaxDeclarationDocumentHandlerTest
{
	private TaxDeclarationDocumentHandler handler;
	private TaxDeclarationRepository repository;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new TaxDeclarationRepository();
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

	private DocumentTableFields createDocumentTableFields(final I_C_TaxDeclaration taxDeclaration)
	{
		final DocumentTableFields fields = Mockito.mock(DocumentTableFields.class);
		Mockito.when(fields.get_ValueAsInt("C_TaxDeclaration_ID")).thenReturn(taxDeclaration.getC_TaxDeclaration_ID());
		Mockito.when(fields.getFieldValue("C_TaxDeclaration_ID")).thenReturn(taxDeclaration.getC_TaxDeclaration_ID());
		// Return the full record so InterfaceWrapperHelper.create can work
		Mockito.when(fields.getPO()).thenReturn(taxDeclaration);
		return fields;
	}

	// ---------------------------------------------------------------------------
	// reactivateIt tests
	// ---------------------------------------------------------------------------

	@Test
	public void reactivateIt_throws_whenCorrectionExists()
	{
		// Given: an Original + a Correction (draft, but still active)
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(original.getC_TaxDeclaration_ID());

		createTaxDeclaration(true, originalId.getRepoId(), false, true); // IsActive=true, Processed=false

		final DocumentTableFields docFields = createDocumentTableFields(original);

		// When / Then
		Assertions.assertThatThrownBy(() -> handler.reactivateIt(docFields))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_HasCorrections");
	}

	@Test
	public void reactivateIt_succeeds_whenNoCorrection()
	{
		// Given: an Original with no Correction
		final I_C_TaxDeclaration original = createTaxDeclaration(false, 0, true, true);
		final DocumentTableFields docFields = createDocumentTableFields(original);

		// When: reactivateIt completes normally
		handler.reactivateIt(docFields);

		// Then: verify the Original's state changed (Processed=false, DocAction='Complete')
		final I_C_TaxDeclaration reloaded = InterfaceWrapperHelper.reload(original);
		Assertions.assertThat(reloaded.isProcessed()).isFalse();
		Assertions.assertThat(reloaded.getDocAction()).isEqualTo("Complete");
	}
}
