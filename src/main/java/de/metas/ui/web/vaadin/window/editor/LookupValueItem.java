package de.metas.ui.web.vaadin.window.editor;

import com.google.common.base.Preconditions;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;

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

/**
 * {@link Item} implementation which wraps a {@link LookupValue}
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public class LookupValueItem extends PropertysetItem
{
	public static final LookupValueItem of(final LookupValue lookupValue)
	{
		return new LookupValueItem(lookupValue);
	}

	static final String PROPERTY_ID_Key = "key";
	static final String PROPERTY_ID_Name = "name";

	private final LookupValue lookupValue;

	public LookupValueItem(final LookupValue lookupValue)
	{
		super();
		this.lookupValue = Preconditions.checkNotNull(lookupValue);

		final ObjectProperty<?> keyProperty = new ObjectProperty<Object>(lookupValue.getId());
		keyProperty.setReadOnly(true);
		super.addItemProperty(PROPERTY_ID_Key, keyProperty);
		final ObjectProperty<String> nameProperty = new ObjectProperty<>(lookupValue.getDisplayName());
		nameProperty.setReadOnly(true);
		super.addItemProperty(PROPERTY_ID_Name, nameProperty);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean addItemProperty(final Object id, final Property property)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItemProperty(final Object id)
	{
		throw new UnsupportedOperationException();
	}

	public LookupValue getLookupValue()
	{
		return lookupValue;
	}
}
