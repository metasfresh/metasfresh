package de.metas.pricing.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ASIBuilder
{
	public static final ASIBuilder newInstance()
	{
		return new ASIBuilder();
	}

	private final Map<AttributeId, Object> attributeValues = new LinkedHashMap<>();
	private boolean skipNullAttributeValues = false;

	public I_M_AttributeSetInstance build()
	{
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.create(Env.getCtx(), I_M_AttributeSetInstance.class, ITrx.TRXNAME_None);
		asi.setM_AttributeSet_ID(0);
		InterfaceWrapperHelper.save(asi);

		attributeValues
				.entrySet()
				.forEach(entry -> createAttributeInstance(asi, entry.getKey(), entry.getValue()));

		return asi;
	}

	private final void createAttributeInstance(final I_M_AttributeSetInstance asi, final AttributeId attributeId, final Object value)
	{
		final I_M_AttributeInstance ai = InterfaceWrapperHelper.newInstance(I_M_AttributeInstance.class, asi);
		ai.setM_AttributeSetInstance(asi);
		ai.setM_Attribute_ID(attributeId.getRepoId());

		if (value == null)
		{
			if (skipNullAttributeValues)
			{
				return;
			}
			else
			{
				ai.setValue(null);
				ai.setValueNumber(null);
				ai.setValueDate(null);
				ai.setM_AttributeValue_ID(-1);
			}
		}
		else if (value instanceof String)
		{
			ai.setValue(value.toString());
		}
		else if (value instanceof BigDecimal)
		{
			ai.setValueNumber((BigDecimal)value);
		}
		else if (value instanceof java.util.Date)
		{
			final Timestamp valueDate = TimeUtil.asTimestamp((java.util.Date)value);
			ai.setValueDate(valueDate);
		}
		else if (value instanceof AttributeListValue)
		{
			final AttributeListValue av = (AttributeListValue)value;
			ai.setValue(av.getValue());
			ai.setM_AttributeValue_ID(av.getId().getRepoId());
		}
		else
		{
			throw new IllegalStateException("Unsupported attribute value: " + value + " (" + value.getClass() + ")");
		}

		InterfaceWrapperHelper.save(ai);
	}

	public ASIBuilder setAttribute(final I_M_Attribute attribute, final String value)
	{
		attributeValues.put(AttributeId.ofRepoId(attribute.getM_Attribute_ID()), value);
		return this;
	}

	public ASIBuilder setAttribute(final I_M_Attribute attribute, final BigDecimal value)
	{
		attributeValues.put(AttributeId.ofRepoId(attribute.getM_Attribute_ID()), value);
		return this;
	}

	public ASIBuilder setAttribute(final I_M_Attribute attribute, final java.util.Date value)
	{
		attributeValues.put(AttributeId.ofRepoId(attribute.getM_Attribute_ID()), value);
		return this;
	}

	public ASIBuilder setAttribute(final AttributeListValue value)
	{
		attributeValues.put(value.getAttributeId(), value);
		return this;
	}

	public ASIBuilder setAttribute(final I_M_Attribute attribute, final AttributeListValue value)
	{
		attributeValues.put(AttributeId.ofRepoId(attribute.getM_Attribute_ID()), value);
		return this;
	}

	public ASIBuilder skipNullAttributeValues()
	{
		skipNullAttributeValues = true;
		return this;
	}

}
