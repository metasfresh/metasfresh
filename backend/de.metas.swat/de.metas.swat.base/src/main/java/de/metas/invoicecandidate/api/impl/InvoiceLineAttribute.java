package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import com.google.common.annotations.VisibleForTesting;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;

import java.math.BigDecimal;

@VisibleForTesting
public final class InvoiceLineAttribute implements IInvoiceLineAttribute
{
	private final String aggregationKey;

	@ToStringBuilder(skip = true)
	private final I_M_AttributeInstance attributeInstanceTemplate;

	/**
	 * @param attributeInstance attribute instance used to extract the informations from
	 */
	public InvoiceLineAttribute(@NonNull final I_M_AttributeInstance attributeInstance)
	{
			//
		// Build aggregation key
		{
			final int attributeId = attributeInstance.getM_Attribute_ID();
			final I_M_Attribute attribute = Services.get(IAttributeDAO.class).getAttributeById(attributeId);
			final StringBuilder aggregationKey = new StringBuilder();
			aggregationKey.append(attribute.getName());
			aggregationKey.append("=");
			String value = attributeInstance.getValue();
			if (Check.isEmpty(value))
			{
				BigDecimal valueNumber = attributeInstance.getValueNumber();
				valueNumber = NumberUtils.stripTrailingDecimalZeros(valueNumber);
				value = valueNumber.toString(); // try valueNumber too
			}
			aggregationKey.append(value);

			this.aggregationKey = aggregationKey.toString();
		}

		//
		// Copy given attribute instance, we will use it as a template
		this.attributeInstanceTemplate = InterfaceWrapperHelper.newInstance(I_M_AttributeInstance.class, attributeInstance);
		InterfaceWrapperHelper.copyValues(attributeInstance, attributeInstanceTemplate);
		this.attributeInstanceTemplate.setM_AttributeSetInstance(null); // reset it
		this.attributeInstanceTemplate.setIsActive(true);
		InterfaceWrapperHelper.setSaveDeleteDisabled(attributeInstanceTemplate, true);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final InvoiceLineAttribute other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.aggregationKey, other.aggregationKey)
				.isEqual();
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(aggregationKey)
				.toHashcode();
	}

	@Override
	public String toAggregationKey()
	{
		return aggregationKey;
	}

	@Override
	public I_M_AttributeInstance getAttributeInstanceTemplate()
	{
		return attributeInstanceTemplate;
	}
}
