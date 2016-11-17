package de.metas;

import java.io.File;
import java.io.IOException;

import org.adempiere.ad.housekeeping.IHouseKeepingBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import de.metas.logging.LogManager;
import de.metas.server.housekeep.MissingTranslationHouseKeepingTask;
import de.metas.server.housekeep.RoleAccessUpdateHouseKeepingTask;
import de.metas.server.housekeep.SequenceCheckHouseKeepingTask;
import de.metas.server.housekeep.SignDatabaseBuildHouseKeepingTask;

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

/**
 * metasfresh server boot.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@SpringBootApplication( //
scanBasePackages = { "de.metas", "org.adempiere" },  //
excludeName = "de.metas.SwingUIApplication" // exclude the SwingUIApplication, just in case it's on classpath when running (usually when started from eclipse)
)
@ServletComponentScan(value = { "de.metas", "org.adempiere" })
public class ServerBoot
{
	@Autowired
	private ApplicationContext applicationContext;

	public static void main(final String[] args)
	{
		// important because in Ini, there is a org.springframework.context.annotation.Condition that userwise wouldn't e.g. let the jasper servlet start
		Ini.setRunMode(RunMode.BACKEND);

		new SpringApplicationBuilder(ServerBoot.class)
				.headless(true)
				.web(true)
				.run(args);
	}

	@Configuration
	public static class StaticResourceConfiguration extends WebMvcConfigurerAdapter
	{
		private static final Logger LOG = LogManager.getLogger(StaticResourceConfiguration.class);

		@Value("${metasfresh.serverRoot.downloads:}")
		private String downloadsPath;

		@Override
		public void addResourceHandlers(final ResourceHandlerRegistry registry)
		{
			if (Check.isEmpty(downloadsPath, true))
			{
				downloadsPath = defaultDownloadsPath();
			}

			// Make sure the path ends with separator
			// see https://jira.spring.io/browse/SPR-14063
			if (downloadsPath != null && !downloadsPath.endsWith(File.separator))
			{
				downloadsPath += File.separator;
			}

			if (!Check.isEmpty(downloadsPath, true))
			{
				LOG.info("Serving static content from " + downloadsPath);
				registry.addResourceHandler("/download/**").addResourceLocations("file:" + downloadsPath);

				// the "binaries" download path is about to be removed soon!
				registry.addResourceHandler("/binaries/**").addResourceLocations("file:" + downloadsPath);
			}
		}

		private String defaultDownloadsPath()
		{
			try
			{
				final File cwd = new File(".").getCanonicalFile();
				final File downloadsFile = new File(cwd, "download");
				return downloadsFile.getCanonicalPath();
			}
			catch (final IOException e)
			{
				LOG.warn("Failed finding the default downloads path", e);
				return null;
			}
		}
	}

	@Bean
	public Adempiere adempiere()
	{
		final IHouseKeepingBL houseKeepingRegistry = Services.get(IHouseKeepingBL.class);
		houseKeepingRegistry.registerStartupHouseKeepingTask(new SignDatabaseBuildHouseKeepingTask());
		houseKeepingRegistry.registerStartupHouseKeepingTask(new SequenceCheckHouseKeepingTask());
		houseKeepingRegistry.registerStartupHouseKeepingTask(new RoleAccessUpdateHouseKeepingTask());
		houseKeepingRegistry.registerStartupHouseKeepingTask(new MissingTranslationHouseKeepingTask());

		final Adempiere adempiere = Env.getSingleAdempiereInstance();
		adempiere.setApplicationContext(applicationContext);
		adempiere.startup(RunMode.BACKEND);
		return adempiere;
	}

}
