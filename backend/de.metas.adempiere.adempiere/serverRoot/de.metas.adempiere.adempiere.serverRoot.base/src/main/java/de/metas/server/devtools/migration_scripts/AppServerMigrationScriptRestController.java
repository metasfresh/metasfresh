/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.server.devtools.migration_scripts;

import de.metas.Profiles;
import de.metas.util.web.MetasfreshRestAPIConstants;
import org.adempiere.ad.migration.rest.MigrationScriptRestControllerTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/dev/migrationScriptsLogger")
@RestController
@Profile(Profiles.PROFILE_App)
public class AppServerMigrationScriptRestController extends MigrationScriptRestControllerTemplate
{
	public AppServerMigrationScriptRestController()
	{
		super("app");
	}

	@Override
	protected void assertAuth() {}

	@Override
	protected String getUserName() {return "app-server";}
}
