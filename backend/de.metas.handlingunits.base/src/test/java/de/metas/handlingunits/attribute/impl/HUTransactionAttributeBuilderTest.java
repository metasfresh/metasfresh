package de.metas.handlingunits.attribute.impl;

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


import java.util.Collections;

import org.compiere.model.I_M_Attribute;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

public class HUTransactionAttributeBuilderTest extends AbstractHUTest
{
	@Mocked
	IHUAttributeTransferRequest request;

	@Mocked
	IAttributeStorage attributesFrom;

	@Mocked
	IAttributeStorage attributesTo;

	@Mocked
	IHUAttributeTransferStrategy transferStrategy;

	@Mocked
	I_M_Attribute attribute;

	@Test
	public void testTransferCalled()
	{
		final IHUTransactionAttributeBuilder trxAttributeBuilder = new HUTransactionAttributeBuilder(helper.getHUContext());

		createExpectations(true, true); // targetHasAttribute, transferable

		trxAttributeBuilder.transferAttributes(request);

		createVerifications(1); // noOfTimesTransferCalled
	}

	@Test
	public void testTransferNotCalled()
	{
		final IHUTransactionAttributeBuilder trxAttributeBuilder = new HUTransactionAttributeBuilder(helper.getHUContext());

		createExpectations(true, false); // targetHasAttribute, transferable

		trxAttributeBuilder.transferAttributes(request);

		createVerifications(0); // noOfTimesTransferCalled
	}

	@Test
	public void testTargetDoesNotHaveAttribute()
	{
		final IHUTransactionAttributeBuilder trxAttributeBuilder = new HUTransactionAttributeBuilder(helper.getHUContext());

		createExpectations(false, true); // targetHasAttribute, transferable

		trxAttributeBuilder.transferAttributes(request);

		createVerifications(0); // noOfTimesTransferCalled
	}

	private void createExpectations(final boolean targetHasAttribute, final boolean transferable)
	{
		// @formatter:off
		new Expectations() {{
			request.getAttributesFrom();
			result = attributesFrom;

			request.getAttributesTo();
			result = attributesTo;

			attributesFrom.getAttributes();
			result = Collections.singletonList(attribute);

			attributesFrom.retrieveTransferStrategy(attribute);
			minTimes = 0;
			result = transferStrategy;

			attributesTo.hasAttribute(attribute);
			result = targetHasAttribute;

			transferStrategy.isTransferable(request, attribute);
			minTimes = 0;
			result = transferable;
		}};
		// @formatter:on
	}

	private void createVerifications(final int noOfTimesTransferCalled)
	{
		// @formatter:off
		new Verifications()	
		{{
			transferStrategy.transferAttribute(request, attribute); times = noOfTimesTransferCalled;
		}};
		// @formatter:on
	}

	@Override
	protected void initialize()
	{
		// nothing
	}
}
