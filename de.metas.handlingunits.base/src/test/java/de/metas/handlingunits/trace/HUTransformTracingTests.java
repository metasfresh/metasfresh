package de.metas.handlingunits.trace;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.allocation.transfer.HUTransformServiceTests;
import de.metas.handlingunits.model.I_M_HU_Trace;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HUTransformTracingTests
{
	private HUTransformServiceTests huTransformServiceTests;
	private HUTraceRepository huTraceRepository;

	@Before
	public void init()
	{
		huTraceRepository = new HUTraceRepository();

		huTransformServiceTests = new HUTransformServiceTests();
		huTransformServiceTests.init(); // this init invocation inits and resets everything

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);
		modelInterceptorRegistry.addModelInterceptor(new de.metas.handlingunits.trace.interceptor.M_HU_Trx_Hdr(
				new HUTraceEventsCreateAndAdd(huTraceRepository)));
	}

	/**
	 * Call
	 * {@link HUTransformServiceTests#test_CUToExistingTU_create_mixed_TU_completeCU()}
	 * and then verifies the tracing info
	 */
	@Test
	public void test_CUToExistingTU_create_mixed_TU_completeCU()
	{
		huTransformServiceTests.test_CUToExistingTU_create_mixed_TU_completeCU();

		List<I_M_HU_Trace> allTraceRecords = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
				.create().list();
		assertThat(allTraceRecords.isEmpty(), is(false));
	}
}
