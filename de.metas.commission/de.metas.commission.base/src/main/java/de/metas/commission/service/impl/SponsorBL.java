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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeInstance;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.adempiere.service.ICalendarDAO;
import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.custom.type.ISalesRefFactCollector;
import de.metas.commission.exception.CommissionException;
import de.metas.commission.exception.SponsorConfigException;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_AdvCommissionType;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvComRankForecast;
import de.metas.commission.model.MCAdvComSystem;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvComSystem_Type;
import de.metas.commission.model.X_C_Sponsor_SalesRep;
import de.metas.commission.modelvalidator.SponsorValidator;
import de.metas.commission.service.ICommissionConditionDAO;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ICommissionTermDAO;
import de.metas.commission.service.ICommissionTypeBL;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.commission.util.CommissionConstants;
import de.metas.commission.util.CommissionTools;
import de.metas.commission.util.Messages;
import de.metas.logging.LogManager;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Sponsor_%282009_0027_G8%29'>(2009 0027 G8)</a>"
 */
public class SponsorBL implements ISponsorBL
{

	private static final Logger logger = LogManager.getLogger(SponsorBL.class);

	// private void checkFirstAndLast(final List<I_C_Sponsor_SalesRep> seList) throws SponsorConfigException
	// {
	// final Timestamp first = seList.get(0).getValidFrom();
	// final Timestamp last = seList.get(seList.size() - 1).getValidTo();
	//
	// // first must be after VALID_RANGE_MIN
	// final boolean firstOk = CommissionConstants.VALID_RANGE_MIN.compareTo(first) <= 0;
	// if (!firstOk)
	// {
	// throw new SponsorConfigException(Messages.SPONSOR_VALIDITY_GAP_2P,
	// CommissionConstants.VALID_RANGE_MIN, first);
	// }
	//
	// final boolean lastOk = CommissionConstants.VALID_RANGE_MAX.compareTo(last) <= 0;
	// if (!lastOk)
	// {
	// throw new SponsorConfigException(Messages.SPONSOR_VALIDITY_GAP_2P,
	// last, CommissionConstants.VALID_RANGE_MAX);
	// }
	// }

	public static final String UPDATE_VALID_TO = "UPDATE "
			+ I_C_Sponsor_SalesRep.Table_Name + " SET "
			+ I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo + "=? WHERE "
			+ I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_SalesRep_ID + "=?";

	private static final String MSG_MISSING_CONTRACT_FOR_ORPHANED_SPONSOR_2P = "MissingContrForOrphanedSponsor_2P";

	@Override
	public I_C_Sponsor_SalesRep retrieveContractSSR(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final Timestamp date,
			final String trxName)
	{
		// final I_C_Sponsor rootSponsor = retrieveRoot(ctx, sponsor, date, trxName);
		// assert rootSponsor != null : "rootSponsor may not be null; sponsor=" + sponsor + "; date=" + date;
		//
		// final MCAdvComSystem comSystem = MCAdvComSystem.retrieveForRootSponsor(ctx, rootSponsor, trxName);
		// if (comSystem == null)
		// {
		// logger.warn("RootSponsor " + rootSponsor + " has no commission system");
		// return null;
		// }

		return retrieveContractSSR(ctx, sponsor, null, date, trxName);
	}


	private I_C_Sponsor_SalesRep retrieveContractSSR(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final Timestamp date,
			final String trxName)
	{
		for (final I_C_Sponsor_SalesRep ssr : Services.get(ISponsorDAO.class).retrieveSSRsAtDate(ctx, sponsor, date, X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP, trxName))
		{
			if (comSystem == null || ssr.getC_AdvCommissionCondition().getC_AdvComSystem_ID() == comSystem.getC_AdvComSystem_ID())
			{
				return ssr;
			}
		}
		return null;
	}

	@Override
	public I_C_AdvComSystem retrieveComSystem(final ICommissionContext comCtx)
	{
		I_C_AdvComSystem result = null;

		final List<I_C_Sponsor_SalesRep> ssrs = Services.get(ISponsorDAO.class).retrieveSSRsAtDate(comCtx.getCtx(), comCtx.getC_Sponsor(), comCtx.getDate(), null, comCtx.getTrxName());
		for (final I_C_Sponsor_SalesRep ssr : ssrs)
		{
			// note: we could return the firs ssr's C_AdvComSystem, but while we're at it, we might as well do some consistency verifications.
			Check.errorIf(ssr.getC_AdvComSystem_ID() <= 0, "{} has no C_AdvComSystem_ID", ssr);
			if (result == null)
			{
				result = ssr.getC_AdvComSystem();
			}
			else
			{
				Check.errorIf(result.getC_AdvComSystem_ID() != ssr.getC_AdvComSystem_ID(), "The C_Sponsor_SalesRep records {} for {} have different C_AdvComSystem_IDs", ssrs, comCtx);
			}
		}
		return result;
	}

