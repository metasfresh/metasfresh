package de.metas.distribution.ddordercandidate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class DDOrderCandidateId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static DDOrderCandidateId ofRepoId(final int repoId) {return new DDOrderCandidateId(repoId);}

	@Nullable
	public static DDOrderCandidateId ofRepoIdOrNull(@Nullable final Integer repoId) {return repoId != null && repoId > 0 ? new DDOrderCandidateId(repoId) : null;}

	private DDOrderCandidateId(final int repoId) {this.repoId = Check.assumeGreaterThanZero(repoId, "DD_Order_Candidate_ID");}

	@JsonValue
	public int toJson() {return getRepoId();}

	public static int toRepoId(@Nullable final DDOrderCandidateId id) {return id != null ? id.getRepoId() : -1;}

	public static boolean equals(@Nullable final DDOrderCandidateId id1, @Nullable final DDOrderCandidateId id2) {return Objects.equals(id1, id2);}
}
