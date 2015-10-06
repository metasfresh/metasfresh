package de.metas.commission.service.impl;

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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.model.MYear;
import org.compiere.model.Query;
import org.compiere.util.CLogger;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.custom.type.ISalesRefFactCollector;
import de.metas.commission.model.I_C_AdvComRankCollection;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_AdvCommissionType;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvComFactSalesRepFact;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionRankDAO;
import de.metas.commission.service.ICommissionSalesRepFactDAO;
import de.metas.commission.service.ICommissionTypeBL;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;

public class SalesRepFactBL implements ISalesRepFactBL
{

	private static final CLogger logger = CLogger.getCLogger(SalesRepFactBL.class);

	@Override
	public I_C_AdvCommissionSalaryGroup retrieveSalaryGroup(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final Timestamp date,
			final String statusProvRelevant,
			final String trxName)
	{
		return retrieveSalaryGroup(ctx, sponsor, comSystem, date, statusProvRelevant, true, trxName).getValue();
	}

	private Map.Entry<I_C_AdvComSalesRepFact, I_C_AdvCommissionSalaryGroup> retrieveSalaryGroup(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final Timestamp date,
			final String status,
			final boolean create,
			final String trxName)
	{
		Check.assume(comSystem.getC_AdvComSystem_ID() > 0, "comSystemId may not be null; sponsor=" + sponsor + "; date=" + date);

		final I_C_AdvComSalesRepFact rankFactTmp = Services.get(ICommissionSalesRepFactDAO.class).retrieveLatestAtDate(ctx, sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_VG_Aenderung, date,
				trxName,
				status);

		final I_C_AdvComSalesRepFact rankFact;
		if (rankFactTmp == null && create)
		{
			final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
			final I_C_AdvComRankCollection rankCollection = comSystem.getC_AdvComRankCollection();

			final I_C_AdvCommissionSalaryGroup starterRank = Services.get(ICommissionRankDAO.class).retrieveLowest(ctx, rankCollection, trxName);

			Check.assumeNotNull(starterRank, "starterRank not null for sponsor {0}", sponsor);
			// assert starterRank != null : sponsor;

			final I_C_AdvCommissionCondition contract = sponsorBL.retrieveContract(ctx, sponsor, comSystem, date, trxName);
			assert contract.getC_AdvComSystem_ID() == comSystem.getC_AdvComSystem_ID();

			final I_C_Period period = sponsorBL.retrieveCommissionPeriod(ctx, contract, date, trxName);
			final Timestamp starterRankDate = period.getStartDate();

			rankFact = Services.get(ICommissionSalesRepFactDAO.class).createDontSave(
					ctx, sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_VG_Aenderung, status, starterRankDate, period.getC_Period_ID(), trxName);
			assert rankFact.getAD_Client_ID() == sponsor.getAD_Client_ID();
			assert rankFact.getAD_Org_ID() == sponsor.getAD_Org_ID();

			rankFact.setC_AdvCommissionSalaryGroup_ID(starterRank.getC_AdvCommissionSalaryGroup_ID());
			InterfaceWrapperHelper.save(rankFact);

			SalesRepFactBL.logger.info("Latest rank of " + sponsor + " at date=" + date + " was NULL. Added fallback rank=" + starterRank + " with date " + starterRankDate);
		}
		else
		{
			rankFact = rankFactTmp;
		}

		if (rankFact == null)
		{
			return null;
		}

		final I_C_AdvCommissionSalaryGroup sg = InterfaceWrapperHelper.create(
				ctx,
				rankFact.getC_AdvCommissionSalaryGroup_ID(),
				I_C_AdvCommissionSalaryGroup.class,
				trxName);

		SalesRepFactBL.logger.fine("At " + date + " and with status " + status + ", " + sponsor + " is in SG: " + sg);

		return new Tuple<I_C_AdvComSalesRepFact, I_C_AdvCommissionSalaryGroup>(rankFact, sg);
	}

	@Override
	public List<I_C_Period> retrieveNextPeriods(final I_C_Sponsor sponsor, final int periodsLookForward, final Timestamp date, final boolean includeCurrentPeriod)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final I_C_Sponsor_SalesRep contractSSR = sponsorBL.retrieveContractSSR(ctx, sponsor, date, trxName);
		if (contractSSR == null)
		{
			SalesRepFactBL.logger.info(sponsor + " has no contract at " + date + "; No periods to return");
			return Collections.emptyList();
		}

