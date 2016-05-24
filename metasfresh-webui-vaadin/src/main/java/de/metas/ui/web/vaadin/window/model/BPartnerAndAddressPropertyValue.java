package de.metas.ui.web.vaadin.window.model;

import java.util.Map;

import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.model.PropertyNameDependenciesMap.DependencyType;
import de.metas.ui.web.vaadin.window.shared.datatype.ComposedValue;

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

/**
 * Composed propery value: BPartner, address and contact
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
final class BPartnerAndAddressPropertyValue extends CalculatedPropertyValue
{
	public static final String PARTNAME_C_BPartner_ID = "C_BPartner_ID";
	public static final String PARTNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";
	public static final String PARTNAME_AD_User_ID = "AD_User_ID";

	private final PropertyNameDependenciesMap dependencies;
	private final PropertyValue bpartnerPropertyValue;
	private final PropertyValue bpLocationPropertyValue;
	private final PropertyValue bpContactPropertyValue;

	BPartnerAndAddressPropertyValue(final PropertyValueBuilder builder)
	{
		super(builder.getPropertyName());

		final Map<PropertyName, PropertyValue> childProperyValues = builder.getChildPropertyValues();
		PropertyValue bpartnerPropertyValue = null;
		PropertyValue bpLocationPropertyValue = null;
		PropertyValue bpContactPropertyValue = null;
		for (final PropertyValue childValue : childProperyValues.values())
		{
			final String childPartName = childValue.getComposedValuePartName();
			if (PARTNAME_C_BPartner_ID.equals(childPartName))
			{
				bpartnerPropertyValue = childValue;
			}
			else if (PARTNAME_C_BPartner_Location_ID.equals(childPartName))
			{
				bpLocationPropertyValue = childValue;
			}
			else if (PARTNAME_AD_User_ID.equals(childPartName))
			{
				bpContactPropertyValue = childValue;
			}
		}

		// Fallback to property names
		if (bpartnerPropertyValue == null)
		{
			bpartnerPropertyValue = childProperyValues.get(PropertyName.of(PARTNAME_C_BPartner_ID));
		}
		if (bpLocationPropertyValue == null)
		{
			bpLocationPropertyValue = childProperyValues.get(PropertyName.of(PARTNAME_C_BPartner_Location_ID));
		}
		if (bpContactPropertyValue == null)
		{
			bpContactPropertyValue = childProperyValues.get(PropertyName.of(PARTNAME_AD_User_ID));
		}

		// Validate
		if (bpartnerPropertyValue == null || bpLocationPropertyValue == null || bpContactPropertyValue == null)
		{
			throw new RuntimeException("Value " + getClass().getSimpleName() + " is incompletelly defined");
		}

		this.bpartnerPropertyValue = bpartnerPropertyValue;
		this.bpLocationPropertyValue = bpLocationPropertyValue;
		this.bpContactPropertyValue = bpContactPropertyValue;
		
		final PropertyName propertyName = builder.getPropertyName();
		this.dependencies = PropertyNameDependenciesMap.builder()
				.add(propertyName, bpartnerPropertyValue.getName(), DependencyType.Value)
				.add(propertyName, bpLocationPropertyValue.getName(), DependencyType.Value)
				.add(propertyName, bpContactPropertyValue.getName(), DependencyType.Value)
				.build();
	}

	@Override
	public PropertyNameDependenciesMap getDependencies()
	{
		return dependencies;
	}

	@Override
	protected Object calculateValue(final PropertyValueCollection values)
	{
		final String bpartnerStr = getPartAsString(bpartnerPropertyValue);
		final String bpLocationStr = getPartAsString(bpLocationPropertyValue);
		final String bpContactStr = getPartAsString(bpContactPropertyValue);

		final String displayName = bpartnerStr;
		final String longDisplayName = ""
				+ bpartnerStr
				+ "<br/>" + bpLocationStr
				+ "<br/>" + bpContactStr;
		return ComposedValue.of(null, displayName, longDisplayName);
	}

	private final String getPartAsString(final PropertyValue partValue)
	{
		return partValue.getValueAsString().or("");
	}

}