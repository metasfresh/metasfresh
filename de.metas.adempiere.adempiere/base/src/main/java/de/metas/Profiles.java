package de.metas;

import org.compiere.Adempiere;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import de.metas.logging.LogManager;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public class Profiles
{
	private static final Logger logger = LogManager.getLogger(Profiles.class);

	public static final String PROFILE_Test = "test";
	public static final String PROFILE_NotTest = "!" + PROFILE_Test;

	public static final String PROFILE_Webui = "metasfresh-webui-api";

	/**
	 * metasfresh-app a.k.a. the app-server.
	 */
	public static final String PROFILE_App = "metasfresh-app";

	/** Profile activate when running from IDE */
	public static final String PROFILE_Development = "development";

	public static final String PROFILE_MaterialDispo = "material-dispo";

	public static final String PROFILE_JasperService = "metasfresh-jasper-service";

	public static final String PROFILE_PrintService = "metasfresh-printing-service";

	/** @return true if {@link #PROFILE_Development} is active (i.e. we are running from IDE) */
	public boolean isDevelopmentProfileActive()
	{
		return isProfileActive(Profiles.PROFILE_Development);
	}

	/** @return true if given profile is active */
	public boolean isProfileActive(final String profile)
	{
		final ApplicationContext context = Adempiere.getSpringApplicationContext();
		if (context == null)
		{
			logger.warn("No application context found to determine if '{}' profile is active", profile);
			return true;
		}

		return context.getEnvironment().acceptsProfiles(profile);
	}

}
