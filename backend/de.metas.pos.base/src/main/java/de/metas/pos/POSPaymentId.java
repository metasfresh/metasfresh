package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class POSPaymentId implements RepoIdAware
{
	int repoId;

	private POSPaymentId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_POS_Payment_ID");
	}

	@JsonCreator
	public static POSPaymentId ofRepoId(final int repoId)
	{
		return new POSPaymentId(repoId);
	}

	@Nullable
	public static POSPaymentId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new POSPaymentId(repoId) : null;
	}

	public static int toRepoId(@Nullable final POSPaymentId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final POSPaymentId id1, @Nullable final POSPaymentId id2)
	{
		return Objects.equals(id1, id2);
	}
}
