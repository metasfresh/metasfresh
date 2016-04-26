package de.metas.ui.web.vaadin.login;

import java.util.List;

import org.adempiere.util.Check;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.metas.ui.web.vaadin.event.UIEventBus;
import de.metas.ui.web.vaadin.i18n.ResourceBundleTranslator;
import de.metas.ui.web.vaadin.login.event.UserLoggedInEvent;
import de.metas.ui.web.vaadin.theme.Theme;

/*
 * #%L
 * test_vaadin
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

public class LoginView extends VerticalLayout
{
	private static final long serialVersionUID = -2268417649784117250L;

	private final LoginModel loginModel = new LoginModel();
	private final ResourceBundleTranslator translator = new ResourceBundleTranslator(loginModel.getResourceBundle());

	private final VerticalLayout mainPanel = new VerticalLayout();
	private Component currentPanel = null;
	private final UserLoginPanel loginPanel = new UserLoginPanel();
	private final RolePanel rolePanel = new RolePanel();
	

	public LoginView()
	{
		super();

		setSizeFull();

		mainPanel.setSizeUndefined();
		mainPanel.setSpacing(true);
		// loginPanel.addStyleName("login-panel");

		//
		// Header
		{
			final CssLayout labels = new CssLayout();
			labels.addStyleName("labels");

			final Image logo = new Image();
			logo.setSource(Theme.getProductLogoLargeResource());
			mainPanel.addComponent(logo);
		}

		//
		// Fields
		{
			currentPanel = loginPanel;
			mainPanel.addComponent(loginPanel);
			loginPanel.focus();
		}

		//
		addComponent(mainPanel);
		setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);
	}

	private void setCurrentPanel(final Component panel)
	{
		Check.assumeNotNull(panel, "panel not null");

		if (currentPanel == panel)
		{
			return;
		}

		mainPanel.replaceComponent(currentPanel, panel);
		currentPanel = panel;
	}

	private final void onUserLogin(final String username, final String password)
	{
		loginModel.authenticate(username, password);

		setCurrentPanel(rolePanel);
		rolePanel.activate();
	}

	private final void onRoleSelected(final KeyNamePair role, final KeyNamePair client, final KeyNamePair org, final KeyNamePair warehouse)
	{
		loginModel.loginComplete(role, client, org, warehouse);
		UIEventBus.post(new UserLoggedInEvent());
	}

	private final void onBackToLogin()
	{
		setCurrentPanel(loginPanel);
		loginPanel.focus();
	}

	private final void onLanguageChanged(final Language language)
	{
		loginModel.setLanguage(language);
		translator.setResourceBundle(loginModel.getResourceBundle());
	}

	@SuppressWarnings("serial")
	private final class UserLoginPanel extends HorizontalLayout
	{
		private static final long serialVersionUID = 3065173902901993715L;
		private final TextField username;
		private final Button loginButton;
		private final PasswordField password;
		private final ComboBox language;

		public UserLoginPanel()
		{
			super();
			setSpacing(true);
			
			translator.addOnComponentAttach(this);

			this.username = new TextField("User");
			username.setIcon(FontAwesome.USER);
			username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

			this.password = new PasswordField("Password");
			password.setIcon(FontAwesome.LOCK);
			password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

			loginButton = new Button("Login");
			loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
			loginButton.setClickShortcut(KeyCode.ENTER);

			final BeanItemContainer<Language> languagesContainer = new BeanItemContainer<>(Language.class, loginModel.getAvailableLanguages());
			this.language = new ComboBox("Language", languagesContainer);
			language.setItemCaptionMode(ItemCaptionMode.PROPERTY);
			language.setItemCaptionPropertyId("name");
			language.addValueChangeListener(new Property.ValueChangeListener()
			{
				@Override
				public void valueChange(final ValueChangeEvent event)
				{
					final Language language = (Language)event.getProperty().getValue();
					onLanguageChanged(language);
				}
			});
			language.setValue(loginModel.getLanguage());

			loginButton.addClickListener(new Button.ClickListener()
			{
				private static final long serialVersionUID = -6602272662219908156L;

				@Override
				public void buttonClick(final ClickEvent event)
				{
					onUserLogin(username.getValue(), password.getValue());
				}
			});

			addComponents(username, password, language, loginButton);
			setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);
		}
		
		@Override
		protected void focus()
		{
			loginButton.focus();
		}
	}

	@SuppressWarnings("serial")
	private final class RolePanel extends VerticalLayout
	{
		private final ComboBox role;
		private final ComboBox adClient;
		private final ComboBox org;
		private final ComboBox warehouse;
		private final Button okButton;
		private final Button backButton;

		public RolePanel()
		{
			super();
			setSpacing(true);

			translator.addOnComponentAttach(this);

			role = new ComboBox("Role");
			role.setRequired(true);
			role.setNullSelectionAllowed(false);
			role.addValueChangeListener(new ValueChangeListener()
			{
				@Override
				public void valueChange(ValueChangeEvent event)
				{
					KeyNamePair roleKNP = (KeyNamePair)event.getProperty().getValue();
					onRoleChanged(roleKNP);
				}
			});

			adClient = new ComboBox("Client");
			adClient.setRequired(true);
			adClient.setNullSelectionAllowed(false);
			adClient.addValueChangeListener(new ValueChangeListener()
			{
				@Override
				public void valueChange(ValueChangeEvent event)
				{
					KeyNamePair adClientKNP = (KeyNamePair)event.getProperty().getValue();
					onClientChanged(adClientKNP);
				}
			});

			org = new ComboBox("Organization");
			org.setRequired(true);
			org.setNullSelectionAllowed(false);
			org.addValueChangeListener(new ValueChangeListener()
			{
				@Override
				public void valueChange(ValueChangeEvent event)
				{
					KeyNamePair orgKNP = (KeyNamePair)event.getProperty().getValue();
					onOrgChanged(orgKNP);
				}
			});

			warehouse = new ComboBox("Warehouse");
			warehouse.setVisible(loginModel.isShowWarehouseOnLogin());

			// Buttons panel
			final HorizontalLayout buttonsPanel = new HorizontalLayout();
			{
				translator.addOnComponentAttach(buttonsPanel);
				
				this.okButton = new Button("Ok");
				okButton.addClickListener(new Button.ClickListener()
				{
					@Override
					public void buttonClick(final ClickEvent event)
					{
						final KeyNamePair roleKNP = (KeyNamePair)role.getValue();
						final KeyNamePair clientKNP = (KeyNamePair)adClient.getValue();
						final KeyNamePair orgKNP = (KeyNamePair)org.getValue();
						final KeyNamePair warehouseKNP = loginModel.isShowWarehouseOnLogin() ? (KeyNamePair)warehouse.getValue() : null;
						onRoleSelected(roleKNP, clientKNP, orgKNP, warehouseKNP);
					}
				});
				this.backButton = new Button("Cancel");
				backButton.addClickListener(new Button.ClickListener()
				{
					@Override
					public void buttonClick(ClickEvent event)
					{
						onBackToLogin();
					}
				});
				buttonsPanel.addComponents(backButton, okButton);
			}

			addComponents(role, adClient, org, warehouse, buttonsPanel);
		}

		public void activate()
		{
			final List<KeyNamePair> roles = loginModel.getRoles();
			role.setContainerDataSource(new BeanItemContainer<>(KeyNamePair.class, roles));
			if (!roles.isEmpty())
			{
				role.select(roles.get(0));
			}

			role.focus();
		}

		private void onRoleChanged(KeyNamePair roleKNP)
		{
			role.setComponentError(null);

			final List<KeyNamePair> adClients = loginModel.getAD_Clients(roleKNP);
			adClient.setContainerDataSource(new BeanItemContainer<>(KeyNamePair.class, adClients));
			if (!adClients.isEmpty())
			{
				adClient.select(adClients.get(0));
			}
		}

		private void onClientChanged(KeyNamePair adClientKNP)
		{
			adClient.setComponentError(null);

			final List<KeyNamePair> orgs = loginModel.getAD_Orgs(adClientKNP);
			org.setContainerDataSource(new BeanItemContainer<>(KeyNamePair.class, orgs));
			if (!orgs.isEmpty())
			{
				org.select(orgs.get(0));
			}
		}

		private void onOrgChanged(KeyNamePair orgKNP)
		{
			org.setComponentError(null);

			final List<KeyNamePair> warehouses = loginModel.getM_Warehouses(orgKNP);
			warehouse.setContainerDataSource(new BeanItemContainer<>(KeyNamePair.class, warehouses));
			if (!warehouses.isEmpty())
			{
				warehouse.select(warehouses.get(0));
			}
		}

		@Override
		protected void focus()
		{
			role.focus();
		}
	}

}
