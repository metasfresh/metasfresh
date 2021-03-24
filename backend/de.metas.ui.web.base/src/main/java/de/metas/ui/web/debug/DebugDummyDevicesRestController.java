package de.metas.ui.web.debug;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.device.pool.dummy.DummyDevicesRestControllerTemplate;
import de.metas.ui.web.session.UserSession;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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
@RequestMapping(DebugDummyDevicesRestController.ENDPOINT)
public class DebugDummyDevicesRestController extends DummyDevicesRestControllerTemplate
{
	public static final String ENDPOINT = DebugRestController.ENDPOINT + "/dummyScales";

	private final UserSession userSession;

	public DebugDummyDevicesRestController(@NonNull final UserSession userSession)
	{
		this.userSession = userSession;
	}

	@Override
	protected void assertLoggedIn()
	{
		userSession.assertLoggedIn();
	}
}
