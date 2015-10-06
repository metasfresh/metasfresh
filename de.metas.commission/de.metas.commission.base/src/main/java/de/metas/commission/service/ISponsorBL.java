package de.metas.commission.service;

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


import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.commission.custom.type.ISalesRefFactCollector;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvComRankForecast;
import de.metas.commission.model.X_C_AdvCommissionType;

public interface ISponsorBL extends ISingletonService
{

	I_C_Period retrieveCommissionPeriod(I_C_Sponsor sponsor, Timestamp date);

	I_C_Period retrieveCommissionPeriod(Properties ctx, I_C_Sponsor sponsor, Timestamp date, String trxName);

	I_C_Period retrieveCommissionPeriod(Properties ctx, I_C_AdvCommissionCondition contract, Timestamp date, String trxName);

	/**
	 * Retrieves a date, based on the value of <code>retroactive</code>
	 * 
	 * @param sponsor if retroactive = {@link X_C_AdvCommissionType#RETROACTIVEEVALUATION_Provisionsperiode} , then the begin of the sponsor's current commission period is used.
	 * @param date
	 * @param retroactive <ul>
	 *            <li>
	 *            {@link X_C_AdvCommissionType#RETROACTIVEEVALUATION_Provisionsperiode} : Begin of the sponsor's period which <code>date</code> is in</li>
	 *            <li>{@link X_C_AdvCommissionType#RETROACTIVEEVALUATION_Tag}: Same day as <code>date</code>, 0:00</li>
	 *            <li>{@link X_C_AdvCommissionType#RETROACTIVEEVALUATION_Keine}: <code>date</code> without change</li>
	 *            <li></li>
	 *            </ul>
	 * @return
	 */
	Timestamp retrieveDateFrom(I_C_Sponsor sponsor, I_C_AdvComSystem_Type comSystemType, Timestamp date);

	Timestamp retrieveDateTo(I_C_Sponsor sponsor, I_C_AdvComSystem_Type comSystemType, Timestamp date);

	/**
	 * Implemented for  US1026:  aenderung Verguetungsplan (2011010610000028), R01A06 <br>
	 * <br>
	 * Modified for 02527: Provisionsabrechnungbeleg Teil 2 (2012022910000062)<br>
	 * <br>
	 * This method processes the Change of the Flag IsManualRank. If it is changed this method sets the actual Manual rank and if the Parameter <tt>processRanks</tt> is set, it also launches the
	 * method OnManualRankChange
	 * 
	 * 
	 * @param sponsor The Sponsor whose rank will be changed
	 * @param saveNewRank defines if the Rank should be saved immediately
	 * @param oldSalaryGroup needed to save the Rank
	 * 
	 */
	void onIsManualRankChange(I_C_Sponsor sponsor, final boolean saveNewRank, final int oldSalaryGroupId);

	/**
	 * Implemented for  02527. This Method processes the actual manual rank change, which means the old manual rank (If there is one) will be terminated, and the new one (if there is one) will be
	 * created. Terminating a rank means creating a new SRF-Record for the Current date with the C_AdvCommissionSalaryGroup_ID = 0, which means that there is no manual rank for this sponsor.
	 * 
	 * conditions for terminating / creating:
	 * <ul>
	 * <li>If isManualRank has just been set to 'Y', there's no previous rank, therefore nothing will be terminated.
	 * <li>If isManualRank has just been set to 'N', there won't be a new rank, therefore nothing will be created.
	 * <li>If a rank is used in more then one commission system, it will be created/terminated for each of them.
	 * <li>There won't be seperate SRF-Records for a Termination AND a creation of a rank for one Sponsor/System/Date. in that case the terminating SalesRepFact record will simply be overwritten by
	 * the creating one
	 * <ul>
	 * 
	 * @param sponsor The sponsor that's been changed
	 * @param isIsManualRankChanged Indicates if the flag <tt>isManualRank</tt> has also changed
	 * @param oldSalaryGroupId Defines the previous SalaryGroupId, so the SRF-records of the previous ManualRank can be terminated
	 */
	public void onManualRankChange(final I_C_Sponsor sponsor, final boolean isIsManualRankChanged, final int oldSalaryGroupId);

	/**
	 * Implemented for  02527. This Method will create an AttributeSetInstance and fill it with values. Will include:
	 * <ul>
	 * <li>current rank (At the given date)
	 * <li>top rank of the previous 12 months (from the given date)
	 * <li>Rank at the end of the given period
	 * <ul>
	 * <br>
	 * 
	 * @param sponsor The sponsor whose ranks will be recorded
	 * @param calculationPeriod The Period of a commissionCaluculation
	 * @param currentDate date which is used to retrieve the ranks
	 */
	public I_M_AttributeSetInstance createSalaryGroupAttributeSet(final I_C_Sponsor sponsor, final I_C_Period calculationPeriod, final Timestamp currentDate);

