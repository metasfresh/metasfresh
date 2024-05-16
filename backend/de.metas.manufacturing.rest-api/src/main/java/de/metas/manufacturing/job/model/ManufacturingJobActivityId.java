package de.metas.manufacturing.job.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivityId;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class ManufacturingJobActivityId implements RepoIdAware
{
	int repoId;

	private ManufacturingJobActivityId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@JsonCreator
	@NonNull
	public static ManufacturingJobActivityId ofRepoId(final int repoId) {return new ManufacturingJobActivityId(repoId);}

	@Nullable
	public static ManufacturingJobActivityId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static ManufacturingJobActivityId of(final PPOrderRoutingActivityId ppOrderRoutingActivityId) {return ofRepoId(ppOrderRoutingActivityId.getRepoId());}

	public static int toRepoId(@Nullable final ManufacturingJobActivityId id) {return id != null ? id.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	public static boolean equals(@Nullable final ManufacturingJobActivityId id1, @Nullable final ManufacturingJobActivityId id2) {return Objects.equals(id1, id2);}

	public PPOrderRoutingActivityId toPPOrderRoutingActivityId(final PPOrderId ppOrderId)
	{
		return PPOrderRoutingActivityId.ofRepoId(ppOrderId, getRepoId());
	}
}
