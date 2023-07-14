package de.metas.acct.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class FactAcctId implements RepoIdAware
{
	int repoId;

	private FactAcctId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "Fact_Acct_ID");
	}

	@JsonCreator
	public static FactAcctId ofRepoId(final int repoId) {return new FactAcctId(repoId);}

	@Nullable
	public static FactAcctId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static Optional<FactAcctId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static int toRepoId(@Nullable final FactAcctId id) {return id != null ? id.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final FactAcctId id1, @Nullable final FactAcctId id2) {return Objects.equals(id1, id2);}
}
