package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.order.OrderId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class POSConfigId implements RepoIdAware
{
	int repoId;

	private POSConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_POS_ID");
	}

	@JsonCreator
	public static POSConfigId ofRepoId(final int repoId)
	{
		return new POSConfigId(repoId);
	}

	@Nullable
	public static POSConfigId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new POSConfigId(repoId) : null;
	}

	public static int toRepoId(@Nullable final OrderId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final POSConfigId id1, @Nullable final POSConfigId id2)
	{
		return Objects.equals(id1, id2);
	}
}
