package de.metas.ui.web.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.i18n.Language;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.json.JSONUserSession;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;

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
@RequestMapping(value = UserSessionRestController.ENDPOINT)
public class UserSessionRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/userSession";

	@Autowired
	private UserSession userSession;
	
	@Autowired
	private UserSessionRepository userSessionRepo;


	@GetMapping
	public JSONUserSession getAll()
	{
		return JSONUserSession.of(userSession);
	}

	@PutMapping("/language")
	public JSONLookupValue setLanguage(@RequestBody final JSONLookupValue value)
	{
		final String adLanguage = value.getKey();
		userSessionRepo.setAD_Language(userSession.getLoggedUserId(), adLanguage);
		
		return getLanguage();
	}

	@GetMapping("/language")
	public JSONLookupValue getLanguage()
	{
		final Language language = userSession.getLanguage();
		return JSONLookupValue.of(language.getAD_Language(), language.getName());
	}
}
