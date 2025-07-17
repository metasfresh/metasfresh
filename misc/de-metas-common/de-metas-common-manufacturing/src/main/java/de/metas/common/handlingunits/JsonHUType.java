/*
 * #%L
 * de-metas-common-manufacturing
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

package de.metas.common.handlingunits;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = "JsonHUType: \n" +
		"* `LU` - Handling unit which accommodates Loading Unit\n" +
		"* `TU` - Handling unit which accommodates Transport Unit\n" +
		"* `CU` - Handling unit which accommodates Customer Unit\n" +
		"")
public enum JsonHUType
{
	LU,
	TU,
	CU
}
