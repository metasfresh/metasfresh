package de.metas.handlingunits.expectations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.util.Services;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_M_Attribute;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class HUAttributeExpectations<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	public static final HUAttributeExpectations<Object> newExpectation()
	{
		return new HUAttributeExpectations<>();
	}

	private List<HUAttributeExpectation<HUAttributeExpectations<ParentExpectationType>>> expectations;

	private HUAttributeExpectations()
	{
		super();
	}

	public HUAttributeExpectations(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public HUAttributeExpectations<ParentExpectationType> assertExpected(final String message, final I_M_HU hu)
	{
		final ErrorMessage messageActual = newErrorMessage(message)
				.addContextInfo("hu", hu);

		for (final HUAttributeExpectation<HUAttributeExpectations<ParentExpectationType>> expectation : expectations)
		{
			final I_M_Attribute attribute = expectation.getAttributeNotNull();
			final I_M_HU_Attribute huAttribute = Services.get(IHUAttributesDAO.class).retrieveAttribute(hu, attribute);
			expectation.assertExpected(messageActual.expect("attribute is matching"), huAttribute);
		}

		return this;
	}

	public HUAttributeExpectation<HUAttributeExpectations<ParentExpectationType>> newAttributeExpectation()
	{
		final HUAttributeExpectation<HUAttributeExpectations<ParentExpectationType>> expectation = new HUAttributeExpectation<>(this);
		if (expectations == null)
		{
			expectations = new ArrayList<>();
		}
		expectations.add(expectation);
		return expectation;
	}

	public List<I_M_HU_Attribute> createHUAttributes(final I_M_HU hu)
	{
		if (expectations == null || expectations.isEmpty())
		{
			return ImmutableList.of();
		}

		return expectations.stream()
				.map((expectation) -> expectation.createHUAttribute(hu))
				.collect(Collectors.toList());
	}

}
