package de.metas.acct.tax;

import de.metas.acct.api.AcctSchemaId;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_TaxDeclaration;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class TaxDeclarationService
{
	private static final AdMessageKey MSG_TaxDeclaration_AlreadyProcessed = AdMessageKey.of("TaxDeclaration_AlreadyProcessed");
	private static final AdMessageKey MSG_TaxDeclaration_CreateCorrection_OriginalNotLocked = AdMessageKey.of("TaxDeclaration_CreateCorrection_OriginalNotLocked");
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
		taxDeclarationRepository.runBuild(id);
	}

	public I_C_TaxDeclaration getById(@NonNull final TaxDeclarationId id)
	{
		return taxDeclarationRepository.getById(id);
	}

	public boolean isLatestInChain(@NonNull final TaxDeclarationId id)
	{
		return taxDeclarationRepository.isLatestInChain(id);
	}

	public boolean hasUnprocessedCorrectionFor(@NonNull final TaxDeclarationId originalId, @Nullable final TaxDeclarationId excludeId)
	{
		return taxDeclarationRepository.hasUnprocessedCorrectionFor(originalId, excludeId);
	}

	public void checkDrift(@NonNull final TaxDeclarationId id)
	{
		checkDrift(getById(id));
	}

	public void checkDrift(@NonNull final I_C_TaxDeclaration record)
	{
		final boolean isDriftDetected = taxDeclarationRepository.isDriftDetected(
				TaxDeclarationId.ofRepoId(record.getC_TaxDeclaration_ID()));
		record.setIsCorrectionNeeded(isDriftDetected);
		taxDeclarationRepository.save(record);
	}

	/**
	 * Create a Correction for the chain that {@code anyChainMemberId} belongs to.
	 * Resolves to the root Original, rejects when an unprocessed draft Correction already exists, runs the drift
	 * check against the latest-in-chain snapshot, and rejects when no correction is needed. Returns the id of the
	 * newly spawned (still draft) Correction.
	 */
	public TaxDeclarationId createCorrection(@NonNull final TaxDeclarationId anyChainMemberId)
	{
		final I_C_TaxDeclaration record = getById(anyChainMemberId);
		if (!record.isProcessed())
		{
			throw new AdempiereException(MSG_TaxDeclaration_CreateCorrection_OriginalNotLocked);
		}

		final TaxDeclarationId originalId = record.isCorrection()
				? TaxDeclarationId.ofRepoId(record.getC_TaxDeclaration_Original_ID())
				: anyChainMemberId;

		if (taxDeclarationRepository.hasUnprocessedCorrectionFor(originalId, null))
		{
			throw new AdempiereException(MSG_TaxDeclaration_CreateCorrection_DraftExists);
		}

		// Drift is evaluated against the latest-in-chain snapshot (most recent completed correction, or the
		// original if none): once a correction is completed it — not the stale original — represents current truth.
		final I_C_TaxDeclaration latest = taxDeclarationRepository.getLatestInChain(originalId);
		checkDrift(latest);
		if (!latest.isCorrectionNeeded())
		{
			throw new AdempiereException(MSG_TaxDeclaration_CreateCorrection_NoCorrectionNeeded);
		}

		final I_C_TaxDeclaration original = getById(originalId);
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
