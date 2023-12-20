package de.metas.acct.gljournal_sap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@RepoIdAwares.SkipTest
public class SAPGLJournalLineId implements RepoIdAware
{
	@JsonCreator
	public static SAPGLJournalLineId ofRepoId(@NonNull final SAPGLJournalId glJournalId, final int repoId)
	{
		return new SAPGLJournalLineId(glJournalId, repoId);
	}

	@JsonCreator
	public static SAPGLJournalLineId ofRepoId(final int glJournalRepoId, final int repoId)
	{
		return new SAPGLJournalLineId(SAPGLJournalId.ofRepoId(glJournalRepoId), repoId);
	}

	public static SAPGLJournalLineId ofRepoIdOrNull(@Nullable final SAPGLJournalId glJournalId, final int repoId)
	{
		return glJournalId != null && repoId > 0 ? new SAPGLJournalLineId(glJournalId, repoId) : null;
	}

	public static SAPGLJournalLineId ofRepoIdOrNull(final int glJournalRepoId, final int repoId)
	{
		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoIdOrNull(glJournalRepoId);
		return ofRepoIdOrNull(glJournalId, repoId);
	}

	@NonNull SAPGLJournalId glJournalId;
	int repoId;

	private SAPGLJournalLineId(final @NonNull SAPGLJournalId glJournalId, final int repoId)
	{
		this.glJournalId = glJournalId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "SAP_GLJournalLine_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final SAPGLJournalLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final SAPGLJournalLineId id1, @Nullable final SAPGLJournalLineId id2)
	{
		return Objects.equals(id1, id2);
	}
}
