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
import java.util.Set;

import de.metas.tourplanning.model.I_M_TourVersion;

public interface ITourVersionRange
{
	Date getValidFrom();

	Date getValidTo();

	I_M_TourVersion getM_TourVersion();

	/**
	 * Generates planned delivery dates to be used.
	 * 
	 * NOTE: this method is not checking if a given date is a business day or not
	 * 
	 * @return delivery dates
	 */
	Set<Date> generateDeliveryDates();
}
