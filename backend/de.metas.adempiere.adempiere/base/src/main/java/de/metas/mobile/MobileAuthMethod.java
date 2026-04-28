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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableList;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_MobileConfiguration;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
public enum MobileAuthMethod implements ReferenceListAwareEnum
{
	QR_CODE(X_MobileConfiguration.DEFAULTAUTHENTICATIONMETHOD_QRCode),
	USER_PASS(X_MobileConfiguration.DEFAULTAUTHENTICATIONMETHOD_UserPass),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<MobileAuthMethod> index = ReferenceListAwareEnums.index(values());
	private static final ImmutableList<MobileAuthMethod> list = index.toList();

	@NonNull private final String code;

	@NonNull
	public static ImmutableList<MobileAuthMethod> all() {return list;}

	@Nullable
	public static MobileAuthMethod ofNullableCode(final String code)
	{
		return index.ofNullableCode(code);
	}

	@NonNull
	@JsonCreator
	public static MobileAuthMethod ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Override
	@JsonValue
	@NonNull
	public String getCode() {return code;}
}
