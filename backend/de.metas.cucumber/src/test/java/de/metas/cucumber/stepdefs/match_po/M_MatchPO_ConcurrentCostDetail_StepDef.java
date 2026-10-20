package de.metas.cucumber.stepdefs.match_po;

import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDef;
import de.metas.order.MatchPOId;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_MatchPO;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reproduces the race between the async posting of an M_MatchPO and the deletion of that M_MatchPO when its material receipt is reversed:
 * the posting commits a new M_CostDetail for the M_MatchPO after {@code MMatchPO.beforeDelete()} cleaned up the M_MatchPO's cost details,
 * but before the M_MatchPO row is deleted.
 */
@RequiredArgsConstructor
public class M_MatchPO_ConcurrentCostDetail_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final M_MatchPO_StepDefData matchPOTable;
	@NonNull private final M_InOut_StepDef inOutStepDef;

	@Nullable private Integer concurrentCostDetailId;
	@Nullable private Throwable concurrentCommitError;

	@And("^while a concurrent transaction commits a new M_CostDetail for M_MatchPO (.*), the material receipt identified by (.*) is reversed as (.*)$")
	public void reverseWhileConcurrentCostDetailCommit(
			@NonNull final String matchPOIdentifier,
			@NonNull final String receiptIdentifier,
			@NonNull final String reversalIdentifier)
	{
		final MatchPOId matchPOId = matchPOTable.getId(matchPOIdentifier);
		try (final ConcurrentMatchPOCostDetailCommit concurrentCommit = ConcurrentMatchPOCostDetailCommit.insertUncommitted(matchPOId))
		{
			concurrentCostDetailId = concurrentCommit.getCostDetailId();
			M_CostDetail_BeforeDeleteHook.runWhileArmed(
					matchPOId,
					concurrentCommit::commitAndWaitUntilDoneOrBlockedBy,
					() -> inOutStepDef.reverseInOut("material receipt", receiptIdentifier, reversalIdentifier));
			concurrentCommitError = concurrentCommit.awaitCommitResult();
		}
	}

	@And("^the concurrent M_CostDetail commit failed, because M_MatchPO (.*) was deleted first$")
	public void assertConcurrentCommitFailed(@NonNull final String matchPOIdentifier)
	{
		final MatchPOId matchPOId = matchPOTable.getId(matchPOIdentifier);

		assertThat(concurrentCommitError)
				.as("commit of the concurrent M_CostDetail_ID=%s", concurrentCostDetailId)
				.isNotNull()
				.hasMessageContaining("mmatchpo_mcostdetail");
		assertThat(queryBL.createQueryBuilder(I_M_MatchPO.class)
				.addEqualsFilter(I_M_MatchPO.COLUMNNAME_M_MatchPO_ID, matchPOId)
				.create()
				.anyMatch())
				.as("M_MatchPO %s still exists", matchPOId)
				.isFalse();
		assertThat(queryBL.createQueryBuilder(I_M_CostDetail.class)
				.addEqualsFilter(I_M_CostDetail.COLUMNNAME_M_MatchPO_ID, matchPOId)
				.create()
				.anyMatch())
				.as("M_CostDetails of the deleted M_MatchPO %s", matchPOId)
				.isFalse();
	}
}
