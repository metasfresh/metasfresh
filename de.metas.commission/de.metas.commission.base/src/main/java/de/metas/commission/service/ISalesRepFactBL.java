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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Period;

import de.metas.commission.model.I_C_AdvComRankCollection;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.X_C_AdvComSalesRepFact;

public interface ISalesRepFactBL extends ISingletonService
{

	/**
	 * <b>IMPORTANT</b> method retrieves the rank from the sales rep facts, thus ignoring the manual rank that might be set in the sponsor!!
	 * 
	 * @param sponsor
	 * @param date
	 * @param srfStatus
	 * @return
	 */
	I_C_AdvCommissionSalaryGroup retrieveSalaryGroup(Properties ctx, I_C_Sponsor sponsor, I_C_AdvComSystem comSystem, Timestamp date, String statusProvRelevant, String trxName);

	/**
	 * Tells if the sponsor's rank is at least the given rank.
	 * 
	 * @param rankValue the value of the rank to chack against
	 * @param sponsor the sponsor in question
	 * @param srfStatus the ranks status, e.g. {@link X_C_AdvComSalesRepFact#STATUS_Prognose}.
	 * @param date the date for which the check is performed
	 * @return
	 */
	boolean isRankGE(I_C_AdvComSystem comSystem, String rankValue, I_C_Sponsor sponsor, String srfStatus, Timestamp date);

	public boolean isChangeToRank(I_C_AdvComRankCollection rankCollection, I_C_AdvComSystem comSystem,
			String rankValue, I_C_Sponsor sponsor, String status, int periodId, boolean ge);

	boolean isInSGToLookFor(List<I_C_AdvCommissionSalaryGroup> sgsToLookfor, int salaryGroupId);

	I_C_AdvComSalesRepFact addAmtToSrf(I_C_Sponsor sponsor, I_C_AdvComSystem comSystem,
			String factName,
			String srfStatus, I_C_Period period, Timestamp date,
			I_C_AdvCommissionFact volumeOfSalesFact);

	I_C_AdvComSalesRepFact subtractAmtFromSrf(I_C_Sponsor sponsor, I_C_AdvComSystem comSystem,
			String factName,
			String srfStatus, I_C_Period period, Timestamp date,
			I_C_AdvCommissionFact volumeOfSalesFact);

	void resetFact(I_C_Sponsor sponsor, I_C_AdvComSystem comSystem, String factName, String srfStatus, I_C_Period period);

	BigDecimal retrieveSumUntilComFact(I_C_AdvComSalesRepFact salesRepFact,
			I_C_AdvCommissionFact comFact, I_C_Period period, String comFactStatus);

	I_C_AdvComSalesRepFact createOrUpdateRank(I_C_Sponsor sponsor, I_C_AdvComSystem comSystem, String srfStatus, int periodId, Timestamp date, int newRankId);

	/**
	 * 
	 * @param sponsor
	 * @param date
	 * @param srfStatus
	 * @param periodId
	 * @param returnFirst if the best rank is achieved in more than one month, and this paramter is <code>true</code>, then the srf of the first month is returned. Otherwise the srf of the last month
	 *            is returned.
	 * @return
	 */
	Map.Entry<I_C_AdvComSalesRepFact, I_C_AdvCommissionSalaryGroup> retrieveBestRank(
			I_C_Sponsor sponsor, I_C_AdvComSystem comSystem,
			Timestamp date, String srfStatus, int periodId, boolean returnFirst);

	boolean retrieveIsCompress(final ICommissionContext commissionCtx, final I_C_Period period, final boolean evalOnly);

	void copyFactReferences(I_C_AdvComSalesRepFact fact, I_C_Period period, String[] factName, String[] srfStatus);

	BigDecimal retrieveSum(I_C_Sponsor sponsor, String srfName, I_C_Period period, String... srfStatus);

	List<I_C_AdvComSalesRepFact> retrieveFacts(I_C_Sponsor sponsor, I_C_AdvComSystem comSystem, I_C_Period period, String srfName, String... srfStatus);

	/**
	 * Retrieves the <code>periodsLookBack</code> last periods, based on the given <code>date</code> and the calendar set in <code>sponsor</code>'s commission contract. The list is ordered by the
	 * periods' start date, most recent first.
	 * 
	 * @param sponsor
	 * @param periodsLookBack
	 * @param date
	 * @param includeCurrent if <code>true</code>, then the first period is the period that <code>date</code> lies in. Otherwise, the first period is the latest period whose end date is before
	 *            <code>date</code>.
	 * @return a list of periods, ordered by the periods' start date, most recent first
	 */
	List<I_C_Period> retrieveLastPeriods(I_C_Sponsor sponsor, int periodsLookBack, Timestamp date, boolean includeCurrent);

	List<I_C_Period> retrieveNextPeriods(I_C_Sponsor sponsor, int periodsLookForward, Timestamp date, boolean includeCurrent);

	List<Map.Entry<I_C_Period, I_C_AdvComSalesRepFact>> retrieveLastPerPeriod(I_C_Sponsor sponsor, I_C_AdvComSystem comSystem, int periodsLookBack, Timestamp date, String factName, String srfStatus);
}
