package org.eevolution.api;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;
import org.eevolution.model.I_PP_Order_Node;

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
	I_PP_Order_Node node;
	Date movementDate;
	BigDecimal qtyMoved;
	int durationSetup;
	BigDecimal duration;

	@Builder
	private ActivityControlCreateRequest(
			@NonNull final I_PP_Order_Node node,
			@Nullable final Date movementDate,
			@NonNull final BigDecimal qtyMoved,
			final int durationSetup,
			@NonNull final BigDecimal duration)
	{
		Check.assume(durationSetup >= 0, "durationSetup >= 0 but it was {}", durationSetup);
		Check.assume(duration.signum() >= 0, "duration >= 0 but it was {}", duration);

		this.node = node;
		this.movementDate = movementDate != null ? movementDate : SystemTime.asTimestamp();
		this.qtyMoved = qtyMoved;
		this.durationSetup = durationSetup;
		this.duration = duration;
	}

}
