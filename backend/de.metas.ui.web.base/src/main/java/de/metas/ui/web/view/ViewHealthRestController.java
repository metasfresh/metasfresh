package de.metas.ui.web.view;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JsonWindowsHealthResponse;
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value = ViewHealthRestController.ENDPOINT)
@RequiredArgsConstructor
public class ViewHealthRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/view";

	@NonNull private static final Logger logger = LogManager.getLogger(ViewRestController.class);
	@NonNull private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	@NonNull private final UserSession userSession;
	@NonNull private final IViewsRepository viewsRepo;

	@GetMapping("/health")
	public JsonWindowsHealthResponse healthCheck(
			@RequestParam(name = "windowIds", required = false) final String windowIdsCommaSeparated
	)
	{
		userSession.assertLoggedIn();
		final String adLanguage = userSession.getAD_Language();
		//final IUserRolePermissions permissions = userSession.getUserRolePermissions();

		final ImmutableSet<AdWindowId> skipAdWindowIds = ImmutableSet.of(
				// AdWindowId.ofRepoId(540371), // Picking Tray Clearing - placeholder window
				// AdWindowId.ofRepoId(540674), // Shipment Schedule Editor - placeholder window
				// AdWindowId.ofRepoId(540759), // Payment Allocation - placeholder window
				// AdWindowId.ofRepoId(540485) // Picking Terminal (v2) - placeholder window
		);

		final ImmutableSet<AdWindowId> onlyAdWindowIds = RepoIdAwares.ofCommaSeparatedSet(windowIdsCommaSeparated, AdWindowId.class);
		final ImmutableSet<AdWindowId> allAdWidowIds = adWindowDAO.retrieveAllActiveAdWindowIds();
		final ImmutableSet<AdWindowId> adWindowIds = !onlyAdWindowIds.isEmpty() ? onlyAdWindowIds : allAdWidowIds;

		final ArrayList<JsonWindowsHealthResponse.Entry> skipped = new ArrayList<>();
		final ArrayList<JsonWindowsHealthResponse.Entry> errors = new ArrayList<>();
		final int countTotal = adWindowIds.size();
		int countCurrent = 0;
		final Stopwatch stopwatch = Stopwatch.createStarted();
		for (final AdWindowId adWindowId : adWindowIds)
		{
			countCurrent++;

			final WindowId windowId = WindowId.of(adWindowId);

			if (skipAdWindowIds.contains(adWindowId))
			{
				skipped.add(JsonWindowsHealthResponse.Entry.builder()
						.windowId(windowId)
						.errorMessage("Programmatically skipped")
						.build());
				continue;
			}

			try
			{
				if (!allAdWidowIds.contains(adWindowId))
				{
					throw new AdempiereException("Not an existing/active window");
				}

				// if (!permissions.checkWindowPermission(adWindowId).hasReadAccess())
				// {
				// 	skipped.add(JsonWindowsHealthResponse.Entry.builder()
				// 			.windowId(windowId)
				// 			.errorMessage("No permissions")
				// 			.build());
				// 	continue;
				// }

				final ViewLayout viewLayout = viewsRepo.getViewLayout(windowId, JSONViewDataType.grid, null, null);

				logger.info("healthCheck [{}/{}] View {} ({}) is OK", countCurrent, countTotal, viewLayout.getCaption(adLanguage), windowId);
			}
			catch (Exception ex)
			{
				final String windowName = adWindowDAO.retrieveWindowName(adWindowId).translate(adLanguage);
				logger.info("healthCheck [{}/{}] View `{}` ({}) is NOK: {}", countCurrent, countTotal, windowName, windowId, ex.getLocalizedMessage());

				final Throwable cause = DocumentLayoutBuildException.extractCause(ex);
				errors.add(JsonWindowsHealthResponse.Entry.builder()
						.windowId(windowId)
						.windowName(windowName)
						.error(de.metas.rest_api.utils.v2.JsonErrors.ofThrowable(cause, adLanguage))
						.build());
			}
		}

		stopwatch.stop();

		return JsonWindowsHealthResponse.builder()
				.took(stopwatch.toString())
				.countTotal(countTotal)
				.errors(errors)
				.skipped(skipped)
				.build();
	}
}
