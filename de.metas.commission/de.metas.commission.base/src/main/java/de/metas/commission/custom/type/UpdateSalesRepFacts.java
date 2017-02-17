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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_Product;
import org.compiere.model.PO;
import org.compiere.util.Msg;
import org.slf4j.Logger;

import de.metas.adempiere.model.IProductAware;
import de.metas.adempiere.service.IParameterizable;
import de.metas.commission.custom.config.BaseConfig;
import de.metas.commission.exception.CommissionException;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComRankCollection;
import de.metas.commission.model.I_C_AdvComRankForecast;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvComRankForecast;
import de.metas.commission.model.MCAdvComSystem;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionRankDAO;
import de.metas.commission.service.ICommissionSalesRepFactDAO;
import de.metas.commission.service.IContractBL;
import de.metas.commission.service.IHierarchyBL;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.util.CommissionTools;
import de.metas.commission.util.HierarchyAscender;
import de.metas.logging.LogManager;

/**
 * Evaluates volume-of-sales commission facts. Writes APV, ADV and Rank changes to <code>C_AdvComSalesRep</code>.
 * 
 * @author ts
 * 
 */
public class UpdateSalesRepFacts implements ISalesRefFactCollector
{
	public static final String MSG_DISCOUNT_SCHEMA_MISSING_1P = "DiscountSchemaMissing_1P";

	private static final Logger logger = LogManager.getLogger(UpdateSalesRepFacts.class);

	private int typeId;

	private I_C_AdvComSystem_Type comSystemType;

	private String[] comFact2SalesRepFactStatus(final I_C_AdvCommissionFact volumeOfSalesFact)
	{
		final String[] salesRepStatus;

		if (X_C_AdvCommissionFact.STATUS_ZuBerechnen.equals(volumeOfSalesFact.getStatus()))
		{
			salesRepStatus = new String[] { X_C_AdvComSalesRepFact.STATUS_Prov_Relevant };
		}
		else if (X_C_AdvCommissionFact.STATUS_Prognostiziert.equals(volumeOfSalesFact.getStatus()))
		{
			salesRepStatus = new String[] { X_C_AdvComSalesRepFact.STATUS_Prognose, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant };
		}
		else
		{
			throw new IllegalStateException(volumeOfSalesFact + " has illegal status " + volumeOfSalesFact.getStatus());
		}
		return salesRepStatus;
	}

	/**
	 * Evaluates <code>MCAdvCommissionFact</code>s if their commission instance has the right commission term id.
	 */
	@Override
	public void evaluateCandidate(final MCAdvCommissionFactCand cand, final String status, final int adPInstanceId)
	{
		Check.assume(X_C_AdvComSalesRepFact.STATUS_Prognose.equals(status), status + "=" + X_C_AdvComSalesRepFact.STATUS_Prognose);

		final PO po = Services.get(ICommissionFactCandBL.class).retrievePO(cand);

		if (!(po instanceof MCAdvCommissionFact))
		{
			return;
		}

		final MCAdvCommissionFact volumeOfSalesFact = (MCAdvCommissionFact)po;

		if (X_C_AdvCommissionFact.STATUS_Auszuzahlen.equals(volumeOfSalesFact.getStatus()))
		{
			// 'volumeOfSalesFact' deals with commission payment and is therefore not relevant for sale rep facts.
			return; // nothing to do
		}

		final IAdvComInstance comInstance = InterfaceWrapperHelper.create(volumeOfSalesFact.getC_AdvCommissionInstance(), IAdvComInstance.class);
		if (!comInstance.isVolumeOfSales())
		{
			return; // nothing to do
		}

		Check.assume(comInstance.getC_AdvComSystem_Type_ID() == comInstance.getC_AdvCommissionTerm().getC_AdvComSystem_Type_ID(),
				"C_AdvComSystem_Type_ID of " + comInstance + " is the same as the C_AdvComSystem_Type_ID of the onstance's C_AdvCommissionTerm");

		if (getComSystemType().getC_AdvComSystem_ID() != comInstance.getC_AdvComSystem_Type().getC_AdvComSystem_ID())
		{
			// this commission type is not interested in the given volumeOfSales fact,
			// because that fact belongs to a different commission system
			return;
		}

		final I_C_Sponsor sponsor = comInstance.getC_Sponsor_SalesRep();
		final Timestamp date = cand.getDateAcct();

		final BigDecimal commissionPointsSum = volumeOfSalesFact.getCommissionPointsSum();

		//
		// the sales rep fact status
		final String[] srfStatus = comFact2SalesRepFactStatus(volumeOfSalesFact);

		//
		// update APV
		final List<I_C_Sponsor> apv = updateAPV(sponsor, srfStatus[0], volumeOfSalesFact);

		//
		// update ADV and return the sponsors that we touched
		final List<I_C_Sponsor> adv = updateADV(sponsor, srfStatus[0], volumeOfSalesFact);

		final Set<I_C_Sponsor> sponsorsToUpdate = new HashSet<I_C_Sponsor>();
		sponsorsToUpdate.addAll(apv);

		final Object poLine = Services.get(ICommissionInstanceDAO.class).retrievePO(comInstance, Object.class);
		Check.assumeNotNull(poLine, "poLine with AD_Table_ID=" + comInstance.getAD_Table_ID() + " and Record_ID=" + comInstance.getRecord_ID() + " not null");

		final IProductAware productAware = InterfaceWrapperHelper.create(poLine, IProductAware.class);
		final org.compiere.model.I_M_Product product = productAware.getM_Product();

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, product);

