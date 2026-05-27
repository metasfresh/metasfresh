package de.metas.acct.tax;

import de.metas.acct.api.AcctSchemaId;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

@Service
public class TaxDeclarationService
{
	private static final AdMessageKey MSG_TaxDeclaration_AlreadyProcessed = AdMessageKey.of("TaxDeclaration_AlreadyProcessed");
	private static final AdMessageKey MSG_TaxDeclaration_CreateCorrection_OriginalNotLocked = AdMessageKey.of("TaxDeclaration_CreateCorrection_OriginalNotLocked");
	private static final AdMessageKey MSG_TaxDeclaration_OriginalMustBeOriginal = AdMessageKey.of("TaxDeclaration_OriginalMustBeOriginal");

	@NonNull private final TaxDeclarationRepository taxDeclarationRepository;

	public TaxDeclarationService(
			@NonNull final TaxDeclarationRepository taxDeclarationRepository)
	{
		this.taxDeclarationRepository = taxDeclarationRepository;
	}

	public void build(@NonNull final TaxDeclarationId id)
	{
		final I_C_TaxDeclaration record = taxDeclarationRepository.getById(id);
		if (record.isProcessed())
		{
			throw new AdempiereException(MSG_TaxDeclaration_AlreadyProcessed);
		}

		DB.executeFunctionCallEx(
				ITrx.TRXNAME_ThreadInherited,
				"SELECT de_metas_acct.tax_declaration_build(?)",
				new Object[] { id });
	}

	/** Spawns a Correction for {@code originalId}, inheriting its acctSchema/period/dateAcct. */
	public TaxDeclarationId createCorrection(@NonNull final TaxDeclarationId originalId)
	{
		final I_C_TaxDeclaration original = taxDeclarationRepository.getById(originalId);
		if (!original.isProcessed())
		{
			throw new AdempiereException(MSG_TaxDeclaration_CreateCorrection_OriginalNotLocked);
		}
		if (original.isIsCorrection())
		{
			throw new AdempiereException(MSG_TaxDeclaration_OriginalMustBeOriginal);
		}

		return taxDeclarationRepository.createTaxDeclaration(TaxDeclarationCreateRequest.builder()
				.adOrgId(OrgId.ofRepoId(original.getAD_Org_ID()))
				.acctSchemaId(AcctSchemaId.ofRepoId(original.getC_AcctSchema_ID()))
				.periodRepoId(original.getC_Period_ID())
				.dateAcct(original.getDateAcct())
				.isCorrection(true)
				.originalId(originalId)
				.build());
	}
}
