/*
 * #%L
 * de.metas.printing.base
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

package de.metas.printing;

import de.metas.printing.model.X_AD_PrinterHW;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum OutputType implements ReferenceListAwareEnum
{
	Attach(X_AD_PrinterHW.OUTPUTTYPE_Attach),
	Queue(X_AD_PrinterHW.OUTPUTTYPE_Queue),
	Store(X_AD_PrinterHW.OUTPUTTYPE_Store),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<OutputType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static OutputType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public static OutputType ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static boolean equals(@Nullable final OutputType type1, @Nullable final OutputType type2) {return Objects.equals(type1, type2);}

	public boolean isStore() {return this == Store;}
}
