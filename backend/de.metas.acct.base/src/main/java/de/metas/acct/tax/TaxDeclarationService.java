package de.metas.acct.tax;

import de.metas.acct.api.AcctSchemaId;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxDeclarationService
{
	private static final AdMessageKey MSG_TaxDeclaration_AlreadyProcessed = AdMessageKey.of("TaxDeclaration_AlreadyProcessed");
	private static final AdMessageKey MSG_TaxDeclaration_CreateCorrection_OriginalNotLocked = AdMessageKey.of("TaxDeclaration_CreateCorrection_OriginalNotLocked");
	private static final AdMessageKey MSG_TaxDeclaration_OriginalMustBeOriginal = AdMessageKey.of("TaxDeclaration_OriginalMustBeOriginal");
	private static final AdMessageKey MSG_TaxDeclaration_CreateCorrection_DraftExists = AdMessageKey.of("TaxDeclaration_CreateCorrection_DraftExists");
	private static final AdMessageKey MSG_TaxDeclaration_CreateCorrection_NoCorrectionNeeded = AdMessageKey.of("TaxDeclaration_CreateCorrection_NoCorrectionNeeded");

	@NonNull private final TaxDeclarationRepository taxDeclarationRepository;

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

	public void checkDrift(@NonNull final TaxDeclarationId id)
	{
		final I_C_TaxDeclaration record = taxDeclarationRepository.getById(id);

		final int result = DB.getSQLValueEx(
				ITrx.TRXNAME_ThreadInherited,
				"SELECT CASE WHEN de_metas_acct.tax_declaration_check_drift(?) THEN 1 ELSE 0 END",
				new Object[] { id.getRepoId() });
		final boolean isDriftDetected = result == 1;

		record.setIsCorrectionNeeded(isDriftDetected);
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * Spawns a Correction for {@code originalId}, inheriting its acctSchema/period/dateAcct.
	 */
	public TaxDeclarationId createCorrection(@NonNull final TaxDeclarationId originalId)
	{
		final I_C_TaxDeclaration original = taxDeclarationRepository.getById(originalId);
		if (!original.isProcessed())
		{
			throw new AdempiereException(MSG_TaxDeclaration_CreateCorrection_OriginalNotLocked);
		}
		if (original.isCorrection())
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

	/**
	 * Create a Correction for the chain that {@code anyChainMemberId} belongs to, after verifying a correction is actually needed.
	 * Resolves to the root Original, rejects when an unprocessed draft Correction already exists, runs the drift check,
	 * and rejects when no drift is detected. Returns the id of the newly spawned (still draft) Correction.
	 */
	public TaxDeclarationId createCorrectionWithDriftCheck(@NonNull final TaxDeclarationId anyChainMemberId)
	{
		final I_C_TaxDeclaration record = taxDeclarationRepository.getById(anyChainMemberId);
		final TaxDeclarationId originalId = record.isCorrection()
				? TaxDeclarationId.ofRepoId(record.getC_TaxDeclaration_Original_ID())
				: anyChainMemberId;

		if (taxDeclarationRepository.hasUnprocessedCorrectionFor(originalId, null))
		{
			throw new AdempiereException(MSG_TaxDeclaration_CreateCorrection_DraftExists);
		}

		// Drift is evaluated against the latest-in-chain snapshot (most recent completed correction, or the
		// original if none): once a correction is completed it — not the stale original — represents current truth.
		final TaxDeclarationId latestId = TaxDeclarationId.ofRepoId(
				taxDeclarationRepository.getLatestInChain(originalId).getC_TaxDeclaration_ID());
		checkDrift(latestId);
		final I_C_TaxDeclaration latest = taxDeclarationRepository.getById(latestId);
		if (!latest.isCorrectionNeeded())
		{
			throw new AdempiereException(MSG_TaxDeclaration_CreateCorrection_NoCorrectionNeeded);
		}

		return createCorrection(originalId);
	}
}
