/**
 * 
 */
package de.metas.tourplanning.api;

import java.time.LocalDate;
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
	List<ITourVersionRange> retrieveTourVersionRanges(I_M_Tour tour, LocalDate dateFrom, LocalDate dateTo);
}
