/**
 *
 */
package de.metas.tourplanning.api;

import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;
import de.metas.tourplanning.model.I_M_TourVersionLine;
import de.metas.tourplanning.model.TourId;
import de.metas.util.ISingletonService;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

/**
 * @author cg
 *
 */
public interface ITourDAO extends ISingletonService
{
	I_M_Tour getById(TourId tourId);

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

	ITourVersionRange generateTourVersionRange(I_M_TourVersion tourVersion, LocalDate validFrom, LocalDate validTo);
}
