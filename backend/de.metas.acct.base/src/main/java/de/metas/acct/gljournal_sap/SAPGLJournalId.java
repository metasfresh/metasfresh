package de.metas.acct.gljournal_sap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

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

	@NonNull
	public static Optional<SAPGLJournalId> ofRepoIdOptional(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
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

	public static int toRepoId(@Nullable final SAPGLJournalId SAPGLJournalId)
	{
		return SAPGLJournalId != null ? SAPGLJournalId.getRepoId() : -1;
	}
}
