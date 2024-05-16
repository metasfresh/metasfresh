package de.metas.distribution.ddorder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class DDOrderId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static DDOrderId ofRepoId(final int repoId)
	{
		return new DDOrderId(repoId);
	}

	@Nullable
	public static DDOrderId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new DDOrderId(repoId) : null;
	}

	private DDOrderId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "DD_Order_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static int toRepoId(@Nullable final DDOrderId ddOrderId) {return ddOrderId != null ? ddOrderId.getRepoId() : -1;}
}
