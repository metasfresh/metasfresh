/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.process;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
public final class ProcessHandlerType
{
	public static final ProcessHandlerType AD_Process = ProcessHandlerType.ofCode("ADP");

	@NonNull private final String code;

	private ProcessHandlerType(@NonNull final String code)
	{
		Check.assumeNotEmpty(code, "code shall not be empty");
		this.code = code;
	}

	public static ProcessHandlerType ofCode(@NonNull final String code)
	{
		return new ProcessHandlerType(code);
	}

	@Override
	public String toString() {return getAsString();}

	public String getAsString() {return code;}

	public static boolean equals(@Nullable ProcessHandlerType type1, @Nullable ProcessHandlerType type2) {return Objects.equals(type1, type2);}

	public boolean isADProcess() {return equals(this, AD_Process);}
}
