package org.adempiere.mm.attributes.api.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.Services;
import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Env;
import org.junit.Assert;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * {@link I_M_AttributeInstance} expectation.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AttributeInstanceExpectation<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{

	private I_M_Attribute attribute;
	/** i.e. M_Attribute.Value */
	private String attributeKey;
	private String valueString;
	private boolean valueStringSet;
	private BigDecimal valueNumber;
	private boolean valueNumberSet;
	private Date valueDate;
	private boolean valueDateSet;

	public AttributeInstanceExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public AttributeInstanceExpectation()
	{
		this(null);
	}

	public AttributeInstanceExpectation<ParentExpectationType> assertExpected(final I_M_AttributeSetInstance asi)
	{
		return assertExpected(newErrorMessage(), asi);
	}

	public AttributeInstanceExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final I_M_AttributeSetInstance asi)
	{
		final int attributeId = getAttributeNotNull().getM_Attribute_ID();
		final I_M_AttributeInstance attributeInstance = Services.get(IAttributeDAO.class).retrieveAttributeInstance(asi, attributeId);
		return assertExpected(message, attributeInstance);
	}

	public AttributeInstanceExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final I_M_AttributeInstance attributeInstance)
	{
		final ErrorMessage messageToUse = derive(message)
				.addContextInfo("Attribute Instance", attributeInstance);

		assertNotNull(messageToUse.expect("attributeInstance not null"), attributeInstance);

		if (attribute != null)
		{
			assertModelEquals(messageToUse.expect("M_Attribute_ID"), attribute, attributeInstance.getM_Attribute());
		}
		if (attributeKey != null)
		{
			final I_M_Attribute attributeActual = attributeInstance.getM_Attribute();
			assertNotNull(messageToUse.expect("M_Attribute_ID not null"), attributeActual);
			assertEquals(messageToUse.expect("M_Attribute.Value"), attributeKey, attributeActual.getValue());
		}
		if (valueStringSet)
		{
			assertEquals(messageToUse.expect("ValueString"), valueString, attributeInstance.getValue());
		}
		if (valueNumberSet)
		{
			assertEquals(messageToUse.expect("ValueNumber"), valueNumber, attributeInstance.getValueNumber());
		}
		if (valueDateSet)
		{
			assertEquals(messageToUse.expect("ValueDate"), valueDate, attributeInstance.getValueDate());
		}

		return this;
	}

	public AttributeInstanceExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute)
	{
		this.attribute = attribute;
		return this;
	}

	public AttributeInstanceExpectation<ParentExpectationType> attribute(final String attributeValueKey)
	{
		this.attributeKey = attributeValueKey;
		return this;
	}

	public AttributeInstanceExpectation<ParentExpectationType> valueString(final String valueString)
	{
		this.valueString = valueString;
		this.valueStringSet = true;
		return this;
	}

	public AttributeInstanceExpectation<ParentExpectationType> valueNumber(final BigDecimal valueNumber)
	{
		this.valueNumber = valueNumber;
		this.valueNumberSet = true;
		return this;
	}

	public BigDecimal getValueNumber()
	{
		return this.valueNumber;
	}

	public AttributeInstanceExpectation<ParentExpectationType> valueNumber(final String valueNumberStr)
	{
		return valueNumber(new BigDecimal(valueNumberStr));
	}

	public AttributeInstanceExpectation<ParentExpectationType> valueDate(final Date valueDate)
	{
		this.valueDate = valueDate;
		this.valueDateSet = true;
		return this;
	}

	public final I_M_Attribute getAttributeNotNull()
	{
		final ErrorMessage messageIfNotFound = newErrorMessage("")
				.expect("no cost price attribute was configured on expectation " + this);
		return getAttributeNotNull(messageIfNotFound);
	}

	private final I_M_Attribute getAttributeNotNull(final ErrorMessage messageIfNotFound)
	{
		if (attribute != null)
		{
			return attribute;
		}

		if (attributeKey != null)
		{
			return Services.get(IAttributeDAO.class).retrieveAttributeByValue(Env.getCtx(), attributeKey, I_M_Attribute.class);
		}

		// Fail
		Assert.fail(messageIfNotFound.toString());
		return null; // shall not reach this point
	}

}
