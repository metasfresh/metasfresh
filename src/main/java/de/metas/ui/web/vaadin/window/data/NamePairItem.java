package de.metas.ui.web.vaadin.window.data;

import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;

/*
 * #%L
 * de.metas.ui.web.vaadin
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
public class NamePairItem extends PropertysetItem
{
	public static final NamePairItem of(final NamePair namePair)
	{
		return new NamePairItem(namePair);
	}

	public static final NamePairItem of(final String value, final String name)
	{
		return new NamePairItem(new ValueNamePair(value, name));
	}

	static final String PROPERTY_ID_Key = "key";
	static final String PROPERTY_ID_Name = "name";
	
	private final NamePair namePair;

	public NamePairItem(final NamePair namePair)
	{
		super();
		this.namePair = namePair;
		
		final ObjectProperty<?> keyProperty;
		if (namePair instanceof ValueNamePair)
		{
			final ValueNamePair vnp = (ValueNamePair)namePair;
			keyProperty = new ObjectProperty<>(vnp.getValue());
		}
		else if (namePair instanceof KeyNamePair)
		{
			final KeyNamePair knp = (KeyNamePair)namePair;
			keyProperty = new ObjectProperty<>(knp.getKey());
		}
		else
		{
			throw new IllegalArgumentException("Name pair not supported: " + namePair + " (" + (namePair == null ? "-" : namePair.getClass()) + ")");
		}
		
		keyProperty.setReadOnly(true);
		super.addItemProperty(PROPERTY_ID_Key, keyProperty);
		final ObjectProperty<String> nameProperty = new ObjectProperty<>(namePair.getName());
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

	public NamePair getItemId()
	{
		return namePair;
	}
	
	public <T extends NamePair> T getNamePair()
	{
		@SuppressWarnings("unchecked")
		final T namePairCasted = (T)namePair;
		return namePairCasted;
	}

}
