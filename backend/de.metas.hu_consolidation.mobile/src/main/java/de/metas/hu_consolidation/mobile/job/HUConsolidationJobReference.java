package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.lang.RepoIdAwares;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;

import javax.annotation.Nullable;

@Value
@Builder
public class HUConsolidationJobReference
{
	@NonNull BPartnerLocationId bpartnerLocationId;
	@NonNull @Singular ImmutableSet<PickingSlotId> pickingSlotIds;

	public Params toParams()
	{
		return Params.builder()
				.value("bpartnerId", bpartnerLocationId.getBpartnerId().getRepoId())
				.value("bpartnerLocationId", bpartnerLocationId.getRepoId())
				.value("pickingSlotIds", RepoIdAwares.toCommaSeparatedString(pickingSlotIds))
				.build();
	}

	public static HUConsolidationJobReference ofParams(final Params params)
	{
		try
		{
			final BPartnerId bpartnerId = params.getParameterAsId("bpartnerId", BPartnerId.class);
			if (bpartnerId == null)
			{
				throw new AdempiereException("bpartnerId not set");
			}

			@Nullable final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, params.getParameterAsInt("bpartnerLocationId", -1));
			@Nullable final ImmutableSet<PickingSlotId> pickingSlotIds = RepoIdAwares.ofCommaSeparatedSet(params.getParameterAsString("pickingSlotIds"), PickingSlotId.class);

			return builder()
					.bpartnerLocationId(bpartnerLocationId)
					.pickingSlotIds(pickingSlotIds != null ? pickingSlotIds : ImmutableSet.of())
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid params: " + params, ex);
		}
	}

}
