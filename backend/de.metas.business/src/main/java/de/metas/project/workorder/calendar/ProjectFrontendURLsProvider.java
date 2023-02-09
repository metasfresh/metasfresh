package de.metas.project.workorder.calendar;

import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.logging.LogManager;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.ui.web.WebuiURLs;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Project;
import org.slf4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Optional;

class ProjectFrontendURLsProvider
{
	private static final Logger logger = LogManager.getLogger(ProjectFrontendURLsProvider.class);

	private final WebuiURLs webuiURLs = WebuiURLs.newInstance();

	private final HashMap<ProjectCategory, Optional<AdWindowId>> adWindowIdByProjectCategory = new HashMap<>();

	public Optional<URI> getProjectUrl(@NonNull final ProjectCategory projectCategory, @NonNull final ProjectId projectId)
	{
		return getAdWindowIdByProjectCategoryId(projectCategory, projectId)
				.flatMap(adWindowId -> computeProjectUrl(projectId, adWindowId));
	}

	private Optional<AdWindowId> getAdWindowIdByProjectCategoryId(
			@NonNull final ProjectCategory projectCategory,
			@NonNull final ProjectId testProjectId)
	{
		// NOTE: we have to cache AD_Window_IDs by project category
		// because if not, in case of high amount of entries (e.g. 700),
		// calling computeAdWindowIdByProjectId might be expensive.
		return adWindowIdByProjectCategory.computeIfAbsent(projectCategory, (k) -> computeAdWindowIdByProjectId(testProjectId));
	}

	@NonNull
	private Optional<URI> computeProjectUrl(final @NonNull ProjectId projectId, final @NonNull AdWindowId adWindowId)
	{
		final String url = webuiURLs.getDocumentUrl(adWindowId, projectId.getRepoId());
		if (url == null)
		{
			return Optional.empty();
		}

		try
		{
			return Optional.of(new URI(url));
		}
		catch (final URISyntaxException e)
		{
			logger.warn("Failed converting `{}` to URI", url, e);
			return Optional.empty();
		}
	}

	private Optional<AdWindowId> computeAdWindowIdByProjectId(@NonNull final ProjectId projectId)
	{
		return RecordWindowFinder.newInstance(TableRecordReference.of(I_C_Project.Table_Name, projectId))
				.checkRecordPresentInWindow() // IMPORTANT: make sure the right Project window is picked
				.findAdWindowId();
	}
}