		return retrievePeriods(ctx, contractSSR, periodsLookForward, date, includeCurrentPeriod, false, trxName); // includeCurrentPeriod=false
	}

	@Override
	public List<I_C_Period> retrieveLastPeriods(
			final I_C_Sponsor sponsor, final int periodsLookBack, final Timestamp date, final boolean includeCurrentPeriod)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final I_C_Sponsor_SalesRep contractSSR = sponsorBL.retrieveContractSSR(ctx, sponsor, date, trxName);
		if (contractSSR == null)
		{
			SalesRepFactBL.logger.info(sponsor + " has no contract at " + date + "; No periods to return");
			return Collections.emptyList();
		}

		return retrievePeriods(ctx, contractSSR, periodsLookBack, date, includeCurrentPeriod, true, trxName);
	}

	private List<I_C_Period> retrievePeriods(
			final Properties ctx,
			final I_C_Sponsor_SalesRep contractSSR,
			final int periodsLookForward, final Timestamp date,
			final boolean includeCurrentPeriod,
			final boolean retrievePast,
			final String trxName)
	{
		Check.assume(contractSSR != null, "contractSSR may not be null");

		final String wcYear = I_C_Year.COLUMNNAME_C_Calendar_ID + "=?";

		final int paramYear = contractSSR.getC_AdvCommissionCondition().getC_Calendar_ID();

		final String orderByYear = I_C_Year.COLUMNNAME_FiscalYear + " DESC";

		final List<MYear> years = new Query(ctx, I_C_Year.Table_Name, wcYear, trxName)
				.setParameters(paramYear)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(orderByYear)
				.list();

		Check.assume(!years.isEmpty(), "");

		final int resultSize = includeCurrentPeriod ? periodsLookForward + 1 : periodsLookForward;

		final List<I_C_Period> periods = new ArrayList<I_C_Period>(resultSize);

		boolean beforeValidFrom = false;
		for (final MYear year : years)
		{
			final String wcPeriods = I_C_Period.COLUMNNAME_C_Year_ID + "=?";

			final int paraI_C_Periods = year.get_ID();

			final String orderByPeriods = I_C_Period.COLUMNNAME_StartDate + " DESC";

			final List<I_C_Period> periodsOfYear =
					new Query(ctx, I_C_Period.Table_Name, wcPeriods, trxName)
							.setParameters(paraI_C_Periods)
							.setOnlyActiveRecords(true)
							.setOrderBy(orderByPeriods)
							.list(I_C_Period.class);

			for (final I_C_Period period : periodsOfYear)
			{
				if (period.getEndDate().before(contractSSR.getValidFrom()))
				{
					SalesRepFactBL.logger.fine(period + " ends before " + contractSSR + " is valid");
					beforeValidFrom = true;
					break;
				}
				if (period.getStartDate().after(contractSSR.getValidTo()))
				{
					continue;
				}
				// value of 'pastPeriod' tells if the current period is is the future or in the past of 'date'
				final boolean dateInPeriod =
						(period.getStartDate().before(date) || period.getStartDate().equals(date)) &&
								(period.getEndDate().after(date) || period.getEndDate().equals(date));

				final boolean dateAfterPeriod = period.getEndDate().after(date);
				final boolean dateBeforePeriod = !dateInPeriod && !dateAfterPeriod;

				boolean add;
				if (dateInPeriod)
				{
					add = includeCurrentPeriod;
				}
				else if (dateBeforePeriod)
				{
					add = retrievePast;
				}
				else
				{
					add = !retrievePast;
				}

				if (add)
				{
					periods.add(period);
					if (periods.size() >= resultSize)
					{
						break;
					}
				}
			}

			if (periods.size() == resultSize)
			{
				break;
			}
			if (beforeValidFrom)
			{
				break;
			}
		}
		return periods;
	}

	@Override
	public boolean isChangeToRank(
			final I_C_AdvComRankCollection rankCollection,
			final I_C_AdvComSystem comSystem,
			final String rankValue,
			final I_C_Sponsor sponsor, final String status, final int periodId,
			final boolean ge)
	{
		final List<I_C_AdvComSalesRepFact> facts = Services.get(ICommissionSalesRepFactDAO.class)
				.retrieveFactsAt(sponsor, comSystem,
						X_C_AdvComSalesRepFact.NAME_VG_Aenderung, periodId,
						X_C_AdvComSalesRepFact.STATUS_Prov_Relevant);
		if (facts.isEmpty())
		{
			return false;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final I_C_AdvCommissionSalaryGroup mostRecentRank = facts.get(facts.size() - 1).getC_AdvCommissionSalaryGroup();

		final List<I_C_AdvCommissionSalaryGroup> sgsToLookfor;

		final List<I_C_AdvCommissionSalaryGroup> sgsTmp = Services.get(ICommissionRankDAO.class).retrieveGroupAndBetter(ctx, rankCollection, rankValue, trxName);

		if (ge)
		{
			sgsToLookfor = sgsTmp;
		}
		else
		{
			final String lowestValue = Services.get(ICommissionRankDAO.class).retrieveLowest(ctx, rankCollection, trxName).getValue();

			sgsToLookfor = Services.get(ICommissionRankDAO.class).retrieveGroupAndBetter(
					ctx, rankCollection, lowestValue,
					trxName);
			sgsToLookfor.removeAll(sgsTmp);
		}

		final boolean isInSG = isInSGToLookFor(sgsToLookfor, mostRecentRank.getC_AdvCommissionSalaryGroup_ID());

		return isInSG;
	}

	/**
	 * Note: method may not be cached unless we can be sure that the sponsor's rank for a given date can't be different on two invocations with the same parameters.
	 */
	@Override
	public boolean isRankGE(
			final I_C_AdvComSystem comSystem,
			final String rankValue,
			final I_C_Sponsor sponsor,
			final String status,
			final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		Check.assume(rankValue != null, "Param 'rankValue' is not null");

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_AdvCommissionSalaryGroup sg = sponsorBL.retrieveRank(ctx, sponsor, comSystem, date, status, trxName);

		Check.assume(sg.getC_AdvCommissionSalaryGroup_ID() > 0, "");

		final List<I_C_AdvCommissionSalaryGroup> sgsToLookfor = Services.get(ICommissionRankDAO.class).retrieveGroupAndBetter(ctx,
				comSystem.getC_AdvComRankCollection(),
				rankValue,
				trxName);

		final boolean isInSG = isInSGToLookFor(sgsToLookfor, sg.getC_AdvCommissionSalaryGroup_ID());

		SalesRepFactBL.logger.fine(
				"At date " + date + " BPartner is in SG " + rankValue + " or better with status " + status + ": " + isInSG);

		return isInSG;
	}

	@Override
	public boolean isInSGToLookFor(
			final List<I_C_AdvCommissionSalaryGroup> sgsToLookfor,
			final int salaryGroupId)
	{
		if (sgsToLookfor == null)
		{
			return true;
		}

		for (final I_C_AdvCommissionSalaryGroup sgtoLookFor : sgsToLookfor)
		{
			if (sgtoLookFor.getC_AdvCommissionSalaryGroup_ID() == salaryGroupId)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public I_C_AdvComSalesRepFact addAmtToSrf(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String factName,
			final String srfstatus,
			final I_C_Period period,
			final Timestamp date,
			final I_C_AdvCommissionFact volumeOfSalesFact)
	{

		final BigDecimal amt = volumeOfSalesFact.getCommissionPointsSum();

		return addOrSubtractAmt(sponsor, comSystem, factName, srfstatus, period, date, volumeOfSalesFact, amt);
	}

	private I_C_AdvComSalesRepFact addOrSubtractAmt(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String factName, final String srfStatus,
			final I_C_Period period, final Timestamp date,
			final I_C_AdvCommissionFact volumeOfSalesFact, final BigDecimal amt)
	{
		Check.assume(!X_C_AdvComSalesRepFact.NAME_DynKompression.equals(factName), "Param factName!=" + X_C_AdvComSalesRepFact.NAME_DynKompression);
		Check.assume(!X_C_AdvComSalesRepFact.NAME_VG_Aenderung.equals(factName), "Param factName!=" + X_C_AdvComSalesRepFact.NAME_VG_Aenderung);

		I_C_AdvComSalesRepFact salesRepFact = Services.get(ICommissionSalesRepFactDAO.class).retrieveForPeriod(sponsor, comSystem, factName, srfStatus, period.getC_Period_ID());

		if (salesRepFact == null)
		{
			salesRepFact = Services.get(ICommissionSalesRepFactDAO.class).createDontSave(sponsor, comSystem, factName, srfStatus, date, period.getC_Period_ID());

			salesRepFact.setValueNumber(amt);
			salesRepFact.setDateAcct(date);
			InterfaceWrapperHelper.save(salesRepFact);

			SalesRepFactBL.logger.fine("Created new srf " + salesRepFact);
		}
		else
		{
			SalesRepFactBL.logger.fine("Using existing srf " + salesRepFact);

			final MCAdvComFactSalesRepFact cf2srf = MCAdvComFactSalesRepFact.retrieve(volumeOfSalesFact, salesRepFact);
			if (cf2srf != null)
			{
				SalesRepFactBL.logger.fine("Commission fact's amount has already been added");
				return salesRepFact;
			}

			Check.assume(srfConsistent(salesRepFact), salesRepFact + " is consistent");

			salesRepFact.setValueNumber(salesRepFact.getValueNumber().add(amt));
			salesRepFact.setDateAcct(date);
			InterfaceWrapperHelper.save(salesRepFact);
		}

		final MCAdvComFactSalesRepFact cf_srf = MCAdvComFactSalesRepFact.create(volumeOfSalesFact, salesRepFact);

		Check.assume(srfConsistent(salesRepFact), salesRepFact + " and " + cf_srf + " is consistent");

		return salesRepFact;
	}

	@Override
	public I_C_AdvComSalesRepFact subtractAmtFromSrf(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String factName, final String srfStatus,
			final I_C_Period period, final Timestamp date,
			final I_C_AdvCommissionFact volumeOfSalesFact)
	{

		final I_C_AdvComSalesRepFact salesRepFact =
				Services.get(ICommissionSalesRepFactDAO.class).retrieveForPeriod(sponsor, comSystem, factName, srfStatus, period.getC_Period_ID());

		if (salesRepFact == null)
		{
			return null;
		}

		final MCAdvComFactSalesRepFact cf2srf = MCAdvComFactSalesRepFact
				.retrieve(volumeOfSalesFact, salesRepFact);

		if (cf2srf == null)
		{
			return null;
		}

		assert srfConsistent(salesRepFact) : salesRepFact;

		cf2srf.deleteEx(false);

		salesRepFact.setValueNumber(salesRepFact.getValueNumber().subtract(
				volumeOfSalesFact.getCommissionPointsSum()));
		InterfaceWrapperHelper.save(salesRepFact);

		assert srfConsistent(salesRepFact) : salesRepFact;

		return salesRepFact;
	}

	@Override
	public void resetFact(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String factName,
			final String status,
			final I_C_Period period)
	{
		assert !X_C_AdvComSalesRepFact.NAME_DynKompression.equals(factName);
		assert !X_C_AdvComSalesRepFact.NAME_VG_Aenderung.equals(factName);

		final I_C_AdvComSalesRepFact fact = Services.get(ICommissionSalesRepFactDAO.class)
				.retrieveForPeriod(sponsor, comSystem, factName, status, period.getC_Period_ID());

		if (fact != null)
		{
			assert srfConsistent(fact) : fact;

			fact.setValueNumber(BigDecimal.ZERO);
			InterfaceWrapperHelper.save(fact);

			MCAdvComFactSalesRepFact.deleteFor(fact);

			assert srfConsistent(fact) : fact;
		}
	}

	@Override
	public BigDecimal retrieveSumUntilComFact(
			final I_C_AdvComSalesRepFact salesRepFact,
			final I_C_AdvCommissionFact comFact, final I_C_Period period,
			final String comFactStatus)
	{

		final StringBuilder where = new StringBuilder();

		where.append(" C_AdvCommissionFact_ID IN ( ");
		where.append("   select C_AdvCommissionFact_ID ");
		where.append("   from C_AdvComFact_SalesRepFact ");
		where.append("   where C_AdvComSalesRepFact_ID=? ");
		where.append(" ) ");
		where.append(" AND C_AdvCommissionFact_ID<? ");
		where.append(" AND DateDoc>=? ");
		where.append(" AND DateDoc<=? ");
		where.append(" AND Status=? ");

		final Object[] params = {
				salesRepFact.getC_AdvComSalesRepFact_ID(),
				comFact.getC_AdvCommissionFact_ID(),
				period.getStartDate(),
				period.getEndDate(),
				comFactStatus };

		final String sumExpr = I_C_AdvCommissionFact.COLUMNNAME_CommissionPointsSum;

		final Properties ctx = InterfaceWrapperHelper.getCtx(salesRepFact);
		final String trxName = InterfaceWrapperHelper.getTrxName(salesRepFact);

		return new Query(ctx, I_C_AdvCommissionFact.Table_Name, where.toString(), trxName)
				.setParameters(params)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.sum(sumExpr);
	}

	@Override
	public I_C_AdvComSalesRepFact createOrUpdateRank(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String srfStatus,
			final int periodId,
			final Timestamp date,
			final int newRankId)
	{

		// record every rank change for debugging
		// I_C_AdvComSalesRepFact fact = null;

		// we only have one rank-srf per day. Otherwise, there might be many
		// records with the ranks just switching back and forth, when minus
		// comFacts are followed by plus comFacts, e.g. when an invoice line
		// is added to an order line.

		// TODO Breakpoint newRankId==1000021 "Direktionskaufmann AEB"
		I_C_AdvComSalesRepFact fact =
				Services.get(ICommissionSalesRepFactDAO.class).retrieveForDate(
						sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_VG_Aenderung, srfStatus, date);

		if (fact == null)
		{
			fact = Services.get(ICommissionSalesRepFactDAO.class).createDontSave(
					sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_VG_Aenderung, srfStatus, date, periodId);
		}

		Check.assume(periodId == fact.getC_Period_ID(),
				"C_Period_ID of " + fact + " equals param 'periodId'=" + periodId);

		fact.setC_AdvCommissionSalaryGroup_ID(newRankId);
		InterfaceWrapperHelper.save(fact);

		return fact;
	}

	private boolean srfConsistent(final I_C_AdvComSalesRepFact srf)
	{
		final BigDecimal valueOrNull = InterfaceWrapperHelper.getValueOrNull(srf, I_C_AdvComSalesRepFact.COLUMNNAME_ValueNumber);

		if (valueOrNull != null)
		{
			final BigDecimal factSum = MCAdvCommissionFact.retrieveSum(srf);

			final boolean sumMatches = valueOrNull.compareTo(factSum) == 0;

			if (!sumMatches)
			{
				SalesRepFactBL.logger.warning(srf + ": ValuesNumber=" + valueOrNull + " != CommissionFactsSum= " + factSum);
			}
			return sumMatches;
		}
		return true;
	}

	@Override
	public Map.Entry<I_C_AdvComSalesRepFact, I_C_AdvCommissionSalaryGroup> retrieveBestRank(
			final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final Timestamp date, final String status, final int periods, final boolean returnFirst)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		Map.Entry<I_C_AdvComSalesRepFact, I_C_AdvCommissionSalaryGroup> bestSrfAndRank = null;

		for (final I_C_Period period : retrieveLastPeriods(sponsor, periods, date, false))
		{
			final Map.Entry<I_C_AdvComSalesRepFact, I_C_AdvCommissionSalaryGroup> srfAndRank =
					retrieveSalaryGroup(ctx, sponsor, comSystem, period.getEndDate(), status, false, trxName); // create=false

			if (srfAndRank == null)
			{
				continue;
			}
			if (bestSrfAndRank == null)
			{
				bestSrfAndRank = srfAndRank;
			}
			else if (returnFirst && bestSrfAndRank.getValue().getSeqNo() < srfAndRank.getValue().getSeqNo())
			{
				bestSrfAndRank = srfAndRank;
			}
			else if (bestSrfAndRank.getValue().getSeqNo() <= srfAndRank.getValue().getSeqNo())
			{
				bestSrfAndRank = srfAndRank;
			}
		}
		return bestSrfAndRank;
	}

	/**
	 * Creates references (MCAdvComFactSalesRepFact) between the given <code>salesRepFact</code> and the commission facts that belong to the same sponsor and have the given <code>srfNames</code> and
	 * <code>srfStatus</code>.
	 */
	@Override
	public void copyFactReferences(
			final I_C_AdvComSalesRepFact salesRepFact,
			final I_C_Period period, final String[] srfNames,
			final String[] srfStatus)
	{

		assert salesRepFact.getC_AdvCommissionSalaryGroup_ID() > 0;

		MCAdvComFactSalesRepFact.deleteFor(salesRepFact);

		final I_C_Sponsor sponsor = salesRepFact.getC_Sponsor();

		for (final String srfName : srfNames)
		{
			for (final I_C_AdvComSalesRepFact currentSrf : retrieveFacts(sponsor, salesRepFact.getC_AdvComSystem(), period, srfName, srfStatus))
			{
				for (final I_C_AdvCommissionFact comFact : MCAdvCommissionFact.retrieveFacts(currentSrf))
				{
					MCAdvComFactSalesRepFact.createIfNotExists(comFact, salesRepFact);
				}
			}
		}
		assert srfConsistent(salesRepFact);
	}

	@Override
	public List<I_C_AdvComSalesRepFact> retrieveFacts(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final I_C_Period period, final String srfName,
			final String... srfStatus)
	{
		final List<I_C_AdvComSalesRepFact> salesrepFacts = new ArrayList<I_C_AdvComSalesRepFact>();

		for (final String status : srfStatus)
		{
			if (X_C_AdvComSalesRepFact.STATUS_Prognose.equals(status))
			{
				salesrepFacts.addAll(Services.get(ICommissionSalesRepFactDAO.class).retrieveFactsUntil(sponsor, comSystem, srfName, period, true, status));
			}
			else
			{
				salesrepFacts.addAll(Services.get(ICommissionSalesRepFactDAO.class).retrieveFactsAt(sponsor, comSystem, srfName, period.getC_Period_ID(), status));
			}
		}
		return salesrepFacts;
	}

	@Override
	public BigDecimal retrieveSum(
			final I_C_Sponsor sponsor,
			final String srfName,
			final I_C_Period period,
			final String... srfStatus)
	{
		assert sponsor != null : "sponsor may not me null";

		BigDecimal sum = BigDecimal.ZERO;

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_Sponsor_SalesRep ssr = sponsorBL.retrieveContractSSR(ctx, sponsor, period.getEndDate(), trxName);
		if (ssr == null)
		{
			return BigDecimal.ZERO;
		}

		for (final String status : srfStatus)
		{
			if (X_C_AdvComSalesRepFact.STATUS_Prognose.equals(status))
			{
				sum = sum.add(Services.get(ICommissionSalesRepFactDAO.class).retrieveSumUntil(sponsor, ssr.getC_AdvComSystem(), srfName, period, status));
			}
			if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(status))
			{
				sum = sum.add(Services.get(ICommissionSalesRepFactDAO.class).retrieveSumAt(sponsor, ssr.getC_AdvComSystem(), srfName, period.getC_Period_ID(), status));
			}
		}
		return sum;
	}

	/**
	 * Creates or updates a <code>C_AdvComSalesRepFact</code> record with name {@link X_C_AdvComSalesRepFact#NAME_DynKompression}, status {@link X_C_AdvComSalesRepFact#STATUS_Prov_Relevant} and the
	 * given parameters.
	 * 
	 * @param sponsor
	 * @param periodId
	 * @param date
	 * @param isCompress if true the records <code>ValueStr</code> field is set to 'Y'. Otherwise it is set to 'N'.
	 * @return <code>true</code> if a new record was created or if an existing record's <code>ValueStr</code> was changed.
	 */
	private boolean createOrUpdateIsCompress(final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final int periodId, final Timestamp date, final boolean isCompress)
	{

		final String name = X_C_AdvComSalesRepFact.NAME_DynKompression;
		final String status = X_C_AdvComSalesRepFact.STATUS_Prov_Relevant;

		I_C_AdvComSalesRepFact fact = Services.get(ICommissionSalesRepFactDAO.class).retrieveForDate(sponsor, comSystem, name, status, date);

		final boolean needtoCreateNewRecord = fact == null;
		if (needtoCreateNewRecord)
		{
			fact = Services.get(ICommissionSalesRepFactDAO.class).createDontSave(sponsor, comSystem, name, status, date, periodId);
		}

		assert periodId == fact.getC_Period_ID();

		final String newValueStr = isCompress ? "Y" : "N";
		final boolean change = !newValueStr.equals(fact.getValueStr());

		if (change)
		{
			fact.setValueStr(newValueStr);
			InterfaceWrapperHelper.save(fact);
		}
		return change;
	}

	/**
	 * Finds out (given sponsor, date and commission type), if the sponsor is compressed right now. Makes sure that the result is properly recorded in the sales rep facts.
	 */
	@Override
	public boolean retrieveIsCompress(final ICommissionContext commissionCtxParam
			, final I_C_Period period
			, final boolean evalOnly)
	{
		final ICommissionContext commissionCtx = commissionCtxParam.setDate(period.getEndDate());

		final ISalesRefFactCollector srfCollectorType = retrieveSrfCollector(commissionCtx);
		if (srfCollectorType == null)
		{
			return false;
		}
		
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();

		final boolean compressEval = srfCollectorType.isCompress(commissionCtx);

		if (evalOnly)
		{
			return compressEval;
		}

		final I_C_AdvComSystem comSystem = commissionCtx.getC_AdvComSystem();
		final I_C_AdvComSalesRepFact compressFact = Services.get(ICommissionSalesRepFactDAO.class).retrieveForPeriod(
				sponsor,
				comSystem,
				X_C_AdvComSalesRepFact.NAME_DynKompression,
				X_C_AdvComSalesRepFact.STATUS_Prov_Relevant,
				period.getC_Period_ID());

		final boolean update;

		if (compressFact == null)
		{
			// need to create a new fact
			update = true;
			createOrUpdateIsCompress(sponsor, comSystem, period.getC_Period_ID(), period.getEndDate(), compressEval);
		}
		else
		{
			final boolean compressRecord = "Y".equals(compressFact.getValueStr());

			if (compressRecord != compressEval)
			{
				// need to update the existing fact or create a new one
				update = true;

				createOrUpdateIsCompress(sponsor, comSystem, period.getC_Period_ID(), period.getEndDate(), compressEval);
			}
			else
			{
				update = false;
			}
		}
		// TODO: check if these updates are really necessary. If they are,
		// consider making some improvements to
		// de.metas.commission.custom.type.UpdateSalesRepFacts instead
		if (update)
		{
			final ICommissionFactBL comFactBL = Services.get(ICommissionFactBL.class);

			comFactBL.updateLevelCalculation(srfCollectorType, period.getEndDate(), sponsor, period);
		}
		return compressEval;
	}

	private ISalesRefFactCollector retrieveSrfCollector(final ICommissionContext commissionCtx)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final I_C_AdvCommissionTerm term = sponsorBL.retrieveTerm(commissionCtx, false);
		if (term == null)
		{
			return null;
		}
		if (term.isSalesRepFactCollector())
		{
			final ICommissionType type = commissionCtx.getCommissionType();
			return (ISalesRefFactCollector)type;
		}
		else
		{
			final I_C_AdvComSystem_Type comSystemType = term.getC_AdvComTermSRFCollector().getC_AdvComSystem_Type();

			final I_C_AdvCommissionType srfCollector = comSystemType.getC_AdvCommissionType();

			return (ISalesRefFactCollector)Services.get(ICommissionTypeBL.class).getBusinessLogic(srfCollector, comSystemType);
		}
	}

	@Override
	public List<Entry<I_C_Period, I_C_AdvComSalesRepFact>> retrieveLastPerPeriod(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final int periodsLookBack,
			final Timestamp date,
			final String factName, final String srfStatus)
	{
		final List<Entry<I_C_Period, I_C_AdvComSalesRepFact>> result = new ArrayList<Entry<I_C_Period, I_C_AdvComSalesRepFact>>();

		for (final I_C_Period period : retrieveLastPeriods(sponsor, periodsLookBack, date, true))
		{
			final List<I_C_AdvComSalesRepFact> srfsForPeriod = Services.get(ICommissionSalesRepFactDAO.class).retrieveFactsAt(sponsor, comSystem, factName, period.getC_Period_ID(), srfStatus);

			if (srfsForPeriod.isEmpty())
			{
				result.add(new Tuple<I_C_Period, I_C_AdvComSalesRepFact>(period, null));
			}
			else
			{
				final I_C_AdvComSalesRepFact lastSrf = srfsForPeriod.get(srfsForPeriod.size() - 1);
				result.add(new Tuple<I_C_Period, I_C_AdvComSalesRepFact>(period, lastSrf));
			}
		}
		return result;
	}

	public class Tuple<K, V> implements Map.Entry<K, V>
	{
		final private K key;

		private V value;

		public Tuple(final K key, final V value)
		{
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey()
		{
			return key;
		}

		@Override
		public V getValue()
		{
			return value;
		}

		@Override
		public V setValue(final V value)
		{
			final V oldVal = this.value;
			this.value = value;
			return oldVal;
		}

		@Override
		public String toString()
		{
			return "(" + key.toString() + " , " + value.toString() + ")";
		}
	}

}