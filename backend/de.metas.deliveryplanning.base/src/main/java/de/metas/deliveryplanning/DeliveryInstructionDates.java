/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/**
 * The six date/time fields shared by the delivery instruction and an allocated planning, resolved to their
 * FINAL value by the caller (the service layer, which alone may read the order/schedule or apply a
 * fill-if-empty rule) and written verbatim by the repository - a repository decides nothing about these values,
 * it only persists what it is handed.
 */
@Value
@Builder
class DeliveryInstructionDates
{
	@Nullable Timestamp etd;
	@Nullable Timestamp eta;
	@Nullable Timestamp atd;
	@Nullable Timestamp ata;
	@Nullable String loadingTime;
	@Nullable String deliveryTime;
}
