package de.metas.ui.web.vaadin.window.model;

import java.util.Map;

import com.google.common.base.Optional;

import de.metas.ui.web.vaadin.window.PropertyName;

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

public interface PropertyValue
{
	PropertyName getName();

	String getComposedValuePartName();

	PropertyNameDependenciesMap getDependencies();

	void onDependentPropertyValueChanged(DependencyValueChangedEvent event);

	void setValue(final Object value);

	Object getValue();

	Optional<String> getValueAsString();

	Map<PropertyName, PropertyValue> getChildPropertyValues();

	boolean isChanged();
	
	/** @return true if value is read-only for user */
	boolean isReadOnlyForUser();
	
	/** @return true if value is calculated and it cannot be set using {@link #setValue(Object)} */
	boolean isCalculated();
}
