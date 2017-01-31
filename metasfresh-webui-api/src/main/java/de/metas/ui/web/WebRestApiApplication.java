package de.metas.ui.web;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import de.metas.logging.LogManager;
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

@SpringBootApplication(scanBasePackageClasses = {
		WebRestApiApplication.class // this one
		, org.adempiere.ad.dao.IQueryStatisticsLogger.class // FIXME: hardcoded because else SQL tracing is not working and atm i am not confident to scan the whole de.metas/org.compiere/org.adempiere trees.
})
public class WebRestApiApplication
{
	public static final String PROFILE_Test = "test";
	public static final String PROFILE_NotTest = "!" + PROFILE_Test;
	public static final String PROFILE_Webui = "webui";
	/** Profile activate when running from IDE */
	public static final String PROFILE_Development = "development";

	private static final Logger logger = LogManager.getLogger(WebRestApiApplication.class);

	public static void main(String[] args)
	{
		if (Check.isEmpty(System.getProperty("PropertyFile"), true))
		{
			System.setProperty("PropertyFile", "./metasfresh.properties");
		}

		// important because in Ini, there is a org.springframework.context.annotation.Condition that userwise wouldn't e.g. let the jasper servlet start
		Ini.setRunMode(RunMode.WEBUI);

		new SpringApplicationBuilder(WebRestApiApplication.class)
				.headless(false) // FIXME: we need it for initial connection setup popup (if any)
				.web(true)
				.profiles(PROFILE_Webui)
				.run(args);

	}

	/** @return true if {@link #PROFILE_Development} is active (i.e. we are running from IDE) */
	public static boolean isDevelopmentProfileActive()
	{
		return isProfileActive(PROFILE_Development);
	}

	/** @return true if given profile is active */
	public static boolean isProfileActive(final String profile)
	{
		final ApplicationContext context = Adempiere.getSpringApplicationContext();
		if (context == null)
		{
			logger.warn("No application context found to determine if '{}' profile is active", profile);
			return true;
		}

		return context.getEnvironment().acceptsProfiles(profile);
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public Adempiere adempiere()
	{
		Env.setContextProvider(new WebRestApiContextProvider());

		InterfaceWrapperHelper.registerHelper(new DocumentInterfaceWrapperHelper());

		final Adempiere adempiere = Env.getSingleAdempiereInstance();
		adempiere.setApplicationContext(applicationContext);
		adempiere.startup(RunMode.WEBUI);
		return adempiere;
	}

	@Bean
	public EmbeddedServletContainerCustomizer servletContainerCustomizer()
	{
		return new EmbeddedServletContainerCustomizer()
		{
			@Override
			public void customize(ConfigurableEmbeddedServletContainer servletContainer)
			{
				final TomcatEmbeddedServletContainerFactory tomcatContainerFactory = (TomcatEmbeddedServletContainerFactory)servletContainer;
				tomcatContainerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer()
				{
					@Override
					public void customize(Connector connector)
					{
						final AbstractHttp11Protocol<?> httpProtocol = (AbstractHttp11Protocol<?>)connector.getProtocolHandler();
						httpProtocol.setCompression("on");
						httpProtocol.setCompressionMinSize(256);
						String mimeTypes = httpProtocol.getCompressableMimeType();
						String mimeTypesWithJson = mimeTypes + "," + MediaType.APPLICATION_JSON_VALUE + ",application/javascript";
						httpProtocol.setCompressableMimeType(mimeTypesWithJson);
					}
				});
			}
		};
	}
}
