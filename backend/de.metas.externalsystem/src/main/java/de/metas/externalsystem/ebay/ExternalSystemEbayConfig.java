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

package de.metas.externalsystem.ebay;

import java.util.List;

import javax.annotation.Nullable;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.pricing.PriceListId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class ExternalSystemEbayConfig implements IExternalSystemChildConfig 
{
	@NonNull
	ExternalSystemEbayConfigId id;
	@NonNull
	ExternalSystemParentConfigId parentId;
	@NonNull
	String value;
	@NonNull
	String appId;
	@NonNull
	String certId;
	@NonNull
	String devId;
	@NonNull
	String refreshToken;
	@NonNull
	List<ExternalSystemEbayConfigMapping> externalSystemEbayConfigMappingList;
	@NonNull
	ApiMode apiMode;
	@Nullable
	PriceListId priceListId;
	@NonNull
	Boolean isActive;

	@Builder
	public ExternalSystemEbayConfig(@NonNull final ExternalSystemEbayConfigId id,
			@NonNull final ExternalSystemParentConfigId parentId,
			@NonNull final String value,
			@NonNull final String appId, 
			@NonNull final String certId, 
			@NonNull final String devId,
			@NonNull final String refreshToken,
			@NonNull final List<ExternalSystemEbayConfigMapping> externalSystemEbayConfigMappingList,
			@NonNull final ApiMode apiMode, 
			@Nullable final PriceListId priceListId, 
			@NonNull final Boolean isActive) 
	{
		this.id = id;
		this.parentId = parentId;
		this.value = value;
		this.appId = appId;
		this.certId = certId;
		this.devId = devId;
		this.refreshToken = refreshToken;
		this.externalSystemEbayConfigMappingList = externalSystemEbayConfigMappingList;
		this.apiMode = apiMode;
		this.priceListId = priceListId;
		this.isActive = isActive;
	}

	@NonNull
	public static ExternalSystemEbayConfig cast(@NonNull final IExternalSystemChildConfig childConfig) 
	{
		return (ExternalSystemEbayConfig) childConfig;
	}

}
