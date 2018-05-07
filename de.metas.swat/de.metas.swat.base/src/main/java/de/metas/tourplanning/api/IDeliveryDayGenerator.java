package de.metas.tourplanning.api;

import java.time.LocalDate;

import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour;

/**
 * {@link I_M_DeliveryDay} generator
 * 
 * @author tsa
 *
 */
public interface IDeliveryDayGenerator
{
	int getCountGeneratedDeliveryDays();

	void setM_Tour(final I_M_Tour tour);

	void setDateTo(final LocalDate dateTo);

	void setDateFrom(final LocalDate dateFrom);

	void generate(String trxName);
}
