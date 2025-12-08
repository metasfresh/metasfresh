package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.order.OrderId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class POSTerminalId implements RepoIdAware
{
	int repoId;

	private POSTerminalId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_POS_ID");
	}

	public static POSTerminalId ofRepoId(final int repoId)
	{
		return new POSTerminalId(repoId);
	}

	@JsonCreator
	public static POSTerminalId ofString(@NonNull final String str)
	{
		return RepoIdAwares.ofObject(str, POSTerminalId.class, POSTerminalId::ofRepoId);
	}

	@Nullable
	public static POSTerminalId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new POSTerminalId(repoId) : null;
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

	public static boolean equals(@Nullable final POSTerminalId id1, @Nullable final POSTerminalId id2)
	{
		return Objects.equals(id1, id2);
	}
}
