package de.metas.fresh.mrp_productinfo.impl;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.IParamsBL;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Transaction;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelector;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelectorFactory;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;

/*
 * #%L
 * de.metas.fresh.base
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

public class MRPProductInfoSelectorFactoryTests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Tests if {@link MRPProductInfoSelectorFactory} correctly creates an {@link IMRPProductInfoSelector} from a {@link I_PMM_PurchaseCandidate}.
	 *
	 * @task https://metasfresh.atlassian.net/browse/FRESH-86
	 */
	@Test
	public void testPurchaseCandidate()
	{
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		InterfaceWrapperHelper.save(asi);

		final I_PMM_PurchaseCandidate pc = InterfaceWrapperHelper.newInstance(I_PMM_PurchaseCandidate.class);
		pc.setDatePromised(SystemTime.asDayTimestamp());
		pc.setM_Product_ID(23);
		pc.setM_AttributeSetInstance(asi);
		InterfaceWrapperHelper.save(pc);

		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = new MRPProductInfoSelectorFactory();
		final IMRPProductInfoSelector selector = mrpProductInfoSelectorFactory.createOrNullForModel(pc);

		assertNotNull(selector);
		assertThat(selector.getParamPrefix(), startsWith(I_PMM_PurchaseCandidate.Table_Name));
		assertThat(selector.getDate(), is(pc.getDatePromised()));
		assertThat(selector.getM_Product_ID(), is(pc.getM_Product_ID()));
		assertThat(selector.getM_AttributeSetInstance_ID(), is(pc.getM_AttributeSetInstance_ID()));
	}

	/**
	 * Tests if {@link MRPProductInfoSelectorFactory} correctly creates an {@link IMRPProductInfoSelector} from a {@link I_M_Transaction}.
	 */
	@Test
	public void testTransaction()
	{
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		InterfaceWrapperHelper.save(asi);

		final I_M_Transaction transaction = InterfaceWrapperHelper.newInstance(I_M_Transaction.class);
		transaction.setMovementDate(SystemTime.asDayTimestamp());
		transaction.setM_Product_ID(23);
		transaction.setM_AttributeSetInstance(asi);
		InterfaceWrapperHelper.save(transaction);

		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = new MRPProductInfoSelectorFactory();
		final IMRPProductInfoSelector selector = mrpProductInfoSelectorFactory.createOrNullForModel(transaction);

		assertNotNull(selector);
		assertThat(selector.getParamPrefix(), startsWith(I_M_Transaction.Table_Name));
		assertThat(selector.getDate(), is(transaction.getMovementDate()));
		assertThat(selector.getM_Product_ID(), is(transaction.getM_Product_ID()));
		assertThat(selector.getM_AttributeSetInstance_ID(), is(transaction.getM_AttributeSetInstance_ID()));
	}
	
	/**
	 * Verifies that the factory can create a single selector just using parameters.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/761
	 */
	@Test
	public void testCreateSingleForParamsEmptyPrefix()
	{
		testCreateSingleForParams("");
	}

	/**
	 * Similar to {@link #testCreateSingleForParamsEmptyPrefix()}.
	 */
	@Test
	public void testCreateSingleForParamsSomePrefix()
	{
		final String somePrefix = "someprefix";
		testCreateSingleForParams(somePrefix);
	}

	private void testCreateSingleForParams(String prefix)
	{
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		InterfaceWrapperHelper.save(asi);

		final Timestamp date = SystemTime.asDayTimestamp();
		final IParams params = Services.get(IParamsBL.class).createParams(ImmutableMap.of(
				prefix + MRPProductInfoSelectorFactory.DATE_PARAM_SUFFIX, date,
				prefix + MRPProductInfoSelectorFactory.PRODUCT_PARAM_SUFFIX, 23,
				prefix + MRPProductInfoSelectorFactory.ASI_PARAM_SUFFIX, asi.getM_AttributeSetInstance_ID()));

		final MRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = new MRPProductInfoSelectorFactory();
		final IMRPProductInfoSelector selector = mrpProductInfoSelectorFactory.createSingleForParams(params, prefix);

		assertNotNull(selector);
		assertThat(selector.getParamPrefix(), is(prefix));
		assertThat(selector.getDate(), is(date));
		assertThat(selector.getM_Product_ID(), is(23));
		assertThat(selector.getM_AttributeSetInstance_ID(), is(asi.getM_AttributeSetInstance_ID()));
	}

	@Test
	public void testGetPrefix()
	{
		final MRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = new MRPProductInfoSelectorFactory();

		assertThat(mrpProductInfoSelectorFactory.getPrefix("blah2" + MRPProductInfoSelectorFactory.PRODUCT_PARAM_SUFFIX), is("blah2"));
		assertThat(mrpProductInfoSelectorFactory.getPrefix("blah2" + MRPProductInfoSelectorFactory.ASI_PARAM_SUFFIX), is("blah2"));
		assertThat(mrpProductInfoSelectorFactory.getPrefix("blah2" + MRPProductInfoSelectorFactory.DATE_PARAM_SUFFIX), is("blah2"));
		
		assertThat(mrpProductInfoSelectorFactory.getPrefix(MRPProductInfoSelectorFactory.PRODUCT_PARAM_SUFFIX), is(""));
		assertThat(mrpProductInfoSelectorFactory.getPrefix(MRPProductInfoSelectorFactory.ASI_PARAM_SUFFIX), is(""));
		assertThat(mrpProductInfoSelectorFactory.getPrefix(MRPProductInfoSelectorFactory.DATE_PARAM_SUFFIX), is(""));
	}

	/**
	 * Verifies that the factory can create an ordered list of multiple {@link IMRPProductInfoSelector} from an {@link IParams} instance.
	 */
	@Test
	public void testCreateMultipleSelectors()
	{
		final I_M_AttributeSetInstance asi1 = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		InterfaceWrapperHelper.save(asi1);

		final I_M_AttributeSetInstance asi2 = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		InterfaceWrapperHelper.save(asi2);

		final Timestamp date = SystemTime.asDayTimestamp();
		final IParams params = Services.get(IParamsBL.class).createParams(ImmutableMap.<String, Object> builder()
				.put("blah2" + MRPProductInfoSelectorFactory.DATE_PARAM_SUFFIX, date)
				.put("blah2" + MRPProductInfoSelectorFactory.PRODUCT_PARAM_SUFFIX, 23)
				.put("blah2" + MRPProductInfoSelectorFactory.ASI_PARAM_SUFFIX, asi1.getM_AttributeSetInstance_ID())

				.put("blah1" + MRPProductInfoSelectorFactory.DATE_PARAM_SUFFIX, date)
				.put("blah1" + MRPProductInfoSelectorFactory.PRODUCT_PARAM_SUFFIX, 24)
				.put("blah1" + MRPProductInfoSelectorFactory.ASI_PARAM_SUFFIX, asi2.getM_AttributeSetInstance_ID())
				.build());

		final MRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = new MRPProductInfoSelectorFactory();
		final List<IMRPProductInfoSelector> selectors = mrpProductInfoSelectorFactory.createForParams(params);

		assertThat(selectors.size(), is(2));

		// note that we want them to be sorted by prefix
		assertThat(selectors.get(1).getParamPrefix(), is("blah2"));
		assertThat(selectors.get(1).getDate(), is(date));
		assertThat(selectors.get(1).getM_Product_ID(), is(23));
		assertThat(selectors.get(1).getM_AttributeSetInstance_ID(), is(asi1.getM_AttributeSetInstance_ID()));

		assertThat(selectors.get(0).getParamPrefix(), is("blah1"));
		assertThat(selectors.get(0).getDate(), is(date));
		assertThat(selectors.get(0).getM_Product_ID(), is(24));
		assertThat(selectors.get(0).getM_AttributeSetInstance_ID(), is(asi2.getM_AttributeSetInstance_ID()));
	}
}
