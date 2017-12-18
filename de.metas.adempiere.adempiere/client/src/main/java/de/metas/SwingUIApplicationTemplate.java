package de.metas;

import java.awt.KeyboardFocusManager;
import java.util.concurrent.Future;

import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.apps.AKeyboardFocusManager;
import org.compiere.apps.AMenu;
import org.compiere.print.ReportCtl;
import org.compiere.print.SwingViewerProvider;
import org.compiere.util.Splash;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.swing.SwingClientUI;
import de.metas.boot.Metasfresh;
import de.metas.boot.MetasfreshBootConfiguration;
import lombok.NonNull;

/*
 * #%L
 * de.metas.endcustomer.mf15.swingui
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
 * Swing application starter template. To be extended in "swing distribution" projects.
 * 
 * Usually, in extending classes you just have to declare the "main" method and call {@link #main(Class, String[])}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class SwingUIApplicationTemplate
{
	public static final String PROFILE = "metasfresh-swingui";

	protected static <T extends SwingUIApplicationTemplate> void main(@NonNull final Class<T> launcherClass, final String[] args)
	{
		Splash.showSplash();

		new SpringApplicationBuilder(launcherClass)
				.headless(false)
				// actually we would like to it to start actuator endpoints and register with the spring-boot admin server, BUT
				// we first need to solve the problem of running multiple clients on the same machine (they need to bind to differnt ports)
				// there might be resource/performance problems
				// at any rate, we have not yet a solution as to how to configure them
				.web(false)
				.profiles(PROFILE)
				.run(args);
	}

	@Bean("swingMainWindow")
	public Future<AMenu> swingApplication(final Metasfresh metasfresh)
	{
		Splash.disposeCurrent();

		KeyboardFocusManager.setCurrentKeyboardFocusManager(AKeyboardFocusManager.get());
		Services.registerService(IClientUI.class, new SwingClientUI());
		ReportCtl.setDefaultReportEngineReportViewerProvider(SwingViewerProvider.instance);

		return AMenu.loginAndStartAsync();
	}

	@Configuration
	@Profile(SwingUIApplicationTemplate.PROFILE)
	public static class SwingMetasfreshConfiguration implements MetasfreshBootConfiguration
	{
		@Override
		public RunMode getRunMode()
		{
			return RunMode.SWING_CLIENT;
		}
	}
}
