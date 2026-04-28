/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public enum CarrierAdviseStatus implements ReferenceListAwareEnum
{
	NotRequested(X_M_ShipmentSchedule.CARRIER_ADVISING_STATUS_NotRequested),
	Requested(X_M_ShipmentSchedule.CARRIER_ADVISING_STATUS_Requested),
	InProgress(X_M_ShipmentSchedule.CARRIER_ADVISING_STATUS_InProgress),
	Completed(X_M_ShipmentSchedule.CARRIER_ADVISING_STATUS_Completed),
	Failed(X_M_ShipmentSchedule.CARRIER_ADVISING_STATUS_Failed),
	Manual(X_M_ShipmentSchedule.CARRIER_ADVISING_STATUS_Manual);

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<CarrierAdviseStatus> index = de.metas.util.lang.ReferenceListAwareEnums.index(values());

	@NonNull @Getter private final String code;

	@NonNull
	public static CarrierAdviseStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static CarrierAdviseStatus ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@JsonValue
	public String toJson() {return getCode();}

	public boolean isRequested() {return Requested.equals(this);}

	public boolean isManual() {return Manual.equals(this);}

	public boolean isInProgress() {return InProgress.equals(this);}

	public boolean isEligibleForAutoEnqueue() {return !isManual() && !isInProgress() && !isRequested();}

	public boolean isEligibleForManualEnqueue() {return !isInProgress() && !isRequested();}
}
