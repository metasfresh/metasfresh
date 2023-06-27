/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.github.config;

import de.metas.serviceprovider.model.X_S_GithubConfig;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum GithubConfigName implements ReferenceListAwareEnum
{
	ACCESS_TOKEN(X_S_GithubConfig.NAME_AccessToken),
	LOOK_FOR_PARENT(X_S_GithubConfig.NAME_LookForParent),
	GITHUB_SECRET(X_S_GithubConfig.NAME_GithubSecret),
	GITHUB_USER(X_S_GithubConfig.NAME_GithubUser),
	IMPORT_TIMEOUT_MINUTES(X_S_GithubConfig.NAME_ImportTimeoutMinutes),
	;

	@Getter
	@NonNull
	private final String code;

	@Nullable
	public static GithubConfigName ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	@NonNull
	public static GithubConfigName ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<GithubConfigName> index = ReferenceListAwareEnums.index(values());
}
