/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.ad.persistence.modelgen;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

public enum NullableType
{
	NULLABLE("@Nullable ", javax.annotation.Nullable.class),
	NON_NULL("", null),
	DOES_NOT_APPLY("", null);

	@Getter
	private final String javaCode;

	@Getter
	private final ImmutableList<Class<?>> classesToImport;

	NullableType(
			@NonNull final String javaCode,
			@Nullable final Class<?> classToImport)
	{
		this.javaCode = javaCode;
		this.classesToImport = classToImport != null
				? ImmutableList.of(classToImport)
				: ImmutableList.of();
	}
}
