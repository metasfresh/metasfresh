package org.eevolution.productioncandidate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class PPOrderLineCandidateId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static PPOrderLineCandidateId ofRepoId(final int repoId)
	{
		return new PPOrderLineCandidateId(repoId);
	}

	@Nullable
	public static PPOrderLineCandidateId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new PPOrderLineCandidateId(repoId) : null;
	}

	private PPOrderLineCandidateId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_OrderLine_Candidate_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable final PPOrderLineCandidateId id1, @Nullable final PPOrderLineCandidateId id2) {return Objects.equals(id1, id2);}

}
