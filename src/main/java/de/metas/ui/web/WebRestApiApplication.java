package de.metas.ui.web;

import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;

import de.metas.Profiles;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.session.WebRestApiContextProvider;
import de.metas.ui.web.window.model.DocumentInterfaceWrapperHelper;

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

@SpringBootApplication(scanBasePackages = { "de.metas", "org.adempiere" })
@EnableAsync
@Profile(Profiles.PROFILE_Webui)
public class WebRestApiApplication
{
	/**
	 * By default, we run in headless mode. But using this system property, we can also run with headless=false.
	 * The only known use of that is that metasfresh can open the initial license & connection dialog to store the initial properties file.
	 */
	public static final String SYSTEM_PROPERTY_HEADLESS = "webui-api-run-headless";

	public static void main(final String[] args)
	{
		if (Check.isEmpty(System.getProperty("PropertyFile"), true))
		{
			System.setProperty("PropertyFile", "./metasfresh.properties");
		}

		AdempiereException.enableCaptureLanguageOnConstructionTime(); // because usually at the time the message is (lazy) parsed the user session context is no longer available.

		// important because in Ini, there is a org.springframework.context.annotation.Condition that otherwise wouldn't e.g. let the jasper servlet start
		Ini.setRunMode(RunMode.WEBUI);

		final String headless = System.getProperty(SYSTEM_PROPERTY_HEADLESS, Boolean.toString(true));

		new SpringApplicationBuilder(WebRestApiApplication.class)
				.headless(Boolean.parseBoolean(headless)) // we need headless=false for initial connection setup popup (if any), usually this only applies on dev workstations.
				.web(true)
				.profiles(Profiles.PROFILE_Webui)
				.run(args);

	}

	@Autowired
	private ApplicationContext applicationContext;

	@Bean(Adempiere.BEAN_NAME)
	public Adempiere adempiere(final WebRestApiContextProvider webuiContextProvider)
	{
		Env.setContextProvider(webuiContextProvider);

		InterfaceWrapperHelper.registerHelper(new DocumentInterfaceWrapperHelper());

		final Adempiere adempiere = Env.getSingleAdempiereInstance(applicationContext);
		adempiere.startup(RunMode.WEBUI);

		Services.get(IMigrationLogger.class).addTableToIgnoreList(I_T_WEBUI_ViewSelection.Table_Name);

		return adempiere;
	}

	@Bean
	public EmbeddedServletContainerCustomizer servletContainerCustomizer()
	{
		return new EmbeddedServletContainerCustomizer()
		{
			@Override
			public void customize(final ConfigurableEmbeddedServletContainer servletContainer)
			{
				final TomcatEmbeddedServletContainerFactory tomcatContainerFactory = (TomcatEmbeddedServletContainerFactory)servletContainer;
				tomcatContainerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer()
				{
					@Override
					public void customize(final Connector connector)
					{
						final AbstractHttp11Protocol<?> httpProtocol = (AbstractHttp11Protocol<?>)connector.getProtocolHandler();
						httpProtocol.setCompression("on");
						httpProtocol.setCompressionMinSize(256);
						final String mimeTypes = httpProtocol.getCompressibleMimeType();
						final String mimeTypesWithJson = mimeTypes + "," + MediaType.APPLICATION_JSON_VALUE + ",application/javascript";
						httpProtocol.setCompressibleMimeType(mimeTypesWithJson);
					}
				});
			}
		};
	}
}
