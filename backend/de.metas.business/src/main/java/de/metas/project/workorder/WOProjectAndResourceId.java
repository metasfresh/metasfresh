package de.metas.project.workorder;

import com.google.common.collect.ImmutableSet;
import de.metas.project.ProjectId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

@Value(staticConstructor = "of")
public class WOProjectAndResourceId
{
	@NonNull ProjectId projectId;
	@NonNull WOProjectResourceId projectResourceId;

	public static WOProjectAndResourceId ofRepoIds(
			final int projectRepoId,
			final int projectResourceRepoId)
	{
		final ProjectId projectId = ProjectId.ofRepoId(projectRepoId);
		return of(
				projectId,
				WOProjectResourceId.ofRepoId(projectId, projectResourceRepoId));
	}

	public static boolean equals(@Nullable final WOProjectAndResourceId id1, @Nullable final WOProjectAndResourceId id2) {return Objects.equals(id1, id2);}

	public static ImmutableSet<WOProjectResourceId> unbox(@NonNull final Set<WOProjectAndResourceId> projectAndResourceIds)
	{
		if (projectAndResourceIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		else
		{
			return projectAndResourceIds.stream().map(WOProjectAndResourceId::getProjectResourceId).collect(ImmutableSet.toImmutableSet());
		}
	}
}
