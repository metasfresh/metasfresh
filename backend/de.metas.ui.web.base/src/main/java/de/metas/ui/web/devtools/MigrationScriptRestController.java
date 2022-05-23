package de.metas.ui.web.devtools;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import org.adempiere.ad.migration.rest.MigrationScriptRestControllerTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
@RequestMapping(WebConfig.ENDPOINT_ROOT + "/dev/migrationScriptsLogger")
public class MigrationScriptRestController extends MigrationScriptRestControllerTemplate
{
	private final UserSession userSession;

	public MigrationScriptRestController(final UserSession userSession)
	{
		super("webui");
		this.userSession = userSession;
	}

	@Override
	protected void assertAuth() {userSession.assertLoggedIn();}

	@Override
	protected String getUserName() {return userSession.getUserName();}
}
