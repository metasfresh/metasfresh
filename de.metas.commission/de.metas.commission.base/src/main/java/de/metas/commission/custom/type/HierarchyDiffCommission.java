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
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

import de.metas.adempiere.model.IProductAware;
import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.service.IParameterizable;
import de.metas.commission.custom.config.BaseConfig;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComRankCollection;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvComSystem_Type;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionRankDAO;
import de.metas.commission.service.IContractBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.IInstanceTriggerBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.util.HierarchyAscender;
import de.metas.commission.util.HierarchyClimber.Result;

/**
 * 
 * @author ts
 * 
 */
public class HierarchyDiffCommission extends HierarchyCommission
{
	public static final String CTX_COMMISSION_GRANTED = "COMMISSION_GRANTED";

	public static final String CTX_ALREADY_SEEN_SALESREP_C_SPONSOR_ID = "ALREADY_SEEN_SALESREP_C_SPONSOR_ID";

	private static final Logger logger = LogManager.getLogger(HierarchyDiffCommission.class);

	private boolean isMaxCommissionGranted(
			final Properties ctx,
			final Map<String, Object> contextInfo,
			final I_C_AdvComSystem_Type comSystemType,
			final I_C_Sponsor currentSponsor,
			final String trxName)
	{
		final IContractBL contractBL = Services.get(IContractBL.class);

		if (stopBecauseOfEarlierSalesRep(ctx, contextInfo, comSystemType, currentSponsor, trxName))
		{
			return true;
		}

		final BigDecimal maxCommission = (BigDecimal)contractBL.retrieveInstanceParam(ctx, comSystemType, ConfigParams.PARAM_MAX_COMMISSION, trxName);

		final BigDecimal alreadyGrantedCommission = (BigDecimal)contextInfo.get(HierarchyDiffCommission.CTX_COMMISSION_GRANTED);

		final boolean maxCommissionGranted =
				alreadyGrantedCommission != null && alreadyGrantedCommission.compareTo(maxCommission) >= 0;

		if (maxCommissionGranted)
		{
			Check.assume(alreadyGrantedCommission.compareTo(maxCommission) == 0,
					getComSystemType() + "; [alreadyGrantedCommission=" + alreadyGrantedCommission + "> [maxCommissionGranted=" + maxCommissionGranted + "]");
		}
		return maxCommissionGranted;
	}

	private boolean stopBecauseOfEarlierSalesRep(
			final Properties ctx,
			final Map<String, Object> contextInfo,
			final I_C_AdvComSystem_Type comSystemType,
			final I_C_Sponsor currentSponsor,
			final String trxName)
	{
		final IContractBL contractBL = Services.get(IContractBL.class);

		//
		// 04632 / 04775: even if no commission was granted, we stop, because there was already a salesrep
		// (note that this could even be the customer/buyer)
		final Integer alreadySeenSalesRep = (Integer)contextInfo.get(HierarchyDiffCommission.CTX_ALREADY_SEEN_SALESREP_C_SPONSOR_ID);

		final boolean stopBecauseOfEarlierSalesRep;
		final boolean stopAfterFirstSalesRep = (Boolean)contractBL.retrieveInstanceParam(ctx, comSystemType, ConfigParams.PARAM_STOP_AFTER_FIRST_SALES_REP, trxName);
		if (stopAfterFirstSalesRep
				&& alreadySeenSalesRep != null // value is set
				&& (int)alreadySeenSalesRep != currentSponsor.getC_Sponsor_ID()) // value is different from the sponsor we are currently looking at
		{
			stopBecauseOfEarlierSalesRep = true;
		}
		else
		{
			stopBecauseOfEarlierSalesRep = false;
		}
		return stopBecauseOfEarlierSalesRep;
	}

	/**
	 * 
	 * @param contextInfo contains the commission percentage that has already been granted somewhere else
	 * @param fullCommission the commission that would be returned if nothing had been granted yet.
	 * @return
	 */
	private BigDecimal subtractAlreadyGranted(final Map<String, Object> contextInfo, final BigDecimal fullCommission)
	{

		Check.errorIf(fullCommission.signum() < 0, "Param fullCommission={} may not be < 0", fullCommission);

		final BigDecimal alreadyGrantedCommission = (BigDecimal)contextInfo.get(HierarchyDiffCommission.CTX_COMMISSION_GRANTED);

		final BigDecimal commissionToGrant;

		if (alreadyGrantedCommission == null)
		{
			commissionToGrant = fullCommission;
			contextInfo.put(HierarchyDiffCommission.CTX_COMMISSION_GRANTED, fullCommission);
		}
		else
		{
			final BigDecimal leftCommission = fullCommission.subtract(alreadyGrantedCommission);

			if (leftCommission.signum() > 0)
			{
				commissionToGrant = leftCommission;
				contextInfo.put(HierarchyDiffCommission.CTX_COMMISSION_GRANTED, alreadyGrantedCommission.add(commissionToGrant));
			}
			else
			{
				commissionToGrant = BigDecimal.ZERO;
			}
		}
		return commissionToGrant;
	}

