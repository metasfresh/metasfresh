package de.metas.tourplanning.api;

import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;

import java.time.LocalDate;

/**
 * {@link I_M_DeliveryDay} generator
 *
 * @author tsa
 */
public interface IDeliveryDayGenerator
{
	int getCountGeneratedDeliveryDays();

	void setM_Tour(final I_M_Tour tour);

	void setDateTo(final LocalDate dateTo);

	void setDateFrom(final LocalDate dateFrom);

	void generate(String trxName);

	void generateForTourVersion(String trxName, I_M_TourVersion tourVersion);
}
