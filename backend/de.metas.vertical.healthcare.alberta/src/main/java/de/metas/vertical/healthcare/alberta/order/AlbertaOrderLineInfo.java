/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.order;

import de.metas.ordercandidate.api.OLCandId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class AlbertaOrderLineInfo
{
	@NonNull
	OLCandId olCandId;

	@NonNull
	String externalId;

	@Nullable
	String salesLineId;

	@Nullable
	String unit;

	@Nullable
	Boolean isRentalEquipment;

	@Nullable
	Boolean isPrivateSale;

	@Nullable
	Instant updated;

	@Nullable
	BigDecimal durationAmount;

	@Nullable
	BigDecimal timePeriod;
}
