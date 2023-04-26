/*
 * #%L
 * marketing-base
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

package de.metas.marketing.base.model;

import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

@AllArgsConstructor
public enum DeactivatedOnRemotePlatform implements ReferenceListAwareEnum
{
	YES(X_MKTG_ContactPerson.DEACTIVATEDONREMOTEPLATFORM_YES),
	NO(X_MKTG_ContactPerson.DEACTIVATEDONREMOTEPLATFORM_NO),
	UNKNOWN(X_MKTG_ContactPerson.DEACTIVATEDONREMOTEPLATFORM_UNKNOWN),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<DeactivatedOnRemotePlatform> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	@NonNull
	public static DeactivatedOnRemotePlatform ofCode(@Nullable final String code)
	{
		return code != null && Check.isNotBlank(code) ? index.ofCode(code) : DeactivatedOnRemotePlatform.UNKNOWN;
	}

	public static DeactivatedOnRemotePlatform ofIsActiveFlag(final boolean active)
	{
		return active ? NO : YES;
	}

	public static boolean equals(@Nullable final DeactivatedOnRemotePlatform value1, @Nullable final DeactivatedOnRemotePlatform value2)
	{
		return Objects.equals(value1, value2);
	}

	public boolean isYes() {return this == YES;}

	public boolean isNo() {return this == NO;}

}
