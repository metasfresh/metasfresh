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

	void setDateTo(final Date dateTo);

	void setDateFrom(final Date dateFrom);

	void generate(String trxName);
}
