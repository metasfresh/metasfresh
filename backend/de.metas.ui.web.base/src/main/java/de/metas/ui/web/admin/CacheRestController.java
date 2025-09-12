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

package de.metas.ui.web.admin;

import de.metas.cache.rest.CacheRestControllerTemplate;
import de.metas.cache.rest.JsonCacheResetRequest;
import de.metas.cache.rest.JsonCacheResetResponse;
import de.metas.cache.rest.JsonCacheStats;
import de.metas.cache.rest.JsonGetStatsResponse;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.process.ProcessRestController;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfig.ENDPOINT_ROOT + "/cache")
@RequiredArgsConstructor
public class CacheRestController extends CacheRestControllerTemplate
{
	public static final String CACHE_RESET_PARAM_forgetNotSavedDocuments = "forgetNotSavedDocuments";

	@NonNull private final UserSession userSession;
	@NonNull private final DocumentCollection documentCollection;
	@NonNull private final MenuTreeRepository menuTreeRepo;
	@NonNull private final ProcessRestController processesController;
	@NonNull private final LookupDataSourceFactory lookupDataSourceFactory;

	@Override
	protected void assertAuth() {userSession.assertLoggedIn();}

	protected void resetAdditional(@NonNull final JsonCacheResetResponse response, @NonNull final JsonCacheResetRequest request)
	{
		{
			final boolean forgetNotSavedDocuments = request.getValueAsBoolean(CACHE_RESET_PARAM_forgetNotSavedDocuments);
			final String documentsResult = documentCollection.cacheReset(forgetNotSavedDocuments);
			response.addLog("documents: " + documentsResult + " (" + CACHE_RESET_PARAM_forgetNotSavedDocuments + "=" + forgetNotSavedDocuments + ")");
		}
		{
			menuTreeRepo.cacheReset();
			response.addLog("menuTreeRepo: cache invalidated");
		}
		{
			processesController.cacheReset();
			response.addLog("processesController: cache invalidated");
		}
		{
			ViewColumnHelper.cacheReset();
			response.addLog("viewColumnHelper: cache invalidated");
		}
	}

	@GetMapping("/lookups/stats")
	public JsonGetStatsResponse getLookupCacheStats()
	{
		assertAuth();

		return lookupDataSourceFactory.getCacheStats()
				.stream()
				.sorted(DEFAULT_ORDER_BY)
				.map(JsonCacheStats::of)
				.collect(JsonGetStatsResponse.collect());
	}
}
