package org.eevolution.api;

import java.time.Duration;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import de.metas.common.util.time.SystemTime;
import org.eevolution.model.I_PP_Order;

import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class ActivityControlCreateRequest
{
	@NonNull I_PP_Order order;
	@NonNull PPOrderRoutingActivity orderActivity;
	@NonNull ZonedDateTime movementDate;
	@NonNull Quantity qtyMoved;
	@NonNull Duration durationSetup;
	@NonNull Duration duration;

	@Builder
	private ActivityControlCreateRequest(
			@NonNull final I_PP_Order order,
			@NonNull final PPOrderRoutingActivity orderActivity,
			@Nullable final ZonedDateTime movementDate,
			@NonNull final Quantity qtyMoved,
			@NonNull final Duration durationSetup,
			@NonNull final Duration duration)
	{
		Check.assume(!durationSetup.isNegative(), "durationSetup >= 0 but it was {}", durationSetup);
		Check.assume(!duration.isNegative(), "duration >= 0 but it was {}", duration);

		this.order = order;
		this.orderActivity = orderActivity;
		this.movementDate = movementDate != null ? movementDate : SystemTime.asZonedDateTime();
		this.qtyMoved = qtyMoved;
		this.durationSetup = durationSetup;
		this.duration = duration;
	}

}
