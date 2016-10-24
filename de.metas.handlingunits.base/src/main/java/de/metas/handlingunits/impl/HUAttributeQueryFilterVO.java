package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import de.metas.handlingunits.model.I_M_HU_Attribute;

/**
 * Helper class used to filter/match {@link I_M_HU_Attribute}s, {@link IAttributeSet}s.
 *
 * @author tsa
 *
 */
/* package */final class HUAttributeQueryFilterVO
{
	public static final String ATTRIBUTEVALUETYPE_Unknown = null;

	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	private final int attributeId;
	@ToStringBuilder(skip = true)
	private final I_M_Attribute attribute;
	private final String attributeValueType;
	private final ModelColumn<I_M_HU_Attribute, Object> huAttributeValueColumn;
	private final Set<Object> _values = new HashSet<>();
	private Set<Object> _valuesAndSubstitutes = null;

	/* package */HUAttributeQueryFilterVO(final I_M_Attribute attribute, final String attributeValueType)
	{
		super();
		Check.assumeNotNull(attribute, "attribute not null");
		attributeId = attribute.getM_Attribute_ID();
		this.attribute = attribute;

		if (attributeValueType == ATTRIBUTEVALUETYPE_Unknown)
		{
			final String type = attribute.getAttributeValueType();
			if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(type))
			{
				this.attributeValueType = X_M_Attribute.ATTRIBUTEVALUETYPE_Number;
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(type))
			{
				this.attributeValueType = X_M_Attribute.ATTRIBUTEVALUETYPE_Date;
			}
			else
			{
				this.attributeValueType = X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40;
			}
		}
		else
		{
			this.attributeValueType = attributeValueType;
		}

		if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(this.attributeValueType))
		{
			huAttributeValueColumn = I_M_HU_Attribute.COLUMN_ValueNumber;
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(this.attributeValueType))
		{
			huAttributeValueColumn = I_M_HU_Attribute.COLUMN_ValueDate;
		}
		else
		{
			huAttributeValueColumn = I_M_HU_Attribute.COLUMN_Value;
		}
	}

	private HUAttributeQueryFilterVO(final HUAttributeQueryFilterVO from)
	{
		super();
		attributeId = from.attributeId;
		attribute = from.attribute;
		attributeValueType = from.attributeValueType;
		huAttributeValueColumn = from.huAttributeValueColumn;
		_valuesAndSubstitutes = from._valuesAndSubstitutes == null ? null : new HashSet<>(_valuesAndSubstitutes);
		_values.addAll(from._values);
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.append(attributeId)
				// .append(attribute)
				.append(attributeValueType)
				.append(huAttributeValueColumn)
				.append(_values)
				// .append(_valuesAndSubstitutes) // those are loaded on demand based on the other values
				.toHashCode();
	}

	@Override
	public boolean equals(final Object otherObj)
	{
		if (this == otherObj)
		{
			return true;
		}

		final HUAttributeQueryFilterVO other = EqualsBuilder.getOther(this, otherObj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(attributeId, other.attributeId)
				// .append(attribute)
				.append(attributeValueType, other.attributeValueType)
				.append(huAttributeValueColumn, other.huAttributeValueColumn)
				.append(_values, other._values)
				// .append(_valuesAndSubstitutes) // those are loaded on demand based on the other values
				.isEqual();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public HUAttributeQueryFilterVO copy()
	{
		return new HUAttributeQueryFilterVO(this);
	}

	@Override
	public HUAttributeQueryFilterVO clone()
	{
		return copy();
	}

	public final String getSummary()
	{
		final String attributeName = getM_Attribute().getName();
		final Set<Object> values = getValuesAndSubstitutes();
		final String valuesStr = ListUtils.toString(values, ", ");

		final StringBuilder sb = new StringBuilder();
		sb.append(attributeName).append(": ").append(valuesStr);
		return sb.toString();
	}

	/**
	 * NOTE: keep in sync with {@link #matches(IAttributeSet)}
	 *
	 * @param this
	 * @return
	 */
	public final IQueryFilter<I_M_HU_Attribute> createQueryFilter()
	{
		final ICompositeQueryFilter<I_M_HU_Attribute> attributeFilter = queryBL.createCompositeQueryFilter(I_M_HU_Attribute.class);
		attributeFilter.setJoinAnd();

		// Only HU_Attributes for our M_Attribute_ID
		final int attributeId = getM_Attribute_ID();
		attributeFilter.addEqualsFilter(I_M_HU_Attribute.COLUMN_M_Attribute_ID, attributeId);

		//
		// Only those M_HU_Attributes which are matching our Values list
		final Set<Object> valuesAndSubstitutes = getValuesAndSubstitutes();
		attributeFilter.addInArrayFilter(getHUAttributeValueColumn(), valuesAndSubstitutes);

		return attributeFilter;
	}

	/**
	 * NOTE: keep in sync with {@link #createQueryFilter()}
	 */
	public final boolean matches(final IAttributeSet attributes)
	{
		//
		// Check if attribute set has our attribute
		final int attributeId = getM_Attribute_ID();
		final I_M_Attribute attribute = attributes.getAttributeByIdIfExists(attributeId);
		if (attribute == null)
		{
			// the attribute which is required by our filter does not exist
			// TODO: i think we shall check if the filter is asking for a null value of it... not sure yet!
			// But atm, we consider it as NOT MATCHED because we require an attribute which does not exist
			return false; // not matched
		}

		//
		// Check if current attribute's value is in our list of values that we require
		final Object recordAttributeValue = attributes.getValue(attribute);
		final Set<Object> queryAttributeValues = getValuesAndSubstitutes();
		if (!queryAttributeValues.contains(recordAttributeValue))
		{
			return false; // not matched
		}

		//
		// If we reach this point it means that the attribute set was matching our criterias
		return true; // matched
	}

	public HUAttributeQueryFilterVO addValue(final Object value)
	{
		final boolean added = _values.add(value);
		if (added)
		{
			_valuesAndSubstitutes = null;
		}

		return this;
	}

	public HUAttributeQueryFilterVO addValues(final Collection<? extends Object> values)
	{
		final boolean added = _values.addAll(values);
		if (added)
		{
			_valuesAndSubstitutes = null;
		}

		return this;
	}

	public final int getM_Attribute_ID()
	{
		return attributeId;
	}

	/**
	 * @return attribute; never return <code>null</code>
	 */
	private final I_M_Attribute getM_Attribute()
	{
		return attribute;
	}

	public HUAttributeQueryFilterVO setAttributeValueType(final String attributeValueType)
	{
		// NOTE: actually we are not setting it but just validate if it's the same

		if (attributeValueType == ATTRIBUTEVALUETYPE_Unknown)
		{
			return this;
		}

		Check.assume(
				Check.equals(this.attributeValueType, attributeValueType),
				"Invalid attributeValueType for {}. Expected: {}",
				this, attributeValueType
				);

		return this;
	}

	private ModelColumn<I_M_HU_Attribute, Object> getHUAttributeValueColumn()
	{
		return huAttributeValueColumn;
	}

	private Set<Object> getValues()
	{
		return _values;
	}

	private Set<Object> getValuesAndSubstitutes()
	{
		if (_valuesAndSubstitutes != null)
		{
			return _valuesAndSubstitutes;
		}

		final ModelColumn<I_M_HU_Attribute, Object> valueColumn = getHUAttributeValueColumn();
		final boolean isStringValueColumn = I_M_HU_Attribute.COLUMNNAME_Value.equals(valueColumn.getColumnName());

		final Set<Object> valuesAndSubstitutes = new HashSet<>();

		//
		// Iterate current values
		for (final Object value : getValues())
		{
			// Append current value to result
			valuesAndSubstitutes.add(value);

			// Search and append it's substitutes too, if found
			if (isStringValueColumn && value instanceof String)
			{
				final String valueStr = value.toString();
				final I_M_Attribute attribute = getM_Attribute();
				final Set<String> valueSubstitutes = attributeDAO.retrieveAttributeValueSubstitutes(attribute, valueStr);
				valuesAndSubstitutes.addAll(valueSubstitutes);
			}
		}

		_valuesAndSubstitutes = valuesAndSubstitutes;
		return _valuesAndSubstitutes;
	}
}
