/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.resource;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_S_Resource;

import javax.annotation.Nullable;
import java.util.Optional;

@AllArgsConstructor
public enum ManufacturingResourceType implements ReferenceListAwareEnum
{
	ProductionLine(X_S_Resource.MANUFACTURINGRESOURCETYPE_ProductionLine),
	Plant(X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant),
	WorkCenter(X_S_Resource.MANUFACTURINGRESOURCETYPE_WorkCenter),
	WorkStation(X_S_Resource.MANUFACTURINGRESOURCETYPE_WorkStation),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ManufacturingResourceType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	public static ManufacturingResourceType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static ManufacturingResourceType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static Optional<ManufacturingResourceType> optionalOfNullableCode(@Nullable final String code)
	{
		return index.optionalOfNullableCode(code);
	}

	public boolean isPlant() {return this == Plant;}

	public static boolean isPlant(@Nullable final String code)
	{
		final ManufacturingResourceType type = ofNullableCode(code);
		return type != null && type.isPlant();
	}

	public static String toCode(@Nullable ManufacturingResourceType type) {return type != null ? type.getCode() : null;}

	public boolean isWorkstation() {return WorkStation.equals(this);}
}
