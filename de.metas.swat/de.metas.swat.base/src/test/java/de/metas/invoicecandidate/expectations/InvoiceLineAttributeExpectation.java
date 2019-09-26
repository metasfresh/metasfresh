package de.metas.invoicecandidate.expectations;

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


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.util.Check;

/**
 * {@link IInvoiceLineAttribute} expectation.
 * 
 * @author tsa
 *
 * @param <ParentExpectationType>
 */
public class InvoiceLineAttributeExpectation<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{
	public static InvoiceLineAttributeExpectation<Object> newExpectation()
	{
		return new InvoiceLineAttributeExpectation<>();
	}

	private I_M_Attribute attribute;
	private String valueString;
	private boolean valueStringSet;
	private BigDecimal valueNumber;
	private boolean valueNumberSet;

	public InvoiceLineAttributeExpectation(ParentExpectationType parent)
	{
		super(parent);
	}

	private InvoiceLineAttributeExpectation()
	{
		super();
	}

	public InvoiceLineAttributeExpectation<ParentExpectationType> assertExpected(final String message, final IInvoiceLineAttribute invoiceLineAttribute)
	{
		return assertExpected(newErrorMessage(message), invoiceLineAttribute);
	}

	public InvoiceLineAttributeExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final IInvoiceLineAttribute invoiceLineAttribute)
	{
		ErrorMessage messageToUse = derive(message)
				.addContextInfo(invoiceLineAttribute);

		assertNotNull(messageToUse.expect("invoiceLineAttribute is null"), invoiceLineAttribute);

		final I_M_AttributeInstance attributeInstanceTemplate = invoiceLineAttribute.getAttributeInstanceTemplate();
		final int attributeActualId = attributeInstanceTemplate.getM_Attribute_ID();
		final String valueStringActual = attributeInstanceTemplate.getValue();
		final BigDecimal valueNumberActual = attributeInstanceTemplate.getValueNumber();

		//
		// Attribute
		final I_M_Attribute attributeExpected = this.attribute;
		assertEquals(messageToUse.expect("attribute"), attributeExpected.getM_Attribute_ID(), attributeActualId);

		//
		// Validates values
		if (valueStringSet)
		{
			assertEquals(messageToUse.expect("ValueString"), valueString, valueStringActual);
		}
		if (valueNumberSet)
		{
			assertEquals(messageToUse.expect("ValueNumber"), valueNumber, valueNumberActual);
		}

		return this;
	}

	public final I_M_AttributeInstance createM_AttributeInstance(final I_M_AttributeSetInstance asi)
	{
		Check.assumeNotNull(asi, "asi not null");
		Check.assumeNotNull(attribute, "attribute not null");
		Check.assume(valueStringSet || valueNumberSet, "value String/Number shall be set on {}", this);

		final I_M_AttributeInstance ai = InterfaceWrapperHelper.newInstance(I_M_AttributeInstance.class, asi);
		ai.setM_AttributeSetInstance(asi);
		ai.setM_Attribute_ID(attribute.getM_Attribute_ID());
		ai.setValue(valueString);
		ai.setValueNumber(valueNumber);
		InterfaceWrapperHelper.save(ai);
		return ai;
	}

	public InvoiceLineAttributeExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute)
	{
		this.attribute = attribute;
		return this;
	}

	public InvoiceLineAttributeExpectation<ParentExpectationType> valueString(final String valueString)
	{
		this.valueString = valueString;
		this.valueStringSet = true;
		return this;
	}

	public InvoiceLineAttributeExpectation<ParentExpectationType> valueNumber(final BigDecimal valueNumber)
	{
		this.valueNumber = valueNumber;
		this.valueNumberSet = true;
		return this;
	}

	public BigDecimal getValueNumber()
	{
		return this.valueNumber;
	}

	public InvoiceLineAttributeExpectation<ParentExpectationType> valueNumber(final String valueNumberStr)
	{
		return valueNumber(new BigDecimal(valueNumberStr));
	}

}