		final IContractBL contractBL = Services.get(IContractBL.class);
		final String advRankRelevant = (String)contractBL.retrieveSponsorParam(commissionCtx, ConfigParams.PARAM_ADV_RANK_RELEVANT);

		if ("Y".equals(advRankRelevant))
		{
			// adv-points are relevant for the rank, so add them to our list
			sponsorsToUpdate.addAll(adv);
		}

		//
		// for all salesRepFacts, we need to check:

		//
		// 1. do they cause a change in rank?
		if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(srfStatus[0]))
		{
			// an ACTUAL change in points can cause a change in FORECAST rank,
			// because it is based on FORCAST + ACTUAL points
			updateRanks(commissionCtx,
					sponsorsToUpdate,
					new String[] { X_C_AdvComSalesRepFact.STATUS_Prognose, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant },
					commissionPointsSum);
		}

		final Map<I_C_Sponsor, List<I_C_AdvCommissionSalaryGroup>> sponsor2RankChange = updateRanks(commissionCtx, sponsorsToUpdate, srfStatus, commissionPointsSum);

		//
		// 2. do the rank changes cause a change in discount schema? (only if
		// salesRepStatus=FORECAST)
		if (X_C_AdvComSalesRepFact.STATUS_Prognose.equals(srfStatus[0]))
		{
			updateDiscountSchemas(sponsor2RankChange, date, commissionPointsSum);
		}

		//
		// 3. does the given fact cause a change in compression? Sponsors that
		// didn't have a change in APV, but only in their rank must also be
		// concerned here (if salesRepStatus=ACTUAL)
		if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(srfStatus[0]))
		{
			final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
			final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

			final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

			for (final I_C_Sponsor currentSponsor : sponsorsToUpdate)
			{
				final I_C_Period period = sponsorBL.retrieveCommissionPeriod(currentSponsor, date);

				final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(currentSponsor, period.getEndDate(), this, comSystem, product);

				srfBL.retrieveIsCompress(commissionCtx2, period, false);
			}
		}
	}

	/**
	 * Returns <code>true</code> is the given bPartner has at least 3 sales reps in his downline who have at least the salary group of the given <code>sgValue</code>.
	 * 
	 * @param date
	 * @param bPartner
	 * @param sgValue
	 * @return
	 */
	protected boolean hasInDownLine(final Timestamp date, final I_C_Sponsor sponsor, final String sgValue, final String status)
	{
		final IHierarchyBL hierarchyBL = Services.get(IHierarchyBL.class);

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		return hierarchyBL.findSponsorsInDownline(date, sponsor, Integer.MAX_VALUE, 3, comSystem, sgValue, status).size() >= 3;
	}

	protected boolean isIntParamLessOrEqual(final ICommissionContext commissionCtx, final String name, final BigDecimal sum)
	{
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();
		final Timestamp date = commissionCtx.getDate();
		final I_M_Product product = commissionCtx.getM_Product();

		final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, product);

		final IContractBL contractBL = Services.get(IContractBL.class);

		final int intValue = (Integer)contractBL.retrieveSponsorParam(commissionCtx2, name);

		return sum.compareTo(new BigDecimal(intValue)) >= 0;
	}

	/**
	 * Note: the discount schema is an instance param (not a sponsor param), because it is currently unspecified, which discount schema to apply for a given bPartner with two sponsors, if these
	 * sponsors' would have different discount schemas for the same rank.
	 * 
	 * @param ctx
	 * @param sg
	 * @param trxName
	 * @return null if the salary group has no discount schema
	 */
	@Override
	public I_M_DiscountSchema retrieveDiscountSchema(final I_C_AdvCommissionSalaryGroup sg, final I_C_Sponsor sponsor, final Timestamp date)
	{
		if (sg == null)
		{
			throw MiscUtils.illegalArgumentEx(sg, "sg");
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(sg);
		final String trxName = InterfaceWrapperHelper.getTrxName(sg);

		final IContractBL contractBL = Services.get(IContractBL.class);

		final String dsValue = (String)contractBL.retrieveInstanceParam(
				ctx,
				comSystemType,
				ConfigParams.PARAM_DS_OF_SG_ + sg.getValue(),
				trxName);

		if (dsValue == null)
		{
			return null;
		}

		final I_M_DiscountSchema ds = CommissionTools.retrieveDiscountSchemaForValue(ctx, dsValue, trxName);

		if (ds == null)
		{
			final String msg = Msg.getMsg(ctx, UpdateSalesRepFacts.MSG_DISCOUNT_SCHEMA_MISSING_1P, new Object[] { dsValue });
			throw CommissionException.inconsistentConfig(msg, sg);
		}
		return ds;
	}

	private I_C_Period retrievePeriod(final I_C_Sponsor salesRep, final Timestamp date)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_Period period = sponsorBL.retrieveCommissionPeriod(salesRep, date);
		return period;
	}

	/**
	 * 
	 * @param bPartner
	 * @param sum
	 * @param date
	 * @param status
	 * @return
	 */
	@Override
	public I_C_AdvCommissionSalaryGroup retrieveSalaryGroup(
			final ICommissionContext commissionCtx,
			final BigDecimal sum,
			final String status)
	{
		final I_C_AdvComRankCollection comRankCollection = getComSystemType().getC_AdvComSystem().getC_AdvComRankCollection();
		assert comRankCollection.getC_AdvComRankCollection_ID() > 0;

		final Properties ctx = commissionCtx.getCtx();
		final String trxName = commissionCtx.getTrxName();

		final List<I_C_AdvCommissionSalaryGroup> ranks = Services.get(ICommissionRankDAO.class).retrieveForCollection(ctx, comRankCollection, trxName);

		Collections.reverse(ranks);
		for (final I_C_AdvCommissionSalaryGroup rank : ranks)
		{
			if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + rank.getValue(), sum))
			{
				final I_C_AdvCommissionSalaryGroup sgResult = Services.get(ICommissionRankDAO.class).retrieve(ctx, comRankCollection, rank.getValue(), trxName);
				return sgResult;
			}
		}

		return Services.get(ICommissionRankDAO.class).retrieveLowest(ctx, comRankCollection, trxName);
	}

	/**
	 * 
	 * @param salesRep the "sales rep" sponsor of the commission fact's instance
	 * 
	 * @return List of sales rep facts that have been created or updated due to the given <code>volumeOfSalesFact</code> .
	 */
	List<I_C_Sponsor> updateAPV(final I_C_Sponsor salesRep, final String status, final I_C_AdvCommissionFact volumeOfSalesFact)
	{
		// Add the fact's commissionPointsSum to the salesRep's APV.

		// No need to search for necessary corrections up and down the hierarchy,
		// because these would all be triggered by additional volumeOfSalesFact's
		// with negative commissionPointsSum values.

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final Timestamp date = volumeOfSalesFact.getDateDoc();

		final I_C_Period period = retrievePeriod(salesRep, date);

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();
		srfBL.addAmtToSrf(salesRep, comSystem, X_C_AdvComSalesRepFact.NAME_APV, status, period, date, volumeOfSalesFact);

		return Collections.singletonList(salesRep);
	}

	void updateDiscountSchemas(
			final Map<I_C_Sponsor, List<I_C_AdvCommissionSalaryGroup>> sponsor2RankChange,
			final Timestamp date,
			final BigDecimal amt)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		// map is used to find currently unsupported cases of two sponsors
		// having the same bPartner
		final Map<Integer, Integer> bPartnerId2SponsorId = new HashMap<Integer, Integer>();

		for (final I_C_Sponsor sponsor : sponsor2RankChange.keySet())
		{
			final List<I_C_AdvCommissionSalaryGroup> oldAndNewRank = sponsor2RankChange.get(sponsor);
			if (oldAndNewRank.isEmpty())
			{
				continue;
			}
			Check.assume(oldAndNewRank.size() == 2, "");

			final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
			final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

			final I_C_BPartner bPartner = sponsorBL.retrieveSalesRepAt(ctx, date, sponsor, false, trxName);

			if (bPartner == null)
			{
				// nothing to do
				continue;
			}

			final Integer existingSponsorId = bPartnerId2SponsorId.put(bPartner.getC_BPartner_ID(), sponsor.getC_Sponsor_ID());
			if (existingSponsorId != null)
			{
				throw CommissionException.inconsistentConfig(
						"BPartner " + bPartner.getValue() + " has more than one sponsor", bPartner);
			}

			final I_M_DiscountSchema oldDs = retrieveDiscountSchema(oldAndNewRank.get(0), sponsor, date);
			final int oldDsId = oldDs == null ? 0 : oldDs.getM_DiscountSchema_ID();

			final I_M_DiscountSchema newDs = retrieveDiscountSchema(oldAndNewRank.get(1), sponsor, date);
			final int newDsId = newDs == null ? 0 : newDs.getM_DiscountSchema_ID();

			if (newDsId != oldDsId)
			{
				UpdateSalesRepFacts.logger.info("M_DiscountSchema_ID changes " + oldDsId + " => " + newDsId + " for " + bPartner);
				bPartner.setM_DiscountSchema_ID(newDsId);
				InterfaceWrapperHelper.save(bPartner);
			}
		}
	}

	List<I_C_Sponsor> updateADV(
			final I_C_Sponsor salesRep,
			final String status,
			final I_C_AdvCommissionFact volumeOfSalesFact)
	{
		final I_C_AdvComRankCollection comRankCollection = getComSystemType().getC_AdvComSystem().getC_AdvComRankCollection();

		final Properties ctx = InterfaceWrapperHelper.getCtx(salesRep);
		final String trxName = InterfaceWrapperHelper.getTrxName(salesRep);
		final I_C_AdvCommissionSalaryGroup lowestGroup = Services.get(ICommissionRankDAO.class).retrieveLowest(ctx, comRankCollection, trxName);

		return distributeEDL(salesRep, status, volumeOfSalesFact, lowestGroup.getValue(), null);
	}

	protected List<I_C_Sponsor> distributeEDL(
			final I_C_Sponsor salesRep,
			final String status,
			final I_C_AdvCommissionFact volumeOfSalesFact,
			final String minimum,
			final String maxExcl)
	{
		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final Timestamp date = volumeOfSalesFact.getDateDoc();

		final List<I_C_Sponsor> result = new ArrayList<I_C_Sponsor>();

		final int[] level = { 0 };

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		final IContractBL contractBL = Services.get(IContractBL.class);
		final int maxLevel = (Integer)contractBL.retrieveInstanceParam(
				InterfaceWrapperHelper.getCtx(salesRep),
				comSystemType, ConfigParams.PARAM_ADV_MAX_LEVEL,
				InterfaceWrapperHelper.getTrxName(salesRep));

		new HierarchyAscender()
		{
			@Override
			public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
					final int logicalLevel,
					final int hierarchyLevel,
					final Map<String, Object> contextInfo)
			{
				if (!srfBL.isRankGE(comSystem, minimum, sponsorCurrentLevel, status, date))
				{
					return Result.SKIP_IGNORE;
				}

				if (level[0] == 0)
				{
					level[0] += 1;
					return Result.GO_ON;
				}

				if (maxExcl != null && srfBL.isRankGE(comSystem, maxExcl, sponsorCurrentLevel, status, date))
				{
					// sponsorCurrentLevel's rank is too high. Don't give it any
					// points (it might fore example have received already APV points for this fact).

					// Count this level
					level[0] += 1;
					return Result.GO_ON;
				}

				final I_C_Period period = retrievePeriod(salesRep, date);

				srfBL.addAmtToSrf(sponsorCurrentLevel, comSystem, X_C_AdvComSalesRepFact.NAME_6EDL, status, period, date, volumeOfSalesFact);
				result.add(sponsorCurrentLevel);

				level[0] += 1;
				return Result.GO_ON;
			}
		}
				.setDate(date)
				.climb(salesRep, maxLevel);

		return result;
	}

	Map<I_C_Sponsor, List<I_C_AdvCommissionSalaryGroup>> updateRanks(
			final ICommissionContext commissionCtx,
			final Set<I_C_Sponsor> sponsors,
			final String[] srfStatus,
			final BigDecimal amt)
	{
		final Timestamp date = commissionCtx.getDate();
		final I_M_Product product = commissionCtx.getM_Product();

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final Map<I_C_Sponsor, List<I_C_AdvCommissionSalaryGroup>> result = new HashMap<I_C_Sponsor, List<I_C_AdvCommissionSalaryGroup>>();

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		for (final I_C_Sponsor sponsor : sponsors)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
			final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

			final I_C_Period period = sponsorBL.retrieveCommissionPeriod(sponsor, date);

			final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, comSystem, product);

			BigDecimal sum = BigDecimal.ZERO;
			for (final String factName : getTurnoverFactNames(commissionCtx2))
			{
				sum = sum.add(srfBL.retrieveSum(sponsor, factName, period, srfStatus));
			}

			I_C_AdvCommissionSalaryGroup newRank = retrieveSalaryGroup(commissionCtx2, sum, srfStatus[0]);
			int newRankId = newRank == null ? 0 : newRank.getC_AdvCommissionSalaryGroup_ID();

			// using srfBL because we are not interested in manual rank.
			final I_C_AdvCommissionSalaryGroup oldRank = srfBL.retrieveSalaryGroup(ctx, sponsor, comSystem, date, srfStatus[0], trxName);

			final int oldRankId = oldRank == null ? 0 : oldRank.getC_AdvCommissionSalaryGroup_ID();

			if (newRank.getSeqNo() < oldRank.getSeqNo())
			{
				final I_C_AdvCommissionCondition contract = sponsorBL.retrieveContract(ctx, sponsor, comSystem, date, trxName);

				final int periodsLookBack = getPeriodsLookBack(ctx, contract, trxName);

				final Map.Entry<I_C_AdvComSalesRepFact, I_C_AdvCommissionSalaryGroup> bestOfLastYear =
						srfBL.retrieveBestRank(sponsor, comSystem, date, srfStatus[0], periodsLookBack - 1, true);

				if (bestOfLastYear != null && bestOfLastYear.getValue().getSeqNo() > newRank.getSeqNo())
				{
					newRank = bestOfLastYear.getValue();
					newRankId = newRank == null ? 0 : newRank.getC_AdvCommissionSalaryGroup_ID();
				}
			}

			if (sponsor.getC_AdvComRank_System_ID() != newRankId && X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(srfStatus[0]))
			{
				// make sure that the rank is also set in the sponsor record
				sponsor.setC_AdvComRank_System_ID(newRankId);
				if (!sponsor.isManualRank())
				{
					sponsor.setC_AdvCommissionSalaryGroup_ID(newRankId);
				}
				InterfaceWrapperHelper.save(sponsor);
			}

			if (newRankId == oldRankId)
			{
				final MCAdvComRankForecast forecast = sponsorBL.retrieveRankForecast(commissionCtx2, false);
				if (forecast != null)
				{
					forecast.saveEx();
				}
				UpdateSalesRepFacts.logger.info("No change for " + sponsor + " (Status=" + srfStatus[0] + "): " + oldRank);
				continue;
			}

			UpdateSalesRepFacts.logger.info("Status " + srfStatus[0] + ": Rank changes " + oldRank.getValue() + " to " + newRank.getValue() + " for " + sponsor);

			final I_C_AdvComSalesRepFact rankSrf = srfBL.createOrUpdateRank(sponsor, comSystem, srfStatus[0], period.getC_Period_ID(), date, newRankId);

			srfBL.copyFactReferences(rankSrf, period, //
					new String[] { X_C_AdvComSalesRepFact.NAME_APV }, //
					srfStatus);

			final I_C_AdvComRankForecast forecast = sponsorBL.retrieveRankForecast(commissionCtx2, true);
			if (forecast != null)
			{
				InterfaceWrapperHelper.save(forecast);
			}

			final List<I_C_AdvCommissionSalaryGroup> oldAndNewRank = new ArrayList<I_C_AdvCommissionSalaryGroup>();
			oldAndNewRank.add(oldRank);
			oldAndNewRank.add(newRank);

			result.put(sponsor, oldAndNewRank);
		}
		return result;
	}

	public int getTypeId()
	{
		return typeId;
	}

	public void setTypeId(final int typeId)
	{
		this.typeId = typeId;
	}

	@Override
	public BigDecimal getCommissionPointsSum(final IAdvComInstance inst, final String status, final Timestamp date, final Object po)
	{
		// not applicable
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getFactor()
	{
		// not applicable
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getPercent(final IAdvComInstance inst, final String status, final Timestamp date)
	{
		// not applicable
		return BigDecimal.ZERO;
	}

	/**
	 * Compares the sponsor's C_AdvCommissionSalaryGroup_ID (i.e. the "actual" SG as opposed to the "forecast" SG) to the commission term's PARAM_MIN_APV_FOR_+<ACTUAL_SG> value. If the bPartner's APV
	 * score is below the parameter value, the method returns <code>true</code>.
	 */
	@Override
	public boolean isCompress(final ICommissionContext commissionCtx)
	{
		final Properties ctx = commissionCtx.getCtx();
		final String trxName = commissionCtx.getTrxName();
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();
		final Timestamp date = commissionCtx.getDate();

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final I_C_AdvCommissionSalaryGroup sg = sponsorBL.retrieveRank(ctx, sponsor, getComSystemType().getC_AdvComSystem(), date, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, trxName);

		if (sg == null)
		{
			UpdateSalesRepFacts.logger.debug(sponsor + " has no SG -> compress=false");
			return false;
		}

		final BigDecimal apvActual = getSalesVolume(sponsor, date);

		return isCompress(commissionCtx, sg, apvActual);
	}

	private boolean isCompress(
			final ICommissionContext commissionCtx,
			final I_C_AdvCommissionSalaryGroup sg, final BigDecimal apvActual)
	{

		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();

		final IContractBL contractBL = Services.get(IContractBL.class);

		final String name = ConfigParams.PARAM_MIN_APV_FOR_ + sg.getValue();

		final BigDecimal minAPV = (BigDecimal)contractBL.retrieveSponsorParam(commissionCtx, name);

		UpdateSalesRepFacts.logger.debug("Minimum APV for SG " + sg.getValue() + " is " + minAPV);

		final boolean result;

		if (apvActual.compareTo(minAPV) < 0)
		{
			UpdateSalesRepFacts.logger.debug(sponsor + " has APV=" + apvActual + " -> compress=true");
			result = true;
		}
		else
		{
			UpdateSalesRepFacts.logger.debug(sponsor + " has APV=" + apvActual + " -> compress=false");
			result = false;
		}
		return result;
	}

	private BigDecimal getSalesVolume(final I_C_Sponsor sponsor, final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_Period commissionPeriod = sponsorBL.retrieveCommissionPeriod(sponsor, date);

		final I_C_Sponsor root = sponsorBL.retrieveRoot(ctx, sponsor, commissionPeriod.getEndDate(), trxName);
		Check.assume(root != null, "root=null; sponsor=" + sponsor + "; commissionPeriod=" + commissionPeriod);

		final MCAdvComSystem comSystem = MCAdvComSystem.retrieveForRootSponsor(ctx, root, trxName);
		Check.assume(comSystem != null, "comSystem=null; root=" + root);

		final BigDecimal apvActual = Services.get(ICommissionSalesRepFactDAO.class).retrieveSumAt(sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_APV, commissionPeriod.getC_Period_ID(),
				X_C_AdvComSalesRepFact.STATUS_Prov_Relevant);

		UpdateSalesRepFacts.logger.debug(sponsor + " has APV=" + apvActual + " at date " + date);

		return apvActual;
	}

	@Override
	public IParameterizable getSponsorParams(final Properties ctx, final I_C_AdvCommissionCondition contract, final String trxName)
	{
		final I_C_AdvComRankCollection collection = contract.getC_AdvComSystem().getC_AdvComRankCollection();

		final List<I_C_AdvCommissionSalaryGroup> ranks = Services.get(ICommissionRankDAO.class).retrieveForCollection(ctx, collection, trxName);

		final SponsorParams params = new SponsorParams();
		int count = 20;

		for (final I_C_AdvCommissionSalaryGroup rank : ranks)
		{
			params.addNewParam(ConfigParams.PARAM_6EDL12M_FOR_ + rank.getValue(), "Mindest-Umsatzpunkte fuer die VG " + rank.getName(), "", count, 0);
			params.addNewParam(ConfigParams.PARAM_MIN_APV_FOR_ + rank.getValue(), "Mindest-APV der VG " + rank.getName(), "", count + ranks.size(), BigDecimal.ZERO);

			count += 10;
		}

		return params;
	}

	@Override
	public IParameterizable getInstanceParams(final Properties ctx, final I_C_AdvComSystem system, final String trxName)
	{
		final I_C_AdvComRankCollection collection = system.getC_AdvComRankCollection();

		final List<I_C_AdvCommissionSalaryGroup> ranks = Services.get(ICommissionRankDAO.class).retrieveForCollection(ctx, collection, trxName);

		final BaseConfig params = new BaseConfig();

		params.addNewParam(ConfigParams.PARAM_ADV_MAX_LEVEL, "EDL-Erfassung bis Ebene", "EDL wird fuer die gesetzte Zahl an Ebenen erfasst", 10, 1);

		int count = 20;

		for (final I_C_AdvCommissionSalaryGroup rank : ranks)
		{
			params.addNewParam(ConfigParams.PARAM_DS_OF_SG_ + rank.getValue(), "Rabattschema der VG " + rank.getName(), "", count, "0");
			count += 10;
		}
		return params;
	}

	private static class SponsorParams extends BaseConfig implements IParameterizable
	{
		// private static final String PARAM_PV_ONLY = "PV_Only";
		private SponsorParams()
		{
			//
			// removing this parameter for now: impl is more complex than
			// thought an there are open questions. see
			// "US223:CR- Provisionsanpassung für Frankreich PV statt APV bei Kompression betrachten (2010053110000074)"
			// addNewParam(PARAM_PV_ONLY, "Nur PV (APV ohne KD-Umsatz)", "", 3, false);

			addNewParam(ConfigParams.PARAM_PERIODS_LOOKBACK, "Anz. zu betr. Prov.-Perioden",
					"Anzahl der zu betrachtenden Provisionsperioden zur Ermittlung des besten Umsatz-Wertes",
					5, 12);

			addNewParam(ConfigParams.PARAM_ADV_RANK_RELEVANT, "EDL-Punkte relevant für Rang",
					"Bei Wert 'N' wird des Downline-Volumen nur ausgwiesen. Bei Wert 'Y' fliesst es in die Rang-berechnung mit ein",
					10, "N");
		}
	}

	@Override
	public boolean isCommissionCalculated()
	{
		return false;
	}

	@Override
	@Cached
	public String[] getTurnoverFactNames(final ICommissionContext commissionCtx)
	{
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();
		final Timestamp date = commissionCtx.getDate();
		final I_M_Product product = commissionCtx.getM_Product();

		final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, product);

		final IContractBL contractBL = Services.get(IContractBL.class);

		final String advRankRelevant = (String)contractBL.retrieveSponsorParam(commissionCtx2, ConfigParams.PARAM_ADV_RANK_RELEVANT);

		if ("Y".equals(advRankRelevant))
		{
			return new String[] { X_C_AdvComSalesRepFact.NAME_APV, X_C_AdvComSalesRepFact.NAME_6EDL };
		}
		else
		{
			return new String[] { X_C_AdvComSalesRepFact.NAME_APV };
		}
	}

	@Override
	public int getPeriodsLookBack(final Properties ctx, final I_C_AdvCommissionCondition contract, final String trxName)
	{
		final IParameterizable sponsorParams = getSponsorParams(ctx, contract, trxName);

		final Integer periodsLookBack = (Integer)sponsorParams.getParameter(ConfigParams.PARAM_PERIODS_LOOKBACK).getValue();

		return periodsLookBack;
	}

	@Override
	public void setComSystemType(final I_C_AdvComSystem_Type comSystemType)
	{
		this.comSystemType = comSystemType;
	}

	@Override
	public I_C_AdvComSystem_Type getComSystemType()
	{
		return comSystemType;
	}

}
