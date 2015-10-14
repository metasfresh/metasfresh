package org.adempiere.mm.attributes.spi.impl;

/*
 * #%L
 * ADempiere ERP - Base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.model.I_M_Attribute;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

/**
 * Implementation of {@link IAttributeValuesProvider} which is fetching the attributes from {@link I_M_AttributeValue}.
 * 
 * @author tsa
 * 
 */
public class StaticAttributeValuesProvider implements IAttributeValuesProvider
{
	private I_M_Attribute attribute;
	private List<NamePair> attributeValuesNP_RO;
	private Map<String, NamePair> attributeValuesNP_HighVolumeCache = new HashMap<>();
	private NamePair nullAttributeValueNP;

	@Override
	public String toString()
	{
		return "StaticAttributeValuesProvider [attribute=" + attribute + ", attributeValuesNP=" + attributeValuesNP_RO + "]";
	}

	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40;
	}

	private final void load(final I_M_Attribute attribute)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final List<I_M_AttributeValue> attributeValues = attributeDAO.retrieveAttributeValues(attribute);
		final List<NamePair> attributeValuesNP = new ArrayList<>(attributeValues.size());
		NamePair nullAttributeValueNP = null;
		for (final I_M_AttributeValue av : attributeValues)
		{
			if (!av.isActive())
			{
				continue;
			}

			final ValueNamePair vnp = createNamePair(av);
			attributeValuesNP.add(vnp);

			//
			// Null placeholder value (if defined)
			if (av.isNullFieldValue())
			{
				Check.assumeNull(nullAttributeValueNP, "Only one null value shall be defined for {0}, but we found: {1}, {2}",
						attribute.getName(), nullAttributeValueNP, av);
				nullAttributeValueNP = vnp;
			}
		}

		this.attribute = attribute;
		this.attributeValuesNP_RO = Collections.unmodifiableList(attributeValuesNP);
		this.nullAttributeValueNP = nullAttributeValueNP;
	}

	private ValueNamePair createNamePair(final I_M_AttributeValue av)
	{
		final String value = av.getValue();
		final String name = av.getName();
		final ValueNamePair vnp = new ValueNamePair(value, name);
		return vnp;
	}

	@Override
	public boolean isAllowAnyValue(final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		// only the fixed set of values is allowed
		return false;
	}

	@Override
	public List<? extends NamePair> getAvailableValues(final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		if (this.attribute == null)
		{
			load(attribute);
		}
		else if (this.attribute.getM_Attribute_ID() != attribute.getM_Attribute_ID())
		{
			throw new AdempiereException("Attribute " + attribute + " is not supported by " + this);
		}

		return attributeValuesNP_RO;
	}

	@Override
	public NamePair getAttributeValueOrNull(final IAttributeSet attributeSet, final I_M_Attribute attribute, final String value)
	{
		final List<? extends NamePair> availableValues = getAvailableValues(attributeSet, attribute);
		for (final NamePair vnp : availableValues)
		{
			if (Check.equals(vnp.getID(), value))
			{
				return vnp;
			}
		}

		if (isHighVolume())
		{
			final NamePair vnp = findValueDirectly(attribute, value);
			if (vnp != null)
			{
				return vnp;
			}
		}

		return null;
	}

	private final NamePair findValueDirectly(final I_M_Attribute attribute, final String value)
	{
		NamePair vnp = attributeValuesNP_HighVolumeCache.get(value);
		if (vnp != null)
		{
			return vnp;
		}

		final I_M_AttributeValue av = Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(attribute, value);
		if (av == null)
		{
			return null;
		}

		vnp = createNamePair(av);
		attributeValuesNP_HighVolumeCache.put(vnp.getID(), vnp);
		return vnp;

	}

	@Override
	public NamePair getNullValue(final I_M_Attribute attribute)
	{
		// Make sure is loaded
		if (this.attribute == null)
		{
			load(attribute);
		}

		return nullAttributeValueNP;
	}

	@Override
	public boolean isHighVolume()
	{
		return Services.get(IAttributeDAO.class).isHighVolumeValuesList(attribute);
	}
}
