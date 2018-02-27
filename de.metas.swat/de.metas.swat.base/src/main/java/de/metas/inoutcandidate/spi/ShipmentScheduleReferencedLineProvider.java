package de.metas.inoutcandidate.spi;

import org.springframework.stereotype.Service;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Implementations create {@link ShipmentScheduleReferencedLine} instances for shipment schedules that reference a particular table.
 * They are automatically discovered and added to {@link ShipmentScheduleReferencedLineProvider} on startup.
 * <p>
 * Please annotate your implementations also with {@link Service} to enable auto detection.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ShipmentScheduleReferencedLineProvider
{
	/**
	 * @return he name of the table (as references by {@link I_M_ShipmentSchedule#COLUMN_AD_Table_ID}) this implementation is in charge of.
	 */
	String getTableName();

	/**
	 * Provide a {@link ShipmentScheduleReferencedLine} that is based on the given {@code shipmentSchedule}'s references document(line).<br>
	 * Please don't call this method directly. It is called from {@link ShipmentScheduleReferencedLineFactory#createFor(I_M_ShipmentSchedule)}.
	 *
	 * @param shipmentSchedule may not be {@code null}.
	 * @return
	 */
	ShipmentScheduleReferencedLine provideFor(I_M_ShipmentSchedule shipmentSchedule);
}
