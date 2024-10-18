package de.metas.pos.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class POSProductCategoryId implements RepoIdAware
{
	int repoId;

	private POSProductCategoryId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_POS_ProductCategory_ID");
	}

	@JsonCreator
	public static POSProductCategoryId ofRepoId(final int repoId)
	{
		return new POSProductCategoryId(repoId);
	}

	public static POSProductCategoryId ofString(final String repoIdStr)
	{
		return RepoIdAwares.ofObject(repoIdStr, POSProductCategoryId.class, POSProductCategoryId::ofRepoId);
	}

	@Nullable
	public static POSProductCategoryId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new POSProductCategoryId(repoId) : null;
	}

	public static int toRepoId(@Nullable final POSProductCategoryId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final POSProductCategoryId id1, @Nullable final POSProductCategoryId id2)
	{
		return Objects.equals(id1, id2);
	}
}
