package de.metas.materialtracking;

/*
 * #%L
 * de.metas.materialtracking
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

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_PP_Order;

import de.metas.materialtracking.model.I_M_InOutLine;

public interface IMaterialTrackingPPOrderBL extends ISingletonService
{
	/**
	 *
	 * @param ppOrder
	 * @return <code>true</code> if the given <code>ppOrder</code> is a quality inspection (Waschprobe), <code>false</code> if not
	 */
	boolean isQualityInspection(I_PP_Order ppOrder);

	/**
	 * Asserts given manufacturing order is a Quality Inspection order
	 *
	 * @param ppOrder
	 */
	void assertQualityInspectionOrder(I_PP_Order ppOrder);

	/**
	 *
	 * @return filter used to keep only Quality Inspection manufacturing orders
	 */
	IQueryFilter<I_PP_Order> getQualityInspectionFilter();

	/**
	 * task 09546: I moved this method here because the date of Production of a PP_Order might be needed in different places.
	 *
	 * @return production date; never return null
	 */
	Timestamp getDateOfProduction(I_PP_Order ppOrder);

	/**
	 * Retrieve the inout lines that were issued with the given PP_Order.
	 * 
	 * @param ppOrder
	 * @return the found inout lines
	 */
	List<I_M_InOutLine> retrieveIssuedInOutLines(I_PP_Order ppOrder);

}
