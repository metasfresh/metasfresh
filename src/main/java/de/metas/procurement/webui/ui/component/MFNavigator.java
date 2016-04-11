package de.metas.procurement.webui.ui.component;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.google.gwt.thirdparty.guava.common.base.Supplier;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/*
 * #%L
 * de.metas.procurement.webui
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

@SuppressWarnings("serial")
public class MFNavigator extends Navigator
{
	public static final MFNavigator createAndBind(final UI ui)
	{
		return new MFNavigator(ui);
	}

	public static final MFNavigator cast(final Navigator navigator)
	{
		return (MFNavigator)navigator;
	}

	public static final String createURIFragment(final String viewName)
	{
		final String parameters = null;
		return createURIFragment(viewName, parameters);
	}

	public static final String createURIFragment(final String viewName, final String parameters)
	{
		final StringBuilder uriFragment = new StringBuilder();
		uriFragment.append("!");
		if (viewName != null)
		{
			uriFragment.append(viewName.trim());
		}
		
		if (parameters != null && !parameters.trim().isEmpty())
		{
			uriFragment.append("/").append(parameters.trim());
		}
		return uriFragment.toString();
	}

	/** Default view name to be used with {@link com.vaadin.navigator.View} */
	public static final String VIEWNAME_DEFAULT = "";
	/** Default login view name */
	public static final String VIEWNAME_DEFAULT_LOGIN = "login";

	private String loginViewName;
	private Supplier<Boolean> isLoggedInSupplier;

	private final Set<String> noLoginRequiredViewNames = new HashSet<>();
	
	private Notification nextViewNotification;

	private MFNavigator(final UI ui)
	{
		super(ui, ui);

		addViewChangeListener(new ViewChangeListener()
		{

			@Override
			public boolean beforeViewChange(final ViewChangeEvent event)
			{
				return checkLoggedInAndRedirect(event);
			}

			@Override
			public void afterViewChange(final ViewChangeEvent event)
			{
				showNextViewNotification();
			}
		});

	}

	public MFNavigator setLoginView(final String loginViewName, final Class<? extends View> loginViewClass, final Supplier<Boolean> isLoggedInSupplier)
	{
		Preconditions.checkNotNull(loginViewName, "loginViewName is null");
		Preconditions.checkArgument(!Objects.equal(VIEWNAME_DEFAULT, loginViewClass), "loginViewName cannot be the default view");
		Preconditions.checkNotNull(isLoggedInSupplier, "isLoggedInSupplier is null");
		Preconditions.checkNotNull(loginViewClass, "loginViewClass is null");

		this.loginViewName = loginViewName;
		this.isLoggedInSupplier = isLoggedInSupplier;

		addView(loginViewName, loginViewClass);

		return this;
	}

	public MFNavigator setLoginView(final Class<? extends View> loginViewClass, final Supplier<Boolean> isLoggedInSupplier)
	{
		return setLoginView(VIEWNAME_DEFAULT_LOGIN, loginViewClass, isLoggedInSupplier);
	}

	public MFNavigator setView(final String viewName, final Class<? extends View> viewClass)
	{
		addView(viewName, viewClass);
		return this;
	}

	public MFNavigator setViewNoLoginRequired(final String viewName, final Class<? extends View> viewClass)
	{
		addView(viewName, viewClass);
		noLoginRequiredViewNames.add(viewName);
		return this;
	}

	public MFNavigator setDefaultView(final Class<? extends View> viewClass)
	{
		setView(VIEWNAME_DEFAULT, viewClass);
		return this;
	}

	public MFNavigator navigateToDefaultView()
	{
		navigateTo(VIEWNAME_DEFAULT);
		return this;
	}

	public MFNavigator navigateToLoginView()
	{
		Preconditions.checkNotNull(loginViewName, "loginViewName is set");
		navigateTo(loginViewName);
		return this;
	}

	private final boolean checkLoggedInAndRedirect(final ViewChangeEvent event)
	{
		// If no login view was configured then we don't need the login functionality,
		// so allow the navigation to go on.
		if (loginViewName == null)
		{
			return true;
		}

		final String viewName = event.getViewName();

		// Check if a user has logged in
		final boolean isLoggedIn = isLoggedIn();
		final boolean isLoginView = loginViewName.equals(viewName);

		if (!isLoggedIn && !isLoginView)
		{
			if (!isLoginRequired(viewName))
			{
				return true;
			}

			// Redirect to login view always if a user has not yet logged in
			navigateTo(loginViewName);
			return false;

		}
		else if (isLoggedIn && isLoginView)
		{
			// If someone tries to access to login view while logged in, then cancel
			return false;
		}

		return true;
	}

	private boolean isLoggedIn()
	{
		if (isLoggedInSupplier == null)
		{
			return false;
		}
		final Boolean loggedIn = isLoggedInSupplier.get();
		return loggedIn != null && loggedIn;
	}

	private boolean isLoginRequired(final String viewName)
	{
		if (noLoginRequiredViewNames.contains(viewName))
		{
			return false;
		}
		return true;
	}
	
	public MFNavigator setNextViewNotification(final Notification notification)
	{
		this.nextViewNotification = notification;
		return this;
	}
	
	private final void showNextViewNotification()
	{
		final Notification notification = this.nextViewNotification;
		this.nextViewNotification = null;
		
		if (notification == null)
		{
			return;
		}
		
		notification.show(Page.getCurrent());
	}

}
