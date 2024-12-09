package de.metas.inoutcandidate.api;

<<<<<<< HEAD
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import de.metas.inout.ShipmentScheduleId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
=======
import de.metas.inout.ShipmentScheduleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class ShipmentScheduleUserChangeRequest
{
	@NonNull
	ShipmentScheduleId shipmentScheduleId;

	@Nullable
	BigDecimal qtyToDeliverStockOverride;

	@Nullable
	BigDecimal qtyToDeliverCatchOverride;

	@Nullable
	AttributeSetInstanceId asiId;

	@Nullable
	LocalDate bestBeforeDate;
}
