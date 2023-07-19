package de.metas.acct.gljournal_sap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class SAPGLJournalId implements RepoIdAware
{
	@JsonCreator
	public static SAPGLJournalId ofRepoId(final int repoId)
	{
		return new SAPGLJournalId(repoId);
	}

	public static SAPGLJournalId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new SAPGLJournalId(repoId) : null;
	}

	int repoId;

	private SAPGLJournalId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "SAP_GLJournal_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final SAPGLJournalId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final SAPGLJournalId id1, @Nullable final SAPGLJournalId id2)
	{
		return Objects.equals(id1, id2);
	}
}
