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
import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.MBPartner;

import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.util.LevelAndSponsor;
import de.metas.interfaces.I_C_BPartner;

/**
 * @author ts
 * 
 */
public interface IHierarchyBL extends ISingletonService
{

	/**
	 * 
	 * @param date the date to use when looking for child sponsors as well as a sponsor's sales rep
	 * @param sponsor the sponsor we start from
	 * @param maxLevel max number of levels to go down
	 *            <p>
	 *            Note: The method doesn't count sponsors that don't have a sales rep at the given <code>date</code> or whose sales rep doesn't have a salary group at all.
	 * @param maxSalesReps max number of sales reps to return (if there are as many sales reps found by descending <code>maxLevel</code> levels).
	 * @param lowestSalaryGroupValue only bPartners whose {@link I_C_BPartner#C_AdvCommissionSalaryGroup_ID} has at least the given value are added to the result list.
	 *            <p>
	 *            Note: salary groups are ordered by {@link I_C_AdvCommissionSalaryGroup#COLUMNNAME_SeqNo}.
	 * @param srfStatus
	 * @return the bPartners that were found, in the order of the finding (highest in hierarchy first)
	 */
	List<I_C_Sponsor> findSponsorsInDownline(
			Timestamp date, I_C_Sponsor sponsor,
			int maxLevel, int maxSalesReps,
			I_C_AdvComSystem comSystem,
			String lowestSalaryGroupValue,
			String srfStatus);

	/**
	 * Ascends the upline from a given sponsor and returns the sponsors that have a sales rep at the given <code>date</code>.
	 * 
	 * @param date the date to use when looking for parent sponsors as well as a sponsor's sales rep
	 * @param sponsor the sponsor we start from
	 * @param maxLevel max number of levels to go up
	 * @param maxSalesReps max number of sales reps to return (if there are as many sales reps found by ascending <code>maxLevel</code> levels).
	 * @param lowestSalaryGroupValue may be <code>null</code> only bPartners whose {@link I_C_BPartner#C_AdvCommissionSalaryGroup_ID} has at least the given value are added to the result list.
	 *            <p>
	 *            Note: salary groups are ordered by {@link I_C_AdvCommissionSalaryGroup#COLUMNNAME_SeqNo}.
	 * @param srfStatus
	 * @return the sponsors that were found, in the order of the finding (lowest in hierarchy first)
	 */
	List<I_C_Sponsor> findSponsorsInUpline(
			Timestamp date,
			I_C_Sponsor sponsor,
			int maxLevel,
			int maxSalesReps,
			I_C_AdvComSystem comSystem,
			String lowestSalaryGroupValue,
			String srfStatus);

	List<I_C_Sponsor> findSponsorsInUpline(Timestamp date, MBPartner customer, int maxLevel, int maxSalesReps, boolean skipNonSalesReps);

	/**
	 * For a given sponsor, this method retrieves a list of level-sponsor pairs. The list contains all sponsors in the downline (including those whose rank is below the given minimum rank).
	 * 
	 * @param date the date for which the hierachy is browsed
	 * @param sponsor the sponsor whose downline is retrieved. It will be in the first result list item, with a level of 0.
	 * @param lowestSalaryGroupValue the minimum rank a sponsor must have to be counted a level. If a given sponsor's rank is below this parameter, the sponsor is considered to have the same level as
	 *            its parent.
	 * 
	 * @return
	 */
	List<LevelAndSponsor> retrieveDownLine(
			Timestamp date,
			I_C_Sponsor sponsor,
			I_C_AdvComSystem comSystem,
			String lowestSalaryGroupValue);
}
