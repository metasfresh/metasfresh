package de.metas.acct.interceptor;

import de.metas.acct.tax.TaxDeclarationId;
import de.metas.acct.tax.TaxDeclarationRepository;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_TaxDeclaration.class)
@RequiredArgsConstructor
public class C_TaxDeclaration
{
	private static final AdMessageKey MSG_OriginalRequired = AdMessageKey.of("TaxDeclaration_OriginalRequired");
	private static final AdMessageKey MSG_OriginalMustBeOriginal = AdMessageKey.of("TaxDeclaration_OriginalMustBeOriginal");
	private static final AdMessageKey MSG_CorrectionInheritsPeriod = AdMessageKey.of("TaxDeclaration_CorrectionInheritsPeriod");
	private static final AdMessageKey MSG_TaxDeclaration_ProcessedLocked = AdMessageKey.of("TaxDeclaration_ProcessedLocked");

	@NonNull private final TaxDeclarationRepository taxDeclarationRepository;

	// IDocTypeBL is an ISingletonService — must NOT be ctor-injected; resolve via Services.get().
	@NonNull private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void defaultDocType(final I_C_TaxDeclaration taxDeclaration)
	{
		if (taxDeclaration.getC_DocType_ID() > 0)
		{
			return;
		}
		taxDeclaration.setC_DocType_ID(docTypeBL.getDocTypeId(DocTypeQuery.builder()
				.adClientId(taxDeclaration.getAD_Client_ID())
				.adOrgId(taxDeclaration.getAD_Org_ID())
				.docBaseType(DocBaseType.TaxDeclaration)
				.build()).getRepoId());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void enforceCorrectionInvariants(final I_C_TaxDeclaration td)
	{
		if (!td.isIsCorrection())
		{
			return;
		}
		if (td.getC_TaxDeclaration_Original_ID() <= 0)
		{
			throw new AdempiereException(MSG_OriginalRequired).markAsUserValidationError();
		}
		final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(td.getC_TaxDeclaration_Original_ID());
		final I_C_TaxDeclaration original = taxDeclarationRepository.getById(originalId);
		if (original.isIsCorrection())
		{
			throw new AdempiereException(MSG_OriginalMustBeOriginal).markAsUserValidationError();
		}
		if (td.getC_Period_ID() != original.getC_Period_ID()
				|| td.getC_AcctSchema_ID() != original.getC_AcctSchema_ID()
				|| !java.util.Objects.equals(td.getDateAcct(), original.getDateAcct()))
		{
			throw new AdempiereException(MSG_CorrectionInheritsPeriod).markAsUserValidationError();
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void lockProcessedDeclaration(final I_C_TaxDeclaration td)
	{
		// Once Processed='Y', prevent most field mutations except for IsCorrectionNeeded and CorrectionNeededReason.
		// This guard enforces AC#3 and AC#4 (prevents accidental edits to locked declarations).
		// Iter 8 drift-detector needs to modify IsCorrectionNeeded / CorrectionNeededReason even when Processed='Y'.
		if (!td.isProcessed())
		{
			return;
		}

		// Check critical disallowed columns. Other system columns (IsActive, Updated, etc.) are excluded from the check.
		final String[] disallowedColumns = {
			I_C_TaxDeclaration.COLUMNNAME_Description,
			I_C_TaxDeclaration.COLUMNNAME_C_Period_ID,
			I_C_TaxDeclaration.COLUMNNAME_DateAcct,
			I_C_TaxDeclaration.COLUMNNAME_C_AcctSchema_ID,
			I_C_TaxDeclaration.COLUMNNAME_C_TaxDeclaration_Original_ID,
			I_C_TaxDeclaration.COLUMNNAME_IsCorrection
		};

		// Check if ANY disallowed column changed
		for (final String columnName : disallowedColumns)
		{
			if (InterfaceWrapperHelper.isValueChanged(td, columnName))
			{
				throw new AdempiereException(MSG_TaxDeclaration_ProcessedLocked).markAsUserValidationError();
			}
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteTaxDeclarationLinesAndAccts(final I_C_TaxDeclaration taxDeclaration)
	{
		taxDeclarationRepository.deleteChildRows(TaxDeclarationId.ofRepoId(taxDeclaration.getC_TaxDeclaration_ID()));
	}
}
