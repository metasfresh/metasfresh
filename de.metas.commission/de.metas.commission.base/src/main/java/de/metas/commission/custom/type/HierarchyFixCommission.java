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
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.adempiere.model.IProductAware;
import de.metas.adempiere.service.IParameterizable;
import de.metas.commission.custom.config.BaseConfig;
import de.metas.commission.exception.CommissionException;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.model.I_C_AdvComRankCollection;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionRankDAO;
import de.metas.commission.service.IContractBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.util.HierarchyClimber.Result;

public class HierarchyFixCommission extends HierarchyCommission
{
	private static final Logger logger = LogManager.getLogger(HierarchyFixCommission.class);

	private BigDecimal getPercentForSG(final ICommissionContext commissionCtx, final I_C_AdvCommissionSalaryGroup sg, final int level)
	{
		if (sg == null)
		{
			HierarchyFixCommission.logger.info("sg is null => returning zero");
			return BigDecimal.ZERO;
		}

		final String paramName = ConfigParams.PARAM_COMMISSION_ + sg.getValue() + "_" + level;

		final IContractBL contractBL = Services.get(IContractBL.class);

		if (!contractBL.hasSponsorParam(commissionCtx, paramName))
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal percent = (BigDecimal)contractBL.retrieveSponsorParam(commissionCtx, paramName);

		return percent;
	}

	@Override
	public BigDecimal getPercent(
			final I_C_Sponsor salesRep,
			final I_C_Sponsor customer,
			final PO poLine,
			final String status,
			final Timestamp date,
			final int level)
	{
		if (salesRep.getC_Sponsor_ID() == customer.getC_Sponsor_ID())
		{
			// a sales rep can't get HierachyFixCommission for her own sales.
			return BigDecimal.ZERO;
		}

		if (isPricingManuallyChanged(poLine))
		{
			return BigDecimal.ZERO; // task 04775, testcase 11: if pricing has been manually changed, don't give fixCommission
		}
		
		final IProductAware productAware = InterfaceWrapperHelper.create(poLine, IProductAware.class);
		final I_M_Product product = productAware.getM_Product();

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(salesRep, date, this, product);

		return getPercent(commissionCtx, level);
	}

	@Override
	public BigDecimal getPercent(final IAdvComInstance inst, final String status, final Timestamp date)
	{
		final int level;

		if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(status))
		{
			level = inst.getLevelCalculation();
		}
		else
		{
			level = inst.getLevelForecast();
		}

		// 04639 start: use M_Product parameter as well

		final Object poLine = Services.get(ICommissionInstanceDAO.class).retrievePO(inst, Object.class);
		Check.assumeNotNull(poLine, "po with AD_Table_ID=" + inst.getAD_Table_ID() + " and Record_ID=" + inst.getRecord_ID() + " not null");

		if (isPricingManuallyChanged(poLine))
		{
			return BigDecimal.ZERO; // task 04775, testcase 11: if pricing has been manually changed, don't give fixCommission
		}
		
		final IProductAware productAware = InterfaceWrapperHelper.create(poLine, IProductAware.class);
		final I_M_Product product = productAware.getM_Product();

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(inst.getC_Sponsor_SalesRep(), date, this, product);

		return getPercent(commissionCtx, level);
	}

	private boolean isPricingManuallyChanged(final Object poLine)
	{
		if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_OrderLine.class))
		{
			final I_C_OrderLine ol = InterfaceWrapperHelper.create(poLine, I_C_OrderLine.class);
			return ol.isManualDiscount() || ol.isManualPrice();
		}
		return false;
	}

	/**
	 * Note: we always use the actual rank. In HierarchyFixCommission, the status (forecast or actual) only affects the level that is used.
	 * 
	 * @param sponsor
	 * @param i
	 * @param date
	 * @param level
	 * @return
	 */
	private BigDecimal getPercent(final ICommissionContext commissionCtx, final int level)
	{
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();
		final Timestamp date = commissionCtx.getDate();
		if (level < 0)
		{
			// instance has been compressed; note it might be OK to get commission for level 0, depending on the contract
			return BigDecimal.ZERO;
		}

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final I_C_Sponsor_SalesRep contractSSR = sponsorBL.retrieveContractSSR(ctx, sponsor, date, trxName);
		if (contractSSR == null)
		{
			HierarchyFixCommission.logger.debug(sponsor + " is not a sales rep at " + date);
			return BigDecimal.ZERO;
		}

		final I_C_AdvCommissionSalaryGroup rank = sponsorBL.retrieveRank(ctx, sponsor, getComSystemType().getC_AdvComSystem(), date, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, trxName);

		return getPercentForSG(commissionCtx, rank, level);
	}

	@Override
	Result getResult(final I_C_Sponsor sponsorSalesRep, final Timestamp date, final IAdvComInstance newInstance)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsorSalesRep);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsorSalesRep);

		final I_C_BPartner bSalesRepCurrentLevel = sponsorBL.retrieveSalesRepAt(ctx, date, sponsorSalesRep, false, trxName);

		if (bSalesRepCurrentLevel == null)
		{
			if (isSkipOrphanedSponsor(sponsorSalesRep, date))
			{
				return Result.SKIP_EXTEND;
			}
		}
		else
		{
			// we are asserting this to trap caching problems. It needs to be
			// removed on the long run, because it doesn't hold under
			// all circumstances.
			// assert sponsorSalesRep.getSponsorNo().equals(bSalesRepCurrentLevel.getValue());
		}
		return Result.GO_ON;

	}

	private class InstanceParams extends BaseConfig implements IParameterizable
	{
		public InstanceParams()
		{
			addNewParam(ConfigParams.PARAM_HIERARCHY_SKIP_CUSTOMERS, "Nicht-VP ueberspringen", "", 10, true);
			addParam(ConfigParams.PARAM_LEVEL_MAX, 21, 6);
		}
	}

	@Override
	public IParameterizable getInstanceParams(final Properties ctx, final I_C_AdvComSystem system, final String trxName)
	{
		return new InstanceParams();
	}

	@Override
	public IParameterizable getSponsorParams(final Properties ctx, final I_C_AdvCommissionCondition contract, final String trxName)
	{
		final IContractBL contractBL = Services.get(IContractBL.class);

		final Integer maxLevel = (Integer)contractBL.retrieveInstanceParam(ctx, getComSystemType(), ConfigParams.NAME_LEVEL_MAX, trxName);
		if (maxLevel == null || maxLevel < 0)
		{
			// TODO -> AD_Message
			throw CommissionException.inconsistentConfig(
					"Invalid instance parameter '" + ConfigParams.NAME_LEVEL_MAX + "'=" + maxLevel,
					this);
		}

		final I_C_AdvComRankCollection collection = contract.getC_AdvComSystem().getC_AdvComRankCollection();

		final List<I_C_AdvCommissionSalaryGroup> ranks = Services.get(ICommissionRankDAO.class).retrieveForCollection(ctx, collection, trxName);

		final BaseConfig params = new BaseConfig();
		int count = 10;
		for (int level = 0; level <= maxLevel; level++)
		{
			for (final I_C_AdvCommissionSalaryGroup rank : ranks)
			{
				params.addNewParam(ConfigParams.PARAM_COMMISSION_ + rank.getValue() + "_" + level, "Ebenenbonus fuer " + rank.getName() + " Ebene " + level, "", count, BigDecimal.ZERO);
				count += 10;
			}
		}
		return params;
	}

	@Override
	public boolean isCommissionCalculated()
	{
		return true;
	}
}
