package de.metas.invoice.proforma;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class ProformaOrderAllocId implements RepoIdAware
{
	@JsonCreator
	public static ProformaOrderAllocId ofRepoId(final int repoId)
	{
		return new ProformaOrderAllocId(repoId);
	}

	@Nullable
	public static ProformaOrderAllocId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<ProformaOrderAllocId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private ProformaOrderAllocId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_ProformaOrderAlloc_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final ProformaOrderAllocId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final ProformaOrderAllocId id1, @Nullable final ProformaOrderAllocId id2)
	{
		return Objects.equals(id1, id2);
	}
}
