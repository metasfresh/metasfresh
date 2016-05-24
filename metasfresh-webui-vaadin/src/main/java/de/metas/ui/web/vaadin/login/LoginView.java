package de.metas.ui.web.vaadin.login;

import java.util.List;
import java.util.ResourceBundle;

import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;

import com.vaadin.ui.Component;

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

public interface LoginView
{
	Component getComponent();

	void setListener(LoginViewListener listener);

	void showRolePanel();

	void setResourceBundle(ResourceBundle res);

	void setLanguages(List<Language> languages, Language defaultLanguage);

	void setWarehouseVisible(boolean visible);

	void setRoles(List<KeyNamePair> roles);

	void setClients(List<KeyNamePair> clients);

	void setOrgs(List<KeyNamePair> orgs);

	void setWarehouses(List<KeyNamePair> warehouses);

}
