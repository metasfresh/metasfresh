package de.metas.material.planning.ddorder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DistributionNetworkAndLineId
{
	@NonNull DistributionNetworkId networkId;
	@NonNull DistributionNetworkLineId lineId;

	public static DistributionNetworkAndLineId of(final DistributionNetworkId ddNetworkDistributionId, final DistributionNetworkLineId ddNetworkDistributionLineId)
	{
		return new DistributionNetworkAndLineId(ddNetworkDistributionId, ddNetworkDistributionLineId);
	}

	public static DistributionNetworkAndLineId ofRepoIds(final int ddNetworkDistributionId, final int ddNetworkDistributionLineId)
	{
		return of(DistributionNetworkId.ofRepoId(ddNetworkDistributionId), DistributionNetworkLineId.ofRepoId(ddNetworkDistributionLineId));
	}

	@Nullable
	public static DistributionNetworkAndLineId ofRepoIdsOrNull(final int ddNetworkDistributionRepoId, final int ddNetworkDistributionLineRepoId)
	{
		final DistributionNetworkId networkId = DistributionNetworkId.ofRepoIdOrNull(ddNetworkDistributionRepoId);
		if (networkId == null)
		{
			return null;
		}

		final DistributionNetworkLineId lineId = DistributionNetworkLineId.ofRepoIdOrNull(ddNetworkDistributionLineRepoId);
		if (lineId == null)
		{
			return null;
		}

		return of(networkId, lineId);
	}

	public static Optional<DistributionNetworkAndLineId> optionalOfRepoIds(final int ddNetworkDistributionRepoId, final int ddNetworkDistributionLineRepoId)
	{
		return Optional.ofNullable(ofRepoIdsOrNull(ddNetworkDistributionRepoId, ddNetworkDistributionLineRepoId));
	}
}
