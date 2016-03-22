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
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.commission.model.I_C_AdvComRankCollection;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionInstance;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionRankDAO;
import de.metas.commission.service.ICommissionSalesRepFactDAO;
import de.metas.commission.service.IContractBL;
import de.metas.commission.service.IHierarchyBL;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.util.HierarchyAscender;
import de.metas.commission.util.HierarchyDescender;

/**
 * Evaluates volume-of-sales commission facts. Writes APV, ADV and Rank changes to <code>C_AdvComSalesRep</code>.
 * 
 * @author ts
 * 
 */
public class UpdateSalesRepFactsCustomerLegacy extends UpdateSalesRepFacts
{
	public static final String MSG_DISCOUNT_SCHEMA_MISSING_1P = "DiscountSchemaMissing_1P";

	private static final Logger logger = LogManager.getLogger(UpdateSalesRepFactsCustomerLegacy.class);

	// /**
	// * If <code>isApvSponsor</code> is true:
	// * <ul>
	// * <li>
	// * Retrieves the sponsor (actual) APV of sales (which is assumed not to
	// * include the given <code>salesVolFact</code>'s amount) and check if the
	// * compression status would be different with the volume of sales plus the
	// * amount.</li>
	// * </ul>
	// * Otherwise just
	// * <ul>
	// * <li>
	// * Checks if the currently recorded IsCompression state is still the same
	// as
	// * the current evaluation result given the sponsors current rank etc.</li>
	// * </ul>
	// *
	// * @param sponsor
	// * @param salesVolFact
	// * @param date
	// * @param isApvSponsor
	// *
	// * @return
	// */
	// private boolean makesCompressDifference(final MCSponsor sponsor,
	// final MCAdvCommissionFact salesVolFact, final Timestamp date,
	// final boolean isApvSponsor) {
	//
	// final ISalesRepFactBL salesRepFactBL = Services
	// .get(ISalesRepFactBL.class);
	//
	// final I_C_AdvCommissionSalaryGroup sg = salesRepFactBL
	// .retrieveSalaryGroup(sponsor, date, STATUS_Prov_Relevant);
	//
	// assert sg != null : sponsor + " " + date;
	//
	// final BigDecimal apvActual = getSalesVolume(sponsor, salesVolFact, date);
	//
	// final boolean isCompressEvalNew;
	// final boolean isCompressEvalOld;
	//
	// if (isApvSponsor) {
	//
	// final BigDecimal amt = salesVolFact.getCommissionPointsSum();
	//
	// // find out if the apv change makes a difference
	// isCompressEvalNew = isCompress(sponsor, date, sg, apvActual
	// .add(amt));
	// isCompressEvalOld = isCompress(sponsor, date, sg, apvActual);
	//
	// } else {
	// isCompressEvalNew = isCompress(sponsor, date, sg, apvActual);
	// // value is not relevant
	// isCompressEvalOld = isCompressEvalNew;
	// }
	//
	// final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);
	// // only retrieve the compress value, because we want to draw the
	// // conclusions by ourselves.
	// final boolean isCompressRecord = srfBL.retrieveIsCompress(sponsor,
	// date, this, true);
	//
	// if (isCompressEvalNew != isCompressRecord) {
	//
	// return true;
	// }
	//
	// return isCompressEvalNew != isCompressEvalOld;
	// }

	/**
	 * Recompute APV commission points for a sponsor that just turned "big" (1b to 2a).
	 * 
	 * @param sponsor
	 * @param status
	 * @param date
	 * @param minimum
	 */
	private void recomputeAPVForBigSalesrep(final ICommissionContext commissionCtx, final String srfStatus, final String minimum)
	{
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();

		final I_M_Product product = commissionCtx.getM_Product();

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final List<I_C_Period> periodsToUpdate = retrievePeriodsToUpdate(commissionCtx, true);

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		for (final I_C_Period period : periodsToUpdate)
		{
			srfBL.resetFact(sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_APV, srfStatus, period);

			ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, period.getEndDate(), this, product);

			final int salesVolTermId = retrieveMySalesVolTermId(commissionCtx2);

			new HierarchyDescender()
			{
				@Override
				public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
						final int logicalLevel, final int hierarchyLevel,
						final Map<String, Object> contextInfo)
				{
					final boolean countAsAPV;

					if (sponsor.getC_Sponsor_ID() == sponsorCurrentLevel.getC_Sponsor_ID())
					{
						countAsAPV = true;
					}
					else if (!srfBL.isRankGE(comSystem, minimum, sponsorCurrentLevel, srfStatus, period.getEndDate()))
					{
						// sponsorCurrentLevel doesn't have the minimum rank, so
						// his volume is also accounted to sponsor's APV
						countAsAPV = true;
					}
					else
					{
						countAsAPV = false;
					}
					if (countAsAPV)
					{
						// add sponsorCurrentLevel's volume-of-sales commission
						// facts to sponsor's APV. Make sure that the facts we
						// add are subtracted from other sponsors if necessary.
						sumUpVolOfSales(sponsor, X_C_AdvComSalesRepFact.NAME_APV, srfStatus, period, salesVolTermId, sponsorCurrentLevel, true);

						// counting sponsorCurrentLevel's point into our APV
						// means that we don't count it as a logical level. Thus
						// SKIP_IGNORE.
						return Result.SKIP_IGNORE;
					}
					return Result.GO_ON;
				}
			}
					.setDate(period.getEndDate())
					.climb(sponsor, 0);

