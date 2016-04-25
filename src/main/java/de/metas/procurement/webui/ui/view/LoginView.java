package de.metas.procurement.webui.ui.view;

import java.io.File;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.vaadin.spring.i18n.I18N;

import com.google.gwt.thirdparty.guava.common.base.Strings;
import com.vaadin.addon.touchkit.ui.EmailField;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.Constants;
import de.metas.procurement.webui.event.UIEventBus;
import de.metas.procurement.webui.event.UserLoggedInEvent;
import de.metas.procurement.webui.exceptions.PasswordResetFailedException;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.ILoginService;
import de.metas.procurement.webui.service.impl.LoginRememberMeService;
import de.metas.procurement.webui.service.impl.LoginRememberMeService.RememberMeToken;

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
public class LoginView extends NavigationView implements View
{
	private static final String STYLE = "LoginView";
	private static final String STYLE_LoginFormWrapper = "login-form-wrapper";
	private static final String STYLE_Logo = "logo";
	private static final String STYLE_LoginEmail = "login-email";
	private static final String STYLE_LoginPassword = "login-password";
	private static final String STYLE_LoginButton = "login-button";
	private static final String STYLE_ForgotPasswordButton = "forgot-password-button";
	private static final String STYLE_PoweredBy = "powered-by";

	private static final Logger logger = LoggerFactory.getLogger(LoginView.class);

	@Autowired
	private I18N i18n;

	@Autowired(required = true)
	private ILoginService loginService;
	@Autowired(required = true)
	private LoginRememberMeService rememberMeService;

	@Value("${mfprocurement.logo.file:}")
	private String logoFilename;
	@Value("${mfprocurement.poweredby.url:}")
	private String poweredByLogoUrl;
	@Value("${mfprocurement.poweredby.link.url:}")
	private String poweredByLinkUrl;

	private final EmailField email;
	private final PasswordField password;

	public LoginView()
	{
		super();
		Application.autowire(this);

		addStyleName(STYLE);

		//
		// Content
		{
			final VerticalComponentGroup content = new VerticalComponentGroup();

			final Resource logoResource = getLogoResource();
			final Image logo = new Image(null, logoResource);
			logo.addStyleName(STYLE_Logo);
			content.addComponent(logo);

			this.email = new EmailField(i18n.get("LoginView.fields.email"));
			email.addStyleName(STYLE_LoginEmail);
			email.setIcon(FontAwesome.USER);
			content.addComponent(email);

			this.password = new PasswordField(i18n.get("LoginView.fields.password"));
			password.addStyleName(STYLE_LoginPassword);
			password.setIcon(FontAwesome.LOCK);
			content.addComponent(password);

			final Button loginButton = new Button(i18n.get("LoginView.fields.loginButton"));
			loginButton.addStyleName(STYLE_LoginButton);
			loginButton.setClickShortcut(KeyCode.ENTER);
			loginButton.addClickListener(new Button.ClickListener()
			{
				@Override
				public void buttonClick(final ClickEvent event)
				{
					onUserLogin(email.getValue(), password.getValue());
				}
			});

			final Button forgotPasswordButton = new Button(i18n.get("LoginView.fields.forgotPasswordButton"));
			forgotPasswordButton.setStyleName(STYLE_ForgotPasswordButton);
			forgotPasswordButton.addClickListener(new Button.ClickListener()
			{

				@Override
				public void buttonClick(ClickEvent event)
				{
					onForgotPassword(email.getValue());
				}
			});

			final CssLayout contentWrapper = new CssLayout(content, loginButton, forgotPasswordButton);
			contentWrapper.addStyleName(STYLE_LoginFormWrapper);
			setContent(contentWrapper);
		}

		//
		// Bottom:
		{
			//
			// Powered-by logo resource
			// Use the configured one if any; fallback to default embedded powered-by logo
			final Resource poweredByLogoResource;
			if (poweredByLogoUrl != null && !poweredByLogoUrl.trim().isEmpty())
			{
				poweredByLogoResource = new ExternalResource(poweredByLogoUrl.trim());
			}
			else
			{
				poweredByLogoResource = Constants.RESOURCE_PoweredBy;
			}

			//
			// Powered-by component:
			final Component poweredByComponent;
			if (poweredByLinkUrl != null && !poweredByLinkUrl.trim().isEmpty())
			{
				final Link link = new Link();
				link.setIcon(poweredByLogoResource);
				link.setResource(new ExternalResource(poweredByLinkUrl.trim()));
				link.setTargetName("_blank");
				poweredByComponent = link;
			}
			else
			{
				final Image image = new Image(null, poweredByLogoResource);
				poweredByComponent = image;
			}
			//
			poweredByComponent.addStyleName(STYLE_PoweredBy);
			setToolbar(poweredByComponent);
		}
	}
	
	private Resource getLogoResource()
	{
		if (logoFilename == null)
		{
			return Constants.RESOURCE_Logo;
		}
		if (logoFilename.trim().isEmpty())
		{
			return Constants.RESOURCE_Logo;
		}
		
		final File logoFile = new File(logoFilename.trim());
		if (!logoFile.isFile() || !logoFile.canRead())
		{
			logger.warn("Using default log because {} does not exist or it's not readable", logoFile);
			return Constants.RESOURCE_Logo;
		}
		
		return new FileResource(logoFile);
	}

	@Override
	public void enter(final ViewChangeEvent event)
	{
		loadFromRememberMeToken();
	}

	private void loadFromRememberMeToken()
	{
		final RememberMeToken token = rememberMeService.getRememberMeTokenIfEnabled();
		if (token == null)
		{
			return;
		}
		email.setValue(token.getUser());
		password.setValue(token.getToken());
	}

	private void onUserLogin(final String email, final String password)
	{
		final User user = loginService.login(email, password);

		// Create remember me cookie (FRESH-197)
		rememberMeService.createRememberMeCookieIfEnabled(user);

		UIEventBus.post(UserLoggedInEvent.of(user));
	}

	protected void onForgotPassword(final String email)
	{
		if (Strings.isNullOrEmpty(email))
		{
			throw new PasswordResetFailedException(email, i18n.get("LoginView.passwordReset.error.fillEmail"));
		}

		final String passwordResetKey = loginService.generatePasswordResetKey(email);
		final URI passwordResetURI = PasswordResetView.buildPasswordResetURI(passwordResetKey);
		loginService.sendPasswordResetKey(email, passwordResetURI);

		final Notification notification = new Notification(
				i18n.get("LoginView.passwordReset.notification.title") // "Password reset"
				, i18n.get("LoginView.passwordReset.notification.message") // "Your password has been reset. Please check your email and click on that link"
				, Type.HUMANIZED_MESSAGE);
		notification.setDelayMsec(15 * 1000);
		notification.show(Page.getCurrent());
	}
}
