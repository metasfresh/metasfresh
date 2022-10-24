package de.metas.project.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.project.ProjectId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Project;
import org.junit.jupiter.api.Disabled;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Disabled
public class PlainProjectRepository extends ProjectRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public ImmutableList<ProjectId> getProjectIdsUpStream(final @NonNull ProjectId startFromProjectId)
	{
		final ImmutableSet<ProjectId> projectIdsDownStream = getProjectIdsDownStream(ImmutableSet.of(startFromProjectId));
		return ImmutableList.copyOf(projectIdsDownStream);
	}

	@Override
	public ImmutableSet<ProjectId> getProjectIdsUpStream(@NonNull final Collection<ProjectId> startFromProjectIds)
	{
		final ArrayList<ProjectId> upstreamProjectIds = new ArrayList<>();

		for (ProjectId startFromProjectId : startFromProjectIds)
		{
			ProjectId projectId = startFromProjectId;
			while (projectId != null)
			{
				if (upstreamProjectIds.contains(projectId))
				{
					break;
				}
				upstreamProjectIds.add(projectId);

				final I_C_Project project = Check.assumeNotNull(getRecordById(projectId), "NO project found for " + projectId);
				projectId = ProjectId.ofRepoIdOrNull(project.getC_Project_Parent_ID());
			}
		}

		return ImmutableSet.copyOf(upstreamProjectIds);
	}

	@Override
	public ImmutableSet<ProjectId> getProjectIdsDownStream(final @NonNull Collection<ProjectId> projectIds)
	{
		if (projectIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final List<I_C_Project> projectRecords = InterfaceWrapperHelper.loadByRepoIdAwares(ImmutableSet.copyOf(projectIds), I_C_Project.class);
		return getProjectIdsDownStreamFromRecords(projectRecords);
	}

	public ImmutableSet<ProjectId> getProjectIdsDownStreamFromRecords(final @NonNull List<I_C_Project> projectRecords)
	{
		if (projectRecords.isEmpty())
		{
			return ImmutableSet.of();
		}

		final LinkedHashSet<ProjectId> downstreamProjectIds = new LinkedHashSet<>();

		for (final I_C_Project project : projectRecords)
		{
			final ProjectId projectId = ProjectId.ofRepoId(project.getC_Project_ID());
			downstreamProjectIds.add(projectId);

			final List<I_C_Project> childProjects = getChildProjectRecords(projectId);
			final ImmutableSet<ProjectId> childProjectIds = getProjectIdsDownStreamFromRecords(childProjects);
			downstreamProjectIds.addAll(childProjectIds);
		}

		return ImmutableSet.copyOf(downstreamProjectIds);
	}

	private List<I_C_Project> getChildProjectRecords(final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addEqualsFilter(I_C_Project.COLUMNNAME_C_Project_Parent_ID, projectId)
				.create()
				.list();
	}
}