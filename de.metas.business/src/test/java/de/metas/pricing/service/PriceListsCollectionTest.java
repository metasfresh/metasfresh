package de.metas.pricing.service;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_PriceList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class PriceListsCollectionTest
{
	@Rule
	public AdempiereTestWatcher watcher = new AdempiereTestWatcher();

	private static final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(1);
	private static final CountryId countryId1 = CountryId.ofRepoId(1);
	private static final CountryId countryId2 = CountryId.ofRepoId(2);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test_getPriceListId_OneCountry_SalesAndPurchase()
	{
		final I_M_PriceList priceList1 = createPriceList(countryId1, SOTrx.SALES);
		final I_M_PriceList priceList2 = createPriceList(countryId1, SOTrx.PURCHASE);
		final PriceListsCollection priceLists = createPriceListsCollection(priceList1, priceList2);

		{
			Optional<PriceListId> result = priceLists.getPriceListId(countryId1, SOTrx.SALES);
			assertThat(result).isPresent();
			assertThat(result.get().getRepoId()).isEqualTo(priceList1.getM_PriceList_ID());
		}

		{
			Optional<PriceListId> result = priceLists.getPriceListId(countryId1, SOTrx.PURCHASE);
			assertThat(result).isPresent();
			assertThat(result.get().getRepoId()).isEqualTo(priceList2.getM_PriceList_ID());
		}
	}

	@Test
	public void test_getPriceListId_FallbackToPriceListWithoutCountry()
	{
		final I_M_PriceList priceList1 = createPriceList(null, SOTrx.SALES);
		final I_M_PriceList priceList2 = createPriceList(countryId1, SOTrx.SALES);
		final PriceListsCollection priceLists = createPriceListsCollection(priceList1, priceList2);

		{
			Optional<PriceListId> result = priceLists.getPriceListId(countryId1, SOTrx.SALES);
			assertThat(result).isPresent();
			assertThat(result.get().getRepoId()).isEqualTo(priceList2.getM_PriceList_ID());
		}

		{
			Optional<PriceListId> result = priceLists.getPriceListId(countryId2, SOTrx.SALES);
			assertThat(result).isPresent();
			assertThat(result.get().getRepoId()).isEqualTo(priceList1.getM_PriceList_ID());
		}
	}

	private I_M_PriceList createPriceList(final CountryId countryId, final SOTrx soTrx)
	{
		final I_M_PriceList record = newInstance(I_M_PriceList.class);
		record.setM_PricingSystem_ID(pricingSystemId.getRepoId());
		record.setC_Country_ID(CountryId.toRepoId(countryId));
		record.setIsSOPriceList(soTrx.toBoolean());
		saveRecord(record);
		return record;
	}

	private PriceListsCollection createPriceListsCollection(final I_M_PriceList... priceLists)
	{
		return new PriceListsCollection(pricingSystemId, ImmutableList.copyOf(priceLists));
	}
}
