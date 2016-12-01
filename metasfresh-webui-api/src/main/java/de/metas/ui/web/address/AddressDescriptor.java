package de.metas.ui.web.address;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;

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

public class AddressDescriptor
{
	/* package */public static AddressDescriptor of(final DocumentEntityDescriptor entityDescriptor, final AddressLayout layout)
	{
		return new AddressDescriptor(entityDescriptor, layout);
	}

	private final DocumentEntityDescriptor entityDescriptor;
	private final AddressLayout layout;

	private AddressDescriptor(final DocumentEntityDescriptor entityDescriptor, final AddressLayout layout)
	{
		this.entityDescriptor = entityDescriptor;
		this.layout = layout;
	}

	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
	}

	public AddressLayout getLayout()
	{
		return layout;
	}
}