	@Override
	public I_C_AdvCommissionCondition retrieveContract(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final Timestamp date,
			final String trxName)
	{
		final I_C_Sponsor_SalesRep contractSSR = retrieveContractSSR(ctx, sponsor, comSystem, date, trxName);

		if (contractSSR == null)
		{
			final I_C_AdvComSystem comSystemToUse;
			if (comSystem == null)
			{
				final List<I_C_Sponsor_SalesRep> parentSSRs = Services.get(ISponsorDAO.class).retrieveParentLinks(ctx, sponsor.getC_Sponsor_ID(), date, date, trxName);
				if (parentSSRs.isEmpty())
				{
					// 'sponsor' has no parent sponsor. This is fine, as long as 'sponsor' is a root sponsor
					comSystemToUse = MCAdvComSystem.retrieveForRootSponsor(ctx, sponsor, trxName);
					if (comSystemToUse == null)
					{
						final String msg = Msg.getMsg(ctx, SponsorValidator.MSG_INCONSISTENT_PARENT_SPONSOR_MISSING_1P, new Object[] { sponsor.getC_BPartner().getValue(), date });
						throw CommissionException.inconsistentConfig(msg, sponsor);
					}
				}
				else
				{
					Check.assume(parentSSRs.size() == 1, sponsor + " has exactly one parent at date " + date);
					comSystemToUse = parentSSRs.get(0).getC_AdvComSystem();
				}
			}
			else
			{
				comSystemToUse = comSystem;
			}
			if (comSystemToUse == null)
			{
				final String msg = Msg.getMsg(ctx, SponsorBL.MSG_MISSING_CONTRACT_FOR_ORPHANED_SPONSOR_2P, new Object[] { sponsor.getSponsorNo(), date });
				throw CommissionException.inconsistentConfig(msg, sponsor);
			}

			final I_C_AdvCommissionCondition contract = Services.get(ICommissionConditionDAO.class).retrieveForOrphanedSponsors(ctx, comSystemToUse, trxName);
			if (contract == null)
			{
				final String msg = Msg.getMsg(ctx, SponsorBL.MSG_MISSING_CONTRACT_FOR_ORPHANED_SPONSOR_2P, new Object[] { sponsor.getSponsorNo(), date });
				throw CommissionException.inconsistentConfig(msg, sponsor);
			}
			return contract;
		}

		Check.assume(contractSSR != null, "contractSSR for " + sponsor + " and date " + date + " is not null");
		Check.assume(contractSSR.getC_AdvCommissionCondition_ID() > 0, contractSSR + " for " + sponsor + " and date " + date + " has C_AdvCommissionCondition_ID>0");

		return contractSSR.getC_AdvCommissionCondition();
	}

	@Override
	public I_C_AdvCommissionCondition retrieveContract(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final Timestamp date,
			final String trxName)
	{
		// final I_C_Sponsor rootSponsor = retrieveRoot(ctx, sponsor, date, trxName);
		// assert rootSponsor != null : "rootSponsor may not be null; sponsor=" + sponsor + "; date=" + date;
		//
		// final MCAdvComSystem comSystem = MCAdvComSystem.retrieveForRootSponsor(ctx, rootSponsor, trxName);
		// if (comSystem == null)
		// {
		// logger.warn("RootSponsor " + rootSponsor + " has no commission system");
		// return null;
		// }

		return retrieveContract(ctx, sponsor, null, date, trxName);
	}

	/**
	 * Retrieves the BPartner that is a sales rep for this sponsor at the given date.
	 * 
	 * @param date
	 * @param throwEx if <code>true</code> and there is no sales rep for this sponsor at the given date, an {@link SponsorConfigException} is thrown
	 * @return returns the sales rep or <code>null</code>, if <code>throwEx</code> is false and there is no sale rep at the given date
	 * @throws SponsorConfigException
	 */
	@Override
	public I_C_BPartner retrieveSalesRepAt(
			final Properties ctx,
			final Timestamp date,
			final I_C_Sponsor sponsor,
			final boolean throwEx,
			final String trxName)
	{
		for (final I_C_Sponsor_SalesRep ssr : Services.get(ISponsorDAO.class).retrieveSalesRepSSRs(ctx, sponsor, trxName))
		{
			final Timestamp from = ssr.getValidFrom();
			final Timestamp to = ssr.getValidTo();
			if ((from.equals(date) || from.before(date)) && (to.equals(date) || to.after(date)))
			{
				return InterfaceWrapperHelper.create(ssr.getC_BPartner(), I_C_BPartner.class);
			}
		}

		SponsorBL.logger.info(this + " has no sales rep at date " + date);

		if (throwEx)
		{
			throw CommissionException.inconsistentConfig(
					"Der Hierarchienummer " + sponsor.getSponsorNo()
							+ " ist zum Datum '" + date
							+ "' keine VP zugeordnet", this);
		}
		return null;
	}

	@Override
	public I_C_AdvCommissionTerm retrieveTerm(final ICommissionContext commissionCtx, boolean assertTermExists)
	{
		Check.assumeNotNull(commissionCtx.getM_Product(), "Product should not be null"); // 04639

		final I_C_AdvCommissionTerm term = Services.get(ICommissionTermDAO.class).retrieveTermForSponsorAndProductAndSystemType(commissionCtx);

		Check.errorIf(assertTermExists && term == null,
				"term is null; sponsor=" + commissionCtx.getC_Sponsor()
						+ "; date=" + commissionCtx.getDate()
						+ "; comSystemType=" + commissionCtx.getC_AdvComSystem_Type()
						+ "; M_Product_ID=" + commissionCtx.getM_Product().getM_Product_ID());
		return term;
	}

