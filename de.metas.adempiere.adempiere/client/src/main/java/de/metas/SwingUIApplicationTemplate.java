package de.metas;

import java.awt.KeyboardFocusManager;
import java.util.concurrent.Future;

import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.apps.AKeyboardFocusManager;
import org.compiere.apps.AMenu;
import org.compiere.print.ReportCtl;
import org.compiere.print.SwingViewerProvider;
import org.compiere.util.Env;
import org.compiere.util.Splash;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import de.metas.adempiere.addon.IAddonStarter;
import de.metas.adempiere.addon.impl.AddonStarter;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.swing.SwingClientUI;
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
 * Swing application starter.
 * 
 * WARNING: please keep in sync with all other SwingUIApplications
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class SwingUIApplicationTemplate
{
	public static final String PROFILE = "metasfresh-swingui";

	protected static <T extends SwingUIApplicationTemplate> void main(@NonNull final Class<T> launcherClass, final String[] args)
	{
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

	/**
	 * Starts the metasfresh swing client. This needs to be decomposed to make it return during startup.<br>
	 * Otherwise, the whole spring thing doesn't work properly.
	 *
	 * @return
	 */
	@Bean(Adempiere.BEANNAME)
	public Adempiere adempiere(final ApplicationContext applicationContext)
	{
		final Splash splash = Splash.showSplash();
		try
		{
			//
			// First thing, get adempiere instance and set spring's applicationContext to it
			final Adempiere adempiere = Env.getSingleAdempiereInstance(applicationContext);
			adempiere.setApplicationContext(applicationContext);

			//
			// Start add-ons
			final IAddonStarter addonStarter = new AddonStarter();
			addonStarter.startAddons();

			//
			// Make sure first thing that we do is to initialize ADempiere.
			// Mainly because we want to have the ContextProvider correctly setup. (task 08859).
			adempiere.startup(RunMode.SWING_CLIENT);     // error exit and initUI

			return adempiere;
		}
		finally
		{
			splash.dispose();
		}
	}

	@Bean("swingMainWindow")
	public Future<AMenu> swingApplication(final Adempiere adempiere)
	{
		KeyboardFocusManager.setCurrentKeyboardFocusManager(AKeyboardFocusManager.get());
		Services.registerService(IClientUI.class, new SwingClientUI());
		ReportCtl.setDefaultReportEngineReportViewerProvider(SwingViewerProvider.instance);

		return AMenu.loginAndStartAsync();
	}
}
