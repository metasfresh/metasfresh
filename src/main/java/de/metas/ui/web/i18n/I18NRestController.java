package de.metas.ui.web.i18n;

import java.util.Map;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
@RequestMapping(I18NRestController.ENDPOINT)
public class I18NRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/i18n";

	private static final String AD_MESSAGE_PREFIX = "webui.";

	@Autowired
	private UserSession userSession;

	@GetMapping("/messages")
	public Map<String, String> getMessages()
	{
		return Services.get(IMsgBL.class).getMsgMap(userSession.getAD_Language(), AD_MESSAGE_PREFIX, true /* removePrefix */);
	}
}