	@Cached
	@Override
	public I_C_Period retrieveCommissionPeriod(final I_C_Sponsor sponsor, final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);
		return retrieveCommissionPeriod(ctx, sponsor, date, trxName);
	}

	@Override
	public I_C_Period retrieveCommissionPeriod(final Properties ctx, final I_C_Sponsor sponsor, final Timestamp date, final String trxName)
	{
		final I_C_AdvCommissionCondition contract = retrieveContract(ctx, sponsor, date, trxName);
		assert contract != null : "contract may not be null; sponsor=" + sponsor + "; date=" + date;

		return retrieveCommissionPeriod(ctx, contract, date, trxName);
	}

	@Override
	public I_C_Period retrieveCommissionPeriod(final Properties ctx, final I_C_AdvCommissionCondition contract, final Timestamp date, final String trxName)
	{
		final int calendarId = contract.getC_Calendar_ID();
		Check.assume(calendarId > 0, "Calendar shall be set for contract {}", contract);

		final I_C_Period period = Services.get(ICalendarDAO.class).findByCalendar(ctx, date, calendarId, trxName);

		if (period == null)
		{
			// TODO -> AD_Message
			throw CommissionException.inconsistentConfig(
					"Contract's calendar has no period for date " + date,
					contract);
		}
		return period;
	}

	@Override
	public Timestamp retrieveDateFrom(final I_C_Sponsor sponsor, final I_C_AdvComSystem_Type comSystemType, final Timestamp date)
	{
		final String retroactive = comSystemType.getRetroactiveEvaluation();

		if (X_C_AdvComSystem_Type.RETROACTIVEEVALUATION_Keine.equals(retroactive))
		{
			return date;
		}
		else if (X_C_AdvComSystem_Type.RETROACTIVEEVALUATION_Tag.equals(retroactive))
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			return new Timestamp(cal.getTimeInMillis());

		}
		else if (X_C_AdvComSystem_Type.RETROACTIVEEVALUATION_Provisionsperiode.equals(retroactive))
		{
			return retrieveCommissionPeriod(sponsor, date).getStartDate();
		}
		throw new AdempiereException("Can't handle CommissionConfig.Retroactive=" + retroactive);
	}

	@Override
	public Timestamp retrieveDateTo(
			final I_C_Sponsor sponsor, final I_C_AdvComSystem_Type comSystemType, final Timestamp date)
	{
		final String retroactive = comSystemType.getRetroactiveEvaluation();

		if (X_C_AdvComSystem_Type.RETROACTIVEEVALUATION_Keine.equals(retroactive))
		{
			return date;
		}
		else if (X_C_AdvComSystem_Type.RETROACTIVEEVALUATION_Tag.equals(retroactive))
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);

			return new Timestamp(cal.getTimeInMillis());

		}
		else if (X_C_AdvComSystem_Type.RETROACTIVEEVALUATION_Provisionsperiode.equals(retroactive))
		{
			return retrieveCommissionPeriod(sponsor, date).getEndDate();
		}
		throw new AdempiereException("Can't handle CommissionConfig.Retroactive=" + retroactive);
	}

	@Override
	public void onIsManualRankChange(final I_C_Sponsor sponsor, final boolean saveNewRank, final int oldSalaryGroupId)
	{
		if (!sponsor.isManualRank())
		{
			sponsor.setC_AdvCommissionSalaryGroup_ID(sponsor.getC_AdvComRank_System_ID());
		}

		if (saveNewRank && oldSalaryGroupId > 0)
		{
			onManualRankChange(sponsor, true, oldSalaryGroupId);
		}
	}

	@Override
	public void onManualRankChange(final I_C_Sponsor sponsor, final boolean isIsManualRankChanged, final int oldSalaryGroupId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		// All rank changes are performed once for every commission system that uses this rank.
		// First, the old fixed Ranks, if available, will be terminated.
		// Then, if necessary, the new fixed ranks will be created.
		// This insures that all fixed ranks are terminated, even if the commission system changes.

		boolean terminate = true;
		boolean create = true;

		if (isIsManualRankChanged)
		{
			if (sponsor.isManualRank()) // when changed to 'Y'
			{
				terminate = false; // don't terminate, because there is no old fixed rank
			}
			else
			{
				create = false; // don't create, because there will be no fixed rank anymore
			}
		}

		// Prepare creating SalesRepFact Records (for both, creating and terminating Ranks)
		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		final Timestamp date = TimeUtil.getDay(SystemTime.asTimestamp());
		final I_C_Period period = retrieveCommissionPeriod(ctx, sponsor, date, trxName);
		final int newSalaryGroupID = sponsor.getC_AdvCommissionSalaryGroup_ID();

		if (terminate)
		{
			// Get old Systems
			final int oldRankCollectionID = getRankCollection_ID(ctx, oldSalaryGroupId, trxName);
			final List<I_C_AdvComSystem> oldComSystems = getSystemsForCollection(ctx, sponsor, oldRankCollectionID, trxName);

			// Terminate old Rank for each System
			for (final I_C_AdvComSystem comSystem : oldComSystems)
			{
				srfBL.createOrUpdateRank(sponsor, comSystem, X_C_AdvComSalesRepFact.STATUS_Manuell, period.getC_Period_ID(), date, 0);
			}
		}
		if (create)
		{
			// Get new Systems
			final int newRankCollectionID = getRankCollection_ID(ctx, newSalaryGroupID, trxName);
			final List<I_C_AdvComSystem> newComSystems = getSystemsForCollection(ctx, sponsor, newRankCollectionID, trxName);

			// Create new ranks for each System
			for (final I_C_AdvComSystem comSystem : newComSystems)
			{
				srfBL.createOrUpdateRank(sponsor, comSystem, X_C_AdvComSalesRepFact.STATUS_Manuell, period.getC_Period_ID(), date, newSalaryGroupID);
			}
		}
	}

	private List<I_C_AdvComSystem> getSystemsForCollection(final Properties ctx, final I_C_Sponsor sponsor, final int rankCollectionID, final String trxName)
	{
		// Create a query to get the systems with the given rank collection ID
		final String where = I_C_AdvComSystem.COLUMNNAME_C_AdvComRankCollection_ID + "=" + rankCollectionID;
		final Query query = new Query(ctx, I_C_AdvComSystem.Table_Name, where, trxName);

		// Execute query and return result in a List
		return query.list(I_C_AdvComSystem.class);
	}

	private int getRankCollection_ID(final Properties ctx, final int salaryGroupId, final String TrxName)
	{
		final I_C_AdvCommissionSalaryGroup rank = InterfaceWrapperHelper.create(ctx, salaryGroupId, I_C_AdvCommissionSalaryGroup.class, TrxName);
		return rank.getC_AdvComRankCollection_ID();
	}

	@Override
	public I_C_AdvCommissionSalaryGroup retrieveRank(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final Timestamp date,
			final String status,
			final String trxName)
	{
		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);

		assert comSystem != null : "comSytem may not be null; sponsor=" + sponsor + "; date=" + date;

		if (sponsor.isManualRank())
		{
			final I_C_AdvCommissionSalaryGroup manualRank = sponsor.getC_AdvCommissionSalaryGroup();

			// we need to check if the given manual rank is valid at 'date'
			if (comSystem.getC_AdvComRankCollection_ID() == manualRank.getC_AdvComRankCollection_ID())
			{
				// we can return the manual rank for the given date only if the given sponsor has a commission-systen
				// with the manual rank's rank-collection at that date.
				return manualRank;
			}
			SponsorBL.logger.info("Not returning manual rank '" + manualRank.getValue() + "', because at " + date + ", " + sponsor + " had comSystem " + comSystem);
		}

		final I_C_AdvCommissionSalaryGroup rank = srfBL.retrieveSalaryGroup(ctx, sponsor, comSystem, date, status, trxName);
		return rank;
	}

	@Override
	public I_C_AdvCommissionSalaryGroup retrieveRank(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final Timestamp date,
			final String status,
			final String trxName)
	{

		final I_C_AdvCommissionCondition contract = retrieveContract(ctx, sponsor, date, trxName);
		if (contract == null)
		{
			// TODO -> AD_Message
			throw CommissionException.inconsistentConfig("Sponsor " + sponsor + " hat an Datum '" + date + "' keinen Vertrag und kann daher auch keinen Rang haben", sponsor);
		}

		final I_C_AdvComSystem comSystem = contract.getC_AdvComSystem();

		if (sponsor.isManualRank())
		{
			final I_C_AdvCommissionSalaryGroup manualRank = sponsor.getC_AdvCommissionSalaryGroup();

			// we need to check if the given manual rank is valid at 'date'
			if (comSystem.getC_AdvComRankCollection_ID() == manualRank.getC_AdvComRankCollection_ID())
			{
				// we can return the manual rank for the given date only if the given sponsor has a commission-system
				// with the manual rank's rank-collection at that date.
				return manualRank;
			}
			SponsorBL.logger.info("Not returning manual rank '" + manualRank.getValue() + "', because at " + date + ", " + sponsor + " had comSystem " + comSystem);
		}

		return retrieveRank(ctx, sponsor, comSystem, date, status, trxName);

	}

	@Override
	public I_M_AttributeSetInstance createSalaryGroupAttributeSet(final I_C_Sponsor sponsor, final I_C_Period calculationPeriod, final Timestamp currentDate)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final int attributeSetId = new Integer(MSysConfig.getValue(ISponsorBL.SYSCONFIG_RankAttributeSet)).intValue();

		final MAttributeSetInstance setInstance = new MAttributeSetInstance(ctx, 0, trxName);
		setInstance.setM_AttributeSet_ID(attributeSetId);
		setInstance.saveEx(trxName);

		// Get Attributes
		final MAttributeSet attributeSet = InterfaceWrapperHelper.create(ctx, attributeSetId, MAttributeSet.class, trxName);
		final MAttribute[] attributes = attributeSet.getMAttributes(true);

		// Check if AttributeSet is valid
		checkAttributes(ctx, attributes);

		// Create attribute instances
		for (final MAttribute attribute : attributes)
		{
			String value = "";
			if (attribute.getName().equals(Msg.getMsg(ctx, Messages.CURRENT_RANK)))
			{
				value = retrieveAttributeRank(sponsor, currentDate, false);
			}
			else if (attribute.getName().equals(Msg.getMsg(ctx, Messages.HIGHEST_RANK_IN_12_MONTHS)))
			{
				if (calculationPeriod != null && calculationPeriod.getEndDate() != null)
				{
					value = retrieveAttributeRank(sponsor, calculationPeriod.getEndDate(), true);
				}
				else
				{
					value = retrieveAttributeRank(sponsor, currentDate, true);
				}
			}
			else if (attribute.getName().equals(Msg.getMsg(ctx, Messages.RANK_OF_PAYROLL_PERIOD)))
			{
				if (calculationPeriod != null && calculationPeriod.getEndDate() != null)
				{
					value = retrieveAttributeRank(sponsor, calculationPeriod.getEndDate(), false);
				}
			}
			final MAttributeInstance attInst = new MAttributeInstance(ctx, attribute.get_ID(), setInstance.get_ID(), value, trxName);

			attInst.saveEx(trxName);
		}

		return setInstance;
	}

	private String retrieveAttributeRank(final I_C_Sponsor sponsor, final Timestamp date, final boolean highestOfYearBeforeDate)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		String rank = "";

		// Figure out which function should be used
		// For further information about the functions, please consult their Descriptions.
		final String sqlFunction = highestOfYearBeforeDate ? " getTopRank(?, ?) " : " getRank(?, ?) ";

		// create SQL Statement
		final String sql =
				" SELECT " + I_C_AdvCommissionSalaryGroup.COLUMNNAME_Name +
						" FROM " + I_C_AdvCommissionSalaryGroup.Table_Name +
						" WHERE " + I_C_AdvCommissionSalaryGroup.COLUMNNAME_C_AdvCommissionSalaryGroup_ID + " = " + sqlFunction + ";";

		// execute SQL
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, sponsor.getC_Sponsor_ID());
			pstmt.setTimestamp(2, date);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				rank = rs.getString(1);
			}

			rs.close();
			pstmt.close();
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return rank;
	}

	private void checkAttributes(final Properties ctx, final MAttribute[] attributes)
	{
		final String attrNameCurrent = Msg.getMsg(ctx, Messages.CURRENT_RANK);
		final String attrNamePayroll = Msg.getMsg(ctx, Messages.RANK_OF_PAYROLL_PERIOD);
		final String attrNameHighest = Msg.getMsg(ctx, Messages.HIGHEST_RANK_IN_12_MONTHS);
		StringBuffer actualList = null;

		boolean currentRankFound = false;
		boolean periodRankFound = false;
		boolean highestRankFound = false;
		for (final MAttribute attribute : attributes)
		{
			final String name = attribute.getName();
			if (actualList == null)
			{
				actualList = new StringBuffer("\"" + name + "\"");
			}
			else
			{
				actualList.append(", \"" + name + "\"");
			}

			if (name.equals(attrNameCurrent))
			{
				currentRankFound = true;
			}
			else if (name.equals(attrNamePayroll))
			{
				periodRankFound = true;
			}
			else if (name.equals(attrNameHighest))
			{
				highestRankFound = true;
			}
		}

		Check.assume(currentRankFound && periodRankFound && highestRankFound,
				"The ranking attribute set defined in Sysconfig (" + ISponsorBL.SYSCONFIG_RankAttributeSet + ") \n" +
						"contains attributes that are covering all attribute names defined by the following AD_Messages: \n" +
						Messages.CURRENT_RANK + " (\"" + attrNameCurrent + "\"), " +
						Messages.RANK_OF_PAYROLL_PERIOD + " (\"" + attrNamePayroll + "\"), " +
						Messages.HIGHEST_RANK_IN_12_MONTHS + " (\"" + attrNameHighest + "\")\n\n" +
						"Actually found: \n" + actualList.toString()
				);
	}

	@Override
	public ISalesRefFactCollector retrieveSalesRepFactCollector(
			final Properties ctx, final I_C_Sponsor sponsor, final Timestamp date, final String trxName)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_AdvCommissionCondition contract = sponsorBL.retrieveContract(ctx, sponsor, date, trxName);
		assert contract != null : "contract may not be null; sponsor=" + sponsor + "; date=" + date;

		return retrieveSalesRepFactCollector(ctx, contract, date, trxName);
	}

	@Override
	public ISalesRefFactCollector retrieveSalesRepFactCollector(
			final Properties ctx,
			final I_C_AdvCommissionCondition contract,
			final Timestamp date,
			final String trxName)
	{
		final I_C_AdvCommissionTerm srfCollectorTermPO = Services.get(ICommissionTermDAO.class).retrieveSalesRepFactCollector(ctx, contract.getC_AdvCommissionCondition_ID(), trxName);

		if (srfCollectorTermPO == null)
		{
			return null;
		}
		final I_C_AdvCommissionType srfCollectorTypePO = srfCollectorTermPO.getC_AdvComSystem_Type().getC_AdvCommissionType();

		final ISalesRefFactCollector srfCollector = (ISalesRefFactCollector)Services.get(ICommissionTypeBL.class).getBusinessLogic(srfCollectorTypePO, srfCollectorTermPO.getC_AdvComSystem_Type());

		return srfCollector;
	}

	@Override
	public MCAdvComRankForecast retrieveRankForecast(final ICommissionContext commissionCtx, final boolean updateIfExists)
	{
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();
		final Timestamp date = commissionCtx.getDate();
		final I_C_AdvComSystem comSystem = commissionCtx.getC_AdvComSystem();
		final I_M_Product product = commissionCtx.getM_Product();
		final Properties ctx = commissionCtx.getCtx();
		final String trxName = commissionCtx.getTrxName();
		final ICommissionType type = commissionCtx.getCommissionType();

		final MCAdvComRankForecast forecast;
		final MCAdvComRankForecast existingForecast = MCAdvComRankForecast.retrieveForSponsor(ctx, sponsor, comSystem, trxName);
		if (existingForecast == null)
		{
			forecast = new MCAdvComRankForecast(ctx, 0, trxName);
			forecast.setC_Sponsor_ID(sponsor.getC_Sponsor_ID());
			forecast.setC_AdvComSystem_ID(comSystem.getC_AdvComSystem_ID());
		}
		else
		{
			if (!updateIfExists)
			{
				return existingForecast;
			}
			forecast = existingForecast;
		}

		final I_C_Sponsor_SalesRep contractSSR = retrieveContractSSR(ctx, sponsor, comSystem, date, trxName);
		if (contractSSR == null)
		{
			SponsorBL.logger.info(sponsor + " has no contract at " + date + "; No forecast to create or update");
			return null;
		}
		final I_C_AdvCommissionCondition contract = contractSSR.getC_AdvCommissionCondition();
		assert contract.getC_AdvComSystem_ID() == comSystem.getC_AdvComSystem_ID() : "contract=" + contract + "; comSystem=" + comSystem;

		final ISalesRefFactCollector srfCollector = retrieveSalesRepFactCollector(ctx, contract, date, trxName);

		//
		// We are going to find the best rank within the last commission periods.
		// We will assert that it is equal to the current rank.
		// We are mainly interested in the latest period in which the best rank was confirmed.
		I_C_AdvCommissionSalaryGroup bestRank = null;
		I_C_Period bestRankLastConfirmed = null;
		I_C_Period bestRankSince = null;

		//
		// We are also interested in the second-best rank which has a period later than 'bestPeriod'.
		// 'secondBestRank' is the rank that 'sponsor' will be demoted to if she doesn't confirm or improve her
		// current rank.
		I_C_AdvCommissionSalaryGroup secondBestRank = null;

		final int periodsLookBack = srfCollector.getPeriodsLookBack(ctx, contract, trxName);

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);
		final List<I_C_Period> lastPeriods = srfBL.retrieveLastPeriods(sponsor, periodsLookBack - 1, date, true);
		assert !lastPeriods.isEmpty() : "lastPeriods must at least include the current period";
		// periods are returned with most recent period first. Therefore, we reverse the list
		Collections.reverse(lastPeriods);

		for (final I_C_Period currentPeriod : lastPeriods)
		{
			BigDecimal turnOver = BigDecimal.ZERO;
			for (final String factName : srfCollector.getTurnoverFactNames(commissionCtx))
			{
				turnOver = turnOver.add(srfBL.retrieveSum(sponsor, factName, currentPeriod, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant));
			}

			final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, currentPeriod.getEndDate(), type, comSystem, product);

			final I_C_AdvCommissionSalaryGroup rankInCurrentPeriod = srfCollector.retrieveSalaryGroup(commissionCtx2, turnOver, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant);

			if (bestRank == null || bestRank.getSeqNo() <= rankInCurrentPeriod.getSeqNo())
			{
				if (bestRank == null || !bestRank.getValue().equals(rankInCurrentPeriod))
				{
					// this is the first occurrence of the current best rank.
					bestRankSince = currentPeriod;
				}

				bestRank = rankInCurrentPeriod;
				bestRankLastConfirmed = currentPeriod;
				secondBestRank = null;
			}
			else
			{
				// current period is after the best period
				assert currentPeriod.getStartDate().after(bestRankLastConfirmed.getStartDate());

				// check if current period and rank are the 2nd-best
				if (secondBestRank == null || secondBestRank.getSeqNo() <= rankInCurrentPeriod.getSeqNo())
				{
					secondBestRank = rankInCurrentPeriod;
				}
			}
		}

		assert bestRank != null;
		assert bestRankLastConfirmed != null;

		final List<I_C_Period> nextPeriods = srfBL.retrieveNextPeriods(sponsor, periodsLookBack, bestRankLastConfirmed.getEndDate(), true);
		assert !nextPeriods.isEmpty() : "nextPeriods must at least include the current period";

		final I_C_Period lastPeriodWithRank = nextPeriods.get(0);

		assert lastPeriodWithRank != null;

		final Collection<I_C_Sponsor_SalesRep> ssr = Services.get(ISponsorDAO.class).retrieveSSRsAtDate(
				InterfaceWrapperHelper.getCtx(sponsor),
				sponsor,
				lastPeriodWithRank.getEndDate(),
				X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP,
				InterfaceWrapperHelper.getTrxName(sponsor));

		forecast.setC_AdvCommissionSalaryGroup_ID(bestRank.getC_AdvCommissionSalaryGroup_ID());
		forecast.setC_Period_Since_ID(bestRankSince.getC_Period_ID());
		forecast.setC_Period_Until_ID(lastPeriodWithRank.getC_Period_ID());

		if (!ssr.isEmpty())
		{
			forecast.setC_BPartner_ID(ssr.iterator().next().getC_BPartner_ID());
		}
		if (secondBestRank != null)
		{
			forecast.setC_AdvComRank_Next_ID(secondBestRank.getC_AdvCommissionSalaryGroup_ID());
		}
		return forecast;
	}

	@Override
	public I_C_Sponsor retrieveParent(final Properties ctx, final I_C_Sponsor sponsor, final Timestamp ts, final String trxName)
	{
		final List<I_C_Sponsor_SalesRep> parents = Services.get(ISponsorDAO.class).retrieveParentLinks(ctx, sponsor.getC_Sponsor_ID(), ts, ts, trxName);

		Check.errorIf(parents.size() > 1, "{} has more than one parent: {} at {}", sponsor, parents, ts);

		if (parents.isEmpty())
		{
			return null;
		}
		return parents.get(0).getC_Sponsor_Parent();
	}

	@Override
	public I_C_Sponsor retrieveRoot(final Properties ctx, final I_C_Sponsor sponsor, final Timestamp date, final String trxName)
	{
		I_C_Sponsor parent = retrieveParent(ctx, sponsor, date, trxName);
		if (parent == null)
		{
			return sponsor;
		}

		I_C_Sponsor current = sponsor;

		while (parent != null)
		{
			parent = retrieveParent(ctx, current, date, trxName);
			if (parent != null)
			{
				current = parent;
			}
		}
		assert current != null;
		return current;
	}

	@Override
	public Map<Timestamp[], I_C_Sponsor> retrieveParents(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final String trxName)
	{
		// define a comparator to order our keys by the validFrom date
		final Comparator<Timestamp[]> validFromComparator = new Comparator<Timestamp[]>()
		{
			@Override
			public int compare(final Timestamp[] o1, final Timestamp[] o2)
			{
				return o1[0].compareTo(o2[0]);
			}
		};

		// put all parent sponsors into the map, using their validFrom and validTo dates as keys
		final SortedMap<Timestamp[], I_C_Sponsor> result = new TreeMap<Timestamp[], I_C_Sponsor>(validFromComparator);

		for (final I_C_Sponsor_SalesRep parentLinkSSR : Services.get(ISponsorDAO.class).retrieveParentLinksSSRs(ctx, sponsor, trxName))
		{
			result.put(new Timestamp[] { parentLinkSSR.getValidFrom(), parentLinkSSR.getValidTo() }, parentLinkSSR.getC_Sponsor_Parent());
		}

		// Iterate the map, identify gaps in the (validFrom - validTo) intervals and fill them with keys that have a
		// null value. The gaps mean that 'sponsor' is a root in those intervals.
		final SortedSet<Timestamp[]> keySet = new TreeSet<Timestamp[]>(validFromComparator);
		keySet.addAll(result.keySet());

		Timestamp validFrom = CommissionConstants.VALID_RANGE_MIN;

		for (final Timestamp[] currentKey : keySet)
		{
			if (validFrom.before(currentKey[0]))
			{
				result.put(new Timestamp[] { validFrom, TimeUtil.addDays(currentKey[0], -1) }, null);
			}

			if (currentKey[1].before(CommissionConstants.VALID_RANGE_MAX))
			{
				validFrom = TimeUtil.addDays(currentKey[1], 1);
			}
		}

		return result;
	}

	/**
	 * Method verifies that the validFrom and validTo dates of the given ssr and its siblings don't overlap.
	 * 
	 * @param
	 * @return a human-readable error message if the dates overlap
	 */
	private String checkSponsorSalesReps(final I_C_Sponsor_SalesRep ssr, final String whereClause)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ssr);
		final String trxName = InterfaceWrapperHelper.getTrxName(ssr);

		final List<I_C_Sponsor_SalesRep> ssrList = Services.get(ISponsorDAO.class).retrieveSSRs(ctx, whereClause, ssr.getC_Sponsor_ID(), trxName);

		Timestamp lastValidTo = null;
		for (final I_C_Sponsor_SalesRep ssrToCheck : ssrList)
		{
			Check.assume(ssrToCheck.isActive(), ssrToCheck + " is active");

			if (lastValidTo == null)
			{
				lastValidTo = ssrToCheck.getValidTo();
				continue;
			}

			final Timestamp currentValidFrom = ssrToCheck.getValidFrom();
			if (currentValidFrom.before(TimeUtil.addDays(lastValidTo, 1)))
			{
				return Msg.getMsg(ctx,
						Messages.SPONSOR_VALIDITY_OVERLAP_3P,
						new Object[] { ssrToCheck.getSponsorSalesRepType(), currentValidFrom, lastValidTo });
			}
			lastValidTo = ssrToCheck.getValidTo();
		}
		// nothing found
		return null;
	}

	public static final String MSG_INCONSISTENT_COMSYSTEM_ROOT_3P = "SSR_InconsistentComSystem_Root";
	public static final String MSG_INCONSISTENT_COMSYSTEM_CHILD_6P = "SSR_InconsistentComSystem_Child_6P";
	public static final String MSG_INCONSISTENT_COMSYSTEM_SALESREP_1P = "SSR_InconsistentComSystem_SalesRep_1P";

	@Override
	public String validateSSR(final I_C_Sponsor_SalesRep ssr)
	{
		final StringBuilder result = new StringBuilder();

		final Properties ctx = InterfaceWrapperHelper.getCtx(ssr);
		final String trxName = InterfaceWrapperHelper.getTrxName(ssr);

		//
		// make sure that there is no overlap in the valid dates
		final String overlapErrorMsg;
		if (ssr.getC_BPartner_ID() > 0)
		{
			overlapErrorMsg = checkSponsorSalesReps(ssr, ISponsorDAO.WHERE_SPONSORSALESREP_SALESREP);
		}
		else
		{
			overlapErrorMsg = checkSponsorSalesReps(ssr, ISponsorDAO.WHERE_SPONSORSALESREP_PARENT_SPONSOR);
		}
		if (!Check.isEmpty(overlapErrorMsg))
		{
			appendToResult(result, overlapErrorMsg);
		}

		if (ssr.getC_Sponsor_Parent_ID() > 0)
		{
			// make sure that this change didn't introduce a cycle in our hierarchy
			final List<List<I_C_Sponsor_SalesRep>> pathsToSSR = checkIfChildSponsor(ssr, ssr.getC_Sponsor_Parent_ID());

			if (!pathsToSSR.isEmpty())
			{
				final String cycleErrorMsg = CommissionTools.mkParentChildMessage(pathsToSSR, ssr, ctx, trxName);
				if (!Check.isEmpty(cycleErrorMsg))
				{
					appendToResult(result, cycleErrorMsg);
				}
			}
		}
		// Check if 'ssr's C_AdvComSystem_ID is consistent with the child-sponsors.

		// We check that all C_SponsorSalesRep records that
		// *are parent links to the sponsor of 'ssr'
		// *have validity dates overlapping with 'ssr'
		// do have the same C_AdvComSystem_ID as 'ssr'.
		final List<I_C_Sponsor_SalesRep> childrenSSRs =
				Services.get(ISponsorDAO.class).retrieveChildrenLinks(ctx, ssr.getC_Sponsor_ID(), ssr.getValidFrom(), ssr.getValidTo(), trxName);

		for (final I_C_Sponsor_SalesRep childSSR : childrenSSRs)
		{
			if (childSSR.getC_AdvComSystem_ID() != ssr.getC_AdvComSystem_ID())
			{
				appendToResult(result, Msg.getMsg(ctx,
						SponsorBL.MSG_INCONSISTENT_COMSYSTEM_CHILD_6P,
						new Object[] {
								ssr.getC_Sponsor().getSponsorNo(),
								ssr.getValidFrom(),
								ssr.getValidTo(),
								ssr.getC_AdvComSystem().getName(),
								childSSR.getC_Sponsor().getSponsorNo(),
								childSSR.getC_AdvComSystem().getName() }));
			}
		}

		// Check for consistency with sales rep links.
		// Note: 'ssr' might be a sales rep link itself, or it might be a parent link.

		// In any case, we check that all C_SponsorSalesRep records that
		// *have the same sponsor as 'ssr
		// *have validity dates overlapping with 'ssr'
		// *reference a sales rep
		// do have the same C_AdvComSystem_ID as 'ssr'.
		final List<I_C_Sponsor_SalesRep> salesRepSSRs =
				Services.get(ISponsorDAO.class).retrieveSalesRepLinksForSponsor(ctx, ssr.getC_Sponsor_ID(), ssr.getValidFrom(), ssr.getValidTo(), trxName);

		for (final I_C_Sponsor_SalesRep salesRepSSR : salesRepSSRs)
		{
			if (salesRepSSR.getC_Sponsor_SalesRep_ID() == ssr.getC_Sponsor_SalesRep_ID())
			{
				continue; // no need to compare our current 'ssr' with itself
			}
			if (salesRepSSR.getC_AdvComSystem_ID() != ssr.getC_AdvComSystem_ID())
			{
				SponsorBL.logger.warn(
						ssr + " has C_AdvComSystem_ID=" + ssr.getC_AdvComSystem_ID()
								+ " but sibling-ssr " + salesRepSSR + " has C_AdvComSystem_ID=" + salesRepSSR.getC_AdvComSystem_ID());

				// the sponsor of 'ssr' has at least one other ssr that references a different com system.
				appendToResult(result, Msg.getMsg(ctx,
						SponsorBL.MSG_INCONSISTENT_COMSYSTEM_SALESREP_1P,
						new Object[] { salesRepSSR.getC_AdvComSystem().getName() }));
			}
		}

		// Check if the change makes 'ssr's sponsor a root sponsor.
		// If it does, check if the sponsor is referenced as root sponsor by a commission system.
		final I_C_Sponsor sponsor = ssr.getC_Sponsor();

		final Map<Timestamp[], I_C_Sponsor> sponsorParents = retrieveParents(ctx, sponsor, trxName);
		for (final Timestamp[] interval : sponsorParents.keySet())
		{
			if (sponsorParents.get(interval) == null)
			{
				// 'ssr.getC_Sponsor()' is a root node at 'interval' (i.e. doesn't have a parent)
				// check if the root is declared as root in any commission system
				final MCAdvComSystem comSystem = MCAdvComSystem.retrieveForRootSponsor(ctx, sponsor, trxName);
				if (comSystem == null)
				{
					appendToResult(result, Msg.getMsg(ctx,
							SponsorBL.MSG_INCONSISTENT_COMSYSTEM_ROOT_3P,
							new Object[] { sponsor.getSponsorNo(), interval[0], interval[1] }));
				}
			}
		}

		return result.toString();
	}

	private void appendToResult(final StringBuilder result, final String msg)
	{
		if (result.length() > 0)
		{
			result.append("\n");
		}
		result.append(msg);
	}

	/**
	 * 
	 * @param sponsorID
	 * @return ssr-lists (paths) that indicate the way in which the sponsor with the given id is a child of this ssr
	 */
	@Override
	public List<List<I_C_Sponsor_SalesRep>> checkIfChildSponsor(
			final I_C_Sponsor_SalesRep ssr,
			final int sponsorID)
	{
		SponsorBL.logger.info("sponsorID=" + sponsorID);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ssr);
		final String trxName = InterfaceWrapperHelper.getTrxName(ssr);

		final List<List<I_C_Sponsor_SalesRep>> paths = new ArrayList<List<I_C_Sponsor_SalesRep>>();

		final List<I_C_Sponsor_SalesRep> parentSSRs = Services.get(ISponsorDAO.class).retrieveParentLinks(ctx, sponsorID, ssr.getValidFrom(), ssr.getValidTo(), trxName);

		for (final I_C_Sponsor_SalesRep potentialChild : parentSSRs)
		{
			final List<I_C_Sponsor_SalesRep> path = new ArrayList<I_C_Sponsor_SalesRep>();
			path.add(potentialChild);

			findPathsToSponsor(potentialChild, ssr.getC_Sponsor_ID(), ssr.getValidFrom(), ssr.getValidTo(), paths, path);
		}
		return paths;
	}

	/**
	 * finds one or more paths from this ssr to a ssr that has the given sponsorID.
	 * 
	 * @param sponsorID
	 * @param validFrom
	 * @param validTo
	 * @param paths
	 * @param currentPath
	 */
	@Override
	public void findPathsToSponsor(
			final I_C_Sponsor_SalesRep potentialChild,
			final int sponsorID,
			final Timestamp validFrom,
			final Timestamp validTo,
			final Collection<List<I_C_Sponsor_SalesRep>> paths,
			final List<I_C_Sponsor_SalesRep> currentPath)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(potentialChild);
		final String trxName = InterfaceWrapperHelper.getTrxName(potentialChild);

		if (potentialChild.getC_Sponsor_ID() == sponsorID)
		{
			// we found a cycle!
			paths.add(currentPath);
			return;
		}

		// load our current parent SSRs
		final List<I_C_Sponsor_SalesRep> parentSSRs = Services.get(ISponsorDAO.class).retrieveParentLinks(
				ctx,
				potentialChild.getC_Sponsor_Parent_ID(), validFrom, validTo,
				trxName);

		// // we are done
		if (parentSSRs.isEmpty())
		{
			return;
		}

		// there is one parent. continue the search with the parent-
		if (parentSSRs.size() == 1)
		{
			final I_C_Sponsor_SalesRep singleParentSSR = parentSSRs.get(0);
			currentPath.add(singleParentSSR);
			findPathsToSponsor(singleParentSSR, sponsorID, validFrom, validTo, paths, currentPath);

			return;
		}

		// there are multiple parents. fork.
		boolean first = true;

		for (final I_C_Sponsor_SalesRep currentParentSSR : parentSSRs)
		{
			final List<I_C_Sponsor_SalesRep> currentPathToUse;
			if (first)
			{
				currentPathToUse = currentPath;
				first = false;
			}
			else
			{
				currentPathToUse = new ArrayList<I_C_Sponsor_SalesRep>(currentPath);
			}

			currentPathToUse.add(currentParentSSR);
			findPathsToSponsor(currentParentSSR, sponsorID, validFrom, validTo, paths, currentPathToUse);
		}
	}
}
