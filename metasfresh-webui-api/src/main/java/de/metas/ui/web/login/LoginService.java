package de.metas.ui.web.login;

import java.util.Properties;

import org.adempiere.service.IValuePreferenceBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.WindowConstants;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class LoginService
{
	@Autowired
	private UserSession userSession;

	public void autologin()
	{
		// FIXME: debug logging
		LogManager.setLoggerLevel(de.metas.ui.web.window.WindowConstants.logger, Level.INFO);
		//
		// Descriptor & factory
		LogManager.setLoggerLevel("de.metas.ui.web.window.descriptor", Level.TRACE);
		LogManager.setLoggerLevel("de.metas.ui.web.window.descriptor.factory", null);
		//
		LogManager.setLoggerLevel(de.metas.ui.web.window.model.Document.class, Level.TRACE);
		LogManager.setLoggerLevel("de.metas.ui.web.window.model.DocumentField", Level.TRACE);
		LogManager.setLoggerLevel(de.metas.ui.web.window.controller.Execution.class, Level.TRACE);
		WindowConstants.setProtocolDebugging(true);
		LogManager.setLoggerLevel(de.metas.ui.web.window.model.sql.SqlDocumentRepository.class, null);
		//
		LogManager.setLoggerLevel(org.adempiere.ad.callout.api.impl.CalloutExecutor.class, Level.INFO);
		//
		// LogManager.setLoggerLevel("org.adempiere.ad.expression.api", Level.TRACE); // logic expressions debugging
		//
		// LogManager.dumpAllLevelsUpToRoot(de.metas.ui.web.window.WindowConstants.logger);
		// LogManager.dumpAllLevelsUpToRoot(LogManager.getLogger(DocumentFieldChangedEventCollector.class));

		LogManager.setLoggerLevel(de.metas.ui.web.menu.MenuTree.class, Level.TRACE);
		LogManager.setLoggerLevel(de.metas.ui.web.menu.MenuTreeLoader.class, Level.TRACE);

		// FIXME: only for testing
		final Properties ctx = userSession.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, 100);
		Env.setContext(ctx, Env.CTXNAME_ShowAcct, false);
		Env.setContext(ctx, "#C_UOM_ID", 100);
		
		if(Check.isEmpty(userSession.getAD_Language()))
		{
			userSession.setAD_Language("en_US");
		}

		Services.get(IValuePreferenceBL.class)
				.getAllWindowPreferences(Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx), Env.getAD_User_ID(ctx))
				.stream()
				.flatMap(userValuePreferences -> userValuePreferences.values().stream())
				.forEach(userValuePreference -> Env.setPreference(ctx, userValuePreference));

		userSession.setLoggedIn(true);
	}
}
