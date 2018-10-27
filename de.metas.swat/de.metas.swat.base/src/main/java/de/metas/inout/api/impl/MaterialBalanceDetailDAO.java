package de.metas.inout.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.calendar.ICalendarDAO;
import de.metas.inout.IInOutBL;
import de.metas.inout.api.IMaterialBalanceConfigBL;
import de.metas.inout.api.IMaterialBalanceDetailDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_Material_Balance_Config;
import de.metas.inout.model.I_M_Material_Balance_Detail;
import de.metas.inout.spi.IMaterialBalanceConfigMatcher;
import de.metas.util.Services;

public class MaterialBalanceDetailDAO implements IMaterialBalanceDetailDAO
{

	@Override
	public void addLineToBalance(final I_M_InOutLine line, final I_M_Material_Balance_Config balanceConfig)
	{
		// services
		final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);
		final IMaterialBalanceConfigBL materialConfigBL = Services.get(IMaterialBalanceConfigBL.class);

		// create new entry
		final I_M_Material_Balance_Detail newBalanceDetail =
				InterfaceWrapperHelper.newInstance(I_M_Material_Balance_Detail.class, line);

		// header
		final org.compiere.model.I_M_InOut inout = line.getM_InOut();

		// set info from line

		newBalanceDetail.setM_InOutLine(line);
		newBalanceDetail.setM_Product(line.getM_Product());
		newBalanceDetail.setC_UOM(line.getC_UOM());

		// set qty based on SOTrx of the inout

		final boolean isReturnMovementType = Services.get(IInOutBL.class).isReturnMovementType(inout.getMovementType());

		if (!isReturnMovementType)
		{
			if (inout.isSOTrx()) // sales => outgoing qty
			{
				newBalanceDetail.setQtyOutgoing(line.getQtyEntered());
				newBalanceDetail.setQtyIncoming(Env.ZERO);
			}
			else
			// purchase => incoming qty
			{
				newBalanceDetail.setQtyIncoming(line.getQtyEntered());
				newBalanceDetail.setQtyOutgoing(Env.ZERO);
			}
		}
		// task 09037
		// In case of return movement type, the incoming and outgoing qty must be the other way around
		else
		{
			if (inout.isSOTrx()) // sales => outgoing qty
			{
				newBalanceDetail.setQtyIncoming(line.getQtyEntered());
				newBalanceDetail.setQtyOutgoing(Env.ZERO);
			}
			else
			// purchase => incoming qty
			{
				newBalanceDetail.setQtyOutgoing(line.getQtyEntered());
				newBalanceDetail.setQtyIncoming(Env.ZERO);
			}
		}

		// set info from header

		newBalanceDetail.setM_InOut(inout);
		newBalanceDetail.setC_BPartner(inout.getC_BPartner());
		newBalanceDetail.setC_DocType(inout.getC_DocType());
		newBalanceDetail.setDocumentNo(inout.getDocumentNo());

		final Timestamp movementDate = inout.getMovementDate();
		newBalanceDetail.setMovementDate(movementDate);

		// set info from balance config

		newBalanceDetail.setM_Material_Balance_Config(balanceConfig);

		// set the correct period
		final Properties ctx = InterfaceWrapperHelper.getCtx(line);
		final String trxName = InterfaceWrapperHelper.getTrxName(line);
		final I_C_Calendar calendar = balanceConfig.getC_Calendar();
		final I_C_Period period = calendarDAO.findByCalendar(ctx, movementDate, calendar.getC_Calendar_ID(), trxName);

		newBalanceDetail.setC_Period(period);

		// initialize isReset with false just to make sure
		newBalanceDetail.setIsReset(false);

		// is used for flatrate

		// NOTE: For the time being, the flatrate matcher is the only matcher we have so it is safe to
		// use this implementation for only making sure it is for flatrate or not
		// In the future, if there will be more matchers, this implementation shall be modified accordingly

		final CopyOnWriteArrayList<IMaterialBalanceConfigMatcher> matchers = materialConfigBL.retrieveMatchers();

		boolean isUseForFlatrate = true;
		for (final IMaterialBalanceConfigMatcher matcher : matchers)
		{
			if (!matcher.matches(line))
			{
				isUseForFlatrate = false;
				break;
			}
		}

		// Using the default value of the isUseForFlatrate to make sure it matched for null value
		final boolean configFitForFlatrate = DisplayType.toBoolean(balanceConfig.getIsForFlatrate(), isUseForFlatrate);
		if (configFitForFlatrate != isUseForFlatrate)
		{
			// shall never happen
			throw new AdempiereException("The material balance configuration " + balanceConfig
					+ " has isUseForFlatrate = " + balanceConfig.getIsForFlatrate()
					+ " but the inout line has isUseForFlatare = " + isUseForFlatrate);
		}

		newBalanceDetail.setIsForFlatrate(isUseForFlatrate);

		// save
		InterfaceWrapperHelper.save(newBalanceDetail);
	}

	@Override
	public void removeInOutFromBalance(final I_M_InOut inout)
	{
		final IMaterialBalanceDetailDAO materialBalanceDetailDAO = Services.get(IMaterialBalanceDetailDAO.class);

		materialBalanceDetailDAO.deleteBalanceDetailsForInOut(inout);

	}

	@Override
	public List<I_M_Material_Balance_Detail> retrieveBalanceDetailsForInOut(final I_M_InOut inout)
	{

		final IQueryBuilder<I_M_Material_Balance_Detail> queryBuilder = createQueryBuilderForInout(inout);

		return queryBuilder
				.create()
				.list();
	}

	private IQueryBuilder<I_M_Material_Balance_Detail> createQueryBuilderForInout(I_M_InOut inout)
	{

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_M_Material_Balance_Detail> queryBuilder = queryBL.createQueryBuilder(I_M_Material_Balance_Detail.class, inout)
				.filterByClientId()
				.addOnlyActiveRecordsFilter();

		// same inout id ( possible because all the balance details have both inoutline and inout)
		queryBuilder.addEqualsFilter(I_M_Material_Balance_Detail.COLUMNNAME_M_InOut_ID, inout.getM_InOut_ID());

		return queryBuilder;
	}

	@Override
	public List<I_M_Material_Balance_Detail> retrieveDetailsForConfigAndDate(final I_M_Material_Balance_Config config, final Timestamp resetDate)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_M_Material_Balance_Detail> queryBuilder = queryBL.createQueryBuilder(I_M_Material_Balance_Detail.class, config)
				.filterByClientId()
				.addOnlyActiveRecordsFilter();

		if (config != null)
		{
			queryBuilder.addEqualsFilter(I_M_Material_Balance_Detail.COLUMNNAME_M_Material_Balance_Config_ID, config.getM_Material_Balance_Config_ID());
		}

		queryBuilder.addCompareFilter(I_M_Material_Balance_Detail.COLUMNNAME_MovementDate, Operator.LESS, resetDate);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public void deleteBalanceDetailsForInOut(I_M_InOut inout)
	{
		final IQueryBuilder<I_M_Material_Balance_Detail> queryBuilder = createQueryBuilderForInout(inout);

		// delete directly all the entries
		queryBuilder
				.create()
				.deleteDirectly();
	}

}
