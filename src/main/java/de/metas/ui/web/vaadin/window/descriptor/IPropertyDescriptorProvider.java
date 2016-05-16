package de.metas.ui.web.vaadin.window.descriptor;

import de.metas.ui.web.vaadin.window.PropertyDescriptor;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPropertyDescriptorProvider
{
	/**
	 * Just an idea: provide a descriptor for an "entry" page, from which we can jump to the other pages
	 *
	 * @return
	 */
	PropertyDescriptor provideEntryWindowdescriptor();

	/**
	 * Provides a descriptor for the given window.
	 *
	 * @param AD_Window_ID the application disctionary's <code>AD_Window_ID</code>.
	 * @return
	 */
	PropertyDescriptor provideForWindow(int AD_Window_ID);
}
