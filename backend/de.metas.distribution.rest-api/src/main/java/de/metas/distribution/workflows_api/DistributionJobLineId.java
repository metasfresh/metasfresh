package de.metas.distribution.workflows_api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
public class DistributionJobLineId
{
	@NonNull private final DDOrderLineId ddOrderLineId;

	private DistributionJobLineId(@NonNull final DDOrderLineId ddOrderLineId)
	{
		this.ddOrderLineId = ddOrderLineId;
	}

	public static DistributionJobLineId ofDDOrderLineId(final @NonNull DDOrderLineId ddOrderLineId)
	{
		return new DistributionJobLineId(ddOrderLineId);
	}

	@JsonCreator
	public static DistributionJobLineId ofJson(final @NonNull Object json)
	{
		final DDOrderLineId ddOrderLineId = RepoIdAwares.ofObject(json, DDOrderLineId.class, DDOrderLineId::ofRepoId);
		return ofDDOrderLineId(ddOrderLineId);
	}

	@JsonValue
	public String toString()
	{
		return toJson();
	}

	@NonNull
	private String toJson()
	{
		return String.valueOf(ddOrderLineId.getRepoId());
	}

	public DDOrderLineId toDDOrderLineId() {return ddOrderLineId;}

	public static boolean equals(@Nullable final DistributionJobLineId id1, @Nullable final DistributionJobLineId id2) {return Objects.equals(id1, id2);}
}
