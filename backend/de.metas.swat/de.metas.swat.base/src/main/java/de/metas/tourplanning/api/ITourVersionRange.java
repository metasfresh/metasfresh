package de.metas.tourplanning.api;

import java.time.LocalDate;
import java.util.Set;

import de.metas.tourplanning.model.I_M_TourVersion;

public interface ITourVersionRange
{
	LocalDate getValidFrom();

	LocalDate getValidTo();

	I_M_TourVersion getM_TourVersion();

	/**
	 * Generates planned delivery dates to be used.
	 * 
	 * NOTE: this method is not checking if a given date is a business day or not
	 * 
	 * @return delivery dates
	 */
	Set<LocalDate> generateDeliveryDates();
}
