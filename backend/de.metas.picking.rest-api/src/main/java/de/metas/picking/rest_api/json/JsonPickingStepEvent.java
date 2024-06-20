/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Jacksonized
public class JsonPickingStepEvent
{
	//
	// Picking Line/Step Identifier
	@NonNull String wfProcessId;
	@NonNull String wfActivityId;
	@NonNull String pickingLineId;
	@Nullable String pickingStepId;

	//
	// Event Type
	public enum EventType
	{PICK, UNPICK}

	@NonNull EventType type;

	//
	// Common
	@NonNull String huQRCode;

	//
	// Event Type: PICK
	@Nullable BigDecimal qtyPicked;
	@Nullable BigDecimal qtyRejected;
	@Nullable String qtyRejectedReasonCode;
	@Nullable BigDecimal catchWeight;
	boolean pickWholeTU;
	@Nullable Boolean checkIfAlreadyPacked;

	boolean setBestBeforeDate;
	@Nullable @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate bestBeforeDate;

	boolean setLotNo;
	@Nullable String lotNo;

	// Event Type: UNPICK
	@Nullable String unpickToTargetQRCode;
}
