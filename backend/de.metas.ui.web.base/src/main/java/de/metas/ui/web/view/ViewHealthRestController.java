/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.view;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.ui.web.config.WebConfig;
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
import org.compiere.util.Env;
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
	@NonNull private final IViewsRepository viewsRepo;

	@GetMapping("/health")
	public JsonWindowsHealthResponse healthCheck(
			@RequestParam(name = "windowIds", required = false) final String windowIdsCommaSeparated
	)
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		final ImmutableSet<AdWindowId> skipAdWindowIds = ImmutableSet.of();

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

				final ViewLayout viewLayout = viewsRepo.getViewLayout(windowId, JSONViewDataType.grid, null, null);

				final String viewName = viewLayout.getCaption(adLanguage);
				logger.info("healthCheck [{}/{}] View `{}` ({}) is OK", countCurrent, countTotal, viewName, windowId);
			}
			catch (Exception ex)
			{
				final String viewName = adWindowDAO.retrieveWindowName(adWindowId).translate(adLanguage);
				logger.info("healthCheck [{}/{}] View `{}` ({}) is NOK: {}", countCurrent, countTotal, viewName, windowId, ex.getLocalizedMessage());

				final Throwable cause = DocumentLayoutBuildException.extractCause(ex);
				errors.add(JsonWindowsHealthResponse.Entry.builder()
						.windowId(windowId)
						.windowName(viewName)
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
