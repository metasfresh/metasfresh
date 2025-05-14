package de.metas.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class ProjectIssueId implements RepoIdAware
{
	@JsonCreator
	public static ProjectIssueId ofRepoId(final int repoId)
	{
		return new ProjectIssueId(repoId);
	}

	@Nullable
	public static ProjectIssueId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static int toRepoId(@Nullable final ProjectIssueId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private ProjectIssueId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_ProjectIssue_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final ProjectIssueId id1, @Nullable final ProjectIssueId id2)
	{
		return Objects.equals(id1, id2);
	}
}
