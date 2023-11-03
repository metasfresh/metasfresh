/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.window.descriptor.decorator;

import de.metas.i18n.BooleanWithReason;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ReadOnlyInfo
{
	public static final ReadOnlyInfo TRUE = ReadOnlyInfo.builder()
			.isReadOnly(BooleanWithReason.TRUE)
			.forceReadOnlySubDocuments(false)
			.build();
	public static final ReadOnlyInfo FALSE = ReadOnlyInfo.builder()
			.isReadOnly(BooleanWithReason.FALSE)
			.forceReadOnlySubDocuments(false)
			.build();

	@NonNull
	public static ReadOnlyInfo of(@NonNull final BooleanWithReason isReadOnly)
	{
		if (BooleanWithReason.TRUE.equals(isReadOnly))
		{
			return TRUE;
		}
		else if (BooleanWithReason.FALSE.equals(isReadOnly))
		{
			return FALSE;
		}
		else
		{
			return ReadOnlyInfo.builder()
					.isReadOnly(isReadOnly)
					.forceReadOnlySubDocuments(false)
					.build();
		}
	}

	@Getter(AccessLevel.NONE) @NonNull BooleanWithReason isReadOnly;
	boolean forceReadOnlySubDocuments;

	public boolean isReadOnly()
	{
		return isReadOnly.isTrue();
	}

	@NonNull
	public BooleanWithReason getIsReadOnlyWithReason()
	{
		return isReadOnly;
	}
}
