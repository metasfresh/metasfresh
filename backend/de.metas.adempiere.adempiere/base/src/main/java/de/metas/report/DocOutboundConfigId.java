package de.metas.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class DocOutboundConfigId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static DocOutboundConfigId ofRepoId(final int repoId)
	{
		return new DocOutboundConfigId(repoId);
	}

	@Nullable
	public static DocOutboundConfigId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new DocOutboundConfigId(repoId) : null;
	}

	@Nullable
	public static DocOutboundConfigId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new DocOutboundConfigId(repoId) : null;
	}

	public static int toRepoId(@Nullable final DocOutboundConfigId docOutboundConfigId)
	{
		return docOutboundConfigId != null ? docOutboundConfigId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final DocOutboundConfigId o1, @Nullable final DocOutboundConfigId o2)
	{
		return Objects.equals(o1, o2);
	}

	private DocOutboundConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Doc_Outbound_Condifg_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
