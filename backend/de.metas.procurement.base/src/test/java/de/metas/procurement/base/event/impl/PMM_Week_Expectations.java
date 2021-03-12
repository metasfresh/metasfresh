package de.metas.procurement.base.event.impl;

import de.metas.procurement.base.model.I_PMM_Week;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.junit.Assert;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

public class PMM_Week_Expectations
{
	public static PMM_Week_Expectations newExpectations()
	{
		return new PMM_Week_Expectations();
	}

	private final List<PMM_Week_Expectation> expectationsList = new ArrayList<>();
	private boolean strictMatching = true;

	private PMM_Week_Expectations()
	{
		super();
	}

	public PMM_Week_Expectations assertExpected()
	{
		final Map<ArrayKey, I_PMM_Week> recordsOrig = retrievePMM_Weeks();
		final Map<ArrayKey, I_PMM_Week> records = new HashMap<>(recordsOrig);

		final Map<ArrayKey, PMM_Week_Expectation> expectationsBySegmentKey = getExpectationsIndexedBySegmentKey();
		for (final Map.Entry<ArrayKey, PMM_Week_Expectation> e : expectationsBySegmentKey.entrySet())
		{
			final ArrayKey key = e.getKey();
			final PMM_Week_Expectation expectation = e.getValue();

			final I_PMM_Week record = records.remove(key);

			final String msg = "SegmentKey=" + key;

			try
			{
				expectation.assertExpected(msg, record);
			}
			catch (final AssertionError ex)
			{
				dumpRecords(recordsOrig);
				dumpExpectations(expectationsBySegmentKey);
				throw ex;
			}
		}

		if (strictMatching)
		{
			Assert.assertEquals("No other entries were expected", Collections.emptyMap(), records);
		}

		return this;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public PMM_Week_Expectations copy()
	{
		final PMM_Week_Expectations expectationsNew = new PMM_Week_Expectations();
		expectationsNew.setStrictMatching(strictMatching);

		for (final PMM_Week_Expectation expectation : this.expectationsList)
		{
			expectationsNew.expectationsList.add(expectation.copy(expectationsNew));
		}

		return expectationsNew;
	}

	private void dumpRecords(final Map<ArrayKey, I_PMM_Week> records)
	{
		System.out.println("Dump PMM_Week records: ");
		for (final Map.Entry<ArrayKey, I_PMM_Week> e : records.entrySet())
		{
			System.out.println("" + e.getKey() + ": " + e.getValue());
		}
	}

	private void dumpExpectations(final Map<ArrayKey, PMM_Week_Expectation> expectationsBySegmentKey)
	{
		System.out.println("Dump expectations: ");
		for (final Entry<ArrayKey, PMM_Week_Expectation> e : expectationsBySegmentKey.entrySet())
		{
			System.out.println("" + e.getKey() + ": " + e.getValue());
		}
	}

	public PMM_Week_Expectations setStrictMatching(final boolean strictMatching)
	{
		this.strictMatching = strictMatching;
		return this;
	}

	public PMM_Week_Expectation newExpectation()
	{
		final PMM_Week_Expectation expectation = new PMM_Week_Expectation(this);
		expectationsList.add(expectation);
		return expectation;
	}

	private Map<ArrayKey, PMM_Week_Expectation> getExpectationsIndexedBySegmentKey()
	{
		final Map<ArrayKey, PMM_Week_Expectation> map = new HashMap<>();

		for (final PMM_Week_Expectation expectation : expectationsList)
		{
			final ArrayKey key = createSegmentKey(expectation);
			map.put(key, expectation);
		}

		return map;
	}

	private ArrayKey createSegmentKey(final I_PMM_Week record)
	{
		return Util.mkKey(
				"BP=" + record.getC_BPartner_ID() //
				, "P=" + record.getM_Product_ID() //
				, "ASI=" + normalizeId(record.getM_AttributeSetInstance_ID()) //
				, "Week=" + normalizeDate(record.getWeekDate()) //
		);
	}

	private ArrayKey createSegmentKey(final PMM_Week_Expectation expectation)
	{
		return Util.mkKey(
				"BP=" + expectation.C_BPartner_ID //
				, "P=" + expectation.M_Product_ID //
				, "ASI=" + normalizeId(expectation.M_AttributeSetInstance_ID) //
				, "Week=" + normalizeDate(expectation.dateWeek) //
		);
	}

	private Map<ArrayKey, I_PMM_Week> retrievePMM_Weeks()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PMM_Week.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.create()
				.map(I_PMM_Week.class, this::createSegmentKey);
	}

	@Nullable
	private Object normalizeDate(final Date date)
	{
		if (date == null)
		{
			return null;
		}
		return normalizeDate(TimeUtil.asLocalDate(date));
	}

	@Nullable
	private Object normalizeDate(@Nullable final LocalDate date)
	{
		if (date == null)
		{
			return null;
		}

		return date.toString();
	}

	@Nullable
	private Integer normalizeId(final Integer id)
	{
		return id != null && id > 0 ? id : null;
	}
}
