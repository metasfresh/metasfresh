package de.metas.ui.web.vaadin;

import java.util.Map;
import java.util.Properties;

import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.HARDCODED_Order;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.WindowPresenter;

/*
 * #%L
 * metasfresh-webui
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

@SpringUI(path = WindowUI.PATH + "/*")
@PreserveOnRefresh
@Widgetset("de.metas.ui.web.vaadin.widgetset.metasfreshWidgetSet")
@Theme(de.metas.ui.web.vaadin.theme.Theme.NAME)
@SuppressWarnings("serial")
@Push
public class WindowUI extends UI
{
	public static final String PATH = "/window";

	private static final Logger logger = LogManager.getLogger(WindowUI.class);

	@Override
	protected void init(final VaadinRequest request)
	{
		// FIXME: setting up the context
		{
			final Properties ctx = Env.getCtx();
			Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1000000);
			Env.setContext(ctx, Env.CTXNAME_AD_User_ID, 100);
			Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, 1000000);
			Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_DE");
			// getSession().setLocale(Locale.GERMANY); // TODO: date field does not display the date on any locale here.. or it's a JRebel issue????
		}

		final RequestCommand requestCommand = RequestCommand.of(request);
		logger.debug("Command: {}", requestCommand);

		final WindowPresenter windowPresenter = getWindowPresenter(requestCommand);
		final Component viewComp = windowPresenter.getViewComponent();
		setContent(viewComp);
		return;
	}

	private WindowPresenter getWindowPresenter(final RequestCommand command)
	{
		final PropertyDescriptor rootPropertyDescriptor = getRootPropertyDescriptor(command);
		final WindowPresenter windowPresenter = new WindowPresenter();
		windowPresenter.setRootPropertyDescriptor(rootPropertyDescriptor);
		return windowPresenter;
	}

	private PropertyDescriptor getRootPropertyDescriptor(final RequestCommand command)
	{
		final int windowId = command.getWindowId();
		if (windowId == 143)
		{
			return HARDCODED_Order.createRootPropertyDescriptor();
		}
		else
		{
			throw new IllegalArgumentException("No window found: " + command);
		}
	}

	private static final class RequestCommand
	{
		public static final RequestCommand of(final VaadinRequest request)
		{
			return new RequestCommand(request);
		}

		private final String command;
		private final int windowId;
		private Map<String, String[]> parameters;

		private RequestCommand(final VaadinRequest request)
		{
			super();
			String pathInfo = request.getPathInfo();
			if (pathInfo == null)
			{
				throw new IllegalArgumentException("Invalid path info");
			}
			if (!pathInfo.startsWith(PATH))
			{
				throw new IllegalArgumentException("Invalid path info: " + pathInfo);
			}

			final String[] pathInfoParts = pathInfo.split("/");
			this.command = pathInfoParts[1];
			this.windowId = Integer.parseInt(pathInfoParts[2]);

			this.parameters = request.getParameterMap();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("command", command)
					.add("windowId", windowId)
					.toString();
		}

		public String getCommand()
		{
			return command;
		}

		public int getWindowId()
		{
			return windowId;
		}
	}

}
