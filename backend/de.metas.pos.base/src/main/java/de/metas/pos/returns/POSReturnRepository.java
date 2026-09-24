package de.metas.pos.returns;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository Tables: M_InOut
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
}
