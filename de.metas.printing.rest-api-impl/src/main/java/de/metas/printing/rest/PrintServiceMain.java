package de.metas.printing.rest;

import java.util.Collections;

import org.adempiere.util.StringUtils;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;

/*
 * #%L
 * de.metas.printing.rest-api-impl
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SpringBootApplication
@Profile(Profiles.PROFILE_PrintService)
public class PrintServiceMain
{
	/**
	 * By default, we run in headless mode. But using this system property, we can also run with headless=false.
	 * The only known use of that is that metasfresh can open the initial license & connection dialog to store the initial properties file.
	 */
	public static final String SYSTEM_PROPERTY_HEADLESS = "app-server-run-headless";

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(final String[] args)
	{
		Ini.setRunMode(RunMode.BACKEND);

		final String headless = System.getProperty(SYSTEM_PROPERTY_HEADLESS, Boolean.toString(true));

		new SpringApplicationBuilder(PrintServiceMain.class)
				.headless(StringUtils.toBoolean(headless)) // we need headless=false for initial connection setup popup (if any), usually this only applies on dev workstations.
				.web(true)
				.profiles(Profiles.PROFILE_PrintService)
				.run(args);
	}

	@Profile(Profiles.PROFILE_NotTest)
	@Bean(Adempiere.BEAN_NAME)
	public Adempiere adempiere()
	{
		// as of right now, we are not interested in loading *any* model validator whatsoever within this service
		// therefore we don't e.g. have to deal with the async-processor. It just won't be started.
		ModelValidationEngine.setInitEntityTypes(Collections.emptyList());

		final Adempiere adempiere = Env.getSingleAdempiereInstance(applicationContext);
		adempiere.startup(RunMode.BACKEND);

		return adempiere;
	}
}
