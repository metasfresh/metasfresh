/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.manufacturing.job.service.commands;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Value
@Builder
public class ReceiveGoodsRequest
{
	@NonNull PPOrderId ppOrderId;
	@NonNull SelectedReceivingTarget receivingTarget;
	@NonNull BigDecimal qtyToReceiveBD;
	@NonNull ZonedDateTime date;
	@Nullable LocalDate bestBeforeDate;
	@Nullable String lotNo;
	@Nullable Quantity catchWeight;
	@Nullable PPOrderBOMLineId coProductBOMLineId;
}
