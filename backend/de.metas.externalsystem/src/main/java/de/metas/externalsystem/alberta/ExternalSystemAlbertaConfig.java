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

package de.metas.externalsystem.alberta;

import de.metas.bpartner.BPartnerId;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.pricing.PriceListId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemAlbertaConfig implements IExternalSystemChildConfig
{
	@NonNull
	ExternalSystemAlbertaConfigId id;
	@NonNull
	ExternalSystemParentConfigId parentId;
	@NonNull
	String value;
	@NonNull
	String apiKey;
	@NonNull
	String baseUrl;
	@NonNull
	String tenant;
	@Nullable
	PriceListId pharmacyPriceListId;
	@Nullable
	BPartnerId rootBPartnerIdForUsers;

	@Builder
	public ExternalSystemAlbertaConfig(final @NonNull ExternalSystemAlbertaConfigId id,
			final @NonNull ExternalSystemParentConfigId parentId,
			final @NonNull String value,
			final @NonNull String apiKey,
			final @NonNull String baseUrl,
			final @NonNull String tenant,
			final @Nullable PriceListId pharmacyPriceListId,
			final @Nullable BPartnerId rootBPartnerIdForUsers)
	{
		this.id = id;
		this.parentId = parentId;
		this.value = value;
		this.apiKey = apiKey;
		this.baseUrl = baseUrl;
		this.tenant = tenant;
		this.pharmacyPriceListId = pharmacyPriceListId;
		this.rootBPartnerIdForUsers = rootBPartnerIdForUsers;
	}

	public static ExternalSystemAlbertaConfig cast(@NonNull final IExternalSystemChildConfig childCondig)
	{
		return (ExternalSystemAlbertaConfig)childCondig;
	}
}
