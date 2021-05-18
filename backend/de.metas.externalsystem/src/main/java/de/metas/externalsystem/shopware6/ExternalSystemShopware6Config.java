/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.shopware6;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemShopware6Config implements IExternalSystemChildConfig
{
	@NonNull
	ExternalSystemShopware6ConfigId id;
	@NonNull
	ExternalSystemParentConfigId parentId;
	@NonNull
	String baseUrl;
	@NonNull
	String clientId;
	@NonNull
	String clientSecret;
	@Nullable
	String bPartnerIdJSONPath;
	@Nullable
	String bPartnerLocationIdJSONPath;

	@Builder
	public ExternalSystemShopware6Config(final @NonNull ExternalSystemShopware6ConfigId id,
			final @NonNull ExternalSystemParentConfigId parentId,
			final @NonNull String baseUrl,
			final @NonNull String clientId,
			final @NonNull String clientSecret,
			final @Nullable String bPartnerIdJSONPath,
			final @Nullable String bPartnerLocationIdJSONPath)
	{
		this.id = id;
		this.parentId = parentId;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.baseUrl = baseUrl;
		this.bPartnerIdJSONPath = bPartnerIdJSONPath;
		this.bPartnerLocationIdJSONPath = bPartnerLocationIdJSONPath;
	}

	public static ExternalSystemShopware6Config cast(@NonNull final IExternalSystemChildConfig childConfig)
	{
		return (ExternalSystemShopware6Config)childConfig;
	}
}
