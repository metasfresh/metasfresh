/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.external.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

import static de.metas.serviceprovider.model.X_S_ExternalProjectReference.PROJECTTYPE_Budget;
import static de.metas.serviceprovider.model.X_S_ExternalProjectReference.PROJECTTYPE_Development;

@AllArgsConstructor
@Getter
public enum ExternalProjectType
{
	EFFORT(PROJECTTYPE_Development),
	BUDGET(PROJECTTYPE_Budget);

	private final String value;

	public static Optional<ExternalProjectType> getTypeByValue( final String value )
	{
		return Arrays.stream(values())
				.filter(projectType -> projectType.getValue().equals(value))
				.findFirst();
	}
}
