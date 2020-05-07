/**
 * 
 */
package de.metas.tourplanning.api;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;
import de.metas.tourplanning.model.I_M_TourVersionLine;

/**
 * @author cg
 * 
 */
public interface ITourDAO extends ISingletonService
{
	List<I_M_Tour> retriveAllTours(Properties ctx);

	List<I_M_TourVersion> retrieveTourVersions(I_M_Tour tour);

	List<I_M_TourVersionLine> retrieveTourVersionLines(I_M_TourVersion tourVersion);

	/**
	 * Retrieve Tour Version ranges for given [<code>dateFrom</code>, <code>dateTo</code>] range.
	 * 
	 * NOTE: first range will start with <code>dateFrom</code> (in case the version was valid before) and last range will end with <code>dateTo</code>.
	 * 
	 * @param tour
	 * @param dateFrom
	 * @param dateTo
	 * @return tour version ranges
	 */
	List<ITourVersionRange> retrieveTourVersionRanges(I_M_Tour tour, Date dateFrom, Date dateTo);
}
