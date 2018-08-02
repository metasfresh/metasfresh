package de.metas.handlingunits.inout.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.eevolution.model.I_DD_Order;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.inout.impl.DistributeAndMoveReceiptCreator.Result;
import lombok.Singular;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class DistributeAndMoveReceiptCreator_Result_Test
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Make sure that if we invoke {@link Result.ResultBuilder#ddOrders(java.util.Collection)} multiple times, the the respective collections are aggregated.
	 * (This actually tests lombok {@link Singular}).
	 */
	@Test
	public void ddOrders_multiple_invocations()
	{
		final I_DD_Order ddOrder1 = newInstance(I_DD_Order.class);

		final I_DD_Order ddOrder2 = newInstance(I_DD_Order.class);

		final Result result = Result.builder()
				.ddOrders(ImmutableList.of(ddOrder1))
				.ddOrders(ImmutableList.of(ddOrder2))
				.build();

		assertThat(result.getDdOrders()).containsOnly(ddOrder1, ddOrder2);
	}

}
