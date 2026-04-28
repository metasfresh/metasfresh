package de.metas.ui.web.window.health;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;
import de.metas.ui.web.window.health.json.JsonWindowHealthCheckRequest;
import de.metas.ui.web.window.health.json.JsonWindowsHealthCheckResponse;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.util.Env;
import org.slf4j.Logger;

public class WindowHealthCheckCommand
{
	//
	// Services
	@NonNull private static final Logger logger = LogManager.getLogger(WindowHealthCheckCommand.class);
	@NonNull private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	@NonNull private final DocumentDescriptorFactory documentDescriptorFactory;

	//
	// Constants
	private static final int MAX_ERRORS = 4000;

	//
	// Params
	@NonNull private final ImmutableSet<AdWindowId> onlyAdWindowIds;
	private final boolean checkContextVariables;

	//
	// State
	@NonNull private final ErrorsCollector errorsCollector;
	@NonNull private final ContextVariables rootContextVariables;
	@NonNull private final MissingContextVariables missingContextVariables;

	@Builder
	private WindowHealthCheckCommand(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final JsonWindowHealthCheckRequest request)
	{
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.onlyAdWindowIds = request.getOnlyAdWindowIds() != null && !request.getOnlyAdWindowIds().isEmpty()
				? ImmutableSet.copyOf(request.getOnlyAdWindowIds())
				: ImmutableSet.of();
		this.errorsCollector = ErrorsCollector.builder()
				.adLanguage(Env.getADLanguageOrBaseLanguage())
				.build();
		this.checkContextVariables = request.getCheckContextVariables() != null ? request.getCheckContextVariables() : true;
		this.rootContextVariables = ContextVariables.newGlobalContext()
				.withKnownMissing(request.getKnownContextVariables());
		this.missingContextVariables = MissingContextVariables.builder()
				.knownMissing(request.getKnownMissingContextVariables())
				.build();
	}

	public JsonWindowsHealthCheckResponse execute()
	{
		final AdWindowIdSelection adWindowIdSelection = retrieveAdWindowIdSelection();
		missingContextVariables.setAdWindowIdsInScope(adWindowIdSelection);

		final int countTotal = adWindowIdSelection.size();
		int countCurrent = 0;
		final Stopwatch overallStopwatch = Stopwatch.createStarted();
		for (final AdWindowId adWindowId : adWindowIdSelection)
		{
			errorsCollector.setCurrentWindow(adWindowId);
			countCurrent++;

			final WindowId windowId = WindowId.of(adWindowId);
			final Stopwatch windowStopwatch = Stopwatch.createStarted();
			try
			{
				if (!adWindowIdSelection.isExistingActiveWindow(adWindowId))
				{
					errorsCollector.collectError("Not an existing/active window");
					continue;
				}

				documentDescriptorFactory.invalidateForWindow(windowId);
				final DocumentDescriptor documentDescriptor = documentDescriptorFactory.getDocumentDescriptor(windowId);
				final DocumentEntityDescriptor entityDescriptor = documentDescriptor.getEntityDescriptor();
				errorsCollector.setCurrentWindow(entityDescriptor);
				documentDescriptorFactory.invalidateForWindow(windowId);

				checkContextVariables(entityDescriptor);

				windowStopwatch.stop();
				logger.info("testWindows [{}/{}] Window `{}` ({}) is OK (took: {})",
						countCurrent, countTotal, errorsCollector.getCurrentWindowName(), AdWindowId.toRepoId(errorsCollector.getCurrentWindowId()), windowStopwatch);
			}
			catch (final Exception ex)
			{
				windowStopwatch.stop();
				logger.info("testWindows [{}/{}] Window `{}` ({}) is NOK: {} (took {})",
						countCurrent, countTotal, errorsCollector.getCurrentWindowName(), AdWindowId.toRepoId(errorsCollector.getCurrentWindowId()), windowStopwatch, ex.getLocalizedMessage());
				errorsCollector.collectError(DocumentLayoutBuildException.extractCause(ex));
			}
			finally
			{
				errorsCollector.clearCurrentWindow();

				System.gc();

				// final Runtime runtime = Runtime.getRuntime();
				// long totalBefore = runtime.totalMemory();
				// long freeBefore = runtime.freeMemory();
				// long usedBefore = totalBefore - freeBefore;
				// logger.info("Memory: Total/Used/Free={}/{}/{}", formatBytes(totalBefore), formatBytes(usedBefore), formatBytes(freeBefore));
			}

			if (errorsCollector.getCollectedErrorsCount() > MAX_ERRORS)
			{
				logger.info("Stop checking because we reached the maximum number of errors: {}. Stopping at window: {}", MAX_ERRORS, errorsCollector.getCurrentWindowName());
				break;
			}
		}

		overallStopwatch.stop();

		return JsonWindowsHealthCheckResponse.builder()
				.took(overallStopwatch.toString())
				.countTotal(countTotal)
				.errors(errorsCollector.getCollectedErrors())
				.contextVariables(missingContextVariables.toJsonContextVariablesResponse())
				.build();
	}

	private AdWindowIdSelection retrieveAdWindowIdSelection()
	{
		return AdWindowIdSelection.builder()
				.allAdWidowIds(adWindowDAO.retrieveAllActiveAdWindowIds())
				.onlyAdWindowIds(onlyAdWindowIds)
				.build();
	}

	private void checkContextVariables(final DocumentEntityDescriptor entityDescriptor)
	{
		if (!checkContextVariables) {return;}
		
		ContextVariablesCheckCommand.builder()
				.missingContextVariables(missingContextVariables)
				.errorsCollector(errorsCollector)
				.rootContextVariables(rootContextVariables)
				.rootEntityDescriptor(entityDescriptor)
				.build()
				.execute();
	}

	// private static String formatBytes(long bytes) {return String.format("%.2f MB", bytes / (1024.0 * 1024.0));}
}
