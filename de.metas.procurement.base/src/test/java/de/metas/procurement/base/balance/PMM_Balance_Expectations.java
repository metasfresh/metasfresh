package de.metas.procurement.base.balance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.junit.Assert;

import com.google.common.base.Function;

import de.metas.procurement.base.model.I_PMM_Balance;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PMM_Balance_Expectations
{
	public static final PMM_Balance_Expectations newExpectations()
	{
		return new PMM_Balance_Expectations();
	}

	private final List<PMM_Balance_Expectation> expectations = new ArrayList<>();
	private boolean strictMatching = true;

	public void assertExpected()
	{
		final Map<ArrayKey, I_PMM_Balance> balanceRecordsOrig = retrievePMM_Balances();
		final Map<ArrayKey, I_PMM_Balance> balanceRecords = new HashMap<>(balanceRecordsOrig);

		final Map<ArrayKey, PMM_Balance_Expectation> expectationsBySegmentKey = getExpectationsIndexedBySegmentKey();
		for (final Map.Entry<ArrayKey, PMM_Balance_Expectation> e : expectationsBySegmentKey.entrySet())
		{
			final ArrayKey key = e.getKey();
			final PMM_Balance_Expectation expectation = e.getValue();

			final I_PMM_Balance balanceRecord = balanceRecords.remove(key);

			final String msg = "SegmentKey=" + key;

			try
			{
				expectation.assertExpected(msg, balanceRecord);
			}
			catch (AssertionError ex)
			{
				dumpBalanceRecords(balanceRecordsOrig);
				dumpExpectations(expectationsBySegmentKey);
				throw ex;
			}
		}

		if (strictMatching)
		{
			Assert.assertEquals("No other balance entries were expected", Collections.emptyMap(), balanceRecords);
		}
	}

	private void dumpBalanceRecords(Map<ArrayKey, I_PMM_Balance> balanceRecords)
	{
		System.out.println("Dump balance records: ");
		for (final Map.Entry<ArrayKey, I_PMM_Balance> e : balanceRecords.entrySet())
		{
			System.out.println("" + e.getKey() + ": " + e.getValue());
		}
	}

	private void dumpExpectations(Map<ArrayKey, PMM_Balance_Expectation> expectationsBySegmentKey)
	{
		System.out.println("Dump expectations: ");
		for (final Entry<ArrayKey, PMM_Balance_Expectation> e : expectationsBySegmentKey.entrySet())
		{
			System.out.println("" + e.getKey() + ": " + e.getValue());
		}
	}

	public PMM_Balance_Expectations setStrictMatching(boolean strictMatching)
	{
		this.strictMatching = strictMatching;
		return this;
	}

	public PMM_Balance_Expectation newExpectation()
	{
		final PMM_Balance_Expectation expectation = new PMM_Balance_Expectation(this);
		expectations.add(expectation);
		return expectation;
	}

	private Map<ArrayKey, PMM_Balance_Expectation> getExpectationsIndexedBySegmentKey()
	{
		final Map<ArrayKey, PMM_Balance_Expectation> map = new HashMap<>();

		for (final PMM_Balance_Expectation expectation : expectations)
		{
			final ArrayKey key = createSegmentKey(expectation);
			map.put(key, expectation);
		}

		return map;
	}

	private ArrayKey createSegmentKey(final I_PMM_Balance balanceRecord)
	{
		return Util.mkKey(
				"BP=" + balanceRecord.getC_BPartner_ID() //
				, "P=" + balanceRecord.getM_Product_ID() //
		// , "PIIP=" + balanceRecord.getM_HU_PI_Item_Product_ID() //
		, "ASI=" + balanceRecord.getM_AttributeSetInstance_ID() //
		, "C_Flatrate_DataEntry_ID=" + normalizeId(balanceRecord.getC_Flatrate_DataEntry_ID()) //
		, "Month=" + normalizeDate(balanceRecord.getMonthDate())  //
		, "Week=" + normalizeDate(balanceRecord.getWeekDate()) //
		);
	}

	private ArrayKey createSegmentKey(final PMM_Balance_Expectation expectation)
	{
		return Util.mkKey(
				"BP=" + expectation.C_BPartner_ID //
				, "P=" + expectation.M_Product_ID //
		// , "PIIP=" + expectation.M_HU_PI_Item_Product_ID //
		, "ASI=" + expectation.M_AttributeSetInstance_ID //
		, "C_Flatrate_DataEntry_ID=" + normalizeId(expectation.C_Flatrate_DataEntry_ID) //
		, "Month=" + normalizeDate(expectation.dateMonth)  //
		, "Week=" + normalizeDate(expectation.dateWeek) //
		);
	}

	private Map<ArrayKey, I_PMM_Balance> retrievePMM_Balances()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PMM_Balance.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.create()
				.map(I_PMM_Balance.class, new Function<I_PMM_Balance, ArrayKey>()
				{

					@Override
					public ArrayKey apply(final I_PMM_Balance balanceRecord)
					{
						return createSegmentKey(balanceRecord);
					}
				});
	}

	private final Object normalizeDate(final Date date)
	{
		if (date == null)
		{
			return null;
		}
		return dateFormat.format(date);
	}

	private final Integer normalizeId(final Integer id)
	{
		return id != null && id > 0 ? id : null;
	}

	@ToStringBuilder(skip = true)
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

}
