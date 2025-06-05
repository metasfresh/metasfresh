package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@Builder
@Value
public class HUConsolidationJob
{
	@NonNull HUConsolidationJobId id;
	@NonNull BPartnerLocationId shipToBPLocationId;
	@NonNull ImmutableSet<PickingSlotId> pickingSlotIds;

	@NonNull @With HUConsolidationJobStatus docStatus;
	@Nullable @With UserId responsibleId;
	@Nullable @With HUConsolidationTarget currentTarget;

	public static boolean equals(@Nullable HUConsolidationJob job1, @Nullable HUConsolidationJob job2) {return Objects.equals(job1, job2);}

	public void assertUserCanEdit(final UserId callerId)
	{
		if (this.responsibleId != null && !UserId.equals(this.responsibleId, callerId))
		{
			throw new AdempiereException("User has no permissions");
		}
	}

	public BPartnerId getCustomerId() {return shipToBPLocationId.getBpartnerId();}

	@NonNull
	public HUConsolidationTarget getCurrentTargetNotNull()
	{
		return Check.assumeNotNull(currentTarget, "job has a current target");
	}

	public boolean isProcessed() {return docStatus.isProcessed();}

}
