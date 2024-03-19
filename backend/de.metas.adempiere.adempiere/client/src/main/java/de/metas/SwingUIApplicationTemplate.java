package de.metas;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.swing.SwingClientUI;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.Role;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.util.hash.HashableString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.AKeyboardFocusManager;
import org.compiere.apps.ALogin;
import org.compiere.apps.AMenu;
import org.compiere.db.CConnection;
import org.compiere.model.I_AD_User;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Login;
import org.compiere.util.Splash;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.util.Properties;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public abstract class SwingUIApplicationTemplate
{
	private static final Logger logger = LogManager.getLogger(SwingUIApplicationTemplate.class);

	@Autowired
	private ApplicationContext applicationContext;

	protected static <T extends SwingUIApplicationTemplate> void main(final Class<T> bootstrapClass, final String[] args)
	{
		basicStartup();

		//showLoginDialog();
		loginDirectlyAsSysAdmin();

		try
		{
			new SpringApplicationBuilder(bootstrapClass)
					.headless(false)
					// actually we would like to it to start actuator endpoints and register with the spring-boot admin server, BUT
					// we first need to solve the problem of running multiple clients on the same machine (they need to bind to differnt ports)
					// there might be resource/performance problems
					// at any rate, we have not yet a solution as to how to configure them
					.web(WebApplicationType.NONE)
					.profiles(Profiles.PROFILE_SwingUI)
					.beanNameGenerator(new MetasfreshBeanNameGenerator())
					.run(args);
		}
		catch (final Exception ex)
		{
			ADialog.error(-1, null, ex);
			AEnv.exit(1);
		}
	}

	private static void loginDirectlyAsSysAdmin()
	{
		System.out.println("*************************************************************************************");
		System.out.println("*************************************************************************************");
		System.out.println(" Automatically logging in as System Administrator.");
		System.out.println(" If this is failing somehow pls edit SwingUIApplicationTemplate.main.");
		System.out.println("*************************************************************************************");
		System.out.println("*************************************************************************************");

		Splash.getSplash("Logging in as System Administrator...");

		try (final IAutoCloseable c = ModelValidationEngine.postponeInit())
		{

			final CConnection cc = CConnection.get();
			DB.setDBTarget(cc); // set the connection to DB to have early access to it (for messages, languages etc)
			Ini.setProperty(Ini.P_CONNECTION, CConnection.get().toStringLong());

			Ini.setProperty(Ini.P_SHOW_TRL, true);
			Ini.setProperty(Ini.P_SHOW_ADVANCED, true);
			Ini.setProperty(Ini.P_CACHE_WINDOW, false);

			final Properties ctx = Env.getCtx();
			Env.setClientId(ctx, ClientId.SYSTEM);
			Env.setAD_Language(ctx, Language.AD_Language_en_US);

			final Login login = new Login(ctx);

			final IUserBL userBL = Services.get(IUserBL.class);
			final I_AD_User user = userBL.getById(UserId.METASFRESH);
			final String username = userBL.extractUserLogin(user);
			final HashableString password = userBL.extractUserPassword(user);
			final Role systemRole = login.authenticate(username, password).getAvailableRoles()
					.stream()
					.filter(role -> role.getId().isSystem())
					.findFirst()
					.orElseThrow(() -> new AdempiereException("User `" + username + "` has no System role assigned"));
			login.setRoleAndGetClients(systemRole.getId());

			String error = login.validateLogin(OrgId.ANY);
			if (error != null && !error.isEmpty())
			{
				throw new AdempiereException(error);
			}

			login.loadPreferences(OrgId.ANY, null);
		}
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
		try (final IAutoCloseable c = ModelValidationEngine.postponeInit())
		{
			AEnv.showCenterScreen(login);    // HTML load errors
		}
		catch (final Exception ex)
		{
			logger.error("Login failed", ex);
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

	@Bean
	@Primary
	public ObjectMapper jsonObjectMapper()
	{
		return JsonObjectMapperHolder.sharedJsonObjectMapper();
	}

	@Bean(Adempiere.BEAN_NAME)
	public Adempiere adempiere()
	{
		return Env.getSingleAdempiereInstance(applicationContext);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void showSwingUIMainWindow()
	{
		ModelValidationEngine
				.get()
				.loginComplete(
						Env.getAD_Client_ID(),
						Env.getAD_Org_ID(Env.getCtx()),
						Env.getAD_Role_ID(Env.getCtx()),
						Env.getAD_User_ID());
		new AMenu();
	}
}
