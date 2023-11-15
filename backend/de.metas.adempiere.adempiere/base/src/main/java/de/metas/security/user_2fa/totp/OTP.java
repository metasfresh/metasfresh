/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.security.user_2fa.totp;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * One Time Password
 */
@EqualsAndHashCode
public class OTP
{
	private final String string;

	private OTP(@NonNull final String string)
	{
		this.string = string;
	}

	public static OTP ofString(@NonNull String string)
	{
		return new OTP(string);
	}

	@Deprecated
	@Override
	public String toString() {return getAsString();}

	public String getAsString() {return string;}

	public static boolean equals(@Nullable final OTP otp1, @Nullable final OTP otp2) {return Objects.equals(otp1, otp2);}
}
