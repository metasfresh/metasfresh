package de.metas.payment.sumup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class SumUpConfigId implements RepoIdAware
{
	@JsonCreator
	public static SumUpConfigId ofRepoId(final int repoId)
	{
		return new SumUpConfigId(repoId);
	}

	@Nullable
	public static SumUpConfigId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new SumUpConfigId(repoId) : null;
	}

	public static int toRepoId(@Nullable final SumUpConfigId SumUpConfigId)
	{
		return SumUpConfigId != null ? SumUpConfigId.getRepoId() : -1;
	}

	int repoId;

	private SumUpConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "SUMUP_Config_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
