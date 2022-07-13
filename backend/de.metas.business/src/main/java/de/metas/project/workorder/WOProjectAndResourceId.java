package de.metas.project.workorder;

import de.metas.project.ProjectId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value(staticConstructor = "of")
public class WOProjectAndResourceId
{
	@NonNull ProjectId projectId;
	@NonNull WOProjectResourceId projectResourceId;

	public static WOProjectAndResourceId ofRepoIds(
			final int projectRepoId,
			final int projectResourceRepoId)
	{
		return of(
				ProjectId.ofRepoId(projectRepoId),
				WOProjectResourceId.ofRepoId(projectResourceRepoId));
	}

	public static boolean equals(@Nullable final WOProjectAndResourceId id1, @Nullable final WOProjectAndResourceId id2) {return Objects.equals(id1, id2);}
}