			if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(srfStatus))
			{
				commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, period.getEndDate(), this, comSystem, product);
				srfBL.retrieveIsCompress(commissionCtx, period, false);
			}
		}
	}

	/**
	 * Computes the given sponsor's <code>6EDL</code> value by summing up the APV sales rep facts of all sponsors with the given <code>minimum</code> rank that are in the following six logical
	 * downline levels. Logical means that only sponsors with the minimum rank count as a level.
	 * 
	 * @param sponsor
	 * @param srfStatus
	 * @param date
	 * @param minimum
	 */
	private void recomputeEDLForBigSalesrep(final ICommissionContext commissionCtx, final String srfStatus, final String minimum)
	{
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();
		final I_M_Product product = commissionCtx.getM_Product();

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		// TODO Warum werden bei Windecker nicht 6EDL-Punkte von Auftrag 66333
		// (29.07.)? abgezogen?
		final List<I_C_Period> periodsToUpdate = retrievePeriodsToUpdate(commissionCtx, true);

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		for (final I_C_Period period : periodsToUpdate)
		{
			srfBL.resetFact(sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_6EDL, srfStatus, period);

			final int level[] = { 0 };

			new HierarchyDescender()
			{
				@Override
				public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
						final int logicalLevel, final int hierarchyLevel,
						final Map<String, Object> contextInfo)
				{
					if (!srfBL.isRankGE(comSystem, minimum, sponsorCurrentLevel, srfStatus, period.getEndDate()))
					{
						return Result.SKIP_IGNORE;
					}

					if (level[0] == 0)
					{
						level[0] += 1;
						return Result.GO_ON;
					}

					//
					// add the volume-of-sales comFact that make up
					// currentSponsor's
					// APV to sponsor's 6EDL.
					final List<I_C_AdvComSalesRepFact> apvFacts = srfBL.retrieveFacts(sponsorCurrentLevel, comSystem, period, X_C_AdvComSalesRepFact.NAME_APV, srfStatus);

					for (final I_C_AdvComSalesRepFact currentAvpFact : apvFacts)
					{
						for (final MCAdvCommissionFact currentComFact : MCAdvCommissionFact.retrieveFacts(currentAvpFact))
						{
							srfBL.addAmtToSrf(sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_6EDL, srfStatus, period, currentComFact.getDateDoc(), currentComFact);
						}
					}
					level[0] += 1;
					return Result.GO_ON;
				}
			}
					.setDate(period.getEndDate())
					.climb(sponsor, 6);

			if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(srfStatus))
			{
				final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, period.getEndDate(), this, comSystem, product);
				srfBL.retrieveIsCompress(commissionCtx2, period, false);
			}
		}
	}

	private void recomputeAPVForSmallSalesrep(final ICommissionContext commissionCtx,
			final String srfStatus)
	{

		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();

		final I_M_Product product = commissionCtx.getM_Product();

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final List<I_C_Period> periodsToUpdate = retrievePeriodsToUpdate(commissionCtx, false);

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		for (final I_C_Period period : periodsToUpdate)
		{
			srfBL.resetFact(sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_APV, srfStatus, period);

			ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, period.getEndDate(), this, product);

			final int salesVolTermId = retrieveMySalesVolTermId(commissionCtx2);

			//
			// get the next big sales rep in the upline
			final IHierarchyBL hierarchyBL = Services.get(IHierarchyBL.class);
			final List<I_C_Sponsor> sponsors = hierarchyBL.findSponsorsInUpline(
					period.getEndDate(),
					sponsor, Integer.MAX_VALUE, 1,
					comSystem,
					ConfigParams.SG2A,
					srfStatus);
			assert sponsors.size() <= 1;

			final I_C_Sponsor nextBigSalesRep = sponsors.isEmpty() ? null : sponsors.get(0);

			new HierarchyDescender()
			{
				@Override
				public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
						final int logicalLevel, final int hierarchyLevel,
						final Map<String, Object> contextInfo)
				{
					final boolean countAsAPV;

					if (sponsor.getC_Sponsor_ID() == sponsorCurrentLevel.getC_Sponsor_ID())
					{
						countAsAPV = true;
					}
					else
					{
						countAsAPV = false;
					}
					if (countAsAPV)
					{

						// add sponsorCurrentLevel's volume-of-sales commission
						// facts to sponsor's APV. Make sure that the facts we
						// add are subtracted from other sponsors if necessary.
						sumUpVolOfSales(sponsor, X_C_AdvComSalesRepFact.NAME_APV, srfStatus, period, salesVolTermId, sponsorCurrentLevel, true);

						// similar to case 2 from method updateAPV, next
						// big salesRep receives these points as APV also
						if (nextBigSalesRep != null)
						{
							sumUpVolOfSales(nextBigSalesRep, X_C_AdvComSalesRepFact.NAME_APV,
									srfStatus, period, salesVolTermId,
									sponsorCurrentLevel, false);
						}

						// counting sponsorCurrentLevel's point into our APV
						// means that we don't count it as a logical level. Thus
						// SKIP_IGNORE.
						return Result.SKIP_IGNORE;
					}

					return Result.GO_ON;
				}
			}
					.setDate(period.getEndDate())
					.climb(sponsor, 0);

			if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(srfStatus))
			{
				commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, period.getEndDate(), this, comSystem, product);
				srfBL.retrieveIsCompress(commissionCtx2, period, false);
			}
		}
	}

	/**
	 * Computes the given sponosr's <code>6EDL</code> value by summing up the volume-of-sales MCAdvCommissionFact records of all sponsors within the following six downline levels.
	 * 
	 * Note: We can't sum up these sponsors' APV values because for sponsors with rank >= 2a, the same points can be also be included in another sponsor's (<=1b) APV sales rep fact. different APV
	 * sales rep facts.
	 * 
	 * @param sponsor
	 * @param status
	 * @param date
	 */
	private void recomputeEDLForSmallSalesrep(final ICommissionContext commissionCtx,
			final String srfStatus)
	{

		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();

		final I_M_Product product = commissionCtx.getM_Product();

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final List<I_C_Period> periodsToUpdate = retrievePeriodsToUpdate(commissionCtx, false);

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		for (final I_C_Period period : periodsToUpdate)
		{
			srfBL.resetFact(sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_6EDL, srfStatus, period);

			ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, period.getEndDate(), this, product);

			final int salesVolTermId = retrieveMySalesVolTermId(commissionCtx2);

			final int level[] = { 0 };

			new HierarchyDescender()
			{
				@Override
				public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
						final int logicalLevel, final int hierarchyLevel,
						final Map<String, Object> contextInfo)
				{
					if (level[0] == 0)
					{
						level[0] += 1;
						return Result.GO_ON;
					}

					sumUpVolOfSales(sponsor, X_C_AdvComSalesRepFact.NAME_6EDL, srfStatus, period, salesVolTermId, sponsorCurrentLevel, false);

					level[0] += 1;
					return Result.GO_ON;
				}
			}
					.setDate(period.getEndDate())
					.climb(sponsor, 6);

			if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(srfStatus))
			{
				commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, period.getEndDate(), this, comSystem, product);
				srfBL.retrieveIsCompress(commissionCtx2, period, false);
			}
		}
	}

	/**
	 * Helper for the recompute* methods. It retrieves those periods that need updating. The method has been added for the cases when <code>date</code> is not in the current/most recent period.
	 * 
	 * @param sponsor
	 * @param date
	 * @param small2big tells the method when to stop adding further periods to the result<br>
	 *            If <code>true</code>, the method returns the period of <code>date</code> plus the following periods until the first period with a sales rep fact with rank >= 2a is found.<br>
	 *            If <code>false</code>, the method returns the period of <code>date</code> plus the following periods until the first period with a sales rep fact with rank <= 1b is found.
	 * @return a list of periods, starting with <code>date</code>'s period until (excluding!) the first period that meets the criterion defined in <code>small2big</code>.
	 */
	private List<I_C_Period> retrievePeriodsToUpdate(final ICommissionContext commissionCtx, final boolean small2big)
	{
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();
		final Timestamp date = commissionCtx.getDate();

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final I_C_Period firstPeriod = retrievePeriod(sponsor, date);

		final Calendar cal = new GregorianCalendar();
		cal.setTime(firstPeriod.getStartDate());
		cal.add(Calendar.YEAR, 1);

		final Timestamp datePlusOneYear = new Timestamp(cal.getTime().getTime());

		// retrieve last 12 periods last to first and reverse the list order
		final List<I_C_Period> periods = srfBL.retrieveLastPeriods(sponsor, 12, datePlusOneYear, false);
		Collections.reverse(periods);

		final List<I_C_Period> periodsToUpdate = new ArrayList<I_C_Period>();

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();
		final I_C_AdvComRankCollection rankCollection = comSystem.getC_AdvComRankCollection();

		for (final I_C_Period period : periods)
		{
			if (period.getStartDate().before(firstPeriod.getStartDate()))
			{
				continue;
			}

			if (period.getC_Period_ID() == firstPeriod.getC_Period_ID())
			{
				periodsToUpdate.add(period);
			}
			else
			{

				// check if there are any sales vol facts to make sure we don't
				// create srfs for future months
				final boolean salesVolFactsExist = !MCAdvCommissionFact.retrieveFacts(period, retrieveMySalesVolTermId(commissionCtx)).isEmpty();

				if (salesVolFactsExist)
				{
					// there is at least one sales vol fact, therefore a srf
					// might need updating

					if (small2big)
					{
						if (!srfBL.isChangeToRank(rankCollection, comSystem, ConfigParams.SG2A, sponsor, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, period.getC_Period_ID(), true))
						{
							// In this period, the rank is still below 2a
							periodsToUpdate.add(period);
						}
					}
					else
					{
						if (!srfBL.isChangeToRank(rankCollection, comSystem, ConfigParams.SG2A, sponsor, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, period.getC_Period_ID(), false))
						{
							// In this period, the rank is still 2a or above
							periodsToUpdate.add(period);
						}
					}
				}
			}
		}
		return periodsToUpdate;
	}

	private int retrieveMySalesVolTermId(final ICommissionContext commissionCtx)
	{
		// final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, date, this);

		final IContractBL contractBL = Services.get(IContractBL.class);

		final int salesVolTermId = (Integer)contractBL.retrieveSponsorParam(commissionCtx, "C_AdvComTerm_SalesVol_ID");

		return salesVolTermId;
	}

	private I_C_Period retrievePeriod(final I_C_Sponsor salesRep, final Timestamp date)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_Period period = sponsorBL.retrieveCommissionPeriod(salesRep, date);
		return period;
	}

	private String salesRepStatus2ComFactStatus(final String status)
	{

		final String comFactStatus;
		if (X_C_AdvComSalesRepFact.STATUS_Prognose.equals(status))
		{

			comFactStatus = X_C_AdvCommissionFact.STATUS_Prognostiziert;

		}
		else if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(status))
		{
			comFactStatus = X_C_AdvCommissionFact.STATUS_ZuBerechnen;

		}
		else
		{
			throw new IllegalArgumentException(status.toString());
		}
		return comFactStatus;
	}

	/**
	 * Loads the volume-of-sales facts of <code>donatingSponsor</code> and adds their commission points to <code>receivingSponsor</code>'s sales rep fact.
	 * 
	 * Abbreviations:
	 * <ul>
	 * <li>cf = commission fact (volume-of-sales fact)</li>
	 * <li>srf = sales rep fact</li>
	 * </ul>
	 * 
	 * @param receivingSponsor
	 * @param srfName name of the <code>receivingSponsor</code>'s srf into which the facts are summed up
	 * @param srfStatus status of the <code>receivingSponsor</code>'s srf into which the facts are summed up
	 * @param period only cfs from this period are summed up
	 * @param salesVolTermId only facts whose commission instance has this commission term ID are summed up
	 * @param donatingSponsor
	 * @param remove if true, the cf records that are added to <code>receivingSponsor</code>'s srf are also removed from other sponsors' facts with the same <code>srfStatus</code> and
	 *            <code>srfName</code>, unless the respective cf record <b>belongs</b> to the respective sponsor.
	 */
	private void sumUpVolOfSales(final I_C_Sponsor receivingSponsor,
			final String srfName, final String srfStatus, final I_C_Period period,
			final int salesVolTermId, final I_C_Sponsor donatingSponsor,
			final boolean remove)
	{

		assert !remove || X_C_AdvComSalesRepFact.NAME_APV.equals(srfName) : "remove=true implies name=APV";

		final String comFactStatus = salesRepStatus2ComFactStatus(srfStatus);

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		for (final I_C_AdvCommissionInstance inst : Services.get(ICommissionInstanceDAO.class)
				.retrieveForSalesRep(donatingSponsor, period.getStartDate(),
						period.getEndDate(), salesVolTermId))
		{
			for (final MCAdvCommissionFact fact : MCAdvCommissionFact.retrieveFacts(inst, period, comFactStatus))
			{
				final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();
				srfBL.addAmtToSrf(receivingSponsor, comSystem, srfName, srfStatus, period, fact.getDateDoc(), fact);

				if (remove)
				{

					final Set<I_C_Sponsor> sponsorToRemoveFrom = new HashSet<I_C_Sponsor>();

					for (final I_C_AdvComSalesRepFact srf : Services.get(ICommissionSalesRepFactDAO.class).retrieveForComFact(fact))
					{
						if (srf.getC_Sponsor_ID() == receivingSponsor.getC_Sponsor_ID())
						{
							// no point in removing these facts from
							// receivingSponsor, because we just added them.
							continue;
						}

						// only remove from srfs that have the same name and status
						if (srf.getName().equals(srfName) && srf.getStatus().equals(srfStatus))
						{
							// only remove if the cf's instance doesn't
							// belong to the sponsor
							if (srf.getC_Sponsor_ID() != fact
									.getC_AdvCommissionInstance()
									.getC_Sponsor_SalesRep_ID())
							{
								sponsorToRemoveFrom.add(srf.getC_Sponsor());
							}
						}
					}

					for (final I_C_Sponsor sponsor : sponsorToRemoveFrom)
					{
						srfBL.subtractAmtFromSrf(sponsor, comSystem, srfName, srfStatus, period, fact.getDateDoc(), fact);
					}
				}
			}
		}
	}

	@Override
	List<I_C_Sponsor> updateADV(final I_C_Sponsor salesRep, final String status,
			final I_C_AdvCommissionFact volumeOfSalesFact)
	{

		// 1. search the upline for the next (at most 6) sales reps that have
		// rank between K and 1b. Add the amt to their 6EDL.
		final List<I_C_Sponsor> result = distributeEDL(salesRep, status,
				volumeOfSalesFact, ConfigParams.K, ConfigParams.SG2A);

		// 2. search the upline for the next (at most 6) sales reps that have
		// rank >= 2a. Add the amt to their 6EDL.
		result.addAll(distributeEDL(salesRep, status, volumeOfSalesFact,
				ConfigParams.SG2A, null));

		return result;
	}

	/**
	 * 
	 * @param salesRep the "sales rep" sponsor of the commission fact's instance
	 * 
	 * @return List of sales rep facts that have been created or updated due to the given <code>volumeOfSalesFact</code> .
	 */
	@Override
	List<I_C_Sponsor> updateAPV(final I_C_Sponsor salesRep, final String status, final I_C_AdvCommissionFact volumeOfSalesFact)
	{
		// case 1, salesrep is >=2a: add the fact's commissionPointsSum to
		// the salesRep's APV.

		// case 2, salesRep is <=1b: add the fact's commissionPointsSum to
		// salesRep's APV. Also find the next salesRep >=2a in the upline and
		// add the fact's commissionPointsSum to that salesRep's APV as well.

		// No need to search for necessary corrections up and
		// down the hierarchy, because these would all be triggered by
		// additional volumeOfSalesFact's (with negative commissionPointsSum
		// values).

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final Timestamp date = volumeOfSalesFact.getDateDoc();

		final I_C_Period period = retrievePeriod(salesRep, date);

		final List<I_C_Sponsor> result = new ArrayList<I_C_Sponsor>();

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		//
		// cases 1 and 2
		srfBL.addAmtToSrf(salesRep, comSystem, X_C_AdvComSalesRepFact.NAME_APV, status, period, date, volumeOfSalesFact);
		result.add(salesRep);

		if (!srfBL.isRankGE(comSystem, ConfigParams.SG2A, salesRep, status, date))
		{
			//
			// only case 2
			final IHierarchyBL hierarchyBL = Services.get(IHierarchyBL.class);
			final List<I_C_Sponsor> sponsors = hierarchyBL.findSponsorsInUpline(
					date, salesRep, Integer.MAX_VALUE, 1, comSystem, ConfigParams.SG2A, status);

			assert sponsors.size() <= 1;

			for (final I_C_Sponsor sponsor : sponsors)
			{
				srfBL.addAmtToSrf(sponsor, comSystem, X_C_AdvComSalesRepFact.NAME_APV, status, period, date, volumeOfSalesFact);
				result.add(sponsor);
			}
		}
		return result;
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
			final ICommissionContext commissionCtx, final BigDecimal sum,
			final String status)
	{
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();
		final Timestamp date = commissionCtx.getDate();
		final String sgValue;

		if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG3E, sum)
				&& hasInDownLine(date, sponsor, ConfigParams.SG3A, status))
		{
			sgValue = ConfigParams.SG3E;
		}
		else if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG3D, sum)
				&& hasInDownLine(date, sponsor, ConfigParams.SG2E, status))
		{
			sgValue = ConfigParams.SG3D;
		}
		else if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG3C, sum)
				&& hasInDownLine(date, sponsor, ConfigParams.SG2D, status))
		{
			sgValue = ConfigParams.SG3C;
		}
		else if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG3B, sum)
				&& hasInDownLine(date, sponsor, ConfigParams.SG2C, status))
		{
			sgValue = ConfigParams.SG3B;
		}
		else if (isIntParamLessOrEqual(commissionCtx,
				ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG3A, sum))
		{
			sgValue = ConfigParams.SG3A;
		}
		else if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG2E, sum))
		{
			sgValue = ConfigParams.SG2E;
		}
		else if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG2D, sum))
		{
			sgValue = ConfigParams.SG2D;
		}
		else if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG2C, sum))
		{
			sgValue = ConfigParams.SG2C;
		}
		else if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG2B, sum))
		{
			sgValue = ConfigParams.SG2B;
		}
		else if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG2A, sum))
		{
			sgValue = ConfigParams.SG2A;
		}
		else if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG1B, sum))
		{
			sgValue = ConfigParams.SG1B;
		}
		else if (isIntParamLessOrEqual(commissionCtx, ConfigParams.PARAM_6EDL12M_FOR_ + ConfigParams.SG1A, sum))
		{
			sgValue = ConfigParams.SG1A;
		}
		else
		{
			sgValue = ConfigParams.K;
		}

		final I_C_AdvComRankCollection rankCollection = getComSystemType().getC_AdvComSystem().getC_AdvComRankCollection();
		final I_C_AdvCommissionSalaryGroup sgResult = Services.get(ICommissionRankDAO.class).retrieve(
				InterfaceWrapperHelper.getCtx(sponsor),
				rankCollection, sgValue,
				InterfaceWrapperHelper.getTrxName(sponsor)
				);
		return sgResult;
	}

	@Override
	Map<I_C_Sponsor, List<I_C_AdvCommissionSalaryGroup>> updateRanks(
			final ICommissionContext commissionCtx,
			final Set<I_C_Sponsor> sponsors,
			final String[] srfStatus, final BigDecimal amt)
	{

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final Map<I_C_Sponsor, List<I_C_AdvCommissionSalaryGroup>> result = new HashMap<I_C_Sponsor, List<I_C_AdvCommissionSalaryGroup>>();

		final List<I_C_Sponsor> rankChange2AOrBetter = new ArrayList<I_C_Sponsor>();
		final List<I_C_Sponsor> rankChange1BOrWorse = new ArrayList<I_C_Sponsor>();

		final List<I_C_Sponsor> rankChangeNeedsEscalation = new ArrayList<I_C_Sponsor>();

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		final Timestamp date = commissionCtx.getDate();
		final I_M_Product product = commissionCtx.getM_Product();

		for (final I_C_Sponsor sponsor : sponsors)
		{
			final I_C_Period period = sponsorBL.retrieveCommissionPeriod(sponsor, date);

			final BigDecimal apv = srfBL.retrieveSum(sponsor, X_C_AdvComSalesRepFact.NAME_APV, period, srfStatus);

			final BigDecimal adv = srfBL.retrieveSum(sponsor, X_C_AdvComSalesRepFact.NAME_6EDL, period, srfStatus);

			final BigDecimal pointsSum = adv.add(apv);

			final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, comSystem, product);

			I_C_AdvCommissionSalaryGroup newRank = retrieveSalaryGroup(commissionCtx2, pointsSum, srfStatus[0]);
			int newRankId = newRank == null ? 0 : newRank.getC_AdvCommissionSalaryGroup_ID();

			// using srfBL because we are not interested in manual rank.
			final I_C_AdvCommissionSalaryGroup oldRank = srfBL.retrieveSalaryGroup(
					InterfaceWrapperHelper.getCtx(sponsor),
					sponsor, comSystem, date, srfStatus[0],
					InterfaceWrapperHelper.getTrxName(sponsor)
					);

			final int oldRankId = oldRank == null ? 0 : oldRank.getC_AdvCommissionSalaryGroup_ID();

			if (newRank.getSeqNo() < oldRank.getSeqNo())
			{
				final Map.Entry<I_C_AdvComSalesRepFact, I_C_AdvCommissionSalaryGroup> bestOfLastYear = srfBL.retrieveBestRank(sponsor, comSystem, date, srfStatus[0], 11, true);

				if (bestOfLastYear != null && bestOfLastYear.getValue().getSeqNo() > newRank.getSeqNo())
				{
					newRank = bestOfLastYear.getValue();
					newRankId = newRank == null ? 0 : newRank.getC_AdvCommissionSalaryGroup_ID();
				}
			}

			if (newRankId == oldRankId)
			{
				UpdateSalesRepFactsCustomerLegacy.logger.info("No change for " + sponsor + " (Status=" + srfStatus[0] + "): " + oldRank);
				continue;
			}

			UpdateSalesRepFactsCustomerLegacy.logger.info("Status " + srfStatus[0] + ": Rank changes " + oldRank.getValue() + " to " + newRank.getValue() + " for " + sponsor);

			final I_C_AdvComSalesRepFact rankSrf = srfBL.createOrUpdateRank(sponsor, comSystem, srfStatus[0], period.getC_Period_ID(), date, newRankId);

			srfBL.copyFactReferences(rankSrf, period, //
					new String[] { X_C_AdvComSalesRepFact.NAME_APV, X_C_AdvComSalesRepFact.NAME_6EDL }, //
					srfStatus);

			final List<I_C_AdvCommissionSalaryGroup> oldAndNewRank = new ArrayList<I_C_AdvCommissionSalaryGroup>();
			oldAndNewRank.add(oldRank);
			oldAndNewRank.add(newRank);

			result.put(sponsor, oldAndNewRank);

			if (X_C_AdvComSalesRepFact.STATUS_Prov_Relevant.equals(srfStatus[0]))
			{
				sponsor.setC_AdvComRank_System_ID(newRankId);
				if (!sponsor.isManualRank())
				{
					sponsor.setC_AdvCommissionSalaryGroup_ID(newRankId);
				}
				InterfaceWrapperHelper.save(sponsor);
			}
			final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
			final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);
			final List<I_C_AdvCommissionSalaryGroup> rank2aAndBetter = Services.get(ICommissionRankDAO.class).retrieveGroupAndBetter(
					ctx,
					comSystem.getC_AdvComRankCollection(),
					ConfigParams.SG2A,
					trxName);

			final boolean oldRankIs2AOrBetter = srfBL.isInSGToLookFor(
					rank2aAndBetter, oldRankId);

			final boolean newRankIs2AOrBetter = srfBL.isInSGToLookFor(
					rank2aAndBetter, newRankId);

			if (oldRankIs2AOrBetter == newRankIs2AOrBetter)
			{
				UpdateSalesRepFactsCustomerLegacy.logger
						.debug("No reevaluation of the volumeOfSales points needed");

			}
			else if (newRankIs2AOrBetter)
			{

				rankChange2AOrBetter.add(sponsor);

			}
			else if (oldRankIs2AOrBetter)
			{

				rankChange1BOrWorse.add(sponsor);
			}

			// if this bPartner enters or exists rank 2C (and higher), that
			// might trigger changes for bpartners in the upline that have rank
			// 3b and higher

			final List<I_C_AdvCommissionSalaryGroup> rank2cAndBetter = Services.get(ICommissionRankDAO.class).retrieveGroupAndBetter(
					ctx,
					comSystem.getC_AdvComRankCollection(),
					ConfigParams.SG2C,
					trxName);

			final boolean oldRankIs2cOrBetter = srfBL.isInSGToLookFor(
					rank2cAndBetter, oldRankId);

			final boolean newRankIs2cOrBetter = srfBL.isInSGToLookFor(
					rank2cAndBetter, newRankId);

			if (oldRankIs2cOrBetter != newRankIs2cOrBetter)
			{

				rankChangeNeedsEscalation.add(sponsor);

			}
			else if (newRankIs2cOrBetter && oldRankId != newRankId)
			{

				rankChangeNeedsEscalation.add(sponsor);
			}
		}

		for (final I_C_Sponsor sponsor : rankChange1BOrWorse)
		{

			ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, product);
			recomputeAPVForSmallSalesrep(commissionCtx2, srfStatus[0]);

			final IHierarchyBL hierarchyBL = Services.get(IHierarchyBL.class);
			for (final I_C_Sponsor sponsorInUpLine : hierarchyBL
					.findSponsorsInUpline(date, sponsor, 6, Integer.MAX_VALUE,
							comSystem, ConfigParams.SG2A, srfStatus[0]))
			{

				commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsorInUpLine, date, this, comSystem, product);
				recomputeEDLForBigSalesrep(commissionCtx2, srfStatus[0],
						ConfigParams.SG2A);
			}
		}
		for (final I_C_Sponsor sponsor : rankChange2AOrBetter)
		{
			ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, comSystem, product);
			recomputeAPVForBigSalesrep(commissionCtx2, srfStatus[0], ConfigParams.SG2A);

			final IHierarchyBL hierarchyBL = Services.get(IHierarchyBL.class);
			for (final I_C_Sponsor sponsorInUpLine : hierarchyBL
					.findSponsorsInUpline(date, sponsor, 6, Integer.MAX_VALUE,
							comSystem, ConfigParams.SG2A, srfStatus[0]))
			{
				commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsorInUpLine, date, this, comSystem, product);

				recomputeEDLForBigSalesrep(commissionCtx2, srfStatus[0],
						ConfigParams.SG2A);
			}
		}

		for (final I_C_Sponsor sponsor : rankChange1BOrWorse)
		{
			final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, comSystem, product);

			recomputeEDLForSmallSalesrep(commissionCtx2, srfStatus[0]);
		}
		for (final I_C_Sponsor sponsor : rankChange2AOrBetter)
		{
			final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, comSystem, product);

			recomputeEDLForBigSalesrep(commissionCtx2, srfStatus[0],
					ConfigParams.SG2A);
		}

		for (final I_C_Sponsor sponsor : rankChangeNeedsEscalation)
		{
			final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, date, this, comSystem, product);
			recomputeUpperRanks(commissionCtx2, srfStatus);
		}

		return result;
	}

	private void recomputeUpperRanks(final ICommissionContext commissionCtx,
			final String[] status)
	{

		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();
		final Timestamp date = commissionCtx.getDate();
		final I_M_Product product = commissionCtx.getM_Product();

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final I_C_AdvComSystem comSystem = getComSystemType().getC_AdvComSystem();

		new HierarchyAscender()
		{
			@Override
			public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
					final int logicalLevel, final int hierarchyLevel,
					final Map<String, Object> contextInfo)
			{
				final I_C_Period period = sponsorBL.retrieveCommissionPeriod(sponsor, date);

				if (srfBL.isRankGE(comSystem, ConfigParams.SG3B, sponsorCurrentLevel, status[0], date))
				{
					final BigDecimal edl = srfBL.retrieveSum(sponsor, X_C_AdvComSalesRepFact.NAME_6EDL, period, status);
					final BigDecimal apv = srfBL.retrieveSum(sponsor, X_C_AdvComSalesRepFact.NAME_APV, period, status);

					final BigDecimal pointsSum = edl.add(apv);

					final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsorCurrentLevel, date, UpdateSalesRepFactsCustomerLegacy.this, comSystem,
							product);

					updateRanks(commissionCtx2, Collections.singleton(sponsorCurrentLevel), status, pointsSum);
				}
				return Result.GO_ON;
			}
		}
				.setDate(date)
				.climb(sponsor, Integer.MAX_VALUE);
	}
}
