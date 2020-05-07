package org.adempiere.ad.modelvalidator;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_Order;
import org.junit.Before;
import org.junit.Test;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class InterceptorUtilTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * "Warmup" test, to make sure that {@link InterfaceWrapperHelper#createOld(Object, Class)} actually works in unit tests.
	 *
	 */
	@Test
	public void interfaceWrapperHelper_createOld()
	{
		final I_AD_Table table = newInstance(I_AD_Table.class);
		table.setName("oldname");
		save(table);

		table.setName("name");
		final I_AD_Table oldTable = InterfaceWrapperHelper.createOld(table, I_AD_Table.class);

		assertThat(table.getName()).isEqualTo("name");
		assertThat(oldTable.getName()).isEqualTo("oldname");
	}

	@Test
	public void justDeactivated()
	{
		testJustDeactivated(spec().firstActive(false).thenActive(false).result(false));
		testJustDeactivated(spec().firstActive(false).thenActive(true).result(false));
		testJustDeactivated(spec().firstActive(true).thenActive(false).result(true));
		testJustDeactivated(spec().firstActive(true).thenActive(true).result(false));
	}

	private void testJustDeactivated(Spec.SpecBuilder specBuilder)
	{
		final Spec spec = specBuilder.build();
		final I_C_Order table = setupTestRecord(spec);

		assertThat(InterceptorUtil.isJustDeactivated(table)).isEqualTo(spec.isResult());
	}

	@Test
	public void justActivated()
	{
		testJustActivated(spec().firstActive(false).thenActive(false).result(false));
		testJustActivated(spec().firstActive(false).thenActive(true).result(true));
		testJustActivated(spec().firstActive(true).thenActive(false).result(false));
		testJustActivated(spec().firstActive(true).thenActive(true).result(false));
	}

	private void testJustActivated(Spec.SpecBuilder specBuilder)
	{
		final Spec spec = specBuilder.build();

		final I_C_Order table = setupTestRecord(spec);
		assertThat(InterceptorUtil.isJustActivated(table)).isEqualTo(spec.isResult());
	}

	@Test
	public void justDeactivatedOrUnProcessed()
	{
		testJustDeactivatedOrUnprocessed(spec().firstActive(false).thenActive(false).firstProcessed(false).thenProcessed(false).result(false));
		testJustDeactivatedOrUnprocessed(spec().firstActive(false).thenActive(false).firstProcessed(false).thenProcessed(true).result(false));
		testJustDeactivatedOrUnprocessed(spec().firstActive(false).thenActive(false).firstProcessed(true).thenProcessed(false).result(true));
		testJustDeactivatedOrUnprocessed(spec().firstActive(false).thenActive(false).firstProcessed(true).thenProcessed(true).result(false));

		testJustDeactivatedOrUnprocessed(spec().firstActive(false).thenActive(true).firstProcessed(false).thenProcessed(false).result(false));
		testJustDeactivatedOrUnprocessed(spec().firstActive(false).thenActive(true).firstProcessed(false).thenProcessed(true).result(false));
		testJustDeactivatedOrUnprocessed(spec().firstActive(false).thenActive(true).firstProcessed(true).thenProcessed(false).result(true));
		testJustDeactivatedOrUnprocessed(spec().firstActive(false).thenActive(true).firstProcessed(true).thenProcessed(true).result(false));

		testJustDeactivatedOrUnprocessed(spec().firstActive(true).thenActive(false).firstProcessed(false).thenProcessed(false).result(true));
		testJustDeactivatedOrUnprocessed(spec().firstActive(true).thenActive(false).firstProcessed(false).thenProcessed(true).result(true));
		testJustDeactivatedOrUnprocessed(spec().firstActive(true).thenActive(false).firstProcessed(true).thenProcessed(false).result(true));
		testJustDeactivatedOrUnprocessed(spec().firstActive(true).thenActive(false).firstProcessed(true).thenProcessed(true).result(true));

		testJustDeactivatedOrUnprocessed(spec().firstActive(true).thenActive(true).firstProcessed(false).thenProcessed(false).result(false));
		testJustDeactivatedOrUnprocessed(spec().firstActive(true).thenActive(true).firstProcessed(false).thenProcessed(true).result(false));
		testJustDeactivatedOrUnprocessed(spec().firstActive(true).thenActive(true).firstProcessed(true).thenProcessed(false).result(true));
		testJustDeactivatedOrUnprocessed(spec().firstActive(true).thenActive(true).firstProcessed(true).thenProcessed(true).result(false));
	}


	private void testJustDeactivatedOrUnprocessed(Spec.SpecBuilder specBuilder)
	{
		final Spec spec = specBuilder.build();

		final I_C_Order table = setupTestRecord(spec);
		assertThat(InterceptorUtil.isJustDeactivatedOrUnProcessed(table)).isEqualTo(spec.isResult());
	}

	@Test
	public void justActivatedOrProcessed()
	{
		testJustActivatedOrProcessed(spec().firstActive(false).thenActive(false).firstProcessed(false).thenProcessed(false).result(false));
		testJustActivatedOrProcessed(spec().firstActive(false).thenActive(false).firstProcessed(false).thenProcessed(true).result(true));
		testJustActivatedOrProcessed(spec().firstActive(false).thenActive(false).firstProcessed(true).thenProcessed(false).result(false));
		testJustActivatedOrProcessed(spec().firstActive(false).thenActive(false).firstProcessed(true).thenProcessed(true).result(false));

		testJustActivatedOrProcessed(spec().firstActive(false).thenActive(true).firstProcessed(false).thenProcessed(false).result(true));
		testJustActivatedOrProcessed(spec().firstActive(false).thenActive(true).firstProcessed(false).thenProcessed(true).result(true));
		testJustActivatedOrProcessed(spec().firstActive(false).thenActive(true).firstProcessed(true).thenProcessed(false).result(true));
		testJustActivatedOrProcessed(spec().firstActive(false).thenActive(true).firstProcessed(true).thenProcessed(true).result(true));

		testJustActivatedOrProcessed(spec().firstActive(true).thenActive(false).firstProcessed(false).thenProcessed(false).result(false));
		testJustActivatedOrProcessed(spec().firstActive(true).thenActive(false).firstProcessed(false).thenProcessed(true).result(true));
		testJustActivatedOrProcessed(spec().firstActive(true).thenActive(false).firstProcessed(true).thenProcessed(false).result(false));
		testJustActivatedOrProcessed(spec().firstActive(true).thenActive(false).firstProcessed(true).thenProcessed(true).result(false));

		testJustActivatedOrProcessed(spec().firstActive(true).thenActive(true).firstProcessed(false).thenProcessed(false).result(false));
		testJustActivatedOrProcessed(spec().firstActive(true).thenActive(true).firstProcessed(false).thenProcessed(true).result(true));
		testJustActivatedOrProcessed(spec().firstActive(true).thenActive(true).firstProcessed(true).thenProcessed(false).result(false));
		testJustActivatedOrProcessed(spec().firstActive(true).thenActive(true).firstProcessed(true).thenProcessed(true).result(false));
	}

	private void testJustActivatedOrProcessed(Spec.SpecBuilder specBuilder)
	{
		final Spec spec = specBuilder.build();

		final I_C_Order table = setupTestRecord(spec);
		assertThat(InterceptorUtil.isJustActivatedOrProcessed(table)).isEqualTo(spec.isResult());
	}

	private Spec.SpecBuilder spec()
	{
		return Spec.builder();
	}

	@Builder
	@Value
	private static class Spec
	{
		boolean firstActive;
		boolean thenActive;

		Boolean firstProcessed;
		Boolean thenProcessed;

		boolean result;
	}

	private I_C_Order setupTestRecord(final Spec spec)
	{
		final I_C_Order order = newInstance(I_C_Order.class);

		order.setIsActive(spec.isFirstActive());
		if (spec.getFirstProcessed() != null)
		{
			order.setProcessed(spec.getFirstProcessed());
		}

		save(order);

		order.setIsActive(spec.isThenActive());
		if (spec.getThenProcessed() != null)
		{
			order.setProcessed(spec.getThenProcessed());
		}

		return order;
	}

}
