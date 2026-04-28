/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.endpoint;

import de.metas.externalsystem.model.X_ExternalSystem_Endpoint;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SftpAuthType implements ReferenceListAwareEnum
{
	PASSWORD(X_ExternalSystem_Endpoint.SFTPAUTHTYPE_PASSWORD),
	SSH_KEY(X_ExternalSystem_Endpoint.SFTPAUTHTYPE_SSH_KEY);

	private static final ReferenceListAwareEnums.ValuesIndex<SftpAuthType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static SftpAuthType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
}
