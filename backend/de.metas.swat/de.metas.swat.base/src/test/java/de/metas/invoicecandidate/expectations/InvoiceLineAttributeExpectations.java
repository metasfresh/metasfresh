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

import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.util.Check;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_M_AttributeSetInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * Expectations for a set of {@link IInvoiceLineAttribute}s.
 * 
 * @author tsa
 *
 * @param <ParentExpectationType>
 */
public class InvoiceLineAttributeExpectations<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{
	public static InvoiceLineAttributeExpectations<Object> newExpectation()
	{
		return new InvoiceLineAttributeExpectations<>();
	}

	private final List<InvoiceLineAttributeExpectation<InvoiceLineAttributeExpectations<ParentExpectationType>>> attributeExpectations = new ArrayList<>();

	public InvoiceLineAttributeExpectations(final ParentExpectationType parent)
	{
		super(parent);
	}

	private InvoiceLineAttributeExpectations()
	{
	}

	public InvoiceLineAttributeExpectations<ParentExpectationType> assertExpected(final String message, final List<IInvoiceLineAttribute> invoiceLineAttributes)
	{
		return assertExpected(newErrorMessage(message), invoiceLineAttributes);
	}

	public InvoiceLineAttributeExpectations<ParentExpectationType> assertExpected(final ErrorMessage message, final List<IInvoiceLineAttribute> invoiceLineAttributes)
	{
		final int attributesCount = attributeExpectations.size();
		assertEquals(message.expect("attributes count"), attributesCount, invoiceLineAttributes.size());

		final List<IInvoiceLineAttribute> invoiceLineAttributesList = new ArrayList<>(invoiceLineAttributes);

		for (int i = 0; i < attributesCount; i++)
		{
			final ErrorMessage messageToUse = derive(message)
					.addContextInfo("Attribute index", i)
					.addContextInfo("Attributes count", attributesCount);

			final IInvoiceLineAttribute invoiceLineAttribute = invoiceLineAttributesList.get(i);
			attributeExpectations.get(i)
					.assertExpected(messageToUse, invoiceLineAttribute);
		}

		return this;
	}
	
	public I_M_AttributeSetInstance createM_AttributeSetInstance(final Object contextProvider)
	{
		Check.assumeNotNull(contextProvider, "contextProvider not null");
		Check.assume(!attributeExpectations.isEmpty(), "At least one attribute expectation shall be defined for {}", this);
		
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class, contextProvider);
		InterfaceWrapperHelper.save(asi);
		
		
		for (final InvoiceLineAttributeExpectation<InvoiceLineAttributeExpectations<ParentExpectationType>> attributeExpectation : attributeExpectations)
		{
			attributeExpectation.createM_AttributeInstance(asi);
		}

		return asi;
	}

	public InvoiceLineAttributeExpectation<InvoiceLineAttributeExpectations<ParentExpectationType>> newAttributeExpectation()
	{
		final InvoiceLineAttributeExpectation<InvoiceLineAttributeExpectations<ParentExpectationType>> attributeExpectation = new InvoiceLineAttributeExpectation<>(this);
		attributeExpectations.add(attributeExpectation);
		return attributeExpectation;
	}

}
