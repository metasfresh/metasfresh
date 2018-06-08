package de.metas.material.event.pporder;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.event.EventTestHelper;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor.ChangedPPOrderLineDescriptorBuilder;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PPOrderChangedEventTest
{
	private static final BigDecimal FIVE = new BigDecimal("5");
	private ChangedPPOrderLineDescriptorBuilder changedPPOrderLineDescriptorBuilder;

	@Before
	public void init()
	{
		changedPPOrderLineDescriptorBuilder = PPOrderChangedEvent.ChangedPPOrderLineDescriptor.builder()
				.productDescriptor(EventTestHelper.createProductDescriptor())
				.issueOrReceiveDate(SystemTime.asDate());
	}

	/**
	 * This tests a ppOrder line where
	 * <li>it was planned to receive(!) 5 of a byproduct (line receipt-quantities are negative!)
	 * <li>then the ppOrder is closed without any actual receipt.
	 * <p>
	 * Expectation: the delta needs to be plus 5
	 */
	@Test
	public void changedPPOrderLineDescriptor_getOpenQtyDelta_receipt()
	{
		final ChangedPPOrderLineDescriptor d = changedPPOrderLineDescriptorBuilder
				.oldQtyRequired(FIVE.negate())
				.newQtyRequired(FIVE.negate())
				.oldQtyDelivered(ZERO)
				.newQtyDelivered(FIVE.negate())
				.build();

		assertThat(d.computeOpenQtyDelta()).isEqualTo(FIVE);
	}

	/**
	 * This tests a ppOrder line where
	 * <li>it was planned to issue 5 of a component (line issue-quantities are positive)
	 * <li>then the ppOrder is closed without any actual issue.
	 * <p>
	 * Expectation: the delta needs to be minus 5
	 */
	@Test
	public void changedPPOrderLineDescriptor_getOpenQtyDelta_issue()
	{
		final ChangedPPOrderLineDescriptor d = changedPPOrderLineDescriptorBuilder
				.oldQtyRequired(FIVE)
				.newQtyRequired(FIVE)
				.oldQtyDelivered(ZERO)
				.newQtyDelivered(FIVE)
				.build();

		assertThat(d.computeOpenQtyDelta()).isEqualTo(FIVE.negate());
	}

	//oldQtyRequired=-54.000, newQtyRequired=-54.000, oldQtyDelivered=0, newQtyDelivered=0
	@Test
	public void changedPPOrderLineDescriptor_getOpenQtyDelta_noChange()
	{
		final ChangedPPOrderLineDescriptor d = changedPPOrderLineDescriptorBuilder
				.oldQtyRequired(FIVE)
				.newQtyRequired(FIVE)
				.oldQtyDelivered(ZERO)
				.newQtyDelivered(ZERO)
				.build();

		assertThat(d.computeOpenQtyDelta()).isEqualTo(ZERO);
	}
}