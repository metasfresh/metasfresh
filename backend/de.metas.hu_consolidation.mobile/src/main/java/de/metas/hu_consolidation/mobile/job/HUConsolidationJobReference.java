package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.slot.PickingSlotQueuesSummary;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.lang.RepoIdAwares;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;

import javax.annotation.Nullable;
import java.util.OptionalInt;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class HUConsolidationJobReference
{
	@NonNull BPartnerLocationId bpartnerLocationId;
	@NonNull @Singular ImmutableSet<PickingSlotId> pickingSlotIds;
	@Nullable Integer countHUs;
	@Nullable HUConsolidationJobId startedJobId;

	public static class HUConsolidationJobReferenceBuilder
	{
		@SuppressWarnings("UnusedReturnValue")
		public HUConsolidationJobReferenceBuilder addToCountHUs(final int countHUsToAdd)
		{
			this.countHUs = this.countHUs == null ? countHUsToAdd : this.countHUs + countHUsToAdd;
			return this;
		}
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
			@Nullable final HUConsolidationJobId startedJobId = params.getParameterAsId("startedJobId", HUConsolidationJobId.class);

			return builder()
					.bpartnerLocationId(bpartnerLocationId)
					.pickingSlotIds(pickingSlotIds != null ? pickingSlotIds : ImmutableSet.of())
					.countHUs(params.getParameterAsInt("countHUs", 0))
					.startedJobId(startedJobId)
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid params: " + params, ex);
		}
	}

	public static HUConsolidationJobReference ofJob(@NonNull final HUConsolidationJob job)
	{
		return builder()
				.bpartnerLocationId(job.getShipToBPLocationId())
				.pickingSlotIds(job.getPickingSlotIds())
				.countHUs(null)
				.startedJobId(job.getId())
				.build();
	}

	public Params toParams()
	{
		return Params.builder()
				.value("bpartnerId", bpartnerLocationId.getBpartnerId().getRepoId())
				.value("bpartnerLocationId", bpartnerLocationId.getRepoId())
				.value("pickingSlotIds", RepoIdAwares.toCommaSeparatedString(pickingSlotIds))
				.value("countHUs", countHUs)
				.value("startedJobId", startedJobId != null ? startedJobId.getRepoId() : null)
				.build();
	}

	public boolean isStatsMissing()
	{
		return countHUs == null;
	}

	public OptionalInt getCountHUs()
	{
		return countHUs != null ? OptionalInt.of(countHUs) : OptionalInt.empty();
	}

	public HUConsolidationJobReference withUpdatedStats(@NonNull final PickingSlotQueuesSummary summary)
	{
		final OptionalInt optionalCountHUs = summary.getCountHUs(pickingSlotIds);
		if (optionalCountHUs.isPresent())
		{
			final int countHUsNew = optionalCountHUs.getAsInt();
			if (this.countHUs == null || this.countHUs != countHUsNew)
			{
				return toBuilder().countHUs(countHUsNew).build();
			}
			else
			{
				return this;
			}
		}
		else
		{
			return this;
		}
	}

}
