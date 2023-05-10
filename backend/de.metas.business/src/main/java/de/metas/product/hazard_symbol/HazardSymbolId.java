package de.metas.product.hazard_symbol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class HazardSymbolId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static HazardSymbolId ofRepoId(final int repoId)
	{
		return new HazardSymbolId(repoId);
	}

	@Nullable
	public static HazardSymbolId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new HazardSymbolId(repoId) : null;
	}

	@Nullable
	public static HazardSymbolId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new HazardSymbolId(repoId) : null;
	}

	public static int toRepoId(@Nullable final HazardSymbolId HazardSymbolId)
	{
		return HazardSymbolId != null ? HazardSymbolId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final HazardSymbolId o1, @Nullable final HazardSymbolId o2)
	{
		return Objects.equals(o1, o2);
	}

	private HazardSymbolId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_HazardSymbol_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
