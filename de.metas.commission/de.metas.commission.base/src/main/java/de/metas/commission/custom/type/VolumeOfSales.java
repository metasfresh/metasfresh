package de.metas.commission.custom.type;

/*
 * #%L
 * de.metas.commission.base
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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.adempiere.service.IParameterizable;
import de.metas.commission.custom.config.BaseConfig;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.IContractBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.util.HierarchyClimber.Result;

public class VolumeOfSales extends HierarchyCommission
{

	private static Logger logger = LogManager.getLogger(VolumeOfSales.class);

	@Override
	IAdvComInstance createNewInstance(
			final I_C_AdvCommissionFactCand cand,
			final PO poLine,
			final Timestamp dateToUse,
			final I_C_Sponsor customer,
			final I_C_Sponsor salesRep,
			final I_C_AdvCommissionTerm term,
			final int hierarchyLevel,
			final int forecastLevel,
			final int calculationLevel,
			final String status,
			final int adPInstanceId,
			final Map<String, Object> contextInfo)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(cand);
		final String trxName = InterfaceWrapperHelper.getTrxName(cand);

		final Timestamp date = cand.getDateAcct();

		final IContractBL contractBL = Services.get(IContractBL.class);
		final int maxLevel = (Integer)contractBL.retrieveInstanceParam(ctx, getComSystemType(), ConfigParams.NAME_LEVEL_MAX, trxName);

		if (forecastLevel > maxLevel)
		{
			VolumeOfSales.logger.debug("forecastLevel=" + forecastLevel + " > " + maxLevel + "; returning");
			return null;
		}

		Check.assume(hasMinimumRank(date, salesRep), salesRep + " has the minumum rank at date " + date);

		final ICommissionFactBL commissionFactBL = Services.get(ICommissionFactBL.class);

		final Map<IAdvComInstance, MCAdvCommissionFact> newInstanceAndFact = commissionFactBL
				.createInstanceAndFact(term, cand, poLine, customer, salesRep, hierarchyLevel, forecastLevel, -1, true, adPInstanceId);

		return newInstanceAndFact.keySet().iterator().next();
	}

	/**
	 * Always returns {@link Result#GO_ON}, because there might be an existing instance in the upline which might need to be corrected.
	 * 
	 * @return <code>Result.GO_ON</code>
	 */
	@Override
	Result getResult(final I_C_Sponsor sponsorSalesRep, final Timestamp date, final IAdvComInstance instance)
	{
		return Result.GO_ON;
	}

	/**
	 * Always returns 0, because this commission type is here to record volumes of sales, not to compute payable commissions.
	 * 
	 * @returns <code>BigDecimal.ZERO</code>
	 */
	@Override
	public BigDecimal getPercent(final IAdvComInstance inst, final String status, final Timestamp date)
	{
		return BigDecimal.ZERO;
	}

	@Override
	BigDecimal getPercent(final I_C_Sponsor salesRep, final I_C_Sponsor customer, final PO poLine, final String status, final Timestamp date, final int level)
	{
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getCommissionPointsSum(
			final IAdvComInstance inst,
			final String status,
			final Timestamp date,
			final Object po)
	{
		//
		// check if there is a sales rep at the given date
		final I_C_Sponsor sponsor = inst.getC_Sponsor_SalesRep();

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_BPartner salesRep = sponsorBL.retrieveSalesRepAt(ctx, date, sponsor, false, trxName);

		if (salesRep == null)
		{
			return BigDecimal.ZERO;
		}

		final IContractBL contractBL = Services.get(IContractBL.class);

		final int maxLevel = (Integer)contractBL.retrieveInstanceParam(
				ctx,
				getComSystemType(),
				ConfigParams.NAME_LEVEL_MAX,
				trxName);

		if (inst.getLevelForecast() > maxLevel)
		{
			VolumeOfSales.logger.debug("forecastLevel=" + inst.getLevelForecast() + " > " + maxLevel + "; returning 0 ");
			return BigDecimal.ZERO;
		}

		//
		// check if the sales rep has the right rank
		final boolean hasMinimumRank = hasMinimumRank(date, sponsor);
		if (!hasMinimumRank)
		{
			VolumeOfSales.logger.debug("Rank of " + salesRep + " is too low");
			return BigDecimal.ZERO;
		}

		return super.getCommissionPointsSum(inst, status, date, po);
	}

	@Override
	public IParameterizable getSponsorParams(final Properties ctx, final I_C_AdvCommissionCondition contract, final String trxName)
	{
		return new BaseConfig();
	}

	@Override
	public IParameterizable getInstanceParams(final Properties ctx, final I_C_AdvComSystem system, final String trxName)
	{
		return new InstanceParams();
	}

	private class InstanceParams extends BaseConfig implements IParameterizable
	{
		public InstanceParams()
		{
			addParam(ConfigParams.PARAM_LEVEL_MAX, 100, 0);
			addNewParam(ConfigParams.PARAM_HIERARCHY_SKIP_CUSTOMERS, "Nicht-VP ueberspringen", "", 10, true);
		}
	}

	@Override
	public boolean isCommissionCalculated()
	{
		return false;
	}
}
