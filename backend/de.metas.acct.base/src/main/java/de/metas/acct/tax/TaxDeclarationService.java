package de.metas.acct.tax;

import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

@Service
public class TaxDeclarationService
{
	private static final AdMessageKey MSG_TaxDeclaration_AlreadyProcessed = AdMessageKey.of("TaxDeclaration_AlreadyProcessed");
	private static final AdMessageKey MSG_TaxDeclaration_CreateCorrection_OriginalNotLocked = AdMessageKey.of("TaxDeclaration_CreateCorrection_OriginalNotLocked");

	@NonNull private final TaxDeclarationRepository taxDeclarationRepository;

	public TaxDeclarationService(@NonNull final TaxDeclarationRepository taxDeclarationRepository)
	{
		this.taxDeclarationRepository = taxDeclarationRepository;
	}

	public void build(@NonNull final TaxDeclarationId id)
	{
		final I_C_TaxDeclaration record = taxDeclarationRepository.getById(id);
		if (record.isProcessed())
		{
			throw new AdempiereException(MSG_TaxDeclaration_AlreadyProcessed).markAsUserValidationError();
		}

		DB.executeFunctionCallEx(
				ITrx.TRXNAME_ThreadInherited,
				"SELECT de_metas_acct.tax_declaration_build(?)",
				new Object[] { id });
	}

	/**
	 * Spawn a new Correction declaration anchored to {@code originalId}. The new declaration
	 * starts in NEW state ({@code Processed='N'}) and inherits {@code (C_AcctSchema_ID,
	 * C_Period_ID, DateAcct)} from the Original (interceptor in iter 7 enforces equality).
	 *
	 * @throws AdempiereException if the Original is not yet locked ({@code Processed='Y'})
	 * @throws AdempiereException if the Original is itself a Correction (star topology)
	 */
	public TaxDeclarationId createCorrection(@NonNull final TaxDeclarationId originalId)
	{
		final I_C_TaxDeclaration original = taxDeclarationRepository.getById(originalId);
		if (!original.isProcessed())
		{
			throw new AdempiereException(MSG_TaxDeclaration_CreateCorrection_OriginalNotLocked)
					.markAsUserValidationError();
		}
		if (original.isIsCorrection())
		{
			throw new AdempiereException(AdMessageKey.of("TaxDeclaration_OriginalMustBeOriginal"))
					.markAsUserValidationError();
		}

		final I_C_TaxDeclaration correction = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		correction.setAD_Org_ID(original.getAD_Org_ID());
		correction.setC_AcctSchema_ID(original.getC_AcctSchema_ID());
		correction.setC_Period_ID(original.getC_Period_ID());
		correction.setDateAcct(original.getDateAcct());
		correction.setIsCorrection(true);
		correction.setC_TaxDeclaration_Original_ID(originalId.getRepoId());
		final String prefix = "Correction of ";
		final String origDesc = original.getDescription();
		correction.setDescription(origDesc != null ? prefix + origDesc : prefix.trim());
		correction.setProcessed(false);
		InterfaceWrapperHelper.save(correction);
		return TaxDeclarationId.ofRepoId(correction.getC_TaxDeclaration_ID());
	}
}
