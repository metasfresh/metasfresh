package de.metas;

import java.awt.KeyboardFocusManager;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.apps.AEnv;
import org.compiere.apps.AKeyboardFocusManager;
import org.compiere.apps.ALogin;
import org.compiere.apps.AMenu;
import org.compiere.db.CConnection;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.Env;
import org.compiere.util.Splash;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.swing.SwingClientUI;
import de.metas.logging.LogManager;

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
@SpringBootApplication(scanBasePackages = { "de.metas", "org.adempiere" })
@Profile(SwingUIApplication.PROFILE)
public class SwingUIApplication
{
	private static final Logger logger = LogManager.getLogger(SwingUIApplication.class);

	public static final String PROFILE = "metasfresh-swingui";

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args)
	{
		basicStartup();

		showLoginDialog();

		new SpringApplicationBuilder(SwingUIApplication.class)
				.headless(false)
				// actually we would like to it to start actuator endpoints and register with the spring-boot admin server, BUT
				// we first need to solve the problem of running multiple clients on the same machine (they need to bind to differnt ports)
				// there might be resource/performance problems
				// at any rate, we have not yet a solution as to how to configure them
				.web(false)
				.profiles(PROFILE)
				.properties(CConnection.get().createRabbitmqSpringProperties())
				.run(args);
	}

	private static void showLoginDialog()
	{
		final Splash splash = Splash.getSplash();
		final Properties ctx = Env.getCtx();
		final ALogin login = new ALogin(splash, ctx);
		if (login.initLogin())
		{
			return; // automatic login, nothing more to do
		}

		// Center the window
		try
		{
			ModelValidationEngine.disable();
			AEnv.showCenterScreen(login);	// HTML load errors
		}
		catch (Exception ex)
		{
			logger.error(ex.toString());
		}
		finally
		{
			ModelValidationEngine.enable();
		}

		if (!(login.isConnected() && login.isOKpressed()))
		{
			AEnv.exit(1);
		}
	}

	private static void basicStartup()
	{
		Adempiere.instance.startup(RunMode.SWING_CLIENT);

		// Focus Traversal
		KeyboardFocusManager.setCurrentKeyboardFocusManager(AKeyboardFocusManager.get());
		Services.registerService(IClientUI.class, new SwingClientUI());
	}

	@Bean(Adempiere.BEAN_NAME)
	public Adempiere adempiere()
	{
		final Adempiere adempiere = Env.getSingleAdempiereInstance(applicationContext);
		return adempiere;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void showSwingUIMainWindow()
	{
		new AMenu();
	}
}
