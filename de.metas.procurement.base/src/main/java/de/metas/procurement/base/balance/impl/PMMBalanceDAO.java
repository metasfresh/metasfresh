package de.metas.procurement.base.balance.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;

import de.metas.procurement.base.balance.IPMMBalanceDAO;
import de.metas.procurement.base.balance.PMMBalanceSegment;
import de.metas.procurement.base.model.I_PMM_Balance;

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

public class PMMBalanceDAO implements IPMMBalanceDAO
{
	public static enum DateSegment
	{
		Week, Month;
	};

	@Override
	public List<I_PMM_Balance> retriveForAllDateSegments(final PMMBalanceSegment event, final Date date)
	{
		return Arrays.asList(
				retrieveForDateSegment(event, date, DateSegment.Month) //
				, retrieveForDateSegment(event, date, DateSegment.Week));
	}

	@VisibleForTesting
	I_PMM_Balance retrieveForDateSegment(final PMMBalanceSegment segment, final Date date, final DateSegment dateSegment)
	{
		//
		// Set the date segment values
		final Timestamp monthDate = TimeUtil.trunc(date, TimeUtil.TRUNC_MONTH);
		final Timestamp weekDate;
		if (dateSegment == DateSegment.Week)
		{
			weekDate = TimeUtil.trunc(date, TimeUtil.TRUNC_WEEK);
		}
		else if (dateSegment == DateSegment.Month)
		{
			weekDate = null;
		}
		else
		{
			throw new AdempiereException("@NotSupported@ " + DateSegment.class + ": " + dateSegment);
		}

		final PlainContextAware context = PlainContextAware.newWithThreadInheritedTrx();
		I_PMM_Balance balanceRecord = Services.get(IQueryBL.class).createQueryBuilder(I_PMM_Balance.class, context)
				//
				// BPartner + Product segment
				.addEqualsFilter(I_PMM_Balance.COLUMN_C_BPartner_ID, segment.getC_BPartner_ID())
				.addEqualsFilter(I_PMM_Balance.COLUMN_M_Product_ID, segment.getM_Product_ID())
				.addEqualsFilter(I_PMM_Balance.COLUMN_M_AttributeSetInstance_ID, segment.getM_AttributeSetInstance_ID() > 0 ? segment.getM_AttributeSetInstance_ID() : null)
				// .addEqualsFilter(I_PMM_Balance.COLUMN_M_HU_PI_Item_Product_ID, segment.getM_HU_PI_Item_Product_ID() > 0 ? segment.getM_HU_PI_Item_Product_ID() : null)
				.addEqualsFilter(I_PMM_Balance.COLUMN_C_Flatrate_DataEntry_ID, segment.getC_Flatrate_DataEntry_ID() > 0 ? segment.getC_Flatrate_DataEntry_ID() : null)
				//
				// Date segment
				.addEqualsFilter(I_PMM_Balance.COLUMN_MonthDate, monthDate)
				.addEqualsFilter(I_PMM_Balance.COLUMN_WeekDate, weekDate)
				//
				.create()
				.firstOnly(I_PMM_Balance.class);

		//
		// Create new record if not found
		if (balanceRecord == null)
		{
			balanceRecord = InterfaceWrapperHelper.newInstance(I_PMM_Balance.class, context);
			balanceRecord.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);

			//
			// BPartner + Product segment
			balanceRecord.setC_BPartner_ID(segment.getC_BPartner_ID());
			balanceRecord.setM_Product_ID(segment.getM_Product_ID());
			if (segment.getM_AttributeSetInstance_ID() > 0)
			{
				balanceRecord.setM_AttributeSetInstance_ID(segment.getM_AttributeSetInstance_ID());
			}

			// balanceRecord.setM_HU_PI_Item_Product_ID(segment.getM_HU_PI_Item_Product_ID());

			if (segment.getC_Flatrate_DataEntry_ID() > 0)
			{
				balanceRecord.setC_Flatrate_DataEntry_ID(segment.getC_Flatrate_DataEntry_ID());
			}

			//
			// Date segment
			balanceRecord.setMonthDate(monthDate);
			balanceRecord.setWeekDate(weekDate);

			//
			// Qtys
			balanceRecord.setQtyOrdered(BigDecimal.ZERO);
			balanceRecord.setQtyOrdered_TU(BigDecimal.ZERO);
			balanceRecord.setQtyPromised(BigDecimal.ZERO);
			balanceRecord.setQtyPromised_TU(BigDecimal.ZERO);
		}

		return balanceRecord;
	}
}
