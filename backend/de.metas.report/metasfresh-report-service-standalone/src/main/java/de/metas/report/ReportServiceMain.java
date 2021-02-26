package de.metas.report;

import java.util.Collections;

import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
import de.metas.MetasfreshBeanNameGenerator;
import de.metas.Profiles;
import de.metas.util.StringUtils;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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

@SpringBootApplication(scanBasePackages = {
		"de.metas",
		"org.adempiere.ad.modelvalidator" // FIXME: workaround needed for ModuleActivatorDescriptorsRepository to be discovered
})
@ServletComponentScan(value = { "de.metas.adempiere.report.jasper.servlet" })
@Profile(Profiles.PROFILE_ReportService_Standalone)
public class ReportServiceMain
{
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * By default, we run in headless mode. But using this system property, we can also run with headless=false.
	 * The only known use of that is that metasfresh can open the initial license & connection dialog to store the initial properties file.
	 */
	static final String SYSTEM_PROPERTY_HEADLESS = "app-server-run-headless";

	public static void main(final String[] args)
	{
		try (final IAutoCloseable c = ModelValidationEngine.postponeInit())
		{
			Ini.setRunMode(RunMode.BACKEND);
			Adempiere.instance.startup(RunMode.BACKEND);

			final String headless = System.getProperty(SYSTEM_PROPERTY_HEADLESS, Boolean.toString(true));

			new SpringApplicationBuilder(ReportServiceMain.class)
					.headless(StringUtils.toBoolean(headless)) // we need headless=false for initial connection setup popup (if any), usually this only applies on dev workstations.
					.web(true)
					.profiles(Profiles.PROFILE_ReportService, Profiles.PROFILE_ReportService_Standalone)
					.beanNameGenerator(new MetasfreshBeanNameGenerator())
					.run(args);
		}

		// now init the model validation engine
		ModelValidationEngine.get();
	}

	@Bean
	@Primary
	public ObjectMapper jsonObjectMapper()
	{
		return JsonObjectMapperHolder.sharedJsonObjectMapper();
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
