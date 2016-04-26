package de.metas.ui.web.vaadin.window.data;

import java.util.Locale;

import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import com.google.common.base.Preconditions;
import com.vaadin.data.util.converter.Converter;

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
public class NamePairConverter implements Converter<Object, Object>
{
	public static final NamePairConverter of(final NamePairProvider namePairProvider)
	{
		return new NamePairConverter(namePairProvider);
	}

	private final NamePairProvider namePairProvider;
	private final Class<? extends Object> modelClass;
	private final Class<? extends Object> presentationClass;

	private NamePairConverter(final NamePairProvider namePairProvider)
	{
		super();
		this.namePairProvider = Preconditions.checkNotNull(namePairProvider, "namePairProvider is null");

		if (namePairProvider.isNumericKey())
		{
			modelClass = Integer.class;
			presentationClass = KeyNamePair.class;
		}
		else
		{
			modelClass = String.class;
			presentationClass = ValueNamePair.class;
		}
	}

	@Override
	public Object convertToModel(final Object presentationValue, final Class<? extends Object> targetType, final Locale locale) throws Converter.ConversionException
	{
		if (presentationValue == null)
		{
			return null;
		}
		else if (modelClass.isAssignableFrom(presentationValue.getClass()))
		{
			final Object modelValue = presentationValue;
			return modelValue;
		}
		else if (presentationValue instanceof ValueNamePair)
		{
			final String modelValue = ((ValueNamePair)presentationValue).getValue();
			return modelValue;
		}
		else if (presentationValue instanceof KeyNamePair)
		{
			final int modelValue = ((KeyNamePair)presentationValue).getKey();
			return modelValue;
		}
		else
		{
			throw new IllegalArgumentException("Value type is not supported: " + presentationValue + "(" + presentationValue.getClass() + ")");
		}
	}

	@Override
	public Object convertToPresentation(final Object modelValue, final Class<? extends Object> targetType, final Locale locale) throws Converter.ConversionException
	{
		if (modelValue == null)
		{
			return null;
		}

		NamePair presentationValue = namePairProvider.getNamePair(modelValue);
		return presentationValue;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<Object> getModelType()
	{
		return (Class<Object>)modelClass;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<Object> getPresentationType()
	{
		return (Class<Object>)presentationClass;
	}
}
