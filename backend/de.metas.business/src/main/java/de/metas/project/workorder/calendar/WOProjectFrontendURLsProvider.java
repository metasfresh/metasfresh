package de.metas.project.workorder.calendar;

import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.logging.LogManager;
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

class WOProjectFrontendURLsProvider
{
	private static final Logger logger = LogManager.getLogger(WOProjectFrontendURLsProvider.class);

	private final WebuiURLs webuiURLs = WebuiURLs.newInstance();

	private final HashMap<ProjectId, Optional<URI>> cache = new HashMap<>();

	public Optional<URI> getFrontendURL(final ProjectId projectId)
	{
		return cache.computeIfAbsent(projectId, this::computeFrontendURL);
	}

	private Optional<URI> computeFrontendURL(@NonNull final ProjectId projectId)
	{
		final AdWindowId adWindowId = RecordWindowFinder.newInstance(TableRecordReference.of(I_C_Project.Table_Name, projectId))
				.checkRecordPresentInWindow() // IMPORTANT: make sure the right Project window is picked
				.findAdWindowId()
				.orElse(null);
		if (adWindowId == null)
		{
			return Optional.empty();
		}

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
}
