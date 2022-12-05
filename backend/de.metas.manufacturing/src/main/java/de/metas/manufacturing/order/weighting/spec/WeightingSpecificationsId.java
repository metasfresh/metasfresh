package de.metas.manufacturing.order.weighting.spec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class WeightingSpecificationsId implements RepoIdAware
{
	@JsonCreator
	public static WeightingSpecificationsId ofRepoId(final int repoId)
	{
		return new WeightingSpecificationsId(repoId);
	}

	public static WeightingSpecificationsId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new WeightingSpecificationsId(repoId) : null;
	}

	public static Optional<WeightingSpecificationsId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final WeightingSpecificationsId WeightingSpecificationsId)
	{
		return WeightingSpecificationsId != null ? WeightingSpecificationsId.getRepoId() : -1;
	}

	int repoId;

	private WeightingSpecificationsId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Weighting_Spec_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable WeightingSpecificationsId id1, @Nullable WeightingSpecificationsId id2)
	{
		return Objects.equals(id1, id2);
	}
}
