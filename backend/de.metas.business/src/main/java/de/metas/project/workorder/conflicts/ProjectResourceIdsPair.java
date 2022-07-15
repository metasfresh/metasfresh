package de.metas.project.workorder.conflicts;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.project.workorder.WOProjectAndResourceId;
import de.metas.project.workorder.WOProjectResourceId;
import lombok.NonNull;
import lombok.Value;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ProjectResourceIdsPair
		implements Comparable<ProjectResourceIdsPair> // needed to json serialization which is needed for snapshot testing
{
	@NonNull WOProjectAndResourceId projectResourceId1;
	@NonNull WOProjectAndResourceId projectResourceId2;

	public static ProjectResourceIdsPair of(
			@NonNull WOProjectAndResourceId projectResourceId1,
			@NonNull WOProjectAndResourceId projectResourceId2)
	{
		// SUPER IMPORTANT: always keep the IDs in order because IDs order shall not be important,
		// ie. (id1, id2) shall be equal with (id2, id1)

		return projectResourceId1.getProjectResourceId().getRepoId() < projectResourceId2.getProjectResourceId().getRepoId()
				? new ProjectResourceIdsPair(projectResourceId1, projectResourceId2)
				: new ProjectResourceIdsPair(projectResourceId2, projectResourceId1);
	}

	public boolean isMatching(@NonNull final WOProjectResourceId projectResourceId)
	{
		return WOProjectResourceId.equals(this.projectResourceId1.getProjectResourceId(), projectResourceId)
				|| WOProjectResourceId.equals(this.projectResourceId2.getProjectResourceId(), projectResourceId);
	}

	@Override
	public int compareTo(final ProjectResourceIdsPair other)
	{
		int cmp = this.projectResourceId1.getProjectResourceId().getRepoId()
				- other.projectResourceId1.getProjectResourceId().getRepoId();
		if (cmp != 0)
		{
			return cmp;
		}

		return this.projectResourceId2.getProjectResourceId().getRepoId()
				- other.projectResourceId2.getProjectResourceId().getRepoId();
	}
}