	public static final String SYSCONFIG_RankAttributeSet = "de.metas.commission.RankAttributeSet";

	I_C_BPartner retrieveSalesRepAt(Properties ctx, Timestamp date, I_C_Sponsor sponsor, boolean throwEx, String trxName);

	I_C_AdvCommissionSalaryGroup retrieveRank(Properties ctx, I_C_Sponsor sponsor, Timestamp date, String status, String trxName);

	I_C_AdvCommissionSalaryGroup retrieveRank(Properties ctx, I_C_Sponsor sponsor, I_C_AdvComSystem comSystem, Timestamp date, String status, String trxName);

	ISalesRefFactCollector retrieveSalesRepFactCollector(Properties ctx, I_C_Sponsor sponsor, Timestamp date, String trxName);

	ISalesRefFactCollector retrieveSalesRepFactCollector(Properties ctx, I_C_AdvCommissionCondition contract, Timestamp date, String trxName);

	MCAdvComRankForecast retrieveRankForecast(ICommissionContext commissionCtx, boolean updateIfExists);

	I_C_Sponsor retrieveParent(Properties ctx, I_C_Sponsor sponsor, Timestamp ts, String trxName);

	/**
	 * Retrieves a given sponsor's parents as a mapping [ValidFrom, ValidTo]=>Parent-Sponsor.
	 * 
	 * @param ctx
	 * @param cSponsor
	 * @param getTrxName
	 * @return
	 */
	Map<Timestamp[], I_C_Sponsor> retrieveParents(Properties ctx, I_C_Sponsor cSponsor, String getTrxName);

	I_C_Sponsor retrieveRoot(Properties ctx, I_C_Sponsor sponsor, Timestamp date, String trxName);

	/**
	 * Returns the contract for the given sponsor at the given date.
	 * 
	 * @param ctx
	 * @param sponsor
	 * @param date
	 * @param trxName
	 * @return
	 */
	I_C_AdvCommissionCondition retrieveContract(Properties ctx, I_C_Sponsor sponsor, Timestamp date, String trxName);

	/**
	 * Returns the sponsor sales rep that specifies the given sponsor's commission contract at the given date
	 * 
	 * @param ctx
	 * @param sponsor
	 * @param date
	 * @param trxName
	 * @return
	 */
	I_C_Sponsor_SalesRep retrieveContractSSR(Properties ctx, I_C_Sponsor sponsor, Timestamp date, String trxName);

	/**
	 * Returns the contract for the given sponsor and the given comSystem at the given date. If there is no contract for sponsor, comSystem and date, the contract for "orphaned" sponsors is returned.
	 * If no commission contract has been configured for orphaned sponsors (i.e. no contract with {@link I_C_AdvCommissionCondition#COLUMNNAME_IsDefaultForOrphandedSponsors}='Y'), then an exception is
	 * thrown.
	 * 
	 * @param ctx
	 * @param sponsor
	 * @param comSystem commission system of the contract to retrieve. May be <code>null</code>.
	 * @param date
	 * @param trxName
	 * @return
	 */
	I_C_AdvCommissionCondition retrieveContract(Properties ctx, I_C_Sponsor sponsor, I_C_AdvComSystem comSystem, Timestamp date, String trxName);

	String validateSSR(I_C_Sponsor_SalesRep ssr);

	void findPathsToSponsor(
			I_C_Sponsor_SalesRep potentialChild,
			int sponsorID,
			Timestamp validFrom,
			Timestamp validTo,
			Collection<List<I_C_Sponsor_SalesRep>> paths,
			List<I_C_Sponsor_SalesRep> currentPath);

	List<List<I_C_Sponsor_SalesRep>> checkIfChildSponsor(I_C_Sponsor_SalesRep ssr, int sponsorID);

	/**
	 * Note: The same commission type can be used only once for the same sponsor
	 * 
	 * @param commissionCtx
	 * @param assertTermExists if true and there is no term to be found, an {@link AdempiereException} is thrown (msg, not localized, info is not aimed for the enduser)
	 * 
	 * @return term for the given commission type to be applied to the given sponsor and product
	 */
	I_C_AdvCommissionTerm retrieveTerm(ICommissionContext commissionCtx, boolean assertTermExists);

	I_C_AdvComSystem retrieveComSystem(ICommissionContext comCtx);
}
