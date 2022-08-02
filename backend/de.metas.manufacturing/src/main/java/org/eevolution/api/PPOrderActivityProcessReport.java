package org.eevolution.api;

import de.metas.common.util.time.SystemTime;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;
import java.time.Instant;

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
@Builder
public class PPOrderActivityProcessReport
{
	@NonNull
	PPOrderRoutingActivityId activityId;

	Quantity qtyProcessed;
	Quantity qtyScrapped;
	Quantity qtyRejected;

	@NonNull
	@Default
	Duration duration = Duration.ZERO;
	@NonNull
	@Default
	Duration setupTime = Duration.ZERO;

	@NonNull
	@Default
	Instant finishDate = SystemTime.asInstant();
}