	/**
	 * Returns the commission as it is before some difference commission has been subtracted. If the value of {@link ConfigParams#PARAM_SUBTRACT_POLINE_DISCOUNT} is true and
	 * {@link IFieldAccessBL#getDiscount(org.compiere.model.PO, boolean)} returns a discount value (not null), that value is subtracted from the full commission.
	 * 
	 * 
	 * @param poLine the line whose discount is subtracted under the conditions outlines above
	 * @param sgCurrentLevel
	 * @return commission percent
	 */
	private BigDecimal getFullCommission(
			final Object poLine,
			final I_C_Sponsor sponsor,
			final Timestamp date,
			final I_C_AdvComSystem_Type comSystemType,
			final I_C_AdvCommissionSalaryGroup sgCurrentLevel,
			final I_M_Product product)
	{

		if (sgCurrentLevel == null)
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal fullCommission;

		final IContractBL contractBL = Services.get(IContractBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(poLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(poLine);
		final boolean subtractPOLineDiscount =
				(Boolean)contractBL.retrieveInstanceParam(
						ctx,
						comSystemType,
						ConfigParams.PARAM_SUBTRACT_POLINE_DISCOUNT,
						trxName);

		final BigDecimal percentForRank = getPercentForSG(sgCurrentLevel, sponsor, date, comSystemType, product);

		if (subtractPOLineDiscount)
		{
			final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);
			final BigDecimal poLineDiscount = faBL.getDiscount(poLine, false);

			if (poLineDiscount == null || poLineDiscount.signum() < 0)
			{
				fullCommission = percentForRank;
			}
			else
			{
				fullCommission =
						percentForRank.subtract(poLineDiscount.setScale(2, BigDecimal.ROUND_HALF_UP). // setting scale make sure we don't round aways too much
								divide(Env.ONEHUNDRED, BigDecimal.ROUND_HALF_UP));
			}
		}
		else
		{
			fullCommission = percentForRank;
		}
		if (fullCommission.signum() < 0)
		{
			return BigDecimal.ZERO;
		}
		return fullCommission;
	}

	private BigDecimal getPercentForSG(
			final I_C_AdvCommissionSalaryGroup sg,
			final I_C_Sponsor sponsor,
			final Timestamp date,
			final I_C_AdvComSystem_Type comSystemType,
			final I_M_Product product)
	{
		if (sg == null)
		{
			HierarchyDiffCommission.logger.info("sg is null => returning zero");
			return BigDecimal.ZERO;
		}
		if (sg.getValue() == null)
		{
			throw new IllegalArgumentException("sg.getValue() == null for sg=" + sg);
		}

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, comSystemType.getC_AdvComSystem(), product);

		final IContractBL contractBL = Services.get(IContractBL.class);
		final BigDecimal percent =
				(BigDecimal)contractBL.retrieveSponsorParam(commissionCtx, ConfigParams.PARAM_COMMISSION_ + sg.getValue());

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
		return getPercent(customer, salesRep, poLine, status, date);
	}

	@Override
	public BigDecimal getPercent(
			final IAdvComInstance inst,
			final String status,
			final Timestamp date)
	{
		if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(status)
				&& inst.getLevelCalculation() < 0)
		{
			// instance has been compressed
			return BigDecimal.ZERO;
		}

		final I_C_Sponsor spCustomer = inst.getC_Sponsor_Customer();
		final I_C_Sponsor spSalesRep = inst.getC_Sponsor_SalesRep();

		final Object instanceReferencedRecord = Services.get(ICommissionInstanceDAO.class).retrievePO(inst, Object.class);

