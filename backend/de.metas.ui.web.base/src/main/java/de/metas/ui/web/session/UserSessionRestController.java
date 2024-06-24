package de.metas.ui.web.session;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.Language;
import de.metas.organization.ClientAndOrgId;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.json.JSONUserSession;
import de.metas.ui.web.session.json.JsonChangeWorkplaceRequest;
import de.metas.ui.web.session.json.JsonGetWorkplaceResponse;
import de.metas.ui.web.session.json.JsonGetWorkplaceResponse.JsonGetWorkplaceResponseBuilder;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceAssignmentCreateRequest;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.Map;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@RestController
@RequestMapping(UserSessionRestController.ENDPOINT)
@RequiredArgsConstructor
public class UserSessionRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/userSession";

	private static final String SYSCONFIG_SETTINGS_PREFIX = "webui.frontend.";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final UserSession userSession;
	@NonNull private final UserSessionRepository userSessionRepo;
	@NonNull private final DocumentCollection documentCollection;
	@NonNull private final WorkplaceService workplaceService;

	@GetMapping
	public JSONUserSession getAll()
	{
		final Map<String, String> settings = sysConfigBL.getValuesForPrefix(
				SYSCONFIG_SETTINGS_PREFIX,
				true,
				ClientAndOrgId.ofClientAndOrg(userSession.getClientId(), userSession.getOrgId()));

		return new JSONUserSession(userSession, settings);
	}

	@PutMapping("/language")
	public JSONLookupValue setLanguage(@RequestBody final JSONLookupValue value)
	{
		final String adLanguage = value.getKey();
		userSessionRepo.setAD_Language(userSession.getLoggedUserId(), adLanguage);
		documentCollection.cacheReset(false); // don't evict unsaved documents from the cache, because they would be lost entirely

		return getLanguage();
	}

	@GetMapping("/language")
	public JSONLookupValue getLanguage()
	{
		final Language language = userSession.getLanguage();
		return JSONLookupValue.of(language.getAD_Language(), language.getName());
	}

	@GetMapping("/workplace")
	public JsonGetWorkplaceResponse getWorkplace(@RequestParam(name = "includeAvailable", required = false) final boolean includeAvailable)
	{
		userSession.assertLoggedIn();

		final boolean isWorkplacesEnabled = userSession.isWorkplacesEnabled();
		if (isWorkplacesEnabled)
		{
			final JsonGetWorkplaceResponseBuilder builder = JsonGetWorkplaceResponse.builder().workplacesEnabled(true);

			userSession.getWorkplace()
					.map(UserSessionRestController::toJSONLookupValue)
					.ifPresent(builder::currentWorkplace);

			if (includeAvailable)
			{
				builder.available(workplaceService.getAllActive()
						.stream()
						.map(UserSessionRestController::toJSONLookupValue)
						.sorted(Comparator.comparing(JSONLookupValue::getCaption))
						.collect(ImmutableList.toImmutableList()));
			}

			return builder.build();
		}
		else
		{
			return JsonGetWorkplaceResponse.NOT_ENABLED;
		}
	}

	private static JSONLookupValue toJSONLookupValue(@NonNull Workplace workplace)
	{
		return JSONLookupValue.of(workplace.getId(), workplace.getName());
	}

	@PutMapping("/workplace")
	public void setWorkplace(@RequestBody @NonNull final JsonChangeWorkplaceRequest request)
	{
		userSession.assertLoggedIn();

		if (!userSession.isWorkplacesEnabled())
		{
			throw new AdempiereException("Workplaces not enabled");
		}

		workplaceService.assignWorkplace(WorkplaceAssignmentCreateRequest.builder()
				.workplaceId(request.getWorkplaceId())
				.userId(userSession.getLoggedUserId())
				.build());

	}
}
