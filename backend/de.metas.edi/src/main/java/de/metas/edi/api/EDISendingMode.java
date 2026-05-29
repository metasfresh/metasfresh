/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.edi.api;

import de.metas.edi.model.I_C_BPartner;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor
@Getter
public enum EDISendingMode implements ReferenceListAwareEnum
{
	ReplicationInterface(I_C_BPartner.EDISendingMode_ReplicationInterface),
	ExternalSystem(I_C_BPartner.EDISendingMode_ExternalSystem);

	private static final ReferenceListAwareEnums.ValuesIndex<EDISendingMode> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@Nullable
	public static EDISendingMode ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@NonNull
	public static EDISendingMode ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isReplicationInterface() {return ReplicationInterface == this;}
	public boolean isExternalSystem() {return ExternalSystem == this;}
}
