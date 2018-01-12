package de.metas.inoutcandidate.spi;

import java.sql.Timestamp;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.event.commons.DocumentLineDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
 * Contains data about the document a given {@link I_M_ShipmentSchedule} references via its {@code AD_Table_ID} and {@code Reference_ID} columns.
 * Instances are generally created by {@link ShipmentScheduleReferencedLineFactory}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
@Builder
public class ShipmentScheduleReferencedLine
{
	@NonNull
	Integer groupId;

	Timestamp deliveryDate;

	Timestamp preparationDate;

	/**
	 * Might be zero.
	 */
	@NonNull
	Integer shipperId;

	@NonNull
	Integer warehouseId;

	@NonNull
	DocumentLineDescriptor documentLineDescriptor;
}
