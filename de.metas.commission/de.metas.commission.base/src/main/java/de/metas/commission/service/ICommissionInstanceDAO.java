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
import org.compiere.model.PO;

import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_Sponsor;

public interface ICommissionInstanceDAO extends ISingletonService
{

	List<IAdvComInstance> retrieveFor(final Object poLine, final I_C_Sponsor customerSponsor, final I_C_Sponsor salesRepSponsor);

	IAdvComInstance retrieveNonClosedFor(final Object poLine, final I_C_Sponsor customerSponsor, final I_C_Sponsor salesRepSponsor, final int termId);

	BigDecimal retrieveBalanceQty(final IAdvComInstance instance, final String status);

	/**
	 * 
	 * @param status
	 * @return the sum of all commission amounts for the given status or <code>null</code> if there are no facts with that status
	 */
	BigDecimal retrieveBalanceAmt(final IAdvComInstance instance, final String status);

	BigDecimal retrieveBalancePointsSum(final IAdvComInstance instance, final String status);

	/**
	 * Computes the sum of all commission points for the given status or <code>null</code> if there are no facts with that status.
	 * 
	 * @param status
	 * @return
	 */
	BigDecimal retrieveBalancePoints(final IAdvComInstance instance, final String status);

	List<IAdvComInstance> retrieveForCustomer(final I_C_Sponsor customer, final Timestamp dateFrom, final Timestamp dateTo, final int commissionTermId);

	/**
	 * 
	 * @param salesRep
	 * @param dateFrom
	 * @param dateTo
	 * @param commissionTermId filter for given C_AdvCommissionTerm_ID. If <=0, no filtering takes place
	 * @return
	 */
	List<IAdvComInstance> retrieveForSalesRep(final I_C_Sponsor salesRep, final Timestamp dateFrom, final Timestamp dateTo, final int commissionTermId);

	/**
	 * 
	 * @param ctx
	 * @param period
	 * @param commissionTermId may be 0 if no filtering by commission term id is desired
	 * @param onlyForReview if <code>true</code>, the method returns only instances that have <code>LevelForeCast!=LevelCalculation</code>.
	 * @param trxName
	 * @return
	 */
	List<IAdvComInstance> retrieveToCalcUnprocessed(final Properties ctx, final I_C_Period period, final I_C_AdvComSystem_Type comSystemType, final boolean onlyForReview,
			final String trxName);

	/**
	 * Retrieves all instances that have an open balance and have been started before the given period's end date.
	 * 
	 * @param periodId
	 * @param commissionTerm
	 * @return a map of C_BPartner_ID to list of instances. C_BPartner_ID is the ID of the sales rep that will reveive the commission for the instances. If ID=0, then there is no sales rep BPartner
	 *         for the given Sponsors.
	 */
	Map<Integer, List<IAdvComInstance>> retrieveForCalculation(final Properties ctx, final I_C_Period period, final I_C_AdvComSystem_Type comSystemType, final String trxName);

	/**
	 * Returns all instances that contain the given po, either as instance trigger or as a commission fact.
	 * 
	 * @param po
	 * @return
	 */
	List<IAdvComInstance> retrieveAllFor(final Object po, final I_C_AdvComSystem_Type comSystemType);

	/**
	 * Returns all instances that were started by the given po, ordered by their commission term and hierarchy level
	 * 
	 * @param po
	 * @return
	 */
	List<IAdvComInstance> retrieveNonClosedForInstanceTrigger(final Object po, final int comSystemTypeId);

	/**
	 * Returns all instances that were started by the given po, ordered by their hierarchy level.
	 * 
	 * 
	 * @param po
	 * @return
	 */
	List<IAdvComInstance> retrieveFor(final PO po, final I_C_AdvComSystem_Type comSystemSystemType);

	/**
	 * Finds the instance whose sales rep is lowest in the hierarchy.
	 * 
	 * @param po
	 * @return
	 */
	IAdvComInstance retriveFirstLine(final IAdvComInstance instance);

	/**
	 * Creates a new instance for the given po. Client and Org-ID are taken from the PO.
	 * <ul>
	 * <li>Only sets <code>Record_ID</code> and <code>AD_Table_ID</code>.</li>
	 * <li>Doesn't save the new instance</li>
	 * </ul>
	 * 
	 * @param candidate
	 * @return
	 */
	IAdvComInstance createNew(final Object po);

	/**
	 * 
	 * @param instance
	 * @return
	 * @deprecated use {@link #retrievePO(IAdvComInstance, Class)}
	 */
	@Deprecated
	PO retrievePO(final IAdvComInstance instance);

	<T> T retrievePO(final IAdvComInstance instance, Class<T> clazz);

}
