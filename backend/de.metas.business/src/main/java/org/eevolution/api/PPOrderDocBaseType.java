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

import de.metas.document.DocBaseType;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

public enum PPOrderDocBaseType implements ReferenceListAwareEnum
{
	MANUFACTURING_ORDER(DocBaseType.ManufacturingOrder),
	QUALITY_ORDER(DocBaseType.QualityOrder),
	MAINTENANCE_ORDER(DocBaseType.MaintenanceOrder),
	REPAIR_ORDER(DocBaseType.ServiceRepairOrder),
	;

	private final DocBaseType docBaseType;

	private static final ReferenceListAwareEnums.ValuesIndex<PPOrderDocBaseType> index = ReferenceListAwareEnums.index(values());

	PPOrderDocBaseType(@NonNull final DocBaseType docBaseType)
	{
		this.docBaseType = docBaseType;
	}

	@Nullable
	public static PPOrderDocBaseType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static PPOrderDocBaseType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@NonNull
	public static Optional<PPOrderDocBaseType> optionalOfNullable(@Nullable final String code) {return Optional.ofNullable(ofNullableCode(code));}

	public static PPOrderDocBaseType ofDocBaseType(@NonNull final DocBaseType docBaseType) {return ofCode(docBaseType.getCode());}

	public DocBaseType toDocBaseType() {return docBaseType;}

	@Override
	public String getCode() {return docBaseType.getCode();}

	public boolean isManufacturingOrder() {return MANUFACTURING_ORDER.equals(this);}

	public boolean isQualityOrder()
	{
		return QUALITY_ORDER.equals(this);
	}

	public boolean isRepairOrder() {return REPAIR_ORDER.equals(this);}
}
