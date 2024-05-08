/*
 * #%L
 * de.metas.business
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

package org.eevolution.api;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_DocType;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum PPOrderDocBaseType implements ReferenceListAwareEnum
{
	MANUFACTURING_ORDER(X_C_DocType.DOCBASETYPE_ManufacturingOrder),
	QUALITY_ORDER(X_C_DocType.DOCBASETYPE_QualityOrder),
	MAINTENANCE_ORDER(X_C_DocType.DOCBASETYPE_MaintenanceOrder),
	REPAIR_ORDER(X_C_DocType.DOCBASETYPE_ServiceRepairOrder);

	@Getter
	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<PPOrderDocBaseType> index = ReferenceListAwareEnums.index(values());

	@Nullable
	public static PPOrderDocBaseType ofNullableCode(@Nullable final String code) { return index.ofNullableCode(code); }

	public static PPOrderDocBaseType ofCode(@NonNull final String code) { return index.ofCode(code); }

	public boolean isManufacturingOrder() { return MANUFACTURING_ORDER.equals(this); }

	public boolean isQualityOrder() { return QUALITY_ORDER.equals(this); }

	public boolean isRepairOrder() { return REPAIR_ORDER.equals(this); }
}
