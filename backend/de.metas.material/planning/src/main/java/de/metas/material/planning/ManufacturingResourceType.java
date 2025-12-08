/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.material.planning;

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
	ExternalSystem(X_S_Resource.MANUFACTURINGRESOURCETYPE_ExternalSystem);

	@Getter
	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<ManufacturingResourceType> index = ReferenceListAwareEnums.index(values());

	public static ManufacturingResourceType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static Optional<ManufacturingResourceType> ofNullableCode(@Nullable final String code)
	{
		return index.optionalOfNullableCode(code);
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final ManufacturingResourceType type)
	{
		return type == null ? null : type.getCode();
	}
}
