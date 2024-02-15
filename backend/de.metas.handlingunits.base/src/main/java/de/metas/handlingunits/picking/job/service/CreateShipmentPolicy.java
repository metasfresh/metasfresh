/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.picking.job.service;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_MobileUI_UserProfile_Picking;

import javax.annotation.Nullable;

@Getter
@AllArgsConstructor
public enum CreateShipmentPolicy implements ReferenceListAwareEnum
{
	DO_NOT_CREATE(X_MobileUI_UserProfile_Picking.CREATESHIPMENTPOLICY_DO_NOT_CREATE),
	CREATE_DRAFT(X_MobileUI_UserProfile_Picking.CREATESHIPMENTPOLICY_CREATE_DRAFT),
	CREATE_AND_COMPLETE(X_MobileUI_UserProfile_Picking.CREATESHIPMENTPOLICY_CREATE_AND_COMPLETE),
	CREATE_COMPLETE_CLOSE(X_MobileUI_UserProfile_Picking.CREATESHIPMENTPOLICY_CREATE_COMPLETE_CLOSE),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<CreateShipmentPolicy> index = ReferenceListAwareEnums.index(values());

	private final String code;

	@NonNull
	public static CreateShipmentPolicy ofCode(@NonNull final String code) {return index.ofCode(code);}

	@NonNull
	public static CreateShipmentPolicy ofCodeOrName(@NonNull final String code) {return index.ofCodeOrName(code);}

	@Nullable
	public static CreateShipmentPolicy ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public boolean isCreateShipment() {return CREATE_DRAFT.equals(this) || CREATE_AND_COMPLETE.equals(this) || CREATE_COMPLETE_CLOSE.equals(this);}

	public boolean isCreateAndCompleteShipment() {return CREATE_AND_COMPLETE.equals(this) || CREATE_COMPLETE_CLOSE.equals(this);}

	public boolean isCloseShipmentSchedules() {return CREATE_COMPLETE_CLOSE.equals(this);}
}