		return getPercent(spCustomer, spSalesRep, instanceReferencedRecord, status, date);
	}

	private BigDecimal getPercent(
			final I_C_Sponsor spCustomer,
			final I_C_Sponsor spSalesRep,
			final Object instanceReferencedRecord,
			final String status,
			final Timestamp date)
	{
		if (spSalesRep.getC_Sponsor_ID() == spCustomer.getC_Sponsor_ID())
		{
			// a sales rep can't receive difference commission for their own
			// purchases
			return BigDecimal.ZERO;
		}

		final IProductAware productAware = InterfaceWrapperHelper.create(instanceReferencedRecord, IProductAware.class);
		final org.compiere.model.I_M_Product product = productAware.getM_Product();

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(spSalesRep, date, this, product);

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_AdvCommissionTerm term = sponsorBL.retrieveTerm(commissionCtx, false);
		if (term == null)
		{
			// the contract of spSalesRep doesn't cove the given product or category
			return BigDecimal.ZERO;
		}
		
		final IInstanceTriggerBL instanceTriggerBL = Services.get(IInstanceTriggerBL.class);
		if (!instanceTriggerBL.isInCorrectProductCategory(term, spSalesRep, instanceReferencedRecord))
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal[] result = { BigDecimal.ZERO };

		final IContractBL contractBL = Services.get(IContractBL.class);

		final int maxLevel = (Integer)contractBL.retrieveInstanceParam(
				InterfaceWrapperHelper.getCtx(spSalesRep),
				getComSystemType(), ConfigParams.NAME_LEVEL_MAX,
				InterfaceWrapperHelper.getTrxName(spSalesRep));

		// starting at the customer's level in the downline and ascending to the
		// sales rep's level, find out which percentage makes it to this level
		// (by subtracting the percentage that is granted on the way up)
		new HierarchyAscender()
		{
			@Override
			public Result actOnLevel(
					final I_C_Sponsor sponsorCurrentLevel,
					final int logicalLevel,
					final int hierarchyLevel,
					final Map<String, Object> contextInfo)
			{
				final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class)
						.create(sponsorCurrentLevel, date, HierarchyDiffCommission.this, product);

				// find out if sponsorCurrentLevel is a sales rep
				final I_C_Sponsor_SalesRep contractSSR = sponsorBL.retrieveContractSSR(
						InterfaceWrapperHelper.getCtx(sponsorCurrentLevel),
						sponsorCurrentLevel, date,
						InterfaceWrapperHelper.getTrxName(sponsorCurrentLevel));

				if (contractSSR == null)
				{
					if (isSkipOrphanedSponsor(sponsorCurrentLevel, date))
					{
						return Result.SKIP_IGNORE;
					}
					else
					{
						return Result.GO_ON;
					}
				}
				else
				{
					// 04632 / 04775 this info is required if 'ConfigParams.PARAM_STOP_AFTER_FIRST_SALES_REP' is true
					if(!contextInfo.containsKey(HierarchyDiffCommission.CTX_ALREADY_SEEN_SALESREP_C_SPONSOR_ID))
					{
						contextInfo.put(HierarchyDiffCommission.CTX_ALREADY_SEEN_SALESREP_C_SPONSOR_ID, sponsorCurrentLevel.getC_Sponsor_ID());
					}
				}

				if (sponsorCurrentLevel.getC_Sponsor_ID() == spCustomer.getC_Sponsor_ID())
				{
					return Result.SKIP_IGNORE;
				}

				final Properties ctx = InterfaceWrapperHelper.getCtx(spSalesRep);
				final String trxName = InterfaceWrapperHelper.getTrxName(spSalesRep);

				if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(status))
				{
					//
					// if we shift on dynamic compression and if the sales rep
					// at the current level is compressed away, then no
					// commission is subtracted at the current level. Therefore,
					// in this case the commission, that would otherwise go to
					// the sales rep at the current level, is shifted upwards
					final String compressionMode = getComSystemType().getDynamicCompression();

					if (X_C_AdvComSystem_Type.DYNAMICCOMPRESSION_NachObenVerschShift.equals(compressionMode))
					{
						final ISalesRefFactCollector srfCollector =
								sponsorBL.retrieveSalesRepFactCollector(ctx, sponsorCurrentLevel, date, trxName);
						if (srfCollector.isCompress(commissionCtx))
						{
							return Result.SKIP_IGNORE;
						}
					}
				}

				if(stopBecauseOfEarlierSalesRep(ctx,contextInfo,getComSystemType(), sponsorCurrentLevel, trxName))
				{
					// there was already another sales rep-sponsor (maybe the actual purchaser) and ConfigParams.PARAM_STOP_AFTER_FIRST_SALES_REP is true
					result[0] = BigDecimal.ZERO;
					return Result.FINISHED;
				}
				
				// find out which commission
				final BigDecimal fullCommission;

				final I_C_AdvCommissionSalaryGroup sgCurrentLevel = sponsorBL.retrieveRank(ctx, sponsorCurrentLevel, date, status, trxName);

				if (sgCurrentLevel.getC_AdvComRankCollection_ID() != getComSystemType().getC_AdvComSystem().getC_AdvComRankCollection_ID())
				{
					HierarchyDiffCommission.logger.info("Sponsor " + sponsorCurrentLevel.getSponsorNo()
							+ " has rank " + sgCurrentLevel.getValue()
							+ " at date " + date
							+ "; That rank doesn't fit with " + this + "; Using fullCommission=0");

					fullCommission = BigDecimal.ZERO;
				}
				else
				{
					final IProductAware productAware = InterfaceWrapperHelper.create(instanceReferencedRecord, IProductAware.class);
					final I_M_Product product = InterfaceWrapperHelper.create(productAware.getM_Product(), I_M_Product.class);
					fullCommission = getFullCommission(instanceReferencedRecord, sponsorCurrentLevel, date, getComSystemType(), sgCurrentLevel, product);
				}

				final BigDecimal maxCommission = (BigDecimal)contractBL.retrieveInstanceParam(ctx, getComSystemType(), ConfigParams.PARAM_MAX_COMMISSION, trxName);
				Check.assume(maxCommission.compareTo(fullCommission) >= 0,
						getComSystemType() + ";[fullCommission=" + fullCommission + " > [" + ConfigParams.PARAM_MAX_COMMISSION + "=" + maxCommission
								+ "]; rank=" + sgCurrentLevel.getValue() + "; instanceReferencedRecord=" + instanceReferencedRecord);

				result[0] = subtractAlreadyGranted(contextInfo, fullCommission);
				Check.assume(maxCommission.compareTo(result[0]) >= 0,
						getComSystemType() + "; [result[0]=" + fullCommission + "] > [" + ConfigParams.PARAM_MAX_COMMISSION + "=" + maxCommission + "]");

				if (sponsorCurrentLevel.getC_Sponsor_ID() == spSalesRep.getC_Sponsor_ID())
				{
					// we are done, 'result' contains the commission that is
					// left at spSalesRep level
					return Result.FINISHED;

				}
				else if (isMaxCommissionGranted(ctx, contextInfo, getComSystemType(), sponsorCurrentLevel, trxName))
				{
					// we didn't reach spSalesRep's level, but the maximum
					// possible commission has already been given away to
					// intermediate sponsors
					result[0] = BigDecimal.ZERO;
					return Result.FINISHED;
				}
				return Result.GO_ON;
			}
		}
				.setDate(date)
				.climb(spCustomer, maxLevel);

		return result[0];
	}

	@Override
	Result getResult(final I_C_Sponsor sponsorSalesRep, final Timestamp date, final IAdvComInstance instance)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsorSalesRep);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsorSalesRep);

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_BPartner bSalesRepCurrentLevel = sponsorBL.retrieveSalesRepAt(ctx, date, sponsorSalesRep, false, trxName);

		if (bSalesRepCurrentLevel == null)
		{
			if (isSkipOrphanedSponsor(sponsorSalesRep, date))
			{
				return Result.SKIP_EXTEND;
			}
		}
		return Result.GO_ON;
	}

	private class InstanceParams extends BaseConfig implements IParameterizable
	{
		public InstanceParams()
		{
			addNewParam(ConfigParams.PARAM_HIERARCHY_SKIP_CUSTOMERS, "Nicht-VP ueberspringen", "", 10, true);
			addNewParam(ConfigParams.PARAM_SUBTRACT_POLINE_DISCOUNT, "Auftragspos.-Rabatt abziehen", "", 12, false);

			addParam(ConfigParams.PARAM_LEVEL_MAX, 21, Integer.MAX_VALUE);

			// 04318
			addNewParam(ConfigParams.PARAM_STOP_AFTER_FIRST_SALES_REP, "Nur der naechste VP erhaelt eine Provision", "", 41, false);

			addNewParam(ConfigParams.PARAM_MAX_COMMISSION, "Maximal mögliche Provision", "", 601, BigDecimal.ONE);
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
		final BaseConfig params = new BaseConfig();

		final I_C_AdvComRankCollection collection = contract.getC_AdvComSystem().getC_AdvComRankCollection();

		final List<I_C_AdvCommissionSalaryGroup> ranks = Services.get(ICommissionRankDAO.class).retrieveForCollection(ctx, collection, trxName);
		int count = 10;

		for (final I_C_AdvCommissionSalaryGroup rank : ranks)
		{
			params.addNewParam(ConfigParams.PARAM_COMMISSION_ + rank.getValue(), getComSystemType().getName() + " fuer " + rank.getName(), "", count, BigDecimal.ZERO);
			count += 10;
		}
		return params;
	}

	@Override
	public final boolean isCommissionCalculated()
	{
		return true;
	}
}
