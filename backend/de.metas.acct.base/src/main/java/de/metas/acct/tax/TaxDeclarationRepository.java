package de.metas.acct.tax;

import de.metas.acct.api.AcctSchemaId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationAcct;
import org.compiere.model.I_C_TaxDeclarationLine;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class TaxDeclarationRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_C_TaxDeclaration getById(@NonNull final TaxDeclarationId id)
	{
		final I_C_TaxDeclaration record = InterfaceWrapperHelper.load(id, I_C_TaxDeclaration.class);
		if (record == null)
		{
			throw new AdempiereException("No C_TaxDeclaration found for id=" + id.getRepoId());
		}
		return record;
	}

	/**
	 * Pure CRUD: persist a new {@code C_TaxDeclaration} row from the given request and return its id.
	 * No guards, no business logic — callers are responsible for validating the request first.
	 */
	public TaxDeclarationId createTaxDeclaration(@NonNull final TaxDeclarationCreateRequest request)
	{
		final I_C_TaxDeclaration record = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		record.setAD_Org_ID(request.getAdOrgId().getRepoId());
		record.setC_AcctSchema_ID(request.getAcctSchemaId().getRepoId());
		record.setC_Period_ID(request.getPeriodRepoId());
		record.setDateAcct(request.getDateAcct());
		record.setIsCorrection(request.isCorrection());
		record.setC_TaxDeclaration_Original_ID(TaxDeclarationId.toRepoId(request.getOriginalId()));
		record.setProcessed(false);
		InterfaceWrapperHelper.save(record);
		return TaxDeclarationId.ofRepoId(record.getC_TaxDeclaration_ID());
	}

	public void deleteChildRows(@NonNull final TaxDeclarationId id)
	{
		queryBL.createQueryBuilder(I_C_TaxDeclarationAcct.class)
				.addEqualsFilter(I_C_TaxDeclarationAcct.COLUMNNAME_C_TaxDeclaration_ID, id)
				.create()
				.deleteDirectly();
		queryBL.createQueryBuilder(I_C_TaxDeclarationLine.class)
				.addEqualsFilter(I_C_TaxDeclarationLine.COLUMNNAME_C_TaxDeclaration_ID, id)
				.create()
				.deleteDirectly();
	}

	public boolean hasAnyLines(@NonNull final TaxDeclarationId id)
	{
		return queryBL.createQueryBuilder(I_C_TaxDeclarationLine.class)
				.addEqualsFilter(I_C_TaxDeclarationLine.COLUMNNAME_C_TaxDeclaration_ID, id)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	public boolean existsCompletedOverlappingPeriod(
			@NonNull final TaxDeclarationId selfId,
			@NonNull final AcctSchemaId acctSchemaId,
			final int periodId)
	{
		// Period-uniqueness rule: at most one declaration with IsCorrection='N' and
		// Processed='Y' may exist per (C_AcctSchema_ID, C_Period_ID). Corrections
		// (IsCorrection='Y') legitimately share their Original's period and must NOT count,
		// otherwise a completed Correction would wrongly block completing a new Original.
		return queryBL.createQueryBuilder(I_C_TaxDeclaration.class)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_Processed, true)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_IsCorrection, false)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_C_Period_ID, periodId)
				.addNotEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_C_TaxDeclaration_ID, selfId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	/**
	 * @return true iff at least one ACTIVE C_TaxDeclaration row has
	 *         C_TaxDeclaration_Original_ID = originalId (regardless of Processed status —
	 *         Reopen must be blocked even by a NEW Correction).
	 */
	public boolean existsCorrectionFor(@NonNull final TaxDeclarationId originalId)
	{
		return queryBL.createQueryBuilder(I_C_TaxDeclaration.class)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_C_TaxDeclaration_Original_ID, originalId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	/**
	 * @return true iff at least one ACTIVE, NOT-yet-Processed Correction (IsCorrection='Y',
	 *         Processed='N') exists for {@code originalId}, excluding {@code excludeId} when non-null.
	 */
	public boolean hasUnprocessedCorrectionFor(
			@NonNull final TaxDeclarationId originalId,
			@Nullable final TaxDeclarationId excludeId)
	{
		final IQueryBuilder<I_C_TaxDeclaration> builder = queryBL.createQueryBuilder(I_C_TaxDeclaration.class)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_C_TaxDeclaration_Original_ID, originalId)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_IsCorrection, true)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_Processed, false)
				.addOnlyActiveRecordsFilter();
		if (excludeId != null)
		{
			builder.addNotEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_C_TaxDeclaration_ID, excludeId);
		}
		return builder.create().anyMatch();
	}

	/**
	 * Returns the latest LIVE (Processed='Y') Correction in the chain rooted at {@code originalId},
	 * ordered by Created DESC; falls back to the Original itself if no completed Correction exists.
	 */
	public I_C_TaxDeclaration getLatestInChain(@NonNull final TaxDeclarationId originalId)
	{
		final I_C_TaxDeclaration latestCorrection = queryBL.createQueryBuilder(I_C_TaxDeclaration.class)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_C_TaxDeclaration_Original_ID, originalId)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_IsCorrection, true)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_Processed, true)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_C_TaxDeclaration.COLUMNNAME_Created)
				.create()
				.first();
		return latestCorrection != null ? latestCorrection : getById(originalId);
	}

	/**
	 * @return true iff {@code id} is the latest LIVE entry in its correction chain
	 *         (an Original with no Processed Correction, or the most recent Processed Correction).
	 */
	public boolean isLatestInChain(@NonNull final TaxDeclarationId id)
	{
		final I_C_TaxDeclaration record = getById(id);
		final TaxDeclarationId originalId = record.isCorrection()
				? TaxDeclarationId.ofRepoId(record.getC_TaxDeclaration_Original_ID())
				: id;
		final I_C_TaxDeclaration latest = getLatestInChain(originalId);
		return TaxDeclarationId.ofRepoId(latest.getC_TaxDeclaration_ID()).equals(id);
	}
}
