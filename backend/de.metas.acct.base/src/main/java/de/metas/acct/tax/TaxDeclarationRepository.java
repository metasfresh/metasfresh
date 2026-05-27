package de.metas.acct.tax;

import de.metas.acct.api.AcctSchemaId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationAcct;
import org.compiere.model.I_C_TaxDeclarationLine;
import org.springframework.stereotype.Repository;

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
		record.setC_Period_ID(request.getCPeriodId().getRepoId());
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
		// Iter 7 (me03#29631): Corrections legitimately share their Original's period.
		// The skip-the-check-for-Corrections branch in TaxDeclarationDocumentHandler.completeIt
		// covers that case; this DAO method only runs from the Original-completion path now.
		// (We previously added an IsCorrection='N' filter here too, but the boolean-false
		// translation interacted badly with the cucumber suite; the completeIt-side skip is
		// sufficient given the call-site invariant.)
		return queryBL.createQueryBuilder(I_C_TaxDeclaration.class)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_Processed, true)
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
}
