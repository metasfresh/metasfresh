package de.metas.pos.returns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.document.engine.DocStatus;
import de.metas.inout.InOutId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository Tables: M_InOut, C_Invoice_Candidate
 * Repository Cluster: POSReturnRepository, POSReturnService
 */
@Repository
public class POSReturnRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Looks up an existing POS-return {@code M_InOut} by its {@code ExternalId} — so a retried request (same
	 * {@link POSReturnRequest#getExternalId()}) does not create a second return document.
	 *
	 * <p>Only an active, non-reversed and non-voided document counts as "existing": a reversed/voided return no
	 * longer represents the credit, so a retry after a reversal must create a fresh one rather than resolve back
	 * to the dead document.
	 */
	@NonNull
	public Optional<InOutId> findReturnIdByExternalId(@NonNull final String externalId)
	{
		return queryBL.createQueryBuilder(I_M_InOut.class)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_ExternalId, externalId)
				.addOnlyActiveRecordsFilter()
				.addNotInArrayFilter(I_M_InOut.COLUMNNAME_DocStatus, ImmutableList.of(DocStatus.Voided.getCode(), DocStatus.Reversed.getCode()))
				.create()
				.firstIdOnlyOptional(InOutId::ofRepoIdOrNull);
	}

	/**
	 * Batch-loads every invoice candidate created directly for any of the given return-line IDs (one query for
	 * the whole return document, instead of one query per line) — grouped by the line's own {@code M_InOutLine_ID}.
	 * Mirrors the "direct" match {@code IInvoiceCandDAO#retrieveInvoiceCandidatesForInOutLine} itself uses
	 * ({@code AD_Table_ID}/{@code Record_ID} pointing at the line); a POS return has no {@code C_OrderLine_ID}
	 * to match against, so that other match kind does not apply here.
	 */
	@NonNull
	public ImmutableListMultimap<Integer, I_C_Invoice_Candidate> findInvoiceCandidatesByInOutLineId(@NonNull final Collection<Integer> inOutLineIds)
	{
		if (inOutLineIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		final int inOutLineTableId = InterfaceWrapperHelper.getTableId(I_M_InOutLine.class);
		final ImmutableList<I_C_Invoice_Candidate> candidates = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, inOutLineTableId)
				.addInArrayFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, inOutLineIds)
				.create()
				.listImmutable(I_C_Invoice_Candidate.class);

		return candidates.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(I_C_Invoice_Candidate::getRecord_ID, ic -> ic));
	}
}
