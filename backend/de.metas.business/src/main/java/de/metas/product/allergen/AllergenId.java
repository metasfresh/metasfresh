package de.metas.product.allergen;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class AllergenId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static AllergenId ofRepoId(final int repoId)
	{
		return new AllergenId(repoId);
	}

	@Nullable
	public static AllergenId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new AllergenId(repoId) : null;
	}

	@Nullable
	public static AllergenId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new AllergenId(repoId) : null;
	}

	public static int toRepoId(@Nullable final AllergenId AllergenId)
	{
		return AllergenId != null ? AllergenId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final AllergenId o1, @Nullable final AllergenId o2)
	{
		return Objects.equals(o1, o2);
	}

	private AllergenId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Allergen_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
