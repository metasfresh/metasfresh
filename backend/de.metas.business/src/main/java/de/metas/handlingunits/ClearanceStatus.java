/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_M_Product;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum ClearanceStatus implements ReferenceListAwareEnum
{
	Cleared(X_M_Product.HUCLEARANCESTATUS_Cleared),
	Locked(X_M_Product.HUCLEARANCESTATUS_Locked),
	Quarantined(X_M_Product.HUCLEARANCESTATUS_Quarantined),
	TestPending(X_M_Product.HUCLEARANCESTATUS_TestPending),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ClearanceStatus> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static ClearanceStatus ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static ClearanceStatus ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@NonNull
	public static Optional<ClearanceStatus> optionalOfNullableCode(@Nullable final String code)
	{
		return index.optionalOfNullableCode(code);
	}

	public static boolean equals(ClearanceStatus status1, ClearanceStatus status2) {return Objects.equals(status1, status2);}
}
