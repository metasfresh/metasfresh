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
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.ISingletonService;

import de.metas.commission.exception.SponsorConfigException;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.X_C_Sponsor_SalesRep;

public interface ISponsorDAO extends ISingletonService
{
	// public static final String WHERE_PARENT_SPONSOR =
	public static final String WHERE_SPONSORSALESREP_PARENT_SPONSOR =
			I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + "=? AND "
					+ I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType + "='" + X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_Hierarchie + "'";

	// public static final String WHERE_SALESREP =
	public static final String WHERE_SPONSORSALESREP_SALESREP =
			I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + "=? AND "
					+ I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType + "='" + X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP + "'";

	List<I_C_Sponsor_SalesRep> retrieveForBPartner(org.compiere.model.I_C_BPartner bPartner);

	List<I_C_Sponsor_SalesRep> retrieveSalesRepSSRs(I_C_Sponsor sponsor);

	I_C_BPartner getCustomer(I_C_Sponsor sponsor, Timestamp date);

	Collection<I_C_Sponsor> retrieveChildren(I_C_Sponsor sponsor, Timestamp ts);

	/**
	 * 
	 * @param ts
	 * @param comSystemId optional if >0, then only those children are returned, whose parent-link-ssr has the given <code>C_AdvComSystem_ID</code>.
	 * @return
	 */
	Collection<I_C_Sponsor> retrieveChildrenBetween(I_C_Sponsor sponsor, Timestamp dateFrom, Timestamp dateTo, int comSystemId);

	/**
	 * Returns all sponsors that had the the given bpartner as a salesrep <b>some time before</b> the given date.
	 * 
	 * @param salesRep
	 * @param ts
	 * @return
	 */
	List<I_C_Sponsor> retrieveForSalesRepUntil(I_C_BPartner salesRep, Timestamp dateTo);

	/**
	 * Returns all sponsors that had the the given bpartner as a salesrep <b>at</b> the given date.
	 * 
	 * @param salesRep
	 * @param ts
	 * @return
	 */
	List<I_C_Sponsor> retrieveForSalesRepAt(org.compiere.model.I_C_BPartner salesRep, Timestamp ts);

	/**
	 * 
	 * @param ctx
	 * @param customer
	 * @param throwEx if <code>true</code> and there is no sponsor referencing the given 'customer', the method throws a {@link SponsorConfigException}. Otherwise, the method returns <code>null</code>
	 *            .
	 * @param trxName
	 * @return
	 */
	I_C_Sponsor retrieveForBPartner(Properties ctx, org.compiere.model.I_C_BPartner customer, boolean throwEx, String trxName);

	/**
	 * 
	 * @param customer
	 * @param throwEx if <code>true</code> and there is no sponsor referencing the given 'customer', the method throws a {@link SponsorConfigException}. Otherwise, the method returns <code>null</code>
	 *            .
	 * @return
	 */
	I_C_Sponsor retrieveForBPartner(org.compiere.model.I_C_BPartner customer, boolean throwEx);

	I_C_Sponsor createNewForCustomer(org.compiere.model.I_C_BPartner bPartner);

	List<I_C_Sponsor_SalesRep> retrieveSSRsAtDate(Properties ctx, I_C_Sponsor sponsor, Timestamp date, String ssrType, String trxName);

	List<I_C_Sponsor_SalesRep> retrieveSSRs(Properties ctx, String whereClause, int sponsorId, String trxName);

	/**
	 * 
	 * @param sponsor
	 * @return all SSRs that belong to the given sponsor and have a C_Sponsor_Parent_ID>0
	 */
	List<I_C_Sponsor_SalesRep> retrieveParentLinksSSRs(I_C_Sponsor sponsor);

	/**
	 * Loads the SSRs that
	 * <ul>
	 * <li>Belong to the given sponsorID <b>and</b></li>
	 * <li>have a C_Sponsor_Parent_ID value of their own <b>and</b></li>
	 * <li>whose ValidFrom and ValidTo values overlap with the given values</li>
	 * </ul>
	 * 
	 */
	List<I_C_Sponsor_SalesRep> retrieveSalesRepLinks(Properties ctx, int bPartnerId, Timestamp validFrom, Timestamp validTo, String trxName);

	/**
	 * select those SSRs whose parent sponsor is this ssr's sponsor at the given time.
	 * 
	 * @param date
	 * @return
	 */
	Collection<I_C_Sponsor_SalesRep> retrieveChildren(I_C_Sponsor_SalesRep sponsorSalesRep, Timestamp date);

	/**
	 * Loads the SSRs that
	 * <ul>
	 * <li>Belong have the given sponsorParentID as their C_Sponsor_Parent_ID <b>and</b></li>
	 * <li>whose ValidFrom and ValidTo values overlap with the given values</li>
	 * </ul>
	 * 
	 */
	List<I_C_Sponsor_SalesRep> retrieveChildrenLinks(Properties ctx, int sponsorParentID, Timestamp validFrom, Timestamp validTo, String trxName);

	List<I_C_Sponsor_SalesRep> retrieveSalesRepSSRs(Properties ctx, I_C_Sponsor sponsor, String trxName);

	List<I_C_Sponsor_SalesRep> retrieveParentLinksSSRs(Properties ctx, I_C_Sponsor sponsor, String trxName);

	List<I_C_Sponsor_SalesRep> retrieveSalesRepLinksForSponsor(Properties ctx, int sponsorId, Timestamp validFrom, Timestamp validTo, String trxName);

	/**
	 * Validate given {@link I_C_Sponsor_SalesRep} record
	 * 
	 * @param sponsorSalesRep
	 */
	void validate(I_C_Sponsor_SalesRep sponsorSalesRep);

	/**
	 * Loads the SSRs that
	 * <ul>
	 * <li>Belong to the given sponsorID <b>and</b></li>
	 * <li>have a C_Sponsor_Parent_ID value of their own <b>and</b></li>
	 * <li>whose ValidFrom and ValidTo values overlap with the given values</li>
	 * </ul>
	 * 
	 */
	List<I_C_Sponsor_SalesRep> retrieveParentLinks(Properties ctx, int sponsorID, Timestamp validFrom, Timestamp validTo, String trxName);

	List<I_C_Sponsor_SalesRep> retrieveSalesRepsForConditionSet(Properties ctx, Set<Integer> conditionIDs, String trxName);

	List<I_C_Sponsor_SalesRep> retrieveSponsorSalesRepsForCondition(Properties ctx, I_C_AdvCommissionCondition condition, String trxName);
}
