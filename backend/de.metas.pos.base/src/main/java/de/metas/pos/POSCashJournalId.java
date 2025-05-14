package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class POSCashJournalId implements RepoIdAware
{
	int repoId;

	private POSCashJournalId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_POS_CashJournal_ID");
	}

	@JsonCreator
	public static POSCashJournalId ofRepoId(final int repoId)
	{
		return new POSCashJournalId(repoId);
	}

	@Nullable
	public static POSCashJournalId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new POSCashJournalId(repoId) : null;
	}

	public static int toRepoId(@Nullable final POSCashJournalId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final POSCashJournalId id1, @Nullable final POSCashJournalId id2)
	{
		return Objects.equals(id1, id2);
	}
}
