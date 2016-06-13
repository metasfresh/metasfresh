package de.metas.ui.web.vaadin.login;

import java.util.List;
import java.util.ResourceBundle;

import org.adempiere.util.Check;
import org.compiere.util.KeyNamePair;
import org.compiere.util.ValueNamePair;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.metas.ui.web.vaadin.i18n.ResourceBundleTranslator;
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

@SuppressWarnings("serial")
public class LoginViewImpl extends VerticalLayout implements LoginView
{
	private final ResourceBundleTranslator translator = ResourceBundleTranslator.newInstance();

	private final VerticalLayout mainPanel = new VerticalLayout();
	private Component currentPanel = null;
	private final UserLoginPanel loginPanel = new UserLoginPanel();
	private final RolePanel rolePanel = new RolePanel();

	private LoginViewListener listener;

	public LoginViewImpl()
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

	@Override
	public Component getComponent()
	{
		return this;
	}

	@Override
	public void setListener(final LoginViewListener listener)
	{
		this.listener = listener;
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

	public void showLoginPanel()
	{
		setCurrentPanel(loginPanel);
		loginPanel.focus();
	}

	@Override
	public void showRolePanel()
	{
		setCurrentPanel(rolePanel);
		rolePanel.focus();
	}

	@Override
	public void setResourceBundle(final ResourceBundle res)
	{
		translator.setResourceBundle(res);
	}

	@Override
	public void setLanguages(final List<ValueNamePair> languages, final ValueNamePair defaultLanguage)
	{
		loginPanel.setLanguages(languages, defaultLanguage);
	}

	@Override
	public void setWarehouseVisible(final boolean visible)
	{
		rolePanel.setWarehouseVisible(visible);
	}

	@Override
	public void setRoles(final List<KeyNamePair> roles)
	{
		rolePanel.setRoles(roles);
	}

	@Override
	public void setClients(final List<KeyNamePair> clients)
	{
		rolePanel.setClients(clients);
	}

	@Override
	public void setOrgs(final List<KeyNamePair> orgs)
	{
		rolePanel.setOrgs(orgs);
	}

	@Override
	public void setWarehouses(final List<KeyNamePair> warehouses)
	{
		rolePanel.setWarehouses(warehouses);
	}

	private static final void setContainerValues(final BeanItemContainer<KeyNamePair> container, final ComboBox combobox, final List<KeyNamePair> values)
	{
		container.removeAllItems();
		container.addAll(values);

		if (!values.isEmpty())
		{
			combobox.select(values.get(0));
		}
	}

	private static final <T> T extractValue(final Property.ValueChangeEvent event)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)event.getProperty().getValue();
		return value;
	}

	private final class UserLoginPanel extends HorizontalLayout
	{
		private final TextField fUsername;
		private final PasswordField fPassword;
		private final Button btnLogin;

		private final BeanItemContainer<ValueNamePair> languagesContainer = new BeanItemContainer<>(ValueNamePair.class);
		private final ComboBox fLanguage;

		public UserLoginPanel()
		{
			super();
			setSpacing(true);

			translator.addOnComponentAttach(this);

			fUsername = new TextField("User");
			fUsername.setRequired(true);
			fUsername.setIcon(FontAwesome.USER);
			fUsername.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

			fPassword = new PasswordField("Password");
			fPassword.setRequired(true);
			fPassword.setIcon(FontAwesome.LOCK);
			fPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

			fLanguage = new ComboBox("Language", languagesContainer);
			fLanguage.setRequired(true);
			fLanguage.setItemCaptionMode(ItemCaptionMode.PROPERTY);
			fLanguage.setItemCaptionPropertyId("name");
			fLanguage.addValueChangeListener(event -> listener.viewLanguageChanged(extractValue(event)));

			btnLogin = new Button("Login");
			btnLogin.addStyleName(ValoTheme.BUTTON_PRIMARY);
			btnLogin.setClickShortcut(KeyCode.ENTER);
			btnLogin.addClickListener(event -> listener.viewAuthenticate(fUsername.getValue(), fPassword.getValue()));

			addComponents(fUsername, fPassword, fLanguage, btnLogin);
			setComponentAlignment(btnLogin, Alignment.BOTTOM_LEFT);
		}

		@Override
		protected void focus()
		{
			btnLogin.focus();
		}

		public void setLanguages(final List<ValueNamePair> languages, final ValueNamePair defaultLanguage)
		{
			languagesContainer.removeAllItems();
			languagesContainer.addAll(languages);
			fLanguage.setValue(defaultLanguage);
		}
	}

	private final class RolePanel extends VerticalLayout
	{
		private final ComboBox fRole;
		private final BeanItemContainer<KeyNamePair> rolesContainer = new BeanItemContainer<>(KeyNamePair.class);

		private final ComboBox fClient;
		private final BeanItemContainer<KeyNamePair> clientContainer = new BeanItemContainer<>(KeyNamePair.class);

		private final ComboBox fOrg;
		private final BeanItemContainer<KeyNamePair> orgContainer = new BeanItemContainer<>(KeyNamePair.class);

		private final ComboBox fWarehouse;
		private final BeanItemContainer<KeyNamePair> warehouseContainer = new BeanItemContainer<>(KeyNamePair.class);

		private final Button btnOk;
		private final Button btnBack;

		public RolePanel()
		{
			super();
			setSpacing(true);

			translator.addOnComponentAttach(this);

			fRole = new ComboBox("Role", rolesContainer);
			fRole.setRequired(true);
			fRole.setNullSelectionAllowed(false);
			fRole.addValueChangeListener(event -> onRoleChanged(extractValue(event)));

			fClient = new ComboBox("Client", clientContainer);
			fClient.setRequired(true);
			fClient.setNullSelectionAllowed(false);
			fClient.addValueChangeListener(event -> onClientChanged(extractValue(event)));

			fOrg = new ComboBox("Organization", orgContainer);
			fOrg.setRequired(true);
			fOrg.setNullSelectionAllowed(false);
			fOrg.addValueChangeListener(event -> onOrgChanged(extractValue(event)));

			fWarehouse = new ComboBox("Warehouse", warehouseContainer);

			// Buttons panel
			final HorizontalLayout buttonsPanel = new HorizontalLayout();
			{
				translator.addOnComponentAttach(buttonsPanel);

				btnOk = new Button("Ok");
				btnOk.setClickShortcut(KeyCode.ENTER);
				btnOk.addClickListener(event -> onOkPressed());
				
				btnBack = new Button("Cancel");
				btnBack.addClickListener(event -> showLoginPanel());
				
				buttonsPanel.addComponents(btnBack, btnOk);
			}

			addComponents(fRole, fClient, fOrg, fWarehouse, buttonsPanel);
		}

		public void setRoles(final List<KeyNamePair> roles)
		{
			setContainerValues(rolesContainer, fRole, roles);
		}

		public void setClients(final List<KeyNamePair> clients)
		{
			setContainerValues(clientContainer, fClient, clients);
		}

		public void setOrgs(final List<KeyNamePair> orgs)
		{
			setContainerValues(orgContainer, fOrg, orgs);
		}

		public void setWarehouses(final List<KeyNamePair> warehouses)
		{
			setContainerValues(warehouseContainer, fWarehouse, warehouses);
		}

		public void setWarehouseVisible(final boolean visible)
		{
			fWarehouse.setVisible(visible);
		}
		
		private void onOkPressed()
		{
			final KeyNamePair roleKNP = (KeyNamePair)fRole.getValue();
			final KeyNamePair clientKNP = (KeyNamePair)fClient.getValue();
			final KeyNamePair orgKNP = (KeyNamePair)fOrg.getValue();
			final KeyNamePair warehouseKNP = fWarehouse.isVisible() ? (KeyNamePair)fWarehouse.getValue() : null;
			listener.viewLoginComplete(roleKNP, clientKNP, orgKNP, warehouseKNP);
		}

		private void onRoleChanged(final KeyNamePair roleKNP)
		{
			fRole.setComponentError(null);
			listener.viewRoleChanged(roleKNP);
		}

		private void onClientChanged(final KeyNamePair adClientKNP)
		{
			fClient.setComponentError(null);
			listener.viewClientChanged(adClientKNP);
		}

		private void onOrgChanged(final KeyNamePair orgKNP)
		{
			fOrg.setComponentError(null);
			listener.viewOrgChanged(orgKNP);
		}

		@Override
		protected void focus()
		{
			fRole.focus();
		}
	}

}
