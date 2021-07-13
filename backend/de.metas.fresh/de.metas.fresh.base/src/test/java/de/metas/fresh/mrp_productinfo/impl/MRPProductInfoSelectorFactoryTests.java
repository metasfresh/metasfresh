package de.metas.fresh.mrp_productinfo.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.Params;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Transaction;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	@BeforeEach
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
		pc.setDatePromised(de.metas.common.util.time.SystemTime.asDayTimestamp());
		pc.setM_Product_ID(23);
		pc.setM_AttributeSetInstance(asi);
		InterfaceWrapperHelper.save(pc);

		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = new MRPProductInfoSelectorFactory();
		final IMRPProductInfoSelector selector = mrpProductInfoSelectorFactory.createOrNullForModel(pc);

		assertThat(selector).isNotNull();
		assertThat(selector.getParamPrefix()).startsWith(I_PMM_PurchaseCandidate.Table_Name);
		assertThat(selector.getDate()).isEqualTo(TimeUtil.asZonedDateTime(pc.getDatePromised()));
		assertThat(selector.getM_Product_ID()).isEqualTo(pc.getM_Product_ID());
		assertThat(selector.getM_AttributeSetInstance_ID()).isEqualTo(pc.getM_AttributeSetInstance_ID());
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

		assertThat(selector).isNotNull();
		assertThat(selector.getParamPrefix()).startsWith(I_M_Transaction.Table_Name);
		assertThat(selector.getDate()).isEqualTo(TimeUtil.asZonedDateTime(transaction.getMovementDate()));
		assertThat(selector.getM_Product_ID()).isEqualTo(transaction.getM_Product_ID());
		assertThat(selector.getM_AttributeSetInstance_ID()).isEqualTo(transaction.getM_AttributeSetInstance_ID());
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

		final ZonedDateTime date = de.metas.common.util.time.SystemTime.asZonedDateTime();
		final IParams params = Params.ofMap(ImmutableMap.of(
				prefix + MRPProductInfoSelectorFactory.DATE_PARAM_SUFFIX, TimeUtil.asTimestamp(date),
				prefix + MRPProductInfoSelectorFactory.PRODUCT_PARAM_SUFFIX, 23,
				prefix + MRPProductInfoSelectorFactory.ASI_PARAM_SUFFIX, asi.getM_AttributeSetInstance_ID()));

		final MRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = new MRPProductInfoSelectorFactory();
		final IMRPProductInfoSelector selector = mrpProductInfoSelectorFactory.createSingleForParams(params, prefix);

		assertThat(selector).isNotNull();
		assertThat(selector.getParamPrefix()).isEqualTo(prefix);
		assertThat(selector.getDate()).isEqualTo(date);
		assertThat(selector.getM_Product_ID()).isEqualTo(23);
		assertThat(selector.getM_AttributeSetInstance_ID()).isEqualTo(asi.getM_AttributeSetInstance_ID());
	}

	@Test
	public void testGetPrefix()
	{
		final MRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = new MRPProductInfoSelectorFactory();

		assertThat(mrpProductInfoSelectorFactory.getPrefix("blah2" + MRPProductInfoSelectorFactory.PRODUCT_PARAM_SUFFIX)).isEqualTo("blah2");
		assertThat(mrpProductInfoSelectorFactory.getPrefix("blah2" + MRPProductInfoSelectorFactory.ASI_PARAM_SUFFIX)).isEqualTo("blah2");
		assertThat(mrpProductInfoSelectorFactory.getPrefix("blah2" + MRPProductInfoSelectorFactory.DATE_PARAM_SUFFIX)).isEqualTo("blah2");

		assertThat(mrpProductInfoSelectorFactory.getPrefix(MRPProductInfoSelectorFactory.PRODUCT_PARAM_SUFFIX)).isEqualTo("");
		assertThat(mrpProductInfoSelectorFactory.getPrefix(MRPProductInfoSelectorFactory.ASI_PARAM_SUFFIX)).isEqualTo("");
		assertThat(mrpProductInfoSelectorFactory.getPrefix(MRPProductInfoSelectorFactory.DATE_PARAM_SUFFIX)).isEqualTo("");
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

		final ZonedDateTime date = de.metas.common.util.time.SystemTime.asZonedDateTime();
		final IParams params = Params.ofMap(ImmutableMap.<String, Object> builder()
				.put("blah2" + MRPProductInfoSelectorFactory.DATE_PARAM_SUFFIX, TimeUtil.asTimestamp(date))
				.put("blah2" + MRPProductInfoSelectorFactory.PRODUCT_PARAM_SUFFIX, 23)
				.put("blah2" + MRPProductInfoSelectorFactory.ASI_PARAM_SUFFIX, asi1.getM_AttributeSetInstance_ID())

				.put("blah1" + MRPProductInfoSelectorFactory.DATE_PARAM_SUFFIX, TimeUtil.asTimestamp(date))
				.put("blah1" + MRPProductInfoSelectorFactory.PRODUCT_PARAM_SUFFIX, 24)
				.put("blah1" + MRPProductInfoSelectorFactory.ASI_PARAM_SUFFIX, asi2.getM_AttributeSetInstance_ID())
				.build());

		final MRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = new MRPProductInfoSelectorFactory();
		final List<IMRPProductInfoSelector> selectors = mrpProductInfoSelectorFactory.createForParams(params);

		assertThat(selectors).hasSize(2);

		// note that we want them to be sorted by prefix
		assertThat(selectors.get(1).getParamPrefix()).isEqualTo("blah2");
		assertThat(selectors.get(1).getDate()).isEqualTo(date);
		assertThat(selectors.get(1).getM_Product_ID()).isEqualTo(23);
		assertThat(selectors.get(1).getM_AttributeSetInstance_ID()).isEqualTo(asi1.getM_AttributeSetInstance_ID());

		assertThat(selectors.get(0).getParamPrefix()).isEqualTo("blah1");
		assertThat(selectors.get(0).getDate()).isEqualTo(date);
		assertThat(selectors.get(0).getM_Product_ID()).isEqualTo(24);
		assertThat(selectors.get(0).getM_AttributeSetInstance_ID()).isEqualTo(asi2.getM_AttributeSetInstance_ID());
	}
}
