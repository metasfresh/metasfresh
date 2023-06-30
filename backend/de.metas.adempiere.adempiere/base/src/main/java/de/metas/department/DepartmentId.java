package de.metas.department;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_M_Department;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * M_Department_ID
 */
@Value
public class DepartmentId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static DepartmentId ofRepoId(final int repoId)
	{
		return new DepartmentId(repoId);
	}

	@Nullable
	public static DepartmentId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new DepartmentId(repoId) : null;
	}

	@Nullable
	public static DepartmentId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new DepartmentId(repoId) : null;
	}

	public static Optional<DepartmentId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final DepartmentId DepartmentId)
	{
		return toRepoIdOr(DepartmentId, -1);
	}

	public static int toRepoIdOr(@Nullable final DepartmentId DepartmentId, final int defaultValue)
	{
		return DepartmentId != null ? DepartmentId.getRepoId() : defaultValue;
	}

	private DepartmentId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_M_Department.COLUMNNAME_M_Department_ID);
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
