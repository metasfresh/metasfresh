package de.metas.ui.web.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.json.JSONUserSession;

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

	private static final void logResourceValueChanged(final String name, final Object value, final Object valueOld)
	{
		System.out.println("*********************************************************************************************");
		System.out.println("Changed " + name + " " + valueOld + " -> " + value);
		System.out.println("*********************************************************************************************");
	}

	@GetMapping
	public JSONUserSession getAll()
	{
		return JSONUserSession.of(userSession);
	}

	@PutMapping("/language")
	public String setAD_Language(@RequestBody final String adLanguage)
	{
		final String adLanguageOld = userSession.setAD_Language(adLanguage);
		final String adLanguageNew = userSession.getAD_Language();
		logResourceValueChanged("AD_Language", adLanguageNew, adLanguageOld);

		return adLanguageNew;
	}

	@GetMapping("/language")
	public String getAD_Language()
	{
		return userSession.getAD_Language();
	}
}
