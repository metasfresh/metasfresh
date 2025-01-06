package de.metas.payment.sumup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class SumUpTransactionId implements RepoIdAware
{
	@JsonCreator
	public static SumUpTransactionId ofRepoId(final int repoId)
	{
		return new SumUpTransactionId(repoId);
	}

	@Nullable
	public static SumUpTransactionId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new SumUpTransactionId(repoId) : null;
	}

	public static int toRepoId(@Nullable final SumUpTransactionId SumUpTransactionId)
	{
		return SumUpTransactionId != null ? SumUpTransactionId.getRepoId() : -1;
	}

	int repoId;

	private SumUpTransactionId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "SUMUP_Transaction_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
