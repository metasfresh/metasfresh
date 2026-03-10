package de.metas.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class DocOutboundConfigCCId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static DocOutboundConfigCCId ofRepoId(final int repoId)
	{
		return new DocOutboundConfigCCId(repoId);
	}

	@Nullable
	public static DocOutboundConfigCCId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new DocOutboundConfigCCId(repoId) : null;
	}

	@Nullable
	public static DocOutboundConfigCCId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new DocOutboundConfigCCId(repoId) : null;
	}

	public static int toRepoId(@Nullable final DocOutboundConfigCCId docOutboundConfigCCId)
	{
		return docOutboundConfigCCId != null ? docOutboundConfigCCId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final DocOutboundConfigCCId o1, @Nullable final DocOutboundConfigCCId o2)
	{
		return Objects.equals(o1, o2);
	}

	private DocOutboundConfigCCId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Doc_Outbound_Condifg_CC_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
