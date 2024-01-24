/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.mobile;

import com.google.common.collect.ImmutableList;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_MobileConfiguration;
import org.springframework.lang.Nullable;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum MobileAuthMethod implements ReferenceListAwareEnum
{
	QR_CODE(X_MobileConfiguration.DEFAULTAUTHENTICATIONMETHOD_QRCode),
	USER_PASS(X_MobileConfiguration.DEFAULTAUTHENTICATIONMETHOD_UserPass),
	;

	@Getter
	private final String code;

	@NonNull
	public static Stream<MobileAuthMethod> stream()
	{
		return index.streamValues();
	}

	@NonNull
	public static ImmutableList<MobileAuthMethod> all()
	{
		return stream().collect(ImmutableList.toImmutableList());
	}

	@Nullable
	public static MobileAuthMethod ofNullableCode(final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	@NonNull
	public static MobileAuthMethod ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<MobileAuthMethod> index = ReferenceListAwareEnums.index(values());
}
