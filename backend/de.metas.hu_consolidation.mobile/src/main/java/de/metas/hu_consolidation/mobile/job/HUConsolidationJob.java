package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;

@Builder
@Value
public class HUConsolidationJob
{
	@NonNull HUConsolidationJobId id;
	@NonNull BPartnerLocationId shipToAddress;
	@NonNull ImmutableSet<PickingSlotId> pickingSlotIds;

	@Nullable @With UserId responsibleId;
	@Nullable @With HUConsolidationTarget currentTarget;

	public BPartnerId getCustomerId() {return shipToAddress.getBpartnerId();}
}
